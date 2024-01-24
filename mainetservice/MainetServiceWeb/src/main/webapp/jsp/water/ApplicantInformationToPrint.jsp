<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/ui/kendo.all.min.js"></script>
<script type="text/javascript" src="js/water/newWaterConnectionForm.js"></script>
<script>
	//function ExportPdf() {
	//	kendo.drawing.drawDOM("#downloadapplication", {
	//		forcePageBreak : ".page-break",
	//		paperSize : "A4",
			// margin: { top: "0cm", bottom: "0cm" },
	//		scale : 0.7,
	//		height : 300,
	//		template : $("#page-template").html(),
	//		keepTogether : ".prevent-split"
	//	}).then(function(group) {
	//		kendo.drawing.pdf.saveAs(group, "ApplicationForm.pdf")
	//	});
	//}

	function printDiv(divName) {
		var printContents = document.getElementById(divName).innerHTML;
		var originalContents = document.body.innerHTML;
		document.body.innerHTML = printContents;
		window.print();
		document.body.innerHTML = originalContents;
	}

	function openApplicationForm() {
		$('#frmNewWaterFormViewApplicationPrint').submit();

	}
</script>
<!-- Start Content here -->
<style>
#downloadapplication h2 {
	font-size: 1.4em;
	background: #dcdcdc;
	text-align: center;
}
</style>
<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">

			<div class="widget-content padding">

				<form:form action="NewWaterConnectionForm.html"
					class="form-horizontal form"
					name="frmNewWaterFormViewApplicationPrint"
					id="frmNewWaterFormViewApplicationPrint">
					<form:hidden path="" id="onlineOfflinePay"
						value="${command.offlineDTO.onlineOfflineCheck}" />


					<div id="downloadapplication">


						<h2>
							<spring:message code="app.details" />
						</h2>

						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label"><spring:message
									code="water.road.applNo" /></label>
							<div class="col-sm-4 col-xs-4">

								<span>${command.responseDTO.applicationNo}</span>

							</div>
							<label class="col-sm-2 col-xs-2 control-label " for="lastName"><spring:message
									code="water.road.applDate" /></label>
							<div class="col-sm-4 col-xs-4">
								<c:set var="now" value="<%=new java.util.Date()%>" />
								<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
								<span>${date}</span>

							</div>
						</div>
						<div class="clear"></div>

						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label"><spring:message
									code="water.meterDet.servName" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.serviceName}</span>
							</div>
							<label class="col-sm-2 col-xs-2 control-label"><spring:message
									code="serv.date" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.serviceDuration}</span>
							</div>
						</div>
						<div class="clear"></div>

						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label"><spring:message
									code="pay.amount" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.offlineDTO.amountToPay}</span>

							</div>
							<label class="col-sm-2 col-xs-2 control-label"><spring:message
									code="pay.mode" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.paymentMode}</span>

							</div>
						</div>


						<div class="clear"></div>


						<h2>
							<spring:message code="ulb.details" />
						</h2>

						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label" for="middleName"><spring:message
									code="ulb.type" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.talukaDesc}</span>

							</div>
							<label class="col-sm-2 col-xs-2 control-label " for="lastName"><spring:message
									code="ulb.name" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${userSession.getCurrent().getOrganisation().getONlsOrgname()}</span>

							</div>
						</div>
						<div class="clear"></div>


						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label" for="middleName"><spring:message
									code="district" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.districtDesc}</span>

							</div>

						</div>


						<div class="clear"></div>



						<div class="form-group">
							<apptags:lookupFieldSet baseLookupCode="WWZ" hasId="true"
								showOnlyLabel="true" pathPrefix="csmrInfo.codDwzid"
								isMandatory="true" hasLookupAlphaNumericSort="true"
								hasSubLookupAlphaNumericSort="true" disabled="${disabled}"
								cssClass="form-control col-xs-4" />
						</div>


						<div class="clear"></div>

						<h2>
							<spring:message code="applicantinfo.label.header" />
						</h2>

						<div class="form-group">
							<label class="col-sm-2 col-xs-2 control-label " for="firstName"><spring:message
									code="water.applicnt.name" text="Applicant Name" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.csName} ${command.csmrInfo.csMname} ${command.csmrInfo.csLname}</span>

							</div>
						</div>
						<div class="clear"></div>
						<div class="form-group">
							<c:if test="${not empty command.csmrInfo.csEmail}">
								<label class="col-sm-2 col-xs-2 control-label"><spring:message
										code="applicantinfo.label.email" /></label>
								<div class="col-sm-4 col-xs-4">
									<span>${command.csmrInfo.csEmail}</span>

								</div>
							</c:if>
							<label class="col-sm-2  col-xs-2 control-label "><spring:message
									code="applicantinfo.label.mobile" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.csContactno}</span>

							</div>
						</div>
						<div class="clear"></div>
						<div class="form-group">

							<label class="col-sm-2 col-xs-2 control-label" for="middleName"><spring:message
									code="district" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.districtDesc}</span>

							</div>
							<label class="col-sm-2 col-xs-2 control-label" for="middleName"><spring:message
									code="state" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.stateDesc}</span>

							</div>
						</div>

						<%-- <div class="form-group">

							<label class="col-sm-2 col-xs-2 control-label "><spring:message
									code="address.line1" text="Address Line1" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.applicantDetailDto.areaName}</span>

							</div>
							<label class="col-sm-2 col-xs-2 control-label "><spring:message
									code="address.line2" text="Address Line2" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.applicantDetailDto.villageTownSub}</span>

							</div>
						</div> --%>
						<div class="clear"></div>
						<div class="form-group">
							<%-- <label class="col-sm-2 col-xs-2  control-label"><spring:message
									code="address.line3" text="Address Line3" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.applicantDetailDto.roadName}</span>

							</div> --%>

							<label class="col-sm-2 col-xs-2 control-label "><spring:message
									code="applicantinfo.label.pincode" /></label>
							<div class="col-sm-4 col-xs-4">
								<span>${command.csmrInfo.csCpinCode}</span>

							</div>
						</div>
						<div class="clear"></div>
					</div>


					<div class="text-center" id="divSubmit">

						<input type="button" class="btn btn-blue-2"
							onclick="printDiv('downloadapplication')"
							value="<spring:message code="water.plumberLicense.print"/>" /> <%-- <input
							type="button" class="btn btn-warning" onclick="ExportPdf()"
							value="<spring:message code="water.download"/>" /> --%>

						<c:if test="${command.free eq 'N'}">
							<c:if test="${command.offlineDTO.onlineOfflineCheck eq 'N'}">
								<button type="button" class="btn btn-success"
									onclick="continueForPayment(this)" id="payment">
									<spring:message code="print.challan" />
								</button>
							</c:if>
							<c:if test="${command.offlineDTO.onlineOfflineCheck eq 'P'}">
								<button type="button" class="btn btn-success"
									onclick="continueForPayment(this)" id="payment">
									<spring:message code="pay.receipt" />
								</button>
							</c:if>
							<c:if test="${command.offlineDTO.onlineOfflineCheck eq 'Y'}">
								<button type="button" class="btn btn-success"
									onclick="continueForPayment(this)" id="payment">
									<spring:message code="conti.online.pay" />
								</button>
							</c:if>
						</c:if>

						<button type="button" class="btn btn-success"
							onclick="openApplicationForm()" id="newForm">
							<spring:message code="new.app" />
						</button>
						<input type="button" class="btn btn-danger"
							onclick="window.location.href='AdminHome.html'"
							value="<spring:message code="water.btn.cancel"/>" />
					</div>




				</form:form>
			</div>
		</div>
	</div>
</div>
