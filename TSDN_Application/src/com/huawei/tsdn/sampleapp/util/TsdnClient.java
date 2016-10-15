package com.huawei.tsdn.sampleapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import org.apache.logging.log4j.LogManager;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.tsdn.sampleapp.ethsrv.util.TsdnException;
import com.huawei.tsdn.sampleapp.ethsrv.util.TsdnResponse;
import com.huawei.tsdn.sampleapp.ethsrv.util.TsdnException.ErrorType;


public class TsdnClient {
	
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger();
    private static MyX509TrustManager trustManager = new MyX509TrustManager();
    private static MyHostnameVerifier hostnameVerifier = new MyHostnameVerifier();
    private static BASE64Decoder decoder = new BASE64Decoder();
    private static BASE64Encoder encoder = new BASE64Encoder();
    public final static int WEB_STATUS_CODE_200 = 200;
    public final static String HTTP_REQUEST_POST = "POST";
    public final static String HTTP_REQUEST_GET = "GET";
    public final static String HTTP_REQUEST_DELETE = "DELETE";
    public final static String HTTP_REQUEST_PUT = "PUT";
    private final static int DEFAULT_MAX_CONNECTION_TIME = 5000;
    
    private TsdnResponse response = null;
    
    
    private RestLog restLog = null;

    public TsdnClient(RestLog restLog)
    {
    	this.restLog = restLog;
    }
    
    /**
     * open the connection to the requestUrl
     * 
     * @param requestUrl
     * @param requestMethod
     * @return
     */
    public static HttpsURLConnection getHttpsURLConnection(String requestUrl, String requestMethod) {
        init();
        HttpsURLConnection urlConnection = null;
        try {
            urlConnection = (HttpsURLConnection) (new URL(requestUrl)).openConnection();
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            return urlConnection;
        } catch (IOException e) {
            log.error(e.getMessage());
            return urlConnection;
        }
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setConnectTimeout(DEFAULT_MAX_CONNECTION_TIME);
        try {
            urlConnection.setRequestMethod(requestMethod);
        } catch (ProtocolException e) {
            log.error(e.getMessage());
            return urlConnection;
        }
        String username = GlobalResourceLoaderServlet.getUserName();
        String password = GlobalResourceLoaderServlet.getPassWord();
        String userpassword = username + ":" + password;
        String encodedAuthorization = encodeBase64(userpassword);
        urlConnection.setRequestProperty("Authorization", "Basic " + encodedAuthorization);

        return urlConnection;
    }
    
    /***
     * get response from tsdn 
     * @param reponse  the reponse from t-sdn
     * @return
     */
    public  String getResponseString(String reponse) 
    {
		
		log.info("get msg is " + reponse);
		
		writeLog(reponse);
		if (reponse == null)
		{
			return null;
		}
		
		
		ObjectMapper mapper = new ObjectMapper();
        JsonNode root;
		try {
			root = mapper.readTree(reponse);
		    if ((root != null) && (root.get("srv_list") != null)) {
	         	return reponse;
	        }
		} catch (JsonProcessingException e) {
			log.error("parse json error," + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			log.error("io error," + e.toString());
			e.printStackTrace();
		}
		
		if (reponse != null)
		{
			return reponse;
		}
      
   		return null;
    }
    
    
    /***
     * get response from tsdn
     * @param urlConnection
     * @return
     */
    public  String getResponse(HttpsURLConnection urlConnection) 
    {
    	 
   		BufferedReader bufferReader = null;
   		InputStreamReader inputReader = null;
   		try 
   		{
   			inputReader = new InputStreamReader(urlConnection.getInputStream());
   		} 
   		catch (IOException e1) 
   		{
   			log.error("io error," + e1.toString());
   		}
   		
   		bufferReader = new BufferedReader(inputReader);
   		String retReadInfo = "";
   		String readInfo = "";
   		StringBuffer buf = new StringBuffer();
   		try 
   		{
   			
   			while ((readInfo = bufferReader.readLine()) != null) 
   			{
     			 if (readInfo != null) { 
   	     			 buf.append(readInfo);
    			 }
   			}
   			return buf.toString();
   		}  
   		catch (IOException e) 
   		{
   			log.error("io error," + e.toString());
   		} 
   		finally 
   		{
   			closeStreamReader(bufferReader);
   			closeStreamReader(inputReader);
   		}
   		
   		return null;
   		
    }
    

    /***
     * process rest request 
     * @param method support GET/POST/PUT/DELETE
     * @param url request url
     * @return
     * @throws TsdnException 
     */
    public String proc(String method, String url) throws TsdnException
    {
    	 
    	if (!HTTP_REQUEST_GET.equalsIgnoreCase(method)) {
            return null;
        }
    	HttpsURLConnection urlConnection = getHttpsURLConnection(url, "GET");
    	if (urlConnection == null) 
		{
			return null;
		}
    	String retValue = null;
    	response = getResponseFromConnection(urlConnection, "");
        try{
        	retValue = getResponseString(response.getResponse());
        } finally {
        	urlConnection.disconnect();
        }
        
		return retValue;
		
    }
    

	/**
	 * close the reader stream
	 * 
	 * @param reader
	 */
	private static void closeStreamReader(Reader reader) 
	{
		if (reader != null) 
		{
			try 
			{
				reader.close();
			}
			catch (IOException e) 
			{
				log.error("close reader error," + e.toString());
			}
		}
	}
     
    
    /**
     * write request info and get result
     * 
     * @param urlConnection
     *            the request url connection
     * @param jsonObject
     *            request body
     * @return process result
     * @throws TsdnException 
     */
    public TsdnResponse getResponseFromConnection(HttpsURLConnection urlConnection, String body) throws TsdnException {

    	 response = new TsdnResponse();
         
         if (body != null && body.length() > 0) {
             // write the request in json form to request content
             try {
            	 urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                 urlConnection.getOutputStream().write(body.getBytes());
                 urlConnection.getOutputStream().flush();
                 urlConnection.getOutputStream().close();

             } catch (IOException e) {
                 log.error(e.toString() + urlConnection.getURL().getPath());
                 throw new TsdnException(ErrorType.CONNECTION_FAILED, e.getMessage());
             }
         }
         
         try {
        	 
        	 int responseCode = urlConnection.getResponseCode();
        	 String responseMessage = urlConnection.getResponseMessage();
             
             response.setErrCode(responseCode);
             response.setMessage(responseMessage);
             if(responseCode == WEB_STATUS_CODE_200)
             {
            	 String retValue = getResponse(urlConnection);
            	 response.setResponse(retValue);
             } 
       
         } catch (IOException e) {
             log.error(e.toString());
             response.setErrCode(-1);
             response.setResponse(e.getMessage());
         }
      
         return response;
    }
    
    /**
     * the initialize function,do encoding etc.
     */
    private static void init() {
        SSLContext sslContext = null;
        try {
            // SSLContext
            sslContext = SSLContext.getInstance("TLS");
            X509TrustManager[] xtmArray = new X509TrustManager[] { trustManager };
            sslContext.init(null, xtmArray, new java.security.SecureRandom());
        } catch (GeneralSecurityException e) {
            log.error(e.toString());
        }
        if (sslContext != null) {
            // SSLContextSSLSocketFactory
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
    }

    public String decodeBase64(String targetString) {

        if (targetString == null) {
            return null;
        }
        byte[] decodeArray;

        try {
            decodeArray = decoder.decodeBuffer(targetString);
            return new String(decodeArray, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.toString());
        } catch (IOException e) {
            log.error(e.toString());
        }
        return null;

    }

    public static String encodeBase64(String targetString) {

        try {
            return encoder.encode(targetString.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.toString());
        }
        return null;
    }

    private static class MyX509TrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    private static class MyHostnameVerifier implements HostnameVerifier {

        public boolean verify(String hostname, SSLSession session) {

            return true;
        }
    }

    
    /**
     * process service operator
     * 
     * @param method  the http method (not supported get  )
     * @param jsonObject  request json
     * @param requestUrl  request url
     * @return response string
     * @throws TsdnException 
     */
    public String proc(String method, String requestUrl,  String body) throws TsdnException {
    	
        String retValue = null;
        // not support get  
        if ((method == HTTP_REQUEST_GET)) {
            return null;
        }
        // get an auth connection
        HttpsURLConnection urlConnection = getHttpsURLConnection(requestUrl, method);
        if (urlConnection == null) {
            throw new TsdnException(ErrorType.AUTHENTICATION_FAILED);
        }
        try{
        	response = getResponseFromConnection(urlConnection, body);
        	if (response.getErrCode() !=  WEB_STATUS_CODE_200)
        	{
        		writeLog("failed to create errorCode " + response.getErrCode() + " :" + response.getMessage());
        		throw new TsdnException(ErrorType.SERVICE_FAILED);
        	}else {
    			retValue = response.getResponse();
    			writeLog(retValue);
    		}
        } finally {
        	urlConnection.disconnect();
        }
        return retValue;
    }
    
    /****
     * write log
     * @param restLog
     * @param message
     */
    public void writeLog(String message)
    {
    	if (this.restLog != null)
    	{
    		this.restLog.responseLog(message);
    	}
    }

    /**
     * getResponse
     * @return
     */
	public TsdnResponse getResponse() {
		return response;
	}

	/***
	 * setResponse
	 * @param response
	 */
	public void setResponse(TsdnResponse response) {
		this.response = response;
	}
    
    
    
}
