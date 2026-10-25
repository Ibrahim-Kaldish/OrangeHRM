package pages.pim.addemployeetest;

import dataproviders.DataProviderTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.AllureTestNg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.addemployee.AddEmployeePage;
import pages.basetest.BaseTest;
import pages.dashboard.DashboardPage;
import pages.employeeinfo.JobInfoPage;
import pages.employeeinfo.ReportToPage;
import pages.login.LoginPage;
import pages.pim.PIMPage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Epic("Employee Management")
@Feature("Add Employee")
@Listeners(AllureTestNg.class)
public class AddEmployeeTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(AddEmployeeTest.class);

    @Test(dataProvider = "validEmployeesData", dataProviderClass = DataProviderTest.class)
    @Story("Admin adds a new employee with login details")
    @Description("As an admin, I want to add a new employee with login details so that the employee can sign in and use the system.")
    @Severity(SeverityLevel.CRITICAL)
    public void addEmployee(String... employee) {
        log.info("🚀 Starting add employee test for username {}", employee[4]);

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        PIMPage pimPage = new PIMPage(driver);
        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);
        JobInfoPage jobInfoPage = new JobInfoPage(driver);
        ReportToPage reportToPage = new ReportToPage(driver);

        Map<String, String> admin = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");

        loginPage.enterUsername(admin.get("username"));
        loginPage.enterPassword(admin.get("password"));
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("dashboard")),
                "Admin login did not redirect to the dashboard");
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed(),
                "Dashboard title is not displayed after admin login");

        dashboardPage.clickPimMenuLink();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("PIM")),
                "PIM page URL is incorrect after opening the PIM menu");
        softAssert.assertTrue(pimPage.getPIMTitle().isDisplayed(),
                "PIM title is not displayed");

        pimPage.clickAddEmployeeLink();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("addEmployee")),
                "Add Employee page URL is incorrect");

        addEmployeePage.enterFirstName(employee[0]);
        addEmployeePage.enterMiddleName(employee[1]);
        addEmployeePage.enterLastName(employee[2]);
        addEmployeePage.enterEmployeeId(employee[3]);

        String defaultPhotoSrc = addEmployeePage.getPhotoPreviewSrc();

        Path photo = Paths.get("src", "main", "resources", "photos", employee[32] + ".jpeg");
        addEmployeePage.clickAddPhotoAndSelect(photo.toAbsolutePath().toString());
        addEmployeePage.waitForScreen();
        String uploadedPhotoSrc = addEmployeePage.getPhotoPreviewSrc();
        softAssert.assertNotNull(uploadedPhotoSrc, "No photo preview after uploading the photo");
        softAssert.assertNotEquals(uploadedPhotoSrc, defaultPhotoSrc,
                "Photo preview did not change after uploading the photo");

        addEmployeePage.clickCreateLoginDetails();
        addEmployeePage.enterUsername(employee[4]);
        addEmployeePage.selectEnabled();
        WebElement enabled = driver.findElement(By.xpath("//label[normalize-space()='Enabled']//input[@type='radio']"));

        softAssert.assertTrue(enabled.isSelected(),
                "Enabled status radio button is not selected");

        addEmployeePage.enterPassword(employee[5]);
        addEmployeePage.enterConfirmPassword(employee[5]);
        addEmployeePage.clickSave();

        pimPage.waitForScreen(2);

        softAssert.assertTrue(driver.getCurrentUrl().contains("viewPersonalDetails"),
                "Employee was not saved and the personal details page did not open for " + employee[4]);

        String jobTitle = employee[8];
        String subUnit = employee[9];
        String employmentStatus = employee[10];
        String reportingMethod = employee[12];

        jobInfoPage.clickJobTab();
        softAssert.assertTrue(driver.getCurrentUrl().contains("viewJobDetails"),
                "Job Details page did not open");
        jobInfoPage.selectJobTitle(jobTitle);
        jobInfoPage.selectSubUnit(subUnit);
        jobInfoPage.selectEmploymentStatus(employmentStatus);
        jobInfoPage.clickJobSave();

        reportToPage.clickReportToTab();
        softAssert.assertTrue(driver.getCurrentUrl().contains("viewReportToDetails"),
                "Report-to page did not open");
        reportToPage.clickAddSupervisor();
        reportToPage.enterSupervisorName("A");
        reportToPage.selectFirstSupervisor();
        reportToPage.selectReportingMethod(reportingMethod);
        reportToPage.clickSave();

        dashboardPage.clickLogout();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("url")),
                "Logout did not return to the login page");

        loginPage.enterUsername(employee[4]);
        loginPage.enterPassword(employee[5]);
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("dashboard")),
                "New employee login did not redirect to the dashboard");
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed(),
                "Dashboard title is not displayed after the new employee logged in");

        log.info("🏁 Add employee test finished for username {}", employee[4]);
        softAssert.assertAll();
    }
}