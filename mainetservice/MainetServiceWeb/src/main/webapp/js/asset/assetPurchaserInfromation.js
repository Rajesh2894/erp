$(document).ready(function() {

	$("#dateOfAcquisition").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date(),
		onSelect: function(selected) {
			$("#licenseDate").datepicker("option","maxDate", selected);
	      } 
	});

	$("#dateOfAcquisition").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
	$("#licenseDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date()
	});

	$("#licenseDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('.decimal').on('input', function() {
		this.value = this.value.replace(/[^\d.]/g, '') // numbers and decimals
														// only
		.replace(/(\..*)\./g, '$1') // decimal can't exist more than once
		.replace(/(\.[\d]{2})./g, '$1'); // max 2 digits after decimal
	});

	$('#assetPurchase').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});

	$("#purchaseOrderDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date()
	});

	$("#purchaseOrderDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#astCreationDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date()
	});

	$("#astCreationDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
	
	$("#initialBookDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : new Date()
	});

	$("#initialBookDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
	$("#warrantytilldate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate:new Date() //D #32538
		//maxDate : new Date()
	});

	$("#warrantytilldate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	
	
	
	
});
//T#101105
function validateAssetPurchaserInformation(errorList){
	
	var purchaseOrderNo = $("#purchaseOrderNo").val();
	var costOfAcquisition = $("#costOfAcquisition").val();
	var bookValue = $("#bookValue").val();
	var dateOfAcquisition = $("#dateOfAcquisition").val();
	var purchaseOrderDate = $("#purchaseOrderDate").val();
	var astCreationDate = $("#astCreationDate").val();
	var initialBookDate = $("#initialBookDate").val();	
	var warrantytilldate = $("#warrantytilldate").val();
	
	var pgName =$('#atype').val();
	
	if(pgName == 'IAST' && (purchaseOrderNo == "0" || purchaseOrderNo == undefined || purchaseOrderNo == null || purchaseOrderNo == "")){
		errorList.push(getLocalMessage("asset.purchase.purchaseOrderNo"));
	}
	if (costOfAcquisition == "0" || costOfAcquisition == undefined
			|| costOfAcquisition == '') {
		
		if(pgName == 'AST'){			
			errorList.push(getLocalMessage("asset.vldnn.costOfAcquisition"));
			}else{
			errorList.push(getLocalMessage("asset.vldnn.purchaseValue"));
			}
	}
	if (dateOfAcquisition == "0" || dateOfAcquisition == undefined
			|| dateOfAcquisition == '') {
		
		if(pgName == 'AST'){
			errorList.push(getLocalMessage("asset.vldnn.dateofacquisition"));
			
			}else{
			errorList.push(getLocalMessage("asset.vldnn.dateOfPurchase"));
			}
	}
	if (dateOfAcquisition != "0" && dateOfAcquisition != undefined
			&& dateOfAcquisition != '') {
		errorList = validatedate(errorList, 'dateOfAcquisition');
	}
	if (purchaseOrderDate != "0" && purchaseOrderDate != undefined
			&& purchaseOrderDate != '') {
		errorList = validatedate(errorList, 'purchaseOrderDate');
	}
	/*Task #5318
	 * if (initialBookDate == "0" || initialBookDate == undefined
			|| initialBookDate == '') {
		errorList.push(getLocalMessage("asset.vldnn.initial.date"));
	}*/	
	if (purchaseOrderDate != "0" && purchaseOrderDate != undefined
			&& purchaseOrderDate != '' && dateOfAcquisition != "0"
			&& dateOfAcquisition != undefined && dateOfAcquisition != '') {
		errorList = dateValidation(errorList, 'purchaseOrderDate',
				'dateOfAcquisition');
	}
	
if (astCreationDate != "0" && astCreationDate != undefined
			&& astCreationDate != '' && dateOfAcquisition != "0"
			&& dateOfAcquisition != undefined && dateOfAcquisition != '') {
		errorList = dateValidation(errorList, 'astCreationDate',
				'dateOfAcquisition');
	}

if (dateOfAcquisition != "0" && dateOfAcquisition != undefined
		&& dateOfAcquisition != '' && initialBookDate != "0"
		&& initialBookDate != undefined && initialBookDate != '') {
	errorList = dateValidation(errorList, 'dateOfAcquisition',
			'initialBookDate');
}	

if (dateOfAcquisition != "0" && dateOfAcquisition != undefined
		&& dateOfAcquisition != '' && warrantytilldate != "0"
		&& warrantytilldate != undefined && warrantytilldate != '') {
	errorList = dateValidation(errorList, 
			'dateOfAcquisition','warrantytilldate');
}	

if (warrantytilldate != "0" && warrantytilldate != undefined
		&& warrantytilldate != '' && astCreationDate != "0"
		&& astCreationDate != undefined && astCreationDate != '') {
	errorList = dateValidation(errorList, 'astCreationDate','warrantytilldate'
			);
}
return errorList;
}
function saveAssetPurchaserInformation(element) {
	var errorList = [];
	errorList = validateAssetPurchaserInformation(errorList);
		
	if (errorList.length > 0) {
		$("#errorDivP").show();
		showErrAstPurch(errorList);
	}

	else {
		var divName = '#astPurch';
		//T#92465
		var targetDivName = $('#moduleDeptCode').val() =='AST' ? '#astRealEstate' : '#astSer';
		var mode = $("#modeType").val();
		elementData = null;
		var requestData = __serializeForm('#assetPurchase');

		if (mode == 'E') {
			elementData = element;
		} else {
			elementData = 'html';
		}

		var response = __doAjaxRequest(
				'AssetRegistration.html?saveAstPurchPage', 'POST', requestData,
				false, '', elementData);
		// document.getElementById(divName).style.display = "none";
		// $(divName).css("display", "none");
		if (mode == 'E') {
			editModeProcess(response);
			prepareDateTag();
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

function showErrAstPurch(errorList) {

	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBoxAstPurch()"><span aria-hidden="true">&times;</span></button><ul>';
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

function closeErrBoxAstPurch() {
	$('.warning-div').addClass('hide');
}

function backFormAstPurch() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AssetRegistration.html');
	$("#postMethodForm").submit();
}

function setVendorCode() {
	var selectedType = $("#vmVendorid").find("option:selected").attr('code');
	$("#vendor").val(selectedType);
}

function resetPurchase() {
	
	var divName = '#astPurch';
	var requestData = {
		"resetOption" : "reset"
	}
	var response = __doAjaxRequest('AssetRegistration.html?showAstPurchPage',
			'POST', requestData, false, '', 'html');
	$(divName).html(response);
}