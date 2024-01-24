$(document)
		.ready(function() {
			  $("#proposalActionDatatables").dataTable(
					    {
						"oLanguage" : {
						    "sSearch" : ""
						},
						"aLengthMenu" : [ [ 5, 10, 15, -1 ],
							[ 5, 10, 15, "All" ] ],
						"iDisplayLength" : 5,
						"bInfo" : true,
						"lengthChange" : true
					    });
			  $('.datepicker').datepicker({
					dateFormat : 'dd/mm/yy',
					changeMonth : true,
					changeYear : true,
					maxDate : 0,
					yearRange : "-100:-0"
				    });
			  
			

		});

function searchProposalData(obj) {

	var errorList = [];
	// validate the form
	var proposalNo = $('#proposalNo').val();
	var rowCount = $('#proposalActionDatatables >tbody >tr').length;
	var table = $('#proposalActionDatatables').DataTable();
	var url = "CouncilActionTaken.html?searchProposals";
	var requestData = "proposalNo=" + $('#proposalNo').val();
	var returnData =[];
	if(rowCount == 1){
		$('#errorDiv').hide();
		errorList.push(getLocalMessage("council.action.validation.ProposaLNo"));
		window.location.href = "CouncilActionTaken.html";
		return false;
	}else if ( proposalNo == 0  ||  proposalNo ==""  || proposalNo == null){
		errorList.push(getLocalMessage("council.action.validation.ProposaLNo"));
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);
		return false;
	}else{
		$('#errorDiv').hide();
		returnData= __doAjaxRequest(url, 'post', requestData,
				false, 'json');
	}
	if (returnData.length == 0) {
		errorList.push(getLocalMessage("council.member.validation.grid.nodatafound"));
		displayErrorsOnPage(errorList);
		return false;
	}
	table.rows().remove().draw();
	var result = [];

	var obj = returnData;

	let proNo = obj.proposalNo;
	let proDetails = obj.proposalDetails;
	let proPurpose = obj.purposeremark;
	let actionBT = obj.actionBT;
	let proposalId = obj.proposalId;
	if(proPurpose == null || proPurpose === undefined){
		proPurpose = "";		
	}
	
	result.push([ '<div class="text-center">' + (1) + '</div>',
			'<div class="text-center">' + proNo + '</div>',
			'<div class="text-center">' + proDetails + '</div>',
			'<div class="text-center">' + proPurpose + '</div>',

	
	
	'<div class="text-center">'
	+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="showGridOption(\''
    +proposalId
	+ '\',\'CouncilActionTaken.html\',\'VIEW\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
	+ '<button type="button" class="btn btn-danger btn-sm "  onclick="showGridOption(\''
    +proposalId
	+ '\',\'CouncilActionTaken.html\',\'EDIT\',\'E\')"  title="Edit"><i class="fa fa-pencil-square-o"></i></button>'
	+ '</div>' ]);
	
	table.rows.add(result);
	table.draw();
}
	

function showGridOption(proposalId, formUrl, actionParam, mode) {

		var divName = '.content';
		var requestData = {
		"mode" : mode,
		"id" : proposalId
		 };
	
		
		var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestData,
							'html', false);	
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);

	}
	

function addEntryData() {

		$("#errorDiv").hide();
		var errorList = [];

		errorList = validateDetails(errorList);
		var rowCount = $('#actionTakenTable >tbody >tr').length;
		if (errorList.length == 0) {
			
			addTableRow1('actionTakenTable');
			enableFields();
			 
		} else {
			$('#actionTakenTable').DataTable();
			displayErrorsOnPage(errorList);
		}
		
	}

function addTableRow1(tableId, isDataTable) {
	
	var id = "#" + tableId;
	// remove datatable specific properties
	if ((isDataTable == undefined || isDataTable) && $.fn.DataTable.isDataTable('' + id + '')) {
		$('' + id + '').DataTable().destroy();
	}
	$(".datepicker").datepicker("destroy");
	var content = $('' + id + ' tr').last().clone();
	$('' + id + ' tr').last().after(content);
	
	content.find("input:text").val('');
	content.find("input:hidden").val('');
	content.find("textarea").val('');
	content.find("select").val('');
	content.find("input:checkbox").removeAttr('checked');
	reOrderTableIdSequence(id);
	$('.datepicker').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate : 0,
		yearRange : "-100:-0"
	});
	if(isDataTable == undefined || isDataTable) {
		// adding datatable specific properties
		dataTableProperty(id);
	}
}	

function deleteEntry(obj, ids) {	

		var rowCount = $('#actionTakenTable >tbody >tr').length;
		let errorList = [];
		if (rowCount == 1) {
			errorList.push(getLocalMessage("council.validation.delete.entry"));
		}
		if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
			return false;
		}

		deleteTableRow('actionTakenTable', obj, ids);
	
		$('#actionTakenTable').DataTable().destroy();
		
	}
	

function validateDetails(errorList) {

	var patdate = [], patDept = [], patEmpl = [] , patactinTakenDet = [];

	$(".appendableClass")
			.each(
					function(i) {
						var patDate = $("#patDate" + i).val();
						var patDepId = $("#patDepId" + i).val();
						var patEmpId = $("#patEmpId" + i).val();
						var patDetails = $("#patDetails" + i).val();
						
						var rowCount = i + 1;

						if (patdate.includes(patDate) && patDate !='') {
							errorList
									.push(getLocalMessage("council.validation.duplicate.patDate")
											+ rowCount);
						} else {
							patdate.push(patDate);
						}
						if (patDept.includes(patDepId) ) {
							errorList
									.push(getLocalMessage("council.validation.duplicate.patDept")
											+ rowCount);
						} else {
							patDept.push(patDepId);
						}

						if (patEmpl.includes(patEmpId) && patEmpId != '0' ) {
							errorList
									.push(getLocalMessage("council.validation.duplicate.patEmployee")
											+ rowCount);
						} else {
							patEmpl.push(patEmpId);
						}
					
						if (patactinTakenDet.includes(patDetails)) {
							errorList
									.push(getLocalMessage("council.validation.duplicate.patActionDetails")
											+ rowCount);
						} else {
							patactinTakenDet.push(patDetails);
						}
						
						if (patDepId == "" || patDepId == null
								|| patDepId == 'undefined'
								|| patDepId == '0') {
							errorList
									.push(getLocalMessage("council.validation.patDepId")
											+ rowCount);
						}
					
						/*
						 * if (patDate == "" || patDate == null || patDate ==
						 * 'undefined' || patDate == '0') { errorList
						 * .push(getLocalMessage("council.validation.patdate") +
						 * rowCount); }
						 * 
						 * if (patEmpId == "" || patEmpId == null || patEmpId ==
						 * 'undefined' || patEmpId == '0') { errorList
						 * .push(getLocalMessage("council.validation.patEmpId") +
						 * rowCount); }
						 */
						if (patDetails == "" || patDetails == null
								|| patDetails == 'undefined' || patDetails == " "
								|| patDetails == '0') {
							errorList
									.push(getLocalMessage("council.validation.patActionDetails")
											+ rowCount);
						}


					});
	return errorList;
}

	
function saveActionForm(obj,saveMode) {

	var errorList = [];
	$('#saveMode').val(saveMode);
	validateDetails(errorList);
	
	if (errorList.length ==0 ) {
		return saveOrUpdateForm(obj, 'SAVED', 'CouncilActionTaken.html', 'saveform');
		
	} else {
		// display error MSG
		displayErrorsOnPage(errorList);
		
	}	
}
	

function backActionForm() {

	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'CouncilActionTaken.html');
	$("#postMethodForm").submit();
}


function enableFields(){

	$('#actionTakenTable tr:last').find("input,select,textarea,a").attr("disabled",false);
	
}

function searchForActiveEmp(element) {
	
    var errorList = [];
    
    var ActiveEmpId = $(element).attr('id');
    var index = ActiveEmpId.charAt(ActiveEmpId.length - 1);
    var requestData = {
	"empId" : $(element).val()
    };


    var ajaxResponse = doAjaxLoading(
	    'CouncilActionTaken.html?checkEmployeeActive',
	    requestData, 'json');

    if (ajaxResponse == 'N') {
	errorList
		.push(getLocalMessage('council.validation.patActiveEmp'));
    	$('#patEmpId' + index).val('').trigger('chosen:updated');
	displayErrorsOnPage(errorList);
	
    } else {
	$("#errorDiv").hide();
    }

}