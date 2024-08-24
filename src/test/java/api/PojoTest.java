package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import entities.CustomResponse;
import entities.Requestbody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.Test;
import utilities.APIRunner;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PojoTest {
Faker faker = new Faker();

@Test
    public void CreateCategory() throws JsonProcessingException {

    String url = Config.getProperties("cashwiseurl") + "/api/myaccount/categories";
    String token = CashWiseToken.getToken();

    Requestbody requestbody = new Requestbody();
    requestbody.setCategory_title(faker.name().title());
    requestbody.setCategory_description(faker.name().firstName());
    requestbody.setFlag(true);

    Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
            .body(requestbody).post(url);


    int status = response.statusCode();
    Assert.assertEquals(201, status);


    ObjectMapper mapper = new ObjectMapper();

   CustomResponse customResponse =  mapper.readValue(response.asString(), CustomResponse.class);
    System.out.println(customResponse.getCategory_id());



}

@Test
    public void CreateGetCategory() throws JsonProcessingException {

    String url = Config.getProperties("cashwiseurl") + "/api/myaccount/categories";
    String token = CashWiseToken.getToken();

    Requestbody requestbody = new Requestbody();
    requestbody.setCategory_title(faker.name().title());
    requestbody.setCategory_description(faker.name().firstName());
    requestbody.setFlag(true);

    Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
            .body(requestbody).post(url);

    int status = response.statusCode();

    Assert.assertEquals(201,status);

    ObjectMapper mapper = new ObjectMapper();

    CustomResponse response1 = mapper.readValue(response.asString(), CustomResponse.class);
    int id = response1.getCategory_id();


    String url2 = Config.getProperties("cashwiseurl") + "/api/myaccount/categories/" + id;

    Response response2 = RestAssured.given().auth().oauth2(token).get(url2);

    int statusCode2 = response2.statusCode();
    Assert.assertEquals(200, statusCode2);


    CustomResponse response3 = mapper.readValue(response2.asString(), CustomResponse.class);
    int id2 = response3.getCategory_id();
    Assert.assertEquals(id, id2);


}

@Test
  public void Sellers15() {
    String url = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers";
    String token = CashWiseToken.getToken();

    Requestbody requestbody = new Requestbody();
    requestbody.setSeller_name(faker.name().firstName());
    requestbody.setCompany_name(faker.company().name());
    requestbody.setEmail(faker.name() + "@gmail.com");
    requestbody.setPhone_number(faker.phoneNumber().phoneNumber());
    requestbody.setAddress(faker.address().fullAddress());

    for(int i = 0; i<=15; i++){
        Response response =RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestbody).post(url);


    }





}

@Test
    public void getAllSellers() throws JsonProcessingException {
    String url = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers";
    String token = CashWiseToken.getToken();

    Map<String, Object> params = new HashMap<>();
    params.put("isArchived", false);
    params.put("page",1);
    params.put("size", 100);

    Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

    int status = response.statusCode();
    Assert.assertEquals(200, status);

    ObjectMapper mapper = new ObjectMapper();
    CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

    int size = customResponse.getResponses().size();

    for(int i =0; i<size; i++){
        
        String email = customResponse.getResponses().get(i).getEmail();
        Assert.assertFalse(email.isEmpty());


    }


}

@Test
    public void CreateSellerNegative(){

    String url = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers";
    String token = CashWiseToken.getToken();

    Requestbody requestbody = new Requestbody();
    requestbody.setCompany_name(faker.company().name());
    requestbody.setSeller_name(faker.name().firstName());
    requestbody.setPhone_number(faker.phoneNumber().phoneNumber());
    requestbody.setAddress(faker.address().fullAddress());

    Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
            .body(requestbody).post(url);

    int statusCode = response.statusCode();
    Assert.assertEquals(201, statusCode);





}

@Test
    public void ArchievdSellers(){

    String url = "https://backend.cashwise.us" + "/api/myaccount/sellers/archive/unarchive";
    String token = CashWiseToken.getToken();

    Map<String, Object> params = new HashMap<>();
    params.put("sellersIdsForArchive", 5496890);
    params.put("archive", true);

    Response response = RestAssured.given().auth().oauth2(token).params(params).post(url);

    int statsCode = response.statusCode();
    Assert.assertEquals(200, statsCode);


}

@Test
    public void ArchiveAll() throws JsonProcessingException {

    String url = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers";
    String token = CashWiseToken.getToken();

    Map<String, Object> params = new HashMap<>();
    params.put("isArchived", false);
    params.put("page",1);
    params.put("size", 10);

    Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
    int statusCode = response.statusCode();

    Assert.assertEquals(200, statusCode);

    ObjectMapper mapper = new ObjectMapper();
    CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

    int size = customResponse.getResponses().size();

    for(int i =0; i< size; i++){
        int id = customResponse.getResponses().get(i).getSeller_id();

        String url2 = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers/archive/unarchive";

        Map<String, Object> params2 = new HashMap<>();
        params2.put("sellersIdsForArchive", id);
        params2.put("archive", true);

        Response response1 = RestAssured.given().auth().oauth2(token).params(params2).post(url2);

        int statusCode2 = response1.statusCode();

        Assert.assertEquals(200,statusCode2);

    }





}

@Test
    public void UnarchievSellers() throws JsonProcessingException {
    String url = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers";
    String token = CashWiseToken.getToken();

    Map<String, Object> params = new HashMap<>();
    params.put("isArchived", false);
    params.put("page",1);
    params.put("size", 10);

    Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
    int statusCode = response.statusCode();

    Assert.assertEquals(200, statusCode);

    ObjectMapper mapper =new ObjectMapper();
    CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

    int size = customResponse.getResponses().size();
    String urlUnarchieve = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers/archive/unarchive";
    for(int i =0; i<size; i++) {
        if(customResponse.getResponses().get(i).getEmail() == null){
            continue;
        }
        if(customResponse.getResponses().get(i).getEmail().endsWith("@hotmail.com")){
            int id = customResponse.getResponses().get(i).getSeller_id();


            Map<String, Object> params22 = new HashMap<>();
            params22.put("sellersIdsForArchive", id);
            params22.put("archive", true);

            Response response1 = RestAssured.given().auth().oauth2(token).params(params22).post(urlUnarchieve);
            int statusCode2 = response1.statusCode();

            Assert.assertEquals(200, statusCode2);


        }
    }

}

@Test
    public void CreateSellerVerify() throws JsonProcessingException {

    String url = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers";
    String token = CashWiseToken.getToken();

    Requestbody requestbody = new Requestbody();
    requestbody.setCompany_name("Jackson's IT");
    requestbody.setSeller_name("Jackson");
    requestbody.setPhone_number("2243567890");
    requestbody.setEmail("Jackson@gmail.com");
    requestbody.setAddress("234 evergreen");

    Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
            .body(requestbody).post(url);
    int statusCode = response.statusCode();
    Assert.assertEquals(201, statusCode);

    ObjectMapper mapper = new ObjectMapper();
    CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
    int expectedID = customResponse.getSeller_id();

  Map<String, Object> params = new HashMap<>();
    params.put("isArchived", false);
    params.put("page",1);
    params.put("size", 10);

    Response response1 = RestAssured.given().auth().oauth2(token).get(url);
    int statsCode2 = response1.statusCode();
    Assert.assertEquals(201, statsCode2);


    CustomResponse customResponse2 = mapper.readValue(response1.asString(), CustomResponse.class);

    int size = customResponse.getResponses().size();
    boolean isPresent = false;

    for(int i =0; i<size; i++){
        if(customResponse2.getResponses().get(i).getSeller_id() == expectedID){
            isPresent = true;
            break;
        }
        Assert.assertTrue(isPresent);




    }



}
@Test
    public void testGet(){
    APIRunner.runGET("/api/myaccount/sellers/",524);
    String email = APIRunner.getCustomResponse().getEmail();
    System.out.println(email);

    String name = APIRunner.getCustomResponse().getSeller_name();
    System.out.println(name);

    int id = APIRunner.getCustomResponse().getSeller_id();
    System.out.println(id);
}





}
