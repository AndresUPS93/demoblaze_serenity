package com.demoblaze.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object para la página principal de DemoBlaze.
 * Los productos en el catálogo usan el selector: a.hrefch
 * El carrito está en: #cartur
 */
public class HomePage extends PageObject {

    private static final String URL = "https://www.demoblaze.com/";

    /**
     * Abre la página principal y espera a que los productos carguen.
     */
    public void abrirPagina() {
        getDriver().get(URL);
        new WebDriverWait(getDriver(), Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.hrefch")));
    }

    /**
     * Busca un producto por nombre en el catálogo y hace click sobre él.
     * Usa CSS selector correcto con By.cssSelector para evitar que
     * Serenity lo interprete como XPath.
     */
    public void seleccionarProducto(String nombreProducto) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.hrefch")));

        List<WebElement> productos = getDriver().findElements(By.cssSelector("a.hrefch"));
        for (WebElement producto : productos) {
            if (producto.getText().trim().equalsIgnoreCase(nombreProducto.trim())) {
                producto.click();
                return;
            }
        }
        throw new RuntimeException("Product not found in catalog: " + nombreProducto);
    }

    /**
     * Navega al carrito de compras.
     */
    public void irAlCarrito() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement cartLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("cartur"))
        );
        cartLink.click();
    }

    /**
     * Regresa a la página principal y espera a que los productos carguen.
     */
    public void irAlInicio() {
        getDriver().get(URL);
        new WebDriverWait(getDriver(), Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.hrefch")));
    }
}
