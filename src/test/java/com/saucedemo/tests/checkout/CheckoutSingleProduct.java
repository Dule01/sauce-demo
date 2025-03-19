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

import java.util.Map;

public class CheckoutSingleProduct extends BaseTest {
    //TODO tidy up the data in usage

    private static final Logger logger = LogManager.getLogger(CheckoutSingleProduct.class);

    @Test
    public void testCheckoutSingleProduct(){
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

        inventoryPage.shoppingCartNotificationBadge.click();
        logger.info("User is navigated to the Cart page");
        test.info("User is navigated to the Cart page");

        cartPage.clickCheckoutButton();
        logger.info("User is navigated to the Checkout page");
        test.info("User is navigated to the Checkout page");

        String checkoutUrl = driver.getCurrentUrl();
        Assert.assertEquals(checkoutUrl, "https://www.saucedemo.com/checkout-step-one.html");

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCheckoutDetailsAndContinue("Dusan", "Simic", "12345");
        Assert.assertEquals(selectedProduct, checkoutPage.getProductNameText());
        Assert.assertEquals(selectedProductPrice, checkoutPage.getProductPriceText());
        logger.info("User is navigated to the overview on the Checkout page");
        test.info("User is navigated to the overview on the Checkout page");

        selectedProductPrice = selectedProductPrice.replace("$", "");
        float productPrice = Float.parseFloat(selectedProductPrice);
        String taxPriceText = checkoutPage.getTaxPriceText().replace("Tax: $", "");
        float taxPrice = Float.parseFloat(taxPriceText);
        logger.info("Tax price: {}", taxPrice);
        test.info("Tax price: " + taxPrice);

        float finalTotalPrice = taxPrice + productPrice;
        logger.info("Total price: {}", finalTotalPrice);
        test.info("Total price: " + finalTotalPrice);
        Assert.assertEquals(checkoutPage.getOverviewTotalPriceText(), "Total: $" + finalTotalPrice);
        logger.info("Total price matched!");
        test.info("Total price matched!");

        checkoutPage.clickFinishButton();
        Assert.assertEquals(checkoutPage.getCheckoutCompleteHeaderText(), "Thank you for your order!");
        Assert.assertEquals(checkoutPage.getCheckoutCompleteDescriptionText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
        checkoutPage.clickBackToHomePage();
        Assert.assertTrue(inventoryPage.isInventoryDisplayed(), "User is not navigated to the Home (inventory) page!");
        logger.info("Product has been purchased, and the user navigated back to the Homepage!");
        test.pass("Product has been purchased, and the user navigated back to the Homepage!");
    }
}
