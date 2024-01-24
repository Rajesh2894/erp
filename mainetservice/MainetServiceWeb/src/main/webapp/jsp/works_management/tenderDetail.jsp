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
<script type="text/javascript" src=""></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Update/Get Tender Details" />
			</h2>

			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>

		<!-- End Main Page Heading -->

		<!-- Start Widget Content -->
		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span> <spring:message code="works.fiels.mandatory.message" /></span>
			</div>
			<!-- End mand-label -->

			<!-- Start Form -->
			<form:form action="" class="form-horizontal" name="" id="">
				<!-- Start Validation include tag -->
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<!-- End Validation include tag -->

				<!-- Start Each Section -->

				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="" text="Project Name" /></label>
					<div class="col-sm-4">

						<form:select path="initiationDto.projId"
							cssClass="form-control chosen-select-no-results" id="projId">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${tenderProjects}" var="projArray">
								<form:option value="${projArray[0]}" code="${projArray[2]}">${projArray[1]}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label for="select-start-date"
						class="col-sm-2 control-label required-control"><spring:message
							code="project.master.dept" /> </label>
					<form:input path="" cssClass="form-control" id="" readonly="true"
						data-rule-required="" />
				</div>


				<div class="form-group">
					<label for="select-start-date"
						class="col-sm-2 control-label required-control"><spring:message
							code="" text="Initiation No." /> </label>
					<form:input path="" cssClass="form-control" id="" readonly=""
						data-rule-required="" />

					<label class="col-sm-2 control-label required-control"><spring:message
							code="" text="Initiation Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" id=""
								class="form-control mandColorClass datepicker" value=""
								readonly="" data-rule-required="" disabled="" />
						</div>

					</div>
				</div>

				<div class="form-group">
					<label for="select-start-date"
						class="col-sm-2 control-label required-control"><spring:message
							code="" text="Tender/Quotation No." /> </label>
					<form:input path="" cssClass="form-control" id="" readonly=""
						data-rule-required="" />

					<label class="col-sm-2 control-label required-control"><spring:message
							code="" text="Tender/Quotation Ref. Date" /></label>
					<form:input path="" id=""
						class="form-control mandColorClass datepicker" value=""
						readonly="" data-rule-required="" disabled="" />
				</div>



				<div class="form-group">
					<label for="select-start-date"
						class="col-sm-2 control-label required-control"><spring:message
							code="" text="Estimate Amount" /> </label>
					<form:input path="" cssClass="form-control" id="" readonly=""
						data-rule-required="" />

					<label for="select-start-date"
						class="col-sm-2 control-label required-control"><spring:message
							code="" text="Tender Amount" /> </label>
					<form:input path="" cssClass="form-control" id="" readonly=""
						data-rule-required="" />

				</div>

				<div class="form-group">
					<label for="select-start-date"
						class="col-sm-2 control-label required-control"><spring:message
							code="project.master.dept" /> </label>
					<div class="col-sm-4">
						<form:select path=""
							cssClass="form-control chosen-select-no-results mandColorClass"
							id="dpDeptId" onchange="" data-rule-required="true">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.departmentsList}" var="departments">
								<form:option value="${departments.dpDeptid }"
									code="${departments.dpDeptcode }">${departments.dpDeptdesc }</form:option>
							</c:forEach>
						</form:select>
					</div>

				</div>


				<!-- End Each Section -->

				<!-- Start button -->
				<div class="text-center clear padding-10">
					<button class="btn btn-success add" onclick="" type="button">
						<i class="fa fa-sign-out padding-right-5"></i>
						<spring:message code="works.management.save" text="" />
					</button>
					<button class="btn btn-warning reset" onclick="" type="button">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" text="" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style="" onclick=""
						id="button-Cancel">
						<i class="fa fa-chevron-circle-left padding-right-5"></i>
						<spring:message code="works.management.back" text="" />
					</button>
				</div>
				<!-- End button -->
			</form:form>
			<!-- End Form -->
		</div>
		<!-- End Widget Content here -->
	</div>
</div>
<!-- End of Content -->