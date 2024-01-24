$(document).ready(
		function() {
			
			chosen();
			
			$("#serviceStartDate").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				yearRange : "1900:2200",
				onClose : function(selectedDate) {
					$("#serviceExpiryDate").datepicker("option", "minDate", selectedDate);
				}
			});
			
			
			$("#serviceExpiryDate").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				yearRange : "1900:2200",
				onClose : function(selectedDate) {
					$("#serviceStartDate").datepicker("option", "maxDate", selectedDate);
				}
			});
			
			/*$("#serviceStartDate").datepicker(
					{
						dateFormat : 'dd/mm/yy',
						numberOfMonths : 1,

						onSelect : function(selected) {

							$("#serviceExpiryDate").datepicker("option",
									"serviceStartDate", selected)

						}

					});*/
			$("#serviceStartDate").keyup(function(e) {
				if (e.keyCode != 8) {
					if ($(this).val().length == 2) {
						$(this).val($(this).val() + "/");
					} else if ($(this).val().length == 5) {
						$(this).val($(this).val() + "/");
					}
				}
			});
			
			

			$('.decimal').on('input', function() {
				this.value = this.value.replace(/[^\d.]/g, '') // numbers and
				// decimals only
				.replace(/(\..*)\./g, '$1') // decimal can't exist more than
				// once
				.replace(/(\.[\d]{2})./g, '$1'); // max 2 digits after
				// decimal
			});

			/*$("#serviceExpiryDate").datepicker(
					{
						dateFormat : 'dd/mm/yy',
						numberOfMonths : 1,

						onSelect : function(selected) {

							$("#serviceStartDate").datepicker("option",
									"maxDate", selected)

						}

					});*/
			$("#serviceExpiryDate").keyup(function(e) {
				if (e.keyCode != 8) {
					if ($(this).val().length == 2) {
						$(this).val($(this).val() + "/");
					} else if ($(this).val().length == 5) {
						$(this).val($(this).val() + "/");
					}
				}
			});
			
			$("#manDate").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				minDate : '0d',
				yearRange : "1900:2200",
				onClose : function(selectedDate) {
					$("#serviceStartDate").datepicker("option", "maxDate", selectedDate);
				}
			});
			
			$('.datepicker').datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				minDate : '0d',
				maxDate : '0d',
				yearRange : "-100:+20",
			});

			var dateFields = $('.datepicker');
			dateFields.each(function() {
				var fieldValue = $(this).val();
				if (fieldValue.length > 10) {
					$(this).val(fieldValue.substr(0, 10));
				}
			});

			
			$('#countryOfOrigin1 option[code="IND"]').prop('selected',true);
		});

function saveAstServiceInfo(element) {
	
	var errorList = [];
	var pgName = $('#atype').val()
	var serviceNo = $("#serviceNo").val();
		var serviceProvider = $("#serviceProvider").val();
		var bindingStatus = $("#bindingStatus1").val();
		var serviceStartDate = $("#serviceStartDate").val();
		var serviceExpiryDate = $("#serviceExpiryDate").val();
		let manTypeId = $('#manTypeId').val();
		let manCatId = $('#manCatId').val();
		let dtOfAcqId = $('#dtOfAcqId').val();
		let manDate = $('#manDate').val();
		
	
	if(pgName == 'AST'){
		
		dtOfAcqId = dtOfAcqId.split(' ')[0];//remove time
		if ((compareDate(dtOfAcqId)) > compareDate(manDate)) {
			errorList.push(getLocalMessage("asset.mntDateCompare.requisition"));
		}
		//D#84246
		if (serviceStartDate != undefined
				&& serviceStartDate != ''  && serviceExpiryDate != undefined
				&& serviceExpiryDate != '') {
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			  var eDate = new Date(serviceStartDate.replace(pattern,'$3-$2-$1'));
			  var sDate = new Date(serviceExpiryDate.replace(pattern,'$3-$2-$1'));
			  if (eDate > sDate) {
				    errorList.push(getLocalMessage('asset.service.vldn.startDateAndExpiryDate')); 
				  
			  }
			errorList = validateFutureDate(errorList, 'serviceStartDate');
			errorList = validateFutureDate(errorList, 'serviceExpiryDate');

		}
		if(manTypeId == "0" || manTypeId ==undefined){
			errorList.push(getLocalMessage("asset.service.vldn.mnt.type"));
		}
		if(manCatId =="0" || manCatId ==undefined){
			errorList.push(getLocalMessage("asset.service.vldn.mnt.category"));
		}
		if (serviceNo == "0" || serviceNo == undefined || serviceNo == '') {
			errorList.push(getLocalMessage("asset.service.serialno"));
		}
		if (serviceProvider == "0" || serviceProvider == undefined
				|| serviceProvider == '') {
			errorList.push(getLocalMessage("asset.service.provider"));
		}
		
		
		if (serviceStartDate == undefined
				|| serviceStartDate == '') {
			errorList.push(getLocalMessage("asset.service.vldn.mnt.startDate"));
		}
		
		if (serviceExpiryDate == undefined
				|| serviceExpiryDate == '') {
			errorList.push(getLocalMessage("asset.service.vldn.mnt.expiryDate"));
		}
		if(manDate =="" || manDate ==undefined){
			errorList.push(getLocalMessage("asset.service.vldn.mnt.date"));
		}
		
	}else{
		
		if (serviceProvider == "0" || serviceProvider == undefined
				|| serviceProvider == '') {
			errorList.push(getLocalMessage("asset.service.provider"));
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
			errorList = validateFutureDate(errorList, 'serviceStartDate');
			errorList = validateFutureDate(errorList, 'serviceExpiryDate');

		}
		
		/*if (serviceStartDate == undefined
				|| serviceStartDate == '') {
			errorList.push(getLocalMessage("asset.service.vldn.mnt.startDate"));
		}
		
		if (serviceExpiryDate == undefined
				|| serviceExpiryDate == '') {
			errorList.push(getLocalMessage("asset.service.vldn.mnt.expiryDate"));
		}*/
	}
	
	
	
	if (errorList.length > 0) {
		$("#errorDivAdd").show();
		showErrAstSer(errorList);
	} else {
		
		if (bindingStatus == 'C') {
			$('#addbut').prop('disabled', true);
		}
		var requestData = __serializeForm('#assetServiceAdd');
		var response = __doAjaxRequest(
				'AssetRegistration.html?saveAstServiceInfo', 'POST',
				requestData, false, '', element);
		
		if(pgName == 'AST'){
			if (response != false) {
				var hiddenVal = $(response).find('#bindingStatus1').val();
				if (hiddenVal == 'N') {
					var errorList = [];
					var divName = '.child-popup-dialog';	
					$(divName).removeClass('ajaxloader');
					$(divName).html(response);
				} else {
					/*$.fancybox.close();
					reloadData();*/
					
					$(".serviceInfoPage").removeClass('ajaxloader');
					$(".serviceInfoPage").html(response);
				}
			}
		}else{
			$('.breadcrumb').remove();
			if (response != false) {
				var hiddenVal = $(response).find('#bindingStatus1').val();
				if (hiddenVal == 'N') {
					var errorList = [];
					var divName = '.child-popup-dialog';	
					$(divName).removeClass('ajaxloader');
					$(divName).html(response);
				} else {
					/*$.fancybox.close();
					reloadData();*/
					
					if(pgName == 'AST'){
						$(".serviceInfoPage").removeClass('ajaxloader');
						$(".serviceInfoPage").html(response);
					}else{
						/*$(".assetPurchasePage").removeClass('ajaxloader');
						$(".assetPurchasePage").html(response);*/
						$(".itAssetMain").removeClass('ajaxloader');
						$(".itAssetMain").html(response);
					}
				
				}
			}
		}
		

	}
}

function compareDate(date) {
	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

function CloseFancyBox(element) {
	
	$.fancybox.close();

}
function reloadData() {
	
	var errorList = [];
	
	var requestData = __serializeForm('#assetServiceAdd');
	var table = $('#serviceDataTable').DataTable();
	table.rows().remove().draw();
	$(".warning-div").hide();
	
	var ajaxResponse = __doAjaxRequest('AssetRegistration.html?reloadData',
			'POST', requestData, false, 'json');
	
	var result = [];
	$
			.each(
					ajaxResponse,
					function(index) {
						var obj = ajaxResponse[index];
						result
								.push([
										obj.serviceNo,
										obj.serviceProvider,
										'<div class="text-center">'
												+ '<button type="button" class="btn btn-success btn-sm" onclick="createData('
												+ index
												+ ',this);" title="Add Data" style="text-align: center">'
												+ '<i class="fa fa-pencil"></i></button>'
												+ '</div>' ]);

					});
	table.rows.add(result);
	table.draw();
}

function showErrAstSer(errorList) {
	
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBoxAstSer()"><span aria-hidden="true">&times;</span></button><ul>';
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

function closeErrBoxAstSer() {
	$('.warning-div').addClass('hide');
}

function backToAstSerPageDataTable() {
	var modeType = $("#modeType").val();
	if (modeType == 'E') {
		var ajaxResponse = __doAjaxRequest('AssetRegistration.html?showAstSerPage', 'POST','', false, '', '');
		$('.pagediv').html(ajaxResponse);
	}
}


function backToEdit(element) {
		
		$('.breadcrumb').remove();
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	requestData = __serializeForm(theForm);
			
	var response = __doAjaxRequest('AssetRegistration.html?showEditAssetPage', 'POST',requestData, false, '', '');	
						$(".itAssetMain").removeClass('ajaxloader');
						$(".itAssetMain").html(response);
					
		
		

}
