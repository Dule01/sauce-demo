package com.saucedemo.pages;

import com.saucedemo.utils.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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

    public boolean isCheckoutButtonDisplayed(){
        return utils.isElementVisible(checkoutButton);
    }

    @FindBy(css = ".item_pricebar button")
    private WebElement removeButton;

    public void clickRemoveCartButton(){
        utils.waitForElementToBeVisible(removeButton);
        removeButton.click();
    }

    @FindBy(className = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "shopping_cart_badge")
    public WebElement shoppingCartNotificationBadge;

    public boolean isCartEmpty(){
        boolean noItems = cartItems.isEmpty();

        if(noItems){
            logger.info("Cart is empty");
            return true;
        } else {
            logger.warn("Cart is NOT empty! There's {} products", cartItems.size());
            return false;
        }
    }

    public boolean isNumberOfProductsVisible() {
        List<WebElement> badgeElements = driver.findElements(By.cssSelector(".shopping_cart_badge"));

        if (badgeElements.isEmpty()) {
            return false;
        } else {
            return badgeElements.get(0).isDisplayed();
        }
    }

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    public void clickContinueShoppingButton(){
        utils.waitForElementToBeVisible(continueShoppingButton);
        continueShoppingButton.click();
    }

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public void clickCheckoutButton(){
        utils.waitForElementToBeVisible(checkoutButton);
        checkoutButton.click();
    }
}
