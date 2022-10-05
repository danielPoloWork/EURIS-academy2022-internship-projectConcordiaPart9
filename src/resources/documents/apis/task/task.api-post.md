# Task

## Create a new List

POST /1/tasks

Create a new task

**Request**

QUERY PARAMETERS

**Request body REQUIRED**


JSON Response not created Example

``` 
{
  "timestamp": "2022-10-05T08:00:52.997+00:00",
  "status": 400,
  "error": "Bad Request",
  "path": "/api/task"
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
    "id": "string",
    "title": "string",
    "description": "string",
    "priority": "HIGH",
    "status": "TO_DO",
    "deadLine": "2022-10-05T07:29:25.488"
  }
```