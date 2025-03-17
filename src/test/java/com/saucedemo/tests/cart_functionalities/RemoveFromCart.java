package com.saucedemo.tests.cart_functionalities;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.tests.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RemoveFromCart extends BaseTest {
    private static final Logger logger = LogManager.getLogger(RemoveFromCart.class);

    @Test
    public void testRemoveFromTheCart(){
        String username = "standard_user";
        String password = "secret_sauce";

        logger.info("Launching test: testRemoveFromTheCart");
        test.info("Launching test: testRemoveFromTheCart");

        LoginPage loginPage = new LoginPage(driver);

        // Making sure that the user is logged in before the test
        if(!driver.getCurrentUrl().contains("inventory.html")){
            loginPage.login(username, password);
            logger.info("Logged in user: {}", username);
            test.info("Logged in user: " + username);
        }

        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);

        inventoryPage.clickAddToCartOnAnyProduct();
        inventoryPage.shoppingCartNotificationBadge.click();
        logger.info("User is navigated to the Cart page");
        test.info("User is navigated to the Cart page");

        cartPage.clickRemoveCartButton();
        Assert.assertTrue(cartPage.isCartEmpty(), "Product has not been removed from the list of products!");
        logger.info("Product is successfully removed from the list of products");
        test.pass("Product is successfully removed from the list of products");

    }
}
