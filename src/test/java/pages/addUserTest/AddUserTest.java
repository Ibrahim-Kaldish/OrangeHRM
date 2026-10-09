package pages.addUserTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.dashboardPage.DashboardPage;
import pages.addUser.AddUserPage;
import pages.baseTest.BaseTest;
import pages.loginPage.LoginPage;

import java.util.Map;

@Epic("User Management")
@Feature("Add User")
@Listeners(AllureTestNg.class)
public class AddUserTest extends BaseTest {

    @Test(dataProvider = "validUsernames", dataProviderClass = dataProviders.DataProviderTest.class)
    @Story("Admin creates a system user for an existing employee")
    @Description("As an admin, I want to create login accounts for existing employees so that they can sign in with the assigned role and status.")
    @Severity(SeverityLevel.CRITICAL)
    public void addAllValidUsers(String... user) throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        AddUserPage addUserPage = new AddUserPage(driver);

        Map<String, String> admin = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");

        loginPage.enterUsername(admin.get("username"));
        loginPage.enterPassword(admin.get("password"));
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        softAssert.assertAll();

        dashboardPage.clickAdminMenuItem();
        dashboardPage.clickHeaderSecondaryButton();

        softAssert.assertTrue(addUserPage.isPageDisplayed());

        addUserPage.selectEmployee(user[0]);
        addUserPage.selectUserRole(user[1]);
        addUserPage.selectStatus(user[2]);
        addUserPage.enterUsername(user[3]);
        addUserPage.enterPassword(user[4]);
        addUserPage.enterConfirmPassword(user[5]);
        addUserPage.clickSave();

        Thread.sleep(5000);

        softAssert.assertEquals(driver.getCurrentUrl(), jsonFileManagerUrls.getValueByKey("admin"));

        softAssert.assertAll();
    }
}