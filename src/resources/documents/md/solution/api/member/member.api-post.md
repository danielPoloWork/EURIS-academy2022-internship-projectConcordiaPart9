# Member

## Create a new Member

POST /api/member

Create a new member

**Request**

### QUERY PARAMETERS

**idTrelloMember REQUIRED**

**username REQUIRED**

**password REQUIRED**

**role REQUIRED**

**name REQUIRED**

**surname REQUIRED**


JSON Response not created Example (missing one required field)

``` 
{
  "timestamp": "2022-10-05T13:17:18.043+00:00",
  "status": 400,
  "error": "Bad Request",
  "path": "/api/member"
}
```


JSON Response created Example

``` 

  "httpRequest": "POST",
  "httpResponse": "CREATED",
  "code": "201",
  "desc": "The request has been fulfilled, resulting in the creation of a new resource.",
  "body": {
    "uuid": null,
    "idTrelloMember": "man",
    "username": "string",
    "role": "A1",
    "name": "string",
    "surname": "string"
  }
}
```