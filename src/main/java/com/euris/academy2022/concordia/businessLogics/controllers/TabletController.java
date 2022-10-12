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

@RestController
@RequestMapping("/api/tablet")
public class TabletController {
    private final TabletService tabletService;

    public TabletController(TabletService tabletService) {
        this.tabletService = tabletService;
    }

    @GetMapping("/{uuidMember}")
    public ResponseDto<List<TaskDto>> getMemberTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasks(uuidMember);
    }

    @GetMapping("/highPriority/{uuidMember}")
    public ResponseDto<List<TaskDto>> getHighPriorityTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByPriority(uuidMember, TaskPriority.HIGH);
    }

    @GetMapping("/mediumPriority/{uuidMember}")
    public ResponseDto<List<TaskDto>> getMediumPriorityTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByPriority(uuidMember, TaskPriority.MEDIUM);
    }

    @GetMapping("/lowPriority/{uuidMember}")
    public ResponseDto<List<TaskDto>> getLowPriorityTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByPriority(uuidMember, TaskPriority.LOW);
    }

    @GetMapping("/expiringPriority/{uuidMember}")
    public ResponseDto<List<TaskDto>> getExpiringTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByPriority(uuidMember, TaskPriority.EXPIRING);
    }

    @GetMapping("/toDoStatus/{uuidMember}")
    public ResponseDto<List<TaskDto>> getToDoTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByStatus(uuidMember, TaskStatus.TO_DO);
    }

    @GetMapping("/inProgressStatus/{uuidMember}")
    public ResponseDto<List<TaskDto>> getInProgressTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByStatus(uuidMember, TaskStatus.IN_PROGRESS);
    }

    @GetMapping("/completedStatus/{uuidMember}")
    public ResponseDto<List<TaskDto>> getCompletedTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByStatus(uuidMember, TaskStatus.COMPLETED);
    }
}
