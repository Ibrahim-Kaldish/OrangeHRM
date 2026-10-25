package pages.dashboardtest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import pages.basetest.BaseTest;
import pages.dashboard.DashboardPage;
import pages.login.LoginPage;
import pages.pim.PIMPage;
import java.util.Map;

@Epic("Navigation")
@Feature("Dashboard")
public class DashboardTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(DashboardTest.class);

    @Test
    @Story("Admin navigates from the dashboard to PIM")
    @Description("As an admin, I want to open PIM from the dashboard so that I can manage employee records.")
    @Severity(SeverityLevel.NORMAL)
    public void navigateToPim() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        PIMPage pimPage = new PIMPage(driver);

        Map<String, String> user = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");

        log.info("🔐 Logging in as admin");
        loginPage.enterUsername(user.get("username"));
        loginPage.enterPassword(user.get("password"));
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("dashboard")),
                "Admin login did not redirect to the dashboard");
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed(),
                "Dashboard title is not displayed after admin login");

        log.info("📂 Opening PIM from the dashboard");
        dashboardPage.clickPimMenuLink();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("PIM")),
                "PIM page URL is incorrect after opening the PIM menu");
        softAssert.assertTrue(pimPage.getPIMTitle().isDisplayed(),
                "PIM title is not displayed");

        log.info("🏁 Dashboard navigation test finished, checking results");
        softAssert.assertAll();
    }
}