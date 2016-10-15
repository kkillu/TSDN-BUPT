<!DOCTYPE html>
<%@page import="org.w3c.dom.Document"%>
<%@page import="com.huawei.tsdn.sampleapp.util.GlobalResourceLoaderServlet" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String http = GlobalResourceLoaderServlet.getServerAddress(); 
%>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>T-SDN BoD</title>
<script type="text/javascript" src="/TSDN_Application/js/jquery-1.9.1.min.js"></script>
<!-- jQuery -->
<script src="/TSDN_Application/js/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="/TSDN_Application/js/bootstrap.min.js"></script>

<!-- Morris Charts JavaScript -->
<script type="text/javascript" src="/TSDN_Application/js/jquery.fancybox.js?v=2.1.5"></script>
<!-- Bootstrap Core CSS -->
<link href="/TSDN_Application/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="/TSDN_Application/css/sb-admin.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<link href="/TSDN_Application/css/plugins/morris.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="/TSDN_Application/font-awesome/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">

<link href='/TSDN_Application/css/fullcalendar.css' rel='stylesheet' />
<link href='/TSDN_Application/css/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='/TSDN_Application/js/moment.min.js'></script>
<script src='/TSDN_Application/js/excanvas.js'></script>
<script src='/TSDN_Application/js/fullcalendar.min.js'></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<style>
body {
	margin: 40px 10px;
	padding: 0;
	font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
	font-size: 14px;
}

#calendar {
	max-width: 900px;
	margin: 0 auto;
}

#mytable{
	width:100%;
	height:280px;	
	font-size:16px;
	font-weight:bold;
}
	
#mytable td{
	/*border:10px;*/
	padding:5px;
	/*margin:10px ;*/
}

#mytable{ 
	background:#0xfff;
}

table, td, th  {
  border-collapse:collapse;
  border:1px solid;
}
select option{
	background-color:#6687a6;
}
input,select,.tran,.areatext,.more{
	background-color:transparent;
	border:1;
	/*height:100%;*/
}
/*input[type="submit"],
input[type="reset"],*/
input[type="button"]{
	height:20px;
	width:80; 
	border:1;
	background-color:rgba(255,255,255,0.5);
	color:#000000;
}

.ok{
	width:48%; 
	height:30px;
	float:left;
	background:#a2a2a2;
	/*background-color:rgba(42,42,42,0.8);*/
	background-color:rgba(255,255,255,0.5);
	color:#000000;
	font-size:16px;
	margin-top:2;
	padding:2; 
}
#mytable{ 
	background:#0xfff;
}
.hide{
	display:none 
}
.progress{
	z-index: 2000;
	float:right; 
	position: relative; 
	left: 40%;  
	top: 40%;  	
}
.mask{position: fixed;top: 0;right: 0;bottom: 0;left: 0; z-index: 1000; background-color: #000000}
.popWindow{
			color:black;
			float:right; 
			position: absolute; 
			left: 40%;  
			top: 40%;  
			width:300px; 
			height:110px;
			background:#FFFFFF;
			color:#000000;
			z-index: 1001;
 } 
 
 .img{
	width:200px; 
	height:100px;
	float:left; 
	/*background:url('images/line.png');/*/
	margin-top:10%;
	margin-left:10%;
	/*border: 10 80;*/
}
.cancel{
	width:48%; 
	height:30px;
	float:right;
	background:#a2a2a2;
	background-color:rgba(255,255,255,0.5);
	color:#000000;
	font-size:16px;
	margin-top:2;
	padding:2; 
}
.more{
	width:100%;
	height:16px;
	writing-mode：lr-tb;
	background: #656567;;
    /* height: 100%; */
	/*background:#0500ff;*/
	border:1px;
	/*color:#fff;*/
	background-image:url('images/accordion_collapse.png');/* no-repeat ;-16px 0;*/
	background-repeat:no-repeat;
	text-align: center;
	/*margin:auto  ;*/
	background-position:center 0;/*center center;*/

}
.more1{
	width:100%;
	height:16px;
	writing-mode：lr-tb;
	background:#0500ff;
	border:1px;
	color:#fff;
	background:url('../images/layout_arrows.png') no-repeat -16px -16px;
	background-repeat:no-repeat;
	text-align: center;
	/*margin:auto  ;*/
	/*background-position:center 0;center center;*/

}
</style>

	<link rel="stylesheet" type="text/css" href="/TSDN_Application/css/jquery.fancybox.css?v=2.1.5" media="screen" />
</head>
<script type="text/javascript">
	function refresh(url) {
		if (url == null)
			window.location.reload(true);
		else
			window.location.href = url;
	}
</script>
<body>
	<input type="hidden" id="hidCfgFileCnt" value="${cfgFileContent}" />
	<input type="hidden" id="ethTunnelsCnt" value="${ethTunnelsCnt}" />
	<script type="text/javascript">
		$(document).ready(function() {
			/*
			 *  Simple image gallery. Uses default settings
			 */

			$('.fancybox').fancybox();
			});
		var ne_id_arr = new Array();
		var postion = new Array();

		window.onload = load;

		function load() {
			var url;

			url = "eth_getNeConfig.action";
			startQuery(url);
			onResize();
		}

		//modify node port by node neId
		function selectchange(value, id) {
			
			$.each(configNodeList, function(i, nodeObj) {
				
				if (nodeObj.neId == value) {
					
					var selectObj = document.getElementById(id);
					selectObj.options.length = 0;
				    
					$.each(nodeObj.portList, function(j, portObj) {
						
						var option = document.createElement("option");
						option.value = portObj.port;
						option.innerHTML = portObj.port;
						selectObj.appendChild(option);
					});
				}
				
				 
			});
			
			if ("distPort" == id){
				$("#distPort").attr("value","0-3-2");
			}
		}

		//create xmlHttp object
		var xmlHttp;
		function createXMLHttpRequest() {
			if (window.ActiveXObject) {
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			} else if (window.XMLHttpRequest) {
				xmlHttp = new XMLHttpRequest();
			}
		}

		//get the server config
		function startQuery(url) {
			createXMLHttpRequest();
			xmlHttp.open("post", url, true);
			xmlHttp.send(null);
			xmlHttp.onreadystatechange = function() {
				if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
					querySuccess(xmlHttp.responseText);
				}
			}
		}
		function test(){
		self.location='bod_view.action'; 
		}
		
		
		function shouLoaderProgress() {
			var img = $("#progressImgage");
			var mask = $("#maskOfProgressImage");
			
     
			img.show().css({
				"top" : "50%",
				"left" : "45%",
				"margin-top" : function() {
					return -1 * img.height() / 2;
				},
				"margin-left" : function() {
					return -1 * img.width() / 2;
				}
			});
			mask.show().css("opacity", "0.1");
		}

		var configNodeList = null;
		//fill the config info into page
		function querySuccess(cnt) {

			var configData = $.parseJSON(cnt);
			configNodeList = configData.nodeList;
			var ingress = document.getElementById("ingress");
			var egress = document.getElementById("egress");

			createServiceNeList(ingress, configData.nodeList);
			createSinkServiceNeList(egress, configData.nodeList);

			selectchange(document.getElementById('ingress').value, 'srcPort');
			selectchange(document.getElementById('egress').value, 'distPort');
			mycan();
		}

		function createServiceNeList(serviceNodeLabel, nodeList) {
			serviceNodeLabel.options.length = 0;
			$.each(nodeList, function(i, nodeObj) {
				var option = document.createElement("option");
				option.value = nodeObj.neId;
				option.innerHTML = nodeObj.neName;
				serviceNodeLabel.appendChild(option);
			});
		}
		function createSinkServiceNeList(serviceNodeLabel, nodeList) {
			var selectIndex = 0;
			serviceNodeLabel.options.length = 0;
			$.each(nodeList, function(i, nodeObj) {
				var option = document.createElement("option");
				option.value = nodeObj.neId;
				option.innerHTML = nodeObj.neName;
				selectIndex++;
				if (selectIndex == 3){
					option.setAttribute("selected", "true");
				}
				
				serviceNodeLabel.appendChild(option);
			});
		}

		function queryEthService() {
			
			shouLoaderProgress();
			$.post("eth_execute.action", {
				httpAddr : $("#httpAddr").val()
			}, function(data) {
				 
				var img = $("#progressImgage");
				var mask = $("#maskOfProgressImage");
				img.hide();
				mask.hide();
				return;
			});
			 showResultInfo("Query service success");
		}
		
		function createEthService() {
			shouLoaderProgress();
			$.post("eth_createTunnel.action", {
				httpAddr : $("#httpAddr").val(),
				"serviceData.serviceName" : $("#serviceName").val(),
				"serviceData.ingress" : $("#ingress").val(),
				"serviceData.srcPort" : $("#srcPort").val(),
				"serviceData.egress" : $("#egress").val(),
				"serviceData.distPort" : $("#distPort").val(),
				"serviceData.bandWidth" : $("#bandWidth").val(),
				"serviceData.serviceType" : $("#serviceType").val()
			}, function(data) {
				
				 $.fancybox.open('<div style=margin-top:30%><p>'+data+'</p></div>');
				
				
			});

		}
		
		

		
		function modifyEthService()
		{
			shouLoaderProgress();
 
            var tableElement = document.getElementById("bustable");
			var size = tableElement.rows.length;
			var index = 0;
			var uuid ="";
			var selectnumber = 0;
			for (var i = 1; i < size; i++) {
				var checkbox = tableElement.rows[i].cells[8].firstChild;
				if (checkbox.checked == true) {
					index = i;
					uuid = checkbox.value ;
					selectnumber++;
				}
			}
			
			if (selectnumber == 0 || selectnumber > 1) {
				showResultInfo("Please select only one service.");
				return;
			}	
			
			if ((uuid == null) || (uuid == "")) {
				showResultInfo("Please select one service.");
                return;
			}
			var name = tableElement.rows[index].cells[1].firstChild.value; 
			var bw = tableElement.rows[index].cells[6].firstChild.value;
			
			var sla = tableElement.rows[index].cells[7].firstChild.value; 
			shouLoaderProgress();
			$.post("eth_modifyTunnel.action", {
				httpAddr : $("#httpAddr").val(),
				"serviceData.serviceName" : $("#serviceName").val(),
				"serviceData.serviceType" : $("#serviceType").attr("value"),
				"serviceData.uuid" : uuid,
				"serviceData.bandWidth" : $("#bandWidth").val()
			}, function(data) {
				showResultInfo(data);
			});
		}
		
		//delete the eth tunnel 
		function deleteEthService() {
			shouLoaderProgress();
			var selectArrText = "";
			var tableElement = document.getElementById("bustable");
			var size = tableElement.rows.length;
			if (size <= 1) {
				showResultInfo("Please select service.");
				return;
			}			
			
			var index = 0;
			for (var i = 1; i < size; i++) {

				var checkbox = tableElement.rows[i].cells[8].firstChild;
				if (checkbox.checked == true) {
					if (i != size - 1) {						
						selectArrText = selectArrText + checkbox.value + ",";
					} else {						
						selectArrText = selectArrText + checkbox.value;
					}
				}
			}
			
			if ((selectArrText == null) || (selectArrText == "")) {
				showResultInfo("Please select service.");
                return;
			}

			$.post("eth_deleteTunnel.action", {
				httpAddr : $("#httpAddr").val(),
				selectServiceString : selectArrText
			}, function(data) {
				showResultInfo(data);
				return;
			});
		}
		
		
		function deleteOneEthService(uuid) {
		  
			$.post("eth_deleteTunnel.action", {
				httpAddr : $("#httpAddr").val(),
				selectServiceString : uuid
			}, function(data) {
				showResultInfo(data);
				return;
			});
		}

		////////////////////////////////////////////////////
		window.OnResize = function() {
			onResize();
		}

		function quitPopWindow() {
			$("#maskOfProgressImage").hide();
			refresh("eth_getTunnelList.action");
		}

		function showResultInfo(resultString) {
			var img = $("#progressImgage");
			var mask = $("#maskOfProgressImage");
			img.hide();
			mask.hide();
			document.getElementById("popInfo").innerHTML = resultString;

			$("#maskOfProgressImage").show().css("opacity", "0.1");
			$("#popWindow").fadeIn("fast");

		}

		//modify the size
		function onResize() {
			var righth = document.body.clientHeight * 0.88 - document.getElementById("nav").offsetHeight - 16;
			document.getElementById("right").style.height = righth + "px";
			var areah = righth - document.getElementById("mytable").offsetHeight - 35;
			var areaWidth = $("#right1").width();
			var areaObj = document.getElementById("areatext");
			areaObj.style.height = areah + "px";
			areaObj.style.width = areaWidth + "px";
			mycan();
		}

		function checkAll() {
			var tableElement = document.getElementById("bustable");
			var size = tableElement.rows.length;
			for (var i = 1; i < size; i++) {
				tableElement.rows[i].cells[8].firstChild.checked = true;
			}
		}

		function invert() {
			var tableElement = document.getElementById("bustable");
			var size = tableElement.rows.length;
			for (var i = 1; i < size; i++) {
				var checkbox = tableElement.rows[i].cells[8].firstChild;
				checkbox.checked = !checkbox.checked;
			}
		}

		//get more info
		function clickMore() {
			document.getElementById("divwork").style.display = document
					.getElementById("divwork").style.display == "none" ? '' : 'none';
			updateTable();
			onResize();
		}

		//update table info
		function updateTable() {
			var nav = document.getElementById("nav");
			if (document.getElementById("divwork").style.display != "none") {
				var divh = document.getElementById("bustable").offsetHeight;//
				if (divh > 250) {
					divh = 250;
				}
				document.getElementById("divtable").style.height = divh;
				nav.style.height = divh + 16 + 24 + "px";
				document.getElementById('more').style.backgroundImage = "url(images/accordion_expand.png)";
			} else {
				nav.style.height = "16px";
				document.getElementById("divtable").style.height = 0;
				document.getElementById('more').style.backgroundImage = "url(images/accordion_collapse.png)";
			}
		}
		
		//writeToBodForm
		function writeToBodForm(uuid) {
			
			
			//var ethServiceCheck = document.getElementById("ethServiceCheck");
				
            var tableElement = document.getElementById("bustable");
			var size = tableElement.rows.length;
			if (size <= 1 || size > 2) {
				return;
			}			
			
			var index = 0;
			for (var i = 1; i < size; i++) {
				var checkbox = tableElement.rows[i].cells[8].firstChild;
				if (checkbox.checked == true && uuid == checkbox.value) {
					index = i;
					break;
				}
			}
			
			if (index == 0)
			{
				return ;
			}
			
			var t01 = $("#bustable tr:gt(0) input:checked").each(function() {
				var tr = $(this).closest('tr');
				$("#serviceName").val(tr.find('td')[1].innerHTML);
				var mytext = tr.find('td')[2].innerHTML;
				//$("#ingress option")
				/* $("#ingress option").each(function(){
					
					var option = $(this).closest('option');
					alert (option.text + "==" + mytext+ "===" + option.value);
					if (option.text == mytext) {
						$("#ingress").attr("value",option.value);
					}
				}); */
				//$("#ingress").attr("text","" + tr.find('td')[2].innerHTML)
				//$("#srcPort").attr("value",tr.find('td')[3].innerHTML) 
				//$("#egress").attr("text","" + tr.find('td')[4].innerHTML)
				mytext = tr.find('td')[4].innerHTML;
				/* $("#egress option").each(function(){
					var option = $(this).closest('option');
					alert (option.text + "==" + mytext + "===" + option.value);
					if (option.text == mytext) {
						$("#egress").attr("value",option.value);
					}
				}); */
				//$("#distPort").attr("value",tr.find('td')[5].innerHTML)
				$("#bandWidth").attr("value",tr.find('td')[6].innerHTML);
				$("#serviceType").val(tr.find('td')[7].innerHTML);
 
			})
				 
			
		}

		var osn9800 = new Image();
		osn9800.src = "images/9800_32x32.png";
		
		var osn8800 = new Image();
		osn8800.src = "images/8800_32x32.png";
		
		var videoplayer= new Image();
		videoplayer.src = "images/VideoPlayer_32x32.png";
		
		var videoserver = new Image();
		videoserver.src = "images/VideoServer_32x32.png";
		
		
		var top2 = new Image();
		top2.src = "images/top2.png";

		var neids = new Array();
		var tunnelList = new Array();

		var ethTunnels = "${ethTunnels}";

		<c:forEach var="ethTunnel" items="${ethTunnels}" varStatus="status">
		tunnelObj = new Object();
		tunnelObj.srvName = "${ethTunnel.srvName}";
		tunnelObj.bandWidth = "${ethTunnel.bandWidth}" + "mbps";
		tunnelObj.srcNeId = "${ethTunnel.srcTunnelPoint.neId}";
		tunnelObj.dstNeId = "${ethTunnel.distTunnelPoint.neId}";
		tunnelList["${status.index}"] = tunnelObj;
		</c:forEach>
		// env node and link
		var envNode = [
  		     { 
 				neId : 12,
 				neName : 'OSN8800'
 			 }, { 
	 				neId : 11,
	 				neName : 'OSN8800'
	 		 }, { 
	 				neId : 10,
	 				neName : 'OSN8800'
	 		 }];

		var videoLink = [ 
			
			 { 
 				neId : 20,
 				neName : 'VideoServer'
 			 }, { 
 				neId : 21,
 				neName : 'VideoPlay'
 			 } 
		    ];
		
	 
		function mycan() {
			
			if (configNodeList == null) {
				return;
			}
			var nodeLen = eval(configNodeList).length;
			$.each(configNodeList, function(i, tempNode) {
				nodeObj = new Object();
				nodeObj.neId = tempNode.neId;
				nodeObj.neName = tempNode.neName;
				neids[i] = nodeObj;
			});

			//get the canvas of page
			var canvas = document.getElementById('myCanvas');
			if (!canvas.getContext) {
				alert("your browser is not supported, please use other browser.")
			}
			var ctx = canvas.getContext('2d');

			canvas.width = canvas.offsetWidth;
			canvas.height = canvas.offsetHeight;
			var cenx = canvas.width * 0.5 - 35;
			var ceny = canvas.height * 0.5 + 5;
			var rad = ((cenx < ceny ? cenx : ceny)) - 50;

			if (nodeLen == 0) {
				return;
			}

			var i, j;
			//calculate coordinate of node
			for (i = 0 ; i < nodeLen ;  i++) {
				
				neids[nodeLen - i - 1].xAxis = cenx + (rad+20)	* Math.cos(i * (2 * Math.PI) / nodeLen);
				neids[nodeLen - i - 1].yAxis = ceny + (rad+20)	* Math.sin(i * (2 * Math.PI) / nodeLen); 
			}
			
			rad = rad -180;
			var envNodeLen = eval(envNode).length;
			for (i = 0; i < eval(envNode).length; i++) {
				envNode[i].xAxis = cenx  + (rad+60)* Math.cos(i * (2 * Math.PI) / envNodeLen);
				envNode[i].yAxis = ceny  + (rad+60)* Math.sin(i * (2 * Math.PI) / envNodeLen);
	 
			}
			
			videoLink[0].xAxis = cenx  + (220)* Math.cos( (2) * (2 * Math.PI) / (envNodeLen+1));
			videoLink[0].yAxis = ceny  + (200)* Math.sin( (2)* (2 * Math.PI) /  (envNodeLen+1));
			videoLink[1].xAxis = cenx  + (200)* Math.cos( (1) * (1 * Math.PI) / (envNodeLen+2));;
			videoLink[1].yAxis = ceny  + (200)* Math.sin( (1)* (1 * Math.PI) /  (envNodeLen+2));
			
			

			for (i = 0; i < tunnelList.length; i++) {
				for (j = 0; j < nodeLen; j++) {
					if (neids[j].neId == tunnelList[i].srcNeId) {
						tunnelList[i].srcXAxis = neids[j].xAxis;
						tunnelList[i].srcYAxis = neids[j].yAxis;
					}
					if (neids[j].neId == tunnelList[i].dstNeId) {
						tunnelList[i].dstXAxis = neids[j].xAxis;
						tunnelList[i].dstYAxis = neids[j].yAxis;
					}
				}
			}

			//draw line
			ctx.beginPath();
			ctx.lineWidth = 3;
			ctx.strokeStyle = '#00aa00';
			for (i = 0; i < tunnelList.length; i++) {
				ctx.moveTo(tunnelList[i].srcXAxis, tunnelList[i].srcYAxis);
				ctx.lineTo(tunnelList[i].dstXAxis, tunnelList[i].dstYAxis);
			}

			ctx.stroke();
			ctx.closePath();

			ctx.beginPath();
			ctx.strokeStyle = '#DAA520';
			ctx.lineWidth = 1;
			ctx.moveTo(envNode[0].xAxis, envNode[0].yAxis);
			ctx.lineTo(envNode[1].xAxis, envNode[1].yAxis);
			
			ctx.moveTo(envNode[1].xAxis, envNode[1].yAxis);
			ctx.lineTo(envNode[2].xAxis, envNode[2].yAxis);
			
			ctx.moveTo(envNode[2].xAxis, envNode[2].yAxis);
			ctx.lineTo(envNode[0].xAxis, envNode[0].yAxis);

			ctx.moveTo(envNode[0].xAxis, envNode[0].yAxis);
			ctx.lineTo(neids[2].xAxis, neids[2].yAxis);
			
			ctx.moveTo(envNode[2].xAxis, envNode[2].yAxis);
			ctx.lineTo(neids[0].xAxis, neids[0].yAxis);
			
			ctx.moveTo(envNode[1].xAxis, envNode[1].yAxis);
			ctx.lineTo(neids[1].xAxis, neids[1].yAxis);
			
			ctx.stroke();
			ctx.closePath();

			
			ctx.beginPath();
			ctx.lineWidth = 2;
			ctx.strokeStyle = '#00aa00';
			
			ctx.moveTo(videoLink[0].xAxis, videoLink[0].yAxis);
			ctx.lineTo(neids[0].xAxis, neids[0].yAxis);
			
			ctx.moveTo(videoLink[1].xAxis, videoLink[1].yAxis);
			ctx.lineTo(neids[2].xAxis, neids[2].yAxis);
			
			ctx.stroke();
			ctx.closePath();
			
			//draw the image
			for (i = 0; i < nodeLen; i++) {
				
				ctx.drawImage(osn9800, neids[i].xAxis - 25, neids[i].yAxis - 23, 40, 37);
			}
			
			

			//write neName in the bottom of image
			ctx.fillStyle = '#ffffff';
			ctx.font = '12px verdana';
			for (i = 0; i < nodeLen; i++) {
				ctx.fillText(neids[i].neName, neids[i].xAxis - 23,
						neids[i].yAxis + 45);
			}
			
			//draw the env
			for (i = 0; i <  eval(envNode).length; i++) {
				ctx.drawImage(osn8800, envNode[i].xAxis - 25, envNode[i].yAxis - 23, 40, 37);
				//alert (envNode[i].neName + "==" + envNode[i].xAxis + "==" + envNode[i].yAxis);
			}
			for (i = 0; i < eval(envNode).length; i++) {
				ctx.fillText(envNode[i].neName, envNode[i].xAxis - 25, envNode[i].yAxis +35);
			}
			
			// draw videoserver play and server
			ctx.drawImage(videoserver, videoLink[0].xAxis - 25, videoLink[0].yAxis , 40, 37);
			ctx.drawImage(videoplayer, videoLink[1].xAxis - 25, videoLink[1].yAxis , 40, 37);
			ctx.fillText(videoLink[0].neName, videoLink[0].xAxis - 25, videoLink[0].yAxis +45);
			ctx.fillText(videoLink[1].neName, videoLink[1].xAxis - 25, videoLink[1].yAxis +45); 
			 

			ctx.fillStyle = '#00ffff';
			for (i = 0; i < tunnelList.length; i++) {
				ctx.fillText(tunnelList[i].bandWidth,
								(tunnelList[i].srcXAxis + tunnelList[i].dstXAxis) * 0.5,
								(tunnelList[i].srcYAxis + tunnelList[i].dstYAxis) * 0.5);
			}
		}
	</script>
	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.html">T-SDN APP</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b
						class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
						</li>
						<li><a href="#"><i class="fa fa-fw fa-envelope"></i>
								Inbox</a></li>
						<li><a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
						</li>
						<li class="divider"></li>
						<li><a href="#"><i class="fa fa-fw fa-power-off"></i> Log
								Out</a></li>
					</ul></li>
			</ul>
			<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
			<div class="collapse navbar-collapse navbar-ex1-collapse">
				<ul class="nav navbar-nav side-nav">
					<li><a href="ethSummary.action"><i class="fa fa-fw fa-dashboard"></i>
							Dashboard</a></li>
					<li class="active">
                        <a href="javascript:;"  data-toggle="collapse"
						data-target="#demo"><i class="fa fa-fw fa-caret-down"></i> Topo</a>
                        <ul id="demo" class="collapse in">
                            <li>
                                <a class="fancybox" href="#inline1">Create Tunnel</a>
                            </li>
                            <li>
                                <a href="#">Query Tunnel</a>
                            </li>
                            
                        </ul>
                    </li>
					<li><a href="jsp/event.jsp"><i class="fa fa-fw fa-table"></i>
							Tables</a></li>
					<li><a href="forms.html"><i class="fa fa-fw fa-edit"></i>
							Forms</a></li>
					<li><a href="bootstrap-elements.html"><i
							class="fa fa-fw fa-desktop"></i> Bootstrap Elements</a></li>
					<li><a href="bootstrap-grid.html"><i
							class="fa fa-fw fa-wrench"></i> Bootstrap Grid</a></li>
					<li><a href="javascript:;" data-toggle="collapse"
						data-target="#demo"><i class="fa fa-fw fa-arrows-v"></i>
							Dropdown <i class="fa fa-fw fa-caret-down"></i></a>
						<ul id="demo" class="collapse">
							<li><a href="#">Dropdown Item</a></li>
							<li><a href="#">Dropdown Item</a></li>
						</ul></li>
					<li><a href="blank-page.html"><i class="fa fa-fw fa-file"></i>
							Blank Page</a></li>
					<li><a href="index-rtl.html"><i
							class="fa fa-fw fa-dashboard"></i> RTL Dashboard</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</nav>

		<div id="page-wrapper_1">
			<div class="container-fluid ">
				<div>
					<h2 id="titleh2" style="color:white">T-SDN BoD</h2>
				</div>
				<div id="left"  style="width:100%;float:left">
					<canvas id="myCanvas" style="width:100%;" height="500px"></canvas>
				</div>
				
				<div id="nav1">
					<button id="more" class="more" type="button" onclick="clickMore();">
					</button>
					<div id="divwork" style="display: none;">
						<div style="text-align: center; height: 24px;">
							Packet BOD Service
							<div style="text-align: right; margin-top: -18px; height: 24px;">
								<input type="button" id="refresh" class="all" onclick='refresh("ethSummary.action");' value="Refresh" /> 
								<input type="button" id="all" class="all" onclick="checkAll();" value="Select All" /> 
								<input type="button" class="invert" onclick="invert();" value="Deselect" /> 
								<input type="button" class="delete" onclick="deleteEthService();" value="Delete" />
								<!--@TODO  remove the comment , to active the function of modify tunnel  -->
								<!-- <input type="button" class="all" onclick="modifyEthService();" value="Modify" />  -->
							</div>
						</div>
						<div id="divtable" style="overflow: auto;">

							<table id="bustable" width="100%" border="1" cellspacing="0">
								<tr height="24px">
									<th>No.</th>
									<th>Service Name</th>
									<th>Service Source Node</th>
									<th>Source Port</th>
									<th>Sink Node</th>
									<th>Sink Port</th>
									<th>Bandwidth(Mbps)</th>
									<th>SLA</th>
									<th>Select</th>
								</tr>

								<c:forEach var="ethTunnel" items="${ethTunnels}"
									varStatus="status">
									<tr>
										<td>${status.index+1}</td>
										<td>${ethTunnel.srvName}</td>
										<td>${ethTunnel.srcTunnelPoint.neName}</td>
										<td>${ethTunnel.srcTunnelPoint.portIdStr}</td>
										<td>${ethTunnel.distTunnelPoint.neName}</td>
										<td>${ethTunnel.distTunnelPoint.portIdStr}</td>
										<td>${ethTunnel.bandWidth}</td>
										<td>${ethTunnel.sla==1?"Diamond":(ethTunnel.sla==2?"Silver":"Copper")}</td>
										<td><input type="checkbox" id="ethServiceCheck" value="${ethTunnel.uuid}" onclick="writeToBodForm(${ethTunnel.uuid});" /></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</div>
				<div id="popWindow" class="popWindow hide">
					<div style="left: 0">
						<i><img src="images/info.PNG" width="80px" height="68px"></i>
					</div>
					<div id="popInfo"
						style="left: 80px; font-size: 18px; color: black; width: 200px; height: 50px; position: absolute; top: 15%;"></div>
					<div
						style="width: 100%; height: 40px; bottom: 0; position: absolute; background: #C0D9D9"></div>
					<input type="button" value="ok" style="width: 20%; height: 30px; position: absolute; right: 3; bottom: 3%;'%" onclick="quitPopWindow()" />
				</div>
				<div class="progress">
					<i><img id="progressImgage" class="progress hide" width="100px" height="100px" alt="" src="/TSDN_Application/images/ajax_loader.gif" /></i>
				</div>
				<div id="maskOfProgressImage" class="mask hide"></div>
			</div>
			<!-- /.container-fluid -->

		</div>
		<!-- /#page-wrapper -->

	</div>

	<div id="inline1" style="width:400px;display: none;">
		<form name="bodForm" method="post" action="eth_createTunnel.action" onsubmit="return checkForm();">

						<table id="mytable">
							<tr>
								<td>SNC-T IP</td>
								<td><input type="text" name="httpAddr" id="httpAddr"
									style="width:100%"  value="<%=http%>"></td>
							</tr>
							<tr>
								<td width="40%">Service Name</td>
								<td width="60%"><input type="text" name="serviceName"
									id="serviceName" style="width: 100%" value="BoD_Eth_Service"></input></td>
							</tr>
							<tr>
								<td>Source Node</td>

								<td><select name="ingress" id="ingress" style="width: 100%"
									onchange="selectchange(document.getElementById('ingress').value,'srcPort');">
								</select></td>
							</tr>
							<tr>
								<td>Source Port</td>
								<td><select name="srcPort" id="srcPort" style="width: 100%"></select>
								</td>
							</tr>
							<tr>
								<td>Sink Node</td>

								<td><select name="egress" style="width: 100%" id="egress"
									onchange="selectchange(document.getElementById('egress').value,'distPort');">

								</select></td>

							</tr>
							<tr>
								<td>Sink Port</td>
								<td><select name="distPort" id="distPort"
									style="width: 100%"></select></td>
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
						</table>


						<input type="button" class="ok" value="CreateEthService" style="width: 50%; height: 30px;" onclick="createEthService()" />

						<!-- @TODO modify yuehuaming -->
						<input type="button" class="ok" value="QueryEthService" style="width: 50%; height: 30px;" onclick="refresh('ethSummary.action');" />

						<!-- input
				type="reset" class="cancel" value="Cancel" /-->
					</form>
	</div>
<div id="data" class="fancybox" ></div>
</body>



</html>
