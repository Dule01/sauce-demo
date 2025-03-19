package com.saucedemo.pages;

import com.saucedemo.utils.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage{
    private static final Logger logger = LogManager.getLogger(CheckoutPage.class);
    private WebDriverUtils utils;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.utils = new WebDriverUtils(driver);
    }

    @FindBy(id = "first-name")
    private WebElement firstNameInputField;

    @FindBy(id = "last-name")
    private WebElement lastNameInputField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInputField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    public void enterCheckoutDetailsAndContinue(String firstName, String lastName, String postalCode){
        utils.waitForElementToBeVisible(firstNameInputField);
        firstNameInputField.sendKeys(firstName);
        lastNameInputField.sendKeys(lastName);
        postalCodeInputField.sendKeys(postalCode);
        continueButton.click();
    }

    @FindBy(className = "inventory_item_name")
    private WebElement productName;

    public String getProductNameText(){
        return productName.getText();
    }

    @FindBy(className = "inventory_item_price")
    private WebElement productPrice;

    public String getProductPriceText(){
        return productPrice.getText();
    }

    @FindBy(className = "summary_total_label")
    private WebElement overviewTotalPrice;

    public String getOverviewTotalPriceText(){
        utils.waitForElementToBeVisible(overviewTotalPrice);
        return overviewTotalPrice.getText();
    }

    @FindBy(className = "summary_tax_label")
    private WebElement taxPrice;

    public String getTaxPriceText(){
        return taxPrice.getText();
    }

    @FindBy(id = "finish")
    private WebElement finishButton;

    public void clickFinishButton(){
        finishButton.click();
    }

    @FindBy(className = "complete-header")
    private WebElement checkoutCompleteHeader;

    public String getCheckoutCompleteHeaderText(){
        utils.waitForElementToBeVisible(checkoutCompleteHeader);
        return checkoutCompleteHeader.getText();
    }

    @FindBy(className = "complete-text")
    private WebElement checkoutCompleteDescription;

    public String getCheckoutCompleteDescriptionText(){
        return checkoutCompleteDescription.getText();
    }

    @FindBy(id = "back-to-products")
    private WebElement backToHomePage;

    public void clickBackToHomePage(){
        backToHomePage.click();
    }

}
