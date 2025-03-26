package com.saucedemo.tests.checkout;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.ConfigReader;
import com.saucedemo.utils.TestData;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

public class CheckoutNegativeTests extends BaseTest {

    @BeforeMethod
    public void login(){
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginIfNotLoggedIn(username, password);
        test.info("✅ User logged in: " + username);
    }

    @Test
    public void testFirstNameMissingCheckout(){
        String expectedErrorMessage = "Error: First Name is required";
        String expectedUrlWhenErrorOccurs = "https://www.saucedemo.com/checkout-step-one.html";

        test.info("▶ Starting test: Blank First Name on the Checkout form");

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

        // Fill checkout form without first name
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutDetailsAndContinue(
                "",
                TestData.LAST_NAME,
                TestData.POSTAL_CODE
        );
        test.info("📝 Checkout form filled with missing username");

        // Verify that the correct error message is displayed
        Assert.assertEquals(checkoutPage.getErrorMessageText(), expectedErrorMessage);
        String actualUrlWhenErrorOccurs = driver.getCurrentUrl();
        Assert.assertEquals(actualUrlWhenErrorOccurs, expectedUrlWhenErrorOccurs);
        test.pass("🟩 Correct error message has been displayed! User remained on the Step One checkout process");
    }

    @Test
    public void testLastNameMissingCheckout(){
        String expectedErrorMessage = "Error: Last Name is required";
        String expectedUrlWhenErrorOccurs = "https://www.saucedemo.com/checkout-step-one.html";

        test.info("▶ Starting test: Blank Last Name on the Checkout form");

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

        // Fill checkout form without last name
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutDetailsAndContinue(
                TestData.FIRST_NAME,
                "",
                TestData.POSTAL_CODE
        );
        test.info("📝 Checkout form filled with missing last name");

        // Verify that the correct error message is displayed
        Assert.assertEquals(checkoutPage.getErrorMessageText(), expectedErrorMessage);
        String actualUrlWhenErrorOccurs = driver.getCurrentUrl();
        Assert.assertEquals(actualUrlWhenErrorOccurs, expectedUrlWhenErrorOccurs);
        test.pass("🟩 Correct error message has been displayed! User remained on the Step One checkout process");
    }

    @Test
    public void testZipCodeMissingCheckout(){
        String expectedErrorMessage = "Error: Postal Code is required";
        String expectedUrlWhenErrorOccurs = "https://www.saucedemo.com/checkout-step-one.html";

        test.info("▶ Starting test: Blank Postal Code on the Checkout form");

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

        // Fill checkout form without postal code
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutDetailsAndContinue(
                TestData.FIRST_NAME,
                TestData.LAST_NAME,
                ""
        );
        test.info("📝 Checkout form filled with missing postal code");

        // Verify that the correct error message is displayed
        Assert.assertEquals(checkoutPage.getErrorMessageText(), expectedErrorMessage);
        String actualUrlWhenErrorOccurs = driver.getCurrentUrl();
        Assert.assertEquals(actualUrlWhenErrorOccurs, expectedUrlWhenErrorOccurs);
        test.pass("🟩 Correct error message has been displayed! User remained on the Step One checkout process");
    }
}
