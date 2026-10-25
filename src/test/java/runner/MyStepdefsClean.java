
package runner;

import client.FactoryRequest;
import client.RequestInfo;
import client.TokenService;
import config.Configuration;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;

public class MyStepdefsClean {

    private Response response;
    private String email;
    private String password;
    private String token;
    private long projectId;
    private boolean eliminado = false;

    @Given("creo un usuario nuevo en Todo.ly")
    public void crearUsuario() {

        String identificador = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12);

        email = "vale.api." + identificador + "@example.com";
        password = "Prueba!" + identificador + "A1";

        JSONObject body = new JSONObject();
        body.put("Email", email);
        body.put("Password", password);
        body.put("FullName", "Vale API Testing");

        RequestInfo info = new RequestInfo(
                Configuration.BASE_URL + "api/user.json",
                body.toString()
        );

        response = FactoryRequest.make("POST").send(info);

        response.then().statusCode(200);

        System.out.println("Usuario creado: " + email);
    }

    @Given("obtengo su token de autenticacion")
    public void obtenerToken() {

        TokenService servicio = new TokenService();

        response = servicio.obtenerToken(email, password);

        response.then().statusCode(200);

        token = response.jsonPath().getString("TokenString");

        if (token == null || token.isBlank()) {
            throw new AssertionError("No se obtuvo el TOKEN");
        }

        System.out.println("TOKEN obtenido correctamente");
    }

    @When("creo un proyecto llamado {string}")
    public void crearProyecto(String nombre) {

        JSONObject body = new JSONObject();
        body.put("Content", nombre);
        body.put("Icon", 4);

        RequestInfo info = new RequestInfo(
                Configuration.BASE_URL + "api/projects.json",
                body.toString(),
                token
        );

        response = FactoryRequest.make("POST").send(info);
    }

    @Then("la respuesta debe tener codigo {int}")
    public void verificarCodigo(int codigoEsperado) {

        response.then().statusCode(codigoEsperado);

        System.out.println(
                "Codigo HTTP verificado: " + codigoEsperado
        );
    }

    @Then("el proyecto debe llamarse {string}")
    public void verificarNombre(String nombreEsperado) {

        response.then()
                .body("Content", equalTo(nombreEsperado));

        if (projectId == 0) {

            Number id = response.jsonPath().get("Id");

            if (id == null || id.longValue() <= 0) {
                throw new AssertionError(
                        "No se obtuvo un ID de proyecto valido"
                );
            }

            projectId = id.longValue();

            System.out.println("ID del proyecto: " + projectId);
        }

        System.out.println(
                "Nombre verificado: " + nombreEsperado
        );
    }

    @When("consulto el proyecto creado")
    public void consultarProyecto() {

        RequestInfo info = new RequestInfo(
                Configuration.BASE_URL +
                        "api/projects/" + projectId + ".json",
                null,
                token
        );

        response = FactoryRequest.make("GET").send(info);
    }

    @When("actualizo el proyecto a {string}")
    public void actualizarProyecto(String nuevoNombre) {

        JSONObject body = new JSONObject();
        body.put("Content", nuevoNombre);

        RequestInfo info = new RequestInfo(
                Configuration.BASE_URL +
                        "api/projects/" + projectId + ".json",
                body.toString(),
                token
        );

        response = FactoryRequest.make("PUT").send(info);
    }

    @When("elimino el proyecto creado")
    public void eliminarProyecto() {

        RequestInfo info = new RequestInfo(
                Configuration.BASE_URL +
                        "api/projects/" + projectId + ".json",
                null,
                token
        );

        response = FactoryRequest.make("DELETE").send(info);

        eliminado = response.statusCode() == 200;
    }

    @After
    public void limpiarProyectoSiFalla() {

        if (!eliminado && token != null && projectId > 0) {

            try {
                RequestInfo info = new RequestInfo(
                        Configuration.BASE_URL +
                                "api/projects/" + projectId + ".json",
                        null,
                        token
                );

                FactoryRequest.make("DELETE").send(info);

            } catch (Exception error) {
                System.out.println(
                        "No se pudo limpiar el proyecto de prueba"
                );
            }
        }
    }
}
