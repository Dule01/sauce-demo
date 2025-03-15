package com.saucedemo.pages;

import com.saucedemo.utils.WebDriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage{
    private WebDriverUtils utils;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.utils = new WebDriverUtils(driver);
    }

    @FindBy(id = "user-name")
    private WebElement usernameField;

    public void enterUsername(String username){
        utils.waitForElementToBeVisible(usernameField).sendKeys(username);
    }

    @FindBy(id = "password")
    private WebElement passwordField;

    public void enterPassword(String password){
        utils.waitForElementToBeVisible(passwordField).sendKeys(password);
    }

    @FindBy(id = "login-button")
    private WebElement loginButton;

    public void clickLoginButton(){
        utils.waitForElementToBeVisible(loginButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    @FindBy(css = "[data-test=\"error\"]")
    private WebElement errorMessage;

    public String getErrorMessageText(){
        utils.waitForElementToBeVisible(errorMessage);
        return errorMessage.getText();
    }
}
