package pages.timeSheetTest;

import dataProviders.DataProviderTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import pages.baseTest.BaseTest;
import pages.dashboardPage.DashboardPage;
import pages.loginPage.LoginPage;
import pages.timeSheetPage.TimeSheetPage;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Epic("Time Tracking")
@Feature("Timesheet")
public class TimeSheetTest extends BaseTest {

    LoginPage loginPage;
    DashboardPage dashboardPage ;
    TimeSheetPage timeSheetPage ;

    public void savedHoursAssert(List<String> entered){
        List<String> saved = timeSheetPage.getSavedHours();

        softAssert.assertEquals(saved.size(), 7, "Expected 7 saved day values");
        for (int i = 0; i < 7; i++) {
            softAssert.assertEquals(saved.get(i), entered.get(i), "Saved hours differ for day " + (i + 1));
        }
    }

    public void projectNameAssert(){
        softAssert.assertTrue(timeSheetPage.getViewProjectName().contains("The Coca-Cola Company"),
                "Project on view page is not Coca-Cola");
    }

    public void selectedActivityAssert(String activity){
        softAssert.assertEquals(timeSheetPage.getViewActivityName(), activity,
                "Activity on view page does not match the selected one");
    }

    @Test(dataProvider = "validEmployeesData", dataProviderClass = DataProviderTest.class)
    @Story("Employee submits a timesheet and admin approves it")
    @Description("As an employee, I want to fill in and submit my weekly timesheet so that my manager can review and approve it.")
    @Severity(SeverityLevel.CRITICAL)
    public void submitTimeSheet(String ...employee){

        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        timeSheetPage = new TimeSheetPage(driver);

        loginPage.enterUsername(employee[4]);
        loginPage.enterPassword(employee[5]);
        loginPage.clickLogin();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());

        dashboardPage.clickTimeMenu();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.getCurrentUrl().contains("/time/"));

        softAssert.assertTrue(driver.getCurrentUrl().contains("/time/viewMyTimesheet"),
                "Expected Timesheets URL");
        softAssert.assertTrue(dashboardPage.getTimeMenu().getAttribute("class").contains("active"),
                "Time menu item should be highlighted as active");

        timeSheetPage.enterCurrentWeek();

        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);

        softAssert.assertTrue(timeSheetPage.isWeekShown(
                        monday.getYear(), monday.getMonthValue(), monday.getDayOfMonth()),
                "Chosen week is not shown in the timesheet period field");

        timeSheetPage.clickEditAndWaitForNavigation();

        softAssert.assertTrue(driver.getCurrentUrl().contains("/time/editTimesheet"),
                "Edit button did not navigate to the timesheet edit page");

        timeSheetPage.selectCocaColaProject();
        String activity = timeSheetPage.selectRandomActivity();

        List<String> entered = timeSheetPage.enterRandomHoursForEachDay();
        timeSheetPage.clickSaveButton();

        timeSheetPage.waitForScreen(5);
        projectNameAssert();
        selectedActivityAssert(activity);
        savedHoursAssert(entered);

        timeSheetPage.clickSubmitButton();
        timeSheetPage.waitForSubmittedStatus();

        softAssert.assertTrue(timeSheetPage.getSubmittedActionCard().isDisplayed(),
                "Submitted action card is not shown after submit");
        softAssert.assertTrue(timeSheetPage.getSubmittedActionCard().getText().contains("Submitted"),
                "Card does not show Submitted");

        dashboardPage.clickLogout();

        Map<String, String> admin = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");

        loginPage.enterUsername(admin.get("username"));
        loginPage.enterPassword(admin.get("password"));
        loginPage.clickLogin();

        dashboardPage.clickTimeMenu();

        timeSheetPage.enterEmployeeName(employee[0] + " " + employee[1] + " " + employee[2]);

        timeSheetPage.clickViewEmployeeButton();

        projectNameAssert();
        selectedActivityAssert(activity);
        savedHoursAssert(entered);

        timeSheetPage.clickApproveButton();
        timeSheetPage.waitForScreen(2);
        softAssert.assertEquals(timeSheetPage.getApprovalStatus(), "Approved",
                "Approval status is not Approved");

        softAssert.assertAll();

    }
}