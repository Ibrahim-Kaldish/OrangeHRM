package pages.loginTest;

import dataProviders.DataProviderTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import pages.dashboardPage.DashboardPage;
import pages.baseTest.BaseTest;
import pages.loginPage.LoginPage;

@Epic("Authentication")
@Feature("Login")
public class ValidLogin extends BaseTest {
    private static final Logger log = LogManager.getLogger(ValidLogin.class);

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

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        softAssert.assertAll();

        log.info("✅ Login succeeded for {}", username);
    }
}