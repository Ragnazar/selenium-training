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
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.concurrent.TimeUnit;

public class NoMessagesInBrowserLogTest {
    private EventFiringWebDriver driver;

    public static class Listener extends AbstractWebDriverEventListener {
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println("Found");
            driver.manage().logs().get("browser").forEach(System.out::println);
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println(throwable);
        }
    }

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {

        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.register(new Listener());
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void tryToCheckMessagesInConsoleLog() {
        int count = driver.findElements(By.cssSelector(".dataTable tr td:nth-child(3) a")).size();

        for (int i = 5; i < count; i++) {
            driver.findElement(By.cssSelector(".dataTable tr:nth-child(" + i + ") td:nth-child(3) a")).click();
            driver.navigate().back();
        }
    }
}
