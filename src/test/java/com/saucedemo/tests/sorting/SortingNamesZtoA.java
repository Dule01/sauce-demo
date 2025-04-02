package com.saucedemo.tests.sorting;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.reports.ExtentManager;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.DataProviders;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingNamesZtoA extends BaseTest {
    @Test(groups = "regression", dataProvider = "successfulLoginData", dataProviderClass = DataProviders.class)
    public void testSortingNameAtoZ(String username, String password) throws InterruptedException {
        int indexOptionForNamesZtoA = 1;

        ExtentManager.getTest().info("▶ Starting test: Sorting - Z to A");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginIfNotLoggedIn(username, password);
        ExtentManager.getTest().info("✅ User logged in: " + username);

        InventoryPage inventoryPage = new InventoryPage(driver);
        Select select = new Select(inventoryPage.sortingDropdown);

        select.selectByIndex(indexOptionForNamesZtoA);
        ExtentManager.getTest().info("🔵 Price (Z to A) sorting has been selected");

        List<String> actualNames = inventoryPage.getAllProductNames();
        ExtentManager.getTest().info("📃 Sorting of names: " + actualNames);

        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames);
        Collections.reverse(expectedNames);
        Assert.assertEquals(actualNames, expectedNames);
        ExtentManager.getTest().info("🎉 Prices are properly sorted from Z to A!");
    }
}
