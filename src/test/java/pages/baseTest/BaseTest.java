package pages.baseTest;

import driverFactory.GetChromeDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.testng.AllureTestNg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;
import utils.JsonFileManager;
import utils.OpenCSVFileManager;
import utils.Screenshot;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Listeners(AllureTestNg.class)
public class BaseTest {
    private static final Logger log = LogManager.getLogger(BaseTest.class);
    public static JsonFileManager jsonFileManagerUsers;
    public static JsonFileManager jsonFileManagerUrls;
    public static OpenCSVFileManager openCSVFileManagerInvalidEmployees = new OpenCSVFileManager("src/main/resources/invalidEmployees.csv");
    public static OpenCSVFileManager openCSVFileManagerEmployees = new OpenCSVFileManager("src/main/resources/employees.csv");

    public static WebDriver driver;
    public SoftAssert softAssert;

    @BeforeMethod
    public void setUp() {
        jsonFileManagerUrls = new JsonFileManager("src/main/resources/urls.json");
        jsonFileManagerUsers = new JsonFileManager("src/main/resources/users.json");
        openCSVFileManagerInvalidEmployees = new OpenCSVFileManager("src/main/resources/invalidEmployees.csv");
        openCSVFileManagerEmployees = new OpenCSVFileManager("src/main/resources/employees.csv");

        softAssert = new SoftAssert();

        log.info("🚀 Starting Chrome driver");
        driver = GetChromeDriver.getDriver();
        log.debug("🔍 Driver created: {}", driver);
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver == null) {
            log.warn("⚠️ tearDown called but BaseTest.driver is null");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            saveFailureScreenshot(result.getMethod().getMethodName());
        }
        log.info("🛑 Quitting Chrome driver");
        GetChromeDriver.quitDriver();
        driver = null;
        log.debug("🧹 Driver quit and references cleared");
    }

    private void saveFailureScreenshot(String testName) {
        try {
            File captured = Screenshot.takeScreenshot(driver);
            if (captured == null) {
                log.warn("⚠️ No screenshot captured for {}", testName);
                return;
            }

            Path target = Paths.get("target", "screenshots", testName + "_" + System.currentTimeMillis() + ".png");
            Files.createDirectories(target.getParent());
            Files.copy(captured.toPath(), target, StandardCopyOption.REPLACE_EXISTING);

            Allure.addAttachment(testName, "image/png", new ByteArrayInputStream(Files.readAllBytes(target)), "png");
            log.info("📸 Failure screenshot saved to {}", target.toAbsolutePath());
        } catch (IOException | RuntimeException e) {
            log.error("❌ Could not save failure screenshot for {}", testName, e);
        }
    }
}