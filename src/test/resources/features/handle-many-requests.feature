Feature:
  So that I can get on with my work
  As a busy user
  I want the server to handle many requests without making me wait

  Background:
    Given a running application

  Scenario:  Handle some processing in serial order
    When I POST 10 serial processes
    Then I get a valid response for each one
    And it takes less than 1 second