-- Insert the category first
INSERT INTO rule_category (category_name, description, is_active)
VALUES ('DAILY', 'Daily meeting rules', true)
ON CONFLICT (category_name) DO NOTHING;

-- Then insert your rules
INSERT INTO rule_definition (rule_name, rule_content, category_id, is_active, priority, description)
VALUES (
    'Test Rule',
    'rule "Test Rule"
    when
        $meeting : Meeting(meetingType == MeetingType.DAILY)
    then
        System.out.println("Test rule fired for meeting: " + $meeting.getMeetingType());
        results.put("appliedRule", "Test Rule");
    end',
    (SELECT id FROM rule_category WHERE category_name = 'DAILY'),
    true,
    10,
    'Daily meeting rule for testing'
);