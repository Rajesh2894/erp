<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/material_mgmt/service/grnInspection.js"></script>
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
				<spring:message code="material.management.goodsInspection" text="Goods Inspection" />
			</h2>
			<apptags:helpDoc url="GoodsReceivedNoteInspection.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="material.management.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="material.management.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="GoodsReceivedNoteInspection.html" name="Goods Received Note Inspection"
				id="goodsReceivedNoteInspection" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
					
				<form:hidden path="inspectionStatus" id="inspectionStatus" />	
				<form:hidden path="saveMode" id="saveMode" />	
				<form:hidden path="indexCount" id="indexCount" />
				
				
					
				<div class="form-group">
					<c:choose>
						<c:when test="${command.saveMode eq 'A' }">
							<label class="col-sm-2 control-label required-control" for="grnid"><spring:message
									code="material.management.grnNo" /></label>
							<div class="col-sm-4">
								<form:select path="goodsReceivedNotesDto.grnid"
									cssClass="form-control mandColorClass chosen-select-no-results"
									id="grnid" data-rule-required="true">
									<form:option value="">
										<spring:message code="material.management.select" text="Select" />
									</form:option>
									<c:forEach items="${command.grnObjectList}" var="grnList">
										<form:option value="${grnList[0]}" code="${grnList[1]}">${grnList[1]}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</c:when>
						<c:otherwise>
							<apptags:input labelCode="material.management.grnNo" path="goodsReceivedNotesDto.grnno" 
								isMandatory="true" isReadonly="true" />
						</c:otherwise>
					</c:choose>
					

					<c:if test="${command.saveMode eq 'A' }">
						<div class="col-sm-6">
							<button type="button" class="btn btn-blue-2 search"
								onclick="searchInspectionData(this)">
								<i class="fa fa-search"></i>
								<spring:message code="material.management.search" text="Search"></spring:message>
							</button>
						</div>					
					</c:if>
				</div>
				
				<div class="form-group">
					<apptags:input labelCode="material.management.receivedDate" path="goodsReceivedNotesDto.receiveddate" 
							isMandatory="true" isReadonly="true" />

					<form:hidden path="goodsReceivedNotesDto.storeid" id="storeId" />
					<apptags:input labelCode="material.management.store" path="goodsReceivedNotesDto.storeName" 
							isMandatory="true" isReadonly="true" />
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="poNo"><spring:message
							code="material.management.poNo" text="Purchase Order No." /></label>
					<div class="col-sm-4">
						<form:hidden path="goodsReceivedNotesDto.poid" id="poId" />
						<form:input type="text" class="form-control preventSpace"
							disabled="true" path="goodsReceivedNotesDto.poNumber" id="poNo"
							data-rule-required="true"></form:input>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="material.management.inspectionDate" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="goodsReceivedNotesDto.inspectiondate" placeHolder="dd/mm/yyyy"
								id="inspectiondate" class="form-control mandColorClass datepicker dateValidation"
								readonly="false" maxLength="10" />
							<label class="input-group-addon" for="inspectiondate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=inspectiondate></label>
						</div>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="inspectiorName"><spring:message
							code="material.management.inspectorName" /></label>
					<div class="col-sm-4">
						<form:select path="goodsReceivedNotesDto.inspectorname" id="inspectorName"
							cssClass="form-control required-control chosen-select-no-results"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.employeesObject}" var="employees">
								<form:option value="${employees[3]}">${employees[0]} ${employees[2]}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<!-- Table -->
				<div class="widget-header">
					<h4>
						<spring:message code="material.management.receivedGoodsEntry"
							text="Received Items Details" />
					</h4>
				</div>
				
				<div id="grnInspection">
					<c:set var="e" value="0" scope="page"></c:set>
					<table class="table table-bordered margin-bottom-10" id="unitDetailTable">
						<thead>
							<tr>
								<th class="text-center"><spring:message code="material.management.SrNo" text="Sr No" /></th>
								<th class="text-center"><spring:message code="material.item.master.name" 
									text="Item Name" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.management.UoM" 
									text="UoM" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.management.itemManagementMethod" 
									text="Item Management Method" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.management.receivedQuantity" 
									text="Received Quantity" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.management.acceptedQuantity" 
									text="Accepted Quantity" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.management.rejectedQuantity" 
									text="Rejected Quantity" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.management.remarks" 
									text="Remarks" /><i class="text-red-1">*</i></th>
								<th class="text-center"><spring:message code="material.management.action" text="Action" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${command.goodsReceivedNotesDto.goodsreceivedNotesItemList}" var="data" varStatus="loop">
							<c:set value="${loop.index}" var="count"></c:set>
							<tr class="firstUnitRow">
									<td width="8%"><form:input path="" class="form-control text-center" disabled="true" id="sequence${count}" value="${count+1}" /></td>
									<td><form:input path="goodsReceivedNotesDto.goodsreceivedNotesItemList[${count}].itemDesc" 
												id="itemDesc${count}" type="text" class="form-control" readonly="true" /></td>																		
									<td><form:input path="goodsReceivedNotesDto.goodsreceivedNotesItemList[${count}].uomDesc" 
											id="uomDesc${count}" type="text" class="form-control" readonly="true" /></td>																		
									<td><form:hidden path="goodsReceivedNotesDto.goodsreceivedNotesItemList[${count}].management" 
											id="management${count}" />
										<form:input path="goodsReceivedNotesDto.goodsreceivedNotesItemList[${count}].managementCode" 
												id="managementCode${count}" type="text" class="form-control" readonly="true" /></td>																		
									<td><form:input path="goodsReceivedNotesDto.goodsreceivedNotesItemList[${count}].receivedqty" 
											id="receivedqty${count}" type="text" class="form-control" readonly="true" /></td>																		
									<td><form:input path="goodsReceivedNotesDto.goodsreceivedNotesItemList[${count}].acceptqty" 
											id="acceptqty${count}" type="text" class="form-control" readonly="true" /></td>									
									<td><form:input path="goodsReceivedNotesDto.goodsreceivedNotesItemList[${count}].rejectqty" 
											id="rejectqty${count}" type="text" class="form-control" readonly="true" /></td>
									<td><form:input path="goodsReceivedNotesDto.goodsreceivedNotesItemList[${count}].inspectorremarks"
											id="inspectorremarks${count}" type="text" class="form-control" /></td>
									<td>
										<div class="text-center">
											<button type="button"
												class="btn btn-blue-3 btn-sm margin-right-5"
												onclick="viewInspectionItemsForm(this,'${e}');"
												title="<spring:message code="material.management.inspectionDetails" 
												text="Inspection Details" />"><i class="fa fa-building-o"></i>
											</button>
										</div>										
									</td>
								</tr>
							<c:set var="e" value="${e+1}" scope="page" />
							</c:forEach>
						</tbody>						
					</table>

					<div class="text-center padding-bottom-10">	
						<c:if test="${command.saveMode ne 'V' }">
						    <!-- To save GRN Inspection in Dtraft mode :: Status 'D' -->				
							<button type="button" class="btn btn-blue-2" onclick="saveForm(this,'D');">
								<spring:message code="master.save" text="Save"></spring:message>
							</button>
						    <!-- To Submit GRN Inspection :: mode == Status 'I'-->				
							<button type="button" class="btn btn-success" onclick="saveForm(this,'I')">
								<spring:message code="material.management.submit" text="Submit"></spring:message>
							</button>
						</c:if>
						<c:if test="${command.saveMode eq 'A' }">
							<button type="button" class="btn btn-warning"
								onclick="openForm('GoodsReceivedNoteInspection.html','addGrnInspection')">
								<spring:message code="material.management.reset" text="Reset"></spring:message>
							</button>
						</c:if>
						<button type="button" class="button-input btn btn-danger" name="button-Cancel" 
							value="Cancel" onclick="window.location.href='GoodsReceivedNoteInspection.html'"
							id="button-Cancel"><spring:message code="solid.waste.back" text="Back" />
						</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

