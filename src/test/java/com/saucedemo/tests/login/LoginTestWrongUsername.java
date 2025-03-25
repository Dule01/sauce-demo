package com.saucedemo.tests.login;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.Constants;
import com.saucedemo.utils.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTestWrongUsername extends BaseTest {
    @Test(dataProvider = "wrongUsernameLoginData", dataProviderClass = DataProviders.class)
    public void testWrongUsernameLogin(String username, String password){
        test.info("▶ Starting test: Login with wrong username");

        // Attempt login with an incorrect username
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        test.info("💻 Trying to login with an incorrect username...");

        // Asserting the error message
        Assert.assertEquals(loginPage.getErrorMessageText(), Constants.INVALID_LOGIN_EXPECTED_ERROR);
        test.pass("✅ Login failed! Correct error message appeared");
    }
}
