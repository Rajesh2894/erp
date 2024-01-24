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
<script type="text/javascript" src="js/trade_license/hawkerLicenseApproval.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>



<script>
	$(function() {

		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
		/* maxDate : '0' */
		});
	});
</script>

<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="" text="Hawker License "></spring:message></b>
				</h2>

				<apptags:helpDoc url="LicenseGeneration.html"></apptags:helpDoc>
			</div>

			<div class="widget-content padding">

				<form:form method="POST" action="HawkerLicenseGeneration.html"
					class="form-horizontal" id="LicenseGenerationForm"
					name="LicenseGenerationForm">
					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv"></div>
							<form:hidden path="" id="removedIds" />
							
					</div>

					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse">
						
						
						
					<%-- 	
						<div class="panel panel-default">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse" href="#a2"> <spring:message
											code="" text="Hawker License Generation" /></a>
								</h4>
								<div id="a2" class="panel-collapse collapse in">
									<div class="panel-body">
							
									</div>
								</div>
							</div> --%>
						
						
						<div class="padding-top-10 text-center">
						
								<button type="button" class="btn btn-success" id="submitForm"
								onclick="generateLicenseNumber(this);">
								<spring:message code="trade.generatelicense" />
								</button>
								
								<button type="button" class="btn btn-danger" id="back"
								onclick="backPage()">
								<spring:message code="trade.back"></spring:message>
								</button>
								
							
							</div>
			</div>
				
		</form:form>
		</div>
	 </div>
	</div>
</div>

					