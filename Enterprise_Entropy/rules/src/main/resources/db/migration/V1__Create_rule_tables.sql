-- =====================================================
-- Migration: V1__Create_rule_tables.sql
-- Description: Initial schema for Drools rule storage
-- Author: Your POC Team
-- Date: 2026-04-25
-- =====================================================

-- Create schema for better organization
CREATE SCHEMA IF NOT EXISTS rules;

-- Set search path
SET search_path TO rules;

-- =====================================================
-- Table: rule_category
-- Groups rules by functional area for efficient loading
-- =====================================================
CREATE TABLE IF NOT EXISTS rule_category (
                                             id BIGSERIAL PRIMARY KEY,
                                             category_name VARCHAR(100) NOT NULL UNIQUE,
                                             description TEXT,
                                             kie_session_name VARCHAR(100),
                                             refresh_interval_minutes INTEGER DEFAULT 60,
                                             is_active BOOLEAN DEFAULT TRUE,

    -- Audit columns
                                             created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                             created_by VARCHAR(100) DEFAULT 'SYSTEM',
                                             updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                             updated_by VARCHAR(100) DEFAULT 'SYSTEM',

    -- Indexes
                                             CONSTRAINT uk_category_name UNIQUE (category_name)
);

-- =====================================================
-- Table: rule_definition
-- Stores the actual DRL rules
-- =====================================================
CREATE TABLE IF NOT EXISTS rule_definition (
                                               id BIGSERIAL PRIMARY KEY,
                                               rule_name VARCHAR(255) NOT NULL,
                                               rule_content TEXT NOT NULL,
                                               category_id BIGINT NOT NULL,
                                               rule_version INTEGER NOT NULL DEFAULT 1,
                                               is_active BOOLEAN DEFAULT TRUE,
                                               priority INTEGER DEFAULT 0,
                                               description TEXT,
                                               version INTEGER,

    -- Metadata for rule management
                                               metadata JSONB,  -- Store additional metadata like tags, authors, etc.

    -- Execution statistics
                                               last_executed_at TIMESTAMP WITH TIME ZONE,
                                               execution_count BIGINT DEFAULT 0,
                                               average_execution_time_ms DECIMAL(10, 2),

    -- Audit columns
                                               created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                               created_by VARCHAR(100) DEFAULT 'SYSTEM',
                                               updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                                               updated_by VARCHAR(100) DEFAULT 'SYSTEM',

    -- Constraints
                                               CONSTRAINT fk_rule_category FOREIGN KEY (category_id)
                                                   REFERENCES rule_category(id) ON DELETE RESTRICT,
                                               CONSTRAINT uk_rule_name_version UNIQUE (rule_name, rule_version),
                                               CONSTRAINT chk_priority CHECK (priority >= 0)
);

-- =====================================================
-- Table: rule_execution_log
-- For auditing and debugging rule executions
-- =====================================================
CREATE TABLE IF NOT EXISTS rule_execution_log (
                                                  id BIGSERIAL PRIMARY KEY,
                                                  rule_id BIGINT NOT NULL,
                                                  category_name VARCHAR(100) NOT NULL,
                                                  execution_start TIMESTAMP WITH TIME ZONE NOT NULL,
                                                  execution_end TIMESTAMP WITH TIME ZONE,
                                                  execution_time_ms INTEGER,
                                                  success BOOLEAN,
                                                  error_message TEXT,
                                                  input_data_hash VARCHAR(64),  -- For tracking which inputs triggered the rule
                                                  correlation_id VARCHAR(100),   -- To group related executions

    -- Additional context
                                                  executed_by VARCHAR(100) DEFAULT 'SYSTEM',
                                                  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- Indexes for query performance
                                                  CONSTRAINT fk_execution_rule FOREIGN KEY (rule_id)
                                                      REFERENCES rule_definition(id) ON DELETE CASCADE
);

-- =====================================================
-- Create Indexes for Performance
-- =====================================================

-- Index on rule_category for active lookups
CREATE INDEX IF NOT EXISTS idx_rule_category_active ON rule_category(is_active)
    WHERE is_active = TRUE;

-- Indexes on rule_definition
CREATE INDEX IF NOT EXISTS idx_rule_definition_category ON rule_definition(category_id);
CREATE INDEX IF NOT EXISTS idx_rule_definition_active ON rule_definition(is_active)
    WHERE is_active = TRUE;
CREATE INDEX IF NOT EXISTS idx_rule_definition_priority ON rule_definition(is_active, priority DESC)
    WHERE is_active = TRUE;

-- Composite index for common query pattern
CREATE INDEX IF NOT EXISTS idx_rule_active_category_priority ON rule_definition(is_active, category_id, priority DESC);

-- Index for execution logs (time-based queries)
CREATE INDEX IF NOT EXISTS idx_execution_log_time ON rule_execution_log(execution_start DESC);
CREATE INDEX IF NOT EXISTS idx_execution_log_category ON rule_execution_log(category_name);
CREATE INDEX IF NOT EXISTS idx_execution_log_correlation ON rule_execution_log(correlation_id);

-- =====================================================
-- Table Comments for Documentation
-- =====================================================

COMMENT ON SCHEMA rules IS 'Schema for Drools rule management system';
COMMENT ON TABLE rule_category IS 'Categories for grouping business rules (e.g., DAILY,PLANNING,COLLECTIVE_DEBUGGING_IN_ENVIRONMENT)';
COMMENT ON TABLE rule_definition IS 'Storage for DRL rule definitions with versioning';
COMMENT ON TABLE rule_execution_log IS 'Audit log for rule executions, useful for debugging and performance analysis';

COMMENT ON COLUMN rule_definition.rule_content IS 'The actual DRL rule content';
COMMENT ON COLUMN rule_definition.metadata IS 'JSON metadata including tags, author, department, etc.';
COMMENT ON COLUMN rule_execution_log.input_data_hash IS 'SHA-256 hash of input facts for debugging';