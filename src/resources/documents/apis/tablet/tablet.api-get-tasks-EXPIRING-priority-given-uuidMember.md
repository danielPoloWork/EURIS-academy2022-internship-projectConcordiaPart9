# Tablet

## Get all Tasks with EXPIRING priority a Member is assigned to

GET /api/tablet/priority/expiring/{uuidMember}

Get all Tasks with ExPIRING priority given a Member uuid

**Request**

PATH PARAMETERS

**UuidMember REQUIRED**


JSON Response for existing member (with an assigned task) bun no expiring priority tasks assigned Example

``` 
{
  "httpRequest": "GET",
  "httpResponse": "FOUND",
  "code": "302",
  "desc": "The request has been fulfilled, resulting in the recovery of a resource.",
  "body": []
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
      "id": "task2",
      "title": "title2",
      "description": "desc2",
      "priority": "LOW",
      "status": "TO_DO",
      "deadLine": "2022-10-07T13:11:32"
    },
    {
      "id": "id pariatur Duis enim",
      "title": "dolor anim ullamco",
      "description": "pariatur sit ex officia",
      "priority": "DONE",
      "status": "COMPLETED",
      "deadLine": "1975-07-19T15:56:41"
    },
  ]
} 
``` 

Response for not existing member or member with no task assigned Example

```
"status": 500,
"error": "Internal Server Error",
```