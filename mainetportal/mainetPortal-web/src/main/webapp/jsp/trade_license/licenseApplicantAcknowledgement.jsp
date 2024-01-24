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
<jsp:useBean id="marathiConvert" class="com.abm.mainet.common.util.Utility"></jsp:useBean>
<style>
.table{
  margin-left: 1.5rem !important;
}
.cert-outer-div {
	border: 3px solid #000000;
}

.license-acknowledgement .border-bottom-black {
	border-bottom: 3px solid #000000;
}
.overflow-hidden {
	overflow: hidden;
}
.note{
margin-left: 1rem !important;
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
	<style>
		.text-bold{
			font-weight:bold;
		}
	</style>
		<div class="widget-content padding">
			<form action="" method="get" class="license-acknowledgement">
				<div class="cert-outer-div">
					<div class="border-bottom-black margin-0">
					<div class="overflow-hidden padding-vertical-10">
						<div class="col-xs-2">
							<c:choose>
								<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
									<img src="${userSession.orgLogoPath}"
										alt="${userSession.organisation.ONlsOrgname}"
										class="logo-left img-responsive" />
								</c:when>
								<c:otherwise>
									<img src="${userSession.orgLogoPath}"
										alt="${userSession.organisation.ONlsOrgnameMar}"
										class="logo-left img-responsive" />
								</c:otherwise>
							</c:choose>
						</div>

						<div class="col-xs-8 text-center">
							<h2 class="margin-bottom-10 padding-bottom-10">${userSession.organisation.ONlsOrgnameMar}</h2>
						</div>
						</div>
						<div class="col-xs-12 col-sm-12 text-center margin-bottom-10">
							<h3 class="margin-bottom-0 margin-top-0 padding-top-30">
								<span class="text-bold"> <spring:message
										code="trd.acknowledgement.applNo" /></span> <span
									class="text-bold margin-left-40"> <c:choose>
										<c:when test="${userSession.languageId eq 1}">
										${command.applicationId}
									</c:when>
										<c:otherwise>
										${marathiConvert.convertToRegional("marathi",command.applicationId)}
									</c:otherwise>
									</c:choose></span>
							</h3>
						</div>

					</div>

					<br></br>
					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2 padding-top-5">
							<span class="text-bold"><spring:message
									code="trd.acknowledgement.applicantName" /></span>
						</div>
						<div class="col-xs-9 col-sm-10">
							:<span class="margin-left-20">${command.applicantName}</span>
						</div>
					</div>

					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2 padding-top-5">
							<span class="text-bold"><spring:message
									code="trd.acknowledgement.serviceName" /></span>
						</div>
						<div class="col-xs-9 col-sm-10">
							:<span class="margin-left-20">${command.serviceName}</span>
						</div>
					</div>


					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2 padding-top-5">
							<span class="text-bold"><spring:message
									code="trd.acknowledgement.deptName" /></span>
						</div>
						<div class="col-xs-9 col-sm-10">
							:<span class="margin-left-20">${command.departmentName}</span>
						</div>
					</div>

					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2 padding-top-5">
							<span class="text-bold"><spring:message
									code="trd.acknowledgement.appDate" /></span>
						</div>
						<div class="col-xs-2 col-sm-2">
							:<span class="margin-left-20"><fmt:formatDate
									pattern="dd/MM/yyyy" value="${command.appDate}" /></span>
						</div>

						<div class="col-xs-2 col-sm-2 text-right ">
							<span class="text-bold"><spring:message
									code="trd.acknowledgement.appTime" /></span>
						</div>
						<div class="col-xs-2 col-sm-2">
							:<span class="margin-left-20">${command.appTime}</span>
						</div>
					</div>

					<div
						class="row padding-top-5 margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2">
							<span class="text-bold"><spring:message
									code="trd.acknowledgement.dueDate" /></span>
						</div>
						<div class="col-xs-2 col-sm-2">
							:<span class="margin-left-20"><fmt:formatDate
									pattern="dd/MM/yyyy" value="${command.dueDate}" /></span>

						</div>

					</div>

					<c:if test="${not empty  command.checkList}">
						<div class="padding-horizontal-10">
							<h5 class="note">
								<strong class="margin-left-10"><spring:message
											code="trd.acknowledgement.doc.note" /></strong>
							</h5>
							<table class="table table-striped table-condensed table-bordered"
								style="width: 60%;">
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
						<div class="col-xs-12 col-sm-12 padding-top-5">
							<span class="text-bold line-height-1rem"><spring:message
									code="trd.rec.acknowledgement.comp.generate" text="computer generated" /></span>
						</div>
					</div>
					
				</div>

				<div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i>
						<spring:message code="trade.print" text="Print" />
					</button>

					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.close();">
						<spring:message code="trd.acknowledgement.close" text="Close"></spring:message>
					</button>
				</div>
			</form>
		</div>
	</div>



</div>