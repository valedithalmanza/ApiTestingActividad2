
package client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestPut implements IRequest {

    @Override
    public Response send(RequestInfo info) {

        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);

        if (info.getToken() != null && !info.getToken().isBlank()) {
            request.header("Token", info.getToken());
        }

        if (info.getBody() != null) {
            request.body(info.getBody());
        }

        return request.when().put(info.getUrl());
    }
}

