package com.saucedemo.tests;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest{
    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    @Test
    public void testValidLogin(){
        logger.info("Launching test: testValidLogin");
        test.info("Launching test: testValidLogin");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        logger.info("Logged in user: standard_user");
        test.info("Logged in user: standard_user");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Assert.assertTrue(inventoryPage.isInventoryDisplayed(), "Login failed!");

        logger.info("Test successful: User is logged in!");
        test.info("Test successful: User is logged in!");
    }
}
