# 🧪 OrangeHRM Test Automation

![Java](https://img.shields.io/badge/Java-26-orange) ![Maven](https://img.shields.io/badge/Maven-build-blue) ![Selenium](https://img.shields.io/badge/Selenium-4.48.0-green) ![TestNG](https://img.shields.io/badge/TestNG-7.12.0-lightgrey) ![Allure](https://img.shields.io/badge/Allure-3.20.0-purple)

## 🎬 Demo video

▶️ Watch the full test run before you install anything: **<DEMO_LINK>**

🎞️ The video shows the whole test run from the first login to the last logout, in the order the tests run.

## 📖 What this project does

🤖 This project contains automated tests for OrangeHRM, a web app for managing employees. The tests open Chrome, type into fields, click buttons, and choose options from lists, the same way a person would. Then they check that the app responds correctly.

🌍 The tests run against OrangeHRM's public demo site, so your computer needs internet access.

## 📚 Glossary

- ☕ **JDK (Java Development Kit):** the tools that compile and run Java code.
- 🌐 **Selenium:** a tool that controls a web browser from code.
- 🔌 **WebDriver:** the part of Selenium that talks to the browser.
- 🏃 **TestNG:** the test framework that runs tests, groups them, and reports pass or fail.
- 📦 **Maven:** a build tool. It downloads the libraries the project needs and runs the tests with one command.
- 📊 **Allure:** a tool that turns test results into a web page with steps, errors, and screenshots.
- 📝 **Log4j:** the library that writes log messages to the console and to a file.
- 🎯 **Locator:** the address of an element on a web page, such as a button or text box. Locators here are written in XPath.
- 📄 **Page object:** a Java class that holds the locators and actions for one page of the app.
- 🗃️ **Data provider:** a TestNG method that feeds rows of test data from a CSV or JSON file into a test. The test runs once for each row.
- 1️⃣ **Singleton:** a pattern where only one object of a kind exists and is shared. In this project, one Chrome browser is created, reused, and then closed.
- 🛟 **Soft assertion:** a check that records a failure but lets the test keep running. All recorded failures are reported at the end.
- 🤖 **Robot:** a Java class that presses keys like a person would. This project uses it to paste the photo path into the file dialog.
- 🕶️ **Incognito:** a Chrome mode that does not keep history or cookies after the window closes.
- ⌨️ **Command line:** a text window where you type commands. On Windows, this is Command Prompt (`cmd`).

## 🧰 Prerequisites

Install these tools first. Then check each one with the command shown.

**1️⃣ ☕ JDK 26**

Download JDK 26 from a trusted provider such as Adoptium (Eclipse Temurin) or Oracle. Install it and set the `JAVA_HOME` environment variable to the JDK folder.

```cmd
java -version
```

✅ Expected: the first line contains `26`, for example `java version "26.0.1"`.

**2️⃣ 📦 Apache Maven**

Download the Binary zip from https://maven.apache.org/download.cgi and unzip it to `C:\apache-maven`.

Then add the Maven `bin` folder to the system environment variables:

1. 🔍 Press the Start key, type `environment variables`, and choose **Edit the system environment variables**.
2. 🧭 Click **Environment Variables**.
3. 📂 Under **System variables**, select **Path**, then click **Edit**.
4. ➕ Click **New** and enter `C:\apache-maven\bin`.
5. 💾 Click **OK** on each window to save.
6. 🔁 Open a new Command Prompt window. Changes to `Path` do not apply to windows that were already open.

📌 The `bin` folder contains the `mvn` command. Without it on `Path`, Windows cannot find `mvn`.

```cmd
mvn -version
```

✅ Expected: a line starting with `Apache Maven 3.` and a line starting with `Java version: 26`. The Java version Maven reports must be 26.

**3️⃣ 🌐 Google Chrome**

Install Chrome if it is not already installed. You do not need to install ChromeDriver. The tests download the matching driver automatically.

```cmd
reg query "HKCU\Software\Google\Chrome\BLBeacon" /v version
```

✅ Expected: a line like `version    REG_SZ    1xx.x.x.x`.

**4️⃣ 💻 IntelliJ IDEA (optional)**

Install IntelliJ IDEA Community or Ultimate. You can run the project without it, but the steps below assume it.

**5️⃣ 🟢 Node.js and npm**

📦 npm is the package manager that installs the Allure command-line tool. Download the LTS version from https://nodejs.org and run the installer. Keep the default options.

```cmd
node -v
npm -v
```

✅ Expected: a version number for each command, such as `v22.x.x` and `10.x.x`.

**6️⃣ 📊 Allure command-line tool**

You need this only to view reports. It is installed with npm (`npm install -g allure`). See the 📊 Allure reporting section.

## 📥 Getting the project

1. 🗜️ Unzip `OrangeHRM.zip` to a folder you can find, such as `C:\Projects`. The project folder is `C:\Projects\OrangeHRM` and contains `pom.xml`.
2. 💻 Open IntelliJ IDEA, choose **Open**, and select the `OrangeHRM` folder.
3. 🛡️ If IntelliJ asks whether to trust the project, choose **Trust Project**. Wait for IntelliJ to import the Maven project.
4. ☕ Set the Java version: **File > Project Structure > Project**. Set **SDK** to 26 and **Language level** to 26.

## 📦 Installing dependencies

📚 A dependency is a library the project uses, such as the browser automation tool. Maven reads `pom.xml`, downloads each library, and stores it in your local Maven folder, usually `C:\Users\<your-name>\.m2`.

Open Command Prompt and run:

```cmd
cd /d C:\Projects\OrangeHRM
mvn clean install -DskipTests
```

- 🧹 `clean` deletes the old `target` folder.
- 🏗️ `install` builds the project and downloads the libraries.
- ⏭️ `-DskipTests` builds without running the tests.

✅ Expected at the end:

```text
[INFO] BUILD SUCCESS
```

⏳ The first build can take several minutes because Maven downloads many files. Later builds are faster.

**🔗 What each dependency is for**

| Icon | Dependency | What it does in this project |
|---|---|---|
| 🏃 | TestNG (`testng`) | Runs the tests and reports results |
| 🌐 | Selenium (`selenium-java`) | Controls Chrome |
| 🧾 | Gson (`gson`) | Reads JSON data files |
| 📝 | Log4j Core and Log4j API (`log4j-core`, `log4j-api`) | Writes log messages |
| 📊 | Allure TestNG (`allure-testng`) | Records test results for the Allure report |
| 📑 | OpenCSV (`opencsv`) | Reads CSV data files |

⚙️ The pom also has two plugins. Maven Surefire (`maven-surefire-plugin`) runs the tests and uses `run.xml` to decide which ones. Maven Compiler (`maven-compiler-plugin`) compiles the code for Java 26.

## 🗂️ Project structure

```text
📁 OrangeHRM/
├── 📁 .idea/                                      IntelliJ settings (created by IntelliJ)
├── 🙈 .gitignore                                  Folders and files Git should not save
├── ⚙️ pom.xml                                     Maven settings: libraries, Java version, how tests run
├── 🧩 run.xml                                     TestNG suite file: which test classes run, in what order
└── 📁 src/
    ├── 📁 main/
    │   ├── 📁 java/pages/                         Page objects: locators and actions for each app page
    │   │   ├── ☕ BasePage.java                   Shared helpers: find, type, select from dropdown
    │   │   ├── 📁 addEmployeePage/                Add Employee form
    │   │   ├── 📁 addUser/                        Add User form
    │   │   ├── 📁 dashboardPage/                  Dashboard
    │   │   ├── 📁 employeeInfoPage/               Personal, contact, emergency, dependents, job, report-to
    │   │   ├── 📁 generateReport/                 Reports page
    │   │   ├── 📁 loginPage/                      Login page
    │   │   ├── 📁 PIMPage/                        PIM (employee list) page
    │   │   └── 📁 timeSheetPage/                  Time sheet page
    │   └── 📁 resources/                          Test data and logging settings
    │       ├── 🧾 data.json                       Page addresses and the report name
    │       ├── 🔑 users.json                      Login accounts for admin, valid, and invalid tests
    │       ├── 📑 employees.csv                   Employee data for the add and edit tests
    │       ├── 📑 invalidEmployees.csv            Invalid employee data
    │       ├── 📑 usernames.csv                   Usernames for the Add User test
    │       ├── 📁 photos/                         Employee photos for the Add Employee test
    │       │   └── 🖼️ employee.png
    │       └── 📝 log4j2.properties               Logging settings
    └── 📁 test/
        └── 📁 java/
            ├── 📁 dataProviders/
            │   └── ☕ DataProviderTest.java       Feeds data from files into tests
            ├── 📁 driverFactory/                  Creates and closes the browser
            │   ├── ☕ DriverFactory.java
            │   ├── ☕ GetChromeDriver.java        Chrome browser setup (used by the tests)
            │   ├── ☕ GetEdgeDriver.java          Edge browser setup
            │   └── ☕ GetFirefoxDriver.java       Firefox browser setup
            ├── 📁 utils/                          Helpers for JSON, CSV, and screenshots
            │   ├── ☕ JsonFileManager.java
            │   ├── ☕ OpenCSVFileManager.java
            │   └── ☕ Screenshot.java
            └── 📁 pages/                          The test classes
                ├── 📁 baseTest/
                │   └── ☕ BaseTest.java           Setup and teardown for every test
                ├── 📁 addUserTest/
                │   └── ☕ AddUserTest.java
                ├── 📁 dashboardTest/
                │   └── ☕ DashboardTest.java
                ├── 📁 employeeInformationTest/
                │   └── ☕ EmployeeInformationTest.java
                ├── 📁 loginTest/
                │   ├── ☕ ValidLogin.java
                │   └── ☕ InvalidLogin.java
                ├── 📁 PIMTest/
                │   ├── 📁 addEmployeeTest/        Add Employee test
                │   ├── 📁 PIMSearchTest/          PIM search test
                │   └── 📁 reportGenerationTest/   Report generation test
                └── 📁 timeSheetTest/
                    └── ☕ TimeSheetTest.java
```

💡 **Page objects and tests:** Page objects live under `src/main/java`. Test classes live under `src/test/java`.

## ⚙️ Configuration

🔗 **The website address.** `BaseTest.java` opens this login page before each test:

```text
https://opensource-demo.orangehrmlive.com/web/index.php/auth/login
```

✏️ To change the site, edit that line in `BaseTest.java`.

🧾 **`data.json`** holds page addresses and the report name. Each key has a text value.

| Icon | Key | What it holds |
|---|---|---|
| 🔗 | `url` | The login page address |
| 🏠 | `dashboard` | The dashboard page address the tests expect after login |
| 🧑‍💼 | `PIM` | The PIM page address |
| ➕ | `addEmployee` | The Add Employee page address |
| 🔐 | `admin` | A text value (see 🔒 Security) |
| 📊 | `reportsUrl` | The Reports page address |
| 📋 | `reportName` | The name of the report the Reports test searches for |

🔑 **`users.json`** holds login accounts. It has three parts:

- 👑 `admin`: one object with `username` and `password`, used for admin logins.
- ✅ `valid`: a list of accounts with `username` and `password`, used by the valid login test.
- ❌ `invalid`: a list of accounts with `username` and `password`, used by the invalid login test.

🙈 Use placeholders in any shared copy, for example `<ADMIN_USERNAME>` and `<ADMIN_PASSWORD>`.

📑 **`employees.csv`** holds one header row and then one row per employee. The tests read each column by its position, counting from 0. The table below comes from the header row.

| Index | Column | Index | Column |
|---|---|---|---|
| 0️⃣ | 👤 firstName | 1️⃣6️⃣ | 🚨 emergencyMobile |
| 1️⃣ | 👤 middleName | 1️⃣7️⃣ | 🚨 emergencyWorkTelephone |
| 2️⃣ | 👤 lastName | 1️⃣8️⃣ | 🏠 street1 |
| 3️⃣ | 👤 employeeId | 1️⃣9️⃣ | 🏠 street2 |
| 4️⃣ | 👤 username | 2️⃣0️⃣ | 🏠 city |
| 5️⃣ | 👤 password | 2️⃣1️⃣ | 🏠 stateProvince |
| 6️⃣ | 👤 nationality | 2️⃣2️⃣ | 🏠 zipPostalCode |
| 7️⃣ | 👤 maritalStatus | 2️⃣3️⃣ | 🏠 country |
| 8️⃣ | 💼 jobTitle | 2️⃣4️⃣ | 📞 contactHomeTelephone |
| 9️⃣ | 💼 subUnit | 2️⃣5️⃣ | 📞 contactMobile |
| 1️⃣0️⃣ | 💼 status | 2️⃣6️⃣ | 📞 contactWorkTelephone |
| 1️⃣1️⃣ | 💼 supervisorName | 2️⃣7️⃣ | ✉️ workEmail |
| 1️⃣2️⃣ | 💼 reportingMethod | 2️⃣8️⃣ | ✉️ otherEmail |
| 1️⃣3️⃣ | 🚨 emergencyName | 2️⃣9️⃣ | 👪 dependentName |
| 1️⃣4️⃣ | 🚨 emergencyRelationship | 3️⃣0️⃣ | 👪 dependentRelationship |
| 1️⃣5️⃣ | 🚨 emergencyHomeTelephone | 3️⃣1️⃣ | 👪 dependentDateOfBirth |

🗂️ Group icons: 👤 personal, 💼 job and report-to, 🚨 emergency contact, 🏠 address, 📞 phone, ✉️ email, 👪 dependent.

🧑‍🤝‍🧑 Columns 29 to 31 hold the dependent that the Dependents steps add for each employee. Each row has its own dependent name, relationship, and date of birth. Dates use the `yyyy-mm-dd` format, such as `2014-03-18`.

⚠️ If you add a column, the indexes after it change. Update every test that reads those indexes.

📑 **`invalidEmployees.csv`** has these columns: `firstName`, `middleName`, `lastName`, `employeeId`, `username`, `password`, `confirmPassword`.

📑 **`usernames.csv`** has these columns: `Fullname`, `Role`, `Status`, `Username`, `Password`, `Confirmpassword`.

🗃️ **Data providers**

| Provider name | Reads from | Used by |
|---|---|---|
| `validCredentials` | `users.json`, `valid` | ValidLogin |
| `invalidCredentials` | `users.json`, `invalid` | InvalidLogin |
| `validEmployeesData` | `employees.csv` | AddEmployeeTest, EmployeeInformationTest, TimeSheetTest, ReportGenerationTest |
| `invalidEmployeesData` | `invalidEmployees.csv` | Available for tests that use this provider |
| `validUsernames` | `usernames.csv` | AddUserTest |

📝 **Logging settings** are in `log4j2.properties`. See 📜 Reading the logs.

## ▶️ Running the tests

### 💻 From IntelliJ

1. 📂 Open `run.xml`.
2. ▶️ Click the green run arrow next to the `<suite>` line.
3. 🎯 Or open one test class and click the green arrow next to the class name or a test method.

🟢 The Run window shows each test. Green means it passed. 🔴 Red means it failed.

### ⌨️ From Windows Command Prompt

1. 🖥️ Open Command Prompt: press the Start key, type `cmd`, and press Enter.
2. 📁 Go to the project folder. This must be the folder that contains `pom.xml`.

   ```cmd
   cd /d C:\Projects\OrangeHRM
   ```

   ✅ Expected: the prompt shows `C:\Projects\OrangeHRM>`.

3. ▶️ Run all tests listed in `run.xml`.

   ```cmd
   mvn clean test
   ```

   ✅ Expected at the end:

   ```text
   [INFO] Tests run: <TOTAL>, Failures: 0, Errors: 0, Skipped: 0
   [INFO] BUILD SUCCESS
   ```

   🔢 `Tests run` counts each time a `@Test` method runs. A method that uses a data provider runs once for each row that provider returns. With the current code, the eight classes in `run.xml` have <TOTAL> test runs in total: AddEmployeeTest (1), PIMSearchTest (<N>), AddUserTest (1), ValidLogin (1), InvalidLogin (1), EmployeeInformationTest (2), TimeSheetTest (1), and ReportGenerationTest (1). If the number is different, check the data providers and the `@Test` methods again.

   ❌ If any test fails, the `Failures` or `Errors` number is above 0, and the last line is `BUILD FAILURE`.

🌐 A Chrome window opens for each test and closes when the test ends. ✋ Do not close it by hand while a test is running.

### 🎯 Running one class or method

Run one test class:

```cmd
mvn test -Dtest=ValidLogin
```

Run one method. Replace `methodName` with the name of the method in the test class:

```cmd
mvn test -Dtest=ValidLogin#methodName
```

## 🧩 The run.xml file

📄 `run.xml` is a TestNG suite file. It is a list of the test classes to run. The pom tells Maven to read it, through the `<suiteXmlFiles>` setting.

The current file runs these eight classes, in this order:

| Order | Class | Package |
|---|---|---|
| 1️⃣ | AddEmployeeTest | pages.PIMTest.addEmployeeTest |
| 2️⃣ | PIMSearchTest | pages.PIMTest.PIMSearchTest |
| 3️⃣ | AddUserTest | pages.addUserTest |
| 4️⃣ | ValidLogin | pages.loginTest |
| 5️⃣ | InvalidLogin | pages.loginTest |
| 6️⃣ | EmployeeInformationTest | pages.employeeInformationTest |
| 7️⃣ | TimeSheetTest | pages.timeSheetTest |
| 8️⃣ | ReportGenerationTest | pages.PIMTest.reportGenerationTest |

ℹ️ Test classes that are not listed in `run.xml` are not run by `mvn test`.

🔗 **Why the order matters:** AddEmployeeTest creates the employees that EmployeeInformationTest uses. If AddEmployeeTest does not run first, EmployeeInformationTest, PIMSearchTest, and ReportGenerationTest may fail because the employee is missing.

➕ **To add a class,** place a line like this inside `<classes>`:

```xml
<class name = "pages.PIMTest.reportGenerationTest.ReportGenerationTest"/>
```

➖ **To remove a class,** delete its line. Save the file before you run the tests.

## 📜 Reading the logs

The logs have two places:

- 🖥️ **The console:** the Run window in IntelliJ, or the Command Prompt window.
- 📄 **A file:** `logs\application.log` in the project folder. Maven creates it on the first run.

**🖥️ Console format**

```text
2026-10-17 19:49:28 - ❌ Option 'Job' not found. Found 15 options:
```

**📄 File format**

```text
2026-10-17 19:49:28 [ERROR] DropdownHelper:95 - ❌ Option 'Job' not found.
```

Each file line has:
- 🕒 the date and time,
- 🏷️ the level in square brackets: `DEBUG`, `INFO`, `WARN`, or `ERROR`,
- 📍 the class name and line number,
- 💬 the message.

🔍 The log level is `debug`, so every message is written.

**😀 Emoji markers used in the messages**

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

**🕵️ How to find the failing step**

1. 📂 Open `logs\application.log` in a text editor.
2. 🔎 Search for `[ERROR]` or `❌`.
3. ⬆️ Read the lines just above the error to see the last step that worked.
4. 📍 Note the class name and line number, such as `ReportGenerationPage:97`. Open that line in IntelliJ with **Ctrl + Shift + N**.

## 📊 Allure reporting

📈 Allure creates a web report from the test results. The report shows each test, its steps, its errors, and the failure screenshot.

Allure uses two separate parts:

- 🧩 **The Java library (already in the project):** `allure-testng` in `pom.xml`. The tests use it to write result files while they run. You do not install it by hand. Maven downloads it during `mvn clean install`.
- 🖥️ **The command-line tool (you install it once):** this reads the result files and builds the web report. It is the `allure` npm package, version 3.20, as shown in Step 1.

### 1️⃣ Step 1: Install the Allure command-line tool

📦 Install it globally with npm. Open Command Prompt and run:

```cmd
npm install -g allure
```

📌 This downloads the `allure` package from npm and makes the `allure` command work from any folder.

✅ Expected: the command ends with a line like `added N packages` with no `ERR!` lines.

🔍 Check the install:

```cmd
allure --version
```

✅ Expected: `3.20.0`.

ℹ️ The 3.20 command-line tool can read the results that `allure-testng` (2.35.5 in `pom.xml`) writes, so the two versions do not need to match.

❌ If you see `'allure' is not recognized`, close the Command Prompt window, open a new one, and run the check again. npm adds its global folder to `Path` when it is installed.

### 2️⃣ Step 2: Run the tests

🔌 The Allure listener is attached to `BaseTest`, so every test writes results automatically.

```cmd
cd /d C:\Projects\OrangeHRM
mvn clean test
```

📁 After the run, the results are in the `allure-results` folder in the project folder.

### 3️⃣ Step 3: Generate and open the report

🏗️ First, build the report from the results folder:

```cmd
allure generate allure-results --clean -o allure-report
```

✅ Expected: the command finishes without errors and creates an `allure-report` folder in the project folder.

🌐 Then open it in your browser:

```cmd
allure open allure-report
```

✅ Expected: a line like `Allure is running on http://localhost:<port>` and the report opens in your browser.

⏹️ To stop the report server, go back to the Command Prompt window and press **Ctrl + C**.

### 🔍 What the report shows

- 🏁 **Overview:** the number of passed, failed, and skipped tests.
- 🗂️ **Suites:** the tests grouped by class.
- 🧭 **Behaviors:** the tests grouped by the `@Epic`, `@Feature`, and `@Story` labels in the test classes.
- ⏱️ **Timeline:** when each test ran.
- 🧾 **Test details:** for each test, its steps and any error message.
- 📎 **Attachments:** for a failed test, the failure screenshot.

### 📸 Failure screenshots

❌ When a test fails, `BaseTest` saves a screenshot to `target\screenshots` in the project folder. The same image is attached to the test in the Allure report. Open the failed test in the report and look at the attachment to see the page at the moment of failure.

## 📄 Saving the report as PDF

📑 The test can save the Employee Contact info report as a PDF file. The method `saveReportAsPdf` asks Chrome to print the page, so no browser download is needed.

### 🧭 How the PDF is created

1. ⚙️ The method asks Chrome to print the current page with the DevTools command `Page.printToPDF`.
2. 🎨 The print options turn on background colors, use landscape pages, and shrink the page to 50% (`scale` 0.5).
3. 🔤 Chrome returns the PDF as Base64 text, and the method converts it back into a file.
4. 📁 The method creates the `target\downloads` folder if it does not exist.
5. 💾 It writes the PDF with the file name you pass in.
6. 📝 It logs the full path of the saved file.

### 🏷️ Name the file

Pass the file name when you call the method. Include the `.pdf` extension:

```java
reportGenerationPage.saveReportAsPdf("Employee Contact info report.pdf");
```

⚠️ Do not use these characters in the name, because Windows does not allow them: `\ / : * ? " < > |`. Spaces are fine.

### 📂 Where the file is saved

The PDF goes into the `target\downloads` folder inside the project folder:

```text
C:\Projects\OrangeHRM\target\downloads\Employee Contact info report.pdf
```

To find it:

1. 📁 Open File Explorer, go to `C:\Projects\OrangeHRM`, then open `target` and `downloads`.
2. 🔎 Or search `logs\application.log` for `Saved report as PDF`. The line shows the full path.
3. ⌨️ Or run this in Command Prompt:

   ```cmd
   cd /d C:\Projects\OrangeHRM
   dir target\downloads
   ```

🧹 The `target` folder is deleted by `mvn clean`. If you run `mvn clean test`, the PDF from the previous run is removed. Copy the file somewhere else if you need to keep it.

### ✅ Checking that the PDF was saved

The method `isPdfSaved` checks two things:

- 📁 The file exists in `target\downloads`.
- 🔍 The first four bytes of the file are `%PDF`, the header every PDF file starts with.

Call it in the test with the same file name, and use a soft assertion so the test keeps running if the check fails:

```java
String pdfName = "Employee Contact info report.pdf";
reportGenerationPage.saveReportAsPdf(pdfName);

softAssert.assertTrue(reportGenerationPage.isPdfSaved(pdfName),
        "PDF was not saved or is not a valid PDF: " + pdfName);
```

ℹ️ The check confirms that a valid PDF exists. It does not check what the PDF contains.

### ⚠️ Things to know

- 📄 Chrome prints the page as it is on screen. The report shows only the rows on the current page, so the PDF has only those rows.
- 📏 The `scale` setting changes only the PDF. The browser window keeps its normal size.
- ⏳ Wait for the report table to load before you save. A PDF saved too early can be blank.

## 🖼️ Uploading an employee photo

📸 The Add Employee test uploads a profile photo. The photo is a file inside the project, and the test selects it through the operating system's file dialog.

### 📁 Where the photo is kept

Place the photo at this location inside the project folder:

```text
C:\Projects\OrangeHRM\src\main\resources\photos\employee.png
```

The site accepts JPG, PNG, and GIF files up to 1 MB.

### 🧭 How the path is built

The test builds a relative path from the project folder, then converts it to an absolute path. The file dialog needs the full path.

```java
Path photo = Paths.get("src", "main", "resources", "photos", "employee.png");
String fullPath = photo.toAbsolutePath().toString();
```

### 🖱️ How the photo is selected

The method `clickAddPhotoAndSelect` in `AddEmployeePage` runs these steps:

1. 🖱️ Clicks the **+** button on the photo area.
2. ⏱️ Waits 1.5 seconds for the file dialog to open.
3. 📋 Copies the full path to the clipboard.
4. ⌨️ Uses `Robot` to press Ctrl + V, pause, and press Enter. The dialog selects the file.

The test calls it like this:

```java
Path photo = Paths.get("src", "main", "resources", "photos", "employee.png");
addEmployeePage.clickAddPhotoAndSelect(photo.toAbsolutePath().toString());
```

### ✅ Checking the upload

The test checks the photo in two ways:

- 🔄 **The preview changes.** The test reads the preview's `src` before and after the upload, and asserts that the value changed.
- 📏 **The size is within the limits.** The test reads the uploaded image and asserts that it is larger than 4.6 KB (the default photo size) and smaller than 1 MB.

```java
String defaultPhotoSrc = addEmployeePage.getPhotoPreviewSrc();

Path photo = Paths.get("src", "main", "resources", "photos", "employee.png");
addEmployeePage.clickAddPhotoAndSelect(photo.toAbsolutePath().toString());

String uploadedPhotoSrc = addEmployeePage.getPhotoPreviewSrc();
softAssert.assertNotEquals(uploadedPhotoSrc, defaultPhotoSrc,
        "Photo preview did not change after upload");

long uploadedSize = addEmployeePage.getUploadedPhotoSize();
softAssert.assertTrue(uploadedSize > 4.6 * 1024,
        "Uploaded photo is smaller than 4.6 KB: " + uploadedSize + " bytes");
softAssert.assertTrue(uploadedSize < 1024 * 1024,
        "Uploaded photo is larger than 1 MB: " + uploadedSize + " bytes");
```

### ⚠️ Things to know

- 🖥️ `Robot` controls the real keyboard. It needs a desktop session, and the browser must be the active window when it types.
- 🚫 It does not work on a headless run or on a remote machine without a desktop.
- ⏱️ The 1.5-second wait is a fixed delay. On a slow machine, the dialog may take longer to open.
- 🔁 If the site resizes or re-encodes the photo, its size can differ from the file on disk.
- 📂 Check that the photo exists at `src\main\resources\photos\employee.png` before you run the test.

## 🛠️ Troubleshooting

| Problem | Likely cause | Fix |
|---|---|---|
| 🚫 `'mvn' is not recognized as an internal or external command` | Maven is not installed, or its `bin` folder is not on `Path` | Check the Maven steps under 🧰 Prerequisites. Open a new Command Prompt window and run `mvn -version`. |
| ☕ Errors such as `invalid target release: 26` or `release version 26 not supported` | Maven or IntelliJ is using a different Java version | Run `mvn -version` and check the Java version line. Set `JAVA_HOME` to your JDK 26 folder. In IntelliJ, set the Project SDK to 26. |
| 🌐 `This version of ChromeDriver only supports Chrome version...` or `session not created` | Chrome and the downloaded driver do not match | Update Chrome to the latest version, then run the tests again. Selenium Manager needs internet access to download the driver. |
| 🔍 `NoSuchElementException` | The element is not on the page, the page has not finished loading, or the site changed | Open the failure screenshot and check the page. Then check the log for the last step that worked. |
| ⏳ `TimeoutException` | The page or element did not appear within 10 seconds | Check the screenshot. The site may be slow, or the element may have a different name or position. |
| 🔽 `Dropdown option not found` | The text in the data file does not match an option exactly | Compare the value in `employees.csv` with the option on the site. Spelling, spaces, and capital letters all matter. |
| 📑 `CSV file is empty` or `Could not read CSV file` | The tests are running from the wrong folder | Run Maven from the project folder that contains `pom.xml`. The data paths are relative to that folder. |
| 🧾 `Could not read JSON file` or `Invalid JSON` | The file is missing, or the JSON has a syntax error | Check that the file exists in `src/main/resources`. Check for missing commas, quotes, or brackets. |
| 🔁 Tests pass on one run and fail on the next | The demo site's data changed, or the same employee or username already exists from an earlier run | Use new employee IDs and usernames in `employees.csv`. Check the log for messages about duplicate records. |
| 📊 `allure is not recognized` | The Allure command-line tool is not installed or not on `Path` | Follow Step 1 of 📊 Allure reporting (`npm install -g allure`). Open a new Command Prompt window. |
| 📄 `PDF file is not found` | The save step did not run, or the file name in the test is different | Search `logs\application.log` for `Saved report as PDF`. Check that the file name in the call matches the name you look for. |
| 🫥 The PDF is blank or has only part of the table | The table had not loaded when the page was printed, or the table is cut off by its scroll box | Wait for the table to load before you call `saveReportAsPdf`. Check the table width and the paper size settings. |
| 🧨 `ClassCastException` when saving the PDF | The driver is not a `ChromeDriver` | Make sure `GetChromeDriver` creates the driver that the page objects use. |
| 🧹 The PDF disappears after a new run | `mvn clean` deletes the `target` folder | Copy the PDF to another folder after the test, or save it outside `target`. |
| 🖼️ The photo file dialog does not open | The Add photo button was not clicked, or the wait was too short | Check the log for the click. Increase the wait after the click if the dialog is slow. |
| 📂 The photo is not selected | The browser was not the active window when `Robot` typed, or the path was pasted into another window | Keep the browser in front during the test. Do not use other windows while it runs. |
| 🗂️ A different file is selected | The path is wrong or the file is in another folder | Check the absolute path in the log and confirm the photo is at `src\main\resources\photos\employee.png`. |
| 🤖 `AWTException` or `HeadlessException` when using `Robot` | The test runs without a desktop, such as on CI or a headless machine | Run on a desktop session, or upload with `uploadPhoto` (sends the path to the hidden file input). |
| 📏 Photo size check returns `-1` | The browser could not read the preview image | Check that the preview `src` is a `blob:` or `data:` URL. Look at the screenshot to see whether the photo appears. |
| 🫥 The Allure report is empty | The tests did not run, or `allure-results` is in another folder | Run `mvn clean test` first. Then run `allure generate allure-results --clean -o allure-report` from the project folder. |

## ➕ Adding a new test or page object

**📄 To add a page object:**

1. 📁 Create a folder under `src/main/java/pages/`, for example `src/main/java/pages/exampleFeaturePage/`.
2. 🧱 Create a class that extends `BasePage` and has a constructor that calls `super(driver)`.
3. 🎯 Put locators in `private static final By` fields.
4. 🖱️ Add getter methods that return elements and action methods that click or type, following an existing page such as `JobInfoPage`.

**🧪 To add a test:**

1. 📁 Create a folder under `src/test/java/pages/`, for example `src/test/java/pages/exampleFeatureTest/`.
2. 🧱 Create a class that extends `BaseTest`.
3. ✍️ Create the page objects in a method, then add `@Test` methods. Use `softAssert` for checks.
4. 🧩 Add the class to `run.xml`.
5. ▶️ Run `mvn test -Dtest=ExampleFeatureTest` to check the new class.

## ❓ FAQ

**🌐 Do I need to open Chrome first?**
No. The tests open their own Chrome window.

**🚗 Where does the browser driver come from?**
Selenium Manager downloads the driver that matches your Chrome version. This needs internet access.

**🫥 Can the tests run without a window?**
The code does not set a headless mode, so a Chrome window opens for each test.

**🔄 Can I test a different website?**
Change the login address in `BaseTest.java`. Then update the page addresses in `data.json` and the test data to match the new site.

**🧭 Can I use a different browser?**
The tests start Chrome. To use another browser, change the driver creation in `BaseTest.java`.

**📸 Where are the screenshots?**
In `target\screenshots` in the project folder. They are also attached to failed tests in the Allure report.

**📄 Where is the report PDF saved?**
In `target\downloads` in the project folder. The file name is the one you pass to `saveReportAsPdf`. `mvn clean` deletes this folder.

**🖼️ Can the photo upload run without a desktop?**
Yes. Use `uploadPhoto` in `AddEmployeePage`. It sends the file path straight to the hidden file input, so no file dialog or `Robot` is needed.

**🤔 Why did a test that worked yesterday fail today?**
The demo site is shared and can change. Data on the site can also change. Check the screenshot and the log.

## 🔒 Security

- 🚨 `users.json` and `employees.csv` contain usernames and passwords. Do not copy real values into this README, into screenshots, or into shared messages.
- 🙅 Do not share real credentials in a shared Git repository. `.gitignore` does not currently exclude files in `src/main/resources`. To keep these files out of version control, add these lines to `.gitignore`:

  ```text
  src/main/resources/users.json
  src/main/resources/employees.csv
  ```

- 🧹 If these files are already in Git, remove them from tracking first:

  ```cmd
  git rm --cached src/main/resources/users.json src/main/resources/employees.csv
  ```

- 🔐 For a team project, consider reading credentials from environment variables instead of storing them in files.

---

✨ *Happy testing!* 🧪🚀