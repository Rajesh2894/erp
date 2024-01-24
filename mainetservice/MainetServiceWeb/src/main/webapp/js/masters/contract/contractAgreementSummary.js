var contractUrl = 'ContractAgreement.html';

$(document).ready(
		function() {

			$('#addEstateLink').click(
					function() {
						var ajaxResponse = __doAjaxRequest(contractUrl
								+ '?form', 'POST', {}, false, 'html');
						$('.content').html(ajaxResponse);
					});
			//Defect #154688
			$('.hasNumberWithFSlash').on('keyup', function () {
			    this.value = this.value.replace(/[^0-9/]/g,'');
			  
			});

		});
$(document).ready(function() {/*
								 * 
								 * $("#contractGrid").jqGrid( { url :
								 * contractUrl+"?getGridData", datatype :
								 * "json", mtype : "POST", colNames : [
								 * '',getLocalMessage('rnl.master.contract.no'),getLocalMessage('rnl.master.contract.date'),getLocalMessage('master.department'),
								 * getLocalMessage('rnl.master.represented.by'),getLocalMessage('rnl.master.vender.name'),getLocalMessage('rnl.master.contract.from.date'),
								 * getLocalMessage('rnl.master.contract.to.date'),getLocalMessage('estate.grid.column.action')],
								 * colModel : [ {name : "contId",width : 5,
								 * hidden : true }, {name : "contNo",width :
								 * 30,align : 'center' }, {name :
								 * "contDate",width : 20,align :
								 * 'center',sortable : true}, {name :
								 * "contDept",width : 30,align :
								 * 'center',sortable : false}, {name :
								 * 'contp1Name',width : 30,align :
								 * 'center',sortable : false,search :false},
								 * {name : 'contp2Name',width : 30,align :
								 * 'center',sortable : false,search :false},
								 * {name : "contFromDate",width : 20,align :
								 * 'center',sortable : false,search :false},
								 * {name : "contToDate",width : 20,align :
								 * 'center',sortable : false,search :false},
								 * {name: 'enbll', index: 'enbll', width: 20,
								 * align: 'center
								 * !important',formatter:addLink,search :false} ],
								 * pager : "#estatePager", rowNum : 30, rowList : [
								 * 5, 10, 20, 30 ], sortname : "contId",
								 * sortorder : "desc", height : 'auto',
								 * viewrecords : true, gridview : true, loadonce :
								 * true, postData : { contracNo:function() {
								 * return $('#contractNo').val(); },
								 * contracDate:function() { return
								 * $('#contractDate').val(); },
								 * venderId:function() { return
								 * $('#vendorId').val(); }, deptId : function() {
								 * return $('#departmentId').val(); },
								 * viewClosedCon : function() { return
								 * $('#viewClosedContracts').val(); } },
								 * jsonReader : { root : "rows", page : "page",
								 * total : "total", records : "records",
								 * repeatitems : false, }, autoencode : true });
								 * 
								 * function addLink(cellvalue, options,
								 * rowObject) { return "<a class='btn
								 * btn-blue-3 btn-sm' title='View Properties'
								 * onclick=\"showContract('"+rowObject.contId+"','V')\"><i
								 * class='fa fa-eye'></i></a> "+ "<a
								 * class='btn btn-warning btn-sm' title='Edit'
								 * onclick=\"showContract('"+rowObject.contId+"','E')\"><i
								 * class='fa fa-pencil'></i></a> "; }
								 * 
								 * 
								 * $('#btnsearch').click(function(){
								 * $("#contractGrid").jqGrid('setGridParam', {
								 * datatype : 'json' }).trigger('reloadGrid');
								 * });
								 * 
								 */
});

function showContract(contId, type) {
	if (type == 'V') {
		showContractForm(contId, type);
		//$("#ContractAgreement :input,select").prop("disabled", true);
		$("#partyDetails :input").prop("disabled", true);
		$('#noa_header').show();
		$('.addCF4').attr('disabled', true);
		$('.addCF5').attr('disabled', true);
		$('.addCF2').attr('disabled', true);
		$('.remCF2').attr('disabled', true);
		$('.remCF3').attr('disabled', true);
		$('.remCF4').attr('disabled', true);
		$('.remCF5').attr('disabled', true);
		$('.uploadbtn').attr('disabled', false);
		$(".backButton").removeProp("disabled");
		// $("#partyDetails").removeProp("disabled");
	}

	if (type == 'E') {

		var requestData = 'contId=' + contId + '&type=' + type;
		var ajaxResponse = doAjaxLoading(contractUrl
				+ '?findContractMapedOrNot', requestData, 'html');
		if (ajaxResponse != '"Y"') {
			showContractForm(contId, type);
			$('#noa_header').show();
			$("#resetButton").prop("disabled", true);
			$("#AgreementDate").prop('disabled', true);
		} else {
			showContractForm(contId, 'B');
			$('#noa_header').show();
			//$("#ContractAgreement :input,select").prop("disabled", true);
			$("#partyDetails :input").prop("disabled", true);

			$('.addCF4').attr('disabled', true);
			$('.addCF5').attr('disabled', true);
			$('.addCF2').attr('disabled', true);
			$('.remCF2').attr('disabled', true);
			$('.remCF3').attr('disabled', true);
			$('.remCF4').attr('disabled', true);
			$('.remCF5').attr('disabled', true);
			$('.uploadbtn').attr('disabled', false);
			$(".backButton").removeProp("disabled");
			$(".submitData").removeProp("disabled");
			
			$('#SecurityDeposit').attr('disabled', false);
			$('#SecurityDepositReceipt').attr('disabled', false);
			$('#SecurityDepositDate').attr('disabled', false);
			$('#particular').attr('disabled', false);
			$('#SecurityDepositEndDate').attr('disabled', false);
			$("#contDepTyp").prop('disabled', false);
			$("#contDepBankID").prop('disabled', false);
			$("#contDepModeId").prop('disabled', false);
			$('#submit').attr('disabled', false);
			
			$("#resetButton").prop("disabled", true);
			$("#AgreementDate").prop('disabled', true);
		}
	}
}

function showContractForm(contId, type) {
	var requestData = 'contId=' + contId + '&type=' + type;
	var ajaxResponse = doAjaxLoading(contractUrl + '?form', requestData, 'html');
	$('.content').removeClass('ajaxloader');
	$('.content').html(ajaxResponse);
}

function showAlertBox() {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage('contract.ok');

	message += '<h4 class=\"text-center text-blue-2 padding-10\">'
			+ getLocalMessage('contract.already.maped.edit.not.allowed')
			+ '</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="closeAlertForm()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
}

function searchContract() {

	var errorList = [];

	var contractNo = $("#contractNo").val().trim();
	var contractDate = $("#contractDate").val();
	var viewClosedContracts = $("#viewClosedContracts").val();
	var deptId = $("#deptId").val();

	if (contractNo != '' || contractDate != '' || viewClosedContracts != '') {

		var requestData = {
			"contractNo" : contractNo,
			"contractDate" : contractDate,
			"viewClosedCon" : viewClosedContracts,
			"deptId" : deptId
		}

		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var prePopulate = __doAjaxRequest('ContractAgreement.html?filterData',
				'POST', requestData, false, 'json');

		// var prePopulate = JSON.parse(ajaxResponse);
		var result = [];

		$
				.each(
						prePopulate,
						function(index) {
							var obj = prePopulate[index];
							result
									.push([
											obj.contTndNo,
											obj.contLoaNo,
											obj.contNo,
											obj.contDate,
											obj.contDept,
											obj.contp2Name,
											obj.contFromDate,
											obj.contToDate,
											'<td >'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-5"  onclick="showContract(\''
													+ obj.contId
													+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
													+ '<button type="button" class="btn btn-warning btn-sm btn-sm" onclick="showContract(\''
													+ obj.contId
													+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>'
													+ '</td>' ]);
						});
		table.rows.add(result);
		table.draw();
		/*Defect #153446*/
		var tableColSelector = $('table.contractAgreementSummaryTable tbody tr td:last-child');
		if(tableColSelector.children('button')) {
			tableColSelector.addClass('text-center');
		}
		if (prePopulate.length == 0) {
			errorList
					.push(getLocalMessage("scheme.master.validation.nodatafound"));
			displayErrorsOnPage(errorList);
			$("#errorDiv").show();
		} else {
			$("#errorDiv").hide();
		}
	} else {
		errorList.push(getLocalMessage('agreement.selectAnyField'));
		displayErrorsOnPage(errorList);
	}
}

$(document).ready(function() {

	$("#datatables").dataTable({
		"order" : [ 0, 'desc' ],
		"oLanguage": { "sSearch": "" }
	});
});
