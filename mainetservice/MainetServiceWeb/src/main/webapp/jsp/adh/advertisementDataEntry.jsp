<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript" src="js/adh/advertisementDataEntry.js"></script>
<!-- End JSP Necessary Tags -->
<style>.bluebtn{background-color:#3498db!important}</style>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-content padding">
			<div class="widget-header">
				<h2>
					<spring:message code="adh.advertisement.details.title"
						text="Advertisement Details"></spring:message>
				</h2>
			</div>
			<form:form action="AdvertisementDataEntry.html"
				name="AdvertisementDataEntry" id="AdvertisementDataEntry"
				cssClass="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp"></jsp:include>
				<form:hidden path="licMaxTenureDays" id="licMaxTenureDays" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="removeAdverId" id="removeAdverId" />
				<!-- ------------------------------------ Applicant Information Start ---------------------------- -->

				<h4 class="margin-top-10">
					<spring:message code="adh.new.advertisement.applicant.information"
						text="Applicant Information"></spring:message>
				</h4>
				<div id="ApplicantInformation">
					<jsp:include page="/jsp/adh/applicantInformation.jsp"></jsp:include>
				</div>

				<!-- ------------------------------------ Applicant Information End ---------------------------- -->
				<h4 class="margin-top-10">
					<spring:message code="adh.new.advertisement.applicant.details"
						text="Applicant Details"></spring:message>
				</h4>
				<div class="form-group">
				
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="adh.label.advertiser.category" text="Advertiser Category" /></label>
					<div class="col-sm-4">
						<form:select path="advertisementDto.appCategoryId"
							cssClass="form-control chosen-select-no-results"
							id="advertiseCategory" onchange="getAgencyDetails();">
							<form:option value="">
								<spring:message code="adh.select" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData('ADC')}"
								var="licenseType">
								<form:option value="${licenseType.lookUpId}"
									code="${licenseType.lookUpCode}">${licenseType.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
				<%-- <label class="col-sm-2 control-label required-control" for=""><spring:message
							code="adh.label.license.type" text="License Type" /></label>
					<c:set var="baseLookupCode" value="LIT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="advertisementDto.licenseType" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="adh.select" isMandatory="true"
						showOnlyLabel="License Type" /> --%>
						
						<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="adh.label.license.type" text="License Type" /></label>
					<div class="col-sm-4">
						<form:select
							path="advertisementDto.licenseType"
							onchange="getLicType()"
							class="form-control mandColorClass" id="licenseType" data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData('LIT')}"
								var="licenseType">
								<form:option value="${licenseType.lookUpId}"
									code="${licenseType.lookUpCode}">${licenseType.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				  <div class="form-group">
					<apptags:date labelCode="adh.label.license.from.date"
						datePath="advertisementDto.licenseFromDate"
						fieldclass="datepicker" isMandatory="true"></apptags:date>
					  <apptags:date labelCode="adh.label.license.to.date"
						datePath="advertisementDto.licenseToDate" fieldclass="datepicker" isMandatory="true"></apptags:date>  
						
				</div> 
				
				
				

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="adh.label.location.type" /></label>
					<div class="col-sm-4">
						<form:select path="advertisementDto.locCatType" id="locCatType"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" />
							</form:option>
							<form:option value="E">
								<spring:message code="adh.existing.location"
									text="Existing Location" />
							</form:option>
							<form:option value="N">
								<spring:message code="adh.new.location" text="New Location" />
							</form:option>
						</form:select>
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="adh.label.advertiser.name" /></label>
					<div class="col-sm-4">
						<form:select path="advertisementDto.agencyId" id="agnId"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" />
							</form:option>
							<c:forEach items="${command.advertiserMasterDtoList}"
								var="agency">
								<form:option value="${agency.agencyId}">${agency.agencyName}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="adh.label.location" text="Location" /></label>
					<div class="col-sm-4">
						<form:select path="advertisementDto.locId" id="locId"
							class="chosen-select-no-results" data-rule-required="true"
							onchange="getLocationMapping(this);">
							<form:option value="">
								<spring:message code="adh.select" />
							</form:option>
							<c:forEach items="${command.locationList}" var="location">
								<form:option value="${location.locId}">${location.locNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="adh.label.property.type" text="Property Type" /></label>
					<div class="col-sm-4">
						<form:select path="advertisementDto.propTypeId"
							cssClass="form-control chosen-select-no-results" id="propTypeId">
							<form:option value="">
								<spring:message code="adh.select" text="Select" />
							</form:option>
							<c:forEach items="${command.getLevelData('ONT')}"
								var="propertyType">
								<form:option value="${propertyType.lookUpId}"
									code="${propertyType.lookUpCode}">${propertyType.lookUpDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<apptags:input labelCode="adh.label.property.no"
						path="advertisementDto.propNumber"></apptags:input>
					<apptags:input labelCode="adh.label.trade.shop.oldlicense.no"
						path="advertisementDto.tradeLicNo"></apptags:input>
				</div>

				<div class="form-group">
					<apptags:input labelCode="adh.label.property.owner.name"
						path="advertisementDto.propOwnerName"></apptags:input>
					<label class="col-sm-2 control-label required-control"><spring:message
							code="adh.label.license.status" text="License Status" /></label>
					<div class="col-sm-4">
						<form:select path="advertisementDto.adhStatus" id="adhStatus"
							class="chosen-select-no-results" data-rule-required="true">
							<form:option value="">
								<spring:message code="adh.select" />
							</form:option>
							<form:option value="A">
								<spring:message code="adh.location.type.active" text="Active"></spring:message>
							</form:option>
							<form:option value="T">
								<spring:message code="adh.location.type.terminate"
									text="Terminate"></spring:message>
							</form:option>
							<form:option value="C">
								<spring:message code="adh.location.type.closed" text="Closed"></spring:message>
							</form:option>
							<form:option value="S">
								<spring:message code="adh.location.type.suspended"
									text="Suspended"></spring:message>
							</form:option>
						</form:select>
					</div>
				</div>
				<h4 class="margin-top-0">
					<a data-toggle="collapse" class="collapsed" href="#a1"> <spring:message
							code="adh.advertising.details.title" text="Advertising Details" />
					</a>
				</h4>
				<div class="overflow margin-top-10">
					<div class="table-responsive">
						<c:set var="d" value="0" scope="page"></c:set>
						<table id="newAdvertisingTableId" summary="Advertising Details"
							class="table table-bordered table-striped">
							<thead>
								<tr>
									<th scope="col" width="5%"><spring:message code=""
											text="Sr.No." /></th>
									<apptags:lookupFieldSet baseLookupCode="ADH" hasId="true"
										showOnlyLabel="false"
										pathPrefix="advertisementDto.newAdvertDetDtos[0].adhTypeId"
										isMandatory="true" hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control required-control" showAll="false"
										hasTableForm="true" showData="false" columnWidth="10%" />
									<th scope="col" width="10%" class="required-control" align="center"><spring:message
											code="adh.label.description" text="Description" /></th>
									<th scope="col" width="10%" class="required-control" align="center"><spring:message
											code="adh.label.height" text="Height" /></th>
									<th scope="col" width="10%" class="required-control" align="center"><spring:message
											code="adh.label.length" text="Length" /></th>
									<th scope="col" width="10%" align="center"><spring:message
											code="adh.label.area"  text="Area" /></th>
									<th scope="col" width="10%" class="required-control" align="center"><spring:message
											code="adh.label.unit" text="Unit" /></th>
									<th scope="col" width="10%" class="required-control" align="center"><spring:message
											code="adh.label.facing" text="Facing" /></th>
									<th scope="col" width="5%"><a href="javascript:void(0);"
										class=" btn btn-blue-2 btn-sm addAdvertising bluebtn"> <i
											class="fa fa-plus-circle"></i>
									</a></th>
								</tr>
							</thead>
							<tbody>
								<c:choose>			
									<c:when
										test="${not empty command.advertisementDto.newAdvertDetDtos }">
										<c:forEach var="sorDetailsList"
											items="${command.advertisementDto.newAdvertDetDtos}"
											varStatus="status">
											<tr class="advertisingDetailsClass">
										
												<td align="center" width="5%"><form:input path=""
														cssClass="form-control mandColorClass " id="sequence${d}"
														value="${d+1}" disabled="true" /></td>
													<form:hidden path="advertisementDto.newAdvertDetDtos[${d}].adhHrdDetId" id="adhHrdDetId${d}" />
												<apptags:lookupFieldSet baseLookupCode="ADH" hasId="true"
													pathPrefix="advertisementDto.newAdvertDetDtos[${d}].adhTypeId"
													isMandatory="true" hasLookupAlphaNumericSort="true"
													hasSubLookupAlphaNumericSort="true"
													cssClass="form-control required-control " showAll="false"
													hasTableForm="true" showData="true" columnWidth="10%" />
                                        
												<td><form:textarea id="advDetailsDesc${d}"
														path="advertisementDto.newAdvertDetDtos[${d}].advDetailsDesc"
														maxlength="100" class="form-control" /></td>

												<td><form:input
														path="advertisementDto.newAdvertDetDtos[${d}].advDetailsHeight"
														class="form-control decimal text-right"
														id="advDetailsHeight${d}"
														onkeypress="return hasAmount(event, this, 11, 2)"
														onkeyup="calculateArea()" /></td>

												<td><form:input
														path="advertisementDto.newAdvertDetDtos[${d}].advDetailsLength"
														class="form-control decimal text-right "
														id="advDetailsLength${d}"
														onkeypress="return hasAmount(event, this, 11, 2)"
														onkeyup="calculateArea()" /></td>

												<td><form:input
														path="advertisementDto.newAdvertDetDtos[${d}].advDetailsArea"
														class="form-control decimal text-right"
														id="advDetailsArea${d}" readonly="true" /></td>

												<td><form:input
														path="advertisementDto.newAdvertDetDtos[${d}].unit"
														class="form-control hasNumber" id="unit${d}" /></td>
												<td><form:select id="dispTypeId${d}"
														path="advertisementDto.newAdvertDetDtos[${d}].dispTypeId"
														class="form-control">
														<form:option value="">
															<spring:message code="adh.select" />
														</form:option>
														<c:forEach items="${command.getLevelData('DSP')}"
															var="lookUp">
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
														</c:forEach>
													</form:select></td>
												<td align="center" width="3%"><a
													href="javascript:void(0);"
													class="btn btn-danger btn-sm delButton" title="Delete"
													onclick="deleteEntry($(this),'removeAdverId',${d});"><i class="fa fa-minus"></i> </a></td>							
													<c:set var="d" value="${d + 1}" scope="page" />
											</tr>
											
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="advertisingDetailsClass">
										
											<td align="center" width="5%"><form:input path=""
													cssClass="form-control mandColorClass " id="sequence${d}"
													value="${d+1}" disabled="true" /></td>
										<form:hidden path="advertisementDto.newAdvertDetDtos[${d}].adhHrdDetId" id="adhHrdDetId${d}"/>
											<apptags:lookupFieldSet baseLookupCode="ADH" hasId="true"
												pathPrefix="advertisementDto.newAdvertDetDtos[0].adhTypeId"
												isMandatory="true" hasLookupAlphaNumericSort="true"
												hasSubLookupAlphaNumericSort="true"
												cssClass="form-control required-control " showAll="false"
												hasTableForm="true" showData="true" columnWidth="10%" />
	                                   
											<td><form:textarea id="advDetailsDesc${d}"
													path="advertisementDto.newAdvertDetDtos[0].advDetailsDesc"
													maxlength="100" class="form-control" /></td>

											<td><form:input
													path="advertisementDto.newAdvertDetDtos[0].advDetailsHeight"
													class="form-control decimal text-right"
													id="advDetailsHeight${d}"
													onkeypress="return hasAmount(event, this, 11, 2)"
													onkeyup="calculateArea()" /></td>

											<td><form:input
													path="advertisementDto.newAdvertDetDtos[0].advDetailsLength"
													class="form-control decimal text-right "
													id="advDetailsLength${d}"
													onkeypress="return hasAmount(event, this, 11, 2)"
													onkeyup="calculateArea()" /></td>

											<td><form:input
													path="advertisementDto.newAdvertDetDtos[0].advDetailsArea"
													class="form-control decimal text-right"
													id="advDetailsArea${d}" readonly="true" /></td>

											<td><form:input
													path="advertisementDto.newAdvertDetDtos[0].unit"
													class="form-control hasNumber" id="unit${d}" /></td>
											<td><form:select id="dispTypeId${d}"
													path="advertisementDto.newAdvertDetDtos[0].dispTypeId"
													class="form-control">
													<form:option value="">
														<spring:message code="adh.select" />
													</form:option>
													<c:forEach items="${command.getLevelData('DSP')}"
														var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select></td>
											<td align="center" width="3%"><a
												href="javascript:void(0);"
												class="btn btn-danger btn-sm delButton" title="Delete"
												onclick="deleteEntry($(this),'removeAdverId',${d});"><i class="fa fa-minus"></i> </a></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
				<div class="text-center">
					<c:if test="${command.saveMode eq 'A' || command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success"
							data-toggle="tooltip" data-original-title="Save"
							onclick="save(this,'A')">
							<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
							<spring:message code="adh.save" text="Save"></spring:message>
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'A'}">
						<button type="button" class="btn btn-warning"
							data-toggle="tooltip" data-original-title="Reset"
							onclick="resetDataEntry(this);">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="adh.reset" text="Reset"></spring:message>
						</button>
					</c:if>
					<button type="button" class="btn btn-danger" data-toggle="tooltip"
						data-original-title="Back"
						onclick="window.location.href='AdvertisementDataEntry.html'">
						<i class="fa fa-chevron-circle-left padding-right-5"
							aria-hidden="true"></i>
						<spring:message code="adh.back" text="Back"></spring:message>
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>