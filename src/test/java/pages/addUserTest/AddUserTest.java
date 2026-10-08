package pages.addUserTest;

import org.testng.annotations.Test;
import pages.Dashboardpage.DashboardPage;
import pages.addUser.AddUserPage;
import pages.baseTest.BaseTest;
import pages.loginPage.LoginPage;

import java.util.Map;

public class AddUserTest extends BaseTest {
    @Test (dataProvider = "validUsernames", dataProviderClass = dataProviders.DataProviderTest.class)
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

        softAssert.assertEquals(driver.getCurrentUrl(), jsonFileManagerUrls.getValueByKey("admin"));

        softAssert.assertAll();

        Thread.sleep(5000);
    }
}
