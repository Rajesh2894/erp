<%	response.setContentType("text/html; charset=utf-8"); %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.net.URLEncoder" %>

<style>
.nav>li>a {
    position: relative;
    display: block;
    padding: 10px 27px;
}
</style>
<ol class="breadcrumb">
<li class="breadcrumb-item"><a href="CitizenHome.html" title="Home"><i class="fa fa-home" aria-hidden="true"></i> Home</a></li>
<li class="breadcrumb-item"><a href="dashboard.html" title="Dashboard">Dashboard</a></li>
<li class="breadcrumb-item active">Aarthik Hal, Yuvaon ko Bal</li>

</ol>

<div class="container-fluid dashboard-page">
    <div class="container-fluid">
     <div class="col-sm-12" id="nischay">

  
 
<div class="dashboard-items">
<ul class="nav nav-pills" role="tablist">
  <li role="presentation" class="active"><a href="DashBoard.html" class="red-nischay" title="7  Nischay">7  Nischay </a></li>
  <li role="presentation"><a href="Administration.html" title="Administration"> Administration and Finance </a></li>
  <li role="presentation"><a href="HumanResource.html" title="Human Resource Department">HRD</a></li>
  <li role="presentation"><a href="Infrastructure.html" title="Infrastructure"> Infrastructure</a></li>
  <li role="presentation"><a href="Agriculture.html" title="Agriculture &amp; Allied"> Agriculture &amp; Allied </a></li>
  <li role="presentation"><a href="Welfare.html" title="Welfare">Welfare</a></li>
  <li role="presentation"><a href="ArtCulture.html" title="Art Culture and Tourism"> Art Culture and Tourism</a></li>
 </ul>
</div>
<iframe src="<spring:message code="dashboard.report.url"/><%= URLEncoder.encode(request.getAttribute("reportName").toString(),"UTF-8")%>/" style="height: 1000px;width: 100%;"></iframe>






</div>
</div>
</div>
