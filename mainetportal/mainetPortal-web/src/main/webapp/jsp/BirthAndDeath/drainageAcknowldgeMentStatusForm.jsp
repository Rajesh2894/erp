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
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
</script>
<style>
h3,h4 {text-align: center;
color: #274472;}
</style>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Your awesome content goes here -->
	<div class="widget" id="receipt">
		<div class="widget-content padding">
			<form action="" method="get">

				<h3>${userSession.organisation.ONlsOrgname}</h3>
				<br>
				<h4>${command.serviceName}&nbsp;
				<spring:message code="applicant.acknowledgement.title" text="Acknowledgement" /></h4>

				<br></br>
				<div>
					<p>
						&nbsp;
						<spring:message code="rts.para1"  text="Received the application number"/>
						&nbsp;<span class="text-bold">${command.apmApplicationId}</span>&nbsp;
						<spring:message code="rts.para2"  text="for"/>
						&nbsp; <span class="text-bold">${command.formName}</span>&nbsp;
						<spring:message code="rts.para3"  text="from Sri/smt/M/s" />
						&nbsp; <span class="text-bold">${command.applicantDetailDto.applicantFirstName}</span>&nbsp;
						<spring:message code="rts.para4"  text="on"/>
						&nbsp; <span class="text-bold"><fmt:formatDate
								value="<%=new java.util.Date()%>" pattern="dd/MM/yyyy" /></span>&nbsp;
						<spring:message code="rts.para5" text="Time" />
						&nbsp; <span class="text-bold"><fmt:formatDate
								value="<%=new java.util.Date()%>" pattern="hh:mm a" /></span>&nbsp;
						<spring:message code="rts.para6"  text="along with the following enclosures."/>
						&nbsp;
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

											<c:set value="N" var="flag" scope="page"></c:set>
											<c:forEach items="${command.documentList}" var="documentlist"
												varStatus="lk">
												<c:if
													test="${checklist.documentSerialNo eq documentlist.documentSerialNo}">
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
				<div class="col-xs-6 margin-top-10 text-left">
					<span>${userSession.organisation.ONlsOrgname}</span>
				</div>
				<div class="col-xs-6 margin-top-10 text-right">
					<span>${userSession.employee.designation.dsgname}</span>
				</div>
				<div class="clear"></div>
				<div class="col-xs-6 margin-top-10 text-left">
					<span>
						Date :
						<fmt:formatDate value="<%=new java.util.Date()%>"
							pattern="dd/MM/yyyy" />
					</span>
				</div>
				<div class="col-xs-6 margin-top-10 text-right">
					<span>${userSession.organisation.orgShortNm}</span>
				</div>
				<div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i> Print
					</button>
					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.location.href='CitizenHome.html'">
						<spring:message code="care.receipt.back" text="Back" />
					</button>
					
				</div>
			</form>
		</div>
	</div>
	<!-- End of info box -->
</div>

