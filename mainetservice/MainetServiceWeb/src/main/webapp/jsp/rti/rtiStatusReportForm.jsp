<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/rti/rtiStatusReport.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="rti.status.report" text=" RTI Status Report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="rti.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="RtiStatusReport.html" cssClass="form-horizontal"
				id="rtiFormReport">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="rti.status.report.department" text="Department" /></label>

					<div class="col-sm-4">
						<c:choose>
							<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
								<form:select path="rtiFormDto.rtiDeptId" id="rtiDeptId"
									class="form-control chosen-select-no-results"
									data-rule-required="true">

									<form:option value="0">Select</form:option>
									<c:forEach items="${command.deparmnetDtoList}" var="department">
										<form:option value="${department.dpDeptid}">${department.dpDeptdesc}</form:option>
									</c:forEach>
								</form:select>
							</c:when>

							<c:otherwise>
								<form:select path="rtiFormDto.rtiDeptId" id="rtiDeptId"
									class="form-control chosen-select-no-results"
									data-rule-required="true">

									<form:option value="0">Select</form:option>
									<c:forEach items="${command.deparmnetDtoList}" var="department">
										<form:option value="${department.dpDeptid}">${department.dpNameMar}</form:option>
									</c:forEach>
								</form:select>
							</c:otherwise>
						</c:choose>


					</div>



					<label class="col-sm-2 control-label required-control"
						for="inwardType"> <spring:message
							code="rti.status.report.mode" text="Mode" /></label>
					<c:set var="baseLookupCode" value="RIT" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="rtiFormDto.inwardType" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="true"
						selectOptionLabelCode="applicantinfo.label.select"
						isMandatory="true" showOnlyLabel="Mode" />
				</div>





				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="rti.status.report.sla" text="SLA" /></label>

					<div class="col-sm-4">
						<form:select path="WorkflowRequestDto.applicationSlaDurationInMS"
							id="applicationSlaDurationInMS"
							class="form-control chosen-select-no-results"
							data-rule-required="true">

							<form:option value="0">
								<spring:message code="rti.selects" text="Select" />
							</form:option>
							<form:option value="-1">
								<spring:message code="rti.all" text="All" />
							</form:option>
							<form:option value="W">
								<spring:message code="rti.withInSLA" text="WithInSLA" />
							</form:option>
							<form:option value="B">
								<spring:message code="rti.beyondSLA" text="BeyondSLA" />
							</form:option>

						</form:select>
					</div>



					<label class="col-sm-2 control-label required-control"><spring:message
							code="rti.status.report.rtiStatus" text="RTI Status" /></label>

					<div class="col-sm-4">
						<form:select path="WorkflowRequestDto.status" id="status"
							class="form-control chosen-select-no-results"
							data-rule-required="true">

							<form:option value="0">
								<spring:message code="rti.selects" text="Select" />
							</form:option>
							<form:option value="-1">
								<spring:message code="rti.all" text="All" />
							</form:option>
							<form:option value="PENDING">
								<spring:message code="rti.pending" text="Pending" />
							</form:option>
							<form:option value="CLOSED">
								<spring:message code="rti.closed" text="Closed" />
							</form:option>


						</form:select>
					</div>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"> <spring:message
							code="rti.status.report.from.date" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="rtiFormDto.fromDate" id="fromDate"
								class="form-control mandColorClass datepicker dateValidation"
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="fromDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=fromDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="rti.status.report.to.date" text="To Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="rtiFormDto.toDate" id="toDate"
								class="form-control mandColorClass datepicker dateValidation "
								value="" readonly="false" data-rule-required="true"
								maxLength="10" />
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=toDate></label>
						</div>
					</div>
				</div>



				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveForm(this)">
						<spring:message code="legal.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetForm"
						onclick="window.location.href='RtiStatusReport.html'">
						<spring:message code="legal.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="legal.btn.back"></spring:message>
					</a>
				</div>
			</form:form>
		</div>
	</div>
</div>








