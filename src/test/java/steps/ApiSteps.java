package steps;

import com.github.javafaker.Faker;
import com.sun.source.tree.AssertTree;
import entities.Requestbody;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import utilities.APIRunner;

import java.util.HashMap;
import java.util.Map;

public class ApiSteps {
    Faker faker = new Faker();
    String email;
    String sellername;
    int sellerid;

    @Given("user hits get single seller api with {string}")
    public void user_hits_get_single_seller_api_with(String endpoint) {
        APIRunner.runGET(endpoint,4632);
        sellerid = APIRunner.getCustomResponse().getSeller_id();
    }

    @Then("verify seller email is not empty")
    public void verify_seller_email_is_not_empty() {
       String email = APIRunner.getCustomResponse().getEmail();
        Assert.assertFalse(email.isEmpty());
    }

    @Given("user hits get all sellers api with {string}")
    public void user_hits_get_all_sellers_api_with(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 2);
        APIRunner.runGET(endpoint, params);

    }
    @Then("verify seller id are not equal to {int}")
    public void verify_seller_id_are_not_equal_to(int int1) {
      int size = APIRunner.getCustomResponse().getResponses().size();

      for(int i =0; i<size; i++){
          int sellerId = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
          Assert.assertNotEquals(int1, sellerId);
      }


    }

    @Then("user hits put all sellers api with {string}")
    public void user_hits_put_all_sellers_api_with(String endpoint) {
        Requestbody requestbody = new Requestbody();
        requestbody.setCompany_name(faker.company().name());
        requestbody.setSeller_name(faker.name().firstName());
        requestbody.setEmail(faker.internet().emailAddress());
        requestbody.setPhone_number(faker.phoneNumber().phoneNumber());
        requestbody.setAddress(faker.address().fullAddress());

        APIRunner.runPUT(endpoint, requestbody, 4632);
        email = APIRunner.getCustomResponse().getEmail();
        sellername = APIRunner.getCustomResponse().getSeller_name();

    }

    @Then("verify user email was updated")
    public void verify_user_email_was_updated() {
        Assert.assertFalse(email.isEmpty());
    }

    @Then("verify user firstname was updated")
    public void verify_user_firstname_was_updated() {
      Assert.assertFalse(sellername.isEmpty());
    }



    @Then("user hits archive api with {string}")
    public void user_hits_archive_api_with(String endpoint) {

        Map<String, Object> params = new HashMap<>();
        params.put("sellersIdsForArchive", sellerid);
        params.put("archive", true);
        APIRunner.runPOST(endpoint, params);

    }

    @Then("user hit get all sellers api with {string}")
    public void user_hit_get_all_sellers_api_with(String endpoint) {

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", true);
        params.put("page", 1);
        params.put("size", 2);
        APIRunner.runGET(endpoint, params);

        boolean isPresent = false;

        int size = APIRunner.getCustomResponse().getResponses().size();

        for(int i =0; i<size; i++){

            int ids = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            if(sellerid == ids){
                isPresent = true;
                break;
            }
        }
        Assert.assertTrue(isPresent);

    }

    @Given("the user hits post api with {string}")
    public void the_user_hits_post_api_with(String endpoint) {
       Requestbody requestbody = new Requestbody();
       requestbody.setCompany_name("Pearson company");
       requestbody.setSeller_name("Mary");
       requestbody.setEmail(faker.internet().emailAddress());
       requestbody.setPhone_number("486486778");
       requestbody.setAddress("2234 fairwood dr");

       APIRunner.runPOST(endpoint, requestbody);
       sellerid = APIRunner.getCustomResponse().getSeller_id();
       sellername = APIRunner.getCustomResponse().getSeller_name();

    }
    @Then("verify seller id was generated")
    public void verify_seller_id_was_generated() {
       Assert.assertTrue(sellerid != 0);
    }
    @Then("verify seller name is not empty")
    public void verify_seller_name_is_not_empty() {
        Assert.assertFalse(sellername.isEmpty());

    }
    @Then("delete the seller with {string}")
    public void delete_the_seller_with(String endpoint) {
      APIRunner.runDELETE(endpoint + sellerid);
    }
    @Then("verify deleted seller is not on the list")
    public void verify_deleted_seller_is_not_on_the_list() {

        boolean isEmpty = true;
        int size = APIRunner.getCustomResponse().getResponses().size();
        for(int i=0; i<size; i++){

            int id = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();

            if(id != sellerid){
                isEmpty = false;
                break;
            }
        }
        Assert.assertFalse(isEmpty);

    }








}
