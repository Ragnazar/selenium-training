package ru.stqa.training.selenium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddProductToCustomersCartTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);

        driver.get("http://localhost/litecart");


    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void productAddedToTheCart() {
        addProductsToTheCart();
        goToTheCart();
        removeProductsFromTheCart();
    }

    private void addProductsToTheCart() {
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

    private void removeProductsFromTheCart() {
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

    private void goToTheCart() {
        driver.findElement(By.cssSelector("#cart .link")).click();
    }
}
