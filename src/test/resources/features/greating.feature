Feature:
  So that I can feel welcome
  As a user
  I want to be greeted

  Background:
    Given a running application

  Scenario: get hello world
    When I GET "/hello-world"
    Then I see JSON like "hello-world"
    And it has status 200