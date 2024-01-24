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

<script type="text/javascript">
$(document).ready(function() {
	$('.fancybox').fancybox();
	});
	
function printContent(el){
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
}
</script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated slideInDown"> 	<!-- Start info box -->
	<div class="widget" id="receipt">
	
		
		
		<div class="text-center padding-top-10">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i><spring:message code="water.plumberLicense.print"
												text="Print" /> 
					</button>
				</div>
				
			<%-- <form:form action="DemandNoticePrinting.html" method="post"> --%>
				<c:forEach items="${command.response}" var="data" varStatus="status">
					<c:if test="${data.selected }">
					<div class="widget-content padding">
						<!-- Start info box -->
<!--########################FINAL DEMAND NOTICE #############################################################-->

						<c:if test="${data.noticeType eq command.finalDemandType}">
							<div class="row">
								<div class="col-xs-3 text-left">
									<img src="assets/img/logo.png">
								</div>
								<div class="col-xs-6 text-center">
									<h4 class="margin-bottom-0">${command.orgName}</h4>
									<p class="margin-top-10">
										<strong><spring:message code="final.demand.notice"
												text="Final Demand Notice" /></strong>
									</p>
								</div>
								<div class="col-xs-3 text-right">
									<img src="assets/img/logo.png">
								</div>
							</div>
							<div class="form-group clearfix">
								<div class="col-xs-6">
									<p>
										<spring:message code="demand.number" text="Number" />
										: ${data.noticeNo }
									</p>
								</div>
								<div class="col-xs-6 text-right">
									<fmt:formatDate pattern="dd/MM/yyyy" value="${data.noticeDate}"
										var="noticeDate" />
									<p>
										<spring:message code="demand.date" text="Date" />
										: ${noticeDate }.
									</p>
								</div>
							</div>
							<div class="form-group clearfix margin-top-5">
								<div class="col-xs-6">
									<p>
										<spring:message code="demand.from" text="From" />
										,
									</p>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-11 col-xs-push-1">
									<p><spring:message code="water.MunicipalCommissioner"/></p>
									<p class="margin-top-5">${command.deptName},</p>
									<p class="margin-top-5">${command.orgName}</p>
								</div>
							</div>
							<div class="form-group clearfix margin-top-5">
								<div class="col-xs-6">
									<p>
										<spring:message code="demand.to" text="To" />
										,
									</p>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-11 col-xs-push-1">
									<c:set value="${data.custName }" var="name" />
									<c:set var="custName" value="${fn:replace(name,'null','') }" />
									<p>${custName }</p>
									<c:set value="${data.custAddress }" var="addr" />
									<c:set var="custAddr" value="${fn:replace(addr,'null','') }" />
									<p class="margin-top-5">${custAddr}</p>
									<!-- <p class="margin-top-5">--------------------------------</p> -->
								</div>
							</div>
							<div class="row margin-top-15">
								
								<div class="col-xs-11">
									<spring:message code="final.demand.subject" text="Subject"
										arguments="${data.connectionNo }" />
								</div>
							</div>
							<p class="margin-top-15">
								<spring:message code="demand.salutation" text="Mr. / Mrs," />
								,
							</p>
							<p class="margin-top-15">
								<spring:message code="final.demand.para1"
									text="final prragraph 1"
									arguments=" ${data.noticeNo },${noticeDate },${data.billAmount}" />
							</p>
							<p class="margin-top-10">
								<spring:message code="final.demand.para2"
									text="final paragraph 2"
									arguments=" ${data.billAmount},${command.finalDemandDays}" />
							</p>
							<div class="row margin-top-20">

								
								<div class="col-xs-6 text-left">
									<p>
										<spring:message code="demad.greets" text="Sincerely" />
										,<br> Municipal Commissioner<br> <strong>
											${command.deptName}</strong>
									</p>
								</div>
							</div>

						</c:if>
						<!-- End of info box -->


<!--########################DEMAND NOTICE #############################################################-->
						<c:if test="${data.noticeType eq command.demandType}">
							<!-- Start info box -->
							<div class="row">
								<div class="col-xs-3 text-left">
									<img src="assets/img/logo.png">
								</div>
								<div class="col-xs-6 text-center">
									<h4 class="margin-bottom-0">${command.orgName}</h4>
									<p class="margin-top-10">
										<strong><spring:message code="demand.notice.conn"
												text="Demand Notice" /> </strong>
									</p>
								</div>
								<div class="col-xs-3 text-right">
									<img src="assets/img/logo.png">
								</div>
							</div>
							<div class="form-group clearfix">
								<div class="col-xs-6">
									<p>
										<spring:message code="demand.number" text="Number" />
										: ${data.noticeNo }
									</p>
								</div>
								<div class="col-xs-6 text-right">
									<fmt:formatDate pattern="dd/MM/yyyy" value="${data.noticeDate}"
										var="noticeDate" />
									<p>
										<spring:message code="demand.date" text="Date" />
										: ${noticeDate }
									</p>
								</div>
							</div>
							<div class="form-group clearfix margin-top-5">
								<div class="col-xs-6">
									<p>
										<spring:message code="demand.from" text="From" />
										,
									</p>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-11 col-xs-push-1">
									<p><spring:message code="water.MunicipalCommissioner"/></p>
									<p class="margin-top-5">${command.deptName},</p>
									<p class="margin-top-5">${command.orgName}.</p>
								</div>
							</div>
							<div class="form-group clearfix margin-top-5">
								<div class="col-xs-6">
									<p>
										<spring:message code="demand.to" text="To" />
										,
									</p>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-11 col-xs-push-1">
									<c:set value="${data.custName }" var="name" />
									<c:set var="custName" value="${fn:replace(name,'null','') }" />
									<p>${custName }</p>
									<c:set value="${data.custAddress }" var="addr" />
									<c:set var="custAddr" value="${fn:replace(addr,'null','') }" />
									<p class="margin-top-5">${custAddr}</p>
									<!-- <p class="margin-top-5">--------------------------------</p> -->
								</div>
							</div>
							<div class="row margin-top-15">
								
								<div class="col-xs-11">
									<spring:message code="demand.subject" text="Subject"
										arguments="${data.connectionNo }" />
								</div>
							</div>
							<fmt:formatDate pattern="dd/MM/yyyy"
								value="${data.outstandangFrom}" var="fromDate" />
							<fmt:formatDate pattern="dd/MM/yyyy"
								value="${data.outstandangTo}" var="toDate" />
							<p class="margin-top-15">
								<spring:message code="demand.salutation" text="Mr. / Mrs," />
								,
							</p>
							<p class="margin-top-15">
								<spring:message code="demand.para1" text="prragraph 1"
									arguments=" ${data.billAmount},${fromDate},${toDate}" />
							</p>
							<p class="margin-top-10">
								<spring:message code="demand.para2" text="paragraph 2"
									arguments=" ${data.billAmount},${command.demandDays}" />
							</p>
							<div class="row margin-top-20">
								<div class="col-xs-6 text-left">
									<p>
										<spring:message code="demad.greets" text="Sincerely" />
										,<br>Municipal Commissioner<br> <strong>${command.deptName}.</strong>
									</p>
								</div>
							</div>
							<!-- End of info box -->
						</c:if>
						
						</div>
						<div class="pagebreak"></div>
					</c:if>
				</c:forEach>

				
			<%-- </form:form> --%>
		
	</div>
</div>