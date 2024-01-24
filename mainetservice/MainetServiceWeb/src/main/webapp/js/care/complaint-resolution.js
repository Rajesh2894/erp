$(document).ready(function() {

	var documentId = $('#applicationId').val();
	if ($("#reqType").val() == "C") {
		$('input:radio[name="careRequest.applnType"]').filter('[value="C"]').attr('checked', true);
	} else {
		$('input:radio[name="careRequest.applnType"]').filter('[value="R"]').attr('checked', true);
	}
	$('.fancybox').fancybox();
	$(".All_wardZone_Div").hide();
	$(".wardZoneBlockDiv").hide();
	$(".ComplaintSubType_Div").hide();

	var remarks = $('#remarks').val();
	if (remarks != null) {
		$('#Remark').val(' ');
	}
	
	var dateFields = $('.datepicker');
	dateFields.each(function () {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});

	$("#viewloc").click(function() {
		
		var geocoder = new google.maps.Geocoder();
		var con = document.getElementById('Pincode').value;
		var city = document.getElementById('ComplaintLocation').value;
		var compLatitude = $("#compLatitude").val();
		var compLongitude = $("#compLongitude").val();
		var title = $("#ComplaintLocation").val();
		var com = con;

		if(compLatitude != null && compLongitude != null && compLatitude.length > 0 && compLongitude.length > 0){
			// Display by complaint location lat/log
			var latlng = new google.maps.LatLng(compLatitude,compLongitude);
			loadGoogleMap(latlng,title);
		}else{
			geocoder.geocode({'address' : com},	function(results, status) {
				// Display by pincode location lat/log
				if (status == google.maps.GeocoderStatus.OK) {
					var x = results[0].geometry.location.lat();
					var y = results[0].geometry.location.lng();
					var latlng = new google.maps.LatLng(x,y);
					loadGoogleMap(latlng,com);
				} else {
					res.innerHTML = getLocalMessage("care.details")+ status;
				}

			});
		}
		
	});

	$('.forwardtoemployee').click(function() {
		if ($(this).attr("value") == "LOCATION") {

			$('#EmployeeName').multiselect(
					'deselect',
					$("#EmployeeName")
					.val());

			$(".boxshowhide").not(
			".Locationwiseradio")
			.hide();
			$(".Locationwiseradio").show();

			var requestData = 'deptId=' + $('#Department').val();

			if ($('#dpDeptId').val() != '0') {
				var response = __doAjaxRequest(
						"GrievanceResolution.html?areaMapping",
						'post',
						requestData, false,
				'html');
				$('#areaMappingId1').html(
						response);

			} else {
				$('#areaMappingId1').html(
				'');
			}


		}
		if ($(this).attr("value") == "EMPLOYEE") {
			$(".boxshowhide").not(".WithinDepartment").hide();
			$(".WithinDepartment").show();
			$('.multiselect').addClass('mandColorClass');
		}
	});

	$("#ComplaintSubType").change(function() {
		var requestData = "deptId=" + $('#dpDeptId').val() + "&compTypeId=" + $("#ComplaintSubType").val();
		var response = __doAjaxRequest(	"GrievanceResolution.html?isWardZoneRequired", 'post',	requestData, false, 'html');
		var isWardZoneRequired = (response == 'true');
		if(isWardZoneRequired){
			enableWardZoneSelection();
		}else{
			$(".wardZoneBlockDiv").hide();
		}

	}); 
	
	$('.WardZoneAll').click(function() {

		if ($(this).attr("value") == "A") {
			$(".wardZoneBlockDiv").hide();
		} else if ($(this).attr("value") == "W") {
			enableWardZoneSelection();
		} else {
			$(".wardZoneBlockDiv").hide();
		}
	});

	$("#ComplaintSubType").change(function() {
		var requestData = "deptId=" + $('#dpDeptId').val() + "&compTypeId=" + $("#ComplaintSubType").val();
		var response = __doAjaxRequest(	"GrievanceResolution.html?isWardZoneRequired", 'post',	requestData, false, 'html');
		var isWardZoneRequired = (response == 'true');
		if(isWardZoneRequired){
			enableWardZoneSelection();
		}else{
			$(".wardZoneBlockDiv").hide();
		}

	}); 

	$("#btnSave").click(function(e) {
		var errorList = [];
		var ApplicationAction = $('#ApplicationAction').val();
		var Remark = $('#Remark').val();
		var dpDeptId = $('#dpDeptId').val();
		var compSubTypeId = $("#ComplaintSubType").val();
		var codIdOperLevel1 = $('#codIdOperLevel1').val();
		var codIdOperLevel2 = $('#codIdOperLevel2').val();
		var codIdOperLevel3 = $('#codIdOperLevel3').val();
		var codIdOperLevel4 = $('#codIdOperLevel4').val();
		var codIdOperLevel5 = $('#codIdOperLevel5').val();
		var kdmcEnv = $('#kdmcEnv').val();
		var reasonToForwardId = $('#reasonToForwardId').val();
		

		var DeptRadioValue = $(
				'input[name="careDepartmentAction.deptRadioAction"]:checked',
		'#care').val();

		var EmployeeRadioValue = $(
				'input[name="careDepartmentAction.forwardToEmployeeType"]:checked',
		'#care').val();

		var empId = $("#EmployeeName").val();

		if (ApplicationAction == ''
			|| ApplicationAction == 'Select'
				|| ApplicationAction == undefined) {
			errorList
			.push(getLocalMessage("care.error.applicationAction"));
		}

		if(Remark != undefined && Remark != null)
			Remark = Remark.trim();
		if (Remark == '' || Remark == undefined) {
			errorList.push(getLocalMessage("care.error.remark"));
		}

		if (ApplicationAction == 'FORWARD_TO_DEPARTMENT') {

			if (dpDeptId == '' || dpDeptId == 'Select'
				|| dpDeptId == undefined) {
				errorList.push(getLocalMessage("care.error.dpDeptId"));
			}

			if (compSubTypeId == '' || compSubTypeId == 'Select'
				|| compSubTypeId == undefined) {
				errorList.push(getLocalMessage("care.error.compSubTypeId"));
			}
			/*Defect #127176*/
			if(kdmcEnv == 'Y'){
				if(reasonToForwardId == '' || reasonToForwardId == 'Select'
					|| reasonToForwardId == undefined){
					errorList.push(getLocalMessage("care.error.reasonToForwardId"));
				}
			}


			if (dpDeptId != 'Select') {

				if ($(".wardZoneBlockDiv").is(':visible')) {
					if (codIdOperLevel1 == '0') {
						var codLbl = $("label[for='"+$('#codIdOperLevel1').attr('id')+"']").text();
						errorList.push(getLocalMessage("care.error.select") +codLbl+'.');
					}
					if (codIdOperLevel2 == '0') {
						var codLbl = $("label[for='"+$('#codIdOperLevel2').attr('id')+"']").text();
						errorList.push(getLocalMessage("care.error.select")+codLbl+'.');
					}
					if (codIdOperLevel3 == '0') {
						var codLbl = $("label[for='"+$('#codIdOperLevel3').attr('id')+"']").text();
						errorList.push(getLocalMessage("care.error.select")+codLbl+'.');
					}
					if (codIdOperLevel4 == '0') {
						var codLbl = $("label[for='"+$('#codIdOperLevel4').attr('id')+"']").text();
						errorList.push(getLocalMessage("care.error.select")+codLbl+'.');
					}
					if (codIdOperLevel5 == '0') {
						var codLbl = $("label[for='"+$('#codIdOperLevel5').attr('id')+"']").text();
						errorList.push(getLocalMessage("care.error.select")+codLbl+'.');
					}
				}


				var data = new FormData(document
						.getElementById("care"));
				$
				.ajax({
					url : "/MainetService/GrievanceResolution.html?verifyWorkflowTypeByDepartmentAction",
					type : "POST",
					data : data,
					enctype : 'multipart/form-data',
					processData : false,
					contentType : false,
					async : false,
					success : function(response) {
						if (response === null
								|| response === 'N') {
							errorList
							.push(getLocalMessage("care.error.workflow"));
						}
					},
					error : function(data) {
						displayMessageOnError(data);
					}
				});

			}

			// Make a rest call to get workflowType
		}

		if (ApplicationAction == 'FORWARD_TO_EMPLOYEE') {
			if (EmployeeRadioValue == undefined) {
				errorList
				.push(getLocalMessage("care.error.employeeRadioValue"));
			}

			if (EmployeeRadioValue === "EMPLOYEE") {

				if (empId === null || empId === '') {

					errorList.push(getLocalMessage("care.error.employee"));
				}
			} else if (EmployeeRadioValue === "LOCATION") {

				if (codIdOperLevel1 == '0') {
					var codLbl = $("label[for='"+$('#codIdOperLevel1').attr('id')+"']").text();
					errorList.push(getLocalMessage("care.error.select")+codLbl+'.');
				}
				if (codIdOperLevel2 == '0') {
					var codLbl = $("label[for='"+$('#codIdOperLevel2').attr('id')+"']").text();
					errorList.push(getLocalMessage("care.error.select")+codLbl+'.');
				}
				if (codIdOperLevel3 == '0') {
					var codLbl = $("label[for='"+$('#codIdOperLevel3').attr('id')+"']").text();
					errorList.push(getLocalMessage("care.error.select")+codLbl+'.');
				}
				if (codIdOperLevel4 == '0') {
					var codLbl = $("label[for='"+$('#codIdOperLevel4').attr('id')+"']").text();
					errorList.push(getLocalMessage("care.error.select")+codLbl+'.');
				}
				if (codIdOperLevel5 == '0') {
					var codLbl = $("label[for='"+$('#codIdOperLevel5').attr('id')+"']").text();
					errorList.push(getLocalMessage("care.error.select")+codLbl+'.');
				}

			}

		}

		if (errorList.length == 0) {
			var data = new FormData(document.getElementById("care"));
			$.ajax({	url : "/MainetService/GrievanceResolution.html?saveDetails",
				type : "POST",
				data : data,
				enctype : 'multipart/form-data',
				processData : false,
				contentType : false,
				async : false,
				success : function(response) {
					if (response != null) {
						displayMessageOnSubmit(response);
					}
			},
				error : function(response) {
					displayMessageOnError(response);
				}
			});

		} else {
			displayErrorsOnPage(errorList);
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
		}
		return true;

	});
	
	//T#106045
	$("#btnInspection").click(function(e) {
		let complaintNo=$('#tokenNumber').val();
		var requestData = {
				"complaintId" : complaintNo,
				"taskId": $('#taskId').val(),
				"applicationId": $('#applicationId').val()
			}
			var ajaxResponse = __doAjaxRequest(
					'LandInspection.html?form', 'POST',
					requestData, false, 'html');
			$('.content-page').html(ajaxResponse);
		

	});
	
	


});





/*function showComplaintType(obj){
	$(".wardZoneBlockDiv").hide();
	var dptId = $('#dpDeptId').val();
	var getAllComplaintTypeByDepartment = "/MainetService/GrievanceDepartmentRegistration.html?findDepartmentComplaintByDepartmentId";
	var _csrf = "${_csrf.token}";
	$.ajax({
		url: getAllComplaintTypeByDepartment,
		type: "POST",
		data: {
			id: dptId,
			_csrf:_csrf
		},
		success: function(response) {
			$('#ComplaintSubType').empty();
			$('#ComplaintSubType').append('<option value="">Select</option>');
			$.each(response,function(i,item) {
				$('#ComplaintSubType')
				.append('<option  value="' + item.compId + '">' + item.complaintDesc +  '</option>');
			});
			$(".ComplaintSubType_Div").show();
		},
		error: function(xhr,
				errorType,
				exception) {
			var errorMessage = exception ||
			xhr.statusText;
			alert(getLocalMessage("care.error") +
					errorMessage);
		}
	});
}*/

function resetFormData(obj) {
	$('.RelevantDepartment').hide();
	$(".wardZoneBlockDiv").hide();
	$('.EmployeewithinDepartment').hide();
	$('.Locationwiseradio').hide();
	$('.WithinDepartment').hide();
	$('.error-div').hide();
	//D#127175
	var action = obj.form.action;
	var url	=	action+'?cleareFile';
	var count= __doAjaxRequest(url,'post',{},false);
   
	for(var i = 0; i < count; i++){
	  $("#file_list_"+i).find("*").remove(); 
	 }
	$("#file_list_"+i).hide();
	fileUploadMultipleValidationList();
}




function enableWardZoneSelection(){
	$(".wardZoneBlockDiv").show();
	var errorList = [];
	var deptId = $('#dpDeptId').val();

	if (deptId == '') {
		errorList.push(getLocalMessage("care.error.department"));
	}
	if (errorList.length == 0) {

		var requestData = 'deptId=' + $('#dpDeptId').val();
		var response = __doAjaxRequest(	"GrievanceResolution.html?areaMapping", 'post',	requestData, false, 'html');
		$('#areaMappingId').html(response);

		if ($('#prefixHidden').val() === null || $('#prefixHidden').val() === '') {
			$(".wardZoneBlockDiv").hide();
			showErrormsgboxTitle(getLocalMessage("care.error.warzone"));
			$('.WardZoneAll').removeAttr("checked", false);
		}
	} else {
		displayErrorsOnPage(errorList);
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
	}

}

function showComplaintType(obj){
	//var langId= $("#langId").val();
	 //console.log(langId);
	$(".wardZoneBlockDiv").hide();
	var dptId = $('#dpDeptId').val();
	var getAllComplaintTypeByDepartment = "/MainetService/GrievanceDepartmentRegistration.html?findDepartmentComplaintByDepartmentId";
	var _csrf = "${_csrf.token}";
	$.ajax({
		url: getAllComplaintTypeByDepartment,
		type: "POST",
		data: {
			id: dptId,
			_csrf:_csrf
		},
		success: function(response) {
			
			$('#ComplaintSubType').empty();
			$('#ComplaintSubType').append('<option value="">Select</option>');
			if (langId === 1) {
				$.each(response, function(i, item) {
					$('#ComplaintSubType').append(
							'<option  value="' + item.compId + '">'
									+ item.complaintDesc + '</option>');
				});
			} else {
				$.each(response, function(i, item) {
					$('#ComplaintSubType').append(
							'<option  value="' + item.compId + '">'
									+ item.complaintDescReg + '</option>');
				});
			}
			$(".ComplaintSubType_Div").show();
		},
		error: function(xhr,
				errorType,
				exception) {
			var errorMessage = exception ||
			xhr.statusText;
			alert(getLocalMessage("care.error") +
					errorMessage);
		}
	});
}





function resetFormData(obj) {
	$('.RelevantDepartment').hide();
	$(".wardZoneBlockDiv").hide();
	$('.EmployeewithinDepartment').hide();
	$('.Locationwiseradio').hide();
	$('.WithinDepartment').hide();
	$('.error-div').hide();
	//D#127175
	var action = obj.form.action;
	var url	=	action+'?cleareFile';
	var count= __doAjaxRequest(url,'post',{},false);
   
	for(var i = 0; i < count; i++){
	  $("#file_list_"+i).find("*").remove(); 
	 }
	$("#file_list_"+i).hide();
	fileUploadMultipleValidationList();
	
}




function enableWardZoneSelection(){
	$(".wardZoneBlockDiv").show();
	var errorList = [];
	var deptId = $('#dpDeptId').val();

	if (deptId == '') {
		errorList.push(getLocalMessage("care.error.department"));
	}
	if (errorList.length == 0) {

		var requestData = 'deptId=' + $('#dpDeptId').val();
		var response = __doAjaxRequest(	"GrievanceResolution.html?areaMapping", 'post',	requestData, false, 'html');
		$('#areaMappingId').html(response);

		if ($('#prefixHidden').val() === null || $('#prefixHidden').val() === '') {
			$(".wardZoneBlockDiv").hide();
			showErrormsgboxTitle(getLocalMessage("care.error.warzone"));
			$('.WardZoneAll').removeAttr("checked", false);
		}
	} else {
		displayErrorsOnPage(errorList);
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
	}

}


function displayMessageOnError(Msg) {

	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	message += '<p>' + Msg + '</p>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

function displayMessageOnSubmit(Msg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	/*Defect #115163*/
	/*Error msg added if workflow is not defined for forward_to_department action- WNF(Workflow Not Found)*/
	if(Msg === 'failure'){
		message += '<h4 class="text-center text-blue-2 padding-10 padding-bottom-0">'+getLocalMessage("care.error")+'</h4>';
	}else if(Msg === 'WNF'){
		message += '<h4 class="text-center text-blue-2 padding-10 padding-bottom-0">'+getLocalMessage("care.error.wardzone.workflow")+'</h4>';
	}else if(Msg === 'TNF'){
		message += '<h4 class="text-center text-blue-2 padding-10 padding-bottom-0">'+getLocalMessage("care.workflow.error.taskNotFound")+'</h4>';
	}else if(Msg === 'FORWARD_TO_DEPARTMENT'){
		message += '<h4 class="text-center text-blue-2 padding-10 padding-bottom-0">'+getLocalMessage("care.submit.msg.forwardtodepartment")+'</h4>';
	}else if(Msg === 'FORWARD_TO_EMPLOYEE'){
		message += '<h4 class="text-center text-blue-2 padding-10 padding-bottom-0">'+getLocalMessage("care.submit.msg.forwardtoemployee")+'</h4>';
	}else
		message += '<h4 class="text-center text-blue-2 padding-10 padding-bottom-0">'+getLocalMessage("care.report.submit")+'</h4>';
	
	message += '<div class="text-center">'
		+ '<a href="AdminHome.html" class=\'btn btn-blue-2 margin-bottom-10\'>'+getLocalMessage("care.close")+'</a>'
		+ '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
}

function displayErrorsOnPage(errorList) {

	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('.error-div').html(errMsg);
	$(".error-div").show();

	return false;
}

function loadGoogleMap(latlng,title){
	var myOptions = {
			zoom : 13,
			center : latlng,
			mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	map = new google.maps.Map(document.getElementById("map-canvas"),myOptions);
	var marker = new google.maps.Marker(
			{
				position : latlng,
				map : map,
				title : title
			});
	var infowindow = new google.maps.InfoWindow({content : title});
	infowindow.open(map,marker);
	google.maps.event.addDomListener(window,'load');

}
