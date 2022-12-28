package com.honda.hdm.warrantiesmg.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class UploadFileResponse {

	private String fileName;
    private String fileViewUri;
    private String fileType;
    private long size;

}
