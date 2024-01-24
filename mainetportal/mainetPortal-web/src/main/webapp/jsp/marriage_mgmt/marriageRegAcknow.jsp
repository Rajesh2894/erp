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
<%-- <script type="text/javascript" src="js/mainet/validation.js"></script> --%>
<jsp:useBean id="marathiConvert"
	class="com.abm.mainet.common.util.Utility"></jsp:useBean>

<style>
	h2,h3,h4,h5 {
		color: #000000;
	}
	h5 {
		font-size: 0.9rem;
	}
	.cert-outer-div {
		border: 3px solid #000000;
	}
	.mrg-acknowledgement .border-bottom-black { 
	     border-bottom: 3px solid #000000; 
	}
	.padding-horizontal-10 {
		padding: 0px 10px !important;
	}
	.margin-0 {
		margin: 0px !important;
	}
</style>

<script>
	function printContent(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}
</script>

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Your awesome content goes here -->
	<div class="widget" id="receipt">
		<div class="widget-content padding">
			<form action="" method="get" class="mrg-acknowledgement">
				<div class="cert-outer-div">
					<div class="row margin-0">
						<%-- <div class="col-xs-3">
							<img width="80" src="${userSession.orgLogoPath}">
						</div> --%>
						<div class="border-bottom-black" style="margin: 10px 0px;">
							<div class="col-xs-2">
								<img alt="Organization Logo" width="80" src="${userSession.orgLogoPath}">
							</div>
							<div class="col-xs-8 text-center">
								<h2 class="margin-bottom-10 padding-bottom-10">${userSession.organisation.ONlsOrgnameMar}</h2>
							</div>
							<div class="col-xs-2"></div>
							<div style="clear:both"></div>
						</div>
	
						<div class="col-xs-12 col-sm-12 text-center">
							<h4 class="margin-top-10">
								<%-- ${command.serviceName} --%>
								<%-- <spring:message code="applicant.acknowledgement.title" /> --%>
								<span class="text-bold"><spring:message
										code="mrm.rec.acknowledgement.applNo" text="appNo"/></span> <span
									class="text-bold margin-left-40">
									<!--D#129167  -->
									${command.marriageDTO.applicationId}		 
									<%-- <c:choose>
										<c:when test="${userSession.languageId eq 1}">
												${command.marriageDTO.applicationId}
											</c:when>
										<c:otherwise>
												${marathiConvert.convertToRegional("marathi",command.marriageDTO.applicationId)}
											</c:otherwise>
									</c:choose> --%>
								</span>
							</h4>
						</div>
					</div>

					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2">
							<span class="text-bold"><spring:message
									code="mrm.rec.acknowledgement.applicantName" text="app name"/></span>
						</div>
						<div class="col-xs-9 col-sm-10">
							:<span class="margin-left-20">${command.marriageDTO.applicantName}</span>
						</div>
					</div>
	
					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2">
							<span class="text-bold"><spring:message
									code="mrm.rec.acknowledgement.serviceName" text="service name"/></span>
						</div>
						<div class="col-xs-9 col-sm-10">
							:<span class="margin-left-20">${command.marriageDTO.serviceShortCodeMar}</span>
						</div>
					</div>

					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2">
							<span class="text-bold"><spring:message
									code="mrm.rec.acknowledgement.deptName" text="dept name " /></span>
						</div>
						<div class="col-xs-9 col-sm-10">
							:<span class="margin-left-20">${command.marriageDTO.departmentNameMar}</span>
						</div>
					</div>

					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2">
							<span class="text-bold"><spring:message
									code="mrm.rec.acknowledgement.appDate"  text="app Date"/></span>
						</div>
						<div class="col-xs-2 col-sm-2">
							:<span class="margin-left-20"><fmt:formatDate
									pattern="dd/MM/yyyy" value="${command.marriageDTO.appDate}" /></span>
						</div>
						<div class="col-xs-2 col-sm-2 text-right">
							<span class="text-bold"><spring:message
									code="mrm.rec.acknowledgement.appTime" text="app time "/></span>
						</div>
						<div class="col-xs-2 col-sm-2">
							:<span class="margin-left-20">${command.marriageDTO.appTime}</span>
						</div>
					</div>
				
					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2">
							<span class="text-bold"><spring:message
									code="mrm.rec.acknowledgement.dueDate" text="due date"/></span>
						</div>
						<div class="col-xs-2 col-sm-2">
							:<span class="margin-left-20"><fmt:formatDate pattern="dd/MM/yyyy" value="${command.marriageDTO.dueDate}" /></span>
							
						</div>
						<%-- <div class="col-xs-2">
							<span class="text-bold"><spring:message
									code="mrm.rec.acknowledgement.dueTime" /></span>
						</div>
						<div class="col-xs-4">
							:<span class="margin-left-20">${command.marriageDTO.dueTime}</span>
						</div> --%>
					</div>

					<c:if test="${not empty  command.checkList}">
						<div class="padding-horizontal-10">
							<h4 class="margin-bottom-10"><strong><i><spring:message code="mrm.rec.acknowledgement.doc.note" /></i></strong></h4>
							<table class="table table-striped table-condensed table-bordered">
								<tbody>
									<c:forEach items="${command.checkList}" var="checklist"
										varStatus="lk">
										<tr>
											<td>${checklist.documentSerialNo}</td>
											<c:choose>
												<c:when
													test="${userSession.getCurrent().getLanguageId() eq 1}">
													<td>${checklist.doc_DESC_ENGL}</td>
												</c:when>
												<c:otherwise>
													<td>${checklist.doc_DESC_Mar}</td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
					<br>
					
					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-12 col-sm-12">
							<span class="text-bold line-height-1rem"><spring:message
									code="mrm.rec.acknowledgement.comp.generate" text="computer generated" /></span>
						</div>
					</div>
				</div>

				<div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i>
						<spring:message code="mrm.rec.acknowledgement.print" text="Print"></spring:message>
					</button>
					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.close();">
						<spring:message code="mrm.rec.acknowledgement.close" text="Close"></spring:message>
					</button>
				</div>
			</form>
		</div>
	</div>
	<!-- End of info box -->
</div>
