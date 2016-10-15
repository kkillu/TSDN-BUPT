package com.huawei.tsdn.sampleapp.ethsrv.util;

public class TsdnResponse {

	private int errCode = 200;
	private String response ;
	private String message;

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
 
}
