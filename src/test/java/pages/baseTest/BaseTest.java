package pages.baseTest;

import dataProviders.DataProviderTest;
import driverFactory.GetChromeDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import utils.JsonFileManager;
import utils.OpenCSVFileManager;

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
    public void tearDown() {
        if (driver == null) {
            log.warn("⚠️ tearDown called but BaseTest.driver is null");
        }
        log.info("🛑 Quitting Chrome driver");
        GetChromeDriver.quitDriver();
        driver = null;
        log.debug("🧹 Driver quit and references cleared");
    }
}