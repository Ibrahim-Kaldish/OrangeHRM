package pages.addusertest;

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
import pages.dashboardPage.DashboardPage;
import pages.adduser.AddUserPage;
import pages.basetest.BaseTest;
import pages.login.LoginPage;
import java.util.Map;

@Epic("User Management")
@Feature("Add User")
@Listeners(AllureTestNg.class)
public class AddUserTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(AddUserTest.class);

    @Test(dataProvider = "validUsernames", dataProviderClass = dataproviders.DataProviderTest.class)
    @Story("Admin creates a system user for an existing employee")
    @Description("As an admin, I want to create login accounts for existing employees so that they can sign in with the assigned role and status.")
    @Severity(SeverityLevel.CRITICAL)
    public void addAllValidUsers(String... user) {
        log.info("🚀 Starting add user test for username {}", user[3]);
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        AddUserPage addUserPage = new AddUserPage(driver);

        Map<String, String> admin = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");

        loginPage.enterUsername(admin.get("username"));
        loginPage.enterPassword(admin.get("password"));
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerData.getValueByKey("dashboard")));
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
        log.info("💾 Saving new user {}", user[3]);
        addUserPage.clickSave();

        addUserPage.waitForScreen(5);
        softAssert.assertEquals(driver.getCurrentUrl(), jsonFileManagerData.getValueByKey("admin"));

        log.info("🏁 Add user test finished, checking results");
        softAssert.assertAll();
    }
}