<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript"
	src="js/additionalServices/cfcSchedulingTrx.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">

		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
				class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
			</a>
		</div>
		<div class="widget-content padding">
			<form:form action="CFCSchedulingTrx.html" method="post"
				class="form-horizontal" name="cfcSchedulingTrxSummary"
				id="cfcSchedulingTrxSummary">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<h4 class="margin-top-0">
					<spring:message code="SFT.collection.mast" text="Collection Master" />
				</h4>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="SFT.collection.center.no" text="Collection Center No." /></label>

					<div class="col-sm-4">
						<form:input path="cfcCounterScheduleDto.cmCollncentreno"
							id="collncentreno" class="form-control mandColorClass hasNumber"
							maxlength="6" readonly="true" />
					</div>

					<label class="col-sm-2 control-label required-control"><spring:message
							code="SFT.collection.denter.desc"
							text="Collection Center Description" /></label>

					<div class="col-sm-4">
						<form:input path="cfcCounterScheduleDto.cmDescription"
							id="collectiondesc" class="form-control mandColorClass"
							maxlength="200" readonly="true" />
					</div>

				</div>


				<div class="form-group">

					<c:set var="baseLookupCode" value="CWZ" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="CWZ" hasId="true"
						pathPrefix="cfcCounterScheduleDto.cfcWard"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="false"
						isMandatory="true" disabled="true" />


				</div>

				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a4"> <spring:message
								code="cfc.easytap.det" text="Easy Tap Details" /></a>
					</h4>
					<div id="a4" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">


								<apptags:input labelCode="cfc.deviceId"
									path="cfcCounterScheduleDto.deviceId" maxlegnth="50"></apptags:input>

							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a4"> <spring:message
								code="SFT.counter.det" text="Counter Details" /></a>
					</h4>
					<div id="a4" class="panel-collapse collapse in">
						<div class="panel-body">
							<c:set var="d" value="0" scope="page"></c:set>
							<table class="table table-bordered table-striped"
								id="counterDetails">

								<thead>
									<tr>
										<th class="col-sm-2 required-control" style="text-align: left"><spring:message
												code="SFT.counter.no" text="Counter No."></spring:message></th>
										<th class="col-sm-4 required-control" style="text-align: left"><spring:message
												code="SFT.counter.desc" text="Description"></spring:message></th>


									</tr>
								</thead>						

								<tbody>


									<tr class="counterEntryRow">
										<td><form:input
												path="cfcCounterScheduleDto.cuCountcentreno" id=""
												class="form-control mandColorClass hasNumber" maxlength="6"
												readonly="true" /></td>

										<td><form:input
												path="cfcCounterScheduleDto.cuDescription" id=""
												class="form-control mandColorClass" maxlength="200"
												readonly="true" /></td>
									</tr>


								</tbody>
							</table>
						</div>
					</div>
				</div>
				<form:hidden path="status" id="status" />

				<div class="panel panel-default">
					<h4 class="panel-title table" id="">
						<a data-toggle="collapse" class=""
							data-parent="#accordion_single_collapse" href="#a4"> <spring:message
								code="SFT.schedule.det" text="Schedule Details" /></a>
					</h4>
					<div id="a4" class="panel-collapse collapse in">
						<div class="panel-body">

							<table class="table table-bordered table-striped"
								id="ScheduleDetails">

								<thead>
									<tr>
										<th class="col-sm-4" style="text-align: left"><spring:message
												code="SFT.from.date" text="From Date & Time"></spring:message></th>
										<th class="col-sm-4" style="text-align: left"><spring:message
												code="SFT.to.date" text="To Date & time"></spring:message></th>
										<th class="col-sm-2" style="text-align: left"><spring:message
												code="SFT.user.name" text="User Name"></spring:message></th>
										<th class="col-sm-2" style="text-align: left"><spring:message
													code="" text="Daily Frequency"></spring:message></th>	
										<th class="col-sm-1" style="text-align: left"><spring:message
												code="SFT.status" text="Status"></spring:message></th>



									</tr>
								</thead>

								<tbody>

									<tr class="scheduleEntryRow">
										<td><div class="input-group">
												<form:input path="cfcCounterScheduleDto.csFromTime"
													id="fromDate"
													class="form-control mandColorClass fromDate"
													onchange="" />
												<label class="input-group-addon"><i
													class="fa fa-calendar"></i><span class="hide"> <spring:message
															code="" text="icon" /></span><input type="hidden"></label>
											</div></td>


										<td><div class="input-group">
												<form:input path="cfcCounterScheduleDto.csToTime"
													id="endDate"
													class="form-control mandColorClass toDate"
													 onchange="" />
												<label class="input-group-addon"><i
													class="fa fa-calendar"></i><span class="hide"> <spring:message
															code="" text="icon" /></span><input type="hidden"></label>
											</div></td>
										<td><form:select path="cfcCounterScheduleDto.csUserId"
												class="form-control chosen-select-no-results" id="empId">
												<form:option value="">Select</form:option>
												<c:forEach items="${command.empList}" var="emp">
													<form:option value="${emp.empId}">${emp.empname}</form:option>
												</c:forEach>
											</form:select></td>

										<td><form:select
												path="cfcCounterScheduleDto.frequencySts"
												class="form-control" id="frequencySts" >
												<form:option value="Y">
													<spring:message code='' text="Yes" />
												</form:option>
												<form:option value="N">
													<spring:message code='' text="No" />
												</form:option>
											</form:select></td>

										<td class="text-center"><form:checkbox
												path="cfcCounterScheduleDto.csStatus" id="check"
												checked="${command.cfcCounterScheduleDto.csStatus eq 'A'? 'checked' : '' }"
												value="" onchange="statusChange(this)" /></td>

									</tr>

								</tbody>
							</table>
						</div>
					</div>
				</div>


				<div class="text-center clear padding-10">

					<button class="btn btn-success  submit"
						onclick="saveCounterSchedule(this)" id="Submit" type="button"
						name="button" value="save">
						<i class="button-input"></i>
						<spring:message code="CFC.save" text="Save" />
					</button>

					<button type="back" class="btn btn-danger" onclick="backForm()">
						<spring:message code="NHP.back" />
					</button>

				</div>
			</form:form>
		</div>
	</div>
</div>
