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
				<spring:message code="material.management.store.indent." text="Store Indent" />
			</h2>
			<apptags:helpDoc url="StoreIndent.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>

			<form:form action="StoreIndent.html" name="storeIndeentFrom"
				id="storeIndeentFormId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="levelCheck" id="levelCheck" />

				<div class="form-group">
					<c:if test="${command.saveMode ne 'A' }">
						<apptags:input labelCode="material.management.store.indent.no" path="storeIndentDto.storeIndentNo"
							isDisabled="true" />
					</c:if>
									
					<apptags:date labelCode="material.management.store.indent.date" datePath="storeIndentDto.storeIndentdate"
						fieldclass="lessthancurrdate" isMandatory="true" isDisabled="${command.saveMode ne 'A' }"></apptags:date>
				</div>


	<!--------------------------------- Requesting Store Details --------------------------------------------------->
				<div id="requestingStoreDiv">
					<h4>
						<spring:message code="material.management.request.store" text="Requesting Store" />
					</h4>
					<div class="form-group">
						<c:choose>
							<c:when test="${command.saveMode eq 'A' }">
								<label class="col-sm-2 control-label required-control" for=""><spring:message
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
							</c:when>
							<c:otherwise>
								<form:hidden path="storeIndentDto.requestStore" id="requestStore" />
								<apptags:input labelCode="material.management.requesting.store" path="storeIndentDto.requestStoreName"
									isMandatory="true" isDisabled="true" />							
							</c:otherwise>
						</c:choose>

						<form:hidden path="storeIndentDto.requestStoreLocId" id="requestStoreLocId" />
						<apptags:input labelCode="material.management.requesting.store.location" path="storeIndentDto.requestStoreLocName"
							isMandatory="true" isDisabled="true" />
					</div>

					<div class="form-group">
						<form:hidden path="storeIndentDto.requestedBy" id="requestedBy" />
						<apptags:input labelCode="material.management.requesting.store.incharge" path="storeIndentDto.requestedByName"
							isMandatory="true" isDisabled="true"/>
					</div>
				</div>


	<!--------------------------------- Issuing Store Details --------------------------------------------------->
				<div id="issueingStoreDiv">
					<h4>
						<spring:message code="material.management.issue.store" text="Issuing Store" />
					</h4>
					<div class="form-group">
						<c:choose>
							<c:when test="${command.saveMode eq 'A' }">
								<label class="col-sm-2 control-label required-control" for=""><spring:message
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
							</c:when>
							<c:otherwise>
								<form:hidden path="storeIndentDto.issueStore" id="issueStore" />
								<apptags:input labelCode="material.management.issuing.store" path="storeIndentDto.issueStoreName"
									isMandatory="true" isDisabled="true" />							
							</c:otherwise>
						</c:choose>

						<form:hidden path="storeIndentDto.issueStoreLocId" id="issueStoreLocId" />
						<apptags:input labelCode="material.management.issuing.store.location" path="storeIndentDto.issueStoreLocName"
							isMandatory="true" isDisabled="true" />
					</div>

					<div class="form-group">
						<form:hidden path="storeIndentDto.issueIncharge" id="issueIncharge" />
						<apptags:input labelCode="material.management.issuing.store.incharge" path="storeIndentDto.issueInchargeName"
							isMandatory="true" isDisabled="true" />
					</div>
				</div>


	<!--------------------------------- Indent Details --------------------------------------------------->
				<div id="indentDetailsDiv">
					<h4>
						<spring:message code="material.management.indent.details" text="Indent Details" />
					</h4>
					<div class="form-group">
						<apptags:input labelCode="material.management.delivety.at" path="storeIndentDto.deliveryAt"
							cssClass="hasChar mandColorClass form-control" isMandatory="true" 
							isDisabled="${command.saveMode ne 'A' }"/>
										
						<label class="col-sm-2 control-label required-control"><spring:message
							code="department.indent.deliveryDate" text="Expected Delivery Date" /></label>			
						<div class="col-sm-4">
							<div class="input-group">
								<form:input path="storeIndentDto.expectedDate" id="expectedDate" 
									class="form-control expectedDate" value="" placeholder="dd/mm/yyyy" 
									disabled="${command.saveMode ne 'A' }"/>
								<label class="input-group-addon" for="expectedDate"><i class="fa fa-calendar"></i><span 
									class="hide"> <spring:message text="icon"/></span><input type="hidden" id=expectedDate></label>
							</div>
						</div>
					</div>
				</div>


	<!--------------------------------- Item Details --------------------------------------------------->
				<div id="initialDataTable">
					<c:set var="d" value="0" scope="page"></c:set>
					<table class="table table-bordered margin-bottom-10"
						id="storeIndentDetailTable">
						<thead>
							<tr>
								<th width=10% class="text-center"><spring:message code="store.master.srno" text="Sr.No." /></th>
								<th width=35% class="text-center"><spring:message code="department.indent.item.name" text="Item Name" /><i
									class="text-red-1">*</i></th>
								<th width=25% class="text-center"><spring:message code="material.management.UoM" text="UoM" /><i
									class="text-red-1">*</i></th>
								<th width=20% class="text-center"><spring:message code="department.indent.requested.quantity"
										text="Requested Quantity" /><i class="text-red-1">*</i></th>
								<c:if test="${command.saveMode eq 'A' }">
									<th width="10%" class="text-center"><spring:message code="store.master.action" text="Action" /></th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when	test="${empty command.storeIndentDto.storeIndentItemDtoList}">
									<tr class="itemDetailsRow">
										<td><form:input path="" id="sNo${d}" value="${d + 1}" readonly="true" 
												cssClass="form-control text-center" />
										</td>
										<td><form:select path="storeIndentDto.storeIndentItemDtoList[${d}].itemId"
												class="form-control mandColorClass chosen-select-no-results"
												id="itemId${d}" onchange="getUom(${d})">
												<form:option value="">
													<spring:message code="material.management.select" text="Select" />
												</form:option>
												<c:forEach items="${command.itemIdNameList}" var="item">
													<form:option value="${item[0]}">${item[1]}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:input path="storeIndentDto.storeIndentItemDtoList[${d}].uomName"
												class="form-control hasNameClass valid" id="uomName${d}"
												disabled="true" /></td>

										<td><form:input path="storeIndentDto.storeIndentItemDtoList[${d}].requestedQuantity"
												maxlength="10" id="requestedQuantity${d}" disabled="${command.saveMode ne 'A' }"
												class="form-control text-right hasNumber" /></td>

										<td class="text-center">
											<a href="javascript:void(0);" onclick="addIndentDetail(this);" 
												title="<spring:message code="material.management.add" text="Add" />"
												class="btn btn-success addtable btn-sm"><i class="fa fa-plus-circle"> </i></a> 												
											<a href="javascript:void(0);" class="remItemDet btn btn-danger btn-sm delete"
												title="<spring:message code="material.management.delete" text="Delete" />"
												id=""><i class="fa fa-trash-o"></i></a> 												
									</tr>
									<c:set var="d" value="${d+1}" scope="page" />
								</c:when>
								<c:otherwise>
									<c:forEach items="${command.storeIndentDto.storeIndentItemDtoList}">
										<tr class="itemDetailsRow">
											<td><form:input path="" id="sNo${d}" value="${d + 1}"
													readonly="true" cssClass="form-control text-center" />
												<form:hidden path="storeIndentDto.storeIndentItemDtoList[${d}].siItemId" id="siItemId${d}" />
											</td>
											<td><form:hidden path="storeIndentDto.storeIndentItemDtoList[${d}].itemId" id="itemId${d}" />
												<form:input path="storeIndentDto.storeIndentItemDtoList[${d}].itemName"
													class="form-control hasNameClass valid" id="itemName${d}"
													disabled="true" /></td>

											<td><form:input path="storeIndentDto.storeIndentItemDtoList[${d}].uomName"
													class="form-control hasNameClass valid" id="uomName${d}"
													disabled="true" /></td>

											<td><form:input path="storeIndentDto.storeIndentItemDtoList[${d}].requestedQuantity"
													maxlength="10" id="requestedQuantity${d}" disabled="${command.saveMode ne 'A' }"
													class="form-control text-right hasNumber" /></td>
										</tr>
										<c:set var="d" value="${d+1}" scope="page" />
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>

	<!-------------------------------------- USer Action --------------------------------------------->
				<c:if test="${command.levelCheck eq 1 }">
					<apptags:CheckerAction hideForward="true" hideSendback="true" hideUpload="true"></apptags:CheckerAction>
				</c:if>


	<!-------------------------------------- Buttons --------------------------------------------->
				<div class="text-center padding-bottom-10">
					<c:if test="${command.saveMode ne 'V' }">
						<button type="button" class="btn btn-success" onclick="submitStoreIndentForm(this)">
							<spring:message code="material.management.submit" text="Submit" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'A' }">
						<button type="button" class="btn btn-warning"
							onclick="addStoreIndentForm('StoreIndent.html','addStoreIndentForm');">
							<spring:message code="material.management.reset" text="Reset" />
						</button>
					</c:if>
					<c:choose>
						<c:when test="${command.levelCheck eq 1 }">
							<apptags:backButton url="AdminHome.html"></apptags:backButton>
						</c:when>
						<c:otherwise>
							<apptags:backButton url="StoreIndent.html"></apptags:backButton>
						</c:otherwise>
					</c:choose>
				</div>

			</form:form>
		</div>
	</div>
</div>
