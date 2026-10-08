package pages.addEmployeeTest;

import dataProviders.DataProviderTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.Dashboardpage.DashboardPage;
import pages.PIMPage.PIMPage;
import pages.addEmployeePage.AddEmployeePage;
import pages.baseTest.BaseTest;
import pages.loginPage.LoginPage;

import java.util.Map;


public class AddEmployeeTest extends BaseTest {
    @Test (dataProvider = "validEmployeesData", dataProviderClass = DataProviderTest.class)
    public void addEmployee(String... employee) throws InterruptedException{

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        PIMPage pimPage = new PIMPage(driver);
        AddEmployeePage addEmployeePage = new AddEmployeePage(driver);

        Map<String, String> admin = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");

        loginPage.enterUsername(admin.get("username"));
        loginPage.enterPassword(admin.get("password"));
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        softAssert.assertAll();

        dashboardPage.clickPIMBtn();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("PIM")));
        softAssert.assertTrue(pimPage.getPIMTitle().isDisplayed());

        pimPage.clickAddEmployeeLink();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("addEmployee")));

        addEmployeePage.enterFirstName(employee[0]);
        addEmployeePage.enterMiddleName(employee[1]);
        addEmployeePage.enterLastName(employee[2]);
        addEmployeePage.enterEmployeeId(employee[3]);

        addEmployeePage.clickCreateLoginDetails();
        addEmployeePage.enterUsername(employee[4]);
        addEmployeePage.selectEnabled();
        WebElement enabled = driver.findElement(By.xpath("//label[normalize-space()='Enabled']//input[@type='radio']"));

        softAssert.assertTrue(enabled.isSelected());

        addEmployeePage.enterPassword(employee[5]);
        addEmployeePage.enterConfirmPassword(employee[5]);
        addEmployeePage.clickSave();
        Thread.sleep(3000);

        dashboardPage.clickLogout();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("url")));

        loginPage.enterUsername(employee[4]);
        loginPage.enterPassword(employee[5]);
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        softAssert.assertAll();
    }
}
