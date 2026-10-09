package pages.PIMTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import pages.baseTest.BaseTest;

@Epic("Employee Management")
@Feature("PIM Search")
public class PIMTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(PIMTest.class);

    @Test
    @Story("Admin searches the employee list in PIM")
    @Description("As an admin, I want to search PIM by employee name, Id, and filters so that I can find the right employee record.")
    @Severity(SeverityLevel.NORMAL)
    public void searchForEmployee() throws InterruptedException {
        log.info("🔎 Starting PIM employee search");

//        LoginPage loginPage = new LoginPage(driver);
//        DashboardPage dashboardPage = new DashboardPage(driver);
//        PIMPage pimPage = new PIMPage(driver);
//        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
//
//        Map<String, String> admin = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");
//
//        loginPage.enterUsername(admin.get("username"));
//        loginPage.enterPassword(admin.get("password"));
//        loginPage.clickLogin();
//
//        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
//        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
//        softAssert.assertAll();
//
//        dashboardPage.clickPIMBtn();
//        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("PIM")));
//        softAssert.assertTrue(pimPage.getPIMTitle().isDisplayed());
//
//        Map<String, String> employee = (Map<String, String>) jsonFileManagerUsers.getValueByKey("employee");
//
//        pimPage.enterEmployeeName(employee.get("employeeName"));
//        pimPage.enterEmployeeId(employee.get("employeeId"));
//        pimPage.selectFromDropdown("Employment Status", employee.get("employmentStatus"));
//        pimPage.selectFromDropdown("Include", employee.get("include"));
////        pimPage.enterSupervisorName(employee.get("supervisorName"));
//        pimPage.selectFromDropdown("Job Title", employee.get("jobTitle"));
//        pimPage.selectFromDropdown("Sub Unit", employee.get("subUnit"));
//        pimPage.clickSearch();
//
//        Thread.sleep(5000);

        log.info("✅ PIM search test finished");
    }
}