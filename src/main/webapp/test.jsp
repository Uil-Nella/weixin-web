<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  		<h3 align="center">课程录入系统</h3>
    	<table align="center" bordercolor="blue" border="1">
    		<tr>
    			<td>学院名：</td>
    			<td>
    				<select>  
					  <option value ="1">经贸学院</option>  
					  <option value ="2">计工学院</option>  
					  <option value="3">控院</option>  
					  <option value="4">外语学院</option>  
					</select> 
    			</td>
    		</tr>
    		<tr>
    			<td>班级号：</td>
    			<td>
    				<select>  
					  <option value ="1">41001</option>  
					  <option value ="2">41002</option>  
					  <option value="3">41003</option>  
					  <option value="4">41004</option>  
					</select>
					<input type="button" value="添加">
				</td>
    		</tr>
    		<tr>
    			<td>周几</td>
    			<td>
    				<select>  
					  <option value ="1">周一</option>  
					  <option value ="2">周二</option>  
					  <option value ="3">周三</option>  
					  <option value ="4">周四</option>  
					  <option value ="5">周五</option>  
					</select>
    			</td>
    		</tr>
    		<tr>
    			<td>第几节课</td>
    			<td>
    				<select>  
					  <option value ="1">第一</option>  
					  <option value ="2">第二</option>  
					  <option value ="3">第三</option>  
					  <option value ="4">第四</option>  
					  <option value ="5">第五</option>  
					</select>
				</td>
    		</tr>
    		<tr>
    			<td>课程名</td>
    			<td>
    				<select>  
					  <option value ="1">语文</option>  
					  <option value ="2">数学</option>  
					  <option value ="3">英语</option>  
					  <option value ="4">体育</option>  
					</select>
					<input type="button" value="添加">
    			</td>
    		</tr>
    		<tr>
    			<td>老师名</td>
    			<td>
    				<select>  
					  <option value ="1">haha</option>  
					  <option value ="2">hehe</option>  
					  <option value ="3">xixi</option>  
					  <option value ="4">heihei</option>  
					</select>
					<input type="button" value="添加">
    			</td>
    		</tr>
    		<tr>
    			<td>上课周数</td>
    			<td>
    				<select>  
					  <option value ="1">12345678</option>  
					  <option value ="2">91011121314</option>  
					  <option value ="3">12345</option>  
					  <option value ="4">123456</option>  
					</select>
					<input type="button" value="添加">
    			</td>
    		</tr>
    		<tr>
    			<td>教室号</td>
    			<td>
    				<select>  
					  <option value ="1">G101</option>  
					  <option value ="2">G102</option>  
					  <option value ="3">G103</option>  
					  <option value ="4">G104</option>  
					</select>
					<input type="button" value="添加">
    			</td>
    		</tr>
    		<tr>
    			<td></td>
    			<td>
    				<input type="button" value="提交">
    			</td>
    		</tr>
    		
    	</table>
  </body>
</html>
