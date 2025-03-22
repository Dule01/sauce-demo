package com.saucedemo.tests.checkout;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.ConfigReader;
import com.saucedemo.utils.Constants;
import com.saucedemo.utils.TestData;
import com.saucedemo.utils.TextUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class CheckoutSingleProduct extends BaseTest {

    @Test
    public void testCheckoutSingleProduct() {
        test.info("▶ Starting test: Checkout single product");

        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginIfNotLoggedIn(username, password);
        test.info("✅ User logged in: " + username);

        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Add product to cart
        Map<String, String> productDetails = inventoryPage.clickAddToCartOnAnyProduct();
        String selectedProduct = productDetails.get("name");
        String selectedProductPrice = productDetails.get("price");
        test.info("🛒 Product added to cart: " + selectedProduct + " | Price: " + selectedProductPrice);

        // Go to cart
        inventoryPage.shoppingCartNotificationBadge.click();
        test.info("🧺 Navigated to Cart page");

        // Start checkout
        cartPage.clickCheckoutButton();
        test.info("📦 Navigated to Checkout page");

        // Verify URL
        String checkoutUrl = driver.getCurrentUrl();
        Assert.assertEquals(checkoutUrl, Constants.CHECKOUT_URL);
        test.info("✅ Checkout URL verified");

        // Fill checkout form
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutDetailsAndContinue(
                TestData.FIRST_NAME,
                TestData.LAST_NAME,
                TestData.POSTAL_CODE
        );
        test.info("📝 Checkout form filled");

        // Verify product in overview
        Assert.assertEquals(selectedProduct, checkoutPage.getProductNameText());
        Assert.assertEquals(selectedProductPrice, checkoutPage.getProductPriceText());
        test.info("🔎 Product and price match in overview");

        // Verify tax & total
        float productPrice = TextUtils.dollarToFloat(selectedProductPrice);
        float taxPrice = TextUtils.extractPrice(checkoutPage.getTaxPriceText());
        float finalTotalPrice = taxPrice + productPrice;

        Assert.assertEquals(checkoutPage.getOverviewTotalPriceText(), "Total: $" + finalTotalPrice);
        test.info("💰 Total price validated: $" + finalTotalPrice);

        // Finish order
        checkoutPage.clickFinishButton();
        Assert.assertEquals(checkoutPage.getCheckoutCompleteHeaderText(), Constants.CHECKOUT_THANK_YOU_MESSAGE);
        Assert.assertEquals(checkoutPage.getCheckoutCompleteDescriptionText(), Constants.CHECKOUT_DESCRIPTION_MESSAGE);
        test.info("🎉 Order completed and confirmation received");

        // Back to homepage
        checkoutPage.clickBackToHomePage();
        Assert.assertTrue(inventoryPage.isInventoryDisplayed(), "❌ User is not navigated to the inventory page!");
        test.pass("🏁 Product successfully purchased. User navigated back to the Homepage.");
    }
}
