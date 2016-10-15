package com.huawei.tsdn.sampleapp.ethsrv.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.tsdn.sampleapp.ethsrv.model.EthTunnel;
import com.huawei.tsdn.sampleapp.ethsrv.model.ServiceDataStruct;
import com.huawei.tsdn.sampleapp.ethsrv.util.EthServiceJsonBuilder;
import com.huawei.tsdn.sampleapp.ethsrv.util.EthServiceTool;
import com.huawei.tsdn.sampleapp.ethsrv.util.TsdnException;
import com.huawei.tsdn.sampleapp.ethsrv.util.TsdnException.ErrorType;
import com.huawei.tsdn.sampleapp.ethsrv.util.TsdnResponse;
import com.huawei.tsdn.sampleapp.util.JsonCodec;
import com.huawei.tsdn.sampleapp.util.RestLog;
import com.huawei.tsdn.sampleapp.util.TsdnClient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.List;

/**
 * Class to create eth service
 * 
 * @author tWX301955
 * 
 */
public class EthServiceProc {
	
	public final static double ETH_URL_VERSION = 1.6;
	private final org.apache.logging.log4j.Logger log;
	
	private JsonCodec jsonBuildutil;
	private static List<EthTunnel> ethTunnels;
	
	private RestLog restLog = null;
	private TsdnClient tsdn = null;
	
    public final static String HTTP_REQUEST_POST = "POST";
    public final static String HTTP_REQUEST_GET = "GET";
    public final static String HTTP_REQUEST_DELETE = "DELETE";
    public final static String HTTP_REQUEST_PUT = "PUT";
    
	public EthServiceProc(boolean record, RestLog restLog) {
		log = LogManager.getLogger();
		this.restLog = restLog;
		this.restLog.setRecord(record);
		tsdn = new TsdnClient(this.restLog);
		jsonBuildutil = new EthServiceJsonBuilder();
	}

	/**
	 * provider the create service
	 * 
	 * @param httpAddress
	 * @param serviceData
	 * @return
	 * @throws TsdnException 
	 */
	public String createEthTunnel(String httpAddress,  ServiceDataStruct serviceData) throws TsdnException
	{
		System.out.println("servicedata--->"+serviceData);
		EthTunnel entity = EthServiceTool.translateServiceData(serviceData);
		ObjectNode jsonNode = jsonBuildutil.encodeCreateTunnelJson(entity);//将entity（即要添加的信息转换成json格式）
		if (jsonNode == null) 
		{
			throw new TsdnException(ErrorType.BODY_FORMAT_FAILED);
		}
		
		String url = String.format("%s/netdata/ethsrvs/%s/ethsrvs.json", 
									httpAddress,
									ETH_URL_VERSION);
		String body = jsonNode.toString(); //将json格式转换成字符串
		 
		// write log begin
		restLog.beginPost(serviceData, url, body);//将字符串打印出来
		
		// create ethernet service 			
		String createReply = tsdn.proc(HTTP_REQUEST_POST, url, body);	
		System.out.println("createReply-->"+createReply);
		
		String uuid = UuidOfSRVList(createReply);
		
		// write log end 
		restLog.end();

		return uuid;
	}
	 
	public String UuidOfSRVList(String reply){
		JSONObject srvStr = JSONObject.fromObject(reply);
		JSONArray srvlistArray = srvStr.getJSONArray("srv_list");
		JSONObject srvlistObj = srvlistArray.getJSONObject(0);
		
		String uuid = srvlistObj.getString("uuid");
		
		return uuid;
	}
	 
	/**
	 * query service function,
	 * 
	 * @param urlFromAction
	 * @return return true if success, otherwise return false
	 * @throws IOException
	 * @throws JsonProcessingException
	 * @throws TsdnException 
	 */
	public boolean getEthTunnel(String httpAddress) throws JsonProcessingException, IOException, TsdnException 
	{	
		if ((ethTunnels != null) && (ethTunnels.size() > 0)) 
		{
			ethTunnels.clear();
		}
		
		String url = String.format("%s/netdata/ethsrvs/%s/service_eth_detail.json?srv_type=1", httpAddress, ETH_URL_VERSION );
		
		// write log begin 
		restLog.beginGet(url);
		
		//query ethernet service 
		String responseStr = tsdn.proc(HTTP_REQUEST_GET, url);	
		if  (responseStr != null && responseStr.length() > 0) 
		{
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(responseStr);
			if ((root != null) && (root.get("srv_list") != null)) 
			{
				ethTunnels = jsonBuildutil.decode((ArrayNode) root.get("srv_list"));
			}
		}

		// write log end 
		restLog.end();
		
		return (responseStr != null);
	}

	/**
	 * process the delete request
	 * 
	 * @param httpAddress
	 * @param selectServiceString
	 * @return
	 * @throws TsdnException 
	 */
	public void deleteEthTunnel(String httpAddress, String uuid) throws TsdnException 
	{		
		
		if (uuid == null) 
		{
			throw new TsdnException(ErrorType.LOCK_PARAAMETERS_SRVID_FAILED, uuid);
		}
		
		String url = String.format("%s/netdata/ethsrvs/%s/%s.json", httpAddress, ETH_URL_VERSION, uuid);
		
		// write log begin
		restLog.beginDelete(url);

		//delete ethernet service  
		tsdn.proc(HTTP_REQUEST_DELETE, url, null);
		 
		// write log end
		restLog.end();
		 
	}
	
	
	public void setIsRecord(boolean b) {
		
		this.restLog.setRecord(b);
	}
	
	public List<EthTunnel> getEthTunnels() {
		return ethTunnels;
	}
	
	/***
	 * detect if run ok 
	 * @return
	 */
	public boolean isRunOk()
	{
		if (tsdn != null)
		{
			TsdnResponse response = tsdn.getResponse();
			if(response != null && response.getErrCode() == TsdnClient.WEB_STATUS_CODE_200)
			{
				return true;
			}
		}
		
		return false;
		
	}
	
}
