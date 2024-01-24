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
				<spring:message code="material.expired.item.entry.title" text="Expired Item Summary" />
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
					<label class="col-sm-2 control-label" for="itemCategory"><spring:message
							code="department.indent.storeName" text="Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="expiryItemsDto.storeId" class="form-control mandColorClass chosen-select-no-results"
							id="storeId" data-rule-required="false">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							 <c:forEach items="${command.storeNameList}" var="storeData">
								<form:option value="${storeData.storeId}" code="">${storeData.storeName}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="col-sm-2 control-label"><spring:message
							code="material.expired.item.entry.movement.no" text="Movement No." /></label>
					<div class="col-sm-4">
						<form:select path="expiryItemsDto.movementNo" class="form-control mandColorClass chosen-select-no-results"
							id="movementNo" data-rule-required="false">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							 <c:forEach items="${command.expiryItemDtoList}" var="expData">
								<form:option value="${expData.movementNo}">${expData.movementNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
                <div class="form-group">
                	<apptags:date fieldclass="datepicker" labelCode="material.item.fromDate" 
						datePath="expiryItemsDto.fromDate" isMandatory="true"
						cssClass="fromDateClass fromDateId" />
					
					<apptags:date fieldclass="datepicker" labelCode="material.item.toDate" 
						datePath="expiryItemsDto.toDate" isMandatory="true"
						cssClass="toDateClass toDateId" />						
                </div>
                
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" 
						title="<spring:message code="material.management.search" text="Search" />"
						onclick="searchDisposalOfStock(this);"><i class="fa fa-search"></i>
						<spring:message code="material.management.search" text="Search" />
					</button>
					<button type="button" class="btn btn-warning" onclick="javascript:openRelatedForm('DisposalOfStock.html');" 
						title="<spring:message code="rstBtn" text="Reset" />">
						<i class="fa fa-refresh"></i><spring:message code="rstBtn" text="Reset" />
					</button>
					<button type="button" class="btn btn-success add" title="<spring:message code="material.management.add" text="Add" />"
						onclick="addDisposalOfStockForm('DisposalOfStock.html','addDisposalOfStock');">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="material.management.add" text="Add" />
					</button>
				</div>
				<div class="table-responsive">
					<table id="id_DisposalOfStock" summary="Store Master Data"
						class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<th><spring:message code="store.master.srno" text="Sr.No." /></th>
								<th><spring:message code="material.expired.item.entry.movement.no" text="Movement No." /></th>
								<th><spring:message code="material.management.disposal.no" text="Disposal No." /></th>
								<th><spring:message code="material.expired.item.entry.movement.date" text="Movement Date" /></th>
								<th><spring:message code="department.indent.storeName" text="Store" /></th>
								<th><spring:message code="material.management.disposed.quantity" text="Disposed Quantity" /></th>
								<th><spring:message code="store.master.status" text="Status" /></th>
								<th><spring:message code="material.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>				
							<c:forEach items="${command.expiryItemDtoList}" var="data" varStatus="index">
								<tr>
									<td class="text-center">${index.count}</td>
									<td class="text-center">${data.movementNo}</td>
									<td class="text-center">${data.scrapNo}</td>
									<td class="text-center"><fmt:formatDate value="${data.movementDate}" pattern="dd/MM/yyyy" /> </td>
									<td class="text-center">${data.storeName}</td>
									<td class="text-center">${data.totalDisposedQuantity}</td>
									<td class="text-center">
										<c:choose>
											<c:when test="${data.status eq 'Y'}">
												<spring:message code="material.management.pending.for.approval" 
													text="Pending For Approval" />
											</c:when>
											<c:when test="${data.status eq 'A'}">
												<spring:message code="material.management.approved" text="Approved" />
											</c:when>
											<c:when test="${data.status eq 'R'}">
												<spring:message code="purchase.requisition.rejected" text="Rejected" />
											</c:when>
											<c:when test="${data.status eq 'D'}">
												<spring:message code="material.management.disposed" text="Disposed" />
											</c:when>
											<c:when test="${data.isContractDone eq 'Y' && data.status ne 'D'}">
												<spring:message code="material.management.receipt.entry.pending" text="Pending for Receipt Entry" />
											</c:when>
											<c:otherwise>
												<spring:message code="material.management.tender.inprocess" text="Tender Inprocess" />
											</c:otherwise>
										</c:choose>
									</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm" 
											title="<spring:message code="material.management.view" text="View" />"
											onclick="getExpiryItemData('DisposalOfStock.html','viewDisposalOfStock',${data.expiryId})">
											<i class="fa fa-eye"></i></button>
										<c:choose>
											<c:when test="${data.paymentFlag eq 'Y' }">
												<button onclick="printContent(${data.expiryId});" class="btn btn-primary" 
													type="button" title="<spring:message code="vehicle.fuel.print" text="Print" />">
													<strong class="fa fa-print"></strong>
												</button> 
											</c:when>
											<c:when test="${data.isContractDone eq 'Y' }">
												<button type="button" class="btn btn-success btn-sm" title="<spring:message code="material.management.receipt.entry" text="Receipt Entry" />"
													onclick="getExpiryItemData('DisposalOfStock.html','disposalReceiptEntryForm',${data.expiryId})">
													<strong class="fa fa-file-text-o"></strong></button>
											</c:when>
										</c:choose>
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


