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
	src="js/material_mgmt/master/StoreMaster.js"></script>
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
				<spring:message code="store.master.heading"
					text="Store Master" />
			</h2>
			<apptags:helpDoc url="StoreMaster.html" />
		</div>
		<div class="widget-content padding">
			<form:form action="StoreMaster.html"
				name="StoreMaster"
				id="StoreMasterId" class="form-horizontal">
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
					<label class="control-label col-sm-2" for="Store Location"><spring:message
							code="store.master.locationStore" text="Store Location" /></label>
					<div class="col-sm-4">
						<form:select path="StoreMasterDTO.location"
							cssClass="form-control chosen-select-no-results mandColorClass" id="locationId"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="material.management.select" text="Select" />
							</form:option>
							 <c:forEach items="${command.locList}" var="lookUp">
								<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
							</c:forEach> 
						</form:select>
					</div>
					
					
					<label class="control-label col-sm-2" for="Store Name"><spring:message
							code="store.master.name" text="Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="StoreMasterDTO.storeName" id="storeName"
							class="chosen-select-no-results form-control" data-rule-required="true" >
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeMasterSummmaryDataList}" var="data">
								<form:option value="${data.storeName}">${data.storeName}</form:option>
							</c:forEach>
						</form:select>
					</div>
					

					<%-- <label class="control-label col-sm-2" for="Store Name"><spring:message
							code="store.master.name" text="Store Name" /></label>
					<div class="col-sm-4">
						<form:input name="name" path="StoreMasterDTO.storeName" id="storeName"
							type="text" class="form-control"></form:input>
					</div> --%>
				</div>
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search"
						onclick="searchStoreMaster(this);">
						<i class="fa fa-search"></i>
						<spring:message code="material.management.search" text="Search"></spring:message>
					</button>
					<button type="button" class="btn btn-warning" onclick="resetStoreMaster();">
						<i class="fa fa-refresh"></i>
						<spring:message code="material.management.reset" text="Reset"></spring:message>
					</button>
					<button type="button" class="btn btn-success add"
						onclick="addStoreForm('StoreMaster.html','addStoreMaster');">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="material.management.add"
							text="Add"/>
					</button>
					<button type="button" class="btn btn-primary add"
						onclick="addStoreForm('StoreMaster.html','excelUploadMaster');">
						<i class="fa fa-plus-circle"></i>
						<spring:message code="material.item.master.listImportExport" text="Import/Export" />
					</button>
				</div>
				<div class="table-responsive">
					<table id="id_storemaster" summary="Store Master Data"
						class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<th><spring:message code="store.master.code" text="Store Code"/></th>
								<th><spring:message code="store.master.name" text="Store Name"/></th>
								<th><spring:message code="store.master.location" text="Location"/></th>
								<th><spring:message code="store.master.store.incharge" text="Store InCharge" /></th>
								<th><spring:message code="store.master.status" text="Status" /></th>
								<th width="150"><spring:message code="material.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
						   <c:forEach items="${command.storeMasterSummmaryDataList}" var="data" varStatus="index">
								<tr>
									<td class="text-center">${data.storeCode}</td>
									<td class="text-center">${data.storeName}</td>
									<td class="text-center">${data.locationName}</td>									
									<td class="text-center">${data.storeInchargeName}</td>	
									<td class="text-center">
										<c:choose>
											<c:when test="${data.status eq 'Y'}">
												<spring:message code="material.management.active" text="Active" />
											</c:when>
											<c:when test="${data.status eq 'N'}">
												<spring:message code="material.management.inActive" text="Inactive" />
											</c:when>
										</c:choose>
									</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
												title="<spring:message code="material.management.view" text="View" />"
												onclick="getStoremasterData('StoreMaster.html','viewStoreMaster',${data.storeId})">
												<i class="fa fa-eye"></i></button>
										<button type="button" class="btn btn-warning btn-sm"
											onClick="getStoremasterData('StoreMaster.html','editStoreMaster',${data.storeId})"
											title="<spring:message code="material.management.edit" text="Edit" />">
											<i class="fa fa-pencil"></i></button>
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

