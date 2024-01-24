$(document).ready(function() {

	$("#incidentDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	$('#fromDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true
	});
	
	$("#fromDate").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('#toDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true
	});

	$("#toDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});	
	
	
	$('#date').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		maxDate : '-0d',
		changeYear : true,
		timepicker: false
	});

	$("#date").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
	//Defect #151756
	$('#date').on('keydown', function(e){
		e.preventDefault();
	});
	//Defect #151755
	$('.hasTime').on('blur', function() {
	    this.value = this.value.replace(/[^0-9:]/g,'');
	});
	
	var timeFields = $('.timepicker');
	timeFields.each(function () {
		
		var fieldValue = $(this).val();
		if (fieldValue.length > 5) {
			$(this).val(fieldValue.substr(0, 5));
		}
	});
	
});
	

/*$(".datepicker").datepicker({
	dateFormat : 'dd/mm/yy',
	changeMonth : true,
	maxDate : '-0d',
	changeYear : true,
});*/

$(".timepicker").timepicker({
	// s dateFormat: 'dd/mm/yy',
	changeMonth : true,
	changeYear : true,
	minDate : '0',
});

$("#time").timepicker({

});
 prepareDateTag();

$("#frmDailyIncident").validate({
	onkeyup : function(element) {
		this.element(element);
		console.log('onkeyup fired');
	},
	onfocusout : function(element) {
		this.element(element);
		console.log('onfocusout fired');
	}
});

function SearchDailyIncident() {
	
	var errorList = [];
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var date = new Date();

	if (fromDate == null || fromDate == "") {
		errorList.push(getLocalMessage("securityManagement.select.fromDate"));
	}
	if (toDate == null || toDate == "") {
		errorList.push(getLocalMessage("securityManagement.select.toDate"));
	}
	if (errorList.length > 0) {
		checkDate(errorList);
	} else {
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date(fromDate.replace(pattern, '$3-$2-$1'));
		var sDate = new Date(toDate.replace(pattern, '$3-$2-$1'));

		if (eDate > sDate) {
			errorList
					.push(getLocalMessage("DeploymentOfStaffDTO.Validation.fromTodate"));
		}
		if (sDate >= date) {
			errorList
					.push(getLocalMessage("securityManagement.toDate.not.greater.currentDate"));
		}

		if (errorList.length > 0) {
			checkDate(errorList);
		}
	}

	if (fromDate != '' || toDate != '') {
		var requestData = '&fromDate=' + fromDate + '&toDate=' + toDate;
		var table = $('#incidentDataTable').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var response = __doAjaxRequest(
				'dailyIncidentRegister.html?searchIncident', 'POST',
				requestData, false, 'json');
		var daDtos = response;
		if (daDtos.length == 0) {
			errorList.push(getLocalMessage("DeploymentOfStaffDTO.Validation.notFound"));
			displayErrorsOnPage(errorList);
			return false;
		}
		var result = [];
		$
				.each(
						daDtos,
						function(index) {
							var obj = daDtos[index];
							let incidentId = obj.incidentId;
							let date = getDateFormat(obj.date);
							let time = obj.time;
							let remarks = obj.remarks;
							let nameVisitingOffJoin = obj.nameVisitingOffJoin;

							result
									.push([
											'<div class="text-center">'
													+ (index + 1) + '</div>',
											'<div class="text-center">' + date
													+ '</div>',
											'<div class="text-center">' + time
													+ '</div>',
											'<div class="text-center">'
													+ remarks + '</div>',
											'<div class="text-center">'
													+ nameVisitingOffJoin
													+ '</div>',
											'<div class="text-center">'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="modifyIncident(\''
													+ incidentId
													+ '\',\'dailyIncidentRegister.html\',\'viewDIR\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
													+ '<button type="button" class="btn btn-warning btn-sm "  onclick="modifyIncident(\''
													+ incidentId
													+ '\',\'dailyIncidentRegister.html\',\'editDIR\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</div>' ]);

						});
		table.rows.add(result);
		table.draw();
	} else {
		// errorList.push(getLocalMessage("error"));
		displayErrorsOnPage(errorList);
	}
}

function confirmToProceed(element) {
	
	var errorList = [];

	var date = $("#date").val();
	var time = $("#time").val();
	var remarks = $("#remarks").val();
	var nameVisitingOff = $("#nameVisitingOff").val();

	if (date == null || date == "") {
		errorList.push(getLocalMessage("securityManagement.enter.incidentDate"));
	}
	if (time == null || time == "") {
		errorList.push(getLocalMessage("securityManagement.enter.incidentTime"));
	}
	else{
		var regExGst = /^([0-1]?[0-9]|2[0-4]):([0-5][0-9])(:[0-5][0-9])?$/;
		if(!regExGst.test(time)){
			errorList.push(getLocalMessage("securityManagement.enter.incidentTime.validate"));
		}
	}
	if (remarks == null || remarks == "") {
		errorList.push(getLocalMessage("securityManagement.remarks"));
	}
	if (nameVisitingOff == null || nameVisitingOff == "") {
		errorList.push(getLocalMessage("securityManagement.enter.visiting.officerName"));
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
		return false;
	} else {
		return saveOrUpdateForm(element, "", 'dailyIncidentRegister.html',
				'saveform');
	}
}

function modifyIncident(incidentId, formUrl, actionParam, mode) {
	

	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : incidentId
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', false);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

}

function checkDate(errorList) {
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
}



/*function resetForm(element){
	$("#frmDailyIncident").submit();
}*/
function resetForm() {
    $("#frmDailyIncident").prop('action', '');
    $("select").val("").trigger("chosen:updated");
    $('.error-div').hide();
    prepareTags();
}















