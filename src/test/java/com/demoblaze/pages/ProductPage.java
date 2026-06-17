package com.demoblaze.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object para la página de detalle de un producto.
 * Permite agregar el producto al carrito y manejar el alert de confirmación.
 */
public class ProductPage extends PageObject {

    /**
     * Hace click en el botón "Add to cart" y acepta el alert de confirmación.
     * DemoBlaze muestra un alert nativo del navegador al agregar al carrito.
     */
    public void agregarAlCarrito() {
        // Esperar a que el botón "Add to cart" aparezca y sea clickeable
        waitFor(ExpectedConditions.elementToBeClickable(By.cssSelector("a.btn.btn-success.btn-lg")));
        WebElementFacade addToCartButton = findBy("a.btn.btn-success.btn-lg");
        addToCartButton.click();

        // Esperar y aceptar el alert nativo del navegador
        try {
            waitFor(ExpectedConditions.alertIsPresent());
            getDriver().switchTo().alert().accept();
        } catch (Exception e) {
            System.out.println("No se detectó alert después de agregar al carrito: " + e.getMessage());
        }
    }
}
