<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/validity_master/licenseValidityMaster.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="license.validity.title"
					text="License Validity Entry Form" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>

			<form:form action="LicenseValidityMaster.html"
				name="licenseValidateForm" id="licenseValidateForm"
				class="form-horizontal" commandName="command">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="deptShortName" id="DeptShortName" />
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="license.validity.dept.name" text="Department Name" /></label>

					<div class="col-sm-4">

						<form:select path="masterDto.deptId" id="departmentId"
							class="chosen-select-no-results" data-rule-required="true"
							onchange="searchServicesBydeptId()"
							disabled="${command.saveMode eq 'V' ? true : false}">
							<form:option value="0">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.deparatmentList}" var="department">
								<form:option value="${department.dpDeptid}">${department.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>

					<label class="col-sm-2 control-label"><spring:message
							code="license.validity.service.name" text="Service Name" /></label>
					<div class="col-sm-4">
						<form:select path="masterDto.serviceId" id="serviceId"
							class="form-control mandColorClass chosen-select-no-results"
							disabled="${command.saveMode eq 'V' ? true : false}">
							<form:option value="0">
								<spring:message code='adh.select' />
							</form:option>
							<c:forEach items="${command.serviceList}" var="serviceList">
								<form:option value="${serviceList.smServiceId}">${serviceList.smServiceName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="license.validity.validate.depends.on" text="" /></label>
					<c:set var="baseLookupCode" value="LED" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="masterDto.licDependsOn" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="adh.select" isMandatory="true"
						showOnlyLabel="Validity Depends On"
						disabled="${command.saveMode eq 'V' ? true : false}" />


					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="license.validity.license.type" text="" /></label>
					<c:set var="baseLookupCode" value="LIT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="masterDto.licType" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="adh.select" isMandatory="true"
						showOnlyLabel=""
						disabled="${command.saveMode eq 'V' ? true : false}" />

				</div>
				<div class="form-group">
					<apptags:input labelCode="license.validity.tenure"
						cssClass="hasNumber" path="masterDto.licTenure"
						isDisabled="${command.saveMode eq 'V' ? true : false}" maxlegnth="3"></apptags:input>

					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="license.validity.unit" text="" /></label>
					<c:set var="baseLookupCode" value="UTS" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="masterDto.unit" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="adh.select" isMandatory="true"
						showOnlyLabel=""
						disabled="${command.saveMode eq 'V' ? true : false}" />

				</div>
				<c:if test="${command.envSpec eq 'Y'}">
					<div class="form-group" id="LicCatSubCategry">
						<label class="col-sm-2 control-label"><spring:message
								code="license.categiry" text="License Category" /></label>
						<div class="col-sm-4">

							<form:select path="masterDto.triCod1" id="triCodeList1"
								class="chosen-select-no-results" data-rule-required="true"
								onchange="searchLicenseSubCatagory()"
								disabled="${command.saveMode eq 'V' ? true : false}">
								<form:option value="0">
									<spring:message code="adh.select" text="Select"></spring:message>
								</form:option>
								<c:forEach items="${command.triCodList1}" var="lookup">
									<form:option value="${lookup.lookUpId}">${lookup.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>

						<label class="col-sm-2 control-label"><spring:message
								code="license.sub.category" text="License SubCategory" /></label>
						<div class="col-sm-4">
							<form:select path="masterDto.triCod2" id="triCodeList2"
								class="for -control mandC
							lorClass chosen-select-no-results"
								disabled="${command.saveMode eq 'V' ? true : false}">
								<form:option value="0">
									<spring:message code='adh.all' text="All" />
								</form:option>
								<c:forEach items="${command.triCodList2}" var="lookUP">
									<form:option value="${lookUP.lookUpId}">${lookUP.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</c:if>
				<div class="text-center">
					<c:if test="${command.saveMode eq 'A'}">
						<button type="button" class="btn btn-success"
							data-toggle="tooltip" data-original-title="Save"
							onclick="save(this)">
							<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
							<spring:message code="adh.save" text="Save"></spring:message>
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success"
							data-toggle="tooltip" data-original-title="Save"
							onclick="save(this)">
							<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
							<spring:message code="adh.save" text="Save"></spring:message>
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'A'}">
						<button type="button" class="btn btn-warning"
							onclick="resetForm()">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="adh.reset" text="Reset"></spring:message>
						</button>
					</c:if>
					<button type="button" class="btn btn-danger" data-toggle="tooltip"
						data-original-title="Back"
						onclick="window.location.href='LicenseValidityMaster.html'">
						<i class="fa fa-chevron-circle-left padding-right-5"
							aria-hidden="true"></i>
						<spring:message code="adh.back" text="Back"></spring:message>
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

