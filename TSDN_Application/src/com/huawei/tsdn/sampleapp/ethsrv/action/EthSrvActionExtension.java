package com.huawei.tsdn.sampleapp.ethsrv.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.struts2.ServletActionContext;

import com.huawei.tsdn.sampleapp.ethsrv.model.ServiceDataStruct;
import com.huawei.tsdn.sampleapp.ethsrv.service.EthServiceProc;
import com.huawei.tsdn.sampleapp.ethsrv.util.EthTopologyReadUtil;
import com.huawei.tsdn.sampleapp.util.GlobalResourceLoaderServlet;
import com.huawei.tsdn.sampleapp.util.RestLog;
import com.opensymphony.xwork2.ActionSupport;

public class EthSrvActionExtension extends ActionSupport{
	private static final long serialVersionUID1 = 1L;
    private final org.apache.logging.log4j.Logger log1 = LogManager.getLogger();
    private String httpAddr1;
    private ServiceDataStruct serviceData1;
    private EthServiceProc ethServiceProc;
    private final String ETH_TOPOLOGY_CONFIG = "config/topology_config.xml";
    
    public String getHttpAddr() {
        if (httpAddr1 == null) {
            httpAddr1 = GlobalResourceLoaderServlet.getServerAddress();
        }
        return httpAddr1;
    }

    public void setHttpAddr(String httpAddr1) {
        this.httpAddr1 = httpAddr1;
    }
    
    public void setServiceData(ServiceDataStruct serviceData) {
        this.serviceData1 = serviceData;
    }
    
    public EthSrvActionExtension() {
        String webRootPath = this.getClass().getClassLoader().getResource("/").getPath();
        EthTopologyReadUtil.LoaderTopologyInfo(webRootPath + ETH_TOPOLOGY_CONFIG);
        ethServiceProc = new EthServiceProc(true, new RestLog());
    }
    
    public String getNeConfig() throws Exception {
        boolean result = writeResultToResonpse(EthTopologyReadUtil.buildConfigJsonString());
        if (!result) {
            return ERROR;
        }
        return SUCCESS;
    }

    @Override
    public String execute() throws Exception {
        
        System.out.println("super.execute!");
        return super.execute();
    }
    
    public String createTunnelByCalendar() {
    	if (!serviceData1.isValid()) 
		{
			return "Invalid input parameter!";
		}
    	ResultSet rs = null;//鏁版嵁搴撴煡璇㈢粨鏋滈泦
    	try {
    	Class.forName("com.mysql.jdbc.Driver");
    	Connection conn1=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","828039");

    	System.out.println("杩涙潵浜�@#");
    	System.out.println("httpAddr1---->"+getHttpAddr());
    	String id = "1";
    	//String sql = "select * from calendar where id = "+id;
    	String sql = "INSERT INTO `calendar` VALUES (23, 'vxcv', '2015-05-13 09:00', '2015-05-13 20:00', 0, '5', '200', '600', '200', '600', '#gF5555', 2)";
    	Statement stmt = null;
    	stmt=conn1.createStatement();
//    	rs=stmt.executeQuery(sql);
    	stmt.executeUpdate(sql);
//    	rs.last();
//    	System.out.println(rs.getString("start"));
    	} catch (Exception e) {
    		System.out.println("rs-->"+rs);
    	}
    	
    		return SUCCESS;
    	
        }
    public boolean writeResultToResonpse(String result) {
        HttpServletResponse reponse = ServletActionContext.getResponse();
        reponse.setContentType("text/html;charset=UTF-8");  
        PrintWriter writer = null;
        try {
            writer = reponse.getWriter();
        } catch (IOException e) {
            log1.error(e.toString());
            return false;
        }
        writer.write(result);
        writer.close();
        return true;
    }

}
