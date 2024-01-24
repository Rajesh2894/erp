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
<script type="text/javascript" src="js/material_mgmt/service/materialDispatchNoteInspection.js"></script>
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
				<spring:message code="material.management.mdn.inspection.items" text="Inspection Items" />
			</h2>
			<apptags:helpDoc url="MaterialDispatchNote.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>

			<form:form action="MaterialDispatchNote.html" name="MaterialDispatchNoteInspection" 
					id="MaterialDispatchNoteInspectionId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
					<span aria-hidden="true">&times;</span></button><span id="errorId"></span>
				</div>
				
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="levelCheck" id="levelCheck" />				
				<form:hidden path="indexCount" id="indexCount" />
				<form:hidden path="management" id="management" />
				<form:hidden path="dispatchNoteDTO.matDispatchItemList[${command.indexCount}].isExpiry" id="isExpiry" />			
								
				
				<div class="form-group">
					<form:hidden path="dispatchNoteDTO.matDispatchItemList[${command.indexCount}].itemId" id="itemId" />
					<apptags:input labelCode="material.management.itemName" isMandatory="true" isReadonly="true" 
							path="dispatchNoteDTO.matDispatchItemList[${command.indexCount}].itemName" />
							
					<apptags:input labelCode="material.management.receivedQuantity" isMandatory="true" isReadonly="true" 
							path="dispatchNoteDTO.matDispatchItemList[${command.indexCount}].issuedQty" />
				</div>
				
				<div class="widget-header">
					<h4>
						<c:choose>
							<c:when test="${command.management eq 'B'}">
								<spring:message code="material.management.mdn.management.in.batch"
									text="Item Management Method - In Batch" />
							</c:when>
							<c:when test="${command.management eq 'S'}">
								<spring:message code="material.management.mdn.management.serial"
									text="Item Management Method - Serial" />
							</c:when>
							<c:when test="${command.management eq 'N'}">
								<spring:message code="department.indent.item.management.method.not.in.batch"
									text="Item Management Method - Not In Batch" />
							</c:when>						
						</c:choose>						
					</h4>
				</div>
				<c:set var="e" value="0" scope="page" />
				<table
					class="table table-striped table-bordered appendableClass unitDetail"
					id="inBatchTableId">
					<thead>
						<tr>
							<th width="5%" class="text-center"><spring:message code="material.management.SrNo"
									text="Sr No" /></th>
							<c:choose>
								<c:when test="${command.management eq 'B'}">
									<th width="15%"><spring:message code="material.management.batchNo" text="Batch No." /><i
										class="text-red-1">*</i></th>
								</c:when>
								<c:when test="${command.management eq 'S'}">
									<th width="15%" class="text-center"><spring:message code="material.item.serialNumber" 
											text="Serial No." /><i class="text-red-1">*</i></th>
								</c:when>
							</c:choose>
							<th width="10%" class="text-center"><spring:message code="department.indent.message.issued.quantity"
									text="Issued Quantity" /><i class="text-red-1">*</i></th>
							<th class="text-center"><spring:message code="material.management.acceptedQuantity"
									text="Accepted Quantity" /><i class="text-red-1">*</i></th>
							<th class="text-center"><spring:message code="material.management.rejectedQuantity"
									text="Rejected Quantity" /><i class="text-red-1">*</i></th>
							<th width="15%" class="text-center"><spring:message code="Manufacturing.Date"
									text="Manufacturing Date" /><i class="text-red-1">*</i></th>
							<th width="15%" class="text-center"><spring:message code="material.management.ExpiryDate"
									text="Expiry Date" /><i class="text-red-1">*</i></th>
							<th class="text-center"><spring:message code="material.management.rejectionReason"
									text="Rejection Reason" /><i class="text-red-1">*</i></th>
							<th class="text-center"><spring:message code="binLocMasDto.binLocation"
									text="Bin Location" /><i class="text-red-1">*</i></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${command.noteItemsEntryDTOList}" var="details"
							varStatus="status">
							<tr class="mdnInspectionEntryRow">
								<td><input type="text" class="form-control text-center"
									disabled="true" id="srNo${e}" value="${e+1}" /></td>
									
								<c:if test="${command.management ne 'N'}">
									<td><form:input path="noteItemsEntryDTOList[${e}].itemNo" readonly="true"
										id="itemNo${e}" class="form-control text-right" /></td>
								</c:if>

								<td><form:input path="noteItemsEntryDTOList[${e}].quantity" readonly="true"
										id="quantity${e}" class="form-control text-right" /></td>

								<td><form:input path="noteItemsEntryDTOList[${e}].acceptQty"
										id="acceptQty${e}" class="form-control text-right hasNumber" onchange="calculateTotal()" /></td>

								<td><form:input path="noteItemsEntryDTOList[${e}].rejectQty" readonly="true"
										id="rejectQty${e}" class="form-control text-right" /></td>

								<td align="center"><form:input path="noteItemsEntryDTOList[${e}].mfgDate"
										id="mfgDate${e}" class="form-control mandColorClass datepicker datepickerMfg"
										maxLength="10" autocomplete="off" /></td>

								<td align="center"><form:input path="noteItemsEntryDTOList[${e}].expiryDate"
										id="expiryDate${e}" class="form-control mandColorClass datepicker datepickerExp"
										maxLength="10" autocomplete="off" /></td>

								<td><form:select path="noteItemsEntryDTOList[${e}].rejectionReason"
										id="rejectionReason${e}" cssClass="form-control" data-rule-required="false">
										<c:set var="baseLookupCode" value="ROR" />
										<form:option value="">
											<spring:message code="material.management.select" text="Select" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
											<form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

								<td><form:select path="noteItemsEntryDTOList[${e}].receivedBinLocation"
										id="receivedBinLocation${e}" cssClass="form-control chosen-select-no-results"
										data-rule-required="false">
										<form:option value="">
											<spring:message code="material.management.select" text="Select" />
										</form:option>
										<c:forEach items="${command.binLocObjectList}" var="binLoc">
											<form:option value="${binLoc[0]}">${binLoc[1]}</form:option>
										</c:forEach>
									</form:select></td>
							</tr>
							<c:set var="e" value="${e+1}" scope="page" />
						</c:forEach>
					</tbody>
				</table>

				<div class="form-group">
					<apptags:input labelCode="material.management.totalAcceptedQuantity"
							isMandatory="true"  isReadonly="true"
							path="dispatchNoteDTO.matDispatchItemList[${command.indexCount}].acceptQty" />
							
					<apptags:input labelCode="material.management.totalRejectedQuantity"
							isMandatory="true"  isReadonly="true"
							path="dispatchNoteDTO.matDispatchItemList[${command.indexCount}].rejectQty" />
				</div>
				
				<div class="form-group">
					<apptags:textArea labelCode="remark.goods" isMandatory="true"
							path="dispatchNoteDTO.matDispatchItemList[${command.indexCount}].storeRemarks" />
				</div>
				
				<!-------------------------------------- Buttons --------------------------------------------->
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2" onclick="saveMDNInspectionDetails(this)">
						<spring:message code="master.save.bin" text="Save" />
					</button>
					<button type="button" class="btn btn-danger" onclick="backToMDNForm()">
						<spring:message code="material.management.back" text="Back" />
					</button>
				</div>
								
			</form:form>
		</div>
	</div>
</div>
