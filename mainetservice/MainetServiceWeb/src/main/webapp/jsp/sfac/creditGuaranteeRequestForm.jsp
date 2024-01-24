<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/sfac/creditGuaranteeRequestForm.js"></script>
<script src="js/mainet/file-upload.js"></script>

<style>


.stateDistBlock>label[for="sdb3"]+div {
	margin-top: 0.5rem;
}


</style>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content animated top">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="sfac.fpo.cgf.title"
					text="Credit Guarantee Application For FPO" />
			</h2>

		</div>

		<div class="widget-content padding">
			<form:form id="EquityGrantRequest" action="CreditGrantEntry.html"
				method="post" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#appDetails">
									<spring:message code="sfac.fpo.cgf.appDetails"
										text="Applicant Details" />
								</a>
							</h4>
						</div>
						<div id="appDetails" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label for="State"
										class="col-sm-6 control-label required-control"><spring:message
											code="sfac.fpo.cgf.applicantName"
											text="Name of the Applicant Eligible Lending Institution  (ELI)" /></label>
									<div class="col-sm-6">
										<form:input path="dto.nameOfApplication"
											id="nameOfApplication" class="form-control "
											disabled="${command.viewMode eq 'V' ? true : false }" />

									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#fpoDetails">
									<spring:message code="sfac.fpo.cgf.fpoDetails"
										text="Borrower/Entity FPO Information" />
								</a>
							</h4>
						</div>
						<div id="fpoDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label for="State"
										class="col-sm-2 control-label required-control"><spring:message
											code="sfac.name.of.the.fpc" text="Name of the FPO" /></label>
									<div class="col-sm-4">
										<form:input path="dto.fpoMasterDto.fpoName" id="fpoName$"
											class="form-control " disabled="true" />
										<form:hidden path="dto.fpoMasterDto.fpoId" id="fpoId" />
									</div>
									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.constitution"
											text="Constitution" />
									</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.constitution"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="status">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label" for=""> <spring:message
											code="sfac.fpo.cgf.regDate"
											text="Act Under Which FPO Is Registered" />
									</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.fpoMasterDto.dateIncorporation"
												type="text" class="form-control datepicker" disabled="true"
												id="regIncorpFpc" placeholder="dd/mm/yyyy" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
									<apptags:input labelCode="sfac.fpo.cgf.por" cssClass=""
										isDisabled="${command.viewMode eq 'V' ? true : false }"
										maxlegnth="100" path="dto.placeOfRegistration"
										isMandatory="true"></apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="sfac.registration.no" cssClass=""
										path="dto.fpoMasterDto.fpoRegNo" isMandatory="true"
										isDisabled="true"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.panno" cssClass=""
										path="dto.fpoMasterDto.fpoPanNo" maxlegnth="10"
										isMandatory="true" isDisabled="true"></apptags:input>

								</div>

								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.tanno" cssClass=""
										path="dto.fpoMasterDto.fpoTanNo" isMandatory="true"
										isDisabled="true"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.gst" cssClass=""
										path="dto.fpoMasterDto.gstin" maxlegnth="10" isDisabled="true"></apptags:input>

								</div>

								<div class="form-group">

									<label for="" class="col-sm-6 control-label required-control">
										<spring:message code="sfac.fpo.cgf.bussfpo"
											text="Business of FPO/Current status of FPO activities" />
									</label>
									<div class="col-sm-6">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.businessOfFPO"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="status">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>

								<div class="form-group">

									<label for="" class="col-sm-6 control-label required-control">
										<spring:message code="sfac.fpo.cgf.agribussfpo"
											text="Is FPO wholly/largely connected with Agri  Business acitivities" />
									</label>
									<div class="col-sm-6">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.fpoAgriBusiness"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="status">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>
								<div class="form-group">
									<label for="State"
										class="col-sm-6 control-label required-control"><spring:message
											code="sfac.fpo.cgf.fwdFPO"
											text="Brief Details Of Forward Linkages Developed by FPO" /></label>
									<div class="col-sm-6">
										<form:textarea path="dto.forwardLinkageDetails" id="fpoName$"
											class="form-control "
											disabled="${command.viewMode eq 'V' ? true : false }" />
									</div>
								</div>

								<div class="form-group">
									<label for="State"
										class="col-sm-6 control-label required-control"><spring:message
											code="sfac.fpo.cgf.bwdFPO"
											text="Brief Details Of Backward Linkages Developed by FPO" /></label>
									<div class="col-sm-6">
										<form:textarea path="dto.backwardLinkageDetails" id="fpoName$"
											class="form-control "
											disabled="${command.viewMode eq 'V' ? true : false }" />
									</div>
								</div>

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#memFPODetails">
									<spring:message code="sfac.fpo.cgf.memFPO"
										text="Details of Members of FPO" />
								</a>
							</h4>
						</div>
						<div id="memFPODetails" class="panel-collapse collapse">
							<div class="panel-body">

								<div class="form-group stateDistBlock">
									<c:set var="baseLookupCode" value="SDB" />
									<apptags:lookupFieldSet disabled="true"
										cssClass="form-control required-control" baseLookupCode="SDB"
										hasId="true" pathPrefix="dto.fpoMasterDto.sdb"
										hasLookupAlphaNumericSort="true" isMandatory="true"
										hasSubLookupAlphaNumericSort="true" showAll="false" />
								</div>

								<div class="form-group">

									<apptags:input labelCode="sfac.fpo.cgf.totalFPOPlain"
										cssClass="hasNumber" path="dto.noOfFPOPlainMember"
										isMandatory="true" maxlegnth="3"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.noOfLandless"
										cssClass="hasNumber" path="dto.noOfLandlessFarmer"
										isMandatory="true" maxlegnth="3"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

								</div>

								<div class="form-group">

									<apptags:input labelCode="sfac.fpo.cgf.fpoNorth"
										cssClass="hasNumber" path="dto.noOfNorthEastFarmer"
										isMandatory="true" maxlegnth="3"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>


									<apptags:input labelCode="sfac.fpo.cgf.noOfSmallFar"
										cssClass="hasNumber" path="dto.noOfSmallFarmer"
										isMandatory="true" maxlegnth="3"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

								</div>

								<div class="form-group">

									<apptags:input labelCode="sfac.fpo.cgf.noOfMarFar"
										cssClass="hasNumber" path="dto.noOfMarFarmer"
										isMandatory="true" maxlegnth="3"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.noOfBigFar"
										cssClass="hasNumber" path="dto.noOfBigFarmer"
										isMandatory="true" maxlegnth="3"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

								</div>
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.noOfshareholder"
										cssClass="hasNumber" path="dto.noofShareholderMem"
										isMandatory="true" maxlegnth="3"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

								</div>



							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#categoryFarmer">
									<spring:message code="sfac.fpo.cgf.cateFar"
										text="Category Of Farmer" />
								</a>
							</h4>
						</div>
						<div id="categoryFarmer" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">

									<apptags:input labelCode="sfac.fpo.cgf.noOfWomenMem"
										cssClass="hasNumber" path="dto.noOfWomenFarmer"
										isMandatory="true" maxlegnth="3"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.noOfSCMem"
										cssClass="hasNumber" path="dto.noOfSCFarmer"
										isMandatory="true" maxlegnth="3"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

								</div>
								<div class="form-group">

									<apptags:input labelCode="sfac.fpo.cgf.noOfSTMem"
										cssClass="hasNumber" path="dto.noOfSTsFarmer"
										isMandatory="true" maxlegnth="3"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

								</div>

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#addressDetails">
									<spring:message code="sfac.fpo.cgf.addFPO"
										text="Address Of FPO" />
								</a>
							</h4>
						</div>
						<div id="addressDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.address"
											text="Existing Registered Office Address" />
									</label>
									<div class="col-sm-4">
										<form:textarea path="dto.fpoMasterDto.fpoOffAddr" id=""
											class="form-control" maxlength="" disabled="true" />
									</div>

									

								</div>
								
								<div class="form-group stateDistBlock">
										<c:set var="baseLookupCode" value="SDB" />
										<apptags:lookupFieldSet disabled="true"
											cssClass="form-control required-control" baseLookupCode="SDB"
											hasId="true" pathPrefix="dto.fpoMasterDto.sdb"
											hasLookupAlphaNumericSort="true" isMandatory="true"
											hasSubLookupAlphaNumericSort="true" showAll="false" />
									</div>

								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.pincode"
										cssClass="hasNumber" path="dto.fpoMasterDto.fpoPinCode"
										isMandatory="true" maxlegnth="6" isDisabled="true"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.latitude" maxlegnth="12"
										path="dto.latitude"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
								</div>
								<div class="form-group">

									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.isSameAdd"
											text="Is the Business Address of FPO same as the registered office address?" />
									</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.isBusinessAddressSame"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="isBusinessAddressSame">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

									<apptags:input labelCode="sfac.fpo.cgf.logitude" maxlegnth="12"
										path="dto.logitude"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
								</div>

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#bussAddressDetails"> <spring:message
										code="sfac.fpo.cgf.bussaddFPO" text="Business Address Of FPO" />
								</a>
							</h4>
						</div>
						<div id="bussAddressDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.address"
											text="Existing Registered Office Address" />
									</label>
									<div class="col-sm-4">
										<form:textarea path="dto.businessFPOAddress" id=""
											class="form-control" maxlength=""  />
									</div>

									

								</div>
								<div class="form-group stateDistBlock">
											<c:set var="baseLookupCode" value="SDB" />
											<apptags:lookupFieldSet 
												cssClass="form-control required-control"
												baseLookupCode="SDB" hasId="true"
												pathPrefix="dto.bsdb"
												hasLookupAlphaNumericSort="true" isMandatory="true"
												hasSubLookupAlphaNumericSort="true" showAll="false" />
										</div>

								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.pincode"
										cssClass="hasNumber" path="dto.businessFpoPinCode"
										isMandatory="true" maxlegnth="6"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.latitude" maxlegnth="12"
										path="dto.businessLatitude"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
								</div>
								<div class="form-group">

									<apptags:input labelCode="sfac.fpo.cgf.logitude" maxlegnth="12"
										path="dto.businessLogitude"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
								</div>

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#otherDetails">
									<spring:message code="sfac.fpo.cgf.otherFPODt"
										text="Other Details related to FPO" />
								</a>
							</h4>
						</div>
						<div id="otherDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label for="" class="col-sm-6 control-label required-control">
										<spring:message code="sfac.fpo.cgf.newFPO"
											text="New/Existing FPO" />
									</label>
									<div class="col-sm-6">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.fpoStatus"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="status">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>



								</div>

								<div class="form-group">
									<label for="" class="col-sm-6 control-label required-control">
										<spring:message code="sfac.fpo.cgf.schemeAvail"
											text="Has the FPO availed credit guarantee under any other scheme of Government of India?" />
									</label>
									<div class="col-sm-6">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.fpoAppliedCGFOS"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="fpoAppliedCGFOS">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>



								</div>

							</div>
						</div>
					</div>


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#existCGFDetails"> <spring:message
										code="sfac.fpo.cgf.existCGF"
										text="Details of Existing Credit Facility" />
								</a>
							</h4>
						</div>
						<div id="existCGFDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.cgpan"
										cssClass="hasNumber" path="dto.etCGPAN" isMandatory="true"
										maxlegnth="10"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.typeCF"
											text="Type of Credit Facility" />
									</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.typeOfCreditFacility"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="typeOfCreditFacility">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>

								<div class="form-group">



									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.sacAmt" text="Sanctioned Amount (Rs.)" /></label>
									<div class="col-sm-4">
										<form:input  cssClass="form-control" id = "totalSactionAmount"
											path="dto.totalSactionAmount" 
											disabled="${command.viewMode eq 'V' ? true : false }"
											onkeypress="return hasAmount(event, this, 10, 2)"></form:input>
									</div>
									<label class="col-sm-2 control-label required-control " for=""> <spring:message
											code="sfac.fpo.cgf.valDate"
											text="Validity of Credit Guarantee" />
									</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.validityOfCreditGuarantee" type="text"
												class="form-control datepicker"
												disabled="${command.viewMode eq 'V' ? true : false }"
												id="validityOfCreditGuarantee" placeholder="dd/mm/yyyy" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.whichCF"
											text="Credit Facility for which Credit Guarantee is being sought in this application is different from the already existing Credit Facility" />
									</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.whichCreditGuarantee"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="whichCreditGuarantee">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>

							</div>
						</div>
					</div>


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#promoDetails">
									<spring:message code="sfac.fpo.cgf.promoter"
										text="Promoters / Management (Detail of Chief Promoter)" />
								</a>
							</h4>
						</div>
						<div id="promoDetails" class="panel-collapse collapse">
							<div class="panel-body">


								<div class="form-group">
									<apptags:input labelCode="sfac.name" path="dto.promoterName"
										isMandatory="true" maxlegnth="50"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.fpo.mobileNo"
										cssClass="hasNumber" maxlegnth="12"
										path="dto.promoterMobileNo" isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
								</div>
								<div class="form-group">

									<apptags:input labelCode="sfac.fpo.cgf.prolandline"
										maxlegnth="12" path="dto.promoterLandline" 
										cssClass="hasNumber"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.email" maxlegnth="50"
										path="dto.promoterEmailId" isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
								</div>

							</div>
						</div>
					</div>

					

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#dtCGFSought">
									<spring:message code="sfac.fpo.cgf.dtCGSought"
										text="Details of Credit Facility for which Guarantee Cover is Sought" />
								</a>
							</h4>
						</div>
						<div id="dtCGFSought" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.cust"
										cssClass="hasNumber" path="dto.cfCustomerId"
										isMandatory="true" maxlegnth="20"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.typeFacility"
											text="Type of Facility" />
									</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.cfTypeOfCreditFacility"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="cfTypeOfCreditFacility">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>

								<div class="form-group">




									<apptags:input labelCode="sfac.fpo.cgf.LandingAssTool"
										cssClass="" path="dto.LandingAssTool" isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.purposeOfCF"
											text="Purpose Of the Credit Facility" />
									</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.purposeOfCF"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="purposeOfCF">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>



							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#wctlDetails">
									<spring:message code="sfac.fpo.cgf.wctlDetails"
										text="Term Loan or WCTL Details" />
								</a>
							</h4>
						</div>
						<div id="wctlDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.accNo"
										cssClass="hasNumber" path="dto.wctlAccountNo"
										isMandatory="true" maxlegnth="20"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.sacAmt" text="Sanctioned Amount (Rs.)" /></label>
									<div class="col-sm-4">
									<form:input id="wctlSactionAmount"
										cssClass="form-control" path="dto.wctlSactionAmount"
										onkeypress="return hasAmount(event, this, 10, 2)"
										disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
										</div>
								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label required-control" for=""> <spring:message
											code="sfac.fpo.cgf.sacDate" text="Date of Sanction" />
									</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.wctlSactionDate" type="text"
												class="form-control datepicker"
												disabled="${command.viewMode eq 'V' ? true : false }"
												id="wctlSactionDate" placeholder="dd/mm/yyyy" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>




									<label class="col-sm-2 control-label" for=""> <spring:message
											code="sfac.fpo.cgf.edDtIM"
											text="End date of Interest moratorium" />
									</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.wctlEndDateInterstMORATORIUM"
												type="text" class="form-control datepicker"
												disabled="${command.viewMode eq 'V' ? true : false }"
												id="wctlEndDateInterstMORATORIUM" placeholder="dd/mm/yyyy" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label" for=""> <spring:message
											code="sfac.fpo.cgf.edDtIM"
											text="End date of Principal moratorium" />
									</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.wctlEndDatePrincipleMORATORIUM"
												type="text" class="form-control datepicker"
												disabled="${command.viewMode eq 'V' ? true : false }"
												id="wctlEndDatePrincipleMORATORIUM" placeholder="dd/mm/yyyy" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>




									<label class="col-sm-2 control-label" for=""> <spring:message
											code="sfac.fpo.cgf.dueDtLI"
											text="Due date of Last Instalment" />
									</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.wctlDueDateOfLastInstallment"
												type="text" class="form-control datepicker"
												disabled="${command.viewMode eq 'V' ? true : false }"
												id="wctlDueDateOfLastInstallment" placeholder="dd/mm/yyyy" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
								</div>


								<div class="form-group">
								<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.intRate" text="Interest Rate (in %)" /></label>
									<div class="col-sm-4">
									<form:input id="wctlInterestRate"
										cssClass="form-control" path="dto.wctlInterestRate"
										onkeypress="return hasAmount(event, this, 3, 3)"
										disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
										</div>

									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.isLoadDisb"
											text="Is the Loan fully disbursed?" />
									</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.isWCTLLoanDis"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="isWCTLLoanDis">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>

								<div class="form-group">

									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.cfUnderAgriInfra"
											text="Is the Credit Facility being extended under Agriculture Infrastructure Fund?" />
									</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.wctlCfExtend"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="wctlCfExtend">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
										<label class="col-sm-2 control-label"><spring:message
											code="sfac.fpo.cgf.outAmt" text=" Outstanding Amount As On Date(Rs.)" /></label>
									<div class="col-sm-4">
									<form:input id="wctlOutstandingAmount"
										cssClass="form-control" path="dto.wctlOutstandingAmount"
										onkeypress="return hasAmount(event, this, 10, 2)"
										disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
										</div>

								</div>
								<div class="form-group">

									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.purposeOfCF"
											text="Purpose of the Credit facility" />
									</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.wctlPurposeOfCF"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="wctlPurposeOfCF">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#ccDetails">
									<spring:message code="sfac.fpo.cgf.ccDetails"
										text="Working Capital/CC Limit Details (WC/CC)" />
								</a>
							</h4>
						</div>
						<div id="ccDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.accNo"
										cssClass="hasNumber" path="dto.ccAccountNo" isMandatory="true"
										maxlegnth="20"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.sacAmt" text="Sanctioned Amount (Rs.)" /></label>
									<div class="col-sm-4">
									<form:input id="ccSactionAmount"
										cssClass="form-control" path="dto.ccSactionAmount"
										onkeypress="return hasAmount(event, this, 10, 2)"
										disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
										</div>
								</div>

								<div class="form-group">

									

									<label class="col-sm-2 control-label required-control" for=""> <spring:message
											code="sfac.fpo.cgf.sacDate" text="Date of Sanction" />
									</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.ccSactionDate" type="text"
												class="form-control datepicker"
												disabled="${command.viewMode eq 'V' ? true : false }"
												id="ccSactionDate" placeholder="dd/mm/yyyy" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
										<label class="col-sm-2 control-label"><spring:message
											code="sfac.fpo.cgf.drawpower" text="Drawing Power (Rs.)" /></label>
									<div class="col-sm-4">
									<form:input id="ccDrawAmount"
										cssClass="form-control" path="dto.ccDrawAmount"
										onkeypress="return hasAmount(event, this, 10, 2)"
										disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>

								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label"> <spring:message
											code="sfac.fpo.cgf.edDtM" text="End Date Of Moratorium" />
									</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.ccEndDateOfMORATORIUM" type="text"
												class="form-control datepicker"
												disabled="${command.viewMode eq 'V' ? true : false }"
												id="ccEndDateOfMORATORIUM" placeholder="dd/mm/yyyy" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>




									<label class="col-sm-2 control-label"> <spring:message
											code="sfac.fpo.cgf.edDtLV" text="End Date of Loan Validity" />
									</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.ccEndDateOfLoanValidity" type="text"
												class="form-control datepicker"
												disabled="${command.viewMode eq 'V' ? true : false }"
												id="ccEndDateOfLoanValidity" placeholder="dd/mm/yyyy" />
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
								</div>


								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.intRate" text="Interest Rate (in %)" /></label>
									<div class="col-sm-4">
									<form:input id="ccInterestRate"
										cssClass="form-control" path="dto.ccInterestRate"
										onkeypress="return hasAmount(event, this, 3, 3)"
										disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.isLoadDisb"
											text="Is the Loan fully disbursed?" />
									</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.isCCLoanDis"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="isCCLoanDis">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>

								<div class="form-group">


									<label class="col-sm-2 control-label"><spring:message
											code="sfac.fpo.cgf.outAmt" text=" Outstanding Amount As On Date(Rs.)" /></label>
									<div class="col-sm-4">
									<form:input id="outstandingAmount"
										cssClass="form-control" path="dto.outstandingAmount"
										onkeypress="return hasAmount(event, this, 10, 2)"
										disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.purposeOfCF"
											text="Purpose of the Credit facility" />
									</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.ccPurposeOfCF"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="ccPurposeOfCF">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>

							</div>
						</div>
					</div>


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"
									href="#projectCostDetails"> <spring:message
										code="sfac.fpo.cgf.projectCost"
										text="Project Cost as accepted by Sanctioning Authority: To be financed by the Current Investment" />
								</a>
							</h4>
						</div>
						<div id="projectCostDetails" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.inputAmt" text="Inputs (in Rs )" /></label>
									<div class="col-sm-4">
										<form:input id="inputAmount" cssClass="form-control"
											path="dto.inputAmount"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.marketingAmt" text="Marketing (in Rs )" /></label>
									<div class="col-sm-4">
										<form:input id="marketingAmount" cssClass="form-control"
											path="dto.marketingAmount"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.processingAmt" text="Processing (in Rs )" /></label>
									<div class="col-sm-4">
										<form:input id="processingAmount" cssClass="form-control"
											path="dto.processingAmount"
											onkeypress="return hasAmount(event, this, 10, 2)"
											isDisabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
									<label class="col-sm-2 control-label "><spring:message
											code="sfac.fpo.cgf.otherAmt" text="Other (in Rs )" /></label>
									<div class="col-sm-4">
										<form:input id="ohterAmount"
											cssClass="form-control" path="dto.ohterAmount"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.totalAmt" text="Total (in Rs )" /></label>
									<div class="col-sm-4">
										<form:input id="totalAmount" cssClass="form-control"
											path="dto.totalAmount"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>

									</div>
								</div>

							</div>
						</div>
					</div>


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#meansOfFinWCTL">
									<spring:message code="sfac.fpo.cgf.meansOfFinWCTL"
										text="Means of Finance for Term Loan or WCTL" />
								</a>
							</h4>
						</div>
						<div id="meansOfFinWCTL" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.termLoan" text="Term Loan (Rs.)" /></label>
									<div class="col-sm-4">
										<form:input id="wctlTermLoan" cssClass="form-control"
											path="dto.wctlTermLoan"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.promoterMargin" text="Promoter's Equity/Margin (Rs.)" /></label>
									<div class="col-sm-4">
										<form:input id="wctlPromoterMargin" cssClass="form-control"
											path="dto.wctlPromoterMargin"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.unsecLoan" text="Unsecured Loan (Rs.)" /></label>
									<div class="col-sm-4">
										<form:input id="wctlUnsecuredLoan" cssClass="form-control"
											path="dto.wctlUnsecuredLoan"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.ohterSource" text="Any Other Source (Rs.)" /></label>
									<div class="col-sm-4">
										<form:input id="wctlAnyOtherSource" cssClass="form-control"
											path="dto.wctlAnyOtherSource"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
								</div>


							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#wclrDetails">
									<spring:message code="sfac.fpo.cgf.wclrDetails"
										text="Working Capital Limit Requirement as accepted by the Sanctioning Authority : To be Financed by Current Investment " />
								</a>
							</h4>
						</div>
						<div id="wclrDetails" class="panel-collapse collapse">
							<div class="panel-body">
							<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.inputAmt" text="Inputs (in Rs )" /></label>
									<div class="col-sm-4">
										<form:input id="wclrInputAmount" cssClass="form-control"
											path="dto.wclrInputAmount"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.marketingAmt" text="Marketing (in Rs )" /></label>
									<div class="col-sm-4">
										<form:input id="wclrMarketingAmount" cssClass="form-control"
											path="dto.wclrMarketingAmount"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.processingAmt" text="Processing (in Rs )" /></label>
									<div class="col-sm-4">
										<form:input id="wclrProcessingAmount" cssClass="form-control"
											path="dto.wclrProcessingAmount"
											onkeypress="return hasAmount(event, this, 10, 2)"
											isDisabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
									<label class="col-sm-2 control-label "><spring:message
											code="sfac.fpo.cgf.otherAmt" text="Other (in Rs )" /></label>
									<div class="col-sm-4">
										<form:input id="wclrOtherAmount"
											cssClass="form-control" path="dto.wclrOtherAmount"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.totalAmt" text="Total (in Rs )" /></label>
									<div class="col-sm-4">
										<form:input id="wclrTotalAmount" cssClass="form-control"
											path="dto.wclrTotalAmount"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>

									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#meansOfFinCC">
									<spring:message code="sfac.fpo.cgf.meansOfFinCC"
										text="Means of Finance for WC/CC Limit" />
								</a>
							</h4>
						</div>
						<div id="meansOfFinCC" class="panel-collapse collapse">
							<div class="panel-body">
							
							<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.ccLimit" text="WC/CC Limit (Rs.)" /></label>
									<div class="col-sm-4">
										<form:input id="ccLimit" cssClass="form-control"
											path="dto.ccLimit"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.promoterMargin" text="Promoter's Equity/Margin (Rs.)" /></label>
									<div class="col-sm-4">
										<form:input id="ccPromoterMargin" cssClass="form-control"
											path="dto.ccPromoterMargin"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.unsecLoan" text="Unsecured Loan (Rs.)" /></label>
									<div class="col-sm-4">
										<form:input id="ccUnsecuredLoan" cssClass="form-control"
											path="dto.ccUnsecuredLoan"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.ohterSource" text="Any Other Source (Rs.)" /></label>
									<div class="col-sm-4">
										<form:input id="ccAnyOtherSource" cssClass="form-control"
											path="dto.ccAnyOtherSource"
											onkeypress="return hasAmount(event, this, 10, 2)"
											disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
									</div>
								</div>
							
							
								

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#securityDts">
									<spring:message code="sfac.fpo.cgf.securityDts"
										text="Security Details" />
								</a>
							</h4>
						</div>
						<div id="securityDts" class="panel-collapse collapse">
							<div class="panel-body">
								<h4>
									<spring:message code="sfac.fpo.cgf.secpara"
										text="Please indicate details and value of security at the time of appraisal. For personal guarantee, net worth of the Guarantor also to be
indicated under value of security. For Credit Guarantee , loans upto Rs. 2 crore are to be extended without personal guarantee" />
								</h4>
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.typeOfSec"
										path="dto.typeOfSecurity" isMandatory="true" maxlegnth="100"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.natOfSec"
											text="Nature of Security" />
									</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.natureOfSecurity"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="natureOfSecurity">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>

								<div class="form-group">

									<label for="" class="col-sm-2 control-label required-control">
										<spring:message code="sfac.fpo.cgf.cfSactionWithoutSecurity"
											text="The Credit Facility has been sanctioned without any collateral security and/or third party guarantee" />
									</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="YNC" />
										<form:select path="dto.cfSactionWithoutSecurity"
											class="form-control chosen-select-no-results"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="cfSactionWithoutSecurity">
											<form:option value="">
												<spring:message code="sfac.select" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpDesc}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

									<label class="col-sm-2 control-label required-control"><spring:message
											code="sfac.fpo.cgf.valOfSec" text="Value Of Security" /></label>
									<div class="col-sm-4">
									<form:input labelCode="sfac.fpo.cgf.valueOfSecurity"
										cssClass="form-control" path="dto.valueOfSecurity"
										onkeypress="return hasAmount(event, this, 10, 2)"
										disabled="${command.viewMode eq 'V' ? true : false }"></form:input>
										</div>

								</div>



							</div>
						</div>
					</div>


					<c:if test="${command.checklistFlag eq 'Y' }">

						<!---------------------------------------------------------------document upload start------------------------ -->
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse1" href="#a5"><spring:message
											code="sfac.fpo.mc.docDetails" text="Document Upload Details" /></a>
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
													<%-- <th><spring:message code="trd.documetnDesc"
															text="Document Description" /></th> --%>
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
														<%-- <td><form:input
																path="checkList[${lk.index}].docDescription" type="text"
																class="form-control alphaNumeric" maxLength="50"
																id="docDescription[${lk.index}]"
																data-rule-required="true" /></td> --%>
														<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
															<td><spring:message code="water.doc.mand" /></td>
														</c:if>
														<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
															<td><spring:message code="water.doc.opt" /></td>
														</c:if>
														<td>
															<div id="docs_${lk}" class="">
																<c:if test="${command.viewMode ne 'V'}">
																	<apptags:formField fieldType="7" labelCode=""
																		hasId="true" fieldPath="checkList[${lk.index}]"
																		isMandatory="false" showFileNameHTMLId="true"
																		fileSize="BND_COMMOM_MAX_SIZE"
																		checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																		maxFileCount="CHECK_LIST_MAX_COUNT"
																		validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																		currentCount="${lk.index}" checkListDesc="${docName}" />
																</c:if>
															</div> <c:if
																test="${command.documentList[lk.index] ne null  && not empty command.documentList[lk.index]}">
																<input type="hidden" name="deleteFileId"
																	value="${command.documentList[lk.index].attId}">
																<input type="hidden" name="downloadLink"
																	value="${command.documentList[lk.index]}">
																<apptags:filedownload
																	filename="${command.documentList[lk.index].attFname}"
																	filePath="${command.documentList[lk.index].attPath}"
																	actionUrl="EquityGrantRequest.html?Download"></apptags:filedownload>
															</c:if> <br /> <small class="text-blue-2"> <spring:message
																	code="trade.checklist.validation"
																	text="(Upload Image File upto 2 MB)" />
														</small>


														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>

								<!---------------------------------------------------------------document upload end------------------------ -->

							</div>
						</div>

					</c:if>

					<div class="">
						<h4>
							<spring:message code="sfac.fpo.cgf.certify"
								text="We (the ELI) certify that:" />
						</h4>
						<ul>
							<li><spring:message code="sfac.fpo.cgf.tnc1"
									text="1. The information provided in the Credit Guarantee Application is in line with the ‘Credit Guarantee Scheme for Farmer Producer Organizations Financing." />
							</li>
							<li><spring:message code="sfac.fpo.cgf.tnc2"
									text="2. The credit facility which has been sanctioned without any collateral security and/ or third-party guarantee." />
							</li>
							<li><spring:message code="sfac.fpo.cgf.tnc3"
									text="3. The KYC norms in respect of the Promoters have been complied by us." />
							</li>
							<li><spring:message code="sfac.fpo.cgf.tnc4"
									text="4. Techno-feasibility and economic viability aspect of the project has been taken care of by the sanctioning authority and the branch." />
							</li>
							<li><spring:message code="sfac.fpo.cgf.tnc5"
									text="5. Risks in respect of the credit facility are not additionally covered under any scheme operated/administered by Reserve Bank of India/or by the Government/or by any general insurer or any other person or association of persons carrying on the business of insurance, guarantee or indemnity." />
							</li>
							<li><spring:message code="sfac.fpo.cgf.tnc6"
									text="6. The credit facility conforms to and is consistent with, the provisions of each law, and with all directives and instructions issued by the Central Government or the Reserve Bank of India, which is, for the time being, in force." />
							</li>
							<li><spring:message code="sfac.fpo.cgf.tnc7"
									text="7. The credit facility has not been classified as Fraud on the basis of the provisions of law." />
							</li>
							<li><spring:message code="sfac.fpo.cgf.tnc8"
									text="8. The credit facility has not been granted to a borrower, who has earlier defaulted under any credit guarantee/insurance/indemnity scheme." />
							</li>
							<li><spring:message code="sfac.fpo.cgf.tnc9"
									text="9. The credit facility is not an overdue /NPA facility taken over by ELI from any other lender or any other default converted into a credit facility." />
							</li>
							<li><spring:message code="sfac.fpo.cgf.tnc10"
									text="10. The credit facility is not overdue for payment." />
							</li>
							<li><spring:message code="sfac.fpo.cgf.tnc11"
									text="11. The credit facility has not been rescheduled or restructured on becoming overdue for repayment." />
							</li>
							<li><spring:message code="sfac.fpo.cgf.tnc12"
									text="12. The business or activity of the borrower for which the credit facility was granted has not ceased." />
							</li>

							<li><spring:message code="sfac.fpo.cgf.tnc13"
									text="13. The credit facility has not been utilized, wholly or partly, for adjustment of any debt deemed as bad or doubtful of recovery" />
							</li>



							<li><form:checkbox id="underTaking" path="dto.underTaking" 
									value="N" checked="" /> <label class=" control-label"><spring:message
										code="sfac.fpo.cgf.undertaking"
										text="We undertake to abide by the Terms and Conditions of the ‘Credit Guarantee Scheme for FPO Financing" /></label>

							</li>

						</ul>
					</div>



					<c:if test="${command.checklistFlag eq 'N'}">
						<div class="padding-top-10 text-center">

							<button type="button" class="btn btn-success" id="continueForm"
								onclick="getCheckList(this);">
								<spring:message code="trade.proceed" />
							</button>

							<button type="button" class="btn btn-danger" id="back"
								onclick="backPage()">
								<spring:message code="trade.back"></spring:message>
							</button>

						</div>
					</c:if>

					<c:if test="${command.checklistFlag eq 'Y' }">

						<div class="text-center padding-top-10">
							<c:if test="${command.viewMode ne 'V'}">
								<button type="button" class="btn btn-success"
									title='<spring:message code="sfac.submit" text="Submit" />'
									onclick="saveCreditGuaranteeForm(this);">
									<spring:message code="sfac.submit" text="Submit" />
								</button>
							</c:if>
							<c:if test="${command.viewMode eq 'A'}">
								<button type="button" class="btn btn-warning"
									title='<spring:message code="sfac.button.reset" text="Reset"/>'
									onclick="ResetForm();">
									<spring:message code="sfac.button.reset" text="Reset" />
								</button>
							</c:if>
							<apptags:backButton url="EquityGrantRequest.html"></apptags:backButton>
						</div>
					</c:if>
				</div>
			</form:form>
		</div>
	</div>
</div>



