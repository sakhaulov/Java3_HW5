package sakhaulov;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class JiraTests extends AbstractTest {

    @BeforeAll
    static void authorize() {

        getDriver().navigate().to("https://id.atlassian.com/login");
        new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        Actions auth = new Actions(getDriver());

        auth.sendKeys(getDriver().findElement(By.name("username")), "timur.sakhaulov@gmail.com")
                .pause(1000l)
                .click(getDriver().findElement(By.cssSelector("span.css-19r5em7")))
                .pause(1000l)
                .sendKeys(getDriver().findElement(By.id("password")), "TestSakhaulov2022")
                .click(getDriver().findElement(By.id("login-submit")))
                .build()
                .perform();

        //Products page | Authorization assertion
        new WebDriverWait(getDriver(), Duration.ofSeconds(3))
                .until(ExpectedConditions.urlContains("https://start.atlassian.com/"));
        Assertions.assertTrue(getDriver().findElements(By.xpath(".//*[contains(text(), 'sakhaulov')]")).size() != 0,
                              "Ошибка авторизации");
    }

    @Test
    @Disabled
    void authValidDataTest() {
        System.out.println("Lol");
    }

    @Test
    void createBugTest() {

        getDriver().navigate().to("https://start.atlassian.com/");

            //Products page
        WebElement webElement = getDriver().findElement(By.xpath(".//div[contains(text(), 'tsakhaulov')]"));
        webElement.click();

            //Projects page
            webElement = getDriver().findElement(By.xpath(".//span[contains(text(), 'sakhaulov')]"));
            webElement.click();

            //Products page
            new WebDriverWait(getDriver(), Duration.ofSeconds(5)).
                    until(ExpectedConditions.
                            visibilityOfElementLocated(By.id("createGlobalItem")));

            webElement = getDriver().findElement(By.xpath(".//button[@id='createGlobalItem']"));
            webElement.click();

            //Create issue modal window
            webElement = getDriver().findElement(By.id("issue-create.ui.modal.create-form.type-picker.issue-type-select"));
            webElement.click();
            webElement = getDriver().findElement(By.xpath(".//div[@id='issue-create.ui.modal.create-form.type-picker.issue-type-select']/*/div[contains(.,'Bug')]"));
            webElement.click();
            webElement = getDriver().findElement(By.id("summary-field"));
            webElement.sendKeys("MyTestBug");
            webElement = getDriver().findElement(By.xpath(".//button[@data-testid='issue-create.ui.modal.footer.create-button']"));
            webElement.click();

            //Check fo pop-up
            List<WebElement> webElements = getDriver().findElements(By.cssSelector(".css-1do2z69"));
            if (webElements.size() > 0) {
                System.out.println("Test success");
            } else {
                System.out.println("Test fail");
            }

    }

    @Test
    @Disabled
    public static void visitProjectBoard() {

        /*
        System.setProperty(
                "webdriver.chrome.driver",
                "src/main/resources/chromedriver");
         */

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        //options.addArguments("--headless");
        options.addArguments("start-maximized");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get("https://www.atlassian.com");

        try {
            //Authorization
            //Index page
            WebElement webElement = driver.findElement(By.xpath(".//span[contains(text(),'My account')]"));
            webElement.click();
            webElement = driver.findElement(By.xpath(".//span[contains(text(), 'Log in')]"));
            webElement.click();

            //Authorization form
            webElement = driver.findElement(By.name("username"));
            webElement.sendKeys("timur.sakhaulov@gmail.com");
            webElement = driver.findElement(By.cssSelector("span.css-19r5em7"));
            webElement.click();
            webElement = driver.findElement(By.id("password"));
            webElement.sendKeys("TestSakhaulov2022");
            webElement = driver.findElement(By.id("login-submit"));
            webElement.click();
            //Authorization end

            //Products page
            webElement = driver.findElement(By.xpath(".//div[contains(text(), 'tsakhaulov')]"));
            webElement.click();

            //Projects page
            webElement = driver.findElement(By.xpath(".//span[contains(text(), 'sakhaulov')]"));
            webElement.click();

            //Projects page
            List<WebElement> webElements = driver.findElements(By.xpath(".//h1[contains(text(), 'SAKH board')]"));
            if (webElements.size() > 0) {
                System.out.println("Test success");
            } else {
                System.out.println("Test fail");
            }

        } finally {
            driver.quit();
        }

    }
}
