package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.TabletService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.euris.academy2022.concordia.utils.constants.controller.TabletControllerConstant.*;

@RestController
@RequestMapping(TABLET_REQUEST_MAPPING)
public class TabletController {
    private final TabletService tabletService;

    public TabletController(TabletService tabletService) {
        this.tabletService = tabletService;
    }

    @GetMapping(GET_BY_UUID)
    public ResponseDto<List<TaskDto>> getMemberTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasks(uuidMember);
    }

    @GetMapping(GET_BY_HIGH_PRIORITY)
    public ResponseDto<List<TaskDto>> getHighPriorityTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByPriority(uuidMember, TaskPriority.HIGH);
    }

    @GetMapping(GET_BY_MEDIUM_PRIORITY)
    public ResponseDto<List<TaskDto>> getMediumPriorityTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByPriority(uuidMember, TaskPriority.MEDIUM);
    }

    @GetMapping(GET_BY_LOW_PRIORITY)
    public ResponseDto<List<TaskDto>> getLowPriorityTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByPriority(uuidMember, TaskPriority.LOW);
    }

    @GetMapping(GET_BY_EXPIRING_PRIORITY)
    public ResponseDto<List<TaskDto>> getExpiringPriorityTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByPriority(uuidMember, TaskPriority.EXPIRING);
    }

    @GetMapping(GET_BY_TO_DO_STATUS)
    public ResponseDto<List<TaskDto>> getToDoTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByStatus(uuidMember, TaskStatus.TO_DO);
    }

    @GetMapping(GET_BY_IN_PROGRESS_STATUS)
    public ResponseDto<List<TaskDto>> getInProgressTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByStatus(uuidMember, TaskStatus.IN_PROGRESS);
    }

    @GetMapping(GET_BY_COMPLETED_STATUS)
    public ResponseDto<List<TaskDto>> getCompletedTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByStatus(uuidMember, TaskStatus.COMPLETED);
    }
}
