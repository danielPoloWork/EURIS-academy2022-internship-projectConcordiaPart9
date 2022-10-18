package com.euris.academy2022.concordia.businessLogics.services;


import com.euris.academy2022.concordia.businessLogics.services.impls.ConfigurationServiceImpl;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ConfigurationDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.Configuration;
import com.euris.academy2022.concordia.jpaRepositories.ConfigurationJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
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

        when(configurationJpaRepository.insert(
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<ConfigurationDto> response = configurationService.insert(configuration);

        verify(configurationJpaRepository, times(1))
                .insert(anyString(),
                        anyString(),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class));

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertEquals(expectedResponse.getBody().getLabel(), response.getBody().getLabel());
        assertEquals(expectedResponse.getBody().getValue(), response.getBody().getValue());
        assertEquals(expectedResponse.getBody().getDateCreation(), response.getBody().getDateCreation());
        assertEquals(expectedResponse.getBody().getDateUpdate(), response.getBody().getDateUpdate());
    }

    @Test
    void insertTest_NOT_CREATED() {

        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_CREATED);
        expectedResponse.setCode(HttpResponseType.NOT_CREATED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_CREATED.getDesc());

        when(configurationJpaRepository.insert(
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<ConfigurationDto> response = configurationService.insert(configuration);

        verify(configurationJpaRepository, times(1))
                .insert(anyString(),
                        anyString(),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class));

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }


    @Test
    void updateTest_UPDATED() {

        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.UPDATED);
        expectedResponse.setCode(HttpResponseType.UPDATED.getCode());
        expectedResponse.setDesc(HttpResponseType.UPDATED.getDesc());
        expectedResponse.setBody(configuration.toDto());

        when(configurationJpaRepository.findByLabel(anyString()))
                .thenReturn(Optional.of(configuration));

        when(configurationJpaRepository.update(
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<ConfigurationDto> response = configurationService.update(configuration);

        verify(configurationJpaRepository, times(1)).findByLabel(anyString());
        verify(configurationJpaRepository, times(1)).update(
                anyString(),
                anyString(),
                any(LocalDateTime.class));

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertEquals(expectedResponse.getBody().getLabel(), response.getBody().getLabel());
        assertEquals(expectedResponse.getBody().getValue(), response.getBody().getValue());
        assertEquals(expectedResponse.getBody().getDateCreation(), response.getBody().getDateCreation());
        assertEquals(expectedResponse.getBody().getDateUpdate(), response.getBody().getDateUpdate());

    }

    @Test
    void updateTest_FOUND_NOT_UPDATED() {
        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_UPDATED);
        expectedResponse.setCode(HttpResponseType.NOT_UPDATED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_UPDATED.getDesc());

        when(configurationJpaRepository.findByLabel(anyString()))
                .thenReturn(Optional.of(configuration));

        when(configurationJpaRepository.update(
                        anyString(),
                        anyString(),
                        any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<ConfigurationDto> response = configurationService.update(configuration);

        verify(configurationJpaRepository, times(1)).findByLabel(anyString());
        verify(configurationJpaRepository, times(1)).update(
                anyString(),
                anyString(),
                any(LocalDateTime.class));

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }


    @Test
    void updateTest_NOT_FOUND() {
        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        when(configurationJpaRepository.findByLabel(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<ConfigurationDto> response = configurationService.update(configuration);

        verify(configurationJpaRepository, times(1)).findByLabel(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }


    @Test
    void deleteByLabelTest_FOUND_DELETED() {

        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.DELETED);
        expectedResponse.setCode(HttpResponseType.DELETED.getCode());
        expectedResponse.setDesc(HttpResponseType.DELETED.getDesc());
        expectedResponse.setBody(configuration.toDto());

        when(configurationJpaRepository.findByLabel(anyString()))
                .thenReturn(Optional.of(configuration));

        when(configurationJpaRepository.removeByLabel(anyString()))
                .thenReturn(1);

        ResponseDto<ConfigurationDto> response = configurationService.deleteByLabel(configuration.getLabel());

        verify(configurationJpaRepository, times(1)).findByLabel(anyString());
        verify(configurationJpaRepository, times(1)).removeByLabel(anyString());


        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertEquals(expectedResponse.getBody().getLabel(), response.getBody().getLabel());
    }


    @Test
    void deleteByLabelTest_FOUND_NOT_DELETED() {
        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_DELETED);
        expectedResponse.setCode(HttpResponseType.NOT_DELETED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_DELETED.getDesc());

        when(configurationJpaRepository.findByLabel(anyString()))
                .thenReturn(Optional.of(configuration));

        when(configurationJpaRepository.removeByLabel(anyString()))
                .thenReturn(0);

        ResponseDto<ConfigurationDto> response = configurationService.deleteByLabel(configuration.getLabel());

        verify(configurationJpaRepository, times(1)).findByLabel(anyString());
        verify(configurationJpaRepository, times(1)).removeByLabel(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }


    @Test
    void deleteByLabelTest_NOT_FOUND() {
        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.DELETE);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        when(configurationJpaRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<ConfigurationDto> response = configurationService.deleteByLabel(configuration.getLabel());

        verify(configurationJpaRepository, times(1)).findByLabel(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(response.getBody());
    }


    @Test
    void getByLabelTest_FOUND() {
        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(configuration.toDto());

        when(configurationJpaRepository.findByLabel(anyString()))
                .thenReturn(Optional.of(configuration));

        ResponseDto<ConfigurationDto> response = configurationService.getByLabel(configuration.getLabel());

        verify(configurationJpaRepository, times(1)).findByLabel(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertEquals(expectedResponse.getBody().getLabel(), response.getBody().getLabel());
    }

    @Test
    void getByLabelTest_NOT_FOUND() {
        ResponseDto<ConfigurationDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        when(configurationJpaRepository.findByLabel(anyString()))
                .thenReturn(Optional.empty());

        ResponseDto<ConfigurationDto> response = configurationService.getByLabel(configuration.getLabel());

        verify(configurationJpaRepository, times(1)).findByLabel(anyString());

        assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        assertEquals(expectedResponse.getCode(), response.getCode());
        assertEquals(expectedResponse.getDesc(), response.getDesc());
        assertNull(expectedResponse.getBody());
    }

}
