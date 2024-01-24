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
<script src="js/mainet/validation.js"></script>
<script src="js/eip/citizen/dashboard.js"></script>


<div class="content">
	<div class="widget">
		<div class="widget invoice" id="loiPrint">
			<div class="widget-content padding">
				<form:form action="#" method="post" class="form-horizontal"
					id="loiprintform">

					<div class="row">

						<div class="col-xs-3">
							<img alt="Organization Logo" src="${userSession.orgLogoPath}" width="80">
						</div>

						<div class="col-xs-6 text-center">
							<h2 class="margin-bottom-0">
								<c:if test="${userSession.languageId==1}">
									<spring:message code=""
										text="${userSession.organisation.ONlsOrgname}" />
								</c:if>
								<c:if test="${userSession.languageId !=1}">
									<spring:message code=""
										text="${userSession.organisation.ONlsOrgnameMar}" />
								</c:if>
							</h2>
						</div>

						
					</div>
					<div class="row">
						<div class="col-sm-12 text-center col-xs-12">
								<h2 class="margin-top-20">
									<spring:message code="loi.name" text="Letter of Intimation" />
								</h2>
						</div>
					</div>


					<div class="row margin-top-10 clear">
						<div class="col-sm-1 col-sm-offset-9 col-xs-2 col-xs-offset-8 ">
							<p>
								<spring:message code="loi.no" text="LOI No:" />
							</p>
						</div>
						<div class="col-sm-2 col-xs-2">
							<p>
								<span class="text-bold">${command.dto.loiNo}</span>
							</p>
						</div>
						<div class="col-sm-1 col-sm-offset-9 col-xs-2 col-xs-offset-8 ">
							<p>
								<spring:message code="loi.date" text="Date:" />
							</p>
						</div>
						<div class="col-sm-2 col-xs-2">
							<p>
								<span class="text-bold">${command.dto.applicationDate}</span>
							</p>
						</div>
					</div>

					<div class="row margin-top-30 clear">
						<div class="col-sm-12">
							<p>
								<spring:message code="loi.to" text="To," />
							</p>
							<p class="text-bold margin-left-30">
								<!-- ApplicantName -->
								${command.dto.applicantName}
							</p>

							<p class="text-bold margin-left-30">
								<!-- ApplicantAddress -->
								${command.dto.address}
							</p>
							<div class="col-sm-10 col-sm-offset-2">
								<p class="padding-top-20">
									<span class="text-bold"><spring:message
											code="loi.subject" text="Subject:" /></span>
									<spring:message code="loi.subject.detail" />
									<span class="text-bold">${command.dto.applicationId}</span>
								</p>
							</div>
							<p class="margin-top-20 margin-bottom-20">
								<spring:message code="loi.sirmadam" text="Sir/Madam," />
							</p>

							<p>

								<span class="margin-left-50"><spring:message
										code="loi.report1"></spring:message></span> <span class="text-bold">${command.dto.applicationId}</span>
								<spring:message code="loi.report2"></spring:message>
								<span class="text-bold">${command.dto.applicationDate}</span>
								<spring:message code="loi.report3"></spring:message>
								<span class="text-bold">${command.dto.total}</span>
								<spring:message code="loi.report4"></spring:message>
								<spring:message code="loi.report5"></spring:message>
								<span class="text-bold"><c:if
										test="${userSession.languageId==1}">
										<spring:message code=""
											text="${userSession.organisation.ONlsOrgname}" />
									</c:if> <c:if test="${userSession.languageId !=1}">
										<spring:message code=""
											text="${userSession.organisation.ONlsOrgnameMar}" />
									</c:if></span>
								<spring:message code="loi.report6"></spring:message>

							</p>
						</div>
					</div>

					<!-- <h4>LOI Fees and Charges in Details</h4> -->
					<div class="table-responsive margin-top-20">
						<table class="table table-bordered table-striped">
							<tr>
								<th scope="col" width="80"><spring:message code="loi.sr.no"
										text="Sr. No" /></th>
								<th scope="col"><spring:message code="loi.charge.name"
										text="Charge Name" /></th>
								<th scope="col"><spring:message code="loi.charge.amt"
										text="Amount" /></th>
							</tr>
							<c:forEach var="charges" items="${command.dto.getChargeDesc()}"
								varStatus="status">
								<tr>
									<td>${status.count}</td>
									<td>${charges.key}</td>
									<td><form:input path="" type="text"
											class="form-control text-right" value="${charges.value}"
											readonly="true" /></td>
								</tr>
							</c:forEach>

							<tr>
								<td colspan="2"><span class="pull-right"><b><spring:message
												code="form.loi.tot.amt" text="Total LOI Amount" /></b></span></td>
								<td><form:input path="dto.total" type="text"
										class="form-control text-right" readonly="true" /></td>
							</tr>
						</table>
					</div>

					<div
						class="text-center hidden-print padding-bottom-20 margin-top-20">
						<button onclick="printLoiContent('loiPrint')"
							class="btn btn-success">
							<i class="icon-print-2"></i>
							<spring:message code="loi.print" text="Print" />
						</button>
						<button type="button" class="btn btn-danger"
							onclick="window.location.href='CitizenHome.html'">
							<spring:message code="loi.back" text="Back" />
						</button>
					</div>


				</form:form>
			</div>
		</div>

	</div>
</div>