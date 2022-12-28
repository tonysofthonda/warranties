package com.honda.hdm.warrantiesmg.remoto.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseApi {

	private Boolean success;
	
    private String message;
    
    private String title;
    
    private Integer code;
    
    private Object data;
    
    private String type;
    
}
