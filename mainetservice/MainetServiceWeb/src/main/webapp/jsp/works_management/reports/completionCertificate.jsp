<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script type="text/javascript"
	src="js/works_management/reports/completionCertificate.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<!-- Start Content here -->
	<div class="widget">
		<div class="widget-content padding">
			<form action="" method="get" class="form-horizontal">
				<div id="receipt">
					<div id="export-excel">
						<div class="row">
							<div class="col-xs-3">
								<img src="${userSession.orgLogoPath}" width="80">
							</div>
							<div class="col-xs-6 text-center">
								<h3 class="text-extra-large margin-bottom-0 margin-top-0">
									<u><b><spring:message code="completion.content12"
											text="Completion Certificate" /></b></u></br>
									
									<b><spring:message code="completion.content16"
											text="Completion Certificate of Original Works" /></b>
									<%-- <c:if test="${userSession.languageId eq 1}">
										<spring:message code=""
											text="${userSession.organisation.ONlsOrgname}" />
									</c:if>
									<c:if test="${userSession.languageId ne 1}">
										<spring:message code=""
											text="${userSession.organisation.oNlsOrgnameMar}" />
									</c:if> --%>
								</h3></br></br>
								<!-- <p class="excel-title"> -->
								<!-- <p class="text-bold"> -->
									<%-- <spring:message code="completion.content1" text="" /> --%>
							</div>

						</div>	
								<b>	<spring:message code="completion.content13" text="Name of Work :" /><span></span>&nbsp;&nbsp;${command.workName }</b></br></br>
							    <b> <spring:message code="completion.content14" text="Agency :"></spring:message>&nbsp;&nbsp;${command.vendorName }</b></br></br>
								<b>	<spring:message code="completion.content15" text="Work Order No :"></spring:message>&nbsp;&nbsp;${command.workorderNo } &nbsp;&nbsp;<spring:message code="completion.content10" text="Date"></spring:message>&nbsp;&nbsp;${command.workDate}</b></br></br>
									
								<!-- </p> -->
									
								
								<p class="text-bold">
								
									<spring:message code="completion.content7" text="" />&nbsp;${command.workDate}
									<spring:message code="completion.content8" text="" />&nbsp;${command.workDate}
									<spring:message code="completion.content9" text="" /></br></br>
									<spring:message code="completion.content10" text="" />&nbsp;${command.workDate}
									
								</p>
								<!-- </p> -->
							
						<div>


							<br>
							<%-- <div class="col-sm-12">

								<p class="text-bold">
									<spring:message code="completion.content2" text="" />
									&nbsp;&nbsp;${command.contractCompletionDto.contDate}
									<spring:message code="completion.content3" text="" />
									&nbsp;&nbsp;${command.contractCompletionDto.contractorName}
									<spring:message code="completion.content4" text="" />
									&nbsp;${command.contractCompletionDto.orgState}
									<spring:message code="completion.content5" text="" />
									&nbsp;&nbsp;${command.contractCompletionDto.completionDate }

								</p>




							</div> --%>
							<div class="clear"></div>
							<div
								class="col-sm-4 col-xs-4 col-sm-offset-8 col-xs-offset-8 margin-top-10">
								<p class="text-bold text-center">

									<spring:message code="completion.content11" text="Executive Engeneer" /><br>
									${command.orgName}<spring:message code="" text="," /></br>
									${ command.ulbName}
								</p>

							</div>

						</div>
					</div>
				</div>
				<div class="text-center hidden-print padding-10">
					<button onclick="PrintDiv('');"
						class="btn btn-primary hidden-print" type="button">
						<i class="fa fa-print padding-right-5"></i>
						<spring:message code="work.estimate.report.print" text="" />
					</button>
					<button type="button" class="btn btn-danger"
						onclick="window.location.href='CompletionCertificate.html'">
						<spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>

			</form>
		</div>
	</div>
</div>