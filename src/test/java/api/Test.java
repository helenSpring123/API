package api;

import entities.Requestbody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Test {

    @org.junit.Test
    public void testToken(){
        String endPoint = "https://backend.cashwise.us/api/myaccount/auth/login";
        Requestbody requestBody = new Requestbody();

        requestBody.setEmail("isakazy@gmail.com");
        requestBody.setPassword("isakazyamanbaev");

        Response response =  RestAssured.given().contentType(ContentType.JSON)
                .body(requestBody).post(endPoint);
        int statusCode = response.statusCode();

        Assert.assertEquals(200, statusCode);
        response.prettyPrint();

        String token = response.jsonPath().getString("jwt_token");
        System.out.println(token);

    }
    @org.junit.Test
    public void getSingleSeller(){

        String url = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers/" + 4629;
        String token = CashWiseToken.getToken();

        Response response = RestAssured.given().auth().oauth2(token).get(url);

        String expectedEmail = response.jsonPath().getString("email");

        Assert.assertTrue(expectedEmail.endsWith(".com"));
        Assert.assertFalse(expectedEmail.isEmpty());



    }



    @org.junit.Test
    public void getAllSellers(){
        String url = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers";
        String token = CashWiseToken.getToken();

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page",1);
        params.put("size", 10);

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);
        response.prettyPrint();

        String email = response.jsonPath().getString("responses[0].email");
      Assert.assertFalse(email.isEmpty());

      String email2 = response.jsonPath().getString("responses[2].email");
      Assert.assertFalse(email2.isEmpty());

      String email3 = response.jsonPath().getString("responses[3].email");
      Assert.assertFalse(email3.isEmpty());
    }

    @org.junit.Test
    public void getAllSellersLoop(){
        String url = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers";
        String token = CashWiseToken.getToken();

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page",1);
        params.put("size", 10);

        Response response =RestAssured.given().auth().oauth2(token).params(params).get(url);
        int statusCode = response.statusCode();

        Assert.assertEquals(200, statusCode);

        List<String> listOfEmails = response.jsonPath().getList("responses.email");
        for(String emails : listOfEmails){
            Assert.assertFalse(emails.isEmpty());
        }


    }

    @org.junit.Test
    public void createSeller() {
        String url = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers";
        String token = CashWiseToken.getToken();

        Requestbody requestbody = new Requestbody();
        requestbody.setCompany_name("WiseCode");
        requestbody.setSeller_name("Nurghazy");
        requestbody.setAddress("Earth");
        requestbody.setEmail("Wisecode.gmail.com");
        requestbody.setPhone_number("465746895");

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestbody).post(url);

        response.statusCode();

        int statusCode = response.statusCode();

        Assert.assertEquals(201, statusCode);


    }

    @org.junit.Test
    public void CreateGetSeller(){

        String url = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers";
        String token = CashWiseToken.getToken();


        Requestbody requestbody =new Requestbody();
        requestbody.setCompany_name("J&H");
        requestbody.setSeller_name("Helen");
        requestbody.setAddress("Earth");
        requestbody.setEmail("Helen22@Gmail.com");
        requestbody.setPhone_number("314567890");

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON).body(requestbody).post(url);

        int status = response.statusCode();

        Assert.assertEquals(201, status);

        //get the seller by id

       String id = response.jsonPath().getString("seller_id");
       String url2 = Config.getProperties("cashwiseurl") + "/api/myaccount/sellers/" + id;

       Response response1 = RestAssured.given().auth().oauth2(token).get(url2);

       int status2 = response1.statusCode();

       Assert.assertEquals(200, status2);



    }








}










