package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.AssigneeService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.AssigneeDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.assignees.AssigneePostRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignee")
public class AssigneeController {

    private final AssigneeService assigneeService;

    public AssigneeController(AssigneeService assigneeService) {
        this.assigneeService = assigneeService;
    }

    @PostMapping
    public ResponseDto<AssigneeDto> insert(@RequestBody AssigneePostRequest assigneeDto) {
        return assigneeService.insert(assigneeDto.toModel());
    }

    @DeleteMapping("/uuidMember={uuidMember}&idTask={idTask}")
    public ResponseDto<AssigneeDto> remove(@PathVariable String uuidMember, @PathVariable String idTask) {
        return assigneeService.removeByUuidMemberAndIdTask(uuidMember, idTask);
    }
}