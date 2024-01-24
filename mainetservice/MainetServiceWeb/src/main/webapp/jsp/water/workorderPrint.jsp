<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript" src="js/water/workorderPrintGrid.js">
<!-- <script src="js/siteinspection/siteinspection.js"></script> -->
<script type="text/javascript">
	HTMLElementObject.contentEditable = false
</script>
<style>
.nwc-work-order .applDate{
	float: right;
}
.nwc-work-order .payment-details-table table tr th{
	background: #044c87;
    color: #fff;
}
.nwc-work-order .plumber-table{
	padding: 0 15rem;
}
.nwc-work-order .payment-details-table{
	padding: 0 10rem;
}
.nwc-work-order table>tbody>tr>td{
    border: 1px solid #000 !important;
}
#footer {
   bottom:0;
   width:100%;
   height:30px;  
   background:#3f48cc;
   color:white;
   padding-top: 0.3rem;
}
</style>
<!--  <ol class="breadcrumb">
      <li><a href="../index.html"><i class="fa fa-home"></i></a></li>
      <li>Work Order Printing</li>
    </ol> -->
<!-- ============================================================== -->
<!-- Start Content here -->
<!-- ============================================================== -->
<div class="animated slideInDown">
	<!-- Start info box -->
	<div class="widget" id="letter">
		<div class="widget-content padding nwc-work-order">
			<div class="form-group clearfix">
				<div class="text-center">
					<h2 class="text-bold">
						<img src=".\assets\img\work_order_header.png" style="width:95%;">
					 </h2>	
				</div>
				<!-- <div class="col-xs-3 text-left">
					<img src="assets/img/logo.png">
				</div> -->
				<div class="col-xs-12 text-center margin-top-10">
					<h4 class="margin-bottom-0">${userSession.ULBName.lookUpDesc}</h4>
				</div>
				<!-- <div class="col-xs-3 text-right">
					<img src="assets/img/logo.png">
				</div> -->
			</div>
		 <div class="padding-30">
			<div class="form-group clearfix">
				<%-- <div class="col-xs-6 text-left">
					<p>No. - ${WorkOrderNumber}</p> --%>
					<input type="hidden" value="${WorkOrderNumber}"
						id="WorkOrderNumber" />
				<!-- </div> -->
				<div class="col-xs-4 col-sm-3 applDate">
					<p><span class="text-bold"><spring:message code="nwc.certi.application.no" text="Application No.:" /></span>${ApplicationID}</p>			
					<p><span class="text-bold"><spring:message code="nwc.certi.date" text="Date:" /></span>${NewDate}</p>
			     </div>
			</div>
			<%-- <div class="form-group clearfix margin-top-20">
				<div class="col-xs-1"><spring:message code="demand.from" text="From"/></div>
				<div class="col-xs-11">
					<p><spring:message code="water.orderr.munc.eng" text="Municipal Engineer"/></p>
					<p>${userSession.ULBName.lookUpDesc}</p>
				</div>
			</div> --%>
			<div class="form-group clearfix margin-top-20">
				<div class="text-bold">To,</div>
			</div>
			<div class="form-group clearfix margin-top-20">
				<div class="col-xs-12">
					<p> <spring:message code="nwc.certi.Name" text="Name: " />  ${ApplicantFullName}</p>
					<p>
						<spring:message code="water.meterReadingViewDetails.Address" /> <i class="fa fa-map-marker fa-1x red" aria-hidden="true"></i>:
						${applicantAddress}
					</p>
				</div>
			</div>
			<div class="form-group clearfix">
				<p class="margin-top-15"><span class="text-bold">Subject: </span><spring:message code="water.order.subject" text="Work order details for New Tap Connection"/> </p>
				<p class="margin-top-15">
					<span class="text-bold">Reference: </span> Your Application for New Tap Connection Application number:
					${ApplicationID} and Application Date: ${ApplicationDate} is
					approved and assigned.
					<%-- and your
					connection no.is ${ConnectionNo} --%>
				</p>
			</div>
			<div class="form-group clearfix">
				<p class="margin-bottom-20">
					Sir/Madam,<br>
					<br> 		
					In the context of the application number ${ApplicationID},The new tap connection application has been approved by
					the Municipal Corporation and sent to further process. An amount of Rs. ${chargestotal} for installation has been 
					received by the ULB. Authorized Plumber has been assigned for installation of pipe line. 
										
				</p>
				<p class="text-bold margin-bottom-10"><spring:message code="water.order.plum.details" text="Details of assigned Plumber:"/></p>
				<div class="form-group clearfix plumber-table">
					<table class="table table table-bordered" style="width:100%">
						<tr>
							<td class="text-left" width="50%"><spring:message code="water.plumberName" text="Plumber Name"/></td>
							<td>${PlumName}</td>
						</tr>	
						<tr>
							<td class="text-left" width="50%"><spring:message code="water.order.contact.details" text="Contact Details"/></td>
							<td>${PlumContactNo}</td>
						</tr>	
					</table>
				</div>
				
				<p class="text-bold margin-bottom-10"><spring:message code="water.order.payment.details" text="Payment Details:"/></p>
				<div class="form-group clearfix">
					<!-- <table class="table table-message"> -->
					  <div class="payment-details-table">
						<table class="table table table-bordered">
							<tr>
								<th class="text-left-imp"><spring:message code="water.nodues.service" text="Service"/></th>
								<th class="text-center"><spring:message code="water.order.rupees" text="Amount"/></th>
							</tr>

							<c:forEach items="${Chargedescruption}" var="singleDoc"
								varStatus="count">
								<tr>
									<td><strong> ${singleDoc.key}</strong></td>
									<td class="text-center"><strong>${singleDoc.value}</strong></td>
								</tr>
							</c:forEach>
							<tr>
								<td><p class="text-right text-bold"><spring:message code="water.nonMeterBillprint.Total" text="Total"/></p></td>
								<td><p class="text-center text-bold">${chargestotal}</p></td>
							</tr>

						</table>
					  </div>	

						<%-- <p class="margin-bottom-10 margin-top-10"><spring:message code="trutirejection.rejctionremark"/></p> --%>
						<%-- <p class="padding-vertical-15">
							<strong>Terms &amp; Conditions </strong>
						</p>
						<table class="table">

							<c:forEach items="${TermsConditon}" var="singleDoc"
								varStatus="count">
								<tr>
									<td width="50">${count.index+1}</td>
									<td>${singleDoc.artRemarks}</td>
								</tr>
							</c:forEach>

						</table> --%>

						<div class="form-group clearfix padding-vertical-15">

							<c:if test="${PrintReprintFlg == 'Y'}">
								<div class="col-xs-4">
									<%-- <p class="padding-top-30">Copy To: Plumber Name</p>
									<div class="barcode padding-top-10">${PlumName}</div> --%>
								</div>
								<div class="col-xs-4 text-center" id="Duplicat">
									<i class="fa fa-files-o fa-2x red" aria-hidden="true"></i> <strong><spring:message code="water.order.duplicate.copy" /> </strong>
								</div>

							</c:if>

							<c:if test="${PrintReprintFlg == null}">
								<%-- <div class="col-xs-8">
									<p class="padding-top-30"><spring:message code="water.order.copy.plumber.name" text="Copy To: Plumber Name"/></p>
									<div class="barcode padding-top-10 bold">${PlumName}</div>
								</div> --%>
							</c:if>
							<%-- <div class="col-xs-4 text-center">
								<p>By Order,</p>
								<p class="margin-top-15"><spring:message code="water.orderr.munc.eng" text="Municipal Engineer"/></p>
								<p>${userSession.organisation.ONlsOrgname}</p>
							</div> --%>
						</div>
				  </div>			
				 </div>
				</div>
				<div class="col-xs-12 col-sm-12 footer-div margin-bottom-10">
		 			<div id="footer" class="text-center"><spring:message code="nwc.certi.footer.content" text="Urban Administration & Development, Department"/></div>
				</div>	
				<div class="text-center clearfix margin-top-10"
					id="hideprintbuttton">
					<c:if test="${PrintReprintFlg == null}">
						<button onclick="printContent2('letter')"
							class="btn btn-primary hidden-print printClass">
							<i class="icon-print-2"></i>
							<spring:message code="siteinspection.print" />
						</button>
					</c:if>

					<c:if test="${PrintReprintFlg == 'Y'}">
						<button onclick="printContent2('letter')"
							class="btn btn-primary hidden-print printClass">
							<i class="icon-print-2"></i><spring:message code="water.order.reprint" text="Reprint"/> 
						</button>
					</c:if>
					<!--   <input type="button" class="btn btn-default" value="Back" onclick="window.location.href='WorkOrder.html'"> -->
					<!--  <input type="button"
				onclick="window.location.href='WorkOrder.html'"
				class="btn btn-default hidden-print" value="Back"> -->
					<input type="button"
						onclick="window.location.href='WaterWorkOrderPrinting.html'"
						class="btn btn-danger hidden-print" value="Back">
				</div>
		</div>
		<style>
			@media print {
			  @page {
				  margin-bottom: 0.2in !important; 
				}
				#footer{
					 width: calc(100% - 30px) !important;
					 margin: 2px 15px 0 15px;
				}
				#footer-div{
					bottom: 0;
				}
			  }
		</style>
	</div>