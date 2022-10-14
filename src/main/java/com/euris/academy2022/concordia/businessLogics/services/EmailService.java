package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.DTOs.EmailDto;

public interface EmailService {
    public void send(EmailDto email);
}
