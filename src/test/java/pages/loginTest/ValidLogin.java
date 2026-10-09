package pages.loginTest;

import dataProviders.DataProviderTest;
import org.testng.annotations.Test;
import pages.dashboardPage.DashboardPage;
import pages.baseTest.BaseTest;
import pages.loginPage.LoginPage;

public class ValidLogin extends BaseTest {
    @Test (dataProvider = "validCredentials", dataProviderClass = DataProviderTest.class)
    public void login(String username, String password) {

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        softAssert.assertAll();
    }
}
