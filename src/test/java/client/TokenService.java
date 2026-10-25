
package client;

import config.Configuration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TokenService {

    public Response obtenerToken(String email, String password) {

        return RestAssured.given()
                .auth()
                .preemptive()
                .basic(email, password)
                .accept(ContentType.JSON)
                .when()
                .get(
                        Configuration.BASE_URL +
                                "api/authentication/token.json"
                );
    }
}
