## Ejercicio

Como hemos visto, el manejo de la mayoría de las operaciones para acceder una base de datos arroja excepciones chequeadas, principalmente `SQLException`. Esto hace que el acceso a los datos requiera de múltiples bloques `try-catch-finally`. La idea detrás del ejercicio es lograr una librería utilitaria que facilite el manejo de las excepciones en el acceso a datos.

Realizar la o las clases que se consideren necesarias; crear la o las excepciones o jerarquía de excepciones que se considere necesaria. Se pide al menos manejar las situaciones de apertura y cierre de conexiones, manejo de transacciones y ejecución de consultas.

Por otro lado, es importante realizar las clases u operaciones necesarias para evitar repetir código en cada operación contra la base de datos. Vimos hasta aquí, que cualquiera sea la operación, siempre se deben realizar los mismos pasos. Esta es una buena oportunidad para aplicar algunos de los conceptos básicos de orientación a objetos y codificar código reutilizable para el acceso a datos.

**Aclaración:** no valerse exclusivamente de `RuntimeException`. No usar `@SneakyThrows` dado que no se ve en la materia.


Aquí tienes un resumen de los puntos necesarios a realizar para el ejercicio:

1. **Crear una librería utilitaria** para facilitar el manejo de excepciones en el acceso a datos.
2. **Desarrollar las clases necesarias** para manejar excepciones chequeadas como `SQLException`.
3. **Implementar una jerarquía de excepciones** adecuada para las operaciones de acceso a datos.
4. **Manejar situaciones de apertura y cierre de conexiones**, manejo de transacciones y ejecución de consultas.
5. **Evitar la repetición de código** en cada operación contra la base de datos mediante la creación de clases u operaciones reutilizables.
6. **Aplicar conceptos básicos de orientación a objetos** para codificar código reutilizable.
7. **No usar exclusivamente `RuntimeException`** y evitar el uso de `@SneakyThrows`.
