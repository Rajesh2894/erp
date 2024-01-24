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

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="license.transfer.form" text="License transfer form"></spring:message></b>
				</h2>

				<apptags:helpDoc url="TransperLicenseForm.html"></apptags:helpDoc>
			</div>

			<div class="widget-content padding">

				<form:form action="TransperLicense.html"
					class="form-horizontal" id="TransperLicenseForm"
					name="TransperLicenseForm">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<%-- 	<form:hidden path="" id="immediateService" value="${command.immediateServiceMode}" /> --%>
                  <div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a6"> <spring:message
									code="trade.new.bsns.own.det" text="New Business Owner Details"/></a>
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
											code="license.details.transfer.mode" text="Transfer Mode" /></label>
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

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

					<%-- 	<c:if test="${command.licenseDetails eq 'Y'}"> --%>
							<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a3"> <spring:message
											code="lic.details" text=" License Details"/></a>
								</h4>
								<div id="a3" class="panel-collapse collapse in">
									<div class="panel-body">


										<div class="form-group">


										<label class="col-sm-2 control-label required-control"><spring:message
												code="trade.lic.no" text="License No" /></label>
										<div class="col-sm-4">
											<form:input path="tradeMasterDetailDTO.trdLicno" id="trdLicno"
												class="form-control mandColorClass hasSpecialChara" value=""
												readonly="true" />
										</div>

										
											<label class="col-sm-2 control-label required-control"><spring:message
													code="license.old.owner.name" text=" Old owner name" /></label>
											<div class="col-sm-4">
												<form:input path="tradeDetailDTO.tradeLicenseOwnerdetailDTO[0].troName"
													id="trdOldOwnerNm" class="form-control mandColorClass"
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
													id="trdBusadd" class="form-control mandColorClass hasSpecialCharAndNumber" value=""
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
								<div id="a5" class="panel-collapse collapse in">	
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
							</div>
						</c:if>
						<!---------------------------------------------------------------document upload end------------------------ -->


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
	var licFromDate = $('#licFromDate').val();
	var lLictoDate = $('#lLictoDate').val();
	if (licFromDate) {
		$('#licFromDate').val(licFromDate.split(' ')[0]);
	}
	if (lLictoDate) {
		$('#lLictoDate').val(lLictoDate.split(' ')[0]);
	}
</script>
 