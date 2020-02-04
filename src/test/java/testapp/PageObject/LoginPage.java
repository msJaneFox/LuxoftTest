package testapp.PageObject;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testapp.WebBase;

public class LoginPage {
    WebDriver driver;
    WebBase base;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        base = new WebBase(driver);
    }

    // Элементы на странице
    By userNameField = By.cssSelector("#username");
    By passwordField = By.cssSelector("#password");
    By loginButton = By.cssSelector(".login-container button");
    By errorMessage = By.cssSelector(".error");
    By loginForm = By.cssSelector("#login-page");


    @Step("Переход на страницу логина")
    public void getLoginPage(){
        driver.get("http://localhost:7844");
    }

    @Step("Ввод имени пользователя")
    public void enterUsername(String name){
        WebElement userName = base.findElement(userNameField);
        userName.sendKeys(name);
    }

    @Step("Ввод пароля")
    public void enterPassword(String pass){
        WebElement password = base.findElement(passwordField);
        password.sendKeys(pass);
    }

    @Step("Нажатие на кнопку входа")
    public void clickLogin(){
        WebElement login = base.findElement(loginButton);
        login.click();
    }

    @Step("Проверка наличия формы логина")
    public boolean checkLoginForm(){
        try {
            base.findElement(loginForm);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Step("Проверка наличия ошибки")
    public String checkErrorMessage(){
        try {
            WebElement message = base.findElement(errorMessage);
            return message.getText();
        }
        catch (Exception e) {
            return("Авторизация пройдена успешно");
        }
    }

    @Step("Проверка url")
    public void checkUrl(String url){
        Assert.assertEquals(base.getCurrentUrl(), url);
    }
}
