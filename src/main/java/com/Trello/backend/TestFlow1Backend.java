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

public class TestFlow1Backend extends Base {
    public static String boardID="";
    public static String listIDInBoard="";
    private static Properties testDataProps=null;
    protected static String testcase="";
    Random rand=new Random();
    Response response;
    JsonPath jpath;

    public void preStepTestFlow1() throws IOException {
        readConfig();
        testDataProps=readTestData("TestFlow1TestData.properties");
    }

    public void initTestFlow1(){
        setupTest();
        setupBaseURI(configProps.getProperty("URL"));
        msg="";
        boardID="";
    }

    public void getBoard(){
        log.info("getBoard API");
        Reporting.test = Reporting.extent.createTest(testcase);

        String getBoardURI = configProps.getProperty("getBoardURI")
                +"?key="+configProps.getProperty("apiKey")
                +"&token="+configProps.getProperty("apiToken");

        response = get(getBoardURI);
        log.info(response.prettyPrint());

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

            boardID=jpath.get("id[0]").toString();
            if(boardID.length()<3){
                msg = msg + " No boardID found for the selected testcase";
                log.error("********* Error Message "+ msg);
            }
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("Get Board API Validated Successfully");
    }

    public void updateBoard(){
        log.info("updateBoard API");
        String boardName="Testing_Board_"+rand.nextInt(9999);

        String putBoardURI = configProps.getProperty("updateBoardURI")
                +boardID
                +"?key="+configProps.getProperty("apiKey")
                +"&token="+configProps.getProperty("apiToken")
                +"&name="+boardName;
        response=put(putBoardURI);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "Update Board API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        } else{
            jpath = new JsonPath(response.getBody().asString());

            assertion(jpath.get("name").toString()
                    ,boardName);
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("Update Board API Validated Successfully");
    }

    public void createListInBoard(){
        log.info("createListInBoard API");
        String listName="Testing_List_"+rand.nextInt(99999);
        String createListURI = configProps.getProperty("createListURI");

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("name",listName);
        queryParam.put("idBoard",boardID);
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));

        response=post(createListURI, queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "Create List In Board API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        }else{
            jpath = new JsonPath(response.getBody().asString());

            assertion(jpath.get("name").toString()
                    ,listName);

            listIDInBoard=jpath.get("id").toString();
            if(listIDInBoard.length()<3){
                msg = msg + " No list ID In Board found for the selected testcase";
                log.error("********* Error Message "+ msg);
            }
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("createListInBoard API Validated Successfully");
    }

    public void getFilteredList(){
        log.info("getFilteredList API");
        String filteredListURI = configProps.getProperty("createListURI")+"/"+listIDInBoard;

        Map<String, Object> filteredListqueryParam=new HashMap<>();
        filteredListqueryParam.put("key",configProps.getProperty("apiKey"));
        filteredListqueryParam.put("token",configProps.getProperty("apiToken"));

        response=get(filteredListURI, filteredListqueryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "getFilteredList API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        } else{
            jpath = new JsonPath(response.getBody().asString());

            assertion(jpath.get("id").toString()
                    ,listIDInBoard);
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("getFilteredList API Validated Successfully");

    }

    public void addMember(){
        log.info("addMember API");
        String URI = "/1/boards/"
                +boardID+"/members/"+testDataProps.getProperty("memberID");

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("type","normal");
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));

        response=put(URI, queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "addMember API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        } else{
            jpath = new JsonPath(response.getBody().asString());

            assertion(jpath.get("members.id[0]").toString()
                    ,testDataProps.getProperty("memberID"));

        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("addMember API Validated Successfully");
    }

    public void removeMember(){
        log.info("removeMember API");
        String URI = "/1/boards/"
                +boardID+"/members/"+testDataProps.getProperty("memberID");

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("type","normal");
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));

        response=delete(URI, queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "removeMember API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        } else{
            jpath = new JsonPath(response.getBody().asString());

            assertion(jpath.get("id").toString()
                    ,boardID);
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("removeMember API Validated Successfully");
    }
}
