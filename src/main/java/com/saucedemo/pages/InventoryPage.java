package com.saucedemo.pages;

import com.saucedemo.utils.TestDataUtils;
import com.saucedemo.utils.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(InventoryPage.class);
    private WebDriverUtils utils;

    public InventoryPage(WebDriver driver) {
        super(driver);
        this.utils = new WebDriverUtils(driver);
    }

    @FindBy(className = "inventory_list")
    private WebElement inventoryList;

    public boolean isInventoryDisplayed() {
        return inventoryList.isDisplayed();
    }

    @FindBy(css = ".inventory_item button")
    List<WebElement> addToCartButtons;

    @FindBy(className = "inventory_item_name")
    List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    List<WebElement> productPrices;

    public List<String> getAllPriceTexts() {
        List<String> priceTextsList = new ArrayList<>();
        for (WebElement price : productPrices) {
            String priceText = price.getText();
            priceTextsList.add(priceText);
        }
        return priceTextsList;
    }

    public List<String> getAllProductNames() {
        return productNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String returnProductName(int i) {
        return productNames.get(i).getText();
    }

    public String returnProductPrice(int i) {
        return productPrices.get(i).getText();
    }

    public Map<String, String> clickAddToCartOnAnyProduct() {
        utils.waitForElementsToBeVisible(addToCartButtons);

        int totalAddToCartButtons = addToCartButtons.size();
        logger.info("Total Add To Cart buttons: {}", totalAddToCartButtons);

        int randomAddToCartButtonIndex = TestDataUtils.getRandomNumber(0, totalAddToCartButtons - 1);
        logger.info("Index of the selected Add To Cart button: {}", randomAddToCartButtonIndex);

        addToCartButtons.get(randomAddToCartButtonIndex).click();
        String selectedProduct = returnProductName(randomAddToCartButtonIndex);
        String selectedProductPrice = returnProductPrice(randomAddToCartButtonIndex);
        logger.info("Product name added to the cart: {}", selectedProduct);
        logger.info("Product price: {}", selectedProductPrice);

        Map<String, String> productDetails = new HashMap<>();
        productDetails.put("name", selectedProduct);
        productDetails.put("price", selectedProductPrice);

        return productDetails;
    }

    public Map<String, String> clickOnAnyProduct() {
        utils.waitForElementsToBeVisible(productNames);

        int totalItems = productNames.size();
        logger.info("Total items: {}", totalItems);

        int randomItem = TestDataUtils.getRandomNumber(0, totalItems - 1);
        logger.info("Index of the selected item: {}", randomItem);

        String selectedProduct = returnProductName(randomItem);
        String selectedProductPrice = returnProductPrice(randomItem);
        logger.info("Product item added to the cart: {}", selectedProduct);
        logger.info("Product item price: {}", selectedProductPrice);

        productNames.get(randomItem).click();

        Map<String, String> productDetails = new HashMap<>();
        productDetails.put("name", selectedProduct);
        productDetails.put("price", selectedProductPrice);

        return productDetails;
    }

    @FindBy(className = "shopping_cart_badge")
    public WebElement shoppingCartNotificationBadge;

    public void clickShoppingCartNotificationBadge() {
        utils.waitForElementToBeVisible(shoppingCartNotificationBadge);
        shoppingCartNotificationBadge.click();
    }

    public String getShoppingCartNotificationBadgeNumber() {
        return shoppingCartNotificationBadge.getText();
    }

    @FindBy(className = "product_sort_container")
    public WebElement sortingDropdown;

    public Map<String, String> clickAddToCartOnAnyProductExcluding(List<String> excludedProductNames) {
        utils.waitForElementsToBeVisible(addToCartButtons);

        for (int i = 0; i < productNames.size(); i++) {
            String productName = returnProductName(i);

            if (!excludedProductNames.contains(productName)) {
                addToCartButtons.get(i).click();
                String productPrice = returnProductPrice(i);
                logger.info("Product name added to the cart: {}", productName);
                logger.info("Product price: {}", productPrice);

                Map<String, String> productDetails = new HashMap<>();
                productDetails.put("name", productName);
                productDetails.put("price", productPrice);

                return productDetails;
            }
        }

        throw new RuntimeException("⚠ No available product found that is not in the excluded list!");
    }

}


