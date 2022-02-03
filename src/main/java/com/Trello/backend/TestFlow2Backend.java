package com.Trello.backend;

import com.Trello.utility.Base;
import com.Trello.utility.Reporting;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class TestFlow2Backend extends Base {
    private static Properties testDataProps=null;
    protected static String testcase="";
    CommonFunction commonFunction=new CommonFunction();
    String listID="";
    Random rand=new Random();
    Response response;
    JsonPath jpath;

    public void preStepTestFlow2() throws IOException {
        readConfig();
        testDataProps=readTestData("TestFlow2TestData.properties");
    }

    public void initTestFlow2(){
        setupTest();
        setupBaseURI(configProps.getProperty("URL"));
        msg="";
        listID="";
    }

    public void createList(){
        Reporting.test = Reporting.extent.createTest(testcase);
        commonFunction.getBoardID(testcase,configProps, testDataProps);

        listID=commonFunction.createList(testcase,configProps);
    }

    public void updateList(){
        log.info("updateList API");
        String URI = "/1/lists/"+listID;

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));

        Map<String, Object> payload=new HashMap<>();
        payload.put("name","TF2_Testing_List_"+rand.nextInt(9999));

        response = put(URI,payload,queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "updateList API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        }else{
            jpath = new JsonPath(response.getBody().asString());

            assertion(jpath.get("id").toString()
                    ,listID);
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("updateList API Validated Successfully");
    }

    public void getCardInList(){
        String cardID = commonFunction.createCardInList(testcase,listID,configProps);

        commonFunction.getCardInList(testcase,listID, configProps);
    }

    public void archiveList(){
        log.info("archiveList API");
        String URI = configProps.getProperty("createListURI")
                +"/"+listID+"/closed";

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));
        queryParam.put("value",true);

        response = put(URI,queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "archiveList API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        }else{
            jpath = new JsonPath(response.getBody().asString());

            assertion(jpath.get("id").toString()
                    ,listID);
            assertion(jpath.get("closed").toString()
                    ,"true");
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("archiveList API Validated Successfully");
    }
}
