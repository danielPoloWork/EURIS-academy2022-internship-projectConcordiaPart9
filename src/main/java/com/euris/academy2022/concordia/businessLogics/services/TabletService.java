package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;

import java.util.List;

public interface TabletService {

    ResponseDto<List<TaskDto>> getMemberTasksByPriority(String uuidMember, TaskPriority priority);

    ResponseDto<List<TaskDto>> getMemberTasks(String uuidMember);

    ResponseDto<List<TaskDto>> getExpiringTasks(String uuidMember);
}
