# DATABASE SCHEMA

Given our skills and the type of project we opted to use a MySql database for the application in production while an H2 
database to test the features. The draft envisages that a "concordia" scheme will be created and that the nomenclature 
rules will be as follows:
- Scheme name must be lower case (ex: lowercase)
- Table names must be pascal case (ex: PascalCase)
- Fields and keys must be camel case (ex: snake_case)
- Use Upper case only for system, logging and configurations tables (ex: CAMELCASE)
- Do not use underscore, snake case (ex: snake_case)

## About the Concordia database schema
- Click [**here**](https://dbdiagram.io/d/6337fbc07b3d2034fffedc6d) to directly open UML project website (read-only)

The following PDFs/PNGs show the database schemas for different Concordia versions:
- [**Open PDF/PNG v1.1**](uml-diagram.v1-1.md)
- [**Open PDF/PNG v1.2**](uml-diagram.v1-2.md)
- [**Open PDF/PNG v1.3**](uml-diagram.v1-3.md)
- [**Open PDF/PNG v1.4**](uml-diagram.v1-4.md)

The database schema is also described in ../java/resources/database/changeLog/ xml files in the Concordia web 
application. All entitymodel.xml file has an XML definition of all Concordia database tables, table columns, and their 
data type. Some relationships between tables also appear in the files.
- [**Assignee**](../../../../../main/resources/database/changeLog/create.table.assignee.xml)
- [**Comment**](../../../../../main/resources/database/changeLog/create.table.comment.xml)
- [**Configuration**](../../../../../main/resources/database/changeLog/create.table.configuration.xml)
- [**Connection Window**](../../../../../main/resources/database/changeLog/create.table.connection-window.xml)
- [**Member**](../../../../../main/resources/database/changeLog/create.table.member.xml)
- [**Task**](../../../../../main/resources/database/changeLog/create.table.task.xml)


The generation of the schema is manual and must be carried out by an administrator while the creation of the tables is 
automated through the previous XML scripts and scheduled through the liquibase framework that also offers an excellent 
versioning to cope with subsequent changes with lots of changelog tables. If you want to check the file that generates 
the sequence you can click [**here**](../../../../../main/resources/database/change.log.xml).

#### TABLE: Comment
|FIELD|DATATYPE|PK|FK|NN|UQ|B|UN|AI|G|
|-|-|-|-|-|-|-|-|-|-|
|uuid|CHAR(36)|✓|-|✓|-|-|-|-|-|
|idTrelloComment|CHAR(24)|-|-|-|-|-|-|-|-|
|idTask|CHAR(24)|-|✓|✓|-|-|-|-|-|
|uuidMember|CHAR(36)|-|✓|✓|-|-|-|-|-|
|text|VARCHAR(1000)|-|-|✓|-|-|-|-|-|
|dateCreation|DATETIME|-|-|✓|-|-|-|-|-|
|dateUpdate|DATETIME|-|-|✓|-|-|-|-|-|

#### TABLE: Member
|FIELD|DATATYPE|PK|FK|NN|UQ|B|UN|AI|G|
|-|-|-|-|-|-|-|-|-|-|
|uuid|CHAR(36)|✓|-|✓|-|-|-|-|-|
|idTrelloMember|CHAR(24)|-|✓|-|-|-|-|-|-|
|username|VARCHAR(100)|-|-|✓|✓|-|-|-|-|
|password|VARCHAR(100)|-|-|✓|-|-|-|-|-|
|role|VARCHAR(45)|-|-|✓|-|-|-|-|-|
|firstName|VARCHAR(45)|-|-|✓|-|-|-|-|-|
|lastName|VARCHAR(45)|-|-|✓|-|-|-|-|-|
|dateCreation|DATETIME|-|-|✓|-|-|-|-|-|
|dateUpdate|DATETIME|-|-|✓|-|-|-|-|-|



> **Role** in java is an enum('A1', 'A2', 'A3', 'B1', 'C1', 'C2','CONCORDIA','WRS','ADMIN','MANAGER')
> |LABEL|NAME|
> |-|-|
> |A1|Scientific for astrophysics|
> |A2|Scientific for chemistry and glaciology|
> |A3|Scientific for atmospheric physics|
> |B1|Qualified doctor emergency area|
> |C1|Electronic engineer|
> |C2|ICT/Radio Technician|
> |CONCORDIA|Concordia research station|
> |WRS|World research station|
> |ADMIN|IT Admin|
> |MANAGER|User detail manager|

#### TABLE: Task
|FIELD|DATATYPE|PK|FK|NN|UQ|B|UN|AI|G|
|-|-|-|-|-|-|-|-|-|-|
|id|CHAR(24)|✓|-|✓|-|-|-|-|-|
|title|VARCHAR(255)|-|-|✓|-|-|-|-|-|
|description|VARCHAR(5000)|-|-|✓|-|-|-|-|-|
|priority|VARCHAR(45)|-|-|✓|-|-|-|-|-|
|status|VARCHAR(45)|-|-|✓|-|-|-|-|-|
|deadline|DATETIME|-|-|-|-|-|-|-|-|
|dateCreation|DATETIME|-|-|✓|-|-|-|-|-|
|dateUpdate|DATETIME|-|-|✓|-|-|-|-|-|


> **Priority** in java is an enum('HIGH', 'EXPIRING', 'MEDIUM', 'LOW', 'DONE', 'ARCHIVED')
> |LABEL|ID|NAME|COLOR|
> |-|-|-|-|
> |HIGH|6331********************|High Priority|RED|
> |EXPIRING|6331********************|Expiring Priority|ORANGE|
> |MEDIUM|6331********************|Medium Priority|YELLOW|
> |LOW|6331********************|Low Priority|BLUE|
> |DONE|6331********************|Done|GREEN|
> |ARCHIVED|6331********************|Archived|PURPLE|

> **Status** in java is an enum('TO_DO', 'IN_PROGRESS', 'COMPLETED')
> |LIST|ID|NAME|
> |-|-|-|
> |TO_DO|6331********************|Tasks to do|
> |IN_PROGRESS|6331********************|Tasks in progress|
> |COMPLETED|6331********************|Tasks completed|

#### TABLE: Assignee
|FIELD|DATATYPE|PK|FK|NN|UQ|B|UN|AI|G|
|-|-|-|-|-|-|-|-|-|-|
|uuid|CHAR(36)|✓|-|✓|-|-|-|-|-|
|uuidMember|CHAR(36)|-|✓|✓|-|-|-|-|-|
|idTask|CHAR(24)|-|✓|✓|-|-|-|-|-|
|dateCreation|DATETIME|-|-|✓|-|-|-|-|-|


### Motivations following changes
Following subsequent briefing we opted to merge between the User table and the Member table to avoid code redundancy. 
Initially it was considered to have two separate tables between the creation of the members and a table to manage the 
authentication service with its privileges in anticipation of a front-end development.


## Changelog v1.4

#### TABLE: Configuration
|FIELD|DATATYPE|PK|FK|NN|UQ|B|UN|AI|G|
|-|-|-|-|-|-|-|-|-|-|
|label|VARCHAR(255)|✓|-|✓|-|-|-|-|-|
|value|VARCHAR(1000)|-|-|✓|-|-|-|-|-|
|dateCreation|DATETIME|-|-|✓|-|-|-|-|-|
|dateUpdate|DATETIME|-|-|✓|-|-|-|-|-|


#### TABLE: Connection Window
|FIELD|DATATYPE|PK|FK|NN|UQ|B|UN|AI|G|
|-|-|-|-|-|-|-|-|-|-|
|month|TINYINT|✓|-|✓|-|-|-|-|-|
|cron|VARCHAR(255)|-|-|✓|-|-|-|-|-|
|dateCreation|DATETIME|-|-|✓|-|-|-|-|-|
|dateUpdate|DATETIME|-|-|✓|-|-|-|-|-|