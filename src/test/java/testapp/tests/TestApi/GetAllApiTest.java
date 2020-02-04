package testapp.tests.TestApi;
import io.qameta.allure.Description;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Random;

public class GetAllApiTest {
    String name = "\"john_dow@some.domaine.com\"";
    String password = "\"123456789\"";

    @Description("Проверка получения записей")
    @Test
    public void getAllTest() {
        Api api = new Api();
        Response login = api.loginResponse(password, name);
        Cookies cookie = login.getDetailedCookies();
        api.removeAll(cookie);

        Random ran = new Random();
        String note;
        note = ran.ints(97, 123).limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        Response create = api.createResponse(cookie, "\""+note+"\"");
        Response resp = api.getAllResponse(cookie);
        api.checkResponseCode(resp, 200);
        api.getAllResponseResult(resp, create);
    }

    @Description("Проверка получения записей без авторизации")
    @Test
    public void getAllWithoutCookieTest() {
        Api api = new Api();
        Response login = api.loginResponse(password, name);
        Cookies cookie = login.getDetailedCookies();
        api.removeAll(cookie);

        Random ran = new Random();
        String note;
        note = ran.ints(97, 123).limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        Response create = api.createResponse(cookie, "\""+note+"\"");
        Response resp = api.getAllResponse(null);
        api.checkResponseCode(resp, 200);
    }
}
