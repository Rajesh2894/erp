<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="js/rnl/master/rl-file-upload.js"></script>

<%
    response.setContentType("text/html; charset=utf-8");
%>
<script src="js/rnl/master/propertyAddForm.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated slideInDown">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rl.master.property.form.name" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="master.field.message" /> <i
					class="text-red-1">*</i> <spring:message
						code="master.field.mandatory.message" /></span>
			</div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
			<form:form method="post" action="EstatePropMas.html"
				class="form-horizontal" name="propForm" id="propForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="estatePropMaster.hiddeValue" id="hiddeValue" />
				<form:hidden path="removeEventIds" id="removeEventIds" />
				<form:hidden path="removeShiftIds" id="removeShiftIds" />
				<form:hidden path="removeFacilityIds" id="removeFacilityIds" />
				<form:hidden path="removeAminities" id="removeAminities" />
				<form:hidden path="modeType" id="modeType" />
				

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#estate"><spring:message
										code="rl.property.label.info" /> </a>
							</h4>
						</div>
						
						
						<div id="estate" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="estateId"><spring:message
											code='estate.label.name' /></label>
									<div class="col-sm-4">
										<form:select path="estatePropMaster.estateId"
											class="chosen-select-no-results form-control" id="estateId"
											disabled="${command.modeType eq 'V'}">
											<form:option value="0">
												<spring:message code="select" text="select" />
											</form:option>
											<c:forEach items="${command.estateMasters}" var="objArray">
												<form:option value="${objArray[0]}">
													<c:choose>
														<c:when test="${userSession.languageId eq 2}">${objArray[3]}</c:when>
														<c:otherwise>${objArray[2]}</c:otherwise>
													</c:choose>
												</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label class="control-label col-sm-2" for="estateCode"><spring:message
											code='estate.label.code' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="estatePropMaster.estatecode" id="estatecode"
											readonly="true" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2" for=assesmentPropId><spring:message
											code='rl.property.label.propNo' /></label>
										<%-- <div class="col-sm-4">
											<form:input type="text" class="form-control"
												onblur="getPropertyMasterDetails(this)" path="estatePropMaster.assesmentPropId"
												id="propertyNo" data-rule-required="true"></form:input>
										</div> --%>
										
										<div class="col-sm-4">
										<div class="input-group">
											<form:input type="text" class="form-control alfaNumric"
												maxlength="16" path="estatePropMaster.assesmentPropId"
												id="assesmentPropId" />
											<span class="input-group-addon" onclick="getPropertyMasterDetails(this)"><i
												class="fa fa-search"></i></span>
										</div>
									</div>
										
									<label class="control-label col-sm-2" for="oldPropNo"><spring:message
											code='rl.property.label.oldPropNo' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="estatePropMaster.oldPropNo" id="oldPropNo"
											maxlength="16" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="name"><spring:message
											code='rl.property.label.name' /></label>
									<div class="col-sm-10">
										<form:input type="text" class="form-control"
											path="estatePropMaster.name" id="name" maxlength="1500" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2" for="unitNo"><spring:message
											code='rl.property.label.unitno' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control hasNumber"
											path="estatePropMaster.unitNo" id="unitNo" maxlength="12" />
									</div>
									<label class="control-label col-sm-2" for="gisId"><spring:message
											code='rl.property.label.gisId' /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input type="text" class="form-control"
												path="estatePropMaster.gisId" id="gisId" maxlength="20" />
											<span class="input-group-addon"><i class="fa fa-globe"></i></span>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="OccupancyType"><spring:message
											code='rl.property.label.Occupancy' /></label>
									<c:set var="baseLookupCode" value="ROC" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="estatePropMaster.occupancy"
										cssClass="form-control chosen-select-no-results"
										selectOptionLabelCode="Select" hasId="true" isMandatory="true"
										disabled="${command.modeType eq 'V'}" />

									<label class="control-label col-sm-2 required-control"><spring:message
											code='rl.property.label.Usage' /></label>
									<div class="col-sm-4">
										<form:select path="estatePropMaster.usage"
											cssClass="form-control chosen-select-no-results mandColorClass"
											id="usageId" data-rule-required="true"
											disabled="${command.modeType eq 'V'}">
											<form:option value="">
												<spring:message code='' text="Select" />
											</form:option>
											<c:forEach items="${command.usageType}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<%-- <apptags:lookupField items="${command.getLevelData('USA',1)}"
										path="estatePropMaster.usage"
										cssClass="form-control chosen-select-no-results"
										selectOptionLabelCode="Select" hasId="true" isMandatory="true"
										disabled="${command.modeType eq 'V'}" /> --%>
								</div>

								<div class="form-group">
									<label class="control-label col-sm-2 required-control"
										id="floorLabelId" for="floor"><spring:message
											code='rl.property.label.Floor' /></label>
									<apptags:lookupField items="${command.getLevelData('IDE')}"
										path="estatePropMaster.floor"
										cssClass="form-control chosen-select-no-results"
										selectOptionLabelCode="Select" hasId="true"
										disabled="${command.modeType eq 'V'}" />
									<label class="control-label col-sm-2 required-control"
										for="roadType"><spring:message
											code='rl.property.label.roadType' /></label>
									<apptags:lookupField items="${command.getLevelData('RFT')}"
										path="estatePropMaster.roadType"
										cssClass="form-control chosen-select-no-results"
										selectOptionLabelCode="Select" hasId="true" isMandatory="true"
										disabled="${command.modeType eq 'V'}" />
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2" id="propLatitudeLbl"
										for="propLatitudeLbl"><spring:message
											code='rl.property.label.latitude' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control "
											path="estatePropMaster.propLatitude" id="propLatitude"
											maxlength="15" />
									</div>
									<label class="control-label col-sm-2" id="propLongitudeLbl"
										for="propLongitudeLbl"><spring:message
											code='rl.property.label.longitude' /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control"
											path="estatePropMaster.propLongitude" id="propLongitude"
											maxlength="15" />
									</div>
								</div>
								<div class="form-group ">
									<label class="control-label col-sm-2 " id="propCapacityLbl"><spring:message
											code='rl.property.label.capacity' text="Capacity" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control hasNumber"
											path="estatePropMaster.propCapacity" id="propCapacity" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control" id="propMaintainByLbl"><spring:message
											code='rl.property.label.property.maintain.by'
											text="Property Maintain By" /></label>
									<div class="col-sm-4">
										<form:select path="estatePropMaster.propMaintainBy"
											class="chosen-select-no-results form-control"
											id="propMaintainBy" disabled="${command.modeType eq 'V'}">
											<form:option value="0">
												<spring:message code="rnl.master.select" text="select" />
											</form:option>
											<c:forEach items="${command.getLevelData('PBM')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
									<label class="control-label col-sm-2 " id="propNoDaysAllowLbl"><spring:message
											code='rl.property.label.no.of.days.allowed'
											text="Booking Span" /></label>
									<div class="col-sm-4">
										<form:input type="text" class="form-control hasNumber"
											path="estatePropMaster.propNoDaysAllow" id="propNoDaysAllow" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code='rl.property.label.property.status' text="Status" /></label>
									<div class="col-sm-4">
										<label class="radio-inline margin-top-5"> <form:radiobutton
												path="estatePropMaster.status" value="Y" id="status1" /> <spring:message
												code='rl.property.label.status.active' text="Active" /></label> <label
											class="radio-inline margin-top-5"> <form:radiobutton
												path="estatePropMaster.status" value="N" onclick="" /> <spring:message
												code='rl.property.label.status.inactive' text="Inactive" /></label>
									</div>
									
									<div class="courtCase">
										<label class="col-sm-2 control-label"><spring:message
												code='rl.property.label.action' /></label>
										<div class=" col-sm-4 ">
											<label class="checkbox-inline" style="margin-top: 8PX;">
												<form:checkbox path="estatePropMaster.courtCase" value="Y" />
												<spring:message code='rl.property.label.CourtCase' />
												&nbsp;&nbsp;&nbsp;&nbsp;
											</label> <label class="checkbox-inline" style="margin-top: 8PX;">
												<form:checkbox path="estatePropMaster.stopBilling" value="Y" />
												<spring:message code='rl.property.label.StopBilling' />
											</label>
										</div>
									</div>
								</div>
								<c:set var="d" value="0" scope="page" />
								<div class="table-responsive margin-top-10">
									<table class="table table-bordered table-striped"
										id="customFields">
										<tr>
											<th width="45%"><spring:message
													code='rl.property.label.AreaType' /></th>
											<th><spring:message code='rl.property.label.Area' /></th>
											<c:if test="${command.modeType ne 'V' }">
											<th width="8%"><spring:message
													code='rl.property.label.add' />/<spring:message
													code='rl.property.label.remove' /></th>
											</c:if>
										</tr>

										<c:if
											test="${command.modeType eq 'V' || command.modeType eq 'E'}">
											<c:forEach items="${command.estatePropMaster.details}"
												var="list">
												<tr class="appendableClass">
													<td><form:input type="hidden" id="propDetId_${d}"
															path="estatePropMaster.details[${d}].propDetId" /> <form:select
															path="estatePropMaster.details[${d}].areaType"
															class="form-control" id="areaType_${d}">
															<form:option value="0">
																<spring:message code="select" text="select" />
															</form:option>
															<c:forEach items="${command.getLevelData('ART')}"
																var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>
													<td><div class="input-group">
															<form:input path="estatePropMaster.details[${d}].area"
																class="form-control areatotal hasNumber" id="area_${d}" />
															<label class="input-group-addon" for="Area">Sq.Ft</label>
														</div></td>
													<c:if test="${command.modeType ne 'V' }">
														<td class="text-center"><a href="javascript:void(0);"
															data-toggle="tooltip" data-placement="top" id="addCF"
															class="addCF btn btn-success btn-sm"
															data-original-title="Add"><i class="fa fa-plus-circle"></i></a>
															<a href="javascript:void(0);" data-toggle="tooltip"
															data-placement="top" id="remCF"
															class="remCF btn btn-danger btn-sm"
															data-original-title="Delete"><i class="fa fa-trash"></i></a>
														</td>
													</c:if>
													
												</tr>
												<c:set var="d" value="${d+1}" scope="page" />
											</c:forEach>

										</c:if>

										<c:if test="${command.modeType eq 'C'}">
											<tr class="appendableClass">
												<td><form:input type="hidden" id="propDetId_${d}"
														value="" path="estatePropMaster.details[${d}].propDetId" />
													<form:select path="estatePropMaster.details[${d}].areaType"
														class="form-control" id="areaType_${d}">
														<form:option value="0">
															<spring:message code="select" text="select" />
														</form:option>
														<c:forEach items="${command.getLevelData('ART')}"
															var="lookUp">
															<form:option value="${lookUp.lookUpId}"
																code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
														</c:forEach>
													</form:select></td>
												<td><div class="input-group">
														<form:input path="estatePropMaster.details[${d}].area"
															class="form-control areatotal hasNumber" id="area_${d}"
															maxlength="12" />
														<label class="input-group-addon" for="Area">Sq.Ft</label>
													</div></td>
												<td class="text-center"><a href="javascript:void(0);" data-toggle="tooltip"
													data-placement="top" id="addCF"
													class="addCF btn btn-success btn-sm"
													data-original-title="Add"><i class="fa fa-plus-circle"></i></a>
													<a href="javascript:void(0);" data-toggle="tooltip"
													data-placement="top" id="remCF"
													class="remCF btn btn-danger btn-sm"
													data-original-title="Delete"><i class="fa fa-trash"></i></a>
												</td>
											</tr>

										</c:if>
										<tr>
											<th class="text-right text-blue-1"><spring:message
													code='rl.property.label.totalArea' /></th>
											<th colspan="2">
												<div class="input-group">
													<form:input type="text" class="form-control"
														path="estatePropMaster.totalArea" id="totalCal"
														readonly="true" />
													<label class="input-group-addon" for="Area">Sq.Ft</label>
												</div>
											</th>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#AmenitiesFacilities"><spring:message
										code='rnl.property.select.facilities'
										text=" Amenity & Facilities" /></a>
							</h4>
						</div>
						<div id="AmenitiesFacilities" class="panel-collapse collapse">
							<div class="panel-body">

								<h5 style="color: navy; font-size: medium;">
									<spring:message code='rnl.property.ameties.detials'
										text=" Amenity Details " />
								</h5>
								<div class="table-responsive margin-top-10">
									<c:set var="d" value="0" scope="page" />
									<table class="table table-hover table-bordered table-striped"
										id="ametiesTableClassId">
										<thead>
											<tr>
												<th width="10%"><spring:message
														code='estate.table.column.srno' /></th>
												<th width="80%"><spring:message
														code='rnl.property.amenity.type' text="Amenity Type" /></th>
												<c:if test="${command.modeType ne 'V'}">
													<th class="text-center" width="10%"><a
														onclick='return false;'
														class="btn btn-blue-2 btn-sm addAmenities"> <i
															class="fa fa-plus-circle"></i></a></th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.estatePropMaster.aminityDTOlist) > 0}">
													<c:forEach
														items="${command.estatePropMaster.aminityDTOlist}"
														varStatus="status">
														<tr class="amenitiesClass">
															<td align="center"><form:input path=""
																	cssClass="form-control " id="sNo${d}" value="${d+1}"
																	readonly="true" /></td>
															<form:hidden
																path="estatePropMaster.aminityDTOlist[${d}].propAmenityId"
																id="propAmenityId${d}" />
															<td><form:select
																	path="estatePropMaster.aminityDTOlist[${d}].propAmtFacility"
																	class="form-control" id="AmenityTpe${d}">
																	<form:option value="">
																		<spring:message code="rnl.master.select" />
																	</form:option>
																	<c:forEach items="${command.getLevelData('AMT')}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select></td>
																<c:if test="${command.modeType ne 'V' }">
																<td class="text-center"><a href='#'
																	onclick='return false;'
																	class='btn btn-danger btn-sm delAminitiesButton'><i
																	class="fa fa-trash"></i></a></td>
																</c:if>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="amenitiesClass">
														<td align="center"><form:input path=""
																cssClass="form-control" id="sNo${d}" value="${d+1}"
																readonly="true" /></td>
														<form:hidden
															path="estatePropMaster.aminityDTOlist[${d}].propAmenityId"
															id="propAmenityId${d}" />
														<td><form:select
																path="estatePropMaster.aminityDTOlist[${d}].propAmtFacility"
																class="form-control" id="AmenityTpe${d}">
																<form:option value="">
																	<spring:message code="rnl.master.select" />
																</form:option>
																<c:forEach items="${command.getLevelData('AMT')}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
														<c:if test="${command.modeType ne 'V'}">
															<td class="text-center"><a href='#'
																onclick='return false;'
																class='btn btn-danger btn-sm delAminitiesButton'><i
																	class="fa fa-trash"></i></a></td>
														</c:if>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
								<h5 style="color: navy; font-size: medium; margin-top: 20px;">
									<spring:message code='rnl.property.ameties.detials'
										text=" Facility Details " />
								</h5>

								<div class="table-responsive margin-top-10">
									<c:set var="d" value="0" scope="page" />
									<table class="table table-hover table-bordered table-striped"
										id="faciltiesTableClassId">
										<thead>
											<tr>
												<th width="5%"><spring:message
														code='estate.table.column.srno' /></th>
												<th width="43%"><spring:message
														code='rnl.property.facility.type' text="Facility Type" /></th>
												<th width="42%"><spring:message
														code='rnl.property.quantity' text="Quantity" /></th>
												<c:if test="${command.modeType ne 'V'}">
													<th class="text-center" width="10%"><a
														onclick='return false;'
														class="btn btn-blue-2 btn-sm addFacility"> <i
															class="fa fa-plus-circle"></i></a></th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.estatePropMaster.facilityDTOlist) > 0}">
													<c:forEach
														items="${command.estatePropMaster.facilityDTOlist}"
														varStatus="status">
														<tr class="faciltiesClass">
															<td align="center"><form:input path=""
																	cssClass="form-control " id="serNo${d}" value="${d+1}"
																	readonly="true" /></td>
															<form:hidden
																path="estatePropMaster.facilityDTOlist[${d}].propAmenityId"
																id="propFacilityId${d}" />
															<td><form:select
																	path="estatePropMaster.facilityDTOlist[${d}].propAmtFacility"
																	class="form-control" id="facilityType${d}">
																	<form:option value="">
																		<spring:message code="rnl.master.select" />
																	</form:option>
																	<c:forEach items="${command.getLevelData('FAC')}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select></td>

															<td><form:input
																	path="estatePropMaster.facilityDTOlist[${d}].propQuantity"
																	cssClass="form-control text-right "
																	onkeypress="return allownumeric(this)" maxlength="7"
																	id="facilityQuanity${d}" /></td>
															<c:if test="${command.modeType ne 'V'}">
																<td class="text-center"><a href='#'
																	onclick='return false;'
																	class='btn btn-danger btn-sm delFacilitiesButton'><i
																		class="fa fa-trash"></i></a></td>
															</c:if>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="faciltiesClass">
														<td align="center"><form:input path=""
																cssClass="form-control  " id="serNo${d}" value="${d+1}"
																readonly="true" /></td>
														<form:hidden
															path="estatePropMaster.facilityDTOlist[${d}].propAmenityId"
															id="propFacilityId${d}" />
														<td><form:select
																path="estatePropMaster.facilityDTOlist[${d}].propAmtFacility"
																class="form-control" id="facilityType${d}">
																<form:option value="">
																	<spring:message code="rnl.master.select" />
																</form:option>
																<c:forEach items="${command.getLevelData('FAC')}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>

														<td><form:input
																path="estatePropMaster.facilityDTOlist[${d}].propQuantity"
																cssClass="form-control text-right"
																onkeypress="return allownumeric(this)" maxlength="7"
																id="facilityQuanity${d}" /></td>
														<c:if test="${command.modeType ne 'V'}">
															<td class="text-center"><a href='#'
																onclick='return false;'
																class='btn btn-danger btn-sm delFacilitiesButton'><i
																	class="fa fa-trash"></i></a></td>
														</c:if>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#eventType"><spring:message
										code='rnl.property.select.facilities'
										text="Events Information" /></a>
							</h4>
						</div>
						<div id="eventType" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="table-responsive margin-top-10">
									<c:set var="d" value="0" scope="page" />
									<table class="table table-hover table-bordered table-striped"
										id="eventTypeTableClassId">
										<thead>
											<tr>
												<th width="5%"><spring:message
														code='estate.table.column.srno' /></th>
												<th width="43%"><spring:message
														code='rnl.property.event.type' text="Event Type" /></th>
												<th width="42%"><spring:message
														code='rnl.property.status' text="Status" /></th>
												<c:if test="${command.modeType ne 'V'}">
													<th class="text-center" width="10%"><a
														onclick='return false;'
														class="btn btn-blue-2 btn-sm addEventType"> <i
															class="fa fa-plus-circle"></i></a></th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.estatePropMaster.eventDTOList) > 0}">
													<c:forEach items="${command.estatePropMaster.eventDTOList}"
														varStatus="status">
														<tr class="eventTypeClass">
															<td align="center"><form:input path=""
																	cssClass="form-control  " id="srNo${d}" value="${d+1}"
																	readonly="true" /></td>
															<form:hidden
																path="estatePropMaster.eventDTOList[${d}].propEventId"
																id="propEventId${d}" />
															<td><form:select
																	path="estatePropMaster.eventDTOList[${d}].propEvent"
																	class="form-control" id="eventType${d}">
																	<form:option value="">
																		<spring:message code="rnl.master.select" />
																	</form:option>
																	<c:forEach items="${command.getLevelData('EVT')}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select></td>

															<td><form:select
																	path="estatePropMaster.eventDTOList[${d}].propAllowFlag"
																	cssClass="form-control " id="propAllowFlag${d}">
																	<form:option value="">
																		<spring:message code='rnl.master.select' />
																	</form:option>
																	<form:option value="A">
																		<spring:message code='rnl.property.allowed'
																			text="Allowed" />
																	</form:option>
																	<form:option value="N">
																		<spring:message code='rnl.property.not.allowed'
																			text="Not Allowed" />
																	</form:option>
																</form:select></td>
															<c:if test="${command.modeType ne 'V'}">
																<td class="text-center"><a href='#'
																	onclick='return false;'
																	class='btn btn-danger btn-sm delEventButton'><i
																		class="fa fa-trash"></i></a></td>
															</c:if>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="eventTypeClass">
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass " id="srNo${d}"
																value="${d+1}" readonly="true" /></td>
														<form:hidden
															path="estatePropMaster.eventDTOList[${d}].propEventId"
															id="propEventId${d}" />
														<td><form:select
																path="estatePropMaster.eventDTOList[${d}].propEvent"
																class="form-control" id="eventType${d}">
																<form:option value="">
																	<spring:message code="rnl.master.select" />
																</form:option>
																<c:forEach items="${command.getLevelData('EVT')}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>

														<td><form:select
																path="estatePropMaster.eventDTOList[${d}].propAllowFlag"
																cssClass="form-control " id="propAllowFlag${d}">
																<form:option value="">
																	<spring:message code='rnl.master.select' />
																</form:option>
																<form:option value="A">
																	<spring:message code='rnl.property.allowed'
																		text="Allowed" />
																</form:option>
																<form:option value="N">
																	<spring:message code='rnl.property.not.allowed'
																		text="Not Allowed" />
																</form:option>
															</form:select></td>
														<c:if test="${command.modeType ne 'V'}">
															<td class="text-center"><a href='#'
																onclick='return false;'
																class='btn btn-danger btn-sm delEventButton'><i
																	class="fa fa-trash"></i></a></td>
														</c:if>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#shift"><spring:message
										code='rnl.property.select.facilities' text="Shift Information" /></a>
							</h4>
						</div>
						<div id="shift" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="table-responsive margin-top-10">
									<c:set var="d" value="0" scope="page" />
									<table class="table table-hover table-bordered table-striped"
										id="shiftTableClassId">
										<thead>
											<tr>
												<th width="5%"><spring:message
														code='estate.table.column.srno' /></th>
												<th width="30%"><spring:message
														code='rnl.property.select.shift' text="Select Shift" /></th>
												<th width="28%"><spring:message
														code='rnl.property.shift.check.in' text="Check In" /></th>
												<th width="27%"><spring:message
														code='rnl.property.shift.check.out' text="Check Out" /></th>
												<c:if test="${command.modeType ne 'V'}">
													<th class="text-center" width="10%"><a
														onclick='return false;'
														class="btn btn-blue-2 btn-sm addShift"> <i
															class="fa fa-plus-circle"></i></a></th>
												</c:if>
											</tr>
										</thead>
										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.estatePropMaster.propertyShiftDTOList) > 0}">
													<c:forEach
														items="${command.estatePropMaster.propertyShiftDTOList}"
														varStatus="status">
														<tr class="shiftClass">
															<td align="center"><form:input path=""
																	cssClass="form-control  " id="shiftSrNo${d}"
																	value="${d+1}" readonly="true" /></td>
															<form:hidden
																path="estatePropMaster.propertyShiftDTOList[${d}].propShifId"
																id="propShifId${d}" />
															<td><form:select
																	path="estatePropMaster.propertyShiftDTOList[${d}].propShift"
																	class="form-control" id="shift${d}">
																	<form:option value="">
																		<spring:message code="rnl.master.select" />
																	</form:option>
																	<c:forEach items="${command.getLevelData('SHF')}"
																		var="lookUp">
																		<form:option value="${lookUp.lookUpId}"
																			code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select></td>
															<td align="center"><form:input
																	path="estatePropMaster.propertyShiftDTOList[${d}].startTime"
																	class="form-control datetimepicker3 mandColorClass"
																	maxlength="10" id="propFromTime${d}" /></td>

															<td align="center"><form:input
																	path="estatePropMaster.propertyShiftDTOList[${d}].endTime"
																	class="form-control datetimepicker3 mandColorClass"
																	maxlength="10" id="propToTime${d}" /></td>

															<c:if test="${command.modeType ne 'V'}">
																<td class="text-center"><a href='#'
																	onclick='return false;'
																	class='btn btn-danger btn-sm delShiftButton'><i
																		class="fa fa-trash"></i></a></td>
															</c:if>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="shiftClass">
														<td align="center"><form:input path=""
																cssClass="form-control  " id="shiftSrNo${d}"
																value="${d+1}" readonly="true" /></td>
														<form:hidden
															path="estatePropMaster.propertyShiftDTOList[${d}].propShifId"
															id="propShifId${d}" />
														<td><form:select
																path="estatePropMaster.propertyShiftDTOList[${d}].propShift"
																class="form-control" id="shift${d}">
																<form:option value="">
																	<spring:message code="rnl.master.select" />
																</form:option>
																<c:forEach items="${command.getLevelData('SHF')}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
														<td align="center"><form:input
																path="estatePropMaster.propertyShiftDTOList[${d}].startTime"
																class="form-control datetimepicker3 mandColorClass"
																maxlength="10" id="propFromTime${d}" /></td>

														<td align="center"><form:input
																path="estatePropMaster.propertyShiftDTOList[${d}].endTime"
																class="form-control datetimepicker3 mandColorClass"
																maxlength="10" id="propToTime${d}" /></td>

														<c:if test="${command.modeType ne 'V'}">
															<td class="text-center"><a href='#'
																onclick='return false;'
																class='btn btn-danger btn-sm delShiftButton'><i
																	class="fa fa-trash"></i></a></td>
														</c:if>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#Applicant"><spring:message
										code='rl.property.label.Upload.Attachment' /></a>
							</h4>
						</div>
						<div id="Applicant" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="table-responsive margin-top-10">
									<table class="table table-hover table-bordered table-striped">
										<tbody>
											<tr>
												<th><spring:message code='estate.table.column.srno' /></th>
												<th><spring:message code='estate.table.column.doc' /></th>
												<th><spring:message code='estate.table.column.header' /></th>
											</tr>
											<tr>
												<td>1</td>
												<td><spring:message code='estate.table.upload.image' /></td>
												<td><c:if test="${command.modeType eq 'V'}">
														<c:forEach items="${command.documentList}" var="lookUp"
															varStatus="lk">
															<c:if test="${lookUp.serialNo eq 0}">
																<apptags:filedownload filename="${lookUp.attFname}"
																	filePath="${lookUp.attPath}"
																	actionUrl="EstatePropMas.html?Download" />
															</c:if>
														</c:forEach>
													</c:if> <c:if test="${command.modeType ne 'V'}">
														<apptags:formField fieldType="7"
															fieldPath="estatePropMaster.imagesPath" labelCode=""
															currentCount="0" showFileNameHTMLId="true"
															fileSize="COMMOM_MAX_SIZE"
															maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT" folderName="0"
															validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION" />
													</c:if></td>
											</tr>
											<tr>
												<td>2</td>
												<td><spring:message code='estate.table.upload.terms' /></td>
												<td><c:if test="${command.modeType eq 'V'}">
														<c:forEach items="${command.documentList}" var="lookUp"
															varStatus="lk">
															<c:if test="${lookUp.serialNo eq 1}">
																<apptags:filedownload filename="${lookUp.attFname}"
																	filePath="${lookUp.attPath}"
																	actionUrl="EstatePropMas.html?Download" />
															</c:if>
														</c:forEach>
													</c:if> <c:if test="${command.modeType ne 'V'}">
														<apptags:formField fieldType="7" labelCode="" hasId="true"
															fieldPath="estatePropMaster.docsPath" isMandatory="false"
															showFileNameHTMLId="true" fileSize="COMMOM_MAX_SIZE"
															maxFileCount="CHECK_LIST_MAX_COUNT"
															validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
															folderName="1" currentCount="1" />
													</c:if></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<c:if test="${command.modeType ne 'V'}">
						<button type="button" class="btn btn-success btn-submit"
							id="submitProp">
							<spring:message code="bt.save" />
						</button>
					</c:if>
					<c:if test="${command.modeType eq 'C'}">
						<button type="Reset" class="btn btn-warning" id="resetProp">
							<spring:message code="bt.clear" />
						</button>
					</c:if>
					<!--D#74609  -->
					<c:choose>
					  <c:when test="${command.modeType eq 'VP'}">
					    <input type="button" id="backBtn" class="btn btn-danger"
						onclick="showEstatePropertyGrid(${command.estatePropMaster.estateId})" value="<spring:message code="bt.backBtn"/>" />
					  </c:when>
					  <c:when test="${command.saveMode eq 'VIEW_PROP'}">
					    
					  </c:when>
					  <c:otherwise>
					    <input type="button" id="backBtn" class="btn btn-danger"
						onclick="back()" value="<spring:message code="bt.backBtn"/>" />
					  </c:otherwise>
					</c:choose>
					
				</div>
				<form:hidden path="removeChildIds" />
			</form:form>
		</div>
	</div>
</div>