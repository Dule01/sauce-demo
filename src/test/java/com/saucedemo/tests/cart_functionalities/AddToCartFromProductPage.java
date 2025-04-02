package com.saucedemo.tests.cart_functionalities;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductDetailsPage;
import com.saucedemo.reports.ExtentManager;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.DataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class AddToCartFromProductPage extends BaseTest {

    @Test(groups = {"smoke", "regression"},dataProvider = "successfulLoginData", dataProviderClass = DataProviders.class)
    public void addToCartFromProductPage(String username, String password) {
        ExtentManager.getTest().info("▶ Starting test: Add to cart from Product page");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        ExtentManager.getTest().info("✅ User logged in: " + username);

        // Click random product on the Homepage
        InventoryPage inventoryPage = new InventoryPage(driver);
        Map<String, String> productDetails = inventoryPage.clickOnAnyProduct();
        String selectedProduct = productDetails.get("name");
        String selectedProductPrice = productDetails.get("price");
        ExtentManager.getTest().info("🛍️ Clicked on a random product on the Homepage");

        // Validate name and price on the Product page
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        Assert.assertEquals(productDetailsPage.getProductDetailsName(), selectedProduct);
        Assert.assertEquals(productDetailsPage.getProductDetailsPrice(), selectedProductPrice);
        ExtentManager.getTest().info("🔎 Product and price match on the Product page");

        // Click Add to Cart button
        productDetailsPage.clickAddToCartButton();
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

        ExtentManager.getTest().pass("Product has been added to the cart from the Product Details page!");
    }
}
