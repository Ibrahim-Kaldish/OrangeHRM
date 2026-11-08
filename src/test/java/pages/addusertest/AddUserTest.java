package pages.addusertest;

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
import pages.adduser.AddUserPage;
import pages.basetest.BaseTest;
import pages.dashboard.DashboardPage;

@Epic("User Management")
@Feature("Add User")
@Listeners(AllureTestNg.class)
public class AddUserTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(AddUserTest.class);

    private DashboardPage dashboardPage;
    private AddUserPage addUserPage;

    private void initPages() {
        dashboardPage = new DashboardPage(driver);
        addUserPage = new AddUserPage(driver);
    }

    private void openAddUserForm() {
        dashboardPage.clickAdminMenuItem();
        dashboardPage.clickHeaderSecondaryButton();

        softAssert.assertTrue(addUserPage.isPageDisplayed(),
                "Add User page is not displayed after clicking the Add button");
    }

    private void fillUserForm(String... user) {
        addUserPage.selectEmployee(user[0]);
        addUserPage.selectUserRole(user[1]);
        addUserPage.selectStatus(user[2]);
        addUserPage.enterUsername(user[3]);
        addUserPage.enterPassword(user[4]);
        addUserPage.enterConfirmPassword(user[5]);
    }

    private void saveUser(String username) {
        log.info("💾 Saving new user {}", username);
        addUserPage.clickSave();
        addUserPage.waitForScreen(5);
    }

    private void assertUserSaved(String username) {
        softAssert.assertEquals(driver.getCurrentUrl(), jsonFileManagerData.getValueByKey("admin"),
                "User was not saved and the page did not return to the Admin list for username " + username);
    }

    @Test(dataProvider = "validUsernames", dataProviderClass = DataProviderTest.class)
    @Story("Admin creates a system user for an existing employee")
    @Description("As an admin, I want to create login accounts for existing employees so that they can sign in with the assigned role and status.")
    @Severity(SeverityLevel.CRITICAL)
    public void addAllValidUsers(String... user) {
        log.info("🚀 Starting add user test for username {}", user[3]);

        initPages();
        loginAsAdmin();
        openAddUserForm();
        fillUserForm(user);
        saveUser(user[3]);
        assertUserSaved(user[3]);

        log.info("🏁 Add user test finished for username {}", user[3]);
        softAssert.assertAll();
    }
}