package ru.stqa.training.selenium.app;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.stqa.training.selenium.pages.CartPage;
import ru.stqa.training.selenium.pages.ShopPage;

import java.util.List;

public class Application {
    private WebDriver driver;
    private WebDriverWait wait;

    private ShopPage shopPage;
    private CartPage cartPage;

    public Application() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
        shopPage = new ShopPage(driver);
        cartPage = new CartPage(driver);
    }

    public void quit() {
        driver.quit();
    }

    public void addProductsToTheCart() {
        shopPage.open();
        for (int i = 1; i <= 3; i++) {
            driver.findElement(By.cssSelector(".product a")).click();
            if (driver.findElement(By.cssSelector(".sku")).getText().equals("RD001")) {
                driver.navigate().back();
                driver.findElement(By.cssSelector(".product a")).click();
            }
            driver.findElement(By.cssSelector("[class='buy_now'] [name='add_cart_product']")).click();
            wait.until(ExpectedConditions.attributeToBe(By.cssSelector("#cart .quantity"), "textContent", String.valueOf(i)));

            driver.navigate().back();
        }
    }

    public void removeProductsFromTheCart() {
        cartPage.open();
        List<WebElement> list = driver.findElements(By.cssSelector(".dataTable td.item"));

        WebElement item;
        try {
            for (int j = 1; j <= list.size(); j++) {
                item = driver.findElement(By.cssSelector(".dataTable td"));
                driver.findElement(By.cssSelector("#box-checkout-cart [name='remove_cart_item']")).click();
                wait.until(ExpectedConditions.stalenessOf(item));
            }
        } catch (TimeoutException e) {
            System.out.println("There are no items in your cart.");
        }
    }
}
