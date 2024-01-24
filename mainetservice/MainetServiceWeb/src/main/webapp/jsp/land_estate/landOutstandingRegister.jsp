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
<!-- <script type="" src="js/land_estate/landEstate.js"></script> -->
<script type="text/javascript" src="js/land_estate/landEstateBill.js"></script>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="land.outstanding.title" text="Outstanding Register" />
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
										code="land.outstanding.details" text="Outstanding Register Details" /> </a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
							
							<div class="form-group">
						<%-- 	<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.outstanding.zone" /> 
									</label>
							<div class="col-sm-4 ">
										<form:select path="acquisitionDto.locId" id="locId"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true">
											<form:option value="0">Select Zone</form:option>
											</form:select>
									</div>	
						<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.outstanding.ward" /> 
									</label>
									<div class="col-sm-4 ">
										<form:select path="acquisitionDto.locId" id="locId"
											cssClass="form-control chosen-select-no-results"
											class="form-control mandColorClass" data-rule-required="true"
											>
											<form:option value="0">Select Ward</form:option>
											</form:select>
									</div> --%>					
							
							
									<c:set var="baseLookupCode" value="LZB" />
										<apptags:lookupFieldSet
										cssClass="form-control required-control" baseLookupCode="LZB"
										hasId="true" pathPrefix="acquisitionDto.couEleWZId"
										hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true" isMandatory="true"
										disabled="${command.saveMode eq 'V'}" />	
							
							</div>
							<div class="form-group">
							<%-- <apptags:date fieldclass="datepicker" labelCode="land.outstanding.date"
										datePath="acquisitionDto.acqDate"
										readonly="${command.saveMode eq 'VIEW'}"
										isDisabled="${command.saveMode eq 'VIEW'}"></apptags:date> --%>
										
										 <label class="col-sm-2 control-label required-control" for=""
											id="dispoDate"><spring:message
												code="land.outstanding.date" /></label>
										 	<div class="col-sm-4">
											<div class="input-group">
												<form:input class="form-control datepicker" placeholder="dd-mm-yyyy"
													id="receiptDate" data-label="#dispoDate"
													path="" isMandatory="true"
													onchange=""></form:input>
												<span class="input-group-addon"><i
													class="fa fa-calendar"></i></span>
											</div>
										</div>
								</div>
							</div>
											<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title="Search"
						id="searchlandAcquisition">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.revenue.viewReport" text="View Report" />
					</button>

					<button type="button"
						onclick="landRevenueReport('LandBill.html','landOutstandingRegister');"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.revenue.reset" text=" Reset" />
					</button>
				
				</div>
				<!-- End button -->
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