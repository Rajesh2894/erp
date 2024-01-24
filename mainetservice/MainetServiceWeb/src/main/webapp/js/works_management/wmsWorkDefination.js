var WorkDefURL = "WmsWorkDefinationMaster.html";
var removeAssetIdArray = [];
var removeYearIdArray = [];
var removeFileIdArray = [];
var removeSancDetIdArray = [];
var removeWardZoneById=[];
$(document).ready(function() {
	
	getTotalAmount();
	$("input").on("keypress",function(e) {
		if ((e.which === 32 && !this.value.length)
				|| e.which == 13 || e.which == 34
				|| e.which == 39)
			e.preventDefault();
	});
	
	$("#datatables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ],
			[ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true
	});

	$('#WmsWorkDefinationMaster').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});
	
	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : '-0d',
		yearRange : "-100:-0"
	});	
	$('.datepickerSanction').datepicker({
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
		yearRange : "1900:2200"
	});
	$('.actualWorkCompletiondate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		yearRange : "1900:2200"
	});

	$(".worksDate").bind("keyup change", function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	var saveMode = $("#mode").val();
	if (saveMode == 'E') {
		if ($('#workType').val() != '') {
			var workType = $('#workType').find("option:selected").attr('code');
			if (workType != 'M') {
				$("#assetData").hide();
			} else {
				$("#assetData").show();
			}
		}
	}
	debugger;
	if (saveMode =='C' || saveMode =='E' ){
		$("#sanctionDetData").hide();
	} 
	if(saveMode =='E' && ($('#workType').find("option:selected").attr('code')) == 'L'){
		$("#sanctionDetData").show();
	}
	/*if ($("#mode").val() == 'E') {
		$('.SanctionDetailsClass').each(function(i) {
			var sancNature = $('#sancNature' + i).find("option:selected").val();
			var workSancNo = $('#workSancNo' + i).val();
			if (sancNature == 'A' && workSancNo != '') {
				$('#sancNature' + i).attr("readonly", true);
				$('#deptId' + i).attr("readonly", true);
				$('#workDesignBy' + i).prop("readonly", true);
				$('#workSancBy' + i).prop("readonly", true);
				$('#workSancNo' + i).prop("readonly", true);
				$('#workSancDate' + i).prop("readonly", true);
			} else {
				$('#sancNature' + i).attr("readonly", false);
				$('#deptId' + i).attr("readonly", false);
				$('#workDesignBy' + i).prop("readonly", false);
				$('#workSancBy' + i).prop("readonly", false);
				$('#workSancNo' + i).prop("readonly", false);
				$('#workSancDate' + i).prop("readonly", false);
			}
		});
	}*/
	
	var dateFields = $('.datepicker');
	dateFields.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});

	var datepickerEndDate = $('.datepickerEndDate');
	datepickerEndDate.each(function() {
		var fieldValue = $(this).val();
		if (fieldValue.length > 10) {
			$(this).val(fieldValue.substr(0, 10));
		}
	});
	var cpdMode = $("#cpdMode").val();
	var errorList = [];
	if (cpdMode == "") {
		errorList.push(getLocalMessage("work.Def.valid.setSLI"));
		displayErrorsOnPage(errorList);
	}
	
	$("#addWorkDefination").click(function() {	
		var errorList = [];
		var isDefaulValue = __doAjaxRequest(WorkDefURL+ '?checkForDefaultSLI',
				'POST', {}, false, 'json');
		if (isDefaulValue == "N") {
			errorList.push(getLocalMessage("work.Def.valid.setSLI"));
			displayErrorsOnPage(errorList);
		} else {
			var divName = '.content-page';
			var ajaxResponse = __doAjaxRequest(WorkDefURL + '?form',
					'POST', {}, false, 'html');
			$('.content').removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
		}
	});

	$("#resetWorkDef").click(function() {
		var divName = '.content-page';
		var ajaxResponse = __doAjaxRequest(WorkDefURL
				+ '?form', 'POST', {}, false, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
	});

	$("#searchWorkDef").click(function() {		
		var errorList = [];
		var workcode = $('#workcode').val();
		var workName = $('#workName').val();
		var workStartDate = $('#workStartDate').val();
		var workEndDate = $('#workEndDate').val();
		var projId = $('#projId').val();
		var workType = $('#workType').val();
		var workProjPhase = $('#workProjPhase').val();
		if (workcode != '' || workName != ''|| workStartDate != ''|| workEndDate != ''
			|| projId != ''|| workType != "0" || workProjPhase != "0") {
			if ((workStartDate != '' && workEndDate == '')
					|| (workStartDate == '' && workEndDate != '')) {
				errorList.push(getLocalMessage('work.Def.valid.startdate.enddate'));
			}
			if (errorList.length > 0) {
				displayErrorsOnPage(errorList);
			} else {
				var table = $('#datatables').DataTable();
				table.rows().remove().draw();
				$(".warning-div").hide();
				var theForm = "#WmsWorkDefinationMaster";
				var requestData = __serializeForm(theForm);				
				var ajaxResponse = __doAjaxRequest(WorkDefURL+ '?filterRecords',
						'POST', requestData,false, 'json');
				var result = [];
				$.each(ajaxResponse,function(index) {
					var obj = ajaxResponse[index];
					result.push([obj.workcode,
						obj.workName,obj.workTypeDesc,obj.projName,
						/*
						 * RemoveAs Per SUDA UAT
						 * obj.startDateDesc, obj.endDateDesc, obj.workProjPhaseDesc, 
						 */
						'<td>'
						+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5 "  onClick="viewWorkDef(\''
						+ obj.workId
						+ '\')" title="View Work Definition"><i class="fa fa-eye"></i></button>'
						+ '<button type="button" class="btn btn-success btn-sm margin-right-10" onClick="editWorkDef(\''
						+ obj.workId
						+ '\')"  title="Edit Work Definition"><i class="fa fa-pencil-square-o"></i></button>'
						+ '</td>' ]);
				});
				table.rows.add(result);
				table.draw();
			}
		} else {
			errorList.push(getLocalMessage('work.Def.valid.select.any.field'));
			displayErrorsOnPage(errorList);
		}
	});	
});

function getTotalAmount() {	
	$("#totalAmount").val("0.00");
	var amount = 0;
	var rowCount = $('#finance tr').length;
	for (var m = 0; m <= rowCount - 1; m++) {
		var n = parseFloat(parseFloat($("#yeBugAmount" + m).val()));
		if (isNaN(n)) {
			return n = 0;
		}
		amount += n;
		var result = amount.toFixed(2);
		$("#totalAmount").val(result);
	}
}

/*
 * function getProjCode() { var selectedType =
 * $("#projId").find("option:selected").attr('code');
 * $("#projCode").val(selectedType); }
 */

function editWorkDef(workId) {
	var errorList = [];
	var isDefaulValue = __doAjaxRequest(WorkDefURL + '?checkForDefaultSLI',
			'POST', {}, false, 'json');
	if (isDefaulValue == "N") {
		errorList.push(getLocalMessage("work.Def.valid.setSLI"));
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		var requestData = 'workId=' + workId + '&type=E';
		var ajaxResponse = __doAjaxRequest(WorkDefURL + '?formForUpdate',
				'POST', requestData, false, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		var workStatus = $("#workStatus").val();
	}
}

function viewWorkDef(workId) {
	var errorList = [];
	var isDefaulValue = __doAjaxRequest(WorkDefURL + '?checkForDefaultSLI',
			'POST', {}, false, 'json');
	if (isDefaulValue == "N") {
		errorList.push(getLocalMessage("work.Def.valid.setSLI"));
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		var requestData = 'workId=' + workId + '&type=V';
		var ajaxResponse = __doAjaxRequest(WorkDefURL + '?formForUpdate',
				'POST', requestData, false, 'html');
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		$("#WmsWorkDefinationMaster :input").prop("disabled", true);
		$("#button-Cancel").prop("disabled", false);
	}
}

$("#financeDataDetails").on("click", '.addFinanceDetails', function(e) {
	var errorList = [];
	errorList = validateFinancialDetails(errorList);
	if (errorList.length == 0) {
		// var removedRow = $('#finance tr').last().remove();
		var content = $('#financeDataDetails tr').last().clone();
		$('#financeDataDetails tr').last().after(content);
		// $('#finance tr').last().after(removedRow);
		content.find("select").val("");
		content.find("input:hidden").val("");
		content.find("input:text").val('');
		reOrderWorkDefTableSequence();
	} else {
		displayErrorsOnPage(errorList);
	}

});

function validateFinancialDetails(errorList) {
	$(".finacialInfoClass").each(function(i) {
		var financeCodeDesc = $("#financeCodeDesc" + i).val();
		var faYearId = $("#faYearId" + i).val();
		// var yearPercntWork = $("#yearPercntWork" + i).val();
		var aprrovalNo = $("#yeDocRefNo" + i).val();
		var yeBugAmount = $("#yeBugAmount" + i).val();
		var cpdMode = $("#cpdMode").val();
		var sacHeadId = $("#sacHeadId" + i).val();
		var row = i + 1;
		if (cpdMode == 'L') {
			if (sacHeadId == '') {
				errorList.push(getLocalMessage("work.Def.valid.select.finCode")+ " - " + row);
			}
		} else {
			if (financeCodeDesc == '') {
				errorList.push(getLocalMessage("work.Def.valid.enter.finCode")+ " - " + row);
			}
		}
		if (faYearId == "" || faYearId == null) {
			errorList.push(getLocalMessage("work.Def.valid.selectFinYear")	+ " - " + row);
		}
		/*
		 * if (yearPercntWork == "" || yearPercntWork == null) {
		 * errorList .push(getLocalMessage("work.Def.valid.enter.pergcentage") + " - " + row); }
		 * if (yearPercntWork > 100.00) { errorList .push(getLocalMessage("work.Def.valid.percentage") + " - " + row); }
		 */
		if (aprrovalNo == "" || aprrovalNo == null) {
			errorList.push(getLocalMessage("work.Def.valid.enter.doc.ref.no")	+ " - " + row);
		}
		if (yeBugAmount == "" || yeBugAmount == null) {
			errorList.push(getLocalMessage("work.Def.valid.enter.appv.amount")
					+ " - " + row);
		}
	});
	return errorList;
}

function validateAssetDetails(errorList) {
	$(".assetInf").each(function(i) {
		var assetCode = $("#assetCode" + i).val();
		var assetName = $("#assetName" + i).val();
		var assetCategory = $("#assetCategory" + i).val();
		var assetDepartment = $("#assetDepartment" + i).val();
		var assetLocation = $("#assetLocation" + i).val();
		if (assetCode == "" && assetName == ""
			&& assetCategory == "" && assetDepartment == ""
				&& assetLocation == "") {
			errorList.push(getLocalMessage("work.Def.valid.select.any.asset"));
		}
	});
	return errorList;
}

$("#financeDataDetails").on("click",'.delButton',function(e) {

	var errorList = [];
	var countRows = -1;
	$('.finacialInfoClass').each(function(i) {
		if ($(this).closest('tr').is(':visible')) {
			countRows = countRows + 1;
		}
	});
	var row = countRows;

	if (row == 0) {
		$("#faYearId0").val("");
		$("#sacHeadId0").val("").trigger('chosen:updated');	
		// $("#yearPercntWork0").val("");
		$("#yeDocRefNo0").val("");
		$("#yeBugAmount0").val(""); 
		$("#financeCodeDesc0").val('');
		var deletedYearId = $(this).parent().parent().find(
		'input[type=hidden]:first').attr('value');
		
		if (deletedYearId != '') {
			removeYearIdArray.push(deletedYearId);
		}
	}
	if (row != 0) {
		$(this).parent().parent().remove();
		row--;
		var deletedYearId = $(this).parent().parent().find(
		'input[type=hidden]:first').attr('value');
		if (deletedYearId != '') {
			removeYearIdArray.push(deletedYearId);
		}
	}
	$('#removeYearIds').val(removeYearIdArray);
	reOrderWorkDefTableSequence();
	getTotalAmount();
	e.preventDefault();
});

function reOrderWorkDefTableSequence() {	
	var cpdMode = $("#cpdMode").val();
	if (cpdMode == 'L') {
		$(".finacialInfoClass").each(function(i) {
			$(this).find("input:hidden:eq(0)").attr("id","yearId" + (i)).attr("name",
							"wmsDto.yearDtos[" + (i) + "].yearId");
			$(this).find("input:hidden:eq(1)").attr("id","finActiveFlag" + (i)).val("A");
			$(this).find("select:eq(0)").attr("id","faYearId" + (i)).attr("name",
					"wmsDto.yearDtos[" + (i) + "].faYearId")
					.attr("onchange","resetFinanceCode(this," + i + ")");
			$(this).find("select:eq(1)").attr("id","sacHeadId" + (i)).attr("name",
					"wmsDto.yearDtos[" + (i) + "].sacHeadId")
					.attr("onchange","checkForDuplicateHeadCode(this,"+ i + ")");
			$(this).find("button:eq(0)").attr("id","viewExpDet" + (i)).attr("onclick",
					"viewExpenditureDetails(" + i + ")");
			/*
			 * $(this).find("input:text:eq(0)").attr("id",
			 * "yearPercntWork" + (i)).attr( "name",
			 * "wmsDto.yearDtos[" + (i) + "].yearPercntWork");
			 */
			$(this).find("input:text:eq(1)").attr("id","yeDocRefNo" + (i)).attr("name",
					"wmsDto.yearDtos[" + (i) + "].yeDocRefNo");
			$(this).find("input:text:eq(2)").attr("id","yeBugAmount" + (i)).attr("name",
					"wmsDto.yearDtos[" + (i) + "].yeBugAmount").addClass("Comp");
			
			$(this).find("input:hidden:eq(1)").attr("name","wmsDto.yearDtos[" + (i)+ "].finActiveFlag");
		});
	} else {
		$(".finacialInfoClass").each(function(i) {
			$(this).find("input:hidden:eq(0)").attr("id","yearId" + (i)).attr("name",
					"wmsDto.yearDtos[" + (i) + "].yearId");
			$(this).find("input:hidden:eq(1)").attr("id",
					"finActiveFlag" + (i)).val("A");
			$(this).find("select:eq(0)").attr("id", "faYearId" + (i))
			.attr("name","wmsDto.yearDtos[" + (i) + "].faYearId")
			.attr("onchange","resetFinanceCode(this," + i + ")");
			$(this).find("input:text:eq(0)").attr("id","financeCodeDesc" + (i)).attr("name",
					"wmsDto.yearDtos[" + (i) + "].financeCodeDesc")
					.attr("onchange","checkForDuplicateFinanceCode(this," + i+ ")");
			$(this).find("button:eq(0)").attr("id", "viewExpDet" + (i))
			.attr("onclick","viewExpenditureDetails(" + i + ")");
			/*
			 * $(this).find("input:text:eq(1)").attr("id",
			 * "yearPercntWork" + (i)).attr("name", "wmsDto.yearDtos[" +
			 * (i) + "].yearPercntWork");
			 */
			$(this).find("input:text:eq(1)").attr("id","yeDocRefNo" + (i)).attr("name",
					"wmsDto.yearDtos[" + (i) + "].yeDocRefNo");
			$(this).find("input:text:eq(2)").attr("id","yeBugAmount" + (i)).attr("name",
					"wmsDto.yearDtos[" + (i) + "].yeBugAmount").addClass("Comp");
			$(this).find("input:hidden:eq(1)").attr("name",
					"wmsDto.yearDtos[" + (i) + "].finActiveFlag");
		});
	}
}

$("#asset").on("click", '.addAsset', function(e) {
	var errorList = [];
	var workType = $('#workType').find("option:selected").attr('code');
	if (workType == 'M') {
		errorList = checkAssetDetails(errorList);
	} else {
		errorList = validateAssetDetails(errorList);
	}
	if (errorList.length == 0) {
		var content = $('#asset tr').last().clone();
		$('#asset tr').last().after(content);
		content.find("select").val("");
		content.find("input:text").val('');
		content.find("input:hidden").val("");
		reOrderAssetTableSequence();

	} else {
		displayErrorsOnPage(errorList);
	}
});

$("#asset").on("click",'.delAsset',function(e) {
	
	var errorList = [];
	var rowCount = $('#asset tr').length;
	if (rowCount <= 2) {
		return false;
		// errorList.push(getLocalMessage("first.row.cannot.be.deleted"));
	}
	/*
	 * if(errorList.length > 0){ displayErrorsOnPage(errorList); return
	 * false; }else{
	 */
	$(this).parent().parent().remove();	
	var deletedAssetId = $(this).parent().parent().find('input.drid').attr('value');
	if (deletedAssetId != '') {
		removeAssetIdArray.push(deletedAssetId);
	}
	$('#removeAssetIds').val(removeAssetIdArray);
	reOrderAssetTableSequence();
	// }
});

function reOrderAssetTableSequence() {
	$(".assetInf").each(function(i) {
		$(this).find("input:hidden:eq(0)").attr("id","workAssetId" + (i))
		.attr("name","wmsDto.assetInfoDtos[" + (i)+ "].workAssetId");
		$(this).find("input:hidden:eq(1)").attr("id","assetActiveFlag" + (i)).val("A");
		$(this).find("input:hidden:eq(2)").attr("id","assetId" + (i)).attr("name",
				"wmsDto.assetInfoDtos[" + (i) + "].assetId");
		$(this).find("input:text:eq(0)").attr("id","assetCode" + (i)).attr("name",
				"wmsDto.assetInfoDtos[" + (i) + "].assetCode");
		$(this).find("input:text:eq(1)").attr("id","assetName" + (i)).attr("name",
				"wmsDto.assetInfoDtos[" + (i) + "].assetName");
		$(this).find("input:text:eq(2)").attr("id","assetCategory" + (i)).attr("name","wmsDto.assetInfoDtos[" + (i)+ "].assetCategory");
		$(this).find("input:text:eq(3)").attr("id","assetDepartment" + (i)).
		attr("name","wmsDto.assetInfoDtos[" + (i)+ "].assetDepartment");
		$(this).find("input:text:eq(4)").attr("id","assetLocation" + (i)).attr(
				"name","wmsDto.assetInfoDtos[" + (i)+ "].assetLocation");
		$(this).find("input:text:eq(5)").attr("id","assetStatus" + (i)).attr(
				"name","wmsDto.assetInfoDtos[" + (i)+ "].assetStatus");
		$(this).find("input:hidden:eq(1)").attr("name","wmsDto.assetInfoDtos[" + (i)+ "].assetActiveFlag");
	});
}

$("#attachDoc").on("click", '.delBtn', function(e) {
	var countRows = -1;
	$('.appendableClass').each(function(i) {
		if ($(this).closest('tr').is(':visible')) {
			countRows = countRows + 1;
		}
	});
	var row = countRows;
	if (row != 0) {
		$(this).parent().parent().remove();
		row--;
	}
	e.preventDefault();
});

$("#deleteDoc").on("click",'#deleteFile',function(e) {
	var errorList = [];
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
		return false;
	} else {
		$(this).parent().parent().remove();
		var fileId = $(this).parent().parent().find(
		'input[type=hidden]:first').attr('value');
		if (fileId != '') {
			removeFileIdArray.push(fileId);
		}
		$('#removeFileById').val(removeFileIdArray);
	}
});

function documentUpload(element) {
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest(WorkDefURL + '?fileCountUpload', 'POST',
			requestData, false, 'html');
	$("#uploadTagDiv").html(response);
	prepareTags();

}

function saveWorkDef(obj) {	

	var errorList = [];
	var projID = $("#projId").find("option:selected").attr('value');

	/*Defect #80491*/
	var workStatus = $("#workStatus").val();
	var removeYearIds = $('#removeYearIds').val();
	if($("#mode").val() == 'E')
	 errorList = validateFinancialDetails(errorList);
	if(workStatus == "D" || workStatus == "" && errorList.length>0 ){
		errorList = [];}
	if(workStatus != "D" && errorList.length>0){
		errorList.push(getLocalMessage('wms.financialInfo.notDelete.budget.approve'));
		displayErrorsOnPage(errorList);
		return false;
	}


	errorList = validateWorkDefination(errorList);	
	if($("#mode").val() == 'C')
	errorList = validateitemDetailsTable(errorList);
	if(projID != "" || projID != 0){
		var requestData = 'projID='+projID;
		var ajaxResponse = __doAjaxRequest(
				'WmsWorkDefinationMaster.html?checkSSFCode', 'POST',requestData, false,'json');
		errorList = validateDataForFinancialYear(errorList);
		if(ajaxResponse == "Y" && errorList.length == 0){
			errorList = validateFinancialDetailsForSave(errorList);
		}
	}		
	var workType = $('#workType').find("option:selected").attr('code');
	if (workType == 'M') {
		errorList = checkAssetDetails(errorList);
	}else if(workType == 'L'){
		errorList = validateSanctionDetailsList(errorList);
	}
	
	if (errorList.length == 0) {
		
		var formName = findClosestElementId(obj, 'form');
		 var theForm = '#' + formName; 
		 var requestData = __serializeForm(theForm);
		//return saveOrUpdateForm(obj, "", WorkDefURL, 'saveform');		
		var data = __doAjaxRequest("WmsWorkDefinationMaster.html?saveWorkDefinition", 'POST',requestData, false,'json');
		var message='';
		
		 message	+='<p class="text-blue-2 text-center padding-15">'+ data.messageText+'<br>Do you want to continue for Work Estimation?</p>';
		 message	+='<div class=\'text-center padding-bottom-10\'>'+	
		'<button class=\'btn btn-success\' onclick="openWorkEstimate('+data.workId+','+data.projId+')" type=\'button\'>	Yes	</button>&nbsp;'+
		'<button class=\'btn btn-warning\' onclick="closebox(\''+errMsgDiv+'\',\'WmsWorkDefinationMaster.html\')" type=\'button\'>	No	</button>'+
		'</div>';
		
		$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
		$(errMsgDiv).html(message);
		$(errMsgDiv).show();
		$('#btnNo').focus();	   
		showModalBox(errMsgDiv);		
	} else {
		displayErrorsOnPage(errorList);
	}
}

function checkAssetDetails(errorList) {
	$(".assetInf").each(function(i) {
		var assetCode = $("#assetCode" + i).val();
		var assetName = $("#assetName" + i).val();
		var assetCategory = $("#assetCategory" + i).val();
		var assetDepartment = $("#assetDepartment" + i).val();
		var assetLocation = $("#assetLocation" + i).val();
		
		if (assetCode == null || assetCode == "") {
			errorList.push(getLocalMessage('work.def.select.asset.code.entry.no')
					+ (i + 1));
		}
		if (assetName == null || assetName == "") {
			errorList.push(getLocalMessage('work.def.select.asset.name.entry.no')+ (i + 1));
		}
		if (assetCategory == null || assetCategory == "") {
			errorList.push(getLocalMessage('work.def.select.asset.category.entry.no')
					+ (i + 1));
		}
		if (assetDepartment == null || assetDepartment == "") {
			errorList.push(getLocalMessage('work.def.select.asset.department.entry.no')
					+ (i + 1));
		}		
	});
	return errorList;
}

function validateWorkDefination(errorList) {
	var workName = $("#workName").val();
	var projId = $("#projId").val();
	/* REMOVE AS PER SUDA UAT */
	var workStartDate = $("#workStartDate").val();
	var workEndDate = $("#workEndDate").val();
	var workType = $("#workType").val();
	var deptId = $("#deptId").val();
	// var workProjPhase = $("#workProjPhase").val();
	var locIdSt = $("#locIdSt").val();
	/*var locIdEn = $("#locIdEn").val();*/
	var deviationPercent = $("#deviationPercent").val();
	var workCategory = $("#workCategory").val();
	   var codId1     = $("#codId1").val();
	    var codId2    = $("#codId2").val();

	if (workName == null || workName == "") {
		errorList.push(getLocalMessage('work.Def.valid.enter.workname'));
	}
	if (projId == null || projId == "") {
		errorList.push(getLocalMessage('work.Def.valid.select.projName'));
	}
	if (workCategory == null || workCategory == "") {
		errorList.push(getLocalMessage('work.Def.valid.select.workCategory'));
	}

	/* REMOVE AS PER SUDA UAT */

	/*
	 * if (workStartDate == null || workStartDate == "") {
	 * errorList.push(getLocalMessage('work.Def.valid.select.work.startdate')); }
	 * if (workEndDate == null || workEndDate == "") {
	 * errorList.push(getLocalMessage('work.Def.valid.select.work.enddate')); }
	 */

	if (workType == null || workType == 0) {
		errorList.push(getLocalMessage('work.Def.valid.select.worktype'));
	}
	if (deptId == null || deptId == "") {
		errorList.push(getLocalMessage('work.Def.valid.select.execut.dept'));
	}
	/* REMOVE AS PER SUDA UAT */

	 
	if (locIdSt == null || locIdSt == "") {
		errorList.push(getLocalMessage('work.Def.valid.select.start.loc'));
	}
	/*if (locIdEn == null || locIdEn == "") {
		errorList.push(getLocalMessage('work.Def.valid.select.end.loc'));
	}*/
	  
	if(codId1==0){
		errorList.push(getLocalMessage("wms.select.zone"));
	}
	if(codId2==0){
		errorList.push(getLocalMessage("wms.select.ward"));
	}
	

	/* REMOVE AS PER SUDA UAT */

	if (workEndDate != "" && workEndDate != undefined && workStartDate != ""
			|| workStartDate != undefined) {
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date(workStartDate.replace(pattern, '$3-$2-$1'));
		var sDate = new Date(workEndDate.replace(pattern, '$3-$2-$1'));
		if (eDate > sDate) {
			errorList
					.push(getLocalMessage("work.Def.valid.enddate.less.startdate"));
		}
	}
	if (deviationPercent != '' && deviationPercent != '0') {
		var total = parseFloat(deviationPercent);
		if (total > 100) {
			errorList
					.push(getLocalMessage("work.Def.valid.devaition.percentage.doesnot.greater"));
		}
	}
	return errorList;
}

function checkForDuplicateHeadCode(event, currentRow) {	
	$(".error-div").hide();
	var errorList = [];
	$('.finacialInfoClass').each(function(i) {
		var faYearId = $('#faYearId' + i).val();
		var sacHeadId = $("#sacHeadId" + i).val();	
		var c = i + 1;
	/*	if (faYearId == undefined || faYearId == '') {
			errorList
			.push(getLocalMessage("work.Def.valid.selectFinYear")
					+ " - " + c);
		}
		if (sacHeadId == "" || sacHeadId == null) {
			errorList.push(getLocalMessage("work.def.select.financial.code")+ " - " + c);
		}*/
		if (errorList.length == 0) {
			if (currentRow != i&& ($("#faYearId" + currentRow).val() == faYearId && event.value == sacHeadId)) {
				errorList.push(getLocalMessage('work.Def.valid.duplicate.fincode'));
				$("#sacHeadId" + currentRow).val("");
				displayErrorsOnPage(errorList);
				return false;
			}
		} else {
			$("#sacHeadId" + i).val('');
			displayErrorsOnPage(errorList);
			return false;
		}
	});
}

function checkForDuplicateFinanceCode(event, currentRow) {
	$(".error-div").hide();
	var errorList = [];
	$('.finacialInfoClass').each(function(i) {
		var faYearId = $('#faYearId' + i).val();
		var financeCodeDesc = $("#financeCodeDesc" + i).val();
		var c = i + 1;
		if (faYearId == undefined || faYearId == '') {
			errorList.push(getLocalMessage("work.Def.valid.selectFinYear")
					+ " - " + c);
		}
		if (financeCodeDesc == "" || financeCodeDesc == null) {
			errorList.push(getLocalMessage("work.Def.valid.enter.finCode")
					+ " - " + c);
		}
		if (errorList.length == 0) {
			if (currentRow != i&& ($("#faYearId" + currentRow).val() == faYearId && event.value == financeCodeDesc)) {
				errorList.push(getLocalMessage('work.Def.valid.duplicate.fincode'));
				$("#financeCodeDesc" + currentRow).val("");
				displayErrorsOnPage(errorList);
				return false;
			}
		} else {
			$("#financeCodeDesc" + i).val('');
			displayErrorsOnPage(errorList);
			return false;
		}
	});
}

function resetFinanceCode(obj, index) {
	$("#sacHeadId" + index).val("");
	$("#financeCodeDesc" + index).val("");
}
function openLocationMas() {
	var locationURL = 'LocationMas.html'
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', locationURL);
	$("#postMethodForm").submit();
}

function inputPreventSpace(key, obj) {
	if (key == 32 && obj.value.charAt(0) == ' ') {
		$(obj).val('');
	}
}

function validateFinancialDetailsForSave(errorList) {	
	// var detailsEntered = false;

	var total = 0;
	$(".finacialInfoClass").each(function(i) {
		var financeCodeDesc = $("#financeCodeDesc" + i).val();
		var faYearId = $("#faYearId" + i).val();
		// var yearPercntWork = $("#yearPercntWork" + i).val();
		var yeDocRefNo = $("#yeDocRefNo" + i).val();
		var yeBugAmount = $("#yeBugAmount" + i).val();
		var cpdMode = $("#cpdMode").val();
		var sacHeadId = $("#sacHeadId" + i).val();
		var row = i + 1;
		if (faYearId == '') {
			errorList.push(getLocalMessage("work.Def.valid.selectFinYear")
					+ " - " + row);
		}
		if ((financeCodeDesc != "" && financeCodeDesc != undefined)
				|| faYearId != ""/*
				 * || yearPercntWork != ""
				 */
					|| yeDocRefNo != ""
						|| yeBugAmount != ""
							|| (sacHeadId != "" && sacHeadId != undefined)) {
			// detailsEntered = true;
			// var row = i + 1;
			if (cpdMode == 'L') {
				if (sacHeadId == '') {
					errorList.push(getLocalMessage("work.Def.valid.select.finCode")+ " - " + row);
				}
			} else {
				if (financeCodeDesc == '') {
					errorList.push(getLocalMessage("work.Def.valid.enter.finCode")+ " - " + row);
				}
			}
			if (faYearId == 0) {
				errorList.push(getLocalMessage("work.Def.valid.selectFinYear")
						);
			}
			
			if (yeDocRefNo == 0) {
				errorList.push(getLocalMessage("work.Def.valid.enter.doc.ref.no")
				);
			}
			if (yeBugAmount == 0) {
				errorList.push(getLocalMessage("work.Def.valid.enter.appv.amount")
						);
			}
		
		}
	});


	return errorList;
}

function validateDataForFinancialYear(errorList){
	
	var total = 0;
	$(".finacialInfoClass").each(function(i) {
		var financeCodeDesc = $("#financeCodeDesc" + i).val();
		var faYearId = $("#faYearId" + i).val();
		var yeDocRefNo = $("#yeDocRefNo" + i).val();
		var yeBugAmount = $("#yeBugAmount" + i).val();
		var cpdMode = $("#cpdMode").val();
		var sacHeadId = $("#sacHeadId" + i).val();
		var row = i + 1;
		
	
		if ((financeCodeDesc != "" && financeCodeDesc != undefined)
				|| faYearId != "" || yeDocRefNo != "" || yeBugAmount != "" || (sacHeadId != "" && sacHeadId != undefined)) { 
			if (cpdMode == 'L') {
				if (sacHeadId == 0) {
					errorList.push(getLocalMessage("work.Def.valid.select.finCode"));
				}
			} else {
				if (financeCodeDesc == 0) {
					errorList.push(getLocalMessage("work.Def.valid.enter.finCode"));
				}
			}
			if (faYearId == 0) {
				errorList.push(getLocalMessage("work.Def.valid.selectFinYear")
						);
			}			
			if (yeDocRefNo == 0) {
				errorList.push(getLocalMessage("work.Def.valid.enter.doc.ref.no")
						);
			}
			if (yeBugAmount == 0) {
				errorList.push(getLocalMessage("work.Def.valid.enter.appv.amount")
						);
			}			
		}
	});
	return errorList;	
}

$("#sanctionDetailsList").on("click", '.addSanctionDetails', function(e) {	
	
	var workType = null;
	var count = $('#sanctionDetailsList tr').length - 1;
	if ($('#workType').val() != '') {
	 workType = $('#workType').find("option:selected").attr('code');
	}
	var errorList = [];
	errorList = validateSanctionDetailsList(errorList);
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	} else {
		e.preventDefault();
		var clickedRow = $(this).parent().parent().index();
		$(".datepicker").datepicker("destroy");
		var content = $('#sanctionDetailsList tr').last().clone();
		$('#sanctionDetailsList tr').last().after(content);
		content.find("input:hidden").attr("value", "");
		content.find("input:text").val('');
		content.find("select").attr("selected", "selected").val('');
		/*if(workType=='L'){
			content.find("select").attr("selected", "selected").val('A');
		}*/
		content.find("input:text").val('');		
		content.find('input').removeAttr('readonly');
		content.find("select").removeAttr('readonly');	
		reOrderSanctionDetailsList();		
		$('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '-0d',
			yearRange : "-100:-0"
		});
		getWorkType();
	}
});

function validateSanctionDetailsList(errorList) {
	
	$(".SanctionDetailsClass").each(function(i) {
		
		var sanctionType = $("#deptId" + i).find("option:selected").attr('value');
		var sanctionNature = $("#sancNature" + i).find("option:selected").attr('value');
		var sanctionConstatnt = i + 1;
		if (sanctionType == "") {
			errorList.push(getLocalMessage("work.Def.select.sanction.department"));
		}
		if (sanctionNature == "") {
			errorList.push(getLocalMessage("work.Def.select.sanction.nature " ));
		}
	});
	return errorList;
}

function reOrderSanctionDetailsList() {
	$('.SanctionDetailsClass').each(function(i) {
		// Id
		$(this).find("select:eq(0)").attr("id","sancNature" + (i));
		$(this).find("select:eq(1)").attr("id", "deptId" + (i));
		$(this).find("input:hidden:eq(0)").attr("id","workSancId" + (i));
		$(this).find("input:text:eq(0)").attr("id", "sNo" + (i));
		$(this).find("input:text:eq(1)").attr("id","workSancNo" + (i));
		$(this).find("input:text:eq(2)").attr("id","workSancDate" + (i));
		$(this).find("input:text:eq(3)").attr("id","workSancBy" + (i));
		$(this).find("input:text:eq(4)").attr("id","workDesignBy" + (i));

		// Names
		$(this).find("select:eq(0)").attr("name","wmsDto.sanctionDetails[" + (i)+ "].sancNature").attr('getWorkType();');
		$(this).find("select:eq(1)").attr("name","wmsDto.sanctionDetails[" + (i) + "].deptId");
		$(this).find("input:hidden:eq(0)").attr("name","wmsDto.sanctionDetails[" + (i)+ "].workSancId");
		$(this).find("input:text:eq(1)").attr("name","wmsDto.sanctionDetails[" + (i)+ "].workSancNo");
		$(this).find("input:text:eq(2)").attr("name","wmsDto.sanctionDetails[" + (i)+ "].workSancDate");
		$(this).find("input:text:eq(3)").attr("name","wmsDto.sanctionDetails[" + (i)+ "].workSancBy");
		$(this).find("input:text:eq(4)").attr("name","wmsDto.sanctionDetails[" + (i)+ "].workDesignBy");
		$("#sNo" + i).val(i + 1);
	});
}

$('#sanctionDetailsList').on("click",'.deleteSanctionDetails',function(e) {	
	var errorList = [];
	var count = 0;
	$('.SanctionDetailsClass').each(function(i) {
		count += 1;
	});
	var rowCount = $('#sanctionDetailsList tr').length;
	if (rowCount <= 2) {
		return false;
	}
	$(this).parent().parent().remove();
	var sancId = $(this).parent().parent().find(
	'input[type=hidden]:first').attr('value');
	if (sancId != '') {
		removeSancDetIdArray.push(sancId);
	}
	$('#removeSancDetId').val(removeSancDetIdArray);
	reOrderSanctionDetailsList();
});

function getWorkAssetDetails() {
	var response = getAssetDetails();
	response.length;
	var errorList = [];
	var count = 0;
	$.each(response,function(index) {
		var obj = response[index];
		var flag = false;		
		$("#asset tbody tr").each(function(i) {
			if ($(this).find(':hidden').first().val() != ''
				&& $(this).find(':hidden').last().val() == obj.astId) {
				flag = true;
			}
		});		
		$("#asset tbody tr").each(function(i) {
			if ($(this).find(':hidden').last().val() == obj.astId) {
				flag = true;
			}
		});		
		if (!flag) {
			$('#asset tbody tr').each(function(i) {
				if ($(this).closest('tr').is(':visible')) {
					count = count + 1;
				}
			});
			if (count - 1 == 0) {
				var $CurRow = $('#asset .assetInf').last('tr')
				if ($CurRow.find('td').eq(0).find(':input').val() != '') {
					var content = $('#asset tr').last().clone();
					$('#asset .assetInf').last().after(content);
					content.find("input:text").val("");
					content.find("input:text").val('');
					content.find("input:hidden").val("");
				}
				var $row = $('#asset .assetInf').last('tr');
				$row.find(':hidden').last().val(obj.astId);
				$row.find('td').eq(0).find(':input').val(obj.astCode);
				$row.find('td').eq(1).find(':input').val(obj.assetName);
				$row.find('td').eq(2).find(':input').val(obj.assetClass2Desc);
				$row.find('td').eq(3).find(':input').val(obj.deptName);
				$row.find('td').eq(4).find(':input').val(obj.location);
				reOrderAssetTableSequence();
			} else {
				$('#asset .assetInf').last().after(content);
				content.find("input:text").val("");
				content.find("input:text").val('');
				content.find("input:hidden").val("");
				var $row = $('#asset .assetInf').last('tr');
				$row.find(':hidden').last().val(obj.astId);
				$row.find('td').eq(0).find(':input').val(obj.astCode);
				$row.find('td').eq(1).find(':input').val(obj.assetName);
				$row.find('td').eq(2).find(':input').val(obj.assetClass2Desc);
				$row.find('td').eq(3).find(':input').val(obj.deptName);
				$row.find('td').eq(4).find(':input').val(obj.location);
				reOrderAssetTableSequence();
			}
		}
	});
	if (errorList.length != 0) {
		displayErrorsOnPage(errorList);
		errorList = [];
	}
}

function changeSanctionMode() {
	$('.SanctionDetailsClass').each(function(i) {
		var sancNature = $('#sancNature' + i).find("option:selected").val();
		var workSancNo = $('#workSancNo' + i).val();
		if (sancNature == 'A' && workSancNo != '') {
			$('#sancNature' + i).attr("readonly", true);
			$('#deptId' + i).attr("readonly", true);
			$('#workDesignBy' + i).prop("readonly", true);
			$('#workSancBy' + i).prop("readonly", true);
			$('#workSancNo' + i).prop("readonly", true);
			$('#workSancDate' + i).prop("readonly", true);
		} else {
			$('#sancNature' + i).attr("readonly", false);
			$('#deptId' + i).attr("readonly", false);
			$('#workDesignBy' + i).prop("readonly", false);
			$('#workSancBy' + i).prop("readonly", false);
			$('#workSancNo' + i).prop("readonly", false);
			$('#workSancDate' + i).prop("readonly", false);
		}
	})
}

function closeDiv() {
	$('.add').hide();
}
function assetSearchFun() {	
	$('.add').show();
	searchAsset('#searchAssetChildDialog');
}

function showMap() {	
	// $('.minus').removeClass('hide');
	var errorList = [];
	var locIdSt = $('#locIdSt').val();
	var locIdEn = $('#locIdEn').val();
	if (locIdSt == "") {
		errorList.push(getLocalMessage('work.def.select.start.location'));
		displayErrorsOnPage(errorList);
		return false;
	}
	if (locIdEn == "") {
		errorList.push(getLocalMessage('work.def.select.end.location'));
		displayErrorsOnPage(errorList);
		return false;
	}
	var requestData = {
		'locIdSt' : locIdSt,
		'locIdEn' : locIdEn
	};
	// var data={};
	// var url = 'WmsWorkDefinationMaster.html?getMapPage';
	/* var ajaxResponse = __doAjaxRequest(URL, 'POST', data, false, 'html'); */
	var URL = 'WmsWorkDefinationMaster.html?getMapData';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false, 'Json');
	// mapList=returnData[0];
	/*
	 * $('.content').removeClass('ajaxloader'); $(divName).html(ajaxResponse);
	 */
	initMap2(returnData);
}
function initMap2(mapList) {	
	var locations = [ mapList ];
	var map = new google.maps.Map(document.getElementById('map-canvas'), {
		zoom : parseInt(getLocalMessage("PTM.map.zoom")),
		center : new google.maps.LatLng(
				(getLocalMessage("PTM.lattitute.initialize")),
				parseFloat(getLocalMessage("PTM.Longitutde.initialize"))),
		mapTypeId : google.maps.MapTypeId.ROADMAP
	});
	var infowindow = new google.maps.InfoWindow();
	var marker, i;
	for (i = 0; i < locations[0].length; i++) {
		marker = new google.maps.Marker({
			position : new google.maps.LatLng(locations[0][i][1],
					locations[0][i][2]),
			map : map
		});
		google.maps.event.addListener(marker, 'mouseover',
				(function(marker, i) {
					return function() {
						infowindow.setContent(locations[0][i][0]);
						infowindow.open(map, marker);
					}
				})(marker, i));
	}
}

function viewExpenditureDetails(i) {
	var errorList = [];
	var projId = $("#projId").val();
	var faYearId = $('#faYearId' + i).val();
	var sacHeadId = $('#sacHeadId' + i).val();
	var yeBugAmount = $('#yeBugAmount' + i).val();
	var deptId = $('#deptId').val();
	

	if (projId == '') {
		errorList.push(getLocalMessage('work.Def.valid.select.projName'));
		displayErrorsOnPage(errorList);
		return false;
	}
	if (faYearId == '') {
		errorList.push(getLocalMessage("work.Def.valid.selectFinYear") + " - "+ i)
		displayErrorsOnPage(errorList);
		return false;
	}
	if (sacHeadId == '') {
		errorList.push(getLocalMessage("work.Def.valid.select.finCode") + " - "+ i);
		displayErrorsOnPage(errorList);
		return false;
	}
	if (yeBugAmount == '') {
		errorList.push(getLocalMessage("work.Def.valid.enter.appv.amount")+ " - " + i);
		displayErrorsOnPage(errorList);
		return false;
	}
	if (deptId == '') {
		errorList.push(getLocalMessage("work.Def.valid.enter.appv.deptId")+ " - " + i);
		displayErrorsOnPage(errorList);
		return false;
	}
	var requestData = {
		'projId' : projId,
		'faYearId' : faYearId,
		'sacHeadId' : sacHeadId,
		"yeBugAmount" : yeBugAmount,
		"dpDeptId" : deptId
	};

	var ajaxResponse = __doAjaxRequest("WmsWorkDefinationMaster.html?getBudgetHeadDetails", 'POST',
			requestData, false, 'json');

	if (ajaxResponse.authorizationStatus == 'Y') {
		var message = '';
		var sMsg = '';
		message += '<h4 class="text-center">Budget Details</h4>';
		message += '<div class="margin-right-10 margin-left-10">';
		message += '<table class=\"table table-bordered"\>' + '<tr>'
				+ '<th>Budget' + '</th>' + '<td class="text-right"> '
				+ parseFloat(ajaxResponse.invoiceAmount).toFixed(2) + '</td> '
				+ '</tr>';
		message += '<tr>' + ' <th>Previous Expenditure' + '</th>'
				+ '<td class="text-right">'
				+ parseFloat(ajaxResponse.sanctionedAmount).toFixed(2)
				+ '</td>' + '</tr>';
		message += '<tr>' + ' <th>Current Expenditure' + '</th>'
				+ '<td class="text-right">'
				+ parseFloat(ajaxResponse.billAmount).toFixed(2) + '</td>'
				+ '</tr>';
		message += '<tr>' + ' <th>Balance' + '</th>'
				+ '<td class="text-right">'
				+ parseFloat(ajaxResponse.netPayables).toFixed(2) + '</td>'
				+ '</tr></table>';
		message += '</div>';
		if (ajaxResponse.disallowedRemark == 'Y') {
			sMsg = 'Bill Amount Is Greater Than Remaining Budget Amount';
			message += '<h4 class=\"text-center red padding-12\">' + sMsg
					+ '</h4>';
		}
	} else {
		errorList.push(getLocalMessage("work.def.no.budget.availabe.against.selected.account"));
		displayErrorsOnPage(errorList);
		return false;
	}
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
	return false;
}
function getWorkType() {	
	var workType = null;
	if ($('#workType').val() != '') {
		 workType = $('#workType').find("option:selected").attr('code');
		if (workType != 'M') {
			$("#assetData").hide();
		} else {
			$("#assetData").show();
		}
		if(workType =='M' || workType == 'DP'){
			$("#sanctionDetData").hide();
		}else{
			$("#sanctionDetData").show();
		}
	}
	if(workType == 'L'){
		$('.SanctionDetailsClass').each(function(i) {
			$('#sancNature' + i).val("M").trigger("chosen:updated");
		});
	}else{
		$('.SanctionDetailsClass').each(function(i) {
			$('#sancNature' + i).val("A").trigger("chosen:updated");
		});
	}
}
function openWorkEstimate(workId, projId) {
	var divName = '.content-page';
	$("#errorDiv").hide();
	var requestData = $("form").serialize() + '&projId=' + projId + '&workId=' + workId ;

	var ajaxResponse = doAjaxLoading(
			'WorkEstimate.html?AddWorkEstimate', requestData,
			'html');

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
		$('.fancybox-overlay').hide();
}


//for ward zone 
var selected = [];
$(function() {
	$("#itemDetails").on('change', 'select', function() {
		var errorList = [];
		errorList [0]="Dublicate  Ward is Not Selected";
		var val = $(this);
		$(selected).each(function(i) {
			if(val.val() == selected[i]){
				displayErrorsOnPage(errorList);
				
				val.val("0");
				return;
			}
		});
		
		$(".itemDetailClass").each(function(i){
            selected[i] = $(this).find('td:eq(1) select').val();
		});
	})		
})
$(function() {	
	/* To add new Row into table */
	
	$("#itemDetails").on('click', '.addItemCF', function() {	
		var errorList = [];
		errorList = validateitemDetailsTable(errorList);
		var content = $("#itemDetails").find('tr:eq(1)').clone();
		if (errorList.length == 0) {		
			$(".itemDetailClass").each(function(i){
                selected[i] = $(this).find('td:eq(2) select').val();
			});
			$("#itemDetails").append(content);			
			content.find("select").val('0');
			content.find("input:hidden").val('');
			$('.error-div').hide();
			reOrderItemDetailsSequence(); // reorder id and Path
		} else {
			displayErrorsOnPage(errorList);
			
		}
	});
});

$(function() {
	$("#itemDetails").on('click', '.delButton', function() {
		if($(this).closest("tr").index()!=0){
			$(this).parent().parent().remove();
			var deletedWardId=$(this).parent().parent().find('input[type=hidden]:first').attr('value');
			if(deletedWardId != ''){
				removeWardZoneById.push(deletedWardId);
			}
			$('#removeWardZoneDetId').val(removeWardZoneById);
			selected =[];
			$(".itemDetailClass").each(function(i){
                selected[i] = $(this).find('td:eq(0) select').val();
			});
			reOrderItemDetailsSequence(); // reorder id and Path
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
			displayErrorsOnPage(errorList);
		}
	});
});



function reOrderItemDetailsSequence() {	
	$("#itemDetails tbody tr").each(
			function(i) {
				
				var utp = i;
				if (i > 0) {
					utp = i * 6;
				}
				// IDs
				$(this).find("input:hidden:eq(0)").attr("id", "wardZoneId" + (utp + 1))
						.attr(
								"name",
								"wmsDto.wardZoneDto["
										+ (i) + "].wardZoneId");
				$(this).find("input:hidden:eq(1)").attr("id", "wardZoneId" + (utp + 2))
				.attr(
						"name",
						"wmsDto.wardZoneDto["
								+ (i) + "].wardZoneId");
				$(this).find("input:hidden:eq(2)").attr("id", "wardZoneId" + (utp + 3))
				.attr(
						"name",
						"wmsDto.wardZoneDto["
								+ (i) + "].wardZoneId");
				$(this).find("input:hidden:eq(3)").attr("id", "wardZoneId" + (utp + 4))
				.attr(
						"name",
						"wmsDto.wardZoneDto["
								+ (i) + "].wardZoneId");
				$(this).find("input:hidden:eq(4)").attr("id", "wardZoneId" + (utp + 5))
				.attr(
						"name",
						"wmsDto.wardZoneDto["
								+ (i) + "].wardZoneId");
				
				
				$(this).find("select:eq(0)").attr("id", "codId" + (utp + 1));
				$(this).find("select:eq(1)").attr("id", "codId" + (utp + 2));
				$(this).find("select:eq(2)").attr("id", "codId" + (utp + 3));
				$(this).find("select:eq(3)").attr("id", "codId" + (utp + 4));
				$(this).find("select:eq(4)").attr("id", "codId" + (utp + 5));

				$(this).find("select:eq(0)").attr(
						"name",
						"wmsDto.wardZoneDto[" + i
								+ "].codId1");
				$(this).find("select:eq(1)").attr(
						"name",
						"wmsDto.wardZoneDto[" + i
								+ "].codId2");
				$(this).find("select:eq(2)").attr(
						"name",
						"wmsDto.wardZoneDto[" + i
								+ "].codId3");
				$(this).find("select:eq(3)").attr(
						"name",
						"wmsDto.wardZoneDto[" + i
								+ "].codId4");
				$(this).find("select:eq(4)").attr(
						"name",
						"wmsDto.wardZoneDto[" + i
								+ "].codId5");

			});
}

function validateitemDetailsTable(errorList) {	
	
	var subCategory = [];

	var rowCount = $('#itemDetails tr').length;
	if ($.fn.DataTable.isDataTable('#itemDetails')) {
		$('#itemDetails').DataTable().destroy();
	}

	if (errorList == 0)
		$("#itemDetails tbody tr")
				.each(
						function(i) {
							

							if (rowCount <= 3) {
								var itemCode1 = $("#codId" + 1).val();
								var itemCode2 = $("#codId" + 2).val();
								var itemCode3 = $("#codId" + 3).val();
						 		var itemCode4 = $("#codId" + 4).val();
								var itemCode5 = $("#codId" + 5).val();

							} else {
								var utp = i;
								utp = i * 6;
								var itemCode1 = $("#codId" + (utp + 1)).val();
								var itemCode2 = $("#codId" + (utp + 2)).val();
								var itemCode3 = $("#codId" + (utp + 3)).val();
								var itemCode4 = $("#codId" + (utp + 4)).val();
								var itemCode5 = $("#codId" + (utp + 5)).val();
								var level = i+1;

							}

							if (itemCode1 == "" || itemCode1 == undefined
									|| itemCode1 == "0") {
								errorList
										.push(getLocalMessage("wms.select.zone")
												+ " " + (i + 1));
							}

							if ((itemCode2 == "" || itemCode2 == "0") && itemCode2 != undefined) {
								errorList
										.push(getLocalMessage("wms.select.ward")
												+ " " + (i + 1));
							}
							
							if ((itemCode3 == "" || itemCode3 == "0") && itemCode3 != undefined) {
								errorList
								.push(getLocalMessage("wms.select.block")
										+ " " + (i + 1));
							} 
							if ((itemCode4 == "" || itemCode4 == "0") && itemCode4 != undefined) {
								errorList
								.push(getLocalMessage("wms.select.ward")
										+ " " + (i + 1));
							} 
							if ((itemCode5 == "" || itemCode5 == "0") && itemCode5 != undefined) {
								errorList
								.push(getLocalMessage("wms.select.zone")
										+ " " + (i + 1));
							} 
							
						});
	return errorList;
}
