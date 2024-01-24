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
<script type="text/javascript" src="js/trade_license/transperLicense.js"></script>
<!-- <script src="js/cfc/scrutiny.js"></script> -->

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="" text="License transfer form"></spring:message></b>
				</h2>

				<apptags:helpDoc url="TransperLicenseForm.html"></apptags:helpDoc>
			</div>

			<div class="widget-content padding">

				<form:form action="TransperLicense.html" class="form-horizontal"
					id="TransperLicenseEditForm" name="TransperLicenseEditForm">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<%-- 	<form:hidden path="" id="immediateService" value="${command.immediateServiceMode}" /> --%>


					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">


						
                       <div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a6"> <spring:message
									code="" text="New Business Owner Details" /></a>
						</h4>
						<div id="a6" class="panel-collapse collapse in">
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

										<label id="transferMode" class="col-sm-2 control-label"
											for="transferMode"><spring:message
												code="license.details.transfer.mode" text="Transfer Mode"/></label>
										<div class="col-sm-4">
											<c:set var="baseLookupCode" value="LTM" />
											<form:select path="tradeMasterDetailDTO.transferMode"
												class="form-control mandColorClass" id="transferMode"
												disabled="${command.viewMode eq 'V' ? true : false }"
												data-rule-required="true">
												<form:option value="">
													<spring:message code="master.selectDropDwn" />
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

	

						<%-- 	<c:if test="${command.licenseDetails eq 'Y'}"> --%>
						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="" text="Trade License Details" /></a>
							</h4>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">


									<div class="form-group">


										<label class="col-sm-2 control-label required-control"><spring:message
												code="license.no" text="License Number" /></label>
										<div class="col-sm-4">
											<form:input path="tradeMasterDetailDTO.trdLicno" id="trdLicno"
												class="form-control mandColorClass"
												value=""
												readonly="true" />
										</div> 

										<label class="col-sm-2 control-label required-control"><spring:message
												code="" text="Old owner name" /></label>
										<div class="col-sm-4">
											<form:input
												path="tradeDetailDTO.tradeLicenseOwnerdetailDTO[0].troName"
												id="trdOldOwner" class="form-control mandColorClass" value=""
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
											<form:input path="tradeMasterDetailDTO.trdBusadd" id="trdBusadd"
												class="form-control mandColorClass hasSpecialCharAndNumber" value="" readonly="true" />
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
											for="licenseFromPeriod"><spring:message
												code="license.details.licenseFromPeriod"
												text="License From Period" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="licFromDate" readonly="true" path="tradeMasterDetailDTO.trdLicfromDate"></form:input>
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
													id="lLictoDate" readonly="true" path="tradeMasterDetailDTO.trdLictoDate"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
		</div>
		
		
		
		
		<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a10"> <spring:message
											code="trade.details" /></a>
								</h4>
								<div id="a10" class="panel-collapse collapse in">
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

	                                                <th width="20%"><spring:message code=""
															text="Item Value" /></th>
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
																<apptags:lookupFieldSet baseLookupCode="ITC"
																	hasId="true" showOnlyLabel="false"
																	pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																	hasLookupAlphaNumericSort="true"
																	hasSubLookupAlphaNumericSort="true"
																	cssClass="form-control required-control"
																	disabled="true" showAll="false" hasTableForm="true"
																	showData="true" />

	                                                               <td><form:input
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
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
																path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
																id="triId${d}" />
															<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
																showOnlyLabel="false"
																pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																hasLookupAlphaNumericSort="true"
																hasSubLookupAlphaNumericSort="true" disabled="true"
																cssClass="form-control required-control "
																showAll="false" hasTableForm="true" showData="true" />
																
																
																<td><form:input
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																	type="text"
																	disabled="${command.viewMode eq 'V' ? true : false }"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="Enter Item Value" readonly="true"
																	id="trdUnit${d}" /></td>	
																
																
>

														</tr>

														<c:set var="d" value="${d + 1}" scope="page" />
													</c:otherwise>
												</c:choose>
											</tbody>
										</table>
									</div>
								</div>
								</div>
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		


					<br>
					<div class="form-group">
						<div class="col-sm-12">
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


					<form:hidden path="appid" id="_appId" />
					<form:hidden path="labelid" id="_labelId" />
					<form:hidden path="serviceid" id="_serviceId" />


					<div class="padding-top-10 text-center">
						<div class="form-group">
							<button type="button" class="btn btn-success" id="continueForm"
								onclick="updateTradeLicenseForm(this)">
								<spring:message code="trade.proceed" />
							</button>
						</div>

						<div class="form-group">
							<div class="text-center padding-bottom-10" id="scrutinyDiv">
								<jsp:include page="/jsp/cfc/sGrid/scrutinyButtonTemplet.jsp"></jsp:include>
							</div>
						</div>

					</div>
					

				</form:form>


			</div>
		</div>
	</div>
</div>

