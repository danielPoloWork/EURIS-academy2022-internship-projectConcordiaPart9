# Member

## Delete a Member by id

DELETE /api/member/{id}

Delete a Member

**Request**

PATH PARAMETERS

**id REQUIRED**

The ID of the Member

JSON Response not found Example

``` 
{
  "httpRequest": "DELETE",
  "httpResponse": "NOT_FOUND",
  "code": "404",
  "desc": "The request has not been fulfilled, resulting in a resource not being retrieved.",
  "body": null
}
```


JSON Response succeded Example

``` 
{
  "httpRequest": "DELETE",
  "httpResponse": "DELETED",
  "code": "201.2",
  "desc": "The request has been fulfilled, resulting in the deletion of a resource.",
  "body": {
    "uuid": "d37de9ea-44b0-11ed-acf7-0050568f0077",
    "idTrelloMember": "string",
    "username": "string",
    "role": "A1",
    "name": "string",
    "surname": "string"
  }
}
```