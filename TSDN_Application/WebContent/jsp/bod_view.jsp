<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="java.sql.*"%>

<%
Class.forName("com.mysql.jdbc.Driver");
Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/tsdn","root","828039");
response.setContentType("textml;charset=utf-8");//设置请求以及响应的内容类型(html)(内容类型可以包括字符编码说明)
request.setCharacterEncoding("utf-8");
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String sql = "select * from calendar";
	ResultSet rs = null;//数据库查询结果集
	Statement stmt = null;
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	stmt = (Statement)conn.createStatement();
	try
	{
		rs=(ResultSet)stmt.executeQuery(sql);
	}catch(SQLException ex)
	{
   		System.err.println("数据库查错误:"+ex);
	}
	ResultSetMetaData metaData = rs.getMetaData();
	//这段代码从calendar中读取事件并生成json数据，在下面显示出来
	int cols_len = metaData.getColumnCount();
	while(rs.next()){
		Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = rs.getObject(cols_name);
					if (cols_value == null) {
						cols_value = "";
					}
					map.put(cols_name, cols_value);
			}
			
			list.add(map);
			
		}
	System.out.println("list-->"+list);
	
	JSONArray data = JSONArray.fromObject(list);
	System.out.println("data-->"+data);
%>
<html lang="en">
<head>

    <meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">

    <title>T-SDN BoD</title>

  <!--
   	<script src="/TSDN_Application/js/jquery-1.9.1.js"></script>-->
   
 	<script src='/TSDN_Application/js/jquery.min.js'></script>
    <script src="/TSDN_Application/js/bootstrap.min.js"></script>
	<!-- Add jQuery library -->
	


	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="/TSDN_Application/js/jquery.fancybox.js?v=2.1.5"></script>
	
	
    <script src="/TSDN_Application/js/plugins/morris/raphael.min.js"></script>
    <script src="/TSDN_Application/js/plugins/morris/morris.min.js"></script>
    <script src="/TSDN_Application/js/plugins/morris/morris-data.js"></script>
 
    

	<script src='/TSDN_Application/js/moment.min.js'></script>
	
	<script src='/TSDN_Application/js/fullcalendar.min.js'></script>
	<!--  <script src='/TSDN_Application/js/jquery.fancybox-1.3.1.pack.js'></script>-->
	
	

	<link href="/TSDN_Application/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/TSDN_Application/css/sb-admin.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="/TSDN_Application/css/plugins/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/TSDN_Application/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
	
	<link href='/TSDN_Application/css/fullcalendar.css' rel='stylesheet' />
	<link href='/TSDN_Application/css/fullcalendar.print.css' rel='stylesheet' media='print' />
	<link rel="stylesheet" type="text/css" href="http://www.helloweba.com/demo/css/main.css">
	<link rel="stylesheet" type="text/css" href="/TSDN_Application/css/fullcalendar.css">
	<link rel="stylesheet" type="text/css" href="/TSDN_Application/css/jquery.fancybox.css?v=2.1.5" media="screen" />
<style>

	body {
		margin: 40px 10px;
		padding: 0;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		font-size: 14px;
	}
	
	


#calendar{width:100%; height:100%;margin:20px auto 10px auto}
.fancy{width:450px; height:370px}
.fancy h3{height:30px; line-height:30px; border-bottom:1px solid #d3d3d3; font-size:14px}
.fancy form{padding:10px}
.fancy p{height:28px; line-height:28px; padding:4px; color:#999}
.input{height:20px; line-height:20px; padding:2px; border:1px solid #d3d3d3; width:100px}
.btn{-webkit-border-radius: 3px;-moz-border-radius:3px;padding:5px 12px; cursor:pointer}
.btn_ok{background: #360;border: 1px solid #390;color:#fff}
.btn_cancel{background:#f0f0f0;border: 1px solid #d3d3d3; color:#666 }
.btn_del{background:#f90;border: 1px solid #f80; color:#fff }
.sub_btn{height:32px; line-height:32px; padding-top:6px; border-top:1px solid #f0f0f0; text-align:right; position:relative}
.sub_btn .del{position:absolute; left:2px}

</style>


</head>

<body>

    <div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">T-SDN APP</a>
            </div>
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="bod.jsp"><i class="fa fa-fw fa-user"></i> Profile</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li class="active">
                        <a href="ethSummary.action"><i class="fa fa-fw fa-dashboard"></i> Calendar</a>
                    </li>
                    <li>
                        <a href="bod_config.action" data-toggle="collapse"
						data-target="#menu"><i class="fa fa-fw fa-caret-down"></i> Topo</a>
                        <ul id="menu" class="collapse">
                            <li>
                                <a href="#">Create Tunnel</a>
                            </li>
                            <li>
                                <a href="#">Query Tunnel</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="createTunnelByCalendar.action?a=1"><i class="fa fa-fw fa-table"></i> Tables</a>
                    </li>
                    <li>
                        <a href="forms.html"><i class="fa fa-fw fa-edit"></i> Forms</a>
                    </li>
                    <li>
                        <a href="bootstrap-elements.html"><i class="fa fa-fw fa-desktop"></i> Bootstrap Elements</a>
                    </li>
                    <li>
                        <a href="bootstrap-grid.html"><i class="fa fa-fw fa-wrench"></i> Bootstrap Grid</a>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#demo"><i class="fa fa-fw fa-arrows-v"></i> Dropdown <i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="demo" class="collapse">
                            <li>
                                <a href="#">Dropdown Item</a>
                            </li>
                            <li>
                                <a href="#">Dropdown Item</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="blank-page.html"><i class="fa fa-fw fa-file"></i> Blank Page</a>
                    </li>
                    <li>
                        <a href="index-rtl.html"><i class="fa fa-fw fa-dashboard"></i> RTL Dashboard</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>

        <div id="page-wrapper">
			<div class="container-fluid">
            <div id='calendar' style="margin-top:20px"></div>
			</div>
            <!-- /.container-fluid -->

        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->


</body>
<script>


	$(document).ready(function() {
		
		$('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay,listWeek'
			},
			
			navLinks: true, // can click day/week names to navigate views

			weekNumbers: true,
			weekNumbersWithinDays: true,
			weekNumberCalculation: 'ISO',

			editable: true,
			eventLimit: true, // allow "more" link when too many events
			events: <%=data%>,
			eventClick: function(calEvent, jsEvent, view) {
				$.fancybox({
					'hideOnContentClick' :   true,
    				'autoScale'          :   true,
					'type':'ajax',
					'href':'fancy_box.action?id='+calEvent.id  //flag 0 表示查看日程  1表示添加日程
					
				});
				
	    	},
	    	dayClick:function(date){
	    		
	    		$.fancybox({
					'hideOnContentClick' :   true,
    				'autoScale'          :   true,
					'type':'ajax',
					'href':'dayclick.action?flag=0'  //flag 0 表示查看日程  1表示添加日程
					
				});
	    	}
		});
		
	});
	
	function confirm(){
		self.location='ethSummary.action'; 
	}
</script>
</html>
