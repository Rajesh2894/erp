$(document).ready(function() {
	
	chosen();
	$('#assetClassificationId').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});
	$('.decimal').on('input', function() {
		
		this.value = this.value.replace(/[^\d.]/g, '') // numbers and decimals
		// only
		.replace(/(\..*)\./g, '$1') // decimal can't exist more than once
		.replace(/(\.[\d]{6})./g, '$1'); // max 6 digits after decimal
	});


});
function saveAssetClassification(element) {
	var errorList = [];
	//var locCode = $("#functionalLocationCode").val();
	var dept = $("#dpDeptId").val();
	var longitude = $("#classLongitude").val().split(".")[1];
	var latitude = $("#classLatitude").val().split(".")[1];

/*	if (locCode == "0" || locCode == undefined || locCode == '') {
		errorList.push(getLocalMessage("asset.vldnn.locCode"));
	}*/

	if (dept == "0" || dept == undefined || dept == '') {
		errorList.push(getLocalMessage("asset.vldnn.Dept"));
	}
	
	if (longitude != undefined && longitude.length < 2) {
		errorList.push(getLocalMessage('asset.vldnn.longitude'));
	}

	if (latitude!= undefined && latitude.length < 2) {
		errorList.push(getLocalMessage('asset.vldnn.latitude'));
	}

	if (errorList.length > 0) {
		$("#errorDivC").show();
		showErrAstCls(errorList);
	}else {
		var divName = '#astClass';
		var targetDivName = '#astPurch';
		var mode = $("#modeType").val();
		var elementData = null;
		var requestData = __serializeForm('#assetClassificationId');
		if(mode == 'E'){
			elementData = element;
		}else{
			elementData = 'html';
		}
		$(element).attr('disabled',true);
		var loading	 ='<i class=\"fa fa-circle-o-notch fa-spin fa-2x"></i>';
		showModalBoxWithoutClose(loading);
		var delayInMilliseconds = 40000; //1 second

		setTimeout(function() {
		  //your code to be executed after 1 second
		}, delayInMilliseconds);
	
			var response = __doAjaxRequest('AssetRegistration.html?saveAstClsPage',
					'POST', requestData, false, '', elementData);
			
		//document.getElementById(divName).style.display = "none";
		//$(divName).css("display", "none");
		
		if (mode == 'E') {
			editModeProcess(response);	
			prepareDateTag();
		} else {
			$(divName).removeClass('ajaxloader');
			var tempDiv = $('<div id="tempDiv">' + response + '</div>');
			var errorsPresent = tempDiv.find('#validationerror_errorslist');
			
			if (!errorsPresent || errorsPresent == undefined
					|| errorsPresent.length == 0) {
				// $(targetDivName).html(response);
				// $('#assetParentTab
				// a[href='+targetDivName+']').data('loaded',true);
				//#D34059
				let parentTab =  '#assetParentTab';
				if(mode == 'D'){//Draft
					parentTab = '#assetViewParentTab';
				}
				//$(''+parentTab+' a[href=' + targetDivName + ']').tab('show');
				$(''+parentTab+' a[href="'+targetDivName+'"]').tab('show');
				
				var errorPreviousTab = $(divName).find('#validationerrordiv');
				if(errorPreviousTab.length > 0){
					var divError = $(divName).find('#validationerrordiv');
					$(divError).addClass('hide');
				}
				$.fancybox.close();
			} else {
				$(divName).html(response);
				//window.scrollTo(0, 0);
				prepareDateTag();
			}
		}
	}
}

function showErrAstCls(errorList) {
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBoxAstCls()"><span aria-hidden="true">&times;</span></button><ul>';
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

function closeErrBoxAstCls() {
	$('.warning-div').addClass('hide');
}

function setDepartmentCode() {
	var selectedType = $("#dpDeptId").find("option:selected").attr('code');
	$("#departmentCode").val(selectedType);
}

function backFormAstCls() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AssetRegistration.html');
	$("#postMethodForm").submit();
}

function resetClass() {
	
	var divName = '#astClass';
	var requestData = {
			"resetOption":"reset"
	}
	var response = __doAjaxRequest('AssetRegistration.html?showAstClsPage',
			'POST', requestData, false, '', 'html');
	$(divName).html(response);
	return false;
}