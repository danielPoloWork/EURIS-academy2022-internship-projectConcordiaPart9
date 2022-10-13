package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.ConnectionWindowService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ConnectionWindowDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.connectionWindow.ConnectionWindowPostRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.connectionWindow.ConnectionWindowPutRequest;
import com.euris.academy2022.concordia.dataPersistences.models.ConnectionWindow;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/connectionWindow")
public class ConnectionWindowController {

    private final ConnectionWindowService connectionWindowService;

    public ConnectionWindowController(ConnectionWindowService connectionWindowService) {
        this.connectionWindowService = connectionWindowService;
    }


    @PostMapping
    public ResponseDto<ConnectionWindowDto> insert(@RequestBody ConnectionWindowPostRequest connectionWindowDto) {
        return connectionWindowService.insert(connectionWindowDto.toModel());
    }

    @PutMapping
    public ResponseDto<ConnectionWindowDto> update(@RequestBody ConnectionWindowPutRequest connectionWindowDto) {
        return connectionWindowService.update(connectionWindowDto.toModel());
    }

    @GetMapping("/{month}")
    public ResponseDto<ConnectionWindowDto> getByMonth(@PathVariable int month) {
        return connectionWindowService.getByMonth(month);
    }
}
