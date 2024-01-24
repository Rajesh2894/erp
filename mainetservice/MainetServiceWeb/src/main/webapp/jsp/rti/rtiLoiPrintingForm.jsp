<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/rti/rtiLoiPrintingForm.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<style>
.widget {
	padding: 40px;
}

.widget-content {
	border: 1px solid #000;
}

/*  div img {
	width: 35%;
}  */

 div img {
	width: 80px !important;
	height: 80px !important;
}

.invoice .widget-content.padding {
	padding: 20px;
}

.border-black {
	border: 1px solid #000;
	padding: 10px;
	min-height: 180px;
	width: 75%;
}

.padding-left-50 {
	padding-left: 50px !important;
}
.padding-right-50{padding-right: 50px !important;}
@media print{@page {size: portrait;}.padding-right-50{padding-right: 40px !important;}}

body {
	font-size: 20px !important;
}

.form-horizontal div ol li {
	margin-bottom: 10px;
}

/* .form-horizontal row div img {
	width: 80px !important;
	height: 80px !important;
} */

.margin-top-100 {  
	margin-top: 100px !important;
}

.border-bottom-solid {
	border-bottom: 1px solid #505458;
} 
</style>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rti.information.to.applicant"
					text="LOI Printing" />
			</h2>
		</div>
		<div class="widget-content padding">
			<%-- <div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="rti.fiels.mandatory.message" text="" /></span>
			</div> --%>

			<form:form action="LoiPrintingReport.html" cssClass="form-horizontal"
				id="rtiLoiPrintingForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


             <div class="row">
			    <div class="col-md-2">
					<img src="${userSession.orgLogoPath}"/>
				</div>
                <div class="col-md-8 text-center">
					<h3>
						<spring:message code=""
							text="<b>${userSession.organisation.ONlsOrgname}</b>" />
					</h3>
				</div>
				<div class=" col-md-2 text-right">
					<p>
						<span class="margin-left-20"> <spring:message
								code="rti.information.report.Content32"
								text="Date:" />
						</span>
						<%-- <b>${command.dateDescription}</b> --%>
						 <b>${command.cfcEntity.apmApplicationDate}</b>
					</p>
				</div>
			</div>
			<!-- <div class="border-bottom-solid"></div> -->
				<div class="row">
					<div class="col-xs-12 text-center">
						<h3 class="text-bold margin-top-20">
							<spring:message code="rti.information.report.Content1"
								text="Intimation To Applicant to deposit fee 
							                        and charges for required information and/or documents" />
						</h3>

					</div>
				</div>
			<div class="border-bottom-solid"></div>
             
              
              
              
       <!--  <div class="row margin-top-20 clear">
					
				</div> -->
              
              <br></br>
              
     <div class="row clear margin-top-100 margin-bottom-12">
		<div class="col-sm-2 col-xs-2">
			<p>
				  <b><spring:message code="rti.information.report.Content2" text="To" /></b>
			</p>
		</div>
	</div>

	<div class="row clear margin-bottom-12">
		<div class="col-sm-2 col-lg-2 col-xs-2">
			<p>
				<b><spring:message code="rti.information.report.Content3" text="Shri/Smt./Kum." /></b>

			</p>
		</div>
		<div class="col-sm-10 col-xs-10 col-lg-10 text-left">
			<p>
				<b>${command.cfcEntity.apmFname}</b>
		</div>
	</div>

	<div class="row clear margin-bottom-10">
		<div class="col-sm-2  col-lg-2 col-xs-2">
			<p>
				<b><spring:message code="rti.information.report.Content4" text="Address:" /></b>

			</p>
		</div>
		<div class="col-sm-10 col-xs-10 col-lg-10 text-left">
			<p>
				<b>${command.cfcAddressEntity.apaAreanm}</b>
			</p>
		</div>
	</div>
<br></br>
	<div class="row margin-top- clear">
		<div class="col-sm-2 col-xs-2">
			<p>
				<b><spring:message code="rti.information.report.Content5"
					text="Sir," /></b>
			</p>
		</div>
	</div>

<br></br>
	<div class="row margin-top-20 clear">
		<div class=" col-sm-12 margin-bottom-30">
			<p>
				<span class="margin-left-20"> <spring:message
						code="rti.information.report.Content6"
						text="With reference to your request/application dt." /></span>
						 <b>${command.dateDescription}</b>
				<spring:message code="rti.information.report.Content7"
					text="(I.D.No" />
				<b>${command.cfcEntity.apmApplicationId}</b>
				<spring:message code="rti.information.report.Content8"
					text="dtd)" />
				<%-- <b>${command.rtiLoidto}</b>) --%>
				<spring:message code="rti.information.report.Content9"
					text="Iam to state that you are required to deposit Rs." />
				<b>${command.rtiInformationApplicantDto.amountToPay}</b>
				<spring:message code="rti.information.report.Content10"
					text="(in words Rupeees" />
				<b>${command.amountInWords}</b>
				<spring:message code="rti.information.report.Content11"
					text="only) for required information and documents sought for.
					It is request to obtain the copies of the required information/documents after depositing the amount in this Department/office." />
			</p>
		</div>
	</div>
<br></br>

		
		
				
					<div class="col-sm-offset-2 col-sm-8 margin-top-20" center>
						<ol>
							<li>
								<div class="col-sm-10">
									<p> <spring:message code="rti.information.report.content12" />
									<b>${command.a3a4Quantity}
									
									</b>
									<spring:message code="rti.information.report.content13" />
									<spring:message code="rti.information.report.content14" /></p>
								</div>
								<div class="col-sm-2 padding-right-0">
								<fmt:formatNumber type="number" value="${command.pageQuantity1}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="pageQuantity1"/>
									<p>
										<span>Rs:</span>
										<span class="pull-right"><b>${pageQuantity1}</b></span>
									</p>
								</div>
							</li>
							<li class="clear">
								<div class="col-sm-10">
									<p>
										<spring:message code="rti.information.report.content15" />
									                   <b>${command.largeCopy}</b> 
						               <spring:message code="rti.information.report.content16" />
									</p>
								</div>
								<div class="col-sm-2 padding-right-0">
								<fmt:formatNumber type="number" value="${command.pageQuantity2}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="pageQuantity2"/>
								<p>
									<span>Rs:</span>
									<span class="pull-right"><b>${pageQuantity2}</b></span>
								</p>
								</div>
							</li>
					
							<li class="clear">
							<div class="col-sm-10"><p><spring:message code="rti.information.report.content17" />
								          <%--    <b>${command.a3a4Quantity}</b> --%>
								     <spring:message code="rti.information.report.content18" /></p></div>
							<div class="col-sm-2 padding-right-0">
								<p>
									<span>Rs:</span>
									<span></span>
								</p>	
							</div>
							</li>
							<li class="clear">
							<div class="col-sm-10"><p><spring:message code="rti.information.report.content19" />
								             <b>${command.flopCopy}</b>  
									    <spring:message code="rti.information.report.content20" /></p></div>
							<div class="col-sm-2 padding-right-0">
							<fmt:formatNumber type="number" value="${command.pageQuantity3}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="pageQuantity3"/>
								<p>
									<span>Rs:</span>
									<span class="pull-right"><b>${pageQuantity3}</b></span>  
								</p>
							</div>
							</li>
					
							<li class="clear">
							<div class="col-sm-10"><p><spring:message code="rti.information.report.content21" /></p></div>
							<div class="col-sm-2 padding-right-2">
								<p>
									<span>Rs:</span>
									<span></span>
								</p>
							</div>
							</li>
				        	<li class="clear">
							<div class="col-sm-10"><p><spring:message code="rti.information.report.content22"/>
								             <b>${command.inspection}</b>  
									    <spring:message code="rti.information.report.Content33" /></p></div>
							<div class="col-sm-2 padding-right-0 border-bottom-solid">
							<fmt:formatNumber type="number" value="${command.pageQuantity4}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="pageQuantity4"/>
								<p>
									<span>Rs:</span>
									<span class="pull-right"><b>${pageQuantity4}</b></span> 
								</p>
							</div>
							</li>
							<%-- <li class="clear">
							<div class="col-sm-10"><p> <spring:message code="rti.information.report.content22" /></p></div>
							<div class="col-sm-2 padding-right-0 border-bottom-solid"><p>Rs:  <b>${command.pageQuantity4}</b> </p></div>
							</li> --%>
						</ol>
					</div>
					
					<div class="clear"></div>
					<div class="col-sm-offset-2 col-sm-10 col-xs-10 col-xs-offset-10">
					<ul>
					<li><div class="col-sm-8 col-xs-8 margin-bottom-30">
					    <p class="margin-left-30">Total</p>
					</div></li>
					<li><div class="col-sm-2 col-xs-2 margin-bottom-30 padding-right-50">
					<fmt:formatNumber type="number" value="${command.grandTotal}" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2" var="grandTotal"/>
					    <p class="">
					    <span class="">Rs:</span><span class="pull-right text-bold">${grandTotal}</span></p>
					   </div></li>
					</ul>
					</div>
				<br></br>
				<br></br>
				<br></br>
				
					
						
				
				
		<div class="clear row">
			<div class="col-xs-4 col-xs-offset-8 col-sm-4 col-lg-3 col-sm-offset-8 col-lg-offset-9 margin-top-100">
				<spring:message code="rti.information.report.content25"
					text="Yours faithfully," />	<br></br>
					<br></br>
				<%-- <spring:message code="rti.information.report.content26"
					text="(signature)" />	 --%>	
					
					<div class="margin-bottom-10">
	               <spring:message code="rti.information.report.content26"
					text="(signature)" />	
	               	</p>
               	</div>
						<div class="margin-bottom-10">
	               	<p>
	               		<b>${command.pioName}</b>
	               	</p>
	               	<p>
	               		<spring:message code="rti.information.report.content27"
						text="(Public Information Officer)" />
	               	</p>
               	</div>
			    <div class="margin-bottom-10">
	               	<p>
						<b>${command.department.dpDeptdesc}</b>	
	               	</p>
	               	<p>
	               		<spring:message code="rti.information.report.content28"
					text="(Name of Department/Office)" />
	               	</p>
               	</div> 
				<div class="margin-bottom-10">
					<spring:message code="rti.information.report.content29"
					text="Telephone No:" />
					<b>${command.cfcAddressEntity.apaMobilno}</b>
				</div>
				<div class="margin-bottom-10">
					<spring:message code="rti.information.report.content30"
						text="e-mail:" />
					<b>${command.cfcAddressEntity.apaEmail}</b>
				</div>
				<div>
                	<spring:message code="rti.information.report.content31"
					text="Website:" />
					<%-- <b>${command}</b> --%>
				</div>
			</div>
		</div>

			</form:form>

		</div>
	</div>
</div>








