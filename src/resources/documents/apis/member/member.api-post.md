# Member

## Create a new Member

POST /1/member

Create a new member

**Request**

### QUERY PARAMETERS

**idTrelloMember REQUIRED**

**username REQUIRED**

**password REQUIRED**

**role REQUIRED**

**name REQUIRED**

**surname REQUIRED**


JSON Response not created Example

``` 
{
  "timestamp": "2022-10-05T09:45:40.830+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "path": "/api/member"
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
    "uuid": null,
    "idTrelloMember": "string",
    "username": "string",
    "role": "A1",
    "name": "string",
    "surname": "string"
  }
}
```