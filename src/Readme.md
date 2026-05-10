A CLI-based grade management system built in Java. Admins, teachers, and students log in and interact with the system based on their role. No frameworks — raw JDBC, PostgreSQL, and Maven.

Stack

Java 21 + Maven
PostgreSQL via raw JDBC
BCrypt (Spring Security Crypto) for password hashing
JUnit 5 + Mockito for testing
dotenv-java for environment config


Architecture
App.java (CLI)
│
├── StudentDAO / TeacherDAO / SubjectDAO / GradeDAO / UserDAO  (interfaces)
│
├── StudentDAOImpl / TeacherDAOImpl / ...  (JDBC implementations)
│
└── PostgreSQL
Models validate themselves — invalid email, null name, future date of birth — all throw before touching the DB.

Setup
bash# 1. Clone
git clone https://github.com/irishprajapati/SGM2.git
cd SGM2

# 2. Add .env in project root
DB_URL=jdbc:postgresql://localhost:5432/your_db
DB_USERNAME=your_user
DB_PASSWORD=your_password

# 3. Run
mvn exec:java

Tests
bashmvn test
# Tests run: 87, Failures: 0, Errors: 0
Two layers tested:

Model — validation logic, no mocks
DAO — JDBC methods mocked with Mockito, no real DB hit
