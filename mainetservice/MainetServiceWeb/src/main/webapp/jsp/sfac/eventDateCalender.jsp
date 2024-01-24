<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="assets/libs/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<!-- <script src="assets/libs/fullcalendar/fullcalendar.min.js"></script> 
<script src="assets/libs/fullcalendar/moment.min.js"></script> -->
<script src="js/mainet/validation.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<!-- <script src="assets/libs/tui.calendar-master/js/data/schedules.js"></script> -->
<!-- TUI Calender Initializing JS -->
<style>
.tui-full-calendar-weekday-schedule-time{
	background:#FF0000 !important;
	color:#FFF !important;
	padding:6px;
}
.tui-full-calendar-weekday-schedule-title{
	color:#FFF !important;
}
</style>
<script src="assets/libs/tui.calendar-master/js/app.js"></script>



<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2><strong><spring:message code="" text="Meeting Calendar" /></strong></h2>	
		</div>
		<div class="widget-content padding"> 
			<div id="lnb" class="col-xs-2 col-sm-2">
		        <div class="lnb-new-schedule">
		            <!-- <button id="btn-new-schedule" type="button" class="btn btn-default btn-block lnb-new-schedule-btn" data-toggle="modal">
		                New schedule</button> -->
		            <h3 style="font-size:22px;">My Events Scheduler</h3>
		        </div>
		        <div id="lnb-calendars" class="lnb-calendars">
		            <div>
		                <div class="lnb-calendars-item">
		                    <label>
		                        <input class="tui-full-calendar-checkbox-square" type="checkbox" value="all" checked>
		                        <span></span>
		                        <strong>View all</strong>
		                    </label>
		                </div>
		            </div>
		            <div id="calendarList" class="lnb-calendars-d1">
		            </div>
		        </div>
		       
		    </div>
		    <div id="right" class="col-xs-10 col-sm-10">
		        <div id="menu">
		            <span class="dropdown">
		                <button id="dropdownMenu-calendarType" class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown"
		                    aria-haspopup="true" aria-expanded="true">
		                    <i id="calendarTypeIcon" class="calendar-icon ic_view_month" style="margin-right: 4px;"></i>
		                    <span id="calendarTypeName">Dropdown</span>&nbsp;
		                    <i class="calendar-icon tui-full-calendar-dropdown-arrow"></i>
		                </button>
		                <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu-calendarType">
		                    <li role="presentation">
		                        <a class="dropdown-menu-title" role="menuitem" data-action="toggle-daily">
		                            <i class="calendar-icon ic_view_day"></i>Daily
		                        </a>
		                    </li>
		                    <li role="presentation">
		                        <a class="dropdown-menu-title" role="menuitem" data-action="toggle-weekly">
		                            <i class="calendar-icon ic_view_week"></i>Weekly
		                        </a>
		                    </li>
		                    <li role="presentation">
		                        <a class="dropdown-menu-title" role="menuitem" data-action="toggle-monthly">
		                            <i class="calendar-icon ic_view_month"></i>Month
		                        </a>
		                    </li>
		                    <li role="presentation">
		                        <a class="dropdown-menu-title" role="menuitem" data-action="toggle-weeks2">
		                            <i class="calendar-icon ic_view_week"></i>2 weeks
		                        </a>
		                    </li>
		                    <li role="presentation">
		                        <a class="dropdown-menu-title" role="menuitem" data-action="toggle-weeks3">
		                            <i class="calendar-icon ic_view_week"></i>3 weeks
		                        </a>
		                    </li>
		                   
		                    <li role="presentation" class="dropdown-divider"></li>
		                    <li role="presentation">
		                        <a role="menuitem" data-action="toggle-workweek">
		                            <input type="checkbox" class="tui-full-calendar-checkbox-square" value="toggle-workweek" checked>
		                            <span class="checkbox-title"></span>Show weekends
		                        </a>
		                    </li>
		                    <li role="presentation">
		                        <a role="menuitem" data-action="toggle-start-day-1">
		                            <input type="checkbox" class="tui-full-calendar-checkbox-square" value="toggle-start-day-1">
		                            <span class="checkbox-title"></span>Start Week on Monday
		                        </a>
		                    </li>
		                    <li role="presentation">
		                        <a role="menuitem" data-action="toggle-narrow-weekend">
		                            <input type="checkbox" class="tui-full-calendar-checkbox-square" value="toggle-narrow-weekend">
		                            <span class="checkbox-title"></span>Narrower than weekdays
		                        </a>
		                    </li>
		                </ul>
		            </span>
		            <span id="menu-navi">
		                <button type="button" class="btn btn-default btn-sm move-today" data-action="move-today">Today</button>
		                <button type="button" class="btn btn-default btn-sm move-day" data-action="move-prev">
		                    <i class="calendar-icon ic-arrow-line-left" data-action="move-prev"></i>
		                </button>
		                <button type="button" class="btn btn-default btn-sm move-day" data-action="move-next">
		                    <i class="calendar-icon ic-arrow-line-right" data-action="move-next"></i>
		                </button>
		            </span>
		            <span id="renderRange" class="render-range"></span>
		        </div>
		        <div id="calendar"></div>
		    </div>
		    
		  <div class="text-center padding-bottom-10">
		    <button type="button" class="btn btn-danger" id="btn3" onclick="window.location.href='MeetingCalendar.html'">
					    <spring:message code="legal.btn.back" text="Back" /></button></div>
		</div>
	</div>
</div>