package com.demoblaze.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Runner principal que conecta Cucumber con Serenity BDD.
 * Apunta al feature de compra y los step definitions correspondientes.
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/compra_productos.feature",
        glue = "com.demoblaze.steps",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html"
        },
        tags = "@compra"
)
public class CompraTestRunner {
    // Clase runner — no requiere implementación adicional
}
