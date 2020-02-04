package testapp.tests.TestUI;

import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import testapp.PageObject.LoginPage;
import testapp.PageObject.MainPage;
import testapp.WebDriverSettings;

import java.util.Random;


public class DeleteNoteTests extends WebDriverSettings {
    String name = "simon_dow@some.domaine.com";
    String password = "123456789";

    @Description("Проверка удаления записи")
    @Test
    public void DeleteNoteTest() {
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
        Assert.assertEquals(mainPage.getNote("1"), "Заметка отсутсвует");
    }

    @Description("Проверка раздизейбливания кнопки после удаления записи")
    @Test
    public void DisabledAddNoteAfterDeleteNoteTest() {
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.getLoginPage();

        loginPage.enterUsername(name);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);
        mainPage.deleteAllNote();
        Random ran = new Random();
        String note;
        for (int i = 1; i < 11; i++) {
            note = ran.ints(97, 123).limit(10)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            mainPage.enterNote(note);
            mainPage.addNote();
            String number = Integer.toString(i);
            Assert.assertEquals(mainPage.getNote(number), note);
            Assert.assertEquals(mainPage.getIndexNote(number), number);
        }
        mainPage.deleteNote("1");
        Assert.assertTrue(mainPage.checkEnabledAddNote());
    }

}
