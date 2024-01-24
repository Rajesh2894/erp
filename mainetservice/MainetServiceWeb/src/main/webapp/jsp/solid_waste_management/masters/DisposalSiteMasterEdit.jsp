<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/DisposalSiteMaster.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
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
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="disposal.site.master.heading"
					text="Disposal Site Master" />
			</h2>
			<apptags:helpDoc url="DisposalSiteMaster.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="DisposalSiteMaster.html" name="DisposalSiteMaster"
				id="DisposalSiteMasterId" class="form-horizontal">
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
					<label class="control-label col-sm-2" for="DisposalNo"><spring:message
							code="disposal.site.master.siteno" text="Disposal Site No." /></label>
					<div class="col-sm-4">
						<form:input name="" path="disposalMasterDTO.deId" type="text"
							class="form-control" id="DisposalNo" readonly="true"></form:input>
					</div>
					<apptags:input labelCode="disposal.site.master.sitename"
						path="disposalMasterDTO.deName" isMandatory="true"
						cssClass="form control mandcolour hasNameClass">
					</apptags:input>
					<form:hidden path="disposalMasterDTO.deNameReg"
						class="form-control" id="DisposalSiteName" value="NULL" />
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="DisposalSiteArea"><spring:message
							code="disposal.site.master.sitearea" text="Disposal Site Area" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input path="disposalMasterDTO.deArea"
								class="form-control col-xs-8 mandColorClass  hasDecimal"
								onkeypress="return isNumberKey(event)" id="DisposalSiteArea"></form:input>
							<span class="input-group-addon"><spring:message
									code="disposal.site.master.acres" text="Acres" /></span>
						</div>
					</div>
					<label class="control-label col-sm-2" for="GISId"><spring:message
							code="disposal.site.master.gisid " text="GIS ID" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input type="text" path="disposalMasterDTO.deGisId"
								class="form-control" id="GISId"></form:input>
							<span class="input-group-addon"> <strong
								class="fa fa-globe"><span class="hide"><spring:message
											code="Collection.master.latitude" text="Latitude" /></span></strong>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="CategoryofDisposalSite"><spring:message
							code="disposal.site.master.category.site"
							text="Category of Disposal Site" /></label>
					<c:set var="baseLookupCode" value="DPC" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						selectOptionLabelCode="selectdropdown"
						path="disposalMasterDTO.deCategory"
						cssClass="form-control required-control chosen-select-no-results"
						isMandatory="true" hasId="true" />
					<label class="col-sm-2 control-label required-control"
						for="Capacity"><spring:message
							code="disposal.site.master.capacity" text="Capacity" /></label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">
							<form:input type="text" path="disposalMasterDTO.deCapacity"
								class="form-control col-xs-8 mandColorClass hasDecimal"
								id="Capacity" onkeypress="return isCapacityNumberKey(event)"></form:input>
							<div class='input-group-field'>
								<form:select path="disposalMasterDTO.deCapacityUnit"
									class="form-control mandColorClass hasNumber" label="Select"
									id="deCapacityUnit">
									<form:option value="">
										<spring:message code='master.selectDropDwn' />
									</form:option>
									<c:forEach items="${command.getLevelData('UOM')}" var="lookup">
										<c:if test="${lookup.otherField eq 'WEIGHT'}">
											<form:option value="${lookup.lookUpId}"
												code="${lookup.lookUpCode}">${lookup.lookUpCode}</form:option>
										</c:if>
									</c:forEach>
								</form:select>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"
						for="Location"><spring:message
							code="route.master.location" text="Location" /></label>
					<div class="col-sm-4">
						<form:select path="disposalMasterDTO.deLocId"
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="Location" data-rule-required="true">
							<form:option value="">
								<spring:message code='master.selectDropDwn' />
							</form:option>
							<c:forEach items="${command.locList}" var="lookUp">
								<form:option value="${lookUp.locId}" code="">${lookUp.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for=""><spring:message
							code="disposal.site.master.status" text="Status" /> </label>
					<div class="col-sm-4">
						<label class="radio-inline "> <form:radiobutton
								id="status1" path="disposalMasterDTO.deActive" value="Y"
								checked="checked" /> <spring:message code="swm.Active"
								text="Active" />
						</label> <label class="radio-inline "> <form:radiobutton
								id="status2" path="disposalMasterDTO.deActive" value="N" /> <spring:message
								code="swm.Inactive" text="Inactive" />
						</label>
					</div>
					<c:if test="${ command.attachDocsList.isEmpty() }">
						<label class="col-sm-2 control-label"><spring:message
								code="swm.fileupload" /></label>
						<div class="col-sm-4">
							<small class="text-blue-2"> <spring:message code="disposal.site.master.image.upload"
									text="(Upload File upto 5MB)" />
							</small>
							<apptags:formField fieldType="7" labelCode="" hasId="true"
								fieldPath="" isMandatory="false" showFileNameHTMLId="true"
								fileSize="BND_COMMOM_MAX_SIZE"
								maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="CHECK_LIST_VALIDATION_EXTENSION"
								currentCount="0" />
						</div>
					</c:if>
				</div>
				<c:if test="${! command.attachDocsList.isEmpty() }">
					<div class="text-center padding-top-10">
						<div class="table-responsive">
							<table class="table table-bordered table-striped" id="attachDocs">
								<tr>
									<th width="20%"><spring:message code="" text="Sr. No." /></th>
									<th><spring:message code="swm.viewDocument" text="View Document" /></th>
								</tr>
								<c:forEach items="${command.attachDocsList}" var="lookUp"
									varStatus="d">
									<tr>
										<td align="center">${d.count}</td>
										<td><apptags:filedownload filename="${lookUp.attFname}"
												filePath="${lookUp.attPath}"
												actionUrl="TripSheetMaster.html?Download" /></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</c:if>
				<c:set var="baseLookupCode" value="WTY" />
				<div class="text-center padding-top-10">
					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<tr>
								<th><spring:message code="population.master.srno"
										text="Sr. No." /></th>
								<th><spring:message
										code="disposal.site.master.waste.details" text="Waste Details" /></th>
								<th><spring:message code="solid.waste.select" text="Select" /></th>
							</tr>
							<c:forEach items="${command.disposalMasterDTO.tbSwDesposalDets}"
								var="lookUp" varStatus="index">
								<tr>
									<td>${index.count}</td>
									<td>${lookUp.deWestTypeDesc}</td>
									<td class="text-center"><form:hidden
											path="disposalMasterDTO.tbSwDesposalDets[${index.index}].deWestType" />
										<form:checkbox
											path="disposalMasterDTO.tbSwDesposalDets[${index.index}].deActive"
											value="Y" /></td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-success btn-submit"
						onclick="saveDisposalForm(this);">
						<spring:message code="solid.waste.submit" text="Submit"></spring:message>
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backRouteMasterForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>