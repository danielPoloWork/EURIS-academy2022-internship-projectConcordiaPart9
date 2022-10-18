var pathAssignee = "/api/assignee";

class AssigneeService {

	constructor() {}

	static getAll() {
    		return CrudService.getAll(pathAssignee);
    	}

	static getTaskListByStatus(status) {
		return CrudService.getByStatus(pathAssignee + "/status=", status);
	}

	static getTaskListByUuidMember(uuidMember) {
    	return CrudService.getByUuidMember(pathAssignee + "/uuidMember=", uuidMember);
    }

    static getAllTaskNotAssignedToThisUuidMember(uuidMember) {
        return CrudService.getByUuidMember(pathAssignee + "/excludeUuidMember=", uuidMember);
    }

    static insert(formData) {
        return CrudService.postByFormData(pathAssignee, formData);
    }

    static removeByUuidMemberAndIdTask(formData) {
        return CrudService.deleteByFormData(pathAssignee, formData);
    }
}