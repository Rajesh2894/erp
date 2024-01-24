function getAdvertiserByAdvertiserType() {

    var errorList = [];
    var advertiserType = $("#advertiserType").val();
    $('#agencyName').val('');
    if (advertiserType == '' || advertiserType == '0') {
	errorList
		.push(getLocalMessage('advertiser.remainder.notice.validate.advertiser.type'));
    }

    if (errorList.length == 0) {
	requestData = {
	    "advertiserType" : advertiserType
	};
	$('#agencyId').html('');
	$('#agencyId').append(
		$("<option></option>").attr("value", "0").text(
			getLocalMessage('selectdropdown')));

	var ajaxResponse = doAjaxLoading(
		'RenewalRemainderNotice.html?getAdvertiserByAdvertiserType',
		requestData, 'html');
	var prePopulate = JSON.parse(ajaxResponse);

	$.each(prePopulate,
		function(index, value) {
		    $('#agencyId').append(
			    $("<option></option>")
				    .attr("value", value.agencyId).text(
					    (value.agencyLicNo) + " - "
						    + (value.agencyOwner)));
		});
	$('#agencyId').trigger("chosen:updated");

    } else {
	displayErrorsOnPage(errorList);
    }
}

function searchAdvertiser() {
    var divName = '.content-page';
    var errorList = [];
    var agencyId = $("#agencyId").val();
    $('#remarks').val('');
    var advertiserType = $("#advertiserType").val();
    if (advertiserType == '' || advertiserType == '0') {
	errorList
		.push(getLocalMessage('advertiser.remainder.notice.validate.advertiser.type'));
    }
    if (agencyId == "" || agencyId == undefined || agencyId == null
	    || agencyId == '0') {
	errorList
		.push(getLocalMessage('advertiser.remainder.notice.validate.advertiser.name'));
    }

    if (errorList.length == 0) {
	var requestData = {
	    "agencyId" : agencyId

	};

	var ajaxResponse = doAjaxLoading(
		'RenewalRemainderNotice.html?searchAgencyByLicNo', requestData,
		'html', divName);

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
    } else {
	displayErrorsOnPage(errorList);
    }

}

function printRenewalremainderNotice(element) {

    var errorList = [];
    var remarks = $("#remarks").val();

    if (remarks == "" || remarks == null) {
	errorList.push(getLocalMessage('adh.enter.remark'));
    }
    if (errorList.length == 0) {
	var divName = '.content-page';
	var formName = findClosestElementId(element, 'form');
	var URL = 'RenewalRemainderNotice.html?printRenewalremainderNotice';
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var returnData = __doAjaxRequest(URL, 'Post', requestData, false,
		'html');
	showBoxForApproval();
	var title = 'Renewal Remainder Notice';
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
		.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
		.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
		.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
    } else {
	displayErrorsOnPage(errorList);
    }

}

function showBoxForApproval() {

    var childDivName = '.msg-dialog-box';
    var message = '';
    var Proceed = getLocalMessage("proceed");
    var no = 'No';
    succesMessage = 'Remainder Notice printed Successfully';
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
    window.location.href = 'RenewalRemainderNotice.html';
    $.fancybox.close();
}