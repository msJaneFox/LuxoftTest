package testapp.tests.TestApi;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LoginApiTest {

    @Parameterized.Parameter
    public String name;

    @Parameterized.Parameter(1)
    public String password;

    @Parameterized.Parameter(2)
    public String expectedResult;

    @Parameterized.Parameters(name = "Имя {0} | Пароль {1} | Ожидаемый результат - {2}")
    public static Collection<String[]> dataProvider() {
        return Arrays.asList(new String[][]{
                {"\"john_dow@some.domaine.com\"", "\"123456789\"", "SUCCESS"},
                {"\"simon_dow@some.domaine.com\"", "\"123456789\"", "SUCCESS"},
                {"\"john_dow@some.domaine.com\"", "\"000000000\"", "FAILED"},
                {"\"simon_dow@some.domaine.com\"", "\"000000000\"", "FAILED"},
                {"\"error@some.domaine.com\"", "\"123456789\"", "FAILED"}
        });
    }

    @Description("Проверка логина")
    @Test
    public void loginTest() {
        Api api = new Api();
        Response resp = api.loginResponse(password, name);
        api.checkResponseCode(resp, 200);
        api.loginResponseStatus(resp, expectedResult);
    }

}
