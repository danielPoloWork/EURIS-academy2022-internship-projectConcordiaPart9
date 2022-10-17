package com.euris.academy2022.concordia.businessLogics.services.trelloServices;

import com.euris.academy2022.concordia.dataPersistences.DTOs.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface TrelloLabelService {
    ResponseDto<ResponseEntity<String>> addLabelOnCardByIdCardAndIdLabel(String idCard, String idLabel);
    ResponseDto<ResponseEntity<String>> removeLabelFromCardByIdCardAndIdLabel(String idCard, String idLabel);
}
