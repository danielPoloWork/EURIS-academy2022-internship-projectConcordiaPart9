package com.euris.academy2022.concordia.dataPersistences.DTOs;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmailDto {

    String senderUsername;
    String senderPassword;
    String text;
    String subject;
    String senderAddress;
    String receiverAddress;

}
