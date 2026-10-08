package pages.loginTest;

import org.testng.annotations.Test;
import pages.Dashboardpage.DashboardPage;
import pages.baseTest.BaseTest;


import pages.loginPage.LoginPage;

import java.util.List;


public class ValidLogin extends BaseTest {
    @Test
    public void login() throws InterruptedException {

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        List<String> user = (List<String>) jsonFileManagerUsers.getValueByKey("user1");

        loginPage.enterUsername(user.get(0));
        loginPage.enterPassword(user.get(1));
        loginPage.clickLogin();

        Thread.sleep(3000);

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        softAssert.assertAll();
    }
}
