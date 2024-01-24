<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/material_mgmt/master/itemMaster.js"></script>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<style>
.tooltip-inner {
	text-align: left;
}

.margin-top-10 {
	margin-top: 10px;
}
</style>
<script>
	$(document).ready(function(){
		
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);
		}

		<c:if test="${MODE_DATA == 'EDIT'}">
			var fundRequired = $('#isasset').is(':checked');
			if(fundRequired){
				$("#classification").prop("disabled",false);
			}
			var fundRequired = $('#isexpiry').is(':checked');
			if(fundRequired){
				$("#expirytype").prop("disabled",false);
			}
		</c:if>
	});
</script>
<c:if test="${tbMGItemMaster.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
</c:if>
<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="material.item.master.title" text="Item Master" />
		</h2>
		<apptags:helpDoc url="ItemMaster.html" helpDocRefURL="ItemMaster.html"></apptags:helpDoc>
	</div>
	<div class="widget-content padding">
		<form:form id="frmMaster" class="form-horizontal"
			modelAttribute="tbMGItemMaster" name="frmMaster" method="POST"
			action="ItemMaster.html">
			<form:hidden path="" value="${keyTest}" id="keyTest" />
			<form:hidden path="" id="indexdata" />
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand" text="Field with" /> <i
					class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /></span>
			</div>
			<form:hidden path="hasError" />
			<div class="warning-div alert alert-danger alert-dismissible hide"
				id="errorDivScrutiny">
				<button type="button" class="close" aria-label="Close"
					onclick="closeErrBox()">
					<span aria-hidden="true">&times;</span>
				</button>
				<ul>
					<li><form:errors path="*" /></li>
				</ul>
			</div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>

			<jsp:include page="/jsp/tiles/validationerror.jsp" />

			<c:set var="count" value="0" scope="page" />

			<ul id="ulId">
				<li>
					<fieldset id="divId" class="clear">

						<h4>
							<spring:message code="material.item.master.itemDetails" text="Item Details"></spring:message>
						</h4>

						<c:if test="${MODE_DATA != 'create'}">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="material.item.master.itemcode" text="Item Code" /></label>
								<div class="col-sm-4">
								<form:input path="itemCode"
									id="itemcode" class=" form-control mandColorClass" maxLength="500"
										data-rule-required="true" readonly="${viewMode ne 'true' }" />
								</div>
							</div>
						</c:if>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="material.item.master.name" text="Item Name" /></label>
							<div class="col-sm-4">
								<form:input path="name"
									id="name" class=" form-control mandColorClass" maxLength="200"
										data-rule-required="true"  onchange="CheckName()"/>
							</div>

							
							<label class="col-sm-2 control-label "><spring:message
									code="material.item.master.baseuom" text="Base UoM" /></label>
							<div class="col-sm-4">
								<form:select path="uom" class="form-control mandColorClass"
									id="uom" disabled="${viewMode}" data-rule-required="false">
									<form:option value="">
										<spring:message code="material.management.select" text="Select" />
									</form:option>
									<c:forEach items="${itemMasterUoMMap}" varStatus="status"
										var="levelChild">
										<form:option code="${levelChild.lookUpCode}"
											value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>

						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="material.item.master.description" text="Description" /></label>
							<div class="col-sm-10">
								<form:textarea id="description" path="description"
										class="form-control mandColorClass" maxLength="255" 
										tabindex="-1" data-rule-required="true" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="material.item.master.category" text="Category" /></label>
							<div class="col-sm-4">
								<form:select path="category" class="form-control mandColorClass"
									id="category" disabled="${viewMode}" data-rule-required="false">
									<form:option value="">
										<spring:message code="material.management.select" text="Select" />
									</form:option>
									<c:forEach items="${itemMasterCategoryMap}" varStatus="status"
										var="levelChild">
										<form:option code="${levelChild.lookUpCode}"
											value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="material.item.master.type" text="Item Type" /></label>
							<div class="col-sm-4">
								<form:select path="type" class="form-control" id="type">
									<form:option value="">
										<spring:message code="material.management.select" text="Select" />
									</form:option>
									<c:forEach items="${itemTypeMap}" varStatus="status"
										var="levelChild">
										<form:option code="${levelChild.lookUpCode}"
											value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="material.item.master.group" text="Item Group" /></label>
							<div class="col-sm-4">
								<form:select path="itemGroup"
									class="form-control mandColorClass" id="itemgroup"
									disabled="${viewMode}" data-rule-required="false">
									<form:option value="">
										<spring:message code="material.management.select" text="Select" />
									</form:option>
									<c:forEach items="${itemMasterGroupMap}" varStatus="status"
										var="levelChild">
										<form:option code="${levelChild.lookUpCode}"
											value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="material.item.master.subgroup" text="Item SubGroup" /></label>
							<div class="col-sm-4">
								<form:select path="itemSubGroup" class="form-control"
									id="itemsubgroup">
									<form:option value="">
										<spring:message code="material.management.select" text="Select" />
									</form:option>
									<c:forEach items="${itemMasterSubGroupMap}" varStatus="status"
										var="levelChild">
										<form:option code="${levelChild.lookUpCode}"
											value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="material.item.master.itemvalmethod" text="Item Valuation Method" /></label>
							<div class="col-sm-4">
								<form:select path="valueMethod" class="form-control"
									id="valuemethod">
									<form:option value="">
										<spring:message code="material.management.select" text="Select" />
									</form:option>
									<c:forEach items="${itemMasterValuationMethodsMap}" varStatus="status"
										var="levelChild">
										<form:option code="${levelChild.lookUpCode}"
											value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="material.item.master.itemMgdmethod" text="Item Managed Method" /></label>
							<div class="col-sm-4">
								<form:select path="management" class="form-control"
									id="management">
									<form:option value="">
										<spring:message code="material.management.select" text="Select" />
									</form:option>
									<c:forEach items="${itemMasterMgmtMethodsMap}" varStatus="status"
										var="levelChild">
										<form:option code="${levelChild.lookUpCode}"
											value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="material.item.master.minstocklevel" text="Min Stock Level" /></label>
							<div class="col-sm-4">
								<form:input path="minLevel"
									id="minlevel" class="form-control hasNumber mandColorClass"
										data-rule-required="true" />
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="material.item.master.reorderlevel" text="ReOrder Level" /></label>
							<div class="col-sm-4">
								<form:input path="reorderLevel"
									id="reorderlevel" class="form-control hasNumber mandColorClass"
										data-rule-required="true" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="material.item.master.isasset" text="Is Asset" /></label>
							<div class="col-sm-4">
								<form:checkbox id="isasset" path="isAsset" value="Y"
									class="margin-top-10" onclick="isEnableClassification()" />
							</div>

							<label class="col-sm-2 control-label"><spring:message
									code="material.item.master.classification" text="Classification" /></label>
							<div class="col-sm-4">
								<form:select path="classification" class="form-control"
									id="classification" disabled="true">
									<form:option value="">
										<spring:message code="material.management.select" text="Select" />
									</form:option>
									<c:forEach items="${itemMasterAssetOfMasMap}"
										varStatus="status" var="levelChild">
										<form:option code="${levelChild.lookUpCode}"
											value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="material.item.master.isexpiry" text="Is Expiry" /></label>
							<div class="col-sm-4">
								<form:checkbox id="isexpiry" path="isExpiry" value="Y"
									class="margin-top-10" onclick="isEnableExpiryType()" />
							</div>

							<label class="col-sm-2 control-label"><spring:message
									code="material.item.master.expirytype" text="Expiry Type" /></label>
							<div class="col-sm-4">
								<form:select path="expiryType" class="form-control"
									id="expirytype" disabled="true">
									<form:option value="">
										<spring:message code="material.management.select" text="Select" />
									</form:option>
									<c:forEach items="${itemMasterExpiryMethodsMap}"
										varStatus="status" var="levelChild">
										<form:option code="${levelChild.lookUpCode}"
											value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label required-control"><spring:message
									code="material.item.master.taxper" text="Tax %" /></label>
							<div class="col-sm-4">
								<form:input path="taxPercentage" onkeypress="return hasAmount(event, this, 3, 2)"
									id="taxpercentage" class="form-control hasTaxNumber mandColorClass"
										data-rule-required="true" />
							</div>

							<label class="col-sm-2 control-label required-control"><spring:message
									code="material.item.master.hsncode" text="HSN Code" /></label>
							<div class="col-sm-4">
								<form:input path="hsnCode"
									id="hsncode" class="form-control hasMyNumber mandColorClass"
										data-rule-required="true" onchange="minValueValidation()"/>
							</div>
						</div>
						
						<c:if test="${MODE_DATA != 'create'}">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="store.master.status" text="Status" /></label>
								<div class="col-sm-4">
								<form:select path="status" class="form-control"
									id="Status">
									<form:option value="">
										<spring:message code="material.management.select" text="Select" />
									</form:option>
									<c:forEach items="${activeDeActiveMap}"
										varStatus="status" var="levelChild">
										<form:option code="${levelChild.lookUpCode}"
											value="${levelChild.lookUpCode}">${levelChild.descLangFirst}</form:option>
									</c:forEach>
								</form:select>
								</div>
							</div>
						</c:if>

						<h4>
							<spring:message code="material.item.master.headline2" text="UoM Conversion"></spring:message>
						</h4>

						<form:hidden path="itemId" />
						<div id="Itemid" class="">
							<div class="table-overflow-sm" id="budRevTableDivID">
								<table class="table  table-bordered table-striped "
									id="budRevTable">
									<c:if test="${MODE_DATA == 'create'}">
										<tr>
											<th scope="col" width="45%"><spring:message code="material.item.master.conversionuom"
													text="Conversion UoM" /><i class="text-red-1">*</i></th>
											<th scope="col" width="45%"><spring:message code="material.item.master.conversionqty"
													text="Conversion Quantity" /><i class="text-red-1">*</i></th>
											<th class="text-center" scope="col" width="10%"><spring:message
														code="material.management.action" text="Action" /></span></th>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'EDIT'}">
										<tr>
											<th scope="col" width="50%"><spring:message code="material.item.master.conversionuom"
													text="Conversion UoM" /><i class="text-red-1">*</i></th>
											<th scope="col" width="50%"><spring:message code="material.item.master.conversionqty"
													text="Conversion Quantity" /><i class="text-red-1">*</i></th>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'create'}">
										<tr id="Itemid" class="appendableClass">
											<td><form:select id="convuom${count}"
													path="itemMasterConversionDtoList[${count}].convUom"
													cssClass="form-control mandColorClass"
													disabled="${viewMode}" data-rule-required="true"
													onchange="checkBaseUoMValue(${count})">
													<form:option value="">
														<spring:message code="material.management.select" text="Select" />
													</form:option>
													<c:forEach items="${itemMasterUoMMap}" varStatus="status"
														var="levelChild">
														<form:option code="${levelChild.lookUpCode}"
															value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td><form:input path="itemMasterConversionDtoList[${count}].units"
														id="units${count}" class=" form-control hasNumber mandColorClass"
														data-rule-required="true" /></td>
											<td class="text-center">
												<button title="<spring:message code="material.management.add" text="Add" />"
													class="btn btn-success btn-sm addButton" id="addButton${count}">
													<i class="fa fa-plus-circle"></i>
												</button>
												<button title="<spring:message code="material.management.delete" text="Delete" />" 
													class="btn btn-danger btn-sm delButton" id="delButton${count}">
													<i class="fa fa-trash-o"></i>
												</button>
											</td>
										</tr>
									</c:if>

									<c:if test="${MODE_DATA == 'EDIT'}">
									<c:forEach items="${tbMGItemMaster.itemMasterConversionDtoList}"
																var="itemMasterLevel" varStatus="status">
										<c:set value="${status.index}" var="count"></c:set>
										<form:hidden path="itemMasterConversionDtoList[${count}].convId"
													id="convid" value="${itemMasterConversionDtoList[count].convid}" />
										<form:hidden path="itemMasterConversionDtoList[${count}].itemId"
													id="itemid" value="${itemMasterConversionDtoList[count].itemid}" />
										<tr id="Itemid" class="appendableClass">
											<td><form:select id="convuom${count}"
													path="itemMasterConversionDtoList[${count}].convUom"
													cssClass="form-control mandColorClass"
													disabled="${viewMode}" data-rule-required="true"
													onchange="checkBaseUoMValue(${count})">
													<form:option value="">
														<spring:message code="material.management.select" text="Select" />
													</form:option>
													<c:forEach items="${itemMasterUoMMap}" varStatus="status"
														var="levelChild">
														<form:option code="${levelChild.lookUpCode}"
															value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											<td><form:input path="itemMasterConversionDtoList[${count}].units"
														id="units${count}" class=" form-control hasNumber mandColorClass"
														data-rule-required="true" /></td>
										</tr>
									</c:forEach>
									</c:if>

								</table>
							</div>
						</div>
					</fieldset>
				</li>
			</ul>

			<INPUT type="hidden" id="count" value="0" />
			<INPUT type="hidden" id="countFinalCode" value="0" />

			<div class="text-center padding-top-10">
				<c:if test="${MODE_DATA == 'create' || MODE_DATA == 'EDIT'}">
					<input type="button" id="saveBtn"
						class="btn btn-success btn-submit" onclick="saveLeveledData(this)"
						value="<spring:message code="material.management.submit" text="Submit" />" />
				</c:if>
				<c:if test="${MODE_DATA == 'create'}">
					<input type="button" id="Reset" class="btn btn-warning createData"
						value="<spring:message code="material.management.reset" text="Reset" />" />
				</c:if>
				<spring:url var="cancelButtonURL" value="ItemMaster.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message
						code="material.management.back" text="Back" /></a>
			</div>
		</form:form>
	</div>
</div>
<c:if test="${tbMGItemMaster.hasError =='true'}">
	</div>
</c:if>

