package com.saucedemo.tests.cart_functionalities;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.reports.ExtentManager;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class AddToCartFromHomepage extends BaseTest {

    @Test(groups = {"smoke", "regression"},dataProvider = "successfulLoginData", dataProviderClass = DataProviders.class)
    public void addToCartFromHomepage(String username, String password) {
        ExtentManager.getTest().info("▶ Starting test: Add to cart from Homepage");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        ExtentManager.getTest().info("✅ User logged in: " + username);

        // Add product to Cart from Homepage
        InventoryPage inventoryPage = new InventoryPage(driver);
        Map<String, String> productDetails = inventoryPage.clickAddToCartOnAnyProduct();
        String selectedProduct = productDetails.get("name");
        String selectedProductPrice = productDetails.get("price");
        Assert.assertEquals(inventoryPage.getShoppingCartNotificationBadgeNumber(), "1",
                "No item has been added to the cart!");
        ExtentManager.getTest().info("🛒 Product added to cart: " + selectedProduct + " | Price: " + selectedProductPrice + "." +
                "\n Notification number on the badge equals 1");

        // Verify product in cart
        CartPage cartPage = new CartPage(driver);
        inventoryPage.shoppingCartNotificationBadge.click();
        Assert.assertEquals(selectedProduct, cartPage.getSelectedProductName());
        Assert.assertTrue(cartPage.isCheckoutButtonDisplayed(), "Checkout button is not visible on the Cart page!");
        ExtentManager.getTest().info("🔎 Product name match in Cart, and Checkout button is displayed");

        ExtentManager.getTest().pass("Product has been added to the cart from the Homepage!");
    }
}
