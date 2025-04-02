package com.saucedemo.tests.checkout;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.reports.ExtentManager;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.ConfigReader;
import com.saucedemo.utils.Constants;
import com.saucedemo.utils.TestData;
import com.saucedemo.utils.TextUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class CheckoutMultipleProducts extends BaseTest {

    @Test(groups = {"smoke", "regression"})
    public void testCheckoutMultipleProducts() throws InterruptedException {
        ExtentManager.getTest().info("▶ Starting test: Checkout multiple products");

        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginIfNotLoggedIn(username, password);
        ExtentManager.getTest().info("✅ User logged in: " + username);

        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Add first product to cart
        Map<String, String> productDetails = inventoryPage.clickAddToCartOnAnyProduct();
        String selectedProduct = productDetails.get("name");
        String selectedProductPrice = productDetails.get("price");
        ExtentManager.getTest().info("🛒 First product added to cart: " + selectedProduct + " | Price: " + selectedProductPrice);

        // Add second product to cart
        Map<String, String> secondProductDetails = inventoryPage.clickAddToCartOnAnyProduct();
        String secondSelectedProduct = secondProductDetails.get("name");
        String secondSelectedProductPrice = secondProductDetails.get("price");
        ExtentManager.getTest().info("🛒 Second product added to cart: " + secondSelectedProduct + " | Price: " + secondSelectedProductPrice);

        // Go to cart
        inventoryPage.clickShoppingCartNotificationBadge();
        ExtentManager.getTest().info("🧺 Navigated to Cart page");

        // Start checkout
        cartPage.clickCheckoutButton();
        ExtentManager.getTest().info("📦 Navigated to Checkout page");

        // Verify URL
        String checkoutUrl = driver.getCurrentUrl();
        Assert.assertEquals(checkoutUrl, Constants.CHECKOUT_URL);
        ExtentManager.getTest().info("✅ Checkout URL verified");

        // Fill checkout form
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutDetailsAndContinue(TestData.FIRST_NAME, TestData.LAST_NAME, TestData.POSTAL_CODE);
        ExtentManager.getTest().info("📝 Checkout form filled");

        // Verify products in overview
        Assert.assertEquals(selectedProduct, checkoutPage.getFirstItemInTheCartNameText());
        Assert.assertEquals(selectedProductPrice, checkoutPage.getFirstItemInTheCartPriceText());
        Assert.assertEquals(secondSelectedProduct, checkoutPage.geSecondItemInTheCartNameText());
        Assert.assertEquals(secondSelectedProductPrice, checkoutPage.getSecondItemInTheCartPriceText());
        ExtentManager.getTest().info("🔎 Products and price match in overview");

        // Verify tax & total
        float productPrice = TextUtils.dollarToFloat(selectedProductPrice);
        float secondProductPrice = TextUtils.dollarToFloat(secondSelectedProductPrice);
        float taxPrice = TextUtils.extractPrice(checkoutPage.getTaxPriceText());
        float finalTotalPrice = productPrice + secondProductPrice;
        finalTotalPrice += taxPrice;
        Assert.assertEquals(checkoutPage.getOverviewTotalPriceText(), "Total: $" + finalTotalPrice);
        ExtentManager.getTest().info("💰 Total price validated: $" + finalTotalPrice);

        // Finish order
        checkoutPage.clickFinishButton();
        Assert.assertEquals(checkoutPage.getCheckoutCompleteHeaderText(), Constants.CHECKOUT_THANK_YOU_MESSAGE);
        Assert.assertEquals(checkoutPage.getCheckoutCompleteDescriptionText(), Constants.CHECKOUT_DESCRIPTION_MESSAGE);
        ExtentManager.getTest().info("🎉 Order completed and confirmation received");

        // Back to homepage
        checkoutPage.clickBackToHomePage();
        Assert.assertTrue(inventoryPage.isInventoryDisplayed(), "User is not navigated to the Home (inventory) page!");
        ExtentManager.getTest().pass("🏁 Products successfully purchased. User navigated back to the Homepage.");
    }
}

