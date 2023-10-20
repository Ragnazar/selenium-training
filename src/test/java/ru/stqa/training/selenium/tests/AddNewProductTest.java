package ru.stqa.training.selenium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddNewProductTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();


    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void addNewProduct() {
        String name = "White Duck" + (int) (Math.random() * 1000);
        driver.findElement(By.cssSelector("#content .button:nth-of-type(2)")).click();

        driver.findElement(By.cssSelector("#tab-general input[value='1']")).click();
        driver.findElement(By.cssSelector("#tab-general input[name='name[en]']")).sendKeys(name);
        driver.findElement(By.cssSelector("#tab-general input[name='code']")).sendKeys("RD005");

        driver.findElement(By.cssSelector("#tab-general [class='input-wrapper'] tr:nth-of-type(2) td")).click();
        driver.findElement(By.cssSelector("#tab-general [class='input-wrapper'] tr:nth-of-type(1) td")).click();

        driver.findElement(By.cssSelector("#tab-general [class='input-wrapper'] tr:nth-of-type(4) td")).click();

        driver.findElement(By.cssSelector("#tab-general input[name='quantity']")).sendKeys(Keys.DELETE, "10");

        String filePath = System.getProperty("user.dir") + "/src/test/java/ru/stqa/training/selenium/utils/white_duck.png";
        driver.findElement(By.cssSelector("#tab-general input[name='new_images[]']"))
                .sendKeys(filePath);


        DateFormat dateFormat = new SimpleDateFormat("d.MM.yyyy");
        String date = dateFormat.format(new Date());
        driver.findElement(By.cssSelector("#tab-general input[name='date_valid_from']")).sendKeys(date);
        driver.findElement(By.cssSelector("#tab-general input[name='date_valid_to']")).sendKeys(date);

        driver.findElement(By.cssSelector("div.tabs ul.index li:nth-of-type(2)")).click();

        driver.findElement(By.cssSelector("#tab-information [name='manufacturer_id']")).click();
        driver.findElement(By.cssSelector("#tab-information option:nth-child(2)")).click();

        driver.findElement(By.cssSelector("#tab-information [name= 'keywords']")).sendKeys("blue-duck-p-5");
        driver.findElement(By.cssSelector("#tab-information input[name='short_description[en]']")).sendKeys("blue-duck-p-5");

        driver.findElement(By.cssSelector("#tab-information div[class='trumbowyg-editor']")).sendKeys("Some description");
        driver.findElement(By.cssSelector("#tab-information input[name='head_title[en]']")).sendKeys("Some title");
        driver.findElement(By.cssSelector("#tab-information input[name='meta_description[en]']")).sendKeys("Some meta");

        driver.findElement(By.cssSelector("div.tabs ul.index li:nth-of-type(4)")).click();

        driver.findElement(By.cssSelector("#tab-prices input[name='purchase_price']")).sendKeys(Keys.DELETE, "10");
        driver.findElement(By.cssSelector("#tab-prices select:nth-of-type(1)")).click();
        driver.findElement(By.cssSelector("#tab-prices select option:nth-child(2)")).click();
        driver.findElement(By.cssSelector("#tab-prices input[name='gross_prices[USD]']")).sendKeys(Keys.DELETE, "10");

        driver.findElement(By.cssSelector("#content button[name='save']")).click();

        List<WebElement> list = driver.findElements(By.cssSelector("#content tr[class='row'] td:nth-child(3)"));
        List<String> names = new ArrayList<>();
        for (WebElement element : list) {
            names.add(element.getText());
        }
        boolean check = names.contains(name);
        assertTrue(check);

        //Deleting the duck from products list
//        driver.findElement(By.cssSelector("#content tr:nth-child(9) td:nth-child(1)")).click();
//        driver.findElement(By.cssSelector("#content span[class='button-set'] button[name='delete']")).click();
//        driver.switchTo().alert().accept();

    }
}
