# Comment

## Create a new Comment

POST /1/comment

Create a new comment

**Request**

### QUERY PARAMETERS

**idTask REQUIRED**

**uuidMember REQUIRED**

**text REQUIRED**

JSON Response not found Example (uuidMember not found)

```
{
  "httpRequest": "POST",
  "httpResponse": "NOT_FOUND",
  "code": "404",
  "desc": "The request has not been fulfilled, resulting in a resource not being retrieved.",
  "body": null
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
    "idTrelloComment": null,
    "text": "commento12",
    "lastUpdate": "2022-10-06T12:03:39.5559361"
  }
}
```

