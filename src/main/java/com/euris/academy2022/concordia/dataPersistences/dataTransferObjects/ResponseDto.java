package com.euris.academy2022.concordia.dataPersistences.dataTransferObjects;


import com.euris.academy2022.concordia.utils.enums.HttpRequestType;
import com.euris.academy2022.concordia.utils.enums.HttpResponseType;
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

	private HttpRequestType httpRequest;
	private HttpResponseType httpResponse;
	private String code;
	private String desc;
	private Model body;
}