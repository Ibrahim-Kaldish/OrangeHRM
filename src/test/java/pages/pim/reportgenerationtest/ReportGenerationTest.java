package pages.pim.reportgenerationtest;

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
import pages.generatereport.ReportGenerationPage;
import pages.pim.PIMPage;

@Epic("Employee Management")
@Feature("Reports")
public class ReportGenerationTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(ReportGenerationTest.class);
    private static final String PDF_NAME = "Employee Contact info report.pdf";

    private DashboardPage dashboardPage;
    private PIMPage pimPage;
    private ReportGenerationPage reportGenerationPage;

    private void initPages() {
        dashboardPage = new DashboardPage(driver);
        pimPage = new PIMPage(driver);
        reportGenerationPage = new ReportGenerationPage(driver);
    }

    private void openPim(String employeeUsername) {
        log.info("🧭 Opening PIM from the dashboard");
        dashboardPage.clickPimMenuLink();
        dashboardPage.waitForScreen();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("PIM")),
                "PIM page URL is incorrect for " + employeeUsername);
        softAssert.assertTrue(pimPage.getPIMTitle().isDisplayed(),
                "PIM title is not displayed for " + employeeUsername);
    }

    private void openReportsTab(String employeeUsername) {
        log.info("📊 Opening the Reports tab");
        dashboardPage.clickReportsTab();
        dashboardPage.waitForScreen();
        softAssert.assertTrue(reportGenerationPage.isEmployeeReportsTitleDisplayed(),
                "Employee Reports title is not displayed for " + employeeUsername);
        softAssert.assertEquals(reportGenerationPage.getEmployeeReportsTitle().getText(), "Employee Reports",
                "Employee Reports title text is incorrect for " + employeeUsername);
    }

    private void openPredefinedReportForEditing(String reportName, String employeeUsername) {
        log.info("🔎 Searching for predefined report '{}'", reportName);
        reportGenerationPage.enterReportName(reportName);
        reportGenerationPage.selectReport(reportName);
        reportGenerationPage.clickSearch();

        log.info("✏️ Opening the report for editing");
        reportGenerationPage.clickEditReport();
        reportGenerationPage.waitForScreen();
        softAssert.assertTrue(driver.getCurrentUrl().contains("definePredefinedReport"),
                "Report did not open the definePredefinedReport page for " + employeeUsername);
    }

    private void addSelectionCriteria(String criteriaName, String value, SelectionCriteriaAction action) {
        log.info("🎯 Adding selection criteria: {} = '{}'", criteriaName, value);
        reportGenerationPage.selectSelectionCriteria(criteriaName);
        reportGenerationPage.clickSelectionCriteriaAdd();
        reportGenerationPage.waitForScreen();
        action.apply(value);
    }

    @FunctionalInterface
    private interface SelectionCriteriaAction {
        void apply(String value);
    }

    private void addAllSelectionCriteria(String[] employee) {
        addSelectionCriteria("Job Title", employee[8], reportGenerationPage::selectJobTitleCriteria);
        addSelectionCriteria("Sub Unit", employee[9], reportGenerationPage::selectSubUnitCriteria);
        addSelectionCriteria("Employment Status", employee[10], reportGenerationPage::selectEmploymentStatusCriteria);
    }

    private void addJobDisplayField() {
        log.info("🧾 Adding display field group: Job");
        reportGenerationPage.selectDisplayField("Job");
        reportGenerationPage.clickDisplayFieldAdd();
        reportGenerationPage.waitForScreen();
    }

    private void saveAndOpenDisplayedReport(String employeeUsername) {
        log.info("💾 Saving the predefined report");
        reportGenerationPage.clickSave();
        reportGenerationPage.waitForScreen(4);
        softAssert.assertTrue(driver.getCurrentUrl().contains("displayPredefinedReport"),
                "Report did not open the displayPredefinedReport page for " + employeeUsername);

        reportGenerationPage.clickExpandTable();
        reportGenerationPage.waitForScreen(4);
    }

    private void saveReportAsPdf(String employeeUsername) {
        reportGenerationPage.saveReportAsPdf(PDF_NAME);
        softAssert.assertTrue(reportGenerationPage.isPdfSaved(PDF_NAME),
                "PDF was not saved or is not a valid PDF: " + PDF_NAME + " for " + employeeUsername);
    }

    @Test(dataProvider = "validEmployeesData", dataProviderClass = DataProviderTest.class)
    @Story("Admin generates a predefined report filtered by job details")
    @Description("As an admin, I want to generate a predefined report filtered by job title, sub unit, and employment status so that I can review matching employees.")
    @Severity(SeverityLevel.CRITICAL)
    public void generateReport(String... employee) {
        String employeeUsername = employee[4];
        log.info("🚀 Starting report generation test");

        initPages();
        loginAsAdmin();
        openPim(employeeUsername);
        openReportsTab(employeeUsername);

        String reportName = (String) jsonFileManagerData.getValueByKey("reportName");
        openPredefinedReportForEditing(reportName, employeeUsername);

        addAllSelectionCriteria(employee);
        addJobDisplayField();
        saveAndOpenDisplayedReport(employeeUsername);
        saveReportAsPdf(employeeUsername);

        log.info("🏁 Report generation test finished, checking results");
        softAssert.assertAll();
    }
}