package testapp.tests.TestUI;

import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import testapp.PageObject.LoginPage;
import testapp.PageObject.MainPage;
import testapp.WebDriverSettings;
import java.util.Random;

public class LogoutTests extends WebDriverSettings {
    String name = "simon_dow@some.domaine.com";
    String password = "123456789";

    @Description("Проверка логаута")
    @Test
    public void LogoutTest() {
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.getLoginPage();

        loginPage.enterUsername(name);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);
        mainPage.clickLogout();
        loginPage.checkLoginForm();
    }

    @Description("Проверка отображения добавленных записей после перелогина")
    @Test
    public void AddedNoteAfterLogoutTest() {
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.getLoginPage();

        loginPage.enterUsername(name);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);
        mainPage.deleteAllNote();
        Random ran = new Random();
        String note;
        note = ran.ints(97, 123).limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        mainPage.enterNote(note);
        mainPage.addNote();
        mainPage.clickLogout();

        loginPage.enterUsername(name);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        Assert.assertEquals(mainPage.getNote("1"), note);
    }

    @Description("Проверка отображения удаленных записей после перелогина")
    @Test
    public void DeletedNoteAfterLogoutTest() {
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.getLoginPage();

        loginPage.enterUsername(name);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);
        mainPage.deleteAllNote();
        Random ran = new Random();
        String note;
        note = ran.ints(97, 123).limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        mainPage.enterNote(note);
        mainPage.addNote();
        mainPage.deleteNote("1");
        mainPage.clickLogout();

        loginPage.enterUsername(name);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        Assert.assertEquals(mainPage.getNote("1"), "Заметка отсутсвует");
    }
}
