<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/audit/auditParaEntry.js"></script>
<div class="content" id="content">
 <div class="animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="audit.history.title" text="Audit Para History" /></h2>
			<div class="additional-btn"><a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
		</div>
		<div class="widget-content padding">
	  <form:form action="AuditParaEntry.html" class="form-horizontal" name="Audit Para History">
         <h4 class="margin-top-0"><spring:message text="audit.history.heading.referenceDetails" code="audit.history.heading.referenceDetails"/></h4>
		<div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="audit.history.desc"  text="Description" /> </label>
                <div class="col-sm-4">
                	<span class="form-control height-auto">${servName}</span>
                </div>
                <label class="col-sm-2 control-label"><spring:message text="Raised By" code="audit.history.raisedBy" /> </label> 
				<div class="col-sm-4">
					<span class="form-control">${raisedBy}</span>
				</div>
				</div>
				<div class="form-group">
	                <label class="col-sm-2 control-label"><spring:message text="Reference No." code="audit.history.refno" /> </label> 
					<div class="col-sm-4">
	                	<span class="form-control">${refId}</span>
					</div>
					<label class="col-sm-2 control-label"><spring:message text="Raised Date" code="audit.history.appdate" /> </label>
					<div class="col-sm-4">
					<c:set value="${appDate}" var="applicationDate"/>
					<span class="form-control">${appDate}</span>
							<%-- <span class="form-control"><fmt:formatDate type="date" value="${applicationDate}" pattern="dd/MM/yyyy" /></span> --%>
					</div>
				</div>
	    <h4 class="margin-top-0"><spring:message text="audit.history.heading.historyDetails" code="audit.history.heading.historyDetails"/></h4>
		<div id="ActionHistory" class="panel-collapse collapse in">
				<div class="table-responsive">
					<table class="table table-bordered table-condensed">
		  <tr>
		  	<th><spring:message code="audit.history.actions.srno" text="audit.history.actions.srno"/></th>
		    <th><spring:message code="audit.history.actions.datetime" text="audit.history.actions.datetime"/></th>
		    <th><spring:message code="audit.history.actions.taskName" text="audit.history.actions.taskName"/></th>
		    <th width="18%"><spring:message code="audit.history.actions.action" text="audit.history.actions.action"/></th>
		    <th><spring:message code="audit.history.actions.actor.name" text="audit.history.actions.actor.name"/></th>
		    <th><spring:message code="audit.history.actions.actor.email" text="audit.history.actions.actor.email"/></th>
		    <th><spring:message code="audit.history.actions.actor.designation" text="audit.history.actions.actor.designation"/></th>
		    <th width="20%"><spring:message code="audit.history.actions.remarks" text="audit.history.actions.remarks"/></th>
		    <th><spring:message code="care.attachments" text="Attachments"/></th>
		  </tr>
		  <c:set var="rowCount" value="0" scope="page" />
		  <c:forEach items="${actions}" var="action" varStatus="status">
               <tr>
                 <td ><c:set var="rowCount" value="${rowCount+1}" scope="page" /><c:out value="${rowCount}"></c:out></td>
                 <td >
                 	<fmt:formatDate pattern="dd/MM/yyyy hh:mm a" value="${action.dateOfAction}" />
                 </td>
                  <td><c:out value="${action.taskName}"></c:out></td>
                 <%-- <td><c:out value="${action.decision}"></c:out></td> --%>
                 <c:set var = "statusString" value = "${action.decision}"/>
		         <td><spring:message code="workflow.action.decision.${fn:toLowerCase(statusString)}" text="${action.decision}"/>
                 <td><c:out value="${action.empName}"></c:out></td>
                  <td><c:out value="${action.empEmail}"></c:out></td>
                  <td><c:out value="${action.empGroupDescEng}"></c:out></td>
                  <td><c:out value="${action.comments}"></c:out></td>
                  <td>
	                  <ul>
	                  <c:forEach items="${action.attachements}" var="lookUp" varStatus="status">
	                  <li><apptags:filedownload filename="${lookUp.lookUpCode}" dmsDocId="${lookUp.dmsDocId}" filePath="${lookUp.defaultVal}" actionUrl="AdminHome.html?Download"></apptags:filedownload></li>
	                  </c:forEach>
	                  </ul>
                  </td>
		          </tr>
		           </c:forEach>
			     </table>
				</div>
		 </div>

		 <div class="text-center clear padding-10">
		 <apptags:backButton url="AuditParaEntry.html"></apptags:backButton>
		 </div>
		</form:form>
		</div>
	</div>
	</div>
</div>