$(document).ready(function() {
	chosen();
/*	$("#serviceStartDate").datepicker({
		dateFormat : 'dd/mm/yy',
		numberOfMonths : 1,

		onSelect : function(selected) {

			$("#serviceExpiryDate").datepicker("option", "serviceStartDate", selected)

		}

	});*/
	/*$("#serviceStartDate").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });*/
/*
	$('.decimal').on('input', function() {
		this.value = this.value.replace(/[^\d.]/g, '') // numbers and decimals only
		.replace(/(\..*)\./g, '$1') // decimal can't exist more than once
		.replace(/(\.[\d]{2})./g, '$1'); // max 2 digits after decimal
	});*/
/*
	$("#serviceExpiryDate").datepicker({
		dateFormat : 'dd/mm/yy',
		numberOfMonths : 1,

		onSelect : function(selected) {

			$("#serviceStartDate").datepicker("option", "maxDate", selected)

		}

	});*/
	/*$("#serviceExpiryDate").keyup(function(e){
	    if (e.keyCode != 8){    
	        if ($(this).val().length == 2){
	            $(this).val($(this).val() + "/");
	        }else if ($(this).val().length == 5){
	            $(this).val($(this).val() + "/");
	        }
	     }
	    });*/
	/*$("#serviceDataTable").dataTable(
			{
				"oLanguage" : {
					"sSearch" : ""
				},
				"aLengthMenu" : [ [ 5, 10, 15, -1 ],
						[ 5, 10, 15, "All" ] ],
				"iDisplayLength" : 5,
				"bInfo" : true,
				"lengthChange" : true
			});*/
	//D#72263
	showAndHides();
});

function saveRealEstateInfo(element)
{
	var errorList = [];
	if ($('input:checkbox[id=isRealEstateApplicable]').is(':checked') )
		{
	var propertyRegistrationNo  = $("#propertyRegistrationNo").val();
	
	if(propertyRegistrationNo ==null || propertyRegistrationNo  == undefined
			|| propertyRegistrationNo == '')
	{
		errorList.push(getLocalMessage("asset.real.estate.vldnn.property.registrationNo"));
	}
		}
	if (errorList.length > 0) {
		$("#errorDivS").show();
		showErrAstRealEst(errorList);
	}
	else
	{
		var requestData = __serializeForm('#assetRealEsate');
		var elementData = null;
		
		var divName = '#astRealEstate';
		var targetDivName = '#astSer';
		var mode = $("#modeType").val();
		
		if(mode == 'E'){
			elementData = element;
		}else{
			elementData = 'html';
		}
		
		
		var response = __doAjaxRequest('AssetRegistration.html?saveAstRealEstatePage',
				'POST', requestData, false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		if (mode == 'E') {
			if ($.isPlainObject(response))
			{
				
				var message = response.command.message;
				displayMessageOnSubmit(message);
			}
			else
				{
				var message ="Error: Asset Serial Number is Required and Asset Provider is required"
					displayMessageOnSubmitForEdit(message);
				}
		} else {
			$(divName).removeClass('ajaxloader');
			//D#34059
			let parentTab =  '#assetParentTab';
			if(mode == 'D'){//Draft
				parentTab = '#assetViewParentTab';
			}
			processTabSaveRes(response, targetDivName, divName,parentTab);
			prepareDateTag();
		}

	}
	
	
}

function showErrAstRealEst(errorList) {

	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBoxAstRealEstate()"><span aria-hidden="true">&times;</span></button><ul>';
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
	
	return false;
}

function closeErrBoxAstRealEstate() {
	$('.warning-div').addClass('hide');
}

function backFormAstSer() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AssetRegistration.html');
	$("#postMethodForm").submit();
}

function resetService() {
	
	var divName = '#astSer';
	var requestData = {
			"resetOption":"reset"
	}
	var response = __doAjaxRequest('AssetRegistration.html?showAstSerPage',
			'POST', requestData, false, '', 'html');
	$(divName).html(response);
}
//this is for adding service 
/*function createData(count,element) {
	
	var requestData = {
			'count': count
	}
	var response = __doAjaxRequest('AssetRegistration.html?addServicePage','POST',requestData,false,'html');
	var divName = '.child-popup-dialog';					
	$(divName).removeClass('ajaxloader');
	$(divName).html(response);
	showMsgModalBox(divName);
	prepareDateTag();


}*/



/*function saveAssetServiceInformation(element) {
	
	
	var errorList = [];
	var serviceNo = $("#serviceNo").val();
	var serviceProvider = $("#serviceProvider").val();
	var serviceStartDate = $("#serviceStartDate").val();
	var serviceExpiryDate = $("#serviceExpiryDate").val();
	if (serviceStartDate != "0" && serviceStartDate != undefined
			&& serviceStartDate != '') {
		errorList = dateValidation(errorList,'serviceStartDate','serviceExpiryDate');
		errorList =  validateFutureDate(errorList , 'serviceStartDate');
		
	}
	if (serviceExpiryDate != "0" && serviceExpiryDate != undefined
			&& serviceExpiryDate != '') {
		errorList = dateValidation(errorList,'serviceStartDate','serviceExpiryDate');
		errorList =  validateFutureDate(errorList , 'serviceExpiryDate');
		
	}
	if(serviceNo == "0" || serviceNo == undefined
			|| serviceNo == '')
		{
		errorList.push(getLocalMessage("asset.realEst.serialno"));
		}
	if(serviceProvider == "0" || serviceProvider == undefined
			|| serviceProvider == '')
		{
		errorList.push(getLocalMessage("asset.service.provider"));
		}
	var mode = $("#modeType").val();
	if (errorList.length > 0) {
		$("#errorDivS").show();
		showErrAstRealEst(errorList);
	}

	else {
		var divName = '#astSer';
		var targetDivName = '#astInsu';
		var requestData = __serializeForm('#assetService');
		var elementData = null;
		
		if(mode == 'E'){
			elementData = element;
		}else{
			elementData = 'html';
		}
		
		var response = __doAjaxRequest('AssetRegistration.html?saveAstSerPage',
				'POST', requestData, false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		if (mode == 'E') {
			if ($.isPlainObject(response))
			{
				
				var message = response.command.message;
				displayMessageOnSubmit(message);
			}
			else
				{
				var message ="Error: Asset Serial Number is Required and Asset Provider is required"
					displayMessageOnSubmitForEdit(message);
				}
		} else {
			$(divName).removeClass('ajaxloader');
			processTabSaveRes(response, targetDivName, divName);
			prepareDateTag();
		}
		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		$('.nav li#service-tab').removeClass('active');
		$('.nav li#insurance-tab').addClass('active');
	}
}*/
/*

function closeErrBoxAstSer() {
	$('.warning-div').addClass('hide');
}

function backFormAstSer() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AssetRegistration.html');
	$("#postMethodForm").submit();
}

function resetService() {
	
	var divName = '#astSer';
	var requestData = {
			"resetOption":"reset"
	}
	var response = __doAjaxRequest('AssetRegistration.html?showAstSerPage',
			'POST', requestData, false, '', 'html');
	$(divName).html(response);
}
//this is for adding service 
function createData(count,element) {
	
	var requestData = {
			'count': count
	}
	var response = __doAjaxRequest('AssetRegistration.html?addServicePage','POST',requestData,false,'html');
	var divName = '.child-popup-dialog';					
	$(divName).removeClass('ajaxloader');
	$(divName).html(response);
	showMsgModalBox(divName);
	prepareDateTag();


}



function editASTService(elements) {
	
	 var divName = '.child-popup-dialog';					
	$(elements).removeClass('ajaxloader');
	//$(divName).html(elements);
	showMsgModalBox(elements);
	//$("#Applicant").show()
	
}
function showMsgModalBox(childDialog) {
	
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic', // 'elastic', 'fade' or 'none'
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});

	return false;
}*/

function displayMessageOnSubmitForEdit(message) {
	
	var errMsgDiv = '.msg-dialog-box';
	var cls = 'Proceed';

	var d = '<h5 class=\'text-center text-red padding-5\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceedForEdit()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function proceedForEdit(){
	
		$.fancybox.close();
	
}

// This code is for show and hide of detail when checkbox clicked

function showAndHides() {
	if ($('input:checkbox[id=isRealEstateApplicable]').is(':checked')){
		$("#hideAndShowDeatils").show();
		$("#realEstateLbl").addClass("required-control");	
	}else{
		$("#hideAndShowDeatils").hide();
		$("#realEstateLbl").removeClass( "required-control");
	}
}

