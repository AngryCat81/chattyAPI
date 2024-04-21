package tests;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest {
    FakeUserData dataAndCommonMethods = new FakeUserData();


    final static String BASE_URI = "http://chatty.telran-edu.de:8989/api";//final oznachaet, chto mi ne smozhem ego pereopredelitj gde-to v kode


    static RequestSpecification specWithoutAccessToken = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setContentType(ContentType.JSON)
            .build();


    public static Response postRequestWithoutAccessToken(String endPoint, Integer expectedStatusCode, Object body) {
        Response response = given()
                .spec(specWithoutAccessToken)
                .body(body)
                .when()
                .log().all()
                .post(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;


    }

    public static Response getRequest(String endPoint, Integer expectedStatusCode) {
        Response response = given()

                .spec(specWithoutAccessToken)
                .when()
                .log().all()
                .get(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;


    }

    public static Response getRequestWithToken(String endPoint, Integer expectedStatusCode, String token) {
        Response response = given()

                .spec(specWithoutAccessToken)
                .when()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .get(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response getRequestWithInvalitHttpMethod(String endPoint, Integer expectedStatusCode, String token) {
        Response response = given()

                .spec(specWithoutAccessToken)
                .when()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .post(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response postRequest(String endPoint, Integer expectedStatusCode, Object body) {
        Response response = given()
                .spec(specWithoutAccessToken)
                .body(body)
                .when()
                .log().all()
                .post(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response postRequestWithAccessToken(String endPoint, Integer expectedStatusCode, Object body, String token) {
        Response response = given()
                .spec(specWithoutAccessToken)
                .body(body)
                .when()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .post(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response deleteRequest(String endPoint, Integer expectedStatusCode, String token) {
        Response response = given()
                .spec(specWithoutAccessToken)
                .when()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .delete(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;


    }

    public static Response deleteRequestWithInvalidHttpMethod(String endPoint, Integer expectedStatusCode, String token) {
        Response response = given()
                .spec(specWithoutAccessToken)
                .when()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .post(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;


    }

    public static Response deleteRequestWithoutAuthorization(String endPoint, Integer expectedStatusCode) {
        Response response = given()
                .spec(specWithoutAccessToken)
                .when()
                .log().all()
                .delete(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;

    }

    public static Response putRequest(String endPoint, Integer expectedStatusCode, String token, Object body) {
        Response response = given()
                .spec(specWithoutAccessToken)
                .body(body)
                .when()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .put(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;

    }

    public static Response putRequestWithInvalidHttp(String endPoint, Integer expectedStatusCode, String token, Object body) {
        Response response = given()
                .spec(specWithoutAccessToken)
                .body(body)
                .when()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .post(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;

    }
    public static Response patchRequestWithAccessToken(String endPoint, Integer expectedStatusCode, Object body, String token) {
        Response response = given()
                .spec(specWithoutAccessToken)
                .body(body)
                .when()
                .log().all()
                .header("Authorization", "Bearer " + token)
                .patch(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }


    }

