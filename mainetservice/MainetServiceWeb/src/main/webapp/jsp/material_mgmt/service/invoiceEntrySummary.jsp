<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/material_mgmt/service/invoiceEntry.js"></script>
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
				<spring:message code="material.management.invoice.entry.summary" text="Store Invoice Entry Summary" />
			</h2>
			<apptags:helpDoc url="StoreInvoiceEntry.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>

			<form:form action="StoreInvoiceEntry.html" name="storeInvoiceEntrySummary" id="storeInvoiceEntrySummaryId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
					<span aria-hidden="true">&times;</span></button><span id="errorId"></span>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="material.management.invoice.number" text="Invoice Number" /></label>
					<div class="col-sm-4">
						<form:select path="invoiceEntryDTO.invoiceId" id="invoiceId"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.invoiceEntryDTOList}" var="invoiceList">
								<form:option value="${invoiceList.invoiceId}">${invoiceList.invoiceNo}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message code="Purchase.order.number" 
							text="Purchase Order No." /></label>
					<div class="col-sm-4">
						<form:select path="invoiceEntryDTO.poId" id="poId" data-rule-required="true"
							class="form-control chosen-select-no-results" >
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.purchaseOrderDtoList}" var="purchase">
								<form:option value="${purchase.poId}">${purchase.poNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<apptags:date fieldclass="datepicker" labelCode="material.item.fromDate" 
						datePath="invoiceEntryDTO.fromDate" cssClass="fromDateClass" />

					<apptags:date fieldclass="datepicker" labelCode="material.item.toDate" 
						datePath="invoiceEntryDTO.toDate" cssClass="toDateClass" />
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="store"><spring:message
							code="material.management.store" text="Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="invoiceEntryDTO.storeId" id="storeId" data-rule-required="true" 
							class="form-control chosen-select-no-results" >
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeIdNameList}" var="data">
								<form:option value="${data[0]}">${data[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="control-label col-sm-2" for="Vendor Name"><spring:message
							code="purchase.requisition.vendorName" text="Vendor Name" /></label>
					<div class="col-sm-4">
						<form:select path="invoiceEntryDTO.vendorId" id="vendorId" data-rule-required="true"
							cssClass="form-control mandColorClass" >
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
					<button type="button" class="btn btn-blue-2 search" onclick="searchStoreInvoiceEntryData()">
						<i class="fa fa-search"></i><spring:message code="material.management.search" text="Search" />
					</button>

					<button type="button" class="btn btn-warning" onclick="window.location.href='StoreInvoiceEntry.html'">
						<i class="fa fa-refresh"></i><spring:message code="material.management.reset" text="Reset" />
					</button>

					<button type="submit" class="button-input btn btn-success" name="button-Add" id="button-submit"
						onclick="addInvoiceEntryForm('StoreInvoiceEntry.html','addInvoiceEntryForm');" >
						<i class="fa fa-plus-circle"></i><spring:message code="material.management.add" text="Add" />
					</button>
				</div>

				<div class="table-responsive">
					<table id="invoiceItemDetailsTableID" summary="Invoice Entry Data" class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<th><spring:message code="material.management.SrNo" text="Sr.No" /></th>
								<th><spring:message code="material.management.invoice.number" text="Invoice Number" /></th>
								<th><spring:message code="material.management.invoice.date" text="Invoice Date" /></th>
								<th><spring:message code="material.management.invoice.amount" text="Invoice Amount" /></th>
								<th><spring:message code="material.management.poNo" text="PO No." /></th>
								<th><spring:message code="material.management.store" text="Store Name" /></th>
								<th><spring:message code="purchase.requisition.vendorName" text="Vendor Name" /></th>
								<th><spring:message code="store.master.status" text="Status" /></th>
								<th width="150"><spring:message code="material.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>				
							<c:forEach items="${command.invoiceEntryDTOList}" var="data" varStatus="index">
								<tr>
									<td class="text-center">${index.count}</td>
									<td class="text-center">${data.invoiceNo}</td>
									<td class="text-center"><fmt:formatDate value="${data.invoiceDate}" pattern="dd/MM/yyyy" /></td>
									<td class="text-center">${data.invoiceAmt}</td>
									<td class="text-center">${data.poNumber}</td>
									<td class="text-center">${data.storeName}</td>
									<td class="text-center">${data.vendorName}</td>
									<td class="text-center">
										<c:choose>
											<c:when test="${data.invoiceStatus eq 'P'}">
												<spring:message code="material.management.pending.for.approval" text="Pending For Approval" />
											</c:when>
											<c:when test="${data.invoiceStatus eq 'A'}">
												<spring:message code="material.management.approved" text="Approved" />
											</c:when>  
											<c:when test="${data.invoiceStatus eq 'R'}">
													<spring:message code="purchase.requisition.rejected" text="Rejected" />
											</c:when>
										</c:choose></td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm" title="<spring:message code="material.management.view" text="View" />"
												onclick="getStoreInvoiceEntryData('StoreInvoiceEntry.html','viewStoreInvoiceEntryForm',${data.invoiceId})" >
												<i class="fa fa-eye"></i>
										</button>
									</tr>
							</c:forEach>	
						</tbody>
					</table>
				</div>
				
			</form:form>
		</div>
	</div>
</div>
