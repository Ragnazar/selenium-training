package ru.stqa.training.selenium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class LoginTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void setUp() {
        FirefoxBinary binary = new FirefoxBinary(new File("C:\\Program Files\\Firefox Nightly\\firefox.exe"));
        driver = new FirefoxDriver(new FirefoxOptions().setBinary(binary));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 5);
    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void adminUserShouldLogIn(){
        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(presenceOfElementLocated(By.className("logotype")));
    }
}
