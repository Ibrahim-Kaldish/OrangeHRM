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
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.addemployee.AddEmployeePage;
import pages.basetest.BaseTest;
import pages.dashboard.DashboardPage;
import pages.employeeinfo.JobInfoPage;
import pages.employeeinfo.ReportToPage;
import pages.pim.PIMPage;
import utils.ResourceUtil;

@Epic("Employee Management")
@Feature("Add Employee")
@Listeners(AllureTestNg.class)
public class AddEmployeeTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(AddEmployeeTest.class);
    private static final String SUPERVISOR_SEARCH = "A";

    private DashboardPage dashboardPage;
    private PIMPage pimPage;
    private AddEmployeePage addEmployeePage;
    private JobInfoPage jobInfoPage;
    private ReportToPage reportToPage;

    private void initPages() {
        dashboardPage = new DashboardPage(driver);
        pimPage = new PIMPage(driver);
        addEmployeePage = new AddEmployeePage(driver);
        jobInfoPage = new JobInfoPage(driver);
        reportToPage = new ReportToPage(driver);
    }

    private void openAddEmployeeForm() {
        dashboardPage.clickPimMenuLink();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("PIM")),
                "PIM page URL is incorrect after opening the PIM menu");
        softAssert.assertTrue(pimPage.getPIMTitle().isDisplayed(),
                "PIM title is not displayed");

        pimPage.clickAddEmployeeLink();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("addEmployee")),
                "Add Employee page URL is incorrect");
    }

    private void fillPersonalDetails(String... employee) {
        addEmployeePage.enterFirstName(employee[0]);
        addEmployeePage.enterMiddleName(employee[1]);
        addEmployeePage.enterLastName(employee[2]);
        addEmployeePage.enterEmployeeId(employee[3]);
    }

    private void uploadPhoto(String photoPath) {
        String defaultPhotoSrc = addEmployeePage.getPhotoPreviewSrc();

        addEmployeePage.clickAddPhotoAndSelect(photoPath);
        addEmployeePage.waitForScreen();

        String uploadedPhotoSrc = addEmployeePage.getPhotoPreviewSrc();
        softAssert.assertNotNull(uploadedPhotoSrc, "No photo preview after uploading the photo");
        softAssert.assertNotEquals(uploadedPhotoSrc, defaultPhotoSrc,
                "Photo preview did not change after uploading the photo");
    }

    private void createLoginDetails(String... employee) {
        addEmployeePage.clickCreateLoginDetails();
        addEmployeePage.enterUsername(employee[4]);
        addEmployeePage.selectEnabled();
        softAssert.assertTrue(addEmployeePage.isEnabledSelected(),
                "Enabled status radio button is not selected");

        addEmployeePage.enterPassword(employee[5]);
        addEmployeePage.enterConfirmPassword(employee[5]);
        addEmployeePage.clickSave();

        pimPage.waitForScreen(2);
        softAssert.assertTrue(driver.getCurrentUrl().contains("viewPersonalDetails"),
                "Employee was not saved and the personal details page did not open for " + employee[4]);
    }

    private void assignJobDetails(String... employee) {
        jobInfoPage.clickJobTab();
        softAssert.assertTrue(driver.getCurrentUrl().contains("viewJobDetails"),
                "Job Details page did not open");

        jobInfoPage.selectJobTitle(employee[8]);
        jobInfoPage.selectSubUnit(employee[9]);
        jobInfoPage.selectEmploymentStatus(employee[10]);
        jobInfoPage.clickJobSave();
    }

    private void assignReportTo(String... employee) {
        reportToPage.clickReportToTab();
        softAssert.assertTrue(driver.getCurrentUrl().contains("viewReportToDetails"),
                "Report-to page did not open");

        reportToPage.clickAddSupervisor();
        reportToPage.enterSupervisorName(SUPERVISOR_SEARCH);
        reportToPage.selectFirstSupervisor();
        reportToPage.selectReportingMethod(employee[12]);
        reportToPage.clickSave();
    }

    private void logoutAndVerify() {
        dashboardPage.clickLogout();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("url")),
                "Logout did not return to the login page");
    }

    private void verifyNewEmployeeLogin(String... employee) {
        loginAs(employee[4], employee[5]);
        assertOnDashboard("New employee");
    }

    @Test(dataProvider = "validEmployeesData", dataProviderClass = DataProviderTest.class)
    @Story("Admin adds a new employee with login details")
    @Description("As an admin, I want to add a new employee with login details so that the employee can sign in and use the system.")
    @Severity(SeverityLevel.CRITICAL)
    public void addEmployee(String... employee) {
        log.info("🚀 Starting add employee test for username {}", employee[4]);

        initPages();
        loginAsAdmin();
        openAddEmployeeForm();
        fillPersonalDetails(employee);
        uploadPhoto(ResourceUtil.absolutePath("photos/" + employee[32] + ".jpeg"));
        createLoginDetails(employee);
        assignJobDetails(employee);
        assignReportTo(employee);
        logoutAndVerify();
        verifyNewEmployeeLogin(employee);

        log.info("🏁 Add employee test finished for username {}", employee[4]);
        softAssert.assertAll();
    }
}