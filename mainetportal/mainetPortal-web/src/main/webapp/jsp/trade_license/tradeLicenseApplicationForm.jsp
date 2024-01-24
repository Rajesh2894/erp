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
<script src="js/mainet/validation.js"></script>
<script src="js/trade_license/tradeLicenseApplicationForm.js"></script>
<script src="js/mainet/file-upload.js"></script>
<link
	href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script
	src="assets/libs/jquery-datatables/js/dataTables.bootstrap.min.js"></script>
<style>
.h {
	color: cornflowerblue;
}
</style>


<!-- <script>
	$(function() {

		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
		/* maxDate : '0' */
		});
	});
</script> -->

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="trade.applheader"></spring:message></b>
				</h2>

				<apptags:helpDoc url="TradeApplicationForm.html"></apptags:helpDoc>
			</div>

			<div class="widget-content padding">

				<form:form method="POST" action="TradeApplicationForm.html"
					class="form-horizontal" id="tradeLicenseForm"
					name="tradeLicenseForm">
					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv" style="display: none;"></div>
						<form:hidden path="" id="removedIds" />
						<form:hidden path="length" id="length" />
						<form:hidden path="" id="viewMode" value="${command.viewMode}" />
						<form:hidden path="" id="hideAddBtn"
							value="${command.hideshowAddBtn}" />
						<form:hidden path="" id="hideDeleteBtn"
							value="${command.hideshowDeleteBtn}" />
						<form:hidden path="tradeMasterDetailDTO.assessmentCheckFlag"
							id="assessmentCheckFlag" />
						<form:hidden path="" id="kdmcEnv" value="${command.kdmcEnv}" />
						<form:hidden path="" id="sudaEnv" value="${command.sudaEnv}" />


					</div>
					<spring:message code="license.ent.propertNo" var="propertNo" />
					<spring:message code="license.ent.itemValue" var="enterItemvalue" />
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">


						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="trade.license.ownerDetails" /></a>
							</h4>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">

										<label id="ownershipType"
											class="col-sm-2 control-label required-control"
											for="ownershipType"><spring:message
												code="license.details.ownershipType" /></label>

										<div class="col-sm-4">
											<c:set var="baseLookupCode" value="FPT" />
											<form:select path="tradeMasterDetailDTO.trdFtype"
												onchange="getOwnerTypeDetails()"
												class="form-control mandColorClass" id="trdFtype"
												disabled="${command.viewMode eq 'V' ? true : false }"
												data-rule-required="true">
												<form:option value="">
													<spring:message code="property.sel.optn.ownerType" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>

									</div>

									<div id="owner"></div>

								</div>
							</div>
						</div>

						<div class="panel panel-default">

							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="trade.licenseDetails" /></a>
							</h4>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">


									<div class="form-group">
										<label id="licenseType"
											class="col-sm-2 control-label required-control"
											for="licenseType"><spring:message
												code="license.details.licenseType" /></label>
										<c:set var="baseLookupCode" value="LIT" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tradeMasterDetailDTO.trdLictype"
											cssClass="form-control" hasChildLookup="false" hasId="true"
											showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											disabled="${command.viewMode eq 'V' ? true : false }"
											isMandatory="true" showOnlyLabel="applicantinfo.label.title" />

									</div>

									<div id="licensePeriod" style="display: none;">
										<div class="form-group">


											<label class="col-sm-2 control-label required-control"
												for="licenseFromPeriod"><spring:message
													code="license.details.licenseFromPeriod"
													text="License From Period" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input class="form-control mandColorClass datepicker"
														id="trdLicfromDate"
														disabled="${command.viewMode eq 'V' ? true : false }"
														path="tradeMasterDetailDTO.trdLicfromDate"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>

											<label class="col-sm-2 control-label required-control"
												for="licenseToPeriod"><spring:message
													code="license.details.licenseToPeriod"
													text="License To Period" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input class="form-control mandColorClass datepicker"
														id="trdLictoDate"
														disabled="${command.viewMode eq 'V' ? true : false }"
														path="tradeMasterDetailDTO.trdLictoDate"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>

										</div>
									</div>

									<c:choose>

										<c:when test="${command.propertyActiveStatus eq 'Y'}">
											<div class="form-group">
												<c:if test="${command.sudaEnv eq 'Y'}">
													<label class="col-sm-2 control-label"><spring:message code="trade.license.propertyNo" text="Property No." /><span
														class="mand" id="showMand">*</span></label></c:if>
												<c:if test="${command.sudaEnv ne 'Y'}">
													<label class="col-sm-2 control-label"><spring:message code="trade.license.propertyNo" text="Property No." /> </label>
												</c:if>
												<div class="col-sm-4">

													<div class="input-group">
														<form:input path="tradeMasterDetailDTO.pmPropNo"
															placeholder="${propertNo}" type="text"
															disabled="${command.viewMode eq 'V' ? true : false }"
															onchange="getPropertyDetails(this);" class="form-control"
															id="propertyNo" />

														<c:if test="${command.viewMode ne 'V'}">
															<a href="#" class="input-group-addon"
																onclick="getPropertyDetails(this);"><i
																class="fa fa-search disabled"></i></a>
														</c:if>
														<c:if test="${command.viewMode eq 'V'}">
															<a href="#" class="input-group-addon" onclick=""><i
																class="fa fa-search disabled"></i></a>
														</c:if>
													</div>
												</div>


												<label class="col-sm-2 control-label "><spring:message
														code="license.details.propertyOwnerName"
														text="Property Owner Name" /></label>
												<div class="col-sm-4">
													<form:input path="tradeMasterDetailDTO.trdOwnerNm"
														id="primaryOwnerName"
														class="form-control mandColorClass hasCharacter"
														readonly="true" data-rule-required="" />
												</div>

											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label "><spring:message
														code="license.details.outstandingtype"
														text="Property Outstanding Tax" /></label>
												<div class="col-sm-4">
													<form:input
														path="tradeMasterDetailDTO.totalOutsatandingAmt"
														id="totalOutsatandingAmt"
														disabled="${command.viewMode eq 'V' ? true : false }"
														class="form-control mandColorClass hasNumber text-right"
														value="" placeholder="00.00" readonly="true"
														data-rule-required="" />
												</div>
												<c:if test="${command.sudaEnv eq 'Y'}">
													<label class="col-sm-2 control-label"><spring:message
															code="license.Water.outstanding"
															text="Water Outstanding Tax" /></label>
													<div class="col-sm-4">
														<form:input
															path="tradeMasterDetailDTO.totalWaterOutsatandingAmt"
															id="totalWaterOutsatandingAmt"
															disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control mandColorClass hasNumber text-right"
															placeholder="00.00" readonly="true" data-rule-required="" />
													</div>
												</c:if>


											</div>

											<div class="form-group">

												<label class="col-sm-2 control-label" for="address"><spring:message
														code="license.propert.address" text="Property Address" /></label>
												<div class="col-sm-4">
													<form:textarea class="form-control mandColorClass"
														id="trdpradd" maxlength="1000"
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="true" path="tradeMasterDetailDTO.propertyAddress"></form:textarea>
												</div>

												<label class="col-sm-2 control-label "><spring:message
														code="license.details.usagetype"
														text="Property Usage Type" /></label>
												<div class="col-sm-4">
													<form:input path="tradeMasterDetailDTO.usage" id="usage"
														disabled="${command.viewMode eq 'V' ? true : false }"
														class="form-control mandColorClass hasCharacter"
														readonly="true" data-rule-required="" />
												</div>

											</div>
                                            <div class="propDetails">
											<div class="form-group">
												<label class="col-sm-2 control-label" for=" Village name"><spring:message
														code="trade.app.villageName" text="Village Name" /></label>
												<div class="col-sm-4">
													<form:input class="form-control mandColorClass "
														id="villageName"
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="true" path="tradeMasterDetailDTO.villageName" />
												</div>

												<label class="col-sm-2 control-label" for="Survey no"><spring:message
														code="trade.app.surveyNo" text="Survey No/C.T.S No" /></label>
												<div class="col-sm-4">
													<form:input class="form-control mandColorClass "
														id="surveyNumber"
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="true" path="tradeMasterDetailDTO.surveyNumber" />
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label" for="Part No"><spring:message
														code="trade.app.partNo" text="Part No" /></label>
												<div class="col-sm-4">
													<form:input class="form-control mandColorClass "
														id="partNo"
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="true" path="tradeMasterDetailDTO.partNo" />
												</div>

												<label class="col-sm-2 control-label" for="Plot No"><spring:message
														code="trade.app.plotNo" text="Plot No" /></label>
												<div class="col-sm-4">
													<form:input class="form-control mandColorClass "
														id="plotNo"
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="true" path="tradeMasterDetailDTO.plotNo" />
												</div>

											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label" for="Road name"><spring:message
														code="trade.app.roadName" text="Road Name" /></label>


												<div>
													<c:set var="baseLookupCode" value="RFT" />
													<apptags:lookupField
														items="${command.getLevelData(baseLookupCode)}"
														path="tradeMasterDetailDTO.propLvlRoadType"
														cssClass="form-control" hasChildLookup="false"
														hasId="true" showAll="false"
														selectOptionLabelCode="license.select" disabled="true" />
												</div>

												<label class="col-sm-2 control-label"><spring:message
														code="trade.landType" text="Land Type" /> </label>
												<div>
													<c:set var="baseLookupCode" value="LDT" />
													<apptags:lookupField
														items="${command.getLevelData(baseLookupCode)}"
														path="tradeMasterDetailDTO.landType"
														cssClass="form-control " hasChildLookup="false"
														hasId="true" showAll="false"
														selectOptionLabelCode="license.select" disabled="true" />
												</div>


											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label" for="Area"><spring:message
														code="prop.trade.area" text="Area" /></label>
												<div class="col-sm-4">
													<form:input class="form-control mandColorClass"
														id="assPlotArea"
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="true" path="tradeMasterDetailDTO.assPlotArea" />
												</div>

											</div>
											</div>
										</c:when>
										<c:otherwise>

											<div class="form-group">

												<label class="col-sm-2 control-label"><spring:message
														code="trade.license.propertyNo" text="Property No." /></label>
												<div class="col-sm-4">
													<form:input path="tradeMasterDetailDTO.pmPropNo"
														id="propertyNo" class="form-control" value=""
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="" data-rule-required="" />

												</div>

												<%-- 	<apptags:input labelCode="license.details.flatNo"
													cssClass="" isMandatory="" placeholder="${flatNo}"
													isDisabled="${command.viewMode eq 'V' ? true : false }"
													path="tradeMasterDetailDTO.trdFlatNo"></apptags:input> --%>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label"><spring:message
														code="license.details.propertyOwnerName"
														text="Property Owner Name" /></label>
												<div class="col-sm-4">
													<form:input path="tradeMasterDetailDTO.trdOwnerNm"
														id="primaryOwnerName"
														class="form-control mandColorClass hasCharacter" value=""
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="" data-rule-required="" />
												</div>

											</div>

											<div class="form-group">

												<label class="col-sm-2 control-label"><spring:message
														code="license.details.outstandingtype"
														text="Property Outstanding Tax" /></label>
												<div class="col-sm-4">
													<form:input
														path="tradeMasterDetailDTO.totalOutsatandingAmt"
														id="totalOutsatandingAmt"
														disabled="${command.viewMode eq 'V' ? true : false }"
														class="form-control mandColorClass hasNumber text-right"
														value="" placeholder="00.00" readonly="true"
														data-rule-required="" />
												</div>
												
												<c:if test="${command.sudaEnv eq 'Y'}">
												<label class="col-sm-2 control-label"><spring:message
														code="license.Water.outstanding"
														text="Water Outstanding Tax" /></label>
												<div class="col-sm-4">
													<form:input
														path="tradeMasterDetailDTO.totalWaterOutsatandingAmt"
														id="totalWaterOutsatandingAmt"
														disabled="${command.viewMode eq 'V' ? true : false }"
														class="form-control mandColorClass hasNumber text-right"
														placeholder="00.00" readonly="true" data-rule-required="" />
												</div>
												</c:if>

											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label"><spring:message
														code="license.propert.address" text="Property Address" /></label>
												<div class="col-sm-4">
													<form:textarea
														class="form-control mandColorClass hasSpecialCharAndNumber"
														id="trdpradd" maxlength="200"
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="true" path="tradeMasterDetailDTO.trdFlatNo"></form:textarea>
												</div>
												
												
												<label class="col-sm-2 control-label"><spring:message
														code="license.details.usagetype"
														text="Property Usage Type" /></label>
												<div class="col-sm-4">
													<form:input path="tradeMasterDetailDTO.usage" id="usage"
														disabled="${command.viewMode eq 'V' ? true : false }"
														class="form-control mandColorClass hasCharacter" value=""
														readonly="true" data-rule-required="" />
												</div>

											</div>
     										<div class="propDetails">
											<div class="form-group">
												<label class="col-sm-2 control-label" for=" Village name"><spring:message
														code="trade.app.villageName" text="Village Name" /></label>
												<div class="col-sm-4">
													<form:input
														class="form-control mandColorClass hasSpecialCharAndNumber"
														id="villageName"
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="true" path="tradeMasterDetailDTO.villageName" />
												</div>

												<label class="col-sm-2 control-label" for="Survey no"><spring:message
														code="trade.app.surveyNo" text="Survey No/C.T.S No" /></label>
												<div class="col-sm-4">
													<form:input
														class="form-control mandColorClass hasSpecialCharAndNumber"
														id="surveyNumber"
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="true" path="tradeMasterDetailDTO.surveyNumber" />
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label" for="Part No"><spring:message
														code="trade.app.partNo" text="Part No" /></label>
												<div class="col-sm-4">
													<form:input
														class="form-control mandColorClass hasSpecialCharAndNumber"
														id="partNo"
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="true" path="tradeMasterDetailDTO.partNo" />
												</div>

												<label class="col-sm-2 control-label" for="Plot No"><spring:message
														code="trade.app.plotNo" text="Plot No" /></label>
												<div class="col-sm-4">
													<form:input
														class="form-control mandColorClass hasSpecialCharAndNumber"
														id=""
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="true" path="tradeMasterDetailDTO.PlotNo" />
												</div>

											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label" for="Road name"><spring:message
														code="trade.app.roadName" text="Road name" /></label>
												<div>
													<c:set var="baseLookupCode" value="RFT" />
													<apptags:lookupField
														items="${command.getLevelData(baseLookupCode)}"
														path="tradeMasterDetailDTO.propLvlRoadType"
														cssClass="form-control" hasChildLookup="false"
														hasId="true" showAll="false"
														selectOptionLabelCode="license.select" disabled="true" />
												</div>

												<label class="col-sm-2 control-label"><spring:message
														code="trade.landType" text="Land Type" /> </label>
												<div>
													<c:set var="baseLookupCode" value="LDT" />
													<apptags:lookupField
														items="${command.getLevelData(baseLookupCode)}"
														path="tradeMasterDetailDTO.landType"
														cssClass="form-control " hasChildLookup="false"
														hasId="true" showAll="false"
														selectOptionLabelCode="license.select" disabled="true" />
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="Area"><spring:message
														code="prop.trade.area" text="Area" /></label>
												<div class="col-sm-4">
													<form:input
														class="form-control mandColorClass hasSpecialCharAndNumber"
														id="assPlotArea"
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="true" path="tradeMasterDetailDTO.assPlotArea" />
												</div>
											</div>
											</div>
										</c:otherwise>
									</c:choose>



									<div id="agreementDate">
										<div class="form-group">
											<label class="col-sm-2 control-label" for="licenseFromPeriod"><spring:message
													code="" text="Agreement Start Date" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input
														class="form-control mandColorClass fromDateClass"
														placeholder="DD/MM/YYYY" autocomplete="off"
														id="agreementFromDate"
														disabled="${command.viewMode eq 'V' ? true : false }"
														path="tradeMasterDetailDTO.agreementFromDate"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>

											<label class="col-sm-2 control-label" for="licenseToPeriod"><spring:message
													code="" text="Agreement End Date" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input class="form-control mandColorClass toDateClass"
														placeholder="DD/MM/YYYY" autocomplete="off"
														id="agreementToDate"
														disabled="${command.viewMode eq 'V' ? true : false }"
														path="tradeMasterDetailDTO.agreementToDate"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>

										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label  required-control"><spring:message
												code="license.fire.noc" text="Fire NOC Applicable" /></label>
										<div class="col-sm-4">
											<label class="radio-inline margin-top-5"> <form:radiobutton
													disabled="${command.viewMode eq 'V' ? true : false }"
													path="isfireNOC" value="Y" id="r1" onclick="showNOC()" />
												<spring:message code="trd.yes" text="Yes" /></label> <label
												class="radio-inline margin-top-5"> <form:radiobutton
													disabled="${command.viewMode eq 'V' ? true : false }"
													path="isfireNOC" value="N" id="r2" onclick="showNOC()" />
												<spring:message code="trd.no" text="No" />
											</label>
										</div>
										<div id="NOCapplicable">

											<label class="col-sm-2 control-label addClass"><spring:message
													code="license.noc.no" text="Fire NOC NO" /></label>
											<div class="col-sm-4">

												<form:input class="form-control mandColorClass preventSpace"
													id="fireNOCNo" maxlength="10" placeholder="Fire NOC No."
													disabled="${command.viewMode eq 'V' ? true : false }"
													path="tradeMasterDetailDTO.fireNOCNo"></form:input>



											</div>
										</div>
									</div>
									<c:if test="${command.sudaEnv eq 'Y'}">
										<div class="form-group">
											<label class="col-sm-6 control-label"><spring:message
													code="trade.nameAndAdd.director"
													text="Name and Address of Director or Manager under mentioned voters and their registration rule 12(B) and 30(B)" /></label>
											<div class="col-sm-6">
												<form:textarea class="form-control mandColorClass"
													id="directorsNameAndAdd" maxlength="500"
													disabled="${command.viewMode eq 'V' ? true : false }"
													path="tradeMasterDetailDTO.directorsNameAndAdd"></form:textarea>
											</div>
										</div>
									</c:if>
									<div class="form-group">


										<label class="col-sm-2 control-label" for="gstNumber"><spring:message
												code="license.gst.no" text="Applicant GST No." /></label>
										<div class="col-sm-4">
											<form:input class="form-control mandColorClass preventSpace"
												id="app_gstNumber" maxlength="15"
												placeholder="Ex: 22AAAAA0000A1Z5"
												disabled="${command.viewMode eq 'V' ? true : false }"
												readonly="" path="tradeMasterDetailDTO.gstNo"></form:input>
										</div>


									</div>

									<c:if test="${command.sudaEnv eq 'Y'}">
										<div class="form-group">
											<label class="col-sm-2 control-label required-control"
												for="pincode"><spring:message code="trade.pincode"
													text="Pin Number" /></label>
											<div class="col-sm-4">
												<form:input class="form-control mandColorClass hasNumber"
													id="pincode" maxlength="6"
													disabled="${command.viewMode eq 'V' ? true : false }"
													path="tradeMasterDetailDTO.pincode"></form:input>
											</div>

											<label class="col-sm-2 control-label required-control"
												for="landMark"><spring:message code="trade.landMark"
													text="Land Mark" /></label>
											<div class="col-sm-4">
												<form:textarea class="form-control mandColorClass"
													id="landMark" maxlength="250"
													disabled="${command.viewMode eq 'V' ? true : false }"
													path="tradeMasterDetailDTO.landMark"></form:textarea>
											</div>
										</div>
									</c:if>
									
									<div class="form-group">

										<c:set var="baseLookupCode" value="MWZ" />
										<apptags:lookupFieldSet
											cssClass="form-control required-control" baseLookupCode="MWZ"
											hasId="true" pathPrefix="tradeMasterDetailDTO.trdWard"
											disabled="${command.viewMode eq 'V' ? true : false }"
											hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" showAll="false"
											isMandatory="true" />


									</div>
							
								</div>
							</div>
						</div>
						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a5"> <spring:message
										code="trade.businessdetial" text="Business Details" /></a>
							</h4>
							<div id="a5" class="panel-collapse collapse in">
								<div class="panel-body">
						
									<div class="form-group">
										<apptags:input labelCode="license.details.businessName"
											cssClass="" isMandatory="true"
											isReadonly="${command.viewMode eq 'V' ? true : false }"
											path="tradeMasterDetailDTO.trdBusnm"></apptags:input>



										<label class="col-sm-2 control-label required-control"
											for="address"><spring:message
												code="license.details.businessAddress"
												text="Business Address" /></label>
										<div class="col-sm-4">
											<form:textarea class="form-control mandColorClass"
												id="trdBusadd" maxlength="1000"
												disabled="${command.viewMode eq 'V' ? true : false }"
												readonly="" path="tradeMasterDetailDTO.trdBusadd"></form:textarea>
										</div>

									</div>
									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="BusinessStartDate"><spring:message
												code="license.details.businessStartDate"
												text="Business Start Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass addColor"
													placeholder="DD/MM/YYYY" autocomplete="off"
													id="businessStartDate"
													disabled="${command.viewMode eq 'V' ? true : false }"
													path="tradeMasterDetailDTO.trdLicisdate"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

									</div>


										


						<%--  <div class="panel panel-default">

							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a4"> <spring:message
										code="trade.details" /></a>
							</h4> --%>

							<div class="text-right">
								<h5  class="h" >
									<i class="text-red-1"><spring:message code="trd.areUnit.note" /></i>
								</h5>
							</div>
							<!-- <div id="a4" class="panel-collapse collapse in">
								<div class="panel-body"> -->
									<c:set var="d" value="0" scope="page"></c:set>
									<table
										class="table table-bordered  table-condensed margin-bottom-10"
										id="itemDetails">
										<thead>

											<tr>
												<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
													showOnlyLabel="false"
													pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
													isMandatory="true" hasLookupAlphaNumericSort="true"
													hasSubLookupAlphaNumericSort="true"
													cssClass="tradeCat form-control required-control"
													showAll="false"
													disabled="${command.viewMode eq 'V' ? true : false }"
													hasTableForm="true" showData="false" columnWidth="20%" />

												<th width="8%"><spring:message code="trade.item.value"
														text="Item Value" /><i class="text-red-1">*</i></th>

												<!-- <th></th> -->
												<c:if test="${command.hideshowAddBtn ne 'Y'}">
													<th width="5%"><a title="Add" id="addBtn"
														class="btn btn-blue-2 btn-sm addItemCF" onclick=""><i
															class="fa fa-plus"></i></a></th>
												</c:if>

												<%-- <th width="10%"><spring:message code="trade.rate"></spring:message><span
														class="mand">*</span></th> --%>
												<%-- <c:if test="${command.saveMode ne 'V'}">
													<th width="5%"><a title="Add"
														class="btn btn-blue-2 btn-sm addCF" onclick=""><i
															class="fa fa-plus"></i></a></th>
												</c:if> --%>
											</tr>
										</thead>
										<%-- <tfoot>
												<tr>
													<th colspan="2" class="text-right"><spring:message
															code="trade.total" /></th>
													<th colspan="1"><form:input path=""
															id="totalitemDetail" cssClass="form-control text-right"
															readonly="true" placeholder="00.00" /></th>
													<th colspan="1" class="text-right"></th>

												</tr>
											</tfoot> --%>
										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.tradeMasterDetailDTO.tradeLicenseItemDetailDTO) > 0}">
													<c:forEach var="taxData"
														items="${command.tradeMasterDetailDTO.tradeLicenseItemDetailDTO}"
														varStatus="status">
														<tr class="itemDetailClass">
															<form:hidden
																path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
																id="triId${d}" />
															<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
																showOnlyLabel="false"
																pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																isMandatory="true" hasLookupAlphaNumericSort="true"
																hasSubLookupAlphaNumericSort="true"
																disabled="${command.viewMode eq 'V' ? true : false }"
																cssClass="tradeCat form-control required-control "
																showAll="false" hasTableForm="true" showData="true" />

															<td><form:input
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																	type="text"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="${enterItemvalue}" id="trdUnit${d}" /></td>
															<%-- <c:if test="${command.saveMode ne 'V'}"> --%>
															<c:if test="${command.hideshowDeleteBtn ne 'Y'}">
																<td class="text-center"><a
																	href="javascript:void(0);"
																	class="btn btn-danger btn-sm delButton" id="deleteBtn"
																	onclick=""><i class="fa fa-minus"></i></a></td>
															</c:if>
															<%-- </c:if> --%>



														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="itemDetailClass">
														<form:hidden
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
															id="triId${d}" />
														<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
															showOnlyLabel="false"
															pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
															isMandatory="true" hasLookupAlphaNumericSort="true"
															hasSubLookupAlphaNumericSort="true"
															disabled="${command.viewMode eq 'V' ? true : false }"
															cssClass="tradeCat form-control required-control "
															showAll="false" hasTableForm="true" showData="true" />

														<td><form:input
																path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																type="text"
																disabled="${command.viewMode eq 'V' ? true : false }"
																class="form-control text-right unit required-control hasNumber"
																placeholder="${enterItemvalue}" id="trdUnit${d}" /></td>
														<td class="text-center"><a href="javascript:void(0);"
															class="btn btn-danger btn-sm delButton" onclick=""><i
																class="fa fa-minus"></i></a></td>


													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									<%-- 	<tfoot>
											<tr>
												<td colspan="5">
													<c:if test="${command.hideshowAddBtn ne 'Y'}">
													<td class="text-center width="5%"><a title="Add" id="addBtn"
														class="btn btn-blue-2 btn-sm addItemCF" onclick=""><i
															class="fa fa-plus"></i></a></td>
												</c:if>
												</td>
											</tr>
										</tfoot> --%>
									</table>
									</div>
									</div>
							<!--	</div>
							</div>
						 </div> -->
                        </div>
						<br>
						<div class="form-group">
							<div class="col-sm-12">
								<%-- <apptags:checkbox labelCode="trade.termsCondition" value="" path="" disabled="${command.viewMode eq 'V' ? true : false }"></apptags:checkbox> --%>
								<div class="checkbox">
									<label><input type="checkbox" value="" id="checkId"
										onChange="" data-rule-required="true"> <a
										href="javascript:void(0);"
										onclick="showTermsConditionForm(this);"><b
											class="text-large"><spring:message
													code="trade.termsCondition"></spring:message></b></a> </label>
								</div>
							</div>
						</div>


						<!---------------------------------------------------------------document upload start------------------------ -->

						<c:if
							test="${command.checkListApplFlag ne null && command.checkListApplFlag eq 'Y' && command.paymentCheck ne null}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title table" id="">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse1" href="#a5"><spring:message
												code="trd.doc.upload.det" text="Document Upload Details" /></a>
									</h4>
								</div>
								<div id="a5" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped">
												<tbody>
													<tr>
														<th><spring:message code="Sr.No" text="Sr.No" /></th>
														<th><spring:message code="document.group"
																text="Document Group" /></th>
														<th><spring:message code="trd.documetnDesc"
																text="Document Description" /></th>
														<th><spring:message code="document.status"
																text="Document Status" /></th>
														<th><spring:message code="document.upload"
																text="Upload document" /></th>
													</tr>
													<c:forEach items="${command.checkList}" var="lookUp"
														varStatus="lk">
														<tr>
															<td>${lookUp.documentSerialNo}</td>
															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
																	<td><label>${lookUp.doc_DESC_ENGL}</label></td>
																</c:when>
																<c:otherwise>
																	<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
																	<td><label>${lookUp.doc_DESC_Mar}</label></td>
																</c:otherwise>
															</c:choose>
															<td><form:input
																	path="checkList[${lk.index}].docDescription"
																	type="text" class="form-control alphaNumeric "
																	maxLength="100" id="docDescription[${lk.index}]"
																	data-rule-required="true" /></td>
															<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																<td><spring:message code="water.doc.mand" /></td>
															</c:if>
															<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																<td><spring:message code="water.doc.opt" /></td>
															</c:if>
															<td>
																<div id="docs_${lk}" class="">
																	<apptags:formField fieldType="7" labelCode=""
																		hasId="true" fieldPath="checkList[${lk.index}]"
																		isMandatory="false" showFileNameHTMLId="true"
																		fileSize="BND_COMMOM_MAX_SIZE"
																		checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																		maxFileCount="CHECK_LIST_MAX_COUNT"
																		validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																		currentCount="${lk.index}" checkListDesc="${docName}" />
																</div> 
																<small class="text-blue-2"> <spring:message
																		code="trade.checklist.validation"
																		text="(Upload Image File upto 5 MB)" />
															    </small>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</c:if>
						<!---------------------------------------------------------------document upload end------------------------ -->

						<c:if
							test="${command.paymentCheck ne null && command.paymentCheck eq 'Y'}">
							<div class="form-group">
								<label class="col-sm-2 control-label"><spring:message
										code="water.field.name.amounttopay" /></label>
								<div class="col-sm-4">
									<form:input class="form-control" path=""
										value="${command.offlineDTO.amountToShow}" maxlength="12"
										readonly="true"></form:input>
									<a class="fancybox fancybox.ajax text-small text-info"
										href="TradeApplicationForm.html?showChargeDetailsMarket"><spring:message
											code="water.lable.name.chargedetail" /> <i
										class="fa fa-question-circle "></i></a>
								</div>
								<c:if test="${command.sudaEnv eq 'Y' && command.tradeMasterDetailDTO.loiFee > 0}">
									<div class="col-sm-6">
										<span class="text-bold"><spring:message
												code="trade.applTime.loiCharges.note"
												text="If your application will be approved then you have to pay amount " />${command.tradeMasterDetailDTO.loiFee}</span>
									</div>
								</c:if>
							</div>

							<apptags:payment></apptags:payment>

						</c:if>


						<c:if test="${command.paymentCheck eq null}">
							<div class="padding-top-10 text-center">

								<button type="button" class="btn btn-success" id="continueForm"
									onclick="saveTradeLicenseForm(this);">
									<spring:message code="trade.proceed" />
								</button>

								<%-- <button type="button" class="btn btn-success" id="continueForm"
								onclick="getChecklistAndCharge(this);">
								<spring:message code="trade.proceed" />
							</button> --%>


								<button type="Reset" class="btn btn-warning" id="resetform"
									onclick="resetTradeForm(this)">
									<spring:message code="trade.reset" />
								</button>
								<button type="button" class="btn btn-danger" id="back"
									onclick="backPage()">
									<spring:message code="trade.back"></spring:message>
								</button>
							</div>
						</c:if>


						<c:if test="${command.paymentCheck ne null}">
							<%--  <jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />  --%>


							<div class="padding-top-10 text-center">


								<button type="button" class="btn btn-success" id="continueForm"
									onclick="generateChallanAndPayment(this);">
									<spring:message code="trade.btn.proceed" />
								</button>

								<button type="button" class="btn btn-danger" id="backEdit"
									onclick="editApplication(this);">
									<spring:message code="trade.editappln"></spring:message>
								</button>
								
								<button type="button" class="btn btn-danger" id="back"
									onclick="backPage()">
									<spring:message code="trade.back"></spring:message>
								</button>

							</div>

						</c:if>
						<c:if test="${command.backBtn eq 'Y'}">
							<div class="padding-top-10 text-center">

								<button type="button" class="btn btn-danger" id="bck"
									onclick="backPage()">
									<spring:message code="trade.back"></spring:message>
								</button>

							</div>
						</c:if>

					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>
