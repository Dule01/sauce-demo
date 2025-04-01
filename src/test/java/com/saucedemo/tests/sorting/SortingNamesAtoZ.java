package com.saucedemo.tests.sorting;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.DataProviders;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingNamesAtoZ extends BaseTest {

    @Test(dataProvider = "successfulLoginData", dataProviderClass = DataProviders.class)
    public void testSortingNameAtoZ(String username, String password){
        int indexOptionForNamesAtoZ = 0;

        test.info("▶ Starting test: Sorting - A to Z");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginIfNotLoggedIn(username, password);
        test.info("✅ User logged in: " + username);

        InventoryPage inventoryPage = new InventoryPage(driver);
        Select select = new Select(inventoryPage.sortingDropdown);

        select.selectByIndex(indexOptionForNamesAtoZ);
        test.info("🔵 Price (A to Z) sorting has been selected");

        List<String> actualNames = inventoryPage.getAllProductNames();
        test.info("📃 Sorting of names: " + actualNames);

        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames);
        Assert.assertEquals(actualNames, expectedNames);
        test.info("🎉 Prices are properly sorted from A to Z!");
    }
}
