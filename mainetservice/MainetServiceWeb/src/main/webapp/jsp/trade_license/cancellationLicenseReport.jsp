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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/trade_license/cancellationLicenseReport.js"></script>
<div class="content animated slideInDown">
	<div class="widget invoice" id="receipt">
		<div class="widget-content padding">
			<form:form action="CancellationLicense.html" class="form-horizontal"
				name="cancellationReport" id="cancellationReport">
				
				<div class="row">
				
				<div class="col-xs-3 col-sm-3">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>
					
					<div class="col-xs-6 col-sm-6 text-center">
						<h3 class="margin-bottom-0">
							<c:if test="${userSession.languageId eq 1}">
								<spring:message code=""
								text="${userSession.organisation.ONlsOrgname}" />
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
								<spring:message code=""
								text="${userSession.organisation.ONlsOrgnameMar}" />
							</c:if>
						</h3>
						
					</div>
					<%-- <div class="col-sm-2 col-sm-offset-1 col-xs-2">
						<p>
							<spring:message code="trade.date" text="Date" />
							<span class="text-bold">${command.dateDesc}</span>
						</p>
						<p>
							<spring:message code="trade.noticeNo" text="Notice No" />
							<span class="text-bold">${command.dateDesc}</span>
						</p>
						
					</div> --%>
					<%-- <div class="col-xs-3 text-right">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>
					 --%>
				</div>
				
				<div class="col-sm-10 col-xs-10">
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="trade.to" text="To" />

						</p>
					</div>
				</div>
				
				<%-- <div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="trade.report.name" text="Name" />
						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left ">
						<p>
							<b>${command.tradeDetailDTO.tradeLicenseOwnerdetailDTO[0].troName}</b>
						</p>
					</div>
				</div> --%>
					<div class="row margin-top-10 clear">
						<div class="col-sm-2 col-xs-2">
							<p>
								<spring:message code="trade.report.name" text="Name" />
							</p>
						</div>
						<div class="col-sm-8 col-xs-8 text-left">
							<c:set var="d" value="0" scope="page"></c:set>
							<c:forEach
								items="${command.tradeDetailDTO.tradeLicenseOwnerdetailDTO}"
								var="ownerDTO" varStatus="loop">
								<c:if test="${ownerDTO.troPr  eq 'A' }">						
									<p>
									 <b>${ownerDTO.troName}</b>
									</p>
							    </c:if>	
								<c:set var="d" value="${d + 1}" scope="page" />
							</c:forEach>
						</div>
					</div>
					<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="owner.details.address" text="Address" />
						</p>
					</div>
					<div class="col-sm-8 col-xs-8 text-left ">
						<p>
							<b>${command.tradeDetailDTO.trdBusadd}</b>
						</p>
					</div>
				</div>
				
				</div>
				<div class="col-sm-10 col-xs-10">
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="trade.subject" text="Subject" />
						</p>
					</div>
					<div class="col-sm-10 col-xs-10 text-left ">
						<p>
							 <b>${command.serviceMaster.smServiceName}</b>
						</p>
					</div>
				</div>
			<%-- 	<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="trade.ref" text="Reference:-" />
						</p>
					</div>
				</div>
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="trade.applicationNo" text="Application No" />
						</p>
					</div>
					<div class="col-sm-10 col-xs-10 text-left ">
						<p>
							From <b>${command.licenseFromDateDesc}</b> To <b>${command.tradeDetailDTO.licenseDateDesc}</b>
						</p>
					</div>
				</div>
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="trade.date" text="Date" />
						</p>
					</div>
					<div class="col-sm-10 col-xs-10 text-left ">
						<p>
							From <b>${command.licenseFromDateDesc}</b> To <b>${command.tradeDetailDTO.licenseDateDesc}</b>
						</p>
					</div>
				</div> --%>
				</div>
				<br>
				<br>
				<br>
					<div class="row margin-top-30 clear">
					<div class=" col-sm-12">
						<p class="margin-top-30">
							<spring:message code="cancellation.report1"></spring:message>
							<b>${command.tradeDetailDTO.apmApplicationId}</b>
							<spring:message code="cancellation.report2"></spring:message>
							<b><fmt:formatDate pattern="dd-MM-yyyy"
							value="${command.cfcEntity.apmApplicationDate}"/></b>
							<spring:message code="cancellation.report3"></spring:message>
							<b>${command.tradeDetailDTO.trdLicno}</b>
							<spring:message code="cancellation.report4"></spring:message>
						</p>

					</div>

				</div>
				<div class="row margin-top-10 clear">

					<div class="col-sm-12 col-xs-12 text-right">
						<p class="">
							<spring:message code="trade.report.authorizedSign"
								text="Authorized Signature" />
								<br>
								<b>
								<c:if test="${userSession.languageId eq 1}">
								<spring:message code=""
								text="${userSession.organisation.ONlsOrgname}" />
								</c:if>
								<c:if test="${userSession.languageId eq 2}">
									<spring:message code=""
									text="${userSession.organisation.ONlsOrgnameMar}" />
								</c:if>
								</b>
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
				<br>
				<br>
				<div class="clear"></div>
				
				<br>
				<br>
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
