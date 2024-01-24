$("#tbAcBudgetEstimationPreparation").validate({

	onkeyup : function(element) {
		this.element(element);
		console.log('onkeyup fired');
	},
	onfocusout : function(element) {
		this.element(element);
		console.log('onfocusout fired');
	}
});

var dsgid = '';
var dsgid = '';
var desigFlag = '';

$(function() {
	
	desigFlag = $("#desinationId").val();

	$("#grid")
			.jqGrid(
					{
						url : "AccountBudgetEstimationPreparation.html?getGridData",
						datatype : "json",
						mtype : "POST",
						colNames : [
								'',
								getLocalMessage('budget.estimationpreparation.master.departmenttype'),
								getLocalMessage('account.budgetestimationpreparation.budgethead'),
								getLocalMessage('account.budgetestimationpreparation.approvedbudget'),
								getLocalMessage('account.budgetestimationpreparation.estimationfornextyear(indianrupees)'),
								getLocalMessage('bill.action') ],
						colModel : [
								{
									name : "bugestId",
									width : 30,
									sortable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									},
									hidden : true
								},
								{
									name : "departmentDesc",
									width : 30,
									sortable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									}
								},
								{
									name : "prBudgetCode",
									width : 55,
									sortable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									}
								},
								{
									name : "apprBugStandComDup",
									width : 25,
									sortable : true,
									classes : 'text-right',
									editable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									}
								},
								{
									name : "estimateForNextyearDup",
									width : 25,
									sortable : true,
									classes : 'text-right',
									editable : true,
									sortable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									}
								}, {
									name : 'bugestId',
									index : 'bugestId',
									width : 20,
									align : 'center !important',
									formatter : addLink,
									search : false
								} ],
						pager : "#pagered",
						rowNum : 30,
						rowList : [ 5, 10, 20, 30 ],
						sortname : "bugestId",
						sortorder : "desc",
						height : 'auto',
						viewrecords : true,
						gridview : true,
						loadonce : true,
						jsonReader : {
							root : "rows",
							page : "page",
							total : "total",
							records : "records",
							repeatitems : false,
						},
						autoencode : true,
						caption : getLocalMessage('account.budgetestimationpreparation.accountbudgetestimationpreparationlist'),

						formatter : function(v) {
							// uses "c" for currency formatter and "n" for
							// numbers
							return Globalize.format(Number(v), "c");
						},
						unformat : function(v) {
							return Globalize.parseFloat(v);
						}

					});
	jQuery("#grid").jqGrid('navGrid', '#pagered', {
		edit : false,
		add : false,
		del : false,
		search : true,
		refresh : false
	});
	$("#pagered_left").css("width", "");
});

$(document).ready(function() {
	//alert("Sample Ready function");
	
	/*desigFlag = 'Y';
	if (desigFlag == 'Y'){
		//$('.bulkEditSearch').show();
		$( '<button type="button" class="btn btn-warning bulkEditSearch"><i class="fa fa-pencil"></i>EDIT BULK<spring:message code="" text="EDIT BULK" />' ).insertAfter( ".createData" );
		
		
	}
	else{
		//$('.bulkEditSearch').hide();
		$("bulkEditSearch").remove();
		
	}*/
	

});

function addLink(cellvalue, options, rowdata) {
	// debugger;
	//desigFlag = 'Y';
	if (desigFlag == 'Y')
		return "<a class='btn btn-blue-3 btn-sm viewBugEstPrepareMasterClass' title='View'value='"
				+ rowdata.bugestId
				+ "' bugestId='"
				+ rowdata.bugestId
				+ "' ><i class='fa fa-building-o'></i></a> "
				+ "<a class='btn btn-warning btn-sm editBugEstPrepareMasterClass' title='Edit'value='"
				+ rowdata.bugestId
				+ "' bugestId='"
				+ rowdata.bugestId
				+ "' ><i class='fa fa-pencil'></i></a> ";
	
	else
		return "<a class='btn btn-blue-3 btn-sm viewBugEstPrepareMasterClass' title='View'value='"
				+ rowdata.bugestId
				+ "' bugestId='"
				+ rowdata.bugestId
				+ "' ><i class='fa fa-building-o'></i></a> ";
	
}

function returnisdeletedUrl(cellValue, options, rowdata, action) {

	if (rowdata.isdeleted == '0') {
		return "<a href='#'  class='fa fa-check-circle fa-2x green '   value='"
				+ rowdata.isdeleted
				+ "'  alt='Designation is Active' title='Designation is Active'></a>";
	} else {
		return "<a href='#'  class='fa fa-times-circle fa-2x red ' value='"
				+ rowdata.isdeleted
				+ "' alt='Designation is  INActive' title='Designation is InActive'></a>";
	}

}
/*
 * function returnEditUrl(cellValue, options, rowdata, action) { bugestId =
 * rowdata.bugestId; return "<a href='#' return false;
 * class='editBugEstPrepareMasterClass' value='" + bugestId + "'><img
 * src='css/images/edit.png' width='20px' alt='Edit Master' title='Edit Master' /></a>"; }
 * 
 * function returnViewUrl(cellValue, options, rowdata, action) {
 * 
 * return "<a href='#' return false; class='viewBugEstPrepareMasterClass'
 * value='" + bugestId + "'><img src='css/images/grid/view-icon.png'
 * width='20px' alt='View Master' title='View Master' /></a>"; }
 */

function returnDeleteUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='deleteDesignationClass fa fa-trash-o fa-2x'  alt='View  Master' title='Delete  Master'></a>";
}

function closeOutErrBox() {
	$('.error-div').hide();
}

$(function() {

	$(document).on('click', '.bulkEditSearch', function() {

		var $link = $(this);
		/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
		var bugestId = 1;
		var url = "AccountBudgetEstimationPreparation.html?bulk_edit_search";
		var requestData = "bugestId=" + bugestId + "&MODE_DATA=" + "BULKEDIT"; // unused
		// data
		// in
		// controller
		var returnData = __doAjaxRequest(url, 'post', requestData, false);

		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		return false;
	});

	$(document).on('click', '.createData', function() {

		var $link = $(this);
		/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
		var bugestId = 1;
		var url = "AccountBudgetEstimationPreparation.html?form";
		var requestData = "bugestId=" + bugestId + "&MODE_DATA=" + "ADD"; // unused
		// data
		// in
		// controller
		var returnData = __doAjaxRequest(url, 'post', requestData, false);

		var divName = ".content";
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		return false;
	});

	$(document).on('click', '.editBugEstPrepareMasterClass', function() {

		var $link = $(this);
		/* var bugestId = $link.closest('tr').find('td:eq(0)').text(); */
		// debugger;
		var bugestId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountBudgetEstimationPreparation.html?formforupdate";
		var requestData = "bugestId=" + bugestId + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		// console.log("returnData array : "+JSON.stringify(returnData));
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		// alert("editBugEstPrepareMasterClass $('#test').val() :
		// "+"*"+$('#test').val()+"*");
		if ($('#test').val() === 'EDIT') {
			// var type=$("#cpdBugtypeId option:selected").attr("code");
			var type = $('#cpdBugtypeIdHidden').val();
			if (type == "REV") {
				$("#prProjectionid").removeClass('hide');
				$('#faYearid').prop('disabled', 'disabled');
			} else {
				$("#prExpenditureid").removeClass('hide');
				$('#faYearid').prop('disabled', 'disabled');
			}
		}
		return false;
	});

	$(document).on('click', '.viewBugEstPrepareMasterClass', function() {
		var $link = $(this);
		var bugestId = $link.closest('tr').find('td:eq(0)').text();
		var url = "AccountBudgetEstimationPreparation.html?update";
		var requestData = "bugestId=" + bugestId + "&MODE_DATA=" + "VIEW";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type="text"], textarea').attr('readonly', 'readonly');
		if ($('#test').val() === 'VIEW') {
			// var type=$("#cpdBugtypeId option:selected").attr("code");
			var type = $('#cpdBugtypeIdHidden').val();
			if (type == "REV") {
				$("#prProjectionid").removeClass('hide');
				$('#faYearid').prop('disabled', 'disabled');
			} else {
				$("#prExpenditureid").removeClass('hide');
				$('#faYearid').prop('disabled', 'disabled');
			}
		}
		return false;
	});

});

$(function() {

	$(document).on('click', '.deleteDesignationClass', function() {
		var $link = $(this);
		var dsgid = $link.closest('tr').find('td:eq(0)').text();
		$("#grid").jqGrid('setGridParam', {
			datatype : 'json'
		}).trigger('reloadGrid');
		showConfirmBoxEmployee(dsgid);

	});

});

$(document).ready(function() {

	// alert("estimation preparation js $('#index').val() :
	// *"+$('#index').val()+"*");

	var id = $('#index').val();
	// $('#secondaryId').val($('#prRevBudgetCode0').val());
	// $('#secondaryId').val($('#prRevBudgetCode${id}').val());

	$('body').on('focus', ".hasNumber", function() {
		$(".hasNumber").keyup(function(event) {
			this.value = this.value.replace(/[^0-3]/g, '');
			$(this).attr('maxlength', '1');
		});
	});

	$('body').on('focus', ".hasMyNumber", function() {
		$(".hasMyNumber").keyup(function(event) {
			this.value = this.value.replace(/[^0-9]/g, '');
			$(this).attr('maxlength', '13');
		});
	});

	if ($('#test').val() === 'EDIT') {
		// var type=$("#cpdBugtypeId option:selected").attr("code");
		var type = $('#cpdBugtypeIdHidden').val();
		if (type == "REV") {

			$("#prRevBudgetCode0").data('rule-required', true);
			$("#estimateForNextyearEdit0").data('rule-required', true);

			$("#prProjectionid").removeClass('hide');
			$('#faYearid').prop('disabled', 'disabled');
		} else {

			$("#prExpBudgetCode0").data('rule-required', true);
			$("#estimateForNextyearEditExp0").data('rule-required', true);

			$("#prExpenditureid").removeClass('hide');
			$('#faYearid').prop('disabled', 'disabled');
		}
	}

	if ($('#test').val() === 'VIEW') {
		// var type=$("#cpdBugtypeId option:selected").attr("code");
		var type = $('#cpdBugtypeIdHidden').val();
		if (type == "REV") {
			$("#prProjectionid").removeClass('hide');
		} else {
			$("#prExpenditureid").removeClass('hide');
		}
	}

});

function displayAddView(obj) {
	var formName = findClosestElementId(obj, 'form');

	var theForm = '#' + formName;

	var divName = childDivName;

	var requestData = __serializeForm(theForm);

	var formAction = $(theForm).attr('action');

	var url = formAction + '?create';

	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	$(divName).html(returnData);

	prepareDateTag();
}

$(".warning-div ul")
		.each(
				function() {
					var lines = $(this).html().split("<br>");
					$(this)
							.html(
									'<li>'
											+ lines
													.join("</li><li><i class='fa fa-exclamation-circle'></i>&nbsp;")
											+ '</li>');
				});
$('html,body').animate({
	scrollTop : 0
}, 'slow');

function searchBudgetEstimationPreparationDataBulkEditSearch() {

	$('.error-div').hide();
	var errorList = [];

	// debugger;

	var faYearid = $("#faYearid").val();
	var cpdBugtypeId = $("#cpdBugtypeId").val();
	var dpDeptid = $("#dpDeptid").val();
	var fieldId = $("#fieldId").val();
	var cpdBugsubtypeId = $("#cpdBugsubtypeId").val();

	console.log("faYearid : " + faYearid);
	console.log("cpdBugtypeId : " + cpdBugtypeId);
	console.log("dpDeptid : " + dpDeptid);
	console.log("fieldId : " + fieldId);
	console.log("cpdBugsubtypeId : " + cpdBugsubtypeId);

	if (faYearid == "" || faYearid == "0") {
		errorList.push('Kindly select financial Year');
	}

	if (cpdBugtypeId === undefined || cpdBugtypeId == "") {
		cpdBugtypeId = "";
		errorList.push('Kindly select budget type');
	}

	if (dpDeptid === undefined || dpDeptid == "") {
		dpDeptid = "";
		errorList.push('Kindly select department');
	}

	if (fieldId === undefined || fieldId == "") {
		fieldId = "";
		errorList.push('Kindly select field Id');
	}

	if (cpdBugsubtypeId === undefined) {
		cpdBugsubtypeId = "";
	}

	if (errorList.length > 0) {

		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';

		});
		errorMsg += '</ul>';

		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

	}
	// change by akhilesh if (errorList.length == 0)
	if (errorList.length == 0) {

		var url = "AccountBudgetEstimationPreparation.html?getjqGridBulkEditsearch";
		var requestData = {
			"faYearid" : faYearid,
			"cpdBugtypeId" : cpdBugtypeId,
			"dpDeptid" : dpDeptid,
			"fieldId" : fieldId,
			"cpdBugsubtypeId" : cpdBugsubtypeId
		};

		// var requestData = "faYearid=" + faYearid + "&cpdBugtypeId=" +
		// cpdBugtypeId + "&dpDeptid=" + dpDeptid
		// + "&fieldId=" + fieldId + "&cpdBugsubtypeId=" + cpdBugsubtypeId;

		var result = __doAjaxRequest(url, 'post', requestData, false);

		// var result = __doAjaxRequest(url, 'POST', requestData, false,
		// 'json');
		// console.log("result array : "+JSON.stringify(result));

		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(result);
		// $('select').attr("disabled", true);
		return false;

	}
};

function searchBudgetEstimationPreparationData() {

	$('.error-div').hide();
	var errorList = [];

	var faYearid = $("#faYearid").val();
	var cpdBugtypeId = $("#cpdBugtypeId").val();
	var dpDeptid = $("#dpDeptid").val();
	var prBudgetCodeid = $("#prBudgetCodeid").val();
	// cpdBugtypeId = $("#cpdBugtypeId").val();
	var fundId = $("#fundId").val();
	var functionId = $("#functionId").val();

	if (faYearid == "" || faYearid == "0") {
		errorList.push('Kindly select financial Year');
	}

	if (fundId === undefined) {
		fundId = "";
	}

	if (functionId === undefined) {
		functionId = "";
	}

	if (errorList.length > 0) {

		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';

		});
		errorMsg += '</ul>';

		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

	}

	// change by akhil if (errorList.length == 0)
	if (errorList.length == 0) {

		var url = "AccountBudgetEstimationPreparation.html?getjqGridsearch";
		var requestData = {
			"faYearid" : faYearid,
			"cpdBugtypeId" : cpdBugtypeId,
			"dpDeptid" : dpDeptid,
			"prBudgetCodeid" : prBudgetCodeid,
			"fundId" : fundId,
			"functionId" : functionId
		};

		var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');

		if (result != null && result != "") {
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		} else {
			var errorList = [];

			errorList.push(getLocalMessage("account.norecord.criteria"));

			if (errorList.length > 0) {

				var errorMsg = '<ul>';
				$
						.each(
								errorList,
								function(index) {
									errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
											+ errorList[index] + '</li>';

								});
				errorMsg += '</ul>';

				$('#errorId').html(errorMsg);
				$('#errorDivId').show();
				$('html,body').animate({
					scrollTop : 0
				}, 'slow');

			}
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		}
	}
};

function copyContent(obj) {

	var amount = 0;

	var stt = 0;
	var parentCode = 0;

	stt = parseInt($('#orginalEstamtO').val());
	parentCode = parseInt($('#transferAmount').val());
	$('#newOrgRevAmount').val(parentCode + stt);
}

function setHiddenField(e) {
	$('#hiddenFinYear').val($('#faYearid').val());
}

var objTemp;

function saveLeveledData(obj) {
	var errorList = [];
	objTemp = obj;
	// debugger;
	/*
	 * var levelData = $('#bugestId').val(); levelData++; var sid = 'faYearid' +
	 * levelData; var selID = '#'+sid; if($('#count').val()=="0"){ count=0;
	 * }else{ count=parseInt($('#count').val()); } //var id=$('#index').val();
	 * var assign = count; var faYearid= $("#faYearid").val(); var cpdBugtypeId=
	 * $("#cpdBugtypeId").val(); var cpdBugsubtypeId =
	 * $("#cpdBugsubtypeId").val(); var dpDeptid = $("#dpDeptid").val();
	 */

	// var stringId = $('#ulId li').last().find('select:eq(0)').attr('id');
	// count = stringId.charAt(stringId.length-1);
	incrementvalue = ++count;

	// alert("$('#test').val() : "+ "*"+$('#test').val()+"*");

	if ($('#test').val() === 'EDIT') {
		var budgetType = $('#cpdBugtypeIdHidden').val();
	} else {
		var budgetType = $("#cpdBugtypeId option:selected").attr("code");
	}
	;
	if (budgetType === "REV") {

		/*
		 * if ($("#dpDeptid").val() == '') { errorList.push("Please Select
		 * Department Type"); }
		 * 
		 * if ($("#fieldIdO").val() == '') { errorList.push("Please Select Field
		 * Type"); }
		 * 
		 * if ($("#functionIdO").val() == '') { errorList.push("Please Select
		 * Function Type"); }
		 */

		/*
		 * var estimateForNextyearEdit= $('#estimateForNextyearEdit0').val();
		 * 
		 * if(estimateForNextyearEdit == '' || estimateForNextyearEdit == 0){
		 * 
		 * errorList.push("Please Enter EstimateForNextyear Amount"); }
		 */

		var j = $("#indexdata").val();
		for (n = 0; n < j; n++) {

			if ($('#prRevBudgetCode' + n).val() == $('#prRevBudgetCode' + j)
					.val()) {
				errorList
						.push("The Combination Budget head already exists, Please select again!");
			}
		}

		/*
		 * for(k=0; k<j+1;k++){ //var paheadId= $('#pacHeadId'+k).val(); var
		 * saheadId= $('#prRevBudgetCode'+k).val(); var estimateForNextyear=
		 * $('#estimateForNextyear'+k).val();
		 * 
		 * if(paheadId == '') { errorList.push("Please select Primary head
		 * code"); } if(saheadId == '') { errorList.push("Please select Budget
		 * heads"); } if(estimateForNextyear == ''){ errorList.push("Please
		 * Enter estimateForNextyear Amount"); } }
		 */
	}

	if ($('#test').val() === 'EDIT') {
		var budgetType = $('#cpdBugtypeIdHidden').val();
	} else {
		var budgetType = $("#cpdBugtypeId option:selected").attr("code");
	}
	;
	if (budgetType === "EXP") {

		/*
		 * if ($("#dpDeptidExp").val() == '') { errorList.push("Please Select
		 * Department Type"); }
		 * 
		 * if ($("#fieldIdExp").val() == '') { errorList.push("Please Select
		 * Field Type"); }
		 * 
		 * if ($("#functionIdExp").val() == '') { errorList.push("Please Select
		 * Function Type"); }
		 */

		/*
		 * var estimateForNextyearEditExp=
		 * $('#estimateForNextyearEditExp0').val();
		 * 
		 * if(estimateForNextyearEditExp == '' || estimateForNextyearEditExp ==
		 * 0){
		 * 
		 * errorList.push("Please Enter EstimateForNextyear Amount"); }
		 */

		var j = $("#indexdata").val();
		for (n = 0; n < j; n++) {
			if ($('#prExpBudgetCode' + n).val() == $('#prExpBudgetCode' + j)
					.val()) {
				errorList
						.push("The Combination Budget head already exists, Please select again!");
			}
		}

		/*
		 * for(m=0; m<j+1;m++){ //var paheadId= $('#pacHeadIdExp'+m).val(); var
		 * prExpBudgetCode= $('#prExpBudgetCode'+m).val(); var
		 * estimateForNextyear= $('#estimateForNextyearExp'+m).val();
		 * 
		 * if(paheadId == '') { errorList.push("Please select Primary head
		 * code"); } if(prExpBudgetCode == '') { errorList.push("Please select
		 * Budget heads"); } if(estimateForNextyear == ''){
		 * errorList.push("Please Enter estimateForNextyear Amount"); } }
		 */
	}

	/*
	 * if(faYearid == '') { errorList.push("Please select financial year"); }
	 * 
	 * if(cpdBugtypeId == '0') { errorList.push("Please select Budget Type"); }
	 * if(cpdBugsubtypeId == '') { errorList.push("Please select Budget Sub
	 * Type"); } if(dpDeptid == '') { errorList.push("Please select
	 * Department"); }
	 */

	/*
	 * if(errorList.length>0){
	 * 
	 * var errorMsg = '<ul>'; $.each(errorList, function(index){ errorMsg +='<li><i
	 * class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
	 * 
	 * }); errorMsg +='</ul>';
	 * 
	 * $('#errorId').html(errorMsg); $('#errorDivId').show();
	 * $('html,body').animate({ scrollTop: 0 }, 'slow'); } else{
	 */
	/*
	 * return saveOrUpdateForm(obj, 'Saved Successfully',
	 * 'AccountBudgetEstimationPreparation.html', 'create');
	 */

	showConfirmBoxSave();

	/*
	 * var formName = findClosestElementId(obj, 'form'); var theForm =
	 * '#'+formName; var requestData = __serializeForm(theForm); var url =
	 * $(theForm).attr('action');
	 * 
	 * var response= __doAjaxRequestValidationAccor(obj,url+'?create', 'post',
	 * requestData, false, 'html'); if(response != false){
	 * $('.content').html(response); }
	 */

	// var response= __doAjaxRequestForSave(url+"?create", 'post', requestData,
	// false,'', obj);
	// $('.content').html(response);
	/* } */
}

function showConfirmBoxSave() {
	 

	var saveorAproveMsg = "save";
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var saveMsg = getLocalMessage("account.btn.save.msg");
	var cls = getLocalMessage("account.btn.save.yes");
	var no = getLocalMessage("account.btn.save.no");

	message += '<h4 class=\"text-center text-blue-2\">  ' + saveMsg + '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>  '
			+ '<input type=\'button\' value=\''
			+ cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="saveDataAndShowSuccessMsg()"/>   '
			+ '<input type=\'button\' value=\''
			+ no
			+ '\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '
			+ ' onclick="closeConfirmBoxForm()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function saveDataAndShowSuccessMsg() {
	
	var errorList = [];
	var formName = findClosestElementId(objTemp, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var url = $(theForm).attr('action');

	// var response= __doAjaxRequestValidationAccor(objTemp,url+'?create',
	// 'post', requestData, false, 'html');
	var response = __doAjaxRequestValidationAccor(objTemp, url
			+ '?create', 'post', requestData, false, 'html');
	var msg =$(response).find('#hasError');
	var res=msg.val();
	if (response != false) {
		if(res !="true"){
		showConfirmBox();
	}
   else {
		 $(".widget-content").html(response);
	     $(".widget-content").show();
	     closeConfirmBoxForm();
	}}

}
function showConfirmBox(){
	
	var formMode_Id = $("#MODE_DATA").val();	
	if(formMode_Id=="create"){
		var successMsg = getLocalMessage('account.bank.record.rubmitted.successfully');
	}else if(formMode_Id=="EDIT"){
		var successMsg = getLocalMessage('account.bank.record.updated.successfully');
	}
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = getLocalMessage('account.proceed.btn');
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
	 
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}
function proceed () {
	window.location.href='AccountBudgetEstimationPreparation.html';
}
function loadBudgetEstimationPreparationData(obj) {

	$('.error-div').hide();
	var errorList = [];

	$('#hiddenFinYear').val($('#faYearid').val());
	// var cpdBugsubtypeId = $("#cpdBugsubtypeId").val("");
	// var lastYearCount = $('#lastYearCount').val("");

	$("#prRevBudgetCode0").val("");
	$("#prExpBudgetCode0").val("");

	var lastYearCountDupOne0 = $('#lastYearCountDupOne0').val("");
	var lastYearCountDupTwo0 = $('#lastYearCountDupTwo0').val("");
	var lastYearCountDupThree0 = $('#lastYearCountDupThree0').val("");
	var orginalEstamt0 = $('#orginalEstamt0').val("");

	var lastYearCountExpDupOne = $('#lastYearCountExpDupOne0').val("");
	var lastYearCountExpDupTwo0 = $('#lastYearCountExpDupTwo0').val("");
	var lastYearCountExpDupThree0 = $('#lastYearCountExpDupThree0').val("");
	var orginalEstamtExp0 = $('#orginalEstamtExp0').val("");

	var faYearid = $('#faYearid').val();
	var cpdBugtypeId = $('#cpdBugtypeId').val();

	if (faYearid == '') {
		// errorList.push('Please Select Financial Year');
		// var cpdBugsubtypeId = $('#cpdBugsubtypeId').val("");
		var cpdBugtypeId = $('#cpdBugtypeId').val(0);
	}

	if (cpdBugtypeId == '0') {
		// errorList.push('Please Select Budget Type');
		var cpdBugsubtypeId = $('#cpdBugsubtypeId').val("");
	}

	if (errorList.length > 0) {

		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';

		});
		errorMsg += '</ul>';

		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

	}

	if (errorList.length == 0) {

		var divName = ".content";

		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AccountBudgetEstimationPreparation.html?getjqGridload";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

		var budgetType = $('#cpdBugtypeId').val();

		$('#faYearid').prop('disabled', 'disabled');

		var budgetType = $("#cpdBugtypeId option:selected").attr("code");
		if (budgetType == "REV") {

			$("#prRevBudgetCode0").data('rule-required', true);
			$("#estimateForNextyear0").data('rule-required', true);

			$("#prProjectionid").removeClass("hide");
			$("#prExpenditureid").addClass("hide");
		} else if (budgetType == "EXP") {

			$("#prExpBudgetCode0").data('rule-required', true);
			$("#estimateForNextyearExp0").data('rule-required', true);

			$("#prProjectionid").addClass("hide");
			$("#prExpenditureid").removeClass("hide");
		}

	}

};


function saveLeveledDataBulkEdit(obj) {
	// debugger;
	var errorList = [];
	objTemp = obj;
	incrementvalue = ++count;

	if ($('#test').val() === 'EDIT') {
		var budgetType = $('#cpdBugtypeIdHidden').val();
	} else {
		var budgetType = $("#cpdBugtypeId option:selected").attr("code");
	}
	;
	if (budgetType === "REV") {
		var j = $("#indexdata").val();
		for (n = 0; n < j; n++) {
			if ($('#prRevBudgetCode' + n).val() == $('#prRevBudgetCode' + j)
					.val()) {
				errorList
						.push("The Combination Budget head already exists, Please select again!");
			}
		}
	}

	if ($('#test').val() === 'EDIT') {
		var budgetType = $('#cpdBugtypeIdHidden').val();
	} else {
		var budgetType = $("#cpdBugtypeId option:selected").attr("code");
	}
	;
	if (budgetType === "EXP") {
		var j = $("#indexdata").val();
		for (n = 0; n < j; n++) {
			if ($('#prExpBudgetCode' + n).val() == $('#prExpBudgetCode' + j)
					.val()) {
				errorList
						.push("The Combination Budget head already exists, Please select again!");
			}
		}
	}
	showConfirmBoxSaveBulkEdit();
}

function showConfirmBoxSaveBulkEdit() {
	// debugger;

	var saveorAproveMsg = "save";
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var saveMsg = getLocalMessage("account.btn.save.msg");
	var cls = getLocalMessage("account.btn.save.yes");
	var no = getLocalMessage("account.btn.save.no");

	message += '<h4 class=\"text-center text-blue-2\">  ' + saveMsg + '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>  '
			+ '<input type=\'button\' value=\''
			+ cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="saveDataAndShowSuccessMsgBulkEdit()"/>   '
			+ '<input type=\'button\' value=\''
			+ no
			+ '\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '
			+ ' onclick="closeConfirmBoxForm()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function saveDataAndShowSuccessMsgBulkEdit() {
	 debugger;
	var formName = findClosestElementId(objTemp, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var url = $(theForm).attr('action');

	// var response= __doAjaxRequestValidationAccor(objTemp,url+'?create',
	// 'post', requestData, false, 'html'); createForBulkEdit
	var response = __doAjaxRequestValidationAccor(objTemp, url
			+ '?create', 'post', requestData, false, 'html');
	if (response != false) {
		showConfirmBox();
	} else {
		closeConfirmBoxForm();
	}

}

function copyContentExp(obj) {

	var amount = 0;

	// amount += parseInt($("#transferAmount").val());
	// $("#newOrgExpAmountO").val(amount);

	var stt1 = 0;
	var parentCode1 = 0;

	stt1 = parseInt($('#orginalEstamtExp').val());
	parentCode1 = parseInt($('#transferAmountO').val());
	$('#newOrgExpAmountO').val(parentCode1 + stt1);

}

/*
 * function getOrgBalExpAmount(obj) {
 * 
 * //var orginalEstamtExp = $("#orginalEstamtExp").val("");
 * 
 * if ($('#count').val() == "") { count = 1; } else { count =
 * parseInt($('#count').val()); } var assign = count;
 * 
 * $('.error-div').hide(); var errorList = [];
 * 
 * if (errorList.length == 0) {
 * 
 * 
 * var formName = findClosestElementId(obj, 'form');
 * 
 * var theForm = '#' + formName;
 * 
 * var requestData = __serializeForm(theForm);
 * 
 * var url = "AccountBudgetEstimationPreparation.html?getOrgBalExpGridload";
 * 
 * var returnData = __doAjaxRequest(url, 'post', requestData, false);
 * $.each(returnData, function(index , value) {
 * 
 * $('#orginalEstamtExp').val(returnData[0]);
 * $('#prBalanceAmtAS').val(returnData[1]);
 * 
 * }); }
 * 
 * if (errorList.length == 0) {
 * 
 * var formName = findClosestElementId(obj, 'form');
 * 
 * var theForm = '#' + formName;
 * 
 * var requestData = __serializeForm(theForm);
 * 
 * var url =
 * "AccountBudgetEstimationPreparation.html?getaddsupExpPrimarykeyIdDetails";
 * 
 * var returnData = __doAjaxRequest(url, 'post', requestData, false);
 * $.each(returnData, function(key, value) {
 * $('#prExpenditureidExp').val(value);
 * 
 * }); } };
 */

function clearAmount(obj) {

	var orginalEstamt = $("#orginalEstamtO").val("");
	var prProjectedRev = $('#prProjectedRev').val("");
	var transferAmount = $('#transferAmount').val("");
	var newOrgRevAmount = $('#newOrgRevAmount').val("");
	var prRevBudgetCodeO = $('#prRevBudgetCodeO').val("");
}

function clearAmountExp(obj) {

	var orginalEstamt = $("#orginalEstamtExp").val("");
	var prProjectedRev = $('#prBalanceAmtAS').val("");
	var transferAmount = $('#transferAmountO').val("");
	var newOrgRevAmount = $('#newOrgExpAmountO').val("");
	var prRevBudgetCodeO = $('#prExpBudgetCode').val("");
}

function enteredAmountDynamicallyGeneratedAll(obj) {
	// debugger;
	$('.error-div').hide();
	var errorList = [];

	var idRev = $('#index').val();
	var id = $(obj).attr('id');
	var estimateForNextyearId;
	var arr;
	if ($('#test').val() != 'EDIT') {

		if (id.includes('estimateForNextyear')) {
			arr = id.split('estimateForNextyear');
			estimateForNextyearId = "#estimateForNextyear";
		} else {
			arr = id.split('expectedCurrentYear');
			// estimateForNextyearId="#expectedCurrentYear";
		}

	} else {
		// arr=id.split('estimateForNextyearEdit');
		// estimateForNextyearId="#estimateForNextyearEdit";

		if (id.includes('estimateForNextyearEdit')) {
			arr = id.split('estimateForNextyearEdit');
			estimateForNextyearId = "#estimateForNextyearEdit";
		} else {
			arr = id.split('expectedCurrentYear');
			// estimateForNextyearId="#expectedCurrentYear";
		}

	}
	var indx = arr[1];
	// alert("$('#test').val() "+$('#test').val());
	/*if ($('#test').val() != 'EDIT') {
		var orginalEstamt = $("#orginalEstamt" + indx).val();
		if (orginalEstamt == null || orginalEstamt == ''
				|| orginalEstamt == undefined) {
			errorList.push("Current year budget is required");
			$('#estimateForNextyear' + indx).val("");
		}
	}*/

	var lastYearCountDupOneNew = 0;
	var lastYearCountDupTwoNew = 0;
	var lastYearCountDupThreeNew = 0;
	var originalEstimateNew = 0;
	var lastYearCountDupOne = parseFloat($('#lastYearCountDupOne' + indx).val());
	var lastYearCountDupTwo = parseFloat($('#lastYearCountDupTwo' + indx).val());
	var lastYearCountDupThree = parseFloat($('#lastYearCountDupThree' + indx)
			.val());
	var originalEstimate = parseFloat($('#orginalEstamt' + indx).val());
	var countNumber = 0;

	/* below code added by akhilesh */
	var actualsCurrentYearNew = 0;
	var expectedCurrentYearNew = 0;
	var actualsCurrentYear = parseFloat($("#actualsCurrentYear" + indx).val());
	var expectedCurrentYear = parseFloat($("#expectedCurrentYear" + indx).val());

	if ((actualsCurrentYear == undefined || actualsCurrentYear == null
			|| actualsCurrentYear == '' || isNaN(actualsCurrentYear))) {
		actualsCurrentYearNew = 0;
	} else {
		actualsCurrentYearNew = actualsCurrentYear;
	}

	if ((expectedCurrentYear == undefined || expectedCurrentYear == null
			|| expectedCurrentYear == '' || isNaN(expectedCurrentYear))) {
		expectedCurrentYearNew = 0;
	} else {
		expectedCurrentYearNew = expectedCurrentYear;
	}

	var revisedEstamtTotal = actualsCurrentYearNew + expectedCurrentYearNew;
	if ((revisedEstamtTotal == undefined || revisedEstamtTotal == null
			|| revisedEstamtTotal == '' || isNaN(revisedEstamtTotal))) {
		revisedEstamtTotal = 0;
		$("#revisedEstamt" + indx).val('');
	} else {

		var stt = 0;
		stt = parseFloat(revisedEstamtTotal);
		if ((stt != undefined && !isNaN(stt))) {
			var num = (stt);
			var result = num.toFixed(2);

			// $("#revisedEstamt"+indx).val("123.00");
			// $("#apprBugStandComExp"+indx).val("123.50");
			// $("#revisedEstamt"+indx).val(result);
			// $("#revisedEstamtHC").val(result);
			$("#revisedEstamt" + indx).val(result);

		}
	}

	if ((lastYearCountDupOne == undefined || lastYearCountDupOne == null
			|| lastYearCountDupOne == '' || isNaN(lastYearCountDupOne))) {
		lastYearCountDupOneNew = 0;
	} else {
		lastYearCountDupOneNew = lastYearCountDupOne;
		countNumber++;
	}
	if ((lastYearCountDupTwo == undefined || lastYearCountDupTwo == null
			|| lastYearCountDupTwo == '' || isNaN(lastYearCountDupTwo))) {
		lastYearCountDupTwoNew = 0;
	} else {
		lastYearCountDupTwoNew = lastYearCountDupTwo;
		countNumber++;
	}
	if ((lastYearCountDupThree == undefined || lastYearCountDupThree == null
			|| lastYearCountDupThree == '' || isNaN(lastYearCountDupThree))) {
		lastYearCountDupThreeNew = 0;
	} else {
		lastYearCountDupThreeNew = lastYearCountDupThree;
		countNumber++;
	}
	if ((originalEstimate == undefined || originalEstimate == null
			|| originalEstimate == '' || isNaN(originalEstimate))) {
		originalEstimateNew = 0;
	} else {
		originalEstimateNew = originalEstimate;
	}

	var budgetAvgAmt = ((lastYearCountDupOneNew + lastYearCountDupTwoNew + lastYearCountDupThreeNew))
			/ countNumber;
	if ((budgetAvgAmt == undefined || budgetAvgAmt == null
			|| budgetAvgAmt == '' || isNaN(budgetAvgAmt))) {
		budgetAvgAmt = 0;
	}
	var budgetRevPercent = $("#BudgetRevPercent").val();
	var budgetRevPercentString = "";
	var percentageBudgetAmt = 0;
	if (budgetRevPercent != undefined && budgetRevPercent != null
			&& budgetRevPercent != "") {
		percentageBudgetAmt = (budgetAvgAmt * (parseInt(budgetRevPercent))) / 100;
		budgetRevPercentString = budgetRevPercent + "% of actual";
	} else {
		percentageBudgetAmt = budgetAvgAmt;
	}
	// var estimateForNextyear=$("#estimateForNextyear"+indx).val();
	var estimateForNextyear = 0;
	estimateForNextyear = parseFloat($(estimateForNextyearId + indx).val());
	if ((estimateForNextyear != undefined && !isNaN(estimateForNextyear))) {
		var estimateOverForNextyear = parseFloat(originalEstimateNew
				+ percentageBudgetAmt);
		estimateForNextyear = parseFloat(estimateForNextyear);
	}

	if (errorList.length > 0) {
		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errorMsg += '</ul>';
		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
	}
	if (errorList.length == 0) {

		/*
		 * if(estimateForNextyear > estimateOverForNextyear){
		 * errorList.push("Budget estimate should not exceed more than
		 * "+budgetRevPercentString+" previous 3 years estimated budget."); }
		 */
		if (errorList.length > 0) {
			var errorMsg = '<ul>';
			$
					.each(
							errorList,
							function(index) {
								errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
										+ errorList[index] + '</li>';
							});
			errorMsg += '</ul>';
			$('#errorId').html(errorMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
		}

		var stt = 0;
		stt = parseFloat($("#estimateForNextyear" + indx).val());

		if ((stt != undefined && !isNaN(stt))) {
			var num = (stt);
			var result = num.toFixed(2);
			$("#apprBugStandCom" + indx).val(result);
			$("#finalizedBugGenBody" + indx).val(result);
		} else {
			$("#apprBugStandCom" + indx).val('');
			$("#finalizedBugGenBody" + indx).val('');
		}
	}
}

function enteredAmountDynamicallyGeneratedAllExp(obj) {

	// akhil
	//debugger;
	$('.error-div').hide();
	var errorList = [];

	var idRev = $('#index').val();
	var id = $(obj).attr('id');

	// alert("idRev "+idRev+" id "+id);
	// idRev 0 id expectedCurrentYearO0
	// alert("id : "+id);

	var estimateForNextyearid;
	var arr;
	// alert("$('#test').val() "+$('#test').val());
	if ($('#test').val() != 'EDIT') {
		if (id.includes('estimateForNextyearExp')) {
			arr = id.split('estimateForNextyearExp');
			estimateForNextyearid = "#estimateForNextyearExp";
		} else {
			arr = id.split('expectedCurrentYearO');
			// estimateForNextyearid="#expectedCurrentYearO";
		}
	} else {
		// arr=id.split('estimateForNextyearEditExp');
		// estimateForNextyearid="#estimateForNextyearEditExp";

		if (id.includes('estimateForNextyearEditExp')) {
			arr = id.split('estimateForNextyearEditExp');
			estimateForNextyearid = "#estimateForNextyearEditExp";
		} else {
			arr = id.split('expectedCurrentYearO');
			// estimateForNextyearid="#expectedCurrentYearO";
		}
	}
	var indx = arr[1];

	// alert("indx : "+indx);
    
	/*
	if ($('#test').val() != 'EDIT') {
		var orginalEstamtExp = $("#orginalEstamtExp" + indx).val();
		if (orginalEstamtExp == null || orginalEstamtExp == ''
				|| orginalEstamtExp == undefined) {
			errorList.push("Current year budget is required");
			$('#estimateForNextyearExp' + indx).val("");
		}
	}
	*/

	var lastYearCountDupOneNew = 0;
	var lastYearCountDupTwoNew = 0;
	var lastYearCountDupThreeNew = 0;
	var originalEstimateNew = 0;
	var lastYearCountDupOne = parseFloat($('#lastYearCountExpDupOne' + indx)
			.val());
	var lastYearCountDupTwo = parseFloat($('#lastYearCountExpDupTwo' + indx)
			.val());
	var lastYearCountDupThree = parseFloat($('#lastYearCountExpDupThree' + indx)
			.val());
	var originalEstimate = parseFloat($('#orginalEstamtExp' + indx).val());
	var countNumber = 0;

	/* below code added by akhilesh */
	var actualsCurrentYearONew = 0;
	var expectedCurrentYearONew = 0;
	var actualsCurrentYearO = parseFloat($("#actualsCurrentYearO" + indx).val());
	var expectedCurrentYearO = parseFloat($("#expectedCurrentYearO" + indx)
			.val());

	if ((actualsCurrentYearO == undefined || actualsCurrentYearO == null
			|| actualsCurrentYearO == '' || isNaN(actualsCurrentYearO))) {
		actualsCurrentYearONew = 0;
	} else {
		actualsCurrentYearONew = actualsCurrentYearO;
	}

	if ((expectedCurrentYearO == undefined || expectedCurrentYearO == null
			|| expectedCurrentYearO == '' || isNaN(expectedCurrentYearO))) {
		expectedCurrentYearONew = 0;
	} else {
		expectedCurrentYearONew = expectedCurrentYearO;
	}

	var revisedEstamtTotal = actualsCurrentYearONew + expectedCurrentYearONew;
	if ((revisedEstamtTotal == undefined || revisedEstamtTotal == null
			|| revisedEstamtTotal == '' || isNaN(revisedEstamtTotal))) {
		revisedEstamtTotal = 0;
		$("#revisedEstamtExp" + indx).val('');
	} else {

		var stt = 0;
		stt = parseFloat(revisedEstamtTotal);
		if ((stt != undefined && !isNaN(stt))) {
			var num = (stt);
			var result = num.toFixed(2);

			// $("#revisedEstamt"+indx).val("123.00");
			// $("#apprBugStandComExp"+indx).val("123.50");
			// $("#revisedEstamt"+indx).val(result);
			// $("#revisedEstamtHC").val(result);
			$("#revisedEstamtExp" + indx).val(result);

		} else {

		}
	}

	if ((lastYearCountDupOne == undefined || lastYearCountDupOne == null
			|| lastYearCountDupOne == '' || isNaN(lastYearCountDupOne))) {
		lastYearCountDupOneNew = 0;
	} else {
		lastYearCountDupOneNew = lastYearCountDupOne;
		countNumber++;
	}
	if ((lastYearCountDupTwo == undefined || lastYearCountDupTwo == null
			|| lastYearCountDupTwo == '' || isNaN(lastYearCountDupTwo))) {
		lastYearCountDupTwoNew = 0;
	} else {
		lastYearCountDupTwoNew = lastYearCountDupTwo;
		countNumber++;
	}
	if ((lastYearCountDupThree == undefined || lastYearCountDupThree == null
			|| lastYearCountDupThree == '' || isNaN(lastYearCountDupThree))) {
		lastYearCountDupThreeNew = 0;
	} else {
		lastYearCountDupThreeNew = lastYearCountDupThree;
		countNumber++;
	}
	if ((originalEstimate == undefined || originalEstimate == null
			|| originalEstimate == '' || isNaN(originalEstimate))) {
		originalEstimateNew = 0;
	} else {
		originalEstimateNew = originalEstimate;
	}

	var budgetAvgAmt = ((lastYearCountDupOneNew + lastYearCountDupTwoNew + lastYearCountDupThreeNew))
			/ countNumber;
	if ((budgetAvgAmt == undefined || budgetAvgAmt == null
			|| budgetAvgAmt == '' || isNaN(budgetAvgAmt))) {
		budgetAvgAmt = 0;
	}
	var budgetExpPercent = $("#BudgetExpPercent").val();
	var budgetExpPercentString = "";
	var percentageBudgetAmt = 0;
	if (budgetExpPercent != undefined && budgetExpPercent != null
			&& budgetExpPercent != "") {
		percentageBudgetAmt = (budgetAvgAmt * (parseInt(budgetExpPercent))) / 100;
		budgetExpPercentString = budgetExpPercent + "% of actual";
	} else {
		percentageBudgetAmt = budgetAvgAmt;
	}
	// var estimateForNextyear=$("#estimateForNextyear"+indx).val();
	var estimateForNextyear = 0;
	estimateForNextyear = parseFloat($(estimateForNextyearid + indx).val());
	if ((estimateForNextyear != undefined && !isNaN(estimateForNextyear))) {
		var estimateOverForNextyear = parseFloat(originalEstimateNew
				+ percentageBudgetAmt);
		estimateForNextyear = parseFloat(estimateForNextyear);
	}

	if (errorList.length > 0) {
		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errorMsg += '</ul>';
		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
	}
	// debugger;
	if (errorList.length == 0) {

		/*
		 * if(estimateForNextyear > estimateOverForNextyear){
		 * errorList.push("Budget estimate should not exceed more than
		 * "+budgetExpPercentString+" previous 3 years estimated budget.");
		 * //var msg = "Budget estimate should not exceed more than 10% of
		 * actual previous 3 years estimated budget.";
		 * //showErrormsgboxTitle(msg); }
		 */
		if (errorList.length > 0) {
			var errorMsg = '<ul>';
			$
					.each(
							errorList,
							function(index) {
								errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
										+ errorList[index] + '</li>';
							});
			errorMsg += '</ul>';
			$('#errorId').html(errorMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
		}

		var stt = 0;
		stt = parseFloat($("#estimateForNextyearExp" + indx).val());
		if ((stt != undefined && !isNaN(stt))) {
			var num = (stt);
			var result = num.toFixed(2);
			// alert($("#apprBugStandComExp"+indx));
			$("#apprBugStandComExp" + indx).val(result);
			$("#finalizedBugGenBodyExp" + indx).val(result);
		} else {
			$("#apprBugStandComExp" + indx).val('');
			$("#finalizedBugGenBodyExp" + indx).val('');
		}
	}

}

function clearBudgetSubType(obj) {

	var idRev = $('#index').val();
	var prRevBudgetCode = $("#prRevBudgetCode" + idRev).val("");
	$("#prRevBudgetCode" + idRev).val('').trigger('chosen:updated');
	// var prExpBudgetCode = $("#prExpBudgetCode"+idRev).val("");
	var estimateForNextyear = $("#estimateForNextyear" + idRev).val("");
	var apprBugStandCom = $("#apprBugStandCom" + idRev).val("");
	var finalizedBugGenBody = $("#finalizedBugGenBody" + idRev).val("");
	// var estimateForNextyearExp = $("#estimateForNextyearExp"+idRev).val("");
	var orginalEstamt = $("#orginalEstamt" + idRev).val("");
	var revisedEstamt = $("#revisedEstamt" + idRev).val("");
	var lastYearCountDupOne = $("#lastYearCountDupOne" + idRev).val("");
	var lastYearCountDupTwo = $("#lastYearCountDupTwo" + idRev).val("");
	var lastYearCountDupThree = $("#lastYearCountDupThree" + idRev).val("");
}

function clearExpBudgetSubType(obj) {
	var idExp = $('#index').val();
	var prExpBudgetCode = $("#prExpBudgetCode" + idExp).val("");
	$("#prExpBudgetCode" + idExp).val('').trigger('chosen:updated');
	var estimateForNextyearExp = $("#estimateForNextyearExp" + idExp).val("");
	var apprBugStandComExp = $("#apprBugStandComExp" + idExp).val("");
	var finalizedBugGenBodyExp = $("#finalizedBugGenBodyExp" + idExp).val("");
	var orginalEstamtExp = $("#orginalEstamtExp" + idExp).val("");
	var revisedEstamt = $("#revisedEstamt" + idExp).val("");
	var lastYearCountExpDupOne = $("#lastYearCountExpDupOne" + idExp).val("");
	var lastYearCountExpDupTwo = $("#lastYearCountExpDupTwo" + idExp).val("");
	var lastYearCountExpDupThree = $("#lastYearCountExpDupThree" + idExp).val(
			"");
}

function RemoveTranferAmount(obj) {

	var cpdBugsubtypeId = $("#cpdBugsubtypeId").val("");
}

/*
 * function setSecondaryCodeFinance(count) { $('.error-div').hide(); var
 * errorList = [];
 * 
 * if (errorList.length == 0) {
 * 
 * if ($("#dpDeptid").val() == "") { msgBox("Please Select Department Type");
 * var cpdBugsubtypeIdRev1 = $("#pacHeadId"+count).val(""); return false; }
 * 
 * if ($("#fieldIdO").val() == "") { msgBox("Please Select Field Type"); var
 * cpdBugsubtypeIdRev1 = $("#pacHeadId"+count).val(""); return false; }
 * 
 * if ($("#functionIdO").val() == "") { msgBox("Please Select Function Type");
 * var cpdBugsubtypeIdRev1 = $("#pacHeadId"+count).val(""); return false; }
 * 
 * if ($("#cpdBugsubtypeId").val() == "") { msgBox("Please Select Budget Sub
 * Type"); var cpdBugsubtypeIdRev1 = $("#pacHeadId"+count).val(""); return
 * false; }
 * 
 * if ($("#cpdBugtypeId").val() == "") { msgBox("Please Select Budget Type");
 * var cpdBugsubtypeIdRev1 = $("#pacHeadId"+count).val(""); return false; }
 * 
 * var orginalEstamt = $("#orginalEstamt"+count).val("");
 * 
 * var faYearId = $("#faYearid").val(); var
 * primaryCode=$('#pacHeadId'+count).val();
 * 
 * $('#prRevBudgetCode'+count).find('option:gt(0)').remove();
 * 
 * if (primaryCode >= 0) { var postdata = {"faYearid": faYearId,"pacHeadId" :
 * primaryCode };
 * 
 * var json =
 * __doAjaxRequest('AccountBudgetEstimationPreparation.html?sacHeadItemsList',
 * 'POST', postdata, false, 'json'); var optionsAsString='';
 * 
 * $.each( json, function( key, value ) { optionsAsString += "<option value='"
 * +key+"'>" + value + "</option>"; });
 * $('#prRevBudgetCode'+count).append(optionsAsString ); } } }
 */

/*
 * function setSecondaryCodeFinanceExp(count) {
 * 
 * $('.error-div').hide(); var errorList = [];
 * 
 * if (errorList.length == 0) {
 * 
 * if ($("#dpDeptidExp").val() == "") { msgBox("Please Select Department Type");
 * var cpdBugsubtypeIdRev1 = $("#pacHeadIdExp"+count).val(""); return false; }
 * 
 * if ($("#fieldIdExp").val() == "") { msgBox("Please Select Field Type"); var
 * cpdBugsubtypeIdRev1 = $("#pacHeadIdExp"+count).val(""); return false; }
 * 
 * if ($("#functionIdExp").val() == "") { msgBox("Please Select Function Type");
 * var cpdBugsubtypeIdRev1 = $("#pacHeadIdExp"+count).val(""); return false; }
 * 
 * if ($("#cpdBugsubtypeId").val() == "") { msgBox("Please Select Budget Sub
 * Type"); var cpdBugsubtypeIdRev1 = $("#pacHeadIdExp"+count).val(""); return
 * false; }
 * 
 * if ($("#cpdBugtypeId").val() == "") { msgBox("Please Select Budget Type");
 * var cpdBugsubtypeIdRev1 = $("#pacHeadIdExp"+count).val(""); return false; }
 * 
 * var orginalEstamtExp = $("#orginalEstamtExp"+count).val("");
 * 
 * var faYearId = $("#faYearid").val(); var
 * primaryCode=$('#pacHeadIdExp'+count).val();
 * 
 * $('#prExpBudgetCode'+count).find('option:gt(0)').remove();
 * 
 * if (primaryCode >= 0) { var postdata = {"faYearid": faYearId,"pacHeadIdExp" :
 * primaryCode };
 * 
 * var json =
 * __doAjaxRequest('AccountBudgetEstimationPreparation.html?sacHeadItemsExpList',
 * 'POST', postdata, false, 'json'); var optionsAsString='';
 * 
 * $.each( json, function( key, value ) { optionsAsString += "<option value='"
 * +key+"'>" + value + "</option>"; });
 * $('#prExpBudgetCode'+count).append(optionsAsString ); } } }
 */

function getOrgBalAmount(cnt) {
	// debugger;
	$('.error-div').hide();
	var errorList = [];

	$("#lastYearCountDupOne" + cnt).val("");
	$("#lastYearCountDupTwo" + cnt).val("");
	$("#lastYearCountDupThree" + cnt).val("");
	$("#orginalEstamt" + cnt).val("");
	$("#revisedEstamt" + cnt).val("");
	$("#estimateForNextyear" + cnt).val("");
	$("#apprBugStandCom" + cnt).val("");
	$("#finalizedBugGenBody" + cnt).val("");

	$("#estimateForNextyearEdit" + cnt).val("");
	$("#apprBugStandComEdit" + cnt).val("");
	$("#finalizedBugGenBodyEdit" + cnt).val("");

	var theForm = '#tbAcBudgetEstimationPreparation';

	var dpDeptid = $('#dpDeptid').val();
	var cpdBugtypeId = $('#cpdBugtypeId').val();
	var cpdBugsubtypeId = $('#cpdBugsubtypeId').val();

	if (dpDeptid == '') {
		errorList.push('Please Select Department');
		var cpdBugsubtypeIdRev = $("#prRevBudgetCode" + cnt).val("");
		$("#prRevBudgetCode" + cnt).val('').trigger('chosen:updated');
	}

	/*
	 * if (cpdBugtypeId == '0') { errorList.push('Please Select Budget Type');
	 * var cpdBugsubtypeIdRev = $("#prRevBudgetCode"+cnt).val("");
	 * $("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated'); } if
	 * (cpdBugsubtypeId == '') { errorList.push('Please Select Budget Sub
	 * Type'); var cpdBugsubtypeIdRev = $("#prRevBudgetCode"+cnt).val("");
	 * $("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated'); }
	 */

	var id = $("#indexdata").val();

	if (id == "" || id == undefined) {
		id = 0;
	}
	var prRevBudgetCode = $('#prRevBudgetCode' + id).val();

	for (m = 0; m <= id; m++) {
		if ($('#prRevBudgetCode' + m).val() == "") {

			errorList.push("Please select Account Head First!");
			var pacHeadId = $("#prRevBudgetCode" + cnt).val("");
			$("#prRevBudgetCode" + cnt).val('').trigger('chosen:updated');
		}
	}

	var Revid = $("#indexdata").val();
	if (prRevBudgetCode != "") {
		var dec;
		if (Revid == "" || Revid == undefined) {
			Revid = 0;
		}
		for (m = 0; m <= Revid; m++) {
			for (dec = 0; dec <= Revid; dec++) {
				if (m != dec) {
					if (($('#prRevBudgetCode' + m).val() == $(
							'#prRevBudgetCode' + dec).val())) {
						errorList
								.push("The Combination Budget head already exists!");
						var pacHeadId = $("#prRevBudgetCode" + cnt).val("");
						$("#prRevBudgetCode" + cnt).val('').trigger(
								'chosen:updated');
						var lastYearCountDupOne = $(
								"#lastYearCountDupOne" + cnt).val("");
						var lastYearCountDupTwo = $(
								"#lastYearCountDupTwo" + cnt).val("");
						var lastYearCountDupThree = $(
								"#lastYearCountDupThree" + cnt).val("");
						var orginalEstamt = $("#orginalEstamt" + cnt).val("");
						var revisedEstamt = $("#revisedEstamt" + cnt).val("");
						var estimateForNextyear = $(
								"#estimateForNextyear" + cnt).val("");
						var apprBugStandCom = $("#apprBugStandCom" + cnt).val(
								"");
						var finalizedBugGenBody = $(
								"#finalizedBugGenBody" + cnt).val("");
						// var openbalAmt = $("#openbalAmt"+cnt).val("");
						// var flagFlzd =
						// $("#flagFlzd"+cnt).attr('checked',false);
					}
				}
			}
		}
	}

	if (errorList.length > 0) {

		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';

		});
		errorMsg += '</ul>';

		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

	}

	var requestData = __serializeForm(theForm);

	if (errorList.length == 0) {

		var url = "AccountBudgetEstimationPreparation.html?getOrgBalRevDuplicateGridloadData&cnt="
				+ cnt;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var errorList = [];
		if (returnData) {
			errorList
					.push("Budget estimation combination budget head is already exist!");
			var pacHeadId = $("#prRevBudgetCode" + cnt).val("");
			$("#prRevBudgetCode" + cnt).val('').trigger('chosen:updated');
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

			$
					.each(
							errorList,
							function(index) {
								var errorMsg = '<ul>';
								$
										.each(
												errorList,
												function(index) {
													errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
															+ errorList[index]
															+ '</li>';

												});
								errorMsg += '</ul>';
								$('#errorId').html(errorMsg);
								$('#errorDivId').show();
								$('html,body').animate({
									scrollTop : 0
								}, 'slow');
							});
			return false;
		}
	}

	if (errorList.length == 0) {

		var url = "AccountBudgetEstimationPreparation.html?getOrgBalGridload&cnt="
				+ cnt;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData, function(key, value) {
			// var id = $('#index').val();
			// $('#prProjectionidRev'+ cnt).val(value);
			var prRevBudgetCode = $('#prRevBudgetCode' + cnt).val();
			if (prRevBudgetCode != "") {
				if (key == 'OEA') {
					$("#orginalEstamt" + cnt).val(value);
				}
			}
		});
	}

	if (errorList.length == 0) {

		var url = "AccountBudgetEstimationPreparation.html?getOrgAmountByLastYearCount&cnt="
				+ cnt;

		var returnData1 = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData1, function(key, value) {
			// var id1 = $('#index').val();
			// $('#prProjectionidRev'+ id1).val(key);
			var prRevBudgetCode = $('#prRevBudgetCode' + cnt).val();
			if (prRevBudgetCode != "") {
				if (key == 'OEA2') {
					$("#lastYearCountDupOne" + cnt).val(value);
				}
				if (key == 'OEA1') {
					$("#lastYearCountDupTwo" + cnt).val(value);
				}
				if (key == 'OEA0') {
					$("#lastYearCountDupThree" + cnt).val(value);
				}
			}
		});
	}
	// debugger;
	if (errorList.length == 0) {

		var url = "AccountBudgetEstimationPreparation.html?getActualofOrgAmountRev&cont="
				+ cnt;
		// debugger;
		var returnData1 = __doAjaxRequest(url, 'post', requestData, false);
		$("#actualsCurrentYear" + cnt).val(returnData1);

	}

};

function getOrgBalAmountExp(cont) {
	debugger;
	if ($('#count').val() == "") {
		count = 1;
	} else {
		count = parseInt($('#count').val());
	}
	var assign = count;

	$('.error-div').hide();
	var errorList = [];

	$("#lastYearCountExpDupOne" + cont).val("");
	$("#lastYearCountExpDupTwo" + cont).val("");
	$("#lastYearCountExpDupThree" + cont).val("");
	$("#orginalEstamtExp" + cont).val("");
	$("#revisedEstamt" + cont).val("");
	$("#estimateForNextyearExp" + cont).val("");
	$("#apprBugStandComExp" + cont).val("");
	$("#finalizedBugGenBodyExp" + cont).val("");

	$("#estimateForNextyearEditExp" + cont).val("");
	$("#apprBugStandComEditExp" + cont).val("");
	$("#finalizedBugGenBodyEditExp" + cont).val("");

	var dpDeptid = $('#dpDeptid').val();
	var cpdBugtypeId = $('#cpdBugtypeId').val();
	var cpdBugsubtypeId = $('#cpdBugsubtypeId').val();

	if (dpDeptid == '') {
		errorList.push('Please Select Department');
		var cpdBugsubtypeIdRev = $("#prRevBudgetCode" + cnt).val("");
		$("#prRevBudgetCode" + cnt).val('').trigger('chosen:updated');
	}

	/*
	 * if (cpdBugtypeId == '0') { errorList.push('Please Select Budget Type');
	 * var cpdBugsubtypeIdRev = $("#prRevBudgetCode"+cnt).val("");
	 * $("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated'); } if
	 * (cpdBugsubtypeId == '') { errorList.push('Please Select Budget Sub
	 * Type'); var cpdBugsubtypeIdRev = $("#prRevBudgetCode"+cnt).val("");
	 * $("#prRevBudgetCode"+cnt).val('').trigger('chosen:updated'); }
	 */

	var id = $("#indexdata").val();
	if (id == "" || id == undefined) {
		id = 0;
	}

	var prExpBudgetCode = $('#prExpBudgetCode' + id).val();

	for (m = 0; m <= id; m++) {
		if ($('#prExpBudgetCode' + m).val() == "") {

			errorList.push("Please select Account Head First!");
			var prExpBudgetCode = $("#prExpBudgetCode" + cont).val("");
			$("#prExpBudgetCode" + cont).val('').trigger('chosen:updated');
		}
	}

	var Expid = $("#indexdata").val();
	var dec;
	if (prExpBudgetCode != "") {
		if (Expid == "" || Expid == undefined) {
			Expid = 0;
		}
		for (m = 0; m <= Expid; m++) {
			for (dec = 0; dec <= Expid; dec++) {
				if (m != dec) {
					if (($('#prExpBudgetCode' + m).val() == $(
							'#prExpBudgetCode' + dec).val())) {
						errorList
								.push("The Combination Budget head already exists!");
						var prRevBudgetCode = $("#prExpBudgetCode" + cont).val(
								"");
						$("#prExpBudgetCode" + cont).val('').trigger(
								'chosen:updated');

						var lastYearCountExpDupOne = $(
								"#lastYearCountExpDupOne" + cont).val("");
						var lastYearCountExpDupTwo = $(
								"#lastYearCountExpDupTwo" + cont).val("");
						var lastYearCountExpDupThree = $(
								"#lastYearCountExpDupThree" + cont).val("");
						var orginalEstamtExp = $("#orginalEstamtExp" + cont)
								.val("");
						var orginalEstamtExp = $("#revisedEstamt" + cont).val(
								"");
						var estimateForNextyearExp = $(
								"#estimateForNextyearExp" + cont).val("");
						var apprBugStandComExp = $("#apprBugStandComExp" + cont)
								.val("");
						var finalizedBugGenBodyExp = $(
								"#finalizedBugGenBodyExp" + cont).val("");

					}
				}
			}
		}
	}

	if (errorList.length > 0) {

		var errorMsg = '<ul>';
		$.each(errorList, function(index) {
			errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';

		});
		errorMsg += '</ul>';

		$('#errorId').html(errorMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');

	}

	var theForm = '#tbAcBudgetEstimationPreparation';
	var requestData = __serializeForm(theForm);

	if (errorList.length == 0) {

		var url = "AccountBudgetEstimationPreparation.html?getOrgBalExpDuplicateGridloadData&cont="
				+ cont;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var errorList = [];
		if (returnData) {
			errorList
					.push("Budget Estimation is already exist against selected Budget head!!");
			var pacHeadId = $("#prExpBudgetCode" + cont).val("");
			$("#prExpBudgetCode" + cont).val('').trigger('chosen:updated');
			var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';

			$
					.each(
							errorList,
							function(index) {
								var errorMsg = '<ul>';
								$
										.each(
												errorList,
												function(index) {
													errorMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
															+ errorList[index]
															+ '</li>';

												});
								errorMsg += '</ul>';
								$('#errorId').html(errorMsg);
								$('#errorDivId').show();
								$('html,body').animate({
									scrollTop : 0
								}, 'slow');
							});
			return false;
		}
	}

	if (errorList.length == 0) {

		var url = "AccountBudgetEstimationPreparation.html?getOrgBalGridloadExp&cont="
				+ cont;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData, function(key, value) {
			// var id = $('#index').val();
			// $('#prProjectionidRev'+ id).val(value);
			var prExpBudgetCode = $('#prExpBudgetCode' + cont).val();

			if (prExpBudgetCode != "") {
				if (key == 'OEAE') {
					$("#orginalEstamtExp" + cont).val(value);
				}
			}
		});
	}

	if (errorList.length == 0) {

		var url = "AccountBudgetEstimationPreparation.html?getOrgAmountByLastYearCountExp&cont="
				+ cont;

		var returnData1 = __doAjaxRequest(url, 'post', requestData, false);
		$.each(returnData1, function(key, value) {
			// var id1 = $('#index').val();
			// $('#prProjectionidRev'+ id1).val(key);
			var prExpBudgetCode = $('#prExpBudgetCode' + cont).val();

			if (prExpBudgetCode != "") {
				if (key == 'OEAE2') {
					$("#lastYearCountExpDupOne" + cont).val(value);
				}
				if (key == 'OEAE1') {
					$("#lastYearCountExpDupTwo" + cont).val(value);
				}
				if (key == 'OEAE0') {
					$("#lastYearCountExpDupThree" + cont).val(value);
				}
			}
		});
	}

	if (errorList.length == 0) {

		var url = "AccountBudgetEstimationPreparation.html?getActualofOrgAmountExp&cont="
				+ cont;
		// debugger;
		var returnData1 = __doAjaxRequest(url, 'post', requestData, false);
		$("#actualsCurrentYearO" + cont).val(returnData1);

	}

};

// to generate dynamic table
$("#prProjectionid")
		.on(
				"click",
				'.addButton',
				function(e) {
					var errorList = [];

					$('.appendableClass')
							.each(
									function(i) {
										/*
										 * var pacHeadId =
										 * $.trim($("#pacHeadId"+i).val());
										 * if(pacHeadId==0 || pacHeadId=="")
										 * errorList.push("Select Primary
										 * Account Head");
										 */

										var prRevBudgetCode = $.trim($(
												"#prRevBudgetCode" + i).val());
										if (prRevBudgetCode == 0
												|| prRevBudgetCode == "")
											// errorList.push("Please select
											// Budget heads");

											var prProjected = $.trim($(
													"#estimateForNextyear" + i)
													.val());
										if (prProjected == 0
												|| prProjected == "")
											// errorList.push("Please Enter
											// Estimate for Next Year amount");

											for (m = 0; m < i; m++) {
												if ($('#prRevBudgetCode' + m)
														.val() == $(
														'#prRevBudgetCode' + i)
														.val()) {

													errorList
															.push("The Combination code already exists, Please select another combination code again!");
												}
											}
										$("#indexdata").val(i);
									});
					if (errorList.length > 0) {
						$('#index').val(i);
						var errMsg = '<ul>';
						$
								.each(
										errorList,
										function(index) {
											errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
													+ errorList[index]
													+ '</li>';
										});

						errMsg += '</ul>';

						$('#errorId').html(errMsg);
						$('#errorDivId').show();
						$('html,body').animate({
							scrollTop : 0
						}, 'slow');
						return false;
					}
					e.preventDefault();
					var content = $(this).closest('#revTable tr').clone();
					$(this).closest("#revTable").append(content);
					// reset values
					content.find("select").attr("value", "");
					content.find("input:text").val("");
					content.find("select").val("");
					content.find('div.chosen-container').remove();
					content.find("select").chosen().trigger("chosen:updated");
					content.find("textarea").val("");
					content.find('label').closest('.error').remove(); // for
					// removal
					// duplicate
					reOrderTableIdSequence();

				});

// to delete row
$("#prProjectionid").on("click", '.delButton', function(e) {

	var rowCount = $('#revTable tr').length;
	if (rowCount <= 3) {
		// alert("Can Not Remove");
		var msg = "can not remove";
		showErrormsgboxTitle(msg);
		return false;
	}

	$(this).closest('#revTable tr').remove();

	reOrderTableIdSequence();
	e.preventDefault();
});

function reOrderTableIdSequence() {
	$('.appendableClass').each(
			function(i) {

				$(this).find('div.chosen-container').attr('id',
						"prRevBudgetCode" + i + "_chosen");
				// $(this).find("select:eq(0)").attr("id", "pacHeadId" + i);
				$(this).find("select:eq(0)").attr("id", "prRevBudgetCode" + i);
				$(this).find("input:text:eq(0)").attr("id",
						"prRevBudgetCode" + i);
				$(this).find("input:text:eq(1)").attr("id",
						"lastYearCountDupOne" + i);
				$(this).find("input:text:eq(2)").attr("id",
						"lastYearCountDupTwo" + i);
				$(this).find("input:text:eq(3)").attr("id",
						"lastYearCountDupThree" + i);
				$(this).find("input:text:eq(4)")
						.attr("id", "orginalEstamt" + i);
				// below filed new added for current year partial revised
				// provision

				$(this).find("input:text:eq(5)").attr("id",
						"actualsCurrentYear" + i);
				$(this).find("input:text:eq(6)").attr("id",
						"expectedCurrentYear" + i);

				// below filed new added for current year revised provision
				$(this).find("input:text:eq(7)")
						.attr("id", "revisedEstamt" + i);
				$(this).find("input:text:eq(8)").attr("id",
						"estimateForNextyear" + i);
				$(this).find("input:text:eq(9)").attr("id",
						"apprBugStandCom" + i);
				$(this).find("input:text:eq(10)").attr("id",
						"finalizedBugGenBody" + i);

				/*
				 * $(this).find("select:eq(0)").attr("name",
				 * "bugprojRevBeanList[" + i + "].pacHeadId");
				 */
				$(this).find("select:eq(0)").attr("name",
						"bugprojRevBeanList[" + i + "].prRevBudgetCode");
				$(this).find("input:text:eq(0)").attr("name",
						"bugprojRevBeanList[" + i + "].prRevBudgetCode");
				$(this).find("input:text:eq(1)").attr("name",
						"bugprojRevBeanList[" + i + "].lastYearCountDupOne");
				$(this).find("input:text:eq(2)").attr("name",
						"bugprojRevBeanList[" + i + "].lastYearCountDupTwo");
				$(this).find("input:text:eq(3)").attr("name",
						"bugprojRevBeanList[" + i + "].lastYearCountDupThree");
				$(this).find("input:text:eq(4)").attr("name",
						"bugprojRevBeanList[" + i + "].orginalEstamt");

				// below filed new added for current year partial revised
				// provision

				$(this).find("input:text:eq(5)").attr("name",
						"bugprojExpBeanList[" + i + "].actualsCurrentYear");
				$(this).find("input:text:eq(6)").attr("name",
						"bugprojExpBeanList[" + i + "].expectedCurrentYear");

				// below filed new added for current year revised provision
				$(this).find("input:text:eq(7)").attr("name",
						"bugprojRevBeanList[" + i + "].revisedEstamt");
				$(this).find("input:text:eq(8)").attr("name",
						"bugprojRevBeanList[" + i + "].estimateForNextyear");
				$(this).find("input:text:eq(9)").attr("name",
						"bugprojRevBeanList[" + i + "].apprBugStandCom");
				$(this).find("input:text:eq(10)").attr("name",
						"bugprojRevBeanList[" + i + "].finalizedBugGenBody");
				// $(this).find("#pacHeadId"+i).attr("onchange",
				// "setSecondaryCodeFinance(" + (i) + ")");
				$(this).parents('tr').find('.delButton').attr("id",
						"delButton" + i);
				$(this).parents('tr').find('.addButton').attr("id",
						"addButton" + i);
				/*
				 * $(this).parents('tr').find('#pacHeadId'+i).attr("onchange",
				 * "setSecondaryCodeFinance" + i);
				 */
				$(this).find('#prRevBudgetCode' + i).attr("onchange",
						"getOrgBalAmount(" + (i) + ")");
				$(this).find('#estimateForNextyear' + i).attr("onchange",
						"getAmountFormat(" + (i) + ")");

				$(this).find('#prRevBudgetCode' + i)
						.data('rule-required', true);
				$(this).find('#estimateForNextyear' + i).data('rule-required',
						true);

				$(this).closest("tr").attr("id", "bugestIdRev" + (i));
				$("#indexdata").val(i);
			});
}

// to generate dynamic table
$("#prExpenditureid")
		.on(
				"click",
				'.addButtonExp',
				function(e) {
					// debugger;
					var errorList = [];

					$('.ExpappendableClass')
							.each(
									function(i) {
										/*
										 * var pacHeadIdExp =
										 * $.trim($("#pacHeadIdExp"+i).val());
										 * if(pacHeadIdExp==0 ||
										 * pacHeadIdExp=="")
										 * errorList.push("Select Primary
										 * Account Head");
										 */

										var prExpBudgetCode = $.trim($(
												"#prExpBudgetCode" + i).val());
										if (prExpBudgetCode == 0
												|| prExpBudgetCode == "")
											// errorList.push("Please select
											// Budget heads");

											var prProjectedExp = $.trim($(
													"#estimateForNextyearExp"
															+ i).val());
										if (prProjectedExp == 0
												|| prProjectedExp == "")
											// errorList.push("Please Enter
											// Estimate for Next Year amount");

											for (m = 0; m < i; m++) {
												if ($('#prExpBudgetCode' + m)
														.val() == $(
														'#prExpBudgetCode' + i)
														.val()) {

													errorList
															.push("The Combination code already exists, Please select another combination code again!");
												}
											}
										$("#indexdata").val(i);
									});
					if (errorList.length > 0) {
						$('#index').val(i);
						var errMsg = '<ul>';
						$
								.each(
										errorList,
										function(index) {
											errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
													+ errorList[index]
													+ '</li>';
										});

						errMsg += '</ul>';

						$('#errorId').html(errMsg);
						$('#errorDivId').show();
						$('html,body').animate({
							scrollTop : 0
						}, 'slow');
						return false;
					}
					e.preventDefault();
					var content = $(this).closest('#expTable tr').clone();
					$(this).closest("#expTable").append(content);
					// reset values
					content.find("select").attr("value", "");
					content.find("input:text").val("");
					content.find("select").val("");
					content.find('div.chosen-container').remove();
					content.find("select").chosen().trigger("chosen:updated");
					content.find("textarea").val("");
					content.find('label').closest('.error').remove(); // for
					// removal
					// duplicate
					expreOrderTableIdSequence();

				});

// to delete row
$("#prExpenditureid").on("click", '.delButtonExp', function(e) {

	var rowCount = $('#expTable tr').length;
	if (rowCount <= 3) {
		// alert("Can Not Remove");
		var msg = "can not remove";
		showErrormsgboxTitle(msg);
		return false;
	}

	$(this).closest('#expTable tr').remove();

	expreOrderTableIdSequence();
	e.preventDefault();
});

function expreOrderTableIdSequence() {
	$('.ExpappendableClass').each(
			function(i) {

				$(this).find('div.chosen-container').attr('id',
						"prExpBudgetCode" + i + "_chosen");
				// $(this).find("select:eq(0)").attr("id", "pacHeadIdExp" + i);
				$(this).find("select:eq(0)").attr("id", "prExpBudgetCode" + i);
				$(this).find("input:text:eq(0)").attr("id",
						"prExpBudgetCode" + i);
				$(this).find("input:text:eq(1)").attr("id",
						"lastYearCountExpDupOne" + i);
				$(this).find("input:text:eq(2)").attr("id",
						"lastYearCountExpDupTwo" + i);
				$(this).find("input:text:eq(3)").attr("id",
						"lastYearCountExpDupThree" + i);
				$(this).find("input:text:eq(4)").attr("id",
						"orginalEstamtExp" + i);
				// below filed new added for current year partial revised
				// provision

				$(this).find("input:text:eq(5)").attr("id",
						"actualsCurrentYearO" + i);
				$(this).find("input:text:eq(6)").attr("id",
						"expectedCurrentYearO" + i);

				// below filed new added for current year revised provision

				// $(this).find("input:text:eq(7)").attr("id","revisedEstamt" +
				// i); // Old One
				$(this).find("input:text:eq(7)").attr("id",
						"revisedEstamtExp" + i);
				$(this).find("input:text:eq(8)").attr("id",
						"estimateForNextyearExp" + i);
				$(this).find("input:text:eq(9)").attr("id",
						"apprBugStandComExp" + i);
				$(this).find("input:text:eq(10)").attr("id",
						"finalizedBugGenBodyExp" + i);

				/*
				 * $(this).find("select:eq(0)").attr("name",
				 * "bugprojExpBeanList[" + i + "].pacHeadId");
				 */
				$(this).find("select:eq(0)").attr("name",
						"bugprojExpBeanList[" + i + "].prExpBudgetCode");
				$(this).find("input:text:eq(0)").attr("name",
						"bugprojExpBeanList[" + i + "].prExpBudgetCode");
				$(this).find("input:text:eq(1)").attr("name",
						"bugprojExpBeanList[" + i + "].lastYearCountDupOne");
				$(this).find("input:text:eq(2)").attr("name",
						"bugprojExpBeanList[" + i + "].lastYearCountDupTwo");
				$(this).find("input:text:eq(3)").attr("name",
						"bugprojExpBeanList[" + i + "].lastYearCountDupThree");
				$(this).find("input:text:eq(4)").attr("name",
						"bugprojExpBeanList[" + i + "].orginalEstamt");
				// below filed new added for current year partial revised
				// provision

				$(this).find("input:text:eq(5)").attr("name",
						"bugprojExpBeanList[" + i + "].actualsCurrentYearO");
				$(this).find("input:text:eq(6)").attr("name",
						"bugprojExpBeanList[" + i + "].expectedCurrentYearO");

				// below filed new added for current year revised provision
				$(this).find("input:text:eq(7)").attr("name",
						"bugprojExpBeanList[" + i + "].revisedEstamt");
				$(this).find("input:text:eq(8)").attr("name",
						"bugprojExpBeanList[" + i + "].estimateForNextyear");
				$(this).find("input:text:eq(9)").attr("name",
						"bugprojExpBeanList[" + i + "].apprBugStandCom");
				$(this).find("input:text:eq(10)").attr("name",
						"bugprojExpBeanList[" + i + "].finalizedBugGenBody");
				// $(this).find("#pacHeadIdExp"+i).attr("onchange",
				// "setSecondaryCodeFinanceExp(" + (i) + ")");
				$(this).parents('tr').find('.delButtonExp').attr("id",
						"delButtonExp" + i);
				$(this).parents('tr').find('.addButtonExp').attr("id",
						"addButtonExp" + i);
				/*
				 * $(this).parents('tr').find('#pacHeadIdExp'+i).attr("onchange",
				 * "setSecondaryCodeFinanceExp" + i);
				 */
				$(this).find('#prExpBudgetCode' + i).attr("onchange",
						"getOrgBalAmountExp(" + (i) + ")");
				$(this).find('#estimateForNextyearExp' + i).attr("onchange",
						"getAmountFormatExp(" + (i) + ")");

				$(this).find('#prExpBudgetCode' + i)
						.data('rule-required', true);
				$(this).find('#estimateForNextyearExp' + i).data(
						'rule-required', true);

				$(this).closest("tr").attr("id", "bugestIdExp" + (i));
				$("#indexdata").val(i);
			});
}

function displayMessageOnSubmit(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';

	message += '<h5 class=\'text-center text-blue-2 padding-5\'>' + successMsg
			+ '</h5>';
	message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls
			+ '\'  id=\'btnNo\' onclick="redirectToDishonorHomePage()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	// showPopUpMsg(errMsgDiv);
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function redirectToDishonorHomePage() {
	$.fancybox.close();
	window.location.href = 'AccountBudgetEstimationPreparation.html';
}

function showPopUpMsg(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic', // 'elastic', 'fade' or 'none'
		closeBtn : false,
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});
	return false;
}

function clearAllData(obj) {
	 debugger;
	$('.error-div').hide();
	var errorList = [];

	$('#hiddenFinYear').val($('#faYearid').val());
	// var cpdBugsubtypeId = $("#cpdBugsubtypeId").val("");
	// var lastYearCount = $('#lastYearCount').val("");

	var lastYearCountDupOne0 = $('#lastYearCountDupOne0').val("");
	var lastYearCountDupTwo0 = $('#lastYearCountDupTwo0').val("");
	var lastYearCountDupThree0 = $('#lastYearCountDupThree0').val("");
	var orginalEstamt0 = $('#orginalEstamt0').val("");

	var lastYearCountExpDupOne = $('#lastYearCountExpDupOne0').val("");
	var lastYearCountExpDupTwo0 = $('#lastYearCountExpDupTwo0').val("");
	var lastYearCountExpDupThree0 = $('#lastYearCountExpDupThree0').val("");
	var orginalEstamtExp0 = $('#orginalEstamtExp0').val("");
	var orginalEstamtExp0 = $('#revisedEstamt0').val("");

	if (errorList.length == 0) {

		var divName = ".content";

		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AccountBudgetEstimationPreparation.html?getjqGridload";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

		var budgetType = $('#cpdBugtypeId').val();

		$('#faYearid').prop('disabled', 'disabled');

		var budgetType = $("#cpdBugtypeId option:selected").attr("code");
		if (budgetType == "REV") {

			$("#prRevBudgetCode0").data('rule-required', true);
			$("#estimateForNextyear0").data('rule-required', true);

			$("#prProjectionid").removeClass("hide");
			$("#prExpenditureid").addClass("hide");
		} else if (budgetType == "EXP") {

			$("#prExpBudgetCode0").data('rule-required', true);
			$("#estimateForNextyearExp0").data('rule-required', true);

			$("#prProjectionid").addClass("hide");
			$("#prExpenditureid").removeClass("hide");
		}

	}

	$('.chosen-select-no-results').chosen();
};

