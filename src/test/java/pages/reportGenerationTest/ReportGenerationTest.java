package pages.reportGenerationTest;

import dataProviders.DataProviderTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import pages.PIMPage.PIMPage;
import pages.baseTest.BaseTest;
import pages.dashboardPage.DashboardPage;
import pages.employeeInfoPage.ContactDetailsPage;
import pages.employeeInfoPage.DependentsPage;
import pages.employeeInfoPage.EmergencyContactsPage;
import pages.employeeInfoPage.PersonalDetailsPage;
import pages.generateReport.ReportGenerationPage;
import pages.loginPage.LoginPage;

import java.util.Map;

public class ReportGenerationTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(ReportGenerationTest.class);
    LoginPage loginPage;
    DashboardPage dashboardPage;
    PersonalDetailsPage personalDetailsPage;
    EmergencyContactsPage emergencyContactsPage;
    ContactDetailsPage contactDetailsPage;
    DependentsPage dependentsPage;
    PIMPage pimPage;
    ReportGenerationPage reportGenerationPage;

    public void setAttributes(String ... employeeData){
        log.info("📋 Loading employee data for username {}", employeeData[4]);
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        personalDetailsPage = new PersonalDetailsPage(driver);
        emergencyContactsPage = new EmergencyContactsPage(driver);
        contactDetailsPage = new ContactDetailsPage(driver);
        dependentsPage = new DependentsPage(driver);
        pimPage = new PIMPage(driver);
        reportGenerationPage = new ReportGenerationPage(driver);
    }

    public void loginAsAdmin(){

        log.info("🔐 Logging in as admin");
        Map<String, String> admin = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");

        loginPage.enterUsername(admin.get("username"));
        loginPage.enterPassword(admin.get("password"));
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        log.info("✅ Admin logged in");
    }

    @Test(dataProvider = "validEmployeesData", dataProviderClass = DataProviderTest.class)
    public void generateReport(String ...employee) {

        setAttributes(employee);

        loginAsAdmin();

        dashboardPage.clickPIMBtn();
        dashboardPage.waitForScreen();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("PIM")),
                "PIM page URL is incorrect");
        softAssert.assertTrue(pimPage.getPIMTitle().isDisplayed(),
                "PIM title is not displayed");

        dashboardPage.clickReportsTab();
        dashboardPage.waitForScreen();
        softAssert.assertTrue(reportGenerationPage.isEmployeeReportsTitleDisplayed(),
                "Employee Reports title is not displayed");
        softAssert.assertEquals(reportGenerationPage.getEmployeeReportsTitle().getText(), "Employee Reports",
                "Employee Reports title text is incorrect");

        String reportName = (String) jsonFileManagerData.getValueByKey("reportName");
        reportGenerationPage.enterReportName(reportName);
        reportGenerationPage.selectReport(reportName);
        reportGenerationPage.clickSearch();

        reportGenerationPage.clickEditReport();
        reportGenerationPage.waitForScreen();
        softAssert.assertTrue(driver.getCurrentUrl().contains("definePredefinedReport"),
                "Report did not open the definePredefinedReport page");

        reportGenerationPage.selectSelectionCriteria("Job Title");
        reportGenerationPage.clickSelectionCriteriaAdd();
        reportGenerationPage.waitForScreen();
        reportGenerationPage.selectJobTitleCriteria(employee[8]);

        reportGenerationPage.selectSelectionCriteria("Sub Unit");
        reportGenerationPage.clickSelectionCriteriaAdd();
        reportGenerationPage.waitForScreen();
        reportGenerationPage.selectSubUnitCriteria(employee[9]);

        reportGenerationPage.selectSelectionCriteria("Employment Status");
        reportGenerationPage.clickSelectionCriteriaAdd();
        reportGenerationPage.waitForScreen();
        reportGenerationPage.selectEmploymentStatusCriteria(employee[10]);

        reportGenerationPage.selectDisplayField("Contact Details");
        reportGenerationPage.clickDisplayFieldAdd();
        reportGenerationPage.waitForScreen();

        reportGenerationPage.selectDisplayField("Job");
        reportGenerationPage.clickDisplayFieldAdd();
        reportGenerationPage.waitForScreen();

        reportGenerationPage.clickSave();

        reportGenerationPage.waitForScreen();

        softAssert.assertTrue(driver.getCurrentUrl().contains("displayPredefinedReport"),
                "Report did not open the displayPredefinedReport page");

        reportGenerationPage.waitForScreen();

        softAssert.assertAll();
    }
}