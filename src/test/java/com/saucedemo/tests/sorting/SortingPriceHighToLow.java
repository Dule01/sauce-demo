package com.saucedemo.tests.sorting;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.reports.ExtentManager;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.DataProviders;
import com.saucedemo.utils.TextUtils;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingPriceHighToLow extends BaseTest {
    @Test(groups = "regression", dataProvider = "successfulLoginData", dataProviderClass = DataProviders.class)
    public void testSortingPriceHighToLow(String username, String password) throws InterruptedException {
        int indexOptionForPriceHighToLow = 3;

        ExtentManager.getTest().info("▶ Starting test: Sorting - Price high to low");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginIfNotLoggedIn(username, password);
        ExtentManager.getTest().info("✅ User logged in: " + username);

        InventoryPage inventoryPage = new InventoryPage(driver);
        Select select = new Select(inventoryPage.sortingDropdown);

        select.selectByIndex(indexOptionForPriceHighToLow);
        ExtentManager.getTest().info("🔵 Price (High to low) sorting has been selected");

        List<String> priceTexts = inventoryPage.getAllPriceTexts();
        ExtentManager.getTest().info("📃 Sorting of prices: " + priceTexts);

        List<Float> actualPrices = TextUtils.convertToFloats(priceTexts);
        List<Float> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);
        Collections.reverse(expectedPrices);
        Assert.assertEquals(actualPrices, expectedPrices);
        ExtentManager.getTest().info("🎉 Prices are properly sorted from high to low!");
    }
}
