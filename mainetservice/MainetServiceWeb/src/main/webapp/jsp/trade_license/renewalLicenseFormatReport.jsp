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
<script type="text/javascript" src="js/trade_license/renewalLicenseReportFormat.js"></script>

<style>
.widget {
	padding: 40px;
}
.widget-content{
	border: 1px solid #000;
}
div img {
	width: 80%;
}
hr{
	border-top: 1px solid #000;
}
.invoice .widget-content.padding{
	padding: 20px;
}
.border-black{
	border: 1px solid #000;
	padding:10px;
	min-height: 180px;
    width: 75%;
}
.padding-left-50{
	padding-left: 50px !important;
}
.no-arrow{-webkit-appearance: none;}
.no-arrow::-ms-expand {
    display: none;
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content animated slideInDown">
	<div class="widget invoice" id="receipt">
		<div class="widget-content padding">
			<form:form action="RenewalLicensePrinting.html"
				class="form-horizontal" name="renewalLicenseReportPrinting"
				id="renewalLicenseReportPrinting">
				<form:hidden path="" id="viewMode" value="${command.viewMode}" />
				<form:hidden path="" id="imgMode" value="${command.imagePath}" />
				<div class="row">

					<div class="col-xs-3">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>


					<div class="col-xs-6 text-center">
						<h3 class="margin-bottom-0">
							<spring:message code=""
								text="${userSession.organisation.ONlsOrgname}" />
						</h3>
						<h3 class="text-bold text-center margin-top-20">
							<spring:message code="trade.report.content5" text="license" />
						</h3>
						<%-- <p>
							<spring:message code=""
								text="${userSession.organisation.orgAddress}" />

						</p> --%>
					</div>
					<%-- <div class="col-xs-3 text-right">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>
					 --%>

				</div>

				<hr>
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="trade.report.licenseNo" text="License Number" />

						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<b>${command.tradeDetailDTO.trdLicno}</b>
						</p>
					</div>
				</div>
				<br>
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="trade.report.issuanceDate" text="Issuance Date" />
						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left ">
						<p>
							<b>${command.issuanceDateDesc}</b>
						</p>
					</div>
				</div>

				<div class="row margin-top-30 clear">
					<div class=" col-sm-9">
						<p>
							<b>${userSession.getCurrent().organisation.oNlsOrgnameMar}</b>
							<spring:message code="trade.report.content6" />
							<%-- ${command.year} --%>
							<spring:message code="trade.report.content7" />
							<b>${command.tradeDetailDTO.tradeLicenseOwnerdetailDTO[0].troName}</b>
							<spring:message code="trade.report.content8" />
							<b>${command.tradeDetailDTO.tradeLicenseOwnerdetailDTO[0].troMname}</b>
							<!-- father name -->
							<spring:message code="trade.report.content9" />
							<b>${command.tradeDetailDTO.trdBusnm}</b>
							<spring:message code="trade.report.content10" />
							<b>${command.tradeDetailDTO.trdBusadd}</b>
							<spring:message code="trade.report.content11" />
							<%-- ${command.district} --%>
							<spring:message code="trade.report.content12" />
							<spring:message code="trade.report.content13" />
							<b>${command.fromDateDesc}</b>
							<spring:message code="trade.report.content40" />
							<b>${command.toDateDesc}</b>
							<spring:message code="trade.report.content14" />

						</p>


						<p class="margin-top-20">
							<spring:message code="trade.report.content15" />
							<spring:message code="trade.report.content16" />
							<spring:message code="trade.report.content17" />
							<br>
							<%-- <spring:message code="report.content18" /><br>
								<spring:message code="report.content19" /><br>
								<spring:message code="report.content20" /><br>
								<spring:message code="report.content21" /> --%>

						</p>

					</div>
					<div class=" col-sm-3 padding-left-50">
						<div class="border-black">
						<div class="" id="propImages"></div>
						</div>

					</div>
				</div>

				<br>
				<br>
				<br>

						<h4><spring:message code="trade.details" /></h4>
							<c:set var="d" value="0" scope="page"></c:set>
							<table
								class="table table-bordered  table-condensed margin-bottom-10"
								id="itemDetails">
								<thead>

									<tr>
										<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
											showOnlyLabel="false"
											pathPrefix="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control required-control" showAll="false"
											disabled="${command.viewMode eq 'V' ? true : false }"
											hasTableForm="true" showData="false" columnWidth="20%" />


									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when
											test="${fn:length(command.tradeDetailDTO.tradeLicenseItemDetailDTO) > 0}">
											<c:forEach var="taxData"
												items="${command.tradeDetailDTO.tradeLicenseItemDetailDTO}"
												varStatus="status">
												<tr class="itemDetailClass">
													<form:hidden
														path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
														id="triId${d}" />
													<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
														showOnlyLabel="false"
														pathPrefix="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
														isMandatory="true" hasLookupAlphaNumericSort="true"
														hasSubLookupAlphaNumericSort="true"
														disabled="${command.viewMode eq 'V' ? true : false }"
														cssClass="form-control required-control no-arrow" showAll="false"
														hasTableForm="true" showData="true" />

												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />


											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr class="itemDetailClass">

												<form:hidden
													path="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
													id="triId${d+1}" />
												<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
													showOnlyLabel="false"
													pathPrefix="tradeDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
													isMandatory="true" hasLookupAlphaNumericSort="true"
													hasSubLookupAlphaNumericSort="true"
													disabled="${command.viewMode eq 'V' ? true : false }"
													cssClass="form-control required-control " showAll="false"
													hasTableForm="true" showData="true" />

											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
					<br>
				<br>
				<div class="row margin-top-10 clear">

					<div class="col-sm-10 col-xs-8 text-right">
						<p>
							<spring:message code="trade.report.authorizedSign"
								text="Authorized Signature:" />
						</p>
					</div>
					<div class="col-sm-3 col-xs-3 text-left ">
						<p>
							<%-- <b class="dateFormat">${command.workOrderDto.orderDateDesc}</b> --%>
						</p>
					</div>
				</div>
				<br>
				<br>
				<br>
				<div class="clear"></div>
				<hr>
				<div class="row margin-top-30 clear">
					<div class=" col-sm-12">
						<p>
							<spring:message code="trade.report.content26"></spring:message>
							<br>
							<spring:message code="trade.report.content27"></spring:message>
							<br>
							<spring:message code="trade.report.content28"></spring:message>
							<b>${userSession.getCurrent().organisation.oNlsOrgnameMar}</b>
							<spring:message code="trade.report.content29"></spring:message>
							<br>
							<spring:message code="trade.report.content30"></spring:message>
							<b>${userSession.organisation.ONlsOrgname}.</b>
							<spring:message code="trade.report.content31"></spring:message>
							<br>
							<spring:message code="trade.report.content32"></spring:message>
							<br>
							<spring:message code="trade.report.content33"></spring:message>
							<br>
							<spring:message code="trade.report.content34"></spring:message>
							<br>
							<spring:message code="trade.report.content35"></spring:message>
						</p>

					</div>

				</div>

				<br>
				<br>

				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a5"> <spring:message
								code="renewal.history.details" /></a>
					</h4>
					<div id="a5" class="panel-collapse collapse in">
						<div class="panel-body">
							<c:set var="d" value="0" scope="page"></c:set>
							<table class="table table-striped table-bordered"
								id="ownerDetail">
								<thead>
									<tr>
										<th width="8%"><spring:message
												code="renewal.financialYear"></spring:message></th>
										<th width="15%"><spring:message
												code="renewal.license.date"></spring:message></th>
										<th width="10%"><spring:message
												code="validity.certificate.date"></spring:message></th>
										<th width="8%"><spring:message code="renewal.license.fee"></spring:message></th>
									</tr>
								</thead>
								<tbody>


									<%-- <c:forEach var="taxData"
										items="${command.renewalMasterDetailDTO}" varStatus="status"> --%>
										<tr class="appendableClass">
										
										<td><form:input
													path="finYear"
													type="text"
													disabled="${command.viewMode eq 'V' ? true : false }"
													class="form-control unit required-control hasCharacter text-center"
													id="financialYear" /></td>

											<td><form:input
													path="fromDateDesc"
													type="text"
													disabled="${command.viewMode eq 'V' ? true : false }"
													class="form-control unit required-control hasCharacter text-center"
													id="treLicfromDate" /></td>
											<td><form:input
													path="toDateDesc"
													type="text"
													disabled="${command.viewMode eq 'V' ? true : false }"
													class="form-control unit required-control hasCharacter text-center"
													id="treLictoDate" /></td>

											<td><form:input
													path="tradeDetailDTO.tradeLicenseItemDetailDTO[0].triRate"
													type="text"
													class="form-control text-right unit required-control hasNumber"
													placeholder="00.00" readonly="true" id="triRate" /></td>

										</tr>
										<c:set var="d" value="${d + 1}" scope="page" />
								<%-- 	</c:forEach> --%>
								</tbody>
							</table>

						</div>
					</div>
				</div>




				<%-- <div class="row margin-top-10 clear">
					<div class=" col-sm-4 col-xs-2">
						<p>
							<spring:message code="report.content36"
								text="Date:" />
						</p>
					</div>
					<div class="col-sm-2 col-xs-5 text-left">
						<p>
							<b>${command.tradeDetailDTO.createdDateDesc}</b>
						</p>
					</div>
					<div class="col-sm-4 col-xs-4">
						<p>
							<spring:message code="report.content37" />
						</p>
					</div>
					<div class="col-sm-2 col-xs-3 text-left ">
						<p>
							<b>${command.tradeDetailDTO.createdDateDesc}</b>
						</p>
					</div>
				</div> --%>


				<p class="margin-top-20">

					<spring:message code="trade.report.content18" />
					<br> <br>
					<spring:message code="trade.report.content19" />
					<br> <br>
					<spring:message code="trade.report.content20" />
					<br> <br>
					<spring:message code="trade.report.content21" />

				</p>

				<div class="row margin-top-10">
					<div class="col-xs-12">
						<p class="text-center">
							<spring:message code="trade.report.content22" text="" />
						</p>
					</div>
				</div>
				<br>
				<div class="text-center hidden-print padding-20">
					<button onclick="printReport('receipt')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="trade.print" text="Print" />
					</button>
					<button type="button" class="btn btn-danger" name="button-Cancel"
						value="Cancel" onclick="window.location.href='AdminHome.html'"
						id="button-Cancel">
						<spring:message code="trade.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>