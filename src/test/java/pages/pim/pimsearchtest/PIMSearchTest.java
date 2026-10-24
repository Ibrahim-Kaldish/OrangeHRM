package pages.pim.pimsearchtest;

import dataproviders.DataProviderTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import pages.pim.PIMPage;
import pages.basetest.BaseTest;
import pages.dashboardPage.DashboardPage;
import pages.login.LoginPage;
import java.util.Map;

@Epic("Employee Management")
@Feature("PIM Search")
public class PIMSearchTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(PIMSearchTest.class);

    @Test (dataProvider = "validEmployeesData", dataProviderClass = DataProviderTest.class)
    @Story("Admin searches the employee list in PIM")
    @Description("As an admin, I want to search PIM by employee name, Id, and filters so that I can find the right employee record.")
    @Severity(SeverityLevel.NORMAL)
    public void searchForEmployee(String ...employee) {
        log.info("🔎 Starting PIM employee search");

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        PIMPage pimPage = new PIMPage(driver);

        Map<String, String> admin = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");
        loginPage.enterUsername(admin.get("username"));
        loginPage.enterPassword(admin.get("password"));
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        softAssert.assertAll();

        dashboardPage.clickPimMenuLink();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("PIM")));
        softAssert.assertTrue(pimPage.getPIMTitle().isDisplayed());

        pimPage.enterEmployeeName(employee[0] + " " + employee[1] + " " + employee[2]);
        pimPage.enterEmployeeId(employee[3]);
        pimPage.selectFromDropdown("Employment Status", employee[10]);
        pimPage.selectFromDropdown("Include", "Current Employees Only");
        pimPage.enterSupervisorName("A");
        pimPage.selectFromDropdown("Job Title", employee[8]);
        pimPage.selectFromDropdown("Sub Unit", employee[9]);
        pimPage.clickSearch();

        pimPage.waitForSearchResults();

        int count = pimPage.getResultCount();
        softAssert.assertEquals(count, 1, "Search should return exactly one employee");

        if (count != 1) {
            return;
        }

        String row = pimPage.getDisplayedResultRows().get(0).getText();
        softAssert.assertTrue(row.contains(employee[3]), "Employee Id missing: " + employee[3]);
        softAssert.assertTrue(row.contains(employee[0] + " " + employee[1]), "First and middle name missing");
        softAssert.assertTrue(row.contains(employee[2]), "Last name missing: " + employee[2]);
        softAssert.assertTrue(row.contains(employee[8]), "Job title missing: " + employee[8]);
        softAssert.assertTrue(row.contains(employee[9]), "Sub unit missing: " + employee[9]);
        softAssert.assertTrue(row.contains(employee[10]), "Employment status missing: " + employee[10]);

        pimPage.clickFirstResult();
        softAssert.assertTrue(driver.getCurrentUrl().contains("viewPersonalDetails"));
        pimPage.waitForScreen(3);

        softAssert.assertAll();

        log.info("✅ PIM search test finished");
    }
}