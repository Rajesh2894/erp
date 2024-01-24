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
<script type="text/javascript" src="js/material_mgmt/service/storeIndent.js"></script>
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
				<spring:message code="material.management.store.indent.summary" text="Store Indent Summary" />
			</h2>
			<apptags:helpDoc url="StoreIndent.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>

			<form:form action="StoreIndent.html" name="storeIndeentSummary" id="storeIndeentSummaryId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
					<span aria-hidden="true">&times;</span></button><span id="errorId"></span>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="material.management.requesting.store" text="Requesting Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="storeIndentDto.requestStore" id="requestStore"
							class="chosen-select-no-results form-control" data-rule-required="true"
							onchange="getRequestStoreDetails()">
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeIdNameList}" var="data">
								<form:option value="${data[0]}">${data[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message code="material.management.store.indent.no" 
							text="Store Indent No." /></label>
					<div class="col-sm-4">
						<form:select path="storeIndentDto.storeIndentId" id="storeIndentId" data-rule-required="true"
							class="form-control chosen-select-no-results" >
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeIndentDtoList}" var="storeIndent">
								<form:option value="${storeIndent.storeIndentId}">${storeIndent.storeIndentNo}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="material.management.issuing.store" text="Issuing Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="storeIndentDto.issueStore" id="issueStore"
							class="chosen-select-no-results form-control" data-rule-required="true"
							onchange="getIssuingStoreDetails()">
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeIdNameList}" var="data">
								<form:option value="${data[0]}">${data[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label "><spring:message code="material.management.store.indent.status" 
						text="Store Indent Status" /></label>
					<div class="col-sm-4">
						<form:select path="storeIndentDto.status"
							cssClass="form-control chosen-select-no-results" id="status">
							<form:option value="0">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData('IDS')}" var="lookUp">
								<c:if test="${lookUp.lookUpCode eq 'N' || lookUp.lookUpCode eq 'P' || lookUp.lookUpCode eq 'R'}">
									<form:option value="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:if>								
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" onclick="searchStoreIndentData()">
						<i class="fa fa-search"></i><spring:message code="material.management.search" text="Search" />
					</button>

					<button type="button" class="btn btn-warning" onclick="window.location.href='StoreIndent.html'">
						<i class="fa fa-refresh"></i><spring:message code="material.management.reset" text="Reset" />
					</button>

					<button type="submit" class="button-input btn btn-success" name="button-Add" id="button-submit"
						onclick="addStoreIndentForm('StoreIndent.html','addStoreIndentForm');" >
						<i class="fa fa-plus-circle"></i><spring:message code="material.management.add" text="Add" />
					</button>
				</div>

				<div class="table-responsive">
					<table id="storeInentSummaryTableID" summary="Store Inent Summary" class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<th width="5%"><spring:message code="material.management.SrNo" text="Sr.No" /></th>
								<th><spring:message code="material.management.store.indent.no" text="Store Indent No." /></th>
								<th><spring:message code="department.indent.date" text="Indent Date" /></th>
								<th><spring:message code="material.management.requesting.store" text="Requesting Store Name" /></th>
								<th><spring:message code="material.management.issuing.store" text="Issuing Store Name" /></th>
								<th width="15%"><spring:message code="store.master.status" text="Status" /></th>
								<th width="10%"><spring:message code="material.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>				
							<c:forEach items="${command.storeIndentDtoList}" var="data" varStatus="index">
								<tr>
									<td class="text-center">${index.count}</td>
									<td class="text-center">${data.storeIndentNo}</td>
									<td class="text-center"><fmt:formatDate value="${data.storeIndentdate}" pattern="dd/MM/yyyy" /></td>
									<td class="text-center">${data.requestStoreName}</td>
									<td class="text-center">${data.issueStoreName}</td>
									<td align="center"><spring:eval
                							expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp(data.status,'IDS',${UserSession.organisation}).getLookUpDesc()" var="otherField"/>${otherField}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getIndentDataById('StoreIndent.html','viewStoreIndent',${data.storeIndentId})"
											title="<spring:message code="material.management.view" text="View" />">
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
