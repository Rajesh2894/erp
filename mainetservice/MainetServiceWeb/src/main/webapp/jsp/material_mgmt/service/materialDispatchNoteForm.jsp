<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/material_mgmt/service/materialDispatchNote.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<style>
.input-group-field {
	display: table-cell;
	width: 114px;
}
</style>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<c:choose>
					<c:when test="${command.levelCheck eq 1 || (command.saveMode eq 'V' && command.dispatchNoteDTO.status eq 'S')}">
						<spring:message code="material.management.mdn.inspection.heading" text="Material Dispatch Note Inspection And Store Entry" />
					</c:when>
					<c:otherwise>
						<spring:message code="material.management.mdn.heading" text="Material Dispatch Note" />
					</c:otherwise>
				</c:choose>
			</h2>
			<apptags:helpDoc url="MaterialDispatchNote.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>

			<form:form action="MaterialDispatchNote.html" name="MaterialDispatchNoteForm" 
					id="MaterialDispatchNoteFormId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
					<span aria-hidden="true">&times;</span></button><span id="errorId"></span>
				</div>
				
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="levelCheck" id="levelCheck" />
				<form:hidden path="indexCount" id="indexCount" />
				
				
				<c:if test="${command.saveMode ne 'A' }">
					<div class="form-group">
						<spring:eval
							expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp('${command.dispatchNoteDTO.status}','IDS',${UserSession.organisation}).getLookUpDesc()"
							var="otherField" />													
						<apptags:input labelCode="material.management.mdn.status" path="" 
								placeholder="${otherField}"	isReadonly="true" />
					</div>
				</c:if>

				<div class="form-group">
					<c:if test="${command.saveMode ne 'A' }">
						<apptags:input labelCode="material.management.mdn.no" path="dispatchNoteDTO.mdnNumber" isDisabled="true" />
					</c:if>
						
					<apptags:date labelCode="material.management.mdn.date" datePath="dispatchNoteDTO.mdnDate"
							fieldclass="lessthancurrdate" isMandatory="true" 
							isDisabled="${command.saveMode ne 'A' }"></apptags:date>
				</div>
					
				<div class="form-group">
				
					<c:choose>
						<c:when test="${command.saveMode eq 'A' }">
							<label class="col-sm-2 control-label" for=""><spring:message
									code="material.management.store.indent.no" text="Store Indent No." /></label>
							<div class="col-sm-4">
								<form:select path="dispatchNoteDTO.siId" id="siId"
									class="chosen-select-no-results form-control required-control" data-rule-required="true"
									onchange="getStoreIndentDetails()">
									<form:option value="">
										<spring:message code="material.item.master.select" text="Select" />
									</form:option>
									<c:forEach items="${command.storeIndentList}" var="storeIndent">
										<form:option value="${storeIndent[0]}">${storeIndent[1]}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<form:hidden path="dispatchNoteDTO.siId" id="siId" />
								<apptags:input labelCode="material.management.store.indent.no" isMandatory="true" 
									path="dispatchNoteDTO.siNumber" isDisabled="true" />	
						</c:otherwise>
					</c:choose>
		
					<apptags:date labelCode="material.management.store.indent.date" datePath="dispatchNoteDTO.storeIndentdate"
						fieldclass="lessthancurrdate" isMandatory="true" isDisabled="true"></apptags:date>
				</div>
				
				
				<!--------------------------------- Requesting Store Details --------------------------------------------------->
					<div id="requestingStoreDiv">
						<h4>
							<spring:message code="material.management.request.store" text="Requesting Store" />
						</h4>
						<div class="form-group">
							<form:hidden path="dispatchNoteDTO.requestStoreId" id="requestStoreId" />
							<apptags:input labelCode="material.management.requesting.store" path="dispatchNoteDTO.requestStore"
									isMandatory="true" isDisabled="true" />	
	
							<apptags:input labelCode="material.management.requesting.store.location" 
								path="dispatchNoteDTO.requestStoreLocName" isMandatory="true" isDisabled="true" />
						</div>
	
						<div class="form-group">
							<apptags:input labelCode="material.management.requesting.store.incharge" 
								path="dispatchNoteDTO.requestedByName" isMandatory="true" isDisabled="true"/>
						</div>
					</div>
					
				<!--------------------------------- Issuing Store Details --------------------------------------------------->
					<div id="issueingStoreDiv">
						<h4>
							<spring:message code="material.management.issue.store" text="Issuing Store" />
						</h4>
						<div class="form-group">
							<form:hidden path="dispatchNoteDTO.issueStoreId" id="issueStoreId" />
							<apptags:input labelCode="material.management.issuing.store" path="dispatchNoteDTO.issueStore"
									isMandatory="true" isDisabled="true" />							
	
							<apptags:input labelCode="material.management.issuing.store.location" 
								path="dispatchNoteDTO.issueStoreLocName" isMandatory="true" isDisabled="true" />
						</div>
	
						<div class="form-group">
							<apptags:input labelCode="material.management.issuing.store.incharge" 
								path="dispatchNoteDTO.issueInchargeName" isMandatory="true" isDisabled="true" />
						</div>
					</div>	
					
				<!--------------------------------- Item Details --------------------------------------------------->
					<div id="initialDataTable">
						<c:set var="d" value="0" scope="page"></c:set>
						<table class="table table-bordered margin-bottom-10"
							id="storeInentTableId">
							<thead>
								<tr>
									<th width=5% class="text-center"><spring:message code="store.master.srno" 
											text="Sr.No." /></th>
									<th width=15% class="text-center"><spring:message code="department.indent.item.name" 
											text="Item Name" /><i class="text-red-1">*</i></th>
									<th width=9% class="text-center"><spring:message code="material.management.UoM" 
											text="UoM" /><i class="text-red-1">*</i></th>
								
									<c:if test="${command.saveMode eq 'A' || (command.saveMode eq 'V' && command.dispatchNoteDTO.status eq 'I')}">
										<th width=10% class="text-center"><spring:message code="department.indent.requested.quantity"
											text="Requested Quantity" /><i class="text-red-1">*</i></th>
										<th width=10% class="text-center"><spring:message code="material.management.mdn.previously.issued.quantity"
											text="Previously Issued Quantity" /><i class="text-red-1">*</i></th>
									</c:if>
									
									<th width=10% class="text-center">
										<c:choose>
											<c:when test="${command.levelCheck eq 1 || (command.saveMode eq 'V' && command.dispatchNoteDTO.status eq 'S')}">
												<spring:message code="material.management.receivedQuantity" text="Received Quantity" />
											</c:when>
											<c:otherwise>
												<spring:message code="department.indent.message.issued.quantity" text="Issued Quantity" />
											</c:otherwise>
										</c:choose><i class="text-red-1">*</i>
									</th>
									
									<c:if test="${command.saveMode eq 'A' || (command.saveMode eq 'V' && command.dispatchNoteDTO.status eq 'I')}">
										<th width=10% class="text-center"><spring:message code="material.management.mdn.remaining.quantity"
											text="Remaining Quantity" /><i class="text-red-1">*</i></th>
									</c:if>
									
									<c:if test="${command.levelCheck eq 1 || (command.saveMode eq 'V' && command.dispatchNoteDTO.status eq 'S') }">
										<th width=10% class="text-center"><spring:message code="material.management.acceptedQuantity"
											text="Accepted Quantity" /><i class="text-red-1">*</i></th>
										<th width=10% class="text-center"><spring:message code="material.management.rejectedQuantity"
											text="Rejected Quantity" /><i class="text-red-1">*</i></th>
									</c:if>
									
									<th width=15% class="text-center"><spring:message code="material.management.remarks"
											text="Remarks" /><i class="text-red-1">*</i></th>
									<th width="5%" class="text-center"><spring:message code="store.master.action" 
											text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.dispatchNoteDTO.matDispatchItemList}"  var="data" varStatus="index">
									<tr class="itemDetailsRow">
										<td><form:input path="" id="sNo${d}" value="${d + 1}" readonly="true"  cssClass="form-control text-center" />
											<form:hidden path="dispatchNoteDTO.matDispatchItemList[${d}].siItemId" id="siItemId${d}" />
											<form:hidden path="dispatchNoteDTO.matDispatchItemList[${d}].management" id="management${d}" />	
										</td>												

										<td><form:hidden path="dispatchNoteDTO.matDispatchItemList[${d}].itemId" id="itemId${d}" />	
											<form:input path="dispatchNoteDTO.matDispatchItemList[${d}].itemName"
												class="form-control mandColorClass" id="itemName${d}" readonly="true" /></td>
												
										<td><form:hidden path="dispatchNoteDTO.matDispatchItemList[${d}].uom" id="uom${d}" />
											<form:input path="dispatchNoteDTO.matDispatchItemList[${d}].uomDesc"
												class="form-control mandColorClass" id="uomDesc${d}" readonly="true" /></td>
										
										<c:if test="${command.saveMode eq 'A' || (command.saveMode eq 'V' && command.dispatchNoteDTO.status eq 'I')}">
											<td><form:input path="dispatchNoteDTO.matDispatchItemList[${d}].requestedQty"
												id="requestedQty${d}" class="form-control text-right hasNumber" readonly="true" /></td>
	
											<td><form:input path="dispatchNoteDTO.matDispatchItemList[${d}].prevRecQty"
												id="prevRecQty${d}" class="form-control text-right" readonly="true" /></td>
										</c:if>												
										
										<td><form:input path="dispatchNoteDTO.matDispatchItemList[${d}].issuedQty" id="issuedQty${d}" 
												class="form-control text-right hasNumber" readonly="true" /></td>
											
										<c:if test="${command.saveMode eq 'A' || (command.saveMode eq 'V' && command.dispatchNoteDTO.status eq 'I') }">
											<td><form:input path="dispatchNoteDTO.matDispatchItemList[${d}].remainingQty" readonly="true"
												id="remainingQty${d}" class="form-control text-right" /></td>
										</c:if>	
										
										<c:if test="${command.levelCheck eq 1 || (command.saveMode eq 'V' && command.dispatchNoteDTO.status eq 'S')}">
											<td><form:input path="dispatchNoteDTO.matDispatchItemList[${d}].acceptQty" readonly="true"
												id="acceptQty${d}" class="form-control text-right" /></td>
											<td><form:input path="dispatchNoteDTO.matDispatchItemList[${d}].rejectQty" readonly="true"
												id="rejectQty${d}" class="form-control text-right" /></td>
										</c:if>										
												
										<c:choose>
											<c:when test="${command.levelCheck eq 1 || (command.saveMode eq 'V' && command.dispatchNoteDTO.status eq 'S')}">
												<td><form:textarea path="dispatchNoteDTO.matDispatchItemList[${d}].storeRemarks"
													id="storeRemarks${d}" class="form-control" readonly="true" /></td>
											</c:when>
											<c:otherwise>
												<td><form:textarea path="dispatchNoteDTO.matDispatchItemList[${d}].inspectorRemarks"
													id="inspectorRemarks${d}" class="form-control" readonly="true" /></td>												
											</c:otherwise>
										</c:choose>
										
										<td>
											<div class="text-center">
											<c:choose>
												<c:when test="${command.levelCheck eq 1 
															|| (command.saveMode eq 'V' && command.dispatchNoteDTO.status eq 'S')}">
													<spring:message code="material.management.mdn.inspection.details"
															text="MDN Inspection Details" var="viewDetails"/>
												</c:when>
												<c:otherwise>
													<spring:message code="material.management.mdn.issue.details" 
															text="MDN Issue Details" var="viewDetails"/>
												</c:otherwise>
											</c:choose>											
											<button type="button" class="btn btn-blue-3 btn-sm margin-right-5"
												onclick="viewMDNIssueDetails(${d})" title="${viewDetails}">
												<i class="fa fa-building-o"></i>
											</button>
										</div>
										</td>										
									</tr>
									<c:set var="d" value="${d+1}" scope="page" />
								</c:forEach>
							</tbody>
						</table>
					</div>
								
				
				<!-------------------------------------- Buttons --------------------------------------------->
				<div class="text-center padding-bottom-10">
					<c:if test="${command.saveMode ne 'V' }">					
						<button type="button" class="btn btn-success" onclick="submitMDNFormData(this)">
							<spring:message code="material.management.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'A' }">
						<button type="button" class="btn btn-warning"
							onclick="addMDNForm('MaterialDispatchNote.html','addMaterialDispatchNote');">
							<spring:message code="material.management.reset" text="Reset" />
						</button>
					</c:if>
					<c:choose>
						<c:when test="${command.levelCheck eq 1}">
							<apptags:backButton url="AdminHome.html"></apptags:backButton>
						</c:when>
						<c:otherwise>
							<apptags:backButton url="MaterialDispatchNote.html"></apptags:backButton>
						</c:otherwise>
					</c:choose>
				</div>
								
			</form:form>
		</div>
	</div>
</div>
