package com.saucedemo.tests.login;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.DataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTestWrongUsername extends BaseTest {
    private static final Logger logger = LogManager.getLogger(LoginTestWrongUsername.class);

    @Test(dataProvider = "wrongUsernameLoginData", dataProviderClass = DataProviders.class)
    public void testWrongUsernameLogin(String username, String password){
        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";

        logger.info("Launching test: testWrongUsernameLogin");
        test.info("Launching test: testWrongUsernameLogin");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        logger.info("Tried to login with an incorrect username...");
        test.info("Tried to login with an incorrect username...");

        Assert.assertEquals(loginPage.getErrorMessageText(), expectedErrorMessage);

        test.pass("Test passed: correct error message appeared!");
    }
}
