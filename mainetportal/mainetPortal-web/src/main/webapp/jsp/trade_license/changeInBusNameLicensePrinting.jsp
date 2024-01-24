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
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="assets/css/dashboard-theme-2.css" rel="stylesheet"
	type="text/css" id="" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.min.js"></script>

<style>
.widget {
	padding: 40px;
}
.widget-content{
	border: 1px solid #000;
}
div img {
	width: 35%;
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

</style>

<script>
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

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content animated slideInDown">
	<div class="widget invoice" id="receipt">
		<div class="widget-content padding">
			<form:form action="ChangeInBusinessNameForm.html"
				class="form-horizontal" name="changeInBusinessLicensePrinting"
				id="changeInBusinessLicensePrinting">
				<form:hidden path="" id="viewMode" value="${command.viewMode}" />
				<div class="row">

					<div class="col-xs-3">
						<img alt="Organisation Logo" src="${userSession.orgLogoPath}" width="80">
					</div>


					<div class="col-xs-6 text-center">
						<h2 class="margin-bottom-0">
							<spring:message code=""
								text="${userSession.organisation.ONlsOrgname}" />
						</h2>
						<h2 class="text-bold text-center margin-top-20">
							<spring:message code="report.content5" text="license" />
						</h2>
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
							<spring:message code="report.licenseNo" text="License Number" />

						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<b>${command.tradeMasterDetailDTO.trdLicno}</b>
							<%-- <b>${command.cfcEntity.refNo}</b> --%>
						</p>
					</div>
				</div>
				<br>
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="report.issuanceDate" text="Issuance Date" />
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
							<b>${userSession.getCurrent().organisation.ONlsOrgnameMar}</b>
							<spring:message code="report.content6" />
							<%-- ${command.year} --%>
							<spring:message code="report.content7" />
							<b>${command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[0].troName}</b>
							<spring:message code="report.content8" />
							<b>${command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[0].troMname}</b>
							<!-- father name -->
							<spring:message code="report.content9" />
							<b>${command.tradeMasterDetailDTO.trdNewBusnm}</b>
							<spring:message code="report.content10" />
							<b>${command.tradeMasterDetailDTO.trdBusadd}</b>
							<spring:message code="report.content11" />
							<%-- ${command.district} --%>
							<spring:message code="report.content12" />
							<spring:message code="report.content13" />
							<b>${command.licFromDateDesc}</b>
							<spring:message code="report.content40" />
							<b>${command.licToDateDesc}</b>
							<spring:message code="report.content14" />

						</p>


						<p class="margin-top-20">
							<spring:message code="report.content15" />
							<spring:message code="report.content16" />
							<spring:message code="report.content17" />
							<br>
							<%-- <spring:message code="report.content18" /><br>
								<spring:message code="report.content19" /><br>
								<spring:message code="report.content20" /><br>
								<spring:message code="report.content21" /> --%>

						</p>

					</div>
					<div class=" col-sm-3 padding-left-50">
						<div class="border-black">
						<%-- <c:forEach items="${command.fetchDocumentList}" var="lookUp">
							<apptags:filedownload filename="${lookUp.attFname}"
								filePath="${lookUp.attPath}"
								actionUrl="RenewalLicensePrinting.html?Download"></apptags:filedownload>
							<form:hidden path="" value="${lookUp.attId}" />
						</c:forEach> --%>
						</div>


					</div>
				</div>

				<%-- <div class="row margin-top-10">
					<div class="col-xs-12">
						<p class="text-center">
							<spring:message code="report.content22"
								text="" />
						</p>
					</div>
				</div> --%>

				<br>
				<br>
				<br>


				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a4"> <spring:message
								code="trade.details" /></a>
					</h4>
					<div id="a4" class="panel-collapse collapse in">
						<div class="panel-body">
							<c:set var="d" value="0" scope="page"></c:set>
							<table
								class="table table-bordered  table-condensed margin-bottom-10"
								id="itemDetails">
								<thead>

									<tr>
										<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
											showOnlyLabel="false"
											pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control required-control" showAll="false"
											disabled="${command.viewMode eq 'V' ? true : false }"
											hasTableForm="true" showData="false" columnWidth="20%" />


										<!-- <th width="5%"><a title="Add"
														class="btn btn-blue-2 btn-sm addCF" onclick=""><i
															class="fa fa-plus"></i></a></th> -->

										<%-- <th width="10%"><spring:message code="trade.rate"></spring:message><span
														class="mand">*</span></th> --%>
										<%-- <c:if test="${command.saveMode ne 'V'}">
													<th width="5%"><a title="Add"
														class="btn btn-blue-2 btn-sm addCF" onclick=""><i
															class="fa fa-plus"></i></a></th>
												</c:if> --%>
									</tr>
								</thead>
								<%-- <tfoot>
												<tr>
													<th colspan="2" class="text-right"><spring:message
															code="trade.total" /></th>
													<th colspan="1"><form:input path=""
															id="totalitemDetail" cssClass="form-control text-right"
															readonly="true" placeholder="00.00" /></th>
													<th colspan="1" class="text-right"></th>

												</tr>
											</tfoot> --%>
								<tbody>
									<c:choose>
										<c:when
											test="${fn:length(command.tradeMasterDetailDTO.tradeLicenseItemDetailDTO) > 0}">
											<c:forEach var="taxData"
												items="${command.tradeMasterDetailDTO.tradeLicenseItemDetailDTO}"
												varStatus="status">
												<tr class="itemDetailClass">
													<form:hidden
														path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
														id="triId${d}" />
													<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
														showOnlyLabel="false"
														pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
														isMandatory="true" hasLookupAlphaNumericSort="true"
														hasSubLookupAlphaNumericSort="true"
														disabled="${command.viewMode eq 'V' ? true : false }"
														cssClass="form-control required-control " showAll="false"
														hasTableForm="true" showData="true" />

													<%-- <td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
															type="text"
															class="form-control text-right unit required-control hasNumber"
															placeholder="00.00" readonly="true" id="triRate${d}" /></td> --%>
													<%-- <c:if test="${command.saveMode ne 'V'}"> --%>
													<!-- <td class="text-center"><a
																	href="javascript:void(0);"
																	class="btn btn-danger btn-sm delButton"
																	onclick="deleteTableRow('itemDetails', $(this), 'removedIds', 'isDataTable');"><i
																		class="fa fa-minus"></i></a></td> -->
													<%-- </c:if> --%>



												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />


											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr class="itemDetailClass">

												<form:hidden
													path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triId"
													id="triId${d+1}" />
												<apptags:lookupFieldSet baseLookupCode="ITC" hasId="true"
													showOnlyLabel="false"
													pathPrefix="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triCod"
													isMandatory="true" hasLookupAlphaNumericSort="true"
													hasSubLookupAlphaNumericSort="true"
													disabled="${command.viewMode eq 'V' ? true : false }"
													cssClass="form-control required-control " showAll="false"
													hasTableForm="true" showData="true" />

												<%-- <td><form:input
															path="tradeMasterDetailDTO.tradeLicenseItemDetailDTO[${d}].triRate"
															type="text"
															class="form-control text-right unit required-control hasNumber"
															placeholder="00.00" readonly="true" id="triRate${d}" /></td> --%>
												<!-- <td class="text-center"><a href="javascript:void(0);"
															class="btn btn-danger btn-sm delButton"
															onclick="deleteTableRow('itemDetails', $(this), 'removedIds', 'isDataTable');"><i
																class="fa fa-minus"></i></a></td> -->


											</tr>
											<c:set var="d" value="${d + 1}" scope="page" />
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>




				<br>
				<br>
				<div class="row margin-top-10 clear">

					<div class="col-sm-10 col-xs-8 text-right">
						<p>
							<spring:message code="report.authorizedSign"
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
							<spring:message code="report.content26"></spring:message>
							<br>
							<spring:message code="report.content27"></spring:message>
							<br>
							<spring:message code="report.content28"></spring:message>
							<b>${userSession.getCurrent().organisation.ONlsOrgnameMar}</b>
							<spring:message code="report.content29"></spring:message>
							<br>
							<spring:message code="report.content30"></spring:message>
							<b>${userSession.organisation.ONlsOrgname}.</b>
							<spring:message code="report.content31"></spring:message>
							<br>
							<spring:message code="report.content32"></spring:message>
							<br>
							<spring:message code="report.content33"></spring:message>
							<br>
							<spring:message code="report.content34"></spring:message>
							<br>
							<spring:message code="report.content35"></spring:message>
						</p>

					</div>

				</div>

				<br>
				<br>

				<%-- <div class="panel panel-default">
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


									<c:forEach var="taxData"
										items="${command.renewalMasterDetailDTO}" varStatus="status">
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
									</c:forEach>
								</tbody>
							</table>

						</div>
					</div>
				</div>

 --%>


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

					<spring:message code="report.content18" />
					<br> <br>
					<spring:message code="report.content19" />
					<br> <br>
					<spring:message code="report.content20" />
					<br> <br>
					<spring:message code="report.content21" />

				</p>

				<div class="row margin-top-10">
					<div class="col-xs-12">
						<p class="text-center">
							<spring:message code="report.content22" text="" />
						</p>
					</div>
				</div>
				<br>
				<%-- <div class="text-center hidden-print padding-20">
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
				</div> --%>

			</form:form>
		</div>
	</div>
</div>