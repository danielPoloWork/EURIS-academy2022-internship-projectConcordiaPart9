package com.euris.academy2022.concordia.businessLogics.services;

import com.euris.academy2022.concordia.dataPersistences.DTOs.ConfigurationDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.Configuration;

public interface ConfigurationService {

    ResponseDto<ConfigurationDto> insert(Configuration configuration);
    ResponseDto<ConfigurationDto> update(Configuration configuration);
    ResponseDto<ConfigurationDto> deleteByLabel(String label);
    ResponseDto<ConfigurationDto> getByLabel(String label);




}
