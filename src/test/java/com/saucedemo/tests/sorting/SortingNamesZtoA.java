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

public class SortingNamesZtoA extends BaseTest {
    @Test(dataProvider = "successfulLoginData", dataProviderClass = DataProviders.class)
    public void testSortingNameAtoZ(String username, String password){
        int indexOptionForNamesZtoA = 1;

        test.info("▶ Starting test: Sorting - Z to A");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginIfNotLoggedIn(username, password);
        test.info("✅ User logged in: " + username);

        InventoryPage inventoryPage = new InventoryPage(driver);
        Select select = new Select(inventoryPage.sortingDropdown);

        select.selectByIndex(indexOptionForNamesZtoA);
        test.info("🔵 Price (Z to A) sorting has been selected");

        List<String> actualNames = inventoryPage.getAllProductNames();
        test.info("📃 Sorting of names: " + actualNames);

        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames);
        Collections.reverse(expectedNames);
        Assert.assertEquals(actualNames, expectedNames);
        test.info("🎉 Prices are properly sorted from Z to A!");
    }
}
