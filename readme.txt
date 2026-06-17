====================================================
  DEMOBLAZE E2E TEST - SERENITY BDD
  Prueba automatizada de flujo de compra
====================================================

DESCRIPCION
-----------
Prueba E2E automatizada para el sitio https://www.demoblaze.com/
que cubre el flujo completo de compra:
  1. Agregar dos productos al carrito
  2. Visualizar el carrito
  3. Completar el formulario de compra
  4. Finalizar y confirmar la compra

TECNOLOGIAS UTILIZADAS
-----------------------
- Java 11+
- Maven 3.6+
- Serenity BDD 4.0.46
- Cucumber 7 (BDD / Gherkin)
- JUnit 4
- Selenium WebDriver (ChromeDriver con autodownload)

PRE-REQUISITOS
--------------
1. Tener instalado Java JDK 11 o superior
   Verificar con: java -version

2. Tener instalado Apache Maven 3.6+
   Verificar con: mvn -version

3. Tener instalado Google Chrome (versión reciente)
   El proyecto usa WebDriverManager (autodownload) para el driver,
   por lo que NO es necesario descargar ChromeDriver manualmente.

4. Conexion a Internet activa (para descargar dependencias Maven
   en la primera ejecucion y para acceder a demoblaze.com)

ESTRUCTURA DEL PROYECTO
------------------------
demoblaze_serenity/
  pom.xml                              -> Configuracion Maven + dependencias
  serenity.properties                  -> (opcional) propiedades adicionales
  readme.txt                           -> Este archivo
  conclusiones.txt                     -> Hallazgos y conclusiones
  src/
    test/
      java/com/demoblaze/
        runners/
          CompraTestRunner.java        -> Runner JUnit + Cucumber
        pages/
          HomePage.java                -> Page Object - pagina principal
          ProductPage.java             -> Page Object - detalle de producto
          CartPage.java                -> Page Object - carrito y compra
        steps/
          CompraSteps.java             -> Step definitions en espanol
      resources/
        features/
          compra_productos.feature     -> Escenario BDD en Gherkin
        serenity.conf                  -> Configuracion de Serenity/WebDriver

INSTRUCCIONES DE EJECUCION
---------------------------

OPCION A: Ejecucion completa con reporte (RECOMENDADA)
------------------------------------------------------
Abrir una terminal en la carpeta raiz del proyecto y ejecutar:

  mvn clean verify

Este comando:
  - Limpia resultados anteriores
  - Compila el proyecto
  - Ejecuta los tests
  - Genera el reporte HTML de Serenity

OPCION B: Solo ejecutar tests (sin reporte agregado)
----------------------------------------------------
  mvn clean test

OPCION C: Modo headless (sin abrir el navegador)
------------------------------------------------
  mvn clean verify -Dheadless.mode=true

VER EL REPORTE
--------------
Despues de ejecutar "mvn clean verify", abrir en el navegador:

  target/site/serenity/index.html

El reporte incluye:
  - Resultado de cada paso del escenario
  - Capturas de pantalla en cada paso
  - Detalles del formulario completado
  - Estado final de la compra

POSIBLES PROBLEMAS Y SOLUCIONES
---------------------------------
1. Error "ChromeDriver not found":
   -> Verificar conexion a internet. El driver se descarga automaticamente.
   -> Si persiste, descargar ChromeDriver manualmente desde:
      https://chromedriver.chromium.org/downloads
      y colocarlo en la variable PATH del sistema.

2. Timeout en la carga del carrito:
   -> DemoBlaze puede ser lento. El test incluye esperas explicitas.
   -> Si falla, volver a ejecutar. El sitio tiene latencia variable.

3. Error de compilacion Java:
   -> Verificar que JAVA_HOME apunta a JDK 11+
   -> Ejecutar: echo %JAVA_HOME%  (Windows)

4. Producto no encontrado:
   -> DemoBlaze pagina sus productos. Si el producto no aparece en la
      primera pagina, el test fallara. Los productos usados son de la
      primera pagina: "Samsung galaxy s6" y "Nokia lumia 1520".

REPOSITORIO
-----------
https://github.com/[tu-usuario]/demoblaze_serenity
