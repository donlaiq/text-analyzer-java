package com.donlaiq.api.exceptions;

public enum ErrorCode {
	INPUT_ERROR("0001", "The API is being called in a wrong way");
	
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
