# Tablet

## Get all Tasks with HIGH priority a Member is assigned to

GET /api/tablet/priority/high/{uuidMember}

Get all Tasks with HIGH priority given a Member uuid

**Request**

PATH PARAMETERS

**UuidMember REQUIRED**


JSON Response for existing member (with an assigned task) bun no high priority tasks assigned Example

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
      "id": "string",
      "title": "string",
      "description": "string",
      "priority": "HIGH",
      "status": "TO_DO",
      "deadLine": "2022-10-06T12:26:21"
    }
  ]
}
``` 

Response for not existing member or member with no task assigned Example

```
"status": 500,
"error": "Internal Server Error",
```