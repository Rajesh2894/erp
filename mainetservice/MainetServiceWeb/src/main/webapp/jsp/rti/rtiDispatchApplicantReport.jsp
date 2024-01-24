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
<script type="text/javascript" src="js/rti/rtiDispatch.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>

<style>
.revenue p {
	font-size: 1em;
}

.logo-right {
	width: 45%;
	float: right;
}

p.label-txt::after {
	content: ':';
}
</style>

<script type="text/javascript">
	function printdiv(printpage) {

		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
</script>



<!-- Start Content here -->
<div class="content animated slideInDown">
	<div class="widget invoice revenue" id="receipt">
		<div class="widget-content padding">
			<form:form action="RtiDispatch.html" class="form-horizontal"
				name="dispatchApplicantReport" id="dispatchApplicantReport">
				<%-- <form:hidden path="" id="viewMode" value="${command.viewMode}" /> --%>

				<div class="row">
					<div class="col-xs-3 col-sm-3">
						<c:choose>
							<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
								<img src="${userSession.orgLogoPath}"
									alt="${userSession.organisation.ONlsOrgname}" width="80"
									class="img-responsive" />
							</c:when>
							<c:otherwise>
								<img src="${userSession.orgLogoPath}"
									alt="${userSession.organisation.oNlsOrgnameMar}"
									class="logo-left img-responsive" />
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-xs-6 col-sm-6 text-center">
						<h3 class="text-bold text-center margin-top-20">
							<spring:message code="report.content1" text="" />
						</h3>
					</div>
					<div class="col-xs-3 col-sm-3">
						<img src="./assets/img/RTI-Logo.jpg" alt="RTI"
							class="logo-right img-responsive" />
					</div>
				</div>
				<div class="row margin-top-20 clear">
					<div class="col-sm-2 col-xs-2">
						<p class="label-txt">
							<spring:message code="report.content32" text="From" />
						</p>
					</div>
					<div class="col-sm-3 col-xs-3 text-left">
						<p>
							<b>${command.reqDTO.dispatchNo}</b>
						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p class="label-txt">
							<spring:message code="report.content33" text="From" />
						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<b>${command.reqDTO.rtiDeciDet}</b>
						</p>
					</div>
				</div>

				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="report.content2" text="From" />
						</p>
					</div>
					<div class="col-sm-4 col-xs-4 text-left">
						<p>
							<b>${command.pioName}</b>
						</p>
					</div>
				</div>

				<div class="row margin-top-10 clear">
					<div class="col-sm-12 col-xs-12">
						<p>
							<spring:message code="report.content3"
								text="The Public Information Officer" />
						</p>
					</div>
				</div>
				<div class="row clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="report.content4" text="(Department/office)" />
							:
						</p>
					</div>

					<div class="col-sm-2 col-xs-3 text-left">
						<p>
							<b>${command.reqDTO.departmentName}</b>
						</p>
					</div>
				</div>
				<div class="row  clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="report.content24" text="No." />
						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<b>${command.reqDTO.rtiNo}</b>
						</p>
					</div>

					<div class="col-sm-1 col-xs-1 text-left">
						<p>
							<spring:message code="rti.date" text="Date. " />
						</p>
					</div>
					<div class="margin-left-20">
						<p>
							<b>${command.reqDTO.dateDesc}</b>
						</p>
					</div>
				</div>

				<div class="row margin-top-20 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="report.content6" text="To" />
							:
						</p>
						<br>
					</div>
				</div>

				<div class="row clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="report.content7" text="Shri/Smt./Kum." />
						</p>
					</div>
					<div class="col-sm-10 col-xs-10 text-left">
						<p>
							<b>${command.reqDTO.applicantName}</b>
						</p>
					</div>
				</div>
				<div class="row clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="rti.address" text="Address:" />
						</p>
					</div>
					<div class="col-sm-10 col-xs-10 text-left">
						<p>
							<b>${command.cfcAddressEntity.apaAreanm}</b>
						</p>
					</div>
				</div>
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="report.content8" text="Sir," />
						</p>
					</div>
				</div>
				<c:set var="index" value="0" scope="page" />
				<div class="row margin-top-30 clear">
					<div class=" col-sm-12">
						<p>
							<span class="margin-left-20"> <spring:message
									code="report.content9" /></span> <b>${command.reqDTO.dateDesc}</b>
							<spring:message code="report.content25" />
							<b>${command.reqDTO.rtiNo}</b>)
							<spring:message code="report.content11" />
							<br> <br>
							<c:set var="index" value="${index+1}" />
							<c:out value="${index}" />
							.
							<spring:message code="report.content26" />
							<br>
							<c:set var="index" value="${index+1}" />
							<c:out value="${index}" />
							.
							<spring:message code="report.content27" />
						</p>
					</div>
				</div>
				<c:if test="${not empty command.rtiMediaListDTO}">
					<div class="col-sm-10 col-sm-offset-1">

						<c:set var="d" value="0" scope="page"></c:set>
						<table
							class="table table-bordered  table-condensed margin-bottom-20 margin-top-20"
							id="itemDetails">
							<thead>
								<tr>
									<th><spring:message code="rti.chargemediaType"></spring:message></th>
									<th><spring:message code="rti.quantity"></spring:message></th>
									<th><spring:message code="rti.desc"></spring:message></th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${command.rtiMediaListDTO}"
									var="rtiMediaListDTO" varStatus="status">
									<tr>
										<%-- <td>${status.count}</td> --%>
										<td>${rtiMediaListDTO.mediaTypeDesc}</td>
										<td>${rtiMediaListDTO.quantity}</td>
										<td>${rtiMediaListDTO.mediaDesc}</td>

									</tr>
								</c:forEach>
							</tbody>
						</table>

					</div>
					<br>
				</c:if>
				
				<c:if test="${not empty command.apprejMasList}">
					<div class="row margin-top-30 clear">
						<div class=" col-sm-12">
							<p>
								<c:set var="index" value="${index+1}" />
								<c:out value="${index}" />
								.
								<spring:message code="report.content28" />
							</p>
						</div>
					</div>

					<div class="col-sm-8 col-sm-offset-2">
						<c:set var="d" value="0" scope="page"></c:set>
						<table
							class="table table-bordered  table-condensed margin-bottom-20 margin-top-20"
							id="itemDetails">
							<thead>
								<tr>
									<th><spring:message code="rti.reasons"></spring:message></th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${command.apprejMasList}" var="approveRejList"
									varStatus="status">
									<tr>
										<%-- <td>${status.count}</td> --%>
										<td>${approveRejList.artRemarks}</td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>

				<div class="row margin-top-20 clear">
					<div class=" col-sm-12">
						<p>
							<c:set var="index" value="${index+1}" />
							<c:out value="${index}" />
							.
							<spring:message code="report.content29" />
							<spring:message code="report.content30" />
							<br>
							<c:set var="index" value="${index+1}" />
							<c:out value="${index}" />
							.
							<spring:message code="report.content31" />

						</p>
					</div>

				</div>

				<br>
				<div class="clear"></div>

				<div class="clear row">
					<div
						class="col-xs-4 col-xs-offset-8 col-sm-4 col-lg-3 col-sm-offset-8 col-lg-offset-9">
						<spring:message code="rti.information.report.content25"
							text="Yours faithfully," />


						<div class="margin-bottom-10">
							<p>
								<b>${command.pioName}</b>
							</p>
							<p>
								<spring:message code="rti.information.report.content27"
									text="(Public Information Officer)" />
							</p>
						</div>
						<%-- <div class="margin-bottom-10">
							<p>
								<b>${command.reqDTO.departmentName}</b>
							</p>
							<p>
								<spring:message code="rti.information.report.content28"
									text="(Name of Department/Office)" />
							</p>
						</div> --%>
						<%-- 	<div class="margin-bottom-10">
							<spring:message code="rti.information.report.content29"
								text="Telephone No:" />
							<b>${command.pioNumber}</b>
						</div> --%>
						<%-- <div class="margin-bottom-10">
							<spring:message code="rti.information.report.content30"
								text="e-mail:" />

						</div>
						<div>
							<spring:message code="rti.information.report.content31"
								text="Website:" />
							<b>${command}</b>
						</div> --%>
					</div>
				</div>

				<div class="text-center padding-20">
					<button onclick="printdiv('receipt')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="rti.RePrint.print" text="Print" />
					</button>
					<button onClick="window.close();" type="button"
						class="btn btn-blue-2 hidden-print">Close</button>
				</div>
			</form:form>
		</div>
	</div>
</div>