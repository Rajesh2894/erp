<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<div class="popup-form-div">
	<form:form method="post" action="AdminCorporatorsForm.html"
		name="frmAdminCorporatorsForm" id="frmAdminCorporatorsForm">
		<jsp:include page="/jsp/tiles/validationerror.jsp" />



		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message code="CorporatorForm.Name" />:</label>
				<apptags:inputField fieldPath="profileMaster.pNameEn" hasId="true"
					isDisabled="" cssClass="hasSpecialChara"/><span class="mand">*</span>
			</div>
			<div class="element">
				<apptags:inputField fieldPath="profileMaster.pNameReg" hasId="true"
					isDisabled="" cssClass="hasSpecialChara"/><span class="mand">*</span>
			</div>
		</div>


		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message code="CorporatorForm.Prabhag" />:</label>
				<apptags:inputField fieldPath="profileMaster.prabhagEn" hasId="true"
					isDisabled="" /><span class="mand">*</span>
			</div>
			<div class="element">
				<apptags:inputField fieldPath="profileMaster.prabhagReg"
					hasId="true" isDisabled="" /><span class="mand">*</span>
			</div>
		</div>

		<div class="form-elements">
			<div class="element">

				<label for=""><spring:message
						code="CorporatorForm.Designation" />:</label>
				<apptags:inputField fieldPath="profileMaster.designationEn"
					hasId="true" isDisabled="" /><span class="mand">*</span>
			</div>
			<div class="element">
				<apptags:inputField fieldPath="profileMaster.designationReg"
					hasId="true" isDisabled="" /><span class="mand">*</span>

			</div>
		</div>
		<div class="form-elements">
			<div class="element">

				<label for=""><spring:message code="CorporatorForm.Title" />:</label>
				<apptags:inputField fieldPath="profileMaster.linkTitleEn"
					hasId="true" isDisabled="" /><span class="mand">*</span>
			</div>
			<div class="element">
				<apptags:inputField fieldPath="profileMaster.linkTitleReg"
					hasId="true" isDisabled="" /><span class="mand">*</span>
			</div>
		</div>


		<div class="form-elements">
			<div class="element">

				<label for=""><spring:message code="CorporatorForm.Profile" />:</label>
				<apptags:inputField fieldPath="profileMaster.profileEn"
					hasId="true" isDisabled="" /><span class="mand">*</span>
			</div>
			<div class="element">
				<apptags:inputField fieldPath="profileMaster.profileReg"
					hasId="true" isDisabled="" /><span class="mand">*</span>

			</div>
		</div>
		<div class="form-elements">
			<div class="element">

				<label for=""><spring:message code="CorporatorForm.msg" />:</label>
				<form:textarea path="profileMaster.summaryEng"
					cssStyle="width:150px ; height : 80px;" /><span class="mand">*</span>
			</div>
			<div class="element">
				<form:textarea path="profileMaster.summaryReg"
					cssStyle="width:150px ; height : 80px;" /><span class="mand">*</span>
			</div>
		</div>
		
		
		<div class="buttons btn-fld" align="center">
			<apptags:submitButton entityLabelCode="Admin Corporators Form"
				isChildButton="false" successUrl="AdminCorporatorsList.html" />
			<apptags:resetButton />
			<apptags:backButton url="AdminCorporatorsList.html" />
		</div>
	</form:form>
</div>