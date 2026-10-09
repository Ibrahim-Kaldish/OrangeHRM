package pages.loginTest;

import dataProviders.DataProviderTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.baseTest.BaseTest;
import pages.loginPage.LoginPage;

public class InvalidLogin extends BaseTest {

    @Test (dataProvider = "invalidCredentials", dataProviderClass = DataProviderTest.class)
    public void login (String username, String password){

        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.errorMessage().isDisplayed());
        Assert.assertEquals(loginPage.errorMessage().getText(), "Invalid credentials");
    }
}
