# Rest Assured API Automation Framework

![Java](https://img.shields.io/badge/Java-11+-blue.svg)
![TestNG](https://img.shields.io/badge/TestNG-Framework-brightgreen.svg)
![RestAssured](https://img.shields.io/badge/Rest%20Assured-API%20Testing-orange.svg)
![Allure](https://img.shields.io/badge/Allure-Reporting-purple.svg)

## Overview
This project is a Test Automation Framework built using **Java + Rest Assured + TestNG + Allure Reporting**.  
It validates REST APIs with proper status code checks, JSON schema validation, and data verification.  

The framework is designed to be:
- Modular (separation of `steps`, `utils`, `tests`)  
- Readable (custom logger + Allure reporting)  
- Maintainable (easy to extend with new APIs)  

---

## Tech Stack
- Java 11+  
- Maven  
- Rest Assured  
- TestNG  
- Allure Reports  
- SLF4J Logging  
- JSON Simple  

---

## 🎯 Live Test Reports
**👉 [View Latest Test Results](https://arunramasayam.github.io/restassured-api-automation/)**

*Click above to see detailed Allure test reports with interactive charts and test analysis*  

---

## Project Structure
```bash
src/test/java/com/arunreddy/restassured/
│
├── tests/        # Test classes (organized by feature)
├── steps/        # API steps (request building & sending)
├── utils/        # Utilities (validators, loggers, payloads, tokens)
└── resources/    # JSON schemas, test data
```

---

## How to Run Tests

1. Clone the repo
```bash
git clone https://github.com/arunramasayam/restassured-api-automation.git
cd restassured-api-automation
```

2. Install dependencies
```bash
mvn clean install
```

3. Run Tests
```bash
mvn test
```

4. Run Only REST API Tests
```bash
mvn test -DsuiteXmlFile=testng-rest.xml
```

5. Run only SOAP API Tests
```bash
mvn test -DsuiteXmlFile=testng-soap.xml
```

---

## Allure reporting

1. Generate Allure report after test execution:
```bash
mvn allure:serve
```

2. Or save report in /target/site/allure-maven-plugin using:
```bash
mvn allure:report
```

---

## Example Test Flow
1. **Setup** → Generate auth token, build request payload  
2. **Execution** → Send API request (POST/GET/DELETE etc.)  
3. **Validation** →  
   - Validate status code  
   - Validate JSON schema  
   - Validate response body data  
4. **Teardown** → Delete test data (if required)  

---

## Author
**Arun Ramasayam**  
QA Automation Engineer | Rest Assured | TestNG | Java
