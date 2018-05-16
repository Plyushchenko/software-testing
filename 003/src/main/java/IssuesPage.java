import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

public class IssuesPage extends WebDriverEntity {
    private final static String ISSUE_LIST_ID = "id_l.I.c.il.issueList";
    private static final String ISSUE_SUMARY = "issue-summary";
    private static final String DESCRIPTION = "description";

    public IssuesPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        super(webDriver, webDriverWait);
    }

    public List<Issue> getIssues() {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ISSUE_LIST_ID)));
        WebElement issueList = webDriver.findElement(By.id(ISSUE_LIST_ID));
        return issueList.findElements(By.cssSelector("*[cn='l.I.c.il.i.issueContainer']"))
                .stream()
                .map(webElement -> {
                    List<WebElement> summaries = webElement.findElements(By.className(ISSUE_SUMARY));
                    String summary = summaries.isEmpty() ? "" : summaries.get(0).getText();
                    List<WebElement> descriptions = webElement.findElements(By.className(DESCRIPTION));
                    String description = descriptions.isEmpty() ? "" : descriptions.get(0).getText();
                    return new Issue(summary, description);
                })
                .collect(Collectors.toList());
    }

    public boolean contains(Issue issue) {
        return getIssues().contains(issue);
    }

    public void create(Issue issue) {
        CreateIssueForm createIssueForm = new CreateIssueForm(webDriver, webDriverWait);
        createIssueForm.create(issue);
    }

    public void createWithError(Issue issue) {
        CreateIssueForm createIssueForm = new CreateIssueForm(webDriver, webDriverWait);
        createIssueForm.createWithError(issue);
    }

}
