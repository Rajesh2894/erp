<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/material_mgmt/service/purchaseReturn.js"></script>
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
				<spring:message code="material.management.purchaseReturnSummary"
					text="Purchase Return Summary" />
			</h2>
			<apptags:helpDoc url="PurchaseReturn.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>

			<form:form action="PurchaseReturn.html" name="PurchaseReturnSummary"
				id="purchaseReturnSummaryId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close"><span aria-hidden="true">&times;</span>
					</button><span id="errorId"></span>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="purchaseReturnNo"><spring:message
							code="material.management.purchaseReturnNo"
							text="Purchase Return No." /></label>
					<div class="col-sm-4">
						<form:select path="purchaseReturnDto.returnId" id="purRetId"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.purchaseReturnDtoList}" var="purReturn">
								<form:option value="${purReturn.returnId}">${purReturn.returnNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label" for="grnId"><spring:message
							code="material.management.grnNo" text="GRN No." /></label>
					<div class="col-sm-4">
						<form:select path="purchaseReturnDto.grnId" id="grnId" class="chosen-select-no-results"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.goodsReceivedNotesDtoList}" var="grnList">
								<form:option value="${grnList.grnid}" code="${grnList.grnno}">${grnList.grnno}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<apptags:date fieldclass="datepicker" labelCode="material.item.fromDate" 
						datePath="purchaseReturnDto.fromDate" cssClass="fromDateClass" />

					<apptags:date fieldclass="datepicker" labelCode="material.item.toDate" 
						datePath="purchaseReturnDto.toDate" cssClass="toDateClass" />
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="store"><spring:message
							code="material.management.store" text="Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="purchaseReturnDto.storeId" id="storeId" class="chosen-select-no-results"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeNameList}" var="Store">
								<form:option value="${Store.storeId}">${Store.storeName}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2 required-control"
						for="Vendor Name"><spring:message
							code="purchase.requisition.vendorName" text="Vendor Name" /></label>
					<div class="col-sm-4">
						<form:select path="purchaseReturnDto.vendorId" disabled="${command.saveMode eq 'V'}"
							cssClass="form-control mandColorClass" id="vendorId"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.vendors}" var="vendor">
								<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2" onclick="searchPurchaseReturnSummaryData(this)">
						<i class="fa fa-search"></i><spring:message code="material.management.search" text="Search" />
					</button>

					<button type="button" class="btn btn-warning" onclick="window.location.href='PurchaseReturn.html'">
						<i class="fa fa-refresh"></i><spring:message code="material.management.reset" text="Reset" />
					</button>

					<button type="submit" class="btn btn-success"
						onclick="addPurchaseReturnForm('PurchaseReturn.html','addPurchaseReturnForm');" >
						<i class="fa fa-plus-circle"></i><spring:message code="material.management.add" text="Add" />
					</button>
				</div>

				<div class="table-responsive">
					<table id="purchaseReturnItems" summary="Purchase Return Data"
						class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<th><spring:message code="material.management.SrNo" text="Sr.No" /></th>
								<th><spring:message code="material.management.purchaseReturnNo" text="Purchase Return No." /></th>
								<th><spring:message code="material.management.returnDate" text="Return Date" /></th>
								<th><spring:message code="material.management.grnNo" text="GRN No." /></th>
								<th><spring:message code="material.management.poNo" text="PO No." /></th>
								<th><spring:message code="material.management.store" text="Store Name" /></th>
								<th><spring:message code="purchase.requisition.vendorName" text="Vendor Name" /></th>
								<th width="150"><spring:message code="material.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>				
							<c:forEach items="${command.purchaseReturnDtoList}" var="data" varStatus="index">
								<tr>
									<td class="text-center">${index.count}</td>
										<td class="text-center">${data.returnNo}</td>
										<td class="text-center"><fmt:formatDate
												value="${data.returnDate}" pattern="dd/MM/yyyy" /></td>
										<td class="text-center">${data.grnNo}</td>
										<td class="text-center">${data.poNo}</td>
										<td class="text-center">${data.storeName}</td>
										<td class="text-center">${data.vendorName}</td>
										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm" title="<spring:message code="material.management.view"  text="view" />"
												onclick="getPurchaseReturnData('PurchaseReturn.html','viewPurchaseReturnForm',${data.returnId})">
												<i class="fa fa-eye"></i>
											</button></td>
								</tr>
							</c:forEach>	
						</tbody>
					</table>
				</div>
			</form:form>
		</div>
	</div>
</div>
