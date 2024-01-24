<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script src="js/rti/offlinePay.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/tp/builderRegistration.js"></script>

<div class="form-div">
	<h1>
		<spring:message code="tp.builderDet"></spring:message>
	</h1>
	<div class="clearfix" id="testID">
		<div class="col-xs-12">
			<div class="row">
				<div id="content">
					<div class="mand-label">
						<spring:message code="MandatoryMsg" />
					</div>


					<div class="regheader">
						<spring:message code="tp.FirmDetails" />
					</div>
					<div class="form-elements clear">
						<div class="element">
							<label><spring:message code="tp.NameofFirm" /></label>

							<form:input path="licenseMaster.licAgency"
								cssClass="mandClassColor disablefield" readonly="true" />
							<span class="mand">*</span>
						</div>

						<div class="element">
							<label><spring:message code="tp.TypeofFirm" /></label>
							<c:set var="baseLookupCode" value="TYF" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="licenseMaster.licFirmType" showOnlyLabel=""
								selectOptionLabelCode="tp.selTypeOfFirm" hasId="true" />
						</div>
					</div>
					<div class="form-elements clear">
						<div class="element">
							<label><spring:message code="tp.DateOfBirth" /></label>
							<apptags:dateField fieldclass="datepicker"
								datePath="licenseMaster.licDob" isMandatory="true"
								cssClass="mandClassColor  cal disablefield" readonly="true" />
							<span class="mand">*</span>
						</div>
						<div class="element">
							<label><spring:message code="tp.Nationality" /></label>
							<c:set var="baseLookupCode" value="TRY" />

							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode,1)}"
								path="licenseMaster.licNationality" showOnlyLabel=""
								selectOptionLabelCode="tp.SelectNationality" hasId="true" />
						</div>
					</div>

					<div class="form-elements clear">
						<div class="element">
							<label><spring:message code="tp.RegistrationNo" /></label>
							<form:input path="licenseMaster.licCntArcregno"
								cssClass="mandClassColor hasNumber disablefield" readonly="true" />
							<span class="mand">*</span>
						</div>
						<div class="element">
							<label><spring:message code="tp.PanTin"></spring:message></label>
							<form:input path="licenseMaster.licPanno"
								cssClass="mandClassColor disablefield" readonly="true" />
							<span class="mand">*</span>
						</div>
					</div>

					<div class="form-elements clear">
						<div class="element">
							<label><spring:message code="tp.SalesTaxNo" /></label>
							<form:input path="licenseMaster.licSalesTax"
								cssClass="mandClassColor hasNumber disablefield" readonly="true" />
							<span class="mand">*</span>
						</div>
						<div class="element">
							<label><spring:message code="tp.Vatno" /></label>
							<form:input path="licenseMaster.licVatNo"
								cssClass="mandClassColor hasNumber disablefield" readonly="true" />
							<span class="mand">*</span>
						</div>
					</div>
					<div class="form-elements clear">
						<div class="element">
							<label><spring:message code="tp.CategoryAppliedfor" /></label>
							<c:set var="baseLookupCode" value="CLS" />
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="licenseMaster.licTechperClass" showOnlyLabel=""
								selectOptionLabelCode="tp.SelectCategory " hasId="true" />

						</div>
						<div class="element">
							<label><spring:message code="tp.Addres" /></label>
							<form:textarea path="licenseMaster.licApplicantAddr"
								cssClass="texboxcase mandClassColor disablefield"
								maxlength="500" readonly="true" />
							<span class="mand">*</span>
						</div>
					</div>
					<div class="form-elements">
						<div class="element">
							<label><spring:message code="tp.TelNo" /></label>
							<form:input path="licenseMaster.licPhoneno"
								cssClass="mandClassColor hasNumber disablefield" maxlength="11"
								readonly="true" />
							<span class="mand">*</span>
						</div>
						<div class="element">
							<label><spring:message code="tp.MobileNo" /></label>
							<form:input path="licenseMaster.licMobileno"
								cssClass="mandClassColor hasNumber disablefield" maxlength="10"
								readonly="true" />
							<span class="mand">*</span>
						</div>
					</div>
					<div class="form-elements">
						<div class="element">
							<label><spring:message code="tp.EmailId" /></label>
							<form:input path="licenseMaster.licEmail"
								cssClass="mandClassColor disablefield" readonly="true" />
							<span class="mand">*</span>
						</div>
					</div>


					<div class="regheader">
						<spring:message code="tp.NameofPartner" />
					</div>
					<div class="clear padding_5"></div>
					<c:if test="${command.partnerDetail.size() ne 0 }">
					<table class="gridtable table123" id="secondParty${status.index}">
						<tbody>
							<tr>
								<th><spring:message code="tp.Title" /> <span class="mand2">*</span></th>
								<th><spring:message code="tp.FirstName" /><span class="mand2">*</span></th>
								<th><spring:message code="tp.MiddleName" /></th>
								<th><spring:message code="tp.LastName" /><span class="mand2">*</span></th>
							</tr>
							<c:forEach items="${command.partnerDetail}" var="in"
								varStatus="status">
								<tr class="tr_clone" id="partnerDetail${status.index}">
									<td><c:set var="baseLookupCode" value="TTL" /> <apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}"
											path="partnerDetail[${status.index}].licTitle"
											cssClass="select_title disablefield"
											selectOptionLabelCode="tp.Title" hasId="true"
											isMandatory="true" tabIndex="1" showOnlyLabel="true" /></td>
									<td><form:input
											path="partnerDetail[${status.index}].licFname"
											cssClass="input2 mandClassColor disablefield" hasId="true"
											readonly="true" /></td>
									<td><form:input
											path="partnerDetail[${status.index}].licMname"
											cssClass="input2 disablefield" hasId="true" readonly="true" /></td>
									<td><form:input
											path="partnerDetail[${status.index}].licLname"
											cssClass="input2 mandClassColor disablefield" hasId="true"
											readonly="true" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	$("#licFirmType").prop('disabled', 'disabled').addClass("disablefield");
	$("#licNationality").prop('disabled', 'disabled').addClass("disablefield");
	$("#licTechperClass").prop('disabled', 'disabled').addClass("disablefield");
	$("#licTitle").prop('disabled', 'disabled').addClass("disablefield");
</script>







