# Assignee

## Delete an Assignee by UuidMember and IdTask

DELETE /api/assignee

Delete an Assignee

**Request**

QUERY PARAMETERS

**UuidMember REQUIRED**

**IdTask REQUIRED**


JSON Response not found Example

``` 
{
  "httpRequest": "DELETE",
  "httpResponse": "NOT_FOUND",
  "code": "404",
  "desc": "The request has not been fulfilled, resulting in a resource not being retrieved.",
  "body": null
}
```


JSON Response succeded Example

``` 
{
  "httpRequest": "DELETE",
  "httpResponse": "DELETED",
  "code": "201.2",
  "desc": "The request has been fulfilled, resulting in the deletion of a resource.",
  "body": {
    "uuid": "858092de-4561-11ed-acf7-0050568f0077",
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
      "title": "string",
      "description": "string",
      "priority": "HIGH",
      "status": "TO_DO",
      "deadLine": "2022-10-06T12:26:21"
    }
  }
}
```