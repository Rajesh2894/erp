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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/trade_license/cancellationByForce.js"></script>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="trade.license.header.cancel.byforce"></spring:message></b>
				</h2>

				<apptags:helpDoc url="LicenseCancellationByForce.html"></apptags:helpDoc>
			</div>

			<div class="widget-content padding">

				<form:form action="LicenseCancellationByForce.html"
					class="form-horizontal" id="cancellationLicenseByforce"
					name="cancellationLicenseByforce">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<%-- <form:hidden path="" id="immediateService" value="${command.immediateServiceMode}" /> --%>


					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">


						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="trade.license.cancellation" /></a>
							</h4>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="" text="License No." /></label>
										<div class="col-sm-4">
											<form:select path="tradeDetailDTO.trdLicno" id="trdLicno"
												class="form-control mandColorClass chosen-select-no-results"
												onchange="">
												<form:option value="">
													<spring:message code='master.selectDropDwn' />
												</form:option>
												<c:forEach items="${command.tradeMasterDetailDTO}"
													var="activeProjName">
													<form:option value="${activeProjName.trdLicno}"
														code="${activeProjName.trdLicno}">${activeProjName.trdLicno}</form:option>
												</c:forEach>
											</form:select>
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
											code="trade.license.ownerDetails" /></a>
								</h4>
								<div id="a3" class="panel-collapse collapse in">
									<div class="panel-body">


										<div class="form-group">


											<%-- <label class="col-sm-2 control-label required-control"><spring:message
												code="license.no" text="License Number" /></label>
										<div class="col-sm-4">
											<form:input path="" id="trdLicno"
												class="form-control mandColorClass"
												value=""
												readonly="true" />
										</div>
										 --%>
											<label class="col-sm-2 control-label required-control"><spring:message
													code="owner.details.name" text="owner name" /></label>
											<div class="col-sm-4">
												<form:input
													path="tradeDetailDTO.tradeLicenseOwnerdetailDTO[0].troName"
													id="trdOwnerNm" class="form-control mandColorClass"
													value="" readonly="true" />
											</div>

										</div>

										<div class="form-group">

											<apptags:input labelCode="license.details.businessName"
												cssClass="hasCharacter" isReadonly="true"
												path="tradeDetailDTO.trdBusnm"></apptags:input>

											<label class="col-sm-2 control-label required-control"><spring:message
													code="license.details.businessAddress"
													text="Property Business Address" /></label>
											<div class="col-sm-4">
												<form:input path="tradeDetailDTO.trdBusadd" id="trdBusadd"
													class="form-control mandColorClass" value=""
													readonly="true" />
											</div>
										</div>

										<div class="form-group">

											<c:set var="baseLookupCode" value="MWZ" />
											<apptags:lookupFieldSet
												cssClass="form-control required-control"
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
														id="licFromDate" readonly="true" path="licFromDateDesc"></form:input>
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
														id="lLictoDate" readonly="true" path="licToDateDesc"></form:input>
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
														cssClass="form-control required-control" showAll="false"
														disabled="true" hasTableForm="true" showData="false"
														columnWidth="20%" />

													<th width="60%"><spring:message
															code="trade.item.value" /></th>
													<%-- <th width="20%"><spring:message code="trade.rate"></spring:message><span
														class="mand">*</span></th> --%>
												</tr>
											</thead>

											<%-- <tfoot>
												<tr>
													<th colspan="2" class="text-right"><spring:message
															code="trade.total" /></th>
													<th colspan="1"><form:input path="tbLoiMas[0].loiAmount"
															id="totalitemDetail" cssClass="form-control text-right"
															readonly="true" placeholder="00.00" /></th>
													<th colspan="1" class="text-right"></th>

												</tr>
											</tfoot> --%>

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
																<apptags:lookupFieldSet baseLookupCode="ITC"
																	hasId="true" showOnlyLabel="false"
																	pathPrefix="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																	hasLookupAlphaNumericSort="true"
																	hasSubLookupAlphaNumericSort="true"
																	cssClass="form-control required-control"
																	disabled="true" showAll="false" hasTableForm="true"
																	showData="true" />

																<td><form:input
																		path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																		type="text"
																		disabled="${command.viewMode eq 'V' ? true : false }"
																		class="form-control text-right unit required-control hasNumber"
																		placeholder="Enter Item value" readonly=""
																		id="trdUnit${d}" /></td>

																<%-- <td><form:input
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
																	type="text"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="00.00" readonly="true" id="triRate${d}" /></td>  --%>

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
																cssClass="form-control required-control "
																showAll="false" hasTableForm="true" showData="true" />

															<td><form:input
																	path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																	type="text"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="Enter Item value" readonly=""
																	id="trdUnit${d}" /></td>

															<%-- <td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
															type="text"
															class="form-control text-right unit required-control hasNumber"
															placeholder="00.00" readonly="true" id="triRate${d}" /></td>  --%>

														</tr>

														<c:set var="d" value="${d + 1}" scope="page" />
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</div>
								</div>
							</div>

							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a1"> <spring:message
											code="cancellation.remarks" /></a>
								</h4>
								<div class="form-group">
									<br>
									<apptags:textArea isMandatory="true"
										labelCode="trade.cancel.reason"
										path="tradeDetailDTO.cancelReason" maxlegnth="1000"
										cssClass="preventSpace"></apptags:textArea>

									<%-- <label class="col-sm-2 control-label required-control"
										for="CancelDate"><spring:message
											code="trade.cancel.date" text="Cancellation Date" /></label>

									<div class="col-sm-4">
										<form:input
											class="form-control mandColorClass datepicker2 addColor"
											placeholder="DD/MM/YYYY" autocomplete="off" id="cancelDate"
											path="tradeDetailDTO.cancelDate" readonly="true" size="10"></form:input>
									</div> --%>
								</div>
							</div>
							<div class="padding-bottom-10 text-center">

								<button type="button" class="btn btn-success" id="continueForm"
									onclick="saveCancellationByForce(this);">
									<spring:message code="trade.submit" />
								</button>

								<button type="button" class="btn btn-danger" id="back"
									onclick="backPage()">
									<spring:message code="trade.back"></spring:message>
								</button>

							</div>

						</c:if>


						<c:if test="${command.licenseDetails eq null}">
							<div class="padding-top-10 text-center">

								<button type="button" class="btn btn-success" id="continueForm"
									onclick="getLicenseDetails(this);">
									<spring:message code="trade.search" />
								</button>

								<button type="Reset" class="btn btn-warning" id="resetform"
									onclick="resetDuplicateForm(this)">
									<spring:message code="trade.reset" />
								</button>

								<button type="button" class="btn btn-danger" id="back"
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

