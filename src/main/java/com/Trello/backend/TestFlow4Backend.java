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

public class TestFlow4Backend extends Base {
    private static Properties testDataProps=null;
    protected static String testcase="";
    CommonFunction commonFunction=new CommonFunction();
    String listID="";
    Random rand=new Random();
    Response response;
    JsonPath jpath;
    String memberID="";
    String attachmentID="";

    public void preStepTestFlow4() throws IOException {
        log.info("TestFlow 4 Started");
        readConfig();
        testDataProps=readTestData("TestFlow4TestData.properties");
    }

    public void initTestFlow4(){
        setupTest();
        setupBaseURI(configProps.getProperty("URL"));
        msg="";
        memberID="";
        attachmentID="";
    }

    public void getBoard(){
        Reporting.test = Reporting.extent.createTest(testcase);
        log.info("getBoard API");

        String getBoardURI = configProps.getProperty("getBoardURI")
                +"?key="+configProps.getProperty("apiKey")
                +"&token="+configProps.getProperty("apiToken");

        response = get(getBoardURI);
        log.info(response.prettyPeek().asString());

        if(!(response.getStatusCode()==200)){
            msg = msg + "Get Board API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        } else{
            jpath = new JsonPath(response.getBody().asString());

            assertion(testDataProps.getProperty("getBoardActualValue")
                    ,jpath.get("memberships[0].memberType[0]").toString());

            memberID=jpath.get("memberships[0].idMember[0]").toString();
            if(memberID.length()<3){
                msg = msg + " No memberID found for the selected testcase";
                log.error("********* Error Message "+ msg);
            }
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("Get Board API Validated Successfully");
    }

    public void getAMember(){
        log.info("getAMember API");
        String URI = configProps.getProperty("memberURI")
                +memberID;

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));

        response = get(URI,queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "getAMember API Expected Code: "
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
                    ,memberID);
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("getAMember API Validated Successfully");
    }

}
