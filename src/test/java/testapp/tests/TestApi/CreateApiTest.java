package testapp.tests.TestApi;
import io.qameta.allure.Description;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Random;

public class CreateApiTest {
    String name = "\"john_dow@some.domaine.com\"";
    String password = "\"123456789\"";

    @Description("Проверка создания записи")
    @Test
    public void createTest() {
        Api api = new Api();
        Response login = api.loginResponse(password, name);
        Cookies cookie = login.getDetailedCookies();
        api.removeAll(cookie);

        Random ran = new Random();
        String note;
        note = ran.ints(97, 123).limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        Response resp = api.createResponse(cookie, "\""+note+"\"");
        api.checkResponseCode(resp, 200);
        api.createResponseStatus(resp, "SUCCESS", note);
    }

    @Description("Проверка создания записи без авторизации")
    @Test
    public void createWithoutCookieTest() {
        Api api = new Api();
        Response login = api.loginResponse(password, name);
        Cookies cookie = login.getDetailedCookies();
        api.removeAll(cookie);

        Random ran = new Random();
        String note;
        note = ran.ints(97, 123).limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        Response resp = api.createResponse(null, "\""+note+"\"");
        api.checkResponseCode(resp, 302);
    }

    @Description("Проверка превышения максимального количества записей")
    @Test
    public void createMaxCountTest() {
        Api api = new Api();
        Response login = api.loginResponse(password, name);
        Cookies cookie = login.getDetailedCookies();
        api.removeAll(cookie);

        Random ran = new Random();
        String note;
        note = ran.ints(97, 123).limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        for (int i = 0; i < 10; i++) {
            api.createResponse(cookie, "\"" + note + "\"");
        }
        Response resp = api.createResponse(cookie, "\"" + note + "\"");
        api.checkResponseCode(resp, 400);
    }

    @Description("Проверка превышения максимальной длины записи")
    @Test
    public void createMaxLengthTest() {
        Api api = new Api();
        Response login = api.loginResponse(password, name);
        Cookies cookie = login.getDetailedCookies();
        api.removeAll(cookie);

        Random ran = new Random();
        String note;
        note = ran.ints(97, 123).limit(129)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        Response resp = api.createResponse(cookie, "\"" + note + "\"");
        api.checkResponseCode(resp, 400);
    }
}
