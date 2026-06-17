# language: es
@compra
Feature: Flujo de compra en DemoBlaze
  Como usuario de DemoBlaze
  Quiero agregar productos al carrito y completar una compra
  Para verificar que el flujo E2E funciona correctamente

  Scenario: Compra exitosa de dos productos
    Given el usuario ingresa a la pagina de DemoBlaze
    When agrega el producto "Samsung galaxy s6" al carrito
    And regresa al inicio y agrega el producto "Nokia lumia 1520" al carrito
    And navega al carrito de compras
    Then el carrito debe mostrar 2 productos
    When completa el formulario de compra con los datos:
      | nombre    | Juan Perez     |
      | tarjeta   | 1234567890123456|
      | mes       | 6              |
      | anio      | 2026           |
      | pais      | Colombia       |
      | ciudad    | Bogota         |
    And confirma la compra
    Then la compra debe completarse exitosamente
