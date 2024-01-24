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
	.subheading2 {
		color: #b30000;
		margin: 0 0 1rem;
	}
	.subheading3 {
		color: #ffffff;
	    background-color: #b30000;
	    border-radius: 12px;
	    box-shadow: 0 2px #000000;
	    padding: 0.5rem;
	}
	.main-report-content {
		margin: 1rem 0 0;
	}
	.report-content {
		overflow: hidden;
		margin: 0 0 1rem;
	}
	.report-content :is(.report-label, .report-data, .data1, .data2) {
		font-size: 14px;
	}
	.report-content :is(.report-label, .report-data) {
		float: left;
	}
	.report-content .report-label {
		padding: 0.1rem 0 0;
	}
	.report-content .report-data {
		line-height: 24px;
	}
	.border-bottom {
		border-bottom: 1px solid #000000;
	}
	.height-1 {
		height: 1.4rem;
	}
	.width-2 {
		width: 2%;
	}
	.width-3 {
		width: 3%;
	}
	.width-5 {
		width: 5%;
	}
	.width-8 {
		width: 8%;
	}
	.width-10 {
		width: 10%;
	}
	.width-15 {
		width: 15%;
	}
	.width-16 {
		width: 16%;
	}
	.width-26 {
		width: 26%;
	}
	.width-38 {
		width: 38%;
	}
	.width-62 {
		width: 62%;
	}
	.width-74 {
		width: 74%;
	}
	.width-90 {
		width: 90%;
	}
	.width-97 {
		width: 97%;
	}
	.width-98 {
		width: 98%;
	}
	.line5 .data1,
	.line5 .data2,
	.line5 .data3 {
		float: left;
		margin: 0.1rem;
	}
	.line6 {
		margin: 2rem 0 0;
	}
	.line7 {
		margin: 4rem 0 0;
	}
	.margin-top-80 {
		margin-top: 80px;
	}
	.margin-horizontal-5 {
		margin: 0 5px;
	}
	table.thane-trade-report-table thead tr td,
	table.thane-trade-report-table tbody tr td {
		border: 1px solid #000000 !important;
	}
	.thane-trade-report-table thead tr td {
		font-weight: 600;
		text-align: center;
	}
</style>

<!-- Start Content here -->
<div class="content animated slideInDown">
	<div class="widget invoice" id="receipt">
		<style>
			@media print {
				.subheading2 {
					color: #b30000;
					margin: 0 0 1rem;
				}
				.subheading3 {
					color: #ffffff;
				    background-color: #b30000;
				    border-radius: 8px;
				    box-shadow: 0 2px #000000;
				    padding: 0.5rem;
				}
				.main-report-content {
					margin: 1rem 0 0;
				}
				.main-report-content > div.col-xs-12 {
					padding: 0;
				}
				.report-content {
					overflow: hidden;
					margin: 0 0 1rem;
				}
				.report-content :is(.report-label, .report-data, .data1, .data2) {
					font-size: 14px;
				}
				.report-content :is(.report-label, .report-data) {
					float: left;
				}
				.report-content .report-label {
					padding: 0.1rem 0 0;
				}
				.report-content .report-data {
					line-height: 24px;
				}
				.border-bottom {
					border-bottom: 1px solid #000000;
				}
				.height-1 {
					height: 2rem;
				}
				.width-2 {
					width: 3rem;
				}
				.width-3 {
					width: 4rem;
				}
				.width-8 {
					width: 80px !important;
				}
				.width-5 {
					width: 5%;
				}
				.width-10 {
					width: 12rem;
				}
				.width-15 {
					width: 15%;
				}
				.width-16 {
					width: 20rem;
				}
				.width-26 {
					width: 15rem;
				}
				.width-38 {
					width: 22.5rem;
				}
				.width-62 {
					width: calc(100% - 22.5rem);
				}
				.width-74 {
					width: calc(100% - 15rem);
				}
				.width-90 {
					width: calc(100% - 12rem);
				}
				.width-97 {
					width: calc(100% - 4rem);
				}
				.width-98 {
					width: calc(100% - 3rem);
				}
				.line5 .data1,
				.line5 .data2,
				.line5 .data3 {
					float: left;
					margin: 0.1rem;
				}
				.line6 {
					margin: 2rem 0 0;
				}
				.line7 {
					margin: 4rem 0 0;
				}
				.margin-top-80 {
					margin-top: 80px;
				}
				.margin-horizontal-5 {
					margin: 0 5px;
				}
				table.thane-trade-report-table thead tr td,
				table.thane-trade-report-table tbody tr td {
					border: 1px solid #000000 !important;
				}
				.thane-trade-report-table thead tr td {
					font-weight: 600;
					text-align: center;
				}
			}
		</style>
		<div class="widget-content padding">
			<form:form action="LicenseGeneration.html" class="form-horizontal"
				name="tradeLicenseReportPrinting" id="tradeLicenseReportPrinting">
				
				<div class="row">
					<div class="col-xs-12">
						<div class="col-xs-3 col-sm-3">
							<img src="${userSession.orgLogoPath}" width="80">
						</div>
		
						<div class="col-xs-6 col-sm-6 text-center">
							<h2><spring:message code="trade.report.heading" /></h2>
							<h3><spring:message code="trade.report.subheading" /></h3>
							<p class="subheading2"><spring:message code="trade.report.subheading2" /></p>
							<span class="subheading3"><spring:message code="trade.report.subheading3" /></span>
						</div>
						
						<div class="col-sm-2 col-sm-offset-1 col-xs-2">
							
						</div>
					</div>
				</div>
				
				<div class="row main-report-content">
					<div class="col-xs-12">
						<div>
							<c:forEach
								items="${command.tradeDetailDTO.tradeLicenseOwnerdetailDTO}"
								var="ownerDTO" varStatus="loop">
								<c:if test="${ownerDTO.troPr eq 'A'}">
									<div class="report-content line1">
										<label class="report-label width-2"> <spring:message
												code="trade.report.lable1" />
										</label>
										<div class="report-data border-bottom width-98 height-1">
											${command.tradeDetailDTO.bussinessNature}</div>
									</div>
									<div class="report-content line2">
										<label class="report-label width-10"> <spring:message
												code="trade.report.lable2" />
										</label>
										<div class="report-data border-bottom width-90 height-1">
											${ownerDTO.troName}</div>
									</div>
								</c:if>
							</c:forEach>
						</div>
						<div class="report-content line3">
							<label class="report-label width-3">
								<spring:message code="trade.report.lable3" />
							</label>
							<div class="report-data border-bottom width-97 height-1">
								${command.tradeDetailDTO.trdBusadd}
							</div>
						</div>
						<div class="report-content line4">
							<label class="report-label width-10">
								<spring:message code="trade.report.lable4" />
							</label>
							<div class="report-data border-bottom width-90 height-1">
								${command.tradeDetailDTO.trdBusnm}
							</div>
						</div>
						<div class="report-content line5">
							<label class="report-label width-10">
								<spring:message code="trade.report.lable5" />
							</label>
							<div class="data1 width-8 height-1">
								${command.licenseFromDateDesc}
							</div>
							<label class="report-label">
								<spring:message code="trade.report.lable7" />
							</label>
							<label class="report-label margin-horizontal-5">
								<spring:message code="trade.report.lable8" />
							</label>
							<div class="data2 width-8 height-1">
								${command.tradeDetailDTO.licenseDateDesc}
							</div>
							<label class="report-label width-16 height-1">
								<spring:message code="trade.report.lable9" />
							</label>
						</div>
						<div class="report-content line6">
							<ul class="col-xs-6 padding-left-0">
								<li>
									<label class="report-label width-26 height-1">
										<spring:message code="trade.report.lable10" />
									</label>
									<div class="report-data border-bottom width-74 height-1">
										<c:forEach items="${command.tradeDetailDTO.tradeLicenseItemDetailDTO}"
										var="itemList" varStatus="loop">
											<spring:eval
												expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(${itemList.triCod4},${command.tradeDetailDTO.orgid})"
												var="lookup" />
											${itemList.trdUnit } ${lookup.lookUpDesc} 
										</c:forEach>
		
									</div>
									<div class="clear"></div>
								</li>
								<li>
									<label class="report-label width-26 height-1">
										<spring:message code="trade.report.lable11" />
									</label>
									<div class="report-data border-bottom width-74 height-1">
		
									</div>
									<div class="clear"></div>
								</li>
								<li>
									<label class="report-label width-26">
										<spring:message code="trade.report.lable12" />
									</label>
									<div class="report-data border-bottom width-74 height-1">
		
									</div>
									<div class="clear"></div>
								</li>
							</ul>
							<ul class="col-xs-6 padding-0">
								<li>
									<label class="report-label width-38">
										<spring:message code="trade.report.lable13" />
									</label>
									<div class="report-data border-bottom width-62 height-1">
		
									</div>
									<div class="clear"></div>
								</li>
								<li>
									<label class="report-label width-38">
										<spring:message code="trade.report.lable14" />
									</label>
									<div class="report-data border-bottom width-62 height-1">
										${command.tradeDetailDTO.fireNonApplicable}
									</div>
									<div class="clear"></div>
								</li>
								<li>
									<label class="report-label width-38">
										<spring:message code="trade.report.lable15" />
									</label>
									<div class="report-data border-bottom width-62 height-1">
		
									</div>
									<div class="clear"></div>
								</li>
							</ul>
						</div>
						<div class="report-content line7">
							<label class="report-label col-xs-1">
								<spring:message code="trade.report.lable16" />
							</label>
							<div class="report-data border-bottom col-xs-5 height-1">
								<spring:eval
									expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getHierarchicalLookUp(${command.tradeDetailDTO.trdWard1},${command.tradeDetailDTO.orgid})"
									var="lookup" />
								<b> ${lookup.lookUpDesc }</b>
							</div>
							<label class="report-label col-xs-1">
								<spring:message code="trade.report.lable17" />
							</label>
							<div class="report-data border-bottom col-xs-5 height-1">
								<b>${command.tradeDetailDTO.trdLicno}</b>
							</div>
						</div>
					</div>
					<div class="col-xs-12">
						<div class="pull-right text-center margin-top-80 margin-bottom-10">
							<p class="text-bold"><spring:message code="trade.report.lable18" /></p>
							<p class="text-bold"><spring:message code="trade.report.heading" /></p>
						</div>
					</div>
					<div class="col-xs-12">
						<table class="table table-bordered thane-trade-report-table">
							<thead>
								<tr>
									<td><spring:message code="trade.report.table.heading1" /></td>
									<td><spring:message code="trade.report.table.heading2" /></td>
									<td><spring:message code="trade.report.table.heading3" /></td>
									<td><spring:message code="trade.report.table.heading4" /></td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				
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