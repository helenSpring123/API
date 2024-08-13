package utilities;

import entities.Requestbody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CashWiseToken {

    public static String getToken(){

        String endPoint = "https://backend.cashwise.us/api/myaccount/auth/login";

        Requestbody requestbody = new Requestbody();
        requestbody.setEmail("vladtest@gmail.com");
        requestbody.setPassword("123456");

        Response response = RestAssured.given().contentType(ContentType.JSON)
                .body(requestbody).post(endPoint);
        return response.jsonPath().getString("jwt_token");
    }
}
