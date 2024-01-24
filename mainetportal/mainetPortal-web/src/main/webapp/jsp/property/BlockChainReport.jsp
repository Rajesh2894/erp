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
<jsp:useBean id="date" class="java.util.Date" scope="request" />

<script src="js/property/blockChain.js"></script>
<script>
	$(function() {
		$(".table").tablesorter().tablesorterPager({
			container : $(".ts-pager"),
			cssGoto : ".pagenum",
			removeRows : false,
		});
		$(function() {
			$(".table").tablesorter({
				cssInfoBlock : "avoid-sort",
			});

		});
	});
</script>
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="" text="Propertry Transfer" />
				</h2>
			</div>
			<div class="widget-content padding">
				<form:form action="" method="get" class="form-horizontal">
					<div id="receipt">
						<div class="col-xs-2">
							<h1>
								<img alt="Organization Logo" width="80" src="${userSession.orgLogoPath}">
							</h1>
						</div>
						<div class="col-xs-8 col-xs-8  text-center">
							<h2 class="text-large margin-bottom-0 margin-top-0 text-bold">
								${ userSession.getCurrent().organisation.ONlsOrgname} <br>

							</h2>
						</div>
						<div class="col-xs-2">
							<p>
								<spring:message code="" text="Date" />
								<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								<br>
								<spring:message code="" text="Time" />
								<fmt:formatDate value="${date}" pattern="hh:mm a" />
							</p>
						</div>
						<div class="clearfix padding-10"></div>
						<div class="clearfix padding-10"></div>

						<div class="form-group">
							<div class="col-xs-12">
							<%-- 	<p>
									<spring:message code="" text="To," />
								</p>
								<p>${command.blockChainDTO.witnessDetails[2].witnessName}</p> --%>

							</div>
							<div class="col-xs-12">
								<p class="margin-top-20">Subject : Property Transfer</p>
								<p>Ref : Property No : ${command.blockChainDTO.propNo}</p>
								<p>Address :
									${command.blockChainDTO.ownerDetails[0].ownerAddress}</p>
							</div>

							<div class="col-xs-12">
								<p class="margin-top-20">Sir/Madam,</p>
								<p>This is inform you that, the transfer of above mention
									property/shop in Assessment Register appearing in the name of

									${command.blockChainDTO.ownerDetails[0].ownerName} has been
									Approved</p>
							</div>
							<div class="col-xs-8">
								<p class="margin-top-20">
									<spring:message code="" text="Date : " />
									<fmt:formatDate value="${date}" pattern="dd/MM/yyyy" />
								</p>
							</div>
							<div class="col-xs-2">
								<h1>
								
									<img alt="sign" width="80" src="/images/sign.png">
								</h1>
							</div>
						</div>
						<div class="padding-5 clear">&nbsp;</div>
					</div>
					<div class="text-center hidden-print padding-10">
						<button onclick="PrintDiv('Property_Transfer');"
							class="btn btn-success hidden-print" type="button">
							<i class="fa fa-print"></i>
							<spring:message code="" text="Print" />
						</button>
					<%-- 	<button type="button" class="btn btn-danger"
						onclick="back('BlockChain.html', 'back')" id="btnSave">
						<spring:message code="" text="Back" />
						</button> --%>
						<apptags:backButton url="BlockChain.html"></apptags:backButton>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>