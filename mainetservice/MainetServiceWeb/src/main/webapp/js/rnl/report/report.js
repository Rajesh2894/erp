$(document).ready(
		function() {
			$('#estateId').change(
					function() {
						var requestData = {
							"esId" : $(this).val()
						};
						var ajaxResponse = __doAjaxRequest(
								"EstateDemandRegister.html?propList", 'post',
								requestData, false, 'json');
						if (ajaxResponse == "" || ajaxResponse == null || ajaxResponse == "Internal Server Error.") {
								var errorList = [];
								errorList.push(getLocalMessage("rnl.select.estate"));
								displayErrorsOnPage(errorList);
						}else{
								$('#propId').html('');
								$('#propId').append(
										$("<option></option>").attr("value", "").text(
												'Select'));
								$('#propId').append(
										$("<option></option>").attr("value", "0").text(
												'ALL'));
								$.each(result, function(index, value) {
									$('#propId').append(
											$("<option></option>").attr("value",
													value.propId).text(value.name));
								});
								$('#propId').trigger("chosen:updated");
							}
						
					});

		});

/* Report Summary Details */
$(function() {
	$(".table").tablesorter().tablesorterPager({
		container : $(".ts-pager"),
		cssGoto : ".pagenum",
		removeRows : false,
	});

	$(function() {
		$(".table").tablesorter({
			cssInfoBlock : "avoid-sort",
		});
	});
});

function viewReport(obj) {
	if (checkValidation()) {
		var fromDate = $('#fromDateId').val();
		var toDate = $('#toDateId').val();
		let occupancyType = $("input:radio[id='type']").filter(":checked")
				.val();
		var requestData = {
			'fromDate' : fromDate,
			'toDate' : toDate,
			'occupancyType' : occupancyType

		}

		var divName = formDivName;
		var ajaxResponse = doAjaxLoading(
				'EstateRevenueReport.html?summaryReport', requestData, 'html',
				divName);

		if (ajaxResponse != false) {
			var hiddenVal = $(ajaxResponse).find('#errorId').val();
			if (hiddenVal == 'Y') {
				var errorList = [];
				errorList.push('No record found!');
				displayErrorsOnPage(errorList);
			} else {
				/* $('#content').html(ajaxResponse); */
				$('.content').removeClass('ajaxloader');
				$(divName).html(ajaxResponse);
				prepareTags();
			}
		}

	}
}

function checkValidation() {
	var errorList = [];
	var fromDateId = $("#fromDateId").val();
	var toDateId = $('#toDateId').val();
	let occupancyType = $("input:radio[id='type']").filter(":checked").val();
	if (fromDateId == "" && toDateId == "") {
		errorList.push(getLocalMessage("rnl.revenue.valid.selectDate"));
	} else if (fromDateId != "" && toDateId == "") {
		errorList.push(getLocalMessage("rnl.estate.booking.select.to.date"));
	} else if (fromDateId == "" && toDateId != "") {
		errorList.push(getLocalMessage("rnl.estate.booking.select.from.date"));
	}

	if (occupancyType == '' || occupancyType == 0 || occupancyType == undefined) {
		errorList.push(getLocalMessage("rnl.revenue.valid.occupancyType"));
	}

	if (toDateId != undefined && fromDateId != undefined) {
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date($("#fromDateId").val()
				.replace(pattern, '$3-$2-$1'));
		var sDate = new Date($("#toDateId").val().replace(pattern, '$3-$2-$1'));
		if (eDate > sDate) {
			errorList.push("From Date can not be less than To Date");
			$("#reportTypeId").prop("disabled", true);
		}
	}
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		return false;
	}
	return true;
}

function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('#errorDivId').html(errMsg);
	$("#errorDivId").show();

	$('html,body').animate({
		scrollTop : 0
	}, 'slow');

	return false;
}

function closeOutErrBox() {
	$('.error-div').hide();
}

// Print Div
function PrintDiv(title) {
	var printBtn = getLocalMessage("rnl.print");
	var closeBtn = getLocalMessage("rnl.close");
	var downloadBtn = getLocalMessage("rnl.download");
	var divContents = document.getElementById("receipt").innerHTML;
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
			.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, .tfoot").remove();$("#export-excel").removeClass("table-responsive"); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style"); $(".table .tfoot").addClass("hide");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i>'
					+ ' '
					+ printBtn
					+ '</button> <button id="btnExport" type="button" class="btn btn-blue-2 hidden-print"><i class="fa fa-file-excel-o"></i> '
					+ ' '
					+ downloadBtn
					+ '</button> <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">'
					+ '' + closeBtn + '</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
}

function backAbstractForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'AdvanceRegister.html');
	$("#postMethodForm").submit();
}

// outstanding report
function viewOutstandingReport(obj) {
	let errorList = [];
	var date = $('#fromDateId').val();
	if (date == "") {
		errorList.push(getLocalMessage("rnl.outstanding.report.selectDate"));
	}
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		return false;
	}

	var requestData = {
		'date' : date
	}

	var divName = formDivName;
	var ajaxResponse = doAjaxLoading(
			'EstateOutstandingReport.html?outstandingSummaryReport',
			requestData, 'html', divName);

	if (ajaxResponse != false) {
		var hiddenVal = $(ajaxResponse).find('#errorId').val();
		if (hiddenVal == 'Y') {
			errorList.push('rnl.report.noRecord');
			displayErrorsOnPage(errorList);
		} else {
			/* $('#content').html(ajaxResponse); */
			$('.content').removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		}
	}
}

// hit for demand register report
function viewDemandRegister(obj) {

	let errorList = [];
	var financialYear = $('#financialYearId option:selected').text().trim();
	var fYvalidation = $('#financialYearId option:selected').attr('value')
	var estateId = $('#estateId option:selected').attr('value')
	var propId = $('#propId option:selected').attr('value')
	var financialYear1 = $('#financialYearId1 option:selected').text().trim();
	var fYvalidation1 = $('#financialYearId1 option:selected').attr('value')

	if ((fYvalidation != "" || fYvalidation != 0)
			&& ((estateId == "" || estateId == "0") && (propId == "" ) && (fYvalidation1 == "" || fYvalidation1 == "0"))) {
		var requestData = {
			'financialYear' : financialYear
		}
		var divName = formDivName;
		var ajaxResponse = doAjaxLoading(
				'EstateDemandRegister.html?demandRegisterReport', requestData,
				'html', divName);
		if (ajaxResponse != false) {
			var hiddenVal = $(ajaxResponse).find('#errorId').val();
			if (hiddenVal == 'Y') {
				errorList.push('rnl.report.noRecord');
				displayErrorsOnPage(errorList);
			} else {
				$('.content').removeClass('ajaxloader');
				$(divName).html(ajaxResponse);
				prepareTags();
			}
		}
	}
	else if((estateId != "" || estateId != "0")
			&& (propId != "" )
			&& (fYvalidation != "" || fYvalidation != 0)
			&& (fYvalidation1 != "" || fYvalidation1 != 0)){
		errorList.push(getLocalMessage("rnl.report.allFields"));	
		displayErrorsOnPage(errorList);

	}
	else if ((estateId != "" && estateId != "0") && (propId != "" )
			&& (fYvalidation1 != "" && fYvalidation1 != "0")) {

		var requestData = {
			'estateId' : estateId,
			'propId' : propId,
			'financialYear' : financialYear1
		}
		var divName = formDivName;
		var ajaxResponse = doAjaxLoading(
				'EstateDemandRegister.html?demandRegisterReportUsingProp',
				requestData, 'html', divName);

		if (ajaxResponse != false) {
			var hiddenVal = $(ajaxResponse).find('#errorId').val();
			if (hiddenVal == 'Y') {
				errorList.push('No record found!');
				displayErrorsOnPage(errorList);
			} else {
				$('.content').removeClass('ajaxloader');
				$(divName).html(ajaxResponse);
				prepareTags();
			}
		}
	} else if ((estateId == "" || estateId == "0")
			&& (propId == "" )
			&& (fYvalidation == "" || fYvalidation == 0)
			&& (fYvalidation1 == "" || fYvalidation1 == 0)) {
		errorList.push(getLocalMessage("rnl.report.anyField"));
		displayErrorsOnPage(errorList);
	} else if ((estateId != "" || estateId != "0") && (propId == "" )
			&& (fYvalidation1 == "" || fYvalidation1 == "0")) {
		errorList
				.push(getLocalMessage("rnl.report.prop.financial"));
		displayErrorsOnPage(errorList);
	} else if ((estateId != "" || estateId != "0") && (propId == "" )
			&& (fYvalidation1 != "" || fYvalidation1 != "0")) {
		errorList
				.push(getLocalMessage("rnl.report.prop"));
		displayErrorsOnPage(errorList);
	}else if ((estateId != "" || estateId != "0") && (propId != "" )
			&& (fYvalidation1 == "" || fYvalidation1 == "0")) {
		errorList.push(getLocalMessage("rnl.report.financial"));
		displayErrorsOnPage(errorList);
	} 
}