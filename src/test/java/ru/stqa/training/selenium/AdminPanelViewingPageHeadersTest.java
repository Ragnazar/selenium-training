package ru.stqa.training.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class AdminPanelViewingPageHeadersTest {
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
        wait = new WebDriverWait(driver, 5);


    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void adminUserShouldClickOnMenuItemAndFindHeader() {
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

        List<WebElement> list = driver.findElements(By.cssSelector("li#app-"));

//        List<WebElement> docs = driver.findElements(By.cssSelector(".selected li#doc"));
        int i = 1;
        for (WebElement e : list) {
            e.click();
            List<WebElement> docs = driver.findElements(By.cssSelector("li#doc"));
            System.out.println(docs.size());
            for (WebElement d : docs) {
                d.click();
                wait.until(presenceOfElementLocated(By.cssSelector("#content h1")));
                System.out.println(i++);
            }

        }

    }
}