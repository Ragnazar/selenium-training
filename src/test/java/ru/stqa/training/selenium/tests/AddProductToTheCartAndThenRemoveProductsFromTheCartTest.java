package ru.stqa.training.selenium.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.training.selenium.app.Application;

public class AddProductToTheCartAndThenRemoveProductsFromTheCartTest extends TestBase{
    Application app = new Application();
    @Test
    public void canAddToTheCartThenRemove() {

        app.addProductsToTheCart();
        app.removeProductsFromTheCart();

        app.quit();

    }
}
