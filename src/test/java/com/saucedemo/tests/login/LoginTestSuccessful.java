package com.saucedemo.tests.login;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.reports.ExtentManager;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTestSuccessful extends BaseTest {

    @Test(groups = {"smoke", "regression"}, dataProvider = "successfulLoginDataWithBool", dataProviderClass = DataProviders.class)
    public void testValidLogin(String username, String password, boolean expectedSuccess){
        ExtentManager.getTest().info("▶ Starting test: Successful login");

        // Logging in
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        InventoryPage inventoryPage = new InventoryPage(driver);
        boolean actualSuccess = inventoryPage.isInventoryDisplayed();
        Assert.assertEquals(actualSuccess, expectedSuccess, "Login test failed for the user: " + username);
        ExtentManager.getTest().info("✔ User " + username + " is logged in, and landed on the Inventory page!");

        if (expectedSuccess) {
            ExtentManager.getTest().pass("✅ User successfully logged in: " + username);
        } else {
            ExtentManager.getTest().fail("❌ Login not successful for the user: " + username);
        }
    }
}
