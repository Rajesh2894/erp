$(document).ready(function() {
	$("#id_actAndRuleMaterForm").validate({
		onkeyup : function(element) {
			this.element(element);			
		},
		onfocusout : function(element) {
			this.element(element);			
		}
	});		

	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	});
});

function openAddActAndRuleMaster(formUrl, actionParam) {

	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function confirmToProceed(element) {
	var errorList = [];
	errorList = ValidateForm(errorList);

	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
	}
}

function ValidateForm(errorList) {
	var actNo = $("#actNo").val();
	var actName = $("#actName").val();
	var actYear = $("#actYear").val();
	var activeFromDate = $("#activeFromDate").val();
	var activeToDate = $("#activeToDate").val();
	var actTitle = $("#actTitle").val();
	var actDesc = $("#actDesc").val();
	
	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';

	if (actNo == "" || actNo == null)
		errorList.push(info + getLocalMessage("lgl.validation.actrule.no"));
	if (actName == "" || actName == null)
		errorList.push(info + getLocalMessage("lgl.validation.actrule.name"));
	if (actYear == "" || actYear == null)
		errorList.push(info + getLocalMessage("lgl.validation.actrule.year"));
	if (activeFromDate == "" || activeFromDate == null)
		errorList.push(info + getLocalMessage("lgl.validation.actrule.activefrmdate"));
	if (activeToDate == "" || activeToDate == null)
		errorList.push(info + getLocalMessage("lgl.validation.actrule.activetodate"));
	if (actTitle == "" || actTitle == null)
		errorList.push(info + getLocalMessage("lgl.validation.actrule.title"));
	if (actDesc == "" || actDesc == null)
		errorList.push(info + getLocalMessage("lgl.validation.actrule.desc"));
	
	return errorList;
}

function addEntryData(tableId) {	
	var errorList = [];
	errorList = validateJudgeEntryDetails();
	if (errorList.length == 0) {
		$("#errorDiv").hide();
		addTableRow(tableId);
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateJudgeEntryDetails() {
	var errorList = [];
	var j = 0;
	if ($.fn.DataTable.isDataTable('#judgemasterTbl')) {
		$('#judgemasterTbl').DataTable().destroy();
	}
	$("#id_actAndRuleTbl tbody tr").each(
					function(i) {
						var addNo = $("#addNo" + i).val();
						var addTitle = $("#addTitle" + i).val();
						var addYear = $("#addYear" + i).val();
						var addDate = $("#addDate" + i).val();
						var addDetails = $("#addDetails" + i).val();

						var rowCount = i + 1;

						var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';

						if (addNo == "" || addNo == null) {
							errorList.push(info
									+ getLocalMessage("lgl.validation.actrule.addendumno")
									+ rowCount);
						}

						if (addTitle == "" || addTitle == null) {
							errorList.push(info
									+ getLocalMessage("lgl.validation.actrule.addendumtitle")
									+ rowCount);
						}
						if (addYear == "" || addYear == null) {
							errorList
									.push(info
											+ getLocalMessage("lgl.validation.actrule.addendumyear")
											+ rowCount);
						}
						if (addDate == "" || addDate == null) {
							errorList
									.push(info
											+ getLocalMessage("lgl.validation.actrule.addendumdate")
											+ rowCount);
						}
						if (addDetails == "" || addDetails == null) {
							errorList.push(info
											+ getLocalMessage("lgl.validation.actrule.addendumdetails")
											+ rowCount);
						}		
					});
	return errorList;
}

function deleteEntry(tableId, obj, ids) {
	deleteTableRow(tableId, obj, ids);
}
