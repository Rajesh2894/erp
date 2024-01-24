$(document).ready(function() {
	
	$("#appointmentDataTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 10,
		"bInfo" : true,
		"lengthChange" : true
	});	
	
	
	$("#visDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : 0
	});
	

	$("#visDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
	$('#visD1').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : 0
	});
	
	$("#visD1").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
	$('.datetimepicker3').timepicker({timeFormat: "HH:mm"});
	    
	    	
	    
	  //code functionality for checkbox check/uncheck
		// add multiple select / deselect functionality
		$("#selectall").click(function () {
			  $('.case').prop('checked', this.checked);
			  /*if(this.checked){
				  checkMemberExistDissolveDate();
			  }*/
		});

		// if all checkbox are selected, check the selectall checkbox and viceversa
		$(".case").change(function(){
			if($(".case").length == $(".case:checked").length) {
				$("#selectall").prop("checked", "checked");
			} else {
				$("#selectall").removeProp("checked");
			}

		});
	
	$('#searchAppointment').click(function() {
		
		var errorList = [];
		
		var appointmentDate = $('#visDate').val();
		
		if (appointmentDate !='') {
			var requestData = '&appointmentDate='+ appointmentDate ;
			var table = $('#appointmentDataTable').DataTable();
			table.rows().remove().draw();
			$(".warning-div").hide();
			var ajaxResponse = __doAjaxRequest('InspectionResheduleController.html?searchAppointments','POST', requestData, false,'json');
			if (ajaxResponse.length == 0) {
				errorList.push(getLocalMessage("mrm.validation.grid.nodatafound"));
				displayErrorsOnPage(errorList);
				return false;
			}
			var result = [];
				$.each(ajaxResponse,function(index) {
					debugger;
					var appointment = ajaxResponse[index];
						
					
						result.push([ 
							'<div align="center">'+ (index + 1) + '</div>',
							'<div align="center">'+appointment.visTime + '</div>',
							'<div align="center">'+appointment.visApplicationId + '</div>',
							'<div align="center">'+appointment.visWing + '</div>',
							'<div class="text-center">'
							+ '<input type="checkbox" class="case" name="case" style="margin-top:5px;margin-left:-10px"  value="'+ appointment.visApplicationId + '"></input>'
							+ '</div>' ]);
						});
				table.rows.add(result);
				table.draw();
			
			} else {
				errorList.push(getLocalMessage("mrm.validation.select.any.field"));
				displayErrorsOnPage(errorList);
			}
	});
});

function resetAppointmentResc(){
	//reset checkbox
	$("#selectall").removeProp("checked");
	$('.case').prop('checked', false);
	$('#visD1').val("");
	$('#visD1').val("");
}

function saveAppointmentResc(data) {
	var appRescIds=[];
	$('input[name="case"]:checked').each(function() {
		appRescIds.push(this.value);
		});
	$('#appointmentIdResc').val(appRescIds);
	var errorList = [];
	errorList = validateAppointmentResc(errorList);
	//check any single checkbox selected or not
	if(appRescIds.length<=0){
		//error msg at least select one
		errorList.push(getLocalMessage('mrm.vldn.checkbox'));
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		
		$("#errorDiv").hide();
		return saveOrUpdateForm(data,getLocalMessage('mrm.appoinResc.save'),'AdminHome.html', 'saveAppointmentResc');
	}

}

function validateAppointmentResc(errorList) {
	debugger;
	let appointmentDate = $("#visD1").val();
	let appointmentTime = $("#visTime").val();
		
	if(appointmentDate == "" || appointmentDate =="0" || appointmentDate == undefined){
		errorList.push(getLocalMessage('mrm.vldnn.resDate'));
	}
	if(appointmentTime == "" || appointmentTime =="0" || appointmentTime == undefined){
		errorList.push(getLocalMessage('mrm.vldnn.resTime'));
	}else{
		//D#117818 regEx for appointment time
		let regexTimePattern =  /^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/;
		if(!regexTimePattern.test(appointmentTime)){
			errorList.push(getLocalMessage('mrm.validation.invalidTime'));
	    }
	}
	return errorList;
}
