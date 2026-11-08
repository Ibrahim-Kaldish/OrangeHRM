package pages.basetest;

import driverfactory.GetChromeDriver;
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
import pages.dashboard.DashboardPage;
import pages.login.LoginPage;
import utils.JsonFileManager;
import utils.OpenCSVFileManager;
import utils.ScreenshotUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@Listeners(AllureTestNg.class)
public class BaseTest {
    private static final Logger log = LogManager.getLogger(BaseTest.class);
    protected static JsonFileManager jsonFileManagerUsers;
    protected static JsonFileManager jsonFileManagerData;
    protected static OpenCSVFileManager openCSVFileManagerInvalidEmployees;
    protected static OpenCSVFileManager openCSVFileManagerEmployees;

    protected static WebDriver driver;
    protected SoftAssert softAssert;

    @BeforeMethod
    public void setUp() {
        jsonFileManagerData = new JsonFileManager("data.json");
        jsonFileManagerUsers = new JsonFileManager("users.json");
        openCSVFileManagerInvalidEmployees = new OpenCSVFileManager("invalidEmployees.csv");
        openCSVFileManagerEmployees = new OpenCSVFileManager("employees.csv");

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
            ScreenshotUtil.saveFailureScreenshot(driver, result.getMethod().getMethodName());
        }
        GetChromeDriver.quitDriver();
        driver = null;
        log.debug("🧹 Driver quit and references cleared");
    }

    protected void loginAs(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    protected void assertOnDashboard(String role) {
        DashboardPage dashboardPage = new DashboardPage(driver);
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("dashboard")),
                role + " login did not redirect to the dashboard");
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed(),
                "Dashboard title is not displayed after " + role.toLowerCase() + " login");
        log.info("✅ {} logged in", role);
    }

    protected void loginAsAdmin() {
        log.info("🔐 Logging in as admin");
        Map<String, String> admin = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");
        loginAs(admin.get("username"), admin.get("password"));
        assertOnDashboard("Admin");
    }
}