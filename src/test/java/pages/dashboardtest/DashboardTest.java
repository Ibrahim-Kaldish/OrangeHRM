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
import pages.pim.PIMPage;

@Epic("Navigation")
@Feature("Dashboard")
public class DashboardTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(DashboardTest.class);

    private DashboardPage dashboardPage;
    private PIMPage pimPage;

    private void initPages() {
        dashboardPage = new DashboardPage(driver);
        pimPage = new PIMPage(driver);
    }

    @Test
    @Story("Admin navigates from the dashboard to PIM")
    @Description("As an admin, I want to open PIM from the dashboard so that I can manage employee records.")
    @Severity(SeverityLevel.NORMAL)
    public void navigateToPim() {
        initPages();
        loginAsAdmin();

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