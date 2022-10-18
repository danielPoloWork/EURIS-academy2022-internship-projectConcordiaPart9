var pathTask = "/api/task";

class TaskService {

	constructor() {}

	static lockTaskById(id) {
            return CrudService.putById(pathTask + "/", id);
        }

	static getAll() {
    		return CrudService.getAll(pathTask);
    	}

    static getTaskById(id) {
        return CrudService.getById(pathTask + "/", id);
    }

	static getTaskListByStatus(status) {
		return CrudService.getByStatus(pathTask + "/status=", status);
	}

	static getTaskListByUuidMember(uuidMember) {
    	return CrudService.getByUuidMember(pathTask + "/uuidMember=", uuidMember);
    }
}