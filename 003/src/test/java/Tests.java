import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class Tests {
    private static final long WEB_DRIVER_WAIT_TIMEOUT_IN_SECONDS = 30;
    private static final String ROOT = "root";
    private static final String MAIN_URL = "http://localhost:8081/";
    private static final String NEW_ISSUE_URL = MAIN_URL + "#newissue=yes";
    private static final String ISSUES_PAGE_URL = MAIN_URL + "issues";
    private static String STRING_170_TO_400;
    private static WebDriver webDriver;
    private static WebDriverWait webDriverWait;

    @BeforeClass
    public static void beforeClass() {
        String os = System.getProperty("os.name").toLowerCase();
        String driverPath;
        if (os.contains("windows")) {
            driverPath = System.getProperty("user.dir") + "\\drivers\\chromedriver.exe";
        } else {
            driverPath = System.getProperty("user.dir") + "/drivers/chromedriver";
        }
        System.setProperty("webdriver.chrome.driver", driverPath);
        webDriver = new ChromeDriver();
        webDriverWait = new WebDriverWait(webDriver, WEB_DRIVER_WAIT_TIMEOUT_IN_SECONDS);
        webDriver.get(MAIN_URL);
        LoginForm loginForm = new LoginForm(webDriver, webDriverWait);
        loginForm.login(ROOT, ROOT);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 170; i < 400; i++) {
            stringBuilder.append((char) i);
        }
        STRING_170_TO_400 = stringBuilder.toString();
    }

    @Test
    public void emptySummaryTest() {
        webDriver.get(NEW_ISSUE_URL);
        CreateIssueForm createIssueForm = new CreateIssueForm(webDriver, webDriverWait);
        createIssueForm.createWithError(new Issue("", ""));
    }

    @Test
    public void emptyDescriptionTest() {
        webDriver.get(NEW_ISSUE_URL);
        CreateIssueForm createIssueForm = new CreateIssueForm(webDriver, webDriverWait);
        Issue issue = new Issue("emptyDescriptionTest " + System.currentTimeMillis(), "");
        createIssueForm.create(issue);
        checkContainsPreview(issue);
    }

    @Test
    public void shortSummaryTest() {
        webDriver.get(NEW_ISSUE_URL);
        CreateIssueForm createIssueForm = new CreateIssueForm(webDriver, webDriverWait);
        Issue issue = new Issue("shortSummaryTest " + System.currentTimeMillis(), "" + System.currentTimeMillis());
        createIssueForm.create(issue);
        checkContainsPreview(issue);
    }

    @Test
    public void longSummaryTest() {
        webDriver.get(NEW_ISSUE_URL);
        CreateIssueForm createIssueForm = new CreateIssueForm(webDriver, webDriverWait);
        Issue issue = new Issue(STRING_170_TO_400 + System.currentTimeMillis(), "" + System.currentTimeMillis());
        createIssueForm.create(issue);
        checkContainsPreview(issue);
    }

    @Test
    public void longSummaryAndDescriptionTest() {
        webDriver.get(NEW_ISSUE_URL);
        CreateIssueForm createIssueForm = new CreateIssueForm(webDriver, webDriverWait);
        Issue issue = new Issue(
                STRING_170_TO_400 + System.currentTimeMillis(),
                STRING_170_TO_400 + System.currentTimeMillis()
        );
        createIssueForm.create(issue);
        checkContainsPreview(issue);
    }

    @Test
    public void DescriptionWithSpacesTest() {
        webDriver.get(NEW_ISSUE_URL);
        CreateIssueForm createIssueForm = new CreateIssueForm(webDriver, webDriverWait);
        Issue issue = new Issue(
                "DescriptionWithSpacesTest" + System.currentTimeMillis(),
                STRING_170_TO_400.substring(0, 180) + "                  " + System.currentTimeMillis()
        );
        createIssueForm.create(issue);
        checkContainsPreview(issue);
    }

    @AfterClass
    public static void afterClass() {
        webDriver.quit();
    }


    private void checkContainsPreview(Issue issue) {
        webDriver.get(ISSUES_PAGE_URL);
        IssuesPage issuesPage = new IssuesPage(webDriver, webDriverWait);
        assertTrue(issuesPage.contains(issue.preview()));
    }
}

