<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<style>
.completion-cert .org-logo {
	position: relative;
}
.completion-cert .org-logo img {
	position: absolute;
}
.completion-cert ol li,
.completion-cert ol li:first-child {
	margin-top: 1.5rem;
	margin-left: 1.5rem;
}
.completion-cert ol li {
	margin-bottom: 1.5rem;
}
.completion-cert ol[type="i"] li {
	margin-left: 2rem;
}
.completion-cert .cert-outer-div {
	border: 1px solid #000000;
	padding: 2rem;
}
.completion-cert h3.smaller-h3 {
	font-size: 1.3rem;
}
.completion-cert .signtr {
	width: 19%;
}
.overflow-hidden {
	overflow: hidden;
}
</style>

<div class="content">
	<div class="widget" id="receipt">

		<div class="widget-content padding">
			<form:form action="" name="" id="" class="form-horizontal completion-cert">
			<div class="cert-outer-div">
				<div class="text-center text-uppercase">
						<h3 class="text-bold margin-bottom-0">
							<c:if test="${userSession.getLanguageId() eq 1}">
								<spring:message code=""
									text="${userSession.organisation.ONlsOrgname}" />
							</c:if>
							<c:if test="${userSession.getLanguageId() ne 1}">
								<spring:message code=""
									text="${userSession.organisation.ONlsOrgnameMar}" />
							</c:if>
						</h3>
						<h3 class="text-bold margin-bottom-0">
						<spring:message code="cfc.sono.compl.cer1"
							text="the preconception and pre-natal diagnostic" />
					</h3>
					<h3 class="text-bold margin-bottom-0">
						<spring:message code="cfc.sono.compl.cer2"
							text="techniques" />
					</h3>
					<h3 class="text-bold smaller-h3 margin-bottom-0">
						<spring:message code="cfc.sono.compl.cer3"
							text="(prohibition of sex selection)rules, 1996" />
					</h3>
					<h3 class="text-bold smaller-h3 margin-bottom-0">
						<spring:message code="cfc.sono.compl.cer4"
							text="original/duplicate for display" />
					</h3>
				</div>
				<div class="org-logo"><img src="${userSession.orgLogoPath}" width="80"></div>
				<div class="text-center">
					<h3 class="text-bold text-uppercase smaller-h3 margin-bottom-0">
						<spring:message code="cfc.sono.form.name"
							text="form 'b'" />
					</h3>
					<h3 class="text-bold text-uppercase smaller-h3 margin-bottom-0">
						<spring:message code="cfc.sono.compl.cer5"
							text="(See Rule 6(2),6(5),AND 8(2))" />
					</h3>
					<h3 class="text-bold text-uppercase margin-bottom-0 text-uppercase">
						<u><spring:message code="cfc.sono.compl.cer6"
							text="certificate of registration" /></u>
					</h3>
					<h3 class="text-bold margin-bottom-0">
						<spring:message code="cfc.sono.compl.cer7"
							text="To issue in duplicate" />
					</h3>
				</div>
				<div>
					<ol type="1">
						<li><p>	<spring:message code="cfc.sono.compl.cer19"
								text="In exercise of the powers conferred.." />
								<span class="text-bold text-underline padding-horizontal-5">${userSession.organisation.ONlsOrgname}</span>
								<spring:message code="cfc.sono.compl.cer20"
								text="hereby grants registration to the Genetic.." />
								<spring:message code="cfc.sono.compl.cer21"
								text="named below for purposes of carrying.." />
								<span class="text-bold text-underline padding-left-5">${command.expDate}</span>
							</p></li>
						<li>
							<p><spring:message code="cfc.sono.compl.cer22"
								text="This registration is granted subject to the aforesaid Act.." /></p>
							<ol type="A">
								<li>
									<spring:message code="cfc.sono.compl.cer11"
								 text="Name and address of the Genetic Counselling Center/Genetic Laboratory/Genetic Clinic/Ultrasound Clinic/Imaging Center:-" />
								<span class="text-bold text-underline padding-left-5">${command.cfcSonographyMastDto.centerName} , ${command.cfcSonographyMastDto.centerAddress}</span>
								</li>
								<li>
									<spring:message code="cfc.sono.compl.cer12"
								text="Pre-Natal diagnostic procedures approved for(Genetic Clinic)" /></br>
									<b><spring:message code="cfc.sono.compl.cer13"
								text="Non-Invasive" /></b></br>
									<ol type="i">
										<li>Ultrasound Invasive</li>
										<b><spring:message code="cfc.sono.compl.cer17"
							         	text="Invasive" /></b></br>
										<li>Amniocentesis</li>
										<li>Chorionic cilli aspiration</li>
										<li>Foetoscopy</li>
										<li>Foetal skin or organ bioscopy</li>
										<li>Cordocentesis</li>
										<li>Any Other (Specify)</li>
									</ol>	
								</li>
								<li><spring:message code="cfc.sono.compl.cer14"
								text="Pre-Natal diagnostic Tests approved (For Genetic Laboratory)" /></br>
									<ol type="i">
										<li>Chromosomal Studies</li>
										<li>Biochemical Studies</li>
										<li>Molecular Studies</li>
									</ol>	
								</li>
								<li>
									<spring:message code="cfc.sono.compl.cer15"
								text="Any other Purpose (Please Specify)" /></br>
								</li>
								
							</ol>
						</li>
						
						<li>
							<spring:message code="cfc.sono.compl.cer16"
						text="Model and make of equipments being used (any change is to be intimated to the Appropriate Authority under rule 13):-" />
					   <span class="text-bold text-underline padding-left-5">${command.cfcSonographyMastDto.diagProcedure}</span>
						</li>
						
						<li class="text-bold">
							<spring:message code="cfc.sono.compl.cer8"
							text="Registration No.alloted." />
							<span class="text-bold  padding-left-5">${command.cfcApplicationMst.apmApplicationId}</span>
						</li>	
						<li class="text-bold">
							<spring:message code="cfc.sono.compl.cer9"
							text="Period of validity of earlier certificate of Registration" />
							<spring:message code="cfc.sono.compl.cer10"
							text="For Renewed Certificate of Registration only" /> 
						   <span class="text-bold  padding-left-5">From <fmt:formatDate pattern="dd/MM/yyyy" value="${command.newDate}"/>
						   To <fmt:formatDate pattern="dd/MM/yyyy" value="${command.todate}"/></span>
						</li>
					</ol>
				</div>
				<div class="space" style="margin-top:80px"></div>
				<div class="overflow-hidden text-bold">
					<p class="pull-left">Date: </p>
					<p class="margin-bottom-0 text-center pull-right signtr">
						<spring:message code="cfc.sono.compl.cer18"
							text="Signature, name and Designation of The Appropriate Authority" />
					</p>
				</div>
				</div>
			</form:form>
		</div>	
		
		
			<div class="text-center">
					<button onclick="printContent('receipt')"
						class="btn btn-primary hidden-print">
						<i class="icon-print-2"></i>
						<spring:message code="cfc.print" text="Print"></spring:message>
					</button>
	
					<button type="button" class="btn btn-danger hidden-print"
						onclick="window.location.href='AdminHome.html'">

						<spring:message code="NHP.back" text="Back"></spring:message>
					</button>
				</div>
		
	</div>
</div>	