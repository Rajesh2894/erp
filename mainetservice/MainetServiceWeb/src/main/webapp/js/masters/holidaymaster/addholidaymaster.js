var leave = [];

$(".wrWorkWeekAlternate").hide();
$(".wrWorkWeek").hide();

function hideOption() {
	var selectedType = $("#wrWeekType").find("option:selected").attr('code');

	$("#worrkWeekFlag").val(selectedType);

	if (selectedType == "N") {
		$(".wrWorkWeek").show();
		$(".wrWorkWeekAlternate").hide();
	}
	if (selectedType == "A") {
		$(".wrWorkWeekAlternate").show();
		$(".wrWorkWeek").hide();
	}
}

$(document).ready(
		function() {

			$('.lessthancurrdate').datepicker({
				beforeShowDay : function(date) {
					if (date.getDate() == 1) {
						return [ true, '' ];
					}
					return [ false, '' ];
				},
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true
			}

			);

			$(function() {
				$('.datetimepicker3').timepicker();
			});

			// use to show dropdown for week days (Odd/Even)
			var options = [];

			$('.dropdown-menu a').on(
					'click',
					function(event) {

						var $target = $(event.currentTarget), val = $target
								.attr('data-value'), $inp = $target
								.find('input'), idx;

						if ((idx = options.indexOf(val)) > -1) {
							options.splice(idx, 1);
							setTimeout(function() {
								$inp.prop('checked', false)
							}, 0);
						} else {
							options.push(val);
							setTimeout(function() {
								$inp.prop('checked', true)
							}, 0);
						}

						$(event.target).blur();

						return false;
					});

			$(document).ready(
					function() {
						$(".oddWorkWeek").click(
								function() {
									var selectedLanguage = new Array();
									$('input[name="selector1"]:checked').each(
											function() {
												selectedLanguage
														.push(this.value);
											});
									$("#wrOddWorkWeek").val(selectedLanguage);
								});

						$(".evenWorkWeek").click(
								function() {
									var selectedLanguage = new Array();
									$('input[name="selector2"]:checked').each(
											function() {
												selectedLanguage
														.push(this.value);
											});
									$("#wrEvenWorkWeek").val(selectedLanguage);
								});

						$(".workWeek").click(
								function() {
									var selectedLanguage = new Array();
									$('input[name="selector3"]:checked').each(
											function() {
												selectedLanguage
														.push(this.value);
											});
									$("#wrWorkWeek").val(selectedLanguage);
								});

						$("#hoYearStartDate").val(
								$("#hoYearStartDate").val().substring(0, 10));

						var subDate = $("#hoYearStartDate").val().split("/");

						$("#hoYearEndDate").val(
								$("#hoYearEndDate").val().substring(0, 10));

							$(".checkForData").hide();

					});

		});

$(document).ready(
		function() {

			var my_calendar = $("#dncalendar-container")
					.dnCalendar(
							{
								minDate : convertDateFormateForCalendar($("#hoYearStartDate").val()),
								maxDate : convertDateFormateForCalendar($("#hoYearEndDate").val()),
								defaultDate : convertDateFormateForCalendar($("#hoYearStartDate").val()),
								monthNames : [ "January", "February", "March",
										"April", "May", "June", "July",
										"August", "September", "October",
										"November", "December" ],
								monthNamesShort : [ 'Jan', 'Feb', 'Mar', 'Apr',
										'May', 'Jun', 'Jul', 'Aug', 'Sep',
										'Oct', 'Nov', 'Dec' ],
								dayNames : [ 'Sunday', 'Monday', 'Tuesday',
										'Wednesday', 'Thursday', 'Friday',
										'Saturday' ],
								dayNamesShort : [ 'Sun', 'Mon', 'Tue', 'Wed',
										'Thu', 'Fri', 'Sat' ],
								dataTitles : {
									defaultDate : '',
									today : 'Today'
								},
								notes : leave,
								showNotes : true,
								startWeek : 'Sunday',
								dayClick : function(date, view) {
									openHolidayDescriptinForm(date);

								}
							});

			// init calendar
			my_calendar.build();
			// $('#holiday').appendTo('#avinash');
		})

function openHolidayDescriptinForm(date) {

	var requestData = {
		"hoDate" : date.getFullYear() + "-" + (date.getMonth() + 1) + "-"
				+ date.getDate()
	};
	var returnData = __doAjaxRequest(
			'HolidayMaster.html?addHolidayDescription', 'POST', requestData,
			false, 'html');
	showModalBox(returnData);

	$("#hoDate").val(
			date.getFullYear() + "-" + (date.getMonth() + 1) + "-"
					+ date.getDate());
}

var check = "A";

function editHolidayDescription() {

	var requestData = {
		"hoDate" : $("#hoDate").val()
	};
	var returnData = __doAjaxRequest(
			'HolidayMaster.html?editHolidayDescription', 'POST', requestData,
			false, 'html');

	check = "E";

	saveHolidayDescription();
	$.fancybox.close();
}

function saveHolidayDescription() {

	leave = [];
	var requestData;
	
	$("#errorDiv").hide();
	if(check == "A")
		if($("#hoDescription").val()== undefined || ($("#hoDescription").val()).trim()==""){
		var errorList = [];
		errorList.push(getLocalMessage("holidaymaster.vldnn.Description"));
			$("#errorDivdiscription").show();
			
			showErr(errorList);
			return false;
		} else {
			$("#errorDivdiscription").hide();
		}
		
	if (check == "A") {
		requestData = {
			"hoDate" : $("#hoDate").val(),
			"hoDescription" : $("#hoDescription").val()
		};
	} else {
		requestData = {
			"hoDate" : "",
			"hoDescription" : ""
		};
	}

	check = "A";

	var returnDataMap = __doAjaxRequest(
			'HolidayMaster.html?saveHolidayDescription', 'POST', requestData,
			false, 'html');

	var returnData = returnDataMap.substring(2, returnDataMap.length - 2);

	var next1 = returnData.split(",");

	var returnDataMaptable = next1;

	var num = 0;
	var a1;
	var a2;

	var arrayLength = next1.length;
	for (var i = 0; i < arrayLength; i++) {

		var a1;
		var a2;

		var next2 = next1[i].split("=");
		for (var j = 0; j < next2.length; j++) {

			if (j == 0) {
				a1 = next2[j];
			} else {
				a2 = next2[j];
			}

		}

		j = 0;

		leave.push({
			"date" : a1,
			"note" : [ a2 ]
		});

	}
	$.fancybox.close();

	var my_calendar = $("#dncalendar-container").dnCalendar(
			{
				minDate : convertDateFormateForCalendar($("#hoYearStartDate").val()),
				maxDate : convertDateFormateForCalendar($("#hoYearEndDate").val()),
				defaultDate : $("#hoDate").val(),
				monthNames : [ "January", "February", "March", "April", "May",
						"June", "July", "August", "September", "October",
						"November", "December" ],
				monthNamesShort : [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
						'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dec' ],
				dayNames : [ 'Sunday', 'Monday', 'Tuesday', 'Wednesday',
						'Thursday', 'Friday', 'Saturday' ],
				dayNamesShort : [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri',
						'Sat' ],
				dataTitles : {
					defaultDate : '',
					today : 'Today'
				},
				notes : leave,
				showNotes : true,
				startWeek : 'Sunday',
				dayClick : function(date, view) {
					openHolidayDescriptinForm(date);

				}
			});

	// init calendar
	my_calendar.build();

	var notesGroup = "";

	notesGroup = $("<table id='calTable' class='table table-bordered table-condensed'><tr><th>Holiday Date</th><th>Holiday Description</th></tr></table>");

	var arrayLength = returnDataMaptable.length;
	if(returnDataMaptable!="")
	for (var i = 0; i < arrayLength; i++) {
		var a1;
		var a2;
		var next2 = returnDataMaptable[i].split("=");
		for (var j = 0; j < next2.length; j++) {

			if (j == 0) {
				a1 = next2[j];
			} else {
				a2 = next2[j];
			}
		}

		j = 0;

		var list = "";

		list += "<tr><td>" + convertDateFormateForGrid(a1) + "</td><td>" + a2 + "</td></tr>";

		notesGroup.append(list);

	}

	$("#calTable").html(notesGroup);
	$('#holiday').hide();

}

function saveWorkTimeData(element) {

	var errorList = [];
	if ($("#wrStartTime").val() == "")
		errorList.push(getLocalMessage("holidaymaster.vldnn.starttime"));
	if ($("#wrEndTime").val() == "")
		errorList.push(getLocalMessage("holidaymaster.vldnn.endtime"));
	if ($("#worrkWeekFlag").val() == "")
		errorList.push(getLocalMessage("holidaymaster.vldnn.selectioncriteria"));
	if ($("#worrkWeekFlag").val() == "N" && $("#wrWorkWeek").val() == "")
		errorList.push(getLocalMessage("holidaymaster.vldnn.workweek"));
	if ($("#worrkWeekFlag").val() == "A" && $("#wrOddWorkWeek").val() == "")
		errorList.push(getLocalMessage("holidaymaster.vldnn.oddworkweek"));
	if ($("#worrkWeekFlag").val() == "A" && $("#wrEvenWorkWeek").val() == "")
		errorList.push(getLocalMessage("holidaymaster.vldnn.evenworkweek"));

	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
	} else {
		$("#errorDiv").hide();

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('HolidayMaster.html?saveWorkTimeData',
			'POST', requestData, false);
	showConfirmBox();
	}
}

function saveHolidayDetails(element) {

	var errorList = [];
	if ($("#hoYearStartDate").val() == "")
		errorList.push(getLocalMessage("holidaymaster.vldnn.fromdate"));
	
	var response = __doAjaxRequest('HolidayMaster.html?validateHolidayList',
			'POST', {}, false);
	if (response == "N") {
		errorList.push(getLocalMessage("holidaymaster.vldnn.holidaylist"));
	}

	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
	} else {
		$("#errorDiv").hide();
		
		
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('HolidayMaster.html?saveHolidayDetails',
			'POST', requestData, false);
	showConfirmBox();
	}

}

function showErr(errorList) {
	$(".warning-div").removeClass('hide');
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closePrefixErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);

	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

function showConfirmBox() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';

	message += '<h4 class=\"text-center text-blue-2 padding-12\">Form Submitted Successfully</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceed()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);

	return false;
}

function proceed() {
	$("#postMethodForm").prop('action', 'HolidayMaster.html');
	$("#postMethodForm").submit();
	$.fancybox.close();
	$.fancybox.close();
}

function backHolidayForm() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'HolidayMaster.html');
	$("#postMethodForm").submit();
}

function convertDateFormateForCalendar( date){
	var dateSplit=date.split("/");
	return dateSplit[2]+"-"+dateSplit[1]+"-"+dateSplit[0];
}

function convertDateFormateForGrid( date){
	var dateSplit=date.split("-");
	return dateSplit[2]+"-"+dateSplit[1]+"-"+dateSplit[0];
}