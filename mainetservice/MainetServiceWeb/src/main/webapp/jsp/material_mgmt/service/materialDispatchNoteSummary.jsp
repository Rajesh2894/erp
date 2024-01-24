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
<script type="text/javascript" src="js/material_mgmt/service/materialDispatchNote.js"></script>
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
				<spring:message code="material.management.mdn.summary.heading" text="Material Dispatch Note Summary" />
			</h2>
			<apptags:helpDoc url="MaterialDispatchNote.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>

			<form:form action="MaterialDispatchNote.html" name="MaterialDispatchNoteSummary" 
					id="MaterialDispatchNoteSummaryId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
					<span aria-hidden="true">&times;</span></button><span id="errorId"></span>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="material.management.requesting.store" text="Requesting Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="dispatchNoteDTO.requestStoreId" id="requestStoreId"
							class="chosen-select-no-results form-control" data-rule-required="true" >
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeIdNameList}" var="data">
								<form:option value="${data[0]}">${data[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message code="material.management.mdn.no" 
							text="MDN No." /></label>
					<div class="col-sm-4">
						<form:select path="dispatchNoteDTO.mdnId" id="mdnId" data-rule-required="true"
							class="form-control chosen-select-no-results" >
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.dispatchNoteDTOList}" var="dispatchNoteDTO">
								<form:option value="${dispatchNoteDTO.mdnId}">${dispatchNoteDTO.mdnNumber}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="material.management.issuing.store" text="Issuing Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="dispatchNoteDTO.issueStoreId" id="issueStoreId"
							class="chosen-select-no-results form-control" data-rule-required="true"
							onchange="">
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeIdNameList}" var="data">
								<form:option value="${data[0]}">${data[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label "><spring:message code="material.management.mdn.status" 
						text="MDN Status" /></label>
					<div class="col-sm-4">
						<form:select path="dispatchNoteDTO.status"
							cssClass="form-control chosen-select-no-results" id="status">
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData('IDS')}" var="lookUp">
								<form:option value="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="material.management.store.indent.no" text="Store Indent No." /></label>
					<div class="col-sm-4">
						<form:select path="dispatchNoteDTO.siId" id="siId"
							class="chosen-select-no-results form-control" data-rule-required="true"
							onchange="">
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeIndentList}" var="storeIndent">
								<form:option value="${storeIndent[0]}">${storeIndent[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 search" onclick="searchMaterialDispatchData()">
						<i class="fa fa-search"></i><spring:message code="material.management.search" text="Search"></spring:message>
					</button>

					<button type="button" class="btn btn-warning" onclick="window.location.href='MaterialDispatchNote.html'">
						<i class="fa fa-refresh" aria-hidden="true"></i>
						<spring:message code="material.management.reset" text="Reset"></spring:message>
					</button>

					<button type="submit" class="button-input btn btn-success" name="button-Add" id="button-submit"
						onclick="addMDNForm('MaterialDispatchNote.html','addMaterialDispatchNote');" >
						<strong class="fa fa-plus-circle"></strong>
						<spring:message code="material.management.add" text="Add" />
					</button>
				</div>

				<div class="table-responsive">
					<table id="storeInentTableId" summary="Store Inent Summary" class="table table-bordered table-striped rcm">
						<thead>
							<tr>
								<th width="5%"><spring:message code="material.management.SrNo" text="Sr.No" /></th>
								<th><spring:message code="material.management.mdn.no" text="MDN No." /></th>
								<th><spring:message code="material.management.mdn.date" text="MDN Date" /></th>
								<th><spring:message code="material.management.store.indent.no" text="Store Indent No." /></th>
								<th><spring:message code="material.management.requesting.store" text="Requesting Store Name" /></th>
								<th><spring:message code="material.management.issuing.store" text="Issuing Store Name" /></th>
								<th width="10%"><spring:message code="store.master.status" text="Status" /></th>
								<th width="10%"><spring:message code="material.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>				
							<c:forEach items="${command.dispatchNoteDTOList}" var="data" varStatus="index">
								<tr>
									<td class="text-center">${index.count}</td>
									<td class="text-center">${data.mdnNumber}</td>
									<td class="text-center"><fmt:formatDate value="${data.mdnDate}" pattern="dd/MM/yyyy" /></td>
									<td class="text-center">${data.siNumber}</td>
									<td class="text-center">${data.requestStore}</td>
									<td class="text-center">${data.issueStore}</td>
									<td align="center"><spring:eval
											expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp('${data.status}','IDS',${UserSession.organisation}).getLookUpDesc()"
											var="otherField" />${otherField}</td>
									<td class="text-center">
										<button type="button" class="btn btn-blue-2 btn-sm"
											onClick="getMDNDataById('MaterialDispatchNote.html','viewMaterialDispatch',${data.mdnId})" 
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
