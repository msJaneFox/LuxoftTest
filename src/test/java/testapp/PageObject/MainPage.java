package testapp.PageObject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testapp.WebBase;

import java.util.List;

public class MainPage {
    WebDriver driver;
    WebBase base;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        base = new WebBase(driver);
    }

    // Элементы на странице
    By logout = By.cssSelector(".header-container .button");
    By noteInputField = By.cssSelector(".create-form input");
    By addNoteButton = By.cssSelector(".create-form button");
    String deleteNoteButton = ".single-todo:nth-child(%s) .red-icon";
    String deleteNoteButtons = ".single-todo .red-icon";
    String noteText = ".single-todo:nth-child(%s) .todo-description";
    String noteIndex = ".single-todo:nth-child(%s) .todo-index";


    @Step("Нажатие на кнопку Выход")
    public void clickLogout(){
        WebElement logoutButton = base.findElement(logout);
        logoutButton.click();
    }

    @Step("Ввод заметки")
    public void enterNote(String note){
        WebElement noteField = base.findElement(noteInputField);
        noteField.sendKeys(note);
    }

    @Step("Нажатие на кнопку добавления заметки")
    public void addNote(){
        WebElement addNote = base.findElement(addNoteButton);
        addNote.click();
    }

    @Step("Нажатие на кнопку удаления заметки")
    public void deleteNote(String number){
        WebElement deleteNote = base.findElement(By.cssSelector(String.format(deleteNoteButton, number)));
        deleteNote.click();
    }

    @Step("Проверка кликабильности кнопки добавления заметки")
    public boolean checkEnabledAddNote(){
        WebElement addNote = base.findElement(addNoteButton);
        return addNote.isEnabled();
    }

    @Step("Удаление всех заметок")
    public void deleteAllNote(){
        try {
            List<WebElement> deleteNotes = base.findElements(By.cssSelector(deleteNoteButtons));
            for (int i = 0; i < deleteNotes.size(); i++) {
                WebElement deleteNote = base.findElement(By.cssSelector(String.format(deleteNoteButton, 1)));
                deleteNote.click();
            }
        }
        catch (Exception ignored) {
        }
    }

    @Step("Получение заметки")
    public String getNote(String number){
        try {
            WebElement note = base.findElement(By.cssSelector(String.format(noteText, number)));
            return note.getText();
        }
        catch (Exception e) {
            return("Заметка отсутсвует");
        }
    }

    @Step("Получение номера заметки")
    public String getIndexNote(String number){
        WebElement index = base.findElement(By.cssSelector(String.format(noteIndex, number)));
        return index.getText();
    }

}
