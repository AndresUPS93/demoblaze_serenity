package com.demoblaze.steps;

import com.demoblaze.pages.CartPage;
import com.demoblaze.pages.HomePage;
import com.demoblaze.pages.ProductPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.*;
import net.serenitybdd.core.Serenity;
import org.junit.Assert;

import java.util.Map;

/**
 * Step definitions en español para el flujo de compra en DemoBlaze.
 * Conecta los pasos del feature con la lógica de los Page Objects.
 */
public class CompraSteps {

    // Serenity inyecta los Page Objects automáticamente
    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;

    // ======================================================
    // GIVEN
    // ======================================================

    @Dado("el usuario ingresa a la pagina de DemoBlaze")
    public void elUsuarioIngresaALaPaginaDeDemoBlaze() {
        homePage.abrirPagina();
        Serenity.recordReportData()
                .withTitle("Pagina abierta")
                .andContents("URL: https://www.demoblaze.com/");
    }

    // ======================================================
    // WHEN
    // ======================================================

    @Cuando("agrega el producto {string} al carrito")
    public void agregaElProductoAlCarrito(String nombreProducto) {
        homePage.seleccionarProducto(nombreProducto);
        productPage.agregarAlCarrito();
        Serenity.recordReportData()
                .withTitle("Producto agregado")
                .andContents("Producto: " + nombreProducto);
    }

    @Cuando("regresa al inicio y agrega el producto {string} al carrito")
    public void regresaAlInicioYAgregaElProductoAlCarrito(String nombreProducto) {
        homePage.irAlInicio();
        homePage.seleccionarProducto(nombreProducto);
        productPage.agregarAlCarrito();
        Serenity.recordReportData()
                .withTitle("Segundo producto agregado")
                .andContents("Producto: " + nombreProducto);
    }

    @Cuando("navega al carrito de compras")
    public void navegaAlCarritoDeCompras() {
        homePage.irAlCarrito();
    }

    @Cuando("completa el formulario de compra con los datos:")
    public void completaElFormularioDeCompraConLosDatos(DataTable dataTable) {
        Map<String, String> datos = dataTable.asMap(String.class, String.class);

        cartPage.abrirFormularioCompra();
        cartPage.llenarFormulario(
                datos.get("nombre"),
                datos.get("pais"),
                datos.get("ciudad"),
                datos.get("tarjeta"),
                datos.get("mes"),
                datos.get("anio")
        );

        Serenity.recordReportData()
                .withTitle("Formulario completado")
                .andContents("Nombre: " + datos.get("nombre") + " | País: " + datos.get("pais"));
    }

    @Cuando("confirma la compra")
    public void confirmaLaCompra() {
        cartPage.confirmarCompra();
    }

    // ======================================================
    // THEN
    // ======================================================

    @Entonces("el carrito debe mostrar {int} productos")
    public void elCarritoDebeMostrarProductos(int cantidadEsperada) {
        int cantidadReal = cartPage.obtenerCantidadProductos();
        Serenity.recordReportData()
                .withTitle("Verificacion del carrito")
                .andContents("Productos esperados: " + cantidadEsperada + " | Productos encontrados: " + cantidadReal);
        Assert.assertEquals(
                "El carrito debería tener " + cantidadEsperada + " productos, pero tiene " + cantidadReal,
                cantidadEsperada,
                cantidadReal
        );
    }

    @Entonces("la compra debe completarse exitosamente")
    public void laCompraDebeCompletarseExitosamente() {
        String textoConfirmacion = cartPage.obtenerTextoConfirmacion();
        Serenity.recordReportData()
                .withTitle("Confirmacion de compra")
                .andContents("Mensaje recibido: " + textoConfirmacion);
        Assert.assertTrue(
                "Se esperaba mensaje de confirmacion de compra, pero se obtuvo: " + textoConfirmacion,
                textoConfirmacion.toLowerCase().contains("thank you")
        );
        cartPage.cerrarConfirmacion();
    }
}
