package com.saucedemo.tests.cart_functionalities;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.tests.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContinueShopping extends BaseTest {
    private static final Logger logger = LogManager.getLogger(ContinueShopping.class);

    @BeforeMethod
    public void login(){
        String username = "standard_user";
        String password = "secret_sauce";

        // Making sure that the user is logged in before the test
        if(!driver.getCurrentUrl().contains("inventory.html")){
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login(username, password);
            logger.info("Logged in user: {}", username);
            test.info("Logged in user: " + username);
        }
    }

    @Test
    public void testContinueShopping(){
        logger.info("Launching test: testContinueShopping");
        test.info("Launching test: testContinueShopping");

        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);

        inventoryPage.clickAddToCartOnAnyProduct();
        inventoryPage.shoppingCartNotificationBadge.click();
        logger.info("User is navigated to the Cart page");
        test.info("User is navigated to the Cart page");

        cartPage.clickContinueShoppingButton();
        Assert.assertTrue(inventoryPage.isInventoryDisplayed(), "Inventory page is not displayed!");
        Assert.assertEquals(inventoryPage.getShoppingCartNotificationBadgeNumber(), "1",
                "No item has been added to the cart!");
        logger.info("User is navigated back to the Inventory page, product remains in the cart.");
        test.pass("User is navigated back to the Inventory page, product remains in the cart.");
    }

    @Test
    public void testRemoveProductAndContinueShopping() {
        logger.info("Launching test: testContinueShopping");
        test.info("Launching test: testContinueShopping");

        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);

        inventoryPage.clickAddToCartOnAnyProduct();
        inventoryPage.shoppingCartNotificationBadge.click();
        logger.info("User is navigated to the Cart page");
        test.info("User is navigated to the Cart page");

        cartPage.clickRemoveCartButton();
        Assert.assertTrue(cartPage.isCartEmpty(), "Product has not been removed from the list of products!");
        logger.info("Product is successfully removed from the list of products");
        test.info("Product is successfully removed from the list of products");

        cartPage.clickContinueShoppingButton();
        Assert.assertTrue(inventoryPage.isInventoryDisplayed(), "Inventory page is not displayed!");
        Assert.assertFalse(cartPage.isNumberOfProductsVisible(), "Number badge on the Cart icon is visible!");
        logger.info("User is navigated back to the Inventory page, product is not displayed in the cart.");
        test.pass("User is navigated back to the Inventory page, product is not displayed in the cart.");
    }
}
