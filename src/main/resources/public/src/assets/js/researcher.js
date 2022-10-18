$(document).foundation();
$(document).ready(function () {
    $("#workspace-accordion-div").hide();
    $("#dashboard-progress-detail-callout").hide();
    $("#dashboard-progress-assignee-callout").hide();
    $("#workspace-progress-detail-callout").hide();
    $("#workspace-progress-add-comment-callout").hide();
    $("#workspace-progress-edit-comment-callout").hide();
    $("#workspace-progress-remove-assignee-callout").hide();
    $("#workspace-progress-lock-task-callout").hide();
    dashboardSync();
    $("#header-top-tab-left-p-fullname").text(getFullNameFromCookies());
//    TaskService.getTaskListByStatus("IN_PROGRESS").then(function(apiResponse) { populateInProgressTable(apiResponse); });
//    TaskService.getTaskListByStatus("COMPLETED").then(function(apiResponse) { populateCompletedTable(apiResponse); });
    // CSS -------------------------------------------------------------------------------------------------------------
    //// HIDING

    $("#header-top-tab-left-div-dashboard").hover(function () {
        /* mouse-in */
        $("#header-top-tab-left-li-dashboard")
            .css("border-bottom-width", "5px")
            .css("border-bottom-color", "#9E9E9E")
        $("#header-top-tab-left-span-dashboard")
            .css("font-variation-settings", "'FILL' 1")
            .css("color", "#1468A0")
        $("#header-top-tab-left-p-dashboard")
            .css("color", "#1468A0")
    }, function () {
             /* mouse-out */
             $("#header-top-tab-left-li-dashboard")
                  .css("border-bottom-width", "5px")
                  .css("border-bottom-color", "#FBBC04")
             $("#header-top-tab-left-span-dashboard")
                  .css("font-variation-settings", "'FILL' 0")
                  .css("color", "#1779BA")
             $("#header-top-tab-left-p-dashboard")
                  .css("color", "#1779BA")
        });

        $("#header-top-tab-left-div-workspace").hover(function () {
            /* mouse-in */
            $("#header-top-tab-left-li-workspace")
                  .css("border-bottom-width", "5px")
                  .css("border-bottom-color", "#9E9E9E")
            $("#header-top-tab-left-span-workspace")
                  .css("font-variation-settings", "'FILL' 1")
                  .css("color", "#1468A0")
            $("#header-top-tab-left-p-workspace")
                  .css("color", "#1468A0")
        }, function () {
             /* mouse-out */
             $("#header-top-tab-left-li-workspace")
                  .css("border-bottom-width", "px")
             $("#header-top-tab-left-span-workspace")
                  .css("font-variation-settings", "'FILL' 0")
                  .css("color", "#1779BA")
             $("#header-top-tab-left-p-workspace")
                  .css("color", "#1779BA")
        });

        $("#researcher-div-logout").hover(function () {
                    /* mouse-in */
                    $("#researcher-span-logout")
                          .css("font-variation-settings", "'FILL' 1")
                          .css("font-variation-settings", "'wght' 700")
                          .css("color", "#1468A0")
                    $("#researcher-p-logout")
                           .css("font-weight", "bold")
                          .css("color", "#1468A0")
                }, function () {
                     /* mouse-out */
                     $("#researcher-span-logout")
                          .css("font-variation-settings", "'FILL' 0")
                          .css("font-variation-settings", "'wght' 400")
                          .css("color", "#1779BA")
                     $("#researcher-p-logout")
                     .css("font-weight", "normal")
                          .css("color", "#1779BA")
                });

});
/**********************************************************************************************************************/
function getCookie(cname) {
  let name = cname + "=";
  let decodedCookie = decodeURIComponent(document.cookie);
  let ca = decodedCookie.split(';');
  for(let i = 0; i <ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

function getUuidFromCookies() {
    var uuid = getCookie("uuid");
    return uuid;
}
function getRoleFromCookies() {
    return getCookie("role");
}
function getFullNameFromCookies() {
    return getCookie("fullName");
}

function cleanDashboardRequestsTable() {
  $("#researcher-requests-table" + " tbody tr").remove();
}
function cleanDashboardProgressTable() {
  $("#researcher-progress-table" + " tbody tr").remove();
}
function cleanDashboardCompletedTable() {
  $("#researcher-completed-table" + " tbody tr").remove();
}

function returnSpanFromSwitchPriority(priority) {
    switch(priority) {
        case "HIGH":
            return "<span id=\"researcher-requests-span-priority-high\" class=\"badge-high\">H</span>";
            break;
        case "EXPIRING":
            return "<span id=\"researcher-requests-span-priority-expiring\" class=\"badge-expiring\">E</span>";
            break;
        case "MEDIUM":
            return "<span id=\"researcher-requests-span-priority-medium\" class=\"badge-medium\">M</span>";
             break;
        case "LOW":
            return "<span id=\"researcher-requests-span-priority-low\" class=\"badge-low\">L</span>";
            break;
        case "DONE":
            return "<span id=\"researcher-requests-span-priority-done\" class=\"badge-done\">D</span>";
            break;
    }
}

function getDynamicTaskRowHtml(idTrello, priority, title, deadline, dateUpdate) {
    var html =
        "<tr value=\""+idTrello+"\">"+
            "<td id=\"researcher-requests-td-priority\">"+returnSpanFromSwitchPriority(priority)+"</td>"+
            "<td id=\"researcher-requests-td-title\" onclick=\"taskDetails('"+idTrello+"');\">"+title+"</td>"+
            "<td id=\"researcher-requests-td-deadline\" class=\"deadline\">"+deadline+"</td>"+
            "<td id=\"researcher-requests-td-last-update\" class=\"last-update\">"+dateUpdate+"</td>"+
            "<td id=\"researcher-requests-td-assignee\">"+
                "<a id=\"researcher-requests-a-assignee\" class=\"assignee-researcher-requests-a\" onclick=\"assignMe('"+idTrello+"','"+title+"');\">"+
                    "<span id-hover=\"researcher-requests-span-assignee\" class=\"material-symbols-outlined\">person_add</span>"+
                "</a>"+
            "</td>"+
        "</tr>";
  return html;
}

function getCompletedDynamicTaskRowHtml(idTrello, priority, title, deadline, dateUpdate) {
    var html =
        "<tr value=\""+idTrello+"\">"+
            "<td id=\"researcher-completed-td-priority\">"+returnSpanFromSwitchPriority(priority)+"</td>"+
            "<td id=\"researcher-completed-td-title\" onclick=\"taskDetails('"+idTrello+"');\">"+title+"</td>"+
            "<td id=\"researcher-completed-td-deadline\" class=\"deadline\">"+deadline+"</td>"+
            "<td id=\"researcher-completed-td-last-update\" class=\"last-update\">"+dateUpdate+"</td>"+
        "</tr>";
  return html;
}

function rowsBuilder(apiResponse) {
    $.each(apiResponse.body, function(a, body) {
        switch(body.status) {
            case "TO_DO":
                var HtmlRowString =  getDynamicTaskRowHtml(body.id, body.priority, body.title, body.deadline, body.dateUpdate);
                $("#researcher-requests-table" + " tbody").append(HtmlRowString);
                break;
            case "IN_PROGRESS":
                var HtmlRowString =  getDynamicTaskRowHtml(body.id, body.priority, body.title, body.deadline, body.dateUpdate);
                $("#researcher-progress-table" + " tbody").append(HtmlRowString);
                break;
            case "COMPLETED":
                var HtmlRowString =  getCompletedDynamicTaskRowHtml(body.id, body.priority, body.title, body.deadline, body.dateUpdate);
                $("#researcher-completed-table" + " tbody").append(HtmlRowString);
                break;
        }
	});
}

function populateDashboardTable(apiResponse) {
    rowsBuilder(apiResponse);
}

function dashboardSync() {
 TaskService.getAll().then(function(apiResponse) {
            populateDashboardTable(apiResponse);
     });
}

function tabDashboard() {
    cleanDashboardRequestsTable();
    cleanDashboardProgressTable();
    cleanDashboardCompletedTable();
    closeAllAccordion();
    hideWorkspaceAccordion();
    hideWorkspaceBottomBorder();
    dashboardSync();
}

function taskDetails(idTrello) {
    $("#dashboard-progress-detail-table" + " tbody tr").remove();
    $("#dashboard-progress-detail-callout").show();
    TaskService.getTaskById(idTrello).then(function(apiResponse) {
        $("#dashboard-progress-detail-id").text("ID: "+apiResponse.body.id);
        $("#dashboard-progress-detail-priority").text("Priority: "+apiResponse.body.priority);
        $("#dashboard-progress-detail-status").text("Status: "+apiResponse.body.status);
        $("#dashboard-progress-detail-title").text("Title: "+apiResponse.body.title);
        if (apiResponse.body.deadline == null) {
            $("#dashboard-progress-detail-deadline").text("Deadline: undefined");
        } else {
            $("#dashboard-progress-detail-deadline").text("Deadline: "+apiResponse.body.deadline);
        }
        $("#dashboard-progress-detail-dateCreation").text("Date Creation: "+apiResponse.body.dateCreation);
        $("#dashboard-progress-detail-dateUpdate").text("Date Update: "+apiResponse.body.dateUpdate);
        if (apiResponse.body.description == null) {
            $("#dashboard-progress-detail-description").text("Description: undefined");
        } else {
            $("#dashboard-progress-detail-description").text("Description: "+apiResponse.body.description);
        }
        dashboardLoadComments(idTrello);
    });
}

function dashboardLoadComments(idTask) {
 CommentService.getAllByIdTask(idTask).then(function(apiResponse) {
              populateDashboardTaskCommentTable(apiResponse, idTask);
        });
}

function populateDashboardTaskCommentTable(apiResponse, idTask) {
    dashboardCommentRowsBuilder(apiResponse, idTask);
}

function dashboardCommentRowsBuilder(apiResponse, idTask) {
    $.each(apiResponse.body, function(a, body) {
        var HtmlRowString =  dashboardCommentGetDynamicTaskRowHtml(idTask, body.uuid, body.text, body.dateUpdate);
        $("#dashboard-progress-detail-table" + " tbody").append(HtmlRowString);
    });
}

function dashboardCommentGetDynamicTaskRowHtml(idTask, uuid, text, dateUpdate) {
var html =
        "<tr value=\""+uuid+"\">"+
            "<td id=\"dashboard-comment-td-text\">"+text+"</td>"+
            "<td id=\"dashboard-comment-td-last-update\" class=\"last-update\">"+dateUpdate+"</td>"+
        "</tr>";
  return html;
}


function taskDetailsClose() {
    $("#dashboard-progress-detail-callout").hide();
}

function assignMe(trelloListId, title) {
    $("#dashboard-progress-assignee-callout").show();
    $("#dashboard-progress-assignee-task-id").val(trelloListId);
    $("#dashboard-progress-assignee-task-title").val(title);
    taskDetailsClose();
}

function assignMeConfirmed() {
    var idTask = $("#dashboard-progress-assignee-task-id").val();

    var formData = JSON.stringify({
      	'uuidMember': getUuidFromCookies(),
      	'idTask': idTask
    });
    alert(formData);
    AssigneeService.insert(formData).then(function(assignee) {
        tabDashboard();
    });

    $("#dashboard-progress-assignee-callout").hide();
}

function assignMeCanceled() {
    $("#dashboard-progress-assignee-callout").hide();
}

function closeAllAccordion() {
    $("#dashboard-accordion-ul").css("data-allow-all-closed", "true");
    $("#workspace-accordion-ul").css("data-allow-all-closed", "true");
    $("#dashboard-requests-accordion-a").css("aria-expanded", "false");
    $("#dashboard-progress-accordion-a").css("aria-expanded", "false");
    $("#dashboard-completed-accordion-a").css("aria-expanded", "false");
}

function hideDashboardAccordion() {
    $("#dashboard-accordion-div").hide();
    $("#dashboard-accordion-div").css("display", "none");
    $("#workspace-accordion-div").show();
    $("#workspace-accordion-div").css("display", "block");
}

function hideWorkspaceAccordion() {
    $("#dashboard-accordion-div").show();
    $("#dashboard-accordion-div").css("display", "block");
    $("#workspace-accordion-div").hide();
    $("#workspace-accordion-div").css("display", "none");
}

function hideDashboardBottomBorder() {
    $("#header-top-tab-left-p-workspace").css("color", "#1468A0");
    $("#header-top-tab-left-span-workspace").css("font-variation-settings", "'FILL' 1");
    $("#header-top-tab-left-span-workspace").css("color", "#1468A0");
    $("#header-top-tab-left-li-workspace").css("border-bottom-style", "solid");
    $("#header-top-tab-left-li-workspace").css("border-bottom-color", "#FBBC04");
    $("#header-top-tab-left-li-workspace").css("border-bottom-width", "5px");

        $("#header-top-tab-left-p-dashboard").css("color", "#1779BA");
        $("#header-top-tab-left-span-dashboard").css("font-variation-settings", "'FILL' 0");
        $("#header-top-tab-left-span-dashboard").css("color", "#1779BA");
        $("#header-top-tab-left-li-dashboard").css("border-bottom-width", "0px");
}

function hideWorkspaceBottomBorder() {
        $("#header-top-tab-left-p-dashboard").css("color", "#1468A0");
        $("#header-top-tab-left-span-dashboard").css("font-variation-settings", "'FILL' 1");
        $("#header-top-tab-left-span-dashboard").css("color", "#1468A0");
        $("#header-top-tab-left-li-dashboard").css("border-bottom-style", "solid");
        $("#header-top-tab-left-li-dashboard").css("border-bottom-color", "#FBBC04");
        $("#header-top-tab-left-li-dashboard").css("border-bottom-width", "5px");

            $("#header-top-tab-left-p-workspace").css("color", "#1779BA");
            $("#header-top-tab-left-span-workspace").css("font-variation-settings", "'FILL' 0");
            $("#header-top-tab-left-span-workspace").css("color", "#1779BA");
            $("#header-top-tab-left-li-workspace").css("border-bottom-width", "0px");
}

function cleanWorkspaceProgressTable() {
  $("#workspace-researcher-progress-table" + " tbody tr").remove();
}

function cleanWorkspaceCompletedTable() {
  $("#workspace-researcher-completed-table" + " tbody tr").remove();
}

function workspaceReturnSpanFromSwitchPriority(priority) {
    switch(priority) {
        case "HIGH":
            return "<span class=\"badge-high\">H</span>";
            break;
        case "EXPIRING":
            return "<span class=\"badge-expiring\">E</span>";
            break;
        case "MEDIUM":
            return "<span class=\"badge-medium\">M</span>";
             break;
        case "LOW":
            return "<span class=\"badge-low\">L</span>";
            break;
        case "DONE":
            return "<span class=\"badge-done\">D</span>";
            break;
    }
}

function workspaceProgressCompletedGetDynamicTaskRowHtml(idTrello, priority, title, deadline, dateUpdate) {
    var html =
        "<tr value=\""+idTrello+"\">"+
            "<td id=\"workspace-researcher-progress-td-priority\">"+workspaceReturnSpanFromSwitchPriority(priority)+"</td>"+
            "<td id=\"workspace-researcher-progress-td-title\" onclick=\"workspaceTaskDetails('"+idTrello+"');\">"+title+"</td>"+
            "<td id=\"workspace-researcher-progress-td-deadline\" class=\"deadline\">"+deadline+"</td>"+
            "<td id=\"workspace-researcher-progress-td-last-update\" class=\"last-update\">"+dateUpdate+"</td>"+
            "<td id=\"workspace-researcher-progress-td-assignee\">"+
                "<a id=\"workspace-researcher-progress-a-assignee\" onclick=\"removeMe('"+idTrello+"','"+title+"');\">"+
                    "<span id-hover=\"workspace-researcher-progress-span-assignee\" class=\"material-symbols-outlined\">person_remove</span>"+
                "</a>"+
            "</td>"+
            "<td id=\"workspace-researcher-progress-td-comment\">"+
                "<a id=\"workspace-researcher-progress-a-comment\" onclick=\"addComment('"+idTrello+"','"+title+"');\">"+
                    "<span id-hover=\"workspace-researcher-progress-span-comment\" class=\"material-symbols-outlined\">add_comment</span>"+
                "</a>"+
            "</td>"+
            "<td id=\"workspace-researcher-progress-td-lock\">"+
                 "<a id=\"workspace-researcher-progress-a-lock\" onclick=\"lockTask('"+idTrello+"','"+title+"');\">"+
                     "<span id-hover=\"workspace-researcher-progress-span-lock\" class=\"material-symbols-outlined\">lock</span>"+
                 "</a>"+
            "</td>"+
        "</tr>";
  return html;
}

function workspaceCompletedGetDynamicTaskRowHtml(idTrello, priority, title, deadline, dateUpdate) {
    var html =
        "<tr value=\""+idTrello+"\">"+
            "<td id=\"workspace-researcher-completed-td-priority\">"+workspaceReturnSpanFromSwitchPriority(priority)+"</td>"+
            "<td id=\"workspace-researcher-completed-td-title\" onclick=\"workspaceTaskDetails('"+idTrello+"');\">"+title+"</td>"+
            "<td id=\"workspace-researcher-completed-td-deadline\" class=\"deadline\">"+deadline+"</td>"+
            "<td id=\"workspace-researcher-progress-td-last-update\" class=\"last-update\">"+dateUpdate+"</td>"+
        "</tr>";
  return html;
}

function workspaceRowsBuilder(apiResponse) {
    $.each(apiResponse.body, function(a, body) {
        switch(body.status) {
            case "IN_PROGRESS":
                var HtmlRowString =  workspaceProgressCompletedGetDynamicTaskRowHtml(body.id, body.priority, body.title, body.deadline, body.dateUpdate);
                $("#workspace-researcher-progress-table" + " tbody").append(HtmlRowString);
                break;
            case "COMPLETED":
                var HtmlRowString =  workspaceCompletedGetDynamicTaskRowHtml(body.id, body.priority, body.title, body.deadline, body.dateUpdate);
                $("#workspace-researcher-completed-table" + " tbody").append(HtmlRowString);
                break;
        }
	});
}

function populateWorkspaceTable(apiResponse) {
    workspaceRowsBuilder(apiResponse);
}

function workspaceSync() {
  TabletService.getTaskListByUuidMember(getUuidFromCookies()).then(function(apiResponse) {
                            populateWorkspaceTable(apiResponse);
  });
}

function tabWorkspace() {
    cleanWorkspaceProgressTable();
    cleanWorkspaceCompletedTable();
    closeAllAccordion();
    hideDashboardAccordion();
    hideDashboardBottomBorder();
    workspaceSync();
}

function removeMe(trelloListId, title) {
    $("#workspace-progress-remove-assignee-callout").show();
    $("#workspace-progress-remove-assignee-task-id").val(trelloListId);
    $("#workspace-progress-remove-assignee-task-title").val(title);
}

function removeMeConfirmed() {
    var idTask = $("#workspace-progress-remove-assignee-task-id").val();

    var formData = JSON.stringify({
      	'uuidMember': getUuidFromCookies(),
      	'idTask': idTask
    });
    AssigneeService.removeByUuidMemberAndIdTask(formData).then(function(assignee) {
        tabWorkspace();
    });

    $("#workspace-progress-remove-assignee-callout").hide();
}

function removeMeCanceled() {
    $("#workspace-progress-remove-assignee-callout").hide();
}

function workspaceTaskDetails(idTask) {
    $("#workspace-progress-detail-table" + " tbody tr").remove();
    $("#workspace-progress-detail-callout").show();
    TaskService.getTaskById(idTask).then(function(apiResponse) {
        $("#workspace-progress-detail-id").text("ID: "+apiResponse.body.id);
        $("#workspace-progress-detail-priority").text("Priority: "+apiResponse.body.priority);
        $("#workspace-progress-detail-status").text("Status: "+apiResponse.body.status);
        $("#workspace-progress-detail-title").text("Title: "+apiResponse.body.title);
        if (apiResponse.body.deadline == null) {
            $("#workspace-progress-detail-deadline").text("Deadline: undefined");
        } else {
            $("#workspace-progress-detail-deadline").text("Deadline: "+apiResponse.body.deadline);
        }
        $("#workspace-progress-detail-dateCreation").text("Date Creation: "+apiResponse.body.dateCreation);
        $("#workspace-progress-detail-dateUpdate").text("Date Update: "+apiResponse.body.dateUpdate);
        if (apiResponse.body.description == null) {
            $("#workspace-progress-detail-description").text("Description: undefined");
        } else {
            $("#workspace-progress-detail-description").text("Description: "+apiResponse.body.description);
        }
        loadComments(idTask);

    });
}

function loadComments(idTask) {
 CommentService.getAllByIdTask(idTask).then(function(apiResponse) {
              populateWorkspaceTaskCommentTable(apiResponse, idTask);
        });
}

function populateWorkspaceTaskCommentTable(apiResponse, idTask) {
    workspaceCommentRowsBuilder(apiResponse, idTask);
}

function workspaceCommentRowsBuilder(apiResponse, idTask) {
    $.each(apiResponse.body, function(a, body) {
        var HtmlRowString =  workspaceCommentGetDynamicTaskRowHtml(idTask, body.uuid, body.text, body.dateUpdate);
        $("#workspace-progress-detail-table" + " tbody").append(HtmlRowString);
    });
}

function workspaceCommentGetDynamicTaskRowHtml(idTask, uuidComment, text, dateUpdate) {
var html =
        "<tr value=\""+uuidComment+"\">"+
            "<td id=\"workspace-comment-td-text\">"+text+"</td>"+
            "<td id=\"workspace-comment-td-last-update\" class=\"last-update\">"+dateUpdate+"</td>"+
            "<td id=\"workspace-comment-td-edit\">"+
                "<a id=\"workspace-comment-a-edit\" onclick=\"editComment('"+idTask+"','"+uuidComment+"','"+text+"');\">"+
                    "<span id-hover=\"workspace-comment-span-edit\" class=\"material-symbols-outlined\">rate_review</span>"+
                "</a>"+
            "</td>"+
        "</tr>";
  return html;
}

function workspaceTaskDetailsClose() {
    $("#workspace-progress-detail-callout").hide();
}

function lockTask(idList, title) {
    $("#workspace-progress-detail-callout").hide();
    $("#workspace-progress-lock-task-callout").show();
    $("#workspace-progress-lock-task-id").val(idList);
    $("#workspace-progress-lock-task-title").val(title);

}

function lockTaskConfirmed() {
      var idTask = $("#workspace-progress-lock-task-id").val();

      TaskService.lockTaskById(idTask).then(function(task) {
                tabWorkspace();
      });
      $("#workspace-progress-lock-task-callout").hide();
}

function lockTaskCanceled() {
    $("#workspace-progress-lock-task-callout").hide();
}

function addComment(idList, title) {
    $("#workspace-progress-detail-callout").hide();
    $("#workspace-progress-add-comment-callout").show();
    $("#workspace-progress-add-comment-task-id").val(idList);
    $("#workspace-progress-add-comment-title").val(title);
}

function newCommentConfirmed() {
    var idTask = $("#workspace-progress-add-comment-task-id").val();
    var text   = $("#workspace-progress-add-comment-text").val();
    var formData = JSON.stringify({
         	'idTask': idTask,
         	'uuidMember': getUuidFromCookies(),
         	'text': text
    });

    CommentService.insert(formData).then(function() {
        tabWorkspace();
    });


    $("#workspace-progress-add-comment-callout").hide();
}

function newCommentCanceled() {
    $("#workspace-progress-add-comment-callout").hide();
}

function editComment(idTask, uuidComment, text) {
     $("#workspace-progress-detail-callout").hide();
     $("#workspace-progress-edit-comment-callout").show();
     $("#workspace-progress-edit-comment-uuid").val(uuidComment);
     $("#workspace-progress-edit-comment-text").val(text);
}

function editCommentConfirmed() {
    var uuidComment = $("#workspace-progress-edit-comment-uuid").val();
    var text = $("#workspace-progress-edit-comment-text").val();

    var formData = JSON.stringify({
         	'uuid': uuidComment,
         	'text': text
    });

    CommentService.updateComment(formData).then(function() {
        tabWorkspace();
    });


    $("#workspace-progress-edit-comment-callout").hide();
}

function editCommentCanceled() {
    $("#workspace-progress-edit-comment-callout").hide();
}