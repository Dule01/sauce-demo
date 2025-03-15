package com.saucedemo.tests.add_to_cart;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.DataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class AddToCartFromHomepage extends BaseTest {
    private static final Logger logger = LogManager.getLogger(AddToCartFromHomepage.class);

    @Test(dataProvider = "successfulLoginData", dataProviderClass = DataProviders.class)
    public void addToCartFromHomepage(String username, String password, boolean expectedSuccess) {
        logger.info("Launching test: addToCartFromHomepage");
        test.info("Launching test: addToCartFromHomepage");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        logger.info("Logged in user: {}", username);
        test.info("Logged in user: " + username);

        InventoryPage inventoryPage = new InventoryPage(driver);
        Map<String, String> productDetails = inventoryPage.clickAddToCartOnAnyProduct();
        String selectedProduct = productDetails.get("name");
        String selectedProductPrice = productDetails.get("price");
        logger.info("Selected product: {} and selected product price is: {}", selectedProduct, selectedProductPrice);
        test.info("Selected product: " + selectedProduct + " and selected product price is: " + selectedProductPrice);

        Assert.assertEquals(inventoryPage.getShoppingCartNotificationBadgeNumber(), "1",
                "No item has been added to the cart!");

        CartPage cartPage = new CartPage(driver);
        inventoryPage.shoppingCartNotificationBadge.click();
        Assert.assertEquals(selectedProduct, cartPage.getSelectedProductName());
        Assert.assertTrue(cartPage.isCheckoutButtonDisplayed(), "Checkout button is not visible on the Cart page!");

        test.pass("Product has been added to the cart from the Homepage!");
    }
}
