var pathComment = "/api/comment";

class CommentService {

	constructor() {}

    static getCommentByUuid(uuid) {
        return CrudService.getByUuid(pathComment + "/", uuid);
    }

    static insert(formData) {
        return CrudService.postByFormData(pathComment, formData);
    }

    static updateComment(formData) {
        return CrudService.putByFormData(pathComment, formData);
    }

	static removeCommentsByUuid(uuid) {
		return CrudService.deleteByUuid(pathComment + "/", uuid);
	}

	static getAllByIdTask(idTask) {
	    return CrudService.getById(pathComment + "/idTask=", idTask)
	}
}