package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.businessLogics.services.impls.TaskServiceImpl;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.models.Task;
import com.euris.academy2022.concordia.jpaRepositories.TaskJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import com.euris.academy2022.concordia.utils.enums.TaskPriority;
import com.euris.academy2022.concordia.utils.enums.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.test.properties")
class TaskServiceTest {

    @MockBean
    private TaskJpaRepository taskJpaRepository;
    private TaskService taskService;
    private Task task;
    private List<TaskDto> taskDtoList;
    private List<Task> taskList;

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
        taskList = taskDtoList.stream().map(TaskDto::toModel).collect(Collectors.toList());
    }

    @Test
    void insertTest_CREATED() {

        ResponseDto<TaskDto> expectedResponse = new ResponseDto<>();

        task.setDateCreation(LocalDateTime.now());
        task.setDateUpdate(LocalDateTime.now());
        task.setPriority(TaskPriority.DONE);
        task.setStatus(TaskStatus.COMPLETED);

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.CREATED);
        expectedResponse.setCode(HttpResponseType.CREATED.getCode());
        expectedResponse.setDesc(HttpResponseType.CREATED.getDesc());
        expectedResponse.setBody(task.toDto());

        when(taskJpaRepository.insert(
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<TaskDto> response = taskService.insert(task);

        verify(taskJpaRepository, times(1))
                .insert(Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class));

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertEquals(expectedResponse.getBody().getId(), response.getBody().getId());
        assertEquals(expectedResponse.getBody().getTitle(), response.getBody().getTitle());
        assertEquals(expectedResponse.getBody().getDescription(), response.getBody().getDescription());
        assertEquals(TaskPriority.LOW, response.getBody().getPriority());
        assertEquals(TaskStatus.TO_DO, response.getBody().getStatus());
        assertEquals(expectedResponse.getBody().getDateCreation(), response.getBody().getDateCreation());
        assertEquals(expectedResponse.getBody().getDateUpdate(), response.getBody().getDateUpdate());
    }

    @Test
    void insertTest_NOT_CREATED() {

        ResponseDto<TaskDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_CREATED);
        expectedResponse.setCode(HttpResponseType.NOT_CREATED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_CREATED.getDesc());

        when(taskJpaRepository.insert(
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<TaskDto> response = taskService.insert(task);

        verify(taskJpaRepository, times(1))
                .insert(anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class));

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());

    }

    @Test
    void updateTest_FOUND_UPDATED() {
        ResponseDto<TaskDto> expectedResponse = new ResponseDto<>();

        task.setPriority(TaskPriority.HIGH);
        task.setStatus(TaskStatus.COMPLETED);

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.UPDATED);
        expectedResponse.setCode(HttpResponseType.UPDATED.getCode());
        expectedResponse.setDesc(HttpResponseType.UPDATED.getDesc());
        expectedResponse.setBody(task.toDto());

        when(taskJpaRepository.findById(anyString()))
                .thenReturn(Optional.of(task));

        when(taskJpaRepository.update(
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class)
                ))
                .thenReturn(1);

        ResponseDto<TaskDto> response = taskService.update(task);

        verify(taskJpaRepository, times(1)).findById(anyString());
        verify(taskJpaRepository, times(1)).update(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(LocalDateTime.class),
                any(LocalDateTime.class));

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertEquals(expectedResponse.getBody().getId(), response.getBody().getId());
        assertEquals(expectedResponse.getBody().getTitle(), response.getBody().getTitle());
        assertEquals(expectedResponse.getBody().getDescription(), response.getBody().getDescription());
        assertEquals(TaskPriority.DONE, response.getBody().getPriority());
        assertEquals(TaskStatus.COMPLETED, response.getBody().getStatus());
        assertEquals(expectedResponse.getBody().getDateCreation(), response.getBody().getDateCreation());
        assertEquals(expectedResponse.getBody().getDateUpdate(), response.getBody().getDateUpdate());
    }

    @Test
    void updateTest_FOUND_NOT_UPDATED() {
        ResponseDto<TaskDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_UPDATED);
        expectedResponse.setCode(HttpResponseType.NOT_UPDATED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_UPDATED.getDesc());

        when(taskJpaRepository.findById(anyString()))
                .thenReturn(Optional.of(task));

        when(taskJpaRepository.update(
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class)
                ))
                .thenReturn(0);

        ResponseDto<TaskDto> response = taskService.update(task);

        verify(taskJpaRepository, times(1)).findById(anyString());
        verify(taskJpaRepository, times(1)).update(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(LocalDateTime.class),
                any(LocalDateTime.class));

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void updateTest_NOT_FOUND() {
        ResponseDto<TaskDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        when(taskJpaRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<TaskDto> response = taskService.update(task);

        verify(taskJpaRepository, times(1)).findById(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void deleteByIdTest_FOUND_DELETED() {
        ResponseDto<TaskDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.DELETED);
        expectedResponse.setCode(HttpResponseType.DELETED.getCode());
        expectedResponse.setDesc(HttpResponseType.DELETED.getDesc());
        expectedResponse.setBody(task.toDto());

        when(taskJpaRepository.findById(anyString()))
                .thenReturn(Optional.of(task));

        when(taskJpaRepository.removeById(anyString()))
                .thenReturn(1);

        ResponseDto<TaskDto> response = taskService.deleteById(task.getId());

        verify(taskJpaRepository, times(1)).findById(anyString());
        verify(taskJpaRepository, times(1)).removeById(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertEquals(expectedResponse.getBody().getId(), response.getBody().getId());
    }

    @Test
    void deleteByIdTest_FOUND_NOT_DELETED() {
        ResponseDto<TaskDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_DELETED);
        expectedResponse.setCode(HttpResponseType.NOT_DELETED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_DELETED.getDesc());

        when(taskJpaRepository.findById(anyString()))
                .thenReturn(Optional.of(task));

        when(taskJpaRepository.removeById(anyString()))
                .thenReturn(0);

        ResponseDto<TaskDto> response = taskService.deleteById(task.getId());

        verify(taskJpaRepository, times(1)).findById(anyString());
        verify(taskJpaRepository, times(1)).removeById(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void deleteByIdTest_NOT_FOUND() {
        ResponseDto<TaskDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        when(taskJpaRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<TaskDto> response = taskService.deleteById(task.getId());

        verify(taskJpaRepository, times(1)).findById(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByIdTest_FOUND() {
        ResponseDto<TaskDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(task.toDto());

        when(taskJpaRepository.findByIdTrelloTask(anyString()))
                .thenReturn(Optional.of(task));

        ResponseDto<TaskDto> response = taskService.getByIdTrelloTask(task.getId());

        verify(taskJpaRepository, times(1)).findByIdTrelloTask(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertEquals(expectedResponse.getBody().getId(), response.getBody().getId());
    }

    @Test
    void getByIdTest_NOT_FOUND() {
        ResponseDto<TaskDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        when(taskJpaRepository.findByIdTrelloTask(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<TaskDto> response = taskService.getByIdTrelloTask(task.getId());

        verify(taskJpaRepository, times(1)).findByIdTrelloTask(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(expectedResponse.getBody());
    }

    @Test
    void getAllTest_FOUND() {
        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(taskDtoList);

        when(taskJpaRepository.findAll())
                .thenReturn(taskList);

        ResponseDto<List<TaskDto>> response = taskService.getAll();

        verify(taskJpaRepository, times(1)).findAll();

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertEquals(expectedResponse.getBody().size(), response.getBody().size());
        assertEquals(expectedResponse.getBody().get(0).getId(), response.getBody().get(0).getId());

    }

    @Test
    void getAllTest_NOT_FOUND() {
        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        when(taskJpaRepository.findAll())
                .thenReturn(new ArrayList<>());

        ResponseDto<List<TaskDto>> response = taskService.getAll();

        verify(taskJpaRepository, times(1)).findAll();

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByPriorityTest_FOUND(){
        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(taskDtoList);

        when(taskJpaRepository.findByPriority(anyString()))
                .thenReturn(taskList);

        ResponseDto<List<TaskDto>> response = taskService.getByPriority(task.getPriority());

        verify(taskJpaRepository, times(1)).findByPriority(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertEquals(expectedResponse.getBody().size(), response.getBody().size());
        assertEquals(expectedResponse.getBody().get(0).getPriority(), response.getBody().get(0).getPriority());
    }

    @Test
    void getByPriorityTest_NOT_FOUND(){
        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        when(taskJpaRepository.findByPriority(anyString()))
                .thenReturn(new ArrayList<>());

        ResponseDto<List<TaskDto>> response = taskService.getByPriority(task.getPriority());

        verify(taskJpaRepository, times(1)).findByPriority(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByStatusTest_FOUND(){
        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(taskDtoList);

        when(taskJpaRepository.findByStatus(anyString()))
                .thenReturn(taskList);

        ResponseDto<List<TaskDto>> response = taskService.getByStatus(task.getStatus());

        verify(taskJpaRepository, times(1)).findByStatus(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertEquals(expectedResponse.getBody().size(), response.getBody().size());
        assertEquals(expectedResponse.getBody().get(0).getStatus(), response.getBody().get(0).getStatus());
    }

    @Test
    void getByStatusTest_NOT_FOUND(){
        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        when(taskJpaRepository.findByStatus(anyString()))
                .thenReturn(new ArrayList<>());

        ResponseDto<List<TaskDto>> response = taskService.getByStatus(task.getStatus());

        verify(taskJpaRepository, times(1)).findByStatus(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByTitleTest_FOUND(){
        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(taskDtoList);

        when(taskJpaRepository.findByTitle(anyString()))
                .thenReturn(taskList);

        ResponseDto<List<TaskDto>> response = taskService.getByTitle(task.getTitle());

        verify(taskJpaRepository, times(1)).findByTitle(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertEquals(expectedResponse.getBody().size(), response.getBody().size());
        assertEquals(expectedResponse.getBody().get(0).getTitle(), response.getBody().get(0).getTitle());
    }

    @Test
    void getByTitleTest_NOT_FOUND(){
        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        when(taskJpaRepository.findByTitle(anyString()))
                .thenReturn(new ArrayList<>());

        ResponseDto<List<TaskDto>> response = taskService.getByTitle(task.getTitle());

        verify(taskJpaRepository, times(1)).findByTitle(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void getByDeadLineTest_FOUND(){
        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(taskDtoList);

        when(taskJpaRepository.findByDeadLine(any(LocalDateTime.class)))
                .thenReturn(taskList);

        ResponseDto<List<TaskDto>> response = taskService.getByDeadLine(task.getDeadLine());

        verify(taskJpaRepository, times(1)).findByDeadLine(any(LocalDateTime.class));

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertEquals(expectedResponse.getBody().size(), response.getBody().size());
        assertEquals(expectedResponse.getBody().get(0).getDeadLine(), response.getBody().get(0).getDeadLine());
    }

    @Test
    void getByDeadLineTest_NOT_FOUND(){
        ResponseDto<List<TaskDto>> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        when(taskJpaRepository.findByDeadLine(any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());

        ResponseDto<List<TaskDto>> response = taskService.getByDeadLine(task.getDeadLine());

        verify(taskJpaRepository, times(1)).findByDeadLine(any(LocalDateTime.class));

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }

    @Test
    void findAllTasksByMemberUuidTest() {
        when(taskJpaRepository.findAllTasksByMemberUuid(anyString()))
                .thenReturn(taskList);

        taskService.findAllTasksByMemberUuid("uuid");

        verify(taskJpaRepository, times(1))
                .findAllTasksByMemberUuid(anyString());
    }

    @Test
    void updateExpiringTasksTest() {

        when(taskJpaRepository.findAll())
                .thenReturn(taskList);

        List<Task> updatedTasks = taskService.updateExpiringTasks();

        assertEquals(taskList.size(), updatedTasks.size());
        assertEquals(TaskPriority.EXPIRING, updatedTasks.get(0).getPriority());
    }

}