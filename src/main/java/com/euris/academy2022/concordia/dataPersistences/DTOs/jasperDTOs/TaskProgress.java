package com.euris.academy2022.concordia.dataPersistences.DTOs.jasperDTOs;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskProgress {
    private Long todo;
    private Long inprogress;
    private Long completed;
}
