## Clon de Postman: Cliente REST para Java

### Descripción
Este proyecto tiene como objetivo crear un cliente HTTP básico, similar a Postman, para realizar solicitudes REST. Utilizará la librería apache-commons-http para manejar las peticiones HTTP y permitirá a los usuarios realizar solicitudes GET, POST, PUT y DELETE.

### Funcionalidades básicas
* **Ejecución de solicitudes HTTP:** Permite ingresar una URL y ejecutar los métodos HTTP estándar (GET, POST, PUT, DELETE).
* **Parámetros de consulta:** Los parámetros de consulta (query string) se pueden ingresar manualmente en la URL.
* **Headers:** Permite agregar headers personalizados a las solicitudes.
* **Cuerpo de la solicitud:** Permite ingresar un cuerpo para las solicitudes.
* **Visualización de la respuesta:** Muestra la respuesta del servidor en una caja de texto.
* **Gestión de favoritos:** Permite guardar y cargar URLs y parámetros favoritos.

### Funcionalidades adicionales (opcionales)
* **Grilla para headers:** Presenta los headers en una grilla para una mejor organización.
* **Tabla de parámetros de consulta:** Permite agregar parámetros de consulta en una tabla con claves y valores.
* **Formateado de respuestas:** Formatea las respuestas JSON y XML para una mejor visualización.
* **Historial de solicitudes:** Guarda un historial de las solicitudes recientes para facilitar la repetición.
* **Autocompletado de URLs:** Sugiere URLs basadas en el historial de solicitudes.
* **Sincronización en tiempo real:** Actualiza la URL y la tabla de parámetros de forma dinámica al realizar cambios en cualquiera de ellos.

### Tecnologías utilizadas
* **Java:** Lenguaje de programación principal.
* **apache-commons-http:** Librería para realizar solicitudes HTTP.
* **Swing:** Framework de GUI para crear la interfaz de usuario.
* **Base de datos:** Para almacenar los favoritos (SQLite, H2, etc.)

### Estructura del proyecto
* **src/main/java:** Contiene el código fuente de la aplicación.
* **src/test/java:** Contiene las pruebas unitarias.
* **resources:** Contiene archivos de configuración y recursos.
