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
	src="js/trade_license/hawkerLicenseApplicationForm.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/trade_license/hawkerOwnerTable.js"></script>
<script type="text/javascript"
	src="js/trade_license/hawkerOwnerFamilyTable.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<style>
.addColor {
	background-color: #fff !important
}
</style>


<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<b><spring:message code="" text="Application Form"></spring:message></b>
			</h2>

			<apptags:helpDoc url="HawkerLicenseApplicationForm.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">

			<form:form method="POST" action="HawkerLicenseApplicationForm.html"
				class="form-horizontal" id="hawkerLicenseApplicationForm"
				name="hawkerLicenseApplicationForm">
				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
					<form:hidden path="" id="removedIds" />
					<form:hidden path="length" id="length" />
					<form:hidden path="" id="viewMode" value="${command.viewMode}" />
					<form:hidden path="" id="hideAddBtn"
						value="${command.hideshowAddBtn}" />
					<form:hidden path="" id="hideDeleteBtn"
						value="${command.hideshowDeleteBtn}" />
					<form:hidden path="" id="hideTemporaryDate"
						value="${command.temporaryDateHide}" />
					<form:hidden path="licMaxTenureDays" id="licMaxTenureDays" />

				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">


					<div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a3"> <spring:message
									code="" text="Vendor Details" /></a>
						</h4>
						<div id="a3" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">

									<%-- 	<label id="ownershipType"
										class="col-sm-2 control-label required-control"
										for="ownershipType"><spring:message
											code="license.details.ownershipType" /></label> --%>

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

								</div>

								<div id="owner"></div>




							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a15"> <spring:message
									code="" text="Residential/Electoral Ward Zone Details" /></a>
						</h4>
						<div id="a15" class="panel-collapse collapse in">
							<div class="panel-body">


								<div class="form-group">

									<c:set var="baseLookupCode" value="EWZ" />
									<apptags:lookupFieldSet
										cssClass="form-control required-control hasSpecialChara"
										baseLookupCode="EWZ" hasId="true"
										pathPrefix="tradeMasterDetailDTO.trdEWard"
										disabled="${command.viewMode eq 'V' ? true : false }"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" showAll="false"
										isMandatory="true" />

								</div>
							</div>
						</div>
					</div>



					<div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a10"> <spring:message
									code="" text="Vendor Family Details" /></a>
						</h4>
						<div id="a10" class="panel-collapse collapse in">
							<div class="panel-body">

								<jsp:include
									page="/jsp/trade_license/hawkerOwnerFamilyTable.jsp" />

							</div>
						</div>
					</div>



					<div class="panel panel-default">

						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a1"> <spring:message
									code="hawkerLicense.details.businessDetail"
									text="Business Detail" /></a>
						</h4>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">


								<div class="form-group">

									<%-- <label class="col-sm-2 control-label"> <spring:message
											code="" text="Location"></spring:message>
									</label>
									<div class="col-sm-4">
										<form:select path="tradeMasterDetailDTO.trdLocId"
											id="locationId" class="chosen-select-no-results"
											data-rule-required="true"
											onchange="getLocationMapping(this);">
											<form:option value="">
												<spring:message code="adh.select" text="Select"></spring:message>
											</form:option>
											<c:forEach items="${command.locationList}" var="location">
												<form:option value="${location.locId}">${location.locNameEng}</form:option>
											</c:forEach>
										</form:select>
									</div> --%>

									<label class="col-sm-2 control-label required-control">
										<spring:message code="hawkerLicense.businessType"
											text="Nature of Business "></spring:message>
									</label>

									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="LIT" />

										<form:select path="tradeMasterDetailDTO.trdLictype"
											onchange="getLicenseValidityDateRange()"
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
									</div>
								</div>


								<div id="licensePeriod" style="display: none;">
									<div class="form-group">


										<label class="col-sm-2 control-label required-control"
											for="licenseFromPeriod"><spring:message
												code="hawkerLicense.validity.startDate"
												text="Validity Start Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input
													class="date form-control mandColorClass datepicker2 addColor"
													placeholder="DD/MM/YYYY" autocomplete="off"
													id="trdLicfromDate" readonly="true"
													path="tradeMasterDetailDTO.trdLicfromDate"></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>

										<label class="col-sm-2 control-label required-control"
											for="licenseToPeriod"><spring:message
												code="hawkerLicense.validity.endDate"
												text="Validity End Date" /></label>
										<div class="col-sm-4">
											<div class="input-group">
												<form:input
													class="date form-control mandColorClass datepicker2 addColor"
													placeholder="DD/MM/YYYY" autocomplete="off"
													id="trdLictoDate" readonly="true"
													path="tradeMasterDetailDTO.trdLictoDate"></form:input>
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
								data-parent="#accordion_single_collapse" href="#a4"> <spring:message
									code="hawkerLicense.business.type" text="Business Type" /></a>
						</h4>
						<div id="a4" class="panel-collapse collapse in">
							<div class="panel-body">


								<div class="form-group">

									<apptags:input labelCode="hawkerLicense.details.businessName"
										cssClass="hasSpecialChara preventSpace" isMandatory="true"
										placeholder="Enter Buisness Name"
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										maxlegnth="50" path="tradeMasterDetailDTO.trdBusnm"></apptags:input>

									<label class="col-sm-2 control-label required-control"
										for="address"><spring:message
											code="hawkerLicense.details.VendingPlace"
											text="Vending Place" /></label>
									<div class="col-sm-4">
										<form:input
											class="form-control mandColorClass hasSpecialCharAndNumber preventSpace"
											id="trdBusadd" maxlength="200"
											placeholder="Enter Business Address"
											disabled="${command.viewMode eq 'V' ? true : false }"
											readonly="" path="tradeMasterDetailDTO.trdBusadd"></form:input>
									</div>

								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label required-control"
										for="BusinessStartDate"><spring:message
											code="hawkerLicense.details.businessStartDate"
											text="Business Start Date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input
												class="date form-control mandColorClass datepicker2 addColor"
												placeholder="DD/MM/YYYY" autocomplete="off"
												id="businessStartDate"
												disabled="${command.viewMode eq 'V' ? true : false }"
												path="tradeMasterDetailDTO.trdLicisdate"></form:input>
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>

									<label class="col-sm-2 control-label" for="trdComodity"><spring:message
											code="hawkerLicense.details.comodity"
											text="Commodity /Sale Product" /></label>
									<div class="col-sm-4">
										<form:input
											class="form-control mandColorClass  hasSpecialCharAndNumber preventSpace"
											id="trdComodity" maxlength="99" placeholder=""
											disabled="${command.viewMode eq 'V' ? true : false }"
											readonly="" path="tradeMasterDetailDTO.trdComodity"></form:input>
									</div>



								</div>


								<div class="form-group">



									<label class="col-sm-2 control-label" for="trdExperience"><spring:message
											code="hawkerLicense.details.experience" text="Experience" /></label>
									<div class="col-sm-4">
										<form:input class="form-control hasSpecialCharAndNumber"
											id="trdExperience" maxlength="99" placeholder="Experience"
											disabled="${command.viewMode eq 'V' ? true : false }"
											path="tradeMasterDetailDTO.trdExperience"></form:input>
									</div>




									<label class="col-sm-2 control-label" for="trdMonthlyIncome"><spring:message
											code="hawkerLicense.details.monthyincome"
											text="Monthly Income" /></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass hasNumber"
											id="trdMonthlyIncome" maxlength="14"
											placeholder="Monthly Income"
											disabled="${command.viewMode eq 'V' ? true : false }"
											readonly="" path="tradeMasterDetailDTO.trdMonthlyIncome"></form:input>
									</div>

								</div>
								<div class="form-group">

									<label class="col-sm-2 control-label" for="address"><spring:message
											code="hawkerLicense.details.investmentAmount"
											text="Investment Amount" /></label>
									<div class="col-sm-4">
										<form:input
											class="form-control mandColorClass hasNumber preventSpace"
											id="trdInvestmentAmount" maxlength="14"
											placeholder="Enter Investment Amount"
											disabled="${command.viewMode eq 'V' ? true : false }"
											path="tradeMasterDetailDTO.trdInvestmentAmount"></form:input>
									</div>
									<label class="col-sm-2 control-label" for="trdRationNo"><spring:message
											code="hawkerLicense.details.rationCardNo"
											text="Ration Card No." /></label>
									<div class="col-sm-4">
										<form:input class="form-control mandColorClass preventSpace"
											id="trdRationNo" maxlength="99" placeholder=""
											disabled="${command.viewMode eq 'V' ? true : false }"
											path="tradeMasterDetailDTO.trdRationNo"></form:input>
									</div>


								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label" for="trdRationAuth"><spring:message
											code="hawkerLicense.details.rationOffice"
											text="Ration Authority Office" /></label>
									<div class="col-sm-4">
										<form:input
											class="form-control mandColorClass preventSpace hasSpecialCharAndNumber"
											id="trdRationAuth" maxlength="49" placeholder=""
											disabled="${command.viewMode eq 'V' ? true : false }"
											readonly="" path="tradeMasterDetailDTO.trdRationAuth"></form:input>
									</div>
								</div>

								<div class="form-group">
									<c:set var="baseLookupCode" value="MWZ" />
									<apptags:lookupFieldSet
										cssClass="form-control required-control" baseLookupCode="MWZ"
										hasId="true" pathPrefix="tradeMasterDetailDTO.trdWard"
										disabled="${command.viewMode eq 'V' ? true : false }"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" showAll="false"
										isMandatory="true" />

								</div>


								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="hawkerLicense.details.isMGNREGA"
											text="Is register in MGNREGA" /></label>
									<div class="col-sm-4">
										<label class="radio-inline margin-top-5"> <!-- 	<input type="checkbox" value="" id="checkId"
									               onChange="" data-rule-required="true"> --> <form:checkbox
												disabled="${command.viewMode eq 'V' ? true : false }"
												path="tradeMasterDetailDTO.trdReg" value="t" id="r1"
												onclick="" />
									</div>


									<label class="col-sm-2 control-label" for="trdRagistrationNo"><spring:message
											code="hawkerLicense.details.MGNREGA.RagistrationNo"
											text=" MGNREGA Registration No" /></label>
									<div class="col-sm-4">
										<form:input
											class="form-control mandColorClass preventSpace hasSpecialCharAndNumber"
											id="trdRagistrationNo" maxlength="99" placeholder=""
											disabled="${command.viewMode eq 'V' ? true : false }"
											readonly="" path="tradeMasterDetailDTO.trdRegNo"></form:input>
									</div>


								</div>


								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="hawkerLicense.details.isBPL" text="Is BPL" /></label>
									<div class="col-sm-4">
										<label class="radio-inline margin-top-5"> <!-- 	<input type="checkbox" value="" id="checkId"
									               onChange="" data-rule-required="true"> --> <form:checkbox
												disabled="${command.viewMode eq 'V' ? true : false }"
												path="tradeMasterDetailDTO.isBPL" value="t" id="isBPL"
												onclick="" />
									</div>

									<label id="catagoryType"
										class="col-sm-2 control-label hasSpecialCharAndNumber"
										for="catagoryType"><spring:message
											code="hawkerLicense.details.catagoryType"
											text="Applicant Catagory" /></label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="OCT" />

										<form:select path="tradeMasterDetailDTO.trdOthCatType"
											onchange="" class="form-control mandColorClass" id=""
											disabled="${command.viewMode eq 'V' ? true : false }"
											data-rule-required="true">
											<form:option value="">
												<spring:message code="" text="select" />
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
									<label class="control-label col-sm-2"> <spring:message
											code="hawkerLicense.details.BankName" text="Bank Name">
										</spring:message>
									</label>

									<div class="col-sm-4">
										<form:select id="bankId" path="tradeMasterDetailDTO.trdBankId"
											onchange="setValues(this)"
											class="form-control chosen-select-no-results">
											<form:option value="">
												<spring:message code="master.selectDropDwn" />
											</form:option>
											<c:forEach items="${command.custBankList}" var="bankId">
												<c:set var="bankList" value="${bankId.value}" scope="page"></c:set>

												<form:option value="${bankId.key}"
													bankIfscCode="${bankList[1]}">${bankList[0]}</form:option>

											</c:forEach>
										</form:select>
									</div>

									<label class="control-label col-sm-2"> <spring:message
											code="hawkerLicense.details.Bank.acctNo"
											text="Bank Account No"></spring:message>
									</label>
									<div class="col-sm-4">
										<form:input id="trdbankaccountnumber"
											path="tradeMasterDetailDTO.trdAcctNo"
											class="form-control hasNumber" maxLength="18" />
									</div>


								</div>


								<div class="form-group">


									<label class="control-label col-sm-2"> <spring:message
											code="hawkerLicense.details.Bank.IFSC" text="Bank IFSC Code"></spring:message>
									</label>
									<div class="col-sm-4">
										<form:input id="trdBankIfscCode"
											path="tradeMasterDetailDTO.trdBankIfscCode" readonly="true"
											class="form-control" maxLength="20" />
									</div>


								</div>


								<div class="form-group"></div>

								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered  table-condensed margin-bottom-10"
									id="itemDetails">
									<thead>

										<tr>
											<apptags:lookupFieldSet baseLookupCode="HLC" hasId="true"
												showOnlyLabel="false"
												pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
												isMandatory="true" hasLookupAlphaNumericSort="true"
												hasSubLookupAlphaNumericSort="true"
												cssClass="tradeCat form-control required-control"
												showAll="false"
												disabled="${command.viewMode eq 'V' ? true : false }"
												hasTableForm="true" showData="false" columnWidth="20%" />

											<th width="8%"><spring:message code=""
													text="Business Area(Sq.Mtr)" /></th>
											<%-- <th width="8%"><spring:message code="trade.item.unit" /></th> --%>
											<%-- <th width="8%"><spring:message code="trade.action" /></th> --%>


											<c:if test="${command.hideshowAddBtn ne 'Y'}">
												<th width="5%"><a title="Add" id="addBtn"
													class="btn btn-blue-2 btn-sm addItemCF" onclick=""><i
														class="fa fa-plus"></i></a></th>
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
														<apptags:lookupFieldSet baseLookupCode="HLC" hasId="true"
															showOnlyLabel="false"
															pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
															isMandatory="true" hasLookupAlphaNumericSort="true"
															hasSubLookupAlphaNumericSort="true"
															disabled="${command.viewMode eq 'V' ? true : false }"
															cssClass="form-control required-control " showAll="false"
															hasTableForm="true" showData="true" />

														<td><form:input
																path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
																type="text"
																disabled="${command.viewMode eq 'V' ? true : false }"
																class="form-control text-right unit required-control hasNumber"
																placeholder="Enter Item value" readonly=""
																id="trdUnit${d}" /></td>




														<c:if test="${command.hideshowDeleteBtn ne 'Y'}">
															<td class="text-center"><a
																href="javascript:void(0);"
																class="btn btn-danger btn-sm delButton" id="deleteBtn"
																onclick=""><i class="fa fa-minus"></i></a></td>
														</c:if>




													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />


												</c:forEach>
											</c:when>


											<c:otherwise>
												<tr class="itemDetailClass">

													<form:hidden
														path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
														id="triId${d+1}" />
													<apptags:lookupFieldSet baseLookupCode="HLC" hasId="true"
														showOnlyLabel="false"
														pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
														isMandatory="true" hasLookupAlphaNumericSort="true"
														hasSubLookupAlphaNumericSort="true"
														disabled="${command.viewMode eq 'V' ? true : false }"
														cssClass="tradeCat form-control required-control "
														showAll="false" hasTableForm="true" showData="true" />

													<td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].trdUnit"
															type="text"
															disabled="${command.viewMode eq 'V' ? true : false }"
															class="form-control text-right unit required-control hasNumber"
															placeholder="Enter Item Value" readonly=""
															id="trdUnit${d}" /></td>



													<td class="text-center"><a href="javascript:void(0);"
														class="btn btn-danger btn-sm delButton" onclick=""><i
															class="fa fa-minus"></i></a></td>


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
							<%-- <apptags:checkbox labelCode="trade.termsCondition" value="" path="" disabled="${command.viewMode eq 'V' ? true : false }"></apptags:checkbox> --%>
							<div class="checkbox">
								<label><input type="checkbox" value="" id="checkId"
									onChange="" data-rule-required="true"> <a
									href="javascript:void(0);"
									onclick="showTermsConditionForm(this);"><b
										class="text-large form-control unit required-control"><spring:message
												code="trade.termsCondition"></spring:message></b></a> </label>
							</div>
						</div>
					</div>

					<%-- <c:if
								test="${command.paymentCheck ne null && command.paymentCheck eq 'Y'}"> --%>

					<c:if test="${command.checkListApplFlag eq 'Y'}">
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
													<th><spring:message text="Sr.No" /></th>
													<th><spring:message text="Document Group" /></th>
													<th><spring:message text="Document Status" /></th>
													<th><spring:message text="Upload document" /></th>
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

					<c:if
						test="${command.getTradeMasterDetailDTO().applicationchargeApplFlag eq 'Y' && command.paymentCheck eq 'Y'}">
						<div class="form-group">
							<label class="col-sm-2 control-label"><spring:message
									code="water.field.name.amounttopay" /></label>
							<div class="col-sm-4">
								<form:input class="form-control" path=""
									value="${command.offlineDTO.amountToShow}" maxlength="12"
									readonly="true"></form:input>
								<a class="fancybox fancybox.ajax text-small text-info"
									href="TradeApplicationForm.html?showChargeDetailsMarket"><spring:message
										code="water.lable.name.chargedetail" /> <i
									class="fa fa-question-circle "></i></a>
							</div>


						</div>
					</c:if>


					<c:if
						test="${command.getTradeMasterDetailDTO().applicationchargeApplFlag eq 'Y' && command.paymentCheck eq 'Y'}">
						<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
					</c:if>


					<c:if test="${command.paymentCheck eq null}">
						<div class="padding-top-10 text-center">

							<button type="button" class="btn btn-success" id="continueForm"
								onclick="savehawkerLicenseApplicationForm(this);">
								<spring:message code="trade.proceed" />
							</button>

							<%-- <button type="button" class="btn btn-success" id="continueForm"
								onclick="getChecklistAndCharge(this);">
								<spring:message code="trade.proceed" />
							</button> --%>



							<button type="Reset" class="btn btn-warning" id="resetform"
								onclick="resetTradeForm(this)">
								<spring:message code="trade.reset" />
							</button>
							<button type="button" class="btn btn-danger" id="back"
								onclick="backPage()">
								<spring:message code="trade.back"></spring:message>
							</button>
						</div>
					</c:if>



					<c:if test="${command.paymentCheck ne null}">
						<div class="padding-top-10 text-center">



							<button type="button" class="btn btn-success" id="continueForm"
								onclick="generateChallanAndPayment(this);">
								<spring:message code="trade.btn.proceed" />
							</button>

							<button type="button" class="btn btn-danger" id="backEdit"
								onclick="editApplication(this);">
								<spring:message code="trade.editappln"></spring:message>
							</button>
							<button type="button" class="btn btn-danger" id="back"
								onclick="backPage()">
								<spring:message code="trade.back"></spring:message>
							</button>
						</div>

					</c:if>
				</div>
		</div>


		</form:form>
	</div>
</div>
</div>

