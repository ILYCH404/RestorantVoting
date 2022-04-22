[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c567df7d3f9f4ff3a4c7f185f59600b4)](https://www.codacy.com/gh/ILYCH404/RestorantVoting/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ILYCH404/RestorantVoting&amp;utm_campaign=Badge_Grade)

Restaurant voting
===============================

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant, and it's lunch menu of the day (2-5 items usually, just a dish name and price)
Menu changes each day (admins do the updates)
Users can vote on which restaurant they want to have lunch at
Only one vote counted per user
If user votes again the same day:
If it is before 11:00 we assume that he changed his mind.
If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides a new menu each day.

-------------------------------------------------------------

- Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 2.5,
Lombok, H2, Swagger/OpenAPI 3.0, 
 Spring Data JPA
- Run: `mvn spring-boot:run` in root directory

-----------------------------------------------------

[REST API documentation](http://localhost:8080/swagger-ui.html)  
Креденшелы:
```
User:  user@yandex.com / password
Admin: admin@gmail.com / admin
```