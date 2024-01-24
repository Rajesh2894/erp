$("#tbAcBudgetProjectedRevenueEntry").validate({

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
$(function() {

	$("#grid")
			.jqGrid(
					{
						url : "AccountBudgetProjectedRevenueEntry.html?getGridData",
						datatype : "json",
						mtype : "POST",
						colNames : [
								getLocalMessage('budget.projected.expenditure.master.department'),
								'',
								getLocalMessage('account.budgetprojectedrevenuemaster.budgethead'),
								getLocalMessage('account.budgetprojectedrevenuemaster.originalestimate(indianrupees)'),
								getLocalMessage('budget.projected.revenue.entry.master.revisebudget'),
								getLocalMessage('account.budgetprojectedrevenuemaster.collection'),
								getLocalMessage('account.budgetprojectedrevenuemaster.balancebudget'),
								"", getLocalMessage('bill.action') ],
						colModel : [
								{
									name : "dpDeptName",
									width : 40,
									sortable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									}
								},
								{
									name : "prProjectionid",
									width : 10,
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
									name : "prRevBudgetCode",
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
									name : "formattedCurrency",
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
								},
								{
									name : "formattedCurrency3",
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
								},
								{
									name : "formattedCurrency1",
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
								},
								{
									name : "formattedCurrency2",
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
									name : "faYearid",
									sortable : true,
									hidden : true
								}, {
									name : 'prProjectionid',
									index : 'prProjectionid',
									width : 20,
									align : 'center !important',
									formatter : addLink,
									search : false
								}

						],
						pager : "#pagered",
						rowNum : 30,
						rowList : [ 5, 10, 20, 30 ],
						sortname : "prProjectionid",
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
						caption : getLocalMessage('account.budgetprojectedrevenuemaster.accountbudgetpeojectedrevenueentrylist'),

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

function addLink(cellvalue, options, rowdata) {
	return "<a class='btn btn-warning btn-sm editBugopnBalMasterClass' title='Edit'value='"
			+ rowdata.prProjectionid
			+ "' prProjectionid='"
			+ rowdata.prProjectionid + "' ><i class='fa fa-pencil'></i></a> ";
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
function returnDeleteUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='deleteDesignationClass fa fa-trash-o fa-2x'  alt='View  Master' title='Delete  Master'></a>";
}

$(document).ready(function() {
	$('.error-div').hide();
	var Error_Status = '${Errore_Value}';

});

function closeOutErrBox() {
	$('.error-div').hide();
}

$(function() {
	$(document).on(
			'click',
			'.createData',
			function() {

				var $link = $(this);
				var prProjectionid = 1;
				var url = "AccountBudgetProjectedRevenueEntry.html?form";
				var requestData = "prProjectionid=" + prProjectionid
						+ "&MODE_DATA=" + "ADD";
				var returnData = __doAjaxRequest(url, 'post', requestData,
						false);

				var divName = ".content";
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				return false;
			});

	$(document)
			.on(
					'click',
					'.editBugopnBalMasterClass',
					function() {

						var errorList = [];
						var $link = $(this);
						var prProjectionid = $link.closest('tr').find(
								'td:eq(1)').text();
						var faYearid = $link.closest('tr').find('td:eq(7)')
								.text();
						var revValue = $link.closest('tr').find('td:eq(5)')
								.text();
						var url = "AccountBudgetProjectedRevenueEntry.html?update";
						var requestData = "prProjectionid=" + prProjectionid
								+ "&MODE_DATA=" + "EDIT";
						var returnData = __doAjaxRequest(url, 'post',
								requestData, false);
						if (revValue == "0.00") {
							revValue = "";
						}
						if (jQuery.trim(revValue).length < 0) {
							$('.content').html(returnData);
						}
						if (jQuery.trim(revValue).length == 0) {

							var response = checkTransaction(prProjectionid,
									faYearid);
							if (response == false) {
								$('.content').html(returnData);
							} else {
								errorList
										.push("Transactions are available so edit is not allow!");
								if (errorList.length > 0) {
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
								}
							}
						} else {
							errorList
									.push("Transactions are available so edit is not allowed");
							if (errorList.length > 0) {
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
							}
						}
					});

	$(document)
			.on(
					'click',
					'.viewBugopnBalMasterClass',
					function() {
						var $link = $(this);
						var prProjectionid = $link.closest('tr').find(
								'td:eq(0)').text();
						var url = "AccountBudgetProjectedRevenueEntry.html?formForView";
						var requestData = "prProjectionid=" + prProjectionid
								+ "&MODE_DATA=" + "VIEW";
						var returnData = __doAjaxRequest(url, 'post',
								requestData, false);
						var divName = '.content';
						$(divName).removeClass('ajaxloader');
						$(divName).html(returnData);
						$('select').attr("disabled", true);
						$('input[type=text]').attr("disabled", true);
						$('select').prop('disabled', true).trigger(
								"chosen:updated");
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

	$('.hasMyNumber').keyup(function() {
		this.value = this.value.replace(/[^0-9.]/g, '');
		$(this).attr('maxlength', '13');
	});
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

var objTemp;
function saveLeveledData(obj) {
	
	var errorList = [];
	validateOnsave(errorList);
	
	objTemp = obj;
	incrementvalue = ++count;
	var Revid = $("#indexdata").val();
	var prBudgetCodeid = $('#prBudgetCodeid' + Revid).val();
	var orginalEstamt = $('#orginalEstamt' + Revid).val();
	if (prBudgetCodeid == undefined || prBudgetCodeid == ''
			|| prBudgetCodeid == '0') {
		errorList.push(getLocalMessage('account.receiptSelectBudgetHead.validationBtn'));
	}
	if (orginalEstamt == undefined || orginalEstamt == ''
			|| orginalEstamt == '0') {
		errorList.push(getLocalMessage('account.receiptSelectOriginalBtn.validationBtn'));
	}
	
	if (prBudgetCodeid != "") {
		var dec;
		for (m = 0; m <= Revid; m++) {
			for (dec = 0; dec <= Revid; dec++) {
				if (m != dec) {
					if ($('#prBudgetCodeid' + m).val() == $(
							'#prBudgetCodeid' + dec).val()) {
						errorList
								.push("The combination accounthead already exists!");
					}
				}
			}
		}
	}
	if(errorList.length>0){
    	
    	var errorMsg = '<ul>';
    	$.each(errorList, function(index){
    		errorMsg +='<li><i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
    		
    	});
    	errorMsg +='</ul>';
    	
    	$('#errorId').html(errorMsg);
    	$('#errorDivId').show();
		$('html,body').animate({ scrollTop: 0 }, 'slow');
    		    	
    }else{
	showConfirmBoxSave();
    }
	/*
	 * var formName = findClosestElementId(obj, 'form'); var theForm = '#' +
	 * formName; var requestData = __serializeForm(theForm); var url =
	 * $(theForm).attr('action');
	 * 
	 * var response = __doAjaxRequestValidationAccor(obj, url + '?create',
	 * 'post', requestData, false, 'html'); if (response != false) {
	 * $('.content').html(response); }
	 */

}

function showConfirmBoxSave() {
	// debugger;

	var saveorAproveMsg = "save";
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var saveMsg = getLocalMessage("account.btn.save.msg");
	var cls = getLocalMessage("account.btn.save.yes");
	var no = getLocalMessage("account.btn.save.no");

	message += '<h4 class=\"text-center text-blue-2\"> ' + saveMsg + '</h4>';
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
	debugger;
	var formName = findClosestElementId(objTemp, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var url = $(theForm).attr('action');

	var response = __doAjaxRequestValidationAccor(objTemp, url + '?create',
			'post', requestData, false, 'html');

	var obj = $(response).find('#successFlag');
	if (obj.val() == 'Y') {
		showConfirmBox();
	} else {
		$(".widget-content").html(status);
		$(".widget-content").show();
	}

}
function showConfirmBox() {
	debugger;
	var formMode_Id = $("#formMode_Id").val();
	if (formMode_Id == "create") {
		var successMsg = getLocalMessage("account.receiptManagement.recordSubmit");
	} else if (formMode_Id == "EDIT") {
		var successMsg = getLocalMessage("account.update.succ");
	}
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("account.billEntry.savedBtnProceed");
	message += '<h5 class=\'text-center text-blue-2 padding-5\'>' + successMsg
			+ '</h5>';
	message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}
function proceed() {
	window.location.href = 'AccountBudgetProjectedRevenueEntry.html';
}

function setHiddenData() {
	$('#secondaryId').val($('#prBudgetCodeid0').val());
}

function searchBudgetRevenueData() {
	$('.error-div').hide();
	var errorList = [];
	var faYearid = $("#faYearid").val();
	var cpdBugsubtypeId = $("#cpdBugsubtypeId").val();
	var dpDeptid = $("#dpDeptid").val();
	var prBudgetCodeid = $("#prBudgetCodeid").val();
	var fundId = $("#fundId").val();
	var functionId = $("#functionId").val();
	var fieldId = $("#fieldId").val();

	if (fundId === undefined) {
		fundId = "";
	}

	if (functionId === undefined) {
		functionId = "";
	}

	if (cpdBugsubtypeId === undefined) {
		cpdBugsubtypeId = "";
	}

	if (faYearid == "" || faYearid == "0") {
		errorList.push('Kindly select financial Year');
	}
	if (fieldId === undefined) {
		fieldId = "";
	}
	
	if(faYearid == "" || dpDeptid == "" || prBudgetCodeid == "" || fieldId==""){
		errorList
		.push(getLocalMessage("accounts.validation.mandatory.fields"));
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
		var url = "AccountBudgetProjectedRevenueEntry.html?getjqGridsearch";
		var requestData = {
			"faYearid" : faYearid,
			"cpdBugsubtypeId" : cpdBugsubtypeId,
			"dpDeptid" : dpDeptid,
			"prBudgetCodeid" : prBudgetCodeid,
			"fundId" : fundId,
			"functionId" : functionId,
			"fieldId" : fieldId
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
	window.location.href = 'AccountBudgetProjectedRevenueEntry.html';
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

// to generate dynamic table
$("#budRevTableDivID")
		.on(
				"click",
				'.addButton',
				function(e) {
					var errorList = [];

					$('.appendableClass')
							.each(
									function(i) {
										var prBudgetCodeid = $.trim($(
												"#prBudgetCodeid" + i).val());
										if (prBudgetCodeid == undefined || prBudgetCodeid == ''
											|| prBudgetCodeid == '0') {
										errorList.push("Please select budget head");
									}
									
									
										if (prBudgetCodeid != 0
												&& prBudgetCodeid != "")
											var orginalEstamt = $
													.trim($(
															"#orginalEstamt"
																	+ i).val());
										if (orginalEstamt == undefined || orginalEstamt == ''
											|| orginalEstamt == 0) {
										errorList.push("Please select original  budget ");
										
											for (m = 0; m < i; m++) {
												if ($('#prBudgetCodeid' + m)
														.val() == $(
														'#prBudgetCodeid' + i)
														.val()) {

													errorList
															.push("The combination accounthead code already exists!");
												}
											}}

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
					var content = $(this).closest('#budRevTable tr').clone();

					$(this).closest("#budRevTable").append(content);
					content.find("select").attr("value", "");
					content.find("input:text").val("");
					content.find("select").val("");
					content.find("input:checkbox").attr('checked', false);
					content.find('div.chosen-container').remove();
					content.find("select").chosen().trigger("chosen:updated");
					content.find('label').closest('.error').remove(); // for
					// removal
					// duplicate
					reOrderTableIdSequence();

				});

// to delete row
$("#budRevTableDivID").on("click", '.delButton', function(e) {

	var rowCount = $('#budRevTable tr').length;
	if (rowCount <= 2) {
		var msg = "can not remove";
		showErrormsgboxTitle(msg);
		return false;
	}

	$(this).closest('#budRevTable tr').remove();
	reOrderTableIdSequence();
	e.preventDefault();
});

function reOrderTableIdSequence() {
	$('.appendableClass').each(
			function(i) {

				$(this).find('div.chosen-container').attr('id',
						"prBudgetCodeid" + i + "_chosen");
				$(this).find("select:eq(0)").attr("id", "prBudgetCodeid" + i);
				$(this).find("input:text:eq(0)").attr("id",
						"prBudgetCodeid" + i);
				$(this).find("input:text:eq(1)")
						.attr("id", "orginalEstamt" + i);
				$(this).find("select:eq(0)").attr("name",
						"bugRevenueMasterDtoList[" + i + "].prBudgetCodeid");
				$(this).find("input:text:eq(0)").attr("name",
						"bugRevenueMasterDtoList[" + i + "].prBudgetCodeid");
				$(this).find("input:text:eq(1)").attr("name",
						"bugRevenueMasterDtoList[" + i + "].orginalEstamt");
				$(this).find('.delButton').attr("id", "delButton" + i);
				$(this).find('.addButton').attr("id", "addButton" + i);
				$(this).find('#prBudgetCodeid' + i).attr("onchange",
						"findduplicatecombinationexit(" + (i) + ")");
				$(this).closest("tr").attr("id", "budRevId" + (i));
				$("#indexdata").val(i);

			});

}

function findduplicatecombinationexit(cnt) {
	if ($('#count').val() == "") {
		count = 1;
	} else {
		count = parseInt($('#count').val());
	}
	var assign = count;
	$('.error-div').hide();
	var errorList = [];
	var theForm = '#frmMaster';
	var requestData = __serializeForm(theForm);
	var id = $("#indexdata").val();
	var prBudgetCodeid = $('#prBudgetCodeid' + id).val();
	var Revid = $("#indexdata").val();
	if (prBudgetCodeid != "") {
		var dec;
		for (m = 0; m <= Revid; m++) {
			for (dec = 0; dec <= Revid; dec++) {
				if (m != dec) {
					if ($('#prBudgetCodeid' + m).val() == $(
							'#prBudgetCodeid' + dec).val()) {
						errorList
								.push("The combination accounthead already exists!");
						$("#prBudgetCodeid" + cnt).val("");
						$("#prBudgetCodeid" + cnt).val('').trigger(
								'chosen:updated');
						var orginalEstamt = $("#orginalEstamt" + cnt).val("");
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

	if (errorList.length == 0) {

		if ($('#faYearid').val() == "") {
			msgBox('Please Select Financial Year');
			var prBudgetCodeid = $('#prBudgetCodeid' + cnt).val("");
			$("#prBudgetCodeid" + cnt).val('').trigger('chosen:updated');
			return false;
		}

		if ($('#cpdBugsubtypeId').val() == "") {
			msgBox('Please Select Budget Sub Type');
			var prBudgetCodeid = $('#prBudgetCodeid' + cnt).val("");
			$("#prBudgetCodeid" + cnt).val('').trigger('chosen:updated');
			return false;
		}

		if ($('#dpDeptid').val() == "") {
			msgBox('Please Select Department');
			var prBudgetCodeid = $('#prBudgetCodeid' + cnt).val("");
			$("#prBudgetCodeid" + cnt).val('').trigger('chosen:updated');
			return false;
		}

		var url = "AccountBudgetProjectedRevenueEntry.html?getBudgetRevDuplicateGridloadData&cnt="
				+ cnt;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);

		if (returnData) {

			errorList
					.push(getLocalMessage("account.budget.revenue.exit.validation"));

			var prBudgetCodeid = $('#prBudgetCodeid' + cnt).val("");
			$("#prBudgetCodeid" + cnt).val('').trigger('chosen:updated');
			$('#functionId' + cnt).val("");
			$("#functionId" + cnt).val('').trigger('chosen:updated');

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

};

function clearFormData(cnt) {

	$('.error-div').hide();
	var errorList = [];

	var prBudgetCodeid = $('#prBudgetCodeid' + cnt).val("");
	$("#prBudgetCodeid" + cnt).val('').trigger('chosen:updated');

	var theForm = '#frmMaster';

	var faYearid = $('#faYearid').val();
	var dpDeptid = $('#dpDeptid').val();
	var cpdBugsubtypeId = $('#cpdBugsubtypeId').val();

	if (faYearid == '') {
		errorList.push('Please Select Financial Year');
		var functionId = $('#functionId' + cnt).val("");
		$("#functionId" + cnt).val('').trigger('chosen:updated');
	}

	if (dpDeptid == '') {
		errorList.push('Please Select Department');
		var functionId = $('#functionId' + cnt).val("");
		$("#functionId" + cnt).val('').trigger('chosen:updated');
	}

	if (cpdBugsubtypeId == '') {
		errorList.push('Please Select Budget Sub Type');
		var functionId = $('#functionId' + cnt).val("");
		$("#functionId" + cnt).val('').trigger('chosen:updated');
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
};

function clearAllData(obj) {
	$('#fieldId').val("");
	$("#fieldId").val('').trigger('chosen:updated');

	$('#functionId').val("");
	$("#functionId").val('').trigger('chosen:updated');

	var prBudgetCodeid = $('#prBudgetCodeid0').val("");
	$("#prBudgetCodeid0").val('').trigger('chosen:updated');

	var orginalEstamt = $('#orginalEstamt0').val("");
	var revisedEstamt = $('#revisedEstamt0').val("");
	var prCollected = $('#prCollected0').val("");
	var prRevBudgetCode = $('#prRevBudgetCode0').val("");

	$('.error-div').hide();
	var errorList = [];

	if (errorList.length == 0) {

		var divName = ".content";

		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AccountBudgetProjectedRevenueEntry.html?getjqGridload";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

	}
}

function loadBudgetReappropriationData(obj) {

	$('.error-div').hide();
	var errorList = [];

	$('#fieldId').val("");
	$("#fieldId").val('').trigger('chosen:updated');

	$('#functionId').val("");
	$("#functionId").val('').trigger('chosen:updated');

	var prBudgetCodeid = $('#prBudgetCodeid0').val("");
	$("#prBudgetCodeid0").val('').trigger('chosen:updated');

	var orginalEstamt = $('#orginalEstamt0').val("");
	var revisedEstamt = $('#revisedEstamt0').val("");
	var prCollected = $('#prCollected0').val("");
	var prRevBudgetCode = $('#prRevBudgetCode0').val("");

	var faYearid = $('#faYearid').val();
	var cpdBugsubtypeId = $('#cpdBugsubtypeId').val();

	if (faYearid == '') {
		errorList.push('Please Select Financial Year');
		var dpDeptid = $('#dpDeptid').val("");
		$("#dpDeptid").val('').trigger('chosen:updated');
	}

	if (cpdBugsubtypeId == '') {
		errorList.push('Please Select Budget Sub Type');
		var dpDeptid = $('#dpDeptid').val("");
		$("#dpDeptid").val('').trigger('chosen:updated');
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

		var url = "AccountBudgetProjectedRevenueEntry.html?getjqGridload";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

	}
};

function loadBudgetReappropriationFieldData(obj) {

	$('.error-div').hide();
	var errorList = [];

	$('#functionId').val("");
	$("#functionId").val('').trigger('chosen:updated');

	var prBudgetCodeid = $('#prBudgetCodeid0').val("");
	$("#prBudgetCodeid0").val('').trigger('chosen:updated');

	var orginalEstamt = $('#orginalEstamt0').val("");
	var revisedEstamt = $('#revisedEstamt0').val("");
	var prCollected = $('#prCollected0').val("");
	var prRevBudgetCode = $('#prRevBudgetCode0').val("");

	var faYearid = $('#faYearid').val();
	var cpdBugsubtypeId = $('#cpdBugsubtypeId').val();
	var dpDeptid = $('#dpDeptid').val();

	if (faYearid == '') {
		errorList.push('Please Select Financial Year');
		var dpDeptid = $('#dpDeptid').val("");
		$("#dpDeptid").val('').trigger('chosen:updated');
	}

	if (cpdBugsubtypeId == '') {
		errorList.push('Please Select Budget Sub Type');
		var dpDeptid = $('#dpDeptid').val("");
		$("#dpDeptid").val('').trigger('chosen:updated');
	}

	if (dpDeptid == '') {
		errorList.push('Please Select Department');
		var fieldId = $('#fieldId').val("");
		$("#fieldId").val('').trigger('chosen:updated');
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

		var url = "AccountBudgetProjectedRevenueEntry.html?getjqGridFiledload";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

	}
};

function loadBudgetReappropriationFunctionData(obj) {

	$('.error-div').hide();
	var errorList = [];
	/*
	 * $('#fieldId').val(""); $("#fieldId").val('').trigger('chosen:updated');
	 */
	var prBudgetCodeid = $('#prBudgetCodeid0').val("");
	$("#prBudgetCodeid0").val('').trigger('chosen:updated');

	var orginalEstamt = $('#orginalEstamt0').val("");
	var revisedEstamt = $('#revisedEstamt0').val("");
	var prCollected = $('#prCollected0').val("");
	var prRevBudgetCode = $('#prRevBudgetCode0').val("");

	var faYearid = $('#faYearid').val();
	var cpdBugsubtypeId = $('#cpdBugsubtypeId').val();
	var dpDeptid = $('#dpDeptid').val();

	if (faYearid == '') {
		errorList.push('Please Select Financial Year');
		var dpDeptid = $('#dpDeptid').val("");
		$("#dpDeptid").val('').trigger('chosen:updated');
	}

	if (cpdBugsubtypeId == '') {
		errorList.push('Please Select Budget Sub Type');
		var dpDeptid = $('#dpDeptid').val("");
		$("#dpDeptid").val('').trigger('chosen:updated');
	}

	if (dpDeptid == '') {
		errorList.push('Please Select Department');
		var fieldId = $('#functionId').val("");
		$("#functionId").val('').trigger('chosen:updated');
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

		var url = "AccountBudgetProjectedRevenueEntry.html?getjqGridFunctionload";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

	}
};

function clearAllAmountData(cont) {
	var orginalEstamt = $('#orginalEstamt' + cont).val("");
}

function checkTransaction(prProjectionid, faYearid) {

	var url = "AccountBudgetProjectedRevenueEntry.html?checkTransactions";
	var requestData = "prProjectionid=" + prProjectionid + "&faYearid="
			+ faYearid;
	response = __doAjaxRequest(url, 'post', requestData, false, '');
	return response;
}

function exportTemplate() {
	var $link = $(this);
	var url = "AccountBudgetProjectedRevenueEntry.html?exportTemplateData";
	var requestData = "";
	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	$('.content').html(returnData);

	prepareDateTag();
	return false;
}
function closeOutErrorBox() {
	$('#errorDivSec').hide();
}

function validateOnsave(errorList) {
	
	$('.error-div').hide();

	var faYearid = $("#faYearid").val();
	var cpdBugsubtypeId = $("#cpdBugsubtypeId").val();
	var dpDeptid = $("#dpDeptid").val();
	var prBudgetCodeid = $("#prBudgetCodeid").val();
	var fundId = $("#fundId").val();
	var functionId = $("#functionId").val();
	var fieldId = $("#fieldId").val();

	if (fieldId === undefined ||fieldId=='' ||fieldId=='0') {
		errorList.push(getLocalMessage('account.receiptSelectField.validationBtn'));
	}

	if (functionId === undefined||functionId==''||functionId=='0') {
		errorList.push(getLocalMessage('account.receiptSelectFunction.validationBtn'));
	}

	if (cpdBugsubtypeId === undefined||cpdBugsubtypeId=='') {
		errorList.push(getLocalMessage('account.receipt.validationBtn'));
	}

	if (faYearid == "" || faYearid == "0") {
		errorList.push(getLocalMessage('account.receiptFinancialYear.validationBtn'));
	}
	
	if (dpDeptid === undefined || dpDeptid=='' || dpDeptid==0) {
		errorList.push(getLocalMessage('account.receiptSelectDeptSave.validationBtn'));
	}

}