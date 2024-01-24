$(document).ready(
		function() {

			//call for Image display
			otherTask();
			
				//D#122612 Translator	 
			   var langFlag = getLocalMessage('admin.lang.translator.flag');
				if(langFlag ==='Y'){
					$("#firstNameEng").bind('click keyup', function(event) {
						var no_spl_char;
						no_spl_char = $("#firstNameEng").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'firstNameReg',event,'');
						}else{
							$("#firstNameReg").val('');
						}
					});
					
					$("#middleNameEng").bind('click keyup', function(event) {
						var no_spl_char;
						no_spl_char = $("#middleNameEng").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'middleNameReg',event,'');
						}else{
							$("#middleNameReg").val('');
						}
					});
					
					$("#lastNameEng").bind('click keyup', function(event) {
						var no_spl_char;
						no_spl_char = $("#lastNameEng").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'lastNameReg',event,'');
						}else{
							$("#lastNameReg").val('');
						}
					});
					
					$("#fullAddrEng").bind('click keyup', function(event) {
						var no_spl_char;
						no_spl_char = $("#fullAddrEng").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'fullAddrReg',event,'');
						}else{
							$("#fullAddrReg").val('');
						}
					});
					
				}
				
				//D#127376
				//doing below code only when form fill by pressing tab one by one than it create PBLM (mask not work) so adding below like code it work
				$('#husbandTabForm input[id=lastNameEng]').bind('click keyup', function(event) {
					$.mask.definitions['~'] = '[+-]';
					$('#husbandTabForm input[id=uidNo]').mask('9999-9999-9999');

				});
				
				
				$('#husbandTabForm input[id=uidNo]').click(function() {
					$.mask.definitions['~'] = '[+-]';
					$('#husbandTabForm input[id=uidNo]').mask('9999-9999-9999');

				});
				
				//D#139338
				$("#husNriApplicable").change(function(){
					if($("#husNriApplicable").prop("checked") == true){
						$('#husNriApplicable').val("Y");
						$(".uidLabel").removeClass( "required-control");
						//$("#husbandTabForm input[id=uidNo]").prop("readonly",true);
					}else{
						$('#husNriApplicable').val("N");
						$(".uidLabel").addClass("required-control");
					}

				});
				
				let wifeNriChecked=$('#wifeNriChecked').val();
				if(wifeNriChecked == "Y"){
					$("#husNriApplicable").prop("disabled",true);
				}else{
					$("#husNriApplicable").prop("disabled",false);
				}

		});



/*Start of Husband Tab CODE*/

function resetHusband() {
	
	var divName = '#husband';
	var requestData = {
		"resetOption" : "reset"
	}
	var response = __doAjaxRequest('MarriageRegistration.html?showHusbandPage',
			'POST', requestData, false, '', 'html');
	$(divName).html(response);
}

function saveHusband(element) {
	var errorList = [];
	errorList = validateHusbandDetails(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();
		
		var divName = '#husband';
		var targetDivName = '#wife';
		var mode = $("#modeType").val();
		var elementData = null;

		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'MarriageRegistration.html?saveHusbandPage', 'POST', requestData,
				false, '', elementData);
		window.scrollTo(0, 0);
		if (mode == 'E') {
			editModeProcess(response);
			prepareDateTag();
		} else {
			$(divName).removeClass('ajaxloader');
			var tempDiv = $('<div id="tempDiv">' + response + '</div>');
			var errorsPresent = tempDiv.find('#validationerror_errorslist');
			
			if (!errorsPresent || errorsPresent == undefined
					|| errorsPresent.length == 0) {
				$(targetDivName).html(response);
				//#D34059
				let parentTab =  '#mrmParentTab';
				if(mode == 'D'){//Draft
					parentTab = '#mrmViewParentTab';
				}
				var disabledTab = $(parentTab).find('.disabled');
				console.log("removing diabled" + disabledTab);
				if (disabledTab) {
					$(disabledTab).removeClass('disabled');
					//manageDependentTabs();
				}
				//$('#assetParentTab a[href="#astClass"]').data('loaded', true);
				$(''+parentTab+' a[href="#wife"]').tab('show');
				
				var errorPreviousTab = $(divName).find('#validationerrordiv');
				if (errorPreviousTab.length > 0) {
					var divError = $(divName).find('#validationerrordiv');
					$(divError).addClass('hide');
				}
			} else {
				$(divName).html(response);
				prepareDateTag();
			}
		}
	}
}


function validateHusbandDetails(errorList) {
	let firstNameE = $("#husbandTabForm input[id=firstNameEng]").val();
	let firstNameR = $("#husbandTabForm input[id=firstNameReg]").val();
	let lastNameE = $("#husbandTabForm input[id=lastNameEng]").val();
	let lastNameR = $("#husbandTabForm input[id=lastNameReg]").val();
	let uid = $("#husbandTabForm input[id=uidNo]").val();
	let dob = $("#husbandTabForm input[id=dob]").val();
	let year = $("#husbandTabForm input[id=year]").val();
	let month = $("#husbandTabForm input[id=month]").val();
	let religionBirth = $("#husbandTabForm select[id=religionBirth]").find(":selected").val();
	let occupation = $("#husbandTabForm select[id=occupation]").find(":selected").val();
	let statusMarTime = $("#husbandTabForm select[id=statusMarTime]").find(":selected").val();
	let husbandAddrE = $("#husbandTabForm textarea[id=fullAddrEng]").val();
	let husbandAddrR = $("#husbandTabForm textarea[id=fullAddrReg]").val();
	
	if(firstNameE == "" || firstNameE == undefined){
		errorList.push(getLocalMessage('mrm.vldnn.husbandFirstNameE'));
	}
	if (firstNameR == '' || firstNameR == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.husbandFirstNameR'));
	}
	if(lastNameE == "" || lastNameE == undefined){
		errorList.push(getLocalMessage('mrm.vldnn.husbandLastNameE'));
	}
	if (lastNameR == "" || lastNameR == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.husbandLastNameR'));
	}
	
	//D#139338
	if($('#husNriApplicable').val()=="N"){
		if(uid == "" || uid == undefined) {
			errorList.push(getLocalMessage('mrm.vldnn.husbandUID'));
		}
		else {
			if(validateAadhaar(uid) == false){
    			errorList.push(getLocalMessage('mrm.invalid.adhar.no'));
    		}
		}
	}
	
	if (dob == "" || dob =="0" || dob == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.husbandDOB'));
	}else{
		//D#126789 validate husband DOB with marriage date
		if((moment(dob, 'DD/MM/YYYY',true).isValid())){
			//D#127368
			if($('#applicableENV').val() == 'true'){
				//D#128500 KDMC age validation
				let marDate= $('#marDate').val();
				let year=Number(moment(marDate,"DD/MM/YYYY").diff(moment(dob,"DD/MM/YYYY"),'year'));
				if(year<12){
					//validation MSG INVALID AGE
					errorList.push(getLocalMessage('mrm.valid.age'));
				}
			}else{
				let marDate= $('#marDate').val();
				let year=Number(moment(marDate,"DD/MM/YYYY").diff(moment(dob,"DD/MM/YYYY"),'year'));
				if(year>=21){
					//valid age
				}else{
					errorList.push(getLocalMessage('mrm.husband.age.invalid'));
				}
			}
			
		}else{
			//invalid date
			errorList.push(getLocalMessage('mrm.husband.age.invalid'));
		}
	}
	if(year == "" || year == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.husbandYear'));
	}
	if(month == "" || month == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.husbandMonth'));
	}
	if (religionBirth == "" || religionBirth =="0" || religionBirth == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.husbandReligionBirth'));
	}
	if (occupation == "" || occupation =="0" || occupation == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.husbandOccupation'));
	}
	if (statusMarTime == "" || statusMarTime =="0" || statusMarTime == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.husbandStatusMar'));
	}
	if (husbandAddrE == "" || husbandAddrE == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.husbandFullAddrE'));
	}
	if (husbandAddrR == "" || husbandAddrR == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.husbandFullAddrR'));
	}

	return errorList;
}

function otherTask() {
	var url = "MarriageRegistration.html?getCustomUploadedImage";
	var data = {};
	var returnData = __doAjaxRequest(url, 'post', data, false, 'json');
	$('#showPhoto, #showThumb, #removePhoto, #removeThumb').empty();
	var photoId = $("#photoId").val();
	var thumbId = $("#thumbId").val();
	$("#file_list_" + (photoId)).hide();
	$("#file_list_" + (thumbId)).hide();
	$
			.each(
					returnData,
					function(key, value) {
						if (key == 0) {
							$('#showPhoto').append(
									'<img src="' + value
											+ '"  style="width:150px;height:150px" class="img-thumbnail" >');
							$('#removePhoto')
									.append(
											' <a class="btn btn-danger btn-md"  title="Remove Image" onclick="deleteSingleUpload('
													+ photoId
													+ ',0)"><i class="fa fa-trash"></i></a>');
							
							
						} else if (key == 1) {
							$('#showThumb').append(
									'<img src="' + value
											+ '" style="width:150px;height:150px"  class="img-thumbnail" >');
							$('#removeThumb')
									.append(
											' <a class="btn btn-danger btn-md"  title="Remove Image" onclick="deleteSingleUpload('
													+ thumbId
													+ ',1)"><i class="fa fa-trash"></i></a>');
							
						}
					});
}


function deleteSingleUpload(deleteMapId, no) {
	var url = "MarriageRegistration.html?deleteSingleUpload";
	var requestData = {
		'deleteMapId' : deleteMapId,
		'deleteId' : no
	};
	var returnData = __doAjaxRequest(url, 'post', requestData, false, 'json');
	$('#showPhoto, #showThumb, #removePhoto, #removeThumb  ,#viewPhoto ,#viewThumb').empty();
	if (no == 0) {
		$("#pho").attr('class', 'text-center');
		$("#addPhoto").attr('class', '');
	} else {
		$("#thum").attr('class', 'text-center');
		$("#addThumb").attr('class', '');
	}
	var photoId = $("#photoId").val();
	var thumbId = $("#thumbId").val();
	$("#file_list_" + (photoId)).hide();
	$("#file_list_" + (thumbId)).hide();

	$.each(returnData, function(key, value) {
						if (key == 0) {
							$('#showPhoto').append(
									'<img src="' + value
											+ '" style="width:150px;height:150px" class="img-thumbnail" >');
							$('#removePhoto')
									.append(
											' <a class="btn btn-danger btn-md"  title="Remove Image" onclick="deleteSingleUpload('
													+ photoId
													+ ',0)"><i class="fa fa-trash"></i></a>');
						} else if (key == 1) {
							$('#showThumb').append(
									'<img src="' + value
											+ '" style="width:150px;height:150px"  class="img-thumbnail" >');
							$('#removeThumb')
									.append(
											' <a class="btn btn-danger btn-md"  title="Remove Image" onclick="deleteSingleUpload('
													+ thumbId
													+ ',1)"><i class="fa fa-trash"></i></a>');
						}
					});
}




/*End of Husband Tab CODE*/
