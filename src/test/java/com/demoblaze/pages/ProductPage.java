package com.demoblaze.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object para la página de detalle de un producto en DemoBlaze.
 *
 * Selector del botón "Add to cart":
 *   <a onclick="addToCart(1)" class="btn btn-success btn-lg">Add to cart</a>
 * Se localiza por texto visible para mayor robustez.
 */
public class ProductPage extends PageObject {

    /**
     * Hace click en el botón "Add to cart" y acepta el alert nativo del navegador.
     */
    public void agregarAlCarrito() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));

        // Localizar el botón por su texto — más robusto que por clase
        By addToCartLocator = By.xpath("//a[contains(text(),'Add to cart')]");

        // Esperar a que el botón esté presente en el DOM y sea clickeable
        WebElement addToCartButton = wait.until(
                ExpectedConditions.elementToBeClickable(addToCartLocator)
        );
        addToCartButton.click();

        // DemoBlaze usa un alert nativo del navegador al agregar al carrito
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            getDriver().switchTo().alert().accept();
        } catch (Exception e) {
            System.out.println("No alert detected after adding to cart: " + e.getMessage());
        }
    }
}
