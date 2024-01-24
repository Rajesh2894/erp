$(document).ready(
		function() {/*
			$('.widget-header h2').hide()
			// call to fetch meeting information
			var response = __doAjaxRequest(
					'MeetingCalendar.html?fetchMeetingData', 'POST', '', false,
					'json');

			var events = [];

			$.each(response, function(index) {

				let obj = response[index];
				let date = moment(obj.meetingDate).format();
				var desc = obj.meetingPlace;

				events.push({
					title : "Place: " + desc,
					start : date,
					// color : #009933,
					display : 'background',
					allDay : false
				});
			});

			$('#calendar').fullCalendar({
				header : {
					left : 'prev,next today',
					center : 'title',
					right : 'month,agendaWeek,agendaDay'
				},
				// defaultView: 'agendaDay',
				editable : true,
				events : events,

				// event render function
				// displaying other information on event element
				eventRender : function(event, element, view) {
					 element.append('<span>' + event.info.eventDescription + '<span><br>'); 

				},

	
			});

		*/});

var date;
var bade;
var e;
var str;
var e = [];
var ScheduleList = [];

function getEvent(e) {

	var errorList = [];
	var meetingTypeId = $('#meetingTypeId option:selected').attr('value');

	var meetingId = $('#meetingNo option:selected').attr('value');
	// alert (advId);
	if (meetingTypeId == '' || meetingTypeId == undefined) {
		errorList.push(getLocalMessage("sfac.validate.meetingTypeId"));
	}
	if ((meetingTypeId != '' || meetingTypeId != undefined)
			&& (meetingNo == '0' || meetingNo == undefined)) {
		errorList.push(getLocalMessage("sfac.validate.meetingNo"));
	}

	if (errorList.length == 0) {
		var events = [];
		var URL = 'MeetingCalendar.html?eventDate';
		var requestData = {
			"meetingTypeId" : meetingTypeId,
			"meetingId" : meetingId
		};

		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');
		var result = [];

		var prePopulate = JSON.parse(returnData);
	} else {
		displayErrorsOnPage(errorList);
		return false;
	}

	$.each(prePopulate, function(index) {

		var obj = prePopulate[index];

		let date = obj.meetingDate; // moment(obj.meetingDate).format();
		// let date = moment(obj.meetingDate, "DD.MM.YYYY HH.mm").toDate();
		var meet = obj.meetingTypeName;
		var desc = obj.meetingPlace;
		var meetingTime = obj.meetingTime;
		var remark = obj.remark;

		var description = "<strong>Purpose Of Meeting :  </strong>" + meet
				+ "<br />" + " <strong>Meeting Placee :  </strong>" + desc;
		ScheduleList.push({
			id : index,
			calendarId : '1',
			title : remark,
			body : description,
			dueDateClass : '',
			category : 'time',
			dueDateClass : '',
			start : new Date(date),
			color : '#FFFFFF',
			bgColor : '#FF0000',
			raw : {
				location : desc
			},
			isReadOnly : true
		// schedule is read-only
		});

		// alert(date+"-"+desc+"-"+caseno+"-"+advName+"-"+courtName);
	});

	var divName = '.content-page';
	var requestData = {};
	var ajaxResponse = doAjaxLoading("MeetingCalendar.html" + '?'
			+ "viewCalender", requestData, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	// prepareTags();

}

function getMeetingNoList() {
	

	var meetingTypeId = $('#meetingTypeId option:selected').attr('value');

	var URL = 'MeetingCalendar.html?getMeetingNoList';
	var requestData = {
		"meetingTypeId" : meetingTypeId
	};

	var ajaxResponse = doAjaxLoading(URL, requestData, 'html');
	var prePopulate = JSON.parse(ajaxResponse);
	$.each(prePopulate, function(index, value) {
		$('#meetingNo').append(
				$("<option></option>").attr("value", value.meetingId).text(
						(value.meetingNo)));
	});
	$('#meetingNo').trigger("chosen:updated");
}

function generateSchedule(viewName, renderStart, renderEnd) {
	// ScheduleList = [];
	CalendarList.forEach(function(calendar) {
		var i = 0, length = 10;
		if (viewName === 'month') {
			length = 3;
		} else if (viewName === 'day') {
			length = 4;
		}
		/*
		 * for (; i < length; i += 1) { generateRandomSchedule(calendar,
		 * renderStart, renderEnd); }
		 */
	});
}
