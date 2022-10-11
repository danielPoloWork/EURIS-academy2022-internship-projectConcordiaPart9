# Tablet

## Get all Tasks with MEDIUM priority a Member is assigned to

GET /api/tablet/priority/medium/{uuidMember}

Get all Tasks with MEDIUM priority given a Member uuid

**Request**

PATH PARAMETERS

**UuidMember REQUIRED**


JSON Response for existing member (with an assigned task) bun no medium priority tasks assigned Example

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
      "id": "task1",
      "title": "title1",
      "description": "desc1",
      "priority": "MEDIUM",
      "status": "TO_DO",
      "deadLine": "2022-10-06T13:11:32"
    }
  ]
}
``` 

Response for not existing member or member with no task assigned Example

```
"status": 500,
"error": "Internal Server Error",
```