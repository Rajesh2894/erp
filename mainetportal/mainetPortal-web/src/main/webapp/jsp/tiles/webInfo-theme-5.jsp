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
<div class="container-fluid dashboard-page">
   <div class="col-sm-12" id="nischay">
      <h2><spring:message code="web.information" text="Web Information Manager"/></h2>
      <div class="widget">
         <div class="form-horizontal padding widget-content">
             <c:if test="${userSession.languageId eq 1}">
                <p>${userSession.organisation.ONlsOrgname}</p>
             </c:if>
             <c:if test="${userSession.languageId eq 2}">
                <p>${userSession.organisation.ONlsOrgnameMar}</p>
             </c:if>
         </div>
      </div>
   </div>
</div>
<hr/>