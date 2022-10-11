package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.ConcordiaApplication;
import com.euris.academy2022.concordia.businessLogics.services.impls.TaskServiceImpl;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = ConcordiaApplication.class)
class TaskServiceTest {

    @Autowired
    private TaskJpaRepository taskJpaRepository;

    private TaskService taskService;

    private Task task;

    private List<TaskDto> taskDtoList;

    @BeforeEach
    public void init() {
        taskService = new TaskServiceImpl(taskJpaRepository);

        task = Task.builder()
                .id("idTask")
                .title("titleTask")
                .description("descriptionTask")
                .priority(TaskPriority.HIGH)
                .status(TaskStatus.TO_DO)
                .deadLine(LocalDateTime.now())
                .build();

        taskDtoList = new ArrayList<>();
        taskDtoList.add(task.toDto());
    }

    @Test
    void insert_CREATED() {

        ResponseDto<TaskDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.CREATED);
        expectedResponse.setCode(HttpResponseType.CREATED.getCode());
        expectedResponse.setDesc(HttpResponseType.CREATED.getDesc());
        expectedResponse.setBody(task.toDto());

        ResponseDto<TaskDto> response = taskService.insert(task);

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getId(), response.getBody().getId());

    }
}