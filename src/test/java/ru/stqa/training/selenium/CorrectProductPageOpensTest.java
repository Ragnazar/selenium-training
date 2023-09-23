package ru.stqa.training.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CorrectProductPageOpensTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("http://localhost/litecart/en/");


    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void namesOnPagesMatches() {
        String expectedName = driver
                .findElement(By.cssSelector("#box-campaigns .product .name"))
                .getText();
        driver.findElement(By.cssSelector("#box-campaigns .product .link")).click();
        String actualName = driver.findElement(By.cssSelector(".content h1")).getText();

        assertEquals(expectedName, actualName);
    }


    @Test
    public void pricesOnPagesMatches() {
        String expectedRegularPrice = driver
                .findElement(By.cssSelector("#box-campaigns [class = 'regular-price']"))
                .getText();
        String expectedCampaignPrice = driver
                .findElement(By.cssSelector("#box-campaigns [class = 'campaign-price']"))
                .getText();

        driver.findElement(By.cssSelector("#box-campaigns .product .link")).click();
        String actualRegularPrice = driver
                .findElement(By.cssSelector(".content .information [class = 'regular-price']"))
                .getText();
        String actualCampaignPrice = driver
                .findElement(By.cssSelector(".content .information [class = 'campaign-price']"))
                .getText();

        assertEquals(expectedCampaignPrice, actualCampaignPrice);
        assertEquals(expectedRegularPrice, actualRegularPrice);
    }


    @Test
    public void regularPriceHaveExpectedStyle() {
        String expectedRegularPriceColor = driver
                .findElement(By.cssSelector("#box-campaigns [class = 'regular-price']"))
                .getCssValue("color");

        boolean check = false;
        for (int i = 0; i < 3; i++) {
            if (getRgbColorSet(expectedRegularPriceColor)[0] == getRgbColorSet(expectedRegularPriceColor)[i]) {
                check = true;
            }
        }

        assertTrue(check, "The color of regular price is not grey as expected");
        assertEquals("s",
                driver
                        .findElement(By.cssSelector("[class = 'regular-price']"))
                        .getTagName(),
                "Regular price is not strikethrough as expected");

        driver.findElement(By.cssSelector("#box-campaigns .product .link")).click();
        String actualRegularPriceColor = driver.findElement(By.cssSelector(
                ".content .information [class = 'regular-price']")).getCssValue("color");

        check = false;
        for (int i = 0; i < 3; i++) {
            if (getRgbColorSet(actualRegularPriceColor)[0] == getRgbColorSet(actualRegularPriceColor)[i]) {
                check = true;
            }
        }

        assertTrue(check, "The color of regular price is not grey as expected");
        assertEquals("s",
                driver
                        .findElement(By.cssSelector(".content .information [class = 'regular-price']"))
                        .getTagName(),
                "Regular price is not strikethrough as expected");

    }

    @Test
    public void campaignPriceHaveExpectedStyle() {
        String expectedCampaignPriceColor = driver
                .findElement(By.cssSelector("#box-campaigns [class = 'campaign-price']"))
                .getCssValue("color");

        boolean check = false;
        for (int i = 1; i < 3; i++) {
            if (0 == getRgbColorSet(expectedCampaignPriceColor)[i]) {
                check = true;
            }
        }

        assertTrue(check, "The color of campaign price is not red as expected");
        assertEquals("strong",
                driver.findElement(By.cssSelector("[class = 'campaign-price']")).getTagName(),
                "Campaign price has no strong emphasis as expected");

        driver.findElement(By.cssSelector("#box-campaigns .product .link")).click();
        String actualCampaignPriceColor = driver.findElement(By.cssSelector(
                ".content .information [class = 'campaign-price']")).getCssValue("color");

        check = false;
        for (int i = 1; i < 3; i++) {
            if (0 == getRgbColorSet(actualCampaignPriceColor)[i]) {
                check = true;
            }
        }

        assertTrue(check, "The color of campaign price is not red as expected");
        assertEquals("strong",
                driver
                        .findElement(By.cssSelector(".content .information [class = 'campaign-price']"))
                        .getTagName(),
                "Campaign price has no strong emphasis as expected");

    }

    @Test
    public void campaignPriceIsHigherThanRegular() {
        Dimension expectedCampaignPriceSize = driver
                .findElement(By.cssSelector("#box-campaigns [class = 'campaign-price']"))
                .getSize();
        Dimension expectedRegularPriceSize = driver
                .findElement(By.cssSelector("#box-campaigns [class = 'regular-price']"))
                .getSize();
        boolean check = expectedCampaignPriceSize.height > expectedRegularPriceSize.height ||
                        expectedCampaignPriceSize.width > expectedRegularPriceSize.width;

        assertTrue(check, "The size of Campaign price is not bigger than Regular price on main page");

        driver.findElement(By.cssSelector("#box-campaigns .product .link")).click();

        Dimension actualCampaignPriceSize = driver
                .findElement(By.cssSelector(".content .information [class = 'campaign-price']"))
                .getSize();
        Dimension actualRegularPriceSize = driver
                .findElement(By.cssSelector(".content .information [class = 'regular-price']"))
                .getSize();
        check = actualCampaignPriceSize.height > actualRegularPriceSize.height ||
                actualCampaignPriceSize.width > actualRegularPriceSize.width;

        assertTrue(check, "The size of Campaign price is not bigger than Regular price on product page");
    }

    private int[] getRgbColorSet(String color) {
        int[] colorValue = new int[3];
        String[] colors = color.substring(5).split("[,)]");
        for (int i = 0; i < 3; i++) {
            colorValue[i] = Integer.parseInt(colors[i].trim());
        }
        return colorValue;
    }
}
