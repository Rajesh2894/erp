<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/material_mgmt/service/inspectionItemsStore.js" type="text/javascript"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<style>
.input-group-field {
	display: table-cell;
	width: 114px;
}
</style>
<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="GRN.Store.Entry.Details" text="GRN Store Entry Details" />
		</h2>
		<apptags:helpDoc url="InspectionItems.html" />
	</div>
	<div class="widget-content padding">

		<div class="mand-label clearfix">
			<span><spring:message code="water.fieldwith" /><i
				class="text-red-1">*</i> <spring:message code="water.ismandtry" />
			</span>
		</div>

		<form:form action="GoodsReceivedNoteStoreEntry.html"
			name="Goods Received Note Store Detail Entry"
			id="grnStoreEntryDetailID" class="form-horizontal">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDiv" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>
			
			<form:hidden path="saveMode" id="saveMode" />
			<form:hidden path="managementMethod" id="managementMethod" />
			<form:hidden path="indexCount" id="indexCount" />
			<form:hidden path="grnitemDto.isExpiry" id="isExpiry" />
			<form:hidden path="inspectionItemsDto.grnid" id="grnid" />
			<form:hidden path="inspectionItemsDto.grnitemid" id="grnitemid" />

			<div class="form-group">
				<apptags:input labelCode="material.management.itemName" isReadonly="true" 
						path="goodsReceivedNotesDto.goodsreceivedNotesItemList[${command.indexCount}].itemDesc" />
						
				<apptags:input labelCode="material.management.receivedQuantity" isReadonly="true" 
						path="goodsReceivedNotesDto.goodsreceivedNotesItemList[${command.indexCount}].receivedqty" />
			</div>

			<c:choose>
				<c:when test="${command.managementMethod eq 'B'}">
					<div class="widget-header">
						<h4>
							<spring:message code="Item.Inspection.Details.InBatch"
								text="Item Inspection Details ( Management Method - In Batch )" />
						</h4>
					</div>
					<c:set var="e" value="0" scope="page" />
					<table
						class="table table-striped table-bordered appendableClass unitDetail"
						id="inBatchTableId">
						<thead>
							<tr>
								<th class="text-center"><spring:message
										code="material.management.SrNo" text="Sr No" /></th>
								<th class="text-center"><spring:message
										code="material.management.batchNo" text="Batch No." /><i
									class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.item.master.conversionqty"
										text="Conversion Quantity" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="Manufacturing.Date"
										text="Manufacturing Date" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.management.ExpiryDate"
									text="Expiry Date" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message
										code="material.management.decision" text="Decision" /><i
									class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.management.rejectionReason"
										text="Rejection Reason" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="binLocMasDto.binLocation" text="Bin Location" />
									<i class="text-red-1">*</i></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.inspectionItemsList}" var="details"
								varStatus="status">
								<tr class="inBatchTableRow">
									<td width="8%"><input type="text" class="form-control text-center" disabled="true"
										id="inBatchSrNo${e}" value="${e+1}" /></td>

									<td><form:hidden path="inspectionItemsList[${e}].grnitementryid" id="grnitementryid${e}" /> 
										<form:input type="text" id="batchNo${e}" class="form-control hasCharacterNumbers"
											maxlength="25" path="inspectionItemsList[${e}].itemNo" readonly="true" /></td>

									<td><form:input type="text" id="quantityInBatch${e}" class="form-control" 
											onchange="calculateInBatch()" path="inspectionItemsList[${e}].quantity"
											onkeypress="return hasAmount(event, this, 10, 2)" readonly="true" /></td>

									<td align="center"><form:input path="inspectionItemsList[${e}].mfgDate"
											id="inBatchMfgdate${e}" class="form-control mandColorClass datepicker datepickerMfg"
											maxLength="10" autocomplete="off" readonly="true" /></td>

									<td align="center"><form:input path="inspectionItemsList[${e}].expiryDate"
											id="inBatchExpirydate${e}" class="form-control mandColorClass datepicker datepickerExp"
											maxLength="10" autocomplete="off" readonly="true" /></td>

									<td><form:select path="inspectionItemsList[${e}].decision" id="inBatchDecision${e}" cssClass="form-control" 
											data-rule-required="false" onchange="inBatchChangeDecision();" disabled="true" >
											<c:set var="baseLookupCode" value="AOR" />
											<form:option value="0">
												<spring:message code="material.management.select"
													text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpCode}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:select path="inspectionItemsList[${e}].rejectionReason" id="inBatchRejectionreason${e}" 
											cssClass="form-control" data-rule-required="false" disabled="true" >
											<c:set var="baseLookupCode" value="ROR" />
											<form:option value="0">
												<spring:message code="material.management.select"
													text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
												<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:select path="inspectionItemsList[${e}].binLocation" id="inBatchBinLocId${e}" 
											cssClass="form-control" data-rule-required="false"  >
											<form:option value="0">
												<spring:message code="material.management.select" text="Select" />
											</form:option>
											<c:forEach items="${command.binLocObjectList}" var="binLoc">
												<form:option value="${binLoc[0]}" >${binLoc[1]}</form:option>
											</c:forEach>
										</form:select></td>
								</tr>
								<c:set var="e" value="${e+1}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</c:when>


				<c:when test="${command.managementMethod eq 'S'}">
					<div class="widget-header">
						<h4>
							<spring:message code="Item.Inspection.Details.serial"
								text="Item Inspection Details ( Management Method - Serial )" />
						</h4>
					</div>
					<c:set var="f" value="0" scope="page" />
					<table
						class="table table-striped table-bordered appendableClass unitDetail"
						id="serialTableId">
						<thead>
							<tr>
								<th class="text-center"><spring:message
										code="material.management.SrNo" text="Sr No" /></th>
								<th class="text-center"><spring:message code="material.item.serialNumber"
										text="Serial Number" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="department.indent.quantiy"
										text="Quantity" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="Manufacturing.Date"
										text="Manufacturing Date" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.management.ExpiryDate" 
									text="Expiry Date" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message
										code="material.management.decision" text="Decision" /><i
									class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.management.rejectionReason"
										text="Rejection Reason" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="binLocMasDto.binLocation" text="Bin Location" />
									<i class="text-red-1">*</i></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.inspectionItemsList}" var="details"
								varStatus="status">
								<tr class="serialTableRow">
									<td width="8%"><input type="text" class="form-control text-center" disabled="true"
										id="serSrNo${f}" value="${f+1}" /></td>

									<td><form:hidden path="inspectionItemsList[${f}].grnitementryid" id="grnitementryid${f}" /> 
										<form:input type="text" id="serialFromTo${f}" class="form-control" maxlength="25"
											path="inspectionItemsList[${f}].itemNo" readonly="true" /></td>

									<td><form:input type="text" id="serQuantity${f}" class="form-control" 
											onchange="serialChangeDecision()" path="inspectionItemsList[${f}].quantity"
											onkeypress="return hasAmount(event, this, 10, 2)" readonly="true" /></td>

									<td align="center"><form:input path="inspectionItemsList[${f}].mfgDate"
											id="serialMfgdate${f}" class="form-control mandColorClass datepicker datepickerMfg"
											maxLength="10" autocomplete="off" readonly="true" /></td>

									<td align="center"><form:input path="inspectionItemsList[${f}].expiryDate"
											id="serialExpirydate${f}" class="form-control mandColorClass datepicker datepickerExp"
											maxLength="10" autocomplete="off" readonly="true" /></td>

									<td><form:select path="inspectionItemsList[${f}].decision" id="serialDecision${f}" 
											cssClass="form-control" data-rule-required="false" disabled="true"
											onchange="serialChangeDecision();">
											<c:set var="baseLookupCode" value="AOR" />
											<form:option value="0">
												<spring:message code="material.management.select"
													text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpCode}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:select path="inspectionItemsList[${f}].rejectionReason" id="serialRejectionreason${f}" 
										cssClass="form-control" hasId="true" data-rule-required="false" disabled="true" >
											<c:set var="baseLookupCode" value="ROR" />
											<form:option value="0">
												<spring:message code="material.management.select"
													text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:select path="inspectionItemsList[${f}].binLocation" id="serialBinLocId${f}" 
											cssClass="form-control" hasId="true" data-rule-required="false" >
											<form:option value="0">
												<spring:message code="material.management.select" text="Select" />
											</form:option>
											<c:forEach items="${command.binLocObjectList}" var="binLoc">
												<form:option value="${binLoc[0]}" >${binLoc[1]}</form:option>
											</c:forEach>
										</form:select></td>
								</tr>
								<c:set var="f" value="${f+1}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</c:when>


				<c:otherwise>
					<div class="widget-header">
						<h4>
							<spring:message code="Item.Inspection.Details"
								text="Item Inspection Details ( Management Method - Not In Batch )" />
						</h4>
					</div>
					<c:set var="d" value="0" scope="page" />
					<table id="notInBatchTableID"
						class="table table-striped table-bordered appendableClass unitDetail">
						<thead>
							<tr>
								<th class="text-center"><spring:message
										code="material.management.SrNo" text="Sr No" /></th>
								<th class="text-center"><spring:message code="department.indent.quantiy"
										text="Quantity" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message
										code="material.management.mfgDate" text="Manufacturing Date" /><i
									class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.management.ExpiryDate" 
									text="Expiry Date" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message
										code="material.management.decision" text="Decision" /><i
									class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.management.rejectionReason"
										text="Rejection Reason" /><i class="text-red-1">*</i></th>
								<th class="text-center" width="10%"><spring:message code="binLocMasDto.binLocation" text="Bin Location" />
									<i class="text-red-1">*</i></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.inspectionItemsList}">
								<tr class="notInBatchTableRow">
									<td width="8%"><input class="form-control text-center"
										disabled="true" id="notInBatchSequence${d}" value="${d+1}" /></td>

									<td><form:hidden path="inspectionItemsList[${d}].grnitementryid" id="grnitementryid${d}" /> 
										<form:input type="text" id="notInBatchQuantity${d}" class="form-control"
											path="inspectionItemsList[${d}].quantity" onkeypress="return hasAmount(event, this, 10, 2)"
											onchange="calculateNotInBatch();" readonly="true" /></td>

									<td align="center"><form:input path="inspectionItemsList[${d}].mfgDate"
											id="notInBatchMfgdate${d}" class="form-control mandColorClass datepicker datepickerMfg"
											maxLength="10" autocomplete="off" readonly="true" /></td>

									<td align="center"><form:input path="inspectionItemsList[${d}].expiryDate"
											id="notInBatchExpirydate${d}" class="form-control mandColorClass datepicker datepickerExp"
											maxLength="10" autocomplete="off" readonly="true" /></td>

									<td><form:select path="inspectionItemsList[${d}].decision" id="notInBatchDecision${d}" 
											cssClass="form-control" hasId="true" data-rule-required="false" disabled="true"
											onchange="notInBatchChangeDecision();">
											<c:set var="baseLookupCode" value="AOR" />
											<form:option value="0">
												<spring:message code="material.management.select" text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
												<form:option value="${lookUp.lookUpCode}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:select path="inspectionItemsList[${d}].rejectionReason" id="notInBatchRejectionreason${d}" 
											cssClass="form-control" hasId="true" data-rule-required="false" disabled="true" >
											<c:set var="baseLookupCode" value="ROR" />
											<form:option value="0">
												<spring:message code="material.management.select" text="Select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:select path="inspectionItemsList[${d}].binLocation" id="notInBatchBinLocId${d}" 
											cssClass="form-control" hasId="true" data-rule-required="false" >
											<form:option value="0">
												<spring:message code="material.management.select" text="Select" />
											</form:option>
											<c:forEach items="${command.binLocObjectList}" var="binLoc">
												<form:option value="${binLoc[0]}" >${binLoc[1]}</form:option>
											</c:forEach>
										</form:select></td>
								</tr>
								<c:set var="d" value="${d+1}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>

			</c:choose>


			<div class="form-group">
				<label class="col-sm-2 control-label" for="totalAcceptedQuantity"><spring:message
						code="material.management.totalAcceptedQuantity" /></label>
				<div class="col-sm-4">
					<form:input name="" type="text"
						class="form-control preventSpace hasNumber" readonly="true"
						path="inspectionItemsDto.acceptqty" id="totalAcceptedQuantity"></form:input>
				</div>
				<label class="col-sm-2 control-label" for="totalRejectedQuantity"><spring:message
						code="material.management.totalRejectedQuantity" /></label>
				<div class="col-sm-4">
					<form:input name="" type="text"
						class="form-control preventSpace hasNumber" readonly="true"
						path="inspectionItemsDto.rejectqty" id="totalRejectedQuantity"
						data-rule-required="true"></form:input>
				</div>
			</div>

			</br>
			<div class="text-center padding-bottom-10">
				<c:if test="${command.saveMode ne 'V' }">
					<button type="button" class="btn btn-blue-2" onclick="saveInspectionForm(this);">
						<spring:message code="master.save" text="Save"></spring:message>
					</button>
				</c:if>
				<button type="button" class="button-input btn btn-danger"
					name="button-Cancel" value="Cancel" style=""
					onclick="backButton('${command.saveMode}');" id="button-Cancel">
					<spring:message code="solid.waste.back" text="Back" />
				</button>
			</div>

		</form:form>
	</div>
</div>