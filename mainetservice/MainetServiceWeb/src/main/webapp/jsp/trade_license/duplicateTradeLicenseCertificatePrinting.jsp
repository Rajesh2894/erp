

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
	src="js/trade_license/tradeLicenseReportFormat.js"></script>


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
.add-colon::after {
	content: "\003A";
}
.authorizedSign{
	padding-right: 3rem !important;
}
</style>



<!-- Start Content here -->
<div class="content animated slideInDown">
	<div class="widget invoice" id="receipt">
		<div class="widget-content padding trade-license-cert-div">
			<form:form action="DuplicateLicensePrinting.html"
				class="form-horizontal" name="duplicateLicensePrinting"
				id="tradeLicenseReportPrinting">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<c:if test="${not empty command.imagePath}">
					<form:hidden path="" id="imgMode" value="${command.imagePath}" />
				</c:if>
				<div class="row">

					<div class="text-bold col-xs-3 col-sm-3">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>

					<div class="col-xs-6 col-sm-6 text-center">
						<h3 class="text-bold margin-bottom-0">
							<c:if test="${userSession.languageId eq 1}">
								<spring:message code=""
								text="${userSession.organisation.ONlsOrgname}" />
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
								<spring:message code=""
								text="${userSession.organisation.ONlsOrgnameMar}" />
							</c:if>

						</h3>
						<h3 class=" text-center margin-top-20">
							<spring:message code="trade.ulb" text="license" />

						</h3>
						<h3 class="text-center margin-top-20">
							<spring:message code="license.details.duplicate.license.copy" text="(Duplicate License Copy)" />
						</h3>
						<%-- <p>
							<spring:message code=""
								text="${userSession.organisation.orgAddress}" />

						</p> --%>
					</div>
					<div class="col-sm-2 col-sm-offset-1 col-xs-2">
						<p>
							<span class="add-colon"><spring:message code="trade.date" text="Date" /></span>
							<span class="text-bold">${command.dateDesc}</span>
						</p>




					</div>
					<%-- <div class="col-xs-3 text-right">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>
					 --%>
				</div>

				<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-md-4 col-xs-4">
							<p class="add-colon">
								<spring:message code="license.details.businessName"
									text="Business Name" />

							</p>
						</div>
						<div class="col-sm-8 col-md-8 col-xs-8 text-left">
							<p>
								<b>${command.tradeDetailDTO.trdBusnm}</b>
							</p>
						</div>
					</div>

					<div class="row margin-top-10 clear">
						<div class="col-sm-4  col-md-4 col-xs-4">
							<p class="add-colon">
								<spring:message code="owner.details.name" text="Owner Name" />
							</p>
						</div>
						<div class="col-sm-8 col-xs-8 text-left ">
							<c:set var="d" value="0" scope="page"></c:set>
							<c:forEach
								items="${command.tradeDetailDTO.tradeLicenseOwnerdetailDTO}"
								var="ownerDTO" varStatus="loop">
								<%-- <c:if test="${ownerDTO.troPr eq 'A'}"> --%>
								<%-- <c:if test="${d lt 3}"> --%>
									<p>
										<b>${ownerDTO.troName}</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<spring:message code="license.details.father.husband.name" text="Father/Husband Name" />:
										<b>${ownerDTO.troMname}</b>
									</p>
								<%-- </c:if> --%>
								<c:set var="d" value="${d + 1}" scope="page" />
								<%-- </c:if> --%>
							</c:forEach>
						</div>
					</div>
					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-md-4 col-xs-4">
							<p class="add-colon">
								<spring:message code="license.details.businessAddress"
									text="Address" />
							</p>
						</div>
						<div class="col-sm-8 col-md-8 col-xs-8 text-left ">
							<p>
								<b>${command.tradeDetailDTO.trdBusadd}</b>
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
				<!-- <div class="col-sm-12"> -->
				<div class="col-sm-10 col-xs-10 col-md-10 col-lg-10">
					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-xs-4">
							<p class="add-colon">
								<spring:message code="trade.valid.date"
									text="License Validity Date" />
							</p>
						</div>
						<div class="col-sm-8 col-xs-8 text-left ">
							<p>
								<span class="add-colon">
								<spring:message code="license.details.from.date" text="From Date" />
								</span>	<b>${command.licenseFromDateDesc}</b> &nbsp&nbsp&nbsp
								<span class="add-colon">
								<spring:message code="license.details.end.date" text="End Date" />
								</span> <b>${command.tradeDetailDTO.licenseDateDesc}</b>
							</p>
						</div>
					</div>

					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-xs-4">
							<p class="add-colon">
								<spring:message code="trade.zone" text="Zone" />
							</p>
						</div>
						<c:if test="${command.tradeDetailDTO.trdWard1 ne null}">
							<div class="col-sm-8 col-xs-8 text-left ">
	
	
								<spring:eval
									expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(${command.tradeDetailDTO.trdWard1},${command.tradeDetailDTO.orgid})"
									var="lookup" />
	
								<p>
									<b> ${lookup.lookUpDesc }</b>
								</p>
							</div>
						</c:if>
					</div>
					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-xs-4">
							<p class="add-colon">
								<spring:message code="license.no" text="License Number" />
							</p>
						</div>
						<div class="col-sm-8 col-xs-8 text-left ">
							<p>
								<b>${command.tradeDetailDTO.trdLicno}</b>
							</p>
						</div>
					</div>

				</div>




				<div class="col-sm-10 col-xs-10 col-md-10 col-lg-10">
					<!-- new -->
					<div class="row margin-top-10 clear">

						<div class="col-sm-4 col-xs-4">
							<p class="add-colon">
								<spring:message code="license.details.license.fee.details" text="License Fee Details" />
							</p>

						</div>

						<%-- <div class="col-sm-3 col-md-3 col-xs-3">
							<p>
								<spring:message code="" text="Receipt No." /><b>:${command.rmRcptno}</b>

							</p>
						</div>
						 <div class="col-sm-2 col-md-2 col-xs-2 text-left">
							<p>
								<b>${command.rmRcptno}</b>
							</p>
						</div> --%>

						<%--	<div class="col-sm-3 col-md-3 col-xs-3">
							<p>
								<spring:message code="" text="Receipt Date" /><b>:${command.loiDateDesc}</b>

							</p>
						</div>
						 <div class="col-sm-2 col-md-2 col-xs-2 text-left">
							<p>
								<b>${command.loiDateDesc}</b>
							</p>
						</div> --%>

					</div>
				</div>

				<div class="col-sm-12 col-xs-12 text-left ">
					<!-- new -->
					<div class="row margin-top-10 clear">
						<!-- 
						<div class="col-sm-2 col-xs-2">
							<p></p>
						</div>

 -->
						<div class="col-sm-12 col-xs-12 text-left ">


							<c:forEach
								items="${command.tradeDetailDTO.tradeLicenseItemDetailDTO}"
								var="itemList" varStatus="loop">

								<spring:eval
									expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(${itemList.triCod1},${command.tradeDetailDTO.orgid})"
									var="catagory" />

							</c:forEach>


							<table class="table table-bordered">
								<tbody>
									<tr align="left">
										<td width="150px" rowspan="2">
										<spring:message code="license.details.license.type.category" text="License Type/ License Category" />
										</td>
										<td colspan="2">
										<spring:message code="license.details.fees.details" text="Fees Details" />
										</td>
									</tr>
									<tr>
										<td>
										<spring:message code="license.details.type" text="Type" />
										</td>
										<td>
										<spring:message code="license.details.amount" text="Amount (Rs.)" />
										</td>
									</tr>
									<tr>
										<td>${catagory.lookUpDesc }</td>
										<td>
										<spring:message code="license.details.market.license.fee" text="Market License Fee" />
										</td>
										<td>${command.rmAmount}</td>
									</tr>
									<tr>
										<td colspan="2">
										<spring:message code="license.details.total.fees" text="Total Fees (Rs.)" />
										</td>
										<td>${command.rmAmount}</td>
									</tr>
								</tbody>
							</table>

						</div>
					</div>
				</div>




				<div class="col-sm-10 col-xs-10 col-md-10 col-lg-10">
					<!-- new -->
					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-xs-4">
							<p>
								<spring:message code="trade.details" text="Business Details" />
							</p>
						</div>
					</div>
				</div>


				<div class="col-sm-12 col-xs-12 col-md-12 col-lg-12">
					<!-- new -->
					<div class="row margin-top-10 clear">

						<div class="col-sm-12 col-xs-12 text-left ">
							<p>
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="text-center">
										<spring:message code="license.details.category" text="Category" />
										</td>
										<td class="text-center">
										<spring:message code="license.details.sub.category" text="SubCategory" />
										</td>
										<td class="text-center" >
										<spring:message code="license.details.measurement.unit" text="Measurement Unit" />
										</td>
										<td class="text-center">
										<spring:message code="license.details.item.value" text="Item Value" />
										</td>
									</tr>
									<c:forEach
										items="${command.tradeDetailDTO.tradeLicenseItemDetailDTO}"
										var="itemList" varStatus="loop">

										<tr>

											<spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(${itemList.triCod1},${command.tradeDetailDTO.orgid})"
												var="lookup" />

											<td class="text-center">${lookup.lookUpDesc }</td>
											<spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(${itemList.triCod2},${command.tradeDetailDTO.orgid})"
												var="lookup" />
											<td class="text-center">${lookup.lookUpDesc }</td>
											<spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(${itemList.triCod4},${command.tradeDetailDTO.orgid})"
												var="lookup" />
											<td class="text-center">${lookup.lookUpDesc }</td>
											<td class="text-center">${itemList.trdUnit }</td>

										</tr>

									</c:forEach>


								</tbody>
							</table>

							</p>
						</div>
					</div>


				</div>


				<br>
				<br>
				<br>
				<br>
				<br>

				<div class="row margin-top-10 clear">

					<div class="col-sm-12 col-xs-12 text-right authorizedSign">
						<p class="row margin-top-60">
							<spring:message code="trade.report.authorizedSign"
								text="Authorized Signature" />
							<br> 
							<%-- <b>${userSession.organisation.ONlsOrgname}</b> --%>
							<c:if test="${userSession.languageId eq 1}">
								<spring:message code=""
								text="${userSession.organisation.ONlsOrgname}" />
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
								<spring:message code=""
								text="${userSession.organisation.ONlsOrgnameMar}" />
							</c:if>
						</p>
					</div>
					<div class="col-sm-3 col-xs-3 text-left ">
						<p>
							<%-- <b class="dateFormat">${command.workOrderDto.orderDateDesc}</b> --%>
						</p>
					</div>
				</div>

				<div class="text-bold text-center padding-vertical-10">
				<spring:message code="license.details.terms.and.conditions" text="TERMS AND CONDITIONS" />
				</div>
				<br>

				<div class="col-sm-10 col-xs-10 text-center ">
					<!-- new -->
					<div class="row margin-top-10 clear text-center">

						<!-- <div class="col-sm-8 col-xs-8 text-left "> -->
						<p>
						<table class="table .table-responsive text-center">

							<tbody>

								<!-- 
									<tr>
										<th width="8%" class="text-center"></th>
										
									</tr> -->


								<c:forEach items="${command.apprejMasList}" var="lookup"
									varStatus="loop">

									<tr>

										<td class="text-left"><b>${loop.index+1}.</b> <c:choose>
												<c:when
													test="${userSession.getCurrent().getLanguageId() eq 1}">
																 ${lookup.artRemarks }
															</c:when>
												<c:otherwise>
																 ${lookup.artRemarksreg }
															</c:otherwise>
											</c:choose></td>

									</tr>
								</c:forEach>

							</tbody>
						</table>


						<%-- <b>${command.issuanceDateDesc}</b> --%>
						</p>
						<!-- </div> -->
					</div>


				</div>



				<div class="clear"></div>


				<div class="text-center hidden-print padding-20">
					<c:if test="${command.viewMode ne 'H'}">
						<button onclick="printdiv('receipt')"
							class="btn btn-primary hidden-print">
							<i class="fa fa-print"></i>
							<spring:message code="trade.print" text="Print" />
						</button>
					</c:if>
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











