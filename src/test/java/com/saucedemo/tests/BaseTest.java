package com.saucedemo.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.saucedemo.reports.ExtentManager;
import com.saucedemo.utils.ScreenshotUtils;
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
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    protected WebDriver driver;
    protected Properties config;
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected static ExtentReports extent;


    @BeforeSuite
    public void setupExtentReports() {
        extent = ExtentManager.getInstance();
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(Method method) throws IOException {
        ExtentManager.createTest(method.getName());

        logger.info("Launching the test environment...");
        config = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        config.load(fis);

        String browser = config.getProperty("browser");
        boolean isHeadless = Boolean.parseBoolean(config.getProperty("headless", "false"));

        logger.info("Selected browser: " + browser);
        logger.info("Headless mode: " + isHeadless);

        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("-private");
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--inprivate");
                if (isHeadless) {
                    edgeOptions.addArguments("--headless=new"); // koristi novi engine
                    edgeOptions.addArguments("--window-size=1920,1080");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--incognito");
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new"); // preporučeno od Chrome 109+
                    chromeOptions.addArguments("--window-size=1920,1080");
                }
                driver = new ChromeDriver(chromeOptions);
                break;

            default:
                throw new IllegalArgumentException("❌ Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
        driver.get(config.getProperty("baseUrl"));
        logger.info("URL to navigate to: " + config.getProperty("baseUrl"));
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test failed: " + result.getThrowable());
            ExtentManager.getTest().fail("Test failed: " + result.getThrowable());

            // Getting screenshot
            String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getName());

            // Add screenshot to Extent Report
            ExtentManager.getTest().fail("Screenshot: ", MediaEntityBuilder.createScreenCaptureFromPath("../" + screenshotPath).build());

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentManager.getTest().pass("Test successful!");
        } else {
            ExtentManager.getTest().skip("Test skipped");
        }
        if (driver != null) {
            logger.info("Closing the browser...");
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void flushReports() {
        ExtentManager.flushReports();
    }

}
