package com.Trello.tests;

import com.Trello.backend.TestFlow1Backend;
import com.Trello.dataProvider.TestFlow1DataProvider;
import org.testng.annotations.*;

import java.io.IOException;

public class TestFlow1 extends TestFlow1Backend {

    @BeforeClass
    public void preStep() throws IOException {
        preStepTestFlow1();
    }

    @BeforeMethod
    public void initTestFlow(){
        initTestFlow1();
    }

    @Test(dataProvider = "getTestCase", dataProviderClass = TestFlow1DataProvider.class)
    public void testFlow1(String testcaseName) throws IOException {
        log.info("*************************************************************************");
        log.info("TestcaseName is  : "+testcaseName);
        log.info("*************************************************************************");

        testcase = testcaseName;
        switch (testcaseName){

            case "GetBoard":
                getBoard();
                break;

            case "UpdateBoard":
                getBoard();
                updateBoard();
                break;

            case "Create2List":
                getBoard();
                createListInBoard();
                break;

            case "GetFilteredList":
                getBoard();
                createListInBoard();
                getFilteredList();
                break;

            case "AddAndRemoveMemberInBoard":
                getBoard();
                addMember();
                removeMember();
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
