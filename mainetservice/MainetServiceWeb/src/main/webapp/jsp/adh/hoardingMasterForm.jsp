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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/adh/hoardingMaster.js"></script>

<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="hoarding.master.entry.title"
					text="Hoarding Master Entry"></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span> <spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message>
				</span>
			</div>
			<form:form action="HoardingMaster.html" name="hoardingMasterEntry"
				id="hoardingMasterEntry" cssClass="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp"></jsp:include>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="saveMode" id="saveMode" />
                          <div class="form-group">
                          <div class="col-sm-8">
                          </div>
							   <c:if test="${command.getGisValue() eq 'Y'}">
							   <!-- Defect #126903 -->
										 <%-- <button type="button" class="btn btn-success btn-submit"
												onclick=" window.open('${command.gISUri}&uniqueid=${command.hoardingMasterDto.hoardingNumber}')"
												id="">
												<spring:message text="Map hoarding on GIS map" code="" />
											</button>
											<button class="btn btn-blue-2" type="button"
												onclick=" window.open('${command.gISUri}&id=${command.hoardingMasterDto.hoardingNumber} )'"
												id="">
												<spring:message text="View hoarding on GIS map" code="" />
											</button> --%>
										 </c:if>
							</div>
				<div class="form-group">
					<div id="hrdNumber">
						<apptags:input labelCode="hoarding.label.number"
							path="hoardingMasterDto.hoardingNumber" isDisabled="true"></apptags:input>
					</div>

					<apptags:input labelCode="hoarding.label.old.number"
						path="hoardingMasterDto.hoardingOldNumber" maxlegnth="20" ></apptags:input>
				</div>

				<div class="form-group">
					<apptags:date labelCode="hoarding.master.registration.date"
						datePath="hoardingMasterDto.hoardingRegDate"
						fieldclass="datepicker" isMandatory="true"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>
					<apptags:input
						labelCode="hoarding.entry.label.hoarding.description"
						path="hoardingMasterDto.hoardingDescription" maxlegnth="100"
						isMandatory="true"></apptags:input>
				</div>

				<div class="form-group">
					<!-- <This is for Hierarchical> -->
					<apptags:lookupFieldSet baseLookupCode="ADH" hasId="true"
						pathPrefix="hoardingMasterDto.hoardingTypeId"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" isMandatory="true"
						cssClass="form-control margin-bottom-10" showAll="false"
						columnWidth="20%" />
				</div>

				<div class="form-group">
					<apptags:input labelCode="hoarding.entry.label.hoarding.length"
						path="hoardingMasterDto.hoardingLength" isMandatory="true"
						cssClass="hasDecimal" onChange="calcuateArea();" maxlegnth="10" ></apptags:input>
					<apptags:input labelCode="hoarding.entry.label.hoarding.height"
						path="hoardingMasterDto.hoardingHeight" isMandatory="true"
						cssClass="hasDecimal" onChange="calcuateArea();" maxlegnth="10" ></apptags:input>
				</div>

				<div class="form-group">
					<apptags:input labelCode="hoarding.label.area"
						path="hoardingMasterDto.hoardingArea" isDisabled="true"></apptags:input>

					<label class="col-sm-2 control-label required-control"> <spring:message
							code="hoarding.label.hoarding.location" text="Location"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select path="hoardingMasterDto.locationId" id="locationId"
							class="chosen-select-no-results" data-rule-required="true"
							onchange="getLocationMapping(this);">
							<form:option value="">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.locationList}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<!-- <This is for Non Hierarchical> -->
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="hoarding.label.hoarding.status" text="Hoarding Status" /></label>
					<c:set var="baseLookupCode" value="HRS" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="hoardingMasterDto.hoardingStatus" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="adh.select" isMandatory="false"
						showOnlyLabel="Status" />

					<apptags:textArea labelCode="hoarding.entry.label.hoarding.remarks"
						path="hoardingMasterDto.hoardingRemark" maxlegnth="100"></apptags:textArea>
				</div>


				<div class="form-group">
					<!-- <This is for Non Hierarchical> -->
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="hoarding.label.land.ownership" text="Land Ownership" /></label>
					<c:set var="baseLookupCode" value="ONT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="hoardingMasterDto.hoardingPropTypeId"
						cssClass="form-control" hasChildLookup="false" hasId="true"
						showAll="false" selectOptionLabelCode="adh.select"
						isMandatory="true" showOnlyLabel="Status" />
					<apptags:input labelCode="hoarding.label.property.no"
						path="hoardingMasterDto.hoardingPropertyId" maxlegnth="16" ></apptags:input>
				</div>

				<div class="form-group">
					<apptags:input
						labelCode="hoarding.entry.label.hoarding.property.owner"
						path="hoardingMasterDto.propOwnerName" maxlegnth="100"></apptags:input>

					<apptags:input labelCode="hoarding.label.gis.number"
						path="hoardingMasterDto.gisId" maxlegnth="20"></apptags:input>
				</div>

				<%-- <div class="form-group">
					<!-- <This is for Hierarchical> -->
					<apptags:lookupFieldSet baseLookupCode="ADZ" hasId="true"
						pathPrefix="hoardingMasterDto.hoardingZone"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" isMandatory="true"
						cssClass="form-control margin-bottom-10" showAll="false"
						columnWidth="20%" />
				</div> --%>

				<div class="form-group">
					<label class="col-sm-2 control-label " for=""><spring:message
							code="hoarding.label.display.direction" text="Display Direction" /></label>
					<c:set var="baseLookupCode" value="DSP" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="hoardingMasterDto.displayTypeId" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="adh.select" isMandatory="true"
						showOnlyLabel="Display Direction" />

					<apptags:radio 
						radioLabel="Authorized,Unauthorized" radioValue="A,U"
						labelCode="hoarding.label.flag"
						path="hoardingMasterDto.hoardingFlag" defaultCheckedValue="A"></apptags:radio>
				</div>
				<div class="text-center">
					<c:if test="${command.saveMode eq 'A' || command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success"
							data-toggle="tooltip" data-original-title="Save"
							onclick="save(this)">
							<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
							<spring:message code="adh.save" text="Save"></spring:message>
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'A'}">
						<button type="button" class="btn btn-warning"
							data-toggle="tooltip" data-original-title="Reset"
							onclick="resetForm(this);">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="adh.reset" text="Reset"></spring:message>
						</button>
					</c:if>
					<button type="button" class="btn btn-danger" data-toggle="tooltip"
						data-original-title="Back"
						onclick="window.location.href='HoardingMaster.html'">
						<i class="fa fa-chevron-circle-left padding-right-5" aria-hidden="true"></i>
						<spring:message code="adh.back" text="Back"></spring:message>
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>