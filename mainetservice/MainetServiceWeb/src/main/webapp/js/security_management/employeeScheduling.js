$(document).ready(function() {
	$("#frmEmployeeSchedulingTbl").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true,
		"ordering" : false,
		"order" : [ [ 1, "desc" ] ]
	});
	/*$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
	});*/
	
	$(".fromDateClass").datepicker({		     	
        dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
        onSelect: function(selected) {
          $(".toDateClass").datepicker("option","minDate", selected)	
        }	
    });

	$(".toDateClass").datepicker({			      	
        dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
        onSelect: function(selected) {	
           $(".fromDateClass").datepicker("option","maxDate", selected)	
        }	
    }); 
	
	$(function() {
		$('.datetimepicker3').timepicker();
	});
	$(function() {
		$('.datetimepicker3').timepicker();

	});
	runCalendar();
});

function Daily() {

	$(".hidebox").not(".yes").hide();
	$(".yes").hide();
	var weekDays = [];
	$("input:checkbox[name=day]:checked").each(function() {
		$('.days').attr("checked", false);
	});
}

function Weekly() {

	$(".hidebox").not(".yes").hide();
	$(".yes").show();
	var weekDays = [];
	$("input:checkbox[name=day]:checked").each(function() {
		weekDays.push($(this).val());
		weekDays.join(", ");
		$("#weekdays").val(weekDays);
	});
	if (weekDays != "") {
		$('.days').attr("checked", false);
	}
}

function Monthly() {

	$(".hidebox").not(".yes").hide();
	$(".yes").show();
	var weekDays = [];
	$("input:checkbox[name=day]:checked").each(function() {
		weekDays.push($(this).val());
		weekDays.join(", ");
		$("#weekdays").val(weekDays);
	});
	if (weekDays != "") {
		$('.days').attr("checked", false);
	}
}

function Yearly() {

	$(".hidebox").not(".yes").hide();
	$(".yes").show();
	var weekDays = [];
	$("input:checkbox[name=day]:checked").each(function() {
		$('.days').attr("checked", false);
	});
}

function searchEmployeeScheduling(obj) {

	var errorList = [];
	var empTypeId = $("#empTypeId").val();
	var dayPrefixId = $('#empTypeId').attr('code');
	var vendorId = $("#vmVendorid").val();
	var locId = $("#location").val();
	var cpdShiftId = $("#cpdShiftId").val();
	var contStaffSchFrom = $("#contStaffSchFrom").val();
	var contStaffSchTo = $("#contStaffSchTo").val();
	var contStaffName = $("#contStaffName").val();
	var contStaffIdNo = $("#contStaffIdNo").val();
	if (empTypeId == 0 && vendorId == "" && locId == "" && cpdShiftId == 0
			&& contStaffSchFrom == "" && contStaffSchTo == ""
			&& contStaffName == "" && contStaffIdNo == "") {
		errorList
				.push(getLocalMessage("EmployeeSchedulingDTO.Validation.selectAtLeastOne"));
		displayErrorsOnPage(errorList);
	}
	if (contStaffSchFrom != "" && contStaffSchTo == "") {
		errorList
				.push(getLocalMessage("securityManagement.select.scheduled.toDate"));
		displayErrorsOnPage(errorList);
	}
	if (contStaffSchTo != "" && contStaffSchFrom == "") {
		errorList
				.push(getLocalMessage("securityManagement.select.scheduled.fromDate"));
		displayErrorsOnPage(errorList);
	}
	else {
		$('.error-div').hide();
		runCalendar(obj);
	}
}

function runCalendar(obj) {

	var errorList = [];
	var empTypeId = $("#empTypeId").val();
	var dayPrefixId = $('#empTypeId').attr('code');
	var vendorId = $("#vmVendorid").val();
	var locId = $("#location").val();
	var cpdShiftId = $("#cpdShiftId").val();
	var contStaffSchFrom = $("#contStaffSchFrom").val();
	var contStaffSchTo = $("#contStaffSchTo").val();
	var contStaffName = $("#contStaffName").val();
	var contStaffIdNo = "";
	var data = {
		"empTypeId" : empTypeId,
		"vendorId" : vendorId,
		"locId" : locId,
		"cpdShiftId" : cpdShiftId,
		"contStaffSchFrom" : contStaffSchFrom,
		"contStaffSchTo" : contStaffSchTo,
		"contStaffName" : contStaffName,
		"contStaffIdNo" : contStaffIdNo,
	};

	/*if (contStaffSchFrom != "" && contStaffSchTo != ""
			&& (contStaffSchFrom > contStaffSchTo)) {
		errorList
				.push(getLocalMessage("EmployeeSchedulingDTO.Validation.fromDateToDate"));
		displayErrorsOnPage(errorList);
	} else {*/

		var ajaxResponse = __doAjaxRequest('employeeCalendar.html'
				+ '?getCalData', 'POST', data, false, 'json');
		$('#calendar').fullCalendar('destroy');
		if (ajaxResponse.length == 0) {
			errorList
					.push(getLocalMessage("EmployeeSchedulingDTO.Validation.scheduleNotAvail"));
			displayErrorsOnPage(errorList);
		}

		console.log('-----------' + JSON.stringify(ajaxResponse));
		var calendar = $('#calendar')
				.fullCalendar(
						{
							header : {
								left : 'prev,next today',
								center : 'title',
								right : 'month,agendaWeek,agendaDay'
							},
							buttonText : {
								today : 'today',
								month : 'month',
								week : 'week',
								day : 'day'
							},
							eventSources : [
									{
										events : function(start, end, callback) {
											var events = [];
											$(ajaxResponse)
													.each(
															function(index) {
																events
																		.push({
																			title : $(
																					this)
																					.attr(
																							'title'),
																			start : new Date(
																					$(
																							this)
																							.attr(
																									'start')),
																			end : new Date(
																					$(
																							this)
																							.attr(
																									'end')),
																			className : $(
																					this)
																					.attr(
																							'className'),
																			id : $(
																					this)
																					.attr(
																							'id'),
																			allDay : false
																		});
															});
											callback(events);
										},
									}, ],
							eventColor : '#333333',
							timeFormat : 'hh:mm',
							slotEventOverlap : false, //Defect #150299
							eventClick : function(calEvent, jsEvent, view) {

								var ajaxResponse = __doAjaxRequest(
										'employeeCalendar.html?searchEmployeeScheduling',
										'POST', {
											"emplScdlId" : calEvent.id
										}, false, 'html');

								var divName = '.content-page';
								$(divName).removeClass('ajaxloader');
								$(divName).html(ajaxResponse);
								prepareTags();
							}
						});
	/*}*/
}

function addEmployeeScheduling(formUrl, actionParam, mode) {

	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}
