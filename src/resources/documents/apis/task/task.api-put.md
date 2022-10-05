# Task

## Update a task

PUT /1/tasks/{id}

Update a task

**Request**

PATH PARAMETERS

**id REQUIRED**
CHAR(24)

QUERY PARAMETERS

**Request body REQUIRED**


JSON Response not found Example

``` 
{
  "httpRequest": "PUT",
  "httpResponse": "NOT_FOUND",
  "code": "404",
  "desc": "The request has not been fulfilled, resulting in a resource not being retrieved.",
  "body": null
}
```


JSON Response updated Example

``` 
{
  "httpRequest": "PUT",
  "httpResponse": "UPDATED",
  "code": "201.1",
  "desc": "The request has been fulfilled, resulting in the modification of an old resource.",
  "body": {
    "id": "string",
    "title": "aasdasfd",
    "description": "asfasf",
    "priority": "HIGH",
    "status": "TO_DO",
    "deadLine": "2022-10-05T08:19:38.494"
  }
```