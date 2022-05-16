package sakhaulov;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class JiraTests extends AbstractTest {

    static final String LOGIN = "timur.sakhaulov@gmail.com";
    static final String PASSWORD = "TestSakhaulov2022";

    @BeforeAll
    static void authorize() {

        getDriver().navigate().to("https://id.atlassian.com/login");
        new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        Actions auth = new Actions(getDriver());

        auth.sendKeys(getDriver().findElement(By.name("username")), LOGIN)
                .pause(1000L)
                .click(getDriver().findElement(By.cssSelector("span.css-19r5em7")))
                .pause(1000L)
                .sendKeys(getDriver().findElement(By.id("password")), PASSWORD)
                .click(getDriver().findElement(By.id("login-submit")))
                .build()
                .perform();

        //Products page | Authorization assertion
        new WebDriverWait(getDriver(), Duration.ofSeconds(3))
                .until(ExpectedConditions.urlContains("https://start.atlassian.com/"));
        Assertions.assertTrue(getDriver().findElements(By.xpath(".//*[contains(text(), 'sakhaulov')]")).size() > 0,
                              "Ошибка авторизации");
    }


    @Test
    @DisplayName("Переход на доску проекта")
    void visitProjectBoard() {

        getDriver().navigate().to("https://start.atlassian.com/");

        //Products page
        WebElement webElement = getDriver().findElement(By.xpath(".//div[contains(text(), 'tsakhaulov')]"));
        webElement.click();

        //Projects page
        webElement = getDriver().findElement(By.xpath(".//span[contains(text(), 'sakhaulov')]"));
        webElement.click();

        //Projects page | Content assertion
        List<WebElement> webElements = getDriver().findElements(By.xpath(".//h1[contains(text(), 'SAKH board')]"));
        Assertions.assertTrue(webElements.size() > 0);
    }


    @Test
    @DisplayName("Создание баг-репорта")
    void createBugTest() {

        getDriver().navigate().to("https://tsakhaulov.atlassian.net/jira/software/projects/SAKH/boards/1");

        //Products page
        new WebDriverWait(getDriver(), Duration.ofSeconds(5)).
                until(ExpectedConditions.visibilityOfElementLocated(By.id("createGlobalItem")));

        WebElement webElement = getDriver().findElement(By.xpath(".//button[@id='createGlobalItem']"));
        webElement.click();

        String bugReportName = "MyTestBugAutomated" + (int)(Math.random() * (1000 - 1)) + 1;

        //Create issue modal window
        Actions enterData = new Actions(getDriver());
        enterData.click(getDriver().findElement(By.id("issue-create.ui.modal.create-form.type-picker.issue-type-select")))
                .click(getDriver().findElement(By.xpath(".//div[@id='issue-create.ui.modal.create-form.type-picker.issue-type-select']/*/div[contains(.,'Bug')]")))
                .sendKeys(getDriver().findElement(By.id("summary-field")),bugReportName)
                .build()
                .perform();

        webElement = getDriver().findElement(By.xpath(".//button[@data-testid='issue-create.ui.modal.footer.create-button']"));
        webElement.click();

        //Check fo pop-up
        Assertions.assertEquals(1, getDriver().findElements(By.cssSelector(".css-1do2z69")).size(), "Не обнаружено всплывающее окно с сообщением о создании баг-репорта");
        //Check if element present on "backlog" page
        webElement = getDriver().findElement(By.xpath(".//a[@data-test-id='navigation-apps-sidebar-next-gen.ui.menu.software-backlog-link']"));
        webElement.click();
        new WebDriverWait(getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[contains(text(), 'MyTestBugAutomated')]")));
        Assertions.assertTrue(getDriver().findElements(By.xpath(String.format(".//div[contains(text(), '%s')]", bugReportName))).size() > 0);

    }


    @Test
    @DisplayName("Создание команды")
    void startTeamTest() throws InterruptedException {
        getDriver().navigate().to("https://tsakhaulov.atlassian.net/jira/software/projects/SAKH/boards/1");
        Thread.sleep(2000);

        //Products page
        WebElement webElement = getDriver().findElement(By.xpath(".//*[@data-testid='menu-people-primary-button']"));
        webElement.click();
        webElement = getDriver().findElement(By.xpath(".//*[@data-testid='menu-view-people-directory']"));
        webElement.click();

        //Team management page
        webElement = getDriver().findElement(By.xpath(".//*[@data-test-selector='create-team-button']"));
        webElement.click();

        //Start a team modal window
        String teamName = "MyTestTeamAutomated" + (int)(Math.random() * (1000 - 1)) + 1;

        webElement = getDriver().findElement(By.name("teamName"));
        webElement.sendKeys(teamName);
        webElement = getDriver().findElement(By.xpath(".//span[contains(text(), 'Start team')]"));
        webElement.click();

        //Close "get started" modal window
        getDriver().switchTo().activeElement();
        getDriver().findElement(By.cssSelector(".css-eubphy")).click();

        //Assertions
        Assertions.assertTrue(getDriver()
                .findElements(By.xpath(String.format("//*[contains(text(), '%s')]", teamName))).size() > 0);

    }


    @Test
    @DisplayName("Создание собственного типа документа")
    void addingStandartIssueTypeTest() throws InterruptedException {
        getDriver().navigate().to("https://tsakhaulov.atlassian.net/jira/software/projects/SAKH/boards/1");
        Thread.sleep(2000);

        //Settings menu
        WebElement webElement = getDriver().findElement(By.xpath(".//*[@aria-label='Settings']"));
        webElement.click();
        webElement = getDriver().findElement(By.partialLinkText("Issues"));
        webElement.click();

        //Issue types page
        webElement = getDriver()
                .findElement(By.xpath("//*[@data-testid='admin-pages-issue-types-directory.ui.header-actions.add-issue-type-button']"));
        webElement.click();

        //Add issue type modal window
        Thread.sleep(2000);
        String testIssueTypeName = "TestIssueType" + (int) (Math.random() * (1000 - 1)) + 1;

        webElement = getDriver().findElement(By.name("name"));
        webElement.sendKeys(testIssueTypeName);
        webElement = getDriver().findElement(By.xpath("//*[contains(text(), 'Standard Issue Type')]"));
        webElement.click();
        webElement = getDriver().findElement(By.xpath(".//button[@type='submit']"));
        webElement.click();

        //Assertions
        Assertions.assertTrue(getDriver()
                .findElements(By.xpath(String.format(".//strong[contains(.,'%s')]", testIssueTypeName))).size() > 0);

    }
}

