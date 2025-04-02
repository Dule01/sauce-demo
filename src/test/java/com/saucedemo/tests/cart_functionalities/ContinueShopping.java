package com.saucedemo.tests.cart_functionalities;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.reports.ExtentManager;
import com.saucedemo.tests.BaseTest;
import com.saucedemo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContinueShopping extends BaseTest {

    @BeforeMethod(groups = {"smoke", "regression"})
    public void login() throws InterruptedException {
        String username = ConfigReader.get("username");
        String password = ConfigReader.get("password");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginIfNotLoggedIn(username, password);
        ExtentManager.getTest().info("✅ User logged in: " + username);
    }

    @Test(groups = "regression")
    public void testContinueShopping(){
        ExtentManager.getTest().info("▶ Starting test: Continue shopping");

        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Add product and navigate to Cart page
        inventoryPage.clickAddToCartOnAnyProduct();
        inventoryPage.clickShoppingCartNotificationBadge();
        ExtentManager.getTest().info("🛒 Product added to cart and the user navigated to Cart page");

        // Click the "Continue shopping" button
        cartPage.clickContinueShoppingButton();
        Assert.assertTrue(inventoryPage.isInventoryDisplayed(), "Inventory page is not displayed!");
        Assert.assertEquals(inventoryPage.getShoppingCartNotificationBadgeNumber(), "1",
                "No item has been added to the cart!");
        ExtentManager.getTest().pass("🏠 User is navigated back to the Inventory page, product remains in the cart.");
    }

    @Test(groups = "regression")
    public void testRemoveProductAndContinueShopping() {
        ExtentManager.getTest().info("▶ Starting test: Remove product and continue shopping");

        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Add product and navigate to Cart page
        inventoryPage.clickAddToCartOnAnyProduct();
        inventoryPage.clickShoppingCartNotificationBadge();
        ExtentManager.getTest().info("🛒 Product added to cart and the user navigated to Cart page");

        // Remove product from the cart
        cartPage.clickRemoveCartButton();
        Assert.assertTrue(cartPage.isCartEmpty(), "Product has not been removed from the list of products!");
        ExtentManager.getTest().info("⬅ Product is successfully removed from the list of products");

        // Click the "Continue shopping" button
        cartPage.clickContinueShoppingButton();
        Assert.assertTrue(inventoryPage.isInventoryDisplayed(), "Inventory page is not displayed!");
        Assert.assertFalse(cartPage.isNumberOfProductsVisible(), "Number badge on the Cart icon is visible!");
        ExtentManager.getTest().pass("🏠 User is navigated back to the Inventory page, and there are no products in the cart");
    }
}
