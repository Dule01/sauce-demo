package com.saucedemo.tests.cart_functionalities;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RemoveFromCart extends BaseTest {

    @Test
    public void testRemoveFromTheCart(){
        test.info("▶ Starting test: Remove product from the Cart");

        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginIfNotLoggedIn(username, password);
        test.info("✅ User logged in: " + username);

        // Add product and navigate to Cart page
        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);
        inventoryPage.clickAddToCartOnAnyProduct();
        inventoryPage.clickShoppingCartNotificationBadge();
        test.info("🛒 Product added to cart and the user navigated to Cart page");;

        // Remove product from the cart
        cartPage.clickRemoveCartButton();
        Assert.assertTrue(cartPage.isCartEmpty(), "Product has not been removed from the list of products!");
        test.info("⬅ Product is successfully removed from the list of products");
    }
}
