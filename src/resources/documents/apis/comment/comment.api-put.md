# Comment

## Update a comment

PUT /1/comments/{id}

Update a comment

**Request**

PATH PARAMETERS

**uuid REQUIRED**

QUERY PARAMETERS

**text REQUIRED**

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
  "httpResponse": "CREATED",
  "code": "201",
  "desc": "The request has been fulfilled, resulting in the creation of a new resource.",
  "body": {
    "uuid": "93b14f62-44c5-11ed-acf7-0050568f0077",
    "idTrelloComment": null,
    "text": "commento modificato",
    "lastUpdate": null
  }
}
```