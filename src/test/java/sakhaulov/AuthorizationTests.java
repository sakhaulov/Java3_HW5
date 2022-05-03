package sakhaulov;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
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

public class AuthorizationTests extends AbstractTest{

    @Test
    void authValidDataTest() throws InterruptedException {

        //Authorization
        enterAuthData("timur.sakhaulov@gmail.com", "TestSakhaulov2022");

        //Products page | Authorization assertion
        new WebDriverWait(getDriver(), Duration.ofSeconds(3))
                .until(ExpectedConditions.urlContains("https://start.atlassian.com/"));
        Assertions.assertTrue(getDriver().findElements(By.xpath(".//*[contains(text(), 'sakhaulov')]")).size() != 0,
                    "Ошибка авторизации");

    }

    @Test
    void authWrongPassTest() throws InterruptedException {

        enterAuthData("timur.sakhaulov@gmail.com", "TestSakhaulov202");

        //No authorization assertion
        new WebDriverWait(getDriver(), Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(".//*[@id='login-error']/span[contains(text(), 'Неверный адрес электронной почты и/или пароль.')]")));

        Assertions.assertNotEquals("https://start.atlassian.com/", getDriver().getCurrentUrl());
        Assertions.assertTrue(getDriver().findElements(
                By.xpath(".//*[@id='login-error']/span[contains(text(), 'Неверный адрес электронной почты и/или пароль.')]")).size() != 0);
    }

    static void enterAuthData(String login, String password) throws InterruptedException {

        getDriver().get("https://www.atlassian.com");
//        new WebDriverWait(getDriver(), Duration.ofSeconds(20))
//                .until(ExpectedConditions.presenceOfElementLocated(By.id("imkt-jsx--3642d018")));
        //Thread.sleep(10000);

        WebElement webElement = getDriver().findElement(By.id("imkt-jsx--3642d018"));
        webElement.click();
        webElement = getDriver().findElement(By.xpath(".//span[contains(text(), 'Log in')]"));
        webElement.click();
        Actions auth = new Actions(getDriver());

        auth.sendKeys(getDriver().findElement(By.name("username")), login)
                .pause(1000L)
                .click(getDriver().findElement(By.cssSelector("span.css-19r5em7")))
                .pause(1000L)
                .sendKeys(getDriver().findElement(By.id("password")), password)
                .click(getDriver().findElement(By.id("login-submit")))
                .build()
                .perform();
    }

//    @AfterEach
//    void deleteCookies() throws InterruptedException {
//        getDriver().manage().deleteAllCookies();
//        getDriver().navigate().refresh();
//        new WebDriverWait(getDriver(), Duration.ofSeconds(20))
//                .until(ExpectedConditions.presenceOfElementLocated(By.id("imkt-jsx--3642d018")));
//    }
}
