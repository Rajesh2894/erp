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

<script>
	function printContent(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}
</script>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Your awesome content goes here -->
	<div class="widget" id="receipt">
		<div class="widget-content padding">
			<form action="" method="get">
				<div class="row">
					<div class="col-xs-12 text-center">
						<c:choose>
							<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
								<h3 class="margin-bottom-0">${userSession.organisation.ONlsOrgname}</h3>
							</c:when>
							<c:otherwise>
								<h3 class="margin-bottom-0">${userSession.organisation.oNlsOrgnameMar}<h3>
							</c:otherwise>
						</c:choose>
					</div>


					<div class="col-xs-12 col-sm-12 text-center">
						<h4 class="margin-bottom-0 margin-top-0 padding-top-30">
							<c:choose>
								<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
									${command.serviceName}
								</c:when>
								<c:otherwise>
									${command.serviceMaster.smServiceNameMar}
								</c:otherwise>
							</c:choose>
							<spring:message code="applicant.acknowledgement.title" />
						</h4>
					</div>
				</div>

				<br></br>
				<div>
					<p>
						<spring:message code="applicant.acknowledgement.para1" />
						<span class="text-bold">${command.applicationId}</span>
						

						<c:choose>
							<c:when test="${command.serviceMaster.smShortdesc =='RTL' || command.serviceMaster.smShortdesc=='TLA'}">
								<c:if test="${command.serviceMaster.smShortdesc =='RTL'}">
									<spring:message
										code="tradeApplicant.acknowledgement.rtl.para2.RTL"
										text="Registration Service from Sri/smt/M/s" />
								</c:if>
								<c:if test="${command.serviceMaster.smShortdesc =='TLA'}">
									<spring:message
										code="tradeApplicant.acknowledgement.rtl.para2.TLA"
										text="Registration Service from Sri/smt/M/s" />
								</c:if>
							</c:when>
							<c:otherwise>
								<spring:message code="tradeApplicant.acknowledgement.para2" />
							</c:otherwise>
						</c:choose>

						<span class="text-bold">${command.applicantName}</span>
						<spring:message code="applicant.acknowledgement.para3" />
						<span class="text-bold"><fmt:formatDate
								value="<%=new java.util.Date()%>" pattern="dd/MM/yyyy" /></span>
						<spring:message code="applicant.acknowledgement.para5" />
						<span class="text-bold"><fmt:formatDate
								value="<%=new java.util.Date()%>" pattern="hh:mm a" /></span>
						<spring:message code="applicant.acknowledgement.para4" />

					</p>
				</div>

				<c:if test="${not empty  command.checkList}">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="advertiserTable">
							<thead>

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

										<c:set value="N" var="flag" scope="page"></c:set>
										<c:forEach items="${command.documentList}" var="documentlist"
											varStatus="lk">
											<c:if test="${checklist.documentId eq documentlist.clmId}">
												<c:set value="Y" var="flag" scope="page"></c:set>
											</c:if>

										</c:forEach>

										<c:choose>
											<c:when test="${flag eq 'Y'}">
												<td><spring:message code="license.yes" text="Yes"  /></td>
											</c:when>
											<c:otherwise>
												<td><spring:message code="license.No" text="No" /></td>
											</c:otherwise>
										</c:choose>
									</tr>
								</c:forEach>
							</thead>
						</table>

					</div>
				</c:if>
				<br>
				<c:choose>
					<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
						<p align="left">${userSession.organisation.ONlsOrgname}</p>
					</c:when>
					<c:otherwise>
						<p align="left">${userSession.organisation.oNlsOrgnameMar}</p>
					</c:otherwise>
				</c:choose>

				<%-- <div class="col-xs-6 margin-top-10">
					<p align="right">${userSession.employee.designation.dsgname}</p>
				</div> --%>
				<br>
				<p align="left">
					<spring:message code="trd.notice.date" text="Date" />
					<fmt:formatDate value="<%=new java.util.Date()%>"
						pattern="dd/MM/yyyy" />
				</p>
				<%-- <div class="col-xs-6 margin-top-10">
					<p align="right">${userSession.organisation.orgShortNm}</p>
				</div> --%>
				<div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i>
						<spring:message code="trade.print" text="Print" />
					</button>



					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.close();">

						<spring:message code="adh.close" text="Close"></spring:message>
					</button>
				</div>
				<%-- Defect #150948 --%>
				<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>
			</form>
		</div>
	</div>
	<!-- End of info box -->
</div>
