<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="" src="js/land_estate/landEstate.js"></script>
<script type="" src="js/land_estate/landEstateBill.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="land.acq.rentReminderNotice" text="Rent Reminder Notice" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<!-- End Main Page Heading -->
		<!-- Start Widget Content -->
		<div class="widget-content padding">
			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="fiels.mandatory.message" text="Field with * is mandatory" /></span>
			</div>
			<!-- End mand-label -->
			<!-- Start Form -->
			<form:form action="LandAcquisition.html" cssClass="form-horizontal"
				id="landAcqId">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="land.acq.rentReminderNoticeDetails" text="Rent Reminder Notice Details" /> </a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
							
						<div class="form-group">
						<%-- 	
					    <div class="text-center" style="font-size:medium;"><b><spring:message
										code="land.acq.TMC" text="Thane Municipal Corporation" /></b>
										<p style="float: right;"><spring:message
										code="land.acq.date" text="Date" /></p>	
						</div> --%>
						<div class="text-center col-sm-offset-2 col-sm-8">
						<h3 class="text-bold margin-top-0"><spring:message
										code="land.acq.TMC" text="Thane Municipal Corporation" /></h3>
						<div class="text-bold"><spring:message
										code="land.acq.rentReminderNotice" text="Rent Reminder Notice" /></div>
					</div>
						<div class="col-sm-2 padding-left-0 padding-right-0">
						<div>
							<span class="text-bold col-lg-6 padding-left-0"><spring:message
										code="land.acq.date" text="Date:" /></span>
							<span class="col-lg-6 padding-left-0 padding-right-0">31-08-2016</span>
						</div>
						<div>
							<span class="text-bold col-lg-6 padding-left-0"><spring:message
										code="land.acq.time" text="Time:" /></span>
							<span class="col-lg-6 padding-left-0 padding-right-0">11:39:36</span>
						</div>
						<div>
							<span class="text-bold col-lg-6 padding-left-0"><spring:message
										code="land.acq.pageNo" text="Page No:" /></span>
							<span class="col-lg-6 padding-left-0 padding-right-0">1</span>
						</div>
					</div>
						 <%-- <p class="text-center"><spring:message
										code="land.acq.rentReminderNotice" text="Rent Reminder Notice" /></p>	 --%>
						
						<div class="clear"></div>	
							<hr/>
							<div class="col-sm-offset-10 col-sm-2 padding-left-0 padding-right-0">
						<div>
							<span class="text-bold col-lg-6 padding-left-0"><spring:message
										code="land.acq.noticeNo" text="Notice No.:" /></span>
							<span class="col-lg-6 padding-left-0 padding-right-0">GMCO00000004</span>
						</div>
						<div>
							<span class="text-bold col-lg-6 padding-left-0"><spring:message
										code="land.acq.noticeDate" text="Notice Date" /></span>
							<span class="col-lg-6 padding-left-0 padding-right-0">03-12-2015</span>
						</div>
					</div>
					<div class="clear"></div>
					<p>To,</p>
					<div class="margin-left-20">
						<p>Mr. Sushil</p>
						<p>Gaya</p>
						<p>Reference :- Contract No. <span class="text-bold">GMCC00000009</span> Dated <span class="text-bold">03-12-2015</span></p>
					</div>
					<p class="margin-top-10">Sir/Madam,</p>
					<div class="margin-left-20">
						<p>This is to inform you to make payment of rent as per details given below:</p>
					</div>
					<table class="table table-bordered margin-top-10 margin-bottom-15">
						<thead>
							<tr>
								<th class="text-center"><spring:message
										code="land.acq.rentNo" text="Rent No." /></th>
								<th class="text-center"><spring:message
										code="land.acq.repaymentDate" text="Repayment Date" /></th>
								<th class="text-center"><spring:message
										code="land.acq.repaymentAmount " text="Repayment Amount" /></th>
							</tr>
						</thead>
						<tbody>
							 <tr>
								<td>RB4</td>
								<td>13-12-2015</td>
								<td class="text-right">0.00</td>
							</tr>
							<tr>
								<td>RB4</td>
								<td>13-12-2015</td>
								<td class="text-right">19.80</td>
							</tr>
						</tbody>
					</table>
					<div>
						<p>Kindly arrange to make payments against the rent of this notice, if not already paid.</p>
					</div>
					<div class="text-bold pull-right margin-top-30">
						<p class="margin-top-30">
							<span>Thane Municipal Corporation</span>
						</p>
					</div>		
						<div class="text-center clear padding-10">
				<button type="button" class="btn btn-primary-2" title="Search"
						id="searchlandAcquisition">
						<i class="fa fa-print padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.acq.bill.print" text="Print" />
					</button>
					
					<button type="button" class="button-input btn btn-danger"
										name="button-Cancel" value="Cancel" style=""
										onclick="window.location.href='AdminHome.html'"
										id="button-Cancel">
										<i class="fa fa-chevron-circle-left padding-right-5"></i>
										<spring:message code="bt.backBtn" text="Back" />
									</button>
									
									<button type="button" class="btn btn-primary"
						onclick="landRentNoticeGeneration('LandBill.html','landRentNoticeGeneration');"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.acq.summary.add1" text="landRentNoticeGeneration" />
		         			</button>
		         			
		         			<button type="button" class="btn btn-primary"
						onclick="billPayment('LandBill.html','billPayment');"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.acq.summary.add1" text="Bill Payment" />
		         			</button>
		         			
		         			<button type="button" class="btn btn-primary"
						onclick="searchReport('LandBill.html','searchReport');"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.acq.summary.add1" text="search Report" />
		         			</button>
		         			
		         			
		         			<button type="button" class="btn btn-primary"
						onclick="landRevenueReport('LandBill.html','landRevenueReport');"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.acq.summary.add1" text="Revenue Report" />
		         			</button>
		         			
		         			<button type="button" class="btn btn-primary"
						onclick="landRevenueReport('LandBill.html','landOutstandingRegister');"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.acq.summary.add1" text="Outstanding Report" />
		         			</button>
		         			
		         			<button type="button" class="btn btn-primary"
						onclick="landRevenueReport('LandBill.html','landDefaulterRegister');"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.acq.summary.add1" text="Defaulter Report" />
		         			</button>
									
				              </div>	
									
							</div>
							</div>
						</div>
					</div>
				</div>
							
				</form:form>
			<!-- End Form -->
			
		</div>
		<!-- End Widget Content here -->
	</div>
	<!-- End Widget  here -->
</div>
<!-- End of Content -->
				