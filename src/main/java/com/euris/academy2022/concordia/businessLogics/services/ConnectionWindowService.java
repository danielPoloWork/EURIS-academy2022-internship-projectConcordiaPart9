package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.DTOs.ConnectionWindowDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.ConnectionWindow;

public interface ConnectionWindowService {


    ResponseDto<ConnectionWindowDto> insert(ConnectionWindow connectionWindow);

    ResponseDto<ConnectionWindowDto> update(ConnectionWindow connectionWindow);

    ResponseDto<ConnectionWindowDto> getByMonth(int month);


}
