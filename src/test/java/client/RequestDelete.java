
package client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestDelete implements IRequest {

    @Override
    public Response send(RequestInfo info) {

        RequestSpecification request = RestAssured.given()
                .accept(ContentType.JSON);

        if (info.getToken() != null && !info.getToken().isBlank()) {
            request.header("Token", info.getToken());
        }

        return request.when().delete(info.getUrl());
    }
}
