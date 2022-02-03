package com.Trello.tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class basic {
    public static String apiKey="a9abc21ef217ef48280ab8bce2ab8179";
    public static String apiToken="c55e954ad7e5cbd8ea8f1195f96d26c18e104e436ad708b0029a7b43de588a84";
    public static String boardID = "";

    @Test
    public void getBoard(){
        RestAssured.baseURI="https://api.trello.com/1/";

        Response res= RestAssured.given().header("Accept","application/json")
                .when().get("members/me/boards?key="+apiKey+"&token="+apiToken);

//        res.prettyPeek();

        JsonPath jpath = new JsonPath(res.getBody().asString());
        boardID=jpath.get("id[0]").toString();
        Assert.assertEquals("admin",jpath.get("memberships[0].memberType[0]").toString());
    }

    @Test
    public void updateBoard(){
        RestAssured.baseURI="https://api.trello.com/1/";

        Random rand=new Random();
        String newBoardName="Testing_Board_"+rand.nextInt(9999);

        JSONObject jobj= new JSONObject();
        jobj.put("name",newBoardName);

        Response res= RestAssured.given().header("Accept","application/json").body(jobj)
                .when().put("boards/"+boardID+"?key="+apiKey+"&token="+apiToken);

        res.prettyPeek();
    }

    @Test
    public void createList(){
        RestAssured.baseURI="https://api.trello.com/1/";

        Random rand=new Random();
        String newListname="Testing_List_"+rand.nextInt(9999);

//        JSONObject jobj= new JSONObject();
//        jobj.put("name",newListname);

        Response res= RestAssured.given().header("Content-Type","application/json")
                .when().post("lists?name=Testing_List_761&idBoard=61e99ffc4c322388e39c7520&key="+apiKey+"&token="+apiToken);


        res.prettyPeek();
    }
}
