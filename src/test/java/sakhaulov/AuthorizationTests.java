package sakhaulov;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthorizationTests extends AbstractTest{

    static final String LOGIN = "timur.sakhaulov@gmail.com";
    static final String PASSWORD = "TestSakhaulov2022";
    static final String WRONG_PASSWORD = "TestSakhaulov202";

    @Test
    void authWrongPassTest() {

        getDriver().navigate().to("https://www.atlassian.com/");
        WebElement webElement = getDriver().findElement(By.xpath(".//span[contains(text(), 'My account')]"));
        webElement.click();
        webElement = getDriver().findElement(By.xpath(".//span[contains(text(), 'Log in')]"));
        webElement.click();
        Actions auth = new Actions(getDriver());

        auth.sendKeys(getDriver().findElement(By.name("username")), LOGIN)
                .pause(1000L)
                .click(getDriver().findElement(By.cssSelector("span.css-19r5em7")))
                .pause(1000L)
                .sendKeys(getDriver().findElement(By.id("password")), WRONG_PASSWORD)
                .click(getDriver().findElement(By.id("login-submit")))
                .build()
                .perform();


        //No authorization assertion
        new WebDriverWait(getDriver(), Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath(".//*[@id='login-error']/span[contains(text(), 'Неверный адрес электронной почты и/или пароль.')]")));

        Assertions.assertNotEquals("https://start.atlassian.com/", getDriver().getCurrentUrl());
        Assertions.assertTrue(getDriver().findElements(
                By.xpath(".//*[@id='login-error']/span[contains(text(), 'Неверный адрес электронной почты и/или пароль.')]")).size() != 0);
    }


}
