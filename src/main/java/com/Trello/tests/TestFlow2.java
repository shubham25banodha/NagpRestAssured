package com.Trello.tests;

import com.Trello.backend.TestFlow2Backend;
import com.Trello.dataProvider.TestFlow2DataProvider;
import org.testng.annotations.*;

import java.io.IOException;

public class TestFlow2 extends TestFlow2Backend {

    @BeforeClass
    public void preStep() throws IOException {
        preStepTestFlow2();
    }

    @BeforeMethod
    public void initTestFlow(){
        initTestFlow2();
    }

    @Test(dataProvider = "getTestCase", dataProviderClass = TestFlow2DataProvider.class)
    public void testFlow2(String testcaseName){
        log.info("*************************************************************************");
        log.info("TestcaseName is  : "+testcaseName);
        log.info("*************************************************************************");

        testcase = testcaseName;
        switch (testcaseName){
            case "CreateList":
                createList();
                break;

            case "UpdateList":
                createList();
                updateList();
                break;

            case "GetCardInList":
                createList();
                getCardInList();
                break;

            case "ArchiveList":
                createList();
                archiveList();
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
