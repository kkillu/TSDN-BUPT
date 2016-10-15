<%@page import="java.util.Date"%>
<%@page import="java.sql.*"%>
<%@page
	import="com.huawei.tsdn.sampleapp.util.GlobalResourceLoaderServlet"%>
<%@page
	import="com.huawei.tsdn.sampleapp.ethsrv.model.ServiceDataStruct"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String http = GlobalResourceLoaderServlet.getServerAddress();
%>

<script language="javascript" type="text/javascript" src="/TSDN_Application/js/WdatePicker.js">  </script>
<style>
#mytable {
	width: 100%;
	height: 280px;
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
	width: 80;
	border: 1;
	background-color: rgba(255, 255, 255, 0.5);
	color: #000000;
}
</style>
<link rel="stylesheet" type="text/css"
	href="/TSDN_Application/css/jquery-ui.css">
<div class="fancy">
	<h3>请按需预订您所需专线业务：</h3>
	<form id="add_form" action="createTunnelByCalendar.action" method="post">
		<input type="hidden" name="action" value="add">
		<table id="mytable">
			<tr>
				<td>SNC-T IP</td>
				<td><input type="text" name="httpAddr" id="httpAddr"
					style="width:100%" value="<%=http%>"></td>
			</tr>
			<tr>
				<td width="40%">Service Name</td>
				<td width="60%"><input type="text" name="ServiceName"
					id="ServiceName" style="width: 100%" value="BoD_Eth_Service"></input></td>
			</tr>
			<tr>
				<td>Source Node</td>

				<td><select name="ingress" id="ingress" style="width: 100%"
					onchange="selectchange(document.getElementById('ingress').value,'srcPort');">
						<option value="589855">OSN9800[E_1]</option>
						<option value="589857">OSN9800[E_2]</option>
						<option value="589859">OSN9800[E_3]</option>
				</select></td>
			</tr>
			<tr>
				<td>Source Port</td>
				<td><select name="srcPort" id="srcPort" style="width: 100%">
						<option value="0-3-1">0-3-1</option>
						<option value="0-3-2">0-3-2</option>
				</select></td>
			</tr>
			<tr>
				<td>Sink Node</td>

				<td><select name="egress" style="width: 100%" id="egress"
					onchange="selectchange(document.getElementById('egress').value,'distPort');">
						<option value="589855">OSN9800[E_1]</option>
						<option value="589857">OSN9800[E_2]</option>
						<option value="589859">OSN9800[E_3]</option>
				</select></td>

			</tr>
			<tr>
				<td>Sink Port</td>
				<td><select name="distPort" id="distPort" style="width: 100%">
						<option value="0-3-1">0-3-1</option>
						<option value="0-3-2">0-3-2</option>
				</select></td>
			</tr>
			<tr>
				<td>Bandwidth(Mbps)</td>
				<td><input type="text" name="bandWidth" id="bandWidth"
					value="100" style="width: 100%"></input></td>
			</tr>
			<tr>
				<td>SLA</td>
				<td><select name="serviceType" id="serviceType"
					style="width: 100%">
						<option value="3">Copper</option>
						<option value="2">Silver</option>
						<option value="1">Diamond</option>
				</select></td>
			</tr>
			<tr>
				<td>start</td>
				<td><input id="start" type="text" /> <img
					onclick="WdatePicker({el:'start',dateFmt:'yyyy-MM-dd HH:mm'})"
					src="/TSDN_Application/js/skin/datePicker.gif" width="16"
					height="22" align="absmiddle"></td>
			</tr>
			<tr>
				<td>end</td>
				<td><input id="end" type="text" /> <img
					onclick="WdatePicker({el:'end',dateFmt:'yyyy-MM-dd HH:mm'})"
					src="/TSDN_Application/js/skin/datePicker.gif" width="16"
					height="22" align="absmiddle"></td>
			</tr>
		</table>

		<div align="center"
			style="float:left;margin-left:100px;margin-top:20px;">
			<input type="button" value="Confirm"
				onClick="createEthServiceByCalendar()" style="height:30px">
		</div>
		<div align="center"
			style="float:left;margin-left:50px;margin-top:20px;">
			<input type="button" value="Cancel" onClick="$.fancybox.close()"
				style="height:30px">
		</div>
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
			start : $("#start").val(),
			end : $("#end").val(),
			"serviceData.ServiceName" : $("#ServiceName").val(),
			"serviceData.ingress" : $("#ingress").val(),
			"serviceData.srcPort" : $("#srcPort").val(),
			"serviceData.egress" : $("#egress").val(),
			"serviceData.distPort" : $("#distPort").val(),
			"serviceData.bandWidth" : $("#bandWidth").val(),
			"serviceData.serviceType" : $("#serviceType").val()
		}, function(data) {
			location.reload();

		});
	}
</script>

