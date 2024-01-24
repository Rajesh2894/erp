<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="css/mainet/calendar/dncalendar-skin.min.css"
	rel="stylesheet">
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/masters/holidaymaster/dncalendar.js"></script>
	<script type="text/javascript"
	src="js/masters/holidaymaster/addholidaymaster.js"></script>
<%	response.setContentType("text/html; charset=utf-8"); %>
<style>
.dncalendar-container .table {
	display: none !important;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
<div class="widget">
	<div class="widget-header">
		<h2> <spring:message code="holidaymaster.search.worktimemas"/></h2>
	</div>

	<div class="widget-content padding">
		<form:form action="HolidayMaster.html" method="post"
			class="form-horizontal" name="holidaymaster" id="holidaymaster">

			<div
				class="warning-div error-div alert alert-danger alert-dismissible"
				id="errorDiv"></div>
			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div id="a1" class="panel panel-default checkForData">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="" class=""
								data-parent="#accordion_single_collapse" href="#a1"><spring:message
									code="holidaymaster.search.worktimedet" /></a>
						</h4>
					</div>
					<div id="a1" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label for="select-start-time"
									class="col-sm-2 control-label required-control"><spring:message
										code="holidaymaster.search.starttime" /> </label>
								<div class="col-sm-4">
									<form:input path="workTimeMasterEntity.wrStartTimeString"
										class="form-control datetimepicker3 " id="wrStartTime"
										maxlength="10" />
								</div>

								<label for="select-end time"
									class="col-sm-2 control-label required-control"><spring:message
										code="holidaymaster.search.endtime" /></label>
								<div class="col-sm-4">
									<form:input path="workTimeMasterEntity.wrEndTimeString"
										class="form-control datetimepicker3" id="wrEndTime"
										maxlength="10" />
								</div>
							</div>

							<div class="form-group">
								<label for="select-"
									class="col-sm-2 control-label required-control"><spring:message
										code="holidaymaster.search.selectioncriteria" /> </label>
								<div class="col-sm-4">
									<c:set var="baseLookupCode" value="WTT" />
									<form:select path="workTimeMasterEntity.wrWeekType"
										class="form-control" id="wrWeekType" onChange="hideOption()">
										<form:option value="0">
											<spring:message code="holidaymaster.select"/>
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>

							<form:hidden path="workTimeMasterEntity.worrkWeekFlag"
								id="worrkWeekFlag" />

							<div class="form-group wrWorkWeek">

								<label for="checkbox-group"
									class="col-sm-2 control-label required-control"><spring:message
										code="holidaymaster.search.workweek" /></label>

								<div class="col-sm-4">
									<form:input path="workTimeMasterEntity.wrWorkWeek"
										data-toggle="dropdown" class="form-control" id="wrWorkWeek" />
									<ul class="dropdown-menu workWeek">

										<li><input name="selector3" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Monday" />&nbsp;<spring:message
												code="holidaymaster.mon" /></li>
										<li><input name="selector3" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Tuesday" />&nbsp;<spring:message
												code="holidaymaster.tue" /></li>
										<li><input name="selector3" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Wednesday" />&nbsp;<spring:message
												code="holidaymaster.wed" /></li>
										<li><input name="selector3" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Thursday" />&nbsp;<spring:message
												code="holidaymaster.thu" /></li>
										<li><input name="selector3" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Friday" />&nbsp;<spring:message
												code="holidaymaster.fri" /></li>
										<li><input name="selector3" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Saturday" />&nbsp;<spring:message
												code="holidaymaster.sat" /></li>
										<li><input name="selector3" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Sunday" />&nbsp;<spring:message
												code="holidaymaster.sun" /></li>
									</ul>
								</div>
							</div>

							<div class="form-group wrWorkWeekAlternate">

								<label for="checkbox-group"
									class="col-sm-2 control-label required-control"><spring:message
										code="holidaymaster.search.oddworkweek" /> </label>

								<div class="col-sm-4">

									<form:input path="workTimeMasterEntity.wrOddWorkWeek"
										data-toggle="dropdown" class="form-control" id="wrOddWorkWeek" />
									<ul class="dropdown-menu oddWorkWeek">

										<li><input name="selector1" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Monday" />&nbsp;<spring:message
												code="holidaymaster.mon" /></li>
										<li><input name="selector1" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Tuesday" />&nbsp;<spring:message
												code="holidaymaster.tue" /></li>
										<li><input name="selector1" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Wednesday" />&nbsp;<spring:message
												code="holidaymaster.wed" /></li>
										<li><input name="selector1" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Thursday" />&nbsp;<spring:message
												code="holidaymaster.thu" /></li>
										<li><input name="selector1" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Friday" />&nbsp;<spring:message
												code="holidaymaster.fri" /></li>
										<li><input name="selector1" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Saturday" />&nbsp;<spring:message
												code="holidaymaster.sat" /></li>
										<li><input name="selector1" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Sunday" />&nbsp;<spring:message
												code="holidaymaster.sun" /></li>

									</ul>

								</div>

								<label for="checkbox-group"
									class="col-sm-2 control-label required-control"><spring:message
										code="holidaymaster.search.evenworkweek" /> </label>

								<div class="col-sm-4">

									<form:input path="workTimeMasterEntity.wrEvenWorkWeek"
										data-toggle="dropdown" class="form-control"
										id="wrEvenWorkWeek" />
									<ul class="dropdown-menu evenWorkWeek">

										<li><input name="selector2" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Monday" />&nbsp;<spring:message
												code="holidaymaster.mon" /></li>
										<li><input name="selector2" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Tuesday" />&nbsp;<spring:message
												code="holidaymaster.tue" /></li>
										<li><input name="selector2" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Wednesday" />&nbsp;<spring:message
												code="holidaymaster.wed" /></li>
										<li><input name="selector2" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Thursday" />&nbsp;<spring:message
												code="holidaymaster.thu" /></li>
										<li><input name="selector2" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Friday" />&nbsp;<spring:message
												code="holidaymaster.fri" /></li>
										<li><input name="selector2" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Saturday" />&nbsp;<spring:message
												code="holidaymaster.sat" /></li>
										<li><input name="selector2" id="ad_Checkbox"
											class="ads_Checkbox" type="checkbox" value="Sunday" />&nbsp;<spring:message
												code="holidaymaster.sun" /></li>
									</ul>
								</div>
							</div>

							<div id="table-responsive"></div>
							<div class="text-center clear padding-10">
								<button type="button" class="button-input btn btn-success"
									name="button-Save" onclick="saveWorkTimeData(this)"
									value="Save" style="" id="button-Save">
									<spring:message code="holidaymaster.save" />
								</button>
								<button type="button" class="button-input btn btn-danger"
									name="button-Cancel" value="Cancel" style=""
									onclick="backHolidayForm();" id="button-Cancel">
									<spring:message code="holidaymaster.back" />
								</button>
							</div>
						</div>
					</div>
				</div>
				
				<div id="a2" class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title table" id="">
							<a data-toggle="" class=""
								data-parent="#accordion_single_collapse" href="#a1"><spring:message
									code="holidaymaster.holidaydetails" /></a>
						</h4>
					</div>
					<div id="a2" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">

								<label for="select-start-time"
									class="col-sm-2 control-label required-control"><spring:message
										code="holidaymaster.search.fromdate" /> </label>
								<div class="col-sm-4">
									<form:input path="hoYearStartDate"
										class="form-control " id="hoYearStartDate" readonly="true" 
										maxlength="10" />
								</div>

								<label for="select-start-time" class="col-sm-2 control-label "><spring:message
										code="holidaymaster.search.todate" /> </label>
								<div class="col-sm-4">
									<form:input path="hoYearEndDate" class="form-control "
										id="hoYearEndDate" maxlength="10" readonly="true" />
								</div>
							</div>
							<div class="form-group">
								<label for="Calendar" class="col-sm-2 control-label "><spring:message
										code="holidaymaster.calendar" /></label>
								<div class="col-sm-4">
									<div id="dncalendar-container"></div>
								</div>

								<label for="Calendar" class="col-sm-2 control-label "><spring:message
										code="holidaymaster.holidaylist" /></label>
								<div class="col-sm-4">
									<div id="calTable"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="text-center clear padding-10">
						<button type="button" class="button-input btn btn-success"
							name="button-1509602301770" value="Save" style=""
							onclick="saveHolidayDetails(this)" id="button-1509602301770">
							<spring:message code="holidaymaster.save" />
						</button>
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="backHolidayForm();" id="button-Cancel">
							<spring:message code="holidaymaster.back" />
						</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
</div>