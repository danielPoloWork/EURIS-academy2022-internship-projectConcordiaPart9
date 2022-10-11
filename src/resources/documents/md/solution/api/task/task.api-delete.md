# Task

## Delete a Task by id

DELETE /api/task/{id}

Delete a Task

**Request**

PATH PARAMETERS

**id REQUIRED**

The ID of the Task

JSON Response not found Example

``` 
{
  "httpRequest": "DELETE",
  "httpResponse": "NOT_FOUND",
  "code": "404",
  "desc": the request has not been fulfilled, resulting in a resource not being retrieved.,
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
    "id": "string",
    "title": "string",
    "description": "string",<
    "priority": "HIGH",
    "status": "TO_DO",
    "deadLine": "2022-10-05T07:22:37"
  }
```