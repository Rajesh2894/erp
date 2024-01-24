<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript"
	src="js/solid_waste_management/ContractMapping.js"></script>

<jsp:useBean id="date" class="java.util.Date" scope="request" />
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="contract.agreement" text="CONTRACT AGREEMENT" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="widget invoice" id="receipt">
				<div class="widget-content padding">
					<div id="receipt">
						<form action="" method="get">
							<div class="row">
								<div class="col-xs-12 text-center">
									<h3 class="margin-bottom-0">
										<spring:message code="contract.agreement"
											text="CONTRACT AGREEMENT" />
									</h3>
								</div>
							</div>
							<div class="padding-5 clear">&nbsp;</div>
							<div id="export-excel">
								<div class="row margin-top-30 clear">
									<div class="col-sm-12 text-justify">
										<p class="padding-top-20">
											<spring:message
												code="contract.mapping.agreement.made.ontheday" />
											${userSession.getCurrent().organisation.ONlsOrgname}&nbsp;${userSession.getCurrent().organisation.orgAddress}
											<spring:message code="contract.mapping.agreement.first.part" />
											${command.vendorContractMappingDTO.vendorNam}
											<spring:message
												code="contract.mapping.agreement.register.office" />
											${command.vendorContractMappingDTO.vendorAddress}
											<spring:message code="contract.mapping.agreement.second.part" />
											${command.vendorContractMappingDTO.representedBy},
											${command.vendorContractMappingDTO.designation}
											<spring:message code="contract.mapping.second.part" />
										</p>
										<p class="margin-top-10">
											<spring:message code="contract.mapping.first.part.authority" />
											${command.vendorContractMappingDTO.tendorNo}
											<spring:message code="contract.mapping.second.part.authority" />
											${userSession.getCurrent().organisation.ONlsOrgname}
											&nbsp;${userSession.getCurrent().organisation.orgAddress}
											<spring:message code="contract.mapping.authority" />
											${command.vendorContractMappingDTO.resolutionNo}
											<spring:message code="contract.mapping.dated" text="dated" />
											${command.vendorContractMappingDTO.resolutionDate}
											<spring:message code="contract.mapping.tender" />
											${command.vendorContractMappingDTO.contractAmount} +
											<spring:message code="contract.mapping.service.tax" />
											${command.vendorContractMappingDTO.contractFromDate}
											<spring:message code="contract.mapping.to" />
											${command.vendorContractMappingDTO.contractToDate}.
										</p>
										<p class="margin-top-10">
											<spring:message code="contract.mapping.awarded" />
										</p>
										<p class="margin-top-10">
											<spring:message code="contract.mapping.scnd.part.agreed" />
										</p>
										<c:forEach items="${command.vendorContractMappingList}"
											var="data" varStatus="index">
											<p class="margin-left-10">${index.count}.${data.services}&nbsp;
												<spring:message code="contract.mapping.vide.its.bid" />
											</p>
										</c:forEach>
										<p class="margin-top-10">
											<spring:message code="contract.mapping.agreement.witness" />
										</p>
										<p class="margin-top-10">
											<spring:message
												code="contract.mapping.agreement.second.part.deliver" />
										</p>
										<p class="margin-top-10">
											<spring:message code="contract.mapping.bidder" />
										</p>
										<p class="margin-top-10">
											<spring:message code="contract.mapping.contract.Jurisdiction" />
											${userSession.getCurrent().organisation.ONlsOrgname}.
										</p>
										<p class="margin-top-10">
											<spring:message code="contract.mapping.parties.witness" />
											${userSession.getCurrent().organisation.ONlsOrgname}.
										</p>
										<p class="margin-top-10">
											<spring:message code="contract.mapping.presence" />
										</p>

										<c:forEach items="${command.ulbWitnessMappingList}" var="data"
											varStatus="index">
											<ul style="margin-left: 30px">
												<li><spring:message
														code="contract.mapping.authorized.representative" />
													${userSession.getCurrent().organisation.ONlsOrgname}</li>
												<li>${index.count}<spring:message
														code="contract.mapping.witness" />_______________________
												</li>
												<li><spring:message
														code="contract.mapping.witness.name" />:&nbsp;${data.witnessName}</li>
											</ul>
										</c:forEach>
										<br>
										<c:forEach items="${command.vendorWitnessMappingList}"
											var="data" varStatus="index">
											<ul style="margin-left: 30px">
												<li><spring:message
														code="contract.mapping.behalf.bidder" /></li>
												<li>${index.count}<spring:message
														code="contract.mapping.witness" />_______________________
												</li>
												<li><spring:message
														code="contract.mapping.witness.name" />:&nbsp;${data.vendorWitness}</li>
											</ul>
										</c:forEach>
										<p class="margin-top-10">
											<spring:message code="contract.mapping.sealed.with.common" />
											${userSession.getCurrent().organisation.ONlsOrgname}
											<spring:message code="contract.mapping.presence" />
										</p>
									</div>

								</div>
								<div class="clear"></div>
								<div class="row">

									<div class="col-xs-4 col-xs-push-8  margin-top-50">
										<ol>
											<li>______________________</li>
											<li>______________________</li>
										</ol>
										<br>
										<p>
											<spring:message code="contract.mapping.authorized.person.of" />
											${userSession.getCurrent().organisation.ONlsOrgname}
										</p>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="text-center padding-top-10">
				<button onclick="PrintDiv('${cheque.dishonor.register}');"
					class="btn btn-success hidden-print" type="button">
					<i class="fa fa-print"></i>
					<spring:message code="solid.waste.print" />
				</button>
				<button type="button" class="button-input btn btn-danger"
					name="button-Cancel" value="Cancel" style=""
					onclick="backcontractMapping();" id="button-Cancel">
					<spring:message code="solid.waste.back" text="Back" />
				</button>
			</div>

		</div>
	</div>
</div>
