Feature: Trainer Summary Service

  Scenario: Get existing trainer's workload for a given month
    Given a trainer record with username "John.Trainer" for the month "JULY" in 2025 with duration 90
    When a request to retrieve workload by username "John.Trainer" for "JULY" 2025 is made
    Then the trainer summary in result must be 90

  Scenario: Get non-existing trainer's workload for a given month
    When a request to retrieve workload by username "Non.Exist" for "JULY" 2025 is made
    Then the trainer summary in result must be 0

  Scenario: Add training workload to trainer summary
    Given a trainer workload request with duration 60 on the date "2030-06-15" and action type "ADD"
    When a request to process trainer workload with given trainer workload request from context
    Then the trainer summary in result must contain 60 workload hours for the month "2030-06-15"

  Scenario: Delete training workload from trainer summary
    Given a trainer record with username "John.Trainer" for the month "SEPTEMBER" in 2025 with duration 120
    And a trainer workload request with duration 30 on the date "2025-09-15" and action type "DELETE"
    When a request to process trainer workload with given trainer workload request from context
    Then the trainer summary in result must contain 90 workload hours for the month "2025-09-15"