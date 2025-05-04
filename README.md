# SauceDemo Selenium Automation Framework

![GitHub Actions](https://github.com/Dule01/sauce-demo/actions/workflows/run-smoke.yml/badge.svg)
![GitHub Actions](https://github.com/Dule01/sauce-demo/actions/workflows/run-regression.yml/badge.svg)

This project is a robust automation framework built using **Java**, **Selenium WebDriver**, **TestNG**, **Maven**, and **ExtentReports**, designed to automate and validate test cases on the [SauceDemo](https://www.saucedemo.com) web application.

---

## 📁 Project Structure

```bash
.
├── .github/
│   └── workflows/                 # GitHub Actions workflows (YAML)
├── logs/                          # Log output from Log4j
├── reports/                       # ExtentReports HTML output
├── screenshots/                   # Screenshots on test failure
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com.saucedemo/
│   │           ├── pages/         # Page Object Model classes
│   │           ├── utils/         # General-purpose utility classes
│   │           └── org/           # Reserved for potential expansions
│   └── test/
│       ├── java/
│       │   └── com.saucedemo/
│       │       ├── tests/         # Test classes organized by feature
│       │       ├── reports/       # ExtentReport config
│       │       └── utils/         # ScreenshotUtils, ConfigReader, etc.
│       └── resources/             # config.properties, testng XML files
├── target/                        # Maven build output
├── pom.xml                        # Maven configuration
├── .gitignore
```

---

## 🚀 Features

* ✅ Page Object Model structure
* ✅ Cross-browser support: Chrome, Firefox, Edge
* ✅ Support for **headless** execution (CI-friendly)
* ✅ TestNG Groups: `smoke`, `regression`
* ✅ Parameterization via `config.properties` or CLI
* ✅ Screenshot capture on failure
* ✅ ExtentReports HTML report
* ✅ GitHub Actions CI integration

---

## ⚙️ Configuration

**`src/test/resources/config.properties`**

```properties
browser=chrome
headless=false
baseUrl=https://www.saucedemo.com/
username=standard_user
password=secret_sauce
```

---

## 🧪 Running Tests

### 🔹 Via Maven (with dynamic parameters)

**Smoke tests:**

```bash
mvn clean test \
 -Dsurefire.suiteXmlFiles=src/test/resources/testng/smoke-tests.xml \
 -Dbrowser=chrome \
 -Dheadless=true
```

**Regression tests:**

```bash
mvn clean test \
 -Dsurefire.suiteXmlFiles=src/test/resources/testng/regression-tests.xml \
 -Dbrowser=chrome \
 -Dheadless=true
---

## ⚙️ GitHub Actions

CI pipelines are defined in:

```
.github/workflows/run-smoke.yml
.github/workflows/run-regression.yml
```

Each YAML configures:

* Java 17 environment
* Chrome headless test execution
* Trigger on push to `main` or manual dispatch

---

## 📊 Reports

After test execution, an HTML report is generated in the `reports/` folder:

```
/reports/TestReport_yyyy-MM-dd_HH-mm-ss.html
```

Includes:

* Test steps
* Screenshots on failure
* Pass/Fail status

---

## 🔧 Extensibility

You can extend this framework to include:

* ✅ Parallel test execution (e.g. with TestNG XML or ThreadLocal WebDriver)
* ✅ Data-driven testing with external files (Excel, CSV, JSON)
* ✅ API testing modules (REST-assured)

---

## 🧠 Credits

Developed by Dušan Simić as part of an Upwork-ready portfolio project.
