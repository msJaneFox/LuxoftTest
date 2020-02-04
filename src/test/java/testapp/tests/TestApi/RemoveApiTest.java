package testapp.tests.TestApi;
import io.qameta.allure.Description;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Random;

public class RemoveApiTest {
    String name = "\"john_dow@some.domaine.com\"";
    String password = "\"123456789\"";

    @Description("Проверка удаления записи")
    @Test
    public void removeTest() {
        Api api = new Api();
        Response login = api.loginResponse(password, name);
        Cookies cookie = login.getDetailedCookies();

        Random ran = new Random();
        String note;
        note = ran.ints(97, 123).limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        Response create = api.createResponse(cookie, "\""+note+"\"");
        int id = api.getIdCreate(create);
        Response resp = api.removeResponse(cookie, String.valueOf(id));
        api.checkResponseCode(resp, 200);
        api.removeResponseStatus(resp, "SUCCESS");
    }

    @Description("Проверка удаление записи без авторизации")
    @Test
    public void removeWithoutCookieTest() {
        Api api = new Api();
        Response login = api.loginResponse(password, name);
        Cookies cookie = login.getDetailedCookies();

        Random ran = new Random();
        String note;
        note = ran.ints(97, 123).limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        Response create = api.createResponse(cookie, "\""+note+"\"");
        int id = api.getIdCreate(create);
        Response resp = api.removeResponse(null, String.valueOf(id));
        api.checkResponseCode(resp, 302);
    }

    @Description("Проверка удаления несуществующей записи")
    @Test
    public void removeNonExistTest() {
        Api api = new Api();
        Response login = api.loginResponse(password, name);
        Cookies cookie = login.getDetailedCookies();
        api.removeAll(cookie);

        Response resp = api.removeResponse(cookie, String.valueOf(0));
        api.checkResponseCode(resp, 404);
    }
}
