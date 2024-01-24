$(document).ready(
		function() {
			
			
			wifeDocumentTask();
			//D#122612 Translator	 
			   var langFlag = getLocalMessage('admin.lang.translator.flag');
				if(langFlag ==='Y'){
					$("#wifeTabForm input[id=firstNameEng]").bind('click keyup', function(event) {
						var no_spl_char;
						no_spl_char = $("#wifeTabForm input[id=firstNameEng]").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'wifeTabForm input[id=firstNameReg]',event,'');
						}else{
							$("#wifeTabForm input[id=firstNameReg]").val('');
						}
					});
					
					$("#wifeTabForm input[id=middleNameEng]").bind('click keyup', function(event) {
						var no_spl_char;
						no_spl_char = $("#wifeTabForm input[id=middleNameEng]").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'wifeTabForm input[id=middleNameReg]',event,'');
						}else{
							$("#wifeTabForm input[id=middleNameReg]").val('');
						}
					});
					
					$("#wifeTabForm input[id=lastNameEng]").bind('click keyup', function(event) {
						var no_spl_char;
						no_spl_char = $("#wifeTabForm input[id=lastNameEng]").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'wifeTabForm input[id=lastNameReg]',event,'');
						}else{
							$("#lastNameReg").val('');
						}
					});
					
					$("#wifeTabForm textarea[id=fullAddrEng]").bind('click keyup', function(event) {
						var no_spl_char;
						no_spl_char = $("#wifeTabForm textarea[id=fullAddrEng]").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'wifeTabForm textarea[id=fullAddrReg]',event,'');
						}else{
							$("#wifeTabForm textarea[id=fullAddrReg]").val('');
						}
					});
					
				}
				
				
				
				//D#127376
				//doing below code only when form fill by pressing tab one by one than it create PBLM (mask not work) so adding below like code it work
				$('#wifeTabForm input[id=lastNameEng]').bind('click keyup', function(event) {
					$.mask.definitions['~'] = '[+-]';
					$('#wifeTabForm input[id=uidNo]').mask('9999-9999-9999');

				});
				
				$('#wifeTabForm input[id=uidNo]').click(function() {
					$.mask.definitions['~'] = '[+-]';
					$('#wifeTabForm input[id=uidNo]').mask('9999-9999-9999');

				});
				

				//D#139338
				$("#wifeNriApplicable").change(function(){
					if($("#wifeNriApplicable").prop("checked") == true){
						$('#wifeNriApplicable').val("Y");
						//remove mandatory from UID
						$(".uidLabelW").removeClass( "required-control");
						//$("#wifeTabForm input[id=uidNo]").prop("readonly",true);
					}else{
						$('#wifeNriApplicable').val("N");
						//$("#wifeTabForm input[id=uidNo]").addClass("required-control");
						$(".uidLabelW").addClass("required-control");
					}

				});
				
				let husbNriChecked=$('#husbNriChecked').val();
				if(husbNriChecked == "Y"){
					$("#wifeNriApplicable").prop("disabled",true);
				}else{
					$("#wifeNriApplicable").prop("disabled",false);
				}

			
		});



/*Start of Wife Tab CODE*/

function resetWife() {
	
	var divName = '#wife';
	var requestData = {
		"resetOption" : "reset"
	}
	var response = __doAjaxRequest('MarriageRegistration.html?showWifePage',
			'POST', requestData, false, '', 'html');
	$(divName).html(response);
}

function saveWife(element) {
	var errorList = [];
	errorList = validateWifeDetails(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();
		
		var divName = '#wife';
		var targetDivName = '#witnessDet';
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
				'MarriageRegistration.html?saveWifePage', 'POST', requestData,
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
				$(''+parentTab+' a[href="#witnessDet"]').tab('show');
				
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

function validateWifeDetails(errorList) {
	let firstNameE = $("#wifeTabForm input[id=firstNameEng]").val();
	let firstNameR = $("#wifeTabForm input[id=firstNameReg]").val();
	let lastNameE = $("#wifeTabForm input[id=lastNameEng]").val();
	let lastNameR = $("#wifeTabForm input[id=lastNameReg]").val();
	let uid = $("#wifeTabForm input[id=uidNo]").val();
	let dob = $("#wifeTabForm input[id=wdob]").val();
	let year = $("#wifeTabForm input[id=year]").val();
	let month = $("#wifeTabForm input[id=month]").val();
	let religionBirth = $("#wifeTabForm select[id=religionBirth]").find(":selected").val();
	let occupation = $("#wifeTabForm select[id=occupation]").find(":selected").val();
	let statusMarTime = $("#wifeTabForm select[id=statusMarTime]").find(":selected").val();
	let wifeAddrE = $("#wifeTabForm textarea[id=fullAddrEng]").val();
	let wifeAddrR = $("#wifeTabForm textarea[id=fullAddrReg]").val();
	let hUid = $("#husbandFormId").find("#uidNo").val();
	
	if(firstNameE == "" || firstNameE == undefined){
		errorList.push(getLocalMessage('mrm.vldnn.wifeFirstNameE'));
	}
	if (firstNameR == '' || firstNameR == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.wifeFirstNameR'));
	}
	if(lastNameE == "" || lastNameE == undefined){
		errorList.push(getLocalMessage('mrm.vldnn.wifeLastNameE'));
	}
	if (lastNameR == "" || lastNameR == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.wifeLastNameR'));
	}
	
	//D#139338
	if($('#wifeNriApplicable').val()=="N"){
		if(uid == "" || uid == undefined) {
			errorList.push(getLocalMessage('mrm.vldnn.wifeUID'));
		}
		else {
			if(validateAadhaar(uid) == false){
    			errorList.push(getLocalMessage('mrm.invalid.adhar.no'));
    		}
		}
		
	}
	if(hUid == uid) {
		errorList.push(getLocalMessage('mrm.duplicate.UI'));
	}
	if (dob == "" || dob =="0" || dob == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.wifeDOB'));
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
				if(year>=18){
					//valid age
				}else{
					errorList.push(getLocalMessage('mrm.wife.age.invalid'));
				}	
			}
			
		}else{
			//invalid date
			errorList.push(getLocalMessage('mrm.wife.age.invalid'));
		}
	}
	if(year == "" || year == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.wifeYear'));
	}
	if(month == "" || month == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.wifeMonth'));
	}
	if (religionBirth == "" || religionBirth =="0" || religionBirth == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.wifeReligionBirth'));
	}
	if (occupation == "" || occupation =="0" || occupation == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.wifeOccupation'));
	}
	if (statusMarTime == "" || statusMarTime =="0" || statusMarTime == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.wifeStatusMar'));
	}
	if (wifeAddrE == "" || wifeAddrE == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.wifeFullAddrE'));
	}
	if (wifeAddrR == "" || wifeAddrR == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.wifeFullAddrR'));
	}

	return errorList;
}



//document related code

function wifeDocumentTask() {
	var url = "MarriageRegistration.html?getCustomUploadedImage";
	var data = {};
	var returnData = __doAjaxRequest(url, 'post', data, false, 'json');
	$('#showPhotoW, #showThumbW, #removePhotoW, #removeThumbW').empty();
	var photoIdW = $("#photoIdW").val();
	var thumbIdW = $("#thumbIdW").val();
	$("#file_list_" + (photoIdW)).hide();
	$("#file_list_" + (thumbIdW)).hide();
	$
			.each(
					returnData,
					function(key, value) {
						if (key == 0) {
							$('#showPhotoW').append(
									'<img src="' + value
											+ '"  style="width:150px;height:150px" class="img-thumbnail" >');
							$('#removePhotoW')
									.append(
											' <a class="btn btn-danger btn-md"  title="Remove Image" onclick="deleteSingleUpload('
													+ photoIdW
													+ ',0)"><i class="fa fa-trash"></i></a>');
							$("#phoW").attr('class', 'row');
							//$("#addPhotoW").attr('class', 'col-xs-6');
						} else if (key == 1) {
							$('#showThumbW').append(
									'<img src="' + value
											+ '" style="width:150px;height:150px"  class="img-thumbnail" >');
							$('#removeThumbW')
									.append(
											' <a class="btn btn-danger btn-md"  title="Remove Image" onclick="deleteSingleUpload('
													+ thumbIdW
													+ ',1)"><i class="fa fa-trash"></i></a>');
							/*$("#thumW").attr('class', 'row');
							$("#addThumbW").attr('class', 'col-xs-6');*/
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
	$('#showPhotoW, #showThumbW, #removePhotoW, #removeThumbW  ,#viewPhotoW ,#viewThumbW').empty();
	if (no == 0) {
		$("#phoW").attr('class', 'text-center');
		$("#addPhotoW").attr('class', '');
	} else {
		$("#thum").attr('class', 'text-center');
		$("#addThumb").attr('class', '');
	}
	var photoIdW = $("#photoIdW").val();
	var thumbIdW = $("#thumbIdW").val();
	$("#file_list_" + (photoIdW)).hide();
	$("#file_list_" + (thumbIdW)).hide();

	$.each(returnData, function(key, value) {
						if (key == 0) {
							$('#showPhotoW').append(
									'<img src="' + value
											+ '" style="width:150px;height:150px" class="img-thumbnail" >');
							$('#removePhotoW')
									.append(
											' <a class="btn btn-danger btn-md"  title="Remove Image" onclick="deleteSingleUpload('
													+ photoIdW
													+ ',0)"><i class="fa fa-trash"></i></a>');
						} else if (key == 1) {
							$('#showThumbW').append(
									'<img src="' + value
											+ '" style="width:150px;height:150px"  class="img-thumbnail" >');
							$('#removeThumbW')
									.append(
											' <a class="btn btn-danger btn-md"  title="Remove Image" onclick="deleteSingleUpload('
													+ thumbIdW
													+ ',1)"><i class="fa fa-trash"></i></a>');
						}
					});
}

/*End of Wife Tab CODE*/
