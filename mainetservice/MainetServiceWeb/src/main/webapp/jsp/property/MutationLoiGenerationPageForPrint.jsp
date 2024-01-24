<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/property/mutation.js"></script>

<style>
	.text-left {
		text-align: left !important;
	}
	.width-16 {
		width: 16%;
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
<ol class="breadcrumb">
		<li><a href="AdminHome.html"><spring:message code="cfc.home" text="Home"/></a></li>
		<li><a href="javascript:void(0);"><spring:message code="property.property" text="Property tax"/></a></li>
		<li><a href="javascript:void(0);"><spring:message code="cfc.transaction" text="Transactions"/></a></li>
		<li class="active"><spring:message text="Mutation Authorization" code="propety.mutation.auth" /></li>
	</ol>
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
	<div class="widget-header">

			<%-- <c:if test="${command.noOfDaysAuthFlag eq 'Y'}">
				<h3 align="center">This application is under period of public
					notice . This application cannot be approved</h3>
			</c:if>
			
			<c:if test="${command.noOfDaysAuthFlag eq 'I'}">
				<h3 align="center">This application is under period of objection. This application cannot be approved</h3>
			</c:if> --%>
			<h2><strong><spring:message code="propertyTax.Mutation" text="Mutation"/></strong></h2>				
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide"><spring:message code="property.Help"/></span></i></a>
				</div>
	</div>
	
	<!-- End Main Page Heading -->
		
<!-- Start Widget Content -->
	<div class="widget-content padding">
	
		
		<!-- Start mand-label -->
		<div class="mand-label clearfix">
			<span><spring:message code="property.Fieldwith"/><i class="text-red-1">*</i><spring:message code="property.ismandatory"/>
			</span>
		</div>
		<!-- End mand-label -->
		
		<!-- Start Form -->
		<div id="receipt">
		<form:form action="MutationAuthorization.html"
					class="form-horizontal form mutationLoiGeneration" name="MutationAuthorization"
					id="MutationAuthorization">	
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
			
			<form:hidden  path="" id="isLastAuth" class="form-control" value="${command.isLastAuthorizer()}"/>
			<form:hidden  path="" id="isBeforeLastAuth" class="form-control" value="${command.isBeforeLastAuthorizer()}"/>
			<form:hidden  path="" id="bypassPublicNotice" class="form-control" value="${command.getBypassPublicNotice()}"/>
			<%-- Defect #155273 --%>
			<form:hidden  path="" id="authFlag" class="form-control" value="${command.noOfDaysAuthFlag}"/>
			<!-- Start Each Section -->

			<!-- Applicant Information START -->
				
					
				<!-- Applicant Information END -->
			<div class="accordion-toggle">


			<!-- End Each Section -->

<div class="row">
				<div class="col-xs-3 col-sm-3">
					<img src="${userSession.orgLogoPath}" alt="Org Logo" width="80"/>
				</div>
				<div class="col-xs-6 col-sm-6 text-center">
					<h2 class="margin-bottom-0">${userSession.organisation.ONlsOrgname}</h2>
					<h3>Letter of Intent</h3>
				</div>
				<div class="col-xs-3 col-sm-3 text-right">
					<img src="assets/img/up-gov.png" alt="UP Logo" width="100"/>
				</div>
			</div>
<br>
<p>Date: &nbsp ${command.date}</p>
<br>

<p>LOI NO: &nbsp ${command.loiNo}</p>

<br>

					<p4><spring:message code="loi.message" text=" "/></p4>
<div class="table-responsive">
						<table class="table table-bordered table-striped">
<tr>
<th class="text-left"  ><span><spring:message code="" text="Transfer Type:"/></span></th>
<td colspan="2">${command.propTransferDto.transferTypeDesc}</td>
<th class="text-left"  colspan="2" ><spring:message code="" text="House No"/></th>
<td>${command.provisionalAssesmentMstDto.tppPlotNo}</td>
</tr>



<tr>
<th class="text-left" scope="row" width="120"><span><spring:message code="" text="Property ID No. :"/></span></th>
<td colspan="2">${command.provisionalAssesmentMstDto.assNo}</td>
<th class="text-left" width="160" colspan="2"><span><spring:message code="" text="PTIN No :"/></span></th>
<td>${command.provisionalAssesmentMstDto.assOldpropno}</td>
</tr>


<tr>
<th class="text-left" width="100" colspan="1"><span><spring:message code="" text="Applicant Name:"/></span></th>
<td colspan="9">${command.provisionalAssesmentMstDto.ownerFullName}</td>
</tr>


<tr>
<th class="text-left width-16" scope="row"><span><spring:message code="" text="Zone :"/></span></th>
<td class="width-16">${command.provisionalAssesmentMstDto.assWardDesc1}</td>
<th class="text-left width-16"><span><spring:message code="" text="Ward :"/></span></th>
<td class="width-16">${command.provisionalAssesmentMstDto.assWardDesc2}</td>
<th class="text-left width-16"><span><spring:message code="" text="Mohalla :"/></span></th>
<td class="width-16">${command.provisionalAssesmentMstDto.assWardDesc3}</td>
</tr>

	
<tr>
<th class="text-left" width="160" colspan="1"><span><spring:message code="" text="Applicant No:"/></span></th>
<td colspan="9">${command.propTransferDto.apmApplicationId}</td>
</tdater>
</table>
</div>
					<c:if test="${command.getApprovalFlag() eq 'Y' && not empty command.propTransferDto.charges}">  
   <h4 class="margin-top-10 margin-bottom-10 panel-title ">
   <a data-toggle="collapse" href="#TaxCalculation" class="contentRemove"><spring:message code="Fee Details" text="Fee Details"/></a>
    </h4>         
    <div class="panel-collapse collapse in" id="TaxCalculation">      
     
      <c:set var="totPayAmt" value="0"/>
            <div class="table-responsive">
              <table class="table table-striped table-condensed table-bordered">
                <tbody>
                  <tr>
                    <th width="50"><spring:message code="propertyTax.SrNo"/></th>
                    <th width="600"><spring:message code="propertyTax.TaxName"/></th>
                    <th width="400"><spring:message code="propertyTax.Total"/></th>
                  </tr>
                <c:forEach var="tax" items="${command.propTransferDto.charges}"  varStatus="status">
                  <tr>
                  <td>${status.count}</td>
                  <td>${tax. getTaxDesc()}</td>
                  <td class="text-right">${tax.getTotalTaxAmt()}</td>
                </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
            <div class="table-responsive margin-top-10">
              <table class="table table-striped table-bordered">
                <tr>
                  <th width="500"><spring:message code="propertyTax.TotalTaxPayable"/></th>
                  <th width="500" class="text-right">${command.propTransferDto.billTotalAmt}</th>
                </tr>
              </table>
            </div>
          </div>
      </c:if> 				
					
		
					
  
        
        <c:if test="${command.showFlag eq 'Y' }">
					<h4>LOI Fees and Charges in Details</h4>
					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<tr>
								<th scope="col" width="80">Sr. No</th>
								<th scope="col">Charge Name</th>
								<th scope="col">Amount</th>
							</tr>
							<c:forEach var="charges" items="${command.loiDetail}"
								varStatus="status">
								<tr>
									<td>${status.count}</td>
									<td>${charges.loiRemarks}</td>
									<td><fmt:formatNumber value="${charges.loiAmount}"
											type="number" var="amount" minFractionDigits="2"
											maxFractionDigits="2" groupingUsed="false" /> <form:input
											path="" type="text" class="form-control text-right"
											value="${amount}" readonly="true" /></td>
								</tr>
							</c:forEach>
							
							<spring:eval
										expression="T(com.abm.mainet.common.utility.CommonMasterUtility).getValueFromPrefixLookUp('ASCL','ENV',${UserSession.organisation}).getOtherField()"
										var="otherField" />
										<c:if test="${command.getServiceShortCode() eq 'MUT'}">
										<c:if test="${otherField eq 'Y' }">
										<td colspan="2"><span class="pull-right"><b>Total=
												</b></span></td>
									<td class="text-right">${command.totalAmntIncApplFee}</td>
										<tr>
									<td colspan="2"><span class="pull-right"><b>Already paid Application fee
												</b></span></td>
									<td class="text-right">-${command.applicationFee}</td>

								</tr>
										
										</c:if>
										</c:if>
								<tr>
								<td colspan="2"><span class="pull-right"><b>Total Payable amount</b></span></td>
								<td class="text-right">${command.totalLoiAmount}</td>

							</tr>
						</table>
					</div>
				</c:if>
        
        
				<!--T#92833 Applicant Details -->
				<%-- <c:if test="${command.getCheckListVrfyFlag() eq 'N'}"> --%>
			<!--END Applicant Details -->
			<c:set var="loopCount" value='0' />
		
			<!--  End button -->
		</div>
		
				
				<div class="text-center hidden-print padding-20">
					<button onclick="printdiv('receipt')"
						class="btn btn-primary">
						<i class="fa fa-print"></i>
						<spring:message code="trade.print" text="Print" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" onclick="window.location.href='AdminHome.html'"
						id="button-Cancel">
						<spring:message code="trade.back" text="Back" />
					</button>
				</div>
					</form:form>
					<style>
						@media print {
							@page {
								margin: 20px;
							}
						}
					</style>
					</div>

		</div>
	
		</div>
			</div>
<!-- End of Content -->
		<!-- End Form -->


