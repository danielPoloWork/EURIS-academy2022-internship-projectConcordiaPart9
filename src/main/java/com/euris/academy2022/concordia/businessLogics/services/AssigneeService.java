package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataModels.Assignee;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.AssigneeDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;

import java.util.List;

public interface AssigneeService {

    ResponseDto<AssigneeDto> insert(Assignee assignee);
    ResponseDto<AssigneeDto> removeByUuidMemberAndIdTask(String uuidMember, String idTask);
    ResponseDto<List<AssigneeDto>> getAll();
    ResponseDto<List<AssigneeDto>> getByUuidMember(String uuidMember);
    ResponseDto<List<AssigneeDto>> getByIdTask(String idTask);
}