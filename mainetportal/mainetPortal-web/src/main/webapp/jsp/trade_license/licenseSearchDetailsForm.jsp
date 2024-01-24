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
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/trade_license/licenseSearchDetails.js"></script>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message
							code="trade.change.in.license.detailsearch.form"
							text="License Details Search Form"></spring:message></b>
				</h2>

				<apptags:helpDoc url="LicenseSearchDetails.html"></apptags:helpDoc>
			</div>

			<div class="widget-content padding">

				<form:form action="LicenseSearchDetails.html" class="form-horizontal"
					id="LicenseSearchDetails" name="LicenseSearchDetails">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;"></div>


					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">


						<div class="panel panel-default">
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">

										<label class="col-sm-2 control-label required-control"><spring:message
												code="license.no" text="License No." /></label>
										<div class="col-sm-4">

											<form:input path="tradeDetailDTO.trdLicno" type="text"
												class="form-control" id="licenseNo" />

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<c:if test="${command.licenseDetails eq 'Y'}">
						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="trade.licenseDetails" text="License Details" /></a>
								<div id="a3" class="panel-collapse collapse in">
									<div class="panel-body">


										<div class="form-group">


											<label class="col-sm-2 control-label required-control"><spring:message
													code="owner.details.name" text="Owner name" /></label>
											<div class="col-sm-4">
												<form:input path="ownerName" id="trdOwnerNm"
													class="form-control mandColorClass hasSpecialChara"
													value="" readonly="true" />
											</div>

										</div>

										<div class="form-group">

											<apptags:input labelCode="license.details.businessName"
												cssClass="hasCharacter hasSpecialChara" isReadonly="true"
												path="tradeDetailDTO.trdBusnm"></apptags:input>

											<label class="col-sm-2 control-label required-control"><spring:message
													code="license.details.businessAddress"
													text="Property Business Address" /></label>
											<div class="col-sm-4">
												<form:input path="tradeDetailDTO.trdBusadd" id="trdBusadd"
													class="form-control mandColorClass hasSpecialCharAndNumber"
													value="" readonly="true" />
											</div>
										</div>

										<div class="form-group">

											<c:set var="baseLookupCode" value="MWZ" />
											<apptags:lookupFieldSet
												cssClass="form-control required-control hasSpecialChara"
												baseLookupCode="MWZ" hasId="true"
												pathPrefix="tradeDetailDTO.trdWard" disabled="true"
												hasLookupAlphaNumericSort="true"
												hasSubLookupAlphaNumericSort="true" showAll="false" />

										</div>

										<div class="form-group">

											<label class="col-sm-2 control-label required-control"
												for="licenseFromPeriod"><spring:message
													code="license.details.licenseFromPeriod"
													text="License From Period" /></label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input class="form-control mandColorClass datepicker"
														id="licFromDate" readonly="true"
														path="tradeDetailDTO.trdLicfromDate"></form:input>
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
														id="lLictoDate" readonly="true"
														path="tradeDetailDTO.trdLictoDate"></form:input>
													<span class="input-group-addon"><i
														class="fa fa-calendar"></i></span>
												</div>
											</div>
										</div>
									</div>
								</div>
						</div>

						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a1"> <spring:message
										code="trade.details" /></a>
							</h4>
							<div id="a1" class="panel-collapse collapse in">
								<div class="panel-body">
									<c:set var="d" value="0" scope="page"></c:set>
									<table
										class="table table-bordered  table-condensed margin-bottom-10"
										id="itemDetails">
										<thead>

											<tr>
												<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
													showOnlyLabel="false"
													pathPrefix="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
													isMandatory="true" hasLookupAlphaNumericSort="true"
													hasSubLookupAlphaNumericSort="true"
													cssClass="form-control required-control hasSpecialChara"
													showAll="false" disabled="true" hasTableForm="true"
													showData="false" columnWidth="20%" />


												<th width="20%"><spring:message code="trade.item.value"
														text="Item Value" /></th>
											</tr>
										</thead>



										<tbody>
											<c:choose>
												<c:when
													test="${fn:length(command.tradeDetailDTO.tradeLicenseItemDetailDTO) > 0}">
													<c:forEach var="taxData"
														items="${command.tradeDetailDTO.tradeLicenseItemDetailDTO}"
														varStatus="status">
														<tr class="itemDetailClass">
															<form:hidden
																path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
																id="triId${d}" />
															<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
																showOnlyLabel="false"
																pathPrefix="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																hasLookupAlphaNumericSort="true"
																hasSubLookupAlphaNumericSort="true"
																cssClass="form-control required-control hasSpecialChara"
																disabled="true" showAll="false" hasTableForm="true"
																showData="true" />
															<%-- 
																<td><form:input
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
																	type="text"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="00.00" readonly="true" id="triRate${d}" /></td>  --%>


															<td><form:input
																	path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																	type="text"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="Enter Item Value" readonly="true"
																	id="trdUnit${d}" /></td>


														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr class="itemDetailClass">
														<form:hidden
															path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
															id="triId${d}" />
														<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
															showOnlyLabel="false"
															pathPrefix="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
															hasLookupAlphaNumericSort="true"
															hasSubLookupAlphaNumericSort="true" disabled="true"
															cssClass="form-control required-control hasSpecialChara"
															showAll="false" hasTableForm="true" showData="true" />


														<td><form:input
																path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																type="text"
																disabled="${command.viewMode eq 'V' ? true : false }"
																class="form-control text-right unit required-control hasNumber"
																placeholder="Enter Item Value" readonly="true"
																id="trdUnit${d}" /></td>

													</tr>

													<c:set var="d" value="${d + 1}" scope="page" />
												</c:otherwise>
											</c:choose>
										</tbody>
									</table>
								</div>
							</div>
						</div>



					</c:if>
					<div class="padding-top-10 text-center">
						<c:if test="${command.licenseDetails ne 'Y'}">


							<button type="button" class="btn btn-success" id="continueForm"
								onclick="getLicenseDetails(this);">
								<spring:message code="trade.search" />
							</button>

							<button type="Reset" class="btn btn-warning" id="resetform"
								onclick="resetDuplicateForm(this)">
								<spring:message code="trade.reset" />
							</button>
						</c:if>

						<button type="button" class="btn btn-danger" id="back"
							onclick="window.location.href = 'CitizenHome.html'">
							<spring:message code="trade.back"></spring:message>
						</button>
					</div>

				</form:form>
			</div>
		</div>
	</div>
</div>

