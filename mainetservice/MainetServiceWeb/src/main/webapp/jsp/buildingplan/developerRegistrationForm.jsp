<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/buildingplan/developerRegForm.js"></script>
<style>
.link-disabled{
	pointer-events: none;
}

</style>
<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="developer.registration.heading"
						text="Developer Registration" />
				</h2>
				<apptags:helpDoc url="DeveloperRegistrationForm.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form id="DeveloperRegForm" action="DeveloperRegistrationForm.html"
					method="post" class="form-horizontal">
					
					<div class="">
						<div class="mand-label clearfix">
							<span><spring:message code="mrm.fiels.mandatory.message" /></span>
						</div>

						<ul class="nav nav-tabs" id="devParentTab">


							<li id="applicantInfo-tab" class="active"><a data-toggle="tab"
								href="#applicantInfo" data-content-param="showApplicantInfoPage"
								data-loaded="true"><spring:message
										code="applicant.information" text="Applicant Information" /></a></li>

							<li id="authorizedUser-tab" class="disabled link-disabled"><a data-toggle="tab" 
								href="#authorizedUser" data-content-param="showAuthorizedUserPage"
								data-loaded="false" ><spring:message
										code="authorized.user" text="Authorized User"/></a></li>

							<li id="developerCapacity-tab" class="disabled link-disabled"><a data-toggle="tab"
								href="#developerCapacity" data-content-param="showDeveloperCapacityPage"
								data-loaded="false"><spring:message
										code="developer.capacity" text="Developer Capacity" /></a></li>


							<li id="summarys-tab" class="disabled link-disabled"><a data-toggle="tab"
								href="#summary" data-content-param="showSummaryPage"
								data-loaded="false"><spring:message
										code="summary.tab" text="Summary"/></a></li>

						</ul>
						<div class="tab-content">
							<div id="applicantInfo" class="tab-pane fade in active">
								<jsp:include page="/jsp/buildingplan/applicantInformationForm.jsp" />
							</div>

							<div id="authorizedUser" class="tab-pane fade"></div>

							<div id="developerCapacity" class="tab-pane fade">
								
							</div>

							<div id="summary" class="tab-pane fade"></div>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>