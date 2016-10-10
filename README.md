E1PBajoNivel
===================

Proyecto realizado según las especificaciones del examen del primer parcial para la materia de Diseño de Bajo nivel de la Universidad Autónoma de Chihuahua

El cual consiste en la generación de una base para un  **Dao (Data Access Object)** el cual se conecte a una base de datos que tenga un esquema de entidad relación y mediante **Java** pueda mapear los datos a modelos consistentes de una aplicación.

Esta formado por una aplicación de java en maven.

Para esto dentro de la utilización de la base se tienen que tomar en cuenta las siguientes consideraciones:

Base de datos
-------------

> **Requisitos:**

> - Tiene que estar basada en un modelo entidad-relacion, tomando en cuenta que cada entidad sera un modelo dentro de la aplicación de java.
> - Los nombres tiene que estar en snake_case según el modelo entidad-relacion
> - Tiene que tomarse en cuenta que la cuestión para crear objetos esta basada en un id autoincremental, si necesita un modelo que no se maneje de esa manera tiene la libertad de modificar la base.


Clases o Modelos (Java)
-------------

> **Requisitos:**

> - Todo modelo nuevo que se crea tiene que extender de la clase Model que se encuentra en el paquete mx.uach.dao.models.genericos e implementar la interface mx.uach.dao.DataBaseUtils.java, para que los métodos abstractos se sobre escriban según las necesidades.
> - Cada modelo tiene que tener una constante que sera el nombre de la tabla que modela, el cual sera retornado en el método getTableName().
> - El id de cada modelo viene por default por herencia, todos los demás atributos tienen que estar encapsulados y con sus respectivos métodos de acceso y asignación (Getters y Setters).
> Se tiene que tener el constructor vacío en cada clase.


Conexión a la base de datos
-------------

> **Requisitos:**

> - Configurar el usuario y password dentro de la clase Conexion.java, así como el nombre de la base de datos.




