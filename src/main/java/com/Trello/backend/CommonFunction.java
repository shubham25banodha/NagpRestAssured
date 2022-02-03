package com.Trello.backend;

import com.Trello.utility.Base;
import com.Trello.utility.Reporting;
import com.Trello.utility.TrelloUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

public class CommonFunction extends Base {
    Response response;
    JsonPath jpath;
    String boardID="";
    Random rand=new Random();
    String listIDInBoard="";
    String cardID = "";

    public String getBoardID(String testcase,  Properties configProps,Properties testDataProps){
        log.info("getBoardID API");
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

        return boardID;
    }

    public String createList(String testcase,  Properties configProps){
        log.info("createList API");
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

        return listIDInBoard;
    }

    public String createCardInList(String testcase,String listIDInBoard, Properties configProps){
        log.info("createCardInList API");
        cardID = "";
        String cardName = "TF2_Testing_Card_"+rand.nextInt(99999);
        String URI = configProps.getProperty("createCard");

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("idList",listIDInBoard);
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));
        queryParam.put("name",cardName);

        response = postAcceptHeader(URI,queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "createCardInList API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        }else{
            jpath = new JsonPath(response.getBody().asString());
            cardID = jpath.get("id").toString();
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("createCardInList API Validated Successfully");

        return cardID;
    }

    public void getCardInList(String testcase, String listIDInBoard, Properties configProps){
        log.info("updateBoard API");
        String URI = configProps.getProperty("createListURI")
                +"/"+listIDInBoard+"/cards";

        Map<String, Object> queryParam=new HashMap<>();
        queryParam.put("key",configProps.getProperty("apiKey"));
        queryParam.put("token",configProps.getProperty("apiToken"));

        response = get(URI,queryParam);
        log.info(response.prettyPrint());

        if(!(response.getStatusCode()==200)){
            msg = msg + "getCardInList API Expected Code: "
                    + "200"
                    + " vs Actual Code: "
                    + response.getStatusCode()
                    + ", Response Body: "
                    + response.getBody().asString()+ ".";
            log.error("********* Error Message "+ msg);
            assertion(String.valueOf(response.getStatusCode()),"200");
        }else{
            jpath = new JsonPath(response.getBody().asString());

            assertion(jpath.get("id[0]").toString()
                    ,cardID);
        }

        if(msg.length()>5){
            Reporting.failMessage = testcase+" testcase - "+ msg;
        }
        else Reporting.logPass("getCardInList API Validated Successfully");
    }
}
