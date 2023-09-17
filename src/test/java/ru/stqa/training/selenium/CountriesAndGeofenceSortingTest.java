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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        List<String> sorted = countries;
        Collections.sort(sorted);
        assertEquals(sorted, countries);
    }

    @Test
    public void zonesAreSorted() {
        List<WebElement> countries = new ArrayList<>();
        List<String> zones = new ArrayList<>();
        List<WebElement> list = driver.findElements(By.cssSelector("#content tr.row"));

        for (WebElement e : list) {
            if (!e.findElement(By.cssSelector("td:nth-child(6)")).getText().equals("0")) {
                countries.add(e);
            }
        }



        for (WebElement c : countries) {
            list.clear();
            c.findElement(By.cssSelector("td:nth-child(5) a")).click();
            list = driver.findElements(By.cssSelector(".dataTable tr:not([class='header'])"));
            for (WebElement zone : list) {
                if (!zone.findElement(By.cssSelector("td:nth-child(3)")).getText().equals("")) {
                    zones.add(zone.findElement(By.cssSelector("td:nth-child(3)")).getText());
                }
            }
            List<String> sorted = zones;
            Collections.sort(sorted);
            assertEquals(sorted, zones);
            driver.navigate().back();
        }
    }
}



