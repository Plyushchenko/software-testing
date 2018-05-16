import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateIssueForm extends WebDriverEntity {
    private static final String SUMMARY_ID = "id_l.D.ni.ei.eit.summary";
    private static final String DESCRIPTION_ID = "id_l.D.ni.ei.eit.description";
    private static final String CREATE_SELECTOR = "*[cn='l.D.ni.ei.submitButton']";

    public CreateIssueForm(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    public void create(Issue issue) {
        completeForm(issue);
        webDriverWait.until(ExpectedConditions.urlContains("/issue/"));
    }

    public void createWithError(Issue issue) {
        completeForm(issue);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".errorSeverity")));
    }

    private void completeForm(Issue issue) {
        WebElement summaryTextArea = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SUMMARY_ID)));
        summaryTextArea.sendKeys(issue.getSummary());
        WebElement descriptionField = webDriver.findElement(By.id(DESCRIPTION_ID));
        descriptionField.sendKeys(issue.getDescription());
        WebElement createButton = webDriver.findElement(By.cssSelector(CREATE_SELECTOR));
        createButton.click();
    }
}
