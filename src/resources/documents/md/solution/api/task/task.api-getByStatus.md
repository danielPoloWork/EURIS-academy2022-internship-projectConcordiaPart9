# Task

## Get a Task by status

GET /api/tasks/status={status}

Get a Task by status

**Request**

PATH PARAMETERS

**Status REQUIRED**

The Status of the task


JSON Response not found Example

``` 
{
  "httpRequest": "GET",
  "httpResponse": "NOT_FOUND",
  "code": "404",
  "desc": "The request has not been fulfilled, resulting in a resource not being retrieved.",
  "body": null
}
```


JSON Response found Example

``` 
{
  "httpRequest": "GET",
  "httpResponse": "FOUND",
  "code": "302",
  "desc": "The request has been fulfilled, resulting in the recovery of a resource.",
  "body": [
    {
      "id": "string",
      "title": "string",
      "description": "string",
      "priority": "HIGH",
      "status": "TO_DO",
      "deadLine": "2022-10-05T08:08:32"
    }
  ]
}
``` 