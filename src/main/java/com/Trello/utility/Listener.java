package com.Trello.utility;

import lombok.SneakyThrows;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener extends  Reporting implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("onTestStart");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("onTestSuccess");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("onTestFailure");
        logFail();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("onTestSkipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("onTestFailedButWithinSuccessPercentage");
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        this.onTestFailure(result);
    }

    @SneakyThrows
    @Override
    public void onStart(ITestContext context) {
        System.out.println("onStart");
        initializeReport();
    }

    @SneakyThrows
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("onFinish");
        extent.flush();
        moveToArchiveReport();
    }
}
