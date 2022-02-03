package com.Trello.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Reporting {
    public static ExtentSparkReporter htmlReporter;
    public static ExtentReports extent=new ExtentReports();
    public static ExtentTest test;

    public static String failMessage;
    public static File htmlFileName;
    static String archiveReportDirectory = System.getProperty("user.dir")+"\\Archive_Report";
    static String currentReportDirectory = System.getProperty("user.dir")+"\\Current_Report";
    static File currentTestDirectory;
    static String tempCurrentTestFolderName;
    static String screenshotFolderPath;
    static String testTimeStamp;

    public static void createCurrentTestFolderStructure() throws IOException {
        //Delete Current Report folder and create new one
        FileUtils.deleteDirectory(new File(currentReportDirectory));
        new File(currentReportDirectory).mkdirs();

        // Code to create folder with timestamp to store current test report
        testTimeStamp=new Date().toString().replace(":", "_").replace(" ", "_");
        tempCurrentTestFolderName = currentReportDirectory + "\\Report_" + testTimeStamp;
        currentTestDirectory = new File(tempCurrentTestFolderName);
        currentTestDirectory.mkdirs();

//        // Code to create folder to store screenshot
//        screenshotFolderPath = tempCurrentTestFolderName + "\\"+"screenshot";
//        new File(screenshotFolderPath).mkdirs();
    }

    //Initialize extent report
    public static void initializeReport() throws IOException {
        createCurrentTestFolderStructure();
        htmlReporter = new ExtentSparkReporter(tempCurrentTestFolderName+"\\Report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    //Add message in extent report for every passed user step
    public static void logPass(String passMessage){
        test.log(Status.PASS,passMessage);
    }

    //Add message and screenshot in extent report for every failed user step
    public static void logFail(){
//        System.out.println("starting capturing screenshot");
//        String tempName = screenshotFolderPath+"\\"+failMessage+".jpg";
//        Base.screenshot(tempName);
//        System.out.println("screenshot captured");
//        String screenshotPath = "screenshot\\"+failMessage+".jpg";
        failMessage =failMessage +" ";
        test.log(Status.FAIL,failMessage);
    }

    //move current test report to archive folder
    public static void moveToArchiveReport() throws IOException {
        try {
            File from = new File(currentReportDirectory + "\\Report_" + testTimeStamp);
            File to = new File(archiveReportDirectory + "\\Report_" + testTimeStamp);
            FileUtils.copyDirectory(from, to);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
