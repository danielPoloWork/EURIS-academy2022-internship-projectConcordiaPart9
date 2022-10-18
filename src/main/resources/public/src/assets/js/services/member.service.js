var pathMember = "/api/member";

class MemberService {

	constructor() {}

	static getAll() {
		return CrudService.getAll(pathMember);
	}
}