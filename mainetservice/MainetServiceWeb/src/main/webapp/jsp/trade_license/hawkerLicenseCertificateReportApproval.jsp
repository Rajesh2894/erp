

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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/trade_license/hawkerLicenseReportFormat.js"></script>


<style>
.widget {
	padding: 40px;
}

.widget-content {
	border: 1px solid #000;
}

#receipt img {
	width: 150px;
	height: 110px;
}
/* hr{
	border-top: 1px solid #000;
} */
.invoice .widget-content.padding {
	padding: 20px;
}

.border-black {
	border: 1px solid #000;
	padding: 10px;
	min-height: 130px;
}

.padding-left-50 {
	padding-left: 50px !important;
}

.no-arrow {
	-webkit-appearance: none;
}

.no-arrow::-ms-expand {
	display: none;
}

table, td, th {
	border: 1px solid #595959;
	border-collapse: collapse;
}

td, th {
	padding: 3px;
	width: 600px;
	height: 25px;
}

th {
	background: #f0e6cc;
}

.even {
	background: #fbf8f0;
}

.odd {
	background: #fefcf9;
}
</style>



<!-- Start Content here -->
<div class="content animated slideInDown">
	<div class="widget invoice" id="receipt">
		<div class="widget-content padding">
			<form:form action="HawkerLicenseGeneration.html"
				class="form-horizontal" name="hawkerLicenseReportPrinting"
				id="hawkerLicenseReportPrinting">
				<c:if test="${not empty command.imagePath}">
					<form:hidden path="" id="imgMode" value="${command.imagePath}" />
				</c:if>
				<div class="row">

					<div class="col-xs-3 col-sm-3">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>

					<div class="col-xs-6 col-sm-6 text-center">
						<h3 class="margin-bottom-0">
							<spring:message code=""
								text="Ministry of Housing and Urban Affairs Government of India" />
						</h3>
						<h3 class="text-bold text-center margin-top-20">
							<spring:message code=""
								text="${userSession.organisation.ONlsOrgname}" />

						</h3>

						<h5 class="text-center margin-top-20">
							<b><spring:message code=""
									text="Provisional Certificate of Vending" /></b>
						</h5>
						<h5 class="text-center margin-top-20">
							<spring:message code=""
								text="(Issued for availing benefits under PM SVANidhi Scheme)" />
						</h5>
						<h5 class="text-center margin-top-20">
							Certificate/Registration No:<b><spring:message code=""
									text="${command.tradeDetailDTO.trdLicno}" /></b>
						</h5>

					</div>
					<%-- <div class="col-sm-2 col-sm-offset-1 col-xs-2">
						<p>
							<spring:message code="trade.date" text="Date" />
							<span class="text-bold">${command.dateDesc}</span>
						</p>


					</div> --%>

				</div>



				<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">


					<div class="row margin-top-10 clear">
						<div class="col-sm-4  col-md-4 col-xs-4">
							<p>
								<spring:message code="" text="Vendor Name" />
							</p>
						</div>
						<div class="col-sm-8 col-xs-8 text-left ">
							<p>
								<b>${command.tradeDetailDTO.tradeLicenseOwnerdetailDTO[0].troName}</b>
							</p>

						</div>
					</div>

					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-md-4 col-xs-4">
							<p>
								<spring:message code="" text="Gender" />
							</p>
						</div>
						<div class="col-sm-8 col-md-8 col-xs-8 text-left ">
							<c:set var="baseCode" value="GEN" scope="page" />
							<c:set var="ownList"
								value="${command.tradeDetailDTO.tradeLicenseOwnerdetailDTO}"
								scope="page" />

							<c:if test="${ownList[0].troGen  eq 'M'}">
								<b>Male</b>
							</c:if>
							<%-- <spring:eval
								expression="T(com.abm.mainet.common.utility.CommonMasterUtility).findLookUpCodeDesc(${baseCode},${command.tradeDetailDTO.orgid},${ownList[0].troGen})"
								var="lookup" />

							<p>
								<b>${lookup.lookUpDesc }</b>
							</p> --%>
							<c:if test="${ownList[0].troGen  eq 'F'}">
								<b>Female</b>
							</c:if>
						</div>
					</div>


					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-md-4 col-xs-4">
							<p>
								<spring:message code="" text="Vending Place" />
							</p>
						</div>
						<div class="col-sm-8 col-md-8 col-xs-8 text-left ">
							<p>
								<b>${command.tradeDetailDTO.trdBusadd}</b>
							</p>
						</div>
					</div>
					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-md-4 col-xs-4">
							<p>
								<spring:message code="" text="Name Of Vending Activity" />

							</p>
						</div>
						<div class="col-sm-8 col-md-8 col-xs-8 text-left">
							<p>
								<b>${command.tradeDetailDTO.trdBusnm}</b>
							</p>
						</div>
					</div>
					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-md-4 col-xs-4">
							<p>
								<spring:message code="" text="Date" />
							</p>
						</div>
						<div class="col-sm-8 col-md-8 col-xs-8 text-left ">
							<p>
								<b>${command.dateDesc}</b>
							</p>
						</div>
					</div>

				</div>
				<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
					<div class="border-black"
						style="width: 170px; height: 100px; margin-top: 0px">
						<div class="" style="width: 100px; height: 100px; margin-top: 0px"
							id="propImages"></div>
					</div>
				</div>

				<br>
				<br>
				<br>
				<br>
				<br>

				<div class="row margin-top-10 clear">

					<div class="col-sm-12 col-xs-12 text-right">
						<p class="">
							<spring:message code="trade.report.authorizedSign"
								text="Authorized Signature" />
							<br> <b>${userSession.organisation.ONlsOrgname}</b>
						</p>
					</div>
					<div class="col-sm-3 col-xs-3 text-left ">
						<p>
							<%-- <b class="dateFormat">${command.workOrderDto.orderDateDesc}</b> --%>
						</p>
					</div>
				</div>

				<div class="clear"></div>


				<div class="text-center hidden-print padding-20">
					<button onclick="printdiv('receipt')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="trade.print" text="Print" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" onclick="backPage();"
						id="button-Cancel">
						<spring:message code="trade.back" text="Back" />
					</button>
				</div>


			</form:form>
		</div>
	</div>
</div>











