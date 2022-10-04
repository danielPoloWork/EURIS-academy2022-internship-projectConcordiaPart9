package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.AssigneeService;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.AssigneeDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.assignees.AssigneePostRequest;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.members.MemberPostRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignee")
public class AssigneeController {

    private final AssigneeService assigneeService;

    public AssigneeController(AssigneeService assigneeService) {
        this.assigneeService = assigneeService;
    }

//    @PostMapping
//    public ResponseDto<Integer> assignMemberToTask(@RequestBody AssigneePostRequest assigneePostRequest) {
//       return assigneeService.assignMemberToTask(assigneePostRequest);
//    }

    @PostMapping
    public ResponseDto<AssigneeDto> insert(@RequestBody AssigneePostRequest assigneeDto) {
        return assigneeService.insert(assigneeDto.toModel());
    }
}