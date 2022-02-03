package com.Trello.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class TrelloUtil {
    public Properties configProps=null;
    FileInputStream file = null;
    public static Logger log = Logger.getLogger(TrelloUtil.class.getName());

    //code to read config file
    public void readConfig(){
        configProps=new Properties();
        try {
            String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\";
            file =new FileInputStream(filePath+"config.properties");
            configProps.load(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //code to read testdata file
    public Properties readTestData(String testdataFileName) throws IOException {
        Properties Testdata=new Properties();
        String filePath = System.getProperty("user.dir")+"\\src\\main\\resources\\TestData\\";
        FileInputStream file = new FileInputStream(filePath+testdataFileName);

        Testdata.load(file);

        return Testdata;
    }


}
