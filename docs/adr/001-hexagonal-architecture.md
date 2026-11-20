# ADR-001: Arquitectura hexagonal en Smart Help Desk

## Estado
Aceptada.

## Contexto
Smart Help Desk es una API REST desarrollada con Java 21 y Spring Boot para gestionar solicitudes de soporte técnico.

El sistema necesita mantener separadas las reglas de negocio, los casos de uso y los detalles de infraestructura.

También debe permitir cambiar tecnologías de persistencia sin modificar el comportamiento del dominio.


## Decisión
Utilizar arquitectura hexagonal basada en puertos y adaptadores.

La solución se organiza en tres capas principales:
Dominio: entidades, objetos de valor, reglas de negocio y contratos de salida.
Aplicación: casos de uso, comandos, consultas y coordinación de operaciones.
Infraestructura: controladores REST, configuraciones de Spring Boot y adaptadores de persistencia.

Los controladores dependen de interfaces de casos de uso.

Los servicios de aplicación dependen de puertos, no de repositorios concretos.

Los adaptadores de infraestructura implementan los contratos definidos por el núcleo de la aplicación.

La inyección de dependencias se realiza mediante constructores.

Los casos de uso de tickets se configuran explícitamente mediante TicketUseCaseConfig, utilizando @Configuration y @Bean.
Alternativas consideradas
1. Arquitectura tradicional por capas
   Colocar controladores, servicios y repositorios con dependencias directas entre implementaciones.
   Desventaja: mayor acoplamiento con la infraestructura.
2. Arquitectura hexagonal
   Separar el núcleo de negocio de los detalles técnicos mediante puertos y adaptadores.
   Ventaja: facilita las pruebas, el mantenimiento y la sustitución de implementaciones.
   Consecuencias positivas
   El dominio no depende directamente de Spring Boot.

Las reglas de negocio permanecen centralizadas.

Los casos de uso pueden probarse utilizando repositorios en memoria.

Es posible sustituir un adaptador de persistencia manteniendo sus contratos.

Se facilita la aplicación de los principios SOLID.

Se mejora la separación de responsabilidades.
Consecuencias negativas
Se incrementa el número de interfaces y clases.

Es necesario mantener los mapeos entre modelos.

La configuración de dependencias requiere mayor atención.

Los cambios en contratos pueden afectar varias implementaciones.
Ejemplo práctico
Para consultar un ticket:

```text
TicketController
      |
      v
GetTicketByIdUseCase
      |
      v
GetTicketByIdService
      |
      v
TicketRepositoryPort
      |
      v
JpaTicketRepositoryAdapter
      |
      v
H2 Database
```
La lógica de consulta depende de un puerto de salida, no directamente de H2.
Principios SOLID relacionados
SRP — Responsabilidad única: cada caso de uso representa una operación concreta.
ISP — Segregación de interfaces: los puertos de entrada se diseñan para necesidades específicas.
DIP — Inversión de dependencias: los servicios dependen de abstracciones y los adaptadores concretos implementan los puertos.
Validación
Se verificó que:

El dominio no contiene importaciones directas de Spring Boot.

El dominio no importa clases de infraestructura.

Los controladores utilizan interfaces de casos de uso.

Los adaptadores JPA implementan puertos de salida.

Las pruebas automatizadas finalizan con BUILD SUCCESSFUL.

Spring Boot inicia correctamente después del refactor.

El endpoint GET /api/tickets/{id} responde 200 OK.
Resultado
Se mantiene una estructura hexagonal con dependencias orientadas al núcleo de negocio y configuración explícita de los casos de uso de tickets.

Esta decisión constituye la base para incorporar nuevas funcionalidades sin acoplar la lógica de negocio a tecnologías específicas.
 