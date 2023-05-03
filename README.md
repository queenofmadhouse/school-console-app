# School Console Appplication

  A console-based application designed to manage school-related information, such as groups, students, and courses. There are several versions of the     application available across different branches. You can explore the development history and observe how the application evolved over time, with new technologies being incrementally added.
  
## Functions

- Find all groups with less or equal students’ number
- Find all students related to the course with the given name
- Add a new student
- Delete a student by the id
- Add a student to the course (from a list)
- Remove the student from one of their courses
- Data generator that creates random data in the database, and automatically generates schema and tables if they don't already exist

## Technologies

- Java 8
- Spring Boot
- PostgrSQL
- Flyway Migration
- Spring Data
- Hibernate
- Spring Boot JDBC
- Spring AOP + aspectJ (for logging)

## How to install:

  1) Clone the repository:
  
  ```bash
  git clone https://github.com/queenofmadhouse/school-console-app.git
  ```
  2) Set up the configuration in 'application.yml' (located in src/main/resources/application.yml):
  
  ```bash
  url: jdbc:postgresql://YourIp:YourPort/school
  username: YourUserName
  password: YourPassword
  ```
  3) You're all set! Run the application.
