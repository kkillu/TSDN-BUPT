/*
 *
 * this class process eth tunnel request from user.
 * it can return different result by different input.
 * when the input is valid it can send request to tsdn
 * application. After the server return answer,it will build
 * reponse to user.
 */
package com.huawei.tsdn.sampleapp.ethsrv.action;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javassist.bytecode.stackmap.BasicBlock.Catch;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.apache.logging.log4j.LogManager;
import org.apache.struts2.ServletActionContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.huawei.tsdn.sampleapp.ethsrv.model.EthTunnel;
import com.huawei.tsdn.sampleapp.ethsrv.model.ServiceDataStruct;
import com.huawei.tsdn.sampleapp.ethsrv.service.EthServiceProc;
import com.huawei.tsdn.sampleapp.ethsrv.util.EthTopologyReadUtil;
import com.huawei.tsdn.sampleapp.ethsrv.util.TsdnException;
import com.huawei.tsdn.sampleapp.util.GlobalResourceLoaderServlet;
import com.huawei.tsdn.sampleapp.util.RestLog;
import com.huawei.tsdn.sampleapp.util.TsdnClient;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.crypto.provider.RSACipher;

import java.util.Date;
import java.sql.*;

/**
 * EthSrvActionSupport srvlet class
 * 
 * @author tWX301955
 *
 */
public class EthSrvActionSupport extends ActionSupport implements
		ServletContextListener {

	/**
	 * this init the class version
	 */
	private static final long serialVersionUID = 1L;
	private final org.apache.logging.log4j.Logger log = LogManager.getLogger();
	private String httpAddr;
	private String start;
	private String end;
	private String selectServiceString;// service to delete
	private ServiceDataStruct serviceData;
	private List<EthTunnel> ethTunnels;// to save all the services
	private String sResponseText;
	private String requestHistory;
	private EthServiceProc ethServiceProc;
	private final String ETH_TOPOLOGY_CONFIG = "config/topology_config.xml";

	String sql = null;
	Connection conn1 = null;
	Statement stmt = null;

	public String getHttpAddr() {
		if (httpAddr == null) {
			httpAddr = GlobalResourceLoaderServlet.getServerAddress();
		}
		return httpAddr;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public void setHttpAddr(String httpAddr) {
		this.httpAddr = httpAddr;
	}

	public void setSelectServiceString(String selectServiceString) {
		this.selectServiceString = selectServiceString;
	}

	public String getsResponseText() {
		return sResponseText;
	}

	public void setsResponseText(String sResponseText) {
		this.sResponseText = sResponseText;
	}

	public List<EthTunnel> getEthTunnels() {
		return ethTunnels;
	}

	public void setEthTunnels(List<EthTunnel> ethTunnels) {
		this.ethTunnels = ethTunnels;
	}

	public String getRequestHistory() {
		return requestHistory;
	}

	public ServiceDataStruct getServiceData() {
		return serviceData;
	}

	public void setServiceData(ServiceDataStruct serviceData) {
		this.serviceData = serviceData;
	}

	/**
	 * get config info when init
	 */
	public EthSrvActionSupport() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn1 = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/tsdn", "root", "828039");
			stmt = conn1.createStatement();
			System.out.println("进入数据库！");
			// String sql = "select * from calendar where id = "+id;
		} catch (Exception e) {

		}

		String webRootPath = this.getClass().getClassLoader().getResource("/")
				.getPath();
		EthTopologyReadUtil.LoaderTopologyInfo(webRootPath
				+ ETH_TOPOLOGY_CONFIG);
		ethServiceProc = new EthServiceProc(true, new RestLog());
	}

	/**
	 * this get configuration
	 * 
	 * @return method result
	 * @throws Exception
	 */
	public String getNeConfig() throws Exception {
		boolean result = writeResultToResonpse(EthTopologyReadUtil
				.buildConfigJsonString());
		if (!result) {
			return ERROR;
		}
		return SUCCESS;
	}

	@Override
	public String execute() throws Exception {

		getTunnelList();
		return super.execute();
	}

	/**
	 * the other operator can enuser the service list is new, so just return
	 * service list from local
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getTunnelList() throws Exception {

		requestHistory = RestLog.getLocalRequestHistory();

		queryEthTunnelList(getHttpAddr());
		ethTunnels = ethServiceProc.getEthTunnels();
		return SUCCESS;
	}

	/**
	 * process the create eth tunnel requset
	 * 
	 * @return method result
	 * @throws Exception
	 */
	public String createTunnel() throws Exception {

		String operResult = "Create service success!";
		boolean bret = true;
		try {
			ethServiceProc.createEthTunnel(getHttpAddr(), serviceData);
			if (!ethServiceProc.isRunOk()) {
				operResult = "Create service failed!";
				bret = false;
			}
		} catch (TsdnException ex) {
			operResult = "Create service failed!";
			ex.printStackTrace();
			bret = false;
		}

		boolean result = writeResultToResonpse(operResult);
		if (!result || !bret) {
			return ERROR;
		}
		// get the new service list after the create operator
		ethServiceProc.setIsRecord(false);
		queryEthTunnelList(getHttpAddr());
		return SUCCESS;
	}

	/**
	 * process the create eth tunnel requset by calendar
	 * 
	 * @return method result
	 * @throws Exception
	 */

	public String createTunnelByCalendar() {
		ResultSet rs = null;
		try {
			sql = "INSERT INTO calendar(title,ingress,srcPort,egress,distPort,bandWidth,serviceType,start,end) VALUES ('"
					+ serviceData.getServiceName()
					+ "','"
					+ serviceData.getIngress()
					+ "','"
					+ serviceData.getSrcPort()
					+ "','"
					+ serviceData.getEgress()
					+ "','"
					+ serviceData.getDistPort()
					+ "','"
					+ serviceData.getBandWidth()
					+ "','"
					+ serviceData.getServiceType()
					+ "','"
					+ start
					+ "','"
					+ end + "')";
			System.out.println("sql--->" + sql);

			stmt.executeUpdate(sql);
			// 查询刚存入的记录的ID值
//			sql = "select * from calendar";
//			rs = stmt.executeQuery(sql);
//			rs.last();
//			System.out.println("id---->" + rs.getInt("id"));
//			int id = rs.getInt("id");

		} catch (Exception e) {
			System.out.println("rs-->" + rs);
		}

		return SUCCESS;

	}

	public Date getTime() {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, 0); // 控制秒
		Date time = calendar.getTime();
		System.out.println("starttimegetTime----->" + time);
		return time;
	}

	boolean bret = true;

	public class TimerTask1 extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			String uuid = null;
			String operResult = "Create service success!";
			Date time = getTime();
			SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String timeString = ymdhm.format(time);
			ResultSet rs = null;
			sql = "select * from calendar where start = '" + timeString + "'";
			System.out.println("sql-->" + sql);

			try {
				stmt = conn1.createStatement();
				rs = stmt.executeQuery(sql);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {

				while (rs.next()) {
					ServiceDataStruct serviceData1 = new ServiceDataStruct();
					serviceData1.setServiceName(rs.getString("title"));
					serviceData1.setIngress(Long.parseLong(rs
							.getString("ingress")));
					serviceData1.setSrcPort(rs.getString("srcPort"));
					serviceData1.setEgress(Long.parseLong(rs
							.getString("egress")));
					serviceData1.setDistPort(rs.getString("distPort"));
					serviceData1.setBandWidth(Long.parseLong(rs
							.getString("bandWidth")));
					serviceData1.setServiceType(Byte.parseByte(rs
							.getString("serviceType")));
					System.out.println("servicedata--->" + serviceData1);
					try {
						uuid = ethServiceProc.createEthTunnel(getHttpAddr(),
								serviceData1);
						Calendar calendar = Calendar.getInstance();
						Date creatTime = new Date();
						creatTime = calendar.getTime();
						System.out.println("starttimeCreateTrue----->"
								+ creatTime);

						if (!ethServiceProc.isRunOk()) {
							operResult = "Create service failed!";
							bret = false;
						}
						else {
							sql = "UPDATE calendar SET uuid = '"+uuid+"' WHERE id = "+rs.getInt("id");
							stmt.executeUpdate(sql);
							
						}
					} catch (TsdnException ex) {
						operResult = "Create service failed!";
						ex.printStackTrace();
						bret = false;
					}
					// System.out.println("flag-id-->"+id);
					System.out.println("createUuid-->" + uuid);

					
				}
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public class TimerTask2 extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String uuid = null;
			String operResult = "Delete service success!";

			Date time = getTime();
			SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String timeString = ymdhm.format(time);
			ResultSet rs = null;
			sql = "select * from calendar where end = '" + timeString + "'";
			System.out.println("sql-->" + sql);
			try {
				stmt = conn1.createStatement();
				rs = stmt.executeQuery(sql);
			} catch (Exception e) {
				System.out.println("rs-->" + rs);
				System.out.println("e--->" + e);
			}
			try {
				while (rs.next()) {

					uuid = rs.getString("uuid");
					ethServiceProc.deleteEthTunnel(getHttpAddr(), uuid);
					Calendar calendar = Calendar.getInstance();
					Date deleteTime = new Date();
					deleteTime = calendar.getTime();
					System.out.println("endtimeCreateTrue----->" + deleteTime);
					sql = "DELETE FROM calendar WHERE uuid = '"+uuid+"'";
					stmt.executeUpdate(sql);
					// System.out.println("deleteUuid-->" + uuid);
					// Object[] options = { "确定", "取消", "帮助" };
					// int response = JOptionPane.showOptionDialog(null,
					// operResult, "选项对话框标题", JOptionPane.YES_OPTION,
					// JOptionPane.QUESTION_MESSAGE, null, options,
					// options[0]);
					// if (response == 0) {
					// System.out.println("您按下了第OK按钮 ");
					// }
				}
			} catch (TsdnException | NumberFormatException | HeadlessException
					| SQLException ex) {
				
				operResult = "Delete some service failed!";
			}

		}
	}

	/**
	 * process the create eth tunnel requset
	 * 
	 * @return method result
	 * @throws Exception
	 */
	public String deleteTunnel() throws Exception {

		log.info("del service string is " + selectServiceString);

		String operResult = "Delete service success!";

		String[] selArrString = null;
		boolean bret = false;
		if (selectServiceString != null) {
			selArrString = selectServiceString.split(",");
			for (int i = 0; i < selArrString.length; i++) {

				String tmp = selArrString[i];
				try {
					ethServiceProc.deleteEthTunnel(getHttpAddr(), tmp);

				} catch (TsdnException ex) {
					log.error(ex.getMessage());
					log.error("Delete service ID:" + tmp + " failed!");
					operResult = "Delete some service failed!";
					break;
				}

			}
		}

		boolean result = writeResultToResonpse(operResult);

		if (!result) {
			return ERROR;
		}

		// get the new service list after the delete operator
		ethServiceProc.setIsRecord(false);
		queryEthTunnelList(getHttpAddr());
		return SUCCESS;
	}

	/**
	 * send request to server and get eth service detail
	 * 
	 * @param httpAddress
	 */
	public void queryEthTunnelList(String httpAddress) {

		try {
			if (ethServiceProc.getEthTunnel(httpAddress) == true) {
				log.debug("Get eth service success!");
			} else {
				log.error("Get eth service failed!");
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TsdnException e) {
			e.printStackTrace();
		}
	}

	/**
	 * resonse result to user page
	 * 
	 * @param result
	 */
	public boolean writeResultToResonpse(String result) {
		HttpServletResponse reponse = ServletActionContext.getResponse();
		reponse.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = null;
		try {
			writer = reponse.getWriter();
		} catch (IOException e) {
			log.error(e.toString());
			return false;
		}
		writer.write(result);
		writer.close();
		return true;
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("hahahahah");
		Date time = getTime();
		Timer timer = new Timer();
		timer.schedule(new TimerTask1(), time, 1000 * 60);
		timer.schedule(new TimerTask2(), time, 1000 * 60);

	}
}
