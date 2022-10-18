var pathTablet = "/api/tablet";

class TabletService {

	constructor() {}

	static getTaskListByUuidMember(uuidMember) {
    	return CrudService.getByUuidMember(pathTablet + "/" + uuidMember);
    }
}