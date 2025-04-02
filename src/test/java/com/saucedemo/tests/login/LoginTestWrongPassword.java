package com.saucedemo.tests.login;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.reports.ExtentManager;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.Constants;
import com.saucedemo.utils.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginTestWrongPassword extends BaseTest {
    @Test(groups = "regression",dataProvider = "wrongPasswordLoginData", dataProviderClass = DataProviders.class)
    public void testWrongPasswordLogin(String username, String password){
        ExtentManager.getTest().info("▶ Starting test: Login with wrong password");

        // Attempt login with an incorrect password
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        ExtentManager.getTest().info("💻 Trying to login with an incorrect password...");

        // Asserting the error message
        Assert.assertEquals(loginPage.getErrorMessageText(), Constants.INVALID_LOGIN_EXPECTED_ERROR);
        ExtentManager.getTest().pass("✅ Login failed! Correct error message appeared");
    }
}
