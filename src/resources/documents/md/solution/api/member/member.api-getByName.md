# Member

## Get a Member by Name

GET /api/member/name={name}

Get a Member by name

**Request**

PATH PARAMETERS

**Name REQUIRED**


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
      "uuid": "51d83577-44b3-11ed-acf7-0050568f0077",
      "idTrelloMember": "string",
      "username": "string",
      "role": "A1",
      "name": "string",
      "surname": "string"
    }
  ]
}
``` 