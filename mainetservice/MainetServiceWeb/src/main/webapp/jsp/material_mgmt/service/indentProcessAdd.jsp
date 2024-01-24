<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css">
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/material_mgmt/master/indentProcess.js" type="text/javascript"></script>
<div id="searchAssetPage">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="Department.Indent" text="Department Indent" />
				</h2>
				<apptags:helpDoc url="IndentProcess.html"></apptags:helpDoc>
			</div>
			<div class="pagediv">
				<div class="widget-content padding">
					<div class="mand-label clearfix">
						<span><spring:message code="solid.waste.mand" text="Field with"></spring:message><i class="text-red-1">*</i>
								<spring:message code="solid.waste.mand.field" text="is mandatory"></spring:message>
						</span>
					</div>
					
					<form:form id="indAddFrm" name="indentProcessForm" class="form-horizontal" action="IndentProcess.html" method="post">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div class="error-div alert alert-danger alert-dismissible" id="errorDiv" style="display: none;">
							<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
							<span aria-hidden="true">&times;</span></button><span id="errorId"></span>
						</div>
						
						<div class="panel-group accordion-toggle" id="accordion_single_collapse">
							<div class="panel panel-default">
								<h4 class="panel-title">
									<a data-target="#a1" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#a1"><spring:message
											code="department.indent.indentor.details" text="Indenter Details" /></a>
								</h4>
								<div id="a1" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="form-group">
											<apptags:date fieldclass="datepicker" labelCode="department.indent.date" isMandatory="true"
												datePath="indentProcessDTO.indentdate" cssClass="custDate mandColorClass">
											</apptags:date>
										</div>

										<div class="form-group">
											<label class="control-label col-sm-2 required-control"><spring:message
													code="department.indent.indentor.name" text="Indentor Name" /></label>
											<div class="col-sm-4">
												<form:select path="indentProcessDTO.indenter" cssClass="form-control chosen-select-no-results"
													id="indenter" onchange="getEmpDetails()">
													<form:option value="">
														<spring:message code="material.management.select" text="select" />
													</form:option>
													<c:forEach items="${command.employees}" var="employees">
														<form:option value="${employees[3]}" label="${employees[0]} ${employees[2]}"></form:option>
													</c:forEach>													
												</form:select>												
											</div>

											<form:hidden path="indentProcessDTO.deptId" id="deptId" />
											<apptags:input labelCode="department.indent.dept" path="indentProcessDTO.deptName" isReadonly="true" />
										</div>
										
										<div class="form-group">
											<form:hidden path="indentProcessDTO.desgId" id="desgId" />
											<apptags:input labelCode="department.indent.designation" path="indentProcessDTO.desgName" isReadonly="true" />

											<form:hidden path="indentProcessDTO.reportingmgr" id="reportingmgr" />
											<apptags:input labelCode="department.indent.reporting" path="indentProcessDTO.reportingMgrName" isReadonly="true" />
										</div>

										<div class="form-group">
											<apptags:input labelCode="department.indent.beneficiary" path="indentProcessDTO.beneficiary"
												cssClass="beneficiaryPattern" maxlegnth="250" />
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="panel-group accordion-toggle" id="accordion_single_collapse">
							<div class="panel panel-default">
								<h4 class="panel-title">
									<a data-target="#a2" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#a2"><spring:message
											code="department.indent.details" text="Indent Details" /></a>
								</h4>
								<div id="a2" class="panel-collapse collapse in">
									<div class="panel-body">
									
										<div class="form-group">
											<label class="control-label col-sm-2 required-control"><spring:message
													code="department.indent.storeName" text="Store Name" /></label>
											<div class="col-sm-4 ">
												<form:select path="indentProcessDTO.storeid" id="storeid" class="form-control" 
													cssClass="form-control chosen-select-no-results " data-rule-required="true"
													onchange="getLocation()">
													<form:option value="">
														<spring:message code="material.management.select" text="Select" />
													</form:option>
													<c:forEach items="${command.storeIdNameList}" var="data">
														<form:option value="${data[0]}">${data[1]}</form:option>
													</c:forEach>													
												</form:select>
											</div>
											
											<form:hidden path="indentProcessDTO.location" id="location" />
											<apptags:input labelCode="department.indent.storeLocation" path="indentProcessDTO.locationName" isReadonly="true" />
										</div>
										
										<div class="form-group">
											<apptags:input labelCode="department.indent.deliveryAdd" path="indentProcessDTO.deliveryat"
												cssClass="hasChar mandColorClass form-control" isMandatory="true" maxlegnth="250"/>
												
											<apptags:date fieldclass="datepicker" labelCode="department.indent.deliveryDate"
												datePath="indentProcessDTO.expecteddate" isMandatory="true"
												cssClass="custDate mandColorClass date"></apptags:date>
										</div>
									</div>
								</div>
							</div>
							
							<spring:message code="enter.quantity.value" text="Enter Quantity Value" var="QuantityValue" />
							<div id="initialDataTable">
								<c:set var="d" value="0" scope="page"></c:set>
								<table class="table table-bordered margin-bottom-10" id="initialTable">
									<thead>
										<tr>
											<th width=10% class="text-center"><spring:message code="store.master.srno" text="Sr.No." /></th>
											<th width=35% class="text-center"><spring:message code="department.indent.item.name" 
													text="Item Name" /><i class="text-red-1">*</i></th>
											<th width=25% class="text-center"><spring:message code="material.management.UoM" text="UoM" /></th>
											<th width=15% class="text-center"><spring:message code="department.indent.quantiy" 
													text="Quantity" /><i class="text-red-1">*</i></th>
											<th width="10%" class="text-center"><spring:message code="store.master.action" text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<tr class="withHeld">
											<td><form:input path="" id="sNo${d}" value="${d + 1}" readonly="true" 
													cssClass="form-control text-center" /></td>
											
											<td><form:select path="indentProcessDTO.item[${d}].itemid" id="itemid${d}" 
															class="form-control mandColorClass chosen-select-no-results" onchange="getUom(${d})">
													<form:option value="">
														<spring:message code="material.management.select" text="Select" />
													</form:option>
													<c:forEach items="${command.itemIdNameList}" var="item">
														<form:option value="${item[0]}" code="${item[2]}">${item[1]}</form:option>
													</c:forEach>
												</form:select></td>
	
											<td><form:input path="indentProcessDTO.item[${d}].uom"
													class="form-control hasNameClass valid" id="uom${d}" readonly="true" /></td>

											<td><form:input path="indentProcessDTO.item[${d}].quantity" id="quantity${d}"
													class="form-control text-right" placeholder="${QuantityValue}" 
													onkeypress="return hasAmount(event, this, 11, 1)" /></td>
											
											<td class="text-center"><a href="javascript:void(0);"
													onclick="addIndentDetailsWith(this);" class="btn btn-success addtable btn-sm"
													title="<spring:message code="material.management.add" text="Add" />">
													<i class="fa fa-plus-circle"> </i></a>
												<a href="javascript:void(0);" class="btn btn-danger btn-sm delButton" onclick="deleteEntry($(this));"
													title="<spring:message code="material.management.delete" text="Delete" />"> 
													<i class="fa fa-trash-o"></i></a></td>
										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
									</tbody>
								</table>
							</div>
						</div>

						<div class="text-center clear padding-10">
							<button type="button" class="btn btn-success btn-submit" title="Submit" onclick="submitIndentForm(this);">
									<spring:message code="material.management.submit" text="Submit" />
							</button>
							<button type="button" class="btn btn-warning" title="Reset" onclick="addIndent('IndentProcess.html','addIndent');">
									<spring:message code="material.management.reset" text="Reset" />
							</button>
							<apptags:backButton url="IndentProcess.html"></apptags:backButton>				
						</div>

					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>
