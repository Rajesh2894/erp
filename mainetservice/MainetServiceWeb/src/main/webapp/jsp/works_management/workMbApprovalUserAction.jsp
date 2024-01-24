<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/works_management/workMbApproval.js"></script>
<!-- <script type="text/javascript"
	src="js/works_management/measurementBook.js"></script> -->
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script>
	jQuery('#tab2').addClass('active');
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="works.MBapproval.title" text="Work Mb User Action" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="WorkMBApproval.html" cssClass="form-horizontal"
				id="raUserActionDetails" name="raUserActionDetails">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<jsp:include page="/jsp/works_management/workRAApprovalTab.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="flag" id="flag" />
				<form:hidden path="memoType" id="memoType" />
				<form:hidden path="mbTaskName" id="mbTaskName" />
				<apptags:CheckerAction showInitiator="true"/>
				<div class="text-center clear padding-10">
					<button type="button" id="save" class="btn btn-success btn-submit"
						onclick="saveWorkMbApproval(this);">
						<i class="fa fa-sign-out padding-right-5"></i>
						<spring:message code="mileStone.submit" text="Submit" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel"
						onclick="window.location.href='AdminHome.html'" id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>