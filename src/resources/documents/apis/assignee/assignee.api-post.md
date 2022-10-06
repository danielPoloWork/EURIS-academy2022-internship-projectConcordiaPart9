# Assignee

## Create a new Assignee

POST /1/assignee

Create a new assignee

**Request**

### QUERY PARAMETERS

**uuidMember REQUIRED**

**idTask REQUIRED**


JSON Response not found Example 

```
{
  "httpRequest": "POST",
  "httpResponse": "NOT_FOUND",
  "code": "404",
  "desc": "The request has not been fulfilled, resulting in a resource not being retrieved.",
  "body": null
}
```

JSON Response created Example

``` 
{
  "httpRequest": "POST",
  "httpResponse": "CREATED",
  "code": "201",
  "desc": "The request has been fulfilled, resulting in the creation of a new resource.",
  "body": {
    "uuid": null,
    "memberDto": {
      "uuid": "51d83577-44b3-11ed-acf7-0050568f0077",
      "idTrelloMember": "string",
      "username": "string",
      "role": "A1",
      "name": "string",
      "surname": "string"
    },
    "taskDto": {
      "id": "string",
      "title": "asdasd",
      "description": "pariatur sit ex officia",
      "priority": "DONE",
      "status": "COMPLETED",
      "deadLine": "1975-07-19T15:56:41"
    }
  }
}
```

