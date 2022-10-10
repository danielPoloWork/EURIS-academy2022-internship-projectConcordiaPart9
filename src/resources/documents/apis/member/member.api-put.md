# Member

## Update a member

PUT /api/member/{uuid}

Update a member

**Request**

PATH PARAMETERS

**uuid REQUIRED**

QUERY PARAMETERS

**idTrelloMember REQUIRED**

**password REQUIRED**

**role REQUIRED**

**name REQUIRED**

**surname REQUIRED**


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
    "uuid": "51d83577-44b3-11ed-acf7-0050568f0077",
    "idTrelloMember": "string",
    "username": "string",
    "role": "A1",
    "name": "string",
    "surname": "string"
  }
}
```