package com.Trello.dataProvider;

import org.testng.annotations.DataProvider;

public class TestFlow3DataProvider {

    @DataProvider(name="getTestCase")
    public Object[][] dataProviderMethod() throws Exception {
//        String excelSheetPath = "D://NAGP//RestAssured//excelSheet//TestData.xlsx"; //Utils.path()+ "\\excelSheet\\TestData.xlsx";
//        Object[][] testObjArray = ReadExcelSheet.getTableArray(excelSheetPath,"Sheet1");

        Object[][] testObjArray ={
                {"CreateCard"},
                {"GetTheCard"},
                {"CreateAttachmentOnCard"},
                {"DeleteAttachmentOnCard"},
                {"DeleteCard"}
        };

        return (testObjArray);
    }
}
