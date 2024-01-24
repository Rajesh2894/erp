$(document).ready(
		function() {
			var photoThumbDisp = $("#photoThumbDisp").val();
			if(photoThumbDisp == "Y"){
				var player = document.getElementById('player');
				var canvas = document.getElementById('canvas');
				var context = canvas.getContext('2d');
			}
			
			
			//capture photo from WEBCAM
			$('#canvas').css('display','none');
			$('#player').css('display','none');
			
			$('#webcam').css('display','none');
			$('#startCamBT').css('display','none');
			$('#manual').css('display','none');
			
			$('#fingerPrint').css('display','none');
			$('#manualThumb').css('display','none');
			
			
			//call for Image display
			checkWebCAM();
			otherTask();
			//check fingerprint device present or not
			GetInfo();
			
			//D#122612 Translator	 
			   var langFlag = getLocalMessage('admin.lang.translator.flag');
				if(langFlag ==='Y'){
					$('#firstNameEng').bind('click keyup', function(event) {
						var no_spl_char;
						no_spl_char = $("#firstNameEng").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'firstNameReg',event,'');
						}else{
							$("#firstNameReg").val('');
						}
					});
					
					$('#middleNameEng').bind('click keyup', function(event) {
						var no_spl_char;
						no_spl_char = $("#middleNameEng").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'middleNameReg',event,'');
						}else{
							$("#middleNameReg").val('');
						}
					});
					
					$('#lastNameEng').bind('click keyup', function(event) {
						var no_spl_char;
						no_spl_char = $("#lastNameEng").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'lastNameReg',event,'');
						}else{
							$("#lastNameReg").val('');
						}
					});
					
					$('#fullAddrEng').bind('click keyup', function(event) {
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
				
				
				
				$('#husbandTabForm input[id=uidNo]').click(function(){
					$.mask.definitions['~'] = '[+-]';
					$('#husbandTabForm input[id=uidNo]').mask('9999-9999-9999');

				});
				
				$('#capture').click(function(){
					// Draw the video frame to the canvas.
				    context.drawImage(player, 0,0, canvas.width, canvas.height);
				    var imgQualityBS64=canvas.toDataURL();
				    //var imgQualityBS64 = canvas.toDataURL('image/jpeg', 1.0);
				    // var mediumQuality = canvas.toDataURL('image/jpeg', 0.97);
				    //var imgQualityBS64 = canvas.toDataURL('image/png', 0.99);
				    //var imgQualityBS64 = canvas.toDataURL('image/png');
				    //split data:image/png;base64 constant string
				    imgQualityBS64=imgQualityBS64.substring(imgQualityBS64.indexOf(",") + 1);
				    
				    // Stop all video streams.
				    player.srcObject.getVideoTracks().forEach(track => track.stop());
				    $('#player').css('display','none');
				    //call java handler to make file in temporary location base64 to file
				    uploadPhotoThumbBase64(imgQualityBS64,"photo");
				    
				    $("#capture").prop("disabled",true);
				    

				});
				
				//D#139338
				$("#husNriApplicable").change(function(){
					if($("#husNriApplicable").prop("checked") == true){
						$('#husNriApplicable').val("Y");
						//remove mandatory from UID
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

function checkWebCAM() {
	
	$('#player').css('display','block');
	if ($('#showPhoto img').attr('src') != undefined){
		$('#player').css('display','none');
    }
	var photoThumbDisp = $("#photoThumbDisp").val();
    if (photoThumbDisp == "Y" && navigator.mediaDevices!=undefined && navigator.mediaDevices.getUserMedia!= undefined) {
        navigator.mediaDevices.getUserMedia({ video: true })
            .then(function (stream) {
            	//$('#webcam').css('display','block');
            	$('#startCamBT').css('display','block');
                player.srcObject = stream;
            }).catch(function (error) {
                console.log("Device not Found" +error);
                //alert(error);
                //manually upload code execute
                $('#manual').css('display','block');
                
            });
    }else{
    	//manually upload code execute
        $('#manual').css('display','block');
    }
}


function startWebCAM() {
	$('#player').css('display','block');
	if ($('#showPhoto img').attr('src') != undefined){
		$('#player').css('display','none');
    }
	var photoThumbDisp = $("#photoThumbDisp").val();
    if (photoThumbDisp == "Y" && navigator.mediaDevices!=undefined && navigator.mediaDevices.getUserMedia!= undefined) {
        navigator.mediaDevices.getUserMedia({ video: true })
            .then(function (stream) {
            	$('#webcam').css('display','block');
                player.srcObject = stream;
                $('#startCamBT').css('display','none');
            }).catch(function (error) {
                console.log("Device not Found" +error);
                //alert(error);
                //manually upload code execute
                $('#manual').css('display','block');
                
            });
    }else{
    	//manually upload code execute
        $('#manual').css('display','block');
    }
}

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
		$("#errorDiv").show();
		showErrAstInfo(errorList);

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
					manageDependentTabs();
					console.log("removed disabled");
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
		}else if(uid.length < 12) {
			errorList.push(getLocalMessage('mrm.vldnn.husbandUID.len'));
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
	var url = "MarriageRegistration.html?getUploadedImage";
	var data = {};
	var returnData = __doAjaxRequest(url, 'post', data, false, 'json');
	$('#showPhoto, #showThumb, .removePhoto, .removeThumb').empty();
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
							$('.removePhoto')
									.append(
											' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('
													+ photoId
													+ ',0)"><i class="fa fa-trash"></i></a>');
							
							
						} else if (key == 1) {
							$('#showThumb').append(
									'<img src="' + value
											+ '" style="width:150px;height:150px"  class="img-thumbnail" >');
							$('.removeThumb')
									.append(
											' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('
													+ thumbId
													+ ',1)"><i class="fa fa-trash"></i></a>');
							
						}
					});
	
	//check the camera when page load
	//checkWebCAM();
}


function deleteSingleUpload(deleteMapId, no) {
	
	var url = "MarriageRegistration.html?deleteSingleUpload";
	var requestData = {
		'deleteMapId' : deleteMapId,
		'deleteId' : no
	};
	var returnData = __doAjaxRequest(url, 'post', requestData, false, 'json');
	$('#showPhoto, #showThumb, .removePhoto, .removeThumb  ,#viewPhoto ,#viewThumb').empty();
	if (no == 0) {
		$("#pho").attr('class', 'text-center');
		$(".addPhoto").attr('class', '');
		$("#capture").prop("disabled",false);
	} else {
		$("#thum").attr('class', 'text-center');
		$(".addThumb").attr('class', '');
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
							$('.removePhoto')
									.append(
											' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('
													+ photoId
													+ ',0)"><i class="fa fa-trash"></i></a>');
						} else if (key == 1) {
							$('#showThumb').append(
									'<img src="' + value
											+ '" style="width:150px;height:150px"  class="img-thumbnail" >');
							$('.removeThumb')
									.append(
											' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('
													+ thumbId
													+ ',1)"><i class="fa fa-trash"></i></a>');
						}
					});
	
	$('#canvas').css('display','none');
    startWebCAM();
	
	
}



/*End of Husband Tab CODE*/


//D#129628 start
function saveHusbPhotoThumb(element) {
	var divName = '.content-page';
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);

	var ajaxResponse = doAjaxLoading('MarriageRegistration.html?saveHusbPhotoThumb', requestData, 'html',divName);
	
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}
//D#129628 end


function uploadPhotoThumbBase64(imgQualityBS64,uploadDesc) {
	var requestData = {
			"base64String" : imgQualityBS64,
			"uploadDesc":uploadDesc
		}
	
	var returnData = __doAjaxRequest('MarriageRegistration.html?uploadPhotoThumbBase64','POST', requestData, false,'json');
	
	let photoId = $("#photoId").val();
	let thumbId = $("#thumbId").val();
	$('#showPhoto, #showThumb, .removePhoto, .removeThumb').empty();
	
	$.each(returnData,function(key, value) {
		if (key == 0) {
			$('#showPhoto').append(
					'<img src="' + value
							+ '"  style="width:150px;height:150px" class="img-thumbnail" >');
			$('.removePhoto')
					.append(
							' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('
									+ photoId
									+ ',0)"><i class="fa fa-trash"></i></a>');
		} else if (key == 1) {
			$('#showThumb').append(
					'<img src="' + value
							+ '" style="width:150px;height:150px"  class="img-thumbnail" >');
			$('.removeThumb')
					.append(
							' <a class="btn btn-danger btn-block"  title="Remove Image" onclick="deleteSingleUpload('
									+ thumbId
									+ ',1)"><i class="fa fa-trash"></i></a>');
		}
	});
	
}

//function to initialize the device

function GetInfo() {
    
    var key ;
    
    var res;
    if (key!= undefined && key.length == 0) {
        res = GetMFS100Info();
    }
    else {
        res = GetMFS100KeyInfo(key);
    }
    if (res.httpStaus) {
        let erroCode=res.data.ErrorCode;

        let errDesc=res.data.ErrorDescription;

        if (res.data.ErrorCode == "0") {
            let serialNo=res.data.DeviceInfo.SerialNo;
            let certificate=res.data.DeviceInfo.Certificate;
            $('#fingerPrint').css('display','block');
        }else{
        	//show manual thumb
        	$('#manualThumb').css('display','block');
        	console.log(errDesc);
        }
    }
    else {
        console.log(res.err);
        $('#manualThumb').css('display','block');
    }
    return false;
}



//function to capture the finger prints. 
function captureFingerPrint() {
    try {
        var flag =0;
		var quality = 60; //(1 to 100) (recommanded minimum 55)
		var timeout = 10; // seconds (minimum=10(recommanded), maximum=60, unlimited=0 )
		
        //document.getElementById('imgFinger').src = "data:image/bmp;base64,";
		/*var imgQualityBS64="iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAAAAAXNSR0IArs4c6QAAIABJREFUeF6cvWe3ZFl1LbjCR1yb5qb35aAeICSQQMKrhBwIWsgUQnrd0hs9XneP0WP0/+tv/aE/d4syWAGNgKqC9HltuBM9pln77LiVRVZyUSkz40acOGebueaay+zOaHO06q6CP6tYRaO/lp9OZ+1ffuMqotOJTn6O71lFJ/CC/j/+3cVrq26sVhFNB6+c/sEr+Y24SDfw5mbZxGK5XP9AN6Lb7Qbe1cGf9X3hdnjpjv/UdfmvDp4Kz6Xr4567HdxjEw3uqIP75B/lZ9XpRaz4TWVc8IZO4J70jPoFvqHrf+ZruGYTq9UqOuUm/ZlVj+/lJTA2HTz7Klb8LtyFn4CX0rjotrq6nZXmqL1R/Z5v9+91kYbfzXntNNHvRQwG/Rj08Oz6pk4Xd96JHqdSA4jXu3idt4Ib7fLf+JYe5rWLcctvxHg00fWNdTq8qp7bc9SsGn6Gz8HxwH+chfJcfEEDEl08Mu6b34O/zj0vmEePwwrfgaFfcvy60YuIfiyXOV6r6GGtYN75PqxCfSPuF2ONe8XM8VkwVnxW/BbfmQu1iU5X84hxKHPOz3p+urgm3o97wDuwPnAdr50yJ/oePWc3Vl7yS1x21Q2Mk+7Q98N1o3HhuOGeV7h6EyuPocZTe6vbNNHwOXOctY7wGS1TrxqOB8YPz9Xl3GFf4j7w6R6+r/H8cg1zOrxG8ay51rEi+vxsZ4XR9QNxfWodc4Q6+K/dqPjejsEG348n7q2aWOJ+tPrKmset49WlgaOzXEWDcRhs9glYZcN2OrHkPzCxfh0g4jXcbkJNGj7Lm8LAYUmUz+hB14FgFQ1f6EYf09N0YukHwHTgV0sM3XIVyyUu3O5NjhXXRSd62lHlp96r+dgrLh7Ne47ZYtUrIILB4yLyUsz7zL2qkWsHEe9d8os6XCC6bovYmACCJBeHtkPimq7V1+LXnozVarl2b3oPHhBfstQcAASaDsECP8t1uFobAwwjJlkb3Yt/FdHvLGPYjxj0BzEEXvqZCUwADyxcLMWu5xuA4QHDXOK78Uy4M5oBGCp8gnvKG1rbg4CAn/6qFyt/DxdhGjSOWROdVV/gVoEVVhjuhRsRDwOw4NbHe3GNpTEZr2mMheJ9/onPcQv4vjh3BO6IVYPt2ESXAIN1hd/hP4yxwIYbFd/RxdgLlLoYAIIc5gvP1+PHorPgaAgkYNwEgJ1G4CJLgbHCfXpdrJbRpcECCDXR4PsAVvg4NjLHFUZGYLXE+/0alirejvnvcUwwRniP11/T1b7rCqS1pgFY2ANd7im9iO8BcAIkMRfdaLyWMXNd7HOuHVlwGhAbVwF3RNNLIyeA5DMWW60NByPIb+Se0JKecyQ1H74VzheeqWU+eS3MpwwmR5p7zmAMwBLYeKF7sZfNVTGm1oxrcXOTmEmI03hDcrqxMM27CgXQRHORVWgEFsbns7FtmmU0cw1Q+TFgdbnQWi7VgoJBttwvr1j+470QcNbBLjdT3o8G1BbOf+tigfNaApmyKAxguGbeqrBb1pSLu/ykJfZ18mtqisTrAPS1WQFgaSByPuor1pc4/Xo+16ATMeg1BKxBrwImbCVYa2zEbkSv0+F/hFpsfqAZ/l02Ui4sA02SHgOFsLuYZH6WsFIYnP6NDUmGawsK8EvGhQ0I6zmIBDzsGLNQzqsZdgKdGYtgFXPTMfAIGERJG4Itf2dgiI5AEEaNBgYbFc/BN9pQkNXgffid1i0Aa0VjKQ5lSma7KNChXyGqU+4pvwv31GBOAXCed373solVT9YGQJQ/+R00rrqk58dfDbADSIoDFf8ixxPPREZl7wePpyvJMHBkwW6ScBT7C+DSXQB8vKLNzsygzQLTiOltmu8ELBhdfX8y8ySOQD4RFz20vgPApPdi/Wus9MxikXwOAtZTfsQRgPJY2FhdeIieLJt/emZAZG20ttrofBa7L3LL9IF202EBYDGc+kmDAYq7aAp19hh/IMN62v2LqwjBn/qApz9EFuGFwcdNd09X4YA22Nj+oBczrHJ+gTZUTkIxT374fF5Z8vJD0PfnsKA5ydpsYBwYd1h8r8q1u84nS0d47ZdwBel2dqLXW8WwP4x+vxt9ghDYiu6WU4s57oLLyMIL11tmp42IzZ1sVfNMZp43ZkOATc/lSXdGc9/YdeKniqst95Cv8S8wcLTzdOME1ACBJV0mGQUDBnEIbpQ+y3vowoYLVJplj+sW12xiEd0ORkIMl+8niOoZUgQB46WriLkxC4nV0KvH80/miPvDd8vFpCzQGXhTL2QAeD8Cy1VhzbLIczxLF3djOrzqxEKWvQIduGteNjT8+gddQjIhu+d+HQAhwIAhMEtJyQauFHd8uwdNlrQnyUqx1uzR5KTol8XF55u9r3m/ZORyOznqYIGxioUWRcukMG9kkzIKOdeGvejjc1yDci/J4rz/GgLLkv9OL7AAVvr0dmb8KKKGOXQJSLx3WqwWClpXtbIQBbG1NHgzOUzWGHQdDXbymmXTxPIDAIvazDp1qaxpuiZm++mvEwjW2RX1l2oS8q96VtFifoZakDaSfINc7NLG9LS2cZXulcwgxwosQMvqtEqId7QaiSybGVpy7U43luli5Ws1Oq2LibojYhwWIthVJ4aDPrUpABYXm109aXirGHjxaUzAurSx+9zEgka5ONIAvS7loIChpetnRkNIBGtJVzUZBRezxgL/n8DC99rlw+1BhCLdtmTQGLytp1El8gaXqwKUBOAAWHAtAJZ0v3TK8DqNOoDWm5LsxBrnollGr9+zQUowocrHjcb/patj9glHh+apSfjHlrWrCuT2eiFjMChx43r/UBdcUbzg2FISAQuEm851rk3cAow2lAyadgs+i+uQk5vgCuDk3nG14b7NfvApShu5q02cqY35vXIcLA2l95X7ohFQNd1GAISZt2HCGs0tgDsAiHU4NhELjJ/1N+75phe9RhIK7pFsjI8FsJbCDFkSrrMwyZraYFP4rEVUsMmbK9lRoRWFTRQaVfzlHEh/1FiXukrRIDj51XfVGy8XIdjcacDCmqSYKuubU6ZxlM5ilbX8Tn603Dh4dZpHCd94IjIq3o+ZaSKmJ4d2lKKsDSZZovQKAbldA39egC7moJe0wNZ0v7Ubr1ZNglbRfnSv3JDcjHpmcrParbVw3w6jBeYipaxi0OvGcDCIvnUO7tdEHN8fOAI5JTaKX8u/k3GkEJysxs+qRahFzGe14cEChKtDnQwT581k2+QRTk2tBQ5qhEDWovmlYYQGpeukW95yZwAs5kLaIn66y24syYj0fkvSQckG/+7K3aBOlnoZQZswmhNoDavlshSr+XmtK4JGA5VI7iPWNr6LzwkyBa+E600MA5pRyg5p8OTspIBvLPJ8lGiLN3ay+cQssM9cGwAZskczo3T1CmgLEcxWm1j0pCsBwAA6ABg63WRNADwDEF0yBxvwux4MqFmnAxgkI3g2GEpvjwzAiG1Dmsfezftt9W3+nuCt8VQYhGhFDYtL3ManM9watDTJy8h8ofoXd6o1pppVeZHiQSvfuzIJ2rik5lgpWiTtT2oUrb5DNAXDmgPBq7daw+KmeorozlXkicpPceFQD8pNro0lvzyFb9PUirlIP+pSr0A0CtETbCBNoIV6X0NL25N8iv1Q4KyjDtV7U/AvT1gEyoy4tGMlGr2w+Nsr+oIuV7ueZiZ0uL1zO4sY9XvR7/diANcs3Uv+3e5+ZxUDeirSkxTNw+KScC1jLtBUNFBbmoK8NxYtpMcZYEhXHMwoV1GrDOiRW0peroOXsXlKhJVaGjBDAje1L+syEtP1nWCDiqbp0og8KTqlSChdHgJa6jYGJYrM6wYFjEDPCBDiqoUz3LqedE/z/mWcsF7XQC41ET9nuqF4n8CJDr/EZMttkqnsc5XosdctXVS8QfddiHeJzkn6WDgCS3eSQaVc57o2mJe/QffgsaY44Gi5XhdjU5TSEWWMC0V3uZF8jtWS8EzpYtWLOd3qVfQdTQbjwqhDMM+5QUSSw+fNrbHQ3LXRcswnONlSuha+lAywic5oc8CgYmEBT2MANcb476TSqcxVFDM3ESwtoiypyTDwA2rIQTr9geoLqMc10cyfrmGlNX+qblNdJllRrQm1v5aV4eI2g0rLldabkR/rKBpNCcWOofhSfp0bNRdcsg1NhahF/XwJJBkFyF8aqLiiDDzV5+jX8zLrwQgH42WT0hRVLA8KZb/fiX6/H4Mu0hQckUIEkItUG1AuiMQEbFhGqDg2lTBK9KjSEzDHjIrJoMFogEXAjZS7Ihaqf5nqkO5bAzFLE7DIgjfkkP0ShdT4YTxSfDfD5Z1IixNXxkgQWuSepovE4EKyGt8JI4hKeZDOZEAmIJRogiUPaXHSpZMHtfKFon7exAYAecdym8XsNJGMDhqsOJfGFK4rshtFRwHa2M9wjXocIz9PasUEHwUR0lgyqEC3MrWo1PDSWLcpCQRlsmExLA5HGvFcyo4IUz/it6wK01mAnZIBYdZabZpwSGOn7IGUkPAIcO8whgNgwmoVcwKXBkDGxtDFOekTA5R+gdeVOEHzM9zCkv5gAOHySL9bmUwcfKUjyLKBSlpyKDfAKbN4rLWsCRT9A13/oK8VbUaUMPNV+IVVlHDNxUoXQJzRLoBEXwAmfk39p3rEPoROL8xMTaCOuMb+vMHWwDr1p/xdgpLNFb/jFGF9Ctivv/TbvietQypicjY0JxI5pWf4++2uaWXJ6oNpQXQfDHq0fMAcLCiwoxI9c9pCL8PziZdgo7nZDDJYoIawCv/BWlcCQITWKeL2okE6QboAJZdJSy/dmBJZ5megdSjPDqkr1A75ObO3jMA6n045Q55zb0BufgeJcowkzCNqK3Bu8JxdpCi0OqxcNTEyGV1sJ6UFkCfm+ll3EcRYurlBLVfYHdcWsUaVkTIK0AIXpilgQvDQOdV015WbRXfVOVjY7RqrUxoogSBnxOPBta0b1sY3E7T+iltgnL4wLAGsANkCeGWDU36TSYa2lYMBFmrwFH5ZYtHexv4jQaYdhP+pyLSil1pHmAsxcwAZ3Gyp7rhHsTgNvRIcIjqTzdGKvm75MdKZMSToKBTqHBBGCzU48qWftklzMVSh7pJygQlxikDqBTlh9oWXiwX1hrJQnDjK5NE1fBXJTSve8hrnSqU/bMRPWT4FWdrMakYEH/kF9Z+y/wLpXA+nBXTdy/sB6cOAWAJXfo8XH0P91lRsIGo2+EHfpTGSy9fvrRghhJY9ZJKgVgsTcOkmKGqFtIayXwFVqUvhUnSp9HwZYUv2SIHbyjDmh+kSZfk7apYuBm9YQkeBfeteQKycD2pFKSVx8yqyKv3TAJS7iyjsHChuTgne+rW2Od1cJ41SLbLblptZUysakFlnkrSxqVpmxavSuAkUZP/1Q8OMMe1KNJYojQ0tATVdMHl/1psIYBkplfguoVzzz4jgGoGv8mqsi1G8Z1DBe7dsG/sDTi2hWG4dCxFZ4ro5sPRefTA1Nd59skvHIXRPGYix9mQZh4EDk5oEoWRZTTfj9WaeSwRrwO6crMpcufxurbNW+xID41iONodGnsqXt++am4FrvyzmHLCSjuaLy/fNxDf5wRp4ReQyozuvCsxsN3KLQWJY0LDWjBkBCz6/oIm3VP4fJUK7L3kl5zJR00htpxWHdWtYoADOzNXVjCUFbm9vDSH9AEyCKcmnRTRtTXH7Pv6tSmXwxl8Hm3wuA5Y3GpM3m15r1TwoXlstq+QUpZ7VKwEFjFmvF9HvKdO931VekrQdROFM1ZUWWUZWElAGMzzWjo5lkqaYiy0gM+cdWYVTZ61CywzWES4+3qFEzwS7/MbUQDUneV1pkMiDIh+kBqq5kJ10ZMzMOsVe5jJlDCYjucWzzUx5AY5EZiVz8vNYC/w4UnpU2UCiR0qQWewOLhQAzKAMWKYiXjTtBiJtYt11RgXtYwrwmiXnQgGLyiCYZbRMSHtKib0MKUaH+VtwHTWsXAGZG5kBJe+TEqrSxrTEUa1t+b1OTZVLxuFxxFgzLWst3cnGiJHAlZI86dxX2VTWSRlwIJM3kUFAhH9fmIBk0qsXDnY0tEtiMIynqwlG28OVPmRuYWsgXzS/en1rya+0MFv4LBa3cl3k9klVyLRAXt+5GLp5hENFzuowK4cE0YGnABYmR9qKAYsDh8UsAZMLAxNHt0SAl7mAmYTJyWRUKG227TX0GANzRleEinB5CzVcH4iydaqX67wbfl7RlKI1FCtQsTPqEa2gvVbGVNIZMkqTou1TbsUvKbqn7+0hvwouFkL2jLJ6Y+J1EyoxbLh1eM1RPwuxGhKNrSy5NgbHOiOlJWrrlIleN7pLaGpgW9KVEK7HT9E8CDxYtAJGaom27ngXSFMXC1Zbq2waMRxpEFjwykly2QujqllaBIdOm4qlPlmCA0vOzS3AVvnNkt/V6w1KGY4SFHAF6WTIy6PbQkOiMaLLwyTU/NGmoztjr4zJuBw0rEnHD3kD4mZMlk3Nx2I1c+EAAs6Rk3GVToYXmV/nyocU9BfWgxgltHkspttgUdS/Yu2MZBmhzD2aY+6yII6Tk321lgWQxbXnvu7EjIwelQ7SKZlt6T0IzWrhdAaNllN3SooK5ANzyUbzCC+MOOL1RzM73h7YuyvEtvXD/EYZ9dqtSSYiup2LTlYt3QZ9JgewBJZtvUoWwhoj0XWBrMv5UhpWjnoyLLqE64wn84RSgchM2RZPHCEpD772jlLSkQKfLHz7HV24r+/7eRrrytfKqJxC+jpUVoFgxt4LX8nxrsfcNtqWNF1yLWNlFGf4CH9NlghNqdfr6j+6a2JNipqpthCgXkf8FCF0YifHQpY2AR2MNONN3HZYlNz8YAqCJZahYPq6WMR2GamtWMPwEHGkPZ8Sy8WwAFiUDco8CEKYTFh4YEYwXXNnAJQB02v8Sj9LceczR0w3WFypTs8snSH4BG4Ff1LL1hRpzFmKxlwugZYYSes+5o7i9/K525A/5o2Gm4scQQaXuaXU4mdkIiiXQc5o1hbis9Z8qMUZQLzo050TY/I6KstT85BEoUd0TeaWOV2Z0iBjCpD07Mghz7Hlc8MgQduSnidGqXEg0Pue+DeXUfH5y2/M3Rgn0L3IpU5ATc2zG53J1liP5GCMSGn60Ix1OBwsEbfYEhez6q2yFqkLJLgV7xvCWkn2FPPigrIVkYSUCKtw9WK+eJ/ojk3HqVMNhTaEc2xk+e1oG2AVtah1JonN6/ebo9ZCAJmlRteZ2TVw1BiUwJSk35Q5GdUayNefq41DXrvdiE9NLqU4uc6uvHc8EF6WYDVWA8gCyLBWZlhd61ROaaA7KGgewGrymXMFOJRfpABZOrrMTH/Q2MvEpCuDpM/MHfP7nQRZRiYVhdoI8rqab7j8zTIMrgYAM1CVbmS2tMcelpigoY1RUiKsPVGScKKsfI9GrpQZlveQ15JAaEEKybx6zb+zuLm+MnmWCySLqTussUNJGcuK0m0vmd3Fg1M9HasIkCIIFqIRBNtN0OGypfVPNy83ffuMqVfBAIHtUOUjO7CnYTdWtbsqLC+BsXopkrVlUrNmiW64RS2mJJhlp0ZHplmC2si/h2QhA8xiaD83+amNm34NJisrico7aXypL5IPM3fOcnxhtVxzqVMCsFj5blgqeOjC39JRgIZFljW/JN2uNpHRCRcFGSUcFLGW4qn+TaexJJGKdqdkh4W7nD9ddC9pDc45yftu+Azwf0vaXgWwWcPo3CJGD52QWfAqebJFHXG9taldZ5kFMk/ZiSxgzu+sWVd9uacxNAF/0m69G//WgqKVrwMcVaDVS9nsQ+4V9SuwKRSs9rrR7y2duS7AkeVXyB6uIO/chos1f4xW4TpmBq4LI/E1yyAzK+60hHyBgGYU9z0gY2ufva2YyDUg4GTEmdFBl6FYwCdLSHwqBhIAm8bUQOq6QLnfstAsOzLrkwBu5qW7bNmawRguoIy8NUVsKm722qAl/MIeKhKqqJeeQTlM5oEGfIXtxV746SyWd+Wc8sZa+Pf+5/wrypaI0xpRXUvX00pNDfnU/XroCQlZKcLavGpOnOyZr0gnc+E3yUTRD1S4j7tFLp8ZVLInrQ1dRexKycMYoHmpaskyHdUdgilKpxN4co4InFp4+Hvuis5kZ7Tq5Q0wZOnHKPkfObFFcrPv6vdx8y/VRsQRjXWG0DIIJphxshatQIiksFw8mTBHl9CpN2UEtQFpLc3WcgyVjmDLkK4RjU2mM4jWQqPRwIiQqj2JS0Xrzgx0S06Bla1umWILmiIl6Y5VYPPUyGk+qme0RCdr8PLy4yTjeirqNQqUyvsP4nxKYJc7ByIKd6zXb2jBqWXZUlE/lymn2zYsPEkGhS6ek0xzqykZVeyTESGLqMoV0j1mIqZa+MiqEwi7qkXUts73Z9Qqy3V1DWxr1aStqIdoZ8kQKKkX3Q70TrWKsSTiNAWK5djoKe47uqtId1tig3tUyRbue652K1gR1Xhz8zidQuVDmBN1e2CWebpuVWMV6FIJeDKMGh0YaMbKyIqkbXFjosWK2RFddmelECK5tNr7zEi1LsqQupeGqwk85gKQyghX4IYnQD2gNDHdAwBTFa0UgbXeGPBQ4jcrTBxIYGI3s9pzjbSdNwShWlu1S4yxQgcKgJDSVVotNF1ugRRYGX1LAbsWqrvzrKBhKUooC+c/bSHy9ZpZMJmLrA41VqqlQkxKV9cmUzG0XUVQRFug4tJwjP04Bo+iv3CcVjGfo+i1wAPf3mXoSRaba5Qg6chDiVBhyblk5qkCuO6Rg9KBYA/x1BX2xTq0lnc9k7y6n6f+tQaeD4KUUx8shiHBtW0Rku+U8dVGUcCivYbSSjT0qttzxAnpDMzBWka/Nwh0bUC0UBQKC9AL1JtJrqHdCbooVOjlQqP0BAs2s5S9oUtNHheKs5WzUDVrTem+a3FI9HeCY643tkapXHeyNdC6udjKCvpORiu9Rov8Jw2HjLyj961QDsR2Zo5KNwBKSNICW9prZm3j/Vi3qt1jUMDtZ/BOgo71OwL00ikGHj+MLnHFTKXb7UenmRJ4OGoViAtwvZEzV9BToZkUQ0ubKFvXGnqb16dmDzHKSy3NET0vjdYspOfiK5J8qnZzhVw03wcjv04glUES4HEvZ6EzhhvF1Om0ZQoEpRppcLaAIhDuJCH/aRkd3KOqnEuaB3Qv1k46qGBu5Uil5I0VaNhS7XI6EwCWxQhnOtj9kD8MsFGfHDl3hnyvwfXSE7WJMe3mBJjGchAtGmauSJ2zQKBz8xAMAqOEC2bH2oAJsDxwcAU4pWXjZnmN2UUuguKa2g4mUcm8muyaUC8uEWtbGIvt+T2Zy1Xj0trC8kJ7H25VF8ilSKBJd/lp6CdWqOWoDY+PJEOsjUj7dSnqux0aGEqvid5A4MUIIFelDJOKj/VpsqeadTFylxvNrgQZTZXhUlwq8gc1BbTYzkJYppRkWYunEqCAzZ09pwAmLOlQCYbaRCE/TAXOqaiJ0WVHEG9/t2bJaCPsV4ZHsoNBaj0cwZKVLs00R5azZheuGNqMgtrF070oGp0QID0tXUAALxaschQpyidQZRAnS4nIJDK7Q8mpJlWlltMrvLyuyHuV2FqWDAZMxr2VazSn2vw2OkkMbCiQecdP8MHbuJsYqOa5dWsFnoxoMhKICL++TZ0m3CHCryXDUroIxkxlbQTz9MLIRmAIzXDNror1dZ6BvDZ9B/U0ie5VDhYnr0X3WnvgXcOCM5AjX5NLxxaQKQZlknQNRSrbolXzMw+o68H8fXRjEG1olrHIxNGcGHQEcA7Wei2hs2F9o9riZfpbs+XNX8oZrGEVVumMarmZ1i9OCfbrsJKUUq9mHVSbC/U0ELIFqtSDVEa0xBJRk5UYAOvfeSHWCoTofwK40k2YvoDtg7wrRgnFRPM/zpwNANlP9sNyFDfXBHsboaYyGSwz2t3PiesmXR7pVKlcaUySpeTTwaVXYqkeif6oNwzqxkCVnFufYOmHUysXs4OMrThH6mmCezH2FnYZIKc1d8WFkRe3wt/5ffREaTRlx4uOY0RrrOtlMIrutw2uxgiv9Mgmsl1RRp0x15kokatDCbeCWQ2lRGdVMWhiTXYMxlkmhH3otA5cN1v4cG9bO2YDPyuxHLPUd8Vi1QsM32T32e5hup1wqYtbaq2SpsnLVKwoc/9cX8ibxfqoAnT22JDVnuCY3Tk0xl4fXMPOk7MnJwAzecIfk52xNCwmvOF37YJhVICUmTMij0/muRWHrRMg74e37jKI3GqZa8WcE1+jdKrkqkJUhQUZtM4kl2RYSwRz9GPZoI8IVMUKxJx1X4VlpBjPRZD6R7pUaR1bxlNJqQVhMiigoXz/Z5zev57YWiZbn8kbWvt8pWu1BNNja3e89vy1ik0zTSdTP5K0kwPk7yyzZO3KOWcEcc5Zq7gUDTV9eWcpZ6vjxMjMxJPrmWEIXUvjkwXOqfUkD9C9KZFQfNDx58Lk8rX22srea/+Xq8gh9izvMXgkg5Cbl90cXMJCr0GgzTtMo1jWr8cvN4mrAlhQ7eVRJMYyma0hL1PMiWjddHZNLRxMzhM3LwGDkKahS2DML0uENY5rJFKq0Q25lLhI7LnV6Z7mzXqeCtA5EFADQxojXbRNX8j1rlFvRaZ10oIvQG8PG6tCcMzvykZcJ0FKZ3AKi2tUk6FqmwqYWH2xRpq0LUvu5dbOaIVWEUrstBxacl3k8pV4D+m629C67kyU245LiQyp17f8eIc7SflhRVFV7hwdWqcsuZAomd07kYcF3YA/EI/ZLTMr2/x6izveP97upKhtBEX6j97cWv0cCLfRTdgrm0IfaRmTWxyn7+DtqOuWmHQFIb5+Wey2otmS1zS3TQVpOy346cof6T7kWHOdOUlN0brWuSlbigl768DJ78rNWIRlpzZk6N/zRStZ+rE7P6lyGU+PY/oPmYkAcf4pAAAgAElEQVSe0JgPQdBKFlwt6mJsUvOyPtqyfO9gNSdpTVPSjoRCA3L6dulKpuvMEcq8wsrEyWly7y4zSa1aA24V7cvkZ220QvP0d/7beyEDDumk5b2WsRczEbnwvnO0Ni9VX6siGdppkGqQnJvr2SDMxEsnearrhhv9MUdOwQRFVrNrLAhCX78rPb60HrI9cy6ZTAOhO5gdO/J5MhMe39NDVrq8HBXUK0hDlr4UZekPBvTSqLvxu9kCwsQOuiuSnN2thAn9ShwmC57squOoJoCd1t1DyrkqIgCeFFgy15/bDZTYl1ERVb9nc7yWqWS1vZbCEj47+gmZARWmQPrQpei+WCylYaUZSTNd7eb+oBvnz5+Nszu70esJwdH478GDh7zfra2t6A8Eckp0wyCwXZqBSHlKLLZ23oo6UKDXtZMWuaPUrni5nDsHrK2TpDVNulQCCLnJ0jJk4mQu0kx0TNqcG9EA30KVKTteUP/t/ZODmM+nPizAoJqL2BnWOZ9plfCMaJG8s7kVk8kktvfORXdrM6aPn1C72rp4PvrbWwEGqwWpzgdkxcuGB1gQ7A1YeB81sK4WVq8zjAXymrtYXEsK/BJt7SZm2YnLd8ije47usejZ2lhP4zSA3MAmcdpccFehmQyYiY9NqATVPq6BSGV3yCjnkJGFKe8bGf7ceDZ4zKtCEms3e6XPtVk6/UDiJJglPsO+pohosloC/x7ZYmbhuHVajFUXT7IRTXcS/e4ounC7u6OIzoDw2u8O5OLA2HJssW76/D0aQXd6A5N1MRbci7qjwqkcuNUPvtfuKe6x04khYxTqEtvQiLtI3V02yFc4Tmoix9gqvW8FmARYCeWuGXVKyCJlHjfR4z9JCnFXch+ZPZZ7ldn5iPq7r3xRXKVhLwgn0rXRsA87QJFSzGHDXDTtAu87s/E2jQCQgPMe5AJ3Jtv9UvycQqg2oZHW0Cc3vSp6dnhK73P7jgQ3n4iRQ9L+2RJV5WC5KyTjmqpMZ+i5CQJWJqedZhz57/HGKP7kj74QZ7avRBclat15HB0exHvvvhNnz5yLM2cult5GheFSdMZCw2IEHZZgyap2WACkVCwBYNnaArrDUj2Z0r47pC6WkZqfeh0plC6oztZTMnxJh+2eFNFVGkX+JMhzoaQnQ1+7iaPjw/jBD74fDx48+KAhaa9T8Xh8997eXvz5X/xldPrD+Ju/+dtY9Ubxf/7f/1d8+vf+ML782S/F5mRD4NNT4Qdm58njB9QSz505L+uGfK4uNpUEeQYSM20t4xRmJFmQ5KYeBjuXSuU45jW8XrGX1RxP4KgItH64b7JjbeK638tVk5n4tTwgxaC4XxXBfOb4fdAb6mSXp1WHalunC9zeu+SLTCPImj/LVz5kgg6GiVoJ9SvPlf9JBnA5Wz4b/s0IZlsS5IYQazKJuxTrGo7k5mvpFuB3rsXWS7Wi4nEsMgyTvvWGtjA90xEwI6rlZZCCe0KtrvNwGLJUPpOlJv49dwKqC7T/5XFr3JhGMtkdr7I2EEhOLPXpJQx/uzEaXcGq1UfmQsnvVESHE1V9KW1R8c0ZfOUMAu3BstrpdHlY8kJM0CxzbT54bY02R/G5z3wpdncvR48HsSzjnV/9IuYn07h89VoMx2Ona9hlcnSMG8IaAuclxVuhMjtlLgFeqyV7cGti7bpWpQXlcIBy/Jesb7YqKX63NT80liNF9gZVEmjl11rjUgM2aX76AbtaxMnxYXzv7bfi4f37z7fhOhEXL16Mb33r76PpDOJLX/yz+PXDg/jl/Xfir7/0V3HnxsvR7Q3MBHQ/uIdf/uq9uHb5QmGpvG2tSdvDkm2hRnSVy2ONnNdKbw0fohwpz58XYc8oAx9S8nCdlJ3FHPRTooWnntzyJvmgzF2+//mG6IPeXalWfEt6Lvl65ojXZSZtbwnLEEVuKF6jCESCgHOMAUr4BFmJsk+ULOz/+Lr3FH7HkvLqNDxmkXt/FeNakXZ6YGbhSQZMqOyFGCCY2a/vskRXhkfOBJ4QiZKOmvoov+igrz6eQaciyUuRoqfCd/xrrqgwBX0FxwRMPfXXY+QeDE7RytXSLaq9LzvjXXSH8rRjkNzio+yq1KDs5+vJtIySYZRktlzJ/IxALCcZM6SkxoYdAnjMUZFjnUXvVieYIeRhFZfwA1YTGNbnPvPFOHP2Alf5YtHEL37x8xgNhnH18pV2J6i2wv61mI5aYThilbw3wQP3RlIF4AJrmnMR6WF6sVxN3bNHuSZtVVRadEfOGIrPiJ9cLIqwhf1oxbYi/XoPsexV3izl+syOnsQbb31XDKumZc/amwCsyxfi26//11gsVvHiRz8RP3v3Xrxw82Z89Yt/EZPxljQRJ5ZiGpeLVfzm7m/i6qVLyt9KBmWikPIYn6hkwnuI7L7zI8mCMlaTdY6VF1yChXymTDjQtRR8b0Gohnhfvqyx5xmSZw3Zs36f93T6fdiCGVwyHql8unxAaNRuYLGsZIJ4Gw+MMmIkYCRoJWLy1wYrfEats7UsMn8xxz/HjMDgG2ZdY8XSjEP8LZ/BSKYIaAtcYoAqbc4k3FUDN1dP3v64sSF+x+9RF1h4NNzYK6SzgElVeVnMD/OJPk3+3sDlhN/Oxq4SR8mMvGDa5eEAf0ZBsNGZiJc5sb5BCrTpGqX+WPVstpbAkzk8ZMqxsvhnH1lo3NBqMEpY8++nrKARAOuPPhdnz+4RBB48ehj3Hz2Mi+cuxM7OTjnEs7SmwObycUpWPL3DqtmmBfMMeaDn1rjyFpB2wXa39KvN0LwiW+amFaGRqtlSu63WgStznFLvgkYhDSnt+uzwKN586424d//e8wPWpQvxndf/JQ6mJzHavRC90W782Re+Eh975ePUreAK2lvmYz58eBTD4SB2tgfKms8EUzOj3ATU5ZNZtRpyccMIbDj8QrJVyeNyBxh+V47IaQXvgwDpacD0NAA5/b7TbKnitk/Fp2KjKsCs77FdzWYjZhMFQQkeaklTAMLXKgBVu4MVm6oZULpu5CvO2CkABaaVoJJPUd0kt7UftM0oNwgZK9w4YZ1VeUtoDpSQIcCTm6dzFe0OWsrRZKr7Rf7IJVQ7ZfZ1h7TC4FuLFzl6vDqkGCQTsx4pO3MpWMdUk81dnfwsL8TMKSG1RFVOIUedY5Fn2Dm8XW6VOzc3nx5aDEs/OpQ0uYlNMj+zUlHo1ImjT11KenE4GcYffvqPY+/cZc7KO+/+Khazk7h142b0+hA8LZCi75NPL1aXyaWiFgVARW0JmHxUHebZiumgpbIgHGwGHtR7XrTZAJZ+UJ6YjF7pFJy7rqPSwGti82DQpCwag8xrYsSW0SYl36Kj58nJcbz55nfj7t27re/zW8an/KoTcenqpfj2P/5zvPebe/H4ZBFf/OJfxDf/+puxu7GtU3S6fepSTHWDO/jug7hx4xxfI3HOhFEDF5+7SiLNBEBqDZ5kXApunkpz9B+uR6fYpSHpKDzNNiX/lNSb8VhdvAYjMQy9OzO8VUqrH49sqQytyF1J9Mi8thqE6qFdA7dVEzhlp9tDyEJXo+uGTpkgEF7neXAOwcfTnq5a5nsxBOSW7V56iqBZYrF8KffQLiKuxVbjdgmdu5ldd8q8FNDI+bAepjXrvC/OlzsjlNI5JdkKaiV6Yd2iI4OYFdw47SPuFpbXGSVJyVTSzY6zHBcf1WZxjcAD76VQt9SO3HuMqU5CZm0XdyGG6L51ZuIuWaQfesaqJUXWQhVeSCol10UCWvYDyqlIt8btXV3wmXpFZk2pEFPMrtBlq4pws+azWeC0+t/m+owmo/j0pz4T589fjtl0Gr+5++vYGI/jwoWLLsJN89I6Eqqls4iXhWR5NAAHJwt1je4ccZVwJKhZSbC1ES/nAvPK1ObW6Spez6bvbdiTE+LEvFq8b1mXaspYYMreS00cHx/FG2++IcCqacmzQKsTcfny1fjnf/rn+Pk778WsM4zvvP4/xhf/6PNqLMMumRoW3PNiHvHo8ZO4dHEnuoPWQtdIUb7eQ1zE8ewmXN1funwAKZsQuS6+7wISletBUCp97D2M1fmGbZ2KC47rdZS6ma9fSGDmQmWIzIGPVvjVTZeAtFrPq6YOp5Qj2mWXC8sCuiltYrKkZD/lcwYVg00CS64J/InrsPmvASTZjmLZlStGBGjra/lVqVlVrh2uk2dnplCfe7roZg5SmDO8z+UmjlDq8EGomQPIibKrxn5VCy58HaihyF/5LqYtqLMwPBKSEwA6kwp8yjs3/tzqU3o1SrzNbAUCG78LRKYTnY3dyQqFqTb72mFFECPt8q8koNHqZ9uMsoLxHglpLHAunS8NbJXZwudZeJk+KQ+4VOdPER4dvDifzp8ZJQTD+tTvfyYunL8SDx/fj0ePH8fVS5djc7KpHCJYBS5SpS9kexplF3sxVKE4sRkBsM64s6jIo81bB2EVszIO+Ry5wQgAfG+j03a8OsWW2j7a/AfZl7KNFTWt3Mv8aEkoXcXs5Dj+/bv/j1zC5/kBYF25HN/+x3+Jn777m7h45Wb8b//2v8SFvctUiRAIUKG0bvfRw8PY2NyMjQ3rUxmtzMimNzXfnkJ6uoz4s5ftXrTgCTxp5XNjuhVw5mZxSqoN6T28JmDVWldJfTIDyNa/2EOMggFMCC6OeOemq9lFOts5tdWYtppTSx4KIc+0qxooCp1Znxh8JqN8iQwMUPk783s49zXNrBgWiUmyKwctcM18O8R2ApgDSe8zAIkiJSrieTulTWnMXWtpvSuvJVDXjbdrVfWeei2NurUr9pKXRwINluDlKAMCSGJnuKKSxpUd778SKNElQ9F68jyUbUGO2jw7YpRQtsOHQ1Wh+syP0+S3I1rC9GklSYMzZiIwINT5KOw8ry4lSU2UdwgHwwWgthyL6VwM67f8DMfD+PQn/zDOntmLu/fvxmw+j+tXb0S/P4ymq+JUlajwyAl+HaOb9GUyYVDTVOaS7l5mKyv/CmCqw1R94KNrwoo5Md3JZElFxZR9jclV/k3bX6pp3IU7wzreVAlY0ga0VNi6110BxLD+/XcCrKvXrsTr//CdePf+o/i93/vDeP3v/ysjg8pZUv4V57qJ+M29u3H18gWe6E4QazvuFLtWNK2UPr2JyaBUNy3gz0Voj2HN5crcy0Sn3MRZguqTVvDrQobze6yN5Uc95CX6m+cypFhdZEm7a8neKlm1rLQCIhWQ1QBmL6Wd/moPcI2wb76ZWp0q4Mhg/Z2a84ptZiJ8phQYVFIw53Sw2DuVTcs5Hthyb+UhnOhdj1vF3DRJRu5TKRWZ4qD1mIEBdQGVR6GooA6SzR+AlFKCCFbYK3wv9gNAB68DpOb2WqoNbiJRl521n5fI39k6s1m+i4uLLWNNUV0nhidK+iiLpWxUbSh9XBqGFYmSaZ7OU6XGumZLt5lhTa3qXMzQmRdwCaEmpmvxFOAajobxB5/4VGxsbsf9h3djd/dMbG/tRgcCskGC9062JD+bqRvl+CoAUx7A2faSLgPinCr50nq1BaMUE91dwSkT+r4UKsmpnA7iXCyUNXB8rA/YT0hXWZY0r60AOUALEHd8fBJvvvXduHevcgl/C6DXv7py9Ur8/d+9HsezVXz1q1+PP/rU56LbhaAOoFKEEOhyfHwcs+k8zp3bJdmk5gT2lWBS6VbJrkraghc/wSwZ4qn5yw2lkSn4pxy3GiBOz3v1/VxvzifCy1yJxe/TNdOtqzddjkepYsmNmKjnvZtaUm5U/rpQjWTm7Z953bqLRuJAPQf19yYjKnhRco7aJY+vzHy2ws5qV7pmSB6f4gYmCPkL8Gv4DvXxoSx9y/lMRafkRmmfZ1F10ZLgEtIFltMqD8OHbHCtylC3wjxK9jTTYloCLPaxz/+lgQYQU0Ly3nBfdxIKsm8A1u4W/1VcvXLQkYHLJRtFlM4DJchU5LtCoFZNGbI3LaTjCe2OaacnQLk3OB80V6ET1vgZPfCUDKtawU8FrEF87NVPxGg0jidPnsSNm7cotutknXbFZ04H7pf5QmQB2cbFK5/CqZPcOInOSrebhpnjc+L+Osh6N6Ul9YVeVR0FRWBsjxZTOw99nmNUNbRTZr1AU8cfKaJCoREni1RAeXJ0Em++8e9x78HdDwlTLSIAsL71rX+IZjWIr3/tH+LVjyI6yAY0ZFcZsb1//37s7O7E5uZQ7h52fo5ZVp+0bcaK7pXuJFlaFZkiy62bdlTAlFo0N3LlMpbKsIqlFRaSzCNBxnpR6jbqKlL9VEBW0mzcRirvk1hUuaMpnSa41kBa68QJJl622pQGiswHK8yu1ZXL/fF3PpU8hfk6MKUwT2309Vw1W1xjgxWoYh+peNnJqKwooCgqcsBTtxSYUPql12GChyN6pfgbLh2/fBGL90UCNSl04YrEAe1JKKj9p3wrdcHxqdsr5HL5+C4emCqwkluaidjGCTPVztbZsY17W/0leq1M1nLuHSe17hyZbMpWkp0dlXtlLiKdyNfI6E2pPSwDI58jdRzCxLKJ+Wweiwy3fMD2HAwH8ZEXX41evxuj4SguXLwU9GNYO2WQ4MMzr5b3pWOoFH3D6zrq272TzBB5jHuJUGSNnwQCBQq8W+zKqkZNPspac8FqpcslzAoCTZqMhMp90yKtOjOBlYFsSdqtw0GnJ8fx3Tf+Pe4/eP7EUQDWN77xd7HqDOOb3/jH+OhHfo+uoJ5J941F8vDBo9i7cCFYjQNmZRePOnXt5iUWZgKugYHiuz+X02amv+b6JN5kcTyXtrWy/K48MVNF+TZqp8OJ6ZLyUNzk7G2mfJ1pX5ZRakinAJGMxpuef6TeVoFkCRTUEe9sI2xwKSDjz6delS7EaVDlcqqukd5MwZ8E4cp1FEAohdP9HPS3qjNKe4SHmxK4vjfJVHEFy+5I/YnpnLnQy58UbnwwRXYJVe0vkhYhxoNBZZ5WimBKihBgJT6YW1XMSl0lBEWUkRK03CyQXgmeeescNCwtWG469mSGnqTkytR3BERqKcO2sz7WnOkSDifz4K6ihxm4sreOxbC69bxIGJA1TxORdoMHm01nsTi9OE8BV3/Yizs3XmC5yJWrV2M42eQGzNorZM+Ku6iXNHW1PkpPVIuVaQmS5a07ZXqDy25UKoS7RLhscaoTqXo0FVrPzSP1QsFIcW4cUqAqNYlyMAYsDjJgZcsb1kI67VCLINmWEmuhYX33zf83Hj58+NwM6/K1y/H1r/9tdPub8bff+E585COvSrfi4apqOnOwf8jAxPb2hP4DGVZGvPIbC2C3Qjp+lcyA0+wchHTbsgpiLfkx3bp053z9KmOm5H6lW2dvXi2DK4bFXmkmCRldKy5UBWj5Wn629jqL65j3kWhRvQnrh2CUoIZrmybWTMeeVgmylFut3kvjWc0iUxksJZQoJVu66NPqg6Z1iqgZ/QSI13a/23QZJSNgJRUKwqaabYDHh4UpgZqBKYMLG+5ZXOd+Lx0bdaeJvB10UlFoVH6IDuqQGJ9NoAVcPJKeHVVNbOg1NMFTftJ7KayxEgkI9upSumCpnNzNzubZ8YoFl4VHm4uYdci1cjiaLqASR0sL4URGR+WwscQ4EqFzxjXIGtjTDrZOAMl7gJ+LNIVnAVan34lL568wD+vq1SvMi2FSmbhU6UOdBwmUVi0AHo5WIe6yMXmgZznsQEeOUXTmc869CLDRMRFKhlN6aLWjffgDgasIPL4+hfYsvtY9KCAhTz9T5TCpFFcFr1xI0+PDePOtN+P+8yaO9pCHdS2++bW/jegN41t/+0/xykufDHbJ7HZVULxakrnt7pyN8WSwJrYj54dypi38mryU7pzXD5fvKZGeI2S9pWgylSBfrle5kmtsrnLzMjWg2LJTqkHqSNk7Uh0tsy+bpz2XYPUgGd9h6cpTDGWyRG2yKghgpkP8qAAo3VxilJ9dnov3vgMcFP+NoNkfXVkXuY7zns0+qhPUEXljUoSfQwTALMnrxp+OaGSoWbPLszhb0R6gIvfRJ1371CANk/KuZNBlrUBcmApNg+rTd1ICSqnHUW+MJ8BNLFINA9n0j9nx+L48BpYVu3It7XdzBJxoyrNKESXcPgvRfQ3rzQyqycWGopYlH1b+bqbjeWjrVZcNxHzm2Jpy7hlT0M0DfgppoV1B+H2WS4gbOn9mL1584ZXYPXMuOpn1yLHNtLfsU63SAEawONHtQa3pqq3pATnhmeLhflFcrKiUp65jb7r0sk9RvuqyWQGW5jwHyh00nRhatIXS1z4ZL+5b3zU9PmCm+4OHzy5+rvYOh//q1evxta99M7qDSfz93/1LvPzCR9TCg6J7PxbzeTzZf8IKAbjaDI4ikTRBhODZegl5fZqG2lU5pbmUlWVSoAWZmma78rIgt2ZYCQ5re8/X53Vs9ypbWzSbtJdc9BmdS6pT32PNcipbWkDG03WageXj1MJ8DXSZ6pW4U4Aq7z+ZZaXjppjC0hc/lP6w02ePB5MjAM30IJOMsg7lw8qtbdFa3VITwlyMXyLeVXqN+zrpMI02AVfGuRpE/t0nbrm1lPQqu6nJRDkwBi3eV0tYssBmTSek11U/nwRG1iBun9leJRilNqM95SOMql5LvFX+TgcKiC1lKqCkSp290v6oib99a74siwDkVJImWJtcyfxplsjqBmA9wyfsRlzauxyvfvRj0R+Mo+MeOnLFtKJ1kslchwsgP4iTmKkNMjN1uobmQ9FDRUFdmZr1k1Vsix1Wq7wP9VxKVzgTLtEOGJE2H2uWOhkKy9G9ElSapsUJt8n+HNXUplVe0+wEgPXd53cJuxHXrt6Iv/yrr0V/tBXffv1f4/bNF+Wq2vggh23Q78fGxkZ0Brrn/CFoyThrVCqLnvdHhuGDFbK1EvdKRbA19gKrItKn3nuK9QCkk5XV91IDRO6dUrqSbkcFavn+gpcGryyV0f23z3paxK5/V2g077Vi8mzfi9Vg39TMQBtXGw9vL4XHPIhBaTUZwlfZmtwqsmz/W6xonUam0SO7qWp4dC1pkup+olXMJG3KGnDFMCkZNddACV6yegPHrTktwfCGNtepKzLny2V4KSFZRWmppG+X8cEqZzPBrBhtyk8GTufJyN3OiczcRKcXLRpoWBMqKtSMq2iNcDAby5s+ZrM4PgCyUSUIy2Xx/xf3q45FasV8q/BicClk0lSkoitaim4JiBIunpGHBZfw9rU7cfv2S9HpKzyv/j95qIQvQLpu1kNK7J7V1MtaV1RrI49UB/DiOeQGSucz6CgI71We17KlKUlLGRpDFrmbBFKwzIXODEuDPF57f1q/jq/MhN1VzI9P4q2334hHjx+tGYVn/qMbcfXajfiLv/zrGBCw/i1u3XyJ2h+eA3T78aMnsbm5GcPhMJa9liFyTNIp1e7Rv59yQlKOexLwBKw1lpWjZiPLKKRBvoyoXS57GfrEU0BFG7z6dYb502hUAFbuIYe/lLdUi95rUmF7H3LqZy634Gvj1Gcx8zTaytGT6clxsgRilkKXT26KzJ7bAWszmOFkAmiOdcl91FXFTnFv2ZkT6xevZgET1m+2dZER0V7GBaVVJUjqU/lkWfsLXUvH1GupquW2WhebrVkAT7bsArV2T1ibLmPmNa/PCx+USK19nzmdiRR2fkuHDptJpzWcG6/y5NoaudsVogeVQLzOnnpkHziBRoOXYr3G1AyF9wTRORmLEiD0WS82o2q2a0HGwBQa1jMAqzfoxku3X4mr166TXZUIIUcyBWtPJAEH/rJrKWxpMuTKsbMfoIHM1L9kkp7wUtXmDN1TjBIBiYzb6ECG2jqCXeYO8041g9XZkEmbOZr+T4OAjT07PI63f/BWPH78+JkYtfYGMKwb1+OrX/3L6A034zuv//e4c/OFGDAXoB/H03kcT49jYzKJ7ggN65x3ZzjVwssFJmE1f8Qw8ds8O/opt+Z9usaUMvsdjfiqj8jVWQehOkWgbK8EnnYVWXE5VUrkGcho5PomklFhcMkdLRmtRYcOM5d0vZSukP3b2xsmSPmGKUJkCyahvE+zsVFzsb9Arg3X69/ouglOohbNMmx1oC7/rc+yC2feT3H9DIUFxRF0Sg+nVY6TwdX9e5HpJ9zEenO5WfrdHAQQlFqB9BiQqiZYFutWCEztOWUS9ukVQgzLRD8+tCPXaR7xFfRGmuhsn58ID7nu1Bo1qeya9kSD4EHipmtPuMWTsh2qU+/rG+K8Oe043chsv4Ft0WXhZOuLY2xwY9OTZ4vuXQDWrZfjytVrrsVQWUxGPInUXk/5fEo/yA2XiW+5GFrfWrQZg7IO0sk35Nrm9bVhWx/fIXHnc3FCrZsJGLWppME40cm+UkaIkhVXWyMWJ0fx9vfeisdPnjwfYPUiLl+7GX/+GgBrI/7p9X+L27deiOEAuVYd1g1Ct9qYbEWPoG5+3VWFvha4ir3ThSiJroxkmV+TuaqkiUfNO7eHx8B5IXKk+QUZz7NLUAF/Rr6SqXN9pn5yKsUg834K0y8Ke3ECLbrL1ZK+7fpVu2w6+xFsxAmQLj+R+5sGpt0X2oS6WhfJkE6haSGhmGLvJSVXJiGQMC4WkypebSz1esK2lVInbOv1TD8wCeC0oIOnWw3n79f2o9y6bGWkkrD1+8wO+GoNtU57a7OLCCQBzGwxcaLVoXzPnn+uJp7+nkl62XpKqK4xTpPojZGupOm6COcKgLXhYyd0WGMyoRJgTPXVBxgx1Oiz19rUSmWm5hIRUieFVefNBI4EQ25YvMykyow+aBMjKfMEgPUMhtUddOjaXL16i5n3rW9sjcEd0MCs1HdHbClP3cmFV6wd5y/ZTbHFXlgt9W/Rwlsg17Tj+ZnqoOih8rOok3ESnNpgMV76lF3DtLwAcg5mmwuDZ5sfH8fb33+LSbLP9VMA68+jN5zEt1//n+LW9ZdUwhRN7O8/iY2NzRiNNuwkmB64TUNyS7WS9mbzWNUOvx7JgXGneJpvcagAACAASURBVNSjqXCraUOeyJKiQvYoYzfYdrNmaoiMj+/LThf5SJUPpRlrj/FwgF7r0h9nOqIlCIX09XRI08H70LARukoW/y470FcV3ue20s5ph58EQ6xIPaTWja84j/P8rFMme0rNiV9svVbyQ955NcspsrtZJAEvJQqHRpUhLjC2N1zdZ6GBxcUvcFUZAT2awcyTl/uaQFX2SHu2QzsrrcHXVKWz6AM1eG0dJJPnBaRbmfdLZc/rKL+K2jCD8svonKFL2GZUJ9uSIQIqmirG0keZazQabEIU+BrZSj4IEVOFjC3i6sDLjG5A09HEt66F9rPIPwDrw7iEAKybAKwrAKzsEpFbRItHP2nd1DhNWe6pQ6UIiftr3cD8rDJzFSVt0xKlcXjmda3aBJmi6wQQjJMrAgiWrWpQqBYHu0qPqARd1/ITjI+PD+P7P/heHD7Zfy68wsRdvXYzXnvtqzEYbcc/vf6vceXCdTKN49lJzBezmGxM2LudEMsOAikD6KvUywgBAlfwc4LJkbmLeXgtgx5Sl5n+0gAAlnH3vbsxm63i4qULMUZFNZ+1iR6TijHfaEu9iMFoGOPBUPlhTqyVzOGNwHIOFRjqGHMBSbZHorl1NBfrUgCTE5OMwvomd406yuqdyCI0U4ERyfVo8OCZUOmmaUAoZLOlNg0h+peLlcs1LOY7OZF1JBlnJSxr09ftZwSs2Fn2Q5hS4B6s5egr3IDrY51qJGhw3pR1qxKtS6OQRqaMqdJ2qCN6rGhAS5kcfTGb8PWEthozSAjIntIoC3AVzJIHAcOjYzOc2c45rcUAc2QnfSfdaUFVAYbOmXPbbgKV1FwbnX69zwmk4MZjuLJIWuUpiuS0J/62ezZtshPf7DpJ79DiU9fCBAmBV64t1BCeTE+eybA6g07cuH4nrl+7qZyizETngAuQMrdFqcR5/62OpXtu77yED/LEGDJA7JFkRrb/efR29qIvSrPeL+bUmvY0zIqK5vjoPQoW2Mbws1pBcmMVEk7A+sEPvx9H+wfPDVhXrt+Kr3zpKzEYTQRYl17mFxwcPIp+vxsT9nSXCC+2sNSx9vh2dohEe49lTOdz9XTvq15To+fWzwbuFUNJsqRHJ8fxk5//OI6PjuLWjduxs7XLgMpsfkwjMxyM4u69u/He/d/ER176aFzYuxa93lCZbTzg4BRbYNQ5QazNgDNJ1xyzFbDWbMoCa/JyiS5lBFkGe0njjMiWoluqwMgi9rbGNjO3cRklS6aL1oJjLqu1aJ9btohoGkBZjqK9xm+sGgxYUj1lC5VtntZS7M6npGfHgzIr6hylvMh2HAmpNIoaHxrkcsNaeJUnbJhLV17OMA7DEFv2HOUp2Q5ulcvZ+LJTGU8S197TR2U8OFMp5fBGAW1yDUsRO/KwltSwNlZ5Fl3SsiKQ53HmnAelAuDARi6C1IFKboYPYfKcsaI7C3x95mC6jS2aK+s1hzL9+AU2xofQsBAlvH7tVly/foOnkmj/ZOLq+xMApTMph0oT7s6HfLZsrqdCYLYMTv/ZVi8bluVJQeq6qImjm1mKlq315bBVdZNkXLAU9BTTp9egyf3h3/gf+7+bieJ3R8fHPITiaP/5Gdb1G7fjS1/4UnT6o/jOt/81rl75KOfx4OAgxuNJbGxM1itjvfD0zFleYb+KVtdsis9vPWqFagBt9MyYRqHr4eGTWM6OY3cXuXJjAYG3YTmUIDcW5QMktILFy6DZifDI2HpjjMyqYTRpAJd9dkrA3KJ/WLut2wi4Yt/aaLqgGjLiB0xJzDnD6XLl2M8JSYvU6HUYqqYFGd/4XYfuIMAGmizWfYmqFdPiuQWYoqGk2w0l42gz0nC97L1mTY1RbhXipfCSKQJK25EBVMNKCfZ8DBPTAkZF0Ncvct/rFOgFyUcG7MVXEsr8vO6gi4dn5j0vrGtlCIn3x5xNjzRJn7wH8WYbQUbOTRfojSEZW4aZWFWAD+BpnRuABYbFzOQc2ARO/1sugsVlfnmJgT2FmVT7rTjSQnAloGe8WcwjmUgLYKLn6M3+YQHrxvXbce3azVIPp1XlgeRmymaDRnUfPaWZTZ8/NYZkRjrSiQPHjeSTj2F54RKBVbnHb4KkoihYUI6k8HlP1bVk4akXmLqLVuzOupaOx1qxTAbZ+zh9GdnCAJfv/eDt3wmwrly/GV/83Bej19+Mf/7Of4vr11+I6WwWi3kTo9GEraOttmhenKLNFs2dTvT7fRevNgTzXm+go6s8SEXzdM6PompK9jvcf8juG7tnL0a3PyzBilx1IjwaB1l+tL1JXTQFllaVIRjWqQslRy43Khy4ATdNtzRMl+XOFr/yMskNtaG8brLDa/bRYorAMqO32rjKbZrHaoniXbAyHX2FXZwJBFqBSmPJja1UkJZNpUeRLExDIEDU/yV4tiDKbiok3er4wZ/SRNMabWFMbpxXOpcoOJbBjAyCERA6aMdkw1nOPgfbxvwrD6pobz4seR2w3C03oWsNdJKTqF04b5kpa8pjlEbYehVkydmS1dQTxqCDBn67exsrnJOWWKmaMC/dNELC4zVyDjTkq5WGpbJ+hb21F/PASUM97zQjGW3CZqHNBsbFcvGhAev69dtx48aN6nyVYtLW7le97p2DxUVqTaJyxeSucdu04jAXWObTKHqw7vJV1toYlWFrDIJYUvv8+Dodg24vPb8/DQRq+3rdGPpAiPQVls0inuzvx1s/eDuOD57fJbx8/WZ8/rOfj+F4O/7n//a/k5keHh3F4eFh/PK9d+Peg3vRLNDSZxnzpbrg4fzBZIPLhbQH5Gl1B8O4du0W3fFuX2frcTFzifhEQ/8TDO3o8EmsFvPY2DoT3d7EZVP4ikrDLJtMLoz0F8nIuTaLLuTM/zpjWlPWgv+C5ATzla2oqzMGUpgnKLTpIxSxRZg8x06gZETPcdE1Fq3WP+m6aQoz/07nFRCwXGvr35bn4V5ERLKk1CRfya4HdthcApOcEWUx1Ou09dXHLkV5s82WrTvCizXo1AFE7NRdRKk5aiyd392CSwooHP/EAqCl1zQBLyHawb2SSF0SytPds+sI4CvGKRmcCE16bsoh1YnZqljxc4Jh7V7cWKkLgwRUpCaovUf6pqJn+CHKOcqCAxzxw66aXZWP8LKeWGbl2IRINGyjF7lhpWP5MyloothxAcCaP7uWEKL7jTtx8+atKja0DlgAIYj4Og7JvrzBVrQ4Lbepall9ApnMeF9TUtKd4Ewm95bexOFwv3glx7WpADLqCVR1pKRleIh2gu1AP+qzsZ7mAfrRPgDrh289v+jej7hy/UZ85g/+ODY3z8b/+t//j7h86WocnZzEwdFB/OgnP4679+9FM18EgckngeM5xpNxXLq4xzKeQR8sEr/vxpmzF2Nn6wJP4ZnN5tT4Nrc2Ytgf8rgwNGvDobYA2sODx7FqFrG1vRtdHDTqxcycNQ8/MrBzLpBZDUsM4Z9uDg7hdNG9AjoGglzMlGNQ4G4XjvPrXDoWnjXRuBKbW4QHHeAQUgCDcn7a3ChkemfmV6qb6vghAy05fJmHMJTUCDGGFnzwYDDOBro8l5LLUy40r4hmjtCg3OUgSXbJdielczePdWQUPJ4OHqU3tAZETgR1NFqnOp/+cQAlq7lLCkQ61n4/2axPhkbAAYfa8kmcuG3Oak/R7l2yJ7G7NpUj94KTU93sMmuu5LK2c9HDOju7d2bVUktnz9YBgfX9Lw+GgKZJVGsSD4ApbavY5UO2bCLFaOpApNJtGDgtFA5RZVrDMxr4IXH0hRdeidu37jA1ANfONqxFlDQgoXmYWthYC7BLIJCp48CyNXlCtO6pGhBb2PJ6UUqqkLcZQA5dpi6UsfLvkxFI79D38DThPLXYvcgoUzZyCd/43ptxuP/8aQ3Xrt+KP/yDP2Ly7ze+8Q+xs3sujqYzHliJtAa4h0B1uEDcjHB65vPoD4Zx/tzZ6A/6/A8LaL7QEQ90l5pFTGfHPoMumBoxmWzG1cvX4/z5S2yNe3T8JJrFnIDV6Qmw8MzQfcjgaB/r87/aRafseRu/yn0uInvZtKa3a/swgy4rA5YsdY61WvhWuiHdejSX0xqQU2qmlwfrOqCyJqYbdeVuauzkeTmnzdJD27JJAScpPTZ4Lk9Tf3SzdqYFuD1SSa1IA4nPKsHTUCpATaaS1RgpitNTyCaVHiQT/5ShxWUkkygdJwNx5l8ptXSyJC3BGN+sxGHNpe5I41E5OkyTKbtCHDEjzVz+/mZ/nSLQLcNmWgMA6zTTkKXL0KvpWUaE0vGuFg/GglED+5spCIphlFhN8Yyzi6YYmRmWm9wJdDIPq3UZ3mcQgOmDXrz4wsvx0ssf8bHoEvH1X1ufiE2aGe7UnpQRKPclSyK8IBW9wVBjMciylYhmWgdqBk6NqBPwOLb4bi3Y0xaCwiOPOHcEkcl0bUKuEnfBsHB6jcV8LhIJ9QCsN99+I/Z/hzwsiO6f+uRnYr7oxmt/9tU4d+58TDbGsbOzSb1uvljEbI62PgumlMwOp3H30cM4OTmJ7e0zMeiPGJg4mh4y7X48HLu4cB7NFAeGHEev3+GR7dNlxOaZs/HJT/xB4My6R48fxGx2FNs7OxTeu4gC9sDAlnRJp4tpDIcjiv95NiJ+r6RdGxkfmJZrM3k/jCbcP7UQgvunSKfWnhaYbFPOqzUiv8bRJbuRikz+5NNg8DmI4+qooVbVBNkiFitVB+xOLYDVQihzx/K4IZa4VWEGif7Wg61xlahc8TRa91ZJuwa3aiMIHLHOLQo5kq1n1RpPdy/JQN2sUxgh/UjdRwTSlDL4Nx9IWUDGQFwCMvgSud2tAW8BSfeXpWvWqTBWeQoT5kxm2gyt4n1PYYHQEglYnFq+tw2vU1im4J/sSQJlilZtGY+YQab5G+qsA1XWoCiMVvxtjfRQOngiF5WihEhreFoGXDtjvX4v7tx5KV6484oSR8tA1BOWbiomwLlONEOyJhSG7aq1dX5yBFWH5RNEnIah1An3sspj7TMPi2+3vSstZrQR8Jjqda3/SsE5Uxbqxn/abHCJdMSWFBAeVX94GG+89WYc/A4M6/qNW/GpT342jo6b+OyffC62d88yEx1jyFwbtGFGmkG/S4bVHQxi+uSEJ/RMJiP+G9Gf5QwHcEQMBoM4OjqMk8PH7Mu0ubERo+GE7amPjh5xRbz00f9CAHt4/17M5rPY3jkbk81tzgNSKOazk3jw8NcxnU3j3Pk9ghmY8NXrd2IwHBIY7eEkz+V305myMeHmL9E9rQ2fTFAxGG1+zemp4uJ06aRNcFkIYGiyPZ8ZEkN0r4ogMhqpNaIk1pbhFN6GiDPlFK1JJquCj1iALy6kOYl0dvE6I6560/H2rA2nSMMKCu2fLCUqZCNbvxRdz2eMVv/GYOL7319U1coWHF7mE6LGMOtas1JgnUYUTEgm4l/LwzAYOfVIwcBCpSoXVXtee0IYlN4SgnGdMxd2mDjaRrtSffPxUnmp7AGUkS2lNZHGqT2tXK78Alk3c85iPjDo0r6aBp01tdpEe2Xl8HDQsGDZn9WsgcXPt16KF19UqxR8N5gJNzoXRuZ6yWoUsAmcM9ieXJuJjNnpEHPKBetIlBYarEuCSepzCWiZL9XS3wS1tGJikotYzFcxX+AkaZ84UomTzG9iu2IkmFpTyEmPZRwdoTTn7dg/eP60hhs3bsfvf/LTcXi4jM987osELJmZVXSgOa3QawAHA3RiPmtiFr04OTqM1cksBsN+dAaYN7kgPIV6tYr92YyuJBhJD0mn/W4MGNWEFtqNcacbPQRQpofUEcdbmzHc2IqT+ZJ5ds1SiaAY162tzejjs/1hfP6zr8V4vN32Zq50Iq2YdKUkywtorJNkFiTHTQwtHTsCS3VCkWiODCi7d0KPqdoGZ380gYe4gNg3tLW2MQCvyTUM8bg19Nw6AF27z2rIaGPo96vcTV3Pyg8XizwBOlZ0Da3JOUKn6ACSVSuX2lu7xYv0+fR5AQS+T6yw/VFlhRwrV55knkIqQDxEWbDfkpU6JSnduTQOLeCmEkj2W84yUNpOq1dZFrCmKPdQ2mQCarPoROfspZ1Vn7QUKJ51cxLSki7mg8nry8MqMYDwZTMkqap/QldJyvCAlZHxhOPUDA4avgW6iaNCBBGc/9Z8qNIcdEO+ceNm3Ln9cvS6IyePIiS6TlG5ZFYQsK23kbWA/CdY5oIo6KAOmnAZuaAywOBB5duohtbLzCfjtNewLa6EfS9YLLTFQhneC2eF457gxmTfMbuPwnRZ0pOD4/j+D96Og8PnjBL2I67dvBOf+PgfxPHRPP7ks1+Is2fPErQXy1kMOt0YsaXMKE5W/TjhIaERR0/24/h4P7a3xjEYWiOcz6M3GMSTxTLuHh7FTEd4u/RFxzohujOCm9HMYrQ8ocuFg0fnWCs7Z2I1HLmHehXU6Kwo2G9vbMWf/8mXY3tytrS/YQ1jtRnTcSj659opRt1Yuok8NgjmECCSIXv1zHfbE7tMEL6lY9qIKt6vLe7iaEa8DW7Ks8K0OJ7uaB/XvjUxeibSBQQSvsc8u5KpEVUJTmpia+kMRjG6q+rpEkzKJZtrI5f5mVR7yrXKQJnJEAIqoFoNmDLT6S54+QyMydMyu0wNydq3ziAAa1RZWrJdGQe7wx6/GoSzRU2egsE7cjNCwiCxK4V/SSaF+TEoopasnbMXd3lLtLVGmszgTXKlLFRHvBzNodNS9BsNiDS5DES37qC2cJtzVLt/YlVCblJO5BwtcWjo7JmiOxjWjWu34tZttUlOiqkcDlWVkUGkG8foBrqSaoOJDTpXxfKq2J5cxyxCVW1gLgdLdW4vwjCFxcgEKC3OFswU6nVSl3ttZeFrBgpQdqBaNGfkQxdkg720iss4OTyK733/e3F4dLgGlM/8Rz/i6o1b8YmP/z4B63Of+3ycPXPeoLnkqc8L5uH4lOr5cTTLk5jPjmM+i5iMcboOSmnAyXqBhu/HnU48htbVwL3RMWgQ8NkZcrEsSa+z5Twa6GMnCx6/s7GzHZ3h0FpqtqkWOAy63dje3I6/+dJfxXiEdtdYlRlNa9cQNyRLUixAlENuqzmSMluO12kJw+n2KWV1+i+uL3R0SWdtan3mrObhDXKT2nVNVzcDlHZ55GkqKihmIsPOcwX9WXXlNLqIzjsYJeae5yCkJqXeWTC4eR6N3q+CZcGEJSy7tunttIcEe3RkD13dYLEva0TK73LtcodzWycSphYnvZUnQdUR9JIK4ooOghQ6SGg3kUjYxdafSrcg3rDLr9pe8f1gkotVdPYu7MDZ0Qkq9Y2spezL9SuO5lOkJdE3DWRB+IJ4LdYqOlifX4gJ1MGK0rCQhtDEyTE0rN8uusOVuXLlWty5fVt9yfmw5QxiDbjvm0dZ0eIiZUALR1EK3VsGCKDfpFioac97bTs76EQsRLkU5eAg0+UTE5LlT3bZ+u880pu5L3L5NKCp3Tknh0Wt+lXb10vAenx4FN//wfcpVD/XTz/i+s078bGP/V4cHczi85//EoV0ZM7P5lML3dKyoNvN5icxW+JQgVV0lnZhe93YGI1i1OnFFCxqPAy0RZw1C6W2oDB6oc8gjwsppXD5wMDwJxhlv9+L8eZGxGBYEiwx14gKQ0obd3sxGY/j6699I7bH0NgsYlsjVOZ7bkqNsfpWaeOksC3rbQ4MFkRb4TcReMx8cnOlCs6FsGjBowyymuEJCbx2MFZmTe0elcis9aQ5k4upm9Gm1NFZrKxg8qdLuWzglKRtYHBEU4K7vQHnvNHQ0ZBmSkbibZbutNs1dS2rqyXVgiwwE6DzWRNkSlZb6nh4qDxKRm/WdX0sfbrWxgElzMpzEjnQPCDfHT+nusmI6PhkcPmnPR3KzMCmTpgmDzl/6ZwhWZyvFEJm1hKZit/i+qzSwRx029GWBIpkS8rirni88bqNICqkrbWeLqHo6gIaB7s1/HbRHYONI9iRhyXAyrBq1vspEqjnksYl/NIptRm+FXikp53lGZJdc23nkWbyrbWomA6SYWWJdsXCKSysjdyCaJYitV/GhefNRLLP8osETdNk7r4mTo6O4oc/+mEcHR89F17hceESfuy/fCKmx0185StfjfPn91hRADYE8R22mpZ/vozj2aGa/x8pE576VK8X/Z5YDbyCRa8Th/OTmM5nPvAAx5JpTgFCjDzOodUJ6PE9WLGTjc0YoOcWr7WiqwjAAitGourW5lZ88ytfj52NMwR1khCbFzw0c7cYK3HNX+ZU2W6zjUuBQ/0tZ1YyRxoVaUtiJNlhVqeXK3Jso4HSHQOVlnS+30zNdloke9kaIjJpX94PUWc/Zf6UmLaE/jbCILdVJym1grsYvIwlb6k6B4FjQ6BDo4HWBUzx/mm6kxrzJZgX++m1lSwqGZueWwG6NPLZGkpaW/0JQZuTrHnfBiynVkiIV3SyFdbtDWWXE9oHkaA8gKSzd/lcO28FNdsbULhTTAMFpSli4ltAY5PaldwrhHYrkNNi8eYv20wpE+ka+mANN3MNLuATbJbfTrBIfi5dvBo3b902EJmx5AJzbZVYkMEHbfWXC36HBk2gAMG3P/CZhl7k9KidHsQTX2QmvYBtZSoKXpL9ShTHCxwbFiFZ6xpyVRUYoB7iNs1inxKCc/rVolmGZHoowIJg/Vw/cAlv3olXX/1YzE6a+MIX/jQ2t7ZjOm9iPpe70u/hP+JIzE8ABt04mqJIeRYDdFAYDJjIyk3S78XJ7ISHVizms5ggQhvLmM5OYv9k5oM2dVLLaEsHxy5m81jOmxj0RsyWx9QMBgBBlHwgMjlnFHJrezv+h699KzaGOwpA2BnD+upZg8T1MNZYHyjMLqcDc9S0qbRs5VAqLUVMwebG7Cj/pbnIDUc7EwvXDGrzG34EhVxKqnDkfkj1o2x1R6Cz7MeQ286ZTruR2O+OBTxsQWI+Xc9sIl9yvMzcrZEpkiiGlewe3SJ43kCJZOs7+OS2n63TrOeqG5qyls/rWTJGQqm4WXpO2d9Ov80gSPosAifcE/ZcEhRWmhRGl1KN0T7zr5jmoBQHYghvQfmaHHE4NBevnF8tVrCSkChVvFo2SzJgU7iWWWVzu8r22Z2UeImbzvdYPSgN8TzQ1CBsJXn0D6ilyhpg+T9MPywA1uVL18iwMqNVel9S8MwDU9U3Bgyi8NHRNI6RSb+a8h760YvhEAXA4xhCfC4uY9W+lcgmFkL3o5hPIVpuB5GsSsOii4W8pqOYzedsmrexuRH9/kDJjKVsVJ8jc8vPp3fgCOXJwWH8+D9+/DsD1iuvvBzLRSe+8CdfZc7TETSo5ULtdnK+uv2YnqD6HyF7gGXDe0XaCBJHO8uGZUPYWE/2D2PVzGJjPIp7jx7Hw0cHMT9asC880qiaYTeGk248+PUD1rtdvXI5bt+6HePhMEaDUcxmJ/Hkyb2Yz6dx9+HjuHX7Tnzy45+K7a29mHd1khOGPVv5gHGxfpHuPchEQ/dz6RwllXLIHcWEo7UNz8+zm0O9lCJ8HkxIU5CQ6ERmGbL27EAZwQSzcq4mq0ISghwUKHQwe8vJnMvguy2O5QKI8wkeZJolT0ulRHDF4Z4rxik21eGzUFAo0cZshAh2C0OMBF02ZoT1cdKtgAcdNfSN7GSBaga+DsNiplVJFJk7luOQCbyt16E9xqqVNhm9uMx6X5teRGAv693GngTJ5Wtuy0C0oB6mSDODJFYPByCPe1fP2zkX4aOHnxvSD6jH1KRk1QJVfy6oZE8Zh0ncFSrLlDjkzIfI6Ibb0DIlCtqJBFt8Blb/5GT2zLQGANbFi1cZKeRpz5lHZr1APFH3k7oGmM7R0bEA0S2eQbGH/QE32nCo0CuPFMKg2Srkc8ondzcCR3lYM1aKPrU4s6fXcraI+XQWBwf71nH6MYIWNBpFfzhgp0+AAUpxWNJjOi/syqnRmOEaP/3JT3gE2nP9MEr4QrzyyquxWq7iT7/8WmxMNsiSoC0xux41gTw9ZxVHs1nMmzl7yGOjoCQHANTvDeziogC6w1Ih0CmI8gdH05gvm+j1JzHoD2Nz0o8zW6No5rOYHk250YbodzUZ8Ton80XsHx1Hv99hH64jpE9MNuITH//j2Nre06LlIGQwR9ojGZnHCIEK9R9nyEZpAFxHKo9KXdJyrjZn9mxyQrBWHABQLj5byyjZwrnuPvE4fX/m3uG4dm1G7pUuVRadNwn71e/GiqVrCEyhFAgAKImc94aATeYRMOHUzJ15j9orrDgo0U2d76f7wzPIqGEtT6cH0ZwcM5dtMZvSLQeoowsqTmhWJr+6ep7ZPhs7k13+Pn/IGEuAwPKPKJthWuMvHa5AheWe6t9mnsliRXxM0sheJaa3yhr+hi6pqfelG2tX3XKT0j703QNEIQFYco2M9wlWmH1VQmvLs2g57Uy7XbgoKD63qQTpKHKg0u1KJczaD10L02C5hKnvoCQEeViwnM/Ylr2Ivb3LceXKVW4CLVJ8RvVOWlF5rroGCwIwin6hr2QaBhbWoNuPjbGiV9BlAJpwE5ENjg2lHBI3kKuchEwKLbIBo4xNHJ8cx6OHj+L45DCW80UssLmoIWoBs6X0sB+j3pDh/K3NTeo3eE0TnSJ/SmMruoT/8dMfxQxlNM/zM4Do/mK89OKrHKMvoS9WfxRHRyfWzJR2kEwELtsYiaEHh/yurc1JDAeKEgMkUK6DE3bmJ0exXM5YrA1EOoGL2Yz4PJMOXj+K4bKJg+PjOGbHA7h1EZvjCaOAg/EmvxdHuqHPVoyGcfuFT8aZMxe4HgkMJceqy2RV3Fu6TG1fKNw/Nu80pgvUNXbYCBBMrnSiLe1cSrqi27BItKdP4Ax3aV0K3CudwZExMJ7Fgg0PYdDwnUiqHKKTBRbOAsm3qGaA4cFky+Vk7SK/Q03oYJRgJNAuMV0t7hP4/dbtLO5SsyOopiuIu/KR8NPpLP7z4kIFxgAAIABJREFUlz+L+fw4BstFzI/2I9BBAi47JISmwyguNDmMw9WLl+L6xRe45rSVszV6DRotCJW/8S/6lzLbasWqXauCNf3vKVDhChAZd+9GfnHG+lo8bF/TMhcjHiOXAS5hOXyS1eVq+SqUcoi9TpP3mWjqGpoBSqOjfV4Jzt5R1VPrr+kuyk8Xu8IQuPIdOTtwCY+f3dMd833m7B4jhWhborZfmdJgtY2nVCfCw92cx/EJ2AASN0WLMVBYQOPBOFYLMLADaTf9Pg8WHWwOJaZW+Tb5JEUFsXCugtcO6+vuPrwXRyeHPPMP4Ih8pyyM5oTBvelDeOzF5mQzzm/tcpOxR3gqJHwc5bicHOzHz//zZ6r7e56fXgRKc15++aPRG47iT7/8VyyPgVXmczh1gp0l4L5Oj6LXa+LJwUEsmx5dPjBPLkVkxPeR4NmLJwdPGAzYGAzi3XuP496jwzg+mUW308Srdy7G7uYodvu9OEDL63kEKhhgMQe9Jna2t+PyhesxGIzj/qOH8bNf/iq6w1Hcvv3x2Dq7x/lgLtQKbh/WRzf6wxFBK0P9ZFVcswISJBvPkYzaiRh1+7E1nrCLKdJYrBKIaWcYlmFz0DKcLgwNbhpLJgSJQSJy3u8Omc4hA4LcuSUTf3UyjRIfR32xHpQ3iUkEDaDmNqN7rZ7DucebGOESoGUZj/aNyYG1Nxowex+ZfwX2dTQ9iO//9Ifx5OBRbECHnaLdTRPj8ShOOqs4WKBfFxKxBVg3r9+K65deJAPOAJXkByG2vAjrfeVsUcuqFr/V+gjMMQ/5xf1m4qulGPIc1wlXspKWbFac6HzTnnvDu95E98X9KnbNA1550vQyxmCm5y+fW9G3tSiW7WW0Oyvgsd3PbgOgwWwfbJdwnS36eg7FCszFGyTCyX1UbymfLFLE0ZWihMjDehbD6gbLPS5evMj+TG0370Ro5bxgoeUxX/g+MJ48vICgCwsU3RgNB2Rgx1Mwh2V0+n0mMo5HaG4nIVAnVCMiJqsEa6pDL9LXFlucTo/j/pP7cYyMfbhdAGe4NKb8/H40oLOLMx6N4/zmbkwm+C7pcCXT2i7AyeF+/OKXPyc7fK6fXsTNm3fizgsvRa8/jq985c+jPxjFdDHjc2A2wBp4astyEbMpipkVMeR66HWiPxrFuZ2N2ID21qzIZLCJkHCK/Kt79x7SxUM2PhjphfPbcf3sRox7q3h43MThohNzsLFuJ7aHo9jb2YkzF67E0XwZ7/76PaZqnDl3Ns5duBDj8eVY4ogzsnGts+Wqx55c0NMIAnCJvNmYrQU9a46optpxI+N+czyOPjL0Oz0259M6dKsgDyC3KBgLQHU2ixmz2NWNAO4nrqNNpJBfs2hogPDsiIDi8zAy+DX0QDG1Jka9nsbG7hZLcrjUsL6Pef3BcCMauI/MZbPw795V621rWldK+2dJ9vnk6HH8+Of/EY/3n8TmqBuj5Tw6yznlhpNmFfvslgHit4rRaBg3r92JS+dhJNBOSkacBiuFdo63mKBeSzdNpUXEEOq7WV6XRdAyZEW5RZK2o54aIYszpZ2QD6UQK/J/VfqE95LapbafHzTz6Fy6LA2rCGfmcjm5GYotlde2DgQsakBqK6tTdKowfAE43ZSkyxR3q2OU0K0R6QtFGHVpDvKwPgRgbW3txvm987Qg8nU1mEtEpZAXhDyfbGNCa4gF715MCBnzFNMVE0+HWJyxIruaNwvqCXBf0FaYve0LQ8SZAQK6vtvA8MkYRNDzYvM8fHQvDmdyu7pgCUy8bAtFcFI1xFF8ZDIEIJyNyXjidieiqLKASm493j+KX77z//1OgHXr1ousuxwMJ/Haa38Vw/FYdW0sj8GfM4IxmOf+9ADN2KPZnzMSDKbQ761i0ExjFN0YDgYss9nZ2YpmqfMjF6tV3N9/HA8fH/BAi+0RQjjT2IxO/PLBw3hwMI8ZynMmw7h64VxcubjHxov9/liJwmB7nSBgT0+6cbgaluHG73UfQ262LMNi5JbnSmKspBs2SFpF0m2/rxrIvgRcqujpVjGNQzLIsCdNCukbs9kyTpwkjCJ0rAk2UcyUA4zUoiGTUjqG9KvxeMwzOpul8sJATnYm49jZHFH/43Fqi3ng+DpReqSKgOWgIQ0MgDqagh1OF4s4PJrFkkEp3LYCUcxJImao2wjef3x8FL949+fxi3vvxnjYiz7qPJdNjDcGLAjfnzYEK7T4QZDl+tXbcfHC9Rh3RrHsqie8JB3IFO4blmPDxZcRO5e4IVIHsHTbl1Snqc+5Dz3ulUnlIJA+eFf0RAigNc2DH52npR3BFkBZwlcafvq0cZ5AO5dKDA0LpTn6yVvQv+Vbpg6VX1j3kDJdzDdn33OXNLQ3WeQ3+fLOcykhT1ueLAyW6A56/gwe0Y3Y2Nxm5wEKwo6KwOIvpouYLTHx7KokdxGAgfQFaA5ELbm1GFypXjLDElWX0e33YnuyEd3BuDTjpw6GSeMEq28VrCUs9HyGrgXIxZEWgZq/o+NDWfByjnl2EmAquwpLu53YGE9id3s3JqOx5F7npsG9YktdAtZB/OqdXxBQn+unFwSr27fvEHg///nXOF5ganjWnIfU/zg2EXFyfMRnHY3HMcB+P9qP+WLK5M7xxpjiLXQjzO6lixc5Jnfv3mf0bjLsxsUNRPRW8d6jo9hfdOnaDQbduHluJy7t7sTm3l5MdjF3Q2plJwgmdBFVHcTxckCjgI0AwIKlRp3hZDgmGGWCL8Y98707cNmAnh01GoQuSDYMBoPAhsJPBE4ACwCpi83GCFsTBwjEuOEfNabBICZklGBvkBCUioNABRkdjFq3R/YCTELEEpsfIHf9wlbs7apVEO5Ra6XHFBH8HhsB44VUEWhh+MHaefD4KP7z7nEsV2BnmUDqSKE0CeW9YQ8tl/Hjn/4o3r3/Towmg+gdzYmHCB7NEMVlAjASc1exs7Ud167cjL1zF2PUGbDVeQlGufkhWGimINlRFAbw1Oj2OC6mCJll0TnLQu5EkSy5cTa/cs6kQ6mUR2fnsMWQ5SY6OqmXJzFg9xIiEP83BBhevLan8qR8U6FtLrZMXT+VsfS//X6Wk/QUU9F1HBFEQlgJ0TM1XAHkPFFXDJtWSYuq7WQAWg+d6cMB1g4Bi9oQRVCVl6AlLxYQF4MBGQOCxY5FwlAuTz4RYPEeqmp0DBMzs/uj6KFzAFkoojuyiGAWjC+xyZ5o/WI658LGvYxGg5jNpvH48AnTGZgc6d7vhYIz677HSOHmxhaZSYrEzDL3PZH3dSIOCVi/pIV/rp9esG/YzZu30ZMnvvj5r8ZwhHo+RUMbRATh4nG+OvHkWM0T+82MC4XPCGMwf0KN6/atG/HRl1+J7e1tr8Mm/uNnP6au9bN334s33nw7rl66GB+5fDZGnYhfwF086ZKBAujObY3i3NmtuHz5epw5czEGwxG//+jwKKaLZUw7GyAkCpLAhScLRkrDMCY4nbo3iOUcjBDsbkn3kXMIdxbg1EVOXSeGwwHdVQAoDruYjEcEHbnpOgCj21NwA+vv8dFhzPH5Dto/R2yMN2JnshHz2TweoGfYcm7QU5tiACnABy4YGB4MLbYkUmNuXtyOc1tdamu4BwYqhkOyU+wVgN6Pf/yjOLd3MfbOnWN/egAWGOqvH0+j2wED1rFjAGc0Q2x7d7nP1GIWP/3Zf8T9+/ejN1jF7PiQlGQ8mcSy34nDxZysDYYUxeVwCffOX4tRD+Vp3qsKx9Llw54oAriokGUcMUMaUoJN1kmK1cCdxY9TnlVnaI1LSZ7Wf70PM8udWQncr85f4bkICEgpj04ZRGhHg/nvRR8tki9fPSsOW1TyditkSr+IhzLPRCNVa4eSGuVxCAWVgp+Zq61vnLqXNKxM4lPSWh4VL0ahScGmRwTkmYDViRhvwDXZMXMLghHcNGzGOQ8J0PNkiDv1pkziyKfNUDF9e+gLXUWlmPMzGDLHhVoXNY0slm3cHQIHFTQxY3thWfjNPjZLo35P0ylBBiI+ReMSGJD+NUaaw3hMHc43Sz2JqREQsaF9DQZkPO++967u5Tl+UCR+587LcfPWrej2RvGFL7xG9wqbHXMzZ/0fdDaF1bGhOQ8LnGyjNAIwiH5zEhuTQVy7di12d3aK9gPGiufbO78Xv3lwL37+y1/QNbuw2UR/2cR/3tuPw6mE6clkGNf2tuLC7k5sn78YG9tn2Pxtf/9ATBVR2lk/nsydv2TAgjPdH0zo2oCgLBsYJAEWghb9GFJ/g9FBH3zoT92eABh5WkhbGQ3xLHDpwCyRXwaXr0eGhTV3NFuq0AQMAaVIk3FsDQcxO57Fk6MT5nUheRW7llZ/uSSTwzWgZ2EM8Bu4Z9uDebz3zk/iwaOHcfnCxXj15Vfi7O4uBXEYgEyc5rZiSo4igaondTE5kmOXi3i0vx/7hyfKDSOg4v761BjvP3xAo/uTn/8k3vn1b2LZ9JnY2www3gJVPC9Y8SsvfZyNFSfuZMte8A6mg2lDxsCz8Lg3BwSyYy4Bie9VSTIBLsT04SLSsHFDAyBP6AWMcLjJZIMEYop8ObJDeBQRo94g+gBpvJ/VEviB3ihAVxcOuY9kZdjPx0sA1p4Byy6gN7giGW3avDTHFoF7bC+LBW5tx2U72vBJ2FKGc8uKFNxd0pK5LBLeXUVP+o2WJCf2+X/7zhyOJ6T+WHyVUWjzMel5ASjgvswZYUMXTKoYChkJIzI/xyHn0QAL3NGUbpcMCYtJi1zjwI6r6Z1bAM7DC7DJIc4iZwqbAeBJwIJ1W63KQodlh4WWEJqZx2ByaD0MN+mEOT3QuCCGv/fr98w4PjxiQYN64c5LgRYzKIv58pf/IgaDDVr5xbztHsl7Rv3f4oRAenAIdxabUlpdc3LExXbz5o3Y29uzwUHUsMdnPH/uXMyOjwk+aE0zO3xCEfidR4/jGOVgTRPDUT8ubI/j8plzcfbC5RjvnGH3B4zvdDaPGZjW8SCO2bZb9Yh0CLnA4c47zQOACneH7hQYFhY4jAo+hzlSQCRTFHD/FDwQQOjpumAuTCUIuKFoy93EFJ4BKwsQhBnGZNCL+RSR5TmvtbExijPbI0YGcXWAIMZxPkeyM4IYy1guZvH40Xvxwx//IPYPD+P8pQvx6U9+Ml64fjO2N7fUg8xIweBTEbltr1Lgpp46j8eQFpg7eEyZ4dzuDsEU6/fB/Qdx8cy5+NEvfhW/evcuQRfMCnl1AAi4rfsHB5QAPvHq78fVi2BYkk/0o4Tt2cksnjw5Zq7XcCwvhLrucGR9mLuLUoVcNPfGQjIvCYxeRV7dvV+/G7/61S/o4g/HSJTeoTuPqGuviyjvMm5dvxTbm5OYTRFFP7GKNYqmI3efqSaWjig9IlXo8X50Ll29mAKWNm6WlRTO5Vwm61s0ALhJV1qnxqVPeiIyZcyp+ZmrlKJbshklBqrDqBIAFZ1gHtYxXMK1W3v/DoVIu7EZZ3Z3OagMOTMC+P+T9t7Pll7Xldi6Oef0curXAaEJMICgKAaRo5Fsj6Ys/2f+zWWrZsb2jKpmNJZke2ZcVhi55KEogiABNEIDHV/O4eacXGvt8913mwSJBnyrutDoF+53v++cffZee621TQZjym9bfEzHWVkQqOQiVzk456jgiZYl0xEuEdGC5WsgKxie5D4LLpKiWFYl0NoZHTIgRcImPeHvr9frcgll4CHwz1NeHS1uXJYHvCb3d530jl9jm4i+eCN0+1aqsWwcdDuSw9zgYS8XtBiw1tc3sbKygmg0iR//+J/pOhmslC3ILoXGfIbVsBPGcrrR7qmJEQpPEVbCY9gBh35srK2rZFY2D+Dk/ASZTAa1Wh3n5xdGOCUOOB7h4PQCLZZLbGLE46hkk6jksuoIxlIZdZ66Ipdal6rbpaI/oudEcJsZicirBP/9AWO3E3eiDpIDTDVai8OqbP14pFGzm6ZZIDNvBwHQL40ZCMslF7BU0o+naPWIXZIvZ4oPBj02adhVYAeRjqrlQgKlNAMWD6yxNj+ziVGng4EP8ren5pMGlMFoRPwyzmbkGi3ni8il0ojEohiPSB+h0sLcRPk5mEkzk2UGZV5uNhCaDY02/fdbTWOWM8wQYmCjCQT9Y8IBeU+k5hB3zbIzlqmPdp7h+LqO+6+9iUKWBw2/bjis8SR9Kptbra7NmHQj86hs0Fq2bgH8QTYRSPGZNziwRoB3gI/HQ9Svz9Fq1FTambh9avIuHnrMjId9LK+uCFIISqJF+ISYcRy9sU+fl1njjbDdyuVeowdfZbkoFOyG7Okh+S7DmrHEvUzKIpmVWOw0eKC854vlNHseDViB+cZq2EaFW9p7I2Cdnz7CzIKnnSsJvWzt8/amukoJpDNpZ2dyo9yfZU7u5wJBlncsN+wUtIzRmZ+5zyDcSLgSyYAhl/U4fMuJkok5mc1xwMiDrpVtHSd7wNZ5sTKDAYslFsuJSDBsmcpcVsXv8zIs6wjavWXgZYZGEqvKWoqf220z7/tt9+Rz7hNpCevrWyiXFxCJpfD97/0TXb+6oQzGzufepgoR5zM8sTegpQzxK5ZjLLWHciRdW13F8uKSHEpFLxhPcHVdVRnG62MZ7B/3MOm1EQ77cHTVQG9k9ABie5VUHOVMBvFiAQkaCVIO1B2oU2it/xD6BJ2nAWVfOtRYfgWJFwV1X/kc+LyY+TDTYlkrcqUCFks/e05k18cjRlexZ8WsyoBw8v1Unk0sCyduxX8bKPVm6TWGHTEBlfu+wASJSAAhH0v1OvYPnutQ4gCP7//u97BUXlB5c3Z6hlqthrt374kucnV9rYMyl8mhmEkgFKCuso9Wp4OHO4dSFiyVFzVQdvdgH6FwFFtrK8hQwhUKIZHKIZXO47p6jVq9jmw2h0ariXQ2p3IrHAwIFuEwYX5m8tGajbrud7PTQbXWRKc/wuLKFuJxdnanrtKwZpAUBEEbGsLPH/DbiLlwhKRpZr8MrFNRYkLBmLLsGRXKOSuYXpeyrQka9UsFV8vI7J6LR+aPqGwPh/1YWljU36PCegc4u7hAvrSE8TQCjnJQZe6werbNRqMBKE3zlZdLU8psFM/FY7sB0ZRxuYEIVm45muRsw5glxA1nw9J4+/+bzqA37l2Kqdm4bcOZDGw3WY4nglZJyID1BQmWukHRKFJJRmp2gdxIeqb8LlvjDrDfbeWDl50YM91hEl5Zp7HdTIUpUzGSJx+YzeRj25vumlYWMsjws3stcW4iZkZGbLQL936GC4gYh8awu5Ne0hz3e6n9Im5g5YthhLwHXsbZJVO830OrafKeLx2wghawSqUy4sk0vvvdHyESimpaC4NOn9mPn4HKfNbZWueJRgJtYOJTphBkN2fUQzjgx53t29je2jIxtGtY7OztIZPN4ODwAAckgfr7iPtCiEd92D06Q3sQUBGRTsWxUMyiWMyjXCwhETdn0S7LQWV3I/TGYXT7U/Tdye4tJ2ZZsQj54SbTodiXID3Jnhpk6rILMue9TIsdPCoYmLWwFCQAzU0pjM4x2tVZZBeKnmqOF8Xv1UxDvhfLP/o4kX3ATCgwQDLux87zp+CzOb++xne++W3k0ykFi+OTI+we7ar8Zld0Mh7oHmdSBaRTaR301eqZpgwxg726qqFYLGvLtFo9UV0ISWgoCMnN/imSSQYaoNntO83IREExmUwhm84rE80Wy1jIlZXdHR8f4ejiDAgFkI4nRMDM5njIRJQtsvRncNecFx5UrpPqGpGuccQD1ioCDRcOhxEM0nffAqO6dXKHEEFL15xKRHB1cYp6o274oKtmVJBrnoJliFubWwLbCa43WnUcnu1jcWUTg2HAcDzx7Bx/y1UyjasqfAvLZYUQY09YUeh1BwSyK3J6pZ62odveN7wKT+hsYPychYrAeCdLcIxyRV1hDU75r5LQ+FBet4abtauJLl9Q8viAVDylUkQBQ7QFO1n5u7i5lbH8in2M0QbcieuExuIy63TmwwgpuLAk1IMShjVSwBLXR2WKDZRQCi4HUWJUDG4BB2Ybx4k/IwxrOkFMLW3LVJmJMWDxe/gySYfn6eV85F2rm5uChEwa91mL+Mu9/LSSXtlEuVJBOJbE22/9QM0DPnXaH3eJebDbxa4aGwejvjZ0t9tXZheJsCHADT1BMZ9VhsVgw9PfSpAJWgTN/X7hLQ2WwrVLTLsdeWUd1moYTNhsYJMkgrWlReRSKW3CuKgHfuNhyUuLZT3QHQTQJwiuk9ljhTMT8it4WlfXrTwGI9oxM3slHYN8JGeGYjw5Y5Qzk1THzWEkLCNv4FZPhDvT0VtLXfiJ2wd+P2IhgurU6xGzsobK8empmPvrS8uYMhMYEruJ6vAb9kl9MPoKDyaC7gyeolUQJggE0R309D6tehVXl+dIxRIq94jZMRuRqDlALChhVj8+Ts8m3tjT71ZWSnvpUEi4LwN2rVZVFqVMnc2bCW2sUxiP7T5JSeD88fU9ZOmzSmCjieuO/ukOUrGKwa9Dl+s0kYxioZCU0FwxaAyc1ZoYToJYX1hEt1FXECItSD5p7EKzY691T3zMj5XlDY0L4RPjejm9PEIiU8TpedvmOfI9HSQkZsV0jKuLK4LuZdE9xOZ1jHfLpjyXwBdtJjxA3SOUWqZiuduNaZ0tg5sRiCY1MB/1G/9qclksbXQeR6zcefINLGC5bvZv3p0+IJ1II5FIzMiUXsCyDGcsZjQDicoEmc9bOcpFQK0cFzEfoOxe+VAJNIYMh7Kamw/JpjrbOHqbw0eeGF+el5YwPLeRvCDE9+JiIJBsEg7b4F4ZOB+wvKyMp7mcOykWZvueZU+3q9JStjJfshxUMAz6sbG6iXyxiP5kilfuvql0n40Ab0ZkLBZHIkmXT6DtTP3CQ7sf0VhIBMvRoIdEPIbtW1tYWyXp09m/TKfY291TWfJ49zkePnuCO4vLCJDsN51i9+QE/ZFhfolEHOV8BoVsBotLSxr9RYmKGgwacBFELJLFaOTD4fEx6s0WKqUK8vmiApfn3CAmvsM+tdZmMAUbFuZcaYJ475B1xOW5gSCeHtDwTMM6jcZ489JmUSOIQ2b5eUyuFvBzQMcJ6vUGLq+v8Mb9+yir6WABjGB3JpvF0fGx1k8+lxNmyCySFjp09/3Ju/+I5mSIVq+DjcVljDsdHB8dm3e6m0nAyVDsyBbzOYRCEZxeXKDd6yKdzWB9YQGZWAaRaESj7lKxFEL+MPq9rg43JirMzNvM0HsjjKa09fErGPJ5EwTnOqvWTPMqhlLAj2Q8hqQyX+vi9bpdNazM8mWMSimD1XwMocAYvTH1jFXU21ME4jEslcuY0CdtQGG70TpIQVE2R79a14VcrKwgxg6rf4pms44aS9hQAs93j9DTFCYnbGfXNRpBLBRB9fIKvsWlyozbbsFIqLoLQIap2OQcp9r2psfqlHASGOXszk7GjWOyIOZkkuoCehR7tj7dwEnPlF8j4D0+DIFLGzU141r+ppBFRrELWJ4gmEGAn8MY7D5xm6x841Iky92kDeRItdstfY1MZXV6KJx1BD+nHnPcF3JrnK/TmJwl6wh57+mBvLY1TG4k4ocr/wRkU98Wjsy4aWR0Gz5gpZ82oMu2+O/8w5SZm5gBi7jQl6UzeLeN+NPm+pYUAQROX3/tG3rMjVYH9XZXCzidTiEVTygbJUifziawXMwj5vOhO+wrA+j0OigWCygVSwKRiVl5Uhc6N5AaUW80tKFG9J3vV5XNHFVbGI4t+EejYSzmU8inksgUyuoS8qCzYGwZayiQE0P7+OQQl9dnopWUiguaqmOHqfQvaLVJzG0iGKaY2IdYJKOsjyWm9I4Btu1dABI73MNnp+q4nV2dqetKH69cJo9KsaTNSWxPgPhkJPF7NJaUWFyguDzSJkhnwmh3W2i027ioXuG1jU25sQ76AzTbLfRJr/D7cX19jVw2p9mO7LoSM2SWFIzG8PDTx0jEs6jWT7G6UMHh/iHOqg1lSSKnDkhHCCOVSWGhXEQsFtWUbv6e9Y01bK+ua10qaw+EEAnF1IXkemk0GsLPWp02mo2maXNlWTNFJJHG4tKa5GYMoB89+QxH50eiSTCr2l7fwCtbt1Vz8bk8+ORDXNZqKsET6TjeuLOBzQLxsyn2jo7w8Nkhpv4QiqU8Xru1ChLRtY58PlTrxm0bsPYMhBDW4NUJ0rEElpfYuPGh3+0iEompHN7ZP8LR+akwOjW0An7RQkqFIq7PlWHlNWfXMgRv/JTns+5ZttjSZzfDcxL1MiwLcd6ZZJiWcKwZhmUlpuEQdsO8ssYM/Iwr4rHc7TTjVBUzgvutL5dhidmr05ngoYG7xpKeIhFPIEoBrPC3WQGghcMHSxCYaSrTVr6hyl8Z1RleYSx1dmvYQbKyM+hn6u3JWty9AWf7sS1vAcgkHW40pWNWk5qgSc5cNI6/Y7gViYRWPqoj6FEqQHypr6ETdJjwsrAvVxDa/MbN1U0NnogkEnj727+rcsO4Y5S9DPkQhEkpEwxHkExEsb5UQSIYxFm9ikabbfWernFrcxP37ty1zNMlrbt7uwKDP3v0GQ6PTxCPBlBORgWs7p5eoa9BtlMkElGUkzGUMlnkl1YQz2R06jOrVmkyGaNabWGgAdM2qJXZEoFoUo95mJHIyXJn0OshHPEhl8+py9XpDhGLkR82QqPbQTAYVzs/Fo8hFqVUh13ZkTZaKOhHq9HAxcW5dJ/MNknV8PtDwpRCIR/yuYSaDlwHmXRKRNRGvTmzwWaGTvD+9OxM9yQRC5uL63iKUMTa981WW8GXPCgSY1kapTJpBNXm92HY76qDxiqAY9zU+BHtwuMkOneI8RD5bE4sdj4jdr0JMVjp60cgGraZgtOgDvurahW9flc0E4rLaYddrdaYQqFUWkS5sqJSkdf2aP+5+FweDru+vIJ7G9uCRngI7R7tKFPjQJb7t28jrQzTjARanZ66wCzjKuU8KvncWjQMAAAgAElEQVScnjmxQvHTxPUzkXij00K+UMCgR5F8D+lUViJ1rndicdfXDRyfnKlxMxh2Fdg57JfSuFQijUuVhIvsEpo/D7MA2Z64+OOVL/OTXG3TMyjMOxDyekxTOBu+MDPS86bv3vBNPODbA5XFtXEgORcUN0V3IFXub39R/BxL6SF6guD5bMe6Ulbe2YQczyNpqLHs/W5PAY7AKL28dYKqpc3YauUS8TUuAqNMMAPjM3fkQt4vEknZMRzx1O6hxxa3ykjXAHA4FD9ITC1hYl43J72Hg1m3zfAsXjMzMN4nYlcEVykzeqFW+RJRKxD2Y2v1ljZ2KlvA7/3wD3Va8xkyQ2m16uh0BgoaxArYJmdXhtdELIbBNcqFFfapwbG8smyC81nJNcX1dVXkV26UarOBXuMaEUlDhji6JEvc5EWJWASrhSxSSWZYFSQzOX02Hh426XeKn77zjjZ6fzKUQ0MkahQQgsdRdpoUyIcqzXmvbm2sivZweHqKQIj3bYijkxOB6uzMERMqZFNYXVoQRszyhpuEG3h3d0/iYX9ghGK+gHA4KmNBttz5hxkTsxCT4Jjwmx0/rrOTkxNlY4dnp/jx936gIKcRacQs+33xn3go8Fkyi+dz5DPOForoDXt476MHCDPTDsZwb3UVn336EI92n2u2o3lf8fAKYrW4gLWlZd2Dz54/xVWthoXFBWytrqPWaijg/953vycjSt4H/lyt2tDnZIZ0cHKCepvXMhZmJvb/ZKLDotnlBKYQxsGIupatdkcuF5lEQuTpdCKFTColDqHoQoMxatdVxBNR9PpNNXK65NwNBjLB5D7gMzES+MikxrS/DkYxGA2QzhACGOBg90CyNe4nUpOWS0to1nnNPXVrM+k48ukYhj3ex4GSiLOzCwtYwq80WcGcB01gaVKaGVYlLsP8LnGaQv2bNynHU8Ib7jWfZSmTcNactIrwOoNqCOjtPB6WdePYhn25gBVHNJpQsDBfLq/zaAB6LBo3esKvSYucTstRGTyjPmYe/FZeA9n2vG5P3S7ekrJQN5ZbjGN+LnKMrANizgLGA5N/1IiMcTOEI1WCgPFNg8GwQ+/l/d1jYPP/uZHZOjcR7JeIUnPfyoB1d+ueFguJtm99+7sCgFk2SREwGOD6+kr+WDzdiXnpPk4DSEbiyGWSSMaZpU5AV4n1tTWUK2UEFeits7y3d6Bsdm9/H2fXlwgx+I7JjA/j8OIawzGn8kyRiodRiEdVIlUWVxFjmRcAhoOxqRuYke3sCuBl0OIbkLZCUfiAJFplUh00uyyRJ0ilkrizdVuf4fziAoOpH9lEFAfHR+p2Li8sangGAzTLJ0IBlH7FYkkFJHbTLutVDMZ93Nu+jUQ4ilAkimAgItyQDR1irwxSLMs1k3Ew0LVxzbBkrNbr8poiX4lW0K1e10qvSETPj4A8/75/cCAjlmQyrc/Q7jJrZqk8RiwURPWqhna/Kz8rZikeCZsZbyGTVSf35PxMWV6pVMJCsSBaA7MtlunlUgWhIOENdhCryviZyRydHuKqfi0CLrO5TDKBQq6on2Fm3Wq2hY+xhDw9v1CZGyCOG6XBZBDRUBzhSQDHFxeoNq8wmjDwVJRdvrJ9G/VaFfV6TXI4Khzy+bwwMp7LlATRqmjI+z6l4WYJ4z7pKxNhb+NJH5e1Kq6urtHvDRXYI9EgaHxJl96lxUXRGXjvj8/PiGGVtA0saJnJv9JM+1e37L2MwM31cxnZjR+WlYGGZ3vOC8Zcn/8Vxsdw/kWwAZyygZW9h212nuo8OdnmfrmAldIJpnLKAeeeLpEALkF3yTQcnsQPxL97XTqmtTZa3jZpgDoW1e59E/Y6GgMzI2VAGjTghm/6COpz6pWV04avqAthwC/Idxmi3WELnrpEr6tGHpHrkcxRLTwSn6yARScJGODeaTkz/pnx65eKXP6QD3c27yCTySKVzuK73/shAmHDzySsHg1weXmlTUj/KEbsFjkvPiCfyqCYTSGdSupZxuJxEVAL+eyMh8XH/Pz5rkrO46sLNJttDFsNBCcd8aV2zi4xmJgFSzoewmouhWwmi2xpCfF0RuV5v9efdXdPT08Q8fnRaLVx3ayrjOIGYLeSQY2ZS6NLPGgo4LZUKCjgNbhpQOF1WKaB4XgMX3/lNeTjCfgDNmuR95i/Y2f/EIPhGPEYN1RQWWU4GsHS4gr8fmu4EOQnC43vNxpzLYyV8XC9sSv67PlTBbXrag2v3bsrEmlv0FeTJRmjYJwKgLHZ0ISCWlO1dhPd/khC91BgKsCeWUqYQHgkKmIt1RCSonhOGsOhMvdQNKIDhc+MG1s4nY/23tZ9Nlujsa5pd3cHZ5dniCU5jTsgSIGBn8+Q+B0pHMUssyPKxjoKLFzXbDixTPSHQ1hdXUc2lUan3kCz2UAgxs4ny3JimnSaGOP68gq7eyeiMZCMS/b60tKyrL4IszPYkvvFZ0KHXWZ5bR5EdOCgfpO8vcFAzhr5bAH1Wgudbg+9fhvDdgNLCyU1EHhoM6P1LbFL6HUHPTMv4VmWlHjWHZ5zw0zcrNlhN6XNLGApQnmehDeiR5V/0gtau/KGj+VZm9yIUAf90UuXhExd4/GUEeGUrU2M4asy0zp7fDgCiF3306MqeJ1RC2bGxfECDoOTgM8pcSuyca2c8xwdDNPj/D1KU5jNTY3k50B+BmduIgbSeqOLzsCnet2jQXDhs2zkgjaczIarspwx5rAxoInZWEPYnR/zA3tfMmwxYN3duoN0JodMvogf/uCfwB8MokUnCXk7jcVQZ2dnc3VVZd/7n36MbneIYjqJQpYlnDkfcBOT1rC4WFHAUjdrPMbu/pFwvwue0CQNDtvIp9hGH+Pw8hLDkR12yXgE+URUG6GyvI54Jq3gzsNq0OuryfBf3v8leoMhkpEIMsmUssFwMKKf5XtyY11cX2Pv5EjBhGXZeGg6TzYMwrEwBsOJrvtbd++ilCVOZg4c8jvrdfFkdx9n9Qa6wyFC9G/SBKYFfO31NxDyBZHLFREJx7WWu72Onk23T8wziHQiKU7cL977Jc7Pr5RpUhCuxgSJmvWaykJ+Xmb+pG8wU7u4uFDJ3Oz3kcsXUKteq4nBLIhBiDhmvU451FRcqnanhWg0hHIug43VVa2Vh88e47rVwltvvilgXCTncFiHheCNYEgNg08ffYYPHz3EOBLG1uYtlDIFpBk4IlHdr2gkrqC7uLSIAJ0danVlylJfsNrx+5FIZDHl5+40VbpSMK/mUN80hNwL7334ALvHR1rrvEdc06FoCNEIcWAqVmgTNcZSLq/SsTMeoEDGf3ZBB+VnT5+g3e4IRyZRl5+HUp7RmBwuH26tlVG9Osfp9TWOd3fhW14tGadAL0/jZJnSTbnizKsFnolpNSt5bHqIVxK6/rJGHJkFhn23Kw9dN8ysbT3xs2FhMwLpdCIeSZeo60tgWMI1ojG1bsl49kSlnn2ugpKjHIinxda1p0qX06i1v61MuxkFwPLVfoeJZEvFvDZMp9/TqcTSkScVAWCbqcbyIK6OEE8/meEJqKd17wRNygpMPDIzniNOZEGRvBdaQpOfdpOVemPtZz5BPAu+YsBi2cRZhLF0Bt966wfaoINRR1QFUQq6HJ46UmAif4ZOqWwaRMNRqfsZtInv5At5rC2volIpWobl2v57+0fSuz3d3UWj00bE78fGYkmp/d7JufkyTadIJ8JYyKZ0n0oLS4glkuog2Yw+loYD/P0vfi4zvLXFdRRSBuJeXZ3LFqY7jcEfKQO+MDAZIBakGwJxpoDKm2mAp3oY3QH9lUJIx/yoZCOIhYmJmuiZ16fVHggqG2GQYSCLJeLa1MxWKNpNJFjKRa3EiScEmvNA4eZilnB+ca4skB3SeCSMEXWjbAiMh9rcXAcMqAwSzCyePd/F+cUVBhMfSpUCMsm4sq+z62uUS0uolLLoNdtoU2gtFwpyo2zSejaV1O+i2SHvI+187m5uotttodPr6zDaXN/E9vqm1tDZ+QUOT47RIvBOqGLkw6DXQSZDomkW5UIB/gm7nSlZ7ChB0YFqncGTk1MB9XTCoJElgxsbQFcX1zpgSDFoDxqajBQOsyGRQL/XRm9IfI9ZbN9sv2MpgfPpZBL9XkcVA6ksC4tL2vfvvPuudI/FXBrZdNLoVHLgzWLQ6Wo2wNHpMar1BnYfP4GvsmI8LGePc8PHciHMkzreHObeuCp2lGzWnKUulpJJ7jJzF/Wsu7wpvfZ1C1CODa7U0EBAb/Eb6P5yJWE6wVIhonKGZpMWZHRBM52Ux5FSseaY+x4pzWRGnkmeBdx5rhkDUTwcwerygrpALEPa/R6ZGWg0Wg7nskwumUghGeOACcpHRhixzKHkIxREq83BsHQYdaOYqJ+jGLvnEWaH4BJljFbpQghoYHo3bS7ys3jfhl8eyCKGtb1+G6k0dWwxvPL6fYHWXIDiiDlTOOIPxjC2qTHE7tgoICmTej+2zDdXVrG6sopKmaaJ9sy52avXDZUoOzu74rdNxi2EOfXZN8V5k104DsgFkuEgFjIppHNZ5MpLiCWT1rRwtA5mJg8fPhQmuFBZQjKekBXz3vE+rlsDjMPLiCRW0R360OzTbz6O8TSE+jSIUXcon6dINKXOkn8yQM5Xxa1yAN+8vYCYnw0d0haGcsigBQ03E733GwSlhyRc1hSAqKcTsTUeV+bIDciMxzp6hmHyedTbbdErNJ9AE677OnzWyVOTE4S9J0u6fn8EBvZqrYF8Poc7d+4gGPbj6PQU0VgCt1bWUCnkFexlyzw1HSvxRdogJzNpVOi2EI0K/CbIP2FH0Zu/EAygnFvS85BEqkc+VE9uDzzIWa5yOcUiEaxUFjAadIV7jad9lbrsCrIj2+70cXZ+qUDOpkeQmtqJDxeX5zg9O8GA3LrdfXQHXXEgFyoLWFreRDAaxbvv/ATtVl3OIsQMycL3aFIJqg7icSUXzDhF12k2sbS0pCyVOG0ikZZygSGkW2uoIXR+xS5nDwfPd+BbWi6b24lXA867LTiSpbSAM9mIeS1rCuwMLzbehXUJ+ZqzO3XDID0GvLlnOpBdDHdnUcugJYN9Lg6SCJltfEHNIx6WjcxiQGBr+sVA5MiwbsiA0ljPk+pmMNesOXBDhn2xwSDOSKmMgOYMTUF7HlIM+WlHMi002wyKRUmmVEbnyIgTv3lXT0ZMcf3yDaeVMO9Hr0cPqIG6JzxNvaxQd5AZh+geVgqa0JhOql+Udv76PWPAurW2jWQ6BX8sitv3XlH5wc06HPakWQwSn4pGUCqWlVl4wxzsflKMCnW+iMxtbqzj3t3biETNDoct+P29I/mSvffB+7i+qiMWDyIVlUkVdk/qYNOXnyWXjGIxG0cml0OuuAg6xlIFINJuIKg2+NOnz5TVkJsjMHjYx9O9HeyfnMEXyaFUWpKgttai0d1QGcsoXEIqnsZo0AY4ZAEBpKJjFKOc2tNFLpvBvdUSokHDcJ7t7qDebIKuIyy/iB3xml6794pAbJGLKXqmqaDPaCiaSOMDzi7O1HKvFCsuU2N3cIijoyM8fvRImkBPvuWV+gx4VA7QXYFBcTj1i7eUyeUFokdTUWO7NxvyzmK3VoHTH0Axm0eSDrESEo80Jm5zexMfffiRMDu6Z3znm99AIhpBKh5HvdFErdmWeqHTaSpjoiSIlUit1gAHkL9+exvXlxcqz1iqshG2uLioYEyMiuWn3x+RM2qjUZMmk2618vKaTPDp40fYOz7C/skR7mzdwcrypsrCBw9+jrPzI63/RDKpSkQWSyLeGqYVCYVVKnP9MXBtb2wil8ogl0ghHaYxJPXmQx18B+cXeHJyLA7Z4c4OfMsrFUETM78r1/WzfWIsD9vohu8IXJ6Vi44+7zywva3iqfqV52jHmQzHsh5r1+rf3dRnsVpnAYsl1BA9rfAvDlh0BCV2xFOBZYRnH2AM/Jsup8u5TIKk9ufNUM35N5pllN57+3zIxOMo5jJi7or2wXa+I4kxPWYwIZYlMx35BNk0OY134hu7riIXkLpClOwMJ6g1OyoVmel4OJXHtVKJ7MV9ZjvkijEb/SoBKxTA1tqWFlAgFsH2q68J6OYJ2m3TTcLmMzJI/MEPfx/Li8sIBmKYTgLod1uYTHoi9+0e7OD49FiZFPWEG+travXzs9ZqTTSaLTzf2RGxMRL2I2tcW5zWW4CfBMy4JgMRy6Acg9ewvLyE29u3RIkwQHeAZ8+eIZ3OIpvNasH3hyM823+Os7Mzmd3xWZCSUGs2RKFgFpJJZ1ApFtWRm1n4MLsF0BkMkcpUcGvjlixoqtUL/OMv3sHB8bnWJPlByyuLuLd9C4V8ASPSUzqWBTAr5b3hpmPJq4YQx5OFgwLs6aLBe8CA9/TJY1xdXupQJMDNWQCy8BmNFBQajSauLq9Qqzf0te99//vojYZ48PEniNKYkOoKH73r7cET22Jmw25mIZOWTzzXNKk3yXQal9eXCERDoqvc4mzO8QCJSAinp2c4Oj2XJU/1+krMd95PltQE/RkQwywD/X49BwLmzO5LOYLyPHjDuHfnHsq5LGq1a8QTNFtcRSySchis4ZYMOv/Hf/oPaElHG9J8BeJn1csz4aF8fjYH1Fx3FTqc9lQd4RGNDc0wgIA8S8LVfA452pL7/SpJPz44QbM3RCISw5Mnj5hhmR+Wj8ZZTmaj/zpaguKX9w+zlp+VhTcvFx30vZojPkcmtbmDVnYZo31GHHVAvNnLeGO/CDiyK/ISAUsjo4zJbhjWkESCX5t/JuKq64TaVNxf0bfYh5xljF4w9T5fKh5RCSGmdijiSHMj9PpDbd5cNqkFq4A8pgbS8DdazrIMlGCZOjqVIaQNcCP75apJ7stVtaYpyyb2NjyPp7vHvdUQCLW6v1rAYuPz9sa2AgZCIWy98qpKsC7bxXTZZKkwmYjU99/9N3+MSmnZqQXM9gXjBq6qlzg8PcTB4aEy2crCAm5vbmGlVFHqv7O7q2zk0ZOnqFU7yKbjiMVtHNjO6Rny+RI2VpdRSKfF2To6OhYbe3GhjHt3blswVUbQxc7ermQ+LDdIWeEh8OjZE5ycnSBKLVssanwoOoE2auoIstS4vbWNP/qv/xkyju2tTh+gTfn8+Y42RjaXR7PRwNX1pQI1194h2/X1OhYWKnjt7itIROKi/HolId0YPvn0oTnI0tur00MgGkV/QF+zINY3VvCtN97Af/6rv0a1WlUZUyjk9X7MED0tKtURx8fHuCBjO5rArddfw+tf+zYWFlcRifJ+xTW5STm6IzJLSqOygEfpAN3WFa7PjpTtTgMR3SPiTkFfAP2hBYZa81pcLR4Mx0cH8I+nyKUziKWSeLSzi8Fgiju311GtXaPb6eD8/Fzk2u2tW1gqVdSxzWYzWFteRiTkR6NRFdmVz4IWQUouJqwQBvjXf/qnqPd6cgjm9VMBwWng1eqV62z7lHnzIHhlextPnz6RPREpR4beGCvhun6N57u7ysJoS50jHcIHXNaauGi0sZgv4PGnn8K3srogqNlyKTdEXdnUvMjZ0R6cSdd83vOiw+gsx5rx32czxFznUIFQIlRmQ1anG0HT2bgwcpNS8JIBixs5HIiYfzu7aXNZ2XyC5gUpa4hawPq1wOWC8wzDctlUgsRJkheDDrDXMMux8Bl2rxiIjKtFlvNQn81GSdnlyMKFViaiTrCjaCJtIWfkFXECMx0E3HVx0xJ4ZQliCZoj9rJE/HJmo/bzIZ+Io8xixsEQtu7et/Z2t44+/cEoTB2PkcsV8Ed/+M+RiGcE7hN/Y/C2gRpnOD7dw/XluTJHYjB0GNUMw2hUpNpqzfy/CE4zUKXJdJ9yXBhlN36VKDLPG5Fj1RJRdXl5EaurK+JJMUhzbVDOQh0erYOYYXHc2OOdp8qqmD0l41HDEjttdaHIGwpH49jevoWtjQ2sr62jmMs7AfwQtauqyiu6SbBDtn+wj48/eYjL66rWID8fqQrkdH3rW2+p/GL2mctmUSwVlV09fvZUzHh5qlOCw8xpPNKeqSwv4vb2bfzt3/wVOs2WKCERNivIM2LnjFbb0Ziw2v39fZycnGEcCKrpsP3KK6hUVgXyky9G/JAYUSZTkG22hquREyfcxkz5Bs0W6o0zZSGFfMZlPaSnDNHvtpXdNJoNHB8fKDAzo48Hw3pGPDzrnR4oiE8kYsqUeB/DsajY/JQDcfjt3e1bGnpLXha5U6/ffcUkbnba4umzZ8LEfvrTn+O8boRY+eBTaWKbHEPCDuOxsMjf/9HvS17DcpjrzDe1yT225H2SYL3/4bu4vLy0eQ4cw0cdoyRzHDwSxuXuM/hWVpZ+Y+Gl9M3tOsO4nPGdshQbqWgvl0F5gIvDh4wiYLPORHXwuoSerlCByhapJ4Ph32kt81IYlkvsZOrvacycls+Rwub5orM4a4HqZgz9LIm0MOY4aVYcejYzQecRJGY6u3rO84RBjINDzXHCuqGmzjerFjYmeEqRxMgzwGRAEynWNfuOzpbUgUUDCIaNqXx0donDk5pwBF6r8b4c1veFo4TmjxP3dIJ+bK5tIhqLAqEo1m/fxxjECGoCR61M9qNUqOBH3/2xgayOr8YSZToZolmv4uh0B+1WQ2PSGIT9HKHl5wzGoXhEmWRa5QpJgFUauOnLNKVzMhQ2UlxQ5nNmkF9cKuPObY4fC1p2otKrLW5SNEzCZ0q0ApajDCJrK6soFAo4PT3F853njoVutjFbt25jZXkdhXIJb735NX2uq8sLPN99rgyA3C8SX49PT/D46TN5MDHbG/SmSMbiyBZT+J23v4NoNCige3GhgoXyEq6rVfz8F+9i72BPgYjaNpJUL6ukL/Txwx/8Hl5/9S7+8i//QuRWlqWU+ZjXu40BY7C4qNbw7BnL6nMdWBsbG8gXcrj/+us6BFgCMWNiwDf8jvwq6kptzdjanOD66sI6b6m0OE5kh3e7PVRrVRydnQpv4nDXy4sLk3nRkodkaNJwAmwIWSBhKdtuN0RpIC2IjQb60VPC9Ordu2qu9J2RZqVcce4N5vDxH//6/0YqmcZHH36otcAsmB1Pa2wZ5ZwSNjLrSbVIppJYXdvAq/fesCpFNCGe39aEYrn88LOPcHSyh1qz5iRu3NR0j4hqCMnp8ycsCRdmcwkdbXnmaOAtfcO3jF9100HzxMyuftRQSxf7nGGfpXtuFqGrb2Topa9b59BM/OyiFdBohdEnH+flUwmWTFYbW9T6bdCXR2OwAOd51FuQYgYwP0xSn1uj0WyaLzcfF8KMqOdyInbRbC7eSBqxZJKNAJOZqIwjL8ybsiNB7g3J1CPsyl1AXmJjeXNXm5wNaMHPcx2Qtc0XmoT9esCi+Hl9ZcPIhtEo1m6/isG4pzY3T2WC+1z45N1k0xn5mDOzYRAxrp3rZOrEJAZ0rcDCrKiQzcl9YGllRXIbNhY+e/xI2Uw2k5ITwHg4QafZwfnZubKPXnco4LhYpNNASJq3arMuciNdOuKRqNrvi5VFpBMZXF1f4ODoUKuJkiAOm7i8vJDQmhlDNB7Tc+KmuOs0jpVKxXBSOkTEE7Jmsek1kCMCS9t2tylaAJsefMbxeAZfu/8mcqmcgjsdFtgJPTo9wfuffIJTcq7CYayXl3C0f6TuZSwRw/37r2FloYy/+7u/Q5XBulaVwwKDD+89nz8zTnYCj07OsH90pC7m/fuvY3trUxhVs17XMInFhUU1sJipkAhLhj2BajlTOLtoQhH1ak14HtfLLstx8ba6EhgzMJSKBQy75u7BPcZAJPoOfeFGY/HPJhihUb+WbYuqHPH9JsKTWB6uLC6h0+qi1mgpCPPayPd68uSJMiFGwdOLc1VEDHbm+W6UBjL+yaTPZzMK3IFAWNgdu52kYDDjFHTkOIBNcrQ6LWVqGrgxs3ri8JC0/Pw/ff9D+BaXK1KVyINGnT5b8DZUwsoa8ipmG9wNoOCFyXVQjAY308xGcbjAZpobciYsQJnC3sb8mKDUuFfMPMxTyRNCa+T4ywYsz7vb2cL+pmDlkT49w39dt7vaGd3ByY9M4+gsS1z2QSMzicPdqDAuZPk/8ASTEd9U4LBH19Dp4abYaihnkI6WZvDHE9PuJ83g6HNtHVc+JNIzGLDafSNcGePduGSif7x8HJ9FrkDIj5WFVeE8iWwWy5u3reXdZYnDVN4nYDUUTyNGHya/TRKyVTDSFGOaX3hOrJpUM50aV0l/kihXFtSJ42dj+UQzN5ZvtFlhpnlyfIrj4xN15HhvWWYwKEjc3Wwa5kIGtbAa01OS81XIEGCv4vjiXAeCzm7pXc3vjNdCYqmkN+EwlldWtJHpRU5ch5wyirozHHqRSutnDg8P8PGjh7imRpPmWyMbJhFPRrFKakGewQ7CoZa4aXsdBUxiOxKIu4PVm2lZWVxU5vU3f/O30iUS66IkjFABg4SA5UhYw2tpPbN/eCqA+ju/8128fu8ezs+P8OjJQ2VKLAPpxcaAw8OTRFzCHszI5A4yhUDxn777MySzGXnj7+/tC1An143NgEqlLJ6VDXqg5/xQk3T0O9xcA7pJELqoX5yrq8kSkWJzenNxnZZKC3rmVECQ37i4sCRYZCFfxNXVhTAociSIQ9Iaplgu4e7du6JzMGjvHx6YKy/3SISfZ1ml5snFoZoZzHbpDsGg/PHDT+SmSv0koQPCJFx7dAkmA4BVAbHA5x8/sy6h1xnTVNe5ybhkeDjr/plTgIKX8x4iWG9VoJUuxmA3RwcvE1NQckGKQKbnIz1hb1X+V569ikVnXiw7CC+LYZHrKl8mN0H6hod1E3DErXIL3cOtXGv0xjbH9qe9XgC/jFwq2xlPa2ltVaW7qSg93oMybWNn0/PS5n8F+ioBteyRwcokQbw2m74iYzM5XnIyDqUcAzkjsI1rVrRel9ZJmL4CD4sY1lJpWSByZXEBt1+5h/aAOCGHTZjBXChMS5YpfMampB0AACAASURBVLTP7fXR6vbEEWNpy4AgZ4e+kYG5aelGwEDI5cD462c5G6IPfkr+6Czf+PmotmeZGPbRHpfPn7Y8lj0SN2LAEQF3SkGyOVlS9uGZJ7L8YPZAn3hiLTZ2nQEgJMyFQbPHTJGk10QSW6ubOLk4x+3NDZycnaGnMshE2/RUJxns+OwUx+en6PVt7iKbQvZIR4ilUnjz/tcR91lGsrCwKIji3Qe/xMnFBbLxhILjzsGBcEZm93/wh7+PxXQR/9ff/DWqbbMmZnCniLhcKCKfyaoxwb1BV4vnB4coldfw9ttvYWVtFc+efYZHjx5pHReyad2/fqejYCBROEfe00dKE8aNy8gSioRb2RBx7xEWkXd9EOk4Z1wmZsRXUhYINXBMms0nGIhSI8Jwu4167Uq0A8pkVO1on9KRgdY1GtolIivpL+IbJqktjYkryKlA7C7/6Ac/VHbuY6feNxEvj1gdIwhHs3Gff/DJA9TrTfzxH/+xOIynx7ti0bPrysaJBg3LQ4EmkkxieJjHrHsaDODhBwLdF133/IbZLkM/t39v+FleOuIgawUqD9O6KUNcLjazQjUWjxG4pSV0w0s9iY7ZFJlNsgUsemEN0H/Z9r1M7Fm6uLFQXszxLG1c9OHnsFHn5rzgOSN48hxJZkS1uIlWpu0j6B1wGgAPNPfGopn5n7k8MFCzg+IT2Gi0BAKPnAhsJaDhUZbJzixklFk5c0DQIreFdtcIfipDHR7AB6JJ1jK8/nIvkk4Xy0sqPci1uf3KXXVTiU2R40TpBDuJHC7AY5VBZMh5kSPiEmTE25xCDYNgWUHSKSdrkzvlMDy26VkGky3NEpm6vHwmh1wiIx0gO0q898lUHJ12A93eQJ1dgsMsHwgnMNtiascmBEW8kZCpKLjYudCvLmto1DlEBMpYkokIUinaB8VEaViqLGgzMVgyGPJ0Zst+//hQ2ZRGvAusb6HfpSGdDSphySQsMcYMaxVLxZJKnir1kGGzPyGHinuCUiFiMMrORiPRCza3NkEI+W/+9m8tQCtbsaOe+A3LQTYBCrk8qtfXkjEVy4v4+je+gZXVRTx/+liY2aDHuYeGVTKrVHYzHAq85+eh0kDAOrNUwTP0vDc8iJUMicoMdizn4/EYSuWirJV4X8kbU+PDTSpnZtlqdWRpU6tdzcTYLNWlJaUvv9szBPoFhHOdRkLyY2cg7rc7onHQlJHWQpq56CzSmclSVM21Q+E6Cb79Pg0zPTpFTZ1OcRR9RiSXowrpD1788ZFlH3Fj6YP45L2PmGFRmsMNb8M6nZBllmbMtHMeoP4rjAAbE+R13pwjA2W/zLbmUhUbde8AdmkKreSx8d7eEE/LsBT96YzwRXtTAxMC6u6Q/WvjAnzafOy6UZVvgmUK4O0zcoEKpHcPg+k3gw4zCE43YbDkzSbBU8MZ5gKgF3QsS2NwM9G0TQvyqB5MaV3A8vhmjghq3FwLbEzVbWAFA9ZQWAl9lNptdgyt7lMgnvPIVxftK2BY3DRkE0fjcRQWytje3pYNMiUtlM7wkKCPOO+PSiRmTWEgzrIuHLVBUOLQsEs10mZcWqho8s16KYtX11YFyzHjzEZjOKpf49npFX7y8AFOzmsab7VcquDN7S28vb0FfywmE0dIzcAOxoC+vmynAiMbjPHL43381cdPZL1LTIhUERq8nV600OsaYE/MX+Ranw/LSwvYWlrVPWOHSUTHUFCfh1nNxdWVninBZQZkleecYuRl51ybAaoa4gq2mvTMzJca0Sj9wRLm18/Djh3skUEi2VwOt7a2FCR/+tOfGYWF+KVjA3nYqsrL0qIsjR7vH6BYLuONN9/ExvImTg8OJD3qtjs4PT1U6U43BwZNXge5a8JRHQbFzIq+Yo1aDcFwVMFiytKvP8CQWZ/fL5dOOjpwvV1dXYrs6WMGG/TLnlpYIseSTQc453CLBkXmdqgI6nHe65rO7CAbjmgjFhiPRtVg4VScw8MjXZ85nNJnjENoub4JI5jHmh3qfg1NZTTSwetNtnImoPIh1jRsw3x5xhMuId2CVIpOd4TP3icPa6kytdLFQBsBWl5dJK2rpaHz/2ZZF38/Nxa/Zk6Rxl+yQKXvcS1GDZ9Q+cesw2kLhdBZmcNvM08sKxHl5jl8OUY3NzpLAaq92XnjaZtMcQ5aQF0Xz9jPBLYsN6kDtJvKEo+nH7/GxcnFzVatFnafzHn7fEZ0ZarrBWQvjKm/ZiWjuyferTMw3XmJyyfdgpBXLnDSCW8QOzYM0nJN0ExAOwBEEFT38+aE0LSfl7stL6Rgskhe2xJmlC0UsLq+KqBUXUx263gvQn6MeO+7xEloI0MPqgCy2ZQyFVrj8N/oXtnt9/HK7U1897V7KJCHVCyoq4UgnR44g2qMxsEB3vnkY/xvDx7guD9AMVfCKyureLOcQyZAhRo/vtHfWZYQsuz5OdwjjpNmC6dsvAyp/RtpiCk9sZh5nZ3XcX3VRrs3ks8S7wkHWRCkJyeMYDstWDK5rMz5qPc7v7zAkFZBxL00j5DYoV/gsmQunLDM9yJBl6OsZBYYwmg4EcDPQRaFfBLJaBitdgMtdVYNj8zm83jlzj19DkqKyNmKJmLyryK+R9yNE5PUGR770O/08PjpcyyureKNr7+JhVwJn3z6ES7OLyXFYpNjcWFBGdWnn36K45MTHcjsLrMqMeCd3WbafvtRazSRTJAjFUX9uqaAyZKVQS+XpyssXS+aKBQqNrbOb5bbFG0zeFOiRY8tMssl4Jc/HM0C6CVPeyRLKtgNjQRpmRNGJsM5oD60G3xuhlExAJFEG4tY94/4Fw9gS0qcxtfBIhzOoarCTThSU0pDcYkB8w25d2hpzgnexLEiwr0/+sX77BIa6K6SyPGEtJZUDtEon/vUMCKjqrqyyQU2Ez8zmWPw8WgMCkEzyxXb8B6Ww0zLhM/WxLmZasOgwDKDxNHBy2I1tB7mCCp17yLqVLFrZSeF16mjkyWDjQ385KJlCUeVuvR7GudN0NLKnOGAC3uK4YQnkJV2s1J3Fgosy7rpoNoXvBN1dk89aYsDznl02Nc8LJB73HR2ujZvFqPXmHXgt54Jv/YVAhZxlu2NbRNnl4tYWl3RouUzFr1CWdwE/VFfAC0PQt6fYrmAtUoJy8UiAqMprlttLf5Gu4FcNoU7C2UUopzTV8BCuYLxJICz6ybazTNcXp7gp589wU6zLecNClwXcxncjUdQnvTgm/QRTWUQy5bx6UkLT1ojNLlmeA3DLnqDDjmuCI3N3TKdjCAQZlkTRr1JvKUrIHmxTOpBBelUToJkJjekRTBbZJbFcvDBRx/p8OK/MUCxzc+yjeTUVCqP85MzA9I5bJc2RZOgDc3goJS+dfg4eCEUGiFImxrKqrpdAdULlQrWlpbwNRJOAzQPnKLV78ixIETQeWiTmJgt7Rwd4uziEg8fPcXmnVu4d/cuiqkcGp0ajo4O9b3ZdFqdUK6tBx9+iJOTI+FobFLw8CWLXdyzZkNWMww4bFBQTH6we2xW0bGQqB9fu/91bG5uGUm4b3w7rj3ipEdHZ/jss0cKNo+fP1FAmgZoPGklMAMTX2Zv1EW3Zc2SZCKsA47Xwm6eRtTx0Gdzw9k1MxtmJSCGgBQsJEubTtEXIpZmw2eJf2ogh4boMvNltm+Ht7JBQT08LM277aN33yUPa2EK/xx11BMzu43pyMIuUHljRBi0KHuwzXfj1SDp7gygNwKZJ1ThaeiMIVSzmgUstyu7FRI/OxKprHhfMsMi0BgLh9Qm5SnDLECpfoD2sWSTD+WwwKDEF3k4cvLsUexqAYkfQhYzGprKCR/OEsG1aeeZ75505iZu3eSfL6Q16o6+COB7P+v9d0Zc9bSV89/vNVy9stjOgK/0Iui+vXFH7f90Po/KYsWNo6eDqjcey/zAwvLiojUOT1O/LGJ4mueTadm97Bwf4uz6Csl4HOViHluLJaRB7hyxoR6uW13UByM0h36JxLmIOcOG2r5MIoW1YlHSi/awi2ypgsXFFTw/2MXTvV2Bvq06iZcErolTRRDXvEA/pgF2JoWZI5PKCqtiM4A+TsygeHtizr5HHvrTMeq9hqgL7SbN9yhEJ9/NMgBm5svLK6Ie7O0fquRlVsOGQ7NPiyI6bFL/yUObA3J5HZzs0xWwzE3G9XZ7YwPpYk5zBe+tbiqDb5CiQU97rnNu8nQaw94Iewf7ODo5xqNne9i8taVScrG8iMvzEzx+8hiX1Stk0jn5t19f13B5daH1zO4byztO1H7+/DlqjZoqIWKSZ+fnGA19om4cHBxo0/NzLK+u4ne+/bbUCOZ+6+Y0yPZogMePn+Ln778nadTq2qoNmyUswfmLum6/zSkcD/Hug/fw3scfCbdMJ2MI8V7FYwjR84u4MDehGwfmeeDJobfbV3Bl5urj/XPzPsnXYlnI/EdDYyVVM8db7ibaAwXcfE9hvqz0ggF88t6H8C2vVVTEWavYQe2zoGWb2ev6GT3BUC61JHxzPfaZwJhlpRPBeTtW4JiRRy1QOYxLJaENhWB2JbdO+RXxpH/JvanhDixXzM6Di9jkDOapzuBAfg81YHxvySWCxv2h8FdB08swNU7ICbjnwHcj7Lmi+IV//23XaBmYKBKWss6++eavN197Ibh52dX8j/1K8HvJu6NvY7l3e+uOPI1oi1solVFvVoX/JKjbioTkNkmWMyU17FZ1OgTjpwLQeeLRhYIcNNqMiHcT9Ot0TkfDyISCwkiagzFaI2r3+qaDm/pVnnMqMfFEnsxkkTMzkhOl3w8SEvl6frCDs6srGcXxVLcRV/Zc42zJs1NJEDYQZT6KWIiE3aCyKZZ+HJRBVg2vK6hM31lW93o2tdsNpuDqJ0BNOIBrJSIGOt0vo/jG196UO8XT/T00enSWjZqnGakTNJ8b9lG7PpK2UjP1/H6UyxU5H9zbvCWPr1KRwxlMezgmTkoxr9+Hy/NLcHYjnRl2j06xurYmztjq4jKO9vbx8NFnuq/stKWTcX0mDnCgJGZ7a00dUQ4nPTs7R7dDO+KIAvnewYFNGorFJHeSTU4yisrSAt765jctYMmrn27CtgZ5iO8828F7H32Azc11JKJxrCwt2pQbN5+QZRrLUXZDTy7PEYyEbMoULcA1/WegwE/KBqsw4nrct0xK1LXXsNqJpEzSDJLbp8EgJuLvjSj6d9pflowMcBqVF9Y8R3HRaLzJ7iYbIuEwPvjZu/AtrLIkNK6Pjfp2AIolPw6bYlprO8YTM3uiaG/jqKRklsSshKe0m9xqukHvZx2OpfLGMCWWk5Y2uvHaI2Y/TDe/CHF37yysxyfej+xVHffFI1yaE6NfpxOBQTYJrLxiRmV4kdlCf/5L2NxsMvbnB60ZRcLhdkbj8Mpqx1VzUeoFWsXcW3rcL9fBsK94nFzv++ZMML5MwKJZ362NLQTTCeQKJQl6mcUQ24oTe6I8qNPBqE+vIhL1ouJEsSUdCgVE24iCDQIjyNLcLpOlzxFPRWYzFvTZSSNLm9217duviJ9G+xC25AdjDrwwHIkZUDadQjGdVfnRHvU1DosHCztgxB+5KdTR4nvHo6IvcDRZMBw3V6MBR8A3pEHTgSTDvBFG3DzCQoynRTEux6VFZcYHiY3F3WLTIxiSZTEtkb///e9iLVvS14ifmBkkfeg5NWgktv3J+Sn2D/akEAhywQT8sor+nW99C2ywxiIceuEOS3HuzBWUL2ZFVzTwu7rGk70DrG+uY31jHcu5Ei4uTuWs6k1J5MgADo6oNdpIxpNYWijLb6vbHUhixEnMZ2ccVtpAZzBGIJzEyvIy9nYeS4aVzmSxvr6B+6+9rmDKEszDptWJHw7x/OlTPHj4Ib52/2to12p4/fX7qpq4cDkA4uj4ELtHhwosbGJwr3L/MHBeXlyqCmE2qowuTCY6AXOWnJzsHMa4P1V2xeqGPgY+fwT9fgvRMPcj38MsyLnNbXIUu/jkqrHRY/hpJp1UJs1Dlb5sf/ln/965NbgOoUZTibPlURw8coO2uLIkb7qugewEouyBGF2ImZK3qT0KkkdluJkQbZuTJaIRRi1wWVkobZ0C1ktuSZ2qpBVwWq5JErjIzRnBAgepBUpNNfrLhc/5IOUyGnOk8BjzLwYqD8V6oQCULtAL6s6tVFnoTX/Umg/O8HCWWllpfdOBtXTdC1B2T+ac+rx7aknql35xEW+sbCGSSqBQqiCdTWtSDAFOlgK81+Qaka4QjbNxYVKjsWu4aHQWL8cNBaV4ldITDd3gWPRmE5fNuuRH/jCHGXTx/W+8hXF3gKc7z0WopS6RwUnTgKcjhMJ+lHJ5WS9rdmQ4hO6wh939fdTqNWnyePhQFM3Sh9lLPMRxbS6/dzQUjhTbO6JnOUeKEYezGXgkttooej9S8ZiybTY+iEuFwswWOfLN5iZ6nS/CELy+hXxBG4fGeEfnHG/FzI4/z9KS4ueWJmDzUGZmtrS8hEIuhzvrq7IYZnZ61WohklvBre1vycN9b+8zHD19D63LM5ycX2JlbRnlQl6UDmJ0pAYwIDNT4jKhDY084F2J5/J17TlaUFOTxyyrzSw5ntCItr3dHeURiWQKr772Ku6/+roMCZntWHZlScW4P8Tzved4/5MP8eYbb6BXb0pmUyiUcXxxjCfPH5kXljz/eR+HGt3WH/fh52EggJ1Z8wTxWFLaT/5egvXcdwLZHbWH2Ba9i9jwYeATGd1HeZBZVQeiCQwnxMEDCEcoTTPCcZA6XQ1qncimmlngv/nX/wa+xbXFqedqaMO5GIQsUDnJ3cykz9jfc7wGn6m2tYQs0rnv9Vwz3f87MtoNudPIejfDGDw2PDtmoy8XsFx1Kl0eKQuOMuCN0zLOlXOKmHmlO4mRI2UywzOW2DzmZFOYLVlyjpge/d3jdvGTi39l6ZDFo9+SGcqx9XMijhQCN1N25ikg8/QQMUH+/wQsymEYsHJJnYbqmLF/PLbyiScdcYx4LKTsoUvyHkdWkf2upt5EJSI3CNv8kijREqZLLVtXm49qQYKsX//mWyrXPv34E7TFeTIel5ohfmopfQpWhVIR2XwO+SyZzwNzfejSIJGsbT9CNFBMJbG4soJQgHwodui8dWgDIS6uq7huVNFvdOTrxcCl4KRyVk9Gn1NdKMIQ/rDm6Kk8HA9tmAYZ2Wzc0P0yndan0BSZAUvIoDJEdvum0z7G/TammlTtBO0h8qGCWF9dwqsbt8Qze358hHOKpQPsnDIQRVGKReWieX5Zk46QYD21mfVGVQZ/JhxmyUZb4i5q9SaajRYm3OiSHviEwbLkarYaojJwviErC2a89eqZggidH+69+jq++cY3kEnQMsYCliUJI3VMmT199OlnwtE219bQ69BLnaPArvHhxw80W5H3UPZJMiIkP8/G8dHamPuN10EJUiqZR7PunD+YHJCQ68bs8b4wYzb97UQ+++ncAiLxPKaII5BeRjJ9Dz5/Uo0uYx+MMeqconfxXzBqn2I65prL4N/96f9ibg3cZtQjC8UnX2MWk1xd6NUp/NCCtSxKmLOo87xS93C+DW9dRoKf5sowh1fNBQDPwM+yLjMoY/tajIKXeXFBapSgJ4C2a57Jhrz38gbCOqnL7Lrn3uNXy7X54GwsfudiqsDnWMdegHJBRxHFBSVv3qMItmL2kxrx2z6UffHmNLVA6Rn7fdWARQO/25vb0hGms1kUikWVQiy/RiPiiezacA6kjfmKE5gOhIy8O+VmNKkOF+lkzO+z6SsUDJcSaUTI0+n2Bb4yG/LFwkgsFHF5da2J0HQFSIVj4mlRwSaO02CI4GSKeCGPZMFKTGZ91Z1DBPpjZVzZch6JJMd6jcTh6Y+sYyunU4bFILtkE7mwhjkhu20lZa/FIQbWceKhqBmVjhxK/CwYCWMY9KHHjUGqlSNrMgtg5silpOBLVriwKmaGQRnjtbotmQbSQoUjwCxrNw4jy5hKLqeO29nVpZFNXaBOxVPIpXPC044vrrC5dQu3b23LSoWiXwZiyVBYktHAstdXlkMMiOWTkYjJ3eOcRZI7bWDM2AWU/qCtkot3JhhIoFAoYWOdzhWbMmWUEFtNi5Gm2+wQN3vyCN/+9lsIDm2w8NnZiexvqo06+pSJsRNKcqqf3ClyJa0yIGg/HpiFczBMPDKvwE8cVE4rGoNmPEIbqOLkNs6lhGPNQrE4ApEkfKllRBe+g3A0p5+ndQ+zMw4wyfY+BjrHynD5ef7F//DfO7cGbjDNJTTKgRantITzm8vbaW5TOaHuPMZjVAjXSXRYmHUCmRaQPmB6OeFWpksVFWd+xBc3Tqc3sjH1X/RyFStLQgbbmTGg+zmPWuDRErR4vKzK4UyzCDHHNvN0jUatcu6jHiYlMNDY8nJmEBXEwoxHIJ0v7zREcpYWeQ4XziP/t30+p8s0JwtTAXzVgBWMBHD31l34I2FZEtObajQl85iLOKyTzXA4M2Yj54fTlDvEnyZAJBZCZbGAO3e2xbMhR41WMswIyN6mWJnZ15Onj3BrfRnxOF0AWjg7PsLZ+Snubm/LTZKlRZ82QBMOuLVSjwB9p8MBqLR34Yj0qKgJnP2YdBN8yIXiIuZQDM2x8ftNsye8iVNtmmrDMxNk0ImE6FpprGtv/gCfj+Kcbyp+1/uffIzL6xryhaIFqaAfxWwGiUQEo95YLHxmbzVSJcJxUSeYJdWbNY3QUqYXIFk2hOGkj3MOSjhlt9EcTJstSpsYnrm3gFQihWKmhMlwooEKd+7dw+rSMjLxhH4fS1l13EiFIFhdr8mnyptexFKVgZ5dSJVZHGQ6HsockuUZMy6C8tx/nHaeTmVE26Buj06qtza3kU2knUifU4P28fGnD/Hf/tE/x7gzwIOP38OnTx+h3mR3kyRiswMyb7aBEhlWIobxWeU0IqeShgAhOipwlbMrb4NfSSmhEwMbEAxalKBxjbGMZfAsEw5Ip+WCG4wwC4zoMCK9gThgmB3LfgetVkPPix3jf/knf3IDut+c7HbEk7LA2tGQ9xu01zKFmzHsN3uOC96LMgxSjgPl2l+eztDwHUstzcnAWO8GVJuSvMMH/TIBy100F45H0rzhTN1oCT1MySNtfm6cmDUYTN3uZVNeMPJ+xhuQyutkQCN2Zuk6M017kIbveT9htsoe1vfC/frcdMvday979Vws2L20UXVf+sVu2L179+AnmJlIIJ9fwmiSwGRAy9uwrpmdRAHVLE00dGKEnjqF7Kz68errt/Ff/fB7Gtd0cHqI9z56T35JX7//dRTzZdkc//3/+/8Ih0jHw2jXL+TFxNPYHAtsEC2zHXllZdJiaGtw7dQnnCeXS2mAZkQySxoakuDpw4Amc0enaNRbyvCZ1VAC449EEYnFpWfj72WJyhHu5I0RLxN7WvRBbpgp2t22jZEf9NCoN7Qxb61tIpuLKdMh2E0pjz8UEuh+3azh/LqqTUZpydRHEiiD0FTZJMm/xOY4bDYVz+OXD97Hhx99qMyIMIRmW5JfxNHsyQwq2ZL4SXzvW9vbmtLDB8qqotFs6lrp0klOlUB1OZNG5JAQi9uMQ85R5H+pv6y2ahj6poiGE7LAocyGgS0RiyGXyWocG/FGDn0gRhUJmp6TJeGzwx188tmnePvttxEYT3Gwf4gPHj6QKF4rkAA6+wqyQjcDQa4FlYk+Uxr4EZS2U+pDCv95iHMvBKMIhTMWA0Y1UAKtaVBkt08soJaLRdk5MzCxGjIdqY1X41rhfaMQm26xBsiH8Od/9u8YsKwkpKRFDFUNICCzyn4RqQ4G3cz59XoYgnNd8AB5LQ7hPk6aIDzMzMdMp2dEUbOWMS8sozU41wYHlBJ0/zIBS8oYjxPuOmuekojR3UumXoDfZsRXA9ft2ufqtTlulEcG5TUrxZWi3ATfEus63di8J/uM+uGVzM5q+rdHm/m2oAU6z6NeQDxbKl8Bw2J59eq9V6VSjqVoPcwWdhxxlhcBP2LRMKJhTjW2BSl/o3FMWrMBh4IGArh19x5+8PZ3BTaPpn03LdmvqSiaVByY4ic//QkLGuSTDIJDXF9fKgDUKVomcZdas0BALeos7WzCIQ1tIFOdAlo67zQbVQx7zF4M16OonBmHuFUjYl/EmdxQ1nQaG5vrsgomJsoAyK4SnxFb9yyfeKDoEOz2Ua3WcV2vocmAMmCHs6eAwGdIDI/dQZntxeMqA1kOZdIJ3N7aEpkznkzI6oU7mVkUA4cxhW3S96PHj/Bnf/HnakJIWcEDXI/Up8nZ8UhCwZAW4MtLKxryygyIkphffPAeDo9PkC1k8eYbb4rlXsjkUMkVUFkoa0MzE9vZfYbTkwvQjkVSFzW8yGWciMzKTJcZmLKYYknE1sWlJSyUFs23TSLmER4+e4Td/T0MhnQLZQPG9IIMZrx33BPs2jFTkibWTWdnHPDLwJIkKnbmea9M78mO8Yhs9ngO0fgyRqMgRp0rYHQpa2pWCjwMyxUSfdNSlyjQOT88ZmrsUBMvZQwSO5R7i0QuTPGv/tW/gK+8Upx6ZQ2HMlo2YtwrA8Dsnrupfi4nmMOq+A0mzXMWvw65cYHKcxW1oORKG8d8t4BlhFHP653kMU6TeWkJijUdbpoEjhHuZTizxsEL2YzXdjNq+nyc8oo7r2M33/Ez2ZF7uR4DF7sWNzsxbsCGAfBe6Wx1q5XL9rNGDZknVt04Mni//kWCqmkw5XTxFYD3YCSC1++9qgkr0UQG2dwC8oUyKkvLmAQiGHJkljPqmzZO0a+dI+gLIuSfwtfpozr0Y337Lt76xrdA+J1uCp4omf8VXhgK4O//4ScIhMieH6B5fWZserKsqctkkA8F0JPnPbtAgG/QQ8A3RTKexnKlImnQea2K9mAkE731UhmlbF6meH6C48psmN3bmZhKZrG0tIJgIo3nu3tIJlMS/fJecUxVmYNDgj7UOAuw1dUodbo8+EN+dPpd6frava755gAAIABJREFUnC5Tb4qnR+Evh5USX5FlcSKBRIzZVQyVXFGNAcq62H28vKIwuS/CMvE8ltH7Bzv4s7/8c5FDaXLIks2DJRj8osGIShtyykrFBRRzWblwciP/7BfvYjiaIJFJ4ZV79/Dxhw8ktK4UCzIz5Hpg6UtTPnrb8z2YzcTilqE2OMiU2edoqvegdnRjYx3ZTM4JwcMI+iPqlg4GPTzae4wnTx7rGbFcUwNMw4ydhbnwJue3LpqH4YHeoS68m4GR/LTRRHK2wYiifeNhBVmWIwIf1SvUn4oexESNLPoIMumsTeSW1ZLJ+Rh3SDdRM8MlAmaYwDaOH//Tn/yP8C2uLMx4WF7L3tuU2mQeWO122wynchmJOSE6NwKv0+WN+bKJ95ZZOeHzrNRSYGHnxkTEQro0Np0Bi8Lcl6x8BAabn5Q16T6/Znohe5oFjV99Dy/DuqF1zAcZywodQ9Z7GwmwzXfIIpL7upfBuTb8LBC5z2mB1GPbWlDTT7tO469+Cm+Ix8zE70uUhlTLv/oqAxZlGxmk88sIFRaQXb+LYDyvoZUyxxbZr49+7QSjkx2kxjWEBz0MEUJl/R6+/dbbIgjqEPP7LRsbjHRyByNBvPPez1AqZTAZNDAcdZWF2uzFGy4eyYFsZ9vIeeo1p2JNkxwaJcOZzgTRuMqtZrWqce48uXnyM4PgS615aggT8dlmpmTo3u3XUFkoaeM+f/ZE5SHvKjc5he3MZiKxBNbXN1Gk1YwgCNoGm0ieZSoDDzuoBL5pl8NgoIEL6nyxTCKWZNQC+kixvGUJV66UxEx//PRTfPz0sbKs/b09BS616WnhwxSSLz+NCBdkO0PiLLO6jz75WGRSmgYSw/nwwQOViezCMavjxqUukQGG3ddOt63fw9KT2R6xLbl+DofSEa6srmjMe4pYViqNUCCqk13Uj24Lj3Y+lV0zf+dALhbWPdTvYdkeDmtOIzNQYo5sQpACokGpImdz49EiPCiaCO85r4nXxj1A/hkDJwON3EoscuvwSsTT6E0r6I6T4k8mIiNkEgMk4zTInJqXGqU8bk+w7GzVu/i3//Z/hm9pbYGsFdchdKWVcxSw7MqBzvN722vfC7OyjuFNOmaL077FCaKFZ3lWyB4fy1jtTPe9DqGRCr9ChsWBnq7kvAkwcxesEvxFbMnzrPfKPStrX5TZWAPB0kwvC1RA+ZxgKiM+T+k+l5ne4Fs3Wd0ssHrZ3Vzw8z7HXDrmnFzdtCFXQst59CWDFjGY+6/fx3A6RSSeQjK7AF+8iER+AQgnMJ6a5xVxCepJCbgPm9fwn+0hOmhLKbqyto5vffPb6DnLXLbHaUvDP8zGEqkkfvHBL1AsZxGGOV3y2XJQBDlJBOEruSQ45YjdLrPNMS6a3DJGIyflCSqbpwxEJy3n0/V7EvmyZOH9YUnVbndtInQ4iC5NINlBC4awvrIswiEDCTES0jQsW7VpMOSJra/fwkKxLP6TPUzSU6iYMNkWMa56raaSiZuaq3lzawO3xBqnZIc8r4nZM4+JyQVEhA2Q4d7tCpCn9fTRxSke7+/LobNZq2rYBxUAhEQ4QJbdvnQyrWk+j548Fv9sfXVFTgjvvfceri6vxQdj1miZPPE7m2pOhnk0Zpo8Tkonr4n3gsGXGBnXY6lSkiicHzOfyctVgd9L1896u6lJSMws+fvoT8U//H9SIDQNKGoZodxwlXERDDeHC7P/pquHld3mF88yuS1BP+81v8+kb1QWkAJDWkoX8VQBrcAtNEdxDccIjAaIRydYLUdQSo9AMZewYRd72t0Bjg6v8B/+8n9lhrU45aoISJDMC+Mycmn+HKTjuS94KRrdCa2+8QDmm282uoOTuIhRzpfNHWS0lajUUQTUbSNPSwGNk3+H6PbN//ylXl68nNv0L/yc9++umeex+m9Ks895F687+DlfUieSweLzri9ALo/JCrz7Zffc4WS8lpsk7oW/zztcePf419/eXCN474w/9VJ3SGULZSBEJiKpLFKJEnzTMGL0URoM4BtPJBqnrq6QTyObT6PV7qDVHmjzjUd92aF84+vfEpA8Gtsp7JXI3MS0YPnFg/dQKheQDNmH7A4GqHdGuGjUNA2ZOrR8IiYGPG19IszMVAKYPMvoHw5HdWPUNCSh1dT3sFzw6AbsTpEXRX0kWd70Z+KBQQIlpz3L1kQOiEBvSMeHLhqNNhrdMcoLq6gUc9ailo2PmeBpIzLAXl3I5vkf3nkHu4dnKo1o0vdPf/hjBctWl5lPEmFHMzAHWfsMnpifoDWzycODA/zs3X/A0+ePcVW9Mr4bG1pBn3SNyUQBlfIq6pdXaHXamjoTGPvF/K9eU9bCOYQmZSLRlY0tTlv2xqKRhMsNxnmD7JSy5LUhJn2599BDiyUay3vSE8ij4/sLpVIFZSPhp2O6LzT13EmXoD2NhM2O7Gv8OTZmrGOos5xBMxoX8ZhSJCUcVD8Mjc7B+8bfwWaIjfrz6Ec+9BkXAiXkSqtodYNIJyJYKwaRiYwRjZCaYW67vJ+15hD7+6f4P/93BqzVJfEXvKJEdIZZVjUXhPhkSRSd92ufZS1eh9BErgpRbtSXl5lYcHODKMY8oaz8eTHDmijC9xjlv0TAcvxOvcUMtv6V7EPgu/s4Ejq7jGwOlbJL1EO0etuytfkI42K0spzPz7RY1zOdVUtdN9WItZbRfV4I+nVq1iyb+w0/YHgfgXHu7i8OWrFITERFgufxdAHxGEcxQdbHIbHdg5o7R8kN1fjRONBoXuPk6Eq+ZCwJ2G16661vK0uw8tpKJfHspFcM45cPHqBQTiGhAaoBXNS7uGpzruEEuWRSInVmVzTAi3Kxc+SZE3lbyWuEY8UqT39K/p7W0kRDDvi7qOPj+cuHtLS8Yjwg/RxdAsgHos/WAEEXhNgUoEaNEp79o1PbSJzG3W3r9zKj4cnPjVUqFfV3ZkXvffAAV9W2srcffP97+IMf/VjrIsRAE6MtDW14GBBuTCGp82NAJwVC4+mfPcZ777+Dzx59qvHxg4m5YfATkU8WT5JWAgy7xJYmAsCJCTFD5B9mn7xf8nsL8n5zboDNwmRaGAlFRH9ggEplEiqraS8uXznN/jMViOyLlBiYBtIb1SXs0jOQJIFYkiyf0URo7ULViPPhopaSpbzwWuJaHL4b43gyO4QoX2LglBZ4MFAGnM5kRDWxWQ0kx1pDwJo7Q63Bqc+ceMOhgLqxGQ59jUQVNPn9vdEEJ8cN/MW//5csCRdntYq8xZVpOUHwC4HrJoUxrMXAOboe2r52dhJaSBY2jL5gP6dNqEP5ZpCq59jgjann98q2lRjWS5Y7emsSbOZR9nlw3GU1Xkk4sy2e7XPDpSxI3LypfSYPV7pxYxUk5xoFv6kk40nPRczdZ6XP5wQqj7M2j6fNxUYPK5zvPHr2D0YBMYBUmdYX3KtIJIrNjQ1xdmKJlDRupKdE2X5mEItlsbp6D8lUXs+SgzwHw460euRNcaFl01n87ltvqzSxbMJsdL2p4Jwu/I/v/hzZfAS5dEQK/Y93D9HqjpFNZeTNTiwmm4gj5k58DQPSvErL6r2DcjZhbtaYMKcPZiwMkizleN28R4uVdaTSRfmls0xSl9t1pFS2jgYO4+mi1bjSGHXZPatdzkzV2uZ0+CSlgJwsllnUzT199lwyI15ZPJnGvTuvYGt9TS15fhab35dTYOn2G9ItMnDQbtgXMKM6Dlbg5B4GQOrzOt2W81qzw19eUsL6pmoCsGSUJxqF58JGg5q6k0qwQ0n1hXOn1UwFK7murqpaYJy8ZM/G1rNcPHWgsavnE2+NJZRlS5b5874Sx4vRuVSyKbpiTBCQxUzU/o225R2aW5o7LbNX4qEMupoTINvoiDUxelZmchIPS0nqQBlU+Tn5fKiQYDMkwVFqzRYuLi61R5i5ivYyHikGMKCSwc/7S+yx3ezjP/3HvyDTnWO+zC1TE1IUbFya7vaZcgQnAva2nm1uE6h6AcnrJdr3mDmbBS0XsLwMS84MfCg2vt0rD4041tfJ8EWbcBYCmDgJi5gLqM4W5ua6LFua2Q27hoERPy1KvJAJul/uAeDGq7Lf7/19FuBst734IhVFqbSdkAZSz9XX7g31K2dB6jdHHd2fWWDzUipvUdqUkd92v1g+bGxsGg+GnvIhAtwZZKJJKarYuQnG0hhPqSG0kgw8zVnW8JQeT+VqEMskAdm8WNZlagdmPZwhZaPVc+kYvnN/DaFIAv/5nQ/0tVKlrKGcBW46Em9D3DQu63T8N+/e/mpot89ubfVOf4z+mOOjJojTVbbTxPsffoSjehe5dAFLlSLWl1axtriMbD6LSCLhvL6ACT33WycY99hin8owkMLm0dSvIQvEx5hZcbNwQ5fEE0qIdsF/b1JLR9CdGQd5ZNG4AcPc/KJZMIsxPEd4kyY52eHNA4439uEn7+PDz95Bt1+X4JxaWjYZWKKx1GKAs21jts3CKWV7w84iB53YzACOBCOYbZnSBN22zUjkGjBrHevIGzbHPUY9LYPiSFmRP2yDUOieEeRcRvlf25ATzRegAwp1nBpkS7cJHti8Rlp492y/SO5jODT5a9PhSOVkkBSZWAxDBt4eXT2CiCY5tShue21sHlpsDrB8HE2NwhT0h1XCi6hKjM4XQDzK4brWG+Zgi/c/+Cl8y8sLU5tU4jauFUNzE3Gs9rGvzwUGTXi+MW0yRrfxsEwUrY81W3/mysDsyuNiWfalVr3LcHixKgn7L6vLcb9+Lom56RZ6Uccu2wPjvXLGuzCv7TwLxF4wcZnUr26g+SBoT+5zAhb/Xb7tnCtHEqZHa/AWsQWg+TBrJ6O9202AfFEwbpHvxVpUADBP5N+CZ7Ek5KRmHqthcoliCYRDMcQTKQUUCmmZiRDDCU7NQjoQiWkohSVz/197b/4k93Vded7c98zaqwAUFgLgKkoiTWuxZMmSZdnq9oTd3T929L/ZMx3To7YWarNoSVwggiSIpbAVas3Kfc+Jz7nvZWYVARGamInoGHbJNIlCVeY3v9/37rv33HPPIfMdib8jAcKgSa9BcwZkYcZncioXuv2W/eBrb9jS6rr9j1/+xlqtga2ub9lL169ZrcTUvQ+ocz43uj1rDvi8KEK4sjD4Zx58B1kR4AnR02On2aEEtB0p2tvH+/b2r35p93aPlKFo7o0yJpcVHaFSLAsTYmZxqVqwai5hK0V0uTj105bI8vM5sxTActqazY4O2HSuKAOEdIbxkZLum+OSoZoIzSixu8Vb1AP35xlKrxD5FSTqJ4f26Z2P7M7OR9Zo7UltAfrBZOyjcBS4MPvZHOL3BccmlblaE0xXJCW1Qgkt/pLmYzXyKb0uYdvCidzExUFwFkVoNsSuLt8PiUO1VrB0hlJ0at1e0KTqunQODkNw2iRRrEyerGdoXRRy9WbecVS1IWXSscpA7pAbrqZsDNieQUssq5+RiYXyoVQIHUhiOwwAIM8zoPzlXsvjcAxzfiJtrFa7ZTc/+H1wzZkRkRZ0nyKeMytpiOpxqJm/dGbWfHNN3JdMy0w4vmNZYdxHwUqmqeEEV+DipA6yyWFjzDwJn7ckjKC7P9cFasBCwAp/F6PE2SCl5RZkjmPJF/lhs4D1WShrtpGeFbTkdEJnCtpFuDipe4b5xnmQPNPxC5Es4mvzsjpIUuuwCG8fmxc8+GfgWVAONlY3veO0XLNitSwZWrgudLpgVLMGyQpSE4IVygpIBSfEs+HEbjXo9DEa42Ye5VxG/CQIlVvnzlmhgCfh1B48fGwvXTlvK2vn7YMPb9jNm3etWFu21155xTZXVzTak8v5iBIuQ1Qp7quUsd50Yh1oE5OpQGL4QbwP0Rjzi0I2pa6fP4qE1Q/37O2f/cx2nqCo4LJGzCuqGwgJejhSVsS6g9zoitMTYUDVUtFWq0X5CV48t22FckVlIO18FD4zuYoNWePJfEA0gwCezoupTUbuOoODtbqC/YHuI2429caxSr9W50TqrMPpQMPeGhNKTkTZ4N7rs6t5AWfcR+HY2NxvEX2kvoluV8YHtNM+poWSKaMslsCl2akWmPCmsnQScT4a23ToIoxYNKXydBozluVWJl3Fgq7b+ipYUUbuys163072GSlq6fnSzKAkJeAoQE7ccAUyrNZjJiXsjdtB2SqyaR87sZxtba4qwB4dN62t8njsfgtg1wKc3RZQhGOE+yY0Ppg1ReWVGVHHgQW419uWpDk0ndqH777DLOH2bLdQEtIhmLf4Q+oym85ZcNZZKJPiTvEMIUqn+Os4YTTYgUWH50AgpTbGlUV4TfAo7PdHWsjPXRIGX8KYr8wA63nl9BlUO4wTzsDjefY1zxi9mzmH5GcUq2cF0mcEiwR8FMYYgnvzYsb2VArG7AfmuNop6kVAB6OJR4yaPub09KCF3tPq2qpA1PLyktQPwAmhkEwGaOh3hW8xGsH98wFbuDDO4kdhoNtGupiSggwMbCVpxULOrl27YpeuXLZCvuT8pXbXlsplW1pet5Nm3375zjsCkpFgeeHyRVtfXvXsRoPxnnG7u1DCRr6WtaGUgUJCZGNPx6Jk9NHdRzImYVbJpWzcPrRf/eJtu797ZH3NpdJ9Q6Yka7kUpZ5PC0iNhCAQ7pF6Y5TqyamVa0Vb36j5iR84TPgrrtVWnWEv3piXWQhWci2tDvyxoThDB4cd23mwZ60TeFpjlYMA/Co7O32VPeqYZXBM9msr5gsyciCEod9FOceBnqLSUEPKA6w8IxNOOeEzaB5POYPn5+ki9mx4GfDnaLVHhsj7+J7zZqtm7dw2LkkG5FrzEHLVYsIJejC2XidhrUbXuj0f+mbkCLBfaYg6fOTFXiXQecasg9drttvqcErosVyQzRgehJ1e23rySgBWMMvJZ9DvDd9Q5jYdWh+z23LNcqms/BxCJiRiKut0uYQjT85+8uP/RsCKoHtIT5R+xDoHeVL3xxMYH8mNM+CFl46zbw7ixTLRB5xDSeHyXh68VM5H5QYWrWMZU5xzZBJBSfic/XrPxAMQHOqGkMEtAuixpPDSMBqTxjbgaZKoNwdOB6vFIHOK7T5Pkf5kgOWUYhE7ThhKhzOY2Kn3iNlt0Hyaw1xzxYhZejdz22bhjzUkfPYLnSUN+CpgrciBuNtrBdIkA7VghmgZkREmtalYkC5GlxCvp91EnRWpEYw7fCSJwPbiiy/Zi9dftEIxr3GXnbt31UFbWVoR1YEu1DGt8qk77VSLJTcjjeRigsnCBbPGhLkABivbCBhQUIIlI+eQI0vqto7s7Z//1B493lepOCG7SmXFoqejDQUi0gzY42676b6GqKzmS2lbqRWsVitIBYbMotfuySQUbTUCaa/PWBANDqRWEM9xAwsUWLk3vc7I7j8+sOMmm3Jsa6tLdunipiWnI3v44IkdHXVk7T5iNnKMXvxUgD3PBH4cdbCD7kq3YotU74NKlDeLHED35pA7lONjmkg7vYWgmsyi7kq5LbKWuFCDgQsOsOZ4rjr4yF6x3armrNtsyxFnNBhpUFsHXrA+k4rH2Et15G1E/qU7qc6h76FsoWiTIb6SBGZyQvfupEPa73WE/YGf8rMypEjQSHC/APSuIB17Uymp13LYgWDNGiQYelMBtYlWvWE///lPMaHYcKY7QLsIYh6EvLOcDH58nlbEztXivxe/72UPWYnrS/F/rqAQ/AvDn1n4fImDJZVSnwR3E9XR85moxjJP8jJnAO0YoWLuuIgzBShusQt4FsM7Ww5GesMsCMYsaxGEiu/xtAwMED5LF8bxrBhsYnk4i3sRw5r9xOnQE+9j/H3nLi3ghKg3kl+rzAi/G94bTXDKncrKqqbqW+2mG+CyLcIBAv5I901CiCXvJLGeOic9KYly6gLi5uXES0aWsFdfe82uX7smQT4EAD/56BMNKZ/Um5ZJgHElbHW5ZOsra7Z27pzVllaU/hM8deqrU+gIkJth+IVzn2ayOrMBcBa+BzOWUONk3/7lf/zYHu4ehp+lZCSb9aaH2u8ySo1Ni+g27u4w0nNaylm5iE4X7GxGWyaWHANKo1bglABZu3cJfsgDoajpsiloalHy19vIwHQUDC9srssvkfLUXZZ7klYeD3vKtk46KJ6yz3J6bZx1UAAFu0WGJ5XpS0yRzxnpBjwXSK3FXNEKuZKdNJvK8OiGonIhtdLpwHB/V9XSTQjTU6Y6JevD/DSvrIamFnu7VClYtVrUnuM6RWya+mFKkoIQBJJf/R6WdwPN9mXzWRlY0CA4t31ZMjqddtOmCDcmfR212x643RQm67QPykcgALC1QV/lbTnPXphYEannJA49mO/C5GfoHRKsS5lzQcVi2faf7NnNGzfmGNYi0ZHA5cqAnrUkxGifBy3vBnogihZf/nPe0ZllITM9c29by+JLXSgHA/30cyKkk0qdq9Gj9v5zMazFmHUWCI9/XpTrCgEv4llnZWQWy8EYG2eX9LSAFRK8Z4PwmCoUgr52yEYXcbGILc8MYOMLzj9YbGb49ptTRjw0Ob7IPZY3Xhwhgo1MSbi6pg7OytqGgGHGXGTtFA4riRwGfIfsCe4MJFgWdOOkab1O16YjSq6My1HDhM7l7frLL9rlK1fcD7A/sNuf3tVALc9SQU5UFjS8A0kz7R5/tXJeUjJIzGAjj9ORMg6yB8mbBJcgdaddU01Tqjok/A4gLfzzX/zadnePbKBUIgyj07ETrcT11eLzE1s7g/plShs4k05aHsd1nJq6zDW6PK8fYD6zSCAmM0LbXd6MAYsiG1WEE9s7J0i31+5YERyGUg9jBYaBEUockTlQyqZsmk7aEGemXl+bcm29JmIokAiBQZLT6EHhFJPMqQFFpsIzQTaHQMCjxYqMoNrrN61xciL3JwbG5abDnZq4WW+thmdnRmUk1JNGsyXZ4lKhJLljgjY4Jg5SwhV7fWcJJDLCOXudjjXbOIS7UiyCruXlghXLBet1eppIoMsoI2GGoGkkjGmYgBVCpMYxh3vFKBeD19xXKo6Jhr6zwkkLlknldNgwcN/swY9DD4OmxFSQCgbDf3z/BhnW1lT1sk41nymbB6QF8mQIWjOQXcLasXx0EH7Go5mVgiHbWsCxnNUO6dG7iYsmquoSUhL+uQEr7unFoHUWU1r8uzM/r1MllIqx2p3J1Cxyumap0JyOMC/XFrqFZwNmTHbAV2RKEDuqT0mlFrK2s0FzkXwaqSCnc7Cg8MBMGeJ7gU9HwGJCnu7Z+vqmZfJF6/bbNur3hF2QlCmhh/sjz0ZcWJhhq2imrtk8cVdhEAxKQea/CgSsjL3wwgt2cfu8JTD2GAzt8cEjfb/T6tuT3T2bDBOWg6iZTrpnnWFSMda4DvrqdLhYmoV8zkq5pORlVteWbblWE65BaSDJErkaOSyB00oa/tHBE/vZr9+x/ePGjHDsGFHJ9dplyDmetd+5fzDMC0WyRNduAtwHW2KzEyTgFbk/HlIowSQBO6qQkRFIhcmE5oDWTTpj0+zYxh3Mdyl3slJkkgDgeOi6+Hjx6dDW0W3tfl/ywhWIo4gBqhRK2YQB4tHE8nlkgNat35/4fcQ2i4xPbtJZu3L9qsxWj4725MFYKS/b4cGh3b79sWVzwc4snxKZs30CvuQNIIB4JGTgjuHCvbrGfS5bs34i4UxGnvpggWH+ccw4UaurLJPOXT5XNrQAwbS4T4jtqGPIvaWcBEincoI3wnrSFADZaMISKT9UGZ6nZGVu1cmzaKFVRLFpnpxonGs8hiLiPpcE1KO9A0nuJDYvbrhOqFxmWDqOsMdhW7cHinN4i6Whk9g8hXd1h6hQEMvEGb8qlIS6cGER4FVgFQSvQNiUyaUPnQKpPPdX7BL6ZYTAG3lPXv/PVDuDwsLstWMSGYPEQrkWXkn/iiVwPNlPdxkdY5lxqjz5eXqGKH6WjzvMrzXUqBFSC4mVSLkycQ3lXdTQjzEudAdP36d5WipSaXAlyeaLmt5PZtN2/dpLdm5jy45PTmz/GIvyvrCr1aWqNhjCfKT0EvvL5mSxnsrk5Q/H6a7RC0xB0xktsq1VtNYBxk0A6aPH97VI06mc3bu3a6NRStwvQG5KKeRp6JhJSx7MjfmRkWn0JJnKaZ7wENv2UVuAb6VcEohbrZTc4SfLDF1B9uf140P75NYtaw57Wjv1JrgJIY12mDtIy/k7HEhkAXzWfCFtOUoSHhS6ZiLxugtPqewzcADyooGoLY9CBTOMPY2dsDe4ViYEAE2mklehbOzqvUvlooIUiwJ6AXOFZFtkq3grgmkOegxXZ2w6Gtp4JGBnVmkk0tAY3MUcr0KaVgDl/JtVAbzAKBL1NMA5sAC+htAnUgkY+0EqaYrPouNT2m/infmEQBp4oFB0EcI8z8uFNNGjbzQYXCdr8plBCLGUacwi0nGWJpxBHKZU5Npgq9PldPMZAi7MYenBa/QOiBSmPY0Pxos4HCGtOvikDLuQscmgL1yLkrnfn4roSgXA86gfHNnjx48tcX57E2TPR0CJkHBCxPeJJYn/e4EEflo3Sr+3sENFPCUoeZfCg1YE3Z2dLWwrGIbqBORnBCo6h6RPhvW8X7ydV6izryjLOk+I4LwsBJWY8fhKnqc5C0EvUiAWP7dHr/kve1YWSpfZawXvxWcFrSSloTOIHc8KIzwe9mfaXPNgv3AjAokyXsdZ2sPZW6bO4Whg+WJJAQs97ddf+4qt1Jat2+0pzYZZDRuZNr9KhsYJxB47f/GSxnlqJch7KVtZXrV8DucZ5GOcfc2JysycZE9OkHBpWP3w2NrNhvVhRGcydkIW0PDZtj4DuhpYzgU3mYncZ5g0w/iCQIQcSZiLlkQveNWU4WjKLNYIXJ5Bz7JiL02sPejaMMXmcS2vySindQcmxXu1Oy3r9dgEHiDh/YChZAvuvwgtAACfa9LAL6RKuYETQ4IccyAdo5/VbraV7QByyz066dnUNBGkmMcJS4YOSDEQAAAgAElEQVROGEx76A9sbPAklZUMWedzCgRQeJDr4f6m4BwYlAUwJwFJAs2HQ6cN6J6pWeGqIDhtU4pSjrG76MSOem0b9o+tP+hYrze0QZ+DhREbxl7oSkqcR7LT1VrWTupta7Uw1HXJHbficn2v6Nw8GPSsP3QFBspH1pTzP738lpyM9N3dB5TnwMgS9TN/TuNYLS/EsbJKmRUP3WjVLG2ZfN62NtesVilZt9lShUWQUwaGUgbS1umk1fcPbWfn/lxx1FnfZFmO40YA3Z+Gn1ohvwrqDEEnO6iqzMoVwU9zDMqdk2MK463sGKD0bzZVTJVhuveG0ul+rq+YnJzB3F212Df/Z0b4zv7sYqBZCHqLc4SfAdtDYJvNCi4OSwd9cMF0TysNpd+FrCyzYU72i5Zp8Zp9vOSsRPVC+A1RdM7PWoykp+8cJyUzWesbG1aoVOytN75mS5WKsAq4M3CblpeKWnw79x8rkK2tbdjrr79uy8s1296+pI2iBQmOEQ4YBUNJ5rIZfawCpc3d3UfW7YJ50eafWrWGA03OesOJ7TVO7P7unj18vB9OV7NarWiXVmpWQkc+m9Xs3kkfzISmFKWbd+w0zAy+lXIzCWbkJsOeDQYt62mTOOG1M/XZyEKGDMGltikvEhM2DhkHXdGOJY3rHuia2fA5yduAEyUsQRYgHqFDHgQbiJ7cb+RRmIGD6Z+k45aB35QRmZVdQgeQ3I17grKFZxiu0CnIYzxSw4FSCB4X2Xkx48FnPOa1yXgZDHZlBpoAuvYR70UnEH4UwTZtg35CzP8gdCvGOQxzAgJlFEG0UskLn0PbC7mYr3/jr+zc6nk7rB/YrTsfyv253aZEdN4VwYG4ls2lNRSPTv7R0bG6d2CWNGqWK1V7+OiJTTTPiGRR3qrlirhyqj7EqHW1DUpB7l+ng5IDnWYIpGMb98Hc8IXMW6GEMUVFg9kIRrKWhvq8dKuLwg3v79y2e5/e8YA1S5kdYvdOwSz5mHOvwndndIVZh2q2YRdn8uYsbbes8mgQO3Di4OhhOsWBL0mj9hAC+zMC1iKQHgPJYlKiN11Iqeax0795JoDNfvVp3z+VXYW0O2RGMymakP6oA/osSWPxcvzU1FB2uBDHDz01n0c77mMcf1psfCxoZ+nbz85KJZi2tGSVpZp9+6++bZvr55Seo1/EsCqa6cVcRpbtzIWhy3T9+jW7dOmilUs1x9yCVG7Q4HCCo5xUKLvCP5QP6GcNBq6N1HUbMDY6CxZtKwaQj44bdtJqWSpTsOWVJSlEUEGxoHcOTqzVGosdzQZKpJ1oC+SgvZQYWmo6FEjN+7PpkxkGailrkpJq9jXGBIADzz7/GKANnSSuEII66pSyRoYc8NGGUi0Qb46AMQq0CN4HUwsRoXldbxo5O9W7apR0cMamQ3e1ABOTooQMG4ZWKhbCQHJftJFSxX0SB/0wQI4KxwgzUeRjQKYphXySIJZgyFYDbBMAMZrodcY26rFvopM6QSVjr732JXvjL//S1s+dd+IyNAJp04xt2O5qZCZ2qwlszW5bGWOuWJD2fDFfFD2BZ0vmxnD1Y/T595/Y4/1d6/U69um9+8I/YeyW8jnxr8jSuFbWg4xGICNzIyPcIz6WS2+T7KZhtxPz0uBYRR2slJVkY9CbwMNKpbIVC3l7cPe23frkliU2tzemiwO6MASUZy3Mv80xnBjJPHVQ8Sd6exihmJ38Lgsxiw0ztVEnCnpJGBfWQnmIUFqP9u9zloTPyLA+k53F/R+klGNJ9dSANcOzzsSAiI9FgH5hnOmpqqUiQ9JSf0YsUWmIWiWlQPiZWGKGpoWrY7j3ozKuea92Yah8fq9iGTmnTvhLs2FR41zbWLfvfvd79uK1V2xlecn2D4/s/U/ueto9blvvpG7rK6v28kuv2sbmml26tK1OFiMTmlsQB8FPAAWF8A+ZgAcvH15l45FJ6CdlYuBcHYKZa6CNJRW8df6CVctL1mw0hZ0dHp9otgzwvtVu6zOjVd7qD63DAmbTJVEf7VpO4u5OuMTTUsa5ALxqOPAe1HQoTyB+54RT0hbOh1QCh2EyNQaR+yqzAJkng7T4gE6IBJSHf0QXG04pA+EhQONQTNaXTruBK+Cy2vquvAk/jZ1IBsrGJVRCshVZE8FDSjTUHpjG5WATyB0ObAi9ZHCwwcOcLJ3NFCx3iLlDgqxvZFda8PkdMr5MKmHXLl6yb731Hbt48QXbOhcOHDY1sjRB4ddH4JAOQnIH84rWbJSHv4NCgY58tVYJelc0JHw2dggIPhrbQf3APr3zqf3hg9/bJwyJH51YOu5pdbDpCBLwHfpITIfWG/VUEmqIGnY/jkwUjimG8rErYyzH6THw9orFiviAZFi3P75pt29/OlccddjKfdwWDv3ZjKHD+068c/A5moMugNJhUlz7T8ErAvEBbBezNfBiBLr7UEbUeOLhURKyOJ6Z+ZyNRnMyvucYcf8+LebFALf4dwuZlBZIeONZMhUwrsUScQ66h5+OP3MmY9N8VZxf+EwUZU2TGSx2DU83Nxaxw0UirHcPIyl3Pu+3+BaLP+/EQZxvtuwHf/t39qVXXxeY/WB33969+amNRx0bdxoa+bi0fdG+/OXXJbPC2A2loPPFgtpG9CdW9PSNwnt5wApmA4HK4Axu9wmkAcA1rG9saiHCjN4/3LfHu7t2fNywLEB6NmWt5om9e+MDlW5o2ENqpOOHMukI4Nb6ZqO+unwZBnJRROjiLuyzhHSoMpgZIEgYshOtMgBhUQLoRHflgUhwpWaEe8a6Fp9Pdlo5bSReW5QERlNCmS4lE2gFQVKGgMSG4RoBiBUAQ4ZFKURpSDZbK5dVkPBaHAKVStkGA6glUB6Yuctog2PSCr1Bz49rolHAeIyh/NDW/mDWsVhC3sYlkSPRmYyMTG65UrJ8Km2Xti/b9euvWqFQskp1ydZWN+RWxPswhjQcdq3RaknID9pKv91VeVmqVsQyR5mBLBKpGSgV2Loh5VOqLFmhgLmsE/UIoii+ovd17+GO3bx1027f+cTqh7uW0PXRsQ3V09CVaHUvSExTPogNtQOxQbrGmGnwHDXcHRb17qOHdv/+Q0ucO4+RKu1q7w664xfgioPn0WPPVQ0CkK6NHQXAfMdHqF1cK7Z9UBp0sqOL/HkCNjeemAPu/jPU6z4y8mcErBmG9ie6c4s7ebHUm1dj/hnO4ltBeXUxWMWI6GTbEMQjZBcypBl4riyL7OMZvLLQNdSw6Gw+yAPBYvk9I96eGoeKWGE0+AgQ/ELZGg0s+GyUBZSF3/zWX9ubb7whEuLuk317tH/sUjX9jlWKOTt3bsuuX79uV6++4K3mMAgippweC2m/OwVB9o2jNZ5JOacujp9yKQQyAFQyrOP6kTKSSrlmj3YfayRoc2PdlldW1IHbub9j+4eHVi1jSJG1Ow8+tUeP9qxUrtrFKxft3PZ5GX7uPrhr+48fybBCEscau/MykCAhXIlym89NiRhkrKWnzkYULQF8CKUAV9slkOCIQ7mG/A7f14qHpzQYqIwiQxon6BA6m5vARfnC9/0s9P85HkwvCFmXpE0zZsu1kiYB4EAxmlOpVu3g6MgGYx86Bx+kG9dptEX6nKXdcOPoyiUT2hvswVSaBoEPOjdOOtZvkyUmhFFtMIKVdEnna9dfta+/9dd29+5du3//njq+eSmxDGx5c9nKhSUFDEo5t7/zIAVhk46qVhSKI3DsfCZIYDmdR1+hU3vjK1/RFIV/ER+829lpt+zR3q7duXfLPvroht269bF1mCIIWm7ECWXjSlzoWiK1DGjvstuuXOQbkrJy7+Fj29194iWhygZS6nCfFtnYbkbBjCGXl7Sk+HkJGRroFOAz8eoq513iAgA06iRFgqmXK6EjGBayrOrxxgjRjI6Hi449JR152rc8hgZGcwDYnwZ0nw1YMdOKkXb2uee4lI8oBHWFkDEucp908oHrxNJQaW9YZkEdQpbdwTH3meqgdI1ykADdjSVmRrOmQSwPFz7DqQwr0BvmU4/hGhZoDzGwshCvXbtmP/jB32s26+CJa5KTdRBswI3WN9bttVdfsReuXFInSjN34b3JVGj/q50fJH3iQSSgFNwiCMTFLDt62IFJdHttO6jX7fH+kfV6bWluDWA+jxPW6HTkSlPO1axWXbU7+3etsgqZlPZ7xbKWsvsP7tjDR/etL80lTDdRQ/UZOnn2Yc4K7UFzem6GSlBlbpigAL4DxqIBe/Aqqbd6hxHYCBa7G0pklblADJIaBTb3g7GCFiA9L46CAAEC+yyAYfSiTo7qypjEVdROSxgmtuhUQZngd/l7rg+tdfDD/hCeluNs4F00K7guzT2OUAmFP5UJtArwOja2DwlT0vUZYwsPSPy0NI3QidVW1uyNN//CvvKlryqj2n3yxJ4cHdjjJ49t0hvYN7/5Tbu8zYQCYRWiKXI7fgChUuGmrgDkPRt126JmYHTLD5DJkVm3Rz5fila9cD2Z8aIY3NZMIIkHevOIQbI52E9gYvV2Q8GwVExbrbZi+dySSLKN5rG1GRYnDgxG1u5SHntm/mDnvu0/emyJzQubdI2V8lJRevoPXkH70ue8IgA/R66IR3RJnL/lIGYsU5yRPAeOfRO6iiSkOb/BjmG51X00ChXWEfSXnjNkhS5vkLSJ55KO9qfVYPPvnc2mlIGH9vVs3vDMDwnIlSKal8OkrIvjNjFBE102GCWosfAsHCtcDqk1QSuK/s2wKpUhTh4LJjxRb8TL8oXSUAFioTvr5bGvZOFfujg84YoypDi/fUlGmJsra9ZrtZSmF8sMuxbt8uXL9uorL9vySk14i1Mw3KiWC5Emu+bJAiwQ+DYsWh+1ChW93pPOkY98xKyRZ8+IR7vbktHCSaMhv8DdvV07rDft4vZl+/Y3vmkXz1+0Xrtld3fu2qMnj+WH+OjJQ2l0QUcA2BfPqdeXvT3BsVQsiqTpILMz8wnGUDbc8g3GN907lzqRQjPjPuiQS0WB+0YA9AFgBYwwPqJsEQ11SrYxdIismhQI/3FvkOlREOUFRa1CDoYOa8a2Lm1bdXnTxrTyCTg2smYDdU7Iu32pUsCpAueDrsF9JzNnsyLx4uqyUDXm1u/YfBH4OCghcLImoWUw7kPgobyCjoLNezZX0vqIAaS2tKzyUB8+Kn4I40I5oSjRPbH5vRUeSr++3KXbjbo9eLhjn3z6gdj9yOXQceUegtv6c/Z4oPnH8dSQOBLmJ9oSiqxZK5ayViiCG06s0ehZpzXwWVvkjejCQMDFL6BUtvd/93u7f/8+JSE8rLjTfTrfl5antTHbmsvIhOQv5cqhsnxaaLP7vojZlC9Tz0wcA4niYh7EXPzN4S43oGBhaVDyeb7CvZzHlbkZxWeaZrHdGoPEqWAUSrtAkJ01IeLnjxs/cMW0DYPtdlRhiAHBgxqcHMfqvEMasp4/MdNN2g3AOBdEnN8DZTZh0YQbOr+nETc8pbJ6Nt9ayNwSCVteWrLrL70iUHZjddVNH+Qo7IO1aBPlszmRNc+f27Dt7YuaARSeJetxV/vkWTo3KHCcIgk4CMb4tTpO4Qcbn4msVJRITTyoLJi67yANF7IQl9klk8nZ4fGR7eF6021ZvXlsA2Y7sJXSiT/Q/XJH5J50mChPCBBgZpRYChiUNGAoMMo1wsMZ6/xA0Y6CLHZUvCTA0bHki7Z7liE9sjjs2tmA8tOan4iSfc7l5N6sPUHHMJvSbODWuS178drLwu0y5XXLVLctmV1yIcBw76hslKGIo9iz1ATXqImkV1KTjtm4ZUn065kUSPowej7vksJIsuCwTBAFl+LaO92uHdUb4t8R0IrFqhWKS8qYKXFVHehZOQCuGUlp1bFmUyIKQywlwIBlqWQV1OPPudNq2p17H9qnn3xoh0ePrd/uKLuF6Ek5S7mM0gPlL7QI8DE6riRDHApCnxjdSaWsUi0FZx46pkHYUwcv2QNNFB+4/vCDP8reLLFxfmMaSx9lDY4oz/a7s+CDBIwm6UPmNZNL8egbu1i+r+YjIh69/fcj45y/jwxkn0D3ACesQxf9nAHLD/DTX2dLwqdgVjx0/S981tmPzDwKA7WDMtmR+AUSbMiuQrkozm9oRhCcwOGiTj0Xxn+LYIj2lJxgnhGJE24YEPGsmD1JYfIp2FXEpxYPi8UMKx4Us2C5kH2xyc+fv2Bvvfk12z5/SWqQEu9TGz1rfTZsv63xCk588EgWIQzulZVVmYCurqyqqxM7bDriJIvi6yUGbFdFAw9ybJOMmlOWWUZUADjFGDVhmJeOYKW6bNsXXpCO04PH9+y9m79XN3PQQ4vLDXePDg/t6OhI2QkPhnsWczgpYFL+JXGQYVgZCgkZzdQzBlRksBmjlFTwdbUEgiWbNx42lLAEHkDsUq2o92l1YdQ79ECA5NkWUF3I5KxaKMjolIw7mXUmM1nol15/3X74vR9aPs0IztAgUXStZP30hlmmqAAiukSEJUI3WGhHaF5oXUmhl73nxEzoGNyPpGZxx5pBZMDaBnVL9urWH3bFai8Wa7YkHf+yZZjZy+SCAat38noq8+hUDjXBAI6VTrMOUetwPfd43zi3oamSpd3f+dg++fSG9QddMeA5lFx5H/ehnLUaTbt7754dHx1Zu9lSInLhwnkbddo2nUAOHVir2bVkkiwVH0gX7mM4mltBFkp5L037Qsne+c2/2f6TgzmGpYFTMd1DScfbq0TyOwk/QnyTU+07Z736RplrSc2t52N2tcjJ0jZeaLHyMx7Q5JgjMt3zpFfhZ4IelpvBxmIqCBEGEmtk0cuc8ZS9fBQs9PGayN8RfhUW89PalaG68nYt/2NCntNFC8vBX35fgYsmhoh+tMkD3vCsoKWHU/Ap9ZhUk6W5BVxIrvw/FvG02cD5Qpa1CLiH35wFXa4bH8C/fPMte/31NywFhsZpOpnYyXHHpsmc1VYoJRhx6cviXS4wmrZnbmoqbsza+pqtr60LOOckx06d14kqFKwm5s/4nf29Pdu5f9/29w/FwerIzCJpy+WKba1vWiKTsv1Oy5qjsa2trNvfffPbtvdk344bR/bgwS17sHNXg70drNSRUwlBkXJVIz42EpCO2gcBCJyFLzhVzD9yzQLBh6Pg8IJzMR3BoNIZ6AnqCoJrjVBlSFmhkLRC1aV2el14SQwIj9TJAmslWNKxg7uElVnMqHn+5UrJvv6X37S/+c4PZRqamKDK0FMp2JuaNTNbNsksG5pp4E9sLeWhM8XeWFy5zbsSg6jOKx5ZOOxF4g1O6mjUD7qWm7TMhicqIWsEq1RW/yTSOeFgmluc9OXXCM4nXpuqAcaCEAwECiBYYevlyhpObYEAC37Wtof3P7QEEw1BNy0wnPQ6R8dH9umtTzX/12kxyzi1QrlAUWoDsLe2u3FT8sWyl0xKyhDg0gxNk1jwmNIZe+/d96x9WLfEuQsb00lyJPvphBwsfBMHn9awX9n5btPjFl4B7U7M0XH0f7SRT3UEVcUGDazTFk4CO2eehPA1XLitHzf188SsCJ7zAcMDV7hVWcPEPgqMqAr5JqdtH+2KHBh1dr9KAPhftKiFlXr25ZInnkEtfs3KZD4u2kMasp1HWWlFJd3+nHY882+wokc0L/jMf6KpAAkSEJfX8CwrfJ0B3+OMobP5HS9YpDKcyrBmpdrp0vDS9rZ942tft9oyrjMpSwwRnRvZYS9vTSvZGKfiVNKWS2bbaynbFMs6paHbZrNhPUiRlGgJWvUZaWBtb27Z6rl1W1led8Jj0pQN/fd/+Re7fXfHeg3nYtGJ5gRHr3x7a90SmYQlizmB4avLy/bai69qc8NKrzdO7OGTx7bz6IHVGw09J4kMZrNqFIDl9FCu7A80f1ir1nTvW02UVJ1HRZlL9kOWQxs/StfQ/VI3KuWGn4S2fJZOnPNC09mkJXD7QbyOTKw7tm5nYp1WJ4zNZK1UzVohnbV6szFT5iSQXb50Wby3q1decr5RcG7qtU9kTDFN5a2XX7NBdt1L6wA1cNM8V3EsNq4CHVoJKhAPYONpRtcsaE6/MRDOxXogw5XLTa9ppUTfKlkG0JmuIGgxj8ig9UAseBYZPy9n56Ao654EYNoMhnPQo5em7oUlmXqZTu3J/m0bdFsz5yQ/ER2fJlu9//CBPXr0SPgkZSe0lXa9LhccJKZGfe++8vNUf8xZwk/jD+wrAhnkUfbhe++HgLWxvTnDc30jgkt5chca9xqdIfhGgNzZvQGPirXvAo41FxuLWlhhjs9lq0OGcDoI/BlF4POEMv+ZENCULYWbT6bDYuIUkeVRwLLYhAxmShxtxr0KYn/hzxGY97SSFrrzdGJnTML9+aKUFVudlsBk3kOnPEOczMQFGZGF+HYmGrpcifhZLFwpc7rio2uLzW/gnIsVM67TZhQzUH6Ovy90IV2w7i/eeNO+9Npr2jAaW+lObDTJ2DSds0GaYeSUDcYlmybh3UytlJ3YSilpS6WULRcZgSGTRCq4IVWHeJaV81VbXVmx1dVlZY0QQVvgUxh2trqS4lXWABY0Glur27B2pyeKQ7FYstW1JRsNemrPu+pm2g4bJ+JvQY0Y8KxEwXGYQiYSvb7UGIphVhNcSHr3AtdB11FQYOwjmmigDOZztHruqZTGQyThLLVPl01RB5wRHU77TNZef+3LVi5U7ejgOASMIMooCWQMJMAxR6KFnN86L3yq2e3Y8tKKLS2tW6a0ZAPLW99yNsmUFRnV7hcvkfdynFC7MfC+ZpWM9idYkwbaXJCDIIU0srCLQCHS37mg46DXsn7zwCbtQ8tM+7I1W5UuWUadWvdTDJkdw2JB2pvPSoBTZeLHt5Ia51+NbX/vsY36aHGFKsmv3oeeR2M7Ojq0J/v37eTkQBQWnhFlIJ+PimzYgXeGjpiTP336gzlOnxvVQZPOqMP64fsfWPOkYYlStTgV+L0wgCzeTxwWXjjgnz9S/M/5k6SZiwA9N4ZTnpNXozQykPWf0eNxQEYclJkkTFCckAMJ9yjowVcrFTu3uaXSoF4/sUePH1uz3dCCl10Sc1dTl8qgQ+b27c/4SppL0GZzzm+LQWoh25o9s4W/i/SQGPwX8a14Snh/w3+Cz7Sxtm5vfvVNW1ldE/4GiRHglBIDieHBcCJi5cr6uvWTZTseFKw3wHiTDhp4xcg2Kwm7uFawWiUvjEndLznQwBsK4xmyL6cD5ZLRLFJkRBqQFSdTW11dNe5huZS3zBQzi77VT5pOcFT7fGC9IT/ftkanJSusyLbnOdAKJxC5lXrGUmg5NSkvKLMp/cACcSgeWG80EJ6GooI6syEjAWck2OHtp7EdECcNVRNTeRI4ZWTsP/5v/2QvnLtkDx/clxzKzoP7trm+YefPn9Pz53MrGQqSOJYt2Ci/bJP8uk3QYpdETBBzBEpB9STom6mZEZxzpJ/FdIFUVpHJ8YzfuU78EARTnzhANV08SlautOjVq579PSJ7UiJt79nho5s2bB7Ycqmm9+L+MqBN1rq5gb09AD1cr6wlIcRSSqoc9EOX8p37Uj94ZONBN3QZT8M/8OMQiTw83LNHuw+t0TqyVqtpo0nScjhF91wZArhA+CmD1KxNFDLybjmGvpqaAuOJ3b59W0FP1/E/Z3j5/+iqgsKlErCwABxUnG9igtZcTSFcR+gmuWxLGJmRNnjaqsWqiICQDgGhT05O7HBvzwbDrhQXNTQajEclUTKlBA5A6zPxLBPYCC8oXpuTcoNcx0zJleuLM5yng+AM54pY3kJZyXLm82Pa8MaX/8KuXn3RdaHIAgO9Q1kHvByG4hld4R+V0oyhQC52g04wh1RyosHhfBoGOlP8Pkvm4LuTKh2fcXyPQAJtgEyB72Pr5Dik++TJDYiNKkDYZVqGCQ9kA0aBgodlvwvvx3HPbqcr67lMnmHhpCb/U+kRMcbtsQhY0lsb6nPiVMOh5aVrQvLNpXLJLDVQdieJH2U6juN4kyFp3/vu39trV1/WXB5Zxp17d3QfmA6QFE3CyaTNVs8e1Xu2303bJF2UbhUSOYBfteUNK1Wqzr9DzkZNIF4eXDgQtGXpBaF6aL1O05Ly96NkchoPWZnSvzCP6wJ7GLmO1aUja3GoQM6tAvcVECmLm0fWP7plo/ZeGC1CCwyH6YqyO36/VC0HM1UXE1TW57Y3SmiOTh7YlEZSMEXxmVcXAiS741A/Oj6wTz/9yPaPnkj73Xl8STs+PJYUEM+dA5HWIYPSBGm6uoUcooORmDu0j25+JA7XFy9gSS0h0jUcV6AcUtYRSgepVkgD3G+ioITA5BbjNzjjyiAylbWV2opKGdehZrL8vu3s3JPyJpIsEAU59dUSRhsJD7qwEf+UczMt5WIBnagYUKP+vafvMSB5wjTHsjybCkyYyNeKP7OYrSkNSFghhwpo1TM6YXZELPA/FNJ8kbKRvMTG9hx+UbAD03yhS63Ih1HBzUltZJfCQUIHUvc6nKaoDYCLRFlu/VxwKBbXK+UOxZQgZMERUxFHBzkWB0ylWgC3ST55EGAneAK0rdti0Lotsmqn15HiJwO3lJhQABQ+ybpSYJuePZQYDyoWgsIupbl7DMokgwHmXMGuvfiifeOtb9nW8lY4KMbWbjft+Birsa7WEljX3fs79vHtO3bvwa4dHx6K8pApFSWiuLGyYtWVVYPzeW7znG2sb9nDB7tWqtXszTfestXqsiSWUEk4rNfFW6qUK2psWBpBw6w+p4xDQkXgUtAomfZs2O1YoViyCaRXAoEyMrwTvYWOGqlGjZj97B7aqLFnNmhbLpWQ1T2fF2oI3WM4bTD/YdfTHFCAR5drjA/hkYbRxRGL5HMOZnVnXeniqH5g7//xD7a//8Sa3Z5Vq1VrNlv2+N6+DTRDibihu1zTGUxpPnIqnI1sHDWTSX9kN/7wrkxav3gBS3dWCLw2tcY3wkOfAfZsCEnyBlZ/CIXjXwkAACAASURBVFYoAbCJqsWabWrODunWtp2c1FUC6UaHoVmCFc8MUBgNc8kRgHv1B5LNxYxUc4af47cBEImFluuTBz2xgEnNJZP9G/PZzNNpW6SOeCa28Hez/3YNpEg0/X81tz3TsDj12rFpEgKnCKZq+ISB2WC9xpOIoDXzgJQMaIpVixWrlJnmR5+8atXqklWX1zxTFBfQg5oytdHY6o2m3X+4Y8fNugc4ibgTyNztplTKaF6ORkC5RvAmS4OC0bduE3pHwspLNfvHH/07u375JcunXdI5AQn05NgePXxgayurchv68JOP7aO7d2znwUOrH5NN9G1lec1WajUx6SFO4lKUKRbtpHtsGdZBFPHD/AJcr9WW8w7Z3fbFbdtcW9eaxSjj4wd3bGBZe/GlV2xjdUMUAEQTaVow2VYslfX5sS9DFQP9ee6DiMzkWgH28IOzL/G8VK9hiVFLjHXEG8EN0XJn/cF+TxBAINIG3Gz/YMeK+aw9efxIQ9QRBmC2EAoPxGBuEZmfzD2yOc03vvfeH+3xkz02oQ4mJhDKxZKtrK1qfXK/4Nu5vEzerDewd/71t9YZ9r+4AQv1TcEH0Zs0dAeppTklI+CpbgtdDNk3ZYJ4nQPiLHQ3rHRly0ga9ZIGiZScOn4xcyOYwTNBkcBHWVyv6U8W5QS9gmdpHrDC8PkCyz0afTh/bb5RT3G05sDWAiM+lKUMvNYqtrm+adlc0XLFogT3MBhAIheTBMB/sBIGdiMhNgbIU2YRYUyLTcb7u778QlT+HNb//2OAYiEwqk1eKlqtvKQShy7o8tqaQG8yyMcHB1Y/qau0ZCqODAGjhM2VVVuqlZySwowielldJzJXqzU7t7Vl6ys1e/GFl6xSrCoLX11ZU/bHvacreVw/0DNYrS3pde7df6DgdVw/tiuXLusAQ64lTkpwZDLewprRHAiNHEmF4zjtc7Xcx7XVVdEkCDZgjQf7h+rArm9ShuZcxiZQc7QW1ciXsrq1+mPrDuCFwaVbtmoVFYRgdcYhkUpIrholWEr61GRoJXDA0IzIl+BvQWoGcFfOrcBx86N37ffv/c4Oj460X8hokYtGvyxDVj4dW6nipiB0INc3tuzcuW37xc9+Zcf1hrI17jsBnHJ0c3NLWNbu7q6rtwb8F4ed3/7iV9bVzOYXDcMSeIVdt4CX+YEviRCYuM4jUqDSlL53Syv56ozUKda0SIZubBoDA4uQDQqZjkyhIKttH54lM6tU01bJmYiZzTbkTBcr/LzRHdLAfKEsUDSWgbGj5Bs8Tg3EgBW4WqElOwftQ4Y1w7JCFEsk7NK1q/bWV9+yWmXVVs9flD74wf6OZyCJhFVrnOJ5u3P7Exv1kX5xEJiTe0hto87QVBlJJp+xjfVNq9aK9vDRbY3NnF/ftKWlqq71YL9uu3uHliuVrVpbEVh+dLBro35TlANKK5RCIxCt6QipXvj1i3cld4vZFIj/d/xzKInPZnPFcsWuX3/ZNje29Aw4sTXvKc5U3paQac5AWxjasDv0+T5m2XI5u3jxkl3bvmRlMg8aKMHZ+/LlK5bLF13aZpy0k+Nju33nIwX18+e27PDwyO7cu6s1cO78OatWqtqMfAaNDEG/aPmIEljayuqyVZaWLJUuSP314YMdOzmsW63iGKm7FaU0DZCUaQZmLih4jq0dsny3l3eLNqIaBFKoGaMxTQYGwIeWT5H95xUgINYibUVWs7a2KsVhOtU8Y4JYKV8OrHcgD7erI7t/uHPHfvXrtxXsOP3r9aY6fSi6gkFhMJE0xA6HtrSyalevXrdJf2zv/uF9e7y/b1O07tNJBTjgFZQ8KsWiPdnbVYcd/A2YopTN2Ns/+Yl1Bl/ggMWsF92OOPJCpuX4ixMJtZBDsBKWE1i/kcoXZyE5WX1xaCAznJYjPQhSaWIDC5OfQTGSE9I7T7B9ffBWFIfPI8vSri94AFSmFSkOM4zqs3ysOPI0Ixh6/9n38WLQSiRsZW3Nfvh3/6ATMF8o2mG9YfX6vjAFMK3l1fOWnCbs7p3bzvKejK1QLFuttmwHB0cCuCkXen0CzVjWTEtLNTvY35eUS66YF6OZ0/SkgdNL0zY21m1zfV2ET+RJTtpNG/YG6gACoJOl0TV0fATWdl5M65P6iVyWOdWhDnASH9fr1mgcW7/X1rwb5q8A0J+5r0mT+sMLV6/JVNbnI5kXTMkdmT+ieyX5X7CroQ8rYwL70pWrdmV7W1QBzS6m3JWZO3pu64LwOa6b+wPJlYDDcyejqjebtraxaYVsQRgO3DVwODIPPk+r23G5IXhlw5EVSyWNxxzuH4opDrZExkZpy/UwOoSKKwYyYFGSmgn+hsr6JyZ3I4HoGHlAjwkD7pOEW5fRCVxZqilojfpDy+Sh09DFDHLIrH+uiVEbLOqC9I7K4MlIw/Mf3LwhCSCaMsx2cj/pgNN8TaehVfjI1cVLV+za5asKdu+9957dvn1HQR9ZZrqRYFhkq+tra9ZuNm3vYF+jPW5CPLHf/Oxt66JE+4XMsEKWBVkxzk6S3sdRnZmcS6A2CGiEzBa0oPizTnmNSri5qPg8Q6Q3nAhHpsbISfS/QxJXJhuUhEiZoPct+okPij9TnTSmCRqtwnK8FFjerjwZg5IUXAO5a14KRnA+cmxOEbLmwQvme6Vi/+Gf/1kjO/BlOm2UXz3LYNMtVWsasHn48Il882jzL62s2Pr6qt3b2REnyZMcMhK0mF6wVpO29r7AYFJ+WNN0rDT31u7Y1tambW2sSQUAWZlGg2HmkdrsLiRH16o0kxqhJJddVbcrV2E4O4wK8X18EMFRREqlpZ+EvNixxtGePXn8wPqoLCzAd/lS0a6//JrlilWboHyJAWgOHlBCDHe+KNMY0cHubHv7vF25uG1bK2saLmaDkfFCbgWj2dy8AEnLei1oGGMrlYrWajVsb2/f9vae2FHjxF5+9RXdR7rIaE+Rpe88fKCABo2FIM978vfwpEqlih3VkSf2sSTJ5CRTVsznRMY96vQ1IyicTkaGjsnSTeMeaRRKTSMyooTGlSQLJPPhtA1Qd8jl7er5DRdFRAm1WPTDFRUPnKwxnC34CJHWepKOIVXIRM/2gw9uSHdfCqW4YWfTUg1GD595UcpbDrILF7ft0rkLath89Mkn9sH777uGvNQn5JphS7Ul297eFuv/waNHdlR3rJHZxQ9++zvrMCb2RQ5YahSJmzWXhpmVEfK4S+o0AxRMTJLOG2EwVR0W75o5j8tBYpQuFbA4maR1lbZStqyHyxFIp4nRA2VVYbBa7GE2Bwz/z8N35DEILlZwRnGgNjio7oS9yIpW6ci4xozQG0D1WYZ1um4C/P13P/qReFm0znyigYWdC5QA3g+BOjAWOukjycFUy2V78GjPDg6PJcHCTVleXbetrQ27deuWS5IECRhKSve389k4Ghebq6vaYMjvttsE8yCvi7lB2qVevBlC69sDrtyOB2hXZay2VJSyQbPe0iaV9JhUJFLCG/kcRwdP7PanH1jjYHdeJSbMXnzlVdt+4TWJ/dGhxBBDzlK4RqOOKXwSxYSSbWys2dVLF6xCpy+Tthcvv2CrS0tSuuDkJ7BB0/BZOsxDfeQEz8Abf/zADuvH9uqXviTQHXZ5s9m0Bw8e2Hsf37JUpiiWtwaokdkp5GylVrFGo2NHzNsRrBTEzQqFohXzGTtpnthxs2uFUs2DCxbvE4agRzpogCsgu4o0khSvwyolnFbRlWcGMmvlclXdOFm2QajOkwG7pyABTcPhuZyyY29MIf3MfF8YZ+q19DlEVcA6W1ZfrGWCLlIxLXUmOWQZ41oquW77zVuf2I0/3hDdhxlVDVoXcgrSy1WwP8rLuqYaWkgS7e3Zzd+/6+NcX9iAxdJVwHJWcaiT9C9tEAiH1M/5rFxBEC1rtZh98uBESTdJji2bzFg2lVVG0B2gfDlQINOoSrViNZEQp3bQaFm71bcB0rcLqhBsSJUSqGay6CIW86xWHWTNQkkbYlbyhflFJ7LOO4HzgOXBK3Adwr9PdxKzxaL94z/+o61hHxW88zREnCn6JYX/x7nJnBlAKlki4RZ5FDztkLYeT1OSXKlVq/LTgyMFgM3vaVSKjDKokK6u4D9Y1DDtIVLInb7UPwiMPBKxeqL9JR20cE8cw/JBa6mK4s48dm6bqJNwuqAraLphKsD75o3f2eN7t04FrNfe+LJdu/5VZRnMHroLNhMPBAd03Alc2KaXbHW1alcubFpZ2uxjBcnlCkHlREGW94adTUC7ePGibrXbuSVsb29XGZGE//IFNVzIBu/u3LOHe+g/mfhZG+trdnhyZKtrVXUaj4/rwqlQZOABEBhRAyXrrNePrNVoyyeQKKmsnuwGnh86YGoDOkufw4XQhSpq0E+RVRiZjQ7bCesV/DapoFnIMAmSFslWSg3STHNvSMm/yBKcUtTH8dSiijQayT6P1IDohjIXblspzKvyg48fPbH3b9zQey8t1ywN3oWyxfqWFbJ58e74XYimKD08eHDffvfOO7KA+2IHrEheDlP8UZtdMhhBqI5SzjMqz4QIZPyZKXd6TAQ7ziQ6aNAVZHAGwF4s2FK5bDks0HMJ291vWLMTZhWDhI9GD5CITWeFCfGQwBk+r2tIVwejzRmeFQe3z8wURn6Mzx1G7GoWfU6FxEqtZj/60b+3jZV1bVYWB6ce7xPZB1GKJc48LVIkAK6XKlUFEsZQCOoaZFXprBmCIFXkCpO0sTgQMFAl+Hc6XWmFU55pjEnjGy49Iz4cjZDgNSCjTgingTcXRDdOTQTIz04k1IHMC377m5/boIddVfhKmn37e39r25euiZ4CjuOkSA/sZEwYU0wnBKysLS+V7cLWqpWL+AO61X0hkw2dW0TmcL0hUJitr2+IQ0SGHvW6KP8YBObfUoiYTKTIuXvUFchfKOZtc3PV6kdHVqkUlHE/enxkqTRqED4qBFi/ulaxlVrRdh7uWqM+kfwLhyjrlOvsdBrCjHxSI5B7OZgVLHO6lzpoQr8pg68A2mYBJyTDqpULtra0rMMPPDFOaUAoFidP3LuE+GviwwX9M2Kamt4BT2NW0V/3tKQKjRlmA8nIgQVyhZws6PAT4AVYr2TR4JAcZp/c+th+/atffcFLwgVsKJUJ6S6gu/hO/peS1ghD0lOSH4nWRfKmW6OzWbQh2Zhhyp4HjAqjJszE3k04GB9E1lSMKbiAjQEqehCUHhNe4M9ivy9eczotCZAoPeMuRAsZVqRAaAE5oh9Hd2aAe6QCJMyuvXjV/v4HP5JrCmUFOJXriSMbQrhxYq1/1pnaYRiOR12CElrSjZ4ZBTWFeH+mjIs41927Wrok72Lp1KTU0zWKGWcTHRRDy1Bap1PWH42ti+UVQ/qYSCToYPF8IKvyMmSRPrLiwh2YkA7t3p1P7Be/+KmA3Hm5b3bhyhX77t/8gy0tr+v9XUfdA3s0ynDqgXflqtWCba3VrAgvCAcf2ve4XWOhBW+rWLRKpaoASbDhSYD9wEeivCFAPbh/X/hduULzJGn37t2zthyUlbfa8tKy8KtCLmnDPsTMkaywyNaV8Q16Vq2krZzN2FGrZS3BcmE4PzjjDLp9USMkrug0Ua1pnoMmMXg/sCnGYxS4gr9iaBqRFJYKWStlCZRpS/E7ov84XiuBBL0eFAhXPzn9Fb8RYIogsUSZ6JJS3tHmc5J10rFlSsRneAnkURzT9xmd+t//4Q/2y5//7AtMHF28w9L7DsqhYQ95+QMgPx8qFhF+QTI5/GjQfnL+jN9stzWPgS7+TgTEfezCN4UyCfG+/P0JBJwoZFuf+6UFiJxLPOW9qzkr3Ra7h7Oh6dAlXGz/hw/y3b/5nn3rG3+lkkWMcQ0OmwcvNNAlb+0dTa6TDadyQQPd3qGKnyeRdk9FuopgdWxfBacgUYSCgtQtAi6iUQ+ZmxDgUcwszYToaGiQ/WDC2R64fjuFDSYUuRyHgf+e2Nfc1ykb1B1vPvrju/ab3/xUZN1QC+m25sqUv/9kL1x9RXbolDuoT3h2O7WBZvRcCtoziqQ28cZqxUoFxnP8EJLevbDCiZRcl2vM5k2s3Wq5mw4ZVhiOLxQKtn9wKLwGIT62tcQIkyk7rjfFaCcwMSQgFCko1vo4DMHFO8upKaYZCXkx9ieh2cOgtpRHoC646qsG+Bn2ZuZQMuIEBDdC1TUroLmevWeWLrFErpVLIwzounjYrNGpo+MoC68w6sPvYaYbg6EORP1dlJsMY0G8dDCXIZhruXGAjxmOrrvrc6no32PNjb0jLG/G0A3/7b/+xn75i7elFOu6al/kL02hQDlAPylmTz6uI/NLbVKfXfM0ZZ7eeubiOkSnpJM13R6Hpx1f8KQ7vIRe02fqtCCD2QXYEfgDpeXzPJUorewteJcHWeRpzbKqkGV5MAv/LDzzbCFr/+Gf/6O9dP2l0Eb2WTYyvmajY45UuMKohnZDkahPFLIuOebIcRlQXai83kEBK3RT1b2ThjoZFlMBcbDTmxR8ZZJZWypXFAAbSLhIkdabB3oW4f6nU3QRHcdjkftC9tnD5GRsv/jpj+3Dmx+4iufCVzqfse985/v25S+/KcIlG7kr01OClAdS4TXBkdlLmpQV8pRKeZ9xW5g6cIUTj4e0/rm/yOFEqSLuV6kM/aPmKiFgR5mMHHA+/vgTEVfBqxDP4zUyctOOoo3RPX1OSuZ3CR4ckG5k7W0WeQ/iLoRJqWSqWV/uwCxRQdnOzyWdFGjInFLIK9Ply4hzR9DC/5FRLB/lcW9HvWdwRiLIQ1sgCEcCbOR9zWCCIFmjAzlolmHnBgueA5r1gq0bncFC3vmF0rGHxAv2qIDlwfWnP/mZ/du/vaMO6hcbw4oLWZQBL/98PMTrdH8IXgoRqLz84hD2BRQNYl3pwrMIiZ7FkZI45+WRwzddGI/wkybIUavsobTBiICMpvfcIoYqP6XqgMonRFZX3lBGN9N9XwhUTzmerr38kn3/u9+39ZV1b0AEfTEoBh0aAWoS+FgAHVMpH4SgJR2msXNvBLwqo/IANyPUInniwiNBfoSNFNQMQjdSFnNqwwNWozgArSCw9plv1H1n5i+UfbyLdKNcKJEXJNvbubdj//rLf7HDJw8/cwxXVmv2ta//lb1w9VV1PzW7qHLcy/mZIm7wKvCGjGOSeQIPM2+UScJk4kTBvARScJP7De7FA8e8NQcJkxuLrLIklwG72bD7e/tBkM89CH3g2zvQ6lqGkksxMcBrcQ26dM7YhpNBWDshw9QYkt8LzecxTsMzGc2VRaRDF0whdNCBE4ayPM4bLiq4xI4zFIVen3XGMzLLZ6aWF0/K17HcwBGzJDsMXom6O5H/x/oMUk9gYwhDMiurMjvILzuuC/4o+3a91ts/+5n97ne/+18B69SKDhQHL+X8JvvpGcHixZ/2mTeX/g2pcGCVs1B8aBjpmpTlCxlnz1vCOl1mrNxzTltMv+wRRJ2gSQBpRwSsZ1iDnd2G2uB5P5FEY3Bxt5hp+Wc5k1ktBq1kwja3ztmFrXOWS8OxQUXBsYYxpZbKVe8mUSJRtgjLDUPJriflAnh8UQ7n6TJli5bJYO4ZU3vlaJ6N6OD2IOhWTiFYKYqxczM2TWKrEDu2obzUrGbAz0KjQ92qEBDfffcP9s6vfzIXXVs4kFbObdhff+v7tn3xiuSC4ryifiRgPOqORueiYCScQhMrkVTHkYxOp38IIHFWMVylc+6kuTUQcRh6kRQrgosUrG0IoT5mZWr7u7kqpFt0JoLzU/zkWktB+C8+x/CZuAQIpkN5B571s/QrihhczFDj96J5yCxI6DX9ENKREgxXZ3y+kOXy56ZGhnBtBk8cWQ8JIWV5SSlwCJCfNXk4EFgDBGDP6Hi+7A0CIrwqJKYJ4uKMya8wGKbENWIJ+8mPf2wf3Lhhw8kXlen+mbPX8WDmmmLW85kfcRDrab85AxI1LjMD7GO5F1QfVGbMI4XjBkGgzUewZ68t63eNOzzj7c58m06RbN0V/9wAY5bhRHJ7fK0zr4kb9PUXX7bq0rJKBzAVzlvxaUZTSwhgD4C9gPcYSF3jMkkpEwKiZyoAvv553HRkbBNqjBCb2RTOsHCNd7dsnNpY+k2e5RCwkkHbPJmY43ngVWwAAogHZ28oHD55YruPIRoefvaGpcyqKyt24cIl6cWjLyVXnyjOF8QReXDSZFIJ5VQUb4sgphPliz2vhJ4h+zOCNHyvVMIKKYTmKJXclAPAGqwKO3bnhTlJGZIrBE0Ge4XZoLCgTpwrfCp+BtzMAXHvmMYzR68TAjklGg7bKsNnlW/g4sXnLI5hNEEO1YNOjbAcZ0PiHrSEnYlL4j8US16/32Pr9kfWGXHAUJEM5R4k4F8Zmmf3+gwKYXxmDj2/eh1Y6t4mNWrFgDejRVBEFDw1iO3X4X6YYG1m/9f/8X/K9Vlu2F9oHtaZ5U1ZyKJ9dmCa/8Kp8BMGkaN2vB8Oc1/DCGqGc29WHHn8i8f1/LXdJ5DF8JwRS5mNg6K+6YMg4ymF0vD6Cy9ZKJfsa299065dfTGUHxLIDQvQAejI3wrLL2RJoRwKQUWv7JJLs3jGaRwXnYJU+JjyBoiW9wugmhIrZ8PObkTMYLQJHEOeyZbwc62Tpt388AO79elHAtkXgXX+G2LrK699yV66/qoY2g48L9zngBUhOxz1wVBWVRNAWUXQdhI2KKuIgOq4Um2sdFQ2ggFReoHRiRiVEPDOHJ0wpUDDcFc8MiMv/8i4xSsT4DzWRtawvYKkg89+EM19ETLqrJ4mC88ltd07MjZ7xBtUpuYQhDLEMEom6of+HKCQ8HP+1ouVRXDEFlE6Y2PaHmgZ8jpBktzXHLLNwbHaARLxubwsnPWIXVZIn3/k0sgioxLM0cJyPIuue7fdtbt379i7775r9ZPjsMy+6KD7QtDiIcyF++YrO1IH/nS+szCALDaD79BY3/temx1rfvMD2z0ufA9oXmpSKigl/7wZw3BRfu1uvOCLe05l0EaPrxPjQdLs5Ve/ZG+8+XUrF8tB8wpDzSiM7S/sOB0ALC8QHJ/PuPhoNYaTn8xI+1W0BE+r6HzFze4M/6DoqmuJ9l/h/eI9ifZb0UUmXDdXgfrmRzc/tPfe/Z21GvXPZqJJs62LF+x7f/O3VihUnBAaTu1oMeU3ystRne5JV/1UKNBzcjkgnfahxPEcgQONzYyAnlMH/FlmghtN6CbzShN0utipnvF6oAifk2ck2XGyCsdE9eSTjs2hGupG7Rwa9PMiBuiZh152FkgdeNdGn9FuwmcD/PYJVn8WIWsUbjWe2niUkKehMNqkm8C6xDllMwKCckTz5yv5Z8fXyIbSdIOF8XqJF7EuX+dBejrcIZoDA4bNYclOUTphvMtBdKmdQAeBd6c2NDZkuCv1ZGmmUSY61bqmL6Jaw7MiT8pE4KN7pHZ22CSzMz+YQnhiNG9pn9VaX3x5MYMFeC1mDr6D9f0FHCv+HqcULWwGij9PL2v2XrwUPLFgFupDveFvY7SYlQlmhUrJLr9wzZaXV0JGGZQQwsIbgqiEoWw0i3CloXQJ6967nJzQoiqw72MJ5e8pPfoJ82gB/I92bioTXNLEf87Hm3zagJLBO4eih0imy7ll8K84hQF2Hz16aI92H/jiP3WzPc6sbm7YV15/w3IpN++kctPr09oKnnwMPLNpUbyUsULoZqmlTkmslIQr8pLUHb6l6hh4Xl6a2hRAT4y6UPZE7h231TvPCvpBS02HH3dT3UC+PVTZHTO4yHdTIND/uf75TAspBCt1tGUC4wGN2KuAMZt4DcZnKr399aMnFqGEAKaRn3CohKcWMtmgIBJKYT1eP369DOeeBFVaxyU9kKkkBNEMpidhWXgDJX4eXajTNmZZZ4BLBA2oKSUnC9FMsE776OaN4HSkeYnnRUqetcv/f/T9tMnFt1yqeCs4+hbNGO5+zukBnarWPC9yIN7LJT9f+HJynUYlZlkW/8VGDJvhVHfSFx5A7AET63Fc53lusxjN/j6nAtaZGcVirWQ//OE/2Hf++vuWTgTdo0CSVQkBLSOA6OxJ6SABsqfZrEHKONZ40hBPWBpdcnW62Jww+iPwDyYCIZaSx3XYhVXwGZHABcNBVwybLpUYXhLAUep3+jYY4Lk3sJ07d+w3P/nvdryH8NtTvjJml65u21e+8lUFm8GoZcPAPPf3Hatc4VSXFlnfpXAYwqajx+bA1NcGXF8A3JIBJ9MmCYqoQfE0GlY4UI95RV4dQkZcpF2fzlgmiX4aGZlnIlFvXtw5yRmA1fn8HRIvbquVltEFxgs4N3u2QtePWAlW6eWdiMczIxj4Uk7Bkcz0DKByIN3FqZ0f5Wk92ZVWo42VIfG8eFGmDCJIwSHh5Z2QvJANk+vxOwE/9wxS5XNaGBPy3+hgYYyhzqtggqmNIfhq67jhhUe+9Mw6kNfRLGb0kwAbm0yt0+7Yhx+8a0eN45D1/a+ANV/9YbiYEsm7QdGe07Mhpe0S+o+ZEd8P8sohGM1a+dF8doFsGgFMD1xO5pvxeCSkRqDwjIUy4cmTPbnvPPdXqAICZ/B0dzAG2JTZl7/yhv2nf/pPtlRbDQuc5CMpMNmNOHx63jt5vtnYLIHyfCbwxh6ZX+W8s7pwW9UNDBthxluKUdQpDF4q+v2IHoz8BTN3//t/+6/287d/Kmusp32tX9iw//xf/ou99dWvu/eebG4cQ+Nrhj2F9/Zb4aWfPmZkruiHgyKntOhdjVQDV7MMAh0wLx7F3JZUi9MEFBhlYRWySvGaIjfOPz+cvjgLyTf60f0meAJOxz6nyhC9uFYaZEaz3n0DCfaMDQ1HrhIBf8vxNkpJ+FxT2Z1BQNbkRCwbRcnyAAxehvTOlIH7cGwymeGUHKWTyhgJwARe7zdNLUX3l0CrZwU+lfH+bsqNWiKeJQJxWDfyHFXGmtd9TiUhq2Ieyz3ix9AuxwAAAB5JREFUEHD+mZ5HCHAqDuA+TibSCtu5/an00eDY/d/5bSCz8Ors4AAAAABJRU5ErkJggg==";
		
		uploadPhotoThumbBase64(imgQualityBS64,"thumb");*/
		
        var res = CaptureFinger(quality, timeout);
        if (res.httpStaus) {
              flag = 1;
            if (res.data.ErrorCode == "0") {
                let thumbBase64=res.data.BitmapData;
                thumbBase64=thumbBase64.substring(thumbBase64.indexOf(",") + 1);
                //document.getElementById('imgFinger').src = "data:image/bmp;base64," + res.data.BitmapData;
                uploadPhotoThumbBase64(thumbBase64,"thumb");
                var imageinfo = "Quality: " + res.data.Quality + " Nfiq: " + res.data.Nfiq + " W(in): " + res.data.InWidth + " H(in): " + res.data.InHeight + " area(in): " + res.data.InArea + " Resolution: " + res.data.Resolution + " GrayScale: " + res.data.GrayScale + " Bpp: " + res.data.Bpp + " WSQCompressRatio: " + res.data.WSQCompressRatio + " WSQInfo: " + res.data.WSQInfo;
                
            }else{
                //device not found
                alert(res.data.ErrorDescription);    
            }
        }else {
            alert(res.err);
        }
    }
    catch (e) {
        alert(e);
    }
    return false;
}

function backToMRMChecklist(actualTaskId,appNo,serviceId){
	var requestData = {
			"appNo" : appNo,
			"taskId":serviceId,
			"actualTaskId":actualTaskId
		}
		var ajaxResponse = __doAjaxRequest('MarriageRegistration.html?showDetails', 'POST',requestData, false, 'html');
		$('.content-page').html(ajaxResponse);	
}

