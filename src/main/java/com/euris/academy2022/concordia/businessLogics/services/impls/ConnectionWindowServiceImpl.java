package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.ConnectionWindowService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ConnectionWindowDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.models.ConnectionWindow;
import com.euris.academy2022.concordia.jpaRepositories.ConnectionWindowJpaRepository;
import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConnectionWindowServiceImpl implements ConnectionWindowService {

    private final ConnectionWindowJpaRepository connectionWindowJpaRepository;

    public ConnectionWindowServiceImpl(ConnectionWindowJpaRepository connectionWindowJpaRepository) {
        this.connectionWindowJpaRepository = connectionWindowJpaRepository;
    }

    @Override
    public ResponseDto<ConnectionWindowDto> insert(ConnectionWindow connectionWindow) {
        ResponseDto<ConnectionWindowDto> response = new ResponseDto<>();

        Integer connectionWindowCreated = connectionWindowJpaRepository.insert(
                connectionWindow.getMonth(),
                connectionWindow.getCron(),
                LocalDateTime.now(),
                LocalDateTime.now());

        response.setHttpRequest(HttpRequestType.POST);

        if (connectionWindowCreated != 1) {
            response.setHttpResponse(HttpResponseType.NOT_CREATED);
            response.setCode(HttpResponseType.NOT_CREATED.getCode());
            response.setDesc(HttpResponseType.NOT_CREATED.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.CREATED);
            response.setCode(HttpResponseType.CREATED.getCode());
            response.setDesc(HttpResponseType.CREATED.getDesc());
            response.setBody(connectionWindow.toDto());
        }

        return response;
    }

    @Override
    public ResponseDto<ConnectionWindowDto> update(ConnectionWindow connectionWindow) {
        ResponseDto<ConnectionWindowDto> response = new ResponseDto<>();
        Optional<ConnectionWindow> optionalConnectionWindowDto = connectionWindowJpaRepository.findByMonth(connectionWindow.getMonth());

        response.setHttpRequest(HttpRequestType.PUT);

        if (optionalConnectionWindowDto.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            Integer updatedConnectionWindow = connectionWindowJpaRepository.update(
                    connectionWindow.getMonth(),
                    connectionWindow.getCron(),
                    LocalDateTime.now());

            if (updatedConnectionWindow != 1) {
                response.setHttpResponse(HttpResponseType.NOT_UPDATED);
                response.setCode(HttpResponseType.NOT_UPDATED.getCode());
                response.setDesc(HttpResponseType.NOT_UPDATED.getDesc());
            } else {
                response.setHttpResponse(HttpResponseType.UPDATED);
                response.setCode(HttpResponseType.UPDATED.getCode());
                response.setDesc(HttpResponseType.UPDATED.getDesc());
                response.setBody(connectionWindow.toDto());
            }
        }
        return response;
    }


    @Override
    public ResponseDto<ConnectionWindowDto> getByMonth(int month) {

        ResponseDto<ConnectionWindowDto> response = new ResponseDto<>();
        Optional<ConnectionWindow> optionalConnectionWindow = connectionWindowJpaRepository.findByMonth(month);

        response.setHttpRequest(HttpRequestType.GET);

        if (optionalConnectionWindow.isEmpty()) {
            response.setHttpResponse(HttpResponseType.NOT_FOUND);
            response.setCode(HttpResponseType.NOT_FOUND.getCode());
            response.setDesc(HttpResponseType.NOT_FOUND.getDesc());
        } else {
            response.setHttpResponse(HttpResponseType.FOUND);
            response.setCode(HttpResponseType.FOUND.getCode());
            response.setDesc(HttpResponseType.FOUND.getDesc());
            response.setBody(optionalConnectionWindow.get().toDto());
        }
        return response;
    }
}
