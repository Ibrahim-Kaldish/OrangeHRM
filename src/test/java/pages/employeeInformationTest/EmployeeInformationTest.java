package pages.employeeInformationTest;

import dataProviders.DataProviderTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import pages.PIMPage.PIMPage;
import pages.baseTest.BaseTest;
import pages.dashboardPage.DashboardPage;
import pages.employeeInfoPage.EmployeeInfoPage;
import pages.loginPage.LoginPage;

import java.util.Map;

@Epic("Employee Management")
@Feature("Employee Information")
public class EmployeeInformationTest extends BaseTest {
    private static final Logger log = LogManager.getLogger(EmployeeInformationTest.class);

    String username;
    String password;
    String nationality;
    String maritalStatus;
    String emergencyName;
    String emergencyRelationship;
    String emergencyHomeTelephone;
    String emergencyMobile;
    String emergencyWorkTelephone;
    String street1;
    String street2;
    String city;
    String stateProvince;
    String zipPostalCode;
    String country;
    String contactHomeTelephone;
    String contactMobile;
    String contactWorkTelephone;
    String workEmail;
    String otherEmail;
    LoginPage loginPage;
    DashboardPage dashboardPage;
    EmployeeInfoPage employeeInfoPage;
    PIMPage pimPage;

    public void personalDetailsAssert(){
        log.info("🔍 Verifying Personal Details after reload");
        softAssert.assertEquals(employeeInfoPage.getSelectedNationality(), nationality, "Nationality after reload");
        softAssert.assertEquals(employeeInfoPage.getSelectedMaritalStatus(), maritalStatus, "Marital status after reload");
    }

    public void contactDetailsAssert(){
        log.info("🔍 Verifying Contact Details after reload");
        softAssert.assertEquals(employeeInfoPage.getContactStreet1Value(), street1, "Street 1 after reload");
        softAssert.assertEquals(employeeInfoPage.getContactStreet2Value(), street2, "Street 2 after reload");
        softAssert.assertEquals(employeeInfoPage.getContactCityValue(), city, "City after reload");
        softAssert.assertEquals(employeeInfoPage.getContactStateProvinceValue(), stateProvince, "State/Province after reload");
        softAssert.assertEquals(employeeInfoPage.getContactZipPostalCodeValue(), zipPostalCode, "Zip/Postal Code after reload");
        softAssert.assertEquals(employeeInfoPage.getContactCountryText(), country, "Country after reload");
        softAssert.assertEquals(employeeInfoPage.getContactHomeTelephoneValue(), contactHomeTelephone, "Home telephone after reload");
        softAssert.assertEquals(employeeInfoPage.getContactMobileValue(), contactMobile, "Mobile after reload");
        softAssert.assertEquals(employeeInfoPage.getContactWorkTelephoneValue(), contactWorkTelephone, "Work telephone after reload");
        softAssert.assertEquals(employeeInfoPage.getContactWorkEmailValue(), workEmail, "Work email after reload");
        softAssert.assertEquals(employeeInfoPage.getContactOtherEmailValue(), otherEmail, "Other email after reload");
    }

    public void emergencyContactAssert(){
        log.info("🔍 Verifying saved emergency contact");
        String savedCard = employeeInfoPage.getSavedContactCardText();
        softAssert.assertTrue(savedCard.contains(emergencyName), "Saved name missing: " + emergencyName);
        softAssert.assertTrue(savedCard.contains(emergencyRelationship), "Saved relationship missing: " + emergencyRelationship);
        softAssert.assertTrue(savedCard.contains(emergencyHomeTelephone), "Saved home telephone missing: " + emergencyHomeTelephone);
        softAssert.assertTrue(savedCard.contains(emergencyMobile), "Saved mobile missing: " + emergencyMobile);
        softAssert.assertTrue(savedCard.contains(emergencyWorkTelephone), "Saved work telephone missing: " + emergencyWorkTelephone);
    }

    public void successMessage(String msg1, String msg2){
        log.info("🔔 Checking toast: {}", msg1);
        softAssert.assertEquals(employeeInfoPage.getSuccessToastText(), msg1, msg2);
    }

    public void setAttributes(String ... employeeData){
        log.info("📋 Loading employee data for username {}", employeeData[4]);
        username = employeeData[4];
        password = employeeData[5];
        nationality = employeeData[6];
        maritalStatus = employeeData[7];
        emergencyName = employeeData[8];
        emergencyRelationship = employeeData[9];
        emergencyHomeTelephone = employeeData[10];
        emergencyMobile = employeeData[11];
        emergencyWorkTelephone = employeeData[12];
        street1 = employeeData[13];
        street2 = employeeData[14];
        city = employeeData[15];
        stateProvince = employeeData[16];
        zipPostalCode = employeeData[17];
        country = employeeData[18];
        contactHomeTelephone = employeeData[19];
        contactMobile = employeeData[20];
        contactWorkTelephone = employeeData[21];
        workEmail = employeeData[22];
        otherEmail = employeeData[23];
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        employeeInfoPage = new EmployeeInfoPage(driver);
        pimPage = new PIMPage(driver);
    }

    public void loginAsUser(){
        log.info("🔐 Logging in as employee {}", username);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        log.info("✅ Employee logged in");
    }

    public void loginAsAdmin(){
        log.info("🔐 Logging in as admin");
        Map<String, String> admin = (Map<String, String>) jsonFileManagerUsers.getValueByKey("admin");

        loginPage.enterUsername(admin.get("username"));
        loginPage.enterPassword(admin.get("password"));
        loginPage.clickLogin();

        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("dashboard")));
        softAssert.assertTrue(dashboardPage.getDashboardTitle().isDisplayed());
        log.info("✅ Admin logged in");
    }

    public void navigateToMyInfo(){
        log.info("📂 Opening My Info");
        employeeInfoPage.clickMyInfoMenuItem();
    }

    public void navigateToEmergencyContacts(){
        log.info("📂 Opening Emergency Contacts tab");
        employeeInfoPage.clickEmergencyContactsTab();
    }

    public void navigateToContactDetails(){
        log.info("📂 Opening Contact Details tab");
        employeeInfoPage.clickContactDetailsTab();
    }

    public void navigateToPIMPage(){
        log.info("📂 Opening PIM from the dashboard");
        dashboardPage.clickPIMBtn();
    }

    public void searchForEmployee(String id){
        log.info("🔎 Searching PIM for employee Id {}", id);
        pimPage.enterEmployeeId(id);
        pimPage.clickSearch();

        pimPage.waitForSearchResults();
        softAssert.assertTrue(pimPage.getResultCount() > 0, "Search returned no results");
        pimPage.clickFirstResult();
        log.info("✅ Opened employee record for Id {}", id);
    }

    public void personalDetailsActions(){
        log.info("✏️ Updating Personal Details");
        employeeInfoPage.selectNationality(nationality);
        employeeInfoPage.selectMaritalStatus(maritalStatus);
        employeeInfoPage.clickPersonalDetailsSave();
    }

    public void emergencyContactActions(){
        log.info("✏️ Adding emergency contact {}", emergencyName);
        employeeInfoPage.clickAssignedEmergencyContactsAdd();
        employeeInfoPage.enterEmergencyName(emergencyName);
        employeeInfoPage.enterEmergencyRelationship(emergencyRelationship);
        employeeInfoPage.enterEmergencyHomeTelephone(emergencyHomeTelephone);
        employeeInfoPage.enterEmergencyMobile(emergencyMobile);
        employeeInfoPage.enterEmergencyWorkTelephone(emergencyWorkTelephone);
        employeeInfoPage.clickEmergencyContactSave();
    }

    public void contactDetailsActions(){
        log.info("✏️ Updating Contact Details");
        employeeInfoPage.enterContactDetails(street1, street2, city, stateProvince, zipPostalCode,
                country, contactHomeTelephone, contactMobile, contactWorkTelephone, workEmail, otherEmail);
        employeeInfoPage.clickContactSave();
    }

    @Test(dataProvider = "validEmployeesData", dataProviderClass = DataProviderTest.class)
    @Story("Employee updates personal, contact, and emergency details")
    @Description("As an employee, I want to update my personal, contact, and emergency details so that HR has current records.")
    @Severity(SeverityLevel.CRITICAL)
    public void updateEmployeeInformation(String... employeeData) throws InterruptedException {
        log.info("🚀 Starting employee information update");

        setAttributes(employeeData);

        loginAsUser();

        navigateToMyInfo();
        Thread.sleep(3000);

        personalDetailsActions();
        successMessage("Successfully Updated", "Personal Details save message");
        personalDetailsAssert();
        Thread.sleep(3000);

        navigateToEmergencyContacts();
        emergencyContactActions();
        successMessage("Successfully Saved", "Emergency Contact save message");
        emergencyContactAssert();
        Thread.sleep(3000);

        navigateToContactDetails();
        contactDetailsActions();
        successMessage("Successfully Updated", "Contact Details save message");
        contactDetailsAssert();
        Thread.sleep(3000);

        log.info("🚪 Logging out employee");
        dashboardPage.clickLogout();
        softAssert.assertTrue(driver.getCurrentUrl().equals(jsonFileManagerUrls.getValueByKey("url")));

        loginAsAdmin();

        navigateToPIMPage();

        searchForEmployee(employeeData[3]);

        Thread.sleep(3000);
        personalDetailsAssert();

        employeeInfoPage.clickEmergencyContactsTab();
        Thread.sleep(3000);
        emergencyContactAssert();

        employeeInfoPage.clickContactDetailsTab();
        Thread.sleep(3000);
        contactDetailsAssert();

        log.info("🏁 Employee information update finished, checking results");
        softAssert.assertAll();
    }
}