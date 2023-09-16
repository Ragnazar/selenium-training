package ru.stqa.training.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminPanelViewingPageHeadersTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();


    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void adminUserShouldClickOnMenuItemAndFindHeader() {


        List<WebElement> list = driver.findElements(By.cssSelector("li#app-"));

        for (int i = 1; i <= list.size(); i++) {
            driver.findElement(By.cssSelector("#box-apps-menu li#app-:nth-of-type(" + i + ")")).click();
            List<WebElement> sublist = driver.findElements(By.cssSelector(".docs li"));

            for (int j = 1; j <= sublist.size(); j++) {
                driver.findElement(By.cssSelector(".selected li[id^=doc]:nth-of-type(" + j + ")")).click();
                Assertions.assertTrue(existsElement("h1"));
            }
        }
    }

    private boolean existsElement(String name) {
        try {
            driver.findElement(By.tagName(name));
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }
}