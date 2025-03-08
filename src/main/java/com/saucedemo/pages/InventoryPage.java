package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class InventoryPage extends BasePage{

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "inventory_list")
    private WebElement inventoryList;

    public boolean isInventoryDisplayed() {
        return inventoryList.isDisplayed();
    }
}
