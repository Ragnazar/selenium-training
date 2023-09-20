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

public class CountriesAndGeofenceSortingTest {
    private WebDriver driver;
    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

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
    public void countriesAreSorted() {
        List<String> countries = new ArrayList<>();
        List<WebElement> list = driver.findElements(By.cssSelector("#content tr.row"));

        for (WebElement e : list) {
            countries.add(e.findElement(By.cssSelector("td:nth-child(5)")).getText());
        }

        assertTrue(isSorted(countries));
    }

    @Test
    public void zonesAreSorted() {
        List<String> zones = new ArrayList<>();
        List<WebElement> list;
        int count = driver.findElements(By.cssSelector("#content tr.row")).size();

        for (int i = 0; i < count; i++) {
            if (!driver.findElement(By.cssSelector("td:nth-child(6)")).getText().equals("0")) {
                driver.findElement(By.cssSelector("td:nth-child(5) a")).click();
                list = driver.findElements(By.cssSelector(".dataTable tr:not([class='header'])"));
                for (WebElement zone : list) {
                    if (!zone.findElement(By.cssSelector("td:nth-child(3)")).getText().equals("")) {
                        zones.add(zone.findElement(By.cssSelector("td:nth-child(3)")).getText());
                    }
                }

                assertTrue(isSorted(zones));
                driver.navigate().back();
            }
        }
    }

    private boolean isSorted(List<String> list) {
        String init = "";

        for (final String current : list) {
            if (current.compareTo(init) < 0)
                return false;
            init = current;
        }

        return true;
    }
}



