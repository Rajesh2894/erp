<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/material_mgmt/service/GoodsRecievedNotes.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start info box -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="goods.received.note.heading" text="Goods Received Note Summary" />
			</h2>
			<apptags:helpDoc url="GoodsReceivedNotesItem.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="PurchaseOrder.html" name="PurchaseOrder" id="PurchaseOrderId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
						<span aria-hidden="true">&times;</span></button>
					<span id="errorId"></span>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="grnid"><spring:message
							code="material.management.grnNo" text="PO No."/></label>
					<div class="col-sm-4">
						<form:select path="goodsNotesDto.grnid" cssClass="form-control mandColorClass chosen-select-no-results"
							id="grnid" data-rule-required="true">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							<c:forEach items="${command.goodsReceivedNotesDtoList}" var="grnList">
								<form:option value="${grnList.grnid}" code="${grnList.grnno}">${grnList.grnno}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="col-sm-2 control-label "><spring:message code="material.management.poNo" /></label>
					<div class="col-sm-4">
						<form:select path="goodsNotesDto.poid" id="poid" data-rule-required="true"
							class="form-control chosen-select-no-results input-group" >
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.purchaseOrderObjects}" var="poObject">
								<form:option value="${poObject[0]}">${poObject[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<apptags:date fieldclass="datepicker" labelCode="material.item.fromDate"
						datePath="goodsNotesDto.fromDate" cssClass="fromDateClass fromDate"></apptags:date>

					<apptags:date fieldclass="datepicker" labelCode="material.item.toDate" 
					 	datePath="goodsNotesDto.toDate" cssClass="toDateClass fromDateClass"></apptags:date>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="Store.Name" /></label>
					<div class="col-sm-4">
						<form:select path="goodsNotesDto.storeid" id="storeId" data-rule-required="true"
							class="form-control chosen-select-no-results" >
							<form:option value="">
								<spring:message code="Item.Select" />
							</form:option>
							<c:forEach items="${command.storeIdAndNameList}" var="storeMas">
								<form:option value="${storeMas[0]}">${storeMas[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" onclick="searchGoodsReceivedNote(this);">
						<i class="fa fa-search"></i>
						<spring:message code="material.management.search" text="Search"></spring:message>
					</button>
					
					<button type="button" class="btn btn-warning resetSearch"
						onclick="window.location.href = 'GoodsReceivedNotesItem.html'">
						<i class="fa fa-refresh"></i>
						<spring:message code="material.management.reset" text="Reset" />
					</button>
					
					<button type="button" class="btn btn-success add" onclick="openForm('GoodsReceivedNotesItem.html','add')">
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="material.management.add" text="Add" />
					</button>
				</div>
				
				<div class="table-responsive">
					<table id="id_goodsReceivedNote" summary="Store Master Data"
						class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<th width="5%" align="center"><spring:message code="store.master.srno" text="Sr.No" /></th>
								<th align="center"><spring:message code="material.management.grnNo" text="GRN NO" /></th>
								<th align="center"><spring:message code="material.management.grnDate" text="GRN Date" /></th>
								<th align="center"><spring:message code="material.management.poNo" text="PO NO" /></th>
								<th align="center"><spring:message code="store.master.name" text="Store Name" /></th>
								<th width="10%" align="center"><spring:message code="material.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.goodsReceivedNotesDtoList}" var="notes" varStatus="item">
								<tr>
									<td class="text-center">${item.count}</td>
									<td align="center">${notes.grnno}</td>
									<td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${notes.receiveddate }" /></td>
									<td align="center">${notes.poNumber}</td>
									<td align="center">${notes.storeName}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm" title="<spring:message code="material.management.view" text="View" />"
											onclick="getPurchaseOrderData('GoodsReceivedNotesItem.html','ViewGoodsReceivedNotes',${notes.grnid})">
											<i class="fa fa-eye"></i>
										</button>
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
