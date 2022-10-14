package com.euris.academy2022.concordia.businessLogics.services;


import com.euris.academy2022.concordia.ConcordiaApplication;
import com.euris.academy2022.concordia.businessLogics.services.impls.ConfigurationServiceImpl;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ConfigurationDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.TaskDto;
import com.euris.academy2022.concordia.dataPersistences.models.Configuration;
import com.euris.academy2022.concordia.jpaRepositories.ConfigurationJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import liquibase.pro.packaged.M;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConcordiaApplication.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class ConfigurationServiceTest {

    @MockBean
    private ConfigurationJpaRepository configurationJpaRepository;
    private ConfigurationService configurationService;
    private Configuration configuration;


    @BeforeEach
    public void init() {

        configurationService = new ConfigurationServiceImpl(configurationJpaRepository);

        configuration = Configuration.builder()
                .label("label")
                .value("value")
                .build();
    }

    @Test
    void insertTest_CREATED() {

        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        configuration.setDateCreation(LocalDateTime.now());
        configuration.setDateUpdate(LocalDateTime.now());

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.CREATED);
        expectedResponse.setCode(HttpResponseType.CREATED.getCode());
        expectedResponse.setDesc(HttpResponseType.CREATED.getDesc());
        expectedResponse.setBody(configuration.toDto());

        Mockito
                .when(configurationJpaRepository.insert(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<ConfigurationDto> response = configurationService.insert(configuration);

        Mockito.verify(configurationJpaRepository, Mockito.times(1))
                .insert(Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class));

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getLabel(), response.getBody().getLabel());
        Assertions.assertEquals(expectedResponse.getBody().getValue(), response.getBody().getValue());
        Assertions.assertEquals(expectedResponse.getBody().getDateCreation(), response.getBody().getDateCreation());
        Assertions.assertEquals(expectedResponse.getBody().getDateUpdate(), response.getBody().getDateUpdate());
    }

    @Test
    void insertTest_NOT_CREATED() {

        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_CREATED);
        expectedResponse.setCode(HttpResponseType.NOT_CREATED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_CREATED.getDesc());

        Mockito
                .when(configurationJpaRepository.insert(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<ConfigurationDto> response = configurationService.insert(configuration);

        Mockito.verify(configurationJpaRepository, Mockito.times(1))
                .insert(Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class));

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }


    @Test
    void updateTest_UPDATED() {

        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.UPDATED);
        expectedResponse.setCode(HttpResponseType.UPDATED.getCode());
        expectedResponse.setDesc(HttpResponseType.UPDATED.getDesc());
        expectedResponse.setBody(configuration.toDto());

        Mockito
                .when(configurationJpaRepository.findByLabel(Mockito.anyString()))
                .thenReturn(Optional.of(configuration));

        Mockito
                .when(configurationJpaRepository.update(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<ConfigurationDto> response = configurationService.update(configuration);

        Mockito.verify(configurationJpaRepository, Mockito.times(1)).findByLabel(Mockito.anyString());
        Mockito.verify(configurationJpaRepository, Mockito.times(1)).update(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(LocalDateTime.class));

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getLabel(), response.getBody().getLabel());
        Assertions.assertEquals(expectedResponse.getBody().getValue(), response.getBody().getValue());
        Assertions.assertEquals(expectedResponse.getBody().getDateCreation(), response.getBody().getDateCreation());
        Assertions.assertEquals(expectedResponse.getBody().getDateUpdate(), response.getBody().getDateUpdate());

    }

    @Test
    void updateTest_FOUND_NOT_UPDATED() {
        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_UPDATED);
        expectedResponse.setCode(HttpResponseType.NOT_UPDATED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_UPDATED.getDesc());

        Mockito
                .when(configurationJpaRepository.findByLabel(Mockito.anyString()))
                .thenReturn(Optional.of(configuration));

        Mockito
                .when(configurationJpaRepository.update(
                        Mockito.anyString(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<ConfigurationDto> response = configurationService.update(configuration);

        Mockito.verify(configurationJpaRepository, Mockito.times(1)).findByLabel(Mockito.anyString());
        Mockito.verify(configurationJpaRepository, Mockito.times(1)).update(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.any(LocalDateTime.class));

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }


    @Test
    void updateTest_NOT_FOUND() {
        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(configurationJpaRepository.findByLabel(Mockito.anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<ConfigurationDto> response = configurationService.update(configuration);

        Mockito.verify(configurationJpaRepository, Mockito.times(1)).findByLabel(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }


    @Test
    void deleteByLabelTest_FOUND_DELETED() {

        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.DELETED);
        expectedResponse.setCode(HttpResponseType.DELETED.getCode());
        expectedResponse.setDesc(HttpResponseType.DELETED.getDesc());
        expectedResponse.setBody(configuration.toDto());

        Mockito
                .when(configurationJpaRepository.findByLabel(Mockito.anyString()))
                .thenReturn(Optional.of(configuration));

        Mockito
                .when(configurationJpaRepository.removeByLabel(Mockito.anyString()))
                .thenReturn(1);

        ResponseDto<ConfigurationDto> response = configurationService.deleteByLabel(configuration.getLabel());

        Mockito.verify(configurationJpaRepository, Mockito.times(1)).findByLabel(Mockito.anyString());
        Mockito.verify(configurationJpaRepository, Mockito.times(1)).removeByLabel(Mockito.anyString());


        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getLabel(), response.getBody().getLabel());
    }



    @Test
    void deleteByLabelTest_FOUND_NOT_DELETED() {
        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_DELETED);
        expectedResponse.setCode(HttpResponseType.NOT_DELETED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_DELETED.getDesc());

        Mockito
                .when(configurationJpaRepository.findByLabel(Mockito.anyString()))
                .thenReturn(Optional.of(configuration));

        Mockito
                .when(configurationJpaRepository.removeByLabel(Mockito.anyString()))
                .thenReturn(0);

        ResponseDto<ConfigurationDto> response = configurationService.deleteByLabel(configuration.getLabel());

        Mockito.verify(configurationJpaRepository, Mockito.times(1)).findByLabel(Mockito.anyString());
        Mockito.verify(configurationJpaRepository, Mockito.times(1)).removeByLabel(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }


    @Test
    void deleteByLabelTest_NOT_FOUND() {
        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(configurationJpaRepository.findById(Mockito.anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<ConfigurationDto> response = configurationService.deleteByLabel(configuration.getLabel());

        Mockito.verify(configurationJpaRepository, Mockito.times(1)).findByLabel(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }


    @Test
    void getByLabelTest_FOUND() {
        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(configuration.toDto());

        Mockito
                .when(configurationJpaRepository.findByLabel(Mockito.anyString()))
                .thenReturn(Optional.of(configuration));

        ResponseDto<ConfigurationDto> response = configurationService.getByLabel(configuration.getLabel());

        Mockito.verify(configurationJpaRepository, Mockito.times(1)).findByLabel(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getLabel(), response.getBody().getLabel());
    }

    @Test
    void getByIdTest_NOT_FOUND() {
        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(configurationJpaRepository.findByLabel(Mockito.anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<ConfigurationDto> response = configurationService.getByLabel(configuration.getLabel());

        Mockito.verify(configurationJpaRepository, Mockito.times(1)).findByLabel(Mockito.anyString());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(expectedResponse.getBody());
    }

}