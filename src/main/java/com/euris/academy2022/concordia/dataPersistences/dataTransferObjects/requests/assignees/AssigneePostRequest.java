package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects.requests.assignees;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AssigneePostRequest {

    private String uuidMember;
    private String idTask;
}
