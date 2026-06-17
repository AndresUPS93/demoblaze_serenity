package com.demoblaze.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page Object para la página principal de DemoBlaze.
 * Contiene los locators y métodos para interactuar con el catálogo de productos.
 */
public class HomePage extends PageObject {

    private static final String URL = "https://www.demoblaze.com/";

    /**
     * Navega a la URL principal de DemoBlaze.
     */
    public void abrirPagina() {
        getDriver().get(URL);
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.hrefch")));
    }

    /**
     * Hace click en un producto buscándolo por nombre exacto (ignora mayúsculas).
     *
     * @param nombreProducto nombre del producto a seleccionar
     */
    public void seleccionarProducto(String nombreProducto) {
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.hrefch")));

        List<WebElement> productos = getDriver().findElements(By.cssSelector("a.hrefch"));
        for (WebElement producto : productos) {
            if (producto.getText().trim().equalsIgnoreCase(nombreProducto.trim())) {
                producto.click();
                return;
            }
        }
        throw new RuntimeException("Producto no encontrado en la lista: " + nombreProducto);
    }

    /**
     * Hace click en el enlace del carrito en la barra de navegación.
     */
    public void irAlCarrito() {
        WebElementFacade cartIcon = findBy("#cartur");
        waitFor(cartIcon).waitUntilClickable();
        cartIcon.click();
    }

    /**
     * Regresa a la página de inicio.
     */
    public void irAlInicio() {
        getDriver().get(URL);
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.hrefch")));
    }
}
