package sakhaulov;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public abstract class AbstractTest {

    private static WebDriver driver;

    @BeforeAll
    static void init() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

//    @BeforeEach
//    void getPage() {
//        Assertions.assertDoesNotThrow(()-> driver.navigate().to("https://www.atlassian.com"),
//                              "Страница не доступна");
//    }

    @AfterAll
    public static void closeDriver() {
        driver.quit();
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
