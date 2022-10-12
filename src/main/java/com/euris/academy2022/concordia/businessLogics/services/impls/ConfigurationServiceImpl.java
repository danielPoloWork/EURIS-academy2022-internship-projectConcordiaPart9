package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.ConfigurationService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ConfigurationDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.Configuration;
import com.euris.academy2022.concordia.jpaRepositories.ConfigurationJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import liquibase.pro.packaged.H;

import java.time.LocalDateTime;
import java.util.Optional;

public class ConfigurationServiceImpl implements ConfigurationService {

    private final ConfigurationJpaRepository configurationJpaRepository;

    public ConfigurationServiceImpl(ConfigurationJpaRepository configurationJpaRepository) {
        this.configurationJpaRepository = configurationJpaRepository;
    }

    @Override
    public ResponseDto<ConfigurationDto> insert(Configuration configuration) {
        ResponseDto<ConfigurationDto> response = new ResponseDto<>();

        Integer configurationCreated = configurationJpaRepository.insert(
                configuration.getLabel(),
                configuration.getValue(),
                LocalDateTime.now(),
                LocalDateTime.now());

        response.setHttpRequest(HttpRequestType.POST);

        if (configurationCreated != 1) {
            response.setHttpResponse(HttpResponseType.NOT_CREATED);
            response.setCode(HttpResponseType.NOT_CREATED.getCode());
            response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.CREATED);
            response.setCode(HttpResponseType.CREATED.getCode());
            response.setDesc(HttpResponseType.CREATED.getDesc());
            response.setBody(configuration.toDto());
        }

        return response;
    }

    @Override
    public ResponseDto<ConfigurationDto> update(Configuration configuration) {
        ResponseDto<ConfigurationDto> response = new ResponseDto<>();
        Optional<Configuration> optionalConfiguration = configurationJpaRepository.findByLabel(configuration.getLabel());

        response.setHttpRequest(HttpRequestType.PUT);

        if (optionalConfiguration.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            Integer updatedConfiguration = configurationJpaRepository.update(
                    configuration.getLabel(),
                    configuration.getValue(),
                    LocalDateTime.now());

            if (updatedConfiguration != 1) {
                response.setHttpResponse(HttpResponseType.NOT_UPDATED);
                response.setCode(HttpResponseType.NOT_UPDATED.getCode());
                response.setDesc(HttpResponseType.NOT_UPDATED.getDesc());
            } else {
                response.setHttpResponse(HttpResponseType.UPDATED);
                response.setCode(HttpResponseType.UPDATED.getCode());
                response.setDesc(HttpResponseType.UPDATED.getDesc());
                response.setBody(configuration.toDto());
            }
        }
        return response;
    }

    @Override
    public ResponseDto<ConfigurationDto> deleteByLabel(String label) {
        ResponseDto<ConfigurationDto> response = new ResponseDto<>();
        Optional<Configuration> optionalConfiguration = configurationJpaRepository.findByLabel(label);

        response.setHttpRequest(HttpRequestType.DELETE);

        if (optionalConfiguration.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            Integer deletedConfiguration = configurationJpaRepository.removeByLabel(label);

            if (deletedConfiguration != 1) {
                response.setHttpResponse(HttpResponseType.NOT_DELETED);
                response.setCode(HttpResponseType.NOT_DELETED.getCode());
                response.setDesc(HttpResponseType.NOT_DELETED.getDesc());
            } else {
                response.setHttpResponse(HttpResponseType.DELETED);
                response.setCode(HttpResponseType.DELETED.getCode());
                response.setDesc(HttpResponseType.DELETED.getDesc());
                response.setBody(optionalConfiguration.get().toDto());
            }
        }
        return response;
    }

    @Override
    public ResponseDto<ConfigurationDto> getByLabel(String label) {
        ResponseDto<ConfigurationDto> response = new ResponseDto<>();
        Optional<Configuration> optionalConfiguration = configurationJpaRepository.findByLabel(label);

        response.setHttpRequest(HttpRequestType.GET);

        if (optionalConfiguration.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(optionalConfiguration.get().toDto());
        }

        return response;
    }
}
