# OrangeHRM Test Automation

## Demo video

Watch the full test run before you install anything: **<DEMO_LINK>**

The video shows the whole test run from the first login to the last logout, in the order the tests run.

## What this project does

This project contains automated tests for OrangeHRM, a web app for managing employees. The tests open Chrome, type into fields, click buttons, and choose options from lists, the same way a person would. Then they check that the app responds correctly.

The tests run against OrangeHRM's public demo site, so your computer needs internet access.

## Glossary

- **JDK (Java Development Kit):** the tools that compile and run Java code.
- **Selenium:** a tool that controls a web browser from code.
- **WebDriver:** the part of Selenium that talks to the browser.
- **TestNG:** the test framework that runs tests, groups them, and reports pass or fail.
- **Maven:** a build tool. It downloads the libraries the project needs and runs the tests with one command.
- **Allure:** a tool that turns test results into a web page with steps, errors, and screenshots.
- **Log4j:** the library that writes log messages to the console and to a file.
- **Locator:** the address of an element on a web page, such as a button or text box. Locators here are written in XPath.
- **Page object:** a Java class that holds the locators and actions for one page of the app.
- **Data provider:** a TestNG method that feeds rows of test data from a CSV or JSON file into a test. The test runs once for each row.
- **Singleton:** a pattern where only one object of a kind exists and is shared. In this project, one Chrome browser is created, reused, and then closed.
- **Soft assertion:** a check that records a failure but lets the test keep running. All recorded failures are reported at the end.
- **Incognito:** a Chrome mode that does not keep history or cookies after the window closes.
- **Command line:** a text window where you type commands. On Windows, this is Command Prompt (`cmd`).

## Prerequisites

Install these tools first. Then check each one with the command shown.

**1. JDK 26**

Download JDK 26 from a trusted provider such as Adoptium (Eclipse Temurin) or Oracle. Install it and set the `JAVA_HOME` environment variable to the JDK folder.

```cmd
java -version
```

Expected: the first line contains `26`, for example `java version "26.0.1"`.

**2. Apache Maven**

Download the Binary zip from https://maven.apache.org/download.cgi. Unzip it to `C:\apache-maven`, then add `C:\apache-maven\bin` to your `Path` environment variable. Open a new Command Prompt window after you change `Path`.

```cmd
mvn -version
```

Expected: a line starting with `Apache Maven 3.` and a line starting with `Java version: 26`. The Java version Maven reports must be 26.

**3. Google Chrome**

Install Chrome if it is not already installed. You do not need to install ChromeDriver. The tests download the matching driver automatically.

```cmd
reg query "HKCU\Software\Google\Chrome\BLBeacon" /v version
```

Expected: a line like `version    REG_SZ    1xx.x.x.x`.

**4. IntelliJ IDEA (optional)**

Install IntelliJ IDEA Community or Ultimate. You can run the project without it, but the steps below assume it.

**5. Allure command-line tool**

You need this only to view reports. See the Allure reporting section.

## Getting the project

1. Unzip `OrangeHRM.zip` to a folder you can find, such as `C:\Projects`. The project folder is `C:\Projects\OrangeHRM` and contains `pom.xml`.
2. Open IntelliJ IDEA, choose **Open**, and select the `OrangeHRM` folder.
3. If IntelliJ asks whether to trust the project, choose **Trust Project**. Wait for IntelliJ to import the Maven project.
4. Set the Java version: **File > Project Structure > Project**. Set **SDK** to 26 and **Language level** to 26.

## Installing dependencies

A dependency is a library the project uses, such as the browser automation tool. Maven reads `pom.xml`, downloads each library, and stores it in your local Maven folder, usually `C:\Users\<your-name>\.m2`.

Open Command Prompt and run:

```cmd
cd /d C:\Projects\OrangeHRM
mvn clean install -DskipTests
```

- `clean` deletes the old `target` folder.
- `install` builds the project and downloads the libraries.
- `-DskipTests` builds without running the tests.

Expected at the end:

```text
[INFO] BUILD SUCCESS
```

The first build can take several minutes because Maven downloads many files. Later builds are faster.

**What each dependency is for**

| Dependency | What it does in this project |
|---|---|
| TestNG (`testng`) | Runs the tests and reports results |
| Selenium (`selenium-java`) | Controls Chrome |
| Gson (`gson`) | Reads JSON data files |
| Log4j Core and Log4j API (`log4j-core`, `log4j-api`) | Writes log messages |
| Allure TestNG (`allure-testng`) | Records test results for the Allure report |
| OpenCSV (`opencsv`) | Reads CSV data files |

The pom also has two plugins. Maven Surefire (`maven-surefire-plugin`) runs the tests and uses `run.xml` to decide which ones. Maven Compiler (`maven-compiler-plugin`) compiles the code for Java 26.

## Project structure

```text
OrangeHRM/
├── pom.xml                       Maven settings: libraries, Java version, how tests run
├── run.xml                       TestNG suite file: which test classes run, in what order
├── .gitignore                    Folders and files Git should not save
├── .idea/                        IntelliJ settings (created by IntelliJ)
└── src/
    ├── main/
    │   ├── java/pages/           Page objects: locators and actions for each app page
    │   │   ├── BasePage.java           Shared helpers: find, type, select from dropdown
    │   │   ├── addEmployeePage/        Add Employee form
    │   │   ├── addUser/                Add User form
    │   │   ├── dashboardPage/          Dashboard
    │   │   ├── employeeInfoPage/       Personal, contact, emergency, dependents, job, report-to
    │   │   ├── generateReport/         Reports page
    │   │   ├── loginPage/              Login page
    │   │   ├── PIMPage/                PIM (employee list) page
    │   │   └── timeSheetPage/          Time sheet page
    │   └── resources/            Test data and logging settings
    │       ├── data.json               Page addresses and the report name
    │       ├── users.json              Login accounts for admin, valid, and invalid tests
    │       ├── employees.csv           Employee data for the add and edit tests
    │       ├── invalidEmployees.csv    Invalid employee data
    │       ├── usernames.csv           Usernames for the Add User test
    │       └── log4j2.properties       Logging settings
    └── test/
        └── java/
            ├── dataProviders/DataProviderTest.java   Feeds data from files into tests
            ├── driverFactory/                        Creates and closes the browser
            │   ├── DriverFactory.java
            │   ├── GetChromeDriver.java              Chrome browser setup (used by the tests)
            │   ├── GetEdgeDriver.java                Edge browser setup
            │   └── GetFirefoxDriver.java             Firefox browser setup
            ├── utils/                                Helpers for JSON, CSV, and screenshots
            │   ├── JsonFileManager.java
            │   ├── OpenCSVFileManager.java
            │   └── Screenshot.java
            └── pages/                                The test classes
                ├── baseTest/BaseTest.java            Setup and teardown for every test
                ├── addEmployeeTest/AddEmployeeTest.java
                ├── addUserTest/AddUserTest.java
                ├── dashboardTest/DashboardTest.java
                ├── employeeInformationTest/EmployeeInformationTest.java
                ├── loginTest/ValidLogin.java
                ├── loginTest/InvalidLogin.java
                ├── PIMTest/PIMTest.java
                ├── reportGenerationTest/ReportGenerationTest.java
                └── timeSheetTest/TimeSheetTest.java
```

**Page objects and tests:** Page objects live under `src/main/java`. Test classes live under `src/test/java`.

## Configuration

**The website address.** `BaseTest.java` opens this login page before each test:

```text
https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
```

To change the site, edit that line in `BaseTest.java`.

**`data.json`** holds page addresses and the report name. Each key has a text value.

| Key | What it holds |
|---|---|
| `url` | The login page address |
| `dashboard` | The dashboard page address the tests expect after login |
| `PIM` | The PIM page address |
| `addEmployee` | The Add Employee page address |
| `admin` | A text value (see Security) |
| `reportsUrl` | The Reports page address |
| `reportName` | The name of the report the Reports test searches for |

**`users.json`** holds login accounts. It has three parts:

- `admin`: one object with `username` and `password`, used for admin logins.
- `valid`: a list of accounts with `username` and `password`, used by the valid login test.
- `invalid`: a list of accounts with `username` and `password`, used by the invalid login test.

Use placeholders in any shared copy, for example `<ADMIN_USERNAME>` and `<ADMIN_PASSWORD>`.

**`employees.csv`** holds one header row and then one row per employee. The tests read each column by its position, counting from 0. The table below comes from the header row.

| Index | Column | Index | Column |
|---|---|---|---|
| 0 | firstName | 15 | emergencyHomeTelephone |
| 1 | middleName | 16 | emergencyMobile |
| 2 | lastName | 17 | emergencyWorkTelephone |
| 3 | employeeId | 18 | street1 |
| 4 | username | 19 | street2 |
| 5 | password | 20 | city |
| 6 | nationality | 21 | stateProvince |
| 7 | maritalStatus | 22 | zipPostalCode |
| 8 | jobTitle | 23 | country |
| 9 | subUnit | 24 | contactHomeTelephone |
| 10 | status | 25 | contactMobile |
| 11 | supervisorName | 26 | contactWorkTelephone |
| 12 | reportingMethod | 27 | workEmail |
| 13 | emergencyName | 28 | otherEmail |
| 14 | emergencyRelationship | | |

If you add a column, the indexes after it change. Update every test that reads those indexes.

**`invalidEmployees.csv`** has these columns: `firstName`, `middleName`, `lastName`, `employeeId`, `username`, `password`, `confirmPassword`.

**`usernames.csv`** has these columns: `Fullname`, `Role`, `Status`, `Username`, `Password`, `Confirmpassword`.

**Data providers**

| Provider name | Reads from | Used by |
|---|---|---|
| `validCredentials` | `users.json`, `valid` | ValidLogin |
| `invalidCredentials` | `users.json`, `invalid` | InvalidLogin |
| `validEmployeesData` | `employees.csv` | AddEmployeeTest, EmployeeInformationTest, TimeSheetTest, ReportGenerationTest |
| `invalidEmployeesData` | `invalidEmployees.csv` | Available for tests that use this provider |
| `validUsernames` | `usernames.csv` | AddUserTest |

**Logging settings** are in `log4j2.properties`. See Reading the logs.

## Running the tests

### From IntelliJ

1. Open `run.xml`.
2. Click the green run arrow next to the `<suite>` line.
3. Or open one test class and click the green arrow next to the class name or a test method.

The Run window shows each test. Green means it passed. Red means it failed.

### From Windows Command Prompt

1. Open Command Prompt: press the Start key, type `cmd`, and press Enter.
2. Go to the project folder. This must be the folder that contains `pom.xml`.

   ```cmd
   cd /d C:\Projects\OrangeHRM
   ```

   Expected: the prompt shows `C:\Projects\OrangeHRM>`.

3. Run all tests listed in `run.xml`.

   ```cmd
   mvn clean test
   ```

   Expected at the end:

   ```text
   [INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
   [INFO] BUILD SUCCESS
   ```

   The numbers depend on the tests. If any test fails, the last line is `BUILD FAILURE`.

A Chrome window opens for each test and closes when the test ends. Do not close it by hand while a test is running.

### Running one class or method

Run one test class:

```cmd
mvn test -Dtest=ValidLogin
```

Run one method. Replace `methodName` with the name of the method in the test class:

```cmd
mvn test -Dtest=ValidLogin#methodName
```

## The run.xml file

`run.xml` is a TestNG suite file. It is a list of the test classes to run. The pom tells Maven to read it, through the `<suiteXmlFiles>` setting.

The current file runs these classes, in this order:

| Order | Class | Package |
|---|---|---|
| 1 | AddEmployeeTest | pages.addEmployeeTest |
| 2 | AddUserTest | pages.addUserTest |
| 3 | ValidLogin | pages.loginTest |
| 4 | EmployeeInformationTest | pages.employeeInformationTest |
| 5 | TimeSheetTest | pages.timeSheetTest |
| 6 | InvalidLogin | pages.loginTest |

Test classes that are not listed in `run.xml` are not run by `mvn test`.

**Why the order matters:** AddEmployeeTest creates the employees that EmployeeInformationTest uses. If AddEmployeeTest does not run first, EmployeeInformationTest may fail because the employee is missing.

**To add a class,** place a line like this inside `<classes>`:

```xml
<class name = "pages.reportGenerationTest.ReportGenerationTest"/>
```

**To remove a class,** delete its line. Save the file before you run the tests.

## Reading the logs

The logs have two places:

- **The console:** the Run window in IntelliJ, or the Command Prompt window.
- **A file:** `logs\application.log` in the project folder. Maven creates it on the first run.

**Console format**

```text
2026-10-17 19:49:28 - ❌ Option 'Job' not found. Found 15 options:
```

**File format**

```text
2026-10-17 19:49:28 [ERROR] DropdownHelper:95 - ❌ Option 'Job' not found.
```

Each file line has:
- the date and time,
- the level in square brackets: `DEBUG`, `INFO`, `WARN`, or `ERROR`,
- the class name and line number,
- the message.

The log level is `debug`, so every message is written.

**Emoji markers used in the messages**

| Marker | Meaning |
|---|---|
| 🚀 | A test is starting |
| 🌐 | Chrome is being created |
| 🔐 | A login is happening |
| 📄 📂 📋 | A data file is being opened or read |
| 🔽 | A dropdown option was selected |
| 📸 | A screenshot was saved |
| 🛑 | The browser is closing |
| ✅ | A step succeeded |
| ⚠️ | A warning. The test continues. |
| ❌ | An error |

**How to find the failing step**

1. Open `logs\application.log` in a text editor.
2. Search for `[ERROR]` or `❌`.
3. Read the lines just above the error to see the last step that worked.
4. Note the class name and line number, such as `ReportGenerationPage:97`. Open that line in IntelliJ with **Ctrl + Shift + N**.

## Allure reporting

Allure creates a web report from the test results. The report shows each test, its steps, its errors, and the failure screenshot.

### Step 1: Install the Allure command-line tool

Option A, with Scoop (if you have it):

```cmd
scoop install allure
```

Option B, manually:
1. Go to https://github.com/allure-framework/allure2/releases and download the `.zip` file.
2. Unzip it to `C:\allure`.
3. Add `C:\allure\bin` to your `Path` environment variable.
4. Open a new Command Prompt window.

Check the install:

```cmd
allure --version
```

Expected: a version number, such as `2.x.x`.

### Step 2: Run the tests

The Allure listener is attached to `BaseTest`, so every test writes results automatically.

```cmd
cd /d C:\Projects\OrangeHRM
mvn clean test
```

After the run, the results are in the `allure-results` folder in the project folder.

### Step 3: Open the report

```cmd
allure serve allure-results
```

Expected: a browser window opens with the report. To stop the report server, go back to the Command Prompt window and press **Ctrl + C**.

### What the report shows

- **Overview:** the number of passed, failed, and skipped tests.
- **Suites:** the tests grouped by class.
- **Behaviors:** the tests grouped by the `@Epic`, `@Feature`, and `@Story` labels in the test classes.
- **Timeline:** when each test ran.
- **Test details:** for each test, its steps and any error message.
- **Attachments:** for a failed test, the failure screenshot.

### Failure screenshots

When a test fails, `BaseTest` saves a screenshot to `target\screenshots` in the project folder. The same image is attached to the test in the Allure report. Open the failed test in the report and look at the attachment to see the page at the moment of failure.

## Troubleshooting

| Problem | Likely cause | Fix |
|---|---|---|
| `'mvn' is not recognized as an internal or external command` | Maven is not installed, or its `bin` folder is not on `Path` | Check the Maven steps under Prerequisites. Open a new Command Prompt window and run `mvn -version`. |
| Errors such as `invalid target release: 26` or `release version 26 not supported` | Maven or IntelliJ is using a different Java version | Run `mvn -version` and check the Java version line. Set `JAVA_HOME` to your JDK 26 folder. In IntelliJ, set the Project SDK to 26. |
| `This version of ChromeDriver only supports Chrome version...` or `session not created` | Chrome and the downloaded driver do not match | Update Chrome to the latest version, then run the tests again. Selenium Manager needs internet access to download the driver. |
| `NoSuchElementException` | The element is not on the page, the page has not finished loading, or the site changed | Open the failure screenshot and check the page. Then check the log for the last step that worked. |
| `TimeoutException` | The page or element did not appear within 10 seconds | Check the screenshot. The site may be slow, or the element may have a different name or position. |
| `Dropdown option not found` | The text in the data file does not match an option exactly | Compare the value in `employees.csv` with the option on the site. Spelling, spaces, and capital letters all matter. |
| `CSV file is empty` or `Could not read CSV file` | The tests are running from the wrong folder | Run Maven from the project folder that contains `pom.xml`. The data paths are relative to that folder. |
| `Could not read JSON file` or `Invalid JSON` | The file is missing, or the JSON has a syntax error | Check that the file exists in `src/main/resources`. Check for missing commas, quotes, or brackets. |
| Tests pass on one run and fail on the next | The demo site's data changed, or the same employee or username already exists from an earlier run | Use new employee IDs and usernames in `employees.csv`. Check the log for messages about duplicate records. |
| `allure is not recognized` | The Allure command-line tool is not installed or not on `Path` | Follow Step 1 of Allure reporting. Open a new Command Prompt window. |
| The Allure report is empty | The tests did not run, or `allure-results` is in another folder | Run `mvn clean test` first. Then run `allure serve allure-results` from the project folder. |

## Adding a new test or page object

**To add a page object:**

1. Create a folder under `src/main/java/pages/`, for example `src/main/java/pages/exampleFeaturePage/`.
2. Create a class that extends `BasePage` and has a constructor that calls `super(driver)`.
3. Put locators in `private static final By` fields.
4. Add getter methods that return elements and action methods that click or type, following an existing page such as `JobInfoPage`.

**To add a test:**

1. Create a folder under `src/test/java/pages/`, for example `src/test/java/pages/exampleFeatureTest/`.
2. Create a class that extends `BaseTest`.
3. Create the page objects in a method, then add `@Test` methods. Use `softAssert` for checks.
4. Add the class to `run.xml`.
5. Run `mvn test -Dtest=ExampleFeatureTest` to check the new class.

## FAQ

**Do I need to open Chrome first?**
No. The tests open their own Chrome window.

**Where does the browser driver come from?**
Selenium Manager downloads the driver that matches your Chrome version. This needs internet access.

**Can the tests run without a window?**
The code does not set a headless mode, so a Chrome window opens for each test.

**Can I test a different website?**
Change the login address in `BaseTest.java`. Then update the page addresses in `data.json` and the test data to match the new site.

**Can I use a different browser?**
The tests start Chrome. To use another browser, change the driver creation in `BaseTest.java`.

**Where are the screenshots?**
In `target\screenshots` in the project folder. They are also attached to failed tests in the Allure report.

**Why did a test that worked yesterday fail today?**
The demo site is shared and can change. Data on the site can also change. Check the screenshot and the log.

## Security

- `users.json` and `employees.csv` contain usernames and passwords. Do not copy real values into this README, into screenshots, or into shared messages.
- Do not share real credentials in a shared Git repository. `.gitignore` does not currently exclude files in `src/main/resources`. To keep these files out of version control, add these lines to `.gitignore`:

  ```text
  src/main/resources/users.json
  src/main/resources/employees.csv
  ```

- If these files are already in Git, remove them from tracking first:

  ```cmd
  git rm --cached src/main/resources/users.json src/main/resources/employees.csv
  ```

- For a team project, consider reading credentials from environment variables instead of storing them in files.
