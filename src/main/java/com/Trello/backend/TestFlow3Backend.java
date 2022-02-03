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

public class TestFlow3Backend extends Base {
    private static Properties testDataProps=null;
    protected static String testcase="";
    CommonFunction commonFunction=new CommonFunction();
    String listID="";
    Random rand=new Random();
    Response response;
    JsonPath jpath;
    String cardID="";
    String attachmentID="";

    public void preStepTestFlow3() throws IOException {
        readConfig();
        testDataProps=readTestData("TestFlow3TestData.properties");
    }

    public void initTestFlow3(){
        setupTest();
        setupBaseURI(configProps.getProperty("URL"));
        msg="";
        cardID="";
        attachmentID="";
    }

    public void createList(){
        Reporting.test = Reporting.extent.createTest(testcase);
        commonFunction.getBoardID(testcase,configProps, testDataProps);

        listID=commonFunction.createList(testcase,configProps);
    }
    public void createCard(){
        cardID = commonFunction.createCardInList(testcase,listID,configProps);
    }
    public void getCardInList(){
        String cardID = commonFunction.createCardInList(testcase,listID,configProps);

        commonFunction.getCardInList(testcase,listID, configProps);
    }

    public void createAttachmentOnCard(){
        log.info("createAttachmentOnCard API");
        String URI = configProps.getProperty("createCard")
                +"/"+cardID+"/attachments";

        String attachmentName="TF3_Testing_Attachment_"+rand.nextInt(9999);

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("url",testDataProps.getProperty("attachmentQueryParamURL"));
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));
        queryParam.put("name",attachmentName);

        response = post(URI,queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "createAttachmentOnCard API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        }else{
            jpath = new JsonPath(response.getBody().asString());

            attachmentID=jpath.get("id").toString();

            if(attachmentID.length()<3){
                msg = msg + " No attachmentID found for the selected testcase";
                log.error("********* Error Message "+ msg);
            }
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("createAttachmentOnCard API Validated Successfully");
    }

    public void deleteAttachmentOnCard(){
        log.info("deleteAttachmentOnCard API");
        String URI = configProps.getProperty("createCard")
                +"/"+cardID+"/attachments/"+attachmentID;

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));

        response = delete(URI,queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "deleteAttachmentOnCard API Expected Code: "
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
        else Reporting.logPass("deleteAttachmentOnCard API Validated Successfully");
    }

    public void deleteCard(){
        log.info("deleteCard API");
        String URI = configProps.getProperty("createCard")
                +"/"+cardID;

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));

        response = delete(URI,queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "deleteCard API Expected Code: "
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
        else Reporting.logPass("deleteCard API Validated Successfully");
    }

}
