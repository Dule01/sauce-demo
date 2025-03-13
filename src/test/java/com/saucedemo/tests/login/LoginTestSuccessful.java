package com.saucedemo.tests.login;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.DataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTestSuccessful extends BaseTest {
    private static final Logger logger = LogManager.getLogger(LoginTestSuccessful.class);

    @Test(dataProvider = "successfulLoginData", dataProviderClass = DataProviders.class)
    public void testValidLogin(String username, String password, boolean expectedSuccess){
        logger.info("Launching test: testValidLogin for the user: " + username);
        test.info("Launching test: testValidLogin for the user: " + username);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        logger.info("Logged in user: " + username);
        test.info("Logged in user: " + password);

        InventoryPage inventoryPage = new InventoryPage(driver);
        boolean actualSuccess = inventoryPage.isInventoryDisplayed();

        Assert.assertEquals(actualSuccess, expectedSuccess, "Login test failed for the user: " + username);

        if (expectedSuccess) {
            test.pass("User successfully logged in: " + username);
        } else {
            test.fail("Login not successful for the user: " + username);
        }
    }
}
