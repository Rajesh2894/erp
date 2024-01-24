
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<script src="js/mainet/validation.js"></script>
<script>
$( document ).ready(function() {
	if($("#viewMode").val()=="Y"){
	 $("#firstName").addClass("disablefield").removeClass("mandClassColor mand ").attr("disabled", true);
	 $("#middleName").addClass("disablefield").removeClass("mandClassColor mand ").attr("disabled", true);
	 $("#lastName").addClass("disablefield").removeClass("mandClassColor mand ").attr("disabled", true);
	 $("#mobileNo").addClass("disablefield").removeClass("mandClassColor mand ").attr("disabled", true);          
	 $("#OwnerEmailId").addClass("disablefield").removeClass("mandClassColor mand ").attr("disabled", true);
	 $("#title").addClass("disablefield").removeClass("mandClassColor mand ").attr("disabled", true);
	 
			} 
});

</script>


<c:set value="${command.getLoggedInUserType()}" var="loggedInUserType"></c:set>
<c:choose>

	<c:when test="${loggedInUserType eq 'C' || loggedInUserType eq 'AGN' }">
<input type="hidden" id="viewMode" value="${command.agencyApplicantDetailViewMode}"/>
		<div class="table">
			<div class="col-155">
				<label><spring:message code="common.applicantName" /> :</label>
			</div>
			<div class="col-50">
				<spring:message code="common.addOwnTitle" var="title" />
				<c:set var="baseLookupCode" value="TTL" />
				<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
					path="${param.titleField}" cssClass="col-50"
					selectOptionLabelCode="${title}" hasId="true" isMandatory="true"
					 showOnlyLabel="${command.agencyApplicantDetailViewMode  == 'Y' ? 'true':'false'}"/>
			</div>
			<div class="col-130">
				<spring:message code="common.firstName" var="firstName" />
				<form:input path="${param.firstNameField}" id="firstName"
					cssClass="input2 mandClassColor" placeholder="${firstName}"
					 maxlength="100" readonly="${command.agencyApplicantDetailViewMode  == 'Y' ? 'true':'false'}"/>
			</div>
			<span class="mand padding_5">*</span>
			<div class="col-165">
				<spring:message code="common.middleName"
					var="middleName" />
				<form:input path="${param.middleNameField}" id="middleName"
					cssClass="input2" placeholder="${middleName}" maxlength="100" 
					readonly="${command.agencyApplicantDetailViewMode  == 'Y' ? 'true':'false'}"/>
			</div>
			<div class="col-180">
				<spring:message code="common.lastName" var="lastName" />
				<form:input path="${param.lastNameField}" id="lastName"
					cssClass="input2 mandClassColor" placeholder="${lastName}"
					maxlength="100" readonly="${command.agencyApplicantDetailViewMode  == 'Y' ? 'true':'false'}"/>
			</div>
			<span class="mand padding_5">*</span>

		</div>

	</c:when>
	<c:when
		test="${loggedInUserType eq 'CC' || loggedInUserType eq 'CFC' || loggedInUserType eq '' ||loggedInUserType eq 'HS'}">

		<input type="hidden" id="viewMode" value="${command.agencyApplicantDetailViewMode}"/>

		<div class="table">
			<div class="col-155">
				<label><spring:message code="common.applicantName" /> :</label>
			</div>
			<div class="col-50">
				<spring:message code="common.addOwnTitle" var="title" />
				<c:set var="baseLookupCode" value="TTL" />
				<apptags:lookupField items="${command.getLevelData(baseLookupCode)}"
					path="${param.titleField}" cssClass="col-50"
					selectOptionLabelCode="${title}" hasId="true" isMandatory="true" 
					showOnlyLabel="${command.agencyApplicantDetailViewMode  == 'Y' ? 'true':'false'}"/>
					
			</div>
			<div class="col-130">
				<spring:message code="common.firstName" var="firstName" />
				<form:input path="${param.firstNameField}" id="firstName"
					cssClass="input2 mandClassColor" placeholder="${firstName}" maxlength="100"
					 readonly="${command.agencyApplicantDetailViewMode  == 'Y' ? 'true':'false'}"/>
			</div>
			<span class="mand padding_5">*</span>
			<div class="col-165">
				<spring:message code="common.middleName"
					var="middleName" />
				<form:input path="${param.middleNameField}" 
					id="middleName" cssClass="input2" placeholder="${middleName}" maxlength="100"
					 readonly="${command.agencyApplicantDetailViewMode  == 'Y' ? 'true':'false'}"/>
			</div>
			<div class="col-180">
				<spring:message code="common.lastName" var="lastName" />
				<form:input path="${param.lastNameField}" value=""
					id="lastName" cssClass="input2 mandClassColor" maxlength="100"
					placeholder="${lastNameField}"  readonly="${command.agencyApplicantDetailViewMode  == 'Y' ? 'true':'false'}" />
			</div>
			<span class="mand padding_5">*</span>
		</div>

			</c:when>
	<c:otherwise>

	</c:otherwise>
</c:choose>


