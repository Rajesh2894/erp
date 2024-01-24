
$(document).ready(function() {
	$("#txtFromDate").datepicker({
		dateFormat : 'dd/mm/yy',
		numberOfMonths : 1,
		onSelect : function(selected) {
			$("#txtToDate").datepicker("option", "minDate", selected)
		}
	});
	$("#txtFromDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#txtToDate").datepicker({
		dateFormat : 'dd/mm/yy',
		numberOfMonths : 1,
		onSelect : function(selected) {
			$("#txtFromDate").datepicker("option", "maxDate", selected)
		}
	});
	$("#txtToDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#txtFromDate1").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate: new Date()
	});

	$("#txtFromDate1").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#txtToDate1").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
	$("#txtToDate1").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	$('#informationTabForm').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});
	$("#acquisitionMethod").change(function(e) {
		
		if ($('#acquisitionMethod option:selected').attr('code') == "LE") {
			//D#74016
			//$('.nav li#leasing-comp').find('a').attr("data-toggle", "tab");
			
			//$('.nav li#leasing-comp').removeClass('disabled');
			
		} else {
			$('.nav li#leasing-comp').find('a').removeAttr("data-toggle");
			$('.nav li#leasing-comp').addClass('disabled');
		}

	});

	$("#assetGroup").change(function(e) {
		//D#80698
		if ($('#assetGroup option:selected').attr('code') == "L") {
			//$('.nav li#linear-tab').find('a').attr("data-toggle", "tab");
			//$('.nav li#linear-tab').removeClass('disabled');
		} else {
			$('.nav li#linear-tab').find('a').removeAttr("data-toggle");
			$('.nav li#linear-tab').addClass('disabled');
		}
	});

	$('.decimal').on('input', function() {
		this.value = this.value.replace(/[^\d.]/g, '') // numbers and decimals
		// only
		.replace(/(\..*)\./g, '$1') // decimal can't exist more than once
		.replace(/(\.[\d]{2})./g, '$1'); // max 2 digits after decimal
	});
	var statusType = $("#assetStatus option:selected").attr("value");
	 $("#assetStatusId").val(statusType);
	//T#85539 
	let astTypeCode = $('#assetClass2 option:selected').attr('code');
	if(astTypeCode== 'B'){
		$("#buildPropId").show();
	}else{
		$("#buildPropId").hide();
	}
	 
	$("#manufacturingYear").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate: new Date()
	});
	
	$('.itAssetClass1').hide();
	showAndHideBulkExport();
	addAlphNumClass(); //Defect #164663
});
//Defect #164663
function addAlphNumClass() {
	var htmlLang = $('html').attr('lang');
	var inputSelector = $('#remark');
	if (htmlLang == 'en') {
		inputSelector.addClass('alphaNumeric');
	}
}

function manageDependentTabs() {
	if ($('#acquisitionMethod option:selected').attr('code') == "LE") {
		// 
		// $('.nav li#leasing-comp').find('a').attr("data-toggle", "tab");
		// 
		$('.nav li#leasing-comp').removeClass('disabled');
		// 
	} else {
		// $('.nav li#leasing-comp').find('a').removeAttr("data-toggle");
		$('.nav li#leasing-comp').addClass('disabled');
	}

	if ($('#assetGroup option:selected').attr('code') == "L") {
		// $('.nav li#linear-tab').find('a').attr("data-toggle", "tab");
		$('.nav li#linear-tab').removeClass('disabled');
	} else {
		// $('.nav li#linear-tab').find('a').removeAttr("data-toggle");
		$('.nav li#linear-tab').addClass('disabled');
	}
}
//T#101105
function saveITAssetForm(element) {
	var errorList = []
	
	errorList = validateAssetPurchaserInformation(errorList);
	errorList = validateInformationDetails(errorList);
var dateOfAcquisitionP = $('#dateOfAcquisition').val()
var manufacturingYearP = $('#manufacturingYear').val()
		if (manufacturingYearP != undefined
				&& manufacturingYearP != ''  && dateOfAcquisitionP != undefined
				&& dateOfAcquisitionP != '') {
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			  var eDate = new Date(manufacturingYearP.replace(pattern,'$3-$2-$1'));
			  var sDate = new Date(dateOfAcquisitionP.replace(pattern,'$3-$2-$1'));
			  if (eDate > sDate) {
				    errorList.push(getLocalMessage('asset.manifYear.purchaseDate')); 
				  
			  }
			
		}
/*if(manufacturingYearP > dateOfAcquisitionP){
	errorList.push(getLocalMessage(""));
}*/
if ($('input:checkbox[id=isServiceInfoApplicable]').is(':checked')){
	
	var serviceNo = $("#serviceNo").val();
	var serviceProvider = $("#serviceProvider").val();
	var serviceStartDate = $("#serviceStartDate").val();
	var serviceExpiryDate = $("#serviceExpiryDate").val();
	
	var pgName =$('#atype').val();
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
	/*if(serviceNo == "0" || serviceNo == undefined
			|| serviceNo == '')
		{
		errorList.push(getLocalMessage("asset.service.serialno"));
		}*/
	if(serviceProvider == "0" || serviceProvider == undefined
			|| serviceProvider == '')
		{
		if(pgName == 'AST'){
			errorList.push(getLocalMessage("asset.service.provider"));
			}else{
			errorList.push(getLocalMessage("asset.vldnn.service.provider"));
			}
		}
	
	if($("#modeType").val() == 'E' && pgName == 'IAST' && (($('#astSerListSize').val() != undefined) && $('#astSerListSize').val() > 1)){
		if (serviceStartDate == undefined || serviceStartDate == '') {
			errorList.push(getLocalMessage("asset.service.vldn.mnt.startDate"));
		}
		
		if (serviceExpiryDate == undefined || serviceExpiryDate == '') {
			errorList.push(getLocalMessage("asset.service.vldn.mnt.expiryDate"));
		}
		if (serviceStartDate != undefined
				&& serviceStartDate != ''  && serviceExpiryDate != undefined
				&& serviceExpiryDate != '') {
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			  var eDate = new Date(serviceStartDate.replace(pattern,'$3-$2-$1'));
			  var sDate = new Date(serviceExpiryDate.replace(pattern,'$3-$2-$1'));
			  if (eDate > sDate) {
				    errorList.push(getLocalMessage('asset.service.vldn.startDateAndExpiryDate')); 
				  
			  }
			
		}
	}
}
if ($('input:checkbox[id=isBulkExport]').is(':checked') && $("#modeType").val() == 'C'){
	var quantity = $('#quantity').val();
	if (quantity == undefined || quantity == '' ||  quantity == null) {
		errorList.push(getLocalMessage("asset.information.quantity.empty"));
	}
	
	
}
	if (errorList.length > 0) {
		$("#errorDivP").show();
		showErrAstInfo(errorList);

	} else {
		$("#errorDivP").hide();
		
		var pgName =$('#atype').val();
		var divName = '#errorDivP';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/
				
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
		var ajaxResponse = __doAjaxRequest('AssetRegistration.html?validateSaveITAstForm', 'post', requestData,
				false, 'json');
		if (ajaxResponse.length > 0) {
			
			$("#errorDivP").show();
			displayErrorsOnPage(ajaxResponse);
		}else{
			if ($('input:checkbox[id=isBulkExport]').is(':checked') && $("#modeType").val() == 'C'){
				var response = __doAjaxRequest(
						'AssetRegistration.html?saveITAstFormBulk1', 'POST', requestData,
						false, '', elementData);
			}else{
				var response = __doAjaxRequest(
						'AssetRegistration.html?saveITAstForm', 'POST', requestData,
						false, '', elementData)
			}
			
		window.scrollTo(0, 0);
		
		if (mode == 'E') {
			editModeProcess(response);
			prepareDateTag();
		} else {
			$(divName).removeClass('ajaxloader');
			var tempDiv = $('<div id="tempDiv">' + response + '</div>');
			var errorsPresent = tempDiv.find('#validationerror_errorslist');
			$.fancybox.close();
			if (!errorsPresent || errorsPresent == undefined
					|| errorsPresent.length == 0) {		
				
				if(errorsPresent.length > 0) {
					$(divName).html(response);
					$("#errorDivP").show();
					prepareDateTag();
				}else {
					if ($.isPlainObject(response))
					{
						var message = response.command.message;
						showMessageOnSubmit(response,message,'AssetRegistration.html');
					}
				}
		}else{	
				
				$("#errorDivP").show();
				prepareDateTag();
			
		}
		}
		}
	}
}
function saveAssetInformation(element) {
	var errorList = []
	errorList = validateInformationDetails(errorList);
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);

	} else {
		$("#errorDiv").hide();
		
		var pgName =$('#atype').val();
		var divName = '#astInfo';
		var targetDivName = null;
		/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/
		if(pgName == 'AST'){
			 targetDivName = '#astClass';
		}else{
			 targetDivName = '#astPurch';
			//targetDivName = '#astClass';
		}
		
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
				'AssetRegistration.html?saveAstInfoPage', 'POST', requestData,
				false, '', elementData);
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
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
				let parentTab =  '#assetParentTab';
				if(mode == 'D'){//Draft
					parentTab = '#assetViewParentTab';
				}
				var disabledTab = $(parentTab).find('.disabled');
				console.log("removing diabled" + disabledTab);
				if (disabledTab) {
					$(disabledTab).removeClass('disabled');
					manageDependentTabs();
					console.log("removed disabled");
				}
				//$('#assetParentTab a[href="#astClass"]').data('loaded', true);
				/*pageredirection as if it is of Asset then it will redirect to location tab else it is of ITAsset then redirect to purchaseTab*/
				if(pgName == 'AST'){
					$(''+parentTab+' a[href="#astClass"]').tab('show');
				}else{
					$(''+parentTab+' a[href="#astPurch"]').tab('show');
				}
				
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

function backFormAstInfo(moduleDeptCode) {
	//T#92465
	var saveMode =  $('#saveMode').val();
	if (saveMode == 'C'){
		var searchURL= moduleDeptCode == 'AST' ?  "AssetSearch.html":"ITAssetSearch.html";
		var response = __doAjaxRequest(searchURL + '?searchAsset', 'POST', {},
				false, 'html');
		var dialogId = $('#viewAssetPage').parent().attr('id');
		$('#'+dialogId).html(response);
	}else{
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', moduleDeptCode == 'AST' ?  "AssetSearch.html":"ITAssetSearch.html");
	$("#postMethodForm").submit();
}
}

function backToDashBoard() {
	
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AdminHome.html');
	$("#postMethodForm").submit();
}

function validateInformationDetails(errorList) {

	var assetName = $("#assetName").val();
	var serialNo = $("#serialNo").val();
	var statusType = $("#assetStatus option:selected").attr("value");
	var purpose = $("#purpose").val();
	var assetDetails = $("#details").val();
	var assetClass = $("#assetClass2").val();
	var assetClassification = $("#assetClass1").val();
	var txtFromDate = $("#txtFromDate").val();
	var txtToDate = $("#txtToDate").val();
	var txtFromDate1 = $("#txtFromDate1").val();
	var txtToDate1 = $("#txtToDate1").val();
	var modeType = $("#modeType").val();
	//var assetType = $("#assetType").val();
	//var assetGroup = $("#assetGroup").val();
	var acquisitionMethod = $("#acquisitionMethod").val();
	var rfiId = $("#rfiId").val();
	var assetModelIdentifier = $('#assetModelIdentifier').val()
	var pgName =$('#atype').val();
	/*errorList = validateDuplicateName();*/
	if(rfiId!=undefined && rfiId!=null && rfiId!='')
		{
	    errorList =duplicateCheckRfIdNo();
		}
	if (serialNo != "0" && serialNo != undefined && serialNo != '')
		{
		if(pgName == 'AST'){
		errorList = validateDuplicateSerialNo();
		}else{
			
			if(validateDuplicateSerialNo().length>0){
				errorList.push(validateDuplicateSerialNo());
			}
		}
		}
	//Acc to discussion with RushiRaj Registered serial no is not mandatory
	/*if(pgName == 'IAST' && ( serialNo == undefined || serialNo == '')){
			errorList.push(getLocalMessage('asset.vldnn.registeredSerialNo'));
		}*/
		if(pgName == 'IAST' && ( assetModelIdentifier == undefined || assetModelIdentifier == '')){
			errorList.push(getLocalMessage('asset.vldnn.serialNo'));
		}

	if (assetName == "0" || assetName == undefined || assetName == '') {
		errorList.push(getLocalMessage('asset.vldnn.assetname'));		
	}

	if (purpose == "0" || purpose == undefined || purpose == '') {
		errorList.push(getLocalMessage("asset.vldnn.purpose"));
	}

	/*if (serialNo == "0" || serialNo == undefined || serialNo == '') {
		errorList.push(getLocalMessage("asset.vldnn.assetSerailNo"));
	}*/

	if (statusType == "0" || statusType == undefined || statusType == '') {
		errorList.push(getLocalMessage("asset.vldnn.statusType"));
	}

	/*if (assetType == "0" || assetType == undefined || assetType == '') {
		errorList.push(getLocalMessage("asset.vldnn.assetType"));
	}
*/
	if (assetDetails == "0" || assetDetails == undefined || assetDetails == '') {
		errorList.push(getLocalMessage("asset.vldnn.assetDetails"));
	}

	/*if (assetGroup == "0" || assetGroup == undefined || assetGroup == '') {
		errorList.push(getLocalMessage("asset.vldnn.assetGroup"));
	}*/

	if (acquisitionMethod == "0" || acquisitionMethod == undefined || acquisitionMethod == '') {
		
		if(pgName == 'AST'){
			errorList.push(getLocalMessage("asset.vldnn.assetAcquisitionMethod"));
			
			}else{
				errorList.push(getLocalMessage("asset.vldnn.assetPurchaseMethod"));
			}
	}

	if (assetClass == "0" || assetClass == undefined || assetClass == '') {
	 
		if(pgName == 'AST'){
			errorList.push(getLocalMessage("asset.vldnn.assetClass"));
			
			}else{
				errorList.push(getLocalMessage('asset.vldnn.hardWareName'));
			}
	}
    if(pgName == 'AST'){
	if (assetClassification == "0" || assetClassification == undefined
			|| assetClassification == '') {
		errorList.push(getLocalMessage("asset.vldnn.assetClassification"));
	}
    }
	if (txtFromDate != "0" || txtFromDate != undefined || txtFromDate != '') {
		errorList = validatedate(errorList, 'txtFromDate');

	}
	if (txtToDate != "0" || txtToDate != undefined || txtToDate != '') {
		errorList = validatedate(errorList, 'txtToDate');

	}
	if (txtFromDate1 != "0" || txtFromDate1 != undefined || txtFromDate1 != '') {
		errorList = validatedate(errorList, 'txtFromDate1');

	}
	if (txtToDate1 != "0" || txtToDate1 != undefined || txtToDate1 != '') {
		errorList = validatedate(errorList, 'txtToDate1');

	}
	return errorList;
}
/*// it will provide duplicate validation if it is present
function validateDuplicateName() {
	
	var errorList = [];
	var assetName = $("#assetName").val().trim();
	var requestData = {};
	requestData = {
		assetName : assetName
	};

	var actionParam = "validateDuplicateName";
	var url = "AssetRegistration.html" + '?' + actionParam;
	var obj = "";
	var response = __doAjaxRequestForSave(url, 'post', requestData, false, '',
			obj);
	var duplicate = response["errMsg"];
	if (duplicate != '') {
		errorList.push(duplicate);
	}
	return errorList;
}*/

function validateDuplicateSerialNo() {
	
	var errorList = [];
	var mode = $("#modeType").val();
	var assetId = $("#assetId").val();
	var serialNo = $("#serialNo").val().trim();
	var requestData = {};
	if(mode=='C')
		{
	if(assetId!=undefined && assetId!='')
		{
	requestData = {
		serialNo : serialNo,
		assetId:assetId
	};
	}
	else{
		requestData = {
				serialNo : serialNo,
			};	
	}
	var actionParam = "validateDuplicateSerialNo";
	var url = "AssetRegistration.html" + '?' + actionParam;
	var obj = "";
	var response = __doAjaxRequestForSave(url, 'post', requestData, false, '',
			obj);
	var duplicate = response["errMsg"];
	if (duplicate != '') {
		errorList.push(duplicate);
	}
		}
	return errorList;
}

// it is onblur event when the user wants to put duplicate it will give warning
/*function validateDuplicateAssetName(obj) {
	
	var errorList = [];
	errorList = validateDuplicateName()
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);
	}
}*/

function validateDuplicateSerialNumber(obj) {
	
	var errorList = [];
	var serialNo = $("#serialNo").val().trim();
	if(serialNo != "0" && serialNo != undefined && serialNo != '')
		{
	errorList = validateDuplicateSerialNo()
		}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);
	}
}

function resetInfo() {
	$('#astInfo').find('input[type=text]').val('');
	$('#assetEmpId').find('option').removeAttr('selected').end().trigger('chosen:updated');
	$('#acquisitionMethod').find('option').removeAttr('selected').end().trigger('chosen:updated');
	$('#astParentId').find('option').removeAttr('selected').end().trigger('chosen:updated');
}
$("#resetItAsset").click(
		function() {
			var requestData = {
				"type" : "C"
			}
			var ajaxResponse = __doAjaxRequest(astURL
					+ '?form', 'POST', requestData, false,
					'html');
			$('.content-page').html(ajaxResponse);
			prepareDateTag();
		});



//this is for checking the duplicate of  the rf id 
function duplicateCheckRfId(obj) {
	
	var errorList = [];
	errorList = duplicateCheckRfIdNo()
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErrAstInfo(errorList);
	}
}

function duplicateCheckRfIdNo() {
	
	var errorList = [];
	var rfiId = $("#rfiId").val().trim();
	if(rfiId!=null && rfiId!=0 && rfiId!=undefined )
	{
	var requestData = {};
	requestData = {
			rfiId : rfiId
	};
	var actionParam = "duplicateCheckRfIdNo";
	var url = "AssetRegistration.html" + '?' + actionParam;
	var obj = "";
	var response = __doAjaxRequestForSave(url, 'post', requestData, false, '',
			obj);
	var duplicate = response["errMsg"];
	if (duplicate != '') {
		errorList.push(duplicate);
	}
	}
	return errorList;
}

function getClassification(element) {
	
	var types=$('#assetClass2 option:selected').attr('data-othervalue');
	$('#assetClass1 option[code = "'+types+'"]').prop('selected',true);
	 var assetClassification = $("#assetClass1").val();
	 $("#assetClassification").val(assetClassification); 
	//T#85539
	 let astTypeCode = $('#assetClass2 option:selected').attr('code');
		if(astTypeCode== 'B'){
			$("#buildPropId").show();
		}else{
			$("#buildPropId").hide();
		}

		}



function showAndHideBulkExport() {
	if($('#atype').val() == 'IAST'){
	
	if ($('input:checkbox[id=isBulkExport]').is(':checked')){
		//id-'hideAndShowDeatils' to hideAndShowServiceInfo
		//id name changing because id-'hideAndShowDeatils' already added in DOM structure with display :none
		$('#bulkExport').show();
		$('#quantityDiv').show();
		$('#serialNo').attr("disabled",true);
		//$("#serviceAppLbl").addClass("required-control");	
		
	}else{
		$('#bulkExport').hide();
		$('#quantityDiv').hide();
		$('#quantity').val('');
		$('#serialNo').attr("disabled",false);
		//$("#serviceAppLbl").removeClass( "required-control");
	}
	}
	
	
}
