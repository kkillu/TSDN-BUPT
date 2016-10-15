package com.huawei.tsdn.sampleapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.huawei.tsdn.sampleapp.ethsrv.model.ServiceDataStruct;
import com.huawei.tsdn.sampleapp.ethsrv.util.EthTopologyReadUtil;

public class RestLog {

	private static StringBuilder localRequestHistory = new StringBuilder();
	private boolean record = true;

	/**
	 * save history records
	 * 
	 * @param recordString
	 */
	private synchronized void setHistoryCallRecords(String recordString) {
		//Date date = new Date();

		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss E", Locale.ENGLISH);
		// localRequestHistory.append(dateFormat.format(date)).append("\r\n").append(recordString).append("\r\n");
		if (this.record) {
			
			synchronized(localRequestHistory)
			{
				localRequestHistory.append("\r\n").append(recordString).append("\r\n");
			}
		}
	}

	public String getNow() {
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		
		return dateFormat.format(date);
	}
	
	public static String getLocalRequestHistory() {
		return localRequestHistory.toString();
	}

	public boolean isRecord() {
		return record;
	}

	public void setRecord(boolean record) {
		this.record = record;
	}
	
	public void beginGet(String requestUrl)
	{
		
		setHistoryCallRecords("==========" + getNow() + "============");
		setHistoryCallRecords("[BEGIN]: Get EthService \r\n \r\n[GET]:  " + requestUrl + " \r\n\r\n[BODY ]: NA ");
	}
	
	
	public void beginPost (ServiceDataStruct serviceData, String requestUrl, String body)
	{
		
		String ingressName = EthTopologyReadUtil.getNeNameByNeId(serviceData.getIngress());
		String egressName = EthTopologyReadUtil.getNeNameByNeId(serviceData.getEgress());
		setHistoryCallRecords("==========" + getNow() + "============");
		setHistoryCallRecords("[BEGIN]: Create EthService "+ ingressName + " <--> " + egressName + " \r\n\r\n[POST]:  " + requestUrl + "\r\n\r\n[BODY]:  " + body);
		
	}
	
	public void beginPut(ServiceDataStruct serviceData, String requestUrl, String body)
	{
		setHistoryCallRecords("==========" + getNow() + "============");
		setHistoryCallRecords("[BEGIN]: Modify EthService "+ serviceData.getSrvId() + " ServiceName " + serviceData.getServiceName() + " Bandwidth " + serviceData.getBandWidth() + " \r\n\r\n[PUT]:  " + requestUrl + "\r\n\r\n[BODY]:  " + body);
	}
	
	public void beginDelete(String requestUrl)
	{
		setHistoryCallRecords("==========" + getNow() + "============");
		setHistoryCallRecords("[BEGIN]: Delete EthService \r\n\r\n[DELETE]:  " + requestUrl + "\r\n\r\n[BODY]: NA");
	}
	
	public void responseLog(String body)
	{
		 setHistoryCallRecords("[RESPONSE]: " + body);
	}
	
	public void end()
	{
		 setHistoryCallRecords("[END]\r\n");
	}
	

}
