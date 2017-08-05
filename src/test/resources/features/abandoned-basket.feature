Feature:
  So that I can see potential sales
  As a marketer
  I want to see what's left in baskets

  Background:
    Given a running application

    @pending
  Scenario:
    When I GET "/basket/tom@example.com"
    Then I see JSON like "basket"
    And it has status 200
