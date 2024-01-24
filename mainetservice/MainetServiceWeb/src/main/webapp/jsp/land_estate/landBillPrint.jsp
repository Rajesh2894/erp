<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="" src="js/land_estate/landEstate.js"></script>

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content animated slideInDown">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="land.acq.bill.title" text="Notice Printing " />
			</h2>
			<apptags:helpDoc url="LandAcquisition.html" />
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
			<form:form action="" cssClass="form-horizontal"
				id="" name="">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<!-- End Validation include tag -->
		
							
						
				<div class="form-group">
				
				<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.acq.bill.noticeType" text="Notice Type" />
									</label>
									<div class="col-sm-4 ">
										<form:select path="" id=""
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true">
											<form:option value="">select</form:option>
											</form:select>
									</div>
									<apptags:input labelCode="land.acq.bill.tanant" isMandatory="false"
										path="" cssClass="hasCharacter" maxlegnth="100"></apptags:input>
				</div>
				
				<div class="form-group">
				<apptags:input labelCode="land.acq.bill.noticeNo" isMandatory="false"
										path="" cssClass="hasCharacter" maxlegnth="100"></apptags:input>
										
					<apptags:input labelCode="land.acq.bill.contactNo" isMandatory="false"
										path="" cssClass="hasCharacter" maxlegnth="100"></apptags:input>					
										
				
				</div>
				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title="Search"
						id="searchlandAcquisition">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.acq.summary.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href='LandAcquisition.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.acq.summary.reset" text="Reset" />
					</button>
				</div>
				<!-- End button -->
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="acqSummaryDatatables">
						<thead>
							<tr>
								<th width="3%" align="center"><spring:message
										code="land.acq.summary.sr" text="Sr.No" /></th>
								<th class="text-center"><spring:message
										code="land.acq.bill.all" text="All" /></th>
								<th class="text-center"><spring:message
										code="land.acq.bill.noticeType" text="Notice Type" /></th>
								<th class="text-center"><spring:message
										code="land.acq.bill.noticeNo" text=" Notice No." /></th>
								<th class="text-center"><spring:message
										code="land.acq.bill.contactNo" text="Contact No." /></th>
								<th class="text-center"><spring:message
										code="land.acq.bill.tanant" text="Tanant Name" /></th>
								
							</tr>
						</thead>
						<tbody>
					</tbody>
				</table>
				<div class="text-center clear padding-10">
				<button type="button" class="btn btn-primary-2" title="Search"
						id="searchlandAcquisition">
						<i class="fa fa-print padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.acq.bill.print" text="Print" />
					</button>
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

		
		
		
		
		
		
		
		