package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CustomResponse;
import entities.Requestbody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Data;
import lombok.Getter;

import javax.swing.plaf.PanelUI;
import java.util.Map;

@Data
public class APIRunner {
    @Getter
    private static CustomResponse customResponse;
//get without params
    public static void runGET(String path, int id){
        String token = CashWiseToken.getToken();
        String url = Config.getProperties("cashwiseurl") + path +id;
        Response response = RestAssured.given().auth().oauth2(token).get(url);
        System.out.println("status code: " + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        try{
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        }
        catch (JsonProcessingException e) {
            System.out.println("This is a list response");
        }
    }
//get with params
    public static void runGET(String path, Map<String, Object> params){
        String token = CashWiseToken.getToken();
        String url = Config.getProperties("cashwiseurl") + path;
        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        System.out.println("status code: " + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        try{
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        }
        catch (JsonProcessingException e) {
            System.out.println("This is a single response");
        }




    }
    //post with request body

    public static void runPOST(String path, Requestbody requestbody){
        String token = CashWiseToken.getToken();
        String url = Config.getProperties("cashwiseurl") + path;

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestbody).post(url);
        System.out.println("status code" + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        try{
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        }
        catch (JsonProcessingException e) {
            System.out.println("This is a single response");
        }

    }
    //post API with params

    public static void runPOST(String path, Map<String , Object> params){
        String token = CashWiseToken.getToken();
        String url = Config.getProperties("cashwiseurl") + path;

        Response response = RestAssured.given().auth().oauth2(token).params(params).post(url);

        System.out.println("status code" + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        try{
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        }
        catch (JsonProcessingException e) {
            System.out.println("This is a single response");
        }

    }
    // delete
    public static void runDELETE(String path){

        String token = CashWiseToken.getToken();
        String url = Config.getProperties("cashwiseurl") + path;

        Response response = RestAssured.given().auth().oauth2(token).delete(url);

        System.out.println("status code" + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        try{
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        }
        catch (JsonProcessingException e) {
            System.out.println("This is a single response");
        }
    }
    // put with request body
    public static void runPUT(String path, Requestbody requestbody, int id){

        String token = CashWiseToken.getToken();
        String url = Config.getProperties("cashwiseurl") + path + id;

        Response response = RestAssured.given().auth().oauth2(token).contentType(ContentType.JSON)
                .body(requestbody).put(url);


        System.out.println("status code" + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        try{
            customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        }
        catch (JsonProcessingException e) {
            System.out.println("This is a single response");
        }

    }






}
