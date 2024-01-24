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
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
 
	
			<c:if test="${not empty command.attachmentList}">
			
			<h4 class="margin-top-0"><spring:message text="Applicant Details" code="cfc.applicant.detail"/></h4>
			
				<div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="checklistVerification.serviceName"  text="Service Name" /> </label>
                <div class="col-sm-4">
                	<span class="form-control height-auto">${command.applicationDetails.applicationService }</span>
                </div>
                <label class="col-sm-2 control-label"><spring:message text="Name Of Applicant" code="cfc.applicant.name" /> </label> 
				<div class="col-sm-4">
					<span class="form-control text-hidden">${command.applicationDetails.applicantsName }</span>
				</div>
				</div>
				
				
				<div class="form-group">
	                <label class="col-sm-2 control-label"><spring:message text="Application ID " code="cfc.application" /> </label> 
					<div class="col-sm-4">
	                	<span class="form-control">${command.applicationDetails.apmApplicationId }</span>
					</div>
					<label class="col-sm-2 control-label"><spring:message text="Application Date" code="cfc.applIcation.date" /> </label>
					<div class="col-sm-4">
					<c:set value="${command.applicationDetails.apmApplicationDate }" var="appDate"/>
							<span class="form-control"><fmt:formatDate type="date" value="${appDate}" pattern="dd/MM/yyyy" /></span>
					</div>
				</div>

			</c:if>
			<c:set var="loopCount" value='0' />
			<c:if test="${not empty command.attachmentList}">
			<input type="hidden" value="${fn:length(command.attachmentList)}" id="docCount"/>
				<h4>
					<spring:message code="checklistVerification.tableCaption" />
				</h4>
				 <div class="table-responsive">
              <table class="table table-hover table-bordered table-striped">
					<tr>
						<th><spring:message code="checklistVerification.srNo" /></th>
						<th><spring:message code="checklistVerification.document" /></th>
						<th><spring:message code="checklistVerification.documentStatus" /></th>
						<th><spring:message code="checklistVerification.fileName" /></th>
						<%-- <c:if test="${command.newApplication}">
						<th><spring:message code="cfc.upload.doc" /></th>
						</c:if> --%>
						<%-- <th><spring:message code="checklistVerification.verified" /></th>
						<th><spring:message code="" text="Remark"/></th>   --%>    
					</tr>
					
					<c:forEach var="singleDoc" items="${command.documentsList}"
						varStatus="count">
						
						<c:if test="${count.index eq 0}">
							<input type="hidden" value=" ${fn:length(command.documentsList)}"  id="attSize"> 
						</c:if>
						<tr>
							<td>${count.count}</td>
							<td>${singleDoc.lookUpDesc}</td>
							<td><c:choose>
									<c:when test="${singleDoc.otherField eq 'Y'}">
										<spring:message code="checklistVerification.mandatory" />
										<c:set var="docStatus" value="Mandatory" />
									</c:when>
									<c:otherwise>
										<spring:message code="checklistVerification.optional" />
										<c:set var="docStatus" value="Optional" />
									</c:otherwise>
								</c:choose></td>
							<td class="row1">
								
									<c:set var="links"	value="${fn:split(singleDoc.defaultVal,',')}" />
									<c:forEach items="${singleDoc.defaultVal}" var="download" varStatus="status">
										<apptags:filedownload filename="${singleDoc.lookUpCode}" dmsDocId="${singleDoc.lookUpType}"  filePath="${download}" actionUrl="ChecklistVerification.html?Download"></apptags:filedownload>
									</c:forEach>
							</td>
							<%-- <c:if test="${command.newApplication}">
								
							
									<td>
									<c:choose>
									<c:when test="${singleDoc.otherField eq 'Y'}">
									<label class="checkbox-inline">
									<form:checkbox onclick="validateRejMsg(this,${count.index})" id="chkbx${count.index}" class="chkbx"
									data-mandatory="${singleDoc.otherField}"
									path="listOfChkboxStatus[${count.index}]"
									value="${count.index}" /> <c:set var="loopCount"
									value="${count.index}" /></label>
									</c:when>
									<c:otherwise>
									<label class="checkbox-inline">
									<form:checkbox onclick="validateRejMsg(this,${count.index})" id="chkbx${count.index}" class="chkbx"
									data-mandatory="${singleDoc.otherField}"
									path=""
									value="${count.index}" /> <c:set var="loopCount"
									value="${count.index}" /></label>
									</c:otherwise>
									</c:choose>
									</td>
					       <td><form:textarea id="rej${count.index}" maxlength="100" path="attachmentList[${count.index}].clmRemark"  value="${singleDoc.extraStringField1}" cssClass="form-control" /> 
									<c:set var="loopCount" value="${count.index}" />
						   </td> 
						</c:if> 
						
						<c:if test="${!command.newApplication}">
							<c:if test="${command.applicationDetails.apmChklstVrfyFlag eq 'Y'}">
								<td><label class="checkbox-inline"><form:checkbox path="" value="${count.index}" disabled="true" checked="checked"/></label></td>
								<td><form:textarea path="" value="" disabled="true" cssClass="form-control" /> </td> 
							</c:if>
							
							<c:if test="${command.applicationDetails.apmChklstVrfyFlag ne 'Y' }">
								<td><label class="checkbox-inline"><form:checkbox path="" value="${count.index}" disabled="true" /></label></td>
								<td><form:textarea path="" value="${singleDoc.extraStringField1}" disabled="true" cssClass="form-control"/> </td> 
							</c:if>
						</c:if>--%>
					</tr>
					</c:forEach>
				</table> 
				</div>
				
			</c:if>
		
	