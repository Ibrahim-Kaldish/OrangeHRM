package pages.dashboardTest;

import org.testng.annotations.Test;
import pages.Dashboardpage.DashboardPage;
import pages.PIMPage.PIMPage;
import pages.baseTest.BaseTest;
import pages.loginPage.LoginPage;

import java.util.List;

public class DashboardTest extends BaseTest {
    @Test
    public void navigateToPIM() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        PIMPage pimPage = new PIMPage(driver);


        List<String> user = (List<String>) jsonFileManagerUsers.getValueByKey("user1");

        loginPage.enterUsername(user.get(0));
        loginPage.enterPassword(user.get(1));
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
