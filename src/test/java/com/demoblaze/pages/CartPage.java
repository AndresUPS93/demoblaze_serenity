package com.demoblaze.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object para el carrito de compras y el flujo de pago en DemoBlaze.
 *
 * Selectores verificados en la página:
 *   Tabla de items:     #tbodyid tr
 *   Botón Place Order:  button[data-target="#orderModal"]
 *   Campos del modal:   #name, #country, #city, #card, #month, #year
 *   Botón Purchase:     #orderModal button.btn-primary  (texto "Purchase")
 *   Confirmación:       .sweet-alert h2
 *   Botón OK:           .sweet-alert button.confirm
 */
public class CartPage extends PageObject {

    /**
     * Espera a que los ítems del carrito carguen y devuelve la cantidad.
     * El carrito carga de forma asíncrona vía API, puede tardar varios segundos.
     */
    public int obtenerCantidadProductos() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        try {
            // Esperar al menos una fila en la tabla del carrito
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                    By.cssSelector("#tbodyid tr"), 0
            ));
            // Pausa adicional para que cargue el segundo ítem
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Timeout waiting for cart items: " + e.getMessage());
        }
        List<WebElement> rows = getDriver().findElements(By.cssSelector("#tbodyid tr"));
        return rows.size();
    }

    /**
     * Hace click en "Place Order" y espera a que el modal sea visible.
     */
    public void abrirFormularioCompra() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement placeOrder = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button[data-target='#orderModal']")
                )
        );
        placeOrder.click();

        // Esperar a que el modal esté visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));
    }

    /**
     * Rellena todos los campos del formulario de compra.
     */
    public void llenarFormulario(String nombre, String pais, String ciudad,
                                  String tarjeta, String mes, String anio) {
        fillField("name",    nombre);
        fillField("country", pais);
        fillField("city",    ciudad);
        fillField("card",    tarjeta);
        fillField("month",   mes);
        fillField("year",    anio);
    }

    /**
     * Hace click en el botón "Purchase" dentro del modal.
     */
    public void confirmarCompra() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        // El botón Purchase está dentro del modal #orderModal
        WebElement purchaseBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@id='orderModal']//button[contains(text(),'Purchase')]")
                )
        );
        purchaseBtn.click();
    }

    /**
     * Espera el popup SweetAlert de confirmación y retorna el título.
     */
    public String obtenerTextoConfirmacion() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        try {
            WebElement titulo = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sweet-alert h2"))
            );
            return titulo.getText();
        } catch (Exception e) {
            System.out.println("Timeout waiting for confirmation popup: " + e.getMessage());
            return "";
        }
    }

    /**
     * Cierra el popup de confirmación con el botón OK.
     */
    public void cerrarConfirmacion() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement okBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".sweet-alert button.confirm")
                )
        );
        okBtn.click();
    }

    // --- Helper privado ---
    private void fillField(String fieldId, String value) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(fieldId))
        );
        field.clear();
        field.sendKeys(value);
    }
}
