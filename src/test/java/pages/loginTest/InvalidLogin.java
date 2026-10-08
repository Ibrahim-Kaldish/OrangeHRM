package pages.loginTest;

import dataProviders.DataProviderTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.Dashboardpage.DashboardPage;
import pages.baseTest.BaseTest;
import pages.loginPage.LoginPage;

import java.util.List;
import java.util.Map;

public class InvalidLogin extends BaseTest {

    @Test (dataProvider = "invalidCredentials", dataProviderClass = DataProviderTest.class)
    public void login (String username, String password) throws InterruptedException{

        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.errorMessage().isDisplayed());
        Assert.assertEquals(loginPage.errorMessage().getText(), "Invalid credentials");
    }
}
