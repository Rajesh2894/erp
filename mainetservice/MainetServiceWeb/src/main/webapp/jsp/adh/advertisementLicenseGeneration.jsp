<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script>
	function printContent(el) {
		var restorepage = document.body.innerHTML;
		var printcontent = document.getElementById(el).innerHTML;
		document.body.innerHTML = printcontent;
		window.print();
		document.body.innerHTML = restorepage;
	}
</script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget" id="receipt">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Advertisement License Letter" />
			</h2>
		</div>
		<div class="widget-content padding">
		
			<form:form action="" name="" id="" class="form-horizontal">
				<div class="col-xs-12 text-center">
					<h2><img src="${userSession.orgLogoPath}" alt="Organisation Logo" style="width: 50px; height: 50px;"> ${userSession.organisation.ONlsOrgname}</h2>
				</div>

				<p class="margin-top-10">This letter is to certify
					<strong> M/s.${command.advertisementReqDto.applicantName}
					</strong>  ADD:
					${command.advertisementReqDto.applicantDetailDTO.villageTownSub}.Under
					Raipur Muncipal Council Act 2013 (Section)
				</p>
				<p class="margin-top-10">
					Licence No: <strong>${command.advertisementReqDto.advertisementDto.licenseNo}
					</strong> on
					 <%-- ${command.advertisementReqDto.agencyRegisDate} --%>
					 <strong><fmt:formatDate pattern="dd/MM/yyyy" value="${command.advertisementReqDto.advertisementDto.licenseFromDate}" /></strong>
				</P>
				<p>
					<%-- <b>License Date Period </b> : <strong><fmt:formatDate pattern="dd/MM/yyyy" value="${command.advertisementReqDto.advertisementDto.licenseFromDate}" /></strong>
					to <strong><fmt:formatDate pattern="dd/MM/yyyy" value="${command.advertisementReqDto.advertisementDto.licenseToDate}" /></strong> --%>
				</p>
				<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="advertiserTable">
							<thead>
								<tr>
									<th>Sr.No</th>
									<th>License Period</th>
									<th>Signature</th>
									<th>Remarks</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<%-- <td>${temp}</td> --%>
									<td>1</td>
									<td><fmt:formatDate pattern="dd/MM/yyyy" value="${command.advertisementReqDto.advertisementDto.licenseFromDate}" /> &nbsp; to &nbsp; <fmt:formatDate pattern="dd/MM/yyyy" value="${command.advertisementReqDto.advertisementDto.licenseToDate}" /></td>
									<td></td>
									<td></td>
								</tr>

							</tbody>
						</table>
					</div>
				</div>
				<!--  -->
				<div class="overflow margin-top-10">
					<table class="table table-hover table-bordered table-striped" id="">
						<thead>
							<tr>
								
								<th scope="col" width="3%"><spring:message code="adh.sr.no"
										text="Sr.No." /></th>
								<th scope="col" width="" align="center"><spring:message
										code="" text="Remarks" /></th>
							</tr>
						</thead>
						<tbody>
						<c:if test="${ fn:length( command.getRemarkList() ) > 0}">
						  <c:set var="count" value="0" scope="page"></c:set>
							 <c:forEach items="${command.getRemarkList()}" var="remark"
								varStatus="status">
								<tr>
									 <td>${status.count}</td> 
									<td>${remark}</td>
								</tr>
							</c:forEach>  
							</c:if>
						</tbody>
					</table>
				</div>

				<!--  -->
				<div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i>
						<spring:message code="adh.print" text="Print"></spring:message>
					</button>
					<!-- <input type="button" onclick="window.location.href='AdvertiserMaster.html'"
						class="btn btn-default hidden-print" value="Back"> -->

					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.location.href='AdminHome.html'">

						<spring:message code="adh.back" text="Back"></spring:message>
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>