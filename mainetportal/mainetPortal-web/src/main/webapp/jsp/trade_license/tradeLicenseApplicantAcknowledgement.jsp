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
<style>
h2, h3, h4, h5 {
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
.license-ack .border-bottom-black { 
     border-bottom: 3px solid #000000;
}
.overflow-hidden {
	overflow: hidden;
}

</style>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Your awesome content goes here -->
	<div class="widget" id="receipt">
		<div class="widget-content padding">
			<form action="" method="get" class="license-ack">
				<div class="cert-outer-div">
					<div class="border-bottom-black margin-0">
					<div class="overflow-hidden padding-vertical-10">
						<div class="col-xs-1 col-sm-1">
							<c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
									<c:set var="parts" value="${fn:split(logo, '*')}" />
										<c:if test="${parts[1] eq '1'}">
											<img width="80" src="${parts[0]}" class="logo-div"
											alt="Organisation Logo"
											<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
											<c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if> Logo
											">
										</c:if>
							</c:forEach>
						</div>
						<div class="col-xs-10 col-sm-10 text-center">
							<c:choose>
								<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
									<h2 class="margin-bottom-10 padding-bottom-10">${userSession.organisation.ONlsOrgname}</h2>
								</c:when>
								<c:otherwise>
									<h2 class="margin-bottom-10 padding-bottom-10">${userSession.organisation.ONlsOrgnameMar}</h2>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="col-xs-1 col-sm-1"></div>
					</div>	
					

					<div class="col-xs-12 col-sm-12 text-center">
						<h4 class="margin-bottom-0 margin-top-0 padding-top-20">
							<c:choose>
								<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
									${command.serviceMaster.serviceName}
								</c:when>
								<c:otherwise>
								${command.serviceMaster.serviceNameReg}
								</c:otherwise>
							</c:choose>
							&nbsp;<spring:message code="trade.acknowledgement.title" text="Acknowledgement " />
						</h4>
					</div>
					</div>
				<br></br>
				<div class="padding">
					<p>
						&nbsp;<spring:message code="trade.acknowledgement.para1" text="Received the application number " />&nbsp;
						<span class="text-bold">${command.tradeMasterDetailDTO.apmApplicationId}</span>



						<spring:message code="tradeApplicant.acknowledgement.para2"
							text="for New License Registration Service from Sri/smt/M/s " />&nbsp;


						<span class="text-bold">${command.ownerName}</span>
						<spring:message code="trade.acknowledgement.para3" text="on " />&nbsp;
						<span class="text-bold"><fmt:formatDate
								value="<%=new java.util.Date()%>" pattern="dd/MM/yyyy" /></span>&nbsp;
						<spring:message code="trade.acknowledgement.para5" text="time " />&nbsp;
						<span class="text-bold"><fmt:formatDate
								value="<%=new java.util.Date()%>" pattern="hh:mm a" /></span>&nbsp;
						<spring:message code="trade.acknowledgement.para4" text="along with the following enclosures. " />

					</p>
				</div>

				<c:if test="${not empty  command.checkList}">
					<div class="table-responsive">
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

											<%-- <c:set value="N" var="flag" scope="page"></c:set>
											<c:forEach items="${command.documentList}" var="documentlist"
												varStatus="lk">
												<c:if test="${not empty checklist.documentByteCode}">
													<c:set value="Y" var="flag" scope="page"></c:set>
												</c:if>

											</c:forEach> --%>

											<c:choose>
												<c:when test="${not empty checklist.documentByteCode}">
													<td>Yes</td>
												</c:when>
												<c:otherwise>
													<td>No</td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</thead>
							</table>

						</div>
					</div>
				</c:if>
				<br>
				<c:choose>
					<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
						<p class="padding">${userSession.organisation.ONlsOrgname}</p>
					</c:when>
					<c:otherwise>
						<p class="padding">${userSession.organisation.ONlsOrgnameMar}</p>
					</c:otherwise>
				</c:choose>

				<%-- <div class="col-xs-6 margin-top-10">
					<p align="right">${userSession.employee.designation.dsgname}</p>
				</div> --%>
				<br>
				<p class="padding">
					<spring:message code="trade.acknowledgement.para6" text="Date :" />&nbsp;
					<fmt:formatDate value="<%=new java.util.Date()%>"
						pattern="dd/MM/yyyy" />
				</p>
				<%-- <div class="col-xs-6 margin-top-10">
					<p align="right">${userSession.organisation.orgShortNm}</p>
				</div> --%>
				</div>
				<div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i>
						<spring:message code="trade.print" text="Print" />
					</button>



					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.close();">

						<spring:message code="trade.close" text="Close"></spring:message>
					</button>
				</div>
			</form>
		</div>
		<style>
			.padding{
				padding: 1.5rem;
				}
			.logo-div{
				height: 80px;
				min-width: 90px;
				}	
		</style>
	</div>
	<!-- End of info box -->
</div>
