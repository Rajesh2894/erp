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
			<form:form id="EquityGrantRequest" action="CreditGuaranteeApproval.html"
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
										path="dto.fpoMasterDto.fpoPanNo" maxlegnth="10" isMandatory="true"
										isDisabled="true"></apptags:input>

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

									<div class=" stateDistBlock">
										<c:set var="baseLookupCode" value="SDB" />
										<apptags:lookupFieldSet disabled="true"
											cssClass="form-control required-control" baseLookupCode="SDB"
											hasId="true" pathPrefix="dto.fpoMasterDto.sdb"
											hasLookupAlphaNumericSort="true" isMandatory="true"
											hasSubLookupAlphaNumericSort="true" showAll="false" />
									</div>

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
											class="form-control" maxlength="" disabled="true" />
									</div>

									<%-- <div class=" stateDistBlock">
											<c:set var="baseLookupCode" value="SDB" />
											<apptags:lookupFieldSet disabled="true"
												cssClass="form-control required-control"
												baseLookupCode="SDB" hasId="true"
												pathPrefix="dto.businessState"
												hasLookupAlphaNumericSort="true" isMandatory="true"
												hasSubLookupAlphaNumericSort="true" showAll="false" />
										</div> --%>

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




									<apptags:input labelCode="sfac.fpo.cgf.sacAmt" cssClass=""
										path="dto.totalSactionAmount" isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<label class="col-sm-2 control-label required-control " for=""> <spring:message
											code="sfac.fpo.cgf.valDate"
											text="Validity of Credit Guarantee" />
									</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input path="dto.validityOfCreditGuarantee" type="text"
												class="form-control datepicker"
												disabled="${command.viewMode eq 'V' ? true : false }"
												id="regIncorpFpc" placeholder="dd/mm/yyyy" />
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

									<apptags:input labelCode="sfac.fpo.cgf.sacAmt"
										cssClass="hasAmount" path="dto.wctlSactionAmount"
										isMandatory="true" maxlegnth="20"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
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
									<apptags:input labelCode="sfac.fpo.cgf.intRate"
										cssClass="hasAmount" path="dto.wctlInterestRate"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

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

									<apptags:input labelCode="sfac.fpo.cgf.outAmt"
										cssClass="hasAmount" path="dto.wctlOutstandingAmount"
										
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

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

									<apptags:input labelCode="sfac.fpo.cgf.sacAmt"
										cssClass="hasAmount" path="dto.ccSactionAmount"
										isMandatory="true" maxlegnth="20"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
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
									
									<apptags:input labelCode="sfac.fpo.cgf.drawpower"
										cssClass="hasAmount" path="dto.ccDrawAmount"
										 maxlegnth="20"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>


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
									<apptags:input labelCode="sfac.fpo.cgf.intRate"
										cssClass="hasAmount" path="dto.ccInterestRate"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

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



									<apptags:input labelCode="sfac.fpo.cgf.outAmt"
										cssClass="hasAmount" path="dto.outstandingAmount"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

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
									<apptags:input labelCode="sfac.fpo.cgf.inputAmt"
										cssClass="hasAmount" path="dto.inputAmount" isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.marketingAmt"
										cssClass="hasAmount" path="dto.marketingAmount"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.processingAmt"
										cssClass="hasAmount" path="dto.processingAmount"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.otherAmt"
										cssClass="hasAmount" path="dto.ohterAmount" 
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.totalAmt"
										cssClass="hasAmount" path="dto.totalAmount" isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>


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
									<apptags:input labelCode="sfac.fpo.cgf.termLoan"
										cssClass="hasAmount" path="dto.wctlTermLoan"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.promoterMargin"
										cssClass="hasAmount" path="dto.wctlPromoterMargin"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.unsecLoan"
										cssClass="hasAmount" path="dto.wctlUnsecuredLoan"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.ohterSource"
										cssClass="hasAmount" path="dto.wctlAnyOtherSource"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
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
									<apptags:input labelCode="sfac.fpo.cgf.inputAmt"
										cssClass="hasAmount" path="dto.wclrInputAmount"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.marketingAmt"
										cssClass="hasAmount" path="dto.wclrMarketingAmount"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.processingAmt"
										cssClass="hasAmount" path="dto.wclrProcessingAmount"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.otherAmt"
										cssClass="hasAmount" path="dto.wclrOtherAmount"
										
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.totalAmt"
										cssClass="hasAmount" path="dto.wclrTotalAmount"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>


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
									<apptags:input labelCode="sfac.fpo.cgf.cclimit"
										cssClass="hasAmount" path="dto.ccLimit" isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.promoterMargin"
										cssClass="hasAmount" path="dto.ccPromoterMargin"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:input labelCode="sfac.fpo.cgf.unsecLoan"
										cssClass="hasAmount" path="dto.ccUnsecuredLoan"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

									<apptags:input labelCode="sfac.fpo.cgf.ohterSource"
										cssClass="hasAmount" path="dto.ccAnyOtherSource"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>
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


									<apptags:input labelCode="sfac.fpo.cgf.valOfSec"
										cssClass="hasAmout" path="dto.valueOfSecurity"
										isMandatory="true"
										isDisabled="${command.viewMode eq 'V' ? true : false }"></apptags:input>

								</div>



							</div>
						</div>
					</div>






					<div class="">

						<apptags:CheckerAction hideForward="true" hideSendback="true"
							hideUpload="true"></apptags:CheckerAction>

					</div>

					<div class="text-center padding-top-10">
						<button type="button" align="center" class="btn btn-green-3"
							data-toggle="tooltip" data-original-title="Submit"
							onclick="saveCreditGuaranteeApprovalData(this);">
							<spring:message code="sfac.submit" text="Submit" />
						</button>

						<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>



