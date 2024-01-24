<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Your awesome content goes here -->
	<div class="widget" id="receipt">
		<div class="widget-content padding">
			<form action="" method="get">
				<div class="row">
					<div class="col-xs-12 text-center">
						<h2 class="margin-bottom-0">${userSession.organisation.ONlsOrgname}</h2>
					</div>


					<div class="col-xs-12 col-sm-12 text-center">
						<h3 class="margin-bottom-0 margin-top-0 padding-top-30">
							<%-- ${command.serviceName} --%>
							<%-- <spring:message code="applicant.acknowledgement.title" /> --%>
							<span class="text-bold"><spring:message
									code="mrm.acknowledgement.applNo" /></span> <span
								class="text-bold margin-left-40">212082700001</span>
						</h3>
					</div>
				</div>
				<br></br>

				<div class="row margin-bottom-10">
					<div class="col-xs-2">
						<span class="text-bold"><spring:message
								code="mrm.acknowledgement.applicantName" /></span>
					</div>
					<div class="col-xs-4">
						:<span class="margin-left-20">${command.marriageDTO.applicantName}</span>
					</div>
				</div>
				
				<div class="row">
					<div class="col-xs-2">
						<span class="text-bold"><spring:message
								code="mrm.acknowledgement.serviceName" /></span>
					</div>
					<div class="col-xs-4">
						:<span class="margin-left-20">${command.marriageDTO.serviceShortCode}</span>
					</div>
				</div>
				
				<div class="row margin-bottom-10">
					<div class="col-xs-2">
						<span class="text-bold"><spring:message
								code="mrm.acknowledgement.deptName" /></span>
					</div>
					<div class="col-xs-4">
						:<span class="margin-left-20">${command.marriageDTO.departmentName}</span>
					</div>
				</div>
				
				<div class="row margin-bottom-10">
					<div class="col-xs-2">
						<span class="text-bold"><spring:message
								code="mrm.acknowledgement.appDate" /></span>
					</div>
					<div class="col-xs-4">
						:<span class="margin-left-20">${command.marriageDTO.appDate}</span>
					</div>
					<div class="col-xs-2">
						<span class="text-bold"><spring:message
								code="mrm.acknowledgement.appTime" /></span>
					</div>
					<div class="col-xs-4">
						:<span class="margin-left-20">${command.marriageDTO.appTime}</span>
					</div>
				</div>

				<div class="row margin-bottom-10">
					<div class="col-xs-2">
						<span class="text-bold"><spring:message
								code="mrm.acknowledgement.dueDate" /></span>
					</div>
					<div class="col-xs-4">
						:<span class="margin-left-20">${command.marriageDTO.dueDate}</span>
					</div>
					<div class="col-xs-2">
						<span class="text-bold"><spring:message
								code="mrm.acknowledgement.dueTime" /></span>
					</div>
					<div class="col-xs-4">
						:<span class="margin-left-20">${command.marriageDTO.dueTime}</span>
					</div>
				</div>
				
				<div class="row">
					<div class="col-xs-2">
						<span class="text-bold"><spring:message
								code="mrm.acknowledgement.helpline" /></span>
					</div>
					<div class="col-xs-4">
						:<span class="margin-left-20">${command.marriageDTO.helpLine}</span>
					</div>
				</div>
				
			</form>
		</div>
	</div>
	<!-- End of info box -->
</div>
