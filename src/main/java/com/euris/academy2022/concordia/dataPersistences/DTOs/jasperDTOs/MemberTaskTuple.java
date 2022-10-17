package com.euris.academy2022.concordia.dataPersistences.DTOs.jasperDTOs;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class MemberTaskTuple implements Comparable<MemberTaskTuple>{

    private String username;
    private Float taskNumber;

    @Override
    public int compareTo(MemberTaskTuple m) {
        return m.getTaskNumber().compareTo(this.taskNumber);
    }
}
