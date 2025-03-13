package com.saucedemo.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class WebDriverUtils {
    private WebDriver driver;
    private WebDriverWait webDriverWait;

    public WebDriverUtils(WebDriver driver){
        this.driver = driver;
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement waitForElementToBeVisible(WebElement element) {
        return webDriverWait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForElementToBeClickable(WebElement element) {
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void clickUsingJS(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void sendKeysUsingJS(WebElement element, String text) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + text + "';", element);
    }

    public boolean isElementVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
