package com.huawei.tsdn.sampleapp.ethsrv.util;

public class TsdnException extends Exception {

	private static final long serialVersionUID = -7962997968339576579L;
	
	public static final String AUTH_INFO_ERROR  ="Authentication is not passed, may be the user name and password is not correct";
	public static final String CONNECTION_NOT_REACHABLE  ="The network is not reachable, please check the network connection";
	public static final String SERVICE_RESPONSE_FAILED  ="Task execution error, please check the error message";
	public static final String LACK_PARAMETERS  ="Lack of srv_id parameters, please check the input parameters";
	public static final String BODY_FORMAT_INCORRECT  ="Body is not a normal JSON format, can not be converted into JSON data";
	
	private ErrorType type = null;
	
	public TsdnException(ErrorType errType)
	{
		super(errType.getErrMsg());
		this.type = errType;
	}
	
	public TsdnException(ErrorType errType, String arg0)
	{
		super(arg0);
		this.type = errType;
		this.type.setErrMsg(arg0);
	}
			
    public TsdnException(Throwable arg0)
    {
    	super(arg0);
    	this.type = ErrorType.UNKNOW_EXCEPTION;
    }
    
    public TsdnException(String arg0, Throwable arg1)
    {
    	super(arg0, arg1);
    	this.type = ErrorType.UNKNOW_EXCEPTION;
    }
    
	public enum ErrorType
	{
		AUTHENTICATION_FAILED(203, AUTH_INFO_ERROR),
		CONNECTION_FAILED(404, CONNECTION_NOT_REACHABLE),
		SERVICE_FAILED(500, SERVICE_RESPONSE_FAILED),
		LOCK_PARAAMETERS_SRVID_FAILED(204,LACK_PARAMETERS),
		BODY_FORMAT_FAILED(501,BODY_FORMAT_INCORRECT),
		UNKNOW_EXCEPTION(550, "");
		
		private int value;
		private String errMsg;
		
		private ErrorType(int errCode, String errMsg)
		{
			this.value = errCode;
			this.errMsg = errMsg;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getErrMsg() {
			return errMsg;
		}

		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}
	} 
	
}
