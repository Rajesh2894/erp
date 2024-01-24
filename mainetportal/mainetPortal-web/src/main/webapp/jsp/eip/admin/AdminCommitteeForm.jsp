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
	<form:form method="post" action="AdminCommitteeForm.html"
		name="frmAdminCommitteeForm" id="frmAdminCommitteeForm">

		<jsp:include page="/jsp/tiles/validationerror.jsp" />

	

		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message
						code="adminCommitte.InchargeName" />:</label>
				<apptags:inputField fieldPath="profileMaster.pNameEn" hasId="true"
					isDisabled="" cssClass="hasSpecialChara"/>
				<span class="mand">*</span>
			</div>
			<div class="element">
				<apptags:inputField fieldPath="profileMaster.pNameReg" hasId="true"
					isDisabled="" cssClass="hasSpecialChara"/>
				<span class="mand">*</span>
			</div>
		</div>

		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message
						code="adminCommitte.Designation" />:</label>
				<apptags:inputField fieldPath="profileMaster.designationEn"
					hasId="true" isDisabled="" />
				<span class="mand">*</span>
			</div>
			<div class="element">
				<apptags:inputField fieldPath="profileMaster.designationReg"
					hasId="true" isDisabled="" />
				<span class="mand">*</span>
			</div>
		</div>

		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message
						code="adminCommitte.CommitteeName" />:</label>
				<apptags:inputField fieldPath="profileMaster.linkTitleEn"
					hasId="true" isDisabled="" />
				<span class="mand">*</span>
			</div>
			<div class="element">
				<apptags:inputField fieldPath="profileMaster.linkTitleReg"
					hasId="true" isDisabled="" />
				<span class="mand">*</span>

			</div>
		</div>
		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message code="adminCommitte.shortDesc" />:</label>
				<apptags:inputField fieldPath="profileMaster.profileEn" hasId="true"
					isDisabled="" />
				<span class="mand">*</span>
			</div>
			<div class="element">
				<apptags:inputField fieldPath="profileMaster.profileReg"
					hasId="true" isDisabled="" />
				<span class="mand">*</span>
			</div>
		</div>
		<div class="form-elements">
			<div class="element">
				<label for=""><spring:message code="adminCommitte.longDesc" />:</label>

				<form:textarea path="profileMaster.summaryEng"
					cssStyle="width:150px ; height : 80px;" />
				<span class="mand">*</span>

			</div>
			<div class="element">
				<form:textarea path="profileMaster.summaryReg"
					cssStyle="width:150px ; height : 80px;" />
				<span class="mand">*</span>
			</div>
		</div>

		<div class="buttons btn-fld" align="center">
			<apptags:submitButton entityLabelCode="Admin Committee Form"
				isChildButton="false" successUrl="AdminCommitteeList.html" />
			<apptags:resetButton />
			<apptags:backButton url="AdminCommitteeList.html" />
		</div>
	</form:form>
</div>