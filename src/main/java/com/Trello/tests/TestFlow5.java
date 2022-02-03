package com.Trello.tests;

import com.Trello.backend.TestFlow5Backend;
import com.Trello.dataProvider.TestFlow5DataProvider;
import org.testng.annotations.*;

import java.io.IOException;

public class TestFlow5 extends TestFlow5Backend {

    @BeforeClass
    public void preStep() throws IOException {
        preStepTestFlow5();
    }

    @BeforeMethod
    public void initTestFlow(){
        initTestFlow5();
    }

    @Test(dataProvider = "getTestCase", dataProviderClass = TestFlow5DataProvider.class)
    public void testFlow5(String testcaseName){
        log.info("*************************************************************************");
        log.info("TestcaseName is  : "+testcaseName);
        log.info("*************************************************************************");

        testcase = testcaseName;
        switch (testcaseName){
            case "CreateOrganization":
                createOrganization();
                break;

            case "UpdateOrganization":
                createOrganization();
                updateOrganization();
                break;

            case "GetOrganization":
                createOrganization();
                getOrganization();
                break;

            case "DeleteOrganization":
                createOrganization();
                deleteOrganization();
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
