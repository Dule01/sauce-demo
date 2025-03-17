package com.saucedemo.tests.login;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.DataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginTestWrongPassword extends BaseTest {
    private static final Logger logger = LogManager.getLogger(LoginTestWrongPassword.class);

    @Test(dataProvider = "wrongPasswordLoginData", dataProviderClass = DataProviders.class)
    public void testWrongPasswordLogin(String username, String password){
        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";

        logger.info("Launching test: testWrongPasswordLogin");
        test.info("Launching test: testWrongPasswordLogin");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        logger.info("Tried to login with an incorrect password...");
        test.info("Tried to login with an incorrect password...");

        Assert.assertEquals(loginPage.getErrorMessageText(), expectedErrorMessage);

        test.pass("Test passed: correct error message appeared!");
    }
}
