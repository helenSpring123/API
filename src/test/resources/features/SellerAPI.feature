Feature:  Test Seller API

  @getSellerVerifyEmailNotEmpty  @regression
  Scenario: get a single seller and verify seller email is nto empty

    Given user hits get single seller api with "/api/myaccount/sellers/"
    Then verify seller email is not empty



  @getAllSellers @regression
    Scenario: get all sellers and verify seller id is not 0
      Given user hits get all sellers api with "/api/myaccount/sellers"
    Then verify seller id are not equal to 0

    @updateSeller  @regression
    Scenario: get single seller, update the same seller, verify seller was updated
      Given user hits get single seller api with "/api/myaccount/sellers/"
      Then verify seller email is not empty
      Then user hits put all sellers api with "/api/myaccount/sellers/"
      Then verify user email was updated
      And verify user firstname was updated

      @archiveSeller  @regression
      Scenario: get a single seller, and archive the seller, and verify seller was archived
        Given user hits get single seller api with "/api/myaccount/sellers/"
        Then user hits archive api with "/api/myaccount/sellers/archive/unarchive"
        Then user hit get all sellers api with "/api/myaccount/sellers"


        @createdelete  @regression
        Scenario: create a seller, delete a seller, verify a seller was deleted
          Given the user hits post api with "/api/myaccount/sellers"
          Then verify seller id was generated
          Then verify seller name is not empty
          And verify seller email is not empty
          Then delete the seller with "/api/myaccount/sellers/"
          Then user hits get all sellers api with "/api/myaccount/sellers"
          Then verify deleted seller is not on the list



















