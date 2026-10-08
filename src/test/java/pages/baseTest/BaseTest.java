package pages.baseTest;

import driverFactory.GetChromeDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import utils.jsonFileManager;

public class BaseTest {
    private static final Logger log = LogManager.getLogger(BaseTest.class);
    public static jsonFileManager jsonFileManagerUsers;
    public static jsonFileManager jsonFileManagerUrls;
    public static WebDriver driver;
    public SoftAssert softAssert;

    @BeforeMethod
    public void setUp() {
        jsonFileManagerUsers = new jsonFileManager("src/main/resources/users.json");
        jsonFileManagerUrls = new jsonFileManager("src/main/resources/urls.json");
        softAssert = new SoftAssert();

        log.info("🚀 Starting Chrome driver");
        driver = GetChromeDriver.getDriver();
        log.debug("🔍 Driver created: {}", driver);
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