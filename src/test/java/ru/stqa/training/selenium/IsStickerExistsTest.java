package ru.stqa.training.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsStickerExistsTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("http://localhost/litecart");


    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void productHasSticker() throws Exception {
        List<WebElement> list = driver.findElements(By.cssSelector(".content ul link"));

        for (WebElement e:list) {
            List<WebElement> stickers = e.findElements(By.cssSelector("[class^='sticker']"));
            assertTrue(existsElement(e,"[class^='sticker']"));
            if (stickers.size()>1){
                throw new Exception("The element have more than one sticker");
            }
        }
    }

    private boolean existsElement(WebElement element, String name) {
        try {
            element.findElement(By.cssSelector(name));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
}
