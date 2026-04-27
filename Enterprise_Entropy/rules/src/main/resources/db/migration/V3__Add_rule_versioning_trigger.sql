-- =====================================================
-- Migration: V3__Add_rule_versioning_trigger.sql
-- Description: Automatically version rules when content changes
-- =====================================================

SET search_path TO rules;

-- Function to automatically increment version when rule content changes
CREATE OR REPLACE FUNCTION increment_rule_version()
    RETURNS TRIGGER AS $$
BEGIN
    -- Only increment version if rule_content or priority changed
    IF (OLD.rule_content IS DISTINCT FROM NEW.rule_content) OR
       (OLD.priority IS DISTINCT FROM NEW.priority) THEN
        NEW.rule_version := OLD.rule_version + 1;
        NEW.updated_at := CURRENT_TIMESTAMP;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create trigger for rule_definition
CREATE OR REPLACE TRIGGER trigger_version_rules
    BEFORE UPDATE ON rule_definition
    FOR EACH ROW
EXECUTE FUNCTION increment_rule_version();

-- Function to update the updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Add trigger for updated_at on rule_definition
CREATE OR REPLACE TRIGGER trigger_rule_definition_updated_at
    BEFORE UPDATE ON rule_definition
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- Add trigger for updated_at on rule_category
CREATE OR REPLACE TRIGGER trigger_rule_category_updated_at
    BEFORE UPDATE ON rule_category
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();