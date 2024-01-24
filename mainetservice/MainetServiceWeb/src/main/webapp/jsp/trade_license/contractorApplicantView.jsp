<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% response.setContentType("text/html; charset=utf-8"); %>

<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/mainet/login.js"></script>
<script src="js/trade_license/contractorApplicant.js"></script>

<div id="fomDivId">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<!-- Start info box -->
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="tradeLicense.contractor.applicant"
						text="Contractor Applicant" />
				</h2>
				<div class="additional-btn">
					<apptags:helpDoc url=""></apptags:helpDoc>
				</div>
			</div>
			<div class="widget-content padding">
				<div class="mand-label clearfix">
					<span><spring:message code="water.fieldwith" /><i
						class="text-red-1">*</i> <spring:message code="water.ismandtry" />
					</span>
				</div>
				
				<form:form action="ContractorApplicantDetail.html" method="POST"
					class="form-horizontal" id="ContractorApplicant">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					
			

					<div class="error-div alert alert-danger alert-dismissible"
						id="errorDivId" style="display: none;">
						<ul>
							<li><label id="errorId"></label></li>
						</ul>
					</div>

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">

						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse"
										data-parent="#accordion_single_collapse" href="#details">
										<spring:message code="rnl.contractor.applicant"
											text="Applicant Details"></spring:message>
									</a>
								</h4>
							</div>

							<div class="panel-body">
								<div class="form-group">
									
									<%-- <c:set var="baseLookupCode" value="TTL" /> --%>
								<%-- 	<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="requestDto.titleId" cssClass="form-control"
										hasChildLookup="false" hasId="true" showAll="false"
										disabled="true" selectOptionLabelCode="Select"
										isMandatory="true" /> --%>
									<%-- <label class="col-sm-2 control-label required-control"
										for="applicantTitle"> <spring:message
											code="rnl.tenant.title" text="Title" />
									</label>

										<c:set var="baseLookupCode" value="TTL" />
									<apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="requestDto.titleId"
										cssClass="form-control chosen-select-no-results"
										hasChildLookup="false" hasId="true" 
										selectOptionLabelCode="applicantinfo.label.select"/> --%>


									<%-- <div class="col-sm-4">
										<c:set var="baseLookupCode" value="TTL" />
										<form:select class=" mandColorClass form-control "
											path="" id="titleId"
											<option value="0">
												<spring:message code="lgl.select" text="Select" />
											</option>
												 <c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
													<form:option value="${lookUp.lookUpId}" code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach> 
										</form:select>
									</div> --%>
									
									<apptags:input labelCode="rnl.master.fname"
										cssClass="hasNameClass mandColorClass hasNoSpace"
										path="requestDto.fName" isMandatory="true" maxlegnth="100"></apptags:input>
								</div>
								<div class="form-group">
									<apptags:input labelCode="rnl.master.mname"
										cssClass="hasNameClass mandColorClass hasNoSpace"
										path="requestDto.mName" isMandatory="false" maxlegnth="100"></apptags:input>

									<apptags:input labelCode="rnl.master.lname"
										cssClass="hasNameClass mandColorClass hasNoSpace"
										path="requestDto.lName" isMandatory="true" maxlegnth="100"></apptags:input>
								</div>
								
								</div>

							</div>
							
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse"
										data-parent="#accordion_single_collapse" href="#address">
										<spring:message code="rnl.contractor.applicantAddress"
											text="Applicant Address"></spring:message>
									</a>
								</h4>
							</div>
							<div class="panel-body">
								<div class="form-group">
									<apptags:input labelCode="trade.contractLicense.buildingname"
										cssClass="alphaNumeric form-control"
										path="requestDto.bldgName" isMandatory="true" maxlegnth="100">
									</apptags:input>

									<apptags:input labelCode="trade.contractLicense.block"
										cssClass="hasNameClass mandColorClass"
										path="requestDto.blockName" isMandatory="false"
										maxlegnth="100">
									</apptags:input>
								</div>

							 	<div class="form-group">
									<apptags:input labelCode="trade.app.roadName"
										cssClass="hasNameClass mandColorClass"
										path="requestDto.roadName" isMandatory="false" maxlegnth="100">
									</apptags:input>

									<label class="col-sm-2 control-label required-control"
										for="wardName"> <spring:message
											code="trade.ward" text="Ward" />
									</label>
									<c:set var="baseLookupCode" value="MWZ" />
									<%-- <apptags:lookupField
										items="${command.getLevelData(baseLookupCode)}"
										path="requestDto.wardNo"
										cssClass="form-control chosen-selMect-no-results"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="applicantinfo.label.select"
										isMandatory="true" /> --%>
								</div> 

								<div class="form-group">
									<apptags:input labelCode="rnl.payment.city"
										cssClass="hasNameClass mandColorClass"
										path="requestDto.cityName" isMandatory="true" maxlegnth="100">
									</apptags:input>

									<apptags:input labelCode="rnl.master.pincode"
										cssClass="hasNumber mandColorClass"
										path="requestDto.pincodeNo" isMandatory="false" maxlegnth="6">
									</apptags:input>
								</div>

								<div class="form-group">
									<apptags:input labelCode="accounts.vendormaster.mobileNumber"
										cssClass="hasMobileNo required-control form-control"
										path="requestDto.mobileNo" isMandatory="true" maxlegnth="10">
									</apptags:input>

									<apptags:input labelCode="rnl.master.email"
										cssClass="mandColorClass" path="requestDto.email"
										isMandatory="false" maxlegnth="100">
									</apptags:input>
								</div>
							</div>

							
							
							<!-- ############################ Vendor Master ############################################ -->
							<div id="vendorMasterDiv">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse"
											data-parent="#accordion_single_collapse" href="#details">
											<spring:message code="account.vendor.master"
												text="Vendor Master"></spring:message>
										</a>
									</h4>
								</div>
								<div class="panel-body">
								
									<div class="form-group">

										<label class="control-label col-sm-2 required-control">
											<spring:message code="accounts.vendormaster.vendortype"></spring:message>
										</label>
										<div class="col-sm-4">
											<form:select id="cpdVendortype" path="tbAcVendormaster.cpdVendortype"
												class="form-control chosen-select-no-results"
												data-rule-required="true">
												<form:option value="">
													<spring:message code="master.selectDropDwn" />
												</form:option>
												<c:forEach items="${venderType}" varStatus="status"
													var="levelParent">
													<form:option code="${levelParent.lookUpCode}"
														value="${levelParent.lookUpId}">${levelParent.descLangFirst}</form:option>
												</c:forEach>
											</form:select>
										</div>
										
										<label class="control-label col-sm-2 required-control">
											<spring:message code="tradeLicense.vendormaster.vendorSubType"></spring:message>
										</label>
										<div class="col-sm-4">
											<form:select id="cpdVendorSubType" path="tbAcVendormaster.cpdVendorSubType"
												class="form-control chosen-select-no-results"
												data-rule-required="true">
												<form:option value="">
													<spring:message code="master.selectDropDwn" />
												</form:option>
												<c:forEach items="${vendorStatus}" varStatus="status"
													var="levelChild">
													<form:option code="${levelChild.lookUpCode}"
														value="${levelChild.lookUpId}">${levelChild.descLangFirst}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>

									<div class="form-group">

										<label id=vendorvmvendornamel
											class="control-label col-sm-2 required-control"> <spring:message
												code="accounts.vendormaster.vendorName"></spring:message>
										</label>
										<div id=vendorvmvendorname class="col-sm-4">
											<form:input id="vendor_vmvendorname" path="tbAcVendormaster.vmVendorname"
												class="form-control" data-rule-required="true"
												data-rule-maxLength="200"  />
										</div>
										
										<label class="control-label col-sm-2 required-control" id="vendor_mobileNolbl">
											<spring:message code="accounts.vendormaster.mobileNumber"></spring:message>
										</label>
										<div class="col-sm-4">
											<form:input id="vendor_mobileNo" path="tbAcVendormaster.mobileNo"
												class="form-control hasMobileNo mandColorClass"
												data-rule-number="10" data-rule-minLength="10"
												data-rule-maxLength="10" />
										</div>
									</div>

									<div class="form-group">
										
										<label class="control-label col-sm-2"> <spring:message
												code="accounts.vendormaster.emailId"></spring:message>
										</label>
										<div class="col-sm-4">
											<form:input id="vendoremailId" path="tbAcVendormaster.emailId"
												class="form-control" data-rule-email="true" />
										</div>
										
										<label class="control-label col-sm-2 "> <spring:message
												code="accounts.vendormaster.aadharNo"></spring:message>
										</label>
										<div class="col-sm-4">
											<form:input path="tbAcVendormaster.vmUidNo" id="vendor_vmuidno"
												cssClass="form-control" maxlength="14" />
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-sm-2"><spring:message
												code="accounts.vendormaster.gstno" text="GST Number" /></label>
										<div class="col-sm-4">
											<form:input id="gstNumber" path="tbAcVendormaster.vmGstNo"
												class="form-control" maxLength="15"
												placeholder="Ex: 22AAAAA0000A1Z5" />
										</div>
										
										<label class="control-label col-sm-2 "> <spring:message
												code="accounts.vendormaster.panNo">
											</spring:message>
										</label>
										<div class="col-sm-4">
											<form:input id="vendor_vmpannumber" path="tbAcVendormaster.vmPanNumber"
												class="form-control text-uppercase " maxLength="10"
												onchange="fnValidatePAN(this)" />
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-sm-2"> <spring:message
												code="accounts.vendormaster.bankname">
											</spring:message>
										</label>

										<div class="col-sm-4">
											<form:select id="bankId" path="tbAcVendormaster.bankId"
												class="form-control chosen-select-no-results">
												<form:option value="">
													<spring:message code="master.selectDropDwn" />
												</form:option>
												<c:forEach items="${custBankList}" var="bankId">
													<form:option value="${bankId.key}">${bankId.value}</form:option>
												</c:forEach>
											</form:select>
										</div>

										<label class="control-label col-sm-2"> <spring:message
												code="accounts.vendormaster.bankAccountNumber"></spring:message>
										</label>
										<div class="col-sm-4">
											<form:input id="vendor_bankaccountnumber"
												path="tbAcVendormaster.bankaccountnumber" class="form-control hasNumber"
												maxLength="20" />
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-sm-2 required-control"
											id="vendor_venClassNamelbl"> <spring:message
												code="accounts.vendormaster.vendorClass"></spring:message>
										</label>
										<div class="col-sm-4">
											<form:select path="tbAcVendormaster.vendorClass"
												class="form-control  chosen-select-no-results"
												id="">
												<form:option value="">
													<spring:message code="master.selectDropDwn" text="Select" />
												</form:option>
												<c:forEach items="${vendorClass}" var="venClass">
													<form:option value="${venClass.lookUpId}"
														code="${venClass.lookUpCode}">${venClass.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>

										<label class="control-label col-sm-2 required-control">
											<spring:message code="accounts.vendormaster.address"></spring:message>
										</label>
										<div class="col-sm-4">
											<form:textarea id="vendor_vvmvendoradd" path="tbAcVendormaster.vmVendoradd"
												class="form-control" data-rule-required="true"
												data-rule-maxLength="200" />
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-sm-2" for=""> <spring:message
												code="" text="PF Account Number"></spring:message>
										</label>
										<div class="col-sm-4">
											<form:input path="tbAcVendormaster.pfAcNumber" id="pfAcNumber"
												cssClass="form-control" maxlength="40" />
										</div>		
									</div>
								</div>		
								</div>
								
								
								<h2>
						<strong><spring:message code="" text="User Action" /></strong>
					</h2>
				</div>
				<br>

				<div class="form-group">
					<apptags:radio radioLabel="Approve,Reject"
						radioValue="APPROVED,REJECTED" isMandatory="true"
						labelCode="DeploymentOfStaffDTO.statusApproval"
						path="requestDto.status"
						defaultCheckedValue="APPROVED">
					</apptags:radio>
					<apptags:textArea labelCode="DeploymentOfStaffDTO.remarkApproval"
						isMandatory="true" path="requestDto.remark"
						cssClass="hasNumClass form-control" maxlegnth="100" />
				</div>
				<div class="text-center">
					<button type="button" value="<spring:message code="bt.save"/>"
						class="btn btn-green-3" title='<spring:message code="bt.save"/>'
						onclick="saveContractorApplicantApprovalData(this)">
						<spring:message code="bt.save"/>
					</button>

					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
							</div>
						

							</div>
							</div>
							
				</form:form>
			</div>
		</div>
	</div>
</div>
