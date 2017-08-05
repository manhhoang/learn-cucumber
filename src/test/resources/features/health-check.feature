Feature:
  So that I know the service is running correctly
  As a sysadmin
  I want to see a health check

  Background:
    Given a running application

    @wip
  Scenario: get greeting health check
    When I check the application health
    Then it has status 200
    And I see status "UP" for "greetingHealthCheck"
