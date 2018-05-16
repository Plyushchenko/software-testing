import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class WebDriverEntity {
    protected final WebDriver webDriver;
    protected final WebDriverWait webDriverWait;

    protected WebDriverEntity(WebDriver webDriver, WebDriverWait webDriverWait) {
        this.webDriver = webDriver;
        this.webDriverWait = webDriverWait;
    }
}
