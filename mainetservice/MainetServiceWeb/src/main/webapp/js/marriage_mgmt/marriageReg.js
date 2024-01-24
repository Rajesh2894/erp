$(document).ready(
		function() {

			
			$("#marDate").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				maxDate : new Date()
			});

			$("#marDate").keyup(function(e) {
				if (e.keyCode != 8) {
					if ($(this).val().length == 2) {
						$(this).val($(this).val() + "/");
					} else if ($(this).val().length == 5) {
						$(this).val($(this).val() + "/");
					}
				}
			});
			
			$('#appointmentDate').datepicker({
		    	minDate: 0, 
		    	dateFormat: 'dd/mm/yy'
			});
			
			$("#appointmentDate").keyup(function(e) {
				if (e.keyCode != 8) {
					if ($(this).val().length == 2) {
						$(this).val($(this).val() + "/");
					} else if ($(this).val().length == 5) {
						$(this).val($(this).val() + "/");
					}
				}
			});
			
			$('a[data-toggle="tab"]').on('hide.bs.tab', function(e) {
				if ($(e.relatedTarget).parent().hasClass('disabled')) {
					e.preventDefault();
					return false;
				}
			});

			$('a[data-toggle="tab"]').on('show.bs.tab',function(e) {
						if (!$(e.target).data('loaded')) {
							var response = __doAjaxRequest(
									'MarriageRegistration.html?'
											+ $(e.target).attr(
													'data-content-param') + '',
									'POST', {}, false, 'html');
							$($(e.target).attr('href')).html(response);
							prepareDateTag();							
						}
					});

			//set system date on application date
			if($('#modeType').val() == 'C'){
				$("#appDate").datepicker({
					dateFormat: "dd/mm/yy"			
				}).datepicker("setDate", new Date());	
			}
			
			
			//D#122612 Translator	 
			   var langFlag = getLocalMessage('admin.lang.translator.flag');
				if(langFlag ==='Y'){
					$("#placeMarE").keyup(function(event){
						var no_spl_char;
						no_spl_char = $("#placeMarE").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'placeMarR',event,'');
						}else{
							$("#placeMarR").val('');
						}
					});
					
					$("#priestNameE").keyup(function(event){
						var no_spl_char;
						no_spl_char = $("#priestNameE").val().trim();
						if(no_spl_char!=''){
							commonlanguageTranslate(no_spl_char,'priestNameR',event,'');
						}else{
							$("#priestNameR").val('');
						}
					});
				}
				
				//D#127376
				//doing below code only when form fill by pressing tab one by one than it create PBLM (mask not work) so adding below like code it work
				$('#pinCode').bind('click keyup', function(event) {
					$.mask.definitions['~'] = '[+-]';
					$('#aadharNo').mask('9999-9999-9999');

				});
				
				$("#aadharNo").click(function(){
					$.mask.definitions['~'] = '[+-]';
					$('#aadharNo').mask('9999-9999-9999');

				});
			
			

		});

var tabcheck = false;
var applicantResponse;

function resetMarriageInfo(){
	/*$('#marriageTabForm').find('input[type=text]').val("");
	$('#lastName').val('');
	$('#marriageTabForm').find('input[type=textarea]').val("");
	$('#personalLaw').find('option').removeAttr('selected').end().trigger('chosen:updated');*/
	var divName = '#marriage';
	var requestData = {
		"resetOption" : "reset"
	}
	var response = __doAjaxRequest('MarriageRegistration.html?showMarriagePage',
			'POST', requestData, false, '', 'html');
	$(divName).html(response);
}

function showPreviousTab(previousTab, alternateTab) {
	if(!$('#assetParentTab a[href='+previousTab+']').parent().hasClass('disabled')) {
		$('#assetParentTab a[href='+previousTab+']').tab('show');
	} else {
		$('#assetParentTab a[href='+alternateTab+']').tab('show');
	}
	$('html,body').animate({ scrollTop: 0 }, 'slow');
}

function processTabSaveRes(response, nextTab, currentDiv,parentTab) {
	var tempDiv = $('<div id="tempDiv">' + response + '</div>');
	var errorsPresent = tempDiv.find('#validationerror_errorslist');
	
	if(!errorsPresent || errorsPresent == undefined || errorsPresent.length == 0) {
		$(''+parentTab+' a[href='+nextTab+']').tab('show');
		
		var errorPreviousTab = $(currentDiv).find('#validationerrordiv');
		if(errorPreviousTab.length > 0){
			var divError = $(currentDiv).find('#validationerrordiv');
			$(divError).addClass('hide');
		}
	} else {
		//window.scrollTo(0, 0);
		$(currentDiv).html(response);
		prepareDateTag();
	}
	$('html,body').animate({ scrollTop: 0 }, 'slow');
}

/* ----- JS to highlight the visited link starts ----- */
var $tabLink = $('#mrmParentTab li a[data-toggle="tab"]');
$tabLink.on('shown.bs.tab', function (e) {
    var href = $(e.target).attr('href');
    var $curr = $("#mrmParentTab a[href='" + href + "']").parent();
    $('#mrmParentTab li').removeClass();
    $curr.addClass('active');
    $curr.prevAll().addClass('form-filled');
    $curr.nextAll().addClass('disabled');
});
/* ----- JS to highlight the visited link ends ----- */

function showTab(tabId) {
	$('#mrmParentTab a[href='+tabId+']').tab('show');
	$('#mrmViewParentTab a[href=' + tabId + ']').tab('show');
	$('html,body').animate({ scrollTop: 0 }, 'slow');
}

function navigateTab(id, tabToSubmit, element) {
	if (!($('.nav li#' + id).hasClass("disabled"))) {
		
		var divName = '.tab-pane';
		var response = __doAjaxRequest('MarriageRegistration.html?' + tabToSubmit
				+ '', 'POST', {}, false, 'html');
		
		$(divName).removeClass('ajaxloader');
		
		$(divName).html(response);
		prepareDateTag();
		var activeTab = $('#mrmParentTab').find('.active');
		if(activeTab)
			$(activeTab).removeClass('active');
		$('.nav li#' + id).addClass('active');
		
	}
	return false;
}

function disableTab() {
	
	tabcheck = false;
	$('.nav li#husband-tab').find('a').removeAttr("data-toggle");
	$('.nav li#husband-tab').addClass('disabled');

	$('.nav li#wife-tab').find('a').removeAttr("data-toggle");
	$('.nav li#wife-tab').addClass('disabled');

	$('.nav li#witness-tab').find('a').removeAttr("data-toggle");
	$('.nav li#witness-tab').addClass('disabled');
	
	$('.nav li#appointment-tab').find('a').removeAttr("data-toggle");
	$('.nav li#appointment-tab').addClass('disabled');

	
}

function enableTab() {
	
	tabcheck = true;
	$('.nav li#husband-tab').find('a').removeAttr("data-toggle");
	$('.nav li#husband-tab').removeClass('disabled');

	$('.nav li#wife-tab').find('a').removeAttr("data-toggle");
	$('.nav li#wife-tab').removeClass('disabled');

	$('.nav li#witness-tab').find('a').removeAttr("data-toggle");
	$('.nav li#witness-tab').removeClass('disabled');

	$('.nav li#appointment-tab').find('a').removeAttr("data-toggle");
	$('.nav li#appointment-tab').removeClass('disabled');

	

}

/*Start of Marriage Tab CODE*/



function saveMarriage(element) {
	
	var errorList = [];
	errorList = validateApplicantInfo(errorList);
	errorList = validateMarriageDetails(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);

	} else {
		$("#errorDiv").hide();
		var divName = '#marriage';
		var targetDivName = '#husband';
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
		
		applicantResponse = __doAjaxRequest(
				'MarriageRegistration.html?saveMarriagePage', 'POST', requestData,
				false, '', elementData);
		//$('#marriage-tab').addClass('active');
		//D#128818 start
		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + applicantResponse + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');
		
		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			
		}else{
			$(divName).html(applicantResponse);
			prepareDateTag();
			return false;
		}
		//D#128818 end
		
		//D#127929
		if($('#applicableENV').val() == 'true'){
			let yes = getLocalMessage('mrm.draft.appNo.popup.continue');
			$(targetDivName).html(applicantResponse);//for capture application no
			let warnMsg= getLocalMessage('mrm.draft.appNo.popup') +" "+ $('#applicationNo').val();
			 
			 message	='<p class="text-blue-2 text-center padding-5">'+ warnMsg+'</p>';
			 message	+='<div class=\'text-center padding-bottom-10 margin-top-10\'>'+	
			'<input class="btn btn-success" type=\'button\' value=\''+yes+'\'  id=\'yes\' '+ 
			' onclick="onClose(\''+mode+'\')"/>'+	
			'</div>';
			$(childDivName).addClass('ok-msg').removeClass('warn-msg');
			$(childDivName).html(message);
			$(childDivName).show();
			$('#yes').focus();
			//showModalBox(childDivName);
			showPopUpMsg(childDivName);
			return false;
			
		}else{
			openNextTab(mode);
		}
		
	}
}

function showPopUpMsg(childDialog) {
	$.fancybox({
        type: 'inline',
        href: childDialog,
        openEffect  : 'elastic', 
        closeBtn : false ,
        helpers: {
			overlay : {
				closeClick : false
			}
		},
		 keys : {
			    close  : null
			  }
    });
	return false;
}

function onClose(mode){
	$.fancybox.close();
	openNextTab(mode);
}

function openNextTab(mode){
	let divName = '#marriage';
	let targetDivName = '#husband';
	let response = applicantResponse;
	window.scrollTo(0, 0);
	if (mode == 'E') {
		editModeProcess(response);
		prepareDateTag();
	} else {
		$(divName).removeClass('ajaxloader');
		var tempDiv = $('<div id="tempDiv">' + response + '</div>');
		var errorsPresent = tempDiv.find('#validationerror_errorslist');
		//D#110063
		let errorCode = tempDiv.find(".flipInX h3").first().text();
		
		if(errorCode != "" && errorCode != undefined ){
			$(divName).html(response);
			prepareDateTag();
			return false;
		}
		if (!errorsPresent || errorsPresent == undefined
				|| errorsPresent.length == 0) {
			$(targetDivName).html(response);
			let parentTab =  '#mrmParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#mrmViewParentTab';
			}
			
			var disabledTab = $(parentTab).find('.disabled');
			if (disabledTab) {
				$(disabledTab).removeClass('disabled');
				//manageDependentTabs();
			}
			$(''+parentTab+' a[href="#husband"]').tab('show');
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


function showErrAstInfo(errorList) {

	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBoxAstInfo()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);
	$(".warning-div").removeClass('hide')
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	errorList = [];
}

function closeErrBoxAstInfo() {
	$('.warning-div').addClass('hide');
}

function backFormMRMInfo() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'MarriageRegistration.html');
	$("#postMethodForm").submit();
}

function backToDashBoard() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AdminHome.html');
	$("#postMethodForm").submit();
}

function validateApplicantInfo(errorList) {
    var applicantTitle = $.trim($('#applicantTitle').val());
    var firstName = $.trim($('#firstName').val());
    var lastName = $.trim($('#lastName').val());
    var mobileNo = $.trim($('#mobileNo').val());
    var areaName = $.trim($('#areaName').val());
    var pinCode = $.trim($('#pinCode').val());
    var emailId = $.trim($('#emailId').val());

    if (applicantTitle == "" || applicantTitle == '0'
	    || applicantTitle == undefined) {
	errorList.push(getLocalMessage('mrm.applicant.validate.title'));
    }
    if (firstName == "" || firstName == undefined) {
	errorList.push(getLocalMessage('mrm.applicant.validate.first.name'));
    }
    if (lastName == "" || lastName == undefined) {
	errorList.push(getLocalMessage('mrm.applicant.validate.last.name'));
    }
    if (mobileNo == "" || mobileNo == undefined) {
	errorList.push(getLocalMessage('mrm.applicant.validate.mobile.no'));
    } else {
	if (mobileNo.length < 10) {
	    errorList.push(getLocalMessage('mrm.validate.mobile.number.lenght'));
	}
    }
    if (areaName == "" || areaName == undefined) {
	errorList.push(getLocalMessage('mrm.applicant.validate.area.name'));
    }

    if (pinCode == "" || pinCode == undefined) {
	errorList.push(getLocalMessage('mrm.applicant.validate.pincode'));
    }
     else if (pinCode.length < 6|| pinCode.length > 6) {
		errorList.push(getLocalMessage("mrm.validation.pincode1"));
	}
	else if (pinCode < 400000 || pinCode > 446000) {
		errorList.push(getLocalMessage('mrm.applicant.validate.pincode1'));
		}
	
    if (emailId != "") {
	var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	var valid = emailRegex.test(emailId);
	if (!valid) {
	    errorList.push(getLocalMessage('mrm.applicant.validate.email.invalid'));
	}
    }
    return errorList;
}

function validateMarriageDetails(errorList) {
	
	let ward = $("#marriageTabForm select[id=ward1]").find(":selected").val();
	let marDate = $("#marriageTabForm input[id=marDate]").val();
	let placeMarE = $("#marriageTabForm textarea[id=placeMarE]").val();
	let placeMarR = $("#marriageTabForm textarea[id=placeMarR]").val();
	let personalLaw = $("#marriageTabForm select[id=personalLaw]").find(":selected").val();
	let priestNameE = $("#marriageTabForm input[id=priestNameE]").val();
	let priestNameR = $("#marriageTabForm input[id=priestNameR]").val();
	let priestFullAddr = $("#marriageTabForm textarea[id=priestAddress]").val();
	let priestReligion = $("#marriageTabForm select[id=priestReligion]").find(":selected").val();
	let priestAge = $("#marriageTabForm input[id=priestAge]").val();
	if(ward == "" ||  ward =="0" || ward == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.ward'));
		}
	if(marDate == "" || marDate == undefined){
		errorList.push(getLocalMessage('mrm.vldnn.marriageDate'));
	}
	if (placeMarE == '' || placeMarE == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.placeMrgE'));
	}
	if(placeMarR == "" || placeMarR == undefined){
		errorList.push(getLocalMessage('mrm.vldnn.placeMrgR'));
	}
	if (personalLaw == "" || personalLaw =="0" || personalLaw == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.personalLaw'));
	}
	if(priestNameE == "" || priestNameE == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.priestNameE'));
	}
	if (priestNameR == "" || priestNameR == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.priestNameR'));
	}
	if(priestFullAddr == "" || priestFullAddr == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.priestAddr'));
	}
	if(priestReligion == "" || priestReligion == undefined || priestReligion =="0") {
		errorList.push(getLocalMessage('mrm.vldnn.priestReligion'));
	}
	if (priestAge == "" || priestAge =="0" || priestAge == undefined) {
		errorList.push(getLocalMessage('mrm.vldnn.priestAge'));
	}else if(!(priestAge>=18 && priestAge<=99)){
		errorList.push(getLocalMessage('mrm.vldnn.priestAgeGreter'));
	}

	return errorList;
}

/*End of Marriage Tab CODE*/


/*Start of Appointment Tab CODE*/

$('.datetimepicker3').timepicker({timeFormat: "HH:mm"});

//this function not use
function submitAppointment(element) {
	var errorList = []
	errorList = validateAppointmentDetails(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);

	} else {
		$("#errorDiv").hide();
		var divName = '#appointment';
		var targetDivName = '#appointment';
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
				'MarriageRegistration.html?submitAppointment', 'POST', requestData,
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
				$(''+parentTab+' a[href="#appointment"]').tab('show');
				
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

function manageDependentTabs() {
	if ($('#acquisitionMethod option:selected').attr('code') == "LE") {
		$('.nav li#leasing-comp').removeClass('disabled');
	} else {
		$('.nav li#leasing-comp').addClass('disabled');
	}

}

function validateAppointmentDetails(errorList) {
	
	let appointmentDate = $("#appointmentTabForm input[id=appointmentDate]").val();
	let appointmentTime = $("#appointmentTabForm input[id=appointmentTime]").val();
	let pageNo = $("#appointmentTabForm input[id=pageNo]").val();
		
	if(appointmentDate == "" || appointmentDate =="0" || appointmentDate == undefined){
		errorList.push(getLocalMessage('mrm.vldnn.appointmentDate'));
	}
	if(appointmentTime == "" || appointmentTime =="0" || appointmentTime == undefined){
		errorList.push(getLocalMessage('mrm.vldnn.appointmentTime'));
	}else{
		//D#117818 regEx for appointment time
		let regexTimePattern =  /^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/;
		if(!regexTimePattern.test(appointmentTime)){
			errorList.push(getLocalMessage('mrm.validation.invalidTime'));
	    }
	}
	if(pageNo == "" || pageNo == undefined){
		//errorList.push(getLocalMessage('mrm.vldnn.pageNo'));
	}
	return errorList;
}

/*End of Appointment Tab CODE*/