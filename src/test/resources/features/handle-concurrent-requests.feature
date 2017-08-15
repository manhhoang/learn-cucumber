Feature:
  So that I can handle browser traffic
  As a busy sysadmin
  I want the server to many concurrent requests

  Background:
    Given a running application

  Scenario: Handle concurrent processing
    When I POST 1000 concurrent requests
    Then I get the count 1000 back