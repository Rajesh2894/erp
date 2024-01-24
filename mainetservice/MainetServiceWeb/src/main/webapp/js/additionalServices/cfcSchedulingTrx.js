/*function valueChanged() {

	if ($('.esyTap').is(":checked")) {
		$("#easyTapDet").hide();
		
	} else
		$("#easyTapDet").show();
}*/
$(document).ready(function() {

	$('.fromDate').datetimepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : 0,
		onClose: function( selectedDate ) {
	        $( ".toDate" ).datepicker( "option", "minDate", selectedDate );
	      },
		timeFormat : "HH:mm"
		
	});
	
	$('.toDate').datetimepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		timeFormat : "HH:mm"
		
	});

	$("#collectionTableId").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
});

function formForCreate() {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('CFCSchedulingTrx.html?formForCreate', {},
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse)
	$('.error-div').hide();
}

function saveCollectionData(element) {
	
	var errorList = [];
	errorList = validateCollectionMaster(errorList);
	errorList = validateCounterEntryDetails(errorList);
	errorList = validateCounterScheduleEntryDetails(errorList);
	// errorList = validateForm(errorList)
	if (errorList.length == 0) {
		saveOrUpdateForm(element, 'Collection Saved successfully',
				'CFCSchedulingTrx.html', 'saveform');
	} else
		displayErrorsOnPage(errorList);

}

function addcounterEntry() {

	var errorList = [];
	errorList = validateCollectionMaster(errorList);
	errorList = validateCounterEntryDetails(errorList);
	if (errorList.length == 0) {
		// addTableRow('technicalDetail');

		var content = $('#counterDetails tr').last().clone();
		$('#counterDetails tr').last().after(content);
		content.find("select").val("");
		content.find("input:hidden").val("");
		content.find("input:text").val('');
		reorderCounterEntry();

	} else {
		// $('#counterDetails').DataTable();
		displayErrorsOnPage(errorList);
	}
}

function validateCollectionMaster(errorList) {

	var collncentreno = $("#collncentreno").val();
	var collectiondesc = $("#collectiondesc").val();
	var cfcWard1 = $("#cfcWard1").val();

	if (collncentreno == "") {
		errorList.push(getLocalMessage("SFT.validation.collection.no"));
	}
	if (collectiondesc == "") {
		errorList.push(getLocalMessage("SFT.validation.collection.desc"));
	}
	if (cfcWard1 == 0) {
		errorList.push(getLocalMessage("SFT.validation.ward"));
	}
	return errorList;
}

function reorderCounterEntry() {

	$(".counterEntryRow").each(
			function(i) {

				$(this).find("input:text:eq(0)").attr("id", "counterNo" + (i))
						.attr(
								"name",
								"cfcCollectionMasterDto.cfcCounterMasterDtos["
										+ (i) + "].cuCountcentreno");

				$(this).find("input:text:eq(1)")
						.attr("id", "description" + (i)).attr(
								"name",
								"cfcCollectionMasterDto.cfcCounterMasterDtos["
										+ (i) + "].cuDescription");

			});
}

function validateCounterEntryDetails(errorList) {
	$(".counterEntryRow")
			.each(
					function(i) {
						var collncentreno = $("#counterNo" + i).val();
						var description = $("#description" + i).val();
						// var yearPercntWork = $("#yearPercntWork" + i).val();

						var row = i + 1;
						if (collncentreno == "") {

							errorList
									.push(getLocalMessage("SFT.validation.counter.no")
											+ " - " + row);
						}
						if (description == '') {
							errorList
									.push(getLocalMessage("SFT.validation.counter.desc")
											+ " - " + row);
						}

					});
	return errorList;
}

function addScheduleDetail(obj, index) {

	var errorList = [];
	errorList = validateCollectionMaster(errorList);
	errorList = validateCounterEntryDetails(errorList);

	counterIndex = $("#counterIndex").val(index);

	if (errorList.length == 0) {

		var divName = '.content-page';

		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		var url = "CFCSchedulingTrx.html?addScheduleDetail";
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		

		// $("#addScheduleEntryButton").hide();

	} else {
		displayErrorsOnPage(errorList);
	}

}

function deleteCounterEntry(obj, ids) {

	deleteTableRow('counterDetails', obj, ids);
	$('#counterDetails').DataTable().destroy();

	triggerTable();
}
function deleteScheduleEntry(obj, ids) {
	deleteTableRow('ScheduleDetails', obj, ids);
	$('#ScheduleDetails').DataTable().destroy();

	triggerTable();
}

function addScheduleEntry() {

	var errorList = [];
	errorList = validateCollectionMaster(errorList);
	errorList = validateCounterEntryDetails(errorList);
	errorList = validateCounterScheduleEntryDetails(errorList);
	if (errorList.length == 0) {
		// addTableRow('technicalDetail');
		$(".datetimepicker").datetimepicker("destroy");
		var content = $('#ScheduleDetails tr').last().clone();
		$('#ScheduleDetails tr').last().after(content);
		content.find("select").val("");
		content.find("input:hidden").val("");
		content.find("input:text").val('');
		$(this).parent().parent().remove();
		reorderScheduleEntry();
		$('.datetimepicker').datetimepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			timeFormat : "HH:mm"
			/*onSelect : function(selected) {
				$(".datepickerEndDate").datetimepicker("option", "minDate", selected);
				
			}*/
		});
		

	} else {
		// $('#ScheduleDetails').DataTable();
		displayErrorsOnPage(errorList);
	}
}
function validateCounterScheduleEntryDetails(errorList) {
	$(".scheduleEntryRow").each(
			function(i) {
				
				var fromDate = $("#fromDate" + i).val();
				var endDate = $("#endDate" + i).val();
				var empId = $("#empId" + i).val();
				var freSts=$("#frequencySts" + i).val();

				var row = i + 1;
				if (fromDate == "") {

					errorList.push(getLocalMessage("SFT.validation.from.date")
							+ "  :  " + row);
				}
				if (endDate == "") {
					errorList.push(getLocalMessage("SFT.validation.to.date")
							+ "  :  " + row);
				}
				if (fromDate > endDate){
					errorList.push(getLocalMessage("SFT.date.validation.mes")
							+ "  :  " + row);	
				}
				if (empId == "") {
					errorList.push(getLocalMessage("SFT.validation.user")
							+ "  :  " + row);
				}
				if (freSts == null) {
					errorList.push(getLocalMessage("SFT.validation.fre.sts")
							+ "  :  " + row);
				}

			});
	return errorList;
}

function reorderScheduleEntry() {

	var counterIndex = $("#counterIndex").val();
	$(".scheduleEntryRow").each(
			function(i) {

				$(this).find("input:text:eq(0)").attr("id", "fromDate" + (i))
						.attr(
								"name",
								"cfcCollectionMasterDto.cfcCounterMasterDtos["
										+ (counterIndex)
										+ "].cfcCounterScheduleDtos[" + (i)
										+ "].csFromTime").addClass("datetimepicker");

				$(this).find("input:text:eq(1)").attr("id", "endDate" + (i))
						.attr(
								"name",
								"cfcCollectionMasterDto.cfcCounterMasterDtos["
										+ (counterIndex)
										+ "].cfcCounterScheduleDtos[" + (i)
										+ "].csToTime").addClass("datetimepicker");

				$(this).find("select:eq(0)").attr("id", "empId" + (i)).attr(
						"name",
						"cfcCollectionMasterDto.cfcCounterMasterDtos["
								+ (counterIndex) + "].cfcCounterScheduleDtos["
								+ (i) + "].csUserId");
				$(this).find("select:eq(1)").attr("id", "frequencySts" + (i)).attr(
						"name",
						"cfcCollectionMasterDto.cfcCounterMasterDtos["
								+ (counterIndex) + "].cfcCounterScheduleDtos["
								+ (i) + "].frequencySts");

			});
}

function searchForm(formUrl, actionParam) {

	var errorList = [];
	errorList = validateHomeForm(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var collectionNo = $("#collectionNo").val();
		var counterNo = $("#counterNo").val();
		var userId = $("#userId").val();
		var status = $("#status").val();

		var requestdata = {
			"collectionNo" : collectionNo,
			"counterNo" : counterNo,
			"userId" : userId,
			"status" : status

		};

		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,
				requestdata, 'html', divName);

		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		$("#collectionNo").val(collectionNo);
		$("#counterNo").val(counterNo);
		$("#userId").val(userId);
		$("#status").val(status);
	}
}

function validateHomeForm(errorList) {

	var errorList = [];
	var collectionNo = $("#collectionNo").val();
	var counterNo = $("#counterNo").val();
	var userId = $("#userId").val();
	var status = $("#status").val();

	if (collectionNo == "") {
		if (counterNo == "") {
			if (userId == "") {
				if (status == "") {
					errorList
							.push(getLocalMessage("SFT.validation.select.field"));
				}
			}
		}

	}

	return errorList;
}

function getCounterNosByCollectionNo() {

	$('.warning-div').addClass('hide');

	var collectionNo = $('#collectionNo option:selected').val();

	var errorList = [];

	if (errorList.length > 0) {
		showErr(errorList);
	} else {
		var url = "CFCSchedulingTrx.html?searchCounterNo";
		var requestData = {
			"collectionNo" : collectionNo
		}

		var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false,
				'json');

		$('#counterNo').html('');

		$('#counterNo').append(
				$("<option></option>").attr("value", "").text(
						"Select Counter No"));
		$.each(ajaxResponse, function(index, data) {
			$('#counterNo').append(
					$("<option></option>").attr("value", data).text(data));
		});
		$('#counterNo').trigger('chosen:updated');

	}
}

function editSequenceMaster(formUrl, actionParam, counterScheduleId) {

	var requestdata = {
		"counterScheduleId" : counterScheduleId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	/*prepareTags();*/
}

function statusChange(obj) {

	if ($(obj).is(":checked")) {
		$("#status").val("A");
	} else {
		$("#status").val("I");
	}
}

function saveCounterSchedule(obj) {
	var divName = '.content-page';

	var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);

	var url = "CFCSchedulingTrx.html?updateCounterDetail";
	var response = __doAjaxRequest(url, 'post', requestData, false, 'html');

	$(divName).removeClass('ajaxloader');
	$(divName).html(response);
	prepareTags();

	showConfirmBoxforSchedule(getLocalMessage("SFT.update.meg"));

}
function showConfirmBoxforSchedule(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");
	var C = "C";
	message += '<h4 class=\"text-center text-blue-2 padding-12\">' + successMsg
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="showSummary()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function showSummary() {

	$.fancybox.close();
	var divName = '.content-page';

	var requestdata = {
		"collectionNo" : "",
		"counterNo" : "",
		"userId" : "",
		"status" : ""

	};

	var ajaxResponse = doAjaxLoading("CFCSchedulingTrx.html?searchForm",
			requestdata, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();

}
