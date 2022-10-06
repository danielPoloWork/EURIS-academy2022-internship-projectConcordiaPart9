# Comment

## Delete a Comment by uuid

DELETE /api/comment/{uuid}

Delete a Comment

**Request**

PATH PARAMETERS

**uuid REQUIRED**

The uuid of the Comment

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
  "body": null
}
```