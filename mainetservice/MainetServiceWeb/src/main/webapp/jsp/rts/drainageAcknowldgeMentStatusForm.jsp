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
<style>
.outer-border {
	padding: 20px;
	border: 1px solid #000000;
	margin: 20px auto;
}
.org-logo {
	width: 4rem;
}
.drainageBorderDivide{
	border :1px solid #dadada;
}
</style>
<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Your awesome content goes here -->
	<div class="widget" id="receipt">
		<div class="widget-content padding outer-border">
			<form action="" method="get">
				<div class="row">
					<div class="col-xs-2 text-center">
							<img src="${userSession.orgLogoPath}" alt="Organisation Logo" class="org-logo"/>
						</div>
					<div class="col-xs-8 text-center">
						<h3 class="margin-bottom-0"><strong>${command.orgName}</strong></h3>
					<!-- </div>


					<div class="col-xs-12 col-sm-12 text-center"> -->
						<h4 class="margin-bottom-0 margin-top-20">
							${command.formName}
							<spring:message code="applicant.acknowledgement.title" />
						</h4>
					</div>
				</div>
				<br>
				<div class="drainageBorderDivide"></div>
				</br>
				<div>
					<p class="margin-left-15">
						<spring:message code="rts.para1" />
						<%-- <span class="text-bold">${command.applicationId}</span>
						<spring:message code="rts.para2" />
						<span class="text-bold">${command.formName}</span>
						<spring:message code="rts.para3" />
						<span class="text-bold">${command.applicantName}</span>
						<spring:message code="rts.para4" />
						<span class="text-bold"><fmt:formatDate
								value="<%=new java.util.Date()%>" pattern="dd/MM/yyyy" /></span>
						<spring:message code="rts.para5" />
						<span class="text-bold"><fmt:formatDate
								value="<%=new java.util.Date()%>" pattern="hh:mm a" /></span>
						<spring:message code="rts.para6" /> --%>

					</p>
				</div>
				
				<div class="row margin-bottom-10 margin-top-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2">
							<span class="text-bold"><spring:message
									code="rts.para2" /></span>
						</div>
						<div class="col-xs-9 col-sm-10">
							:<span class="margin-left-20">${command.applicantName}</span>
						</div>
					</div>
					
					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2">
							<span class="text-bold"><spring:message
									code="rts.para3" /></span>
						</div>
						<div class="col-xs-9 col-sm-10">
							:<span class="margin-left-20">${command.applicationId}</span>
						</div>
					</div>
					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2">
							<span class="text-bold"><spring:message
									code="rts.para4" /></span>
						</div>
						<div class="col-xs-2 col-sm-2">
							:<span class="margin-left-20"><fmt:formatDate
								value="<%=new java.util.Date()%>" pattern="dd/MM/yyyy" /></span>
							
						</div>
					</div>
					<div class="row margin-bottom-10 margin-left-0 margin-right-0">
						<div class="col-xs-3 col-sm-2">
							<span class="text-bold"><spring:message
									code="rts.para5" /></span>
						</div>
						<div class="col-xs-2 col-sm-2">
							:<span class="margin-left-20"><fmt:formatDate
								value="<%=new java.util.Date()%>" pattern="hh:mm a" /></span>
							
						</div>
					</div>

				<c:if test="${not empty  command.checkList}">
					<div class="drainageTableFont">
						<div class="margin-top-10">
							<table class="table table-responsive table-striped table-condensed table-bordered"
								id="advertiserTable">
								<thead>

									<c:forEach items="${command.checkList}" var="checklist"
										varStatus="lk">

										<tr>
											<td width="10%">${checklist.documentSerialNo}</td>
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

											<%-- <c:choose>
												<c:when test="${flag eq 'Y'}">
													<td>Yes</td>
												</c:when>
												<c:otherwise>
													<td>No</td>
												</c:otherwise>
											</c:choose> --%>
										</tr>
									</c:forEach>
								</thead>
							</table>

						</div>
					</div>
				</c:if>
				<div class="col-xs-6 margin-top-10">
					<p align="left">${command.orgName}</p>
				</div>
				<div class="col-xs-6 margin-top-10">
					<p align="right">${userSession.employee.designation.dsgname}</p>
				</div>
				<br>
				<div class="margin-bottom-20">
				<div class="col-xs-6 margin-top-10">
					<p align="left">
						<spring:message code="rts.dates" />
						<fmt:formatDate value="<%=new java.util.Date()%>"
							pattern="dd/MM/yyyy" />
					</p>
				</div>
				<div class="col-xs-6 margin-top-10">
					<p align="right">${userSession.organisation.orgShortNm}</p>
				</div>
				</div>
				<div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i> <spring:message code="rts.print" />
					</button>



					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="care.receipt.back" text="Back" />
					</button>
				</div>
			</form>
		</div>
	</div>
	<!-- End of info box -->
</div>
