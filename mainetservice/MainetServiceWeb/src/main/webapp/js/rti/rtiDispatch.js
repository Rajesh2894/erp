$(document).ready(
		function() {

			$("#finalDispatchMode").val('0');
			$("#dispatchDate").val('');

			$(function() {
				$('#dispatchDate').datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					minDate : 0
				});
			});
			$(function() {
				$('#dispatchDt').datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : false,
					changeYear : false,
					minDate : 0,
					
				});
			});

			/*
			 * $("#dispatchDate").datepicker({ beforeShowDay:
			 * $.datepicker.noWeekends, minDate: 0 });
			 */

			$("#deliveryMode")
					.change(
							function(e) {
								if ($('#deliveryMode option:selected').attr(
										'code') == "POC") {
									$("#byCourierorPost").show();
									$("#byHand").hide();

								} else if ($('#deliveryMode option:selected')
										.attr('code') == "CAO") {
									$("#byHand").show();
									$("#byCourierorPost").hide();
								} else if (($('#deliveryMode option:selected')
										.attr('code') == "EM")
										|| ($('#deliveryMode option:selected')
												.attr('value') == "0")) {
									$("#byCourierorPost").hide();
									$("#byHand").hide();

								}
							});

			/*
			 * $('#rtiDispatchForm').validate({ onkeyup : function(element) {
			 * this.element(element); console.log('onkeyup fired'); },
			 * onfocusout : function(element) { this.element(element);
			 * console.log('onfocusout fired'); } });
			 */

		});

function backPage() {
	window.location.href = getLocalMessage("rti.adminHome");
}

function saveDispatchForm(obj) {
	var errorList = [];
	errorList = validateDispatchForm(errorList);
	var theForm = '#rtiDispatchForm';
	var holidayDate = $("#dispatchDt").val();

	var requestData = {
		'holidayDate' : holidayDate
	}
	var response = __doAjaxRequest('RtiDispatch.html?getHolidayDates', 'POST',
			requestData, false, 'html');
	if (response != "") {
		// $("#dispatchDate").val("");
		errorList.push(response);
		displayErrorsOnPage(errorList);
	} else if (errorList.length == 0) {
		var status = saveOrUpdateForm(obj,
				getLocalMessage("rti.dispatch.save"), "AdminHome.html",
				'saveDispatch');

		openDispatchReportWindowTab(status);

	} else {
		displayErrorsOnPage(errorList);
	}
}

function openDispatchReportWindowTab(status) {
	if (!status) {
		var URL = 'RtiDispatch.html?applicantInfoReportPrint';
		var returnData = __doAjaxRequest(URL, 'POST', {}, false);

		var title = 'Applicant Information';
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
				.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
		printWindow.document
				.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
		printWindow.document
				.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
		printWindow.document.write('</head><body style="background:#fff;">');
		// printWindow.document
		// .write('<div style="position:fixed; width:100%; bottom:0px;
		// z-index:1111;"><div class="text-center"><button
		// onclick="window.print();" class="btn btn-success hidden-print"
		// type="button"><i class="fa fa-print" aria-hidden="true"></i>
		// Print</button> <button onClick="window.close();" type="button"
		// class="btn btn-blue-2 hidden-print">Close</button></div></div>')
		printWindow.document.write(returnData);
		printWindow.document.write('</body></html>');
		// printWindow.document.close();

	}
}

/*
 * function getHolidayDate() {
 * 
 * var errorList = []; var theForm = '#rtiDispatchForm'; var holidayDate =
 * $("#dispatchDate").val();
 * 
 * var requestData = { 'holidayDate' : holidayDate } var response =
 * __doAjaxRequest( 'RtiDispatch.html?getHolidayDates', 'POST', requestData,
 * false, 'html'); if(response!=""){ errorList.push(response);
 * displayErrorsOnPage(errorList); } }
 */

function validateDispatchForm(errorList) {
	debugger;
	var delMode = $("#finalDispatchMode").val();
	var delRefNo = $("#deliveryReferenceNumber").val();
	var dispatchNo = $("#dispatchNo").val();
	var dispatchDt = $("#dispatchDt").val();
	//var delDate = $("#dispatchDate").val();
	// var delDate = $("#dispatchDt").val();

	// var date = new Date(delDate, 'dd/mm/yyyy');

	/*
	 * var deliverDate = new Date(delDate); var presentDate = new Date();
	 * presentDate.setHours(0,0,0,0);
	 * 
	 * if(deliverDate < presentDate){
	 * errorList.push(getLocalMessage("rti.validation.dispatchDate")); }
	 * 
	 */
	if (dispatchNo == "" || dispatchNo == undefined) {

		errorList.push(getLocalMessage("rti.validation.dispatchNo"));
	}
	if (dispatchDt == "" || dispatchDt == undefined) {

		errorList.push(getLocalMessage("rti.validation.dispatchDt"));
	}
	if (delMode == "0" || delMode == undefined) {

		errorList.push(getLocalMessage("rti.validation.deliveryMode"));

	} else if ($('#deliveryMode option:selected').attr('code') == "POC") {
		if (delRefNo == "" || delRefNo == undefined || delRefNo == 0) {
			errorList.push(getLocalMessage("rti.validation.deliveryRefNo"));
		}
	}
/*
	if (delDate == "0" || delDate == undefined || delDate == "") {
		errorList.push(getLocalMessage("rti.validation.deliveryDate"));
	}
*/
	return errorList;
}
function getDeliveryMode() {

	var deliveryMode = $("#finalDispatchMode" + " option:selected")
			.attr("code");
	if (deliveryMode != undefined) {
		var data = {
			"deliveryModeType" : deliveryMode
		};
		var URL = 'RtiDispatch.html?getDeliveryMode';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$("#deliveryMode").html(returnData);
		$("#deliveryMode").show();
		$("#DispatchName").val('');
		$("#DispatchMobile").val('');
		$("#DispatchDocketNo").val('');
		$("#deliveryReferenceNumber").val('');
	} else {
		$("#deliveryMode").html("");
	}
}