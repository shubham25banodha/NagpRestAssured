package com.Trello.tests;

import com.Trello.backend.*;
import com.Trello.dataProvider.*;
import org.testng.annotations.*;

import java.io.IOException;

public class TestFlow4 extends TestFlow4Backend {

    @BeforeClass
    public void preStep() throws IOException {
        preStepTestFlow4();
    }

    @BeforeMethod
    public void initTestFlow(){
        initTestFlow4();
    }

    @Test(dataProvider = "getTestCase", dataProviderClass = TestFlow4DataProvider.class)
    public void testFlow4(String testcaseName){
        log.trace("*************************************************************************");
        log.trace("TestcaseName is  : "+testcaseName);
        log.trace("*************************************************************************");

        testcase = testcaseName;
        switch (testcaseName){
            case "GetAMember":
                getBoard();
                getAMember();
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
