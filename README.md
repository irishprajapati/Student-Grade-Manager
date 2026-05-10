A role-based CLI application for managing students, teachers, grades, and subjects. Built with Java 21, raw JDBC, and PostgreSQL — no ORM, no web framework. Designed to demonstrate clean layered architecture, interface-driven design, and production-level unit testing practices.

Tech Stack
LanguageJava 21Build ToolApache MavenDatabasePostgreSQLDB AccessJDBC (PreparedStatement, ResultSet)Password HashingBCrypt via Spring Security CryptoEnvironment Configdotenv-javaTestingJUnit 5 + Mockito

Architecture
┌─────────────────────────────────────────┐
│              App.java (CLI)             │
├─────────────────────────────────────────┤
│         DAO Interfaces                  │
│  StudentDAO, TeacherDAO, SubjectDAO...  │
├─────────────────────────────────────────┤
│         DAO Implementations             │
│  StudentDAOImpl, TeacherDAOImpl...      │
│  (raw JDBC — PreparedStatement)         │
├─────────────────────────────────────────┤
│         BaseDao                         │
│  (shared connection handling)           │
├─────────────────────────────────────────┤
│         PostgreSQL                      │
└─────────────────────────────────────────┘
Domain models (Student, Teacher, User) enforce their own validation rules at construction time. Invalid data — malformed email, null fields, out-of-range dates — throws IllegalArgumentException before any database operation is attempted.

Role-Based Access
Feature                Admin    Teacher    Student
──────────────────────────────────────────────────
Manage Students          ✅        ❌         ❌
Manage Teachers          ✅        ❌         ❌
Manage Subjects          ✅        ❌         ❌
View All Students        ✅        ✅         ❌
View Grades              ✅        ✅      own only
View Teacher Info        ✅        ❌         ❌
View Own Profile         ✅        ✅         ✅

Getting Started
Prerequisites

Java 21
Maven 3.8+
PostgreSQL

Installation
bash# Clone the repository
git clone https://github.com/irishprajapati/SGM2.git
cd SGM2
Create a .env file in the project root:
envDB_URL=jdbc:postgresql://localhost:5432/your_database
DB_USERNAME=your_username
DB_PASSWORD=your_password
Run
bashmvn exec:java

Testing
bashmvn test
Tests run: 87, Failures: 0, Errors: 0, Skipped: 0 — BUILD SUCCESS
The test suite covers two layers:
Model Layer — Validates all domain rules in isolation. No mocks, no database dependency. Tests confirm that validation logic behaves correctly for both valid and invalid inputs across all model classes.
DAO Layer — Each JDBC method is tested using Mockito to mock Connection, PreparedStatement, and ResultSet. No real database is contacted during test execution. Tests cover success paths, not-found scenarios, and DatabaseException propagation on SQL failure.

Project Structure
src/
├── main/java/
│   ├── model/          # Domain models with built-in validation
│   ├── Interfaces/     # DAO contracts
│   ├── impl/           # JDBC implementations
│   ├── dao/            # BaseDao — shared connection handling
│   ├── db/             # DBConnection
│   ├── Exception/      # DatabaseException, AccessDeniedException
│   └── util/           # EmailUtils, PhoneUtils, PasswordUtil
└── test/java/
    ├── model/          # Model validation tests
    └── impl/           # DAO layer tests with Mockito

