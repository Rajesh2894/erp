<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/property/billingMethodAddFlats.js"
	type="text/javascript"></script>
<style>
.addColor {
	background-color: #fff !important
}
</style>

<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="property.selfassessment"
						text=" Property Registration" /></strong>
			</h2>
			<apptags:helpDoc url="BillingMethodAuthorization.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">

			<form:form action="BillingMethodAuthorization.html"
				class="form-horizontal form" name="frmBillMethodFlats"
				id="frmBillMethodFlats">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="accordion-toggle">
					<h4 class="margin-top-0 margin-bottom-10 panel-title">
						<a data-toggle="collapse" href="#UnitDetail"><spring:message
								code="property.unitdetails" /></a>
					</h4>
					<form:hidden path="provisionalAssesmentMstDto.assAcqDate"
						id="assAcqDate" />
					<form:hidden path="countOfRow" id="countOfRow" />
					<form:hidden path="noOfDetailRows" id="noOfDetailRows"
						value="${command.noOfDetailRows}" />

					<!-- newly added -->

					<div class="panel-collapse collapse in" id="UnitDetail">
						<c:choose>
							<c:when
								test="${not empty command.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()}">
								<div class="table-responsive">
									<c:set var="d" value="0" scope="page" />
									<table id="unitDetailTable"
										class="table table-striped table-bordered appendableClass unitDetails">
										<tbody>
											<tr>
												<th width="10%" class="required-control"><spring:message
														code="unitdetails.year" /></th>
												<th width="3%" class="required-control"><spring:message
														code="unitdetails.unitno" /></th>
												<th width="8%" class="required-control"><spring:message
														code="unitdetails.flatNo" /></th>
												<th width="12%" class="required-control"><spring:message
														code="unitdetails.floorno" /></th>
												<th width="13%" class="required-control"><spring:message
														code="unitdetails.ConstcompletionDate" /></th>
												<th width="12%" class="required-control"><spring:message
														code="unitDetails.firstAssessmentDate" /></th>
												<th width="10%" class="required-control"><spring:message
														code="unitdetails.constructiontype" /></th>
												<apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
													showOnlyLabel="false"
													pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[0].assdUsagetype"
													isMandatory="true" hasLookupAlphaNumericSort="true"
													hasSubLookupAlphaNumericSort="true"
													cssClass="form-control required-control" showAll="false"
													hasTableForm="true" showData="false" columnWidth="10%" />

												<th width="12%" class="required-control"><spring:message
														code="unitdetails.carpetArea" text="Carpet Area" /></th>

												<th width="12%" class="required-control"><spring:message
														code="unitdetails.taxable" /></th>
												<th width="" class=""><spring:message
														code="unitdetails.constructPermissionNo"
														text="Construction Permission No." /></th>
												<th width="" class=""><spring:message
														code="unitdetails.permissionUseNo"
														text="Permission Use No." /></th>
												<th width="" class=""><spring:message
														code="unitdetails.assessmentRemark"
														text="Assessment Remark" /></th>
												<th width="" class=""><spring:message
														code="unitdetails.legal" text="Legal" /></th>

												<th colspan="3"><a href="javascript:void(0);"
													title="Add" class="addCF btn btn-success btn-sm unit"
													id="addUnitRow"><i class="fa fa-plus-circle"></i></a></th>

											</tr>

											<c:forEach var="unitDetails"
												items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentDetailDtoList()}"
												varStatus="status">

												<tr class="firstUnitRow">

													<td><form:select
															path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].faYearId"
															id="year${status.count-1}"
															class="form-control disabled text-center mandColorClass displayYearList"
															disabled="true">
															<form:option value="">
																<spring:message code="prop.selectSelectYear" />
															</form:option>
															<c:forEach items="${command.financialYearMap}"
																var="yearMap">
																<form:option value="${yearMap.key}"
																	label="${yearMap.value}"></form:option>
															</c:forEach>
														</form:select> <form:hidden
															path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].faYearId"
															id="hiddenYear${status.count-1}" /></td>

													<td><form:input
															path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdUnitNo"
															type="text"
															class="form-control mandColorClass text-center unit required-control"
															id="unitNo${status.count-1}" data-rule-required="true"
															readonly="true" /></td>

													<td width=""><form:input
															path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].flatNo"
															type="text"
															class="form-control  mandColorClass text-right"
															id="flatNo${status.count-1}" data-rule-required="true" /></td>
													<td><c:set var="baseLookupCode" value="IDE" /> <apptags:lookupField
															items="${command.getLevelData(baseLookupCode)}"
															path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdFloorNo"
															cssClass="form-control changeParameterClass mandColorClass floorNoClass"
															hasChildLookup="false" hasId="true" showAll="false"
															selectOptionLabelCode="prop.selectFloorNo"
															isMandatory="true" hasTableForm="true" /> <c:forEach
															items="${command.getLevelData(baseLookupCode)}"
															var="lookUp">
															<c:if test="${lookUp.lookUpCode eq 'OTH' }">
																<input type="hidden" value="${lookUp.lookUpId}"
																	id="hiddenOther">
															</c:if>
														</c:forEach></td>

													<td>
														<div class="input-group successErrorCheck">
															<form:input type="text"
																path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdYearConstruction"
																class="form-control datepicker2 mandColorClass dateClass addColor"
																id="yearOfConstruc0" data-rule-required="true"
																placeholder="DD/MM/YYYY" autocomplete="off"
																readonly="true" />
															<span class="input-group-addon"><i
																class="fa fa-calendar"></i></span>
														</div>
													</td>

													<td>
														<div class="input-group successErrorCheck">
															<form:input type="text"
																path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].firstAssesmentDate"
																class="form-control lessthancurrdate mandColorClass dateClass addColor"
																id="firstAssesmentDate0" data-rule-required="true"
																placeholder="DD/MM/YYYY" autocomplete="off"
																readonly="true" />
															<span class="input-group-addon"><i
																class="fa fa-calendar"></i></span>
														</div>
													</td>
													<td><c:set var="baseLookupCode" value="CSC" /> <apptags:lookupField
															items="${command.getLevelData(baseLookupCode)}"
															path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdConstruType"
															cssClass="form-control changeParameterClass mandColorClass disableFields"
															hasChildLookup="false" hasId="true" showAll="false"
															selectOptionLabelCode="prop.selectConstType"
															isMandatory="true" hasTableForm="true" /></td>
													<apptags:lookupFieldSet baseLookupCode="USA" hasId="true"
														showOnlyLabel="false"
														pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdUsagetype"
														isMandatory="true" hasLookupAlphaNumericSort="true"
														hasSubLookupAlphaNumericSort="true"
														cssClass="form-control required-control disableFields makeDisabled" showAll="false"
														hasTableForm="true" showData="true" />

													<td><form:input
															path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].carpetArea"
															type="text"
															class="form-control has2Decimal mandColorClass text-right"
															id="carpetArea${status.count-1}"
															data-rule-required="true"
															onkeypress="return hasAmount(event, this, 15, 2)" /></td>

													<td width="150"><form:input
															path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdBuildupArea"
															type="text"
															class="form-control has2Decimal mandColorClass text-right"
															id="taxableArea${status.count-1}"
															data-rule-required="true"
															onkeypress="return hasAmount(event, this, 15, 2)"
															onchange="getAmountFormatInDynamic((this),'proAssdBuildupArea')"
															placeholder="999999.99" /></td>


													<td><form:input type="text" class="form-control "
															path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].constructPermissionNo"
															id="constructPermissionNo${status.count-1}"></form:input></td>
													<td><form:input type="text" class="form-control "
															path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].permissionUseNo"
															id="permissionUseNo${status.count-1}"></form:input></td>
													<td><form:input type="text" class="form-control "
															path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assessmentRemark"
															id="assessmentRemark${status.count-1}"></form:input></td>
													<td><label class="checkbox-inline padding-top-0"
														for="legal"><form:checkbox
																path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].legal"
																value="Y" /></label> <!-- New fields Added -->
													<td class="text-center"><a
														class="clickable btn btn-success btn-xs click_advance"
														data-toggle="collapse" data-target="#group-of-rows-0"
														aria-expanded="false" aria-controls="group-of-rows-0"><i
															class="fa fa-caret-down" aria-hidden="true"></i></a></td>
													<td class="text-center"><a href="javascript:void(0);"
														class="remCF btn btn-danger btn-xs delete" id="deleteRow_"><i
															class="fa fa-minus-circle"></i></a></td>
													<td><input type="button" id="addRoom${status.count-1}"
														class="btn btn-success btn-submit addRoomClass"
														onclick="addRoomDetails(${status.count-1},this)"
														value="Room"></td>

												</tr>

												<tr class="secondUnitRow collapse in" id="group-of-rows-0">
													<td colspan="18"><legend class="text-blue-3 text-left">
															<spring:message code="unitdetails.AdditionalUnitDetails" />
														</legend>

														<div
															class="addunitdetails0 col-xs-6 col-xs-offset-0 col-md-12">
															<div class="form-group">


																<label for="occupancy"
																	class="col-sm-2 control-label required-control"><spring:message
																		code="unitdetails.occupancy" /> </label>
																<div class="col-sm-4">
																	<c:set var="baseLookupCode" value="OCS" />
																	<apptags:lookupField
																		items="${command.getLevelData(baseLookupCode)}"
																		path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdOccupancyType"
																		cssClass="form-control changeParameterClass disableFields"
																		hasChildLookup="false" hasId="true" showAll="false"
																		selectOptionLabelCode="prop.selectSelectOccupancyType"
																		isMandatory="true" hasTableForm="true"
																		changeHandler="loadOccupierName(this)" />
																</div>

																<apptags:lookupFieldSet baseLookupCode="PTP"
																	hasId="true" showOnlyLabel="false"
																	pathPrefix="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].assdNatureOfproperty"
																	isMandatory="true" hasLookupAlphaNumericSort="true"
																	hasSubLookupAlphaNumericSort="true"
																	cssClass="form-control required-control disableFields"
																	showAll="false" columnWidth="10%" />



															</div>

															<div class="form-group">

																<label for="occupierName${status.count-1}"
																	class="col-sm-2 control-label"><spring:message
																		code="unitdetails.OccupierName" /></label>
																<div class="col-sm-4">
																	<form:input
																		path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierName"
																		class="form-control hasCharacter preventSpace disableFields"
																		id="occupierName${status.count-1}" maxlength="500" />
																</div>

																<label for="occupierNameReg${status.count-1}"
																	class="col-sm-2 control-label"><spring:message
																		code="unitdetails.occupierNameReg" /></label>
																<div class="col-sm-4">
																	<form:input
																		path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierNameReg"
																		type="text" class="form-control  preventSpace disableFields"
																		id="occupierNameReg${status.count-1}" />
																</div>

															</div>

															<div class="form-group">
																<label for="occupierMobNo${status.count-1}"
																	class="col-sm-2 control-label"><spring:message
																		code="unitdetails.occupierMobNo" /></label>
																<div class="col-sm-4">
																	<form:input
																		path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierMobNo"
																		type="text"
																		class="form-control hasMobileNo  preventSpace disableFields" 
																		id="occupierMobNo${status.count-1}" />
																</div>

																<label for="occupierEmail${status.count-1}"
																	class="col-sm-2 control-label"><spring:message
																		code="unitdetails.occupierEmail" /></label>
																<div class="col-sm-4">
																	<form:input
																		path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].occupierEmail"
																		type="text"
																		class="form-control hasemailclass  preventSpace disableFields"
																		id="occupierEmail${status.count-1}" />
																</div>
															</div>

															<div class="form-group actualRentClass">
																<label for="actualRent${status.count-1}"
																	class="col-sm-2 "><spring:message
																		code="unitdetails.actualRent" /></label>
																<div class="col-sm-4">
																	<form:input
																		path="provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList[${status.count-1}].actualRent"
																		type="text"
																		class="form-control has2Decimal  text-left disableFields"
																		id="actualRent${status.count-1}"
																		onkeypress="return hasAmount(event, this, 15, 2)" />
																</div>
															</div>

														</div></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>

							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>

					</div>
				</div>

				<div class="text-center padding-bottom-20">
					<button type="button" class="btn btn-success btn-submit"
						onclick="updateFlats(this)" id="submit">
						<spring:message code="bt.save" text="Submit" />
					</button>
					<c:set var="appId" value="${command.lableValueDTO.applicationId}" />
					<c:set var="labelsId" value="${command.lableValueDTO.lableId}" />
					<c:set var="servicesId" value="${command.serviceId}" />
					<form:hidden path="lableValueDTO.applicationId"
						value="${command.lableValueDTO.applicationId}" />
					<form:hidden path="lableValueDTO.lableId"
						value="${command.lableValueDTO.lableId}" />
					<form:hidden path="serviceId" value="${command.serviceId}" />
					<input type="button"
						onclick="loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule','${appId}','${labelsId}','${servicesId}')"
						class="btn btn-danger"
						value="<spring:message code="water.btn.back"/>">
				</div>
				<!--  End button -->
			</form:form>
		</div>
	</div>
</div>
