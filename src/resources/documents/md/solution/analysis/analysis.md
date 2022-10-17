# ANALYSIS

## Dashboard
The application will include the mandatory use of Trello by the various world research centers. They will have the 
opportunity to create new cards (experiments) to be included into a TODO list that will be equivalent as a formal 
request. As requested by our customer they will have the opportunity to enter the title, description, priority (a label) 
and deadline. In addition, search centers will have the ability to add, edit or delete their comments at any time 
from every list. The labels are static and are not given the opportunity to add more to the research centers much less 
to the researchers of the Concordia base. Click [**here**](../../../../images/analysis.trello-dashboard.png) to view our 
proposal for a possible dashboard but should not be considered the final product.

## Management of limited satellite connection
Considering the limited window of time to connect to the internet we will verify the connection through ping tests 
before making our requests (fetch, pull, push). To cope with this problem / need we decided to schedule our 
synchronization features automate the process with a batch configuration file that will execute the aforementioned 
commands in face pre-established times.
Considering the ambiguity of our customer's request and assuming that the operator of the internet service via satellite 
connection, knowing the data relating to the trajectory and orbit of its satellite, provides the list with the relative 
dates and times of internet coverage. Having said this and considering that time is limited we have opted not to manage 
the aforementioned table from databases or CVS files that is but to parameterize the value within our application and 
consequently there will have to be an operator who will change this value if necessary.

## Fetch and pull
The development of our application involves the retrieval of data, provided by the various research centers, from trello 
to synchronize them to our local system. Our solution involves using apIs of trello to make a fetch with our local 
system and if the data are missing or outdated we will insert them into our system.

## Data maintenance
Our application will give the possibility to take advantage of numerous features including: 
- Creation of users (researchers) 
- Management of requests: 
  - Take charge of the experiments
  - Assign experiments to researchers
  - Add, edit, remove comments
  - manage the progress
  - View tasks ordered by priority

## Fetch and push
The development of our application requires that our local data goes to update the information on trello to inform the 
various research centers in mere to the progress of the researchers. This involves a fetch between our database and 
trello to verify that the data is up-to-date and synchronized. If they are not, the system provides:
- A feature to move requests from one list to another to monitor progress 
- Change priority labels to emphasize when some experiments are expiring
- Add, edit, or delete comments.

Due limited time we couldn't implement Trello APIs batch to push all information at once. 
Pro for using trello batch API:
- just one ping to check connection
- rollback

## Jasper report and mail service
We used jaspersoft studio to create two jrxml files which then where turned to pdf's using the jasperreports maven 
dependency. The two files created contain data about the application. Then we created a service containing a send
method which is capable of sending e-mails using gmail and the javax.mail api  

## Web application
This was the most optional feature, so we did not spend much time on development. To make it simple we did not use 
frontend frameworks like angular but only:
- HTML
- Javascript
- Jquery
- CSS
- Foundation CSS framework
- Google icons and fonts

## Actions management
Due develop time limitation and to bypass management problems of IT competence we have opted for a solution that does 
not provide the creation of a trello account to each researcher who will have the opportunity to work for the Concordia 
research station. This would have involved the management of a KEY and TOKEN for each user resulting in generation and 
storage in the database. Rather we opted to use a generic account as the aforementioned research station.

### Workflow representation through a simple UML diagram
- [**v1.1**](../../../../images/concordia.data-workflow-uml.v1-1.light.png)
- [**v1.2**](../../../../images/concordia.data-workflow-uml.v1-2.light.png)