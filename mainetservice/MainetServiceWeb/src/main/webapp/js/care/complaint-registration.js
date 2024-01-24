$(document)
		.ready(
				function() {

					// $("#hiddenDOCprefix").hide();
					$('.fancybox').fancybox();
					if ($("#reqType").val() == "C") {
						$('input:radio[name="careRequest.applnType"]').filter(
								'[value="C"]').attr('checked', true);
					} else {
						$('input:radio[name="careRequest.applnType"]').filter(
								'[value="R"]').attr('checked', true);
					}
					
					$('#propertyLblId').hide();
					$('#waterLblId').hide();
					$('#flatNoSelectId').hide();
					$('.refNoClass').hide();
					$(".pendingDues").hide();
					
					if($("#departmentComplaint").find("option:selected").val()!= undefined && $("#departmentComplaint").find("option:selected").val() != 0){
						$('#complaint-zone-ward').show();
						hitForWardZoneAndLocation();
					}else{
						$('#complaint-zone-ward').hide();
					}
					
					if($("#complaintType").find("option:selected").val()!= undefined && $("#complaintType").find("option:selected").val() != 0){
						//used when reset and validation error from server side 
						hitOnChangeSubtypeData();
						//select runtime label D#128992
						let deptCode  =  $("#departmentComplaint :selected").attr('code');
						$('#propertyLblId','.refNoClass').hide();
						$('#refNoLblId','.refNoClass').hide();
						$('#waterLblId','.refNoClass').hide();
						if(deptCode =='AS'){
							$('#propertyLblId').show();
							$('.refNoClass').show();
						}else if(deptCode =='WT'){
							$('#waterLblId').show();
							$('.refNoClass').show();
						}else{
							$('#refNoLblId').show();
						}
					}else{
						$('#residentDivId').hide();	
					}
					
					//used when reset and validation error from server side
					if($('#kdmcEnv').val() == "Y" && $('#extReferNumber').val() != undefined && $('#extReferNumber').val() !='' ){
						//fetchPropertyFlatNo();
						//show when flat no if selected
						if($("#flatNo").find("option:selected").val())
							$('#flatNoSelectId').show();
					}
					
					
					
					$(".datepicker").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true
					});

					$(
							"#referenceMode option[code='WB'],"
									+ "#referenceMode option[code='MA']")
							.remove();

					var complaintTypeId = document
							.getElementById("complaintTypeHidden").value;
					if (complaintTypeId != '') {
						var id = document.getElementById("Department").value;
						var getAllComplaintTypeByDepartment = "/MainetService/GrievanceDepartmentRegistration.html?findDepartmentComplaintByDepartmentId";
						var _csrf = "${_csrf.token}";
						$('#ComplaintSubType').append(
								'<option value="">Select</option>');

						$.ajax({
							url : getAllComplaintTypeByDepartment,
							type : "POST",
							data : {
								id : id,
								_csrf : _csrf
							},
							success : function(response) {
								$('#ComplaintSubType').empty();
								$('#ComplaintSubType').append(
										'<option value="">Select</option>');
								$.each(response, function(i, item) {
									if (complaintTypeId == item.compId) {
										$('#ComplaintSubType').append(
												'<option  value="'
														+ item.compId
														+ '"selected=selected>'
														+ item.complaintDesc
														+ '</option>');
									} else {
										$('#ComplaintSubType').append(
												'<option  value="'
														+ item.compId + '">'
														+ item.complaintDesc
														+ '</option>');
									}
								});
							},
							error : function(xhr, errorType, exception) {
								var errorMessage = exception || xhr.statusText;
								alert("Error occured :" + errorMessage);
							}
						});

					}

					var getAllLocations = "/MainetService/GrievanceDepartmentRegistration.html?fetchData.html?input=";
					var getAllLocationsByPinCode = "/MainetService/GrievanceDepartmentRegistration.html?getAllLocationsByPinCode";
					var getAllLocationsByOrgId = "/MainetService/GrievanceDepartmentRegistration.html?getAllLocationsByOrgId";

					$('.district')
							.change(
									function() {
										if ($('.district').val() > 0) {
											var requestData = {
												"districtId" : $('.district')
														.val()
											}
											var response = __doAjaxRequest(
													'GrievanceDepartmentRegistration.html?grievanceOrganisations',
													'post', requestData, false,
													'html');

											if (response == "") {
												$('#id_org_div').html('');
												$('#id_org_div').hide();
											} else {
												$('#id_org_div').html(response);
												$('#id_org_div').show();
											}

										} else if ($('.district').val() == 0) {
											$("#orgId option[value!='0']")
													.remove();
											$("#department option[value!='0']")
													.remove();
											$(
													"#complaintType option[value!='0']")
													.remove();
											$("#location option[value!='0']")
													.remove();
										}
									});

					$('#orgId')
							.change(
									function() {
										if ($('#orgId').val() > 0) {
											var requestData = {
												"orgId" : $('#orgId').val()
											}
											var response = __doAjaxRequest(
													'GrievanceDepartmentRegistration.html?grievanceDepartments',
													'post', requestData, false,
													'html');

											if (response == "") {
												$('#id_department_div')
														.html('');
												$('#id_department_div').hide();
											} else {
												$('#id_department_div').html(
														response);
												$('#id_department_div').show();
											}

										} else if ($('#orgId').val() == 0) {
											$("#department option[value!='0']")
													.remove();
											$(
													"#complaintType option[value!='0']")
													.remove();
											$("#location option[value!='0']")
													.remove();
										}
									});

					$('#departmentComplaint')
							.change(
									function() {
										$('#propertyLblId').hide();
										$('#refNoLblId').hide();
										$('#waterLblId').hide();
										$('.refNoClass').hide();
										let deptCode  =  $("#departmentComplaint :selected").attr('code');
										if(deptCode =='AS'){
											$('#propertyLblId').show();
											$('.refNoClass').show();
										}else if(deptCode =='WT'){
											$('#waterLblId').show();
											$('.refNoClass').show();
										}else{
											$('#refNoLblId').show();
										}
										
										$('#extReferNumber').val("");
										$('#flatNoSelectId').hide();
										
										$('#residentId').val("");
										$('#residentDivId').hide();
										hitForWardZoneAndLocation();
										/*D#129264*/
										if($('#kdmcEnv').val() == "Y"){
											$("#FirstName1,#MiddleName,#LastName1,#address,#EmailID,#Pincode").val("");
										}
										
									});
					
					
					
					$('#extReferNumber').blur(function() {
						let deptCode  =  $("#departmentComplaint :selected").attr('code');
						let amtDuesCheck = $('#amtDuesCheck').val();
						if($('#kdmcEnv').val()== "Y" && $('#departmentComplaint').val() > 0 && $('#extReferNumber').val() != ""  && amtDuesCheck == "Y") {
							//first  check deptCode is property (some extra API for get property details) or water  
							if(deptCode =='AS'){
								//call for Property flats (billingMethod :I/W)
								let flatNoList = fetchPropertyFlatNo();
								//if flat list is present than go for dues check by selecting flat no 
								//else directly hit without flat no because  flat list zero it mean it is whole property case
								if(flatNoList.length>0 && flatNoList[0]!= null){
										
								}else{
									//hit API to check dues or not
									checkForDues();
									findOpWardZoneByReferenceNo();
								}
								
							}else if(deptCode == 'WT'){
								checkForDues();
								findOpWardZoneByReferenceNo();	
							}
						}else if($('#kdmcEnv').val()== "Y" && $('#departmentComplaint').val() > 0 && $('#extReferNumber').val() != ""){
							//U#113577
							//it will execute when dues check, not need but here also if property details need than 
							//first fetch property flat no(billingMethod :I/W) concept
							//if not doing this than every time it will give invalid property no
							
								if(deptCode =='AS'){
									//call for Property flats
									let flatNoList = fetchPropertyFlatNo();
									if(flatNoList.length>0){
										
									}else{
										findOpWardZoneByReferenceNo();	
									}
									
								}else if(deptCode == 'WT'){
									findOpWardZoneByReferenceNo();
								}
							}
						
						
						
						
					});
					
					//it will only execute in case of property department selected
					$('#flatNo').change(function() {
						//D#131285 PT.1  hit for checkDues in case of property
						let amtDuesCheck = $('#amtDuesCheck').val();
						if(amtDuesCheck == "Y"){
							checkForDues($("#flatNo").find("option:selected").val());	
						}
						findOpWardZoneByReferenceNo();
					});
					

					$("#Pincode2")
							.change(
									function() {

										var pinCode = document
												.getElementById("Pincode2").value;
										var _csrf = "${_csrf.token}";
										$
												.ajax({
													url : getAllLocationsByPinCode,
													type : "POST",
													data : {
														pinCode : pinCode,
														_csrf : _csrf
													},
													success : function(response) {
														if (!$
																.isEmptyObject(response)) {
															var options = $(
																	"select#location option")
																	.map(
																			function() {
																				return $(
																						this)
																						.val();
																			})
																	.get();
															$
																	.each(
																			response,
																			function(
																					i,
																					item) {
																				if (options
																						.indexOf(item.locId
																								.toString()) != -1)
																					$(
																							'#location')
																							.val(
																									item.locId)
																							.trigger(
																									'chosen:updated');
																			});
														}
													},
													error : function(xhr,
															errorType,
															exception) {
														var errorMessage = exception
																|| xhr.statusText;
														alert("Error occured :"
																+ errorMessage);
													}
												});
									});

					$("#MobileNumber")
							.change(
									function() {
										var mobileNumber = $("#MobileNumber").val();
										getApplicantDataByMob(mobileNumber);
									});
					
					
					
					$('#complaintType').change(function() {
						//function call
						$('#residentId').val("");
						hitOnChangeSubtypeData();
					});

				});

function resetData(resetBtn) {
	$(".compalint-error-div").html("");
	$('#ComplaintDescription').val();
	$('#departmentComplaint').val('0').trigger('chosen:updated');
	$('#complaintType').val('0').trigger('chosen:updated');
	$('#complaint-zone-ward').hide();
	$('#location').val('0').trigger('chosen:updated');
	$('#residentDivId').hide();
	$("#careDocLbl").removeClass( "required-control");
	$('#referenceCategory option:selected').attr("selected",null);
	//D#128713 start
	$("#refNoLblId").removeClass( "required-control").show();
	$("#propertyLblId").removeClass( "required-control").hide();
	$("#waterLblId").removeClass( "required-control").hide();
	//D#128713 end
	$('#extReferNumber,#MobileNumber').val("");
	$('#flatNoSelectId').hide();
	$("#titleId,#gender").val(0);
	$("#FirstName1,#MiddleName,#LastName1,#address,#EmailID,#Pincode").val("");
	//var pinCode = document.getElementById("Pincode2").value;
	var pinCode = $("#Pincode2").val();
	var _csrf = "${_csrf.token}";
	var getAllLocationsByOrgId = "/MainetService/GrievanceDepartmentRegistration.html?getAllLocationsByOrgId";
	$.ajax({
		url : getAllLocationsByOrgId,
		type : "POST",
		data : {
			pinCode : pinCode,
			_csrf : _csrf
		},
		success : function(response) {
			if (!$.isEmptyObject(response)) {
				$('#locationByPincode').empty();
				$('#locationByPincode').append(
						'<option value="">Select</option>');
				$.each(response, function(i, item) {
					$('#locationByPincode').append(
							'<option  value="' + item.locId + '">'
									+ item.locName + '</option>');
				});
			}

		},
		error : function(xhr, errorType, exception) {
			var errorMessage = exception || xhr.statusText;
			alert("Error occured :" + errorMessage);
		}
	});
	
	cleareFile(resetBtn);
	
	if (resetBtn && resetBtn.form) {
		
		$('[id*=file_list]').html('');
		//D#129136
		$('.compalint-error-div').remove();
		$('#complaintDescriptionCount').html(3000);  
		resetBtn.form.reset();
		
	};
	//Defect #155941
	$('select').each(function(){
		var errorField = $(this).next().hasClass('error');
		if (errorField == true) {
			$(this).next().remove();
		}
	});
	
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

function displayMessageOnSubmit() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Close';

	message += '<p>Request submitted successfully.</p>';
	message += '<p style=\'text-align:center;margin: 5px;\'>'
			+ '<br/><input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'css_btn \'    '
			+ ' onclick="refreshWorkflowPage()"/>' + '</p>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
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

function requestType() {
	$('#complaintType').html('');
	$('#complaintType').append('<option value="">Select</option>');

	var divName = formDivName;
	var theForm = "#care";
	var requestData = __serializeForm(theForm);
	var url = "GrievanceDepartmentRegistration.html?requestTypeComplaint";
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
}

function displayMessageForComplaintRegister() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Yes/No';

	message += '<h4 class=\"text-center text-blue-2 padding-10\">This Mobile No is not registered.Please Register before File Complaint ?</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="refreshPage()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);

}

function refreshPage() {
	$.fancybox.close();
	window.location.href = getLocalMessage("rti.adminHome");
}


function showAlertBox(message){
	var	errMsgDiv = '.msg-dialog-box';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	 $('#toDate').val("");
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

function closeAlertForm(){
	disposeModalBox();
	$.fancybox.close();
}

//U#113577
function hitForWardZoneAndLocation(){
	if ($('#departmentComplaint').val() > 0) {
		//department change than reset dues check
		$('#amtDuesCheck').val("N");
		var requestData = {
			"deptId" : $(
					'#departmentComplaint')
					.val(),
			"orgId" : null
		}
		var response = __doAjaxRequest(
				'GrievanceDepartmentRegistration.html?grievanceComplaintTypes',
				'post', requestData, false,
				'html');

		if (response == "") {
			$('#id_complaintType_div')
					.html('');
			$('#id_complaintType_div')
					.hide();
		} else {
			$('#id_complaintType_div')
					.html(response);
			$('#id_complaintType_div')
					.show();
		}
		
		//U#113577
		if($('#kdmcEnv').val()== "Y"){
			let requestData = {
					"orgId":$('#currentOrgId').val(),
					"deptId":$('#departmentComplaint').val()
					}
	    	let response = __doAjaxRequest('GrievanceDepartmentRegistration.html?findWardZone','post',requestData,false,'html');
	        if(response=="") {
	        	//ask to samadhan sir what to do when operation prefix not define for department
	    		var warnMsg = getLocalMessage('workflow.type.noworkflow') ;
	    		var message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
	    		$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	    		$(childDivName).html(message);
	    		$(childDivName).show();
	    		$('#yes').focus();
	    		showModalBox(childDivName);
	    		return false;
				} else{
				    $('#complaint-zone-ward').html(response);
	  	         $('#complaint-zone-ward').show();
				}	
		}
		
			
		var response = __doAjaxRequest(
				'GrievanceDepartmentRegistration.html?grievanceLocations',
				'post', requestData, false,
				'html');
		if (response == "") {
			$('#id_location_div').html('');
			$('#id_location_div').hide();
		} else {
			$('#id_location_div').html(
					response);
			$('#id_location_div').show();
		}

	} else if ($('#departmentComplaint')
			.val() == 0) {
		$(
				"#complaintType option[value!='0']")
				.remove();
		$("#location option[value!='0']")
				.remove();
	}
}

function hitOnChangeSubtypeData(){
	if ($('#departmentComplaint').val() > 0) {
		var requestData = {
			"complaintTypeId" : $("#complaintType").find("option:selected").val()
		}
		let response = __doAjaxRequest(
				'GrievanceDepartmentRegistration.html?checkComplaintTypeData',
				'POST', requestData, false, 'json');
		let residentId=response.residentId;
		let amtDues = response.amtDues;
		let documentReq = response.documentReq;
		
		if ( residentId == undefined || residentId == null || residentId == 0 || residentId =="N") {
			$('#residentDivId').hide();
		} else if(residentId != 0 && residentId != "N"){
			$('#residentDivId').show();
		}
		
		//amtDues set in hidden field
		$('#amtDuesCheck').val(amtDues);
		
		//here document check mandatory or not
		
		if (documentReq == undefined || documentReq == null || documentReq == 0 || documentReq== "N") {
			$("#careDocLbl").removeClass( "required-control");
		}else if(documentReq != 0  &&  documentReq != "N"){
			$("#careDocLbl").addClass("required-control");
		}
		
		//D#128713 start
		if (amtDues == undefined || amtDues == null || amtDues == 0 || amtDues== "N") {
			$("#refNoLblId").removeClass( "required-control");
			$("#propertyLblId").removeClass( "required-control");
			$("#waterLblId").removeClass( "required-control");
		}else if(amtDues != 0  &&  amtDues != "N"){
			$("#refNoLblId").addClass("required-control");
			$("#propertyLblId").addClass( "required-control");
			$("#waterLblId").addClass( "required-control");
			$(".pendingDues").show();
			
		}
		//D#128713 end
	}
	
}

function fetchPropertyFlatNo(){
	$('#flatNo').html('');
	$('#flatNo').append($("<option></option>").attr("value","").text(getLocalMessage('selectdropdown'))).trigger('chosen:updated');
	let reqData = {
			"refNo":$('#extReferNumber').val()
		}
	 flatNoList=  __doAjaxRequest('GrievanceDepartmentRegistration.html?fetchPropertyFlatNo', 'POST', reqData, false,'json');
	if(flatNoList.length>0 && flatNoList[0]!= null){
		$('#flatNoSelectId').show();
		$.each(flatNoList, function(index, value) {
			 $('#flatNo').append($("<option></option>").attr("value",value).text(value));
		});
		 $('#flatNo').trigger('chosen:updated');
	}else{
		$('#flatNoSelectId').hide();
	}
	return flatNoList;
	
}

function checkForDues(flatNo){
	var requestData = {
			"refNo" : $('#extReferNumber').val(),
			"deptId":$('#departmentComplaint').val(),
			"flatNo":flatNo
		}
		let response = __doAjaxRequest('GrievanceDepartmentRegistration.html?checkDues','POST', requestData, false, 'json');
		
		//set applicantDetails and outstanding AMT
		
		//1st hit for getApplicantDetails
		if(response.mobileNo!= "" && response.mobileNo!= null){
			//getApplicantDataByMob(response.mobileNo);	
		}
		
		if(($("#FirstName1").val() == undefined || $("#FirstName1").val() == "")  && response.fName != ""){
			$("#titleId").val(response.titleId);
			$("#FirstName1").val(response.fName);
			$("#MiddleName").val(response.mName);
			$("#LastName1").val(response.lName);
			$("#gender").val(response.gender);
			$("#address").val(response.areaName);
			$("#EmailID").val(response.email);
			$("#Pincode").val(response.pincodeNo);
			
		}
		$(".pendingDues").show();
		$("#payAmount").val(response.payAmount);
		if (response.status == "" || response == "") {
			//enable the BT
			$('.compSubmit').prop('disabled', false);
			
			
			
			
			
		} else {
			//alert MSG for 1st clear the dues  and disable the BT
			$('.compSubmit').prop('disabled', true);
			
			let message="";
			message	+='<h4 class=\"text-center text-blue-2 padding-10\">'+response+'</h4>'
				+'<div class=\'text-center padding-bottom-10\'>'+	
				'<input type=\'button\' value=\'Ok\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
				' onclick="closeAlertForm()"/>'+
				'</div>';
				//142620
				showAlertBox(message);
				return false;
		}	
}

function findOpWardZoneByReferenceNo(){
	let requestData = {
			"refNo" : $('#extReferNumber').val(),
			"deptId":$('#departmentComplaint').val(),
			"flatNo":$("#flatNo").find("option:selected").val()
			}
	let response = __doAjaxRequest('GrievanceDepartmentRegistration.html?findOpWardZoneByReferenceNo','post',requestData,false,'html');
    if(response=="") {
    	//ask to SAMADHAN sir what to do when ward zone not getting by reference no
		/*var warnMsg = getLocalMessage('ward zone issue') ;
		var message	='<p class="text-blue-2 text-center padding-15">'+ warnMsg+'</p>';
		$(childDivName).addClass('ok-msg').removeClass('warn-msg');
		$(childDivName).html(message);
		$(childDivName).show();
		$('#yes').focus();
		showModalBox(childDivName);*/
		return false;
		} else{
		    $('#complaint-zone-ward').html(response);
		    let mobileNoRefId=$('#mobileNoRefId').val();
		    if(mobileNoRefId!= "" && mobileNoRefId!= null && mobileNoRefId != 0){
		    	$('#MobileNumber').val(mobileNoRefId);
		    	getApplicantDataByMob(mobileNoRefId);
		    }
		    //D#129022 start  only for KDMC where Complaint integrate with Property and Water 
	         $('#complaint-zone-ward').show();
	         //disable ward zone if value already bind  till 5 level
	         let ward1=$('#ward1 :selected').val();
	         if(ward1!= "0" && ward1 != undefined){
	        	 $("#ward1").prop("disabled", true);
	         }else{
	        	 $("#ward1").prop("disabled", false);
	         }
	         
	         let ward2=$('#ward2 :selected').val();
	         if(ward2!= "0" && ward2 != undefined){
	        	 $("#ward2").prop("disabled", true);
	         }else{
	        	 $("#ward2").prop("disabled", false);
	         } 
	         
	         let ward3=$('#ward3 :selected').val();
	         if(ward3!= "0" && ward3 != undefined){
	        	 $("#ward3").prop("disabled", true);
	         }else{
	        	 $("#ward3").prop("disabled", false);
	         }
	        
	         let ward4=$('#ward4 :selected').val();
	         if(ward4!= "0" && ward4 != undefined){
	        	 $("#ward4").prop("disabled", true);
	         }else{
	        	 $("#ward4").prop("disabled", false);
	         }
	         
	         let ward5=$('#ward5 :selected').val();
	         if(ward5!= "0" && ward5 != undefined){
	        	 $("#ward5").prop("disabled", true);
	         }else{
	        	 $("#ward5").prop("disabled", false);
	         }
	         //D#129022 end
	         
		}	
}

function getApplicantDataByMob(mobileNumber){
	var successCallback = function(response) {
		$("#titleId").val(response.titleId);
		$("#FirstName1")
				.val(response.fName);
		$("#MiddleName")
				.val(response.mName);
		$("#LastName1").val(response.lName);
		$("#gender").val(response.gender);
		$("#address")
				.val(response.areaName);
		$("#EmailID").val(response.email);
		$("#Pincode").val(
				response.pincodeNo);
	}
	var errorCallback = function(response) {
		$("#titleId,#gender").val(0);
		$("#FirstName1,#MiddleName,#LastName1,#address,#EmailID,#Pincode").val("");
	}

	if (mobileNumber.length == 10) {
		var requestData = {
			mobileNumber : mobileNumber
		};
		__doAjaxRequestWithCallback(
				'GrievanceDepartmentRegistration.html?getApplicationDetails',
				'post', requestData, false,
				'JSON', successCallback,
				errorCallback);
	} else {
		errorCallback();
	}
}