package testapp.tests.TestApi;
import io.qameta.allure.Description;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.junit.Test;

public class LogoutApiTest {
    String name = "\"john_dow@some.domaine.com\"";
    String password = "\"123456789\"";

    @Description("Проверка логаута")
    @Test
    public void logoutTest() {
        Api api = new Api();
        Response login = api.loginResponse(password, name);
        Cookies cookie = login.getDetailedCookies();
        Response resp = api.logoutResponse(cookie);
        api.checkResponseCode(resp, 302);
    }

    @Description("Проверка логаута без авторизации")
    @Test
    public void logoutWithoutCookieTest() {
        Api api = new Api();
        Response resp = api.logoutResponse(null);
        api.checkResponseCode(resp, 401);
    }
}
