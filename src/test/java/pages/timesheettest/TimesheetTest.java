package pages.timesheettest;

import dataproviders.DataProviderTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.basetest.BaseTest;
import pages.dashboard.DashboardPage;
import pages.timesheet.TimesheetPage;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Epic("Time Tracking")
@Feature("Timesheet")
public class TimesheetTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(TimesheetTest.class);
    private static final String PROJECT_NAME = "The Coca-Cola Company";

    private DashboardPage dashboardPage;
    private TimesheetPage timeSheetPage;

    private String activity;
    private List<String> enteredHours;

    private void initPages() {
        dashboardPage = new DashboardPage(driver);
        timeSheetPage = new TimesheetPage(driver);
    }

    private void employeeLogin(String... employee) {
        log.info("🔐 Logging in as employee {}", employee[4]);
        loginAs(employee[4], employee[5]);
        assertOnDashboard("Employee");
    }

    private void openTimesheet() {
        dashboardPage.clickTimeMenu();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.getCurrentUrl().contains("/time/"));

        softAssert.assertTrue(driver.getCurrentUrl().contains("/time/viewMyTimesheet"),
                "Expected Timesheets URL");
        softAssert.assertTrue(dashboardPage.getTimeMenuLink().getAttribute("class").contains("active"),
                "Time menu item should be highlighted as active");
    }

    private void selectCurrentWeekAndEdit() {
        timeSheetPage.enterCurrentWeek();

        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        softAssert.assertTrue(timeSheetPage.isWeekShown(
                        monday.getYear(), monday.getMonthValue(), monday.getDayOfMonth()),
                "Chosen week is not shown in the timesheet period field");

        timeSheetPage.clickEditAndWaitForNavigation();
        softAssert.assertTrue(driver.getCurrentUrl().contains("/time/editTimesheet"),
                "Edit button did not navigate to the timesheet edit page");
    }

    private void fillAndSaveTimesheet() {
        timeSheetPage.selectCocaColaProject();
        activity = timeSheetPage.selectRandomActivity();
        enteredHours = timeSheetPage.enterRandomHoursForEachDay();
        timeSheetPage.clickSaveButton();
        timeSheetPage.waitForScreen(2);
    }

    private void assertSavedTimesheet() {
        softAssert.assertTrue(timeSheetPage.getViewProjectName().contains(PROJECT_NAME),
                "Project on view page is not " + PROJECT_NAME);
        softAssert.assertEquals(timeSheetPage.getViewActivityName(), activity,
                "Activity on view page does not match the selected one");

        List<String> saved = timeSheetPage.getSavedHours();
        softAssert.assertEquals(saved.size(), 7, "Expected 7 saved day values");
        for (int i = 0; i < 7; i++) {
            softAssert.assertEquals(saved.get(i), enteredHours.get(i), "Saved hours differ for day " + (i + 1));
        }
    }

    private void submitTimesheet() {
        timeSheetPage.clickSubmitButton();
        timeSheetPage.waitForSubmittedStatus();

        softAssert.assertTrue(timeSheetPage.getSubmittedActionCard().isDisplayed(),
                "Submitted action card is not shown after submit");
        softAssert.assertTrue(timeSheetPage.getSubmittedActionCard().getText().contains("Submitted"),
                "Card does not show Submitted");
    }

    private void approveAsAdmin(String... employee) {
        log.info("👔 Logging in as admin to review the timesheet");
        loginAsAdmin();

        dashboardPage.clickTimeMenu();
        timeSheetPage.enterEmployeeName(employee[0] + " " + employee[1] + " " + employee[2]);
        timeSheetPage.clickViewEmployeeButton();

        assertSavedTimesheet();

        timeSheetPage.clickApproveButton();
        timeSheetPage.waitForScreen(2);
        softAssert.assertEquals(timeSheetPage.getApprovalStatus(), "Approved",
                "Approval status is not Approved");
    }

    @Test(dataProvider = "validEmployeesData", dataProviderClass = DataProviderTest.class)
    @Story("Employee submits a timesheet and admin approves it")
    @Description("As an employee, I want to fill in and submit my weekly timesheet so that my manager can review and approve it.")
    @Severity(SeverityLevel.CRITICAL)
    public void submitTimeSheet(String... employee) {
        log.info("🚀 Starting timesheet test for employee {}", employee[4]);

        initPages();
        employeeLogin(employee);
        openTimesheet();
        selectCurrentWeekAndEdit();
        fillAndSaveTimesheet();
        assertSavedTimesheet();
        submitTimesheet();
        dashboardPage.clickLogout();
        approveAsAdmin(employee);

        log.info("🏁 Timesheet test finished, checking results");
        softAssert.assertAll();
    }
}