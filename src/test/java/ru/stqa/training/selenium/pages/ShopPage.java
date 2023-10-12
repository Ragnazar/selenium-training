package ru.stqa.training.selenium.pages;

import org.openqa.selenium.WebDriver;

public class ShopPage extends Page{

    public ShopPage(WebDriver driver) {
        super(driver);
    }

    public ShopPage open() {
        driver.get("http://localhost/litecart");
        return this;
    }
}
