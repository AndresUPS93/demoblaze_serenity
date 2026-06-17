package com.demoblaze.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page Object para la página del carrito de compras.
 * Cubre: verificación de ítems, formulario de compra y confirmación.
 */
public class CartPage extends PageObject {

    /**
     * Espera a que el carrito termine de cargar y retorna el número de ítems.
     * El carrito de DemoBlaze puede tardar unos segundos en renderizar.
     *
     * @return cantidad de filas (productos) en la tabla del carrito
     */
    public int obtenerCantidadProductos() {
        try {
            // Espera hasta 5 segundos a que aparezca al menos 1 producto
            waitFor(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tbodyid tr")));
            Thread.sleep(2000); // pausa adicional por si el segundo ítem tarda en cargar
        } catch (Exception e) {
            System.out.println("Timeout esperando productos en el carrito: " + e.getMessage());
        }
        List<WebElement> filas = getDriver().findElements(By.cssSelector("#tbodyid tr"));
        return filas.size();
    }

    /**
     * Hace click en el botón "Place Order" para abrir el modal de compra.
     */
    public void abrirFormularioCompra() {
        WebElementFacade placeOrder = findBy("button[data-target='#orderModal']");
        waitFor(placeOrder).waitUntilClickable();
        placeOrder.click();
        // Esperar a que el modal de orden esté visible
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));
    }

    /**
     * Llena todos los campos del formulario de compra en el modal.
     */
    public void llenarFormulario(String nombre, String pais, String ciudad,
                                  String tarjeta, String mes, String anio) {
        typeInField("name", nombre);
        typeInField("country", pais);
        typeInField("city", ciudad);
        typeInField("card", tarjeta);
        typeInField("month", mes);
        typeInField("year", anio);
    }

    /**
     * Hace click en el botón "Purchase" para confirmar la compra.
     */
    public void confirmarCompra() {
        WebElementFacade purchaseBtn = findBy("#orderModal .btn-primary");
        waitFor(purchaseBtn).waitUntilClickable();
        purchaseBtn.click();
    }

    /**
     * Espera el mensaje de confirmación y retorna su título.
     * DemoBlaze usa la librería SweetAlert para mostrar la confirmación.
     *
     * @return texto del título del popup de confirmación
     */
    public String obtenerTextoConfirmacion() {
        try {
            waitFor(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sweet-alert h2")));
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Timeout esperando confirmación de compra: " + e.getMessage());
        }
        WebElementFacade titulo = findBy(".sweet-alert h2");
        return titulo.getText();
    }

    /**
     * Hace click en el botón OK del popup de confirmación.
     */
    public void cerrarConfirmacion() {
        WebElementFacade okBtn = findBy(".sweet-alert .confirm");
        waitFor(okBtn).waitUntilClickable();
        okBtn.click();
    }

    // --- Helper privado ---
    private void typeInField(String fieldId, String value) {
        WebElementFacade field = findBy("#" + fieldId);
        waitFor(field).waitUntilVisible();
        field.clear();
        field.type(value);
    }
}
