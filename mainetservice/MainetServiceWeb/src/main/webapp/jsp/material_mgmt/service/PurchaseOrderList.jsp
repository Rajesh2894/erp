<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/material_mgmt/service/purchaseOrder.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="purchase.Order.Summary"
					text="Purchase Order Summary" />
			</h2>
			<apptags:helpDoc url="PurchaseOrder.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="PurchaseOrder.html" name="PurchaseOrder"
				id="PurchaseOrderId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2" for="Store Name"><spring:message
							code="store.master.name" text="Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="purchaseOrderDto.storeId"
							cssClass="form-control mandColorClass chosen-select-no-results"
							id="storeNameId" data-rule-required="true">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeIdNameList}" var="data">
								<form:option value="${data[0]}">${data[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
		
					<label class="control-label col-sm-2" for="Vendor Name"><spring:message
							code="purchase.requisition.vendorName" text="Vendor Name" /></label>
					<div class="col-sm-4">
						<form:select path="purchaseOrderDto.vendorId"
							cssClass="form-control chosen-select-no-results" id="vendorName"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.vendors}" var="vendor">
								<form:option value="${vendor.vmVendorid}" code="">${vendor.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<apptags:date fieldclass="datepicker" labelCode="material.item.fromDate" 
						datePath="purchaseOrderDto.fromDate" cssClass="fromDateClass" />

					<apptags:date fieldclass="datepicker" labelCode="material.item.toDate"
						datePath="purchaseOrderDto.toDate" cssClass="toDateClass" />
				</div>
				
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" onclick="searchPurchaseOrder(this);">
						<i class="fa fa-search"></i>
						<spring:message code="material.management.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" onclick="window.location.href='PurchaseOrder.html'">
						<i class="fa fa-refresh"></i>
						<spring:message code="material.management.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addPurchaseOrderForm('PurchaseOrder.html','addPurchaseOrder');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="material.management.add" text="Add" />
					</button>
				</div>
				
				<div class="table-responsive">
					<table id="id_purchaseOrder" summary="Purchase Order Data"
						class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<th><spring:message code="material.management.poNo" text="PO No" /></th>
								<th><spring:message code="material.po.date" text="PO Date" /></th>
								<th><spring:message code="store.master.name" text="Store Name" /></th>
								<th><spring:message code="purchase.requisition.vendorName" text="Vendor Name" /></th>
								<th><spring:message code="store.status" text="Status" /></th>
								<th><spring:message code="material.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.purchaseOrderDtoList}" var="data"
								varStatus="index">
								<tr>
									<td class="text-center">${data.poNo}</td>
									<td class="text-center"><fmt:formatDate
											pattern="dd/MM/yyyy" value="${data.poDate}" /></td>
									<td class="text-center">${data.storeName}</td>
									<td class="text-center">${data.vendorName}</td>
									<td class="text-center">
										<c:if test="${data.status eq 'D'}">Draft</c:if>
										<c:if test="${data.status eq 'Y'}">Approved</c:if>
									</td>									
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm" title="<spring:message code="material.management.view" text="View" />"
											onclick="getPurchaseOrderData('PurchaseOrder.html','viewPurchaseOrder',${data.poId})">
											<i class="fa fa-eye"></i>
										</button>
										<c:if test="${data.status eq 'D'}">
											<button type="button" class="btn btn-warning btn-sm"
												onClick="getPurchaseOrderData('PurchaseOrder.html','editPurchaseOrder',${data.poId})"
												title="<spring:message code="material.management.edit" text="Edit" />">
												<i class="fa fa-pencil"></i>
											</button>
											<button type="button" class="btn btn-danger btn-sm"
												onClick="deletePurchaseOrderData('PurchaseOrder.html','deletePurchaseOrder',${data.poId})"
												title="<spring:message code="material.management.delete" text="Delete" />">
												<i class="fa fa-trash"></i>
											</button>
										</c:if>
									</td>									
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
			</form:form>
		</div>
	</div>
</div>
