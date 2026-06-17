package com.demoblaze.steps;

import com.demoblaze.pages.CartPage;
import com.demoblaze.pages.HomePage;
import com.demoblaze.pages.ProductPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import net.serenitybdd.core.Serenity;
import org.junit.Assert;

import java.util.Map;

/**
 * Step definitions for the DemoBlaze purchase flow.
 * Serenity auto-injects Page Objects via field injection.
 */
public class CompraSteps {

    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;

    // ======================================================
    // GIVEN
    // ======================================================

    @Given("the user opens the DemoBlaze homepage")
    public void theUserOpensTheDemoBlazeHomepage() {
        homePage.abrirPagina();
        Serenity.recordReportData()
                .withTitle("Page opened")
                .andContents("URL: https://www.demoblaze.com/");
    }

    // ======================================================
    // WHEN
    // ======================================================

    @When("adds the product {string} to the cart")
    public void addsTheProductToTheCart(String productName) {
        homePage.seleccionarProducto(productName);
        productPage.agregarAlCarrito();
        Serenity.recordReportData()
                .withTitle("Product added to cart")
                .andContents("Product: " + productName);
    }

    @When("goes back to home and adds the product {string} to the cart")
    public void goesBackToHomeAndAddsTheProductToTheCart(String productName) {
        homePage.irAlInicio();
        homePage.seleccionarProducto(productName);
        productPage.agregarAlCarrito();
        Serenity.recordReportData()
                .withTitle("Second product added to cart")
                .andContents("Product: " + productName);
    }

    @When("navigates to the shopping cart")
    public void navigatesToTheShoppingCart() {
        homePage.irAlCarrito();
    }

    @When("completes the purchase form with the following data:")
    public void completesThePurchaseFormWithTheFollowingData(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        cartPage.abrirFormularioCompra();
        cartPage.llenarFormulario(
                data.get("name"),
                data.get("country"),
                data.get("city"),
                data.get("card"),
                data.get("month"),
                data.get("year")
        );

        Serenity.recordReportData()
                .withTitle("Purchase form completed")
                .andContents("Name: " + data.get("name") + " | Country: " + data.get("country"));
    }

    @When("confirms the purchase")
    public void confirmsThePurchase() {
        cartPage.confirmarCompra();
    }

    // ======================================================
    // THEN
    // ======================================================

    @Then("the cart should display {int} products")
    public void theCartShouldDisplayProducts(int expectedCount) {
        int actualCount = cartPage.obtenerCantidadProductos();
        Serenity.recordReportData()
                .withTitle("Cart verification")
                .andContents("Expected: " + expectedCount + " | Found: " + actualCount);
        Assert.assertEquals(
                "Cart should have " + expectedCount + " products but has " + actualCount,
                expectedCount,
                actualCount
        );
    }

    @Then("the purchase should be completed successfully")
    public void thePurchaseShouldBeCompletedSuccessfully() {
        String confirmationText = cartPage.obtenerTextoConfirmacion();
        Serenity.recordReportData()
                .withTitle("Purchase confirmation")
                .andContents("Message received: " + confirmationText);
        Assert.assertTrue(
                "Expected purchase confirmation but got: " + confirmationText,
                confirmationText.toLowerCase().contains("thank you")
        );
        cartPage.cerrarConfirmacion();
    }
}
