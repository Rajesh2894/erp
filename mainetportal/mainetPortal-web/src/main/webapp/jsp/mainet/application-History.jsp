<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<div class="content" id="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="eip.app.status" text="Application Status" /></h2>
			<div class="additional-btn"><a href="#" data-toggle="tooltip" data-original-title="Help"><i class="fa fa-question-circle fa-lg"></i></a> </div>
		</div>
		<div class="widget-content padding" >
	  <form:form class="form-horizontal" name="Application History">
		<div id="ActionHistory" class="panel-collapse collapse in">
			<div class="panel-body">
			<div id="receipt">
			<div class="form-group">
			<label class=" col-sm-2 col-xs-4 text-right"><spring:message code="citizen.dashboard.applNo"	text="Application No." />:</label>
			<div class="col-sm-4 col-xs-6">
			<label class="label-control text-bold"><c:out value="${appStatus.applicationId}"></c:out></label>
			</div>
			<label class="col-sm-2 col-xs-4 text-right"><spring:message
							code="care.applicant.name" text="Applicant Name :"/></label>
			<div class="col-sm-4 col-xs-6">
				<label class="label-control text-bold"><c:out value="${appStatus.empName}"></c:out></label>
			</div>
			</div>
			
			<div class="form-group">
			<label class=" col-sm-2 col-xs-4 text-right"><spring:message code="care.department"	text="Department" />:</label>
			<div class="col-sm-4 col-xs-6">
			<label class="label-control text-bold"><c:out value="${appStatus.deptName}"></c:out></label>
			
			</div>
			
			</div>
			
			<div class="clear"></div>
				<div class="table-responsive">
					<table class="table table-bordered table-condensed">
		  <tr>
		  	<th><spring:message code="care.srno" text="Sr. No."/></th>
		    <th><spring:message code="care.datetime" text="Date & Time"/></th>
		    <th width="18%"><spring:message code="care.Action" text="Action"/></th>
		    <th><spring:message code="care.action.takenBy" text="Action Taken By"/></th>
		    <th><spring:message code="care.email" text="Email"/></th>
		    <th><spring:message code="care.designation.role" text="Role/Designation"/></th>
		    <th width="20%"><spring:message code="care.remark" text="Remarks"/></th>
		    <%-- <th><spring:message code="care.attachments" text="Attachments"/></th> --%>
		  </tr>
		  <c:set var="rowCount" value="0" scope="page" />
		  <c:forEach items="${appStatus.actions}" var="action" varStatus="status">
               <tr>
              
                 <td ><c:set var="rowCount" value="${rowCount+1}" scope="page" /><c:out value="${rowCount}"></c:out></td>
                 <td ><c:out value="${action.dateOfAction}"></c:out></td>
                 <td><c:out value="${action.decision}"></c:out></td>
                 <td><c:out value="${action.empName}"></c:out></td>
                  <td><c:out value="${action.empEmail}"></c:out></td>
                  <td><c:out value="${action.empGroupDescEng}"></c:out></td>
                  <td><c:out value="${action.comments}"></c:out></td>
                <%--   <td>
                  <ul>
                  <c:forEach items="${action.attachements}" var="lookUp" varStatus="status">
                  <li><apptags:filedownload filename="${lookUp.lookUpCode}" filePath="${lookUp.defaultVal}" actionUrl="NewWaterConnectionForm.html?Download"></apptags:filedownload></li>
                  </c:forEach>
                  </ul>
                  </td> --%>
		            </tr>
		           </c:forEach>
			     </table>
				</div>
			</div>
		 </div>
		 </div>
		 <div class="text-center clear padding-10">
		  <button onclick="printContent('receipt')" class="btn btn-primary hidden-print"><i class="fa fa-print"></i> <spring:message code="care.receipt.print" text="Print"/></button>
		 <apptags:backButton buttonLabel="backButton" url="CitizenHome.html" cssClass="hidden-print"></apptags:backButton>
		 </div>
		</form:form>
		</div>
	</div>
</div>
<script>
function printContent(el){
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}
$("html, body").animate({ scrollTop: 0 }, "slow");
</script>