<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<a id="CitizenService"></a>
<ol class="breadcrumb">
   <c:if
      test="${empty userSession.employee.emploginname or userSession.employee.emploginname eq'NOUSER' }"
      var="user">
      <li><a href="CitizenHome.html"><i class="fa fa-home"></i> Home </a></li>
   </c:if>
   <c:if
      test="${ !empty userSession.employee.emploginname and userSession.employee.emploginname ne'NOUSER' }"
      var="user">
      <li><a href="CitizenHome.html"><i class="fa fa-home"></i> Home </a></li>
   </c:if>
   <li>
      <spring:message code="web.information" text="Web Information Manager"/>
   </li>
</ol>
<div class="content">
   <div class="col-sm-12" id="nischay">
      
      <div class="widget">
		<div class="widget-header">
			<h2><spring:message code="web.information" text="Web Information Manager"/></h2>
		</div>
		<div class="widget-content padding">
			<div class="form-horizontal">
				<p>TMC Office</p>
			</div>
		</div>
      </div>
   </div>
</div>
<hr/>