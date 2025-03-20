package com.saucedemo.tests.checkout;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.tests.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.util.Map;

public class CheckoutMultipleProducts extends BaseTest {
    //TODO Tidy up data and improve readability by creating proper methods
    private static final Logger logger = LogManager.getLogger(CheckoutMultipleProducts.class);

    @Test
    public void testCheckoutMultipleProducts(){
        String username = "standard_user";
        String password = "secret_sauce";

        logger.info("Launching test: testCheckoutSingleProduct");
        test.info("Launching test: testCheckoutSingleProduct");

        LoginPage loginPage = new LoginPage(driver);

        // Making sure that the user is logged in before the test
        if(!driver.getCurrentUrl().contains("inventory.html")){
            loginPage.login(username, password);
            logger.info("Logged in user: {}", username);
            test.info("Logged in user: " + username);
        }

        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);

        Map<String, String> productDetails = inventoryPage.clickAddToCartOnAnyProduct();
        String selectedProduct = productDetails.get("name");
        String selectedProductPrice = productDetails.get("price");
        logger.info("Selected product: {} and selected product price is: {}", selectedProduct, selectedProductPrice);
        test.info("Selected product: " + selectedProduct + " and selected product price is: " + selectedProductPrice);

        Map<String, String> secondProductDetails = inventoryPage.clickAddToCartOnAnyProduct();
        String secondSelectedProduct = secondProductDetails.get("name");
        String secondSelectedProductPrice = secondProductDetails.get("price");
        logger.info("Second selected product: {} and second selected product price is: {}", secondSelectedProduct, secondSelectedProductPrice);
        test.info("Second selected product: " + secondSelectedProduct + " and selected product price is: " + secondSelectedProductPrice);

        inventoryPage.clickShoppingCartNotificationBadge();
        logger.info("User is navigated to the Cart page");
        test.info("User is navigated to the Cart page");

        cartPage.clickCheckoutButton();
        logger.info("User is navigated to the Checkout page");
        test.info("User is navigated to the Checkout page");

        String checkoutUrl = driver.getCurrentUrl();
        Assert.assertEquals(checkoutUrl, "https://www.saucedemo.com/checkout-step-one.html");

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutDetailsAndContinue("Dusan", "Simic", "12345");
        Assert.assertEquals(selectedProduct, checkoutPage.getFirstItemInTheCartNameText());
        Assert.assertEquals(selectedProductPrice, checkoutPage.getFirstItemInTheCartPriceText());
        Assert.assertEquals(secondSelectedProduct, checkoutPage.geSecondItemInTheCartNameText());
        Assert.assertEquals(secondSelectedProductPrice, checkoutPage.getSecondItemInTheCartPriceText());
        logger.info("User is navigated to the overview on the Checkout page");
        test.info("User is navigated to the overview on the Checkout page");

        selectedProductPrice = selectedProductPrice.replace("$", "");
        float productPrice = Float.parseFloat(selectedProductPrice);
        secondSelectedProductPrice = secondSelectedProductPrice.replace("$", "");
        float secondProductPrice = Float.parseFloat(secondSelectedProductPrice);
        String taxPriceText = checkoutPage.getTaxPriceText().replace("Tax: $", "");
        float taxPrice = Float.parseFloat(taxPriceText);
        logger.info("First product price is {}, second product price is {}, tax price is {}", productPrice, secondProductPrice, taxPrice);
        test.info("First product price is " + productPrice + ", second product price is " + secondProductPrice + ", tax price is " + taxPrice);

        DecimalFormat df = new DecimalFormat("0.00");

        float finalTotalPrice = taxPrice + productPrice;
        finalTotalPrice += secondProductPrice;
        String formattedTotalPrice = "Total: $" + df.format(finalTotalPrice);
        logger.info("Total price: {}", finalTotalPrice);
        test.info("Total price: " + finalTotalPrice);
        Assert.assertEquals(checkoutPage.getOverviewTotalPriceText(), formattedTotalPrice);
        logger.info("Total price matched!");
        test.info("Total price matched!");

        checkoutPage.clickFinishButton();
        Assert.assertEquals(checkoutPage.getCheckoutCompleteHeaderText(), "Thank you for your order!");
        Assert.assertEquals(checkoutPage.getCheckoutCompleteDescriptionText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
        checkoutPage.clickBackToHomePage();
        Assert.assertTrue(inventoryPage.isInventoryDisplayed(), "User is not navigated to the Home (inventory) page!");
        logger.info("Products have been purchased, and the user navigated back to the Homepage!");
        test.pass("Products have been purchased, and the user navigated back to the Homepage!");
    }
}

