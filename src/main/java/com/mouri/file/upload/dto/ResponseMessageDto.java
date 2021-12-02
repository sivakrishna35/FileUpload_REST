package com.mouri.file.upload.dto;

public class ResponseMessageDto {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseMessageDto(String message) {
		super();
		this.message = message;
	}

	public ResponseMessageDto() {
		super();
	}

	@Override
	public String toString() {
		return "ResponseMessageDto [message=" + message + "]";
	}
	
	

}
