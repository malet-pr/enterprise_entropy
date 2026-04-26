-- =====================================================
-- Migration: V2__Add_categories.sql
-- Description: Insert rule categories
-- =====================================================

SET search_path TO rules;

-- Insert categories
INSERT INTO rule_category (category_name, description, kie_session_name, refresh_interval_minutes, is_active, created_by) VALUES
    ('DAILY', 'Rules for daily meetings', 'daily', 1440, true, 'MIGRATION'),
    ('PLANNING', 'Rules for planning meetings', 'planning', 1440, true, 'MIGRATION'),
    ('COLLECTIVE_DEBUGGING_IN_ENVIRONMENT', 'Rules for collective debugging', 'debugging', 1440, true, 'MIGRATION')
ON CONFLICT (category_name) DO NOTHING;

-- Verify inserts
DO $$
DECLARE
    category_count INTEGER;
BEGIN
    SELECT COUNT(*) INTO category_count FROM rule_category;
    RAISE NOTICE 'Inserted % rule categories', category_count;
END $$;