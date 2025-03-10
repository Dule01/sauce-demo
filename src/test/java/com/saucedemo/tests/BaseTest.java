package com.saucedemo.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.saucedemo.reports.ExtentManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    protected WebDriver driver;
    protected Properties config;
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeSuite
    public void setupExtentReports(){
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod
    public void setup() throws IOException {
        test = ExtentManager.createTest(this.getClass().getSimpleName());

        logger.info("Launching the test environment...");
        config = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        config.load(fis);

        String browser = config.getProperty("browser");

        logger.info("Selected browser: " + browser);
        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-private");
                driver = new FirefoxDriver();
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--inprivate");
                driver = new EdgeDriver();
                break;
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--incognito");
                driver = new ChromeDriver();
                break;
        }

        driver.manage().window().maximize();
        driver.get(config.getProperty("baseUrl"));
        logger.info("URL to navigate to: " + config.getProperty("baseUrl"));

    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE){
            test.fail("Test failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS){
            test.pass("Test passed");
        } else {
            test.skip("Test skipped");
        }
        if(driver != null){
            logger.info("Closing the browser...");
            driver.quit();
        }
    }

    @AfterSuite
    public void flushReports(){
        ExtentManager.flushReports();
    }

}
