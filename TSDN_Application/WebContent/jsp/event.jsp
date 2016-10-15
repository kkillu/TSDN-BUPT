<%@page import="java.util.Date"%>
<%@page import="java.sql.*"%>
<%@page
	import="com.huawei.tsdn.sampleapp.util.GlobalResourceLoaderServlet"%>
<%@page
	import="com.huawei.tsdn.sampleapp.ethsrv.model.ServiceDataStruct"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	Class.forName("com.mysql.jdbc.Driver");
	Connection conn1 = DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/tsdn", "root", "828039");
	String id = request.getParameter("id");
	String sql = "select * from calendar where id = " + id;
	ResultSet rs = null;//数据库查询结果集
	Statement stmt = null;
	stmt = conn1.createStatement();
	rs = stmt.executeQuery(sql);
	rs.last();

	String http = GlobalResourceLoaderServlet.getServerAddress();
%>
<style>
#mytable {
	width: 100%;
	font-size: 16px;
	font-weight: bold;
}

#mytable td {
	/*border:10px;*/
	padding: 5px;
	/*margin:10px ;*/
}

#mytable {
	background: #0xfff;
}

table, td, th {
	border-collapse: collapse;
	border: 1px solid;
}

select option {
	background-color: #6687a6;
}

input, select, .tran, .areatext, .more {
	background-color: transparent;
	border: 1;
	/*height:100%;*/
}
/*input[type="submit"],
input[type="reset"],*/
input[type="button"] {
	height: 20px;
	width: 60;
	border: 1;
	background-color: rgba(255, 255, 255, 0.5);
	color: #000000;
}
</style>
<link rel="stylesheet" type="text/css"
	href="/TSDN_Application/css/jquery-ui.css">
<div class="fancy" style="width:100%;height:100%">
	<h3>以太网专线业务预订情况：</h3>
	<form id="add_form" action="eth_createTunnel.action" method="post">
		<input type="hidden" name="action" value="add">
		<table id="mytable">
			<tr>
				<td>SNC-T IP</td>
				<td><input type="text" name="httpAddr" id="httpAddr"
					style="width:100%" value="<%=http%>" readonly></td>
			</tr>
			<tr>
				<td width="40%">Service Name</td>
				<td width="60%"><input type="text" name="serviceName"
					id="serviceName" style="width: 100%"
					value="<%=rs.getString("title")%>" readonly></input></td>
			</tr>
			<tr>
				<td>Start Time</td>

				<td><input type="text" name="start" id="start"
					style="width:100%" value="<%=rs.getString("start")%>" readonly></td>
			</tr>
			<tr>
				<td>End Time</td>

				<td><input type="text" name="end" id="end" style="width:100%"
					value="<%=rs.getString("end")%>" readonly></td>
			</tr>
			<tr>
				<td>Source Node</td>

				<td><input type="text" name="ingress" id="ingress"
					style="width:100%" value="<%=rs.getString("ingress")%>" readonly></td>
			</tr>
			<tr>
				<td>Source Port</td>
				<td><input type="text" name="srcPort" id="srcPort"
					style="width:100%" value="<%=rs.getString("srcPort")%>" readonly></td>
			</tr>
			<tr>
				<td>Sink Node</td>

				<td><input type="text" name="egress" id="egress"
					style="width:100%" value="<%=rs.getString("egress")%>" readonly></td>

			</tr>
			<tr>
				<td>Sink Port</td>
				<td><input type="text" name="distPort" id="distPort"
					style="width:100%" value="<%=rs.getString("distPort")%>" readonly></td>
			</tr>
			<tr>
				<td>Bandwidth(Mbps)</td>
				<td><input type="text" name="bandWidth" id="bandWidth"
					value="<%=rs.getString("bandWidth")%>" style="width: 100%" readonly></input></td>
			</tr>
			<tr>
				<td>SLA</td>
				<td><input type="text" name="serviceType" id="serviceType"
					style="width:100%" value="<%=rs.getString("serviceType")%>" readonly></td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	$(function() {
		$(".datepicker").datepicker();
		$("#isallday").click(function() {
			if ($("#sel_start").css("display") == "none") {
				$("#sel_start,#sel_end").show();
			} else {
				$("#sel_start,#sel_end").hide();
			}
		});

		$("#isend").click(function() {
			alert("coming_isend!!");
			if ($("#p_endtime").css("display") == "none") {
				$("#p_endtime").show();
			} else {
				$("#p_endtime").hide();
			}
			alert("coming_resize!!");
			$.fancybox.resize();//调整高度自适应
		});

		//提交表单
		$('#add_form').ajaxForm({
			beforeSubmit : showRequest, //表单验证
			success : showResponse
		//成功返回
		});
	});

	function showRequest() {
		var events = $("#event").val();
		if (events == '') {
			alert("请输入日程内容！");
			$("#event").focus();
			return false;
		}
	}

	function showResponse(responseText, statusText, xhr, $form) {
		if (statusText == "success") {
			if (responseText == 1) {
				$.fancybox.close();
				$('#calendar').fullCalendar('refetchEvents'); //重新获取所有事件数据
			} else {
				alert(responseText);
			}
		} else {
			alert(statusText);
		}
	}

	function createEthServiceByCalendar() {
		$.post("eth_createTunnelByCalendar.action", {
			httpAddr : $("#httpAddr").val(),
			"serviceData.ServiceName" : $("#ServiceName").val(),
			"serviceData.ingress" : $("#ingress").val(),
			"serviceData.srcPort" : $("#srcPort").val(),
			"serviceData.egress" : $("#egress").val(),
			"serviceData.distPort" : $("#distPort").val(),
			"serviceData.bandWidth" : $("#bandWidth").val(),
			"serviceData.serviceType" : $("#serviceType").val()
		}, function(data) {
			self.location = 'bod_view.action';

		});
	}
</script>

