package ru.stqa.training.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends Page{

    public CartPage(WebDriver driver) {
        super(driver);
    }
    public CartPage open() {
        driver.findElement(By.cssSelector("#cart .link")).click();
        return this;
    }

}
