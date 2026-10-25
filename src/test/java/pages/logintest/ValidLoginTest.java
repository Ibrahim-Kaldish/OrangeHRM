package pages.logintest;

import dataproviders.DataProviderTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.dashboard.DashboardPage;
import pages.basetest.BaseTest;
import pages.login.LoginPage;

@Epic("Authentication")
@Feature("Login")
public class ValidLoginTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(ValidLoginTest.class);

    @Test(dataProvider = "validCredentials", dataProviderClass = DataProviderTest.class)
    @Story("User signs in with valid credentials")
    @Description("As a user, I want to sign in with valid credentials so that I can reach the dashboard.")
    @Severity(SeverityLevel.BLOCKER)
    public void login(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        log.info("🔐 Logging in as {}", username);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        log.info("🔍 Checking dashboard after login");
        WebElement title = dashboardPage.getDashboardTitle();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("dashboard")),
                "Login did not redirect to the dashboard for " + username);
        softAssert.assertTrue(title.isDisplayed(),
                "Dashboard title not displayed for " + username);
        softAssert.assertAll();

        log.info("✅ Login succeeded for {}", username);
    }
}