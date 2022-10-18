# APIs Concordia

## Open API
- [**JSON file**](../../../../../main/resources/openAPI/open-api.json)

## Assignee

- [**DELETE** /api/assignee](assignee/assignee.api-deleteByUuidMemberIdTask.md) Delete an assignee by member uuid and task id.
- [**POST** /api/assignee](assignee/assignee.api-post.md) Create a new assignment.


## Comment

- [**DELETE** /api/comment/{uuid}](comment/comment.api-delete.md) Delete a comment by uuid.
- [**GET ALL** /api/comment](comment/comment.api-get-all.md) Get all comments.
- [**GET BY UUID** /api/comment/{uuid}](comment/comment.api-getByUuid.md) Get a comment by uuid.
- [**POST** /api/comment](comment/comment.api-post.md) Create a new comment.
- [**PUT** /api/comment/{uuid}](comment/comment.api-put.md) Update a comment.


## Member

- [**DELETE** /api/member/{uuid}](member/member.api-delete.md) Delete a member.
- [**GET ALL** /api/member](member/member.api-get-all.md) Get all members.
- [**GET BY ID TRELLO MEMBER** /api/member/idTrelloMember={idTrelloMember}](member/member.api-getByIdTrelloMember.md) Get a member by id trello.
- [**GET BY NAME** /api/member/name={name}](member/member.api-getByName.md) Get a member by name.
- [**GET BY ROLE** /api/member/role={role}](member/member.api-getByRole.md) Get a member by role.
- [**GET BY SURNAME** /api/member/surname={surname}](member/member.api-getBySurname.md) Get a member by surname.
- [**GET BY USERNAME** /api/member/username={username}](member/member.api-getByUsername.md) Get a member by username.
- [**POST** /api/member](member/member.api-post.md) Create a new member.
- [**PUT** /api/member/{uuid}](member/member.api-put.md) Update a member.


## Tablet

- [**GET** /api/tablet/priority/expiring/{uuidMember}](tablet/tablet.api-get-tasks-EXPIRING-priority-given-uuidMember.md) Get all tasks with expiring priority a member is assigned to.
- [**GET** /api/tablet/priority/high/{uuidMember}](tablet/tablet.api-get-tasks-HIGH-priority-given-uuidMember.md) Get all tasks with high priority a member is assigned to.
- [**GET** /api/tablet/priority/low/{uuidMember}](tablet/tablet.api-get-tasks-LOW-priority-given-uuidMember.md) Get all tasks with low priority a member is assigned to.
- [**GET** /api/tablet/priority/medium/{uuidMember}](tablet/tablet.api-get-tasks-MEDIUM-priority-given-uuidMember.md) Get all tasks with medium priority a member is assigned to.
- [**GET** /api/tablet/priority/{uuidMember}](tablet/tablet.api-getTasksByUuidMember.md) Get all tasks a member is assigned to.