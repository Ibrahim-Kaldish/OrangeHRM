package pages.PIMTest.reportGenerationTest;

import dataProviders.DataProviderTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
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

@Epic("Employee Management")
@Feature("Reports")
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
        log.info("📋 Loading page objects for employee username {}", employeeData[4]);
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

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("dashboard")),
                "Admin login did not redirect to the dashboard");
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed(),
                "Dashboard title is not displayed after admin login");
        log.info("✅ Admin logged in");
    }

    @Test(dataProvider = "validEmployeesData", dataProviderClass = DataProviderTest.class)
    @Story("Admin generates a predefined report filtered by job details")
    @Description("As an admin, I want to generate a predefined report filtered by job title, sub unit, and employment status so that I can review matching employees.")
    @Severity(SeverityLevel.CRITICAL)
    public void generateReport(String ...employee) {
        log.info("🚀 Starting report generation test");

        setAttributes(employee);

        loginAsAdmin();

        log.info("🧭 Opening PIM from the dashboard");
        dashboardPage.clickPIMBtn();
        dashboardPage.waitForScreen();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("PIM")),
                "PIM page URL is incorrect");
        softAssert.assertTrue(pimPage.getPIMTitle().isDisplayed(),
                "PIM title is not displayed");

        log.info("📊 Opening the Reports tab");
        dashboardPage.clickReportsTab();
        dashboardPage.waitForScreen();
        softAssert.assertTrue(reportGenerationPage.isEmployeeReportsTitleDisplayed(),
                "Employee Reports title is not displayed");
        softAssert.assertEquals(reportGenerationPage.getEmployeeReportsTitle().getText(), "Employee Reports",
                "Employee Reports title text is incorrect");

        String reportName = (String) jsonFileManagerData.getValueByKey("reportName");
        log.info("🔎 Searching for predefined report '{}'", reportName);
        reportGenerationPage.enterReportName(reportName);
        reportGenerationPage.selectReport(reportName);
        reportGenerationPage.clickSearch();

        log.info("✏️ Opening the report for editing");
        reportGenerationPage.clickEditReport();
        reportGenerationPage.waitForScreen();
        softAssert.assertTrue(driver.getCurrentUrl().contains("definePredefinedReport"),
                "Report did not open the definePredefinedReport page");

        log.info("🎯 Adding selection criteria: Job Title = '{}'", employee[8]);
        reportGenerationPage.selectSelectionCriteria("Job Title");
        reportGenerationPage.clickSelectionCriteriaAdd();
        reportGenerationPage.waitForScreen();
        reportGenerationPage.selectJobTitleCriteria(employee[8]);

        log.info("🎯 Adding selection criteria: Sub Unit = '{}'", employee[9]);
        reportGenerationPage.selectSelectionCriteria("Sub Unit");
        reportGenerationPage.clickSelectionCriteriaAdd();
        reportGenerationPage.waitForScreen();
        reportGenerationPage.selectSubUnitCriteria(employee[9]);

        log.info("🎯 Adding selection criteria: Employment Status = '{}'", employee[10]);
        reportGenerationPage.selectSelectionCriteria("Employment Status");
        reportGenerationPage.clickSelectionCriteriaAdd();
        reportGenerationPage.waitForScreen();
        reportGenerationPage.selectEmploymentStatusCriteria(employee[10]);

        log.info("🧾 Adding display field group: Job");
        reportGenerationPage.selectDisplayField("Job");
        reportGenerationPage.clickDisplayFieldAdd();
        reportGenerationPage.waitForScreen();

        log.info("💾 Saving the predefined report");
        reportGenerationPage.clickSave();
        reportGenerationPage.waitForScreen(4);

        softAssert.assertTrue(driver.getCurrentUrl().contains("displayPredefinedReport"),
                "Report did not open the displayPredefinedReport page");

        reportGenerationPage.waitForScreen(4);


        log.info("🏁 Report generation test finished, checking results");
        softAssert.assertAll();
    }
}