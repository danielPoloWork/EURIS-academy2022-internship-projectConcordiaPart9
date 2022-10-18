$(document).foundation();
$(document).ready(function () {
    MemberService.getAll().then(function(apiResponse) { populateSelect(apiResponse); });
    // CSS -------------------------------------------------------------------------------------------------------------
    //// HIDING
    $("#index-admin").hide();
    $("#index-researcher").hide();

    //// ADMIN LOGIN BUTTON
//    $("#body-index-html-div-login-admin").hover(function () {
//        /* mouse-in */
//        $(this)
//            .css("background-color", "#E6E6E6")
//        $("#body-index-html-span-login-admin")
//            .text("pending")
//            .css("font-variation-settings", "'FILL' 1")
//            .css("color", "#1468A0")
//        $("#body-index-html-p-login-admin")
//            .css("color", "#1468A0")
//    }, function () {
//        /* mouse-out */
//        $(this)
//            .css("background-color", "rgba(255, 99, 71, 0)")
//        $("#body-index-html-span-login-admin")
//            .text("login")
//            .css("font-variation-settings", "'FILL' 0")
//            .css("color", "#E6E6E6")
//        $("#body-index-html-p-login-admin")
//            .css("color", "#E6E6E6")
//    });

        //// LOGIN BUTTON
        $("#body-index-html-div-login-admin").hover(function () {
            /* mouse-in */
            $(this)
                .css("background-color", "#9E9E9E")
            $("#body-index-html-p-login-admin")
                .css("color", "#FFFFFF")
        }, function () {
            /* mouse-out */
            $(this)
                .css("background-color", "#FBBC04")
            $("#body-index-html-p-login-admin")
                .css("color", "#1468A0")
        });

        $("#body-index-html-div-login-researcher").hover(function () {
            /* mouse-in */
            $(this)
                .css("background-color", "#9E9E9E")
            $("#body-index-html-p-login-researcher")
                .css("color", "#FFFFFF")
        }, function () {
            /* mouse-out */
            $(this)
                .css("background-color", "#FBBC04")
            $("#body-index-html-p-login-researcher")
                .css("color", "#1468A0")
        });

    $("#body-index-html-select-login").change(function () {
        document.cookie = "uuid=; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
        document.cookie = "role=; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
        document.cookie = "fullName=; expires=Thu, 01 Jan 1970 00:00:00 UTC;";

        var selectedUuid = $("#body-index-html-select-login option:selected").val();
        var selectedRole = $("#body-index-html-select-login option:selected").attr("memberRole");
        var selectedFullName = $("#body-index-html-select-login option:selected").text();


            if (selectedRole == "DEFAULT") {
               hideAdminAndMemberLoginButton();
            }
            if (selectedRole == "ADMIN") {
                hideMemberLoginButton();
            }
            if ((selectedRole != "DEFAULT") && (selectedRole != "ADMIN")){
                hideAdminLoginButton();
            }

            document.cookie = "uuid="+selectedUuid+";";
            document.cookie = "role="+selectedRole+";";
            document.cookie = "fullName="+selectedFullName+";";
    });
});

/**********************************************************************************************************************/
function populateSelect(apiResponse) {
    var i = 0;
    var optionList = "<option id=\"body-index-html-option-login-"+i+"\" class=\"index-html-login-option\" memberRole=\"DEFAULT\" value=\"select-member\">Select member</option>";
    $.each(apiResponse.body, function(a, body) {
        if (body.role == "WRS" || body.role == "CONCORDIA" || body.role == "MANAGER") {
            console.log("External role found"+body.firstName+" "+body.lastName);
        } else {
           optionList += "<option id=\"body-index-html-option-login"+i+"\" class=\"index-html-login-option\" memberRole=\""+body.role+"\" value=\""+body.uuid+"\">"+body.firstName+" "+body.lastName+"</option>";
            i++;
        }
    });
    $("#body-index-html-select-login").append(optionList);
}

function hideAdminAndMemberLoginButton() {
    $("#index-admin").hide();
    $("#index-admin").css("display","none");
    $("#index-researcher").hide();
    $("#index-researcher").css("display","none");
}

function hideAdminLoginButton() {
    $("#index-admin").hide();
    $("#index-admin").css("display","none");
    $("#index-researcher").show();
    $("#index-researcher").css("display","block");
}

function hideMemberLoginButton() {
    $("#index-admin").show();
    $("#index-admin").css("display","block");
    $("#index-researcher").hide();
    $("#index-researcher").css("display","none");
}

function setAllCookies(uuid, role, fullName) {
                CookieService.setCookie("uuid", uuid);
                CookieService.setCookie("role", role);
                CookieService.setCookie("fullName", fullName);
}