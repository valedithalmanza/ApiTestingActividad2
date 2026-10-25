# Actividad 2 - API Testing

**Automatización de una API REST con Todo.ly, Java, Cucumber, RestAssured y Gradle.**

Este proyecto corresponde a la **Actividad 2 de API Testing**. Automatiza la creación de un usuario en **Todo.ly**, obtiene un **TOKEN de autenticación** y realiza operaciones de creación, consulta, actualización y eliminación de un proyecto.

## Caso de prueba

| Campo | Descripción |
| --- | --- |
| ID | TC-API-001 |
| API utilizada | Todo.ly REST API |
| Escenario | Crear usuario y realizar CRUD de un proyecto |
| Autenticación | TOKEN obtenido con el usuario creado |
| Proyecto inicial | Proyecto de Vale |
| Proyecto actualizado | Proyecto de Vale Actualizado |
| Verificación esperada | Respuestas HTTP 200 y validación del nombre al crear y actualizar |
| Etiqueta de ejecución | `@Regression` |

El escenario realiza el flujo de **crear usuario → obtener TOKEN → crear proyecto → consultar → actualizar → eliminar**. El identificador del proyecto creado se utiliza en las peticiones posteriores.

## Tecnologías

- Java 21
- Cucumber (BDD / Gherkin)
- RestAssured
- Gradle (incluye Gradle Wrapper)
- IntelliJ IDEA
- API REST de Todo.ly

## Estructura del proyecto

```text
src/test/java/
├── client/
│   ├── IRequest.java        # Para peticiones HTTP
│   ├── RequestInfo.java     # Datos de cada solicitud
│   ├── RequestGet.java      # Solicitudes GET
│   ├── RequestPost.java     # Solicitudes POST
│   ├── RequestPut.java      # Solicitudes PUT
│   ├── RequestDelete.java   # Solicitudes DELETE
│   ├── FactoryRequest.java  # Selección del tipo de petición
│   └── TokenService.java    # Obtención del TOKEN
├── config/
│   └── Configuration.java   # URL base de la API
└── runner/
    └── MyStepdefsClean.java # Pasos Cucumber

src/test/resources/features/
└── crudProyectoToken.feature # Escenario Gherkin

build.gradle                 # Dependencias y tarea cucumberCli
gradlew.bat                  # Gradle Wrapper para Windows
```

Esta organización separa las solicitudes HTTP, la configuración y los pasos de prueba siguiendo la estructura **Clean** aplicada en clase.

## Requisitos previos

1. Tener instalado **JDK 21**.
2. Contar con conexión a Internet para acceder a **Todo.ly**.
3. Abrir el proyecto en **IntelliJ IDEA** o utilizar una terminal PowerShell.
4. Permitir que Gradle descargue las dependencias la primera vez que se ejecute.


## Cómo ejecutar la prueba (Windows / PowerShell)

### 1. Abrir el proyecto

Desde PowerShell, ubicarse en la carpeta raíz:

```powershell
cd D:\ProyectosTesting\ApiTestingActividad2
```

### 2. Ejecutar el escenario de regresión

```powershell
.\gradlew.bat cucumberCli -Psuite="@Regression"
```

El comando ejecuta el escenario del archivo `crudProyectoToken.feature` usando **Cucumber** y las clases de **RestAssured**.

### 3. Abrir el reporte HTML

Al terminar, el reporte se encuentra en:

```text
report/cucumber-report.html
```

Abrir ese archivo en un navegador para revisar los pasos y sus resultados.

## Validación y evidencias

La prueba automatizada:

1. Registra un nuevo usuario en Todo.ly.
2. Solicita y obtiene su token de autenticación.
3. Crea el proyecto **Proyecto de Vale** y comprueba el código HTTP y el nombre.
4. Consulta el proyecto creado y comprueba la respuesta HTTP 200.
5. Actualiza el nombre a **Proyecto de Vale Actualizado** y valida el cambio.
6. Elimina el proyecto y verifica la respuesta HTTP 200.

La ejecución exitosa registrada mostró:

```text
TOKEN obtenido correctamente
Codigo HTTP verificado: 200
Nombre verificado: Proyecto de Vale
Nombre verificado: Proyecto de Vale Actualizado

1 scenario (1 passed)
BUILD SUCCESSFUL
```

**Nota:** el escenario valida la respuesta HTTP de eliminación, pero no realiza una consulta adicional después de `DELETE` para comprobar que el proyecto ya no exista. Esta comprobación puede añadirse como mejora posterior. Los reportes generados en `report/` se conservan localmente y se excluyen del repositorio mediante `.gitignore`.

## Autoría

**Valeria Edith Almanza Mamani**  
Actividad académica: API Testing.
