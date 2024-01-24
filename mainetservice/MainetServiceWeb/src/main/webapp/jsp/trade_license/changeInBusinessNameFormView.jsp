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
	src="js/trade_license/changeInBusinessNameForm.js"></script>
<style>
	#scrutinyDiv {
		display: inline-block;
	}
	#scrutinyDiv > .btn_fld {
		margin: 0 !important;
	}
</style>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="trade.change.in.business.name"></spring:message></b>
				</h2>

				<apptags:helpDoc url="ChangeInBusinessNameForm.html"></apptags:helpDoc>
			</div>

			<div class="widget-content padding">

				<form:form action="ChangeInBusinessNameForm.html"
					class="form-horizontal" id="changeinBusiness"
					name="changeinBusiness">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
						<form:hidden path="" id="viewMode" value="${command.viewMode}" />
						<%-- <form:hidden path="" id="immediateService" value="${command.immediateServiceMode}" /> --%>


					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

						<c:if test="${command.scrutunyEditMode ne 'SM'}">
						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="trade.license.changeInBusiness" /></a>
							</h4>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="trade.applicationNo" /></label>
					
					
					<div class="col-sm-4">
						
						<form:input path="tradeMasterDetailDTO.trdLicno" id="trdLicno" class="form-control mandColorClass"
						value="" readonly="true" />
					</div>
					
					</div>
								</div>
							</div>
						</div>
						</c:if>

						<%-- <c:if test="${command.licenseDetails eq 'Y'}"> --%>
							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a3"> <spring:message
											code="lic.details" /></a>
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
												<%-- <form:input path="tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[0].troName" --%>
												<form:input path="tradeMasterDetailDTO.trdOwnerNm"
													id="trdOwnerNm" class="form-control mandColorClass"
													value="" readonly="true" />
											</div>

										</div>

										<div class="form-group">

											<apptags:input labelCode="license.details.businessName"
												cssClass="hasCharacter" isReadonly="true"
												path="tradeMasterDetailDTO.trdBusnm"></apptags:input>

											<label class="col-sm-2 control-label required-control"><spring:message
													code="license.details.businessAddress"
													text="Property Business Address" /></label>
											<div class="col-sm-4">
												<form:input path="tradeMasterDetailDTO.trdBusadd"
													id="trdBusadd" class="form-control mandColorClass" value=""
													readonly="true" />
											</div>
										</div>

										<div class="form-group">

											<c:set var="baseLookupCode" value="MWZ" />
											<apptags:lookupFieldSet
												cssClass="form-control required-control"
												baseLookupCode="MWZ" hasId="true"
												pathPrefix="tradeMasterDetailDTO.trdWard" disabled="true"
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
														pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
														isMandatory="true" hasLookupAlphaNumericSort="true"
														hasSubLookupAlphaNumericSort="true"
														cssClass="form-control required-control" showAll="false"
														disabled="true" hasTableForm="true" showData="false"
														columnWidth="20%" />

													<th width="20%"><spring:message code="trade.item.value" /></th>
													<%-- <th width="20%"><spring:message code="trade.rate"></spring:message><span
														class="mand">*</span></th> --%>
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
																	hasLookupAlphaNumericSort="true"
																	hasSubLookupAlphaNumericSort="true"
																	cssClass="form-control required-control"
																	disabled="${command.viewMode ne 'N'}" showAll="false" hasTableForm="true"
																	showData="true" />
																	
																	<td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
															type="text" disabled="${command.viewMode ne 'N'}"
															class="form-control text-right unit required-control hasNumber"
															placeholder="Enter Item value" readonly="" id="trdUnit${d}" /></td>
															
															<c:if test="${command.scrutunyEditMode eq 'SM'}">
																	<td class="text-center"><form:checkbox
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].selectedItems"
																	id="selectedItems${d}" /></td>
																</c:if>

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
																path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
																id="triId${d}" />
															<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
																showOnlyLabel="false"
																pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																hasLookupAlphaNumericSort="true"
																hasSubLookupAlphaNumericSort="true" disabled="${command.viewMode ne 'N'}"
																cssClass="form-control required-control "
																showAll="false" hasTableForm="true" showData="true" />
																
																<td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
															type="text" disabled="${command.viewMode ne 'N'}"
															class="form-control text-right unit required-control hasNumber"
															placeholder="Enter Item value" readonly="" id="trdUnit${d}" /></td>
															
															<c:if test="${command.scrutunyEditMode eq 'SM'}">
															<td class="text-center"><form:checkbox
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].selectedItems"
																id="selectedItems${d}" /></td>
															</c:if>

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
							
							
							<c:if test="${command.scrutunyEditMode ne 'SM'}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title table" id="">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse1" href="#a5"><spring:message
												code="trade.document.name" text="Document Upload Details" /></a>
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

							<%-- <c:if
							test="${command.paymentCheck ne null && command.paymentCheck eq 'Y'}">
							<div class="padding-top-10 text-center">

								<button type="button" class="btn btn-success" id="continueForm"
									onclick="generateChallanAndPayment(this);">
									<spring:message code="trade.submit" />
								</button>

								<button type="button" class="btn btn-danger" id="back"
									onclick="backPage()">
									<spring:message code="trade.back"></spring:message>
								</button>

							</div>

						</c:if> --%>
						<%-- </c:if> --%>

						<%-- <c:if test="${command.licenseDetails eq null}">
							<div class="padding-top-10 text-center">

								<button type="button" class="btn btn-success" id="continueForm"
									onclick="getLicenseDetails(this);">
									<spring:message code="trade.search" />
								</button>

								<button type="button" class="btn btn-danger" id="back"
									onclick="backPage()">
									<spring:message code="trade.back"></spring:message>
								</button>

								<button type="Reset" class="btn btn-warning" id="resetform"
									onclick="resetCancellationForm(this)">
									<spring:message code="trade.reset" />
								</button>

							</div>
						</c:if> --%>
						
						<%-- <c:if test="${command.checklistCheck eq 'Y'}">
							<div class="padding-top-10 text-center">

								<button type="button" class="btn btn-success" id="continueForm"
									onclick="getChecklistAndCharges(this);">
									<spring:message code="trade.proceed" />
								</button>

								<button type="button" class="btn btn-danger" id="back"
									onclick="backPage()">
									<spring:message code="trade.back"></spring:message>
								</button>

							</div>
						</c:if> --%>
						<div class="text-center padding-bottom-10">
							<c:if test="${command.scrutunyEditMode eq 'SM'}">
								<button type="button" class="btn btn-green-1"
									onclick="editForm(this)" id="editButtonId">
									<spring:message text="save" code="trade.proceed" />
								</button>
							</c:if>
							<%-- Defect #156053 --%>
							<div id="scrutinyDiv">
								<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp"></jsp:include>
							</div>
						</div>
					</div>
				</form:form>
				
			</div>
		</div>
	</div>
</div>

<script>
	var licFromDate = $('#licFromDate').val();
	var lLictoDate = $('#lLictoDate').val();
	if (licFromDate) {
		$('#licFromDate').val(licFromDate.split(' ')[0]);
	}
	if (lLictoDate) {
		$('#lLictoDate').val(lLictoDate.split(' ')[0]);
	}
</script>