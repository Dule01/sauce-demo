package com.saucedemo.pages;

import com.saucedemo.utils.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailsPage extends BasePage{
    private static final Logger logger = LogManager.getLogger(ProductDetailsPage.class);
    private WebDriverUtils utils;

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
        this.utils = new WebDriverUtils(driver);
    }

    @FindBy(css = ".inventory_details_name")
    private WebElement productDetailsName;

    public String getProductDetailsName(){
        return productDetailsName.getText();
    }

    @FindBy(className = "inventory_details_price")
    private WebElement productDetailsPrice;

    public String getProductDetailsPrice(){
        return productDetailsPrice.getText();
    }

    @FindBy(id = "add-to-cart")
    private WebElement addToCartButton;

    public void clickAddToCartButton(){
        utils.waitForElementToBeVisible(addToCartButton);
        addToCartButton.click();
    }
}
