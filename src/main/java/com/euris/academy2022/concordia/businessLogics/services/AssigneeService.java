package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.models.Assignee;
import com.euris.academy2022.concordia.dataPersistences.DTOs.AssigneeDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;

import java.util.List;

public interface AssigneeService {

    ResponseDto<AssigneeDto> insert(Assignee assignee);
    ResponseDto<AssigneeDto> removeByUuidMemberAndIdTask(String uuidMember, String idTask);
    ResponseDto<List<AssigneeDto>> getAll();
    ResponseDto<AssigneeDto> getByUuidMemberAndIdTask(String uuidMember, String idTask);
}