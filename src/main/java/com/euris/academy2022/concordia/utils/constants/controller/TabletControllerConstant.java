package com.euris.academy2022.concordia.utils.constants.controller;

public interface TabletControllerConstant {

    String TABLET_REQUEST_MAPPING = "/api/tablet";
    String GET_BY_UUID = "/{uuidMember}";
    String GET_BY_HIGH_PRIORITY = "/highPriority/{uuidMember}";
    String GET_BY_MEDIUM_PRIORITY = "/mediumPriority/{uuidMember}";
    String GET_BY_LOW_PRIORITY = "/lowPriority/{uuidMember}";
    String GET_BY_EXPIRING_PRIORITY = "/expiringPriority/{uuidMember}";
    String GET_BY_TO_DO_STATUS = "/toDoStatus/{uuidMember}";
    String GET_BY_IN_PROGRESS_STATUS = "/inProgressStatus/{uuidMember}";
    String GET_BY_COMPLETED_STATUS = "/completedStatus/{uuidMember}";

}
