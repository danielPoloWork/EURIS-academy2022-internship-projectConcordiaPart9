
# APIs Trello


## Cards 

- [**GET** /1/cards/{idCard}](https://developer.atlassian.com/cloud/trello/rest/api-group-cards/#api-cards-id-get) Get a task.
- [**GET** /1/cards/{idCard}/actions?filter=commentCard](https://developer.atlassian.com/cloud/trello/rest/api-group-cards/#api-cards-id-actions-get) Get all comments on a card.
- [**POST** /1/cards/{id}/idLabels](https://developer.atlassian.com/cloud/trello/rest/api-group-cards/#api-cards-id-idlabels-post) Add a priority to a task.
- [**POST** /1/cards/{idCard}/actions/comments](https://developer.atlassian.com/cloud/trello/rest/api-group-cards/#api-cards-id-actions-comments-post) Add a new comment to a task.
- [**DELETE** /1/cards/{id}/idLabels/{idLabel}](https://developer.atlassian.com/cloud/trello/rest/api-group-cards/#api-cards-id-idlabels-idlabel-delete) Remove a priority from a task.
- [**PUT** /1/cards/{idCard}](https://developer.atlassian.com/cloud/trello/rest/api-group-cards/#api-cards-id-put) Update a task.
- [**DELETE** /1/cards/{id}/actions/{idAction}/comments](https://developer.atlassian.com/cloud/trello/rest/api-group-cards/#api-cards-id-actions-idaction-comments-delete) Remove a comment from a task.
- [**PUT** /1/cards/{id}/actions/{idAction}/comments](https://developer.atlassian.com/cloud/trello/rest/api-group-cards/#api-cards-id-actions-idaction-comments-put) Update a comment.




## List

- [**GET** /1/lists/{idList}/cards](https://developer.atlassian.com/cloud/trello/rest/api-group-lists/#api-lists-id-cards-get) Get all tasks in a list.


## Board

- [**GET** /1/boards/{idBoard}/members](https://developer.atlassian.com/cloud/trello/rest/api-group-boards/#api-boards-id-members-get) Get all members of the board.


## Member

- [**GET** /1/members/{idMember}](https://developer.atlassian.com/cloud/trello/rest/api-group-members/#api-members-id-get) Get a member.
