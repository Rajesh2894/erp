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

<style>
catagory td, #catagory th {
	border: 1px solid black;
	padding: 8px;
}
</style>
<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="license.transper.form"
							text="License transfer form"></spring:message></b>
				</h2>

				<apptags:helpDoc url="TransperLicenseForm.html"></apptags:helpDoc>
			</div>

			<div class="widget-content padding">

				<form:form action="TransperLicense.html" class="form-horizontal"
					id="TransperLicenseForm" name="TransperLicenseForm">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<%-- 	<form:hidden path="" id="immediateService" value="${command.immediateServiceMode}" /> --%>

					<form:hidden path="" id="removedIds" />
					<form:hidden path="length" id="length" />
					<%-- <form:hidden path="" id="viewMode" value="${command.viewMode}" />
					<form:hidden path="" id="hideAddBtn"
						value="${command.hideshowAddBtn}" />
					<form:hidden path="" id="hideDeleteBtn"
						value="${command.hideshowDeleteBtn}" />
					<form:hidden path="" id="hideTemporaryDate"
						value="${command.temporaryDateHide}" /> --%>
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">


						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="trade.transper.form" text="Transfer of  license" /></a>
							</h4>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="trade.lic.no" text="License No." /></label>
										<div class="col-sm-4">



											<form:input path="tradeDetailDTO.trdLicno" id="trdLicNo"
												class="form-control mandColorClass hasSpecialChara" value=""
												readonly="true" />




											<%-- <form:select path="tradeMasterDetailDTO[0].trdLicno"
												id="trdLicno"
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
											</form:select> --%>
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
											code="trade.tradeLicDetails" text="Trade License Details" /></a>
								</h4>
								<div id="a3" class="panel-collapse collapse in">
									<div class="panel-body">


										<div class="form-group">


											<%-- 	<label class="col-sm-2 control-label required-control"><spring:message
												code="license.no" text="License Number" /></label>
										<div class="col-sm-4">
											<form:input path="" id="trdLicno"
												class="form-control mandColorClass"
												value=""
												readonly="true" />
										</div> --%>

											<label class="col-sm-2 control-label required-control"><spring:message
													code="license.old.owner.name" text="Old owner name" /></label>
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


													<th width="20%"><spring:message
															code="trade.item.value" text="Item Value" /></th>
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
						
						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a6"> <spring:message
										code="license.new.business.owner"
										text="New Business Owner Details" /></a>
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


						<!---------------------------------------------------------------document upload start------------------------ -->

						<c:if
							test="${command.checkListApplFlag eq 'Y' && command.checkListApplFlag ne null}">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title table" id="">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse1" href="#a5"><spring:message
												text="Document Upload Details" code="trade.document.name" /></a>
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
																	type="text" class="form-control alphaNumeric" maxLength="50"
																	id="docDescription[${lk.index}]"
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

								<button type="button" class="btn btn-success" id="continueForm"
									onclick="saveTradeLicenseForm(this)">
									<spring:message code="trade.save" text="Save" />
								</button>

								<button type="button" class="btn btn-danger" id="back"
									onclick="backPage()">
									<spring:message code="trade.back"></spring:message>
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
									<a class="fancybox text-small text-info"
										data-fancybox-type="ajax"
										href="TransperLicense.html?showChargeDetailsMarket"><spring:message
											code="water.lable.name.chargedetail" /> <i
										class="fa fa-question-circle "></i></a>
								</div>
							</div>

							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />


							<div class="padding-top-10 text-center">
								<button type="button" class="btn btn-success" id="continueForm"
									onclick="printChallanAndPayment(this)">
									<spring:message code="trade.save" text="Save" />
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

