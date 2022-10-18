package com.euris.academy2022.concordia.utils.constants.controller;

public interface TaskControllerConstant {

    String TASK_REQUEST_MAPPING = "/api/task";
    String DELETE_BY_ID = "/{id}";
    String GET_BY_ID = "/{id}";
    String GET_BY_TITLE = "/title={title}";
    String GET_BY_PRIORITY = "/priority={priority}";
    String GET_BY_STATUS = "/status={status}";
    String GET_BY_DEADLINE = "/deadLine={deadLine}";
}
