# CONCORDIA APPLICATION
## Abstract
https://www.italiantartide.it/st-italo-francese-concordia/ 
**Concordia** is one of the two Italian scientific bases in Antarctica where various university researchers carry out experiments. Due to its location, the base **has chance get Internet connection only in a short period of time**, that is, when a satellite passes over the base and so the connection can be established with the rest of the world. However, various research centers around the world must be able to request, day after day, at their leisure, to the scientists hosted in the base, the result of some experiments. For this purpose Trello has been chosen as a tool in which the research centers will add their requests. 

Despite the problems of connection with the rest of the world, the Concordia base has, of course, an IT structure up to par. Precisely on this we want to install an application that helps researchers in the base and the research centers of the world in their daily work, taking advantage of the window of time granted (by the satellite), to be able to update researchers on what they have to do, and research centers on the status of experiments. 

## Requirements
 1. **MODELLING - Align the base with the requests.** 
 Whenever possible, the data relating to the dashboard must be transferred to Concordia's application. Below are the only data of interest that you want to take into account:
• List → Title;
• Card → Title, Description, Deadline (if any), Priority (label: HIGH, MEDIUM, LOW),  Last Comment, Assignee (if present there may be more than one);

2. **OPERATIONS DEFINITION - Researchers work on assigned tasks.**
Researchers must be able to work and then edit tasks (card) assigned at any time of the day, regardless of the position of the satellite. To edit we mean only:
• Add a comment;
• Move the task from one list to another to make known the progress;

3. **DATA SYNC - Research center alignment.**
When the satellite is connected, the progress of the tasks assigned to our scientists must be made known to the various research centers around the world.

4. **OPERATIONAL DASHBOARD - Supplied devices.**
Scientists have tablets, at their disposal, with which they are connected to the base local network. In addition to keeping track of the progress of the assigned tasks, they highlight the tasks not concluded in this order:
• High priority tasks
• Expiring tasks (less than 5 days left)
• Medium priority tasks
• Low priority tasks

## Solution
### Database
![UML](https://raw.githubusercontent.com/danielPoloWork/Concordia/master/concordia-db-uml.png)
#### TABLE: User
|FIELD|DATATYPE|PK|FK|NN|UQ|B|UN|AI|G|
|-|-|-|-|-|-|-|-|-|-|
|uuid|CHAR(36)|✓|-|✓|-|-|-|-|-|
|username|CHAR(18)|-|-|✓|✓|-|-|-|-|
|password|VARCHAR(16)|-|-|✓|-|-|-|-|-|
|role|VARCHAR(45)|-|-|✓|-|-|-|-|-|

 > **Role** in java is an enum('ADMIN', 'SCIENTIST_0', 'SCIENTIST_1')
 
#### TABLE: Comment
|FIELD|DATATYPE|PK|FK|NN|UQ|B|UN|AI|G|
|-|-|-|-|-|-|-|-|-|-|
|id|CHAR(24)|✓|-|✓|-|-|-|-|-|
|idMember|CHAR(24)|-|✓|✓|-|-|-|-|-|
|idTask|CHAR(24)|-|✓|✓|-|-|-|-|-|
|text|VARCHAR(1000)|-|-|✓|-|-|-|-|-|

#### TABLE: Member
|FIELD|DATATYPE|PK|FK|NN|UQ|B|UN|AI|G|
|-|-|-|-|-|-|-|-|-|-|
|id|CHAR(24)|✓|-|✓|-|-|-|-|-|
|name|VARCHAR(45)|-|-|✓|-|-|-|-|-|
|surname|VARCHAR(45)|-|-|✓|-|-|-|-|-|
|role|VARCHAR(45)|-|-|✓|-|-|-|-|-|
> **Role** in java is an enum('A1', 'A2', 'A3', 'B1', 'C1', 'C2')
> |LABEL|NAME|
> |-|-|
> |A1|Scientific for astrophysics|
> |A2|Scientific for chemistry and glaciology|
> |A3|Scientific for atmospheric physics|
> |B1|Qualified doctor emergency area|
> |C1|Electronic engineer|
> |C2|ICT/Radio Technician|

#### TABLE: Task
|FIELD|DATATYPE|PK|FK|NN|UQ|B|UN|AI|G|
|-|-|-|-|-|-|-|-|-|-|
|id|CHAR(24)|✓|-|✓|-|-|-|-|-|
|title|VARCHAR(100)|-|-|✓|-|-|-|-|-|
|description|VARCHAR(1000)|-|-|✓|-|-|-|-|-|
|deadline|DATETIME|-|-|-|-|-|-|-|-|
|priority|VARCHAR(8)|-|-|✓|-|-|-|-|-|
|status|VARCHAR(11)|-|-|✓|-|-|-|-|-|

> **Priority** in java is an enum('HIGH', 'EXPIRING', 'MEDIUM', 'LOW', 'DONE', 'ARCHIVED')
> |LABEL|ID|NAME|COLOR|
> |-|-|-|-|
> |HIGH|6331a62237ccc99fc3e4b2ec|High Priority|RED|
> |EXPIRING|6331a62237ccc99fc3e4b2e5|Expiring Priority|ORANGE|
> |MEDIUM|6331a62237ccc99fc3e4b2e1|Medium Priority|YELLOW|
> |LOW|6331a62237ccc99fc3e4b2eb|Low Priority|BLUE|
> |DONE|6331a62237ccc99fc3e4b2e3|Done|GREEN|
> |ARCHIVED|6331a62237ccc99fc3e4b2ed|Archived|PURPLE|

> **Status** in java is an enum('TO_DO', 'IN_PROGRESS', 'COMPLETED')
> |LIST|ID|NAME|
> |-|-|-|
> |TO_DO|6331a6227f9f991a315ca09e|Tasks to do|
> |IN_PROGRESS|6331c2489132c20205bac3ab|Tasks in progress|
> |COMPLETED|6331c24bce6fc400d92fe580|Tasks completed|

#### TABLE: Assignee
|FIELD|DATATYPE|PK|FK|NN|UQ|B|UN|AI|G|
|-|-|-|-|-|-|-|-|-|-|
|id|CHAR(24)|✓|-|✓|-|-|-|-|-|
|idMember|CHAR(24)|-|✓|✓|-|-|-|-|-|
|idTask|CHAR(24)|-|✓|✓|-|-|-|-|-|

### API
#### Concordia
- **GET** 
	/task
	/task/{id}
	/comment
	/comment/{id}
	/assignee
	/assignee/{id}
	/member
	/member/{id}
- **POST**
	/task
	/assignee
	/comment
	/member
- **PUT**
	 /task
	 /assignee
	 /comment
	 /member
- **DELETE**
	/task
	/task/{id}
	/assignee
	/assignee/{id}
	/comment
	/comment/{id}
	/member
	/member/{id}
	
#### Trello
- [**GET** /1/card/{idCard}](https://developer.atlassian.com/cloud/trello/rest/api-group-actions/#api-card-id-get) 
- [**GET** /1/card](https://developer.atlassian.com/cloud/trello/rest/api-group-actions/#api-card-get) 
 - [**GET** /1/list/{idList}/cards](https://developer.atlassian.com/cloud/trello/rest/api-group-lists/#api-lists-id-cards-get)
  - [**GET** /1/members](https://developer.atlassian.com/cloud/trello/rest/api-group-lists/#api-members-get)
- [**GET** /1/members/{idMember}](https://developer.atlassian.com/cloud/trello/rest/api-group-lists/#api-members-id-get)
 - [**GET** /1/labels/{id}](https://developer.atlassian.com/cloud/trello/rest/api-group-lists/#api-labels-id-get)
 - [**GET** /1/boards/{idBoard}/labels](https://developer.atlassian.com/cloud/trello/rest/api-group-boards/#api-boards-id-labels-get)
- [**GET** /1/cards/{idCard}/actions?filter=commentCard](https://developer.atlassian.com/cloud/trello/rest/api-group-cards/#api-cards-id-actions-get)
 
## Job schedule
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
- **JUnit**
- **Mockito**
## Estimated development time
3 weeks
###### © 2022 All rights reserved. Developed by Cescutti Marcello, Collinassi Antonio, Coppetti Simone, Polo Daniel 