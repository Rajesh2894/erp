
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

<%--  <apptags:breadcrumb></apptags:breadcrumb> --%>

<!-- ============================================================== -->
<!-- Start Content here -->

<!-- ============================================================== -->
<div class="content">
	<!-- Start info box -->
	<div class="widget" id="receipt">
		<div class="widget-content padding">
			<div id="now">
				<div class="row">
					
					<div class="text-center">
						<h4 class="margin-bottom-0">${userSession.organisation.ONlsOrgname}</h4>
						
						<p class="margin-top-5">
							<strong></strong>
						</p>
					</div>
					
				</div>
				<p class="text-right">
					<span class="text-bold"><spring:message
							code="advertiser.remainder.notice.number" text="Notice No:" /></span>${command.remainderNoticeDto.noticeNo}
				</p>
				<p class="text-right">
					<span class="text-bold"><spring:message
							code="advertiser.remainder.notice.date" text="Date :" /></span>
					<fmt:formatDate value="<%=new java.util.Date()%>"
						pattern="dd/MM/yyyy" />
				</p>
				<div class="form-group clearfix margin-top-15">
					<div class="col-xs-3">
						<p>
							<span class="text-bold"><spring:message
									code="advertiser.remainder.notice.to" text="To," /></span>
						</p>
						<div class="col-xs-9 col-xs-push-1">${command.advertiserDto.agencyOwner}</div>
						<div class="col-xs-9 col-xs-push-1">${command.advertiserDto.agencyAdd}</div>
					</div>
				</div>
				
				<div class="row clearfix margin-top-10 margin">
					<div class="col-xs-1 col-xs-push-2">
						<span class="text-bold"><spring:message
								code="advertiser.remainder.notice.subject" text="Subject:" /></span>
					</div>
					<div class="col-xs-9 col-xs-push-2">Remainder of renewal
						notice</div>
				</div>


				<p class="margin-top-20">
					<spring:message code="adh.sir.madam" text="Sir/Madam," />
				</p>
				<p class="margin-top-5">
					<spring:message code="advertiser.remainder.notice.para1"
						text="You are here by informed that your contract No." />
					<span class="text-bold">${command.advertiserDto.agencyLicNo}</span>
					<spring:message code="advertiser.remainder.notice.para2"
						text="is valid from" />
					${command.advertiserDto.agencyLicenseFromDate}
					<spring:message code="advertiser.remainder.notice.to.para3"
						text="to" />
					${command.advertiserDto.agencyLicenseToDate}
					<spring:message code="advertiser.remainder.notice.para4"
						text=". Please renew your contract before" />
					${command.advertiserDto.agencyLicenseToDate}
				</p>

				<div class="row margin-top-25">
					<div class="col-xs-5 col-xs-push-7 text-center">

						<p>
							<strong>${userSession.organisation.ONlsOrgname}</strong>
						</p>
					</div>
				</div>

				<div class="text-center margin-top-20">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i>
						<spring:message code="adh.print" text="Print"></spring:message>
					</button>
					
					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.close();">

						<spring:message code="adh.close" text="Close"></spring:message>
					</button>
				</div>
			</div>

		</div>
	</div>
</div>
