<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
	.textfielHeight{
		height:auto;
	}
</style>
<div class="content" id="content">
 <div class="animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="history" text="History" /></h2>
			<div class="additional-btn"><a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
		</div>
		<div class="widget-content padding">
	  <form:form class="form-horizontal" name="Application History">
         <h4 class="margin-top-0"><spring:message text="dashboard.heading.referenceDetails" code="dashboard.heading.referenceDetails"/></h4>
		<div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="dashboard.desc"  text="Description" /> </label>
                <div class="col-sm-4">
                	<span class="form-control height-auto">${servName}</span>
                </div>
                <label class="col-sm-2 control-label"><spring:message text="Raised By" code="dashboard.raisedBy" /> </label> 
				<div class="col-sm-4">
					<span class="form-control">${appName}</span>
				</div>
				</div>
				<div class="form-group">
	                <label class="col-sm-2 control-label"><spring:message text="Reference No." code="dashboard.refno" /> </label> 
					<div class="col-sm-4">
	                	<span class="form-control">${refId}</span>
					</div>
					<label class="col-sm-2 control-label"><spring:message text="Raised Date" code="dashboard.appdate" /> </label>
					<div class="col-sm-4">
					<c:set value="${appDate}" var="applicationDate"/>
					<span class="form-control">${appDate}</span>
							<%-- <span class="form-control"><fmt:formatDate type="date" value="${applicationDate}" pattern="dd/MM/yyyy" /></span> --%>
					</div>
				</div>
				
			    <div class="form-group">	
					<label class="col-sm-2 control-label"><spring:message text="Applicant Name" code="master.loi.applicant.name" /> </label>
					<div class="col-sm-10">
					<c:forEach items="${actions}" var="appName" varStatus="status" begin="0" end="0">	
						<span class="form-control textfielHeight">${appName.applicantName}</span>
					</c:forEach>
					</div>					
				</div> 
			
		<h4 class="margin-top-0"><spring:message text="dashboard.heading.historyDetails" code="dashboard.heading.historyDetails"/></h4>
		<div id="ActionHistory" class="panel-collapse collapse in">
				<div class="table-responsive">
					<table class="table table-bordered table-condensed">
		  <tr>
		  	<th><spring:message code="dashboard.actions.srno" text="dashboard.actions.srno"/></th>
		    <th><spring:message code="dashboard.actions.datetime" text="dashboard.actions.datetime"/></th>
		    <th><spring:message code="dashboard.actions.taskName" text="dashboard.actions.taskName"/></th>
		    <th width="12%"><spring:message code="dashboard.actions.action" text="dashboard.actions.action"/></th>
		    <th width="12%"><spring:message code="dashboard.actions.department" text="Department"/></th>
		    <th><spring:message code="dashboard.actions.actor.name" text="dashboard.actions.actor.name"/></th>
		    
		    <c:if test="${tsclEnv ne 'Y'}">
		    <th width="10%"><spring:message code="employee.loginname" text="employee.loginname"/></th>
		    </c:if>
		    
		    <th><spring:message code="dashboard.actions.actor.email" text="dashboard.actions.actor.email"/></th>
		    <th><spring:message code="dashboard.actions.actor.designation" text="dashboard.actions.actor.designation"/></th>
		    <th width="10%"><spring:message code="dashboard.actions.remarks" text="dashboard.actions.remarks"/></th>
		    <th width="10%"><spring:message code="dashboard.actions.actor.fwdEmp" text="Employee Transfer Details"/></th>
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
		         <%-- D#127199 --%>
		         <td><c:out value="${action.deptName}"></c:out></td>
                 <td><c:out value="${action.empName}"></c:out></td>
                 
                 <c:if test="${tsclEnv ne 'Y'}">
                 <td><c:out value="${action.empLoginName}"></c:out></td>
                 </c:if>
                 
                 <td><c:out value="${action.empEmail}"></c:out></td>
                 <td><c:out value="${userSession.languageId eq 1 ? action.empGroupDescEng : action.empGroupDescReg}"></c:out></td>
                 <td><c:out value="${action.comments}"></c:out></td>
                 <td><c:out value="${action.forwardToEmployee}"></c:out></td>
                 
                 <td>
	                  <ul>
	                  <c:forEach items="${action.attachements}" var="lookUp" varStatus="status">
	                  <li><apptags:filedownload filename="${lookUp.lookUpCode}" filePath="${lookUp.defaultVal}" dmsDocId="${lookUp.dmsDocId}" actionUrl="AdminHome.html?Download"></apptags:filedownload></li>
	                  </c:forEach>
	                  </ul>
                  </td>
		          </tr>
		          </c:forEach>
			     </table>
				</div>
		 </div>

		 <div class="text-center clear padding-10">
		 <c:choose>
		 	<c:when test="${userSession.employee.designation.dsgshortname eq 'OPR'}">
		 		<apptags:backButton url="OperatorDashboardView.html"></apptags:backButton>
		 	</c:when>
		 	<c:otherwise>
		 		<apptags:backButton url="AdminHome.html"></apptags:backButton>
		 	</c:otherwise>
		 </c:choose>
		 </div>
		</form:form>
		</div>
	</div>
	</div>
</div>