package com.saucedemo.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentReports getInstance(){
        if (extent == null){
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String reportPath = "reports/TestReport_" + timestamp + ".html";

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setReportName("SauceDemo Automation Report");
            sparkReporter.config().setDocumentTitle("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }

    public static void createTest(String testName){
        ExtentTest extentTest = getInstance().createTest(testName);
        test.set(extentTest); // vezivanje testa za trenutni thread
    }

    public static ExtentTest getTest(){
        return test.get(); // vraca test za trenutni thread
    }

    public static void flushReports(){
        if (extent != null){
            extent.flush();
        }
    }
}
