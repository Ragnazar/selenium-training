package ru.stqa.training.selenium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.training.selenium.utils.GenerateData;

import java.util.concurrent.TimeUnit;

public class UserCreateTest {
    private WebDriver driver;
    private static GenerateData genData;
    private static String login;
    private static String password;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.firefoxdriver().setup();

        genData = new GenerateData();
        password = genData.generateRandomAlphaNumeric(10);
        login = genData.generateEmail(30);
    }

    @BeforeEach
    public void setUp() {

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("http://localhost/litecart");


    }

    @AfterEach
    public void teardown() {
         driver.quit();
    }

    @Test
    public void createNewCustomer() {
        driver.findElement(By.cssSelector("#box-account-login a")).click();
        driver.findElement(By.cssSelector(".content input[name='firstname']"))
                .sendKeys(genData.generateRandomString(10));
        driver.findElement(By.cssSelector(".content input[name='lastname']"))
                .sendKeys(genData.generateRandomString(10));
        driver.findElement(By.cssSelector(".content input[name='address1']"))
                .sendKeys(genData.generateStringWithAllobedSplChars(20, "abc123_-."));
        driver.findElement(By.cssSelector(".content input[name='postcode']"))
                .sendKeys(genData.generateRandomNumber(5));
        driver.findElement(By.cssSelector(".content input[name='city']"))
                .sendKeys(genData.generateRandomString(10));
        driver.findElement(By.cssSelector(".content input[name='email']"))
                .sendKeys(login);
        driver.findElement(By.cssSelector(".content input[name='phone']"))
                .sendKeys("+" + genData.generateRandomNumber(9));
        driver.findElement(By.cssSelector(".content input[name='password']"))
                .sendKeys(password);
        driver.findElement(By.cssSelector(".content input[name='confirmed_password']"))
                .sendKeys(password);

        driver.findElement(By.cssSelector(".content [class^='select2-selection']")).click();
        driver.findElement(By.cssSelector("input[type = 'search']")).sendKeys("United states", Keys.ENTER);

        driver.findElement(By.cssSelector(".content td select[name='zone_code']")).click();
        driver.findElement(By.cssSelector("option[value='AK']")).click();

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        driver.findElement(By.cssSelector("#box-account ul li:nth-child(4) a")).click();

        driver.findElement(By.cssSelector(".content input[name = 'email']")).sendKeys(login);
        driver.findElement(By.cssSelector(".content input[name = 'password']")).sendKeys(password);
        driver.findElement(By.cssSelector(".content button[name = 'login']")).click();

    }
}
