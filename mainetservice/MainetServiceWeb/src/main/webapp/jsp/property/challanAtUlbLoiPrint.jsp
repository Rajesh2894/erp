<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	//Function to print report

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
<!-- End JSP Necessary Tags -->

<div class="content">
	<div class="widget">
		<div id="printPreview">
			<div style="border: 2px solid black;">
				<div class="widget-content padding">

					<%-- <div class="col-sm-2 col-xs-2">
					<img src="${userSession.orgLogoPath}" alt="Organisation Logo" class="not-logged-avatar pull-left">
					</div> --%>

					<div class="clearfix padding-5"></div>
					<div>
						<!-- <div class="col-xs-3 text-left">
							<img src="assets/img/aligarhlogo.png" class="holder_img"
								width="80" alt="Loading please wait">
						</div> -->
						<div class="text-center">
							<h3 class="text-medium margin-bottom-0 margin-top-0 text-bold">
								${command.receiptDTO.orgNameMar}&nbsp;${command.offlineDTO.orgAddressMar}								
							</h3>
						</div>
					</div>
					<hr>
					<!-- <div class="clearfix padding-5"></div> -->
					<div class="form-group clearfix padding-vertical-15">
						<div class="col-xs-8 text-center"></div>
						<div class="col-xs-4 text-center">
							<p class="margin-top-15">
								<spring:message text="" code="mutation.certificate.content1" />
							</p>
							<p class="margin-top-15">
								<spring:message text="" code="mutation.certificate.date" />
								:-&nbsp;&nbsp;${command.receiptDTO.date}
							</p>
							<p class="margin-top-15">
								<spring:message text="" code="mutation.service.token.no" />
								:-&nbsp;&nbsp;${command.offlineDTO.referenceNo}
							</p>
						</div>
					</div>
					<div class="col-xs-11">
						<p class="margin-top-15">
							<span class="text-bold"> <spring:message
									code="mutation.loi.subject" /></span> &nbsp;
							<spring:message code="mutation.loi.content1" />
						</p>
						<p>
							&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
							<spring:message code="mutation.loi.content2" />
							<spring:message code="mutation.loi.propno" />
							&nbsp;&nbsp;${command.offlineDTO.propNoConnNoEstateNoL}

							<c:choose>
								<c:when test="${not empty command.offlineDTO.flatNo}">
									<spring:message code="mutation.loi.houseno" />&nbsp;&nbsp;${command.offlineDTO.flatNo} &nbsp;</c:when>
							</c:choose>
							<spring:message code="mutation.loi.closingBracket" />
						</p>
					</div>
					<div class="col-xs-11">
						<p class="margin-top-15">
							<span class="text-bold"> <spring:message
									code="mutation.certificate.content3" /></span> &nbsp;&nbsp;
							<spring:message code="srno1" />
							&nbsp; ${command.offlineDTO.transferOwnerFullName}
						</p>
						<p>
							&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
							&nbsp;&nbsp;
							<spring:message code="mutation.createdDate" />
							${command.offlineDTO.transferInitiatedDate}
							<spring:message code="mutation.application" />
						</p>
						<p>
							&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;
							<spring:message code="srno2" />
							<spring:message code="mutation.nondani" />
							&nbsp; ${command.offlineDTO.regNo}
							<spring:message code="mutation.dinank" />
							&nbsp; ${command.offlineDTO.transferDate}
						</p>
					</div>
					<div class="clearfix padding-10"></div>
					<div class="col-xs-11">
						<p>
							<spring:message code="mutation.loi.content3" />
							&nbsp;
							<spring:message code="mutation.loi.propno" />
							&nbsp;&nbsp;${command.offlineDTO.propNoConnNoEstateNoL} &nbsp;

							<c:choose>
								<c:when test="${not empty command.offlineDTO.flatNo}">
									<spring:message code="mutation.loi.houseno" />&nbsp;&nbsp;${command.offlineDTO.flatNo}</c:when>
							</c:choose>

							<spring:message code="mutation.loi.content4" />
							<spring:message code="mutation.loi.transfer.fee" />
							<span class="text-bold">${command.receiptDTO.totalReceivedAmount}</span>
							<spring:message code="mutation.loi.content5" />
							&nbsp; ${command.offlineDTO.slaSmDuration}
							<spring:message code="mutation.loi.content6" />
							<spring:message code="mutation.loi.content7" />
						</p>
					</div>

					<div class="clearfix padding-10"></div>
					<div class="form-group clearfix padding-vertical-15">
						<div class="col-xs-8 text-center"></div>
						<div class="col-xs-4 text-center">
							<p class="margin-top-15">
								<spring:message text="" code="mutation.certificate.content25" />
							</p>
							<p>${command.receiptDTO.orgNameMar}</p>
							<p>${command.offlineDTO.orgAddressMar}</p>
						</div>
					</div>

					<div class="clearfix padding-10"></div>
					<div class="col-xs-11">
						<p>
							<spring:message text="" code="mutation.certificate.content24" />
						</p>
						<p>${command.offlineDTO.transferOwnerFullName}</p>
						<p>${command.offlineDTO.transferAddress}</p>
					</div>

					<div class="clearfix padding-10"></div>
					<div class="text-center hidden-print padding-10">
						<button onClick="printdiv('printPreview');"
							class="btn btn-primary hidden-print">
							<i class="fa fa-print"></i>
							<spring:message code="property.report.Print" />
						</button>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>