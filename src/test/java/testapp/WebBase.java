package testapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class WebBase implements WebDriver {
    private WebDriver driver;

    public WebBase(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void get(String s) {
        driver.get(s);
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        List<WebElement> webElements = null;
        try {
            webElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        } catch (Exception e) {
            throw e;
        }
        return webElements;
    }

    @Override
    public WebElement findElement(By by) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        WebElement webElement = null;
        try {
            webElement = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Exception e) {
            throw e;
        }
        return webElement;
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public Options manage() {
        return driver.manage();
    }
}