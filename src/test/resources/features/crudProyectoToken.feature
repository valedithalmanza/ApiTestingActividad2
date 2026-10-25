
@Regression
Feature: Gestion de proyectos en Todo.ly utilizando TOKEN

  Scenario: Crear usuario y realizar CRUD de un proyecto
    Given creo un usuario nuevo en Todo.ly
    And obtengo su token de autenticacion

    When creo un proyecto llamado "Proyecto de Vale"
    Then la respuesta debe tener codigo 200
    And el proyecto debe llamarse "Proyecto de Vale"

    When consulto el proyecto creado
    Then la respuesta debe tener codigo 200

    When actualizo el proyecto a "Proyecto de Vale Actualizado"
    Then la respuesta debe tener codigo 200
    And el proyecto debe llamarse "Proyecto de Vale Actualizado"

    When elimino el proyecto creado
    Then la respuesta debe tener codigo 200
