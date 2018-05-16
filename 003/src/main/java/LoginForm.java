import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginForm extends WebDriverEntity {

    protected LoginForm(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    public void login(String login, String password) {
        WebElement loginField = webDriver.findElement(By.id("id_l.L.login"));
        WebElement passwordField = webDriver.findElement(By.id("id_l.L.password"));
        WebElement loginButton = webDriver.findElement(By.id("id_l.L.loginButton"));
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        loginButton.click();
        webDriverWait.until(ExpectedConditions.urlContains("/dashboard"));
    }
}
