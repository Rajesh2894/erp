$(document).ready(function() {
	$("#id_sanitarystaffscheduling").dataTable({
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
	$(".datepicker").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
	});
	$(function() {
		$('.datetimepicker3').timepicker();
	});
	$(function() {
		$('.datetimepicker3').timepicker();

	});
	yearLength();
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
function yearLength() {
	var frmdateFields = $('#emsFromdate');
	var todateFields = $('#emsTodate');
	frmdateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	})
	todateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	})
}
function resetSanitaryStaffScheduling() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'Scheduling.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
}
function openAddSanitaryStaffScheduling(formUrl, actionParam) {
	
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
function getScheduleType() {
	
	var requestData = {'actionParam' : $("#scheduleType").val(),}
	var url = 'Scheduling.html?getSanitaryStaff';
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$("#sanatary").html(ajaxResponse);
	reOrderUnitTabIdSequence('.firstUnitRow');
	prepareTags();
}
function saveData(element) {
	
	var errorList = [];
	var weekDays = [];
	$("input:checkbox[name=day]:checked").each(function() {
		weekDays.push($(this).val());
		weekDays.join(", ");
		$("#weekdays").val(weekDays);
	});
	if (!$("#reccurance1").is(":checked")) {
		if (weekDays.length == 0) {
			errorList.push(getLocalMessage("swm.validation.days.selection"));
		}
	}
	errorList = errorList.concat(ValidateSanitaryScheduleForm(errorList));
	if (errorList.length == 0) {
		errorList = validateSanitaryScheduleEntryDetails1(errorList);
	}
	if (errorList.length == 0) {
		errorList = validateSanitaryScheduleEntryDetails2(errorList);
	}
	if (errorList.length == 0) {
		errorList = validateSanitaryScheduleEntryDetails3(errorList);
	}
	if (errorList.length == 0) {
		errorList = validateUnitDetailTable(errorList);
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsPage(errorList);
	} else {
		var status = saveOrUpdateForm(element,getLocalMessage('swm.saveSanitaryStaffScheduling'),'Scheduling.html', 'saveform');
		getScheduleType();
		return status;
	}
}
function modifySanitaryScheduling(emsId, formUrl, actionParam) {
	
	var divName = '.content-page';
	var requestData = {
		"emsId" : emsId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
};

function addEntryData(tableId) {
	
	var errorList = [];
	if (tableId == "id_sanitaryschedulingTbl1") {
		errorList = validateSanitaryScheduleEntryDetails1(errorList);
	}
	if (tableId == "id_sanitaryschedulingTbl2") {
		errorList = validateSanitaryScheduleEntryDetails2(errorList);
	}
	if (tableId == "id_sanitaryschedulingTbl3") {
		errorList = validateSanitaryScheduleEntryDetails3(errorList);
	}
	if (tableId == "unitDetailTable") {
		errorList = validateUnitDetailTable(errorList);
	}
	if (errorList.length == 0) {
		$("#errorDiv").hide();
		addTableRow3(tableId);
	} else {
		displayErrorsOnPage(errorList);
	}

}

function deleteEntry(tableId, obj, ids) {
	deleteTableRow(tableId, obj, ids);
}
function deleteEntry2(tableId, obj, ids) {
	deleteTableRow(tableId, obj, ids);
}
function addTableRow3(tableId) {
	
	var id = "#" + tableId;
	if ($.fn.DataTable.isDataTable('' + id + '')) {
		$('' + id + '').DataTable().destroy();
	}
	$(".datetimepicker3").timepicker("destroy");
	var content = $(id + ' tr').last().clone();
	content.find("input:text").val('');
	content.find("input:hidden").val('');
	content.find("textarea").val('');
	content.find("select").val('');
	content.find("multiselect:checkbox").removeAttr('checked');
	content.appendTo('tbody');
	reOrderTableIdSequence(id);
	$(function() {
		$('.datetimepicker3').timepicker();
	});
	dataTableProperty(id);
}

function ValidateSanitaryScheduleForm() {
	var errorList = [];
	var scheduleType = $("#scheduleType").val();
	var emsFromdate = $("#emsFromdate").val();
	var emsTodate = $("#emsTodate").val();
	var reccurance1 = $("#reccurance1").val();
	var reccurance2 = $("#reccurance2").val();
	var reccurance3 = $("#reccurance3").val();
	var reccurance4 = $("#reccurance4").val();
	var x = document.getElementById("reccurance1").checked;
	var y = document.getElementById("reccurance2").checked;
	var z = document.getElementById("reccurance3").checked;
	var A = document.getElementById("reccurance4").checked;
	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
	if (scheduleType == "" || scheduleType == null)
		errorList.push(getLocalMessage("swm.validation.emsType"));
	if (emsFromdate == "" || emsFromdate == null)
		errorList.push(getLocalMessage("swm.validation.emsFromdate"));
	if (emsTodate == "" || emsTodate == null)
		errorList.push(getLocalMessage("swm.validation.emsTodate"));
	if (x == false && y == false && z == false && A == false) {
		errorList.push(getLocalMessage("swm.validation.emsReocc"));
	}
	return errorList;
}

function validateSanitaryScheduleEntryDetails1(errorList) {
	var j = 0;
	if ($.fn.DataTable.isDataTable('#id_sanitaryschedulingTbl1')) {
		$('#id_sanitaryschedulingTbl1').DataTable().destroy();
	}
	$("#id_sanitaryschedulingTbl1 tbody tr").each(function(i) {
						
						var empid = $("#empid" + i).find("option:selected").attr('value');
						var deId = $("#deId" + i).find("option:selected").attr('value');
						var emsdCollType = $("#emsdCollType" + i).find("option:selected").attr('value');
						var startTime = $("#startTime" + i).val();
						var endTime = $("#endTime" + i).val();
						var rowCount = i + 1;
						var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
						if (empid == "" || empid == null) {
							errorList.push(getLocalMessage("swm.validation.empid")+ rowCount);
						}
						if (deId == "0" || deId == null) {
							errorList.push(getLocalMessage("swm.validation.deId")+ rowCount);
						}
						if (emsdCollType == "0" || emsdCollType == '') {
							errorList.push(getLocalMessage("swm.validation.emsdCollType")+ rowCount);
						}
						if (startTime == "" || startTime == null) {
							errorList.push(getLocalMessage("swm.validation.startTime")+ rowCount);
						}
						if (endTime == "" || endTime == null) {
							errorList.push(getLocalMessage("swm.validation.endTime")+ rowCount);
						}
						if (endTime < startTime) {
							errorList.push(getLocalMessage("swm.sanitary.validation.timevalidation"));
						}
						for (j = 0; j < i; j++) {
							if ($('#empid' + j).val() == $('#empid' + i).val()) {
								if ($('#deId' + j).val() == $('#deId' + i).val()) {
									errorList.push("The Disposable Site  already exists!");
								}
								if ($('#emsdCollType' + j).val() == $('#emsdCollType' + i).val()) {
									errorList.push("The Task already exists!");
								}
								if ($('#startTime' + j).val() == $('#startTime' + i).val()) {
									errorList.push("The InTime already assigned!");
								}
							}
						}
					});
	return errorList;
}

function validateSanitaryScheduleEntryDetails2(errorList) {
	var j = 0;
	if ($.fn.DataTable.isDataTable('#id_sanitaryschedulingTbl2')) {
		$('#id_sanitaryschedulingTbl2').DataTable().destroy();
	}
	$("#id_sanitaryschedulingTbl2 tbody tr").each(function(i) {
						
						var empid = $("#empid" + i).find("option:selected").attr('value');
						var emsdCollType = $("#emsdCollType" + i).find("option:selected").attr('value');
						var locId = $("#locId" + i).find("option:selected").attr('value');
						var veId = $("#veId" + i).find("option:selected").attr('value');
						var startTime = $("#startTime" + i).val();
						var endTime = $("#endTime" + i).val();
						var rowCount = i + 1;
						var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
						if (empid == "" || empid == null) {
							errorList.push(getLocalMessage("swm.validation.empid")+ rowCount);
						}
						if (emsdCollType == "0" || emsdCollType == '') {
							errorList.push(getLocalMessage("swm.validation.emsdCollType")+ rowCount);
						}
						if (locId == "0" || locId == null) {
							errorList.push(getLocalMessage("swm.validation.locId")+ rowCount);
						}
						if (veId == "0" || veId == null) {
							errorList.push(getLocalMessage("swm.validation.veId")+ rowCount);
						}
						if (startTime == "" || startTime == null) {
							errorList.push(getLocalMessage("swm.validation.startTime")+ rowCount);
						}
						if (endTime == "" || endTime == null) {
							errorList.push(getLocalMessage("swm.validation.endTime")+ rowCount);
						}
						if (endTime < startTime) {
							errorList.push(getLocalMessage("swm.sanitary.validation.timevalidation"));
						}
					});
	return errorList;
}

function validateSanitaryScheduleEntryDetails3(errorList) {
	var j = 0;
	if ($.fn.DataTable.isDataTable('#id_sanitaryschedulingTbl3')) {
		$('#id_sanitaryschedulingTbl3').DataTable().destroy();
	}
	$("#id_sanitaryschedulingTbl3 tbody tr").each(
					function(i) {
						
						var empid = $("#empId" + i).find("option:selected").attr('value');
						var veVetype = $("#veVetype" + i).find("option:selected").attr('value');
						var veId = $("#veId" + i).find("option:selected").attr('value');
						var roId = $("#roId" + i).find("option:selected").attr('value');
						var emsdCollType = $("#emsdCollType" + i).find("option:selected").attr('value');
						var startTime = $("#startTime" + i).val();
						var rowCount = i + 1;
						var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
						if (empid == "" || empid == null) {
							errorList.push(getLocalMessage("swm.validation.empid")+ rowCount);
						}
						if (veVetype == "0" || veVetype == null) {
							errorList.push(getLocalMessage("Vehicle Type Must be Selected")+ rowCount);
						}
						if (veId == "0" || veId == null) {
							errorList.push(getLocalMessage("swm.validation.veId")+ rowCount);
						}
						if (roId == "0" || roId == null) {
							errorList.push(getLocalMessage("swm.validation.roId")+ rowCount);
						}
						if (emsdCollType == "0" || emsdCollType == '') {
							errorList.push(getLocalMessage("swm.validation.emsdCollType")+ rowCount);
						}

						if (startTime == "" || startTime == null) {
							errorList.push(getLocalMessage("swm.validation.startTime")+ rowCount);
						}
						for (j = 0; j < i; j++) {
							if ($('#empid' + j).val() == $('#empid' + i).val()) {
								if ($('#veId' + j).val() == $('#veId' + i).val()) {
									errorList.push("The Vehicle No. already exists!");
								}
								if ($('#roId' + j).val() == $('#roId' + i).val()) {
									errorList.push("The Route already exists!");
								}
								if ($('#emsdCollType' + j).val() == $('#emsdCollType' + i).val()) {
									errorList.push("The Task already exists!");
								}
								if ($('#startTime' + j).val() == $('#startTime' + i).val()) {
									errorList.push("The InTime  already exists!");
								}
							}
						}
					});
	return errorList;
}

$(function() {
	
	$("#unitDetailTable").on('click', '.datetimepicker3', function() {
		$('.datetimepicker3').timepicker();
	});

});
$(function() {
	
	$("#unitDetailTable").on('click', '.addCF', function() {
		
		var errorList = [];
		errorList = validateUnitDetailTable(errorList);
		if (errorList.length == 0) {
			$(".datetimepicker3").timepicker("destroy");
			var content = $("#unitDetailTable").find('tr:eq(1)').clone();
			$("#unitDetailTable").append(content);
			content.find("input:text").val('');
			content.find("select").val('0');
			content.find("input:hidden:eq(0)").val('0');
			$('.error-div').hide();
			reOrderUnitTabIdSequence('.firstUnitRow'); // reorder id and Path
			$(function() {
				$('.datetimepicker3').timepicker();
			});
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});
function reOrderUnitTabIdSequence(firstRow) {
	
	$(firstRow).each(
			function(i) {
				
				var utp = i;
				if (i > 0) {
					utp = i * 6;
				}
				// IDs
				$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
				$(this).find("select:eq(0)").attr("id", "empid" + i);
				$(this).find("select:eq(1)").attr("id", "codWard" + utp);
				$(this).find("select:eq(2)").attr("id", "codWard" + (utp + 1));
				$(this).find("select:eq(3)").attr("id", "emsdCollType" + i);
				$(this).find("input:text:eq(1)").attr("id", "startTime" + i);
				$(this).find("input:text:eq(2)").attr("id", "endTime" + i);
				// names
				$(this).find("input:text:eq(0)").val(i + 1);
				$(this).find("select:eq(0)").attr("name","employeeScheduleDto.tbSwEmployeeScheddets[" + i + "].empid");
				$(this).find("select:eq(1)").attr("name","employeeScheduleDto.tbSwEmployeeScheddets[" + i + "].codWard1");
				$(this).find("select:eq(2)").attr("name","employeeScheduleDto.tbSwEmployeeScheddets[" + i + "].codWard2");
				$(this).find("select:eq(3)").attr("name","employeeScheduleDto.tbSwEmployeeScheddets[" + i + "].emsdCollType");
				$(this).find("input:text:eq(1)").attr("name","employeeScheduleDto.tbSwEmployeeScheddets[" + i + "].startTime");
				$(this).find("input:text:eq(2)").attr("name","employeeScheduleDto.tbSwEmployeeScheddets[" + i + "].endTime");
			});
}
function validateUnitDetailTable(errorList) {
	var rowCount = $('#unitDetailTable tr').length;
	$('.firstUnitRow').each(
					function(i) {
						
						if (rowCount <= 2) {
							var empid = $("#empid" + i).val();
							var codWard1 = $("#codWard" + i).val();
							var codWard2 = $("#codWard" + i).val();
							var emsdCollType = $("#emsdCollType" + i).val();
							var startTime = $("#startTime" + i).val();
							var endTime = $("#endTime" + i).val();
							var level = 1;
						} else {
							var utp = i;
							utp = i * 6;
							var empid = $("#empid" + i).val();
							var codWard1 = $("#codWard" + utp).val();
							var codWard2 = $("#codWard" + (utp + 1)).val();
							var emsdCollType = $("#emsdCollType" + i).val();
							var startTime = $("#startTime" + i).val();
							var endTime = $("#endTime" + i).val();
							var level = i + 1;
						}
						var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
						if (empid == '' || empid == undefined || empid == '0') {
							errorList.push(getLocalMessage('swm.empName.select')+ " " + level);
						}
						if (codWard1 == '' || codWard1 == undefined || codWard1 == '0') {
							errorList.push(getLocalMessage('swm.select.zone')+ " " + level);
						}
						if (emsdCollType == '' || emsdCollType == undefined || emsdCollType == '0') {
							errorList.push(info + getLocalMessage('swm.select.task')+ " " + level);
						}
						if (startTime == '' || startTime == undefined || startTime == '0') {
							errorList.push(getLocalMessage('swm.select.fromTime')+ " " + level);
						}
						if (endTime == '' || endTime == undefined || endTime == '0') {
							errorList.push(getLocalMessage('swm.select.endTime')+ " " + level);
						}
						if (endTime < startTime) {
							errorList.push(getLocalMessage('swm.sanitary.validation.timevalidation')+ " " + level);
						}
						for (j = 0; j < i; j++) {
							if ($('#empid' + j).val() == $('#empid' + i).val()) {
								if ($('#codWard' + j).val() == $("#codWard" + utp).val()) {
									errorList.push("The Zone  already exists!");
								}
								if ($('#codWard' + j).val() == $("#codWard" + utp + 1).val()) {
									errorList.push("The Ward  already exists!");
								}
								if ($('#emsdCollType' + j).val() == $('#emsdCollType' + i).val()) {
									errorList.push("The Task  already exists!");
								}
								if ($('#startTime' + j).val() == $('#startTime' + i).val()) {
									errorList.push("The InTime already exists!");
								}
								if ($('#endTime' + j).val() == $('#endTime' + i).val()) {
									errorList.push("The OutTime already exists!");
								}
							}
						}
					});
	return errorList;
}
function displayErrorsPage(errorList) {
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}
function updateData(element) {
	
	var errorList = [];
	errorList = validateDisposalEditDetails(errorList);
	errorList = validateVehicleWiseEditDetails(errorList);
	errorList = validateAreaWiseEditDetails(errorList);
	errorList = validateAreaWiseDetail(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsPage(errorList);
	} else {
		return saveOrUpdateForm(element,getLocalMessage('swm.saveSanitaryStaffScheduling'),'Scheduling.html', 'saveform');
	}
}
function validateDisposalEditDetails(errorList) {
	var j = 0;
	if ($.fn.DataTable.isDataTable('#id_sanitaryschedulingTbl1')) {
		$('#id_sanitaryschedulingTbl1').DataTable().destroy();
	}
	$("#id_sanitaryschedulingTbl1 tbody tr")
			.each(function(i) {
						
						var empid = $("#empid" + i).find("option:selected").attr('value');
						var deId = $("#deId" + i).find("option:selected").attr('value');
						var emsdCollType = $("#emsdCollType" + i).find("option:selected").attr('value');
						var startTime = $("#startTime" + i).val();
						var endTime = $("#endTime" + i).val();
						var rowCount = i + 1;
						if (empid == "" || empid == null) {
							errorList.push(getLocalMessage("swm.validation.empid")
											+ rowCount);
						}
						if (emsdCollType == "0" || emsdCollType == '') {
							errorList.push(getLocalMessage("swm.validation.emsdCollType")+ rowCount);
						}
						if (startTime == "" || startTime == null) {
							errorList.push(getLocalMessage("swm.validation.startTime")+ rowCount);
						}
						if (endTime == "" || endTime == null) {
							errorList.push(getLocalMessage("swm.validation.endTime")+ rowCount);
						}
						if (endTime < startTime) {
							errorList.push(getLocalMessage("swm.sanitary.validation.timevalidation")+ rowCount);
						}
					});
	return errorList;
}

//
function validateVehicleWiseEditDetails(errorList) {
	var j = 0;
	if ($.fn.DataTable.isDataTable('#id_sanitaryschedulingTbl2')) {
		$('#id_sanitaryschedulingTbl2').DataTable().destroy();
	}
	$("#id_sanitaryschedulingTbl2 tbody tr")
			.each(function(i) {
						
						var emsdCollType = $("#emsdCollType" + i).find("option:selected").attr('value');
						var startTime = $("#startTime" + i).val();
						var endTime = $("#endTime" + i).val();
						var rowCount = i + 1;
						if (emsdCollType == "0" || emsdCollType == '') {
							errorList.push(getLocalMessage("swm.validation.emsdCollType")+ rowCount);
						}
						if (startTime == "" || startTime == null) {
							errorList.push(getLocalMessage("swm.validation.startTime")+ rowCount);
						}
						if (endTime == "" || endTime == null) {
							errorList.push(getLocalMessage("swm.validation.endTime")+ rowCount);
						}
						if (endTime < startTime) {
							errorList.push(getLocalMessage("swm.sanitary.validation.timevalidation")+ rowCount);
						}
					});
	return errorList;
}
function validateAreaWiseEditDetails(errorList) {
	var j = 0;
	if ($.fn.DataTable.isDataTable('#id_sanitaryschedulingTbl3')) {
		$('#id_sanitaryschedulingTbl3').DataTable().destroy();
	}
	$("#id_sanitaryschedulingTbl3 tbody tr").each(
					function(i) {
						
						var emsdCollType = $("#emsdCollType" + i).find("option:selected").attr('value');
						var startTime = $("#startTime" + i).val();
						var endTime = $("#endTime" + i).val();
						var rowCount = i + 1;
						if (emsdCollType == "0" || emsdCollType == '') {
							errorList.push(getLocalMessage("swm.validation.emsdCollType")+ rowCount);
						}
						if (startTime == "" || startTime == null) {
							errorList.push(getLocalMessage("swm.validation.startTime")+ rowCount);
						}
						if (endTime == "" || endTime == null) {
							errorList.push(getLocalMessage("swm.validation.endTime")+ rowCount);
						}
						if (endTime < startTime) {
							errorList.push(getLocalMessage("swm.sanitary.validation.timevalidation")+ rowCount);
						}
					});
	return errorList;
}

function validateAreaWiseDetail(errorList) {
	var rowCount = $('#unitDetailTable tr').length;
	$('.firstUnitRow')
			.each(
					function(i) {
						
						if (rowCount <= 2) {
							var empid = $("#empid" + i).val();
							var codWard1 = $("#codWard" + i).val();
							var codWard2 = $("#codWard" + i).val();
							var emsdCollType = $("#emsdCollType" + i).val();
							var startTime = $("#startTime" + i).val();
							var endTime = $("#endTime" + i).val();
							var level = 1;
						} else {
							var utp = i;
							utp = i * 6;
							var empid = $("#empid" + i).val();
							var codWard1 = $("#codWard" + utp).val();
							var codWard2 = $("#codWard" + (utp + 1)).val();
							var emsdCollType = $("#emsdCollType" + i).val();
							var startTime = $("#startTime" + i).val();
							var endTime = $("#endTime" + i).val();
							var level = i + 1;
						}
						if (startTime == '' || startTime == undefined || startTime == '0') {
							errorList.push(getLocalMessage('swm.select.fromTime')+ " " + level);
						}
						if (endTime == '' || endTime == undefined || endTime == '0') {
							errorList.push(getLocalMessage('swm.select.endTime')+ " " + level);
						}
						if (endTime < startTime) {
							errorList.push(getLocalMessage('swm.sanitary.validation.timevalidation')+ " " + level);
						}
					});
	return errorList;
}

function searchSanitaryStaffScheduling() {
	
	$('.error-div').hide();
	runCalendar();
}

/** ******************DeleteFunction*********** */
function deleteSanitaryScheduling(emsId, formUrl, actionParam) {
	
	if (actionParam == "deleteSanitaryStaffScheduling") {
		showConfirmBoxForDelete(emsId, actionParam);
	}
}
function showConfirmBoxForDelete(emsId, actionParam) {
	
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-danger padding-5\">'
			+ getLocalMessage('Do you want to delete?') + '</h4>';
	message += '<div class=\'text-center padding-bottom-18\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + emsId + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}
function proceedForDelete(emsId) {
	
	$.fancybox.close();
	var requestData = 'emsId=' + emsId;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('Scheduling.html?'+ 'deleteSanitaryStaffScheduling', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function resetForm() {
	
	$('input[type=text]').val('');
	$('#Address').val('');
	$('input[type=select]').val('');
	$("#postMethodForm").prop('action', '');
	$("select").val("").trigger("chosen:updated");
	$('.error-div').hide();
}

function runCalendar() {
	
	var errorList = [];
	var empid = $("#empid").val();
	var data = {
		"empId" : empid
	};
	var ajaxResponse = __doAjaxRequest('Scheduling.html' + '?getCalData','POST', data, false, 'json');
	$('#calendar').fullCalendar('destroy');
	if (ajaxResponse.length == 0) {
		errorList.push(getLocalMessage('swm.emp.schedule.not.available'));
		displayErrorsPage(errorList);
	}
	console.log('-----------' + JSON.stringify(ajaxResponse));
	var calendar = $('#calendar').fullCalendar({
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
				eventSources : [ {
					events : function(start, end, callback) {
						var events = [];
						$(ajaxResponse).each(function(index) {
							events.push({
								title : $(this).attr('title'),
								start : new Date($(this).attr('start')),
								className : $(this).attr('className'),
								id : $(this).attr('id'),
								allDay : false
							});
						});
						callback(events);
					},
				}, ],
				eventColor : '#333333',
				timeFormat : 'hh:mm',
				eventClick : function(calEvent, jsEvent, view) {
					
					var requestData = {
						"empid" : calEvent.id,
						"fromDate" : calEvent.start
					};
					var ajaxResponse = __doAjaxRequest('Scheduling.html?searchSanitaryStaffScheduling','POST', requestData, false, 'html');
					$('.content').html(ajaxResponse);
				}
			});
}

function showVehicleRegNo() {
	
	if ($.fn.DataTable.isDataTable('#id_sanitaryschedulingTbl3')) {
		$('#id_sanitaryschedulingTbl3').DataTable().destroy();
	}
	$("#id_sanitaryschedulingTbl3 tbody tr").each(
			function(i) {
				
				var veVetype = $("#veVetype" + i).find("option:selected").attr('value');
				$('#veId'+i).html('');
				var crtElementId = $("#veVetype" + i).find("option:selected").attr('value');
				if (crtElementId == -1) {
					crtElementId = "";
				}
				var requestUrl = "Scheduling.html?vehicleNo";
				var requestData = {
					"id" : crtElementId
				};
				var ajaxResponse = __doAjaxRequest(requestUrl, 'post',requestData, false, 'json');
				$('#veId'+ i).append($("<option></option>").attr("value", "").attr("code","").text("select"));
				$.each(ajaxResponse, function(index, value) {
					$('#veId'+ i).append($("<option></option>").attr("value", index).attr("code", index).text(value));
				});
				$('#veId'+ i).trigger('chosen:updated');
			});
}
