$(document).ready(function() {
	prepareDateTag();

});

function saveApprovalData(element) {

	var errorList = [];
	var purchaserName = $("#purchaserName").val();
	var purchaserAddress = $("#purchaserAddress").val();
	var sellerName = $("#sellerName").val();
	var sellerAddress = $("#sellerAddress").val();
	var east = $("#east").val();
	var west = $("#west").val();
	var north = $("#north").val();
	var south = $("#south").val();
	var proDesc= $("#proDesc").val();
	
	var saleDate = $("#saleDate").val();
	var malabaCharge = $("#malabaCharge").val();
	var scrutinyDate = $("#scrutinyDate").val();
	
	

	if (purchaserName == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.purchaserName"))
	}
	if (purchaserAddress == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.purchaserAddress"))
	}
	if (sellerName == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.sellerName"))
	}
	if (sellerAddress == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.sellerAddres"))
	}
	if (east == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.east"))
	}
	if (west == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.west"))
	}
	if (north == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.north"))
	}
	if (south == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.south"))
	}
	
	if ( proDesc == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.proDesc"))
	}
	
	if (saleDate == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.saleDate"))
	}
	
	if (  malabaCharge == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.malabaCharge"))
	}
	

	if (  scrutinyDate == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.scrutinyDate"))
	}
	
	// $.fancybox.close();
	if ($("#birthRegremark").val() == "") {
		errorList.push(getLocalMessage("NOCBuildingPermission.label.remark"))
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var url = 'NOCForBuildingPermissionApproval.html?saveRegApproval';
		var requestData = __serializeForm(theForm);
		var object = __doAjaxRequest(url, 'POST', requestData, false, 'json');
			
		if (object.error != null && object.error != 0) {

			$.each(object.error, function(key, value) {
				$.each(value, function(key, value) {
					if (value != null && value != '') {
						errorList.push(value);
					}
				});
			});
			displayErrorsOnPage(errorList);
		} else {
			if (object.BirthWfStatus == 'REJECTED') {
				showBoxForApproval(getLocalMessage("NOCBuildingPermission.submit.reject"));

			} else if (object.BirthWfStatus == 'SEND_BACK') {
				showBoxForApproval(getLocalMessage('audit.para.sendBack'));
			} else if (object.BirthWfStatus == 'FORWARD_TO') {
				showBoxForApproval(getLocalMessage('audit.para.sendBack'));
			} else {
				showBoxForApproval(getLocalMessage("NOCBuildingPermission.submit.approve"));
				if ( object.APPSTATUS == 'A') {
					bndRegAcknow(element);
				}
			}
		}
	}
}

function showBoxForApproval(succesMessage) {

	var childDivName = '.msg-dialog-box';
	var message = '';
	var Proceed = getLocalMessage('NOCBuildingPermission.proceed');
	var no = 'No';
	message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
			+ '</p>';

	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
			+ Proceed + '\'  id=\'Proceed\' ' + ' onclick="closeApproval();"/>'
			+ '</div>';
	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#Proceed').focus();
	showModalBoxWithoutClose(childDivName);
}

function closeApproval() {
	window.location.href = 'AdminHome.html';
	$.fancybox.close();
}

function refreshServiceData(element, formUrl, actionParam) {

	var requestData = element;
	var divName = '.content-page'
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;

	var requestData = __serializeForm(theForm);
	var serviceName = $("#serviceId>option:selected").text();
	var response = __doAjaxRequest(formUrl + '?' + actionParam, 'POST',
			requestData, false, '', 'html');
	$(divName).html(response);
	$(divName).removeClass('ajaxLoader');
	prepareTags();

}
function bndRegAcknow(element) {
	
	var url1 = 'NOCForBuildingPermissionApproval.html?nocCertificate';
	var returnData = __doAjaxRequest(url1, 'POST', {}, false);
	//var returnData = __doAjaxRequest(URL, 'POST', {}, false);
	if(returnData!=null && returnData!=""){
	var title = 'NOC FOR BUILDING PERMISSION CERTIFICATE';
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





