
$(document).ready(function() {	
	
});
function saveApplicationFormData(approvalData){
	var errorList = [];
	errorList = validateUniqueItem();
	if('NC' == $("#applType option:selected").attr("code")){
		errorList = validateRoadTypeRow(errorList);		
	}
	var decision = $("input[id='decision']:checked").val();
	var comments = document.getElementById("comments").value;
	var applTypeDesc= $("#applTypeDesc").val();
		
	if(decision == undefined || decision == '')
		errorList.push(getLocalMessage('DrainageConnectionDTO.info.approval'));
	else if(comments == undefined || comments =='')
		errorList.push(getLocalMessage('TbDeathregDTO.label.rema'));

	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#errorDiv").hide();
		return saveOrUpdateForm(approvalData,
				getLocalMessage('work.estimate.approval.creation.success'),
				'AdminHome.html', 'saveDecision');
	}

}

function goToDashBoard() {
	var last = $('#lastChecker').val();
	if (last == 'true') {
		loiPrinting();
	}
	var levelcheck=$('#levelcheck').val();
	if (last == 'true') {
		var applicationNo = $("#apmApplicationId").val();
		var applTypeDesc= $("#applTypeDesc").val();
		var requestData = 'applicationNo=' + applicationNo+'&applTypeDesc=' + applTypeDesc;
		var URL = 'drainageConnection.html?getBirtReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		if (returnData == "f") {
			errorList.push('Invalid');
			displayErrorsOnPage(errorList);
		} else {
			window.open(returnData, '_blank');
		}
	}
	window.location.href = 'AdminHome.html';
	$.fancybox.close();

}

function loiPrinting() {
	var URL = 'LoiPrintingController.html?showDetails';
	var apmApplicationId = $("#apmApplicationId").val();
	var serviceId = $("#serviceId").val();
		var url ="LoiPrintingController.html?showDetails";
	var data={"appId":apmApplicationId,
	         "serviceId":serviceId};
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	if(returnData!=null && returnData!=""){
	var title = 'LOI printing';
	prepareTags();
	var printWindow = window.open('', '_blank');

	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/print.css" media="print" rel="stylesheet" type="text/css"/>')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
	}
}

function generateLoiCharges(element) {
	var divName = '.content-page';
	var errorList = [];
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
	$("#appCheck").val('L');
	var URL = 'drainageConnection.html?generateLoiCharges';
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var returnData = __doAjaxRequest(URL, 'Post', requestData, false, 'html');

	$(divName).removeClass('ajaxloader');
	$(divName).html(returnData);

	prepareTags();
	}

}

function calculateTotalLoi() {
	var finalAmount = 0;
	if ($("#chargeAmount").val() == finalAmount) {
		$("#chargeAmount").val("0.00")
	}
	$(".amount").each(function() {
		finalAmount += parseInt($(this).val());
	});
	$("#totAmount").val(finalAmount);
}

function viewApplicationCharge(element){
	var errorList = [];
	
	var applTypeDesc= $("#applTypeDesc").val();
/*	if(applTypeDesc!='EC'){
	var roadType = $("#roadType").val();
	var lenRoad = $("#lenRoad").val();
	if (roadType == "" || roadType == undefined || roadType == null || roadType == "0") {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.road.type"));
	}
	if (lenRoad == "" || lenRoad == undefined || lenRoad == null || lenRoad == "0") {
		errorList.push(getLocalMessage("DrainageConnectionDTO.valid.lengthOfRoad"));
	 }
	}*/

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	} else {
		$("#appCheck").val('V');
		var URL ='drainageConnection.html?generateLoiCharges';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(URL, 'Post', requestData, false);
		var errorMsg = $(response).find('#errorMsg').val();
		if (errorMsg != undefined && errorMsg != '') {
			
			var errorList = [];
			errorList.push(errorMsg);
			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index] + '</li>';
			});

			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
			
		} else {
			var divName = '.popUp';
			$(divName).removeClass('ajaxloader');
			$(divName).html(response);
			$(".popUp").show();
			$(".popUp").draggable();
		}
		
	}

}

function closeApplicationCharge() {
	$( ".popUp" ).hide();
}







$("#rtsApplicationRoad").on('click', '.addOF', function() {  debugger;
	var errorList = [];
	errorList = validateUniqueItem();
	errorList = validateRoadTypeRow(errorList);
	if (errorList.length == 0) {
		var content = $("#rtsApplicationRoad").find('tr:eq(1)').clone();
		$("#rtsApplicationRoad").append(content);
		content.find("input:text").val('');
		content.find("select").val('0');
		content.find("input:hidden:eq(0)").val('0');
		$('.error-div').hide();
		reOrderRoadTypeRow('.roadTypeRow');
	} else {
		displayErrorsOnPage(errorList);
	}
});


$("#rtsApplicationRoad").on('click', '.remOF', function() {   debugger;
	var rowCount = $('#rtsApplicationRoad tr').length;
	var removeIdArray = [];
	if (rowCount <= 2) {
		var errorList = [];
		errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	}
	$(this).closest('#rtsApplicationRoad tr').remove();
	reOrderRoadTypeRow('.roadTypeRow');
});

function reOrderRoadTypeRow(roadTypeRow) {    debugger;
	$(roadTypeRow).each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "sequence" + i);
		$(this).find("select:eq(0)").attr("id", "roadType" +i);
		$(this).find("input:text:eq(1)").attr("id", "lenRoad" + i);
							
		$(this).find("input:text:eq(0)").val(i + 1);
		$(this).find("select:eq(0)").attr("name","drainageConnectionDto.roadDetDto[" + i + "].roadType");
		$(this).find("input:text:eq(1)").attr("name","drainageConnectionDto.roadDetDto[" + i + "].lenRoad");			
	});
}



function validateRoadTypeRow(errorList) {
	$('.roadTypeRow').each(function(i) {
				var roadType = $("#roadType" + i).val();
				var lenRoad = $("#lenRoad" + i).val();
				
				if (roadType == "0" || roadType == null || roadType == ""  || roadType == undefined) {
					errorList.push(getLocalMessage("DrainageConnectionDTO.valid.road.type") + " " + (i + 1));
				}
				if (lenRoad == "0" || lenRoad == null || lenRoad == ""  || lenRoad == undefined) {
					errorList.push(getLocalMessage("DrainageConnectionDTO.valid.lengthOfRoad") + " " + (i + 1));
				}
			});
	return errorList;
}



function validateUniqueItem() {
	var errorList = [];
	var roadTypeArr = [];
	$('.roadTypeRow').each(function(i) {
		var roadType = $("#roadType" + i).val();
		if (roadTypeArr.includes(roadType))
			errorList.push(getLocalMessage("DrainageConnectionDTO.valid.diffroad.type") + " " + (i + 1));
		roadTypeArr.push(roadType);
	});
	if (errorList.length > 0)
		displayErrorsOnPage(errorList);
	return errorList;
}
