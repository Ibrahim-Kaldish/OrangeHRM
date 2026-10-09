package pages.dashboardTest;

import org.testng.annotations.Test;
import pages.dashboardPage.DashboardPage;
import pages.PIMPage.PIMPage;
import pages.baseTest.BaseTest;
import pages.loginPage.LoginPage;

import java.util.Map;

public class DashboardTest extends BaseTest {
    @Test
    public void navigateToPIM() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        PIMPage pimPage = new PIMPage(driver);


        Map<String,String> user = (Map<String,String>) jsonFileManagerUsers.getValueByKey("admin");

        loginPage.enterUsername(user.get("username"));
        loginPage.enterPassword(user.get("password"));
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        softAssert.assertAll();

        dashboardPage.clickPIMBtn();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("PIM")));
        softAssert.assertTrue(pimPage.getPIMTitle().isDisplayed());

        Thread.sleep(3000);
    }
}
