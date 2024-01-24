<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/masters/customerMaster/customerMaster.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header" id="hiddenDiv">
			<h2>
				<spring:message code="customerMaster.heading" text="Customer Master" />
			</h2>
		</div>
		<div class="mand-label clearfix">
			<span><spring:message code="account.common.mandmsg"
					text="Field with" /> <i class="text-red-1">*</i> <spring:message
					code="account.common.mandmsg1" text="is mandatory" /> </span>
		</div>
		<div class="widget-content padding ">

			<form:form class="form-horizontal" commandName="command"
				action="CustomerMaster.html" method="POST" name="CustomerMaster"
				id="customerMasterFrm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<form:hidden path="" value="${command.saveMode}" id="viewMode"/>

				<c:choose>
					<c:when
						test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">
						<div class="form-group padding-top-20">
							<label class="control-label col-sm-2 required-control"> <spring:message
									code="customerMaster.custType" text="Customer Type"></spring:message>
							</label>
							<c:set var="baseLookupCode" value="OWT" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="custMasterDTO.custType"
								cssClass="form-control required-control" isMandatory="true"
								disabled="${command.saveMode eq 'V' ? true : false }"
								selectOptionLabelCode="selectdropdown" hasId="true" />

							<label class="control-label col-sm-2 required-control"> <spring:message
									code="customerMaster.custTinNo" text="Identifier Number">
								</spring:message>
							</label>
							<div class="col-sm-4">
								<form:input id="custTINNo" path="custMasterDTO.custTINNo"
									disabled="${command.saveMode eq 'V' ? true : false }"
									class="form-control" maxLength="10" />
							</div>

						</div>

						<div class="form-group">

							<label class="control-label col-sm-2 required-control"> <spring:message
									code="customerMaster.custName" text="Customer Name"></spring:message>
							</label>
							<div class="col-sm-4">
								<form:input id="custName" path="custMasterDTO.custName"
									disabled="${command.saveMode eq 'V' ? true : false }"
									class="form-control" data-rule-required="true"
									data-rule-maxLength="200" onchange="" />
							</div>
							<label class="control-label col-sm-2 required-control"> <spring:message
									code="customerMaster.custAddress" text="Customer Address"></spring:message>
							</label>
							<div class="col-sm-4">
								<form:textarea id="custAddress" path="custMasterDTO.custAddress"
									disabled="${command.saveMode eq 'V' ? true : false }"
									class="form-control" data-rule-required="true"
									data-rule-maxLength="200" />
							</div>


						</div>

						<div class="form-group">

							<label class="control-label col-sm-2"> <spring:message
									code="accounts.vendormaster.mobileNumber"></spring:message>
							</label>
							<div class="col-sm-4">
								<form:input id="custMobNo" path="custMasterDTO.custMobNo"
									disabled="${command.saveMode eq 'V' ? true : false }"
									class="form-control hasMobileNo" data-rule-number="10"
									data-rule-minLength="10" data-rule-maxLength="10" />
							</div>

							<label class="control-label col-sm-2"> <spring:message
									code="accounts.vendormaster.emailId"></spring:message>
							</label>
							<div class="col-sm-4">
								<form:input id="custEmailId" path="custMasterDTO.custEmailId"
									disabled="${command.saveMode eq 'V' ? true : false }"
									class="form-control hasemailclass" data-rule-email="true" />

							</div>


						</div>

						<div class="form-group">
							<label class="control-label col-sm-2"><spring:message
									code="accounts.vendormaster.gstno" text="GST Number" /></label>
							<div class="col-sm-4">
								<form:input id="custGSTNo" path="custMasterDTO.custGSTNo"
									disabled="${command.saveMode eq 'V' ? true : false }"
									class="form-control" maxLength="15"
									placeholder="Ex: 22AAAAA0000A1Z5" />
							</div>

							<label class="control-label col-sm-2 "> <spring:message
									code="accounts.vendormaster.panNo">
								</spring:message>
							</label>
							<div class="col-sm-4">
								<form:input id="custPANNo" path="custMasterDTO.custPANNo"
									disabled="${command.saveMode eq 'V' ? true : false }"
									class="form-control text-uppercase " maxLength="10"
									onchange="fnValidatePAN(this)" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2 "> <spring:message
									code="customerMaster.custUidNo" text="UID Number"></spring:message>
							</label>
							<div class="col-sm-4">
								<form:input path="custMasterDTO.custUIDNo" id="custUIDNo"
									disabled="${command.saveMode eq 'V' ? true : false }"
									cssClass="form-control hasNumber" maxlength="12" />
							</div>

							<label class="control-label col-sm-2 required-control"> <spring:message
									code="accounts.vendormaster.remark"></spring:message>
							</label>
							<div class="col-sm-4">
								<form:textarea id="remark" path="custMasterDTO.remark"
									disabled="${command.saveMode eq 'V' ? true : false }"
									class="form-control" maxLength="200" data-rule-required="" />
							</div>
						</div>
						<div class="form-group">

							<label class="col-sm-2 control-label" for=""><spring:message
									code="accounts.master.status" text="Status" /> </label>
							<c:set var="baseLookupCode" value="ACN" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="custMasterDTO.custStatus"
								cssClass="form-control required-control" isMandatory="true"
								disabled="${command.saveMode eq 'V' ? true : false }"
								selectOptionLabelCode="selectdropdown" hasId="true" />
						</div>
					</c:when>
					<c:otherwise>
						<div class="form-group padding-top-20">
							<label class="control-label col-sm-2 required-control"> <spring:message
									code="customerMaster.custType" text="Customer Type"></spring:message>
							</label>
							<c:set var="baseLookupCode" value="OWT" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="custMasterDTO.custType"
								cssClass="form-control required-control" isMandatory="true"
								selectOptionLabelCode="selectdropdown" hasId="true" />

							<label class="control-label col-sm-2 required-control"> <spring:message
									code="customerMaster.custTinNo" text="Indentifier Number">
								</spring:message>
							</label>
							<div class="col-sm-4">
								<form:input id="custTINNo" path="custMasterDTO.custTINNo"
									class="form-control " maxLength="10" />
							</div>



						</div>

						<div class="form-group">
							<label class="control-label col-sm-2 required-control"> <spring:message
									code="customerMaster.custName" text="Customer Name"></spring:message>
							</label>
							<div class="col-sm-4">
								<form:input id="custName" path="custMasterDTO.custName"
									class="form-control" data-rule-required="true"
									data-rule-maxLength="200" onchange="" />
							</div>
							<label class="control-label col-sm-2 required-control"> <spring:message
									code="customerMaster.custAddress" text="customer Address"></spring:message>
							</label>
							<div class="col-sm-4">
								<form:textarea id="custAddress" path="custMasterDTO.custAddress"
									class="form-control" data-rule-required="true"
									data-rule-maxLength="200" />
							</div>


						</div>

						<div class="form-group">
							<label class="control-label col-sm-2"> <spring:message
									code="accounts.vendormaster.mobileNumber"></spring:message>
							</label>
							<div class="col-sm-4">
								<form:input id="custMobNo" path="custMasterDTO.custMobNo"
									class="form-control hasMobileNo" data-rule-number="10"
									data-rule-minLength="10" data-rule-maxLength="10" />
							</div>
							<label class="control-label col-sm-2"> <spring:message
									code="accounts.vendormaster.emailId"></spring:message>
							</label>
							<div class="col-sm-4">
								<form:input id="custEmailId" path="custMasterDTO.custEmailId"
									class="form-control hasemailclass" data-rule-email="true" />

							</div>


						</div>

						<div class="form-group">
							<label class="control-label col-sm-2"><spring:message
									code="accounts.vendormaster.gstno" text="GST Number" /></label>
							<div class="col-sm-4">
								<form:input id="custGSTNo" path="custMasterDTO.custGSTNo"
									class="form-control" maxLength="15"
									placeholder="Ex: 22AAAAA0000A1Z5" />
							</div>

							<label class="control-label col-sm-2 "> <spring:message
									code="accounts.vendormaster.panNo">
								</spring:message>
							</label>
							<div class="col-sm-4">
								<form:input id="custPANNo" path="custMasterDTO.custPANNo"
									class="form-control text-uppercase " maxLength="10"
									onchange="fnValidatePAN(this)" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-sm-2 "> <spring:message
									code="accounts.vendormaster.aadharNo"></spring:message>
							</label>
							<div class="col-sm-4">
								<form:input path="custMasterDTO.custUIDNo" id="custUIDNo"
									cssClass="form-control hasNumber" maxlength="12" />
							</div>
						</div>
					</c:otherwise>
				</c:choose>

				<div class="form-group">
					<div class="text-center padding-bottom-20" id="divSubmit">
						<c:if test="${command.saveMode ne 'V'}">

							<button type="button" class="btn btn-success btn-submit"
								id="submit" onclick="Proceed(this)">
								<spring:message code="master.save" text="Save" />
							</button>
						</c:if>
						<c:if test="${command.saveMode eq 'C'}">
							<apptags:resetButton buttonLabel="water.btn.reset"></apptags:resetButton>					
						</c:if>
						<input type="button" class="btn btn-danger"
							onclick="javascript:openRelatedForm('CustomerMaster.html');"
							value="<spring:message code="back.msg" text="Back"/>" />
					</div>
				</div>

			</form:form>
		</div>
	</div>
</div>
