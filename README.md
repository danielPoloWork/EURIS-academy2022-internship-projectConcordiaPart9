# EURIS Academy 2022
## Internship project Part 9
# Concordia research station project
## INITIAL INSTRUCTIONS
### Goal
[**Concordia**](https://www.italiantartide.it/st-italo-francese-concordia/) is one of the two Italian scientific bases in 
Antarctica in which various university researchers carry out experiments. Because of its location, the base **has no 
possibility of connecting to the Internet except in a certain period of time**, that is, when a satellite passes over 
the base and then connects it with the rest of the world.

The various research centers around the world, however, must be able to request, day after day, at will, the scientists 
housed in the base, the conduct of some experiments and for this purpose Trello has been chosen as the instrument in 
which the research centers will insert their requests. Despite the problems of connection with the rest of the world, 
the Concordia base has, of course, an IT structure up to the task.

Precisely on this we want to install an application that helps scientists in the base and research centers of the world 
in their daily work, taking advantage of the time window granted (from the satellite), to be able to update scientists 
on what they have to do, and research centers on the status of experiments

### Tools
- Microsoft Teams
- FortiClient VPN
- Virtual machine
- IDEs (IntelliJ Community, NetBeans, Eclipse, ...)
- DBMS (PostgreSQL, MySQL, Oracle Database, MS SQL Server, ... )
- BitBucket

### Team Organization
The team will have to organize itself in such a way as to:
- Define the architecture together
- Design the architecture and document it
- Divide tasks
- Implement solution

### Reference
API/DOC Contact person
- [API Introduction](https://developer.atlassian.com/cloud/trello/guides/rest-api/api-introduction/)
- [The Trello REST API](https://developer.atlassian.com/cloud/trello/rest/api-group-actions/)

### Commit to a single repository (for each team)
Each team will have a BitBucket project in which to save their work (including documentation)

## SOFTWARE TO BE DEVELOPED
### HIGH│Requirement 1) Align the base to the requests
Whenever possible, the dashboard data should be transferred to Concordia's information systems. Below are the only data 
of interest that you want to take into consideration

- **LIST** 
  - Title
- **CARD**
  - Title,
  - Description,
  - Expiration (if any),
  - Priority (HIGH, MEDIUM, LOW),
  - Last Comment,
  - Assignee (if any. Beware that there may be more than one)

### MEDIUM│Requirement 2) Scientists work on assigned tasks
Scientists must be able to work and then modify the assigned tasks at any time of the day, regardless of the position of
the satellite. To modify the task by scientists we mean only:
- Add a comment
- Change the task list to make known the progress

### HIGH│Requirement 3) Align research centers
The satellite is connected...
The time has come to make known to the various research centers around the world the progress of the tasks assigned to 
our scientists

### HIGH│Requirement 4) Supplied devices
Scientists have at their disposal tablets with which they are connected to the local network of the base and with which, 
in addition to keeping track of the progress of the assigned tasks, they have highlighted the tasks not completed in 
this order:
1. High priority tasks
2. Expiring tasks (<5 days from those that expire earlier)
3. Medium priority tasks
4. Low-priority tasks

### MEDIUM│Non-functional requirements
1. Parameterize the values with which the alignment takes place because the time and the time window of passage of the 
   satellite, and therefore of the connection with the rest of the world, varies according to the period
2. Define the values at point A to be able to cover the entire calendar year without having to put your hand to the
   code once deployed on the concordia systems

### LOW│Optional Requirements
Requirements to be implemented IF and ONLY IF all mandatory requirements have been implemented and validated

**Checklist**
1. Define an elementary WEB interface for scientists. They will have to select their name from a list (assignees of the 
   various tasks) selected the name they will see the tasks assigned according to the priority and at the deadline they 
   can open the particular task to modify it (within the limit of their role)
2. Send a PDF report at the time of transfer of data from the base to a parameterized email address. In particular, you 
   want to know how many tasks are to be done, how many are running and how many have been completed. We want to know 
   within this report also a percentage for each scientist of tasks concluded on assigned tasks being able to display it
   from the highest (most "productive" scientist) to the lowest.
   
   For example: MARIO ROSSI 30 experiments performed out of 50 assigned, 60% (maybe the percentage display it as a bar 
   <30% red, >=30% &&&<60% orange, >=60% green).

## DOCUMENTATION
### HIGH│Before you start writing even half a line of code
Share the following with your tutor

**TODO**
- Produce documentation of the software architecture
- Divide tasks among team mates
- Timing assumed...

### HIGH│Documents to be produced
Throughout the development of the webapp documents must be produced and kept updated continuously

**Documents to be produced**
Hypothesized architecture and motivations behind the choices

**Contents of the document**
- ABSTRACT -> brief description of the request
- REQUIREMENTS -> explain the requirements
- PROPOSED SOLUTION -> a small UML diagram describing the architecture and purpose of each component/module.
- WORKING HYPOTHESIS -> how you will divide the tasks (who does what)
- TECHNOLOGIES
- TEST -> how do you plan to test the application? 10 lines on your testing system you intend to adopt and/or the data 
  needed to validate the implementation
- TIMING -> what are the checkpoints you imagine?

**Hint:** maximum 5 pages for the entire document.

## ADDITIONAL TASKS
### LOW│SOLID Principles
Some pattern (NOT RANDOM) among those you have seen during the course.

### LOW│Unit Test
One of your tutors is the one who taught you testing.









































