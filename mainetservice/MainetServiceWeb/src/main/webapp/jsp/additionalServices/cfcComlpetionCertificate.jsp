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

<style>
.org-logo {
	position: relative;
}
.org-logo img {
	position: absolute;
}
.content_1{
	font-size:20px;
}
</style>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget" id="receipt">

		<div class="widget-content padding">
			<form:form action="" name="" id="" class="form-horizontal" style="border-style:solid; border-width:1px; padding:15px">
				<div class="text-center"><h3 class="margin-bottom-0 text-uppercase">
						<spring:message code="cfc.form.name"
							text="form-c" />
					</h3>
					(<strong class="margin-bottom-0">
							<spring:message code="cfc.see.rule"
							text="See Rule 5" />
					</strong>)</br>
				</div>	
				<div class="org-logo">
					<img src="${userSession.orgLogoPath}" width="80">
				</div>

			<div class="content_1">
				<div class="text-center">
						
						<h3 class="margin-bottom-0 text-uppercase"><u>
									<c:if test="${userSession.languageId eq 1}">
										<spring:message code=""
											text="${userSession.organisation.ONlsOrgname}" />
									</c:if>
									<c:if test="${userSession.languageId ne 1}">
										<spring:message code=""
											text="${userSession.organisation.ONlsOrgnameMar}" />
									</c:if>
									</u>
						
						</h3>
					<strong class="margin-bottom-0">
						<spring:message code="cfc.complete.cer.content1"
							text="Certificate of Registration under Section 5 of the Bombay " />
					</strong></br>
					<strong class="margin-bottom-0">
						<spring:message code="cfc.complete.cer.content2"
							text="Nursing Home Registration Act, 1949." /></br></br></br></br>
					</strong>
					</br>
				</div>
					
				
				
					<strong><spring:message code="cfc.complete.cer.content3"
							text="Total Beds -" />${command.cfcNuringHomeInfoDto.totalBedCount}
					</strong> 
					
					</br>
					</br>
					
					<strong> <spring:message code="cfc.complete.cer.content4"
							text="This is to certify that " /><u>
						${command.cfcNuringHomeInfoDto.doctorName}</u> <spring:message
							code="cfc.complete.cer.content5"
							text="has been registered under the Bombay  Nursing Home Registration Act, 1949. in respect of" /><u> ${command.cfcNuringHomeInfoDto.nameAddClinic}</u>
						<spring:message code="cfc.complete.cer.content6"
							text="Situated at " /><u>${command.cfcNuringHomeInfoDto.nameAddHospital}</u>
						<spring:message code="cfc.complete.cer.content7"
							text="and has been Authorised to carry on the same Nursing Home." />
					</strong></br></br>
				
				
				</br>			
				
				<table>
 				<strong> 
 					<tr>
 						<td>
 							<strong><spring:message code="cfc.complete.cer.content8"
							text="Registration No " />
 						</td>
 						
 						<td>
 							<strong>&nbsp;:-&nbsp; ${command.cfcApplicationMst.apmApplicationId}</strong>
 						</td>
 					</tr>
 					
 					<tr>
 						<td>
 							<strong><spring:message
							code="cfc.complete.cer.content9" text="Date Of Registration" />
 						</td>
 						
 						<td>
 							 <strong>&nbsp;:-&nbsp; <fmt:formatDate pattern="dd/MM/yyyy" value="${command.cfcApplicationMst.apmApplicationDate}"/></strong>
 						</td>
 					</tr>
 					
 					<tr>
 						<td>
 							<strong><spring:message code="cfc.complete.cer.content10" text="Place " />
 						</td>
 						
 						<td>
 							<strong>&nbsp;:-&nbsp; <spring:message code="cfc.complete.cer.content20"
							text="MOH office, KDMC, Kalyan" />
 						</td>
 					</tr>
 					
 					<tr>
 						<td>
 							<strong><spring:message
							code="cfc.complete.cer.content11"
							text="Date Of Issue of Certificate" />
 						</td>
 						
 						<td>
 							 <strong>&nbsp;:-&nbsp; <fmt:formatDate pattern="dd/MM/yyyy" value="${command.newDate}"/></strong>
 						</td>
 					</tr>
 					</strong> 
 				</table></br>
				

				<p>
					<strong> <spring:message code="cfc.complete.cer.content12"
							text="The Certificate of registration shall be valid up to 31st March" /> ${command.year}
					<p>( <spring:message code="cfc.complete.cer.content22"
							text="Thirty First March" /> ${command.yearWord})
					</p>
					</strong>
				</p>
				
				</br>
				
				<p>
					<strong> <spring:message code="cfc.complete.cer.content13"
							text="Name of the Local" /></br> <spring:message
							code="cfc.complete.cer.content14"
							text="Supervising Authority :- " />&nbsp;
									<c:if test="${userSession.languageId eq 1}">
										<spring:message code=""
											text="${userSession.organisation.ONlsOrgname}" />.
									</c:if>
									<c:if test="${userSession.languageId ne 1}">
										<spring:message code=""
											text="${userSession.organisation.ONlsOrgnameMar}" />.
									</c:if>		
					</strong>
				</p>
		</div>
 				
				</br>
				

				<p style="font-size:14px;">
					<strong><spring:message
									code="cfc.complete.cer.content15" text="Note:" /> </strong></br>
									
					<strong><spring:message code="cfc.complete.cer.content16"
								text="This registration is issued without prejudice " /></strong></br>
					
					<strong><spring:message
									code="cfc.complete.cer.content17"
									text="to the action by the  K.D.M.C. under M.R.T.P. Act " /></strong></br>	
									
					<strong><spring:message
									code="cfc.complete.cer.content18"
									text="1966 along with Development Control Rules & " /></strong></br>	
									
					<strong><spring:message
									code="cfc.complete.cer.content19"
									text="Regulations of M.P.M.C. Act." /></strong></br>									
				</p>
				
				<div class="space" style="margin-top:130px"></div>

		<strong><div class="text-right content_1">
					<p><spring:message code="cfc.complete.cer.ord"
							text="By Order," /></p>
					<p><spring:message code="cfc.complete.doctorName"
							text="(Dr. Smita A. Rode)" /></p></p>
					<p>
						<spring:message code="cfc.complete.cer.content21"
							text="I/C Medical Officer of Health" />
					</p>
					<p><c:if test="${userSession.languageId eq 1}">
										<spring:message code=""
											text="${userSession.organisation.ONlsOrgname}" />
									</c:if>
									<c:if test="${userSession.languageId ne 1}">
										<spring:message code=""
											text="${userSession.organisation.ONlsOrgnameMar}" />
									</c:if></p>
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