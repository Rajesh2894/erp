<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/mainet/validation.js"></script>

<script src="js/legal/actandrulemaster.js"></script>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!-- Start breadcrumb Tags -->
<apptags:breadcrumb></apptags:breadcrumb>
<!-- End breadcrumb Tags -->

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="lgl.actrule.masterform"
						text="actandrulemasterform" /></strong>
			</h2>

		</div>

		<div class="widget-content padding">

			<!-- Start mand-label -->
			<div class="mand-label clearfix">
				<span><spring:message code="lgl.fieldwith" /><i
					class="text-red-1">* </i> <spring:message code="lgl.ismandatory" />
				</span>
			</div>
			<!-- End mand-label -->

			<form:form action="ActAndRuleMaster.html" method="POST"
				class="form-horizontal form" name="actAndRuleMaterForm"
				id="id_actAndRuleMaterForm">


				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"> <spring:message
										code="lgl.actrule.details" text="Act/Rule Information" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<apptags:input labelCode="lgl.actrule.no" cssClass=""
										path="actRuleMasterDTO.actNo" isMandatory="true" />
									<apptags:input labelCode="lgl.actrule.name"
										cssClass="actRuleMasterDTO.actName" path="" isMandatory="true" />
								</div>
								<div class="form-group">
									<apptags:input labelCode="lgl.actrule.title"
										path="actRuleMasterDTO.actTitle" isMandatory="true"
										cssClass="" />
								</div>
								<div class="form-group">
									<apptags:date labelCode="lgl.actrule.activefrmdate"
										fieldclass="datepicker" datePath="actRuleMasterDTO.activeFromDate"
										isMandatory="true"></apptags:date>
									<apptags:date labelCode="lgl.actrule.activetodate"
										fieldclass="datepicker" datePath="actRuleMasterDTO.activeToDate"
										isMandatory="true"></apptags:date>
								</div>
								<div class="form-group">
									<apptags:textArea labelCode="lgl.actrule.desc"
										path="actRuleMasterDTO.actDesc" cssClass="maxLength200"
										isMandatory="true" />
									<apptags:textArea labelCode="lgl.actrule.ref"
										path="actRuleMasterDTO.actRef" cssClass="maxLength200" />
								</div>
							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse"> <spring:message
										code="lgl.addenduminfo" text="Addendum Information" />
								</a>
							</h4>
						</div>

						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="num" value="0" scope="page"></c:set>
								<table
									class="table table-striped table-condensed table-bordered"
									id="id_actAndRuleTbl">
									<thead>
										<tr>
											<th scope="col" width="3%"><spring:message
													code="lgl.srno" text="Sr. No." /></th>
											<th scope="col" width="13%"><spring:message
													code="lgl.actrule.addendumno" text="Addendum No." /><span
												class="mand">*</span></th>
											<th scope="col" width="13%"><spring:message
													code="lgl.actrule.addendumtitle" text="Addendum Title" /><span
												class="mand">*</span></th>
											<th scope="col" width="8%"><spring:message
													code="lgl.actrule.addendumyear" text="Addendum Year" /><span
												class="mand">*</span></th>
											<th scope="col" width="13%"><spring:message
													code="lgl.actrule.addendumdate" text="Addendum Date" /><span
												class="mand">*</span></th>
											<th scope="col" width="13%"><spring:message
													code="lgl.actrule.addendumdetails" text="Addendum Details" /><span
												class="mand">*</span></th>
											<th scope="col" width="6%"><a href="javascript:void(0);"
												data-toggle="tooltip" data-placement="top"
												onclick="addEntryData('id_actAndRuleTbl');"
												class=" btn btn-success btn-sm"><i
													class="fa fa-plus-circle"></i></a></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td align="center"><form:input path=""
													cssClass="form-control mandColorClass " id="sequence${num}"
													value="${num+1}" disabled="true" /></td>

											<td align="center"><form:input path=""
													onkeyup="inputPreventSpace(event.keyCode,this);"
													style="margin: 0px; height: 33px;" cssClass="form-control"
													maxlength="4000" id="addNo${num}" disabled=""
													data-rule-required="true" /></td>
											<td align="center"><form:input path=""
													style="margin: 0px; height: 33px;" cssClass="form-control"
													maxlength="4000" id="addTitle${num}" disabled=""
													data-rule-required="true" /></td>

											<td align="center"><form:input path=""
													style="margin: 0px; height: 33px;" cssClass="form-control"
													maxlength="4000" id="addYear${num}" disabled=""
													data-rule-required="true" /></td>
											<td align="center"><form:input path=""
													cssClass="form-control  mandColorClass datepicker text-center"
													id="addDate${num}" /></td>
											<td align="center"><form:input path=""
											style="margin: 0px; height: 33px;" cssClass="form-control"
													maxlength="4000" id="addDetails${num}" disabled=""
													data-rule-required="true" /></td>
											<td align="center"><a href='#' data-toggle="tooltip"
												data-placement="top" class="btn btn-danger btn-sm delButton"
												onclick="deleteEntry('id_actAndRuleTbl',$(this),'removedIds')">
													<i class="fa fa-minus"></i>
											</a></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<!-- Start button -->

				<div class="text-center clear padding-10">
					<button type="submit" class="button-input btn btn-success"
						onclick="confirmToProceed(this)" name="button-submit" style=""
						id="button-submit">
						<spring:message code="" text="Submit" />
					</button>
					<apptags:resetButton></apptags:resetButton>
					<apptags:backButton url="ActAndRuleMaster.html"></apptags:backButton>

				</div>
				<!-- End button -->
			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>
<!-- End of Content -->

