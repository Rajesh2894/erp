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
				<spring:message code="material.management.stores.return.summary" text="Stores Return Summary" />
			</h2>
			<apptags:helpDoc url="StoresReturn.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>

			<form:form action="StoresReturn.html" name="storesReturnSummary" id="storesReturnSummaryId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
					<span aria-hidden="true">&times;</span></button><span id="errorId"></span>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message code="material.management.store.return.no" 
							text="Store Return No." /></label>
					<div class="col-sm-4">
						<form:select path="storesReturnDTO.storeReturnId" id="storeReturnId" data-rule-required="true"
							class="form-control chosen-select-no-results" >
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storesReturnDTOList}" var="storereturn">
								<form:option value="${storereturn.storeReturnId}">${storereturn.storeReturnNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="col-sm-2 control-label"><spring:message code="material.management.mdn.no" 
							text="MDN No." /></label>
					<div class="col-sm-4">
						<form:select path="storesReturnDTO.mdnId" id="mdnId" data-rule-required="true"
							class="form-control chosen-select-no-results" >
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.mdnIdNameList}" var="mdnObject">
								<form:option value="${mdnObject[0]}">${mdnObject[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

                <div class="form-group">
                	<apptags:date fieldclass="datepicker" labelCode="material.item.fromDate" 
						datePath="storesReturnDTO.fromDate" cssClass="fromDateClass" />
					
					<apptags:date fieldclass="datepicker" labelCode="material.item.toDate" 
						datePath="storesReturnDTO.toDate" cssClass="toDateClass" />						
                </div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="material.management.issuing.store" text="Issuing Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="storesReturnDTO.issueStoreId" id="issueStoreId"
							class="chosen-select-no-results form-control" data-rule-required="true" >
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeIdNameList}" var="data">
								<form:option value="${data[0]}">${data[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="col-sm-2 control-label" for=""><spring:message
							code="material.management.requesting.store" text="Requesting Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="storesReturnDTO.requestStoreId" id="requestStoreId"
							class="chosen-select-no-results form-control" data-rule-required="true" >
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeIdNameList}" var="data">
								<form:option value="${data[0]}">${data[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" onclick="searchStoreReturnData()">
						<i class="fa fa-search"></i><spring:message code="material.management.search" text="Search" />
					</button>

					<button type="button" class="btn btn-warning" onclick="window.location.href='StoresReturn.html'">
						<i class="fa fa-refresh"></i><spring:message code="material.management.reset" text="Reset" />
					</button>

					<button type="submit" class="button-input btn btn-success" name="button-Add" id="button-submit"
						onclick="addStoreReturnForm('StoresReturn.html', 'addStoresReturnForm')" >
						<i class="fa fa-plus-circle"></i><spring:message code="material.management.add" text="Add" />
					</button>
				</div>

				<div class="table-responsive">
					<table id="storesReturnTableID" summary="Store Return Summary" class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<th width="5%"><spring:message code="material.management.SrNo" text="Sr.No" /></th>
								<th><spring:message code="material.management.store.return.no" text="Store Return No." /></th>
								<th><spring:message code="material.management.return.date" text="Return Date" /></th>
								<th><spring:message code="material.management.mdn.no" text="MDN No." /></th>
								<th><spring:message code="material.management.store.indent.no" text="Store Indent No." /></th>
								<th><spring:message code="material.management.issuing.store" text="Issuing Store Name" /></th>
								<th><spring:message code="material.management.requesting.store" text="Requesting Store Name" /></th>
								<th width="10%"><spring:message code="material.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>				
							<c:forEach items="${command.storesReturnDTOList}" var="data" varStatus="index">
								<tr>
									<td class="text-center">${index.count}</td>
									<td class="text-center">${data.storeReturnNo}</td>
									<td class="text-center"><fmt:formatDate value="${data.storeReturnDate}" pattern="dd/MM/yyyy" /></td>
									<td class="text-center">${data.mdnNumber}</td>
									<td class="text-center">${data.storeIndentNo}</td>
									<td align="center">${data.issueStoreName}</td>
									<td align="center">${data.requestStoreName}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getStoreReturnById('StoresReturn.html', 'viewStoresReturnForm', ${data.storeReturnId})"
											title="<spring:message code="material.management.view" text="View" ></spring:message>">
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
