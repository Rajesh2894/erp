<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/buildingplan/newTCPLicenseForm.js"></script>


<style>
.link-disabled {
	pointer-events: none;
}
</style>

<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code=""
						text="New License Application" />
				</h2>
				<apptags:helpDoc url="NewTCPLicenseForm.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form id="newLicenseFormId" action="NewTCPLicenseForm.html"
					method="post" class="form-horizontal">
					
					<div class="">
						<div class="mand-label clearfix">
							<span><spring:message code="mrm.fiels.mandatory.message" /></span>
						</div>

						<ul class="nav nav-tabs" id="newLicenseParentTab">


							<li id="applicantForm-tab" class="active"><a data-toggle="tab"
								href="#applicantForm" data-content-param="showApplicantFormPage"
								data-loaded="true"><spring:message
										code="" text="Applicant Information" /></a></li>

							<li id="applicationPurpose-tab" class="disabled link-disabled "><a data-toggle="tab" 
								href="#applicationPurpose" data-content-param="showApplicationPurposePage"
								data-loaded="false"><spring:message
										code="" text="Application Purpose"/></a></li>

							<li id="landSchedule-tab" class="disabled link-disabled "><a data-toggle="tab"
								href="#landSchedule" data-content-param="showLandScheduleage"
								data-loaded="false"><spring:message
										code="" text="Land Schedule" /></a></li>


							<li id="detailsOfLand-tab" class="disabled link-disabled "><a data-toggle="tab"
								href="#detailsOfLand" data-content-param="showDetailsOfLandPage"
								data-loaded="false"><spring:message
										code="" text="Details Of Applied Land"/></a></li>
										
							<li id="charges-tab" class="disabled link-disabled "><a data-toggle="tab"
								href="#chargesPage" data-content-param="showChargesPage"
								data-loaded="false"><spring:message
										code="" text="Fees and Charges"/></a></li>			

						</ul>
						<div class="tab-content">
							<div id="applicantForm" class="tab-pane fade in active">
								<jsp:include page="/jsp/buildingplan/newLicenseApplicantForm.jsp" />
							</div>

							<div id="applicationPurpose" class="tab-pane fade"></div>

							<div id="landSchedule" class="tab-pane fade"></div>

							<div id="detailsOfLand" class="tab-pane fade"></div>
							
							<div id="chargesPage" class="tab-pane fade"></div>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>