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
	src="js/trade_license/renewalLicenseForm.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="trade.licenseheader"></spring:message></b>
				</h2>

				<apptags:helpDoc url="RenewalLicenseForm.html"></apptags:helpDoc>
			</div>

			<div class="widget-content padding">

				<form:form action="RenewalLicenseForm.html" class="form-horizontal"
					id="renewalLicenseEditForm" name="renewalLicenseForm">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>
							<form:hidden path="" id="immediateService" value="${command.immediateServiceMode}" />
								<form:hidden path="licMaxTenureDays" id="licMaxTenureDays" />
				
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">


						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="trade.license.renewal" /></a>
							</h4>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">

										<label class="col-sm-2 control-label required-control"><spring:message
												code="license.no" text="License No." /></label>
										<div class="col-sm-4">

											<div class="input-group">
												<form:input path="tradeMasterDetailDTO.trdLicno" type="text"
													class="form-control" id="licenseNo" readonly="true"/>

												<a href="#" class="input-group-addon"
													onclick=""><i
													class="fa fa-search"></i></a>

											</div>
											<span class="text-small red"><spring:message
													code="renewal.search.msg" /></span>
										</div>
									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"><spring:message
												code="owner.details.name" text="owner name" /></label>
										<div class="col-sm-4">
											<form:input path="tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[0].troName"
												id="trdOwnerNm" class="form-control mandColorClass" value=""
												readonly="true" />
										</div>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="license.no" text="License Number" /></label>
										<div class="col-sm-4">
											<form:input path="" id="trdLicno"
												class="form-control mandColorClass"
												value="${command.tradeMasterDetailDTO.trdLicno}"
												readonly="true" />
										</div>

									</div>

									<div class="form-group">

										<apptags:input labelCode="license.details.businessName"
											cssClass="preventSpace" isReadonly="true"
											path="tradeMasterDetailDTO.trdBusnm"></apptags:input>

										<label class="col-sm-2 control-label required-control"><spring:message
												code="license.details.businessAddress"
												text="Property Business Address" /></label>
										<div class="col-sm-4">
											<form:input path="tradeMasterDetailDTO.trdBusadd"
												id="trdBusadd" class="form-control mandColorClass hasSpecialCharAndNumber" value=""
												readonly="true" />
										</div>
									</div>

									<div class="form-group">

										<c:set var="baseLookupCode" value="MWZ" />
										<apptags:lookupFieldSet
											cssClass="form-control required-control" baseLookupCode="MWZ"
											hasId="true" pathPrefix="tradeMasterDetailDTO.trdWard"
											disabled="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" showAll="false" />

									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="RenewallicenseFromPeriod"><spring:message
												code="license.details.licenseFromPeriod" text="License From Period" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="renewalLicfromDate" readonly="true"
													path="tradeMasterDetailDTO.trdLicfromDate"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

										<label class="col-sm-2 control-label required-control"
											for="RenewallicenseToPeriod"><spring:message
												code="license.details.licenseToPeriod" text="License To Period" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="renewalLictoDate" readonly="true"
													path="tradeMasterDetailDTO.trdLictoDate"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

						<%-- <div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="trade.renewal.history" /></a>
							</h4>
							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">

									<div class="form-group">
										<apptags:input labelCode="renewal.amtPaid"
											cssClass="hasNumber text-right" isReadonly="true"
											path="tradeMasterDetailDTO.totalApplicationFee"></apptags:input>
									</div>


									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="HistoryFromPeriod"><spring:message
												code="trade.from.date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="historyfromDate" readonly="true" path=""></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

										<label class="col-sm-2 control-label required-control"
											for="HistoryToPeriod"><spring:message
												code="trade.to.date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="historytoDate" readonly="true" path=""></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div> --%>

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

                                                <th width="20%"><spring:message code="" text="Item Value"/></th> 
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
																hasLookupAlphaNumericSort="true"
																hasSubLookupAlphaNumericSort="true"
																cssClass="form-control required-control" disabled="true"
																showAll="false" hasTableForm="true" showData="true" />
																
																
																<td><form:input
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																	type="text"
																	disabled="false"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="Enter Item Value" readonly="true"
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
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
															id="triId${d}" />
														<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
															showOnlyLabel="false"
															pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
															hasLookupAlphaNumericSort="true"
															hasSubLookupAlphaNumericSort="true" disabled="true"
															cssClass="form-control required-control " showAll="false"
															hasTableForm="true" showData="true" />
															
															
															
														<td><form:input
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																	type="text"
																	disabled="false"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="Enter Item Value" readonly="true"
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
			
                               <div class="form-group">
									<label class="control-label col-sm-2 required-control"
										for="Census"><spring:message code=""
											text="Renewal Period" /></label>
									<c:set var="baseLookupCode" value="LRP"   scope="page"/>
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="tradeMasterDetailDTO.renewalMasterDetailDTO.renewalPeriod"
										cssClass="mandColorClass form-control" isMandatory="true"
										hasId="true" selectOptionLabelCode="selectdropdown"
										disabled="false" />


								</div>

						<%-- <c:if test="${command.paymentCheck eq null}"> --%>
							<div class="padding-top-10 text-center">

								<button type="button" class="btn btn-success" id="continueForm"
									onclick="updateRenewlForm(this);">
									<spring:message code="trade.proceed" />
								</button>

								
								<button type="Reset" class="btn btn-warning" id="resetform"
										onclick="resetRenewalForm(this)">
										<spring:message code="trade.reset" />
								</button>

								<button type="button" class="btn btn-danger" id="back"
									onclick="backPage()">
									<spring:message code="trade.back"></spring:message>
								</button>
							</div>
						<%-- </c:if> --%>

					</div>
				
				</form:form>
			</div>
		</div>
	</div>
</div>