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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/trade_license/tradeLicenseApplicationForm.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>


<script>
	$(document).ready(
			function() {

				$(".panel-heading").hide();
				$('#submitButtonId').prop('disabled', true);
				$('input[type="text"],textarea,select,input[type="radio"]')
						.attr('disabled', true);
			});
</script>

<div id=pagediv>
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="license.applheader"></spring:message></b>
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
							id="errorDiv"></div>
						<form:hidden path="" id="removedIds" />
						<form:hidden path="length" id="length" />
						<form:hidden path="appid" id="_appId" />
						<form:hidden path="labelid" id="_labelId" />
						<form:hidden path="serviceid" id="_serviceId" />
						<form:hidden path="" id="sudaEnv" value="${command.sudaEnv}" />
						<form:hidden path="hideFlag" id="hideFlag" />

					</div>

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

						<c:if test="${command.scrutunyEditMode ne 'SM'}">
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
						</c:if>
						<div class="panel panel-default">
							<c:if test="${command.scrutunyEditMode ne 'SM'}">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a1"> <spring:message
											code="trade.licenseDetails" /></a>
								</h4>
								<div id="a1" class="panel-collapse collapse in">
									<div class="panel-body">


										<div class="form-group">
											<c:set var="baseLookupCode" value="MWZ" />
											<apptags:lookupFieldSet
												cssClass="form-control required-control"
												baseLookupCode="MWZ" hasId="true"
												pathPrefix="tradeMasterDetailDTO.trdWard"
												disabled="${command.viewMode eq 'V' ? true : false }"
												hasLookupAlphaNumericSort="true"
												hasSubLookupAlphaNumericSort="true" showAll="false"
												isMandatory="true" />
										</div>

										<div class="form-group">
											<label id="licenseType"
												class="col-sm-2 control-label required-control"
												for="licenseType"><spring:message
													code="license.details.licenseType" /></label>

											<div class="col-sm-4">
												<c:set var="baseLookupCode" value="LIT" />

												<form:select path="tradeMasterDetailDTO.trdLictype"
													onchange="" class="form-control mandColorClass"
													id="trdLictype"
													disabled="${command.viewMode eq 'V' ? true : false }"
													data-rule-required="true">
													<form:option value="">
														<spring:message code="property.sel.optn.licenseType" />
													</form:option>
													<c:forEach items="${command.getLevelData(baseLookupCode)}"
														var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>

										<div class="form-group">
											<%-- <label id="licenseType"
											class="col-sm-2 control-label required-control"
											for="licenseType"><spring:message
												code="license.details.licenseType" /></label>
												
												<div class="col-sm-4">
										<c:set var="baseLookupCode" value="LIT" />
										
										<form:select path="tradeMasterDetailDTO.trdLictype"
												onchange=""
												class="form-control mandColorClass" id="trdLictype"
												disabled="${command.viewMode eq 'V' ? true : false }"
												data-rule-required="true">
												<form:option value="">
													<spring:message code="property.sel.optn.licenseType" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
											</div> --%>
											<%-- <apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="tradeMasterDetailDTO.trdLictype"
											cssClass="form-control" hasChildLookup="false" hasId="true"
											showAll="false"
											selectOptionLabelCode="applicantinfo.label.select"
											disabled="${command.viewMode eq 'V' ? true : false }"
											isMandatory="true" showOnlyLabel="applicantinfo.label.title" /> --%>

										</div>

										<div id="licensePeriod">
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
											<div id="hideTemp">
												<div class="form-group">

													<label class="col-sm-2 control-label"><spring:message
															code="trade.license.propertyId" text="Property No." /></label>
													<div class="col-sm-4">

														<div class="input-group">
															<form:input path="tradeMasterDetailDTO.pmPropNo"
																placeholder="Enter Property Number" type="text"
																class="form-control" id="propertyNo" />

															<a href="#" class="input-group-addon"
																onclick="getPropertyDetails(this);"><i
																class="fa fa-search"></i></a>
														</div>
													</div>


													<label class="col-sm-2 control-label "><spring:message
															code="license.details.propertyOwnerName"
															text="Property Owner Name" /></label>
													<div class="col-sm-4">
														<form:input path="tradeMasterDetailDTO.trdOwnerNm"
															id="primaryOwnerName"
															class="form-control mandColorClass hasCharacter" value=""
															readonly="true" data-rule-required="" />
													</div>

												</div>

												<div class="form-group">

													<label class="col-sm-2 control-label "><spring:message
															code="license.details.usagetype"
															text="Property Usage Type" /></label>
													<div class="col-sm-4">
														<form:input path="tradeMasterDetailDTO.usage" id="usage"
															disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control mandColorClass hasCharacter" value=""
															readonly="true" data-rule-required="" />
													</div>



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

												</div>

												<div class="form-group">
													<label class="col-sm-2 control-label" for="address"><spring:message
															code="license.details.proertyAddress"
															text="Property Address" /></label>
													<div class="col-sm-4">
														<form:textarea
															class="form-control mandColorClass hasCharacter"
															id="propertyBusadd" maxlength="1000"
															disabled="${command.viewMode eq 'V' ? true : false }"
															readonly="true" path="tradeMasterDetailDTO.trdFlatNo"></form:textarea>
													</div>


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
																id="plotNo"
																disabled="${command.viewMode eq 'V' ? true : false }"
																readonly="true" path="tradeMasterDetailDTO.plotNo" />
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
											</c:when>
											<c:otherwise>
												<div id="hideTemp">
												<div class="form-group">

													<label class="col-sm-2 control-label required-control"><spring:message
															code="trade.license.propertyId" text="Property No." /></label>
													<div class="col-sm-4">
														<form:input path="tradeMasterDetailDTO.pmPropNo"
															placeholder="Enter Property Number" id="propertyNo"
															class="form-control" value=""
															disabled="${command.viewMode eq 'V' ? true : false }"
															readonly="" data-rule-required="" />

													</div>


													<apptags:input labelCode="license.details.flatNo"
														cssClass="hasNumber" isMandatory=""
														placeholder="Enter Flat/Shop Number"
														isDisabled="${command.viewMode eq 'V' ? true : false }"
														path="tradeMasterDetailDTO.trdFlatNo"></apptags:input>

												</div>

												<div class="form-group">

													<label class="col-sm-2 control-label required-control"><spring:message
															code="license.details.propertyOwnerName"
															text="Property Owner Name" /></label>
													<div class="col-sm-4">
														<form:input path="tradeMasterDetailDTO.trdOwnerNm"
															id="propertyOwnerName"
															placeholder="Enter Property Owner Name"
															class="form-control mandColorClass hasCharacter" value=""
															disabled="${command.viewMode eq 'V' ? true : false }"
															readonly="" data-rule-required="" />
													</div>

													<%-- <label class="col-sm-2 control-label required-control"><spring:message
														code="license.details.proertyAddress"
														text="Property Address" /></label>
												<div class="col-sm-4">
													<form:input path="tradeMasterDetailDTO.propertyAddress"
														id="propertyAddress" placeholder="Enter Property Address"
														class="form-control mandColorClass hasCharacter" value=""
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="" data-rule-required="" />
												</div> --%>

												</div>
												</div>


												<%-- <div class="form-group">

												<apptags:input labelCode="license.details.businessName"
													cssClass="hasCharacter" isMandatory="true"
													isDisabled="${command.viewMode eq 'V' ? true : false }"
													path="tradeMasterDetailDTO.trdBusnm"></apptags:input>

												<label class="col-sm-2 control-label required-control"
													for="address"><spring:message
														code="license.details.businessAddress" text="Business Address" /></label>
												<div class="col-sm-4">
													<form:input class="form-control mandColorClass"
														id="trdBusadd" maxlength="1000"
														disabled="${command.viewMode eq 'V' ? true : false }"
														readonly="" path="tradeMasterDetailDTO.trdBusadd"></form:input>
												</div>

											</div> --%>



												<div id="agreementDate">
													<div class="form-group">
														<label class="col-sm-2 control-label"
															for="licenseFromPeriod"><spring:message code=""
																text="Agreement Start Date" /></label>
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

														<label class="col-sm-2 control-label"
															for="licenseToPeriod"><spring:message code=""
																text="Agreement End Date" /></label>
														<div class="col-sm-4">
															<div class="input-group">
																<form:input
																	class="form-control mandColorClass toDateClass"
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




											</c:otherwise>
										</c:choose>

										<%-- <div class="form-group">
									
									<label class="col-sm-2 control-label required-control"><spring:message
									code="trade.area" text="Area" /></label>
									
								<div class="col-sm-4">
										<form:input cssClass="form-control mandColorClass text-right"
											onkeypress="return hasAmount(event, this, 6, 2)"
											id="trdFarea" path="tradeMasterDetailDTO.trdFarea"
											disabled="${command.viewMode eq 'V' ? true : false }"
											onchange="getAmountFormatInDynamic((this),'trdFarea')"
											data-rule-required="true"></form:input>
								</div>
									</div> --%>
									</div>
								</div>
							</c:if>


							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a4"> <spring:message
											code="trade.details" /></a>
								</h4>
								<div id="a4" class="panel-collapse collapse in">
									<div class="panel-body">


										<div class="form-group">

											<apptags:input labelCode="license.details.businessName"
												cssClass="hasCharacter" isMandatory="true"
												placeholder="Enter Buisness Name"
												isDisabled="${command.viewMode eq 'V' ? true : false }"
												path="tradeMasterDetailDTO.trdBusnm"></apptags:input>

											<label class="col-sm-2 control-label required-control"
												for="address"><spring:message
													code="license.details.businessAddress"
													text="Business Address" /></label>
											<div class="col-sm-4">
												<form:input class="form-control mandColorClass"
													id="trdBusadd" maxlength="1000"
													placeholder="Enter Business Address"
													disabled="${command.viewMode eq 'V' ? true : false }"
													readonly="" path="tradeMasterDetailDTO.trdBusadd"></form:input>
											</div>

										</div>

										<div class="form-group">

											<%-- <label class="col-sm-2 control-label required-control"><spring:message
									code="trade.area" text="Area" /></label>
									
								<div class="col-sm-4">
										<form:input cssClass="form-control mandColorClass text-right"
											onkeypress="return hasAmount(event, this, 6, 2)"
											id="trdFarea" path="tradeMasterDetailDTO.trdFarea"
											disabled="${command.viewMode eq 'V' ? true : false }"
											onchange="getAmountFormatInDynamic((this),'trdFarea')"
											data-rule-required="true"></form:input>
								</div> --%>

											<label class="col-sm-2 control-label required-control"
												for="BusinessStartDate"><spring:message
													code="license.details.businessStartDate"
													text="Business Start Date" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input class="form-control mandColorClass datepicker"
														id="businessStartDate"
														disabled="${command.viewMode eq 'V' ? true : false }"
														path="tradeMasterDetailDTO.trdLicisdate"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>



										</div>


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
														cssClass="form-control required-control" showAll="false"
														hasTableForm="true" showData="false" columnWidth="20%" />

													<th width="8%"><spring:message code="trade.item.value" /></th>
													<%-- <th width="8%"><spring:message code="trade.item.quantity" /></th>
														<th width="8%"><spring:message code="trade.item.unit" /> --%>

													<c:if test="${command.scrutunyEditMode ne 'SM'}">
														<th width="5%"><a title="Add"
															class="btn btn-blue-2 btn-sm addCF" onclick=""><i
																class="fa fa-plus"></i></a></th>
													</c:if>
													<c:if test="${command.scrutunyEditMode eq 'SM'}">
														<th width="5%"><spring:message code="license.select"
																text="Select" /></th>
													</c:if>


												</tr>
											</thead>
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
																<apptags:lookupFieldSet baseLookupCode="ITC"
																	hasId="true" showOnlyLabel="false"
																	pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																	isMandatory="true" hasLookupAlphaNumericSort="true"
																	hasSubLookupAlphaNumericSort="true"
																	cssClass="form-control required-control "
																	showAll="false" hasTableForm="true" showData="true" />

																<td><c:choose>
																		<c:when test="${command.valueEdit}">

																			<form:input
																				path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																				type="tex" disabled="false" readonly="false"
																				class="form-control text-right unit required-control hasNumber"
																				placeholder="Enter Item Quantity" id="trdUnit${d}" />
																		</c:when>
																		<c:otherwise>
																			<form:input
																				path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																				type="text"
																				disabled="${command.viewMode eq 'V' ? true : false }"
																				class="form-control text-right unit required-control hasNumber"
																				placeholder="Enter Item Quantity" readonly=""
																				id="trdUnit${d}" />
																		</c:otherwise>
																	</c:choose></td>


																<%-- <td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdQuantity"
															type="text" disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control text-right unit required-control hasNumber"
															placeholder="Enter Item Quantity" readonly="" id="trdQuantity${d}" /></td>
															
															<td>
															<form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
															type="text" disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control text-right unit required-control hasNumber"
															placeholder="Enter Item Unit" readonly="" id="trdUnit${d}" />
															</td> --%>


																<c:if test="${command.scrutunyEditMode ne 'SM'}">
																	<td class="text-center"><a
																		href="javascript:void(0);"
																		class="btn btn-danger btn-sm " onclick=""><i
																			class="fa fa-minus"></i></a></td>
																</c:if>
																<c:if test="${command.scrutunyEditMode eq 'SM'}">
																	<td class="text-center"><form:checkbox
																			path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].selectedItems"
																			id="selectedItems${d}" /></td>
																</c:if>

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
																cssClass="form-control required-control "
																showAll="false" hasTableForm="true" showData="true" />

															<td><c:choose>
																	<c:when test="${command.valueEdit}">
																
																		<form:input
																			path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																			type="tex" disabled="false"
																			class="form-control text-right unit required-control hasNumber"
																			placeholder="Enter Item Quantity" readonly="false"
																			id="trdUnit${d}" />
																	</c:when>
																	<c:otherwise>
																		<form:input
																			path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																			type="text"
																			disabled="${command.viewMode eq 'V' ? true : false }"
																			class="form-control text-right unit required-control hasNumber"
																			placeholder="Enter Item Quantity" readonly=""
																			id="trdUnit${d}" />
																	</c:otherwise>
																</c:choose></td>

															<%-- <td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdQuantity"
															type="text" disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control text-right unit required-control hasNumber"
															placeholder="Enter Item Quantity" readonly="" id="trdQuantity${d}" /></td>
															
															<td>
															<form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
															type="text" disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control text-right unit required-control hasNumber"
															placeholder="Enter Item Unit" readonly="" id="trdUnit${d}" />
															</td>			 --%>

															<c:if test="${command.scrutunyEditMode ne 'SM'}">
																<td class="text-center"><a
																	href="javascript:void(0);"
																	class="btn btn-danger btn-sm " onclick=""><i
																		class="fa fa-minus"></i></a></td>
															</c:if>
															<c:if test="${command.scrutunyEditMode eq 'SM'}">
																<td class="text-center"><form:checkbox
																		path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].selectedItems"
																		id="selectedItems${d}" /></td>
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
							<%-- <div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a3"> <spring:message
											code="trade.details" /></a>
								</h4>
								<div id="a3" class="panel-collapse collapse in">
									<div class="panel-body">
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
														cssClass="form-control required-control" showAll="false"
														hasTableForm="true" showData="false" columnWidth="20%" />

													<c:if test="${command.scrutunyEditMode ne 'SM'}">
														<th width="5%"><a title="Add" 
															class="btn btn-blue-2 btn-sm addCF" onclick=""><i
																class="fa fa-plus"></i></a></th>
													</c:if>
													<c:if test="${command.scrutunyEditMode eq 'SM'}">
														<th width="5%">Select</th>
													</c:if>


												</tr>
											</thead>

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
																<apptags:lookupFieldSet baseLookupCode="ITC"
																	hasId="true" showOnlyLabel="false"
																	pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																	isMandatory="true" hasLookupAlphaNumericSort="true"
																	hasSubLookupAlphaNumericSort="true"
																	cssClass="form-control required-control "
																	showAll="false" hasTableForm="true" showData="true" />


																<c:if test="${command.scrutunyEditMode ne 'SM'}">
																	<td class="text-center"><a
																		href="javascript:void(0);"
																		class="btn btn-danger btn-sm delButton"
																		onclick="deleteTableRow('itemDetails', $(this), 'removedIds', 'isDataTable');"><i
																			class="fa fa-minus"></i></a></td>
																</c:if>
																<c:if test="${command.scrutunyEditMode eq 'SM'}">
																	<td class="text-center"><form:checkbox
																			path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].selectedItems"
																			id="selectedItems${d}" /></td>
																</c:if>

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
																cssClass="form-control required-control "
																showAll="false" hasTableForm="true" showData="true" />

															<c:if test="${command.scrutunyEditMode ne 'SM'}">
																<td class="text-center"><a
																	href="javascript:void(0);"
																	class="btn btn-danger btn-sm delButton"
																	onclick="deleteTableRow('itemDetails', $(this), 'removedIds', 'isDataTable');"><i
																		class="fa fa-minus"></i></a></td>
															</c:if>
															<c:if test="${command.scrutunyEditMode eq 'SM'}">
																<td class="text-center"><form:checkbox
																		path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].selectedItems"
																		id="selectedItems${d}" /></td>
															</c:if>
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</div>
								</div>
							</div> --%>

							<br>
							<%-- <c:if test="${command.scrutunyEditMode ne 'SM'}">
							<div class="form-group">
								<div class="col-sm-12">
									<div class="checkbox">
										<label><input type="checkbox" value=""
											onChange="valueChanged()" data-rule-required="true">
											<a href="javascript:void(0);"
											onclick="showTermsConditionForm(this);"><b
												class="text-large"><spring:message
														code="trade.license.termscondition"></spring:message></b></a> </label>
									</div>
								</div>
							</div>
							</c:if> --%>


							<%-- <div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
										code="" text="Application number" /></label>
								<div class="col-sm-4">
									<form:input path="tradeMasterDetailDTO.apmApplicationId"
										id="applicationNo"
										class="form-control mandColorClass hasCharacter" value=""
										readonly="true" />
								</div>
							</div> --%>

							<!---------------------------------------------------------------document upload start------------------------ -->
							<c:if test="${command.scrutunyEditMode ne 'SM'}">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title table" id="">
											<a data-toggle="collapse" class=""
												data-parent="#accordion_single_collapse1" href="#a5"><spring:message
													text="Document Upload Details" /></a>
										</h4>
									</div>
									<div class="panel-body">
										<c:if test="${not empty command.documentList}">
											<fieldset class="fieldRound">
												<div class="overflow">
													<div class="table-responsive">
														<table
															class="table table-hover table-bordered table-striped">
															<tbody>
																<tr>
																	<th><label class="tbold"><spring:message
																				code="tp.serialNo" text="Sr No" /></label></th>
																	<th><label class="tbold"><spring:message
																				code="tp.docName" text="Document Name" /></label></th>
																	<th><label class="tbold"><spring:message
																				code="water.download" /></label></th>
																</tr>

																<c:forEach items="${command.documentList}" var="lookUp"
																	varStatus="lk">
																	<tr>
																		<td><label>${lk.count}</label></td>
																		<c:choose>
																			<c:when
																				test="${userSession.getCurrent().getLanguageId() eq 1}">
																				<td><label>${lookUp.clmDescEngl}</label></td>
																			</c:when>
																			<c:otherwise>
																				<td><label>${lookUp.clmDesc}</label></td>
																			</c:otherwise>
																		</c:choose>
																		<td>

																			<div>
																				<apptags:filedownload filename="${lookUp.attFname}"
																					filePath="${lookUp.attPath}"
																					actionUrl="AdminHome.html?Download"></apptags:filedownload>
																			</div>

																		</td>
																	</tr>
																</c:forEach>
															</tbody>
														</table>
													</div>
												</div>
											</fieldset>
										</c:if>
									</div>
								</div>
							</c:if>
							<!---------------------------------------------------------------document upload end------------------------ -->

							<%-- <c:if test="${command.scrutunyEditMode ne 'SM'}">
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="water.field.name.amounttopay" /></label>
									<div class="col-sm-4">
										<form:input class="form-control"
											path="tradeMasterDetailDTO.totalApplicationFee"
											maxlength="12" readonly="true"></form:input>
										<a class="fancybox fancybox.ajax text-small text-info"
											href="TradeApplicationForm.html?showChargeDetailsMarket"><spring:message
												code="water.lable.name.chargedetail" /> <i
											class="fa fa-question-circle "></i></a>
									</div>
								</div>
							</c:if> --%>

							<c:if test="${command.scrutunyEditMode eq 'SM'}">

								<div class="text-center padding-bottom-10">
									<button type="button" class="btn btn-success"
										onclick="editForm(this)" id="editButtonId">
										<spring:message text="save" code="trade.proceed" />
									</button>
								</div>
							</c:if>


						</div>
					</div>
				</form:form>
				<div class="text-center padding-bottom-10" id="scrutinyDiv">
					<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	var trdLicfromDate = $('#trdLicfromDate').val();
	var trdLictoDate = $('#trdLictoDate').val();
	var agreementFromDate = $('#agreementFromDate').val();
	var businessStartDate = $('#businessStartDate').val();
	if (trdLicfromDate) {
		$('#trdLicfromDate').val(trdLicfromDate.split(' ')[0]);
	}
	if (trdLictoDate) {
		$('#trdLictoDate').val(trdLictoDate.split(' ')[0]);
	}

	if (agreementFromDate) {
		$('#agreementFromDate').val(agreementFromDate.split(' ')[0]);
	}

	if (businessStartDate) {
		$('#businessStartDate').val(businessStartDate.split(' ')[0]);
	}
</script>