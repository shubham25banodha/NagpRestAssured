package com.Trello.utility;

import io.restassured.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.Map;

public class Base extends TrelloUtil{

    Response response;
    public static RequestSpecification requestSpecification;
    public static String msg = "";

    public void setupTest(){
        requestSpecification = RestAssured.given();
    }

    public void setupBaseURI(String URL){
        requestSpecification.baseUri(URL);
    }

    public Response get(String URI){
        response = requestSpecification
                .given()
                .header("Accept","application/json")
                .when()
                .get(URI);

        return response;
    }

    public Response get(String URI,Map<String, Object> queryParam){
        response = requestSpecification
                .given()
                .header("Accept","application/json")
                .when()
                .log().all()
                .queryParams(queryParam)
                .get(URI);

        return response;
    }

    public Response put(String URI, Map<String, Object> queryParams){

        response = requestSpecification
                .given()
                .header("Accept","*/*")
                .when()
                .queryParams(queryParams)
                .put(URI);

        return response;
    }

    public Response delete(String URI, Map<String, Object> queryParams){

        response = requestSpecification
                .given()
                .header("Accept","*/*")
                .when()
                .queryParams(queryParams)
                .delete(URI);

        return response;
    }

    public Response putbody(String URI, Map<String, Object> payload){

        response = requestSpecification
                .given()
                .header("Accept","*/*")
                .body(payload)
                .when()
                .put(URI);

        return response;
    }

    public Response put(String URI){

        response = requestSpecification
                .given()
                .header("Accept","*/*")
                .when()
                .put(URI);

        return response;
    }

    public Response post(String URI){

        response = requestSpecification
                .given()
                .header("Content-Type","application/json")
                .when()
                .post(URI);

        return response;
    }

    public Response post(String URI, Map<String,Object> queryParam){

        response = requestSpecification
                .given()
                .header("Content-Type","application/json")
                .when()
                .queryParams(queryParam)
                .post(URI);

        return response;
    }

    public Response postAcceptHeader(String URI, Map<String,Object> queryParam){
        response = requestSpecification
                .given()
                .header("Accept","*/*")
                .when()
                .queryParams(queryParam)
                .post(URI);
        return response;
    }

    public Response put(String URI, Map<String,Object> payload, Map<String,Object> queryParam){

        response = requestSpecification
                .given()
                .header("Content-Type","application/json")
                .body(payload)
                .when()
                .queryParams(queryParam)
                .put(URI);

        return response;
    }

    public void assertion(String Actual, String Expected){
        boolean status=true;
        try{
            Assert.assertEquals(Actual
                    ,Expected);
            status=false;
        }
        catch (Exception ex){

        }
        finally {
            if(status){
                msg = msg + "Assertion is failing, Expected value "
                        +Expected
                        +", Actual value "
                        +Actual;
                log.error("********* Error Message "+ msg);
                Reporting.failMessage=msg;
            }
        }
    }
}
