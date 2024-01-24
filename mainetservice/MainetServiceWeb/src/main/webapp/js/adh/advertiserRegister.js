$(document).ready(function() {

	$('#fromDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#fromDate").keyup(function(e) {

		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$('#toDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});

	$("#toDate").keyup(function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

});
function viewAdvertiserForm(obj) {

	var errorList = [];
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();

	if (fromDate == "") {
		errorList.push(getLocalMessage("adh.report.validn.fromdate"));
	}
	if (toDate == "") {
		errorList.push(getLocalMessage("adh.report.validn.todate"));
	}

	if ((compareDate(fromDate)) > compareDate(toDate)) {
		errorList.push(getLocalMessage("adh.compare.from.to.date"));
	}

	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		$("#errorDiv").hide();

		var requestData = {
			"fromDate" : fromDate,
			"toDate" : toDate
		};

		var URL = 'AdvertiserRegister.html?GetAdvertiserReport';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		if (returnData == "f") {
			errorList.push(getLocalMessage('adh.validation.financialYrDate'));
			displayErrorsOnPage(errorList);
		} else {
			window.open(returnData, '_blank');
		}
	}
}
function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);

}

/*
 * $(document).ready(function() { $(".datepicker").datepicker({ dateFormat :
 * 'dd/mm/yy', changeMonth : true, changeYear : true, maxDate : '0d', }); });
 * 
 * function report(formUrl, actionParam) { var errorList = []; var fromDate =
 * $('#fromDate').val(); var toDate = $('#toDate').val(); errorList =
 * validateForm(errorList); if (errorList.length > 0) {
 * displayErrorsOnPage(errorList); } else{ var data = { "fromDate" : fromDate,
 * "toDate":toDate };
 * 
 * var divName = '.content-page'; var ajaxResponse = doAjaxLoading(formUrl + '?' +
 * actionParam,data,'html'); $(divName).removeClass('ajaxloader');
 * $(divName).html(ajaxResponse); prepareTags(); } }
 * 
 * function validateForm(errorList) { var fromDate = $('#fromDate').val(); var
 * toDate = $('#toDate').val(); var fromDt = moment(fromDate, "DD.MM.YYYY
 * HH.mm").toDate(); var toDt = moment(toDate, "DD.MM.YYYY HH.mm").toDate();
 * 
 * if (fromDate == "" || fromDate == null) {
 * errorList.push(getLocalMessage('Please Enter the From Date')); } else {
 * 
 * var dateErrList = validateDateFormat($("#fromDate").val());
 * if(dateErrList!=undefined) {errorList.push(getLocalMessage('From Date :
 * '+dateErrList));} } if(toDate =="" || toDate == null) {
 * errorList.push(getLocalMessage('Please Enter the To Date')); } else { var
 * dateErrList = validateDateFormat($("#toDate").val());
 * if(dateErrList!=undefined) {errorList.push(getLocalMessage('To Date :
 * '+dateErrList));} } if ((fromDt.getTime()) > (toDt.getTime())) {
 * errorList.push(getLocalMessage("From Date Cannot Be Greater Than To Date")); }
 * return errorList; }
 * 
 * function validateDateFormat(dateElementId) {
 * 
 * var errorList = []; var dateValue= dateElementId; if(dateValue != null &&
 * dateValue != "") { var dateformat =
 * /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
 * if(dateValue.match(dateformat)) { var opera1 = dateValue.split('/'); //var
 * temp = sliDate.split(" "); //var temp1 = temp[0]; //var sli; //Splitting
 * SliDate
 * 
 * lopera1 = opera1.length; //lsli = sli.length; if (lopera1>1) { var pdate =
 * dateValue.split('/'); } var dd = parseInt(pdate[0]); var mm =
 * parseInt(pdate[1]); var yy = parseInt(pdate[2]);
 * 
 * //var slimonth = parseInt(sli[1]); // var sliyear = parseInt(sli[2]); var
 * ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31]; if (mm==1 || mm>2) { if
 * (dd>ListofDays[mm-1]) { errorList.push('Invalid Date Format'); } } if (mm==2) {
 * var lyear = false; if ( (!(yy % 4) && yy % 100) || !(yy % 400)) { lyear =
 * true; } if ((lyear==false) && (dd>=29)) { errorList.push('Invalid Date
 * Format'); } if ((lyear==true) && (dd>29)) { errorList.push('Invalid Date
 * Format'); } } var pattern = /(\d{2})\/(\d{2})\/(\d{4})/; var sDate = new
 * Date(dateValue.replace(pattern,'$3-$2-$1')); if (sDate > new Date()) { }
 * if(yy == sliyear) { if(mm < slimonth && mm != slimonth) {
 * errorList.push("Invalid value for month: " + mm + " Transaction Date must be
 * between " +temp1+" and " + (new Date()).getFullYear()); } } if(yy <sliyear ||
 * yy > (new Date()).getFullYear()) { errorList.push("Invalid value for year: " +
 * yy + " Transaction Date must be between " +temp1+" and " + (new
 * Date()).getFullYear()); } } else { errorList.push('Invalid Date Format'); } }
 * displayErrorsPage(errorList); if(errorList.length > 0) { { correct = false;}
 * else{correct = true;} return errorList; } }
 * 
 * 
 * 
 * 
 * 
 * function displayErrorsPage(errorList) { if (errorList.length > 0) { var
 * errMsg = '<ul>'; $.each(errorList, function(index) { errMsg += '<li> <i
 * class="fa fa-exclamation-circle"></i>&nbsp;'+ errorList[index] + '</li>';
 * }); errMsg += '</ul>'; $('#errorId').html(errMsg); $('#errorDivId').show();
 * $('html,body').animate({ scrollTop : 0 }, 'slow'); return false; } }
 * 
 * 
 * function back() {
 * 
 * $("#postMethodForm").prop('action', ''); $("#postMethodForm").prop('action',
 * 'AdvertiserRegister.html'); $("#postMethodForm").submit(); }
 * 
 * 
 * function PrintDiv(title) { var divContents =
 * document.getElementById("advertiserRegister").innerHTML; var printWindow =
 * window.open('','_blank'); printWindow.document.write('<html><head><title>'+title+'</title>');
 * printWindow.document.write('<link
 * href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet"
 * type="text/css" />') printWindow.document.write('<link
 * href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet"
 * />') printWindow.document.write('<link href="assets/css/style.css"
 * rel="stylesheet" type="text/css" />') printWindow.document.write('<link
 * href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
 * printWindow.document.write('<script src="js/mainet/ui/jquery-1.10.2.min.js"></script>')
 * printWindow.document.write('<script type="text/javascript"
 * src="assets/libs/excel-export/excel-export.js"></script>')
 * printWindow.document.write('<script>$(window).load(function()
 * {$(".table-pagination, .remove-btn, .paging-nav, .tfoot").remove(); $(".table
 * thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc
 * tablesorter-header"); $(".table tr").removeAttr("style"); $(".table
 * .tfoot").addClass("hide");});</script>') printWindow.document.write('</head><body
 * style="background:#fff;">'); printWindow.document.write('<div
 * style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div
 * class="text-center"><button onclick="window.print();" class="btn btn-success
 * hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i>
 * Print</button> <button id="btnExport" type="button" class="btn btn-blue-2
 * hidden-print"><i class="fa fa-file-excel-o"></i> Download</button> <button
 * onClick="window.close();" type="button" class="btn btn-blue-2
 * hidden-print">Close</button></div></div>')
 * printWindow.document.write(divContents); printWindow.document.write('</body></html>');
 * printWindow.document.close(); }
 * 
 */