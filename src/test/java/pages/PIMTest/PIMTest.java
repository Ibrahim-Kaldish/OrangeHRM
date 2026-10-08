package pages.PIMTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.Dashboardpage.DashboardPage;
import pages.PIMPage.PIMPage;
import pages.addEmployeePage.AddEmployeePage;
import pages.baseTest.BaseTest;
import pages.loginPage.LoginPage;

import java.util.List;
import java.util.Map;

import static pages.baseTest.BaseTest.jsonFileManagerUsers;

public class PIMTest extends BaseTest {
    @Test
    public void searchForEmployee() throws InterruptedException {
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
    }

}
