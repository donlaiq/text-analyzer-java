package com.donlaiq.api.exceptions;

public enum ErrorCode {
	INPUT_ERROR("PACKT-0001", "The input is malformed. Only letter from a-z and A-Z, or whitespaces, are accepted");
	
	private String errCode;
	private String errMsgKey;
	
	ErrorCode(final String errCode, final String errMsgKey) {
		this.errCode = errCode;
		this.errMsgKey = errMsgKey;
	}
	
	public String getErrCode() {
		return errCode;
	}
	
	public String getErrMsgKey() {
		return errMsgKey;
	}
}
