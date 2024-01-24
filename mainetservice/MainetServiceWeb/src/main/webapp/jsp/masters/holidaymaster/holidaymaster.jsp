<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%
		response.setContentType("text/html; charset=utf-8");
	%>
<link href="css/mainet/calendar/dncalendar-skin.min.css"
	rel="stylesheet">
<link href="css/mainet/calendar/MonthPicker.css" rel="stylesheet">
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/masters/holidaymaster/dncalendar.js"></script>
<script type="text/javascript"
	src="js/masters/holidaymaster/holidaymaster.js"></script>
<script type="text/javascript" src="js/mainet/ui/MonthPicker.min.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<style>
.dncalendar-container .table {
	display: none !important;
}
</style>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="holidaymaster.search.worktimemas" />
			</h2>
			<apptags:helpDoc url="HolidayMaster.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
		<div class="mand-label clearfix">
		<span><spring:message code="contract.breadcrumb.fieldwith"
				text="Field with" /> <i class="text-red-1">*</i> <spring:message
				code="common.master.mandatory" text="is mandatory" /> </span>
	</div>
			<form:form action="HolidayMaster.html" method="post"
				class="form-horizontal" name="holidaymaster" id="holidaymaster">

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div id="a1" class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title table" id="">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="holidaymaster.search.worktimemas" /> </a>
							</h4>
						</div>


						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<label for="select-start-time"
										class="col-sm-2 control-label required-control"><spring:message
											code="holidaymaster.holidayAndworktimemas" /> </label>
									<div class="col-sm-4">
										<label class="radio-inline"><input type="radio"
											name="holidayTime" value="HolidayMaster" checked>
										<spring:message code="holidaymaster.holidaymas" /></label> <label
											class="radio-inline"><input type="radio"
											name="holidayTime" value="WorkTimeMaster">
										<spring:message code="holidaymaster.worktimemas" /></label>
									</div>
								</div>

								<div id="workTime">

									<div class="form-group">
										<label for="select-start-time"
											class="col-sm-2 control-label required-control"><spring:message
												code="holidaymaster.search.starttime" /> </label>
										<div class="col-sm-4">
											<form:input path="workTimeMasterEntity.wrStartTime"
												class="form-control datetimepicker3" id="wrStartTime"
												maxlength="10" readonly="true" />
										</div>

										<label for="select-end time"
											class="col-sm-2 control-label required-control"><spring:message
												code="holidaymaster.search.endtime" /> </label>
										<div class="col-sm-4">
											<form:input path="workTimeMasterEntity.wrEndTime"
												class="form-control datetimepicker3" id="wrEndTime"
												maxlength="10" readonly="true" />
										</div>
									</div>


									<div class="form-group">
										<label for="select-"
											class="col-sm-2 control-label required-control"><spring:message
												code="holidaymaster.search.selectioncriteria" /> </label>
										<div class="col-sm-4">
											<c:set var="baseLookupCode" value="WTT" />
											<form:select path="workTimeMasterEntity.wrWeekType"
												class="form-control" id="wrWeekType" readonly="true">
												<form:option value="0">
													<spring:message code="holidaymaster.select" />
												</form:option>
												<c:forEach items="${command.getLevelData(baseLookupCode)}"
													var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</form:select>
										</div>
									</div>


									<div class="form-group wrWorkWeek">

										<label for="checkbox-group"
											class="col-sm-2 control-label required-control"><spring:message
												code="holidaymaster.search.workweek" /> </label>

										<div class="col-sm-4">
											<form:input path="workTimeMasterEntity.wrWorkWeek"
												data-toggle="dropdown" class="form-control" id="wrWorkWeek"
												readonly="true" />
										</div>

									</div>


									<div class="form-group wrWorkWeekAlternate">

										<label for="checkbox-group"
											class="col-sm-2 control-label required-control"><spring:message
												code="holidaymaster.search.oddworkweek" /> </label>

										<div class="col-sm-4">

											<form:input path="workTimeMasterEntity.wrOddWorkWeek"
												data-toggle="dropdown" class="form-control"
												id="wrOddWorkWeek" readonly="true" />
										</div>

										<label for="checkbox-group"
											class="col-sm-2 control-label required-control"><spring:message
												code="holidaymaster.search.evenworkweek" /> </label>
										<div class="col-sm-4">

											<form:input path="workTimeMasterEntity.wrEvenWorkWeek"
												data-toggle="dropdown" class="form-control"
												id="wrEvenWorkWeek" readonly="true" />
										</div>
									</div>

									<div class="text-center clear padding-10">
										<button class="btn btn-success "
											onclick="openFormHoliday('HolidayMaster.html','EditHolidayMaster','W');"
											type="button">
											<i class="button-input"></i>
											<spring:message code="holidaymaster.edit" />
										</button>
									</div>
								</div>

								<div id="holidayMas">
									<div class="form-group ">

										<label for="" class="col-sm-2 control-label required-control"><spring:message
												code="holidaymaster.search.fromdate" /> </label>

										<div class="col-sm-4">

											<form:input path="" class="form-control lessthancurrdate "
												id="yearStartDate" readonly="true" dblclick="changeTodate()" />
											<form:hidden path="holidayMasterEntity.hoYearStartDate"
												id="hoYearStartDate" />
										</div>

										<label for="" class="col-sm-2 control-label required-control"><spring:message
												code="holidaymaster.search.todate" /> </label>
										<div class="col-sm-4">

											<form:input path="" class="form-control hoYearEndDate"
												id="yearEndDate" readonly="true" />
											<form:hidden path="holidayMasterEntity.hoYearEndDate"
												id="hoYearEndDate" />
										</div>
									</div>


									<div id="table-responsive"></div>
									<div class="text-center clear padding-10">
										<button type="button" class="button-input btn btn-warning"
											name="button-Save" onclick="saveGridData()" value="Save"
											style="" id="button-Save">
											<spring:message code="holidaymaster.search" />
										</button>

										<button type="button" class="button-input btn btn-default"
											name="button-1509602307721" value="Cancel"
											onclick="printHolidayDetails();" id="button-1509602307721">
											<spring:message code="holidaymaster.print" />
										</button>


										<button class="btn btn-success add"
											onclick="openFormHoliday('HolidayMaster.html','AddHolidayMaster','H');"
											type="button">
											<i class="button-input"></i>
											<spring:message code="holidaymaster.add" />
										</button>
										<button class="btn btn-success edit"
											onclick="openFormHoliday('HolidayMaster.html','EditHolidayMaster','H');"
											type="button">
											<i class="button-input"></i>
											<spring:message code="holidaymaster.edit" />
										</button>
										<button class="btn btn-warning" onclick="resetHolidayForm();"
											type="button">
											<i class="button-input"></i>
											<spring:message code="holidaymaster.reset" />
										</button>
									</div>

									<div id="calTablePrint"></div>
									<div id="" align="center">
										<table id="HolidayGrid"></table>
										<div id="HolidayPager"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>