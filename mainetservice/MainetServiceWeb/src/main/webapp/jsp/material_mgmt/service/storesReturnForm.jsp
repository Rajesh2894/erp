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
<script type="text/javascript" src="js/material_mgmt/service/storesReturn.js"></script>
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
					<c:when test="${command.levelCheck eq 1}">
						<spring:message code="material.management.stores.return.entry" text="Store Return Note - Store Entry" />
					</c:when>
					<c:otherwise>
						<spring:message code="material.management.stores.return.form" text="Store Return" />
					</c:otherwise>
				</c:choose>				
			</h2>
			<apptags:helpDoc url="StoresReturn.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>

			<form:form action="StoresReturn.html" name="storesReturnForm" id="storesReturnFormId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
					<span aria-hidden="true">&times;</span></button><span id="errorId"></span>
				</div>
				
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="levelCheck" id="levelCheck" />
				

				<div class="form-group">
					<c:if test="${command.saveMode ne 'A'}">
						<apptags:input labelCode="material.management.store.return.no" isMandatory="true"
							path="storesReturnDTO.storeReturnNo" isDisabled="true" />
					</c:if>

					<apptags:date fieldclass="lessthancurrdate" labelCode="material.management.return.date"
						isMandatory="true" datePath="storesReturnDTO.storeReturnDate" cssClass="fromDateClass" 
						isDisabled="${command.saveMode ne 'A'}"></apptags:date>
				</div>

				<div class="form-group">
					<c:choose>
						<c:when test="${command.saveMode eq 'A'}">
							<label class="col-sm-2 control-label required-control" for=""><spring:message
									code="material.management.mdn.no" text="MDN No." /></label>
							<div class="col-sm-4">
								<form:select path="storesReturnDTO.mdnId" id="mdnId"
									class="chosen-select-no-results form-control required-control"
									data-rule-required="true" onchange="getMDNRejectedDetails()" >
									<form:option value="">
										<spring:message code="material.item.master.select" text="Select" />
									</form:option>
									<c:forEach items="${command.mdnIdNameList}" var="mdnObject">
										<form:option value="${mdnObject[0]}">${mdnObject[1]}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<form:hidden path="storesReturnDTO.mdnId" id="mdnId" />
							<apptags:input labelCode="material.management.mdn.no" isMandatory="true" 
								path="storesReturnDTO.mdnNumber" isDisabled="true" />
						</c:otherwise>
					</c:choose>

					<apptags:date labelCode="material.management.mdn.date" datePath="storesReturnDTO.mdnDate"
						fieldclass="lessthancurrdate" isMandatory="true" isDisabled="true"></apptags:date>
				</div>

				<div class="form-group">
					<form:hidden path="storesReturnDTO.issueStoreId" id="issueStoreId" />
					<apptags:input labelCode="material.management.issuing.store" isMandatory="true"
						path="storesReturnDTO.issueStoreName" isDisabled="true" />
				
					<form:hidden path="storesReturnDTO.requestStoreId" id="requestStoreId" />
					<apptags:input labelCode="material.management.requesting.store" isMandatory="true"
						path="storesReturnDTO.requestStoreName" isDisabled="true" />
				</div>
				
				<div class="form-group">
					<form:hidden path="storesReturnDTO.storeIndentId" id="storeIndentId" />
					<apptags:input labelCode="material.management.store.indent.no" isMandatory="true"
						path="storesReturnDTO.storeIndentNo" isDisabled="true" />

					<apptags:textArea labelCode="material.management.noting" isDisabled="${command.saveMode ne 'A'}"
						path="storesReturnDTO.noting" isMandatory="true"></apptags:textArea>
				</div>


		<!--------------------- MDN Rejected Items ------------------------->
				<h4>
					<spring:message code="material.management.mdn.rejected.items" text="MDN Rejected Items" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="storesReturnTableID" summary="GRN Rejected Items"
								class="table table-bordered table-striped rcm">
								<thead>
									<tr>
										<th width="5%"><spring:message code="material.management.SrNo" text="Sr.No" /></th>
										<th width="15%"><spring:message code="material.management.itemName" text="Item Name" />
												<i class="text-red-1">*</i></th>
										<th width="10%"><spring:message code="material.management.UoM" text="UoM" />
												<i class="text-red-1">*</i></th>
										<th width="15%"><spring:message code="material.management.batchOrSerialNo" text="Batch No. / Serial No." /></th>
										<th width="10%"><spring:message code="material.management.rejectedQuantity" text="Rejected Quantity" />
												<i class="text-red-1">*</i></th>
										<th><spring:message code="material.management.remarks" text="Remarks" />
												<i class="text-red-1">*</i></th>
										<c:if test="${command.levelCheck eq 1 || (command.saveMode eq 'V' && command.storesReturnDTO.status eq 'Y')}">
											<th  width="8%"><spring:message code="material.expired.item.entry.mark.for.desposal" 
												text="Mark For Disposal" /><i class="text-red-1">*</i></th>
											<th><spring:message code="binLocMasDto.binLocation" text="Bin Location" />
												<i class="text-red-1">*</i></th>
										</c:if>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.storesReturnDTO.storesReturnDetailList}" var="data" varStatus="index">
										<tr class="storesReturnDetailRow">
											<td class="text-center"><input type="text" class="form-control text-center" disabled="true"
												id="SrNo${d}" value="${d+1}" /></td>
												
											<td><form:hidden path="storesReturnDTO.storesReturnDetailList[${d}].mdnId" id="mdnId${d}" />
												<form:hidden path="storesReturnDTO.storesReturnDetailList[${d}].mdnItemEntryId" id="mdnItemEntryId${d}" />
												<form:input path="storesReturnDTO.storesReturnDetailList[${d}].itemId" id="itemId${d}"
													class="form-control mandColorClass" value="${data.itemName}"
													data-rule-required="true" disabled="true" /></td>
											
											<td><form:input path="storesReturnDTO.storesReturnDetailList[${d}].uomName"
													id="uomName${d}" type="text" class="form-control" readonly="true" /></td>
												
											<td><form:input path="storesReturnDTO.storesReturnDetailList[${d}].itemNo" id="itemNo${d}"
													class="form-control mandColorClass" data-rule-required="true" readonly="true" /></td>
											
											<td><form:input path="storesReturnDTO.storesReturnDetailList[${d}].quantity" 
													id="quantity${d}" class="form-control mandColorClass text-right" data-rule-required="true" 
													readonly="true" /></td>
											
											<td><form:input path="storesReturnDTO.storesReturnDetailList[${d}].returnReason"
													id="returnReason${d}" value="${data.returnReasonDesc}" class="form-control mandColorClass" 
													data-rule-required="true" readonly="true"/></td>
											
											<c:if test="${command.levelCheck eq 1 || (command.saveMode eq 'V' && command.storesReturnDTO.status eq 'Y')}">
												<td class="text-center" ><form:checkbox path="storesReturnDTO.storesReturnDetailList[${d}].disposalFlag"
													id="disposalFlag${d}" value="Y" onclick="checkDisposalFlag(this, '${d}');"/></td>
													
												<td><form:select path="storesReturnDTO.storesReturnDetailList[${d}].binLocation" id="binLocation${d}" 
														cssClass="form-control chosen-select-no-results" data-rule-required="false" >
														<form:option value="">
															<spring:message code="material.management.select" text="Select" />
														</form:option>
														<c:forEach items="${command.binLocObjectList}" var="binLoc">
															<form:option value="${binLoc[0]}">${binLoc[1]}</form:option>
														</c:forEach>
													</form:select></td>													
											</c:if>		
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>


				<!-------------------------------------- Buttons --------------------------------------------->
				<div class="text-center padding-bottom-10">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-success" onclick="submitStoreReturnForm(this)">
							<spring:message code="material.management.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'A'}">
						<button type="button" class="btn btn-warning"
							onclick="addStoreReturnForm('StoresReturn.html', 'addStoresReturnForm')">
							<spring:message code="material.management.reset" text="Reset" />
						</button>
					</c:if>					
					<c:choose>
						<c:when test="${command.levelCheck eq 1}">
							<apptags:backButton url="AdminHome.html"></apptags:backButton>
						</c:when>
						<c:otherwise>
							<apptags:backButton url="StoresReturn.html"></apptags:backButton>
						</c:otherwise>
					</c:choose>					
				</div>

			</form:form>
		</div>
	</div>
</div>
