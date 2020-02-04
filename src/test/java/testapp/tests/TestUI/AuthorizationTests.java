package testapp.tests.TestUI;

import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.support.PageFactory;
import testapp.PageObject.LoginPage;
import testapp.WebDriverSettings;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class AuthorizationTests extends WebDriverSettings {
    @Parameter
    public String name;

    @Parameter(1)
    public String password;

    @Parameter(2)
    public String expectedResult;

    @Parameter(3)
    public String url;

    @Parameters(name = "Имя {0} | Пароль {1} | Ожидаемый результат - {2}")
    public static Collection<String[]> dataProvider() {
        return Arrays.asList(new String[][]{
                {"john_dow@some.domaine.com", "123456789", "Авторизация пройдена успешно", "http://localhost:7844/main"},
                {"simon_dow@some.domaine.com", "123456789", "Авторизация пройдена успешно", "http://localhost:7844/main"},
                {"john_dow@some.domaine.com", "000000000", "Неверные логин или пароль", "http://localhost:7844/login"},
                {"simon_dow@some.domaine.com", "000000000", "Неверные логин или пароль", "http://localhost:7844/login"},
                {"error@some.domaine.com", "123456789", "Неверные логин или пароль", "http://localhost:7844/login"}
        });
    }

    @Description("Проверка логина")
    @Test
    public void LoginTest() {
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.getLoginPage();

        loginPage.enterUsername(name);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        Assert.assertEquals(loginPage.checkErrorMessage(), expectedResult);
        loginPage.checkUrl(url);
    }
}
