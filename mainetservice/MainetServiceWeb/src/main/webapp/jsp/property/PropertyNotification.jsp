<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/propertyNotification.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="property.notification"
						text="Property Notification" /></strong>
			</h2>
		</div>

		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="property.notification"
						text="property.notification" /> </span>
			</div>

			<!-- Start Form -->
			<form:form action="PropertyNotification.html"
				class="form-horizontal form" name="PropertyNotification"
				id="PropertyNotification">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<apptags:input labelCode="property.ChangeInAss.EnterPropertyNo"
						path="searchDto.proertyNo" ></apptags:input>
					
				</div>
				<div class="form-group wardZone">
					<apptags:lookupFieldSet baseLookupCode="WZB" hasId="true"
						showOnlyLabel="false" pathPrefix="searchDto.assWard"
						isMandatory="true" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control required-control " showAll="false"
						showData="true" />
				</div>

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2" id="serchBtn"
						onclick="sendNotifiations(this)">
						<spring:message code="property.sendNotification"
							text="Send Notification" />
					</button>

					<button type="button" class="btn btn-warning" id="resetBtn"
						onclick="resetButton()">
						<spring:message code="property.reset" text="Reset" />
					</button>

					<button type="button" class="btn btn-danger" id="back"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="bt.backBtn" text="Back"></spring:message>
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>
