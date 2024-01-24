$(document).ready(function() {
	
	prepareDateTag();
});

$(window).on('load', function() {
	onPageLoad();
});

function onPageLoad() {
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
	
	$(".fromDate").datepicker({		     	
        dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate : '-0d',
       /* onSelect: function(selected) {
          $(".toDateClass").datepicker("option","minDate", selected)	*/
       /* }	*/
    });

	$(".toDate").datepicker({			      	
        dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate : '-0d',
        /*onSelect: function(selected) {	
           $(".fromDateClass").datepicker("option","maxDate", selected)	
        }	*/
    }); 
	
	$("#contStaffSchTo").datepicker({		     	
        dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate : 0,
    });

	
	/*$(function() {
		$('.datetimepicker3').timepicker();
	});
	$(function() {
		$('.datetimepicker3').timepicker();

	});*/
	$(".datepicker2").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-150:-0"
	});

	$(".datepicker2").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
}

function searchEmployeeForOverTime(obj) {
	var errorList = [];
	var divName = '.empAttendance';
	var empTypeId = $("#empTypeId").val();
	var dayPrefixId = $('#empTypeId').attr('code');
	var vendorId = $("#vmVendorid").val();
	var locId = $("#location").val();
	var cpdShiftId = $("#cpdShiftId").val();
	var contStaffSchFrom = $("#contStaffSchFrom").val();
	var contStaffSchTo = $("#contStaffSchTo").val();
	var contStaffName = "";
	var contStaffIdNo = "";
	
	if (empTypeId == 0 || vendorId == 0 || locId == "" || cpdShiftId == 0
			|| contStaffSchFrom == "" || contStaffSchTo == "") {
		errorList
				.push(getLocalMessage("securityManagement.validation.field.mandatory"));
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
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;		
	var contStaffSchFrom1 = new Date(contStaffSchFrom.replace(pattern, '$3-$2-$1'));
	var contStaffSchTo1 = new Date(contStaffSchTo.replace(pattern, '$3-$2-$1'));
	
	if (contStaffSchFrom1 > contStaffSchTo1) {
		errorList.push(getLocalMessage("ContractualStaffMasterDTO.validation.toAndFrom "));
	}
	 if (errorList.length > 0) {
		displayErrorsOnPage(errorList);

	} else {
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

			var ajaxResponse = __doAjaxRequest('EmployeeOvertimeEntry.html'
					+ '?getEmpForOverTime', 'POST', data, false, 'html');
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			$("#contStaffSchFrom").val(contStaffSchFrom);
			$("#contStaffSchTo").val(contStaffSchTo)
			onPageLoad();
			
	}

}

function confirmToSave(element) {
	errorList = [];
	
	errorList = validateOverTime(errorList);
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);

	}
	else{
		return saveOrUpdateForm(element, "", 'EmployeeOvertimeEntry.html',
		'saveform');
	}



}


function validateOverTime(errorList){
	
	$("#frmEmployeeSchedulingTbl tbody tr")
	.each(
			function(i) {
				
				var overTimeShift = $("#otShiftId" + i).val();
				var overTimeShifthr = $("#otShifthr" + i).val();
				var rowCount = i + 1;
				if (overTimeShift == 0 || overTimeShift == null) {
					if(overTimeShifthr != "")
						errorList
								.push(getLocalMessage("securityManagement.validation.overtimeShift") + rowCount);
				}
				if (overTimeShifthr == "" || overTimeShifthr == null) {
	
					if(overTimeShift != 0)
						errorList
								.push(getLocalMessage("securityManagement.validation.overtimeShiftHr") + rowCount);
				}
				
			});
	return errorList;
}