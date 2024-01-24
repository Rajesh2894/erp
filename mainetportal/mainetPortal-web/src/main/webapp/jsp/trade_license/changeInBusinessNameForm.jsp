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
<script
	src="js/trade_license/changeInBusinessNameForm.js"></script>

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
					class="form-horizontal" id="changeBusinessNameForm"
					name="changeBusinessNameForm">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;"></div>
						<form:hidden path="" id="immediateService" value="${command.immediateServiceMode}" />
						<form:hidden path="" id="valueEditableCheckFlag" value="${command.valueEditableCheckFlag}" />
                        <form:hidden path="" id="envFlag" value="${command.envFlag}" />

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">


						<div class="panel panel-default">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a3"> <spring:message
										code="trade.change.in.business.name" /></a>
							</h4>
							<div id="a3" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">

										<label class="col-sm-2 control-label required-control"><spring:message
												code="license.no" text="License No." /></label>
										<div class="col-sm-4">

											<form:input path="tradeMasterDetailDTO.trdLicno" type="text"
												class="form-control" id="licenseNo" />

										</div>
									</div>
									
									<div class="form-group" id="otpdetails" style="display: none;">
										<label class="col-sm-2 control-label required-control"><spring:message
												code="trade.otp" text="Enter Otp" /></label>
										<div class="col-sm-4">
											<form:input path="userOtp" name="userOtp" id="userOtp"
												type="text" class="form-control" data-rule-required="true"></form:input>
										</div>
										<div class="col-sm-6">
											<button type="button" class="btn btn-success"
												onclick="generateOtpNumber(this)">
												<spring:message code="trade.generate.otp"
													text="Generate Otp" />
											</button>
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
											code="trade.licenseDetails" /></a>
								</h4>
								<div id="a3" class="panel-collapse collapse in">
									<div class="panel-body">

										<div class="form-group">

										
											<label class="col-sm-2 control-label required-control"><spring:message
													code="owner.details.name" text="owner name" /></label>
											<div class="col-sm-4">
												<form:input path="ownerName"
													id="trdOwnerNm" class="form-control mandColorClass"
													value="" readonly="true" />
											</div>
											
											<label class="col-sm-2 control-label required-control"><spring:message
													code="license.details.businessAddress"
													text="Property Business Address" /></label>
											<div class="col-sm-4">
												<form:input path="tradeMasterDetailDTO.trdBusadd"
													id="trdBusadd" class="form-control mandColorClass" value=""
													readonly="true" />
											</div>

										</div>

										<%-- <div class="form-group">

											<apptags:input labelCode="license.details.businessName"
												cssClass="hasCharacter" isReadonly="true"
												path="tradeMasterDetailDTO.trdBusnm"></apptags:input>

											
										</div> --%>

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
														cssClass="form-control required-control " showAll="false"
														disabled="true" hasTableForm="true" showData="false"
														columnWidth="20%" />

                                                     <th width="20%"><spring:message code="trade.item.value"
															text="Value" /></th>
													<%-- <th width="20%"><spring:message code="trade.rate"></spring:message><span
														class="mand">*</span></th> --%>
														<c:if test="${command.hideshowAddBtn ne 'Y'}">
														<th width="5%"><a title="Add" id="addBtn"
															class="btn btn-blue-2 btn-sm addItemCF" onclick=""><i
																class="fa fa-plus"></i></a></th>
													</c:if>
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
															 	<apptags:lookupFieldSet baseLookupCode="ITC"
																	hasId="true" showOnlyLabel="false"
																	pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
																	hasLookupAlphaNumericSort="true" disabled="${command.valueEditableCheckFlag eq 'Y' ? true : false }"
																	hasSubLookupAlphaNumericSort="true" 
																	cssClass="tradeCat form-control required-control"
																	 showAll="false" hasTableForm="true"	showData="true" />
																	
																	<td><form:input
																		path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																		type="text" class="form-control text-right unit required-control hasNumber"
																		placeholder="Enter Item value" 	id="trdUnit${d}" /></td>

																<c:if test="${command.hideshowDeleteBtn ne 'Y'}">
																	<td class="text-center"><a
																		href="javascript:void(0);"
																		class="btn btn-danger btn-sm delButton" id="deleteBtn"
																		onclick=""><i class="fa fa-minus"></i></a></td>
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
																hasLookupAlphaNumericSort="true" disabled="${command.valueEditableCheckFlag eq 'Y' ? true : false }"
																hasSubLookupAlphaNumericSort="true" 
																cssClass="tradeCat form-control required-control "
																showAll="false" hasTableForm="true" showData="true" />
																
															<td><form:input
																	path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																	type="text" class="form-control text-right unit required-control hasNumber"
																	placeholder="Enter Item value" 	id="trdUnit${d}" /></td>

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
											code="trade.change.name.info" /></a>
								</h4>
								<div id="a1" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="form-group">

											<apptags:input labelCode="old.trade.name"
												cssClass="hasCharacter" isReadonly="true"
												path="tradeMasterDetailDTO.trdBusnm"></apptags:input>

											<%-- <apptags:input labelCode="new.trade.name" id="newBusinessName"
												cssClass="hasCharacter" path=""></apptags:input> --%>
												
												<label class="col-sm-2 control-label required-control"><spring:message
													code="new.trade.name" text="New Business Name" /></label>
											<div class="col-sm-4">
												<form:input path="tradeMasterDetailDTO.trdNewBusnm"
													id="newBusinessName" class="form-control mandColorClass"
													value="" />
											</div>

										</div>
									</div>
								</div>
							</div>
							
						</c:if>

						<c:if
							test="${command.paymentCheck ne null && command.paymentCheck eq 'Y'}">

							<!---------------------------------------------------------------document upload start------------------------ -->
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
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped">
												<tbody>
													<tr>
														<th><spring:message  code="Sr.No" text="Sr.No" /></th>
														<th><spring:message  code="document.group" text="Document Group" /></th>
														<th><spring:message code="trd.documetnDesc" text="Document Description" /></th>
														<th><spring:message  code="document.status" text="Document Status" /></th>
														<th><spring:message  code="document.upload" text="Upload document" /></th>
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

									<!---------------------------------------------------------------document upload end------------------------ -->
									<div class="form-group">
										<label class="col-sm-2 control-label"><spring:message
												code="water.field.name.amounttopay" /></label>
										<div class="col-sm-4">
											<form:input class="form-control" path=""
												value="${command.offlineDTO.amountToShow}" maxlength="12"
												readonly="true"></form:input>
											<a class="fancybox fancybox.ajax text-small text-info"
												href="ChangeInBusinessNameForm.html?showChargeDetailsMarket"><spring:message
													code="water.lable.name.chargedetail" /> <i
												class="fa fa-question-circle "></i></a>
										</div>
									</div>
								</div>
							</div>


							<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />


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

						</c:if>

						<c:if test="${command.licenseBtnFlag eq 'Y'}">
						<div class="padding-top-10 text-center" id="licenseBtn">

							<button type="button" class="btn btn-success" id="continueForm"
								onclick="getLicenseDetails(this);">
								<spring:message code="trade.proceed" />
							</button>

							

							<button type="Reset" class="btn btn-warning" id="resetform"
								onclick="resetDuplicateForm(this)">
								<spring:message code="trade.reset" />
							</button>
							
							<button type="button" class="btn btn-danger" id="back"
								onclick="backPage(this)">
								<spring:message code="trade.back"></spring:message>
							</button>

						</div>
						</c:if>

						<c:if test="${command.checklistCheck eq 'Y'}">

							<div class="padding-top-10 text-center">

								<button type="button" class="btn btn-success" id="continueForm"
									onclick="getChecklistAndCharges(this);">
									<spring:message code="trade.proceed" />
								</button>

								<button type="button" class="btn btn-danger" id="back"
									onclick="backPage(this)">
									<spring:message code="trade.back"></spring:message>
								</button>

							</div>
						</c:if>


						<c:if test="${command.otpBtnShowFlag eq 'Y'}">
						<div class="padding-top-10 text-center" id="">

							<button type="button" class="btn btn-success" id="continueForm"
								onclick="getOtpBtn(this);">
								<spring:message code="trade.search" />
							</button>

							

							<button type="Reset" class="btn btn-warning" id="resetform"
								onclick="resetDuplicateForm(this);">
								<spring:message code="trade.reset" />
							</button>
							
							<button type="button" class="btn btn-danger" id="back"
								onclick="backPage(this);">
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
<script>
$(document).ready(function() {
	$('.fancybox').fancybox();
});
</script>