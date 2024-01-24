/* Vishwajeet Kumar */

var fileArray = [];
var removeIdArray = [];
$(document).ready(function() {
	/**
	 * This method is used to Search All Scheme
	 * schemeMasterList.jsp START METHOD
	 */
	
	$("#searchSchemeMaster").click(function() {	
		var errorList = [];
		var wmSchNameEng = $('#schNameEng').val().trim();
		var sourceCode = $('#sourceCode').val();
		var sourceName = $('#sourceName').val();
		var UADstatusForScheme = $('#UADstatusForScheme').val();
		var isDefaultStatusSchm = $('#isDefaultStatusSchm').val();
		var rabillView = getLocalMessage("works.management.view");
		var rabillEdit = getLocalMessage("works.management.edit");
		var rabillDelete = getLocalMessage("works.management.delete");
		/*
		 * var startDate = $('#wmSchStrDate').val(); 
		 * var schmCode = $('#wmSchCode').val();		
		 * if ((compareDate(startDate)) > compareDate(endDate)){
		 *  errorList.push(getLocalMessage("Scheme end date should be greater then scheme start date"));
		 * displayErrorsOnPage(errorList);
		 * return false; }
		 */
		if (wmSchNameEng != ''|| sourceCode != ''|| sourceName != '') {		
			var requestData = 'wmSchNameEng='+ wmSchNameEng+ '&sourceCode='+ sourceCode+ '&sourceName='+ sourceName;
			var table = $('#datatables').DataTable();
			table.rows().remove().draw();
			$(".warning-div").hide();
			var ajaxResponse = __doAjaxRequest(
					'WmsSchemeMaster.html?getSchemeMasterGridData','POST', requestData, false,'json');
			
			if (ajaxResponse.length == 0) {
				errorList.push(getLocalMessage("scheme.master.validation.nodatafound"));
				displayErrorsOnPage(errorList);
				return false;
			}

			var result = [];
			$.each(ajaxResponse,function(index) {
				var obj = ajaxResponse[index];
				if(UADstatusForScheme == 'YES' && isDefaultStatusSchm !='Y'){
					var schFund = null;
					if ($("#cpdMode").val() == 'L') {
						schFund = obj.schFundDesc;
					} else {
						schFund = obj.schFundName;
					}
					result.push([index + 1,obj.wmSchNameEng,schFund,'<td >'
						+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:10px;"  onclick="showGridOption(\''
						+ obj.wmSchId
						+ '\',\'V\')" title="' + rabillView + '"><i class="fa fa-eye"></i></button>' + '</td>' ]);				
				}else{
					var schFund = null;
					if ($("#cpdMode").val() == 'L') {
						schFund = obj.schFundDesc;
					} else {
						schFund = obj.schFundName;
					}
					result.push([index + 1,obj.wmSchNameEng,schFund,'<td >'
						+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 " style="margin-left:10px;"  onclick="showGridOption(\''
						+ obj.wmSchId
						+ '\',\'V\')" title="' + rabillView + '"><i class="fa fa-eye"></i></button>'
						+ '<button type="button" class="btn btn-success btn-sm margin-right-10 "  onclick="showGridOption(\''
						+ obj.wmSchId
						+ '\',\'E\')"  title="' + rabillEdit + '"><i class="fa fa-pencil-square-o"></i></button>'
						+ '<button type="button" class="btn btn-danger  btn-sm margin-right-10" onClick="showGridOption(\''
						+ obj.wmSchId
						+ '\',\'D\')"  title="' + rabillDelete + '"><i class="fa fa-trash "></i></button>'
						+ '</td>' ]);	
				}
			});
			table.rows.add(result);
			table.draw();
		} else {
			errorList.push(getLocalMessage('work.management.valid.select.any.field'));
			displayErrorsOnPage(errorList);
		}
	});

	
	
	$("#datatables").dataTable({
		"oLanguage" : {"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ],
			[ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true
	});

	$("#schemeProjectView").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ],
			[ 5, 10, 15, "All" ] ],
			"iDisplayLength" : 5,
			"bInfo" : true,
			"lengthChange" : true
	});

	
	$(function() {
		$('.custDate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true
		});
	});
	
	$(function() {
		$('.dateValidation').datepicker({
			dateFormat : 'dd/mm/yy',
			maxDate : new Date(),
			changeMonth : true,
			changeYear : true
		});
	});
	/* Date Validation autoformat start */

	$("#wmSchStrDate").bind("keyup change", function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});

	$("#wmSchEndDate").bind("keyup change", function(e) {
		if (e.keyCode != 8) {
			if ($(this).val().length == 2) {
				$(this).val($(this).val() + "/");
			} else if ($(this).val().length == 5) {
				$(this).val($(this).val() + "/");
			}
		}
	});
	/* Date Validation autoformat End */
	if ($("#saveMode").val() == "E") {
		$(".reset").hide();
		if ($("#sourceCode").val() != ''&& $("#sourceCode").val() != null) {
			var source = $("#sourceCode").find("option:selected").attr('code');
			if (source != 'U') {
				$("#wmSchNameEng").attr("readonly", true);
				$("#wmSchNameReg").attr("readonly", true);
				}
		}
	}
	if ($("#saveMode").val() == "V") {
		$(".reset").hide();
	}
	$('#wmsSchemeMasterForm').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});

	var cpdMode = $("#cpdMode").val();
	var errorList = [];
	if (cpdMode == "") {
		errorList.push(getLocalMessage("work.Def.valid.setSLI"));
		displayErrorsOnPage(errorList);
	}
});

function openAddSchemeMaster(formUrl, actionParam) {
	/*
	 * if (!actionParam) {
	 * 
	 * actionParam = "add"; }
	 */
	var errorList = [];

	var cpdMode = $("#cpdMode").val();
	if (cpdMode == "") {
		errorList.push(getLocalMessage("work.Def.valid.setSLI"));
		displayErrorsOnPage(errorList);
	} else {
		var divName = '.content-page';
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {},
				'html', divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
}

function saveData(element) {

	var errorList = [];
	/*
	 * if ($("#wmSchCode").val() == "") {
	 * errorList.push(getLocalMessage("scheme.master.validation.schemecode")); }
	 * else { if ($("#saveMode").val() == "A") { var requestSchemeData =
	 * 'wmSchCode=' + $("#wmSchCode").val(); var schemCodeValidator =
	 * doAjaxLoading('WmsSchemeMaster.html?' + 'checkDuplicateSchemeCode',
	 * requestSchemeData, 'html'); if (schemCodeValidator == '"Y"') { errorList
	 * .push(getLocalMessage("scheme.master.vldn.schemeCodeDuplicate")); } } }
	 */
	
	if(($("#sourceCode").val() == "" || $("#sourceCode").val() == null)){
		errorList.push(getLocalMessage("scheme.master.select.source.of.fund"));
	}
	if ($("#sourceCode").val() != '' && $("#sourceCode").val() != null) {
		var source = $("#sourceCode").find("option:selected").attr('code');
		if (source != 'U') {
			if ($("#sourceName").val() == '' || $("#sourceName").val() == null)
				errorList.push(getLocalMessage("project.master.vldn.scheme.name"));
		}

	}

	/*
	 * if ($("#wmSchNameEng").val().length < 50) errorList
	 * .push(getLocalMessage("scheme.master.validation.schemename.eng"));
	 * 
	 * if ($("#wmSchNameReg").val().length < 50) errorList
	 * .push(getLocalMessage("scheme.master.validation.schemename.reg"));
	 */

	/*
	 * if ($("#wmSchStrDate").val() == "") { errorList
	 * .push(getLocalMessage("scheme.master.validation.schemestartdate")); } var
	 * wmSchStrDate = $.trim($("#wmSchStrDate").val());
	 * 
	 * if (wmSchStrDate != null) { errorList = validatedate(errorList,
	 * 'wmSchStrDate'); // errorList =
	 * validationOfdate(errorList,'wmSchStrDate'); }
	 */
	/*
	 * if ((compareDate($("#wmSchStrDate").val())) >
	 * compareDate($("#wmSchEndDate") .val())) errorList
	 * .push(getLocalMessage("scheme.master.validation.scheme.datevalidation"));
	 */

	if (errorList.length == 0) {
		errorList = validateSchemeList(errorList);
	}

	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
	} else {
		$("#errorDiv").hide();

		/*
		 * var formName = findClosestElementId(element, 'form'); var theForm =
		 * '#' + formName; var requestData = __serializeForm(theForm); var
		 * response = __doAjaxRequest(
		 * 'WmsSchemeMaster.html?saveAndUpdateSchemeMaster', 'POST',
		 * requestData, false);
		 */
		return saveOrUpdateForm(element,
				getLocalMessage('scheme.master.creation.success'),
				'WmsSchemeMaster.html', 'saveform');

		// showConfirmBox();
	}
}

function validateSchemeList(errorList) {
	var total = 0;

	$('.appendableClass').each(function(i) {
		row = i + 1;
		var sponsoredBy = $("#schDSpon" + i).val();
		var sharingPercent = $("#schSharPer" + i).val();
		
		if (sponsoredBy != "") {
			if (sharingPercent == "")
				errorList.push(getLocalMessage("scheme.master.validation.sharepercent") + " " + row);
		}
		if (sharingPercent != "") {
			if (sponsoredBy == "") {
				errorList.push(getLocalMessage("scheme.master.enter.sponsored") + " " + row);
			}
		}
		if (sharingPercent != "")
			total = total + parseFloat(sharingPercent);
	});
	if (total != 100 && total != 0) {
		errorList.push(getLocalMessage("scheme.master.doesnot.greater"));
	}
	return errorList;
}

function resetScheme() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'WmsSchemeMaster.html');
	$("#postMethodForm").submit();
}

function showErr(errorList) {
	$(".warning-div").removeClass('hide');
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closePrefixErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);

	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

function showConfirmBox() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';

	message += '<h4 class=\"text-center text-blue-2 padding-12\"> '
			+ getLocalMessage('scheme.master.creation.success') + ' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceed()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);

	return false;
}

function showGridOption(wmSchId, action) {

	var actionData;
	var errorList = [];
	var divName = formDivName;
	var cpdMode = $("#cpdMode").val();
	var requestData = 'wmSchId=' + wmSchId;
	if (action == "E") {
		if (cpdMode == "") {
			errorList.push(getLocalMessage("work.Def.valid.setSLI"));
			displayErrorsOnPage(errorList);
			return false;
		}
		actionData = 'checkSchemeProjectAssociation';
		var ajaxResponse = doAjaxLoading('WmsSchemeMaster.html?' + actionData,
				requestData, 'html');
		if (ajaxResponse == 'false') {
			showErrormsgboxTitle(getLocalMessage("scheme.assocatde.with.project.not.editable"))
		} else {
			actionData = 'editSchemeMasterData';
			var ajaxResponse = doAjaxLoading('WmsSchemeMaster.html?'
					+ actionData, requestData, 'html');
			$('.content').removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();

		}
	}
	if (action == "D") {
		if (cpdMode == "") {
			errorList.push(getLocalMessage("work.Def.valid.setSLI"));
			displayErrorsOnPage(errorList);
		} else {
			actionData = 'deleteSchemeMaster';
			showConfirmBoxForDelete(wmSchId, actionData);
		}
	}
	if (action == "V") {
		if (cpdMode == "") {
			errorList.push(getLocalMessage("work.Def.valid.setSLI"));
			displayErrorsOnPage(errorList);
		} else {
			actionData = 'ViewSchemeMaster';
			var ajaxResponse = doAjaxLoading('WmsSchemeMaster.html?'
					+ actionData, requestData, 'html');
			$('.content').removeClass('ajaxloader');
			$(divName).html(ajaxResponse);

			prepareTags();
		}
	}
}

function showConfirmBoxForDelete(wmSchId, actionData) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'
			+ getLocalMessage('scheme.delete') + '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDelete(' + wmSchId + ')"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);

	return false;
}

function proceedForDelete(wmSchId) {
	$.fancybox.close();
	var requestData = 'wmSchId=' + wmSchId;
	var ajaxResponse = doAjaxLoading('WmsSchemeMaster.html?'
			+ 'deleteSchemeMaster', requestData, 'html');

	if (ajaxResponse == 'false') {
		showErrormsgboxTitle(getLocalMessage("scheme.associated.with.project.not.inactive"))
	} else {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'WmsSchemeMaster.html');
		$("#postMethodForm").submit();
	}
}

function proceed() {
	if ($("#saveMode").val() == "P") {
		backAddProjectMasterForm();
	} else {
		$("#postMethodForm").prop('action', 'WmsSchemeMaster.html');
		$("#postMethodForm").submit();
		$.fancybox.close();
	}
}

function backProjectMasterForm() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'WmsSchemeMaster.html');
	$("#postMethodForm").submit();
}

function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

function compareDate(date) {

	var parts = date.split("/");
	return new Date(parts[2], parts[1] - 1, parts[0]);
}

/* Funding Pattern 1+ Row Creation And deletion */

$("#schemeMstList").on("click", '.addSchemeMstList', function(e) {
	var count = $('#schemeMstList tr').length - 1;
	var errorList = [];
	errorList = validateSchemList(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
	} else {
		$("#errorDiv").addClass('hide');
		e.preventDefault();
		var clickedRow = $(this).parent().parent().index();
		var content = $('#schemeMstList tr').last().clone();
		$('#schemeMstList tr').last().after(content);
		content.find("input:hidden").attr("value", "");
		content.find("select").attr("selected", "selected").val('');
		content.find("input:text").val('');
		reOrderSchMaster();
	}
});

function validateSchemList(errorList) {
	$('.appendableClass').each(function(i) {
		var schSponser = $("#schDSpon" + i).val();
		var schSharePer = $("#schSharPer" + i).val();
		var schemeMast = i + 1;

		if (schSponser == null || schSponser == "") {
			errorList.push(getLocalMessage("scheme.master.enter.sponsored")+ " " + schemeMast);
		}
		if (schSharePer == "" || schSharePer == null) {
			errorList.push(getLocalMessage("scheme.master.enter.sharePercentage") + " " + schemeMast);
		}
	});
		return errorList;
	}

function checkDuplicateSponser(sponser, currentRow) {
	$("#errorDiv").hide();
	var errorList = [];
	if (errorList.length == 0) {
		$('.appendableClass').each(function(i) {
			if (currentRow != i && (sponser.value == $("#schDSpon" + i).val())) {
				$("#schDSpon" + currentRow).val("");
				errorList.push(getLocalMessage("scheme.duplicate.not.allowed"));
				$("#errorDiv").show();
				showErr(errorList);
				return false;
			}
		});
	} else {
			$("#schDSpon" + currentRow).val("");
			showErr(errorList);
			return false;
		}
}

function reOrderSchMaster() {
	$('.appendableClass').each(function(i) {
						// Ids
		$(this).find("input:hidden:eq(0)").attr("id",
				"schDetId" + (i));
		$(this).find("input:hidden:eq(1)").attr("id",
				"schActiveFlag" + (i)).val("A");
		$(this).find("select:eq(0)").attr("id",
				"schDSpon" + (i)).attr('onchange',
						'checkDuplicateSponser(this,' + i + ')');
		$(this).find("input:text:eq(0)").attr("id",
				"schSharPer" + (i));

		// names
		$(this).find("input:hidden:eq(0)").attr(
				"name",
				"schemeMasterDTO.mastDetailsDTO[" + (i)
				+ "].schDetId");
		$(this).find("input:hidden:eq(1)").attr(
				"name",
				"schemeMasterDTO.mastDetailsDTO[" + (i)
				+ "].schActiveFlag");
		$(this).find("select:eq(0)").attr(
				"name",
				"schemeMasterDTO.mastDetailsDTO[" + (i)
				+ "].schDSpon");
		$(this).find("input:text:eq(0)").attr(
				"name",
				"schemeMasterDTO.mastDetailsDTO[" + (i)
				+ "].schSharPer");
	});
}

$("#schemeMstList").on("click",'.deleteSchemeLink',
		function(e) {
	var errorList = [];
	var counter = -1;
	$('.appendableClass').each(function(i) {
		counter += 1;
	});
	var rowCount = counter;
	if (rowCount == 0) {

		$("#schDSpon0").val("");
		$("#schSharPer0").val("");
		var schemeId = $(this).parent().parent().find(
		'input[type=hidden]:first').attr('value');
		
		if (schemeId != '') {
			removeIdArray.push(schemeId);
		}
				/*
				 * $('#removeChildIds').val(removeIdArray); reOrderSchMaster();
				 */
	}

	if (rowCount != 0) {
		$(this).parent().parent().remove();
		rowCount--;
		var schemeId = $(this).parent().parent().find(
		'input[type=hidden]:first').attr('value');
		if (schemeId != '') {
			removeIdArray.push(schemeId);
		}
		/* $('#removeChildIds').val(removeIdArray); */

	}
	// removeIdArray.sort(function(a, b){return a-b});
	$('#removeChildIds').val(removeIdArray);
	reOrderSchMaster();
});

$("#attachDocs").on("click",'#deleteFile',function(e) {
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
			fileArray.push(fileId);
		}
		$('#removeFileById').val(fileArray);
	}
});

/*
 * View Page List of Project Master Data in Grid view associated with Scheme
 * Master scheme Id
 */

function viewProject(projId) {

	var divName = '.content-page';
	var requestData = 'projId=' + projId;
	var response = __doAjaxRequest('WmsSchemeMaster.html?getAllProjectView',
			'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(response);
	prepareTags();
}

function backAddProjectMasterForm() {
	$.fancybox.close();
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('WmsProjectMaster.html' + '?'
			+ 'AddProjectMaster', {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

	prepareTags();

}

function validationOfdate(errorList, dateElementId) {
	$('.error-div').hide();
	var dateValue = $("#" + dateElementId).val();

	if (dateValue != null && dateValue != "") {
		var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;

		if (dateValue.match(dateformat)) {
			var opera1 = dateValue.split('/');
			lopera1 = opera1.length;
			if (lopera1 > 1) {
				var pdate = dateValue.split('/');
			}
			var dd = parseInt(pdate[0]);
			var mm = parseInt(pdate[1]);
			var yy = parseInt(pdate[2]);

			var ListofDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
			if (mm == 1 || mm > 2) {
				if (dd > ListofDays[mm - 1]) {
					errorList.push('Invalid date format !');
				}
			}
			if (mm == 2) {
				var lyear = false;
				if ((!(yy % 4) && yy % 100) || !(yy % 400)) {
					lyear = true;
				}
				if ((lyear == false) && (dd >= 29)) {
					errorList.push('Invalid date format !');
				}
				if ((lyear == true) && (dd > 29)) {
					errorList.push('Invalid date format !');
				}
			}
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var sDate = new Date(dateValue.replace(pattern, '$3-$2-$1'));
			if (sDate > new Date()) {
				errorList.push("Start date Must be Current Date");
			}
			if (yy < 1902 || yy > (new Date()).getFullYear()) {
				errorList.push("Invalid value for year: " + yy
						+ " - must be between 1902 and "
						+ (new Date()).getFullYear());
			}
		} else {
			errorList.push('Invalid date format !');
		}
	}
	return errorList;
}

function getSchemeDetails(obj, mode) {

	var sourceId = $("#sourceCode").val();
	// var sourceName = $("#sourceCode").find("option:selected").attr('code');
	$("#wmSchNameEng").val('');
	$("#wmSchNameEng").attr("readonly", false);
	$("#wmSchNameReg").val('');
	$("#wmSchNameReg").attr("readonly", false);
	if ($("#sourceCode").val() == '') {
		$('#sourceName').html('');
		$('#sourceName').append(
				$("<option></option>").attr("value", "").text(
						getLocalMessage('selectdropdown')));
		$('#sourceName').trigger("chosen:updated");
	} else {
		
		var requestData = {
			"mode" : mode,
			"sourceId" : sourceId
		}
		$('#sourceName').html('');
		$('#sourceName').append(
				$("<option></option>").attr("value", "").text(
						getLocalMessage('selectdropdown')));
		var response = __doAjaxRequest(
				'WmsSchemeMaster.html?getSchemeFundSource', 'post',
				requestData, false, 'html');
		var prePopulate = JSON.parse(response);

		$.each(prePopulate, function(index, value) {
			var source = value.lookUpCode + " -- " + value.lookUpDesc;
			$('#sourceName').append(
					$("<option></option>").attr("value", value.lookUpId).attr(
							"code", value.descLangSecond).text(source));
		});
		$('#sourceName').trigger("chosen:updated");
	}

}

function getSchemeDetailNames(obj) {
	if ($("#sourceName").val() == '') {
		$("#wmSchNameEng").val('');
		$("#wmSchNameEng").attr("readonly", false);
		$("#wmSchNameReg").val('');
		$("#wmSchNameReg").attr("readonly", false);
	} else {
		var sourceName1 = $("#sourceName").find("option:selected").text();
		var sourceName = $("#sourceName").find("option:selected").attr('code');
		if (sourceName1 != '' && sourceName1 != null) {
			var source = sourceName1.split('--');
			$("#wmSchNameEng").val(source[1]);
			$("#wmSchNameEng").attr("readonly", true);
		}
		if (sourceName != '' && sourceName != null) {
			$("#wmSchNameReg").val(sourceName);
			$("#wmSchNameReg").attr("readonly", true);
		}
	}
}
function resetSchemeMaster(resetBtn){
	$('#sourceCode').val('').trigger('chosen:updated');
	$('#sourceName').val('').trigger('chosen:updated');
	resetForm(resetBtn);
}
 function testFunction(element){
	var err = [];
	var sourceCode = $('#sourceCode').val();
	var sourceName = $('#sourceName').val();
	if(sourceCode == ""){
		err.push(getLocalMessage('scheme.master.select.source.of.fund'));
		displayErrorsOnPage(err);
		return false;
	}
};

