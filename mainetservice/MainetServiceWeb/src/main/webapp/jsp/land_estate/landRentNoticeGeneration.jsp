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

<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->
<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="land.notice.noticeGenerate" text="Rent Notice Generation" />
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
										code="land.notice.noticeGenerateDetails" text="Rent Notice Generation Details" /> </a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
							<div class="form-group">
							<label class="col-sm-2 control-label" for="location"> <spring:message
											code="land.notice.location" />
									</label>
									<div class="col-sm-4 ">
										<form:select path="" id="landId"
											cssClass="form-control chosen-select-no-results" isMandatory="true"
										class="form-control mandColorClass" data-rule-required="true" disabled="${command.saveMode eq 'VIEW'}">
											<form:option value="0">select</form:option>
											<c:forEach items="${command.locationList}" var="location">
												<form:option value="${location.locId}">${location.locNameEng}</form:option>
											</c:forEach>
										</form:select>
									</div>
							
							
							</div>
							<div class="form-group">
							
									<apptags:input labelCode="land.notice.contract" 
										path="" cssClass="hasNumber1" maxlegnth="100"></apptags:input>
									<apptags:input labelCode="land.notice.tenant"
										path="" cssClass="hasCharacter" maxlegnth="100"></apptags:input>	
							</div>
							
							<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-blue-2" title="Search"
						id="searchNoticeGeneration">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.notice.search" text="Search" />
					</button>

					<button type="button"
						onclick="window.location.href=''"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="land.notice.reset" text="Reset" />
					</button>

				</div>
				<!-- End button -->
				
				<div class="table-responsive clear">
					<table class="table table-striped table-bordered"
						id="acqSummaryDatatables">
						<thead>
							<tr>
								<th width="3%" align="center"><spring:message
										code="land.notice.srNo" text="Sr.No" /></th>
								<th class="text-center"><spring:message
										code="land.notice.all" text="All" /></th>
								<th class="text-center"><spring:message
										code="land.notice.contract" text="Contract No." /></th>
								<th class="text-center"><spring:message
										code="land.notice.tenant" text="Tenant Name" /></th>
								<th class="text-center"><spring:message
										code="land.notice.amt" text="Outstanding Amount" /></th>
								<th class="text-center"><spring:message
										code="land.notice.count" text="Notice Count" /></th>
								
							</tr>
						</thead>
						<%-- <tbody>
							<c:forEach items="${}"
								var="" varStatus="">
								<tr>
									<td class="text-center">${}</td>
									<td class="text-center">${}</td>
									<td class="text-center">${}</td>
									<td class="text-center">${}</td>
									<td class="text-center">${}</td>
									<td class="text-center">${}</td>
									
									</tr>
							</c:forEach>
						</tbody> --%>
					</table>
					<!-- Start button -->
				<div class="text-center clear padding-10">
					<button type="button" class="btn btn-success" title=""
											onclick="">
											<i class="fa fa-sign-out padding-right-5" aria-hidden="true"></i>
											<spring:message code="land.notice.generate" text="Generate Notice" />
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