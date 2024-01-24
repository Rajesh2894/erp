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
<jsp:useBean id="marathiConvert" class="com.abm.mainet.common.utility.Utility"></jsp:useBean>
<style>
.cert-outer-div {
	border: 3px solid #000000;
}
.bnd-acknowledgement .border-bottom-black { 
     border-bottom: 3px solid #000000;
}
.overflow-hidden {
	overflow: hidden;
}
</style>
<script>
    function printContent(el) {
	var restorepage = document.body.innerHTML;
	var printcontent = document.getElementById(el).innerHTML;
	document.body.innerHTML = printcontent;
	window.print();
	document.body.innerHTML = restorepage;
    }
</script>

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Your awesome content goes here -->
	<div class="widget" id="receipt">
		<div class="widget-content padding">
			<form action="" method="get" class="bnd-acknowledgement">
				<div class="cert-outer-div">
				<div class="border-bottom-black margin-0">
					<div class="overflow-hidden padding-vertical-10">
						<div class="col-xs-2">
								<img width="80" src="${userSession.orgLogoPath}">
						</div>
						<div class="col-xs-8 text-center">
							<h2 class="margin-bottom-10 padding-bottom-10">${userSession.organisation.ONlsOrgnameMar}</h2>
						</div>
					</div>
					<div class="col-xs-12 col-sm-12 text-center margin-bottom-10">
						<h4 class="margin-top-10">
							<span class="text-bold"><spring:message
								code="cfc.ackng.applNo" ></spring:message></span>
							<span class="text-bold margin-left-40" id="applicationId"> 
										${command.ackDto.applicationId}
							  </span>
						</h4>
					</div>
				</div>
				<div class="row margin-bottom-10 margin-left-0 margin-right-0">
					<div class="col-xs-3 col-sm-2 padding-top-5">
						<span class="text-bold"><spring:message
								code="cfc.ackng.applicantName" /></span>
					</div>
					<div class="col-xs-9 col-sm-10">
						:<span class="margin-left-20">${command.ackDto.applicantName}</span>
					</div>
				</div>
				
				<div class="row margin-bottom-10 margin-left-0 margin-right-0">
					<div class="col-xs-3 col-sm-2 padding-top-5">
						<span class="text-bold"><spring:message
								code="cfc.ackng.serviceName" /></span>
					</div>
					<div class="col-xs-9 col-sm-10">
						:<span class="margin-left-20">${command.ackDto.serviceName}</span>
					</div>
				</div>
				
				<div class="row margin-bottom-10 margin-left-0 margin-right-0">
					<div class="col-xs-3 col-sm-2 padding-top-5">
						<span class="text-bold"><spring:message
								code="cfc.ackng.deptName" /></span>
					</div>
					<div class="col-xs-9 col-sm-10">
						:<span class="margin-left-20">${command.ackDto.departmentName}</span>
					</div>
				</div>
				
				<div class="row margin-bottom-10 margin-left-0 margin-right-0">
					<div class="col-xs-3 col-sm-2 padding-top-5">
						<span class="text-bold"><spring:message
								code="cfc.ackng.appDate" /></span>
					</div>
					<div class="col-xs-2 col-sm-2">
						:<span class="margin-left-20"><fmt:formatDate pattern="dd/MM/yyyy" value="${command.ackDto.appDate}" /></span>
						
					</div>
					<div class="col-xs-2 col-sm-2 text-right padding-top-5">
						<span class="text-bold"><spring:message
								code="cfc.ackng.appTime" /></span>
					</div>
					<div class="col-xs-2 col-sm-2">
						:<span class="margin-left-20">${command.ackDto.appTime}</span>
					</div>
				</div>

				<div class="row padding-top-5 margin-bottom-10 margin-left-0 margin-right-0">
					<div class="col-xs-3 col-sm-2">
						<span class="text-bold"><spring:message
								code="cfc.ackng.dueDate" /></span>
					</div>
					<div class="col-xs-2 col-sm-2">
						:<span class="margin-left-20"><fmt:formatDate pattern="dd/MM/yyyy" value="${command.ackDto.dueDate}" /></span>
						
					</div>
					
				</div>
				
				
				<c:if test="${not empty  command.checkList}">
					<div class="padding-horizontal-10">					
						<h4><strong><i><spring:message code="cfc.ackng.doc.note" /></i></strong></h4>
						<table class="table table-striped table-condensed table-bordered" style=" width: 60%;">
							<tbody>
								<c:forEach items="${command.checkList}" var="checklist"
									varStatus="lk">

									<tr>
										<td>${checklist.documentSerialNo}</td>
										<td>${checklist.doc_DESC_Mar}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>	
				</c:if>
				<br>
				<div class="row margin-bottom-10 margin-left-0 margin-right-0">
					<div class="col-xs-12 col-sm-12 padding-top-5">
						<span class="text-bold line-height-1rem"><spring:message
								code="cfc.rec.ackng.comp.generate" text="computer generated" /></span>
					</div>
				</div>
				</div>
				
				<div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i>
						<spring:message code="cfc.ackng.print" text="Print"></spring:message>
					</button>

					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.close();">

						<spring:message code="cfc.ackng.close" text="Close"></spring:message>
					</button>
				</div>
				
			</form>
		</div>
	</div>
	<!-- End of info box -->
</div>
