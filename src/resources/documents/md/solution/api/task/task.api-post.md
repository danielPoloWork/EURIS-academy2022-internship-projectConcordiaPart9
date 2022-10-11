# Task

## Create a new List

POST /1/tasks

Create a new task

**Request**

QUERY PARAMETERS

**id REQUIRED**

**title REQUIRED**

**description REQUIRED**

**priority REQUIRED**

**status REQUIRED**

**deadline REQUIRED**


JSON Response not created Example (id already existing)

``` 
{
  "timestamp": "2022-10-05T15:23:16.251+00:00",
  "status": 500,
  "error": "Internal Server Error",
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