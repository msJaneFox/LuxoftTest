package testapp.tests.TestApi;

import io.restassured.http.Cookies;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Api {

    @Step("Вызов POST login")
    public Response loginResponse(String password, String name) {
        return RestAssured.given().baseUri("http://localhost:7844/")
                .body("{\n" +
                        " \"password\": " + password +
                        ",\n \"username\": " + name +
                        "\n}")
                .basePath("api/login")
                .contentType(ContentType.JSON)
                .when().post();
    }

    @Step("Проверка кода ответа")
    public void checkResponseCode(Response resp, int code) {
         resp.then().statusCode(code);
    }

    @Step("Проверка ответа POST login")
    public void loginResponseStatus(Response resp, String expectedResult) {
        resp.then().body("status", Matchers.equalTo(expectedResult));
    }

    @Step("Вызов POST logout")
    public Response logoutResponse(Cookies cookie) {
        if (cookie == null){
            return RestAssured.given()
                    .baseUri("http://localhost:7844/")
                    .basePath("api/logout")
                    .contentType("text/html;charset=UTF-8")
                    .when().post();
        }
        return RestAssured.given()
                .cookies(cookie).baseUri("http://localhost:7844/")
                .basePath("api/logout")
                .contentType("text/html;charset=UTF-8")
                .when().post();
    }

    @Step("Вызов POST create")
    public Response createResponse(Cookies cookie, String description) {
        if (cookie == null){
            return RestAssured.given()
                    .baseUri("http://localhost:7844/")
                    .body("{\n" +
                            " \"description\": " + description +
                            "\n}")
                    .basePath("api/create")
                    .contentType(ContentType.JSON)
                    .when().post();
        }
        return RestAssured.given()
                .cookies(cookie).baseUri("http://localhost:7844/")
                .body("{\n" +
                        " \"description\": " + description +
                        "\n}")
                .basePath("api/create")
                .contentType(ContentType.JSON)
                .when().post();
    }

    @Step("Проверка ответа POST create")
    public void createResponseStatus(Response resp, String expectedResult, String description) {
        resp.then().body("status", Matchers.equalTo(expectedResult))
                .body("todo.id", Matchers.isA(int.class))
                .body("todo.personId", Matchers.isA(int.class))
                .body("todo.description", Matchers.equalTo(description));
    }

    @Step("Получение id записи в ответе POST create")
    public int getIdCreate(Response resp) {
        return resp.then().extract()
                .path("todo.id");
    }

    @Step("Вызов POST remove")
    public Response removeResponse(Cookies cookie, String id) {
        if (cookie == null){
            return RestAssured.given()
                    .baseUri("http://localhost:7844/")
                    .body("{\n" +
                            " \"id\": " + id +
                            "\n}")
                    .basePath("api/remove")
                    .contentType(ContentType.JSON)
                    .when().post();
        }
        return RestAssured.given()
                .cookies(cookie).baseUri("http://localhost:7844/")
                .body("{\n" +
                        " \"id\": " + id +
                        "\n}")
                .basePath("api/remove")
                .contentType(ContentType.JSON)
                .when().post();
    }

    @Step("Проверка ответа POST remove")
    public void removeResponseStatus(Response resp, String expectedResult) {
        resp.then().body("status", Matchers.equalTo(expectedResult));
    }

    @Step("Вызов GET getAll")
    public Response getAllResponse(Cookies cookie) {
        if (cookie == null){
            return RestAssured.given()
                    .baseUri("http://localhost:7844/")
                    .basePath("api/getAll")
                    .contentType(ContentType.JSON)
                    .when().get();
        }
        return RestAssured.given()
                .cookies(cookie).baseUri("http://localhost:7844/")
                .basePath("api/getAll")
                .contentType(ContentType.JSON)
                .when().get();
    }

    @Step("Проверка ответа GET getAll")
    public void getAllResponseResult(Response resp, Response create) {
        resp.then().body("todoList[0]", Matchers.equalTo(create.then().extract().path("todo")));
    }

    @Step("Удаление всех записей")
    public void removeAll(Cookies cookie) {
        Response all = RestAssured.given()
                .cookies(cookie).baseUri("http://localhost:7844/")
                .basePath("api/getAll")
                .contentType(ContentType.JSON)
                .when().get();
        ArrayList<LinkedHashMap> ss = all.then().extract().path("todoList");
        for (LinkedHashMap linkedHashMap : ss) {
            int id = (int) linkedHashMap.get("id");
            RestAssured.given()
                    .cookies(cookie).baseUri("http://localhost:7844/")
                    .body("{\n" +
                            " \"id\": " + id +
                            "\n}")
                    .basePath("api/remove")
                    .contentType(ContentType.JSON)
                    .when().post();
        }

    }

}
