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
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;

public class LinkOpensInNewWindowTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 5);

        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void tryToFindExternalLinkAndOpenItThanCloseAndBackToMainWindow() {
        driver.findElement(By.cssSelector(".fa-pencil")).click();

        String mainWindow = driver.getWindowHandle();
        List<WebElement> list = driver.findElements(By.cssSelector(".fa-external-link"));

        for (WebElement e : list) {
            e.click();

            wait.until(numberOfWindowsToBe(2));
            Set<String> windows = driver.getWindowHandles();
            windows.remove(mainWindow);
            String newWindow = windows.iterator().next();

            driver.switchTo().window(newWindow);
            System.out.println(driver.getTitle());

            driver.close();
            driver.switchTo().window(mainWindow);
        }

    }

}