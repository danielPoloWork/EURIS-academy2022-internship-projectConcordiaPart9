package com.danielpolo.teslaBattery.dataPersistences.dataTransferObjects.responses;

import com.danielpolo.teslaBattery.utils.enums.HttpRequestType;
import com.danielpolo.teslaBattery.utils.enums.HttpResponseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ResponseDto<Model> {

	// FIELDS ----------------------------------------------------------------------------------------
	private HttpRequestType httpRequest;
	private HttpResponseType httpResponse;
	private String code;
	private String desc;
	private Model body;
}