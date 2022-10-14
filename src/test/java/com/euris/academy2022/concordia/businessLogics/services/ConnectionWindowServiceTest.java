package com.euris.academy2022.concordia.businessLogics.services;


import com.euris.academy2022.concordia.businessLogics.services.impls.ConnectionWindowServiceImpl;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ConfigurationDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ConnectionWindowDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.ConnectionWindow;
import com.euris.academy2022.concordia.jpaRepositories.ConnectionWindowJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.test.properties")
public class ConnectionWindowServiceTest {

    @MockBean
    private ConnectionWindowJpaRepository connectionWindowJpaRepository;
    private ConnectionWindowService connectionWindowService;
    private ConnectionWindow connectionWindow;


    @BeforeEach
    public void init() {

        connectionWindowService = new ConnectionWindowServiceImpl(connectionWindowJpaRepository);

        connectionWindow = ConnectionWindow.builder()
                .month(1)
                .cron("cron")
                .build();
    }

    @Test
    void insertTest_CREATED() {

        ResponseDto<ConnectionWindowDto> expectedResponse = new ResponseDto<>();

        connectionWindow.setDateCreation(LocalDateTime.now());
        connectionWindow.setDateUpdate(LocalDateTime.now());

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.CREATED);
        expectedResponse.setCode(HttpResponseType.CREATED.getCode());
        expectedResponse.setDesc(HttpResponseType.CREATED.getDesc());
        expectedResponse.setBody(connectionWindow.toDto());

        Mockito
                .when(connectionWindowJpaRepository.insert(
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<ConnectionWindowDto> response = connectionWindowService.insert(connectionWindow);

        Mockito.verify(connectionWindowJpaRepository, Mockito.times(1))
                .insert(Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class));

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getMonth(), response.getBody().getMonth());
        Assertions.assertEquals(expectedResponse.getBody().getCron(), response.getBody().getCron());
        Assertions.assertEquals(expectedResponse.getBody().getDateCreation(), response.getBody().getDateCreation());
        Assertions.assertEquals(expectedResponse.getBody().getDateUpdate(), response.getBody().getDateUpdate());
    }


    @Test
    void insertTest_BAD_REQUEST() {

        ResponseDto<ConnectionWindowDto> expectedResponse = new ResponseDto<>();

        connectionWindow.setMonth(15);

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.BAD_REQUEST);
        expectedResponse.setCode(HttpResponseType.BAD_REQUEST.getCode());
        expectedResponse.setDesc(HttpResponseType.BAD_REQUEST.getDesc());

        ResponseDto<ConnectionWindowDto> response = connectionWindowService.insert(connectionWindow);

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }


    @Test
    void insertTest_NOT_CREATED() {

        ResponseDto<ConnectionWindowDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.POST);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_CREATED);
        expectedResponse.setCode(HttpResponseType.NOT_CREATED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_CREATED.getDesc());

        Mockito
                .when(connectionWindowJpaRepository.insert(
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<ConnectionWindowDto> response = connectionWindowService.insert(connectionWindow);

        Mockito.verify(connectionWindowJpaRepository, Mockito.times(1))
                .insert(Mockito.anyInt(),
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

        ResponseDto<ConnectionWindowDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.UPDATED);
        expectedResponse.setCode(HttpResponseType.UPDATED.getCode());
        expectedResponse.setDesc(HttpResponseType.UPDATED.getDesc());
        expectedResponse.setBody(connectionWindow.toDto());

        Mockito
                .when(connectionWindowJpaRepository.findByMonth(Mockito.anyInt()))
                .thenReturn(Optional.of(connectionWindow));

        Mockito
                .when(connectionWindowJpaRepository.update(
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(1);

        ResponseDto<ConnectionWindowDto> response = connectionWindowService.update(connectionWindow);

        Mockito.verify(connectionWindowJpaRepository, Mockito.times(1)).findByMonth(Mockito.anyInt());
        Mockito.verify(connectionWindowJpaRepository, Mockito.times(1)).update(
                Mockito.anyInt(),
                Mockito.anyString(),
                Mockito.any(LocalDateTime.class));

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getMonth(), response.getBody().getMonth());
        Assertions.assertEquals(expectedResponse.getBody().getCron(), response.getBody().getCron());
        Assertions.assertEquals(expectedResponse.getBody().getDateCreation(), response.getBody().getDateCreation());
        Assertions.assertEquals(expectedResponse.getBody().getDateUpdate(), response.getBody().getDateUpdate());
    }


    @Test
    void updateTest_BAD_REQUEST() {

        ResponseDto<ConnectionWindowDto> expectedResponse = new ResponseDto<>();

        connectionWindow.setMonth(50);

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.BAD_REQUEST);
        expectedResponse.setCode(HttpResponseType.BAD_REQUEST.getCode());
        expectedResponse.setDesc(HttpResponseType.BAD_REQUEST.getDesc());

        Mockito
                .when(connectionWindowJpaRepository.findByMonth(Mockito.anyInt()))
                .thenReturn(Optional.of(connectionWindow));

        ResponseDto<ConnectionWindowDto> response = connectionWindowService.update(connectionWindow);

        Mockito.verify(connectionWindowJpaRepository, Mockito.times(1)).findByMonth(Mockito.anyInt());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }


    @Test
    void updateTest_FOUND_NOT_UPDATED() {

        ResponseDto<ConnectionWindowDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.PUT);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_UPDATED);
        expectedResponse.setCode(HttpResponseType.NOT_UPDATED.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_UPDATED.getDesc());

        Mockito
                .when(connectionWindowJpaRepository.findByMonth(Mockito.anyInt()))
                .thenReturn(Optional.of(connectionWindow));

        Mockito
                .when(connectionWindowJpaRepository.update(
                        Mockito.anyInt(),
                        Mockito.anyString(),
                        Mockito.any(LocalDateTime.class)))
                .thenReturn(0);

        ResponseDto<ConnectionWindowDto> response = connectionWindowService.update(connectionWindow);

        Mockito.verify(connectionWindowJpaRepository, Mockito.times(1)).findByMonth(Mockito.anyInt());
        Mockito.verify(connectionWindowJpaRepository, Mockito.times(1)).update(
                Mockito.anyInt(),
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
                .when(connectionWindowJpaRepository.findByMonth(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        ResponseDto<ConnectionWindowDto> response = connectionWindowService.update(connectionWindow);

        Mockito.verify(connectionWindowJpaRepository, Mockito.times(1)).findByMonth(Mockito.anyInt());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(response.getBody());
    }


    @Test
    void getByMonthTest_FOUND() {

        ResponseDto<ConnectionWindowDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.FOUND);
        expectedResponse.setCode(HttpResponseType.FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.FOUND.getDesc());
        expectedResponse.setBody(connectionWindow.toDto());

        Mockito
                .when(connectionWindowJpaRepository.findByMonth(Mockito.anyInt()))
                .thenReturn(Optional.of(connectionWindow));

        ResponseDto<ConnectionWindowDto> response = connectionWindowService.getByMonth(connectionWindow.getMonth());

        Mockito.verify(connectionWindowJpaRepository, Mockito.times(1)).findByMonth(Mockito.anyInt());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertEquals(expectedResponse.getBody().getMonth(), response.getBody().getMonth());
    }


    @Test
    void getByMonthTest_NOT_FOUND() {

        ResponseDto<ConnectionWindowDto> expectedResponse = new ResponseDto<>();

        expectedResponse.setHttpRequest(HttpRequestType.GET);
        expectedResponse.setHttpResponse(HttpResponseType.NOT_FOUND);
        expectedResponse.setCode(HttpResponseType.NOT_FOUND.getCode());
        expectedResponse.setDesc(HttpResponseType.NOT_FOUND.getDesc());

        Mockito
                .when(connectionWindowJpaRepository.findByMonth(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        ResponseDto<ConnectionWindowDto> response = connectionWindowService.getByMonth(connectionWindow.getMonth());

        Mockito.verify(connectionWindowJpaRepository, Mockito.times(1)).findByMonth(Mockito.anyInt());

        Assertions.assertEquals(expectedResponse.getHttpRequest(), response.getHttpRequest());
        Assertions.assertEquals(expectedResponse.getHttpResponse(), response.getHttpResponse());
        Assertions.assertEquals(expectedResponse.getCode(), response.getCode());
        Assertions.assertEquals(expectedResponse.getDesc(), response.getDesc());
        Assertions.assertNull(expectedResponse.getBody());
    }

}
