$(function() {

	$("#tbAcBudgetProjectedExpenditure").validate({

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

	$("#grid")
			.jqGrid(
					{
						url : "AccountBudgetProjectedExpenditure.html?getGridData",
						datatype : "json",
						mtype : "POST",
						colNames : [
							getLocalMessage('budget.projected.expenditure.master.department'),
								'',
								getLocalMessage('account.budgetprojectedexpendituremaster.budgethead'),
								getLocalMessage('account.budgetprojectedexpendituremaster.originalestimate(indianrupees)'),
								getLocalMessage('account.budgetprojectedexpendituremaster.revised.budget'),
								getLocalMessage('account.budgetprojectedexpendituremaster.accural'),
								getLocalMessage('account.budgetprojectedexpendituremaster.payment'),
								getLocalMessage('account.budgetprojectedexpendituremaster.balancebudget'),
								getLocalMessage('account.budgetprojectedexpendituremaster.fieldcode'),
								getLocalMessage('budget.projected.expenditure.master.currentspillOverAmount'),
								getLocalMessage('budget.projected.expenditure.master.nextspillOverAmount'),
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
									name : "prExpenditureid",
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
									name : "prExpBudgetCode",
									width : 65,
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
									width : 35,
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
									width : 35,
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
								},{
									name : "accrualAmount",
									width : 35,
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
									width : 35,
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
								},{
									name : "formattedCurrency2",
									width : 35,
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
									name : "fieldCode",
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
									name : "curYramt",
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
									name : "nxtYramt",
									width : 40,
									sortable : true,
									searchoptions : {
										"sopt" : [ 'cn', 'bw', 'eq', 'ne',
												'lt', 'le', 'gt', 'ge', 'bn',
												'ew', 'en', 'nc', 'nu', 'nn',
												'in', 'ni' ]
									}
								},{
									name : "faYearid",
									sortable : true,
									hidden : true
								}, {
									name : 'prExpenditureid',
									index : 'prExpenditureid',
									width : 20,
									align : 'center !important',
									formatter : addLink,
									search : false
								}
						// {name : 'prExpenditureid',index :
						// 'prExpenditureid',width : 20,align :
						// 'center',formatter : returnEditUrl,editoptions :
						// {value : "Yes:No"},formatoptions : {disabled :
						// false},search:false},
						// {name : 'prExpenditureid',index :
						// 'prExpenditureid',width : 20,align :
						// 'center',formatter : returnViewUrl,editoptions :
						// {value : "Yes:No"},formatoptions : {disabled :
						// false},search:false}
						],
						pager : "#pagered",
						rowNum : 30,
						rowList : [ 5, 10, 20, 30 ],
						sortname : "prExpenditureid",
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
						caption : getLocalMessage('account.budgetprojectedexpendituremaster.accountbudgetpeojectedrevenueentrylist'),

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
			+ rowdata.prExpenditureid
			+ "' prExpenditureid='"
			+ rowdata.prExpenditureid + "' ><i class='fa fa-pencil'></i></a> "+
			"<a class='btn btn-blue-2 btn-sm viewBudgetMasterClass' title='Budget Utilization Details'value='"
			+ rowdata.prExpenditureid
			+ "' prExpenditureid='"
			+ rowdata.prExpenditureid + "' ><i class='fa fa-area-chart'></i></a> ";
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
 * function returnEditUrl(cellValue, options, rowdata, action) { prExpenditureid =
 * rowdata.prExpenditureid; return "<a href='#' return false;
 * class='editBugopnBalMasterClass' value='"+prExpenditureid+"'><img
 * src='css/images/edit.png' width='20px' alt='Edit Master' title='Edit Master' /></a>"; }
 * 
 * function returnViewUrl(cellValue, options, rowdata, action) {
 * 
 * return "<a href='#' return false; class='viewBugopnBalMasterClass'
 * value='"+prExpenditureid+"'><img src='css/images/grid/view-icon.png'
 * width='20px' alt='View Master' title='View Master' /></a>"; }
 */

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
				/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
				var prExpenditureid = 1;
				var url = "AccountBudgetProjectedExpenditure.html?form";
				var requestData = "prExpenditureid=" + prExpenditureid
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
						/*
						 * var prExpenditureid =
						 * $link.closest('tr').find('td:eq(0)').text();
						 */
						
						var prExpenditureid = $link.closest('tr').find(
								'td:eq(1)').text();
						var faYearid = $link.closest('tr').find('td:eq(8)')
								.text();
						var expValue = $link.closest('tr').find('td:eq(5)')
								.text();
						var url = "AccountBudgetProjectedExpenditure.html?update";
						var requestData = "prExpenditureid=" + prExpenditureid
								+ "&MODE_DATA=" + "EDIT";
						var returnData = __doAjaxRequest(url, 'post',
								requestData, false);
                         
						if(expValue=="0.00"){
                        	 expValue="";
                         }
						//#157026 As discuss with Samadhan and Ajay
						//if (jQuery.trim(expValue).length < 0) {
							$('.content').html(returnData);
						//}
						/*if (jQuery.trim(expValue).length == 0) {

							var response = checkTransaction(prExpenditureid,
									faYearid);
							if (response == false) {
								$('.content').html(returnData);
							} else {
								//errorList
									//	.push("Transactions are available so EDIT is not allowed");
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
						//	errorList
									//.push("Transactions are available so EDIT is not allowed");
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
						}*/
						var status = $('#sacHeadStatus').val();
						if(status == "Y"){
							var readonly_select = $('select#prBudgetCodeid0');
							$(readonly_select).attr('readonly', true).attr('data-original-value', $(readonly_select).val()).on('change', function(i) {
							    $(i.target).val($(this).attr('data-original-value'));
							});
							$("#orginalEstamt0").attr("readonly", true);
						} else {
							$('select#prBudgetCodeid0').addClass('chosen-select-no-results');
						}
					});

	$(document).on(
			'click',
			'.viewBugopnBalMasterClass',
			function() {
				var $link = $(this);
				var prExpenditureid = $link.closest('tr').find('td:eq(0)')
						.text();
				var url = "AccountBudgetProjectedExpenditure.html?formForView";
				var requestData = "prExpenditureid=" + prExpenditureid
						+ "&MODE_DATA=" + "VIEW";
				var returnData = __doAjaxRequest(url, 'post', requestData,
						false);
				var divName = '.content';
				$(divName).removeClass('ajaxloader');
				$(divName).html(returnData);
				$('select').attr("disabled", true);
				$('input[type=text]').attr("disabled", true);
				$('select').prop('disabled', true).trigger("chosen:updated");
				return false;
			});
	
	$(document).on(
			'click',
			'.viewBudgetMasterClass',
			function() {
		
		var $link = $(this);
		var prExpenditureid = $link.closest('tr').find(
				'td:eq(1)').text();
		var faYearid = $link.closest('tr').find('td:eq(8)')
				.text();
		var expValue = $link.closest('tr').find('td:eq(5)')
				.text();
		var url = "AccountBudgetProjectedExpenditure.html?viewExpDetails";
		var requestData = "prExpenditureid=" + prExpenditureid
				+ "&MODE_DATA=" + "EDIT";
		var response = __doAjaxRequest(url, 'post', requestData, false);
		
		var divName = '.popUp';
		$(divName).removeClass('ajaxloader');
		$(divName).html(response);
		$(".popUp").show();
		$(".popUp").draggable();
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
		/* this.value = this.value.replace(/[^0-9]+\/.?[^0-9]*$/,''); */
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

var errMsgDiv = '.msg-dialog-box';

function validateAddForm(errorList){            
	var faYearid = $('#faYearid').val();     
	var cpdBugsubtypeId = $('#cpdBugsubtypeId').val();     
	var dpDeptid = $('#dpDeptid').val(); 
	        
	if ($('#faYearid').val() == '' || $("#faYearid").val() == undefined) {            
		errorList.push(getLocalMessage('account.budgetprojectedexpendituremaster.selectBudgetYear'));    
	}        
	if ($('#cpdBugsubtypeId').val() == '' || $("#cpdBugsubtypeId").val() == undefined) {           
	    errorList.push(getLocalMessage('account.budgetprojectedexpendituremaster.selectBudgetSubType'));    
	}        
	if ($('#dpDeptid').val() == '' || $("#dpDeptid").val() == undefined) {            
		errorList.push(getLocalMessage('account.budgetprojectedexpendituremaster.selectDepartment'));    
	} 
	
	$(".appendableClass").each(function(i) {
		var prBudgetCodeid = $("#prBudgetCodeid" + i).val();
		var orginalEstamt = $("#orginalEstamt" + i).val();
		if (prBudgetCodeid == '' || prBudgetCodeid == undefined) {
			errorList.push(getLocalMessage('account.budgetprojectedexpendituremaster.selectBudgetHead'));
		}
		if (orginalEstamt == '' || orginalEstamt == undefined) {
			errorList.push(getLocalMessage('account.budgetprojectedexpendituremaster.selectOriginalBudget'));
		}               
    });
	return errorList;
}

var objTemp;
function saveLeveledData(obj) {
	objTemp=obj;
	var errorList = [];
	
	/*
	 * var levelData = $('#prExpenditureid').val(); levelData++; var sid =
	 * 'faYearid' + levelData; var selID = '#'+sid;
	 * 
	 * if($('#count').val()=="0"){ count=0; }else{
	 * count=parseInt($('#count').val()); } var id=$('#index').val(); var assign =
	 * count;
	 * 
	 * var faYearid= $("#faYearid").val(); var cpdBugsubtypeId=
	 * $("#cpdBugsubtypeId").val(); var dpDeptid= $("#dpDeptid").val();
	 * 
	 * if(faYearid == '') { errorList.push("Please select Financial Year"); }
	 * 
	 * if(cpdBugsubtypeId == '') { errorList.push("Please select Budget Sub
	 * Type"); } if(dpDeptid == '') { errorList.push("Please select
	 * Department"); }
	 */

	incrementvalue = ++count;

	var Revid = $("#indexdata").val();

	// var functionId= $('#functionId'+Revid).val();
	var prBudgetCodeid = $('#prBudgetCodeid' + Revid).val();
	if (prBudgetCodeid != "") {
		var dec;
		for (m = 0; m <= Revid; m++) {
			for (dec = 0; dec <= Revid; dec++) {
				if (m != dec) {
					if ($('#prBudgetCodeid' + m).val() == $('#prBudgetCodeid' + dec).val()) {
						errorList.push("account.budgetprojectedexpendituremaster.accountHeadCombinationexists");
					}
				}
			}
		}
	}
	
	errorList = validateAddForm(errorList);
	if(errorList.length == 0){
		showConfirmBoxSave();
	}else{
		$("#errorDivId").show();
		displayErrorsOnPage(errorList);
	}


	/*
	 * var k = $("#indexdata").val(); for(n=0; n<=k;n++){ //var functionId =
	 * $('#functionId'+n).val(); var prBudgetCodeidRev =
	 * $('#prBudgetCodeid'+n).val(); var orginalEstamt =
	 * $("#orginalEstamt"+n).val(); if (prBudgetCodeidRev == '') {
	 * errorList.push("Please select Function"); } if (prBudgetCodeidRev == '') {
	 * errorList.push("Please select Account heads"); } if(orginalEstamt == ''){
	 * errorList.push("Please Enter Budget Provision Amount"); } }
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
	 * $('html,body').animate({ scrollTop: 0 }, 'slow');
	 *  } else{
	 */

	// return saveOrUpdateForm(obj, 'Saved Successfully',
	// 'AccountBudgetProjectedExpenditure.html','create');
	//showConfirmBoxSave();
	/*var formName = findClosestElementId(obj, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var url = $(theForm).attr('action');

	var response = __doAjaxRequestValidationAccor(obj, url + '?create', 'post',
			requestData, false, 'html');
	if (response != false) {
		$('.content').html(response);
	}*/

	// var response= __doAjaxRequestForSave(url+"?create", 'post', requestData,
	// false,'', obj);
	// $('.content').html(response);
	/* } */

}

function showConfirmBoxSave(){
	
	var saveorAproveMsg="save"; 
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var saveMsg=getLocalMessage("account.btn.save.msg");
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	 message	+='<h4 class=\"text-center text-blue-2\"> '+saveMsg+'</h4>';
	 message	+='<div class=\'text-center padding-bottom-10\'>  '+	
	'<input type=\'button\' value=\''+cls+'\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '+ 
	' onclick="saveDataAndShowSuccessMsg()"/>   '+ 
	'<input type=\'button\' value=\''+no+'\' tabindex=\'0\' id=\'btnNo\' class=\'btn btn-blue-2 autofocus\'    '+ 
	' onclick="closeConfirmBoxForm()"/>'+ 
	'</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutCloseaccount(errMsgDiv);
}


function saveDataAndShowSuccessMsg(){

	var	formName =	findClosestElementId(objTemp, 'form');
    var theForm	=	'#'+formName;
    var requestData = __serializeForm(theForm);
    var url	=	$(theForm).attr('action');
    
   // var response= __doAjaxRequestForSave(url+"?create", 'post', requestData, false,'', obj);
    var response= __doAjaxRequestValidationAccor(objTemp,url+'?create', 'post', requestData, false, 'html');
    var obj=$(response).find('#successFlag'); 
    if(obj.val()=='Y')
    	{
    	showConfirmBox();
    	}
     else
    	{
    	 $(".widget-content").html(status);
	     $(".widget-content").show();
    	}
	
}
function showConfirmBox(){

	var formMode_Id = $("#formMode_Id").val();	
	if(formMode_Id=="create"){
		var successMsg = getLocalMessage('account.receiptManagement.recordSubmit');
	}else if(formMode_Id=="EDIT"){
		var successMsg =  getLocalMessage('account.update.succ');
	}
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls = getLocalMessage('account.billEntry.savedBtnProceed');
	message	+='<h5 class=\'text-center text-blue-2 padding-5\'>'+successMsg+'</h5>';
	 message	+='<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+cls+'\'  id=\'btnNo\' onclick="proceed()"/></div>';
	 
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg'); 
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}
function proceed () {
	window.location.href='AccountBudgetProjectedExpenditure.html';
}

  function setSecondaryCodeFinance(count) {
	var primaryCode = $('#prBudgetCodeid' + count).val();

	$('#prBudgetCodeid' + count).find('option:gt(0)').remove();

	if (primaryCode > 0) {
		var postdata = 'prBudgetCodeid=' + primaryCode;

		var json = __doAjaxRequest(
				'AccountBudgetProjectedExpenditure.html?sacHeadItemsList',
				'POST', postdata, false, 'json');
		var optionsAsString = '';

		$.each(json, function(key, value) {
			optionsAsString += "<option value='" + key + "'>" + value
					+ "</option>";
		});
		$('#prBudgetCodeid' + count).append(optionsAsString);

	}
}

function setHiddenData() {
	$('#secondaryId').val($('#prBudgetCodeid0').val());
}

/*
 * $(document).ready(function() {
 * 
 * var primaryCode=$('#prBudgetCodeid0').val();
 * $('#prBudgetCodeid0').find('option:gt(0)').remove();
 * 
 * if (primaryCode > 0) { var postdata = 'prBudgetCodeid=' + primaryCode;
 * 
 * var json =
 * __doAjaxRequest('AccountBudgetProjectedExpenditure.html?sacHeadItemsList',
 * 'POST', postdata, false, 'json'); var optionsAsString=''; $.each( json,
 * function( key, value ) { optionsAsString += "<option value='" +key+"'>" +
 * value + "</option>"; }); $('#prBudgetCodeid0').append(optionsAsString );
 *  }
 * 
 * });
 */

function searchBudgetExpenditureData() {

	/* alert("In search"); */
	$('.error-div').hide();
	var errorList = [];

	var faYearid = $("#faYearid").val();
	var cpdBugsubtypeId = $("#cpdBugsubtypeId").val();
	var dpDeptid = $("#dpDeptid").val();
	var prBudgetCodeid = $("#prBudgetCodeid").val();
	var fundId = $("#fundId").val();
	var functionId = $("#functionId").val();
    var fieldId=$("#fieldId").val();
	
	
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
		var url = "AccountBudgetProjectedExpenditure.html?getjqGridsearch";
		var requestData = {
			"faYearid" : faYearid,
			"cpdBugsubtypeId" : cpdBugsubtypeId,
			"dpDeptid" : dpDeptid,
			"prBudgetCodeid" : prBudgetCodeid,
			"fundId" : fundId,
			"functionId" : functionId,
			"fieldId":fieldId
		};

		var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');

		if (result != null && result != "") {
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		} else {
			var errorList = [];

			errorList
					.push(getLocalMessage("account.norecord.criteria"));

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
	//showPopUpMsg(errMsgDiv);
	showModalBoxWithoutCloseaccount(errMsgDiv);
}

function redirectToDishonorHomePage() {
	$.fancybox.close();
	window.location.href = 'AccountBudgetProjectedExpenditure.html';
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
$("#budExpTableDivID")
		.on(
				"click",
				'.addButton',
				function(e) {
					var errorList = [];

					$('.appendableClass')
							.each(
									function(i) {

										/*
										 * var functionId =
										 * $.trim($("#functionId"+i).val());
										 * if(functionId==0 || functionId=="")
										 * errorList.push("Please select
										 * Function");
										 */

										var prBudgetCodeid = $.trim($(
												"#prBudgetCodeid" + i).val());
										if (prBudgetCodeid == 0
												|| prBudgetCodeid == "")
											 errorList.push('account.budgetprojectedexpendituremaster.selectBudgetHead');

											var orginalEstamt = $
													.trim($(
															"#orginalEstamt"
																	+ i).val());
										if (orginalEstamt == "")
											 errorList.push('account.budgetprojectedexpendituremaster.selectOriginalBudget');
									

											for (m = 0; m < i; m++) {
												if ($('#prBudgetCodeid' + m)
														.val() == $(
														'#prBudgetCodeid' + i)
														.val()) {

													errorList
															.push("The Combination AccountHead code already exists!");
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
					var content = $(this).closest('#budExpTable tr').clone();
					// call that script function which enable for search
					// remove extra added div by id

					$(this).closest("#budExpTable").append(content);
					// reset values
					content.find("select").attr("value", "");
					content.find("input:text").val("");
					content.find("select").val("");
					content.find("input:checkbox").attr('checked', false);
					content.find('div.chosen-container').remove();
					content.find("select").chosen().trigger("chosen:updated");
					content.find('label').closest('.error').remove(); // for
																		// removal
																		// duplicate
					reOrderTableIdSequenceAddForm();

				});

// to delete row
$("#budExpTableDivID").on("click", '.delButton', function(e) {

	var rowCount = $('#budExpTable tr').length;
	if (rowCount <= 2) {
		// alert("Can Not Remove");
		var msg = "can not remove";
		showErrormsgboxTitle(msg);
		return false;
	}

	$(this).closest('#budExpTable tr').remove();
	reOrderTableIdSequence();
	e.preventDefault();
});

function reOrderTableIdSequenceAddForm() {
	$('.appendableClass')
			.each(
					function(i) {

						$(this).find('div.chosen-container').attr('id',
								"prBudgetCodeid" + i + "_chosen");
						// $(this).find("select:eq(0)").attr("id",
						// "prBudgetCodeid" + i);
						// $(this).find("select:eq(0)").attr("id", "functionId"
						// + i);
						// $(this).find("input:text:eq(0)").attr("id",
						// "functionId" + i);
						$(this).find("select:eq(0)").attr("id",
								"prBudgetCodeid" + i);
						$(this).find("input:text:eq(0)").attr("id",
								"prBudgetCodeid" + i);
						$(this).find("input:text:eq(1)").attr("id",
								"orginalEstamt" + i);
						// $(this).find("input:text:eq(2)").attr("id",
						// "prBalanceAmt" + i);
						// $(this).find("input:text:eq(4)").attr("id",
						// "prCollected" + i);
						// $(this).find("input:text:eq(5)").attr("id",
						// "prRevBudgetCode" + i);

						// $(this).find("select:eq(0)").attr("name","bugExpenditureMasterDtoList["
						// + i + "].functionId");
						// $(this).find("input:text:eq(0)").attr("name","bugExpenditureMasterDtoList["
						// + i + "].functionId");
						$(this).find("select:eq(0)").attr(
								"name",
								"bugExpenditureMasterDtoList[" + i
										+ "].prBudgetCodeid");
						$(this).find("input:text:eq(0)").attr(
								"name",
								"bugExpenditureMasterDtoList[" + i
										+ "].prBudgetCodeid");
						$(this).find("input:text:eq(1)").attr(
								"name",
								"bugExpenditureMasterDtoList[" + i
										+ "].orginalEstamt");
						// $(this).find("input:text:eq(2)").attr("name","bugExpenditureMasterDtoList["
						// + i + "].prBalanceAmt");
						// $(this).find("input:text:eq(4)").attr("name","bugExpenditureMasterDtoList["
						// + i + "].prCollected");
						// $(this).find("input:text:eq(5)").attr("name","bugExpenditureMasterDtoList["
						// + i + "].prRevBudgetCode");

						$(this).find('.delButton').attr("id", "delButton" + i);
						$(this).find('.addButton').attr("id", "addButton" + i);
						// $(this).find('#functionId'+i).attr("onchange",
						// "clearFormData(" + (i) + ")");
						$(this).find('#prBudgetCodeid' + i).attr("onchange",
								"findduplicatecombinationexit(" + (i) + ")");
						// $(this).find('#orginalEstamt'+i).attr("onchange",
						// "getAmountFormat(" + (i) + ")");
						$(this).closest("tr").attr("id", "budExpId" + (i));
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

	// var functionId = $('#functionId'+cnt).val();

	/*
	 * if (functionId == '') { errorList.push('Please Select Function'); var
	 * prBudgetCodeid = $('#prBudgetCodeid'+cnt).val("");
	 * $("#prBudgetCodeid"+cnt).val('').trigger('chosen:updated'); }
	 */

	var id = $("#indexdata").val();

	// var functionId = $('#functionId'+id).val();
	var prBudgetCodeid = $('#prBudgetCodeid' + id).val();

	if ($('#fieldId').val() == "") {
		errorList.push("Please Select Field Id");
		var prBudgetCodeid = $('#prBudgetCodeid' + cnt).val("");
		$("#prBudgetCodeid" + cnt).val('').trigger('chosen:updated');
	}


	/*
	 * var j = $("#indexdata").val(); for(m=0; m<j;m++){
	 * 
	 * if(($('#prBudgetCodeid'+m).val() == $('#prBudgetCodeid'+j).val())){
	 * 
	 * errorList.push("The Combination AccountHead already exists!"); var
	 * prBudgetCodeid = $("#prBudgetCodeid"+j).val("");
	 * //$("#prCollected"+Expid).prop("disabled",true); } }
	 */

	var Expid = $("#indexdata").val();
	if (prBudgetCodeid != "") {
		var dec;
		for (m = 0; m <= Expid; m++) {
			for (dec = 0; dec <= Expid; dec++) {
				if (m != dec) {
					if ($('#prBudgetCodeid' + m).val() == $(
							'#prBudgetCodeid' + dec).val()) {
						errorList
								.push("The Combination AccountHead already exists!");
						$('#functionId' + cnt).val("");
						$("#functionId" + cnt).val('')
								.trigger('chosen:updated');
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
		
		var url = "AccountBudgetProjectedExpenditure.html?getBudgetExpDuplicateGridloadData&cnt="
				+ cnt;

		var returnData = __doAjaxRequest(url, 'post', requestData, false);

		if (returnData) {

			errorList
					.push(getLocalMessage("account.Budget.provision.is.already.entered.against"));
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

	// var functionId = $('#functionId0').val("");
	// $("#functionId0").val('').trigger('chosen:updated');

	$('#fieldId').val("");
	$("#fieldId").val('').trigger('chosen:updated');
	
	$('#functionId').val("");
	$("#functionId").val('').trigger('chosen:updated');
	
	var prBudgetCodeid = $('#prBudgetCodeid0').val("");
	$("#prBudgetCodeid0").val('').trigger('chosen:updated');

	var orginalEstamt = $('#orginalEstamt0').val("");
	var revisedEstamt = $('#revisedEstamt0').val("");
	var expenditureAmt = $('#expenditureAmt0').val("");
	var prBalanceAmt = $('#prBalanceAmt0').val("");
	var prExpBudgetCode = $('#prExpBudgetCode0').val("");

	$('.error-div').hide();
	var errorList = [];

	if (errorList.length == 0) {

		var divName = ".content";

		var formName = findClosestElementId(obj, 'form');

		var theForm = '#' + formName;

		var requestData = __serializeForm(theForm);

		var url = "AccountBudgetProjectedExpenditure.html?getjqGridload";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

	}
}

function loadBudgetExpenditureData(obj) {

	$('.error-div').hide();
	var errorList = [];

	// var functionId = $('#functionId0').val("");
	// $("#functionId0").val('').trigger('chosen:updated');

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

	// $('#hiddenFinYear').val($('#faYearid').val());

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

		var url = "AccountBudgetProjectedExpenditure.html?getjqGridload";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

		// $('#faYearid').prop('disabled', 'disabled');

	}
};

function loadBudgetExpenditureFieldData(obj) {

	$('.error-div').hide();
	var errorList = [];

	// var functionId = $('#functionId0').val("");
	// $("#functionId0").val('').trigger('chosen:updated');

	$('#functionId').val("");
	$("#functionId").val('').trigger('chosen:updated');
	
	var prBudgetCodeid = $('#prBudgetCodeid0').val("");
	$("#prBudgetCodeid0").val('').trigger('chosen:updated');

	var orginalEstamt = $('#orginalEstamt0').val("");
	var revisedEstamt = $('#revisedEstamt0').val("");
	var prCollected = $('#prCollected0').val("");
	var prRevBudgetCode = $('#prRevBudgetCode0').val("");

	// $('#hiddenFinYear').val($('#faYearid').val());

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

		var url = "AccountBudgetProjectedExpenditure.html?getjqGridFiledload";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

		// $('#faYearid').prop('disabled', 'disabled');

	}
};

function loadBudgetExpenditureFunctionData(obj) {

	$('.error-div').hide();
	var errorList = [];

	// var functionId = $('#functionId0').val("");
	// $("#functionId0").val('').trigger('chosen:updated');

	/*$('#fieldId').val("");
	$("#fieldId").val('').trigger('chosen:updated');*/
	
	var prBudgetCodeid = $('#prBudgetCodeid0').val("");
	$("#prBudgetCodeid0").val('').trigger('chosen:updated');

	var orginalEstamt = $('#orginalEstamt0').val("");
	var revisedEstamt = $('#revisedEstamt0').val("");
	var prCollected = $('#prCollected0').val("");
	var prRevBudgetCode = $('#prRevBudgetCode0').val("");

	// $('#hiddenFinYear').val($('#faYearid').val());

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

		var url = "AccountBudgetProjectedExpenditure.html?getjqGridFunctionload";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).removeClass('ajaxloader');
		$(divName).html(response);

		// $('#faYearid').prop('disabled', 'disabled');

	}
};

function copyContent(obj) {

	var id = $(obj).attr('id');
	var arr = id.split('orginalEstamt');
	var indx1 = arr[1];

	var OldAmount1 = $('#orginalEstamt' + indx1).val();
	$('#prBalanceAmt' + indx1).val(OldAmount1);

}

function checkTransaction(prExpenditureid, faYearid) {
	var url = "AccountBudgetProjectedExpenditure.html?checkTransactions";
	var requestData = "prExpenditureid=" + prExpenditureid + "&faYearid="
			+ faYearid;
	response = __doAjaxRequest(url, 'post', requestData, false, '');
	// alert(returnData+"response");
	return response;
}

function exportTemplate() {

	
	var $link = $(this);
	/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
	// var functionId = 1;
	var url = "AccountBudgetProjectedExpenditure.html?importExportExcelTemplateData";
	var requestData = "";
	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	$('.content').html(returnData);

	prepareDateTag();
	return false;
}
function closeOutErrorBox() {
	$('#errorDivSec').hide();
}

function setPrevBalanceAmount() {
	$( ".popUp" ).hide();
}
