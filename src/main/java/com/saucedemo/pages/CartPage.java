package com.saucedemo.pages;

import com.saucedemo.utils.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage{
    private static final Logger logger = LogManager.getLogger(CartPage.class);
    private WebDriverUtils utils;

    public CartPage(WebDriver driver) {
        super(driver);
        this.utils = new WebDriverUtils(driver);
    }

    @FindBy(className = "inventory_item_name")
    private WebElement selectedProductName;

    public String getSelectedProductName(){
        return selectedProductName.getText();
    }

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public boolean isCheckoutButtonDisplayed(){
        return utils.isElementVisible(checkoutButton);
    }
}
