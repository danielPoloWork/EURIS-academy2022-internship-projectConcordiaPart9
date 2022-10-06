# Comment

## Get a Comment

GET /api/tasks/{uuid}

Get a Comment by uuid

**Request**

PATH PARAMETERS

**uuid REQUIRED**

The uuid of the comment

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
  "body": {
    "uuid": "82772932-4560-11ed-acf7-0050568f0077",
    "idTrelloComment": null,
    "text": "commento12",
    "lastUpdate": "2022-10-06T12:20:33"
  }
}
``` 