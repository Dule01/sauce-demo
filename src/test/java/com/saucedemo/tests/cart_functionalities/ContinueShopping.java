package com.saucedemo.tests.cart_functionalities;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContinueShopping extends BaseTest {

    @BeforeMethod
    public void login(){
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginIfNotLoggedIn(username, password);
        test.info("✅ User logged in: " + username);
    }

    @Test
    public void testContinueShopping(){
        test.info("▶ Starting test: Continue shopping");

        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Add product and navigate to Cart page
        inventoryPage.clickAddToCartOnAnyProduct();
        inventoryPage.clickShoppingCartNotificationBadge();
        test.info("🛒 Product added to cart and the user navigated to Cart page");

        // Click the "Continue shopping" button
        cartPage.clickContinueShoppingButton();
        Assert.assertTrue(inventoryPage.isInventoryDisplayed(), "Inventory page is not displayed!");
        Assert.assertEquals(inventoryPage.getShoppingCartNotificationBadgeNumber(), "1",
                "No item has been added to the cart!");
        test.pass("🏠 User is navigated back to the Inventory page, product remains in the cart.");
    }

    @Test
    public void testRemoveProductAndContinueShopping() {
        test.info("▶ Starting test: Remove product and continue shopping");

        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Add product and navigate to Cart page
        inventoryPage.clickAddToCartOnAnyProduct();
        inventoryPage.clickShoppingCartNotificationBadge();
        test.info("🛒 Product added to cart and the user navigated to Cart page");

        // Remove product from the cart
        cartPage.clickRemoveCartButton();
        Assert.assertTrue(cartPage.isCartEmpty(), "Product has not been removed from the list of products!");
        test.info("⬅ Product is successfully removed from the list of products");

        // Click the "Continue shopping" button
        cartPage.clickContinueShoppingButton();
        Assert.assertTrue(inventoryPage.isInventoryDisplayed(), "Inventory page is not displayed!");
        Assert.assertFalse(cartPage.isNumberOfProductsVisible(), "Number badge on the Cart icon is visible!");
        test.pass("🏠 User is navigated back to the Inventory page, and there are no products in the cart");
    }
}
