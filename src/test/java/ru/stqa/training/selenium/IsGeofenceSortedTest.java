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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsGeofenceSortedTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("http://localhost/litecart/admin?app=geo_zones&doc=geo_zones");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();

    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void zonesAreSorted() {
        List<String> zones = new ArrayList<>();

        List<WebElement> list;
        int count = driver.findElements(By.cssSelector(".dataTable tr.row")).size();
        for (int i = 1; i <= count; i++) {
            driver.findElement(By.cssSelector("tr:nth-child(" + (i + 1) + ") td:nth-child(3) a")).click();
            list = driver.findElements(By.cssSelector("#table-zones tr:not([class='header'])"));
            for (int j = 0; j < list.size() - 1; j++) {
                zones.add(list.get(j).findElement(By.cssSelector("select:not([class*='select2']) [selected='selected']")).getText());
            }

            assertTrue(isSorted(zones));
            zones.clear();
            driver.navigate().back();
        }
    }

    private boolean isSorted(List<String> list) {

        String init = "";

        for (final String current : list) {
            if (current.compareTo(init) < 0) {
                return false;
            }
            init = current;
        }
        return true;
    }
}
