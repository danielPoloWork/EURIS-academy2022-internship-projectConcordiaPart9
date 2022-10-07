# Comment


## Get all comments

GET /api/comment

Get all Comments


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
      "uuid": "f7effa9c-44c4-11ed-acf7-0050568f0077",
      "idTrelloComment": null,
      "text": "nuovissimo",
      "lastUpdate": "2022-10-06T10:33:40"
    }
  ]
}
``` 



