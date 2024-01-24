<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/adh/hoardingRegister.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">

		<div class="widget-header">
			<h2 class="excel-title">
				<spring:message code="" text="Loan Register" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>

		<div class="widget-content padding">

			<form action="" method="get" class="form-horizontal">
				<div class="Lreport">

					<div class="form-group">
						<div
							class="col-xs-6 col-sm-8 col-sm-offset-2 col-xs-offset-1  text-center">
							<h3 class="text-extra-large margin-bottom-0 margin-top-0">
								${ userSession.getCurrent().organisation.ONlsOrgname} <br>
								<u><spring:message code="" text="Hording Register" /></u>
							</h3>
						</div>

						<div class="col-sm-2 col-xs-3 pull-right">
							<p>
								<strong><spring:message code="" text="Date" /></strong> :
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
							</p>
							<span class="text-bold"><spring:message
									code="" text="" /></span>
						</div>
					</div>
</div>
</form>
