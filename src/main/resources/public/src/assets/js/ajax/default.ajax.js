const HOST = "http://localhost";
const PORT = "8080";
const URL = HOST + ":" + PORT;

class DefaultAjax {

  constructor() {}

  static callApiByUrl(verb, path, arg) {

    const parameter = (arg ? arg : "");

    return $.ajax({
      type: verb,
      url: URL + path + parameter,
      crossDomain: true,
      crossOrigin: true,
      contentType: "application/json",
      error: function (xhr, status, errorThrown) {
        alert(status, errorThrown);
        console.log("xhr: " + JSON.stringify(xhr.responseJSON));
        console.log("status: " + status);
        console.log("errorThrown: " + errorThrown);
      }
    });
  }

  static callApiByBody(verb, path, formData) {

    return $.ajax({
      type: verb,
      url: URL + path,
      crossDomain: true,
      crossOrigin: true,
      data: formData,
      contentType: "application/json",
      error: function (xhr, status, errorThrown) {
        alert(status, errorThrown);
        console.log("xhr: " + JSON.stringify(xhr.responseJSON));
        console.log("status: " + status);
        console.log("errorThrown: " + errorThrown);
      }
    });
  }
}