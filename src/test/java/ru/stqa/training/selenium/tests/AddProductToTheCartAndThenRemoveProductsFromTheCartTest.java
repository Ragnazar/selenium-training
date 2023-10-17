package ru.stqa.training.selenium.tests;

import org.junit.jupiter.api.Test;

public class AddProductToTheCartAndThenRemoveProductsFromTheCartTest extends TestBase{
    @Test
    public void canAddToTheCartThenRemove() {

        app.addProductsToTheCart();
        app.removeProductsFromTheCart();

        app.quit();

    }
}
