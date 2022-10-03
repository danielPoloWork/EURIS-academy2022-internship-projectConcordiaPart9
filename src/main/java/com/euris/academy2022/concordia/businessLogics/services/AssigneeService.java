package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.assignees.AssigneePostRequest;

public interface AssigneeService {


    ResponseDto<Integer> assignMemberToTask(AssigneePostRequest assigneePostRequest);
}
