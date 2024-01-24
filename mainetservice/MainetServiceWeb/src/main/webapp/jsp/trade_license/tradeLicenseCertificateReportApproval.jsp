

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
	padding: 20px 0;
}
.border-black,
.border-black #propImages {
	height: 100px;
	margin-top: 0px;
}
.border-black {
	border: 1px solid #000;
	width: 170px;
	min-height: 130px;
	padding: 10px;
	position: relative;
    right: 0.4rem;
}
.border-black #propImages {
	width: 100px;
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
#tradeLicenseReportPrinting h4 {
	background: none;
	font-size: 15px;
    font-weight: 500;
    border: none;
}
</style>



<!-- Start Content here -->
<div class="content animated slideInDown">
	<div class="widget invoice" id="receipt">
		<style>
			@media print {
				@page { margin: 15px; }
				.widget-content {
					border: 1px solid #000;
				}
				.widget-content.padding {
					padding: 15px 0;
				}
				.border-black,
				.border-black #propImages {
					height: 100px;
					margin-top: 0px;
				}
				.border-black {
					border: 1px solid #000;
					width: 120px;
					min-height: 130px;
				    padding: 10px;
				    position: relative;
    				right: 20px;
				}
				.border-black #propImages {
					width: 100px;
				}
				#tradeLicenseReportPrinting img {
					width: 150px;
					height: 110px;
				}
				#tradeLicenseReportPrinting h4 {
					background: none;
					font-size: 15px;
				    font-weight: 500;
				    border: none;
				}
			}
		</style>
		<div class="widget-content padding">
			<form:form action="LicenseGeneration.html" class="form-horizontal"
				name="tradeLicenseReportPrinting" id="tradeLicenseReportPrinting">
				<c:if test="${not empty command.imagePath}">
					<form:hidden path="" id="imgMode" value="${command.imagePath}" />
				</c:if>

				<div class="col-xs-3 col-sm-3">
					<img src="${userSession.orgLogoPath}" width="80">
				</div>

				<div class="col-xs-6 col-sm-6 text-center">
				<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
					<h3 class="text-bold margin-bottom-0">
						<spring:message code=""
							text="${userSession.organisation.ONlsOrgname}" />
					</h3>
				</c:if>
				<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
					<h3 class="text-bold margin-bottom-0">
						<spring:message code=""
							text="${userSession.organisation.ONlsOrgnameMar}" />
					</h3>
				</c:if>
					<h4 class="text-center">
						<spring:message code="trade.ulb" text="license" />
					</h4>
					<%-- <p>
						<spring:message code=""
							text="${userSession.organisation.orgAddress}" />

					</p> --%>
				</div>
				<div class="col-sm-2 col-sm-offset-1 col-xs-2">
					<p>
						<spring:message code="trade.date" text="Date" />
						<span class="text-bold">${command.dateDesc}</span>
					</p>




				</div>
				<%-- <div class="col-xs-3 text-right">
					<img src="${userSession.orgLogoPath}" width="80">
				</div>
				 --%>

				<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-md-4 col-xs-4">
							<p>
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
							<p>
								<spring:message code="owner.details.name" text="Owner Name" />
							</p>
						</div>
						<div class="col-sm-8 col-xs-8 text-left ">
							<c:set var="d" value="0" scope="page"></c:set>
							<c:forEach
								items="${command.tradeDetailDTO.tradeLicenseOwnerdetailDTO}"
								var="ownerDTO" varStatus="loop">
								<c:if test="${ownerDTO.troPr eq 'A'}">
									<%-- <c:if test="${d lt 3}"> --%>
										<p>
											<!-- Defect #99393 -->
											<b>${ownerDTO.troName}</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<span class="add-colon"><spring:message code="trade.father.husband.name" text="Father/Husband" /></span>
											<b>${ownerDTO.troMname}</b>

										</p>
									<%-- </c:if> --%>
									<c:set var="d" value="${d + 1}" scope="page" />
								</c:if>
							</c:forEach>
						</div>
					</div>
					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-md-4 col-xs-4">
							<p>
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
					<div class="border-black">
						<div class=""
							id="propImages"></div>
					</div>
				</div>
				<!-- <div class="col-sm-12"> -->
				<div class="col-sm-10 col-xs-10 col-md-10 col-lg-10">
					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-xs-4">
							<p>
								<spring:message code="trade.valid.date"
									text="License Validity Date" />
							</p>
						</div>
						<div class="col-sm-8 col-xs-8 text-left ">
							<p>
								<span class="add-colon"><spring:message code="trade.from.date" text="From Date" /></span>
								<b>${command.licenseFromDateDesc}</b> &nbsp&nbsp&nbsp
								<span class="add-colon"><spring:message code="trade.to.date" text="To Date" /></span>
								<b>${command.tradeDetailDTO.licenseDateDesc}</b>
							</p>
						</div>
					</div>

					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-xs-4">
							<p>
								<spring:message code="trade.zone" text="Zone" />
							</p>
						</div>
						<div class="col-sm-8 col-xs-8 text-left ">


							<spring:eval
								expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(${command.tradeDetailDTO.trdWard1},${command.tradeDetailDTO.orgid})"
								var="lookup" />

							<p>
								<b> ${lookup.lookUpDesc }</b>
							</p>
						</div>
					</div>
					<div class="row margin-top-10 clear">
						<div class="col-sm-4 col-xs-4">
							<p>
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
							<p>
								<spring:message code="license.fee.details" text="License Fee Details" />
							</p>

						</div>
						<div class="col-sm-8 col-xs-8 text-left ">
							<p>
								<spring:message code="lic.rcpt.no" text="Receipt No." />
								<b>:${command.rmRcptno}</b></b> &nbsp&nbsp&nbsp
								<spring:message code="lic.rcpt.date" text="Receipt Date" />
								<b>:${command.loiDateDesc}</b>&nbsp&nbsp&nbsp
								<spring:message code="trade.receipt.mode" text="Receipt Mode" />
								<b>:${command.payMode}</b>
							</p>
						</div>



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
										<td width="150px" rowspan="2"><spring:message
												code="license.type.cat"
												text="License Type/ License Category" /></td>
										<td colspan="2"><spring:message code="license.fee.det"
												text="Fees Details" /></td>
									</tr>
									<tr>
										<td><spring:message code="license.type" text="Type" /></td>
										<td><spring:message code="license.amt"
												text="Amount (Rs.)" /></td>
									</tr>
									<c:forEach items="${command.chargeDescAndAmount}"
										var="chargeMap" varStatus="loop">
										<tr>

											<c:set var="map" value="${command.chargeDescAndAmount}"
												scope="page" />
											<c:if test="${loop.index eq 0}">
												<td rowspan="${map.size()}">${catagory.lookUpDesc }</td>
											</c:if>
											<td>${chargeMap.key}</td>
											<td>${chargeMap.value}</td>

										</tr>
									</c:forEach>
									<tr>
										<td colspan="2"><spring:message
												code="license.amt.tot.fee" text="Total Fees (Rs.)" /></td>
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
										<td class="text-center"><spring:message code="trd.cat" text="Category" /></td>
										<td class="text-center"><spring:message code="trd.subcat" text="SubCategory" /></td>
										<!-- <td>Measurement Unit</td> -->
										<td class="text-center"><spring:message code="trd.item.val" text="Item Value" /></td>
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
											<%-- <spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(${itemList.triCod4},${command.tradeDetailDTO.orgid})"
												var="lookup" />
											<td class="text-center">${lookup.lookUpDesc }</td> --%>
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

				<div class="row margin-top-10 clear padding-20">

					<div class="col-sm-12 col-xs-12 text-right">
						<p class="">
							<spring:message code="trade.report.authorizedSign"
								text="Authorized Signature" />
							<br>
							<c:if test="${userSession.getCurrent().getLanguageId() eq 1}">
								<b>${userSession.organisation.ONlsOrgname}</b>
							</c:if>

							<c:if test="${userSession.getCurrent().getLanguageId() ne 1}">
								<b>${userSession.organisation.ONlsOrgnameMar}</b>
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
					<spring:message code="trade.certificate.terms.condition" text="TERMS AND CONDITIONS" /></div>
				<br>

				<div class="col-sm-10 col-xs-10 text-center ">
					<!-- new -->
					<div class="margin-top-10 clear text-center">

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
					<button onclick="printdiv('receipt')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="trade.print" text="Print" />
					</button>
					<button type="button" class="btn btn-danger backButton" id=""
						onclick="window.location.href ='AdminHome.html',true">
						<spring:message code="trade.back"></spring:message>
					</button>
				</div>





			</form:form>
		</div>
	</div>
</div>











