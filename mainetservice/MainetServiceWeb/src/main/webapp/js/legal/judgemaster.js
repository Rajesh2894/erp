$(document).ready(function() {
	$('#judgeMasterForm').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});

	$("#id_judgeMasterTbl").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true
	});
	
	//Defect #148843
	var judgeBenchNameVal = $('#judgeBenchName').val();
	if (judgeBenchNameVal == 0) {
		$('#judgeBenchName').val().trigger("chosen:updated");
	}

});
function openAddJudgeMaster(formUrl, actionParam) {
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function Proceed(element) {
	var errorList = [];
	errorList = validateJudgeEntryDetails();
	errorList = ValidateJudgeForm().concat(errorList);

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);

	} else {
		return saveOrUpdateForm(element,
				getLocalMessage('lgl.saveJudgetMaster'), 'JudgeMaster.html',
				'saveform');
	}
}

function addEntryData(tableId) {
	var errorList = [];
	errorList = validateJudgeEntryDetails();
	if (errorList.length == 0) {
		$("#errorDiv").hide();
		addTableRow2(tableId);
	} else {
		displayErrorsOnPage(errorList);
	}
}

function modifyJudge(judge_id, formUrl, actionParam, mode) {
	var divName = '.content-page';
	var requestData = {
		"mode" : mode,
		"id" : judge_id
	};
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
			'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
};

function showConfirmBoxForDelete(judge_id) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	message += '<h4 class=\"text-center text-blue-2 padding-12\">'
			+ getLocalMessage('lgl.delete.confirm') + '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + court_id + ')"/>' + '</div>';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}

function proceedForDelete(judge_id) {
	$.fancybox.close();
	var requestData = {
		"mode" : "D",
		"id" : court_id
	};
	var ajaxResponse = doAjaxLoading('JudgeMaster.html?DeleteJudgeMaster',
			requestData, 'html');
	if (ajaxResponse == 'false') {
		showErrormsgboxTitle(getLocalMessage('legal.error.occur'))
	}
}

function deleteEntry(tableId, obj, ids) {
	deleteTableRow2(tableId, obj, ids);
}

function ValidateJudgeForm() {
	var errorList = [];
	var judgeFName = $("#judgeFName").val();
	var judgeLName = $("#judgeLName").val();
	var judgeMName = $("#judgeMName").val();
	var judgeGender = $("#judgeGender").val();
	var judgeBenchName= $("#judgeBenchName").val();
	/*var judgeDob = $("#judgeDob").val();
	var judgeContactNo = $("#judgeContactNo").val();
	var judgeAddress = $("#judgeAddress").val();*/
	var contactPersonName = $("#contactPersonName").val();
	var contactPersonPhoneNo = $("#contactPersonPhoneNo").val();
	
	

	if (judgeFName == null || judgeFName.trim() == "" )
		errorList.push(getLocalMessage("lgl.validation.judgeFName"));
	if ( judgeLName == null || judgeLName.trim() == "")
		errorList.push(getLocalMessage("lgl.validation.judgeLName"));
      //Defect #106449
	if ( judgeBenchName == null || judgeBenchName.trim() == "")
		errorList.push(getLocalMessage("lgl.validation.judgeBenchName"));
	
	/*if ( judgeMName == null || judgeMName.trim() == "")
		errorList.push(getLocalMessage("lgl.validation.judgeMName"));*/
/*	if ( judgeGender == null || judgeGender.trim() == "" || judgeGender =="0")
		errorList.push(getLocalMessage("lgl.validation.judgeGender"));*/
	
	/*if (judgeDob == "" || judgeDob == null)
		errorList.push(getLocalMessage("lgl.validation.judgeDob"));
	if (judgeContactNo == "" || judgeContactNo == null)
		errorList.push(getLocalMessage("lgl.validation.judgeContactNo"));
	if (judgeAddress == "" || judgeAddress == null)
		errorList.push(getLocalMessage("lgl.validation.judgeAddress"));*/
	
	
	
	/*if ( contactPersonName == null || contactPersonName.trim() == "")
		errorList.push(getLocalMessage("lgl.validation.cpName"));*/
	
	
/*	if ( contactPersonPhoneNo == null || contactPersonPhoneNo.trim() == "")
		errorList.push(getLocalMessage("lgl.validation.cpPhoneNo"));*/
	
	/*var empDOB =$("#judgeDob").val();
	 var today = new Date();
	   var curr_date = today.getDate();
	   var curr_month = today.getMonth() + 1;
	   var curr_year = today.getFullYear();

	   var pieces = empDOB.split('/');
	   var birth_date = pieces[0];
	   var birth_month = pieces[1];
	   var birth_year = pieces[2];

	   if(curr_date < birth_date)
		   curr_month=curr_month-1;
		 if(curr_month < birth_month)
			 curr_year=curr_year-1;
		 curr_year=curr_year-birth_year;
		if( curr_year <= 0)
		errorList.push(getLocalMessage("citizen.login.reg.dob.error1")); 
	    else if(curr_year<18)
				 errorList.push(getLocalMessage("citizen.login.reg.dob.error3"));*/

	return errorList;
}

function validateJudgeEntryDetails() {
	var errorList = [];
	var courtName = [];
	var j = 0;
	if ($.fn.DataTable.isDataTable('#judgemasterTbl')) {
		$('#judgemasterTbl').DataTable().destroy();
	}
	$("#judgemasterTbl tbody tr")
			.each(
					function(i) {
						var crtType = $("#crtType" + i).val();
						var crtName = $("#crtName" + i).val();
						var crtAddress = $("#crtAddress" + i).val();
						
						var judgeStatus = $("#judgeStatus" + i).val();
						var rowCount = i + 1;

						/*if (crtType == "" || crtType == null) {
							errorList.push(
									 getLocalMessage("lgl.validation.crtType")
									+ rowCount);
						}*/

						
						if (courtName.includes(crtName)) {
							errorList.push("Duplicate court name for  entry at row "+ rowCount);
						}else{
							courtName.push(crtName);	
						}
						
						
						if (crtName == "" || crtName == null) {
							errorList.push(
									getLocalMessage("lgl.validation.crtName " )
									+ rowCount);
						}
						/*if (crtAddress == "" || crtAddress == null) {
							errorList
									.push( getLocalMessage("lgl.validation.crtAddress")
											+ rowCount);
						}*/
						
						/*if (judgeStatus == "" || judgeStatus == null) {
							errorList
									.push( getLocalMessage("lgl.validation.judgeStatus")
											+ rowCount);
						}*/

					});
	return errorList;
}

function showCourtAddress(element) {
	var crtId = $(element).val();
	var crtElementId = $(element).attr('id');
	var index = crtElementId.charAt(crtElementId.length - 1);
	var crtAddress = "";
	if(crtId == null || crtId == ""){
		$("#crtAddress" + index).val(crtAddress);
		return;
	}
	var requestUrl = "CourtMaster.html?getCourtById";
	var requestData = {
		"id" : crtId
	};
	var ajaxResponse = __doAjaxRequest(requestUrl, 'post', requestData, false,
			'json');
	if (ajaxResponse != null && (typeof ajaxResponse === 'string')) {
		crtAddress = ajaxResponse;
	}
	$("#crtAddress" + index).val(crtAddress);

}

function fnValidatePAN(Obj) {
	$('.error-div').hide();
	var errorList = [];
	if (Obj == null)
		Obj = $('#pannumber').val();
	if (Obj.value != "") {
		ObjVal = Obj.value;
		var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
		var code = /([C,P,H,F,A,T,B,L,J,G])/;
		var code_chk = ObjVal.substring(3, 4);
		if (ObjVal.search(panPat) == -1) {
			errorList.push('Invaild PAN Number');
			$('#pannumber').val("");
		} else if (code.test(code_chk) == false) {
			errorList.push('Invaild PAN Number');
			$('#pannumber').val("");
		}
	}
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
};

//Add specific table-Row
function addTableRow2(tableId) {
	var id = "#" + tableId;
	
	// remove datatable specific properties
	if ($.fn.DataTable.isDataTable('' + id + '')) {
		$('' + id + '').DataTable().destroy();
	}
	$(".fromDateClass").datepicker("destroy");
	$(".toDateClass").datepicker("destroy");
	
	var content = $(id + ' tr').last().clone();
	content.find("input:text").val('').prop( "disabled", false );
	content.find("input:hidden").val('');
	content.find("textarea").val('');
	content.find("select").val('').prop( "disabled", false );
	content.find("input:checkbox").removeAttr('checked').prop( "disabled", false );
	content.appendTo('tbody');
	reOrderTableIdSequence(id);
	
	
	$( "input[id^='sequence']" ).prop( "disabled", true );

	$(".fromDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect : function(selected) {
			$(".toDateClass").datepicker("option", "minDate", selected)
		}
	});
	$(".toDateClass").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect : function(selected) {
			$(".fromDateClass").datepicker("option", "maxDate", selected)
		}
	});
	// adding datatable specific properties
	dataTableProperty(id);
}

// remove specific table-Row
function deleteTableRow2(tableId, rowObj, deletedId) {
	
	var id = "#" + tableId;
	var error = [];
	// remove datatable specific properties
	if ($.fn.DataTable.isDataTable(id)) {
		$(id).DataTable().destroy();
		$(".fromDateClass").datepicker("destroy");
		$(".toDateClass").datepicker("destroy");
	}
	var rowCount = $("" + id + " tbody tr").length;

	// if rowCount is 1, it means only one row present
	if (rowCount != 1) {
		var deletedSorId = $(rowObj).closest('tr').find(
				'input[type=hidden]:first').attr('value');
		$(rowObj).closest('tr').remove();

		if (deletedSorId != '') {
			var prevValue = $('#' + deletedId).val();
			if (prevValue != '')
				$('#' + deletedId).val(prevValue + "," + deletedSorId);
			else
				$('#' + deletedId).val(deletedSorId);
		}
		reOrderTableIdSequence(id);
		$(".fromDateClass").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				$(".toDateClass").datepicker("option", "minDate", selected)
			}
		});

		$(".toDateClass").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			onSelect : function(selected) {
				$(".fromDateClass").datepicker("option", "maxDate", selected)
			}
		});
		// adding datatable specific properties
		dataTableProperty(id);
	}
}

//127193
function searchJugdgeData(){debugger;
	var errorList = [];
	var crtId = replaceZero($("#crtId").val());
	var judgeStatus = replaceZero($("#judgeStatus").val());	
	var judgeBenchName = replaceZero($("#judgeBenchName").val());
	if ((judgeStatus == "" || judgeStatus == undefined ) && (crtId == "" || crtId == undefined ) && (judgeBenchName == "" || judgeBenchName == undefined)){
		errorList.push(getLocalMessage("lgl.select.criteria"))
		displayErrorsOnPage(errorList);
		return;
	}
		var data = {
			"crtId" :  replaceZero($("#crtId").val()),
			"judgeStatus"  : $("#judgeStatus").val(),
			"judgeBenchName" :$("#judgeBenchName").val(),
		};
		var divName = '.content-page';
		var formUrl = "JudgeMaster.html?searchJugdgeData";
		var ajaxResponse = doAjaxLoading(formUrl, data, 'html', divName);
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
}

function replaceZero(value) {
	return value != 0 ? value : undefined;
}



function reset() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'JudgeMaster.html');
	$("#postMethodForm").submit();
}
