class CrudService {

  constructor() { }

    static postByFormData(path, formData) {
      return DefaultAjax.callApiByBody("POST", path, formData);
    }

    static putByFormData(path, formData) {
      return DefaultAjax.callApiByBody("PUT", path, formData);
    }

    static putById(path, id) {
          return DefaultAjax.callApiByUrl("PUT", path, id);
        }

    static deleteByFormData(path, formData) {
          return DefaultAjax.callApiByBody("DELETE", path, formData);
        }

    static deleteByUuid(path, uuid) {
      return DefaultAjax.callApiByUrl("DELETE", path, uuid);
    }

  static getAll(path) {
    return DefaultAjax.callApiByUrl("GET", path, null);
  }

  static getById(path, id) {
      return DefaultAjax.callApiByUrl("GET", path, id);
    }

  static getByUuid(path, uuid) {
    return DefaultAjax.callApiByUrl("GET", path, uuid);
  }

  static getByEmail(path, email) {
    return DefaultAjax.callApiByUrl("GET", path, email);
  }

  static getByStatus(path, status) {
      return DefaultAjax.callApiByUrl("GET", path, status);
  }

  static getByUuidMember(path, uuidMember) {
        return DefaultAjax.callApiByUrl("GET", path, uuidMember);
    }

  static getUnlikeUuidMember(path, uuidMember) {
        return DefaultAjax.callApiByUrl("GET", path, uuidMember);
  }
}