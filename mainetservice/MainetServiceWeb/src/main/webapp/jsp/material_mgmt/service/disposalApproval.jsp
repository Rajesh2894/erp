<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/material_mgmt/master/disposalApprovalItems.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="material.management.disposal.of.dead.stock"
					text="Disposal of Dead Stock" />
			</h2>
			<apptags:helpDoc url="DisposalOfStock.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="DisposalOfStock.html" name="DisposalOfStock"
				id="DisposalOfStockId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="levelCheck" id="levelCheck" />		

				<c:if test="${command.saveMode eq 'V'}">
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message code="material.management.application.status"
							text="Application Status" /></label>
						<div class="col-sm-4">
							<c:choose>
								<c:when test="${command.expiryItemsDto.status eq 'Y'}">
									<input type="text" value="<spring:message code="material.management.pending.for.approval"
											text="Pending For Approval" />" readonly="true" />
								</c:when>
								<c:when test="${command.expiryItemsDto.status eq 'A'}">
									<input type="text" value="<spring:message code="material.management.approved"
											text="Approved" />" readonly="true" />
								</c:when>  
								<c:when test="${command.expiryItemsDto.status eq 'R'}">
									<input type="text" value="<spring:message code="purchase.requisition.rejected"
											text="Rejected" />" readonly="true" />
								</c:when>
								<c:when test="${command.expiryItemsDto.status eq 'D'}">
									<input type="text" value="<spring:message code="material.management.disposed"
											text="Disposed" />" readonly="true" />
								</c:when>
								<c:when test="${command.expiryItemsDto.isContractDone eq 'Y' && command.expiryItemsDto.status ne 'D'}">
									<input type="text" value="<spring:message code="material.management.receipt.entry.pending"
											text="Pending for Receipt Entry" />" readonly="true" />
								</c:when>
								<c:otherwise>
									<input type="text" value="<spring:message code="material.management.tender.inprocess"
											text="Tender Inprocess" />" readonly="true" />
								</c:otherwise>
							</c:choose>					
						</div>
					</div>
				</c:if>


				<div class="form-group">				
					<apptags:input labelCode="material.expired.item.entry.movement.no" 
						path="expiryItemsDto.movementNo" cssClass="form-control" 
						isMandatory="true" isDisabled="true" />
					
					<c:if test="${command.levelCheck gt 1 || (command.saveMode eq 'V' && command.expiryItemsDto.status ne 'R')}">
						<apptags:input labelCode="material.management.disposal.no" path="expiryItemsDto.scrapNo"
							cssClass="form-control required-control" isMandatory="true" isDisabled="true" />
					</c:if>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="itemCategory"><spring:message
							code="material.expired.item.entry.store" text="Store" /></label>
					<div class="col-sm-4">
						<form:select path="expiryItemsDto.storeId"
							class="form-control mandColorClass" id="storeId"
							data-rule-required="false" disabled="true">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeNameList}" var="storeData">
								<form:option value="${storeData.storeId}" code="">${storeData.storeName}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<apptags:date fieldclass="datepicker" labelCode="material.expired.item.entry.expiry.on.or.before.date" 
							datePath="expiryItemsDto.expiryCheck" isMandatory="true" isDisabled="true"
							cssClass="custDate mandColorClass"></apptags:date>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="material.expired.item.entry.movement.by" text="Movement By" /></label>
					<div class="col-sm-4">
						<form:select path="expiryItemsDto.movementBy"
							class="form-control mandColorClass" id="movementBy"
							data-rule-required="false" disabled="true">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.empList}" var="emp">
								<form:option value="${emp.empId}">${emp.empname} - ${emp.emplname}</form:option>
							</c:forEach>
						</form:select>
					</div>
										
					<apptags:date fieldclass="datepicker" labelCode="material.expired.item.entry.movement.date" 
							datePath="expiryItemsDto.movementDate" isMandatory="true" isDisabled="true"
							cssClass="custDate mandColorClass"></apptags:date>					
				</div>
				
				<h4>
					<spring:message
						code="material.expired.item.entry.expired.item.details"
						text="Expired item Details" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="id_ExpiredItemDetails" summary="Store Master Data"
								class="table table-bordered table-striped rcm">
								<thead>
									<tr>
										<th width="8%"><spring:message code="store.master.srno" text="Sr.No." /></th>
										<th><spring:message code="binLocMasDto.binLocation"
												text="Bin Location" /> <i class="text-red-1">*</i></th>
										<th><spring:message code="material.item.master.itemName" text="Item Name" /><i
											class="text-red-1">*</i></th>
										<th><spring:message code="material.expired.item.entry.uom" text="UOM" /> <i
											class="text-red-1">*</i></th>
										<th><spring:message code="material.expired.item.entry.batch.no.or.serial.no"
												text="Batch No/Serial No" /></th>
										<th><spring:message code="material.expired.item.entry.expiry.qty"
												text="Expiry Quantity" /> <i class="text-red-1">*</i></th>
										<th><spring:message code="remark.goods" text="Remark" /><i class="text-red-1">*</i></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${command.expiryItemsDto.expiryItemDetailsDtoList}"
										var="data" varStatus="index">
										<tr class="ExpiredItemDetailsRow">
										
											<td class="text-center"><input type="text" 
												class="form-control text-center" disabled="true"
												id="SrNo${d}" value="${d+1}" /></td>
											<td class="text-center"><form:input
													path="expiryItemsDto.expiryItemDetailsDtoList[${d}].binLocation"
													id="binLocation${d}" class="form-control mandColorClass"
													maxLength="500" value="${data.binLocName}"
													data-rule-required="true" disabled="true" /></td>
											<td class="text-center"><form:hidden
													path="expiryItemsDto.expiryItemDetailsDtoList[${d}].itemId"
													id="itemId${d}" /> <form:input
													path="expiryItemsDto.expiryItemDetailsDtoList[${d}].itemName"
													id="itemName${d}" class="form-control mandColorClass"
													data-rule-required="true" readonly="true" /></td>
											<td class="text-center"><form:input
													path="expiryItemsDto.expiryItemDetailsDtoList[${d}].uomName"
													value="${data.uomId}" id="uomId${d}" type="text"
													class="form-control" readonly="true" /></td>
											<td class="text-center"><form:input
													path="expiryItemsDto.expiryItemDetailsDtoList[${d}].itemNo"
													id="itemNo${d}" class="form-control mandColorClass"
													maxLength="500" value="${data.itemNo}"
													data-rule-required="true" readonly="true" /></td>
											<td class="text-center"><form:input
													path="expiryItemsDto.expiryItemDetailsDtoList[${d}].quantity"
													id="quantity${d}" class="form-control mandColorClass"
													maxLength="500" readonly="true" data-rule-required="true" /></td>
											<td class="text-center"><form:input
													path="expiryItemsDto.expiryItemDetailsDtoList[${d}].remarks"
													id="remark${d}" class="form-control mandColorClass" maxLength="500" 
													disabled="${ command.levelCheck ne 1}" data-rule-required="true" /></td>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				
				<c:if test="${command.levelCheck eq 1 }">
					<apptags:CheckerAction hideForward="true" hideSendback="true" hideUpload="true"></apptags:CheckerAction>
				</c:if>
				
								
		<!--------------------------------------- 		for receipt         ---------------------------->		

				<c:if test="${ command.saveMode eq 'E' || (command.saveMode eq 'V' && command.expiryItemsDto.status eq 'D') }">
					
					<h4>
						<spring:message code="" text="Receipt Entry"></spring:message>
					</h4>
					
					<div class=" form-group">
						<apptags:radio radioLabel="Yes,No" radioValue="Y,N" isMandatory="true" changeHandler="isPaid()"
								labelCode="material.management.is.payment.received" path="expiryItemsDto.paymentFlag" 
								defaultCheckedValue="Y" disabled="${command.saveMode eq 'V'}"  > </apptags:radio>

						<div class="paymentRecievedDiv" >
							<label class="control-label col-sm-2 required-control" ><spring:message
								code="material.management.receipt.amount" text="Receipt Amount" /></label>
							<div class="col-sm-4">
								<form:input path="expiryItemsDto.receiptAmt" id="receiptAmt" 
									onkeyup="receiptAmmount()" onkeypress="return hasAmount(event, this, 10, 2)"
									class="form-control mandColorClass" data-rule-required="true" />
							</div>								
						</div>
					</div>
										
					<div class="form-group paymentRecievedDiv" >
						<label class="control-label col-sm-2 required-control" for="Vendor Name"><spring:message
								code="purchase.requisition.vendorName" text="Vendor Name" /></label>
						<div class="col-sm-4">
							<form:select path="expiryItemsDto.vendorId" disabled="${command.saveMode eq 'V'}"
								cssClass="form-control mandColorClass" id="vendorId"
								data-rule-required="true">
								<form:option value="">
									<spring:message code="material.management.select" text="Select" />
								</form:option>
								<c:forEach items="${command.vendors}" var="vendor">
									<form:option value="${vendor.vmVendorid}" code="${vendor.vmVendorname}" >${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
								</c:forEach>
							</form:select>
						</div>		
											
						<apptags:date fieldclass="datepicker" labelCode="material.management.disposed.date" 
							datePath="expiryItemsDto.disposedDate" isMandatory="true"
							cssClass="disposedDate mandColorClass"></apptags:date>
					</div>

					<c:if test="${command.saveMode ne 'V'}">
						<div class="panel panel-default paymentRecievedDiv">
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
						</div>
					</c:if>

				</c:if>
				
				
				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-success" id="submit"
							onclick="saveApprovalForm(this)">
							<spring:message code="material.management.submit" text="Submit"></spring:message>
						</button>
					</c:if>					
					<c:choose>
						<c:when test="${command.isTenderViewEstimate eq 'Y'}">
							 <button type="button" class="button-input btn btn-danger"
								name="button-Cancel" value="Cancel" style=""
								onclick="backTenderForm();" id="button-Cancel">
								<spring:message code="material.management.back" text="Back" />
							</button>
						</c:when>
						<c:when test="${command.levelCheck eq 1}">
							<apptags:backButton url="AdminHome.html"></apptags:backButton>											
						</c:when>
						<c:otherwise>
							<apptags:backButton url="DisposalOfStock.html"></apptags:backButton>					
						</c:otherwise>
					</c:choose>
				</div>

			</form:form>
		</div>
	</div>
</div>
