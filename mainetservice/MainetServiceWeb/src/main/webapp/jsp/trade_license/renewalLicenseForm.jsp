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
					id="renewalLicenseForm" name="renewalLicenseForm">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<form:hidden path="" id="immediateService"
						value="${command.immediateServiceMode}" />
					<form:hidden path="licMaxTenureDays" id="licMaxTenureDays" />
					 <form:hidden path="" id="sudaEnv" value="${command.sudaEnv}" />
					 <%-- Defect #154065 --%>
					 <spring:message code="license.ent.itemValue" var="enterItemvalue" />

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
												code="trade.lic.no" text="License No." /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input path="tradeMasterDetailDTO.trdLicno" type="text"
													onchange="getLicenseDetails(this);" class="form-control"
													id="licenseNo" />

												<a href="#" class="input-group-addon"
													onclick="getLicenseDetails(this);"><i
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
											<form:input path="ownerName" id="trdOwnerNm"
												class="form-control mandColorClass hasSpecialChara" value=""
												readonly="true" />
										</div>


										<label id="licenseType"
											class="col-sm-2 control-label required-control"
											for="licenseType"><spring:message
												code="license.details.licenseType" /></label>
										<c:set var="baseLookupCode" value="LIT" />
										<div class="col-sm-4">

											<form:select path="tradeMasterDetailDTO.trdLictype"
												class="form-control mandColorClass" id="trdLictype"
												disabled="true" data-rule-required="true">
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

										<apptags:input labelCode="license.details.businessName"
											cssClass="hasCharacter" isReadonly="true"
											path="tradeMasterDetailDTO.trdBusnm"></apptags:input>

										<label
											class="col-sm-2 control-label required-control hasSpecialCharAndNumber"><spring:message
												code="license.details.businessAddress"
												text="Property Business Address" /></label>
										<div class="col-sm-4">
											<form:input path="tradeMasterDetailDTO.trdBusadd"
												id="trdBusadd"
												class="form-control mandColorClass hasSpecialCharAndNumber"
												value="" readonly="true" />
										</div>
									</div>
									<div class="form-group">

										<c:set var="baseLookupCode" value="MWZ" />
										<apptags:lookupFieldSet
											cssClass="form-control required-control hasSpecialChara"
											baseLookupCode="MWZ" hasId="true"
											pathPrefix="tradeMasterDetailDTO.trdWard" disabled="true"
											hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true" showAll="false" />

									</div>

									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="RenewallicenseFromPeriod"><spring:message
												code="license.details.licenseFromPeriod"
												text="License From Period" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="renewalLicfromDate" readonly="true" disabled="true"
													path="tradeMasterDetailDTO.trdLicfromDate"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

										<label class="col-sm-2 control-label required-control"
											for="RenewallicenseToPeriod"><spring:message
												code="license.details.licenseToPeriod"
												text="License To Period" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control mandColorClass datepicker"
													id="renewalLictoDate" readonly="true" disabled="true"
													path="tradeMasterDetailDTO.trdLictoDate"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>
									</div>
									<div class="form-group">

										<label class="col-sm-2 control-label required-control"
											for="Owner Mob No"><spring:message
												code="owner.details.mobileNo" /></label>
										<div class="col-sm-4">

											<form:input
												path="tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[0].troMobileno"
												id="troMobileno"
												class="form-control unit required-control hasMobileNo preventSpace" value="" maxlength="10"/>

										</div>
										<c:if test="${command.sudaEnv eq 'Y'}">
										<label class="col-sm-2 control-label"><spring:message
												code="trade.license.propertyId" text="Property No." /></label>
										<div class="col-sm-4">
											<form:input path="tradeMasterDetailDTO.pmPropNo"
												placeholder="${propertNo}" type="text" disabled="true"
												onchange="getPropertyDetails(this);" class="form-control"
												id="propertyNo" />
										</div>
										</c:if>

									</div>

									<c:if test="${command.sudaEnv eq 'Y'}">
										<div class="form-group">
											<label class="col-sm-2 control-label"><spring:message
													code="license.details.propertyOwnerName"
													text="Property Owner Name" /></label>
											<div class="col-sm-4">
												<form:input path="tradeMasterDetailDTO.primaryOwnerName"
													id="primaryOwnerName"
													class="form-control mandColorClass hasCharacter"
													readonly="true" data-rule-required="" />
											</div>

											<label class="col-sm-2 control-label"><spring:message
													code="license.details.outstandingtype"
													text="Property Outstanding Tax" /></label>
											<div class="col-sm-4">
												<form:input path="tradeMasterDetailDTO.totalOutsatandingAmt"
													id="totalOutsatandingAmt" disabled="true"
													class="form-control mandColorClass hasNumber text-right"
													placeholder="00.00" readonly="true" data-rule-required="" />
											</div>
										</div>

									</c:if>
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
													cssClass="form-control required-control hasSpecialChara"
													showAll="false" disabled="true" hasTableForm="true"
													showData="false" columnWidth="20%" />

												<th width="20%"><spring:message code="trade.item.value"
														text="Value" /></th>
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
																cssClass="form-control required-control hasSpecialChara"
																disabled="true" showAll="false" hasTableForm="true"
																showData="true" />
															<td><form:input
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																	type="text" disabled="false"
																	class="form-control text-right unit required-control hasNumber"
																	placeholder="${enterItemvalue}" readonly="true"
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
																type="text" disabled="true"
																class="form-control text-right unit required-control hasNumber"
																placeholder="${enterItemvalue}" readonly="true"
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


						<%-- 	<c:if test="${command.paymentCheck ne null && command.paymentCheck eq 'Y'}"> --%>

						<div class="form-group">
							<label class="control-label col-sm-2 required-control"
								for="Census"><spring:message code="trade.renewal.period"
									text="Renewal Period" /></label>
							<c:set var="baseLookupCode" value="LRP" scope="page" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="tradeMasterDetailDTO.renewalMasterDetailDTO.renewalPeriod"
								cssClass="mandColorClass form-control" isMandatory="true"
								hasId="true" selectOptionLabelCode="selectdropdown"
								disabled="false" />


						</div>

						<%-- 	</c:if>		 --%>



						<c:if test="${command.checkListApplFlag eq 'Y'}">
							<!---------------------------------------------------------------document upload start------------------------ -->
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title table" id="">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse1" href="#a5"><spring:message
												code="document.upload.det" text="Document Upload Details" />
											<small class="text-blue-2"><spring:message
													code="trd.chk.doc"
													text="(Upload File upto 5MB and only .pdf ,.doc,.docs,.xls,.xlsx)" /></small></a>
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
																	type="text" class="form-control alphaNumeric"
																	maxLength="50" id="docDescription[${lk.index}]"
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
																<small class="text-blue-2"> <spring:message code="trade.checklist.validation"
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

						<c:if test="${command.paymentCheck eq null}">
							<div class="padding-top-10 text-center">
								<c:if test="${command.filterType ne'V' }">
									<button type="button" class="btn btn-success" id="continueForm"
										title='<spring:message code="trade.proceed" text="Proceed" />'
										onclick="getRenewalCharges(this);">
										<spring:message code="trade.proceed" text="Proceed" />
									</button>
								</c:if>



								<button type="Reset" class="btn btn-warning" id="resetform"
									title='<spring:message code="trade.reset" text="Reset" />'
									onclick="resetRenewalForm(this)">
									<spring:message code="trade.reset" text="Reset" />
								</button>

								<button type="button" class="btn btn-danger" id="back"
									title='<spring:message code="trade.back" text="Back" />'
									onclick="backPage()">
									<spring:message code="trade.back" text="Back" />
								</button>
							</div>
						</c:if>

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
										href="RenewalLicenseForm.html?showChargeDetailsMarket"><spring:message
											code="water.lable.name.chargedetail" /> <i
										class="fa fa-question-circle "></i></a>
								</div>
							</div>

							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
						</c:if>
						<c:if test="${command.paymentCheck ne null}">
							<div class="padding-top-10 text-center">


								<c:if test="${command.filterType  ne 'V'}">
									<button type="button" class="btn btn-success" id="continueForm"
										title='<spring:message code="trade.btn.proceed" text="Proceed"/>'
										onclick="generateChallanAndPaymentForRenewal(this);">
										<spring:message code="trade.btn.proceed" text="Proceed"/>
									</button>
								</c:if>
								<button type="button" class="btn btn-danger" id="back"
									title='<spring:message code="trade.back" text="Back" />'
									onclick="backPage()">
									<spring:message code="trade.back" text="Back" />
								</button>

							</div>
						</c:if>
					</div>

				</form:form>


			</div>
		</div>
	</div>
</div>

<script>
	var renewalLicfromDate = $('#renewalLicfromDate').val();
	var renewalLictoDate = $('#renewalLictoDate').val();
	if (renewalLicfromDate) {
		$('#renewalLicfromDate').val(renewalLicfromDate.split(' ')[0]);
	}
	if (renewalLictoDate) {
		$('#renewalLictoDate').val(renewalLictoDate.split(' ')[0]);
	}
</script>