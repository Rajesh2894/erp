<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/constructNDemoWasteCollector.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<script>

</script>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="construct.demolition.header" />
			</h2>
			<apptags:helpDoc url="WasteCollector.html"></apptags:helpDoc>
		</div>
		<div class="pagediv">
			<div class="widget-content padding">
				<form:form id="constructDemolitionWasteCollector"
					commandName="command" name="constructDemolitionWasteCollector"
					class="form-horizontal" action="WasteCollector.html" method="post">

					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>
					</div>
					<!--add new prefix  -->
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse1">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse" href="#Applicant">
										<spring:message code="construct.demolition.applicant.details"
											text="Applicant Information"></spring:message>
									</a>
								</h4>
							</div>
							<div id="Applicant" class="padding-top-10">


								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="applicantTitle"><spring:message
											code="applicantinfo.label.title" /></label>
									<c:set var="baseLookupCode" value="TTL" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="collectorReqDTO.applicantDetailDto.applicantTitle"
										cssClass="form-control" hasChildLookup="false" hasId="true"
										showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" disabled="true" />
									<label class="col-sm-2 control-label required-control"
										for="firstName"><spring:message
											code="applicantinfo.label.firstname" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="collectorReqDTO.applicantDetailDto.applicantFirstName"
											id="firstName" data-rule-required="true" disabled="true"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" for="middleName"><spring:message
											code="applicantinfo.label.middlename" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="collectorReqDTO.applicantDetailDto.applicantMiddleName"
											id="middleName" disabled="true"></form:input>
									</div>
									<label class="col-sm-2 control-label required-control"
										for="lastName"><spring:message
											code="applicantinfo.label.lastname" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="collectorReqDTO.applicantDetailDto.applicantLastName"
											id="lastName" data-rule-required="true" disabled="true"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="gender"><spring:message
											code="applicantinfo.label.gender" /></label>

									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="GEN" />
										<form:select path="collectorReqDTO.applicantDetailDto.gender"
											cssClass="form-control" id="gender" onchange=""
											disabled="true" data-rule-required="true">

											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpCode}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>

									</div>
									<label class="col-sm-2 control-label required-control"
										for="mobileNo"><spring:message
											code="applicantinfo.label.mobile" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="collectorReqDTO.applicantDetailDto.mobileNo"
											id="mobileNo" data-rule-required="true"
											data-rule-digits="true" data-rule-minlength="10"
											data-rule-maxlength="10" disabled="true"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label" for="emailId"><spring:message
											code="applicantinfo.label.email" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="collectorReqDTO.applicantDetailDto.emailId"
											id="emailId" data-rule-email="true" disabled="true"></form:input>
									</div>
									<label class="col-sm-2 control-label required-control"
										for="areaName"><spring:message
											code="disposal.site.master.adress" text="Address" /></label>
									<div class="col-sm-4">
										<form:textarea type="text" class="form-control"
											path="collectorReqDTO.applicantDetailDto.areaName"
											id="areaName" data-rule-required="true" disabled="true"></form:textarea>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="pinCode"><spring:message
											code="applicantinfo.label.pincode" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control "
											path="collectorReqDTO.applicantDetailDto.pinCode"
											id="pinCode" data-rule-required="true"
											data-rule-maxlength="6" data-rule-digits="true"
											disabled="true"></form:input>
									</div>
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse1" href="#a1"><spring:message
											code="" text="Service Details" /></a>
								</h4>
							</div>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<apptags:input labelCode="construct.demolition.waste.capacity"
											path="collectorReqDTO.collectorDTO.capacity"
											cssClass="form-control hasNumber" isMandatory="true"
											isDisabled="true"></apptags:input>
										<apptags:input labelCode="construct.demolition.no.trip"
											cssClass="form-control hasNumber" maxlegnth="3"
											path="collectorReqDTO.collectorDTO.noTrip" isMandatory="true"
											isDisabled="true"></apptags:input>
									</div>

									<div class="form-group">
										<apptags:input
											labelCode="construct.demolition.bldg.permission"
											cssClass="form-control alphaNumeric" maxlegnth="50"
											path="collectorReqDTO.collectorDTO.bldgPermission"
											isDisabled="true"></apptags:input>

										<apptags:input labelCode="construct.demolition.complainNo"
											cssClass="form-control alphaNumeric" maxlegnth="50"
											path="collectorReqDTO.collectorDTO.complainNo"
											isDisabled="true"></apptags:input>
									</div>

									<div class="form-group">
										<label class="control-label col-sm-2 required-control" for="">
											<spring:message code="construct.demolition.location" />
										</label>
										<div class="col-sm-4">
											<form:select path="collectorReqDTO.collectorDTO.locationId"
												disabled="true"
												cssClass="form-control chosen-select-no-results"
												isMandatory="true" id="constructDemolitionLocId">
												<form:option value="">
													<spring:message code='' text="Select" />
												</form:option>
												<c:forEach items="${command.locList}" var="locationList">
													<form:option value="${locationList.locId }" code="">${locationList.locNameEng }</form:option>
												</c:forEach>
											</form:select>
										</div>

										<apptags:input labelCode="construct.demolition.address.of.consruction.site"
											cssClass="form-control alphaNumeric" maxlegnth="50"
											path="collectorReqDTO.collectorDTO.locAddress"
											isMandatory="true" isDisabled="true"></apptags:input>

									</div>
									<div class="form-group">
										<label class="control-label required-control col-sm-2" for="">
											<spring:message code="construct.demolition.vehicle.type" />
										</label>
										<c:set var="baseLookupCode" value="VCH" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="collectorReqDTO.collectorDTO.vehicleType"
											disabled="true" cssClass="form-control"
											changeHandler="showVehicleRegNo()" hasChildLookup="false"
											hasId="true" showAll="false" selectOptionLabelCode="Select"
											isMandatory="true" />
									</div>



									<c:if test="${not empty command.collectorReqDTO.docsList}">
										<div class="table-responsive">
											<table class="table table-bordered table-striped"
												id="attachDocs">
												<tr>
													<th width="20%"><spring:message
															code="public.toilet.master.srno" text="Sr. No." /></th>
													<th><spring:message code="swm.viewDocument"
															text="View Document" /></th>
												</tr>
												<c:forEach items="${command.collectorReqDTO.docsList}"
													var="lookUp" varStatus="d">
													<tr>
														<td align="center">${d.count}</td>
														<td align="center"><apptags:filedownload
																filename="${lookUp.attFname}"
																filePath="${lookUp.attPath}"
																actionUrl="AdminHome.html?Download" /></td>
													</tr>
												</c:forEach>
											</table>
										</div>

									</c:if>

								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse1" href="#a3"><spring:message
											code="" text="C&D Vehicle Allotment" /></a>
								</h4>
							</div>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="gender"><spring:message code="swm.vehicleRegNo" /></label>
										<div class="col-sm-4">
											<form:select path="collectorReqDTO.collectorDTO.vehicleNo"
												id="veid" class="form-control mandColorClass "
												label="Select">
												<form:option value="0">
													<spring:message code="solid.waste.select" text="select" />
												</form:option>
											</form:select>

										</div>



										<label class="col-sm-2 control-label required-control"
											for="desposalsite"><spring:message
												code="swm.dsplsite" /> </label>
										<div class="col-sm-4">
											<form:select path="collectorReqDTO.collectorDTO.mrfId"
												class="form-control mandColorClass chosen-select-no-results"
												label="Select" disabled="" id="mrfId">
												<form:option value="0">
													<spring:message code="solid.waste.select" text="select" />
												</form:option>
												<c:forEach items="${command.mrfList}" var="lookUp">
													<form:option value="${lookUp.mrfId}" code="">${lookUp.mrfPlantName}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>
									<div class="form-group">

										<apptags:input labelCode="employee.verification.employee.name"
											cssClass="form-control hasCharacter" maxlegnth="50"
											path="collectorReqDTO.collectorDTO.empName"
											isMandatory="true" isDisabled="false"></apptags:input>

										<apptags:input labelCode="construct.demolition.dateOfPickUp" isReadonly="true"
											cssClass="datepicker"
											path="collectorReqDTO.collectorDTO.pickUpDate"
											isMandatory="true" isDisabled="" />

									</div>

								</div>
							</div>
						</div>


					</div>
					<apptags:CheckerAction/>
					<div class="padding-top-10 text-center">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveFinalApproval('WasteCollector.html','saveFinalDecision',this)"
							id="submit">
							<spring:message code="bt.save" />
						</button>
						<input type="button" id="backBtn" class="btn btn-danger"
							onclick="back()" value="<spring:message code="bt.backBtn"/>" />
					</div>

				</form:form>
			</div>
		</div>
	</div>
</div>