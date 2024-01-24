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
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/material_mgmt/service/expiryItems.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="material.expired.item.entry.title" text="Expired item Summary" />
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

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="material.expired.item.entry.movement.no" text="Movement No" /></label>
					<div class="col-sm-4">
						<form:input path="expiryItemsDto.movementNo" id="itemcode"
							class="form-control mandColorClass" maxLength="500"
							data-rule-required="true" disabled="true"/>
					</div>
					<label for="fromDateId" class="col-sm-2 control-label"><spring:message
							code="material.item.fromDate" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<c:set var="now" value="<%=new java.util.Date()%>" />
							<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
							<form:input path="expiryItemsDto.movementDate" id="fromDateId"
								cssClass="mandColorClass form-control datepicker"
								data-rule-required="true" value="${date}" disabled="true"
								onchange="setInstrumentDate(this)" maxlength="10"></form:input>
							<label class="input-group-addon mandColorClass" for="fromDateId"><i
								class="fa fa-calendar"></i> </label>
							<form:hidden path="" id="fromDateId" />
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="itemCategory"><spring:message
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
					<label class="col-sm-2 control-label"><spring:message
							code="material.expired.item.entry.movement.by" text="Movement BY" /></label>
					<div class="col-sm-4">
						<form:select path="expiryItemsDto.movementBy"
							class="form-control mandColorClass" id="category"
							data-rule-required="false" disabled="true">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.vendors}" var="vendor">
									<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label for="fromDateId" class="col-sm-2 control-label"><spring:message
							code="material.expired.item.entry.expiry.on.or.before.date" text="Expiry on or Before Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<c:set var="now" value="<%=new java.util.Date()%>" />
							<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
							<form:input path="expiryItemsDto.expiryCheck" id="toDateId"
								cssClass="mandColorClass form-control datepicker"
								data-rule-required="true" value="${date}" disabled="true"
								onchange="setInstrumentDate(this)" maxlength="10"></form:input>
							<label class="input-group-addon mandColorClass" for="fromDateId"><i
								class="fa fa-calendar"></i> </label>
							<form:hidden path="" id="fromDateId" />
						</div>
					</div>
				</div>
				<h4>
					<spring:message code="material.expired.item.entry.expired.item.details" text="Expired item Details" />
				</h4>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="id_ExpiredItemDetails" summary="Store Master Data"
								class="table table-bordered table-striped rcm">
								<thead>
									<tr>
										<th><spring:message code="store.master.srno" text="Sr.No." /></th>
										<th><spring:message code="binLocMasDto.binLocation" text="Bin Location" /></th>
										<th><spring:message code="material.expired.item.entry.item.description" text="Item Description" /></th>
										<th><spring:message code="material.expired.item.entry.uom" text="UOM" /></th>
										<th><spring:message code="material.expired.item.entry.batch.no.or.serial.no" text="Batch No/Serial No" /></th>
										<th><spring:message code="material.expired.item.entry.expiry.qty" text="Expiry Qty" /></th>
									</tr>
								</thead>
								<tbody>
								<c:forEach items="${command.expiryItemsDto.expiryItemDetailsDtoList}" var="data" varStatus="index">
									<tr>
										<td class="text-center">${index.count}</td>
										<td class="text-center"><form:input
												path="expiryItemsDto.expiryItemDetailsDtoList[${d}].itemId" id="itemId"
												class="form-control mandColorClass" maxLength="500" 
												data-rule-required="true"  disabled="true"/></td>
										<td class="text-center"><form:input
												path="expiryItemsDto.expiryItemDetailsDtoList[${d}].itemNo" id="itemNo"
												class="form-control mandColorClass" maxLength="500" 
												data-rule-required="true" disabled="true"/></td>
										<td class="text-center"><form:input
												path="" id="transactionIds"
												class="form-control mandColorClass" maxLength="500" 
												data-rule-required="true"  disabled="true"/></td>
										<td class="text-center"><form:input
												path="expiryItemsDto.expiryItemDetailsDtoList[${d}].transactionId" id="itemcode"
												class="form-control mandColorClass" maxLength="500" 
												data-rule-required="true" disabled="true"/></td>
										<td class="text-center"><form:input
												path="expiryItemsDto.expiryItemDetailsDtoList[${d}].quantity" id="quantity"
												class="form-control mandColorClass" maxLength="500" 
												data-rule-required="true" disabled="true"/></td>
										
									</tr>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backForm();" id="button-Cancel">
						<spring:message code="material.management.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
