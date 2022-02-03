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

public class TestFlow5Backend extends Base {
    private static Properties testDataProps=null;
    protected static String testcase="";
    CommonFunction commonFunction=new CommonFunction();
    String listID="";
    Random rand=new Random();
    Response response;
    JsonPath jpath;
    String organizationID="";
    String attachmentID="";

    public void preStepTestFlow5() throws IOException {
        readConfig();
        testDataProps=readTestData("TestFlow5TestData.properties");
    }

    public void initTestFlow5(){
        setupTest();
        setupBaseURI(configProps.getProperty("URL"));
        msg="";
        organizationID="";
        attachmentID="";
    }

    public void createOrganization(){
        log.info("createOrganization API");
        Reporting.test = Reporting.extent.createTest(testcase);
        String URI = configProps.getProperty("organizationURI");
        String organizationDisplayName = "TF5_Testing_Organization_"+rand.nextInt(9999);

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("displayName",organizationDisplayName);
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));

        response = post(URI,queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "createOrganization API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        }
        else{
            jpath = new JsonPath(response.getBody().asString());

            assertion(jpath.get("displayName").toString()
                    ,organizationDisplayName);

            organizationID=jpath.get("id").toString();
            if(organizationID.length()<3){
                msg = msg + " No organizationID found for the selected testcase";
                log.error("********* Error Message "+ msg);
            }
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("createOrganization API Validated Successfully");
    }

    public void updateOrganization(){
        log.info("updateOrganization API");
        String URI = configProps.getProperty("organizationURI")
                +"/"+organizationID;
        String organizationDisplayName = "TF5_Testing_UpdatedOrganization_"+rand.nextInt(9999);

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("name",organizationDisplayName.toLowerCase());
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));

        response = put(URI,queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "updateOrganization API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        }
        else{
            jpath = new JsonPath(response.getBody().asString());

            assertion(jpath.get("name").toString()
                    ,organizationDisplayName.toLowerCase());
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("updateOrganization API Validated Successfully");
    }

    public void getOrganization(){
        log.info("getOrganization API");
        String URI = configProps.getProperty("organizationURI")
                +"/"+organizationID;

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));

        response = get(URI,queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "getOrganization API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        }
        else{
            jpath = new JsonPath(response.getBody().asString());

            assertion(jpath.get("id").toString()
                    ,organizationID);
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("getOrganization API Validated Successfully");
    }

    public void deleteOrganization(){
        log.info("updateBoard API");
        String URI = configProps.getProperty("organizationURI")
                +"/"+organizationID;

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));

        response = delete(URI,queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "deleteOrganization API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("getOrganization API Validated Successfully");
    }

}
