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
import pages.basetest.BaseTest;
import pages.dashboard.DashboardPage;
import pages.pim.PIMPage;

@Epic("Employee Management")
@Feature("PIM Search")
public class PIMSearchTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(PIMSearchTest.class);
    private static final String SUPERVISOR_SEARCH = "A";

    private DashboardPage dashboardPage;
    private PIMPage pimPage;

    private void initPages() {
        dashboardPage = new DashboardPage(driver);
        pimPage = new PIMPage(driver);
    }

    private void openPim() {
        dashboardPage.clickPimMenuLink();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("PIM")),
                "PIM page URL is incorrect after opening the PIM menu");
        softAssert.assertTrue(pimPage.getPIMTitle().isDisplayed(),
                "PIM title is not displayed");
    }

    private void searchByEmployee(String... employee) {
        pimPage.enterEmployeeName(employee[0] + " " + employee[1] + " " + employee[2]);
        pimPage.enterEmployeeId(employee[3]);
        pimPage.selectFromDropdown("Employment Status", employee[10]);
        pimPage.selectFromDropdown("Include", "Current Employees Only");
        pimPage.enterSupervisorName(SUPERVISOR_SEARCH);
        pimPage.selectFromDropdown("Job Title", employee[8]);
        pimPage.selectFromDropdown("Sub Unit", employee[9]);
        pimPage.clickSearch();
        pimPage.waitForSearchResults();
    }

    private boolean assertSingleResult(String... employee) {
        int count = pimPage.getResultCount();
        softAssert.assertEquals(count, 1, "Search should return exactly one employee for " + employee[4]);
        return count == 1;
    }

    private void assertResultRow(String... employee) {
        String row = pimPage.getDisplayedResultRows().get(0).getText();
        softAssert.assertTrue(row.contains(employee[3]), "Employee Id missing: " + employee[3]);
        softAssert.assertTrue(row.contains(employee[0] + " " + employee[1]), "First and middle name missing");
        softAssert.assertTrue(row.contains(employee[2]), "Last name missing: " + employee[2]);
        softAssert.assertTrue(row.contains(employee[8]), "Job title missing: " + employee[8]);
        softAssert.assertTrue(row.contains(employee[9]), "Sub unit missing: " + employee[9]);
        softAssert.assertTrue(row.contains(employee[10]), "Employment status missing: " + employee[10]);
    }

    private void openFirstResult(String... employee) {
        pimPage.clickFirstResult();
        softAssert.assertTrue(driver.getCurrentUrl().contains("viewPersonalDetails"),
                "Personal details page did not open for " + employee[4]);
        pimPage.waitForScreen(3);
    }

    @Test(dataProvider = "validEmployeesData", dataProviderClass = DataProviderTest.class)
    @Story("Admin searches the employee list in PIM")
    @Description("As an admin, I want to search PIM by employee name, Id, and filters so that I can find the right employee record.")
    @Severity(SeverityLevel.NORMAL)
    public void searchForEmployee(String... employee) {
        log.info("🔎 Starting PIM employee search");

        initPages();
        loginAsAdmin();
        openPim();
        searchByEmployee(employee);

        if (assertSingleResult(employee)) {
            assertResultRow(employee);
            openFirstResult(employee);
        }

        log.info("✅ PIM search test finished");
        softAssert.assertAll();
    }
}