# 🏢 OrangeHRM Test Automation Framework

A comprehensive test automation framework for OrangeHRM application built using Java, Selenium, and TestNG.

## 📋 Project Overview

This framework automates end-to-end testing of the OrangeHRM Human Resource Management system, covering:
- Login & Authentication
- User Management (Admin Module)
- Employee Management (PIM Module)
- Leave Management
- Recruitment Module
- API Testing

## 🏗️ Framework Architecture

### Page Object Model (POM)
This framework follows the Page Object Model design pattern for better maintainability and scalability.

### Structure:
```text
src/
 ├── main/java/com.orangehrm
 │    ├── base/
 │    │    └── BasePage.java       # Parent class for all page objects
 │    └── pages/
 │         ├── LoginPage.java     # Login page actions & elements
 │         └── DashboardPage.java # Dashboard page actions & elements
 └── test/java/com.orangehrm.tests
      ├── BaseTest.java           # Parent class for all test classes
      ├── LoginTests.java         # Login functionality tests
      └── DashboardTests.java     # Dashboard functionality tests
      
```
### Design Patterns Used:
1. **Page Object Model** - Each page is represented as a class
2. **Inheritance** - BasePage provides common functionality
3. **Encapsulation** - Page elements hidden from tests
4. **Method Chaining** - Fluent navigation between pages
## 🛠️ Technologies Used

- **Language:** Java 11
- **Build Tool:** Maven
- **Testing Framework:** TestNG
- **Web Automation:** Selenium WebDriver 4.27
- **Reporting:** Extent Reports
- **Design Pattern:** Page Object Model

## 📊 Current Progress

### Week 1 Completed ✅
- [x] Project setup with Maven
- [x] Selenium WebDriver configuration
- [x] TestNG framework integration
- [x] Login module automation (7 test scenarios)
- [x] Page Object Model implementation
- [x] BasePage utility class
- [x] Dashboard navigation tests

**Total Tests:** 10+  
**Pass Rate:** 100%  
**Code Quality:** Production-ready with POM
```

## 📦 Setup Instructions

### Prerequisites
- Java JDK 11 or higher
- Maven 3.6+
- Chrome browser

### Installation
```bash
# Clone repository
git clone https://github.com/zainbasra/orangehrm-automation-framework.git

# Navigate to project
cd orangehrm-automation-framework

# Install dependencies
mvn clean install

# Run tests
mvn test
```

## 🎯 Project Roadmap

- [x] Week 1: Login & Authentication Module
- [ ] Week 2: Admin Module - User Management
- [ ] Week 3: PIM Module - Employee Management
- [ ] Week 4: Leave Management Module
- [ ] Week 5: API Automation
- [ ] Week 6: Framework Enhancement (Reporting, Logging)
- [ ] Week 7: CI/CD Integration
- [ ] Week 8: Final Polish & Documentation

## 📬 Contact

- **Author:** Zain Ul Rehman
- **LinkedIn:** https://www.linkedin.com/in/zain-ul-rehman-410309178
- **Email:** mrzain011@gmail.com


---

⭐ If you find this project useful, please give it a star!