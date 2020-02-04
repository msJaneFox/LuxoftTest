package testapp.tests.TestUI;

import io.qameta.allure.Description;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import testapp.PageObject.MainPage;
import testapp.PageObject.LoginPage;
import testapp.WebDriverSettings;
import java.util.Random;


public class AddNoteTests extends WebDriverSettings {
    String name = "simon_dow@some.domaine.com";
    String password = "123456789";

    @Description("Проверка превышения максимального количества записей")
    @Test
    public void AddMaxCountNoteTest() {
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
        Assert.assertFalse(mainPage.checkEnabledAddNote());
    }

    @Description("Проверка превышения максимальной длины записи")
    @Test
    public void AddMaxLengthNoteTest() {
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.getLoginPage();

        loginPage.enterUsername(name);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        MainPage mainPage = PageFactory.initElements(driver, MainPage.class);
        mainPage.deleteAllNote();
        Random ran = new Random();
        String note;
        note = ran.ints(97, 123).limit(129)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
        mainPage.enterNote(note);
        mainPage.addNote();
        Assert.assertEquals(mainPage.getNote("1"), note.substring(0,128));
    }

}
