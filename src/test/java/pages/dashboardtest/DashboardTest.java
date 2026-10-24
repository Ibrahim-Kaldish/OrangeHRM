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
import pages.dashboardPage.DashboardPage;
import pages.pim.PIMPage;
import pages.basetest.BaseTest;
import pages.login.LoginPage;
import java.util.Map;

@Epic("Navigation")
@Feature("Dashboard")
public class DashboardTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(DashboardTest.class);

    @Test
    @Story("Admin navigates from the dashboard to PIM")
    @Description("As an admin, I want to open PIM from the dashboard so that I can manage employee records.")
    @Severity(SeverityLevel.NORMAL)
    public void navigateToPIM() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        PIMPage pimPage = new PIMPage(driver);

        Map<String, String> user = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");

        log.info("🔐 Logging in as admin");
        loginPage.enterUsername(user.get("username"));
        loginPage.enterPassword(user.get("password"));
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        softAssert.assertAll();
        log.info("✅ Dashboard is displayed");

        log.info("📂 Opening PIM from the dashboard");
        dashboardPage.clickPimMenuLink();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("PIM")));
        softAssert.assertTrue(pimPage.getPIMTitle().isDisplayed());
        softAssert.assertAll();
        log.info("✅ PIM page is displayed");
    }
}