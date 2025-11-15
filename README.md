# ProyectoFinal_ProgramacionII
🧩 Descripción del proyecto

Este es un sistema de gestión de autores y libros desarrollado en Java, utilizando el patrón DAO (Data Access Object) para separar la lógica de negocio de la lógica de acceso a datos.

El proyecto está conectado a una base de datos MySQL y permite:

-Registrar autores

-Registrar libros

-Listarlos

-Actualizarlos

-Eliminarlos

-Mostrar relaciones (autor → libros)

🛠 Tecnologías utilizadas

-Java

-MySQL

-JDBC

-Patrón DAO

-Arquitectura MVC

-IDE: NetBeans / IntelliJ (dependiendo de tu caso)

-Swing para la interfaz gráfica

📂 Estructura del proyecto
/src
   /dao
   /model
   /gui
   /utils
/resources
database.sql
README.md


🚀 Cómo ejecutar el proyecto
1. Importar la base de datos

En MySQL Workbench o phpMyAdmin:

Abrir database.sql

Ejecutar el script

2. Configurar conexión (si aplica)

Modificar tu archivo Conexion.java:
private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
private static final String USER = "root";
private static final String PASS = "tu_contraseña";

3. Ejecutar el programa:
Ejecutar la clase:
-Main.java
o abrir la GUI desde:
-MenuPrincipal.java

👨‍🏫 Notas para el docente

El video muestra el funcionamiento completo del programa, incluyendo:
-Inserción en tablas
-Consulta desde MySQL
-Relación entre tablas
-Validaciones
-Interfaz gráfica funcional
