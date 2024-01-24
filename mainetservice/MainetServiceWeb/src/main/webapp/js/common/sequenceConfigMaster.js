$(document).ready(function() {

	debugger;
	var prefixId21 = $("#prefixId2").val();
	var prefixIdA1 = $("#prefixId1").val();
	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});

	$('.datepickerEndDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "2021:2200"
	});

	$('#sequenceConfigMaster').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});

	var list = $("#list").val();

	$("#configurationId").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
})

/* Home.jsp */
function addSequenceMaster(formUrl, actionParam) {
	debugger;
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/* Home.jsp */
function searchForm(formUrl, actionParam) {
	debugger;
	var errorList = [];
	errorList = validateHomeForm(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		var deptname = $("#deptId").val();
		var seqName = $("#seqName").val();
		var status = $("#status").val();
		var category = $("#catId").val();

		var requestdata = {
			"deptId" : deptname,
			"seqName" : seqName,
			"status" : status,
			"catId" : category,

		};

		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam,
				requestdata, 'html', divName);

		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

/* Home.jsp */
function validateHomeForm(errorList) {
	debugger;
	var errorList = [];
	var deptname = $("#deptId").val();
	var seqName = $("#seqName").val();
	var status = $("#status").val();
	var category = $("#catId").val();

	if (deptname == "") {
		if (seqName == "0") {
			if (status == "0") {
				if (category == "0") {
					errorList.push("Please Select atleast one entry");
				}
			}
		}

	}

	return errorList;
}

/* Form.jsp */
function saveSequenceConfiguration(element) {
	debugger;
	var errorList = [];
	errorList = validateForm(errorList)
	if (errorList.length == 0) {
		saveOrUpdateForm(element, 'Sequence Config entry added successfully',
				'SequenceConfigrationMaster.html', 'saveform');
	} else
		displayErrorsOnPage(errorList);

}

/* Form.jsp */
function validateForm(errorList) {
	debugger;
	var errorList = [];
	var i = 0;
	var seqOrdererror = 0;
	var prefixError = 0;
	var seqPrefixerror = 0;
	var sequenceError = 0;

	var seqStatus = $("#Astatus").val();
	var seqStatus = $("#Istatus").val();
	var seqName = $("#seqName").val();
	var catId = $("#catId").val();
	var deptId = $("#deptId").val();
	var sequencetype = $("#seqType").val();
	var separator = $("#seqSep").val();
	var seqlen = $("#seqLength").val();
	var custSeq = $("#custSeq").val();
	var seqstartnum = $("#seqFrmNo").val();

	var prefixId1 = $("#prefixId1").val();
	var seqOrder1 = $("#seqOrder1").val();

	var prefixId2 = $("#prefixId2").val();
	var seqOrder2 = $("#seqOrder2").val();
	var prefixId3 = $("#prefixId3").val();
	var seqOrder3 = $("#seqOrder3").val();
	var prefixId4 = $("#prefixId4").val();
	var seqOrder4 = $("#seqOrder4").val();
	var prefixId5 = $("#prefixId5").val();
	var seqOrder5 = $("#seqOrder5").val();
	var prefixId6 = $("#prefixId6").val();
	var seqOrder6 = $("#seqOrder6").val();
	var prefixId7 = $("#prefixId7").val();
	var seqOrder7 = $("#seqOrder7").val();
	var prefixId8 = $("#prefixId8").val();
	var seqOrder8 = $("#seqOrder8").val();
	var prefixId9 = $("#prefixId9").val();
	var seqOrder9 = $("#seqOrder9").val();
	var prefixId10 = $("#prefixId10").val();
	var seqOrder10 = $("#seqOrder10").val();
	var prefixId11 = $("#prefixId11").val();
	var seqOrder11 = $("#seqOrder11").val();
	var prefixId12 = $("#prefixId12").val();
	var seqOrder12 = $("#seqOrder12").val();
	var prefixId13 = $("#prefixId13").val();
	var seqOrder13 = $("#seqOrder13").val();
	var prefixId14 = $("#prefixId14").val();
	var seqOrder14 = $("#seqOrder14").val();
	var fromDate = $("#fromDate").val();
	var startDate = $("#fromDate").val();
	var endDate = $("#endDate").val();

	var prefixes = [ prefixId1, prefixId2, prefixId3, prefixId4, prefixId5,
			prefixId6, prefixId7, prefixId9, prefixId10, prefixId11, prefixId12,prefixId13,prefixId14 ];
	var seqorder = [ seqOrder1, seqOrder2, seqOrder3, seqOrder4, seqOrder5,
			seqOrder6, seqOrder7, seqOrder9, seqOrder10, seqOrder11, seqOrder12,seqOrder13,seqOrder14 ];

	if (seqName == "0") {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqName"));
	}

	if (catId == "0") {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqCategory"));
	}

	if (seqlen == "") {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqLen"));
	}
	if (seqlen > 9) {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqLenVal"));
	}

	if (seqlen == "0" || seqlen == "00") {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqLenZ"));
	}

	if (seqlen != "0" || seqlen != "00") {
		var len = seqstartnum.toString().length;
		if (seqlen < len) {
			errorList
					.push(getLocalMessage("common.sequenceConfig.validation.seqLenMax"));
		}
	}

	if (deptId == "") {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqDept"));
	}

	if (sequencetype == "0") {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqType"));
	}

	if (fromDate == "") {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqStartDate"));
	}

	if ((compareDate(startDate)) > compareDate(endDate)) {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqEndDate"));
		displayErrorsOnPage(errorList);
	}

	iterateFunc(prefixes, seqorder, errorList);
	orderCompare(prefixes, seqorder, errorList);
	orderprefixval(prefixes, seqorder, errorList);
	prefixemptyval(prefixes, seqorder, errorList);
	prefixnoselval(prefixes, seqorder, errorList);
	maxLimitVal(prefixes, seqorder, errorList);

	return errorList;
}

function iterateFunc(prefixes, seqorder, errorList) {

	var orderError = 0;

	for (i = 0; i < seqorder.length; i++) {
		if (seqorder[i] == "0" || seqorder[i] == "00") {
			orderError++;
		}
	}
	if (orderError > 0) {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqOrder"));
	}

}

function orderCompare(prefixes, seqorder, errorList) {

	var ordercompare = 0;

	for (i = 0; i < seqorder.length; i++) {
		if (seqorder[i] != "") {
			for (j = i + 1; j < seqorder.length; j++) {
				if (seqorder[i] == seqorder[j]) {
					ordercompare++;
				}
			}
		}
	}

	if (ordercompare > 0) {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqOrderDuplicate"));
	}
}

function orderprefixval(prefixes, seqorder, errorList) {

	var prefixorderval = 0;

	for (i = 0; i < seqorder.length; i++) {
		if (seqorder[i] != "") {
			for (j = i; j < prefixes.length; j++) {
				if (prefixes[i] == "" && prefixes[i] != "N") {
					prefixorderval++;
					break;
				}
			}
		}

	}

	if (prefixorderval > 0) {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqPrefixSelect"));
	}
}

function prefixnoselval(prefixes, seqorder, errorList) {
	var prefixorderval = 0;

	for (i = 0; i < seqorder.length; i++) {
		if (seqorder[i] != "") {
			for (j = i; j < prefixes.length; j++) {
				if (prefixes[i] == "N") {
					prefixorderval++;
					break;
				}
			}
		}

	}

	if (prefixorderval > 0) {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqOrderEmpty"));
	}
}

function prefixemptyval(prefixes, seqorder, errorList) {

	var errorr = 0;

	for (i = 0; i < prefixes.length; i++) {
		if (prefixes[i] != "" && prefixes[i] != "0" && prefixes[i] != "N") {
			for (j = i; j < seqorder.length; j++) {
				if (seqorder[i] == "") {
					errorr++;
					break;
				}
			}
		}

	}

	if (errorr > 0) {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqOrderSelect"));
	}
}

function maxLimitVal(prefixes, seqorder, errorList) {
	debugger;
	var errorr = 0;

	for (i = 0; i < prefixes.length; i++) {
		if (prefixes[i] != "" && prefixes[i] != "N") {
			for (j = i; j < seqorder.length; j++) {
				if (seqorder[i] != "") {
					errorr++;
					break;
				}
			}
		}

	}

	if (errorr > 4) {
		errorList
				.push(getLocalMessage("common.sequenceConfig.validation.seqLimit"));
	}
}
/* Form.jsp */
function categoryChange() {
	debugger;
	/* var catId = $("#catId").val(); */

	var catId = $('#catId option:selected').attr('code');
	if (catId == "NRM") {

		$("#seqSep").prop('disabled', true);

		$("#prefixId1").prop('disabled', true);
		$("#prefixId1").val("");
		$("#seqOrder1").prop('disabled', true);
		$("#seqOrder1").val("");
		$("#prefixId2").prop('disabled', true);
		$("#prefixId2").val("");
		$("#seqOrder2").prop('disabled', true);
		$("#seqOrder2").val("");
		$("#prefixId3").prop('disabled', true);
		$("#prefixId3").val("");
		$("#seqOrder3").prop('disabled', true);
		$("#seqOrder3").val("");
		$("#prefixId4").prop('disabled', true);
		$("#prefixId4").val("");
		$("#seqOrder4").prop('disabled', true);
		$("#seqOrder4").val("");
		$("#prefixId5").prop('disabled', true);
		$("#prefixId5").val("");
		$("#seqOrder5").prop('disabled', true);
		$("#seqOrder5").val("");
		$("#prefixId6").prop('disabled', true);
		$("#prefixId6").val("");
		$("#seqOrder6").prop('disabled', true);
		$("#seqOrder6").val("");
		$("#prefixId7").prop('disabled', true);
		$("#prefixId7").val("");
		$("#seqOrder7").prop('disabled', true);
		$("#seqOrder7").val("");
		$("#prefixId8").prop('disabled', true);
		$("#prefixId8").val("");
		$("#seqOrder8").prop('disabled', true);
		$("#seqOrder8").val("");
		$("#prefixId9").prop('disabled', true);
		$("#prefixId9").val("");
		$("#seqOrder9").prop('disabled', true);
		$("#seqOrder9").val("");
		$("#prefixId10").prop('disabled', true);
		$("#prefixId10").val("");
		$("#seqOrder10").prop('disabled', true);
		$("#seqOrder10").val("");
		$("#prefixId11").prop('disabled', true);
		$("#prefixId11").val("");
		$("#seqOrder11").prop('disabled', true);
		$("#seqOrder11").val("");
		$("#prefixId12").prop('disabled', true);
		$("#prefixId12").val("");
		$("#seqOrder12").prop('disabled', true);
		$("#seqOrder12").val("");
		$("#seqOrder13").prop('disabled', true);
		$("#seqOrder13").val("");
		$("#prefixId13").prop('disabled', true);
		$("#prefixId13").val("");
		$("#prefixId14").prop('disabled', true);
		$("#prefixId14").val("");

	}
	if (catId == "PRB") {

		$("#seqSep").prop('disabled', false);

		$("#prefixId1").prop('disabled', false);
		$("#seqOrder1").prop('disabled', false);

		$("#prefixId2").prop('disabled', false);
		$("#seqOrder2").prop('disabled', false);
		$("#prefixId3").prop('disabled', false);
		$("#seqOrder3").prop('disabled', false);
		$("#prefixId4").prop('disabled', false);
		$("#seqOrder4").prop('disabled', false);
		$("#prefixId5").prop('disabled', false);
		$("#seqOrder5").prop('disabled', false);
		$("#prefixId6").prop('disabled', false);
		$("#seqOrder6").prop('disabled', false);
		$("#prefixId7").prop('disabled', false);
		$("#seqOrder7").prop('disabled', false);
		$("#prefixId8").prop('disabled', false);
		$("#prefixId9").prop('disabled', false);
		$("#seqOrder9").prop('disabled', false);
		$("#prefixId10").prop('disabled', false);
		$("#seqOrder10").prop('disabled', false);
		$("#prefixId11").prop('disabled', false);
		$("#seqOrder11").prop('disabled', false);
		$("#prefixId12").prop('disabled', false);
		$("#seqOrder12").prop('disabled', false);
		$("#prefixId13").prop('disabled', false);
		$("#seqOrder13").prop('disabled', false);
		$("#prefixId14").prop('disabled', false);
		$("#seqOrder14").prop('disabled', false);

	}
}

/* Home.jsp */
function viewSequenceMaster(formUrl, actionParam, seqConfigId) {
	debugger;
	var requestdata = {
		"seqConfigId" : seqConfigId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/* Home.jsp */
function editSequenceMaster(formUrl, actionParam, seqConfigId) {
	debugger;
	var requestdata = {
		"seqConfigId" : seqConfigId
	};
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

/* Home.jsp */
function backForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'SequenceConfigrationMaster.html');
	$("#postMethodForm").submit();
}

/* Function to validate the selected custom sequence */
function validateCustSeq() {
	debugger;
	var custY = $("#custSeqY").val();
	var custN = $("#custSeqN").val();
	if (custY == 'Y') {
		$("#seqFrmNo").prop('disabled', false);
	}
	if (id == 'N') {
		/* id.value = "1"; */
		$("#seqFrmNo").val("");
		$("#seqFrmNo").prop('disabled', true);
	}
}

function validateDate() {

	debugger;
	var startDate = $("#fromDate").val();
	var endDate = $("#endDate").val();

	if (startDate == "") {
		$("#endDate").prop('disabled', true);
	}

	if (startDate != "") {
		$("#endDate").prop('disabled', false);
	}

}

function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

function ResetForm(resetBtn) {
	debugger;
	var seqConfigId = $("#seqConfigId").val();

	if (seqName.disabled) {

		$("#Astatus").prop('checked', true);

	}
	if (!seqName.disabled) {
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(
				'SequenceConfigrationMaster.html?addSequenceMaster', {},
				'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse)
		$('.error-div').hide();
	}
	prepareTags();
}

function ResetSearchForm(resetBtn) {
	debugger;

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'SequenceConfigrationMaster.html');
	$("#postMethodForm").submit();
	$('.error-div').hide();
	prepareTags();

}

function searchPrefix() {

	debugger;
	$('.warning-div').addClass('hide');

	var department = $('#deptId option:selected').attr('code');

	var errorList = [];

	if (errorList.length > 0) {
		showErr(errorList);
	} else {
		var url = "SequenceConfigrationMaster.html?searchPrefixData";
		var requestData = {
			"department" : department
		}

		debugger;

		var ajaxResponse = __doAjaxRequestForSave(url, 'post', requestData,
				false, '', '');

		$('#prefixId8').html('');

		$('#prefixId8').append(
				$("<option></option>").attr("value", 0).attr("code", 0).text(
						"select prefix"));
		$.each(ajaxResponse, function(index, data) {
			$('#prefixId8').append(
					$("<option></option>").attr("value", data.cpmPrefix).attr(
							"code", data.cpmType).text(data.cpmPrefix));
		});

		$('#prefixId8').trigger('chosen:updated');
	}

}

function getPrefixData() {

	debugger;
	$('.warning-div').addClass('hide');

	var prefix = $('#prefixId8 option:selected').val();
	var type = $('#prefixId8 option:selected').attr('code');
	var errorList = [];

	if (errorList.length > 0) {
		showErr(errorList);
	} else {
		var url = "SequenceConfigrationMaster.html?searchPrefixDataById";
		var requestData = {
			"prefix" : prefix,
			"type" : type
		}

		debugger;

		var ajaxResponse = __doAjaxRequest(url, 'post', requestData, false,
				'json');

		$('#prefixId9').html('');

		if (type == "H") {
			$("#prefixId9").prop('disabled', false);
			$("#seqOrder9").prop('disabled', false);
			$('#prefixId9').append(
					$("<option></option>").attr("value", 0).attr("code", 0)
							.text("select level"));
			$.each(ajaxResponse, function(index, data) {
				$('#prefixId9').append(
						$("<option></option>").attr("value", data.lookUpCode)
								.attr("code", data.lookUpId).text(
										data.lookUpDesc));
			});
			$('#prefixId9').trigger('chosen:updated');
		} else if (type == "N") {
			$("#prefixId9").prop('disabled', true);
			$("#prefixId9").val("");
			$("#seqOrder9").prop('disabled', true);
			$("#seqOrder9").val("");
		}

	}
}
