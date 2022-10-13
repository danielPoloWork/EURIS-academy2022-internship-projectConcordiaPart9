package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.ConfigurationService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ConfigurationDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.configurations.ConfigurationPostRequest;
import com.euris.academy2022.concordia.dataPersistences.DTOs.requests.configurations.ConfigurationPutRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @PostMapping
    public ResponseDto<ConfigurationDto> insert(@RequestBody ConfigurationPostRequest configurationDto) {
        return configurationService.insert(configurationDto.toModel());
    }

    @PutMapping
    public ResponseDto<ConfigurationDto> update(@RequestBody ConfigurationPutRequest configurationDto) {
        return configurationService.update(configurationDto.toModel());
    }

    @DeleteMapping("/{label}")
    public ResponseDto<ConfigurationDto> deleteByLabel(@PathVariable String label) {
        return configurationService.deleteByLabel(label);
    }

    @GetMapping("/{label}")
    public ResponseDto<ConfigurationDto> getByLabel(@PathVariable String label) {
        return configurationService.getByLabel(label);
    }
}
