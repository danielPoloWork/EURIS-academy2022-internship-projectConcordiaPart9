# CONCORDIA APPLICATION
## Abstract

[**Concordia**](https://www.italiantartide.it/st-italo-francese-concordia/ ) is one of the two Italian scientific bases in Antarctica where various university researchers carry out experiments. Due to its location, the base **has chance get Internet connection only in a short period of time**, that is, when a satellite passes over the base and so the connection can be established with the rest of the world. However, various research centers around the world must be able to request, day after day, at their leisure, to the scientists hosted in the base, the result of some experiments. For this purpose Trello has been chosen as a tool in which the research centers will add their requests. 

Despite the problems of connection with the rest of the world, the Concordia base has, of course, an IT structure up to par. Precisely on this we want to install an application that helps researchers in the base and the research centers of the world in their daily work, taking advantage of the window of time granted (by the satellite), to be able to update researchers on what they have to do, and research centers on the status of experiments. 

## Requirements
 - **MODELLING - Align the base with the requests.** 
 Whenever possible, the data relating to the dashboard must be transferred to Concordia's application. Below are the only data of interest that you want to take into account:
	 - List → Title;
     - Card → Title, Description, Deadline (if any), Priority (label: HIGH, MEDIUM, LOW),  Last Comment, Assignee (if present there may be more than one);

2. **OPERATIONS DEFINITION - Researchers work on assigned tasks.**
Researchers must be able to work and then edit tasks (card) assigned at any time of the day, regardless of the position of the satellite. To edit we mean only:
     - Add a comment;
     - Move the task from one list to another to make known the progress;

3. **DATA SYNC - Research center alignment.**
When the satellite is connected, the progress of the tasks assigned to our scientists must be made known to the various research centers around the world.

4. **OPERATIONAL DASHBOARD - Supplied devices.**
Scientists have tablets, at their disposal, with which they are connected to the base local network. In addition to keeping track of the progress of the assigned tasks, they highlight the tasks not concluded in this order:
     - High priority tasks
     - Expiring tasks (less than 5 days left)
     - Medium priority tasks
     - Low priority tasks

## Solution
- [**Analysis**](src/resources/documents/md/solution/analysis/analysis.md)
- [**Database**](src/resources/documents/md/solution/database/database.md)
- [**APIs Concordia**](src/resources/documents/md/solution/api/concordia-api.md)
- [**APIs Trello**](src/resources/documents/md/solution/api/trello-api.md)

## Job schedule
Small desc of how we sorted jobs.
Who did what and why.

Devs specific activities:
- [**Antonio Collinassi**](src/resources/documents/md/job/antonio-collinassi.md)
- [**Daniel Polo**](src/resources/documents/md/job/daniel-polo.md)
- [**Marcello Cescutti**](src/resources/documents/md/job/marcello-cescutti.md)
- [**Simone Copetti**](src/resources/documents/md/job/simone-coppetti.md)

## Technologies
- **Java**│version 18│
- **MySql**│version 8.0.30│
-  **Lombok  DEVELOPER TOOLS** Java annotation library which helps to reduce boilerplate code.
-   **Spring Web  WEB**│version 2.7.4│Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.
-   **Spring Security  SECURITY**│version 2.7.4│Highly customizable authentication and access-control framework for Spring applications.    
-  **Spring Data JPA  SQL**│version 2.7.4│Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.  
-  **MySQL Driver  SQL** MySQL JDBC and R2DBC driver.
-  **Liquibase Migration  SQL** Liquibase database migration and source control library.
- **Trello**
- **Postman**
## Test
- **H2**
- **JUnit**
- **Mockito**
- UAT tests with **Postman**
## Estimated development time
3 weeks
###### © 2022 All rights reserved. Developed by Cescutti Marcello, Collinassi Antonio, Coppetti Simone, Polo Daniel 