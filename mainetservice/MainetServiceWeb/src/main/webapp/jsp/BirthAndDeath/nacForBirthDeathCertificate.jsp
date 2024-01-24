<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/birthAndDeath/printCertificate.js"></script>

<!-- End JSP Necessary Tags -->

<style>
.cert-outer-div {
	border: 2px solid #000000;
	padding: 2px;
}

.cert-inner-div {
	border: 4px solid #000000;
	padding: 5px;
}
</style>
<script>
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

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content" id="printDiv">
	<!-- Start Main Page Heading -->
	<div id="receipt">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message text="" /></b>
				</h2>
			</div>

			<!-- Start Widget Content -->
			<div id="printPreview">
				<div class="widget-content padding">

					<!-- Start Form -->
					<form:form action="PrintCertificate.html"
						class="form-horizontal form" name="nacBirthCertificate"
						id="print-div">
						<div class="cert-outer-div">
							<div class="cert-inner-div">
								<div>
									<div class="col-xs-12 col-sm-12 text-center">
										<h3 class="text-bold">
											<spring:message code="bnd.cert.formNo" text="Form No. 10" />
										</h3>
										<h3 class="">
											<spring:message code="bnd.cert.seeRule" text="(See Rule 13)" />
										</h3>
									</div>
								</div>

								<div class="col-xs-12 col-sm-12 text-center margin-top-15">
									<p class="text-bold">
										<spring:message code="bnd.cert.nonAvailcert"
											text="NON-AVAILABILITY CERTIFICATE" />
									</p>

								</div>
								<div class="col-xs-12 col-sm-12 text-center margin-top-15">
									<p class="">
										<spring:message code="bnd.cert.issuedUnder"
											text="(Issued under Section 17 of the Registration of Births & Deaths Act, 1969)" />
									</p>
								</div>


								<div class="col-xs-12 margin-top-10">
									<p class="margin-top-15 text-justify">
										<spring:message
											text="This is to certify that a search has been made on the request of"
											code="bnd.cert.toCertify" />
										<span class="text-bold">&nbsp;  &nbsp;</span>
										<spring:message code="bnd.cert.shri" text="Shri/Smt/Kum" />
										<span class="text-bold">&nbsp; ${command.childOrDecasedName} &nbsp;</span>
										<spring:message code="bnd.cert.son" text="son/wife/daughter" />
										<spring:message code="bnd.cert.of" text="of" />
										<span class="text-bold">&nbsp; ${command.fatherName} &nbsp;</span>
										<spring:message code="bnd.cert.inRegrecord"
											text="in the registration record of the year(s)" />
										${command.year}
										<spring:message code="bnd.cert.relating"
											text="relating to (Local area)" />
										<span class="text-bold">&nbsp; ${command.address} &nbsp;</span>
										<spring:message code="bnd.cert.off" text="of" />
										<spring:message code="bnd.cert.tahsil" text="(Tahsil)" />
										<span class="text-bold">&nbsp; ${command.talukaDesc} &nbsp;</span>
										<spring:message code="bnd.cert.ofDistrict"
											text="of (District)" />
										<span class="text-bold">&nbsp; ${command.districtDesc} &nbsp;</span>

										<spring:message code="bnd.cert.off" text="of" />
										<spring:message code="bnd.cert.state" text="(State)" />
										<span class="text-bold">&nbsp; ${command.stateDesc} &nbsp;</span>
										<spring:message code="bnd.cert.anFound"
											text="and found that the event relating to birth/death of" />
										<span class="text-bold">&nbsp; ${command.childOrDecasedName} &nbsp;</span>
										<spring:message code="bnd.cert.sonDaught"
											text="son/daughter of" />
										<span class="text-bold">&nbsp; ${command.fatherName} &nbsp;</span>
										<spring:message code="bnd.cert.notReg"
											text="was not registered." />

									</p>

								</div>
								<%-- <c:set var="today" value="<%=new java.util.Date()%>" />
								<div class="col-xs-12 margin-top-15">
									<spring:message text="Date" code="bnd.cert.date" />
									<span class="text-bold">&nbsp; <fmt:formatDate pattern="dd-MM-yyyy" value="${today}"/> &nbsp;</span>
								</div> --%>

							<div class="padding-5 clear">&nbsp;</div>
							<div class="clearfix padding-vertical-15">
								<c:set var="today" value="<%=new java.util.Date()%>" />
								<div class="col-xs-8 margin-top-15">
									<spring:message text="Date" code="bnd.cert.date" />
									<span class="text-bold">&nbsp; <fmt:formatDate pattern="dd-MM-yyyy" value="${today}"/> &nbsp;</span>
								</div>
								<div class="col-xs-4 text-left">
									<p class="margin-top-10">
										<spring:message text="Signature of issuing authority"
											code="bnd.cert.sign" />
									</p>

									<p class="margin-top-10">
										<spring:message text="Seal" code="bnd.cert.seal" />
									</p>
								</div>
								<div class="clear"></div>
							</div>
						</div>
				</div>
				
				<input type="hidden" id="certificateNo" value="${command.tbDeathregDTO.drCertNo}">
			    <input type="hidden" id="brOrdrID" value="${brOrDrID}">
			    <input type="hidden" id="status" value="${status}">
			    <input type="hidden" id="bdId" value="${bdId}">
			    <input type="hidden" id="serviceCode" value="${serviceCode}">
						<div class="text-center hidden-print padding-10">
							<button onClick="printCertificate('printDiv');"
								class="btn btn-primary hidden-print">
								<i class="fa fa-print"></i>
								<spring:message code="print.cert" text="Print" />
							</button>
							<apptags:backButton url="PrintCertificate.html"></apptags:backButton>
						</div>

					</form:form>
				</div>
			</div>
			<!-- End Widget Content -->
		</div>
	</div>
</div>
<!-- End Content here -->

