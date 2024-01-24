<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" type="text/css" />
<script type="text/javascript" src="assets/libs/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css" type="text/css">
<script type="text/javascript" src="js/mainet/bootstrap-multiselect.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/material_mgmt/master/binMas.js" type="text/javascript"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header" id="hiddenDiv">
			<h2>
				<spring:message code="binLocationMaster.heading" text="Bin Location Master" ></spring:message>
			</h2>
		</div>
		<div class="mand-label clearfix">
			<span><spring:message code="material.management.mand"
					text="Field with" /> <i class="text-red-1">*</i><spring:message
					code="material.management.mand.field" text="is mandatory" /> </span>
		</div>
		<div class="widget-content padding ">
			
			<form:form action="BinLocationMas.html" name="binLocationMasForm" 
					id="binLocationMasFormId" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible" id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close">
						<span aria-hidden="true">&times;</span></button>
					<span id="errorId"></span>
				</div>
				
				<form:hidden path="modeType" id="modeType" />

				<div class="form-group">
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="store.master.name" text="Store Name" /></label>
					<div class="col-sm-4">
						<form:select path="binLocMasDto.storeId" id="storeId"
							class="chosen-select-no-results form-control" data-rule-required="true" 
							disabled="${command.modeType eq 'V'}" onchange="getStoreDetails()">
							<form:option value="">
								<spring:message code="material.item.master.select" text="Select" />
							</form:option>
							<c:forEach items="${command.storeObjectList}" var="storeObject">
								<form:option value="${storeObject[0]}" code="${storeObject[2]}">${storeObject[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="store.master.locationStore" text="Store Location" /></label>
					<div class="col-sm-4">
						<form:input id="storeLocation" path="binLocMasDto.storeLocation"
							class="form-control" data-rule-required="true" readonly="true"/>
					</div>
                </div>
                
                <div class="form-group">
                	<label class="control-label col-sm-2 required-control"> <spring:message
							code="binLocMasDto.binLocation" text="Bin Location" /></label>
					<div class="col-sm-4">
						<form:input id="binLocation" path="binLocMasDto.binLocation"
							class="form-control" data-rule-required="true"
							data-rule-maxLength="200" readonly="${command.modeType eq 'V'}" />
					</div>
                
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="store.master.address" text="Store Address" /></label>
					<div class="col-sm-4">
						<form:textarea id="storeAdd" path="binLocMasDto.storeAdd"
							class="form-control" data-rule-required="true"
							data-rule-maxLength="200" readonly="true"/>
					</div>
                </div>
                
                <h4><spring:message code="material.bin.location.mapping" text="Bin Location Mapping"/></h4>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for="itemType" ><spring:message
							code="binLocMasDto.binDefinition" text="Definition" /></label>					
					<div class="col-sm-4">
						<form:select path="binLocMasDto.definitions" id="definitions" multiple="true"
							cssClass="form-control mandColorClass chosen-select-no-results"
							data-rule-required="true" disabled="${command.modeType eq 'V'}">
							<c:forEach items="${command.defList}" varStatus="status" var="def">
								<c:choose>
									<c:when test="${fn:contains(command.binLocMasDto.definitions, def.binDefId)}">
										<form:option value="${def.binDefId}" selected="${fn:contains(command.binLocMasDto.definitions, def.binDefId)}">${def.defName}</form:option>
									</c:when>
									<c:otherwise>
								    	<form:option value="${def.binDefId}">${def.defName}</form:option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="text-center padding-bottom-20" id="divSubmit">
					<c:if test="${command.modeType ne 'V'}">
						<button type="button" class="btn btn-success btn-submit"
							id="submit" onclick="proceedLocMas(this)">
							<spring:message code="material.management.submit" text="Submit" />
						</button>
					</c:if>
					<input type="button" class="btn btn-danger"
						onclick="javascript:openRelatedForm('BinLocationMas.html');"
						value="<spring:message code="material.management.back" text="Back"/>" />
				</div>
			</form:form>
		</div>
	</div>
</div>