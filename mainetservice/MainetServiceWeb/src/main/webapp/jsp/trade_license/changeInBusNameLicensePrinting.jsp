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
	src="js/trade_license/changeInBusinessNameReport.js"></script>


<style>
.widget {
	padding: 40px;
}
.widget-content{
	border: 1px solid #000;
}
div img {
	width: 35%;
}
hr{
	border-top: 1px solid #000;
}
.invoice .widget-content.padding{
	padding: 20px;
}
.border-black{
	border: 1px solid #000;
	padding:10px;
	min-height: 180px;
    width: 75%;
}
.padding-left-50{
	padding-left: 50px !important;
}

</style>

<script type="text/javascript">
	function printdiv(printpage) {
		debugger;
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

<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content animated slideInDown">
	<div class="widget invoice" id="receipt">
		<div class="widget-content padding">
			<form:form action="ChangeInBusinessPrinting.html"
				class="form-horizontal" name="changeInBusinessLicensePrinting"
				id="changeInBusinessLicensePrinting">
				<form:hidden path="" id="viewMode" value="${command.viewMode}" />
				<form:hidden path="" id="imgMode" value="${command.imagePath}" />
				<div class="row">
				
				<div class="col-xs-3 col-sm-3">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>
					
					<div class="col-xs-6 col-sm-6 text-center">
						<h3 class="margin-bottom-0">
							<spring:message code=""
								text="${userSession.organisation.ONlsOrgname}" />
						</h3>
						<h3 class="text-bold text-center margin-top-20">
							<spring:message code="trade.ulb" text="license" />
						</h3>
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
				</div>
				
				<div class="col-sm-10 col-xs-10">
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="license.details.businessName" text="Business Name" />

						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left">
						<p>
							<b>${command.tradeMasterDetailDTO.trdBusnm}</b>
						</p>
					</div>
				</div>
				
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="owner.details.name" text="Owner Name" />
						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left ">
						<p>
							<b>${command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[0].troName}</b>
						</p>
					</div>
				</div>
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="owner.details.address" text="Address" />
						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left ">
						<p>
							<b>${command.tradeMasterDetailDTO.trdBusadd}</b>
						</p>
					</div>
				</div>
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="trade.details" text="Business Details" />
						</p>
					</div>
					<div class="col-sm-2 col-xs-2 text-left ">
						<p>
							<b>${command.categoryDesc}</b>
							
							
						</p>
					</div>
				</div>
				</div>
				<div class="col-sm-2 col-xs-2">
						<div class="border-black">
							<div class="" id="propImages"></div>
						</div>
				</div>
				<!-- <div class="col-sm-12"> -->
				<div class="col-sm-10 col-xs-10">
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="trade.valid.date" text="Valid Date" />
						</p>
					</div>
					<div class="col-sm-10 col-xs-10 text-left ">
						<p>
							From <b>${command.licenseFromDateDesc}</b> To <b>${command.tradeMasterDetailDTO.licenseDateDesc}</b>
						</p>
					</div>
				</div>
				<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="trade.zone" text="Zone" />
						</p>
					</div>
					<div class="col-sm-4 col-xs-4 text-left ">
						<p>
							<b>${command.ward1Level}</b>
							<b>${command.ward2Level}</b>
							<b>${command.ward3Level}</b>
							<b>${command.ward4Level}</b>
							<b>${command.ward5Level}</b>
						</p>
					</div>
					</div>
					<div class="row margin-top-10 clear">
					<div class="col-sm-2 col-xs-2">
						<p>
							<spring:message code="license.no" text="License Number" />
						</p>
					</div>
					<div class="col-sm-4 col-xs-4 text-left ">
						<p>
							<b>${command.tradeMasterDetailDTO.trdLicno}</b>
						</p>
					</div>
				</div>
				</div>
				<br>
				<br>
				<br>
				<div class="row margin-top-10 clear">

					<div class="col-sm-12 col-xs-12 text-right">
						<p class="">
							<spring:message code="trade.report.authorizedSign"
								text="Authorized Signature" />
								<br>
								<b>${userSession.organisation.ONlsOrgname}</b>
						</p>
					</div>
					<div class="col-sm-3 col-xs-3 text-left ">
						<p>
							<%-- <b class="dateFormat">${command.workOrderDto.orderDateDesc}</b> --%>
						</p>
					</div>
				</div>
							
							
							<div class="text-bold text-center padding-vertical-10">
							<spring:message code="trade.certificate.terms.condition"></spring:message>
							<br><br>
							
							<%-- 
							<c:forEach items="${command.apprejMasList}"
								var="approveRejList" varStatus="status">
								<tr>
									<td>${status.count}</td>
									<td>${approveRejList.artRemarks}</td>

								</tr>
							</c:forEach> --%>
							</div>
					<br>
				<br>
				<br>
				<br>
				<br>
				<div class="clear"></div>
				
				
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