var fileArray = [];
$(document).ready(function() {
	$("#hearingTable").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});

var minYear= $("#cseDate").val();

	
	$('.lessthancurrdate2').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		minDate: minYear,
	
	});	
	
	var dateFields = $('.date');
	dateFields.each(function () {
		
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
	
	
	
});

function getForm(id, mode, url) {

	
	var data = {
		"id" : id,
		"mode" : mode
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(url, data, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveForm(element) {
	
	var errorList = [];
	errorList = ValidateCounterAffidavitForm(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		$("#errorDiv").hide();

		return saveOrUpdateForm(element, "Saved Successfully",
				'CounterAffidavit.html', 'saveform');
	}
}

function ValidateCounterAffidavitForm(errorList) {

	
	var afDate = $("#afDate").val();
	var cafDate = $("#cafDate").val();
	var cafType = $("#cafType").val();

	if (cafType == '' || cafType == 'undefined' || cafType == null || cafType == '0') {
		errorList.push(getLocalMessage("Select.Counter.Affidavit.Type"));
	}
	if (afDate == '' || afDate == 'undefined' || afDate == null) {
		errorList.push(getLocalMessage("Select.Affidavit.Date"));
	}

	if (cafDate == '' || cafDate == 'undefined' || cafDate == null) {
		errorList.push(getLocalMessage("Select.Counter.Affidavit.Date"));
	}

	var cseDate=$("#cseDate").val()
	
	
    var CD =  moment(cseDate, "DD.MM.YYYY HH.mm").toDate();
	
	var afDate =  moment($("#afDate").val(), "DD.MM.YYYY HH.mm").toDate();
	
	var cafDate =  moment($("#cafDate").val(), "DD.MM.YYYY HH.mm").toDate();
	
	
	if ((afDate.getTime())<(CD.getTime())) {
		errorList.push(getLocalMessage("please.select.affidavit.date"));
	}
	
	if ((cafDate.getTime())<(CD.getTime())) {
		errorList.push(getLocalMessage("please.select.counter.affidavit.date"));
	}
	
	
	
	
	return errorList;

}
