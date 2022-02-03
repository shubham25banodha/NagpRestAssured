package com.Trello.tests;

import com.Trello.backend.*;
import com.Trello.dataProvider.*;
import org.testng.annotations.*;

import java.io.IOException;

public class TestFlow3 extends TestFlow3Backend {

    @BeforeClass
    public void preStep() throws IOException {
        preStepTestFlow3();
    }

    @BeforeMethod
    public void initTestFlow(){
        initTestFlow3();
    }

    @Test(dataProvider = "getTestCase", dataProviderClass = TestFlow3DataProvider.class)
    public void testFlow3(String testcaseName){
        log.info("*************************************************************************");
        log.info("TestcaseName is  : "+testcaseName);
        log.info("*************************************************************************");

        testcase = testcaseName;
        switch (testcaseName){
            case "CreateCard":
                createList();
                createCard();
                break;

            case "GetTheCard":
                createList();
                getCardInList();
                break;

            case "CreateAttachmentOnCard":
                createList();
                createCard();
                createAttachmentOnCard();
                break;

            case "DeleteAttachmentOnCard":
                createList();
                createCard();
                createAttachmentOnCard();
                deleteAttachmentOnCard();
                break;

            case "DeleteCard":
                createList();
                createCard();
                deleteCard();
                break;
        }

    }

    @AfterMethod
    public void finishTestFlow(){

    }

    @AfterClass
    public void exitStep(){

    }
}
