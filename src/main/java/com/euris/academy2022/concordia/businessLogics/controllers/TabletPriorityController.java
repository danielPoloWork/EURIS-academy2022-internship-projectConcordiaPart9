package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.TabletService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tablet/priority")
public class TabletPriorityController {
    private final TabletService tabletService;

    public TabletPriorityController(TabletService tabletService) {
        this.tabletService = tabletService;
    }

    @GetMapping("/{uuidMember}")
    public ResponseDto<List<TaskDto>> getMemberTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasks(uuidMember);
    }

    @GetMapping("/high/{uuidMember}")
    public ResponseDto<List<TaskDto>> getHighPriorityTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByPriority(uuidMember, TaskPriority.HIGH);
    }

    @GetMapping("/medium/{uuidMember}")
    public ResponseDto<List<TaskDto>> getMediumPriorityTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByPriority(uuidMember, TaskPriority.MEDIUM);
    }

    @GetMapping("/low/{uuidMember}")
    public ResponseDto<List<TaskDto>> getLowPriorityTasks(@PathVariable String uuidMember) {
        return tabletService.getMemberTasksByPriority(uuidMember, TaskPriority.LOW);
    }

    @GetMapping("/expiring/{uuidMember}")
    public ResponseDto<List<TaskDto>> getExpiringTasks(@PathVariable String uuidMember) {
        return tabletService.getExpiringTasks(uuidMember);
    }
}
