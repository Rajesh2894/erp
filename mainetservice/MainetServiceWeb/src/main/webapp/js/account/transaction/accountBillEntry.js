var bmId = '';
var removeExpIdArray=[];
var removeDedIdArray=[];
var fileArray=[];

var isMakerChecker = $("#isMakerChecker").val();
$(function() {
	
	
  var depositePaybleCode=$('#bmBilltypeCpdId option:selected').attr('code');
   if(depositePaybleCode=="DE"){
	  $("#bmInvoicevalue").attr("readonly",true);
	  $("#bmTaxableValue").attr("readonly",true);
	}
	
	
	
	$("#billEntryGrid").jqGrid(
			{
				url : "AccountBillEntry.html?getGridData",
				datatype : "json",
				mtype : "POST",
				colNames : [ '',getLocalMessage('account.bill.number'),getLocalMessage('account.bill.amount'),getLocalMessage('account.bill.deductions'),
					getLocalMessage('account.bill.netpay'),getLocalMessage('account.bankmaster.status'),getLocalMessage('advance.management.Action')],
				colModel : [ {
					name : "id",
					sortable : true,
					searchoptions : {
						"sopt" : [ "bw", "eq" ]
					},
					hidden : true
				}, {
					name : "billNo",
					sortable : false,
					align : 'center',
					searchoptions : {
						"sopt" : [ "bw", "eq", "ne", "ew", "cn", "lt", "gt" ]
					}
				}, {
					name : "billAmountStr",
					sortable : false,
					align : 'right',
					searchoptions : {
						"sopt" : [ "bw", "eq", "ne", "ew", "cn", "lt", "gt" ]
					}
				}, {
					name : "deductionsStr",
					sortable : false,
					align : 'right',
					searchoptions : {
						"sopt" : [ "bw", "eq", "ne", "ew", "cn", "lt", "gt" ]
					}
				}, {
					name : "netPayableStr",
					sortable : false,
					align : 'right',
					searchoptions : {
						"sopt" : [ "bw", "eq", "ne", "ew", "cn", "lt", "gt" ]
					}
				},
				{
					name : "authorizationStatus",
					sortable : false,
					align : 'center',
					searchoptions : {
						"sopt" : [ "bw", "eq", "ne", "ew", "cn", "lt", "gt" ]
					}
				},
				/*
				 * {name : "billDate",width : 75,sortable : false,searchoptions: {
				 * "sopt": ["bw", "eq", "ne" , "ew", "cn", "lt", "gt"] }}, {name :
				 * "vendorDesc",width : 75,sortable : false,searchoptions: {
				 * "sopt": ["bw", "eq", "ne" , "ew", "cn"] }},
				 */
				/*{
					name : 'id',
					name : 'authorizationStatus',
					index : 'bmId',
					width : 20,
					align : 'center',
					formatter : returnEditUrl,
					editoptions : {
						value : "Yes:No"
					},
					formatoptions : {
						disabled : false
					},
					search : false
				}, {
					name : 'id',
					index : 'bmId',
					width : 20,
					align : 'center',
					formatter : returnViewUrl,
					editoptions : {
						value : "Yes:No"
					},
					formatoptions : {
						disabled : false
					},
					search : false
				},*/ 
				{ name: 'id', index: 'id', width:50 , align: 'center !important',formatter:addLink,search :false},
				
				/*{
					name : 'id',
					index : 'bmId',
					width : 20,
					align : 'center',
					formatter : returnDeleteUrl,
					editoptions : {
						value : "Yes:No"
					},
					formatoptions : {
		
					},				disabled : false
					search : false
				} */],
				pager : "#pagered",
				emptyrecords: getLocalMessage("jggrid.empty.records.text"),
				rowNum : 30,
				rowList : [ 5, 10, 20, 30 ],
				sortname : "dsgid",
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
				caption : getLocalMessage("account.bills.invoices.list")
			});
	jQuery("#billEntryGrid").jqGrid('navGrid', '#pagered', {
		edit : false,
		add : false,
		del : false,
		search : true,
		refresh : false
	});
	$("#pagered_left").css("width", "");
});


$(document).ajaxComplete(function() {
	var errorList = [];
	if($("#authorizationMode").val()=='Auth'){	
		 if($("#isMakerChecker").val()!='true' || $("#isServiceActive").val()=="active")
		 {
			 $('.editClass').removeAttr("class");
			 $("#search").prop( "disabled", true);
			 $(".resetSearch").prop( "disabled", true);
			 displayErrorsPage(errorList);
		 }
	}
	});	



function returnEditUrl(cellValue, options, rowdata, action) {
	bmId = rowdata.bmId;
	
/*	var errorsList = [];
	if($("#isMakerChecker").val()!='false'){
		errorsList.push("Cannot edit, no maker checker found");
		 displayErrorsPage(errorsList);
	}*/
	return "<a href='#'  return false; class='editClass' value='"+ bmId	+ "'><img src='css/images/edit.png' width='20px' alt='Edit  Master' title='Edit  Master' /></a>";
}

function returnViewUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='viewClass'><img src='css/images/grid/view-icon.png' width='20px' alt='View  Master' title='View  Master' /></a>";
}

function returnDeleteUrl(cellValue, options, rowdata, action) {

	return "<a href='#'  return false; class='deleteClass' value='"
			+ rowdata.id
			+ "'><img src='css/images/delete.png' width='20px' alt='Delete Master' title='Delete Master' /></a>";
}



function addLink(cellvalue, options, rowdata) 
{
   return "<a class='btn btn-blue-3 btn-sm viewClass' title='View 'value='"+rowdata.id+"' id='"+rowdata.id+"' ><i class='fa fa-building-o'></i></a> " +
   		 "<a class='btn btn-warning btn-sm editClass' title='Edit'value='"+rowdata.id+"' id='"+rowdata.id+"' ><i class='fa fa-pencil'></i></a> ";
}




$(function() {
	$(document).on('click', '.editClass', function() {
		
		var errorList = [];
		var $link = $(this);
		var bmId = $link.closest('tr').find('td:eq(0)').text();
		var authStatus = $link.closest('tr').find('td:eq(5)').text();
		var url;
		if($("#authorizationMode").val()!='Auth'){
			url = "AccountBillEntry.html?formForUpdate";
		}
		else{
			url = "AccountBillAuthorization.html?formForUpdate";
		}
		var requestData = "bmId=" + bmId + "&MODE_DATA=" + "EDIT";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		if(authStatus!="Authorized"){
			$('.content').html(returnData);
		}
		else{
			errorList.push("Cannot edit, invoice/bill is already authorized");
			displayErrorsPage(errorList);
		}
		prepareDateTag();
		

	});
});

$(function() {
	$(document).on('click', '.viewClass', function() {
		var $link = $(this);
		var bmId = $link.closest('tr').find('td:eq(0)').text();
		var url;
		if($("#authorizationMode").val()!='Auth'){
			url = "AccountBillEntry.html?formForView";
		}
		else{
			url = "AccountBillAuthorization.html?formForView";
		}
		var requestData = "bmId=" + bmId + "&MODE_DATA=" + "VIEW";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		$('.content').html(returnData);
		prepareDateTag();

	});
});

$(function() {
	$(document).on('click', '.deleteClass', function() {

		var bmId = $(this).attr('value');
		showConfirmBoxForDelete(bmId);
	});

});

function showConfirmBoxForDelete(bmId) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Yes';

	message += '<h5 class=\'text-center text-blue-2 padding-5\'>'
			+ "Are you sure you want to delete?" + '</h5>';
	// message +='<p>'+getLocalMessage("Are you sure you want to
	// delete?")+'</p>';
	// message +='<p style=\'text-align:center;margin: 5px;\'>'+'<br/><input
	// type=\'button\' value=\''+cls+'\' id=\'btnNo\' class=\'css_btn \' '+ '
	// onclick="deleteData('+bmId+')"/>'+'</p>';

	message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls
			+ '\'  id=\'btnNo\' onclick="deleteData('
			+ bmId
			+ ')"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBox(errMsgDiv);
}

function deleteData(bmId) {

	var url = "AccountBillEntry.html?delete";
	var returnData = {
		"bmId" : bmId
	};
	$
			.ajax({
				url : url,
				datatype : "json",
				mtype : "POST",
				data : returnData,
				success : function(response) {

					$.fancybox.close();
					$("#billEntryGrid").jqGrid('setGridParam', {
						datatype : 'json'
					}).trigger('reloadGrid');

				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList
							.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});

	$.fancybox.close();
	$("#billEntryGrid").jqGrid('setGridParam', {
		datatype : 'json'
	}).trigger('reloadGrid');

}


$(document).on('click', '.resetSearch', function() {
	$('#bmBilltypeCpdId').val('').trigger('chosen:updated');
	$('#vmVendorname').val('').trigger('chosen:updated');
	$('#departmentId').val('').trigger('chosen:updated');
	$('#bmBillno').val('');
});	

function searchBillData() {
	
	var errorsList = [];
	errorsList = validateBillSearchForm(errorsList);
	displayErrorsPage(errorsList);
	var billNo=$("#bmBillno").val();
	if(billNo===undefined ){
		billNo="";
	}
	if(errorsList <= 0){
		var url = "AccountBillEntry.html?searchBillData";
		var returnData = {
			"fromDate" : $("#fromDate").val(),
			"toDate" : $("#toDate").val(),
			"bmBilltypeCpdId" : $("#bmBilltypeCpdId").val(),
			/*"billNo" : $("#bmBillno option:selected").attr("value"),*/
			"billNo" : billNo,
			"vendorId" : $("#vmVendorname option:selected").attr("value"),
			//"expBudgetCodeId" : $("#expenditureBudgetCode").val(),
			"departmentId" : $("#departmentId").val(),
		};
		
		
		var result = __doAjaxRequest(url, 'POST', returnData, false, 'json');
		
		if (result != null && result != '') {
			$("#billEntryGrid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
			$('#errorDivId').hide();
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("account.norecord.criteria"));
			if (errorList.length > 0) {
	
				var errMsg = '<ul>';
				$.each(errorList, function(index) {
					errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
							+ errorList[index] + '</li>';
				});
				errMsg += '</ul>';
				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				$('html,body').animate({
					scrollTop : 0
				}, 'slow');
				// return false;
			}
			$("#billEntryGrid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		}
	}
	/*
	 * $.ajax({ url : url, data : returnData, success : function(response) {
	 * $("#billEntryGrid").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid'); },
	 * error : function(xhr, ajaxOptions, thrownError) { var errorList = [];
	 * errorList.push(getLocalMessage("admin.login.internal.server.error"));
	 * showError(errorList); } });
	 */

}

function validateBillSearchForm(errorList){
	
	var fromDate = 	$("#fromDate").val();
	var toDate = $("#toDate").val();
	var bmBilltypeCpdId =	$("#bmBilltypeCpdId").val();
	var bmBillno = $("#bmBillno").val();
	var	vmVendorname = 	$("#vmVendorname").val();
	//var expenditureBudgetCode = $("#expenditureBudgetCode").val();
	var departmentId = $("#departmentId").val();
	
	
	if(fromDate=="" && toDate=="" && bmBilltypeCpdId =="" && bmBillno =="" && vmVendorname =="" && departmentId ==""){
		
		errorList.push(getLocalMessage('account.select.criteria'));
	}else
	{
		if(fromDate == '' && toDate !='') {
			errorList.push("Please Select From Date");
		}
		
		if(toDate == ''  && fromDate!='') {
			errorList.push("Please Select To Date");
		}
		  var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		  var eDate = new Date($("#fromDate").val().replace(pattern,'$3-$2-$1'));
		  var sDate = new Date($("#toDate").val().replace(pattern,'$3-$2-$1'));
		  if (eDate > sDate) {
			  errorList.push("To Date should not be less than From Date"); 
		 }
	  }
	if(fromDate !=null)
		{
		errorList = validatedate(errorList,'fromDate');
		}
	if(toDate !=null)
	{
	errorList = validatedate(errorList,'toDate');
	}
	return errorList;
	
}

$(function() {
	
	$(document).on('click', '.createData', function() {
		
		var loanFlag = $("#loanFlag").val();
		var grantFlag = $("#grantFlag").val();
		if(loanFlag == 'Y'){
			var loanDetId = $("#loanId").val();
			var loanRepaymentAmnt = $("#bmInvoicevalue").val();
			
			var url = "AccountBillEntry.html?createFormForLoan";
			var requestData = {"loanId":loanDetId, "loanPayAmount":loanRepaymentAmnt};
			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			$('.content').html(returnData);
			prepareDateTag();
			return false;
		}else if(grantFlag == 'Y'){
			var grantId = $("#grantId").val();
			var payableAmount = $("#grantPayableAmount").val();
			var url = "AccountBillEntry.html?createFormForGrantUtilization";
			var requestData = {"grantId":grantId, "payableAmount":payableAmount};
			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			$('.content').html(returnData);
			prepareDateTag();
			return false;
		}else{
			var url = "AccountBillEntry.html?formForCreate";
			var requestData = "MODE_DATA=" + "EDIT";
			var returnData = __doAjaxRequest(url, 'post', requestData, false);
			$('.content').html(returnData);
			/*
			 * errorList = [];
			var expenditureExistsFlag = $("#expenditureExistsFlag").val();
			if(expenditureExistsFlag=="N"){
				errorList.push("No expenditure data found under the current financial year");
				displayErrorsPage(errorList);
			}*/
			var bmBilltype=$("#bmBilltypeCpdId option:selected").attr("code");
			
				$("#addButton0").prop('disabled', false);
				$("#delButton0").prop('disabled', false);
			
			prepareDateTag();
			return false;
		}
		
		
	});
});



function getVendorCode() {

	var vendorId = $('#vmVendorname').val();
	var vendorName = $("#vmVendorname option:selected").text();
	if (vendorId > 0) {
		var postdata = 'vendorId=' + vendorId;

		var json = __doAjaxRequest('AccountBillEntry.html?getVendorCode',
				'POST', postdata, false, 'json');

		var vendorCodeId = '';
		var vendorCodeDesc = '';

		$.each(json, function(key, value) {

			vendorCodeId = key;
			vendorCodeDesc = value;

		});
		$('#vmVendorid').val(vendorCodeId);
		$('#vmVendorCodeDesc').val(vendorCodeDesc);
		$('#vmVendorDesc').val(vendorName);

	}
}


$( document ).ready(function() {
	var flagType = $('#advanceFlag').val();
	//D#79069
	setTaxableAmount();
	if(flagType=="Y")
		{
		$('#bchChargesAmt0').change(function() {
			
		var minAmt = ($('#balAmount').val());
		var maxAmt = ($('#bchChargesAmt0').val());
		 
		  if ((minAmt != '' && minAmt != undefined && !isNaN(minAmt)) && (maxAmt != '' && maxAmt != undefined && !isNaN(maxAmt))){
		    try{
		      maxAmt = parseFloat(maxAmt);
		      minAmt = parseFloat(minAmt);

		      if(maxAmt > minAmt) {
		        var msg = "Invoice / Bill Entry Sanctioned Amount can not be greater than Advance Balance Amount.!";
		    	  showErrormsgboxTitle(msg);
		        $('#bchChargesAmt0').val("");
		        $('#sanctionedTot').val("");
		        $('#disallowedAmtTot').val("");
		        return false;
		      }
		    }catch(e){
		      return false;
		    }
		  } //end maxAmt minAmt comparison 
		 });
		
		$('#actAmt0').change(function() {
			
			var minAmt = ($('#balAmount').val());
			var maxAmt = ($('#actAmt0').val());
			 
			  if ((minAmt != '' && minAmt != undefined && !isNaN(minAmt)) && (maxAmt != '' && maxAmt != undefined && !isNaN(maxAmt))){
			    try{
			      maxAmt = parseFloat(maxAmt);
			      minAmt = parseFloat(minAmt);

			      if(maxAmt > minAmt) {
			        var msg = "Invoice / Bill Entry Amount can not be greater than Advance Balance Amount.!";
			    	  showErrormsgboxTitle(msg);
			        $('#actAmt0').val("");
			        $('#bchChargesAmt0').val("");
			        $('#amountTot').val("");
			        return false;
			      }
			    }catch(e){
			      return false;
			    }
			  } //end maxAmt minAmt comparison 
			 });
		
		}
	
	var depflagType = $('#depositFlag').val();
	if(depflagType=="Y"){
		
		var stt = 0;
		stt = parseFloat($('#bmInvoicevalue').val());
		if((stt != undefined && !isNaN(stt))){ 
		var result = stt.toFixed(2);
		$("#actAmt0").val(result);
		$("#bchChargesAmt0").val(result);
		
		$("#amountTot").val(result);
		getAmountFormatInStatic('amountTot');
		$("#sanctionedTot").val(result);
		getAmountFormatInStatic('sanctionedTot');
		$("#disallowedAmtTot").val(0.00);
		$("#deductionsTot").val(0.00);
		$("#bmTotAmt").val(result);
		getAmountFormatInStatic('bmTotAmt');
		
		}
	}
	
	$("#dedExpenHeadField").hide();
	$("#dedExpenHeadFielddisplay").hide();
	
	if($("#loanFlag").val() == 'Y'){
		$("#bmInvoicevalue").prop("readonly", true);
	}
	var bmBilltype=$("#bmBilltypeCpdId option:selected").attr("code");
	if (bmBilltype!= undefined && (bmBilltype == 'MI' || bmBilltype == 'W' || bmBilltype == 'S')) {
		$('#bmWPOrderNumberL').addClass('required-control');
	} else {
		$('#bmWPOrderNumberL').removeClass('required-control');
	}
		
});

/*
 * var thisCount; function setSecondaryCodeForExp(count) { thisCount = count;
 * var primaryCode=$('#pacHeadId'+count).val();
 * 
 * $('#sacHeadId'+count).find('option:gt(0)').remove();
 * 
 * if (primaryCode > 0) { var postdata = 'pacHeadId=' + primaryCode;
 * 
 * var json = __doAjaxRequest('AccountBillEntry.html?sacHeadItemsList', 'POST',
 * postdata, false, 'json'); var optionsAsString='';
 * 
 * $.each( json, function( key, value ) { optionsAsString += "<option value='"
 * +key+"'>" + value + "</option>"; });
 * $('#sacHeadId'+count).append(optionsAsString );
 *  } }
 */

/*
 * function setSecondaryCodeForDeduction(count) { thisCount = count; var
 * primaryCode=$('#dedPacHeadId'+count).val();
 * 
 * $('#dedSacHeadId'+count).find('option:gt(0)').remove();
 * 
 * if (primaryCode > 0) { var postdata = 'pacHeadId=' + primaryCode;
 * 
 * var json = __doAjaxRequest('AccountBillEntry.html?sacHeadItemsList', 'POST',
 * postdata, false, 'json'); var optionsAsString='';
 * 
 * $.each( json, function( key, value ) { optionsAsString += "<option value='"
 * +key+"'>" + value + "</option>"; });
 * $('#dedSacHeadId'+count).append(optionsAsString );
 *  } }
 */



$(document).ready(function() {
	//Based on TSH prefix budget code will be enabled or disabled
	/*var transBcId = $("#transBcId").val();
	var rowCountExp = $('.expDetailTableClass tr').length;
	for (var i = 0; i < rowCountExp - 1; i++) {
		if (transBcId == "true") {
			// $( "#pacHeadId"+i).prop( "disabled", true);
		} else {
			$("#expenditureBudgetCode" + i).prop("disabled", false);
			//var errorList = [];
			//errorList.push("TSH prefix for budget code is not configured");
			//displayErrorsPage(errorList);
		}
	}*/
	var mode = $("#editMode").val();
	var disallowedRemark = $("#disallowedRemarkHidden").val();
	if(mode!="edit"){
		$('#disallowedRemarkDiv').hide();
	}
	
	if(mode=="edit"){
			var bmBilltype=$("#bmBilltypeCpdId option:selected").attr("code");
			if (bmBilltype == "ESB") {
				$('.deductionClass').each(function(i) {
				$( "#bchId"+i).prop( "disabled", true);
			});
		}if (bmBilltype == "DE") {
			$("#dedExpenHeadField").hide();
			$("#dedExpenHeadFielddisplay").hide();
		}
	}
	
});

// $(document).ready(function(){

// to generate dynamic table
$("#expenditureDetTable").on("click",'.addbtn',	function(e) {
					var errorList = [];
					//debugger;
					$('.expenditureClass')
							.each(
									function(i) {

										for (var m = 0; m < i; m++) {
											if (($('#expenditureBudgetCode' + m)
													.val() == $(
													'#expenditureBudgetCode'
															+ i).val())) {
												errorList
														.push("Budget Heads cannot be same,please select another Budget Head");
											}
										}

										var budgetCode = $(
												'#expenditureBudgetCode' + i)
												.val();
										if (budgetCode == 0 || budgetCode == "")
											errorList
													.push(getLocalMessage('account.expenditure.budgethead'));

										/*
										 * var pacHeadId =
										 * $.trim($("#pacHeadId"+i).val());
										 * if(pacHeadId==0 || pacHeadId=="")
										 * errorList.push(getLocalMessage('Please
										 * select Account Heads'));
										 */

										// To be removed in future
										/*
										 * var fundId =
										 * $.trim($("#fundId"+i).val());
										 * if(fundId==0 || fundId=="")
										 * errorList.push(getLocalMessage('Please
										 * select Fund'));
										 * 
										 * var functionId =
										 * $.trim($("#functionId"+i).val());
										 * if(functionId==0 || functionId=="")
										 * errorList.push(getLocalMessage('Please
										 * select Function'));
										 */

										var billAmount = $
												.trim($("#actAmt" + i).val());
										if (billAmount == 0 || billAmount == "")
											errorList
													.push(getLocalMessage('account.valid.billamount'));

										var sanctionedAmount = $.trim($(
												"#bchChargesAmt" + i).val());
										if (sanctionedAmount == 0
												|| sanctionedAmount == "")
											errorList
													.push(getLocalMessage('account.valid.sanctionedAmount'));

										var disallowedRemark = $(
												"#disallowedRemark" + i).val();
										var disallowedAmt = $(
												"#disallowedAmt" + i).val();

										/*
										 * var prevCount = i-1; var
										 * budgetCodePrev =
										 * $('#expenditureBudgetCode'+prevCount).val();
										 * if(budgetCode==budgetCodePrev){
										 * errorList.push(getLocalMessage('Budeget
										 * codes cannot be same')); }
										 */

										if (disallowedAmt != "") {

											if (disallowedRemark == "")
												errorList
														.push(getLocalMessage('account.valid.disallowedAmount'));
										}

									});
					if (errorList.length > 0) {

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

					var content = $(this).closest('#expDetailTable tr').clone();
					
					$(this).closest("#expDetailTable").append(content);
					 
					content.find("select").val('').attr("selected", "selected"); //Defect #164529
					content.find("input:text").val("");
					content.find("input:hidden").val("");
					content.find('div.chosen-container').remove();
					content.find("select:eq(0)").chosen().trigger("chosen:updated");
					reOrderTableIdSequence();
					e.preventDefault();
				});

// to delete row
$("#expenditureDetTable").on("click", '.delButton', function(e) {

	var rowCount = $('#expenditureDetTable tr').length;
	if (rowCount <= 2) {
		return false;
	}
	$(this).closest('#expenditureDetTable tr').remove();
	
	var deletedSodId=$(this).parent().parent().find('input[type=hidden]:first').attr('value');
	 if(deletedSodId != ''){
		 removeExpIdArray.push(deletedSodId);
		 $("#deletedExpId").val(removeExpIdArray);
	 }

	var billAmtTotal = 0;
	var sancTotal = 0;
	var disallowedAmt = 0;
	var rowCount = $('#expDetailTable tr').length;
	for (var i = 0; i < rowCount - 1; i++) {
		billAmtTotal += parseFloat($("#actAmt" + i).val());
		sancTotal += parseFloat($("#bchChargesAmt" + i).val());
		disallowedAmt += parseFloat($("#disallowedAmt" + i).val());
		if (isNaN(billAmtTotal)) {
			billAmtTotal = "";
		}
		if (isNaN(sancTotal)) {
			sancTotal = "";
		}
		if (isNaN(disallowedAmt)) {
			disallowedAmt = 0;
		}
	}
	$("#amountTot").val(billAmtTotal);
	getAmountFormatInStatic('amountTot');
	$("#sanctionedTot").val(sancTotal);
	getAmountFormatInStatic('sanctionedTot');
	$("#disallowedAmtTot").val(disallowedAmt);
	getAmountFormatInStatic('disallowedAmtTot');
	getNetPayable();
	reOrderTableIdSequence();
	e.preventDefault();
});

function reOrderTableIdSequence() {
	//debugger;
	$('.expenditureClass').each(function(i) {

						// $(this).find("select:eq(2)").attr("id","fundId"+i);
						// $(this).find("select:eq(3)").attr("id","functionId"+i);
						// $(this).find("select:eq(4)").attr("id","fieldId"+i);
						// $(this).find("select:eq(1)").attr("id","pacHeadId"+i);

						$(this).find("select:eq(0)").attr("id",	"expenditureBudgetCode" + i);
						// $(this).find("select:eq(1)").attr("id","sacHeadId"+i);

						$(this).find("input:text:eq(1)").attr("id",	"actAmt" + i);
						$(this).find("input:text:eq(2)").attr("id",	"bchChargesAmt" + i);
						//$(this).find("input:text:eq(3)").attr("id",	"disallowedAmt" + i);
						//$(this).find("input:text:eq(4)").attr("id",	"disallowedRemark" + i);

						//$(this).find("select:eq(0)").attr("onchange", "validateBudgetHead(" + i + ")");
						$(this).find("input:text:eq(1)").attr("onblur",	"enableViewBudget(" + i + ")");
						$(this).find("input:text:eq(2)").attr("onblur",	"calculateDisallowedAmt(" + i + ")");
						$(this).find(".viewExp").attr("onclick", "viewExpenditureDetails(" + i + ")");

						$(this).find(".viewExp").attr("id", "viewExpDet" + i);
						$(this).find(".addbtn").attr("id", "addButton" + i);
						$(this).find(".delButton").attr("id", "delButton" + i);

						/* $(this).find(".prExpId").attr("id", "prExpId"+i); */
						/*
						 * $(this).find(".newBalance").attr("id",
						 * "newBalance"+i);
						 */

						$(this).find("input:hidden:eq(0)").attr("id","expId" + i);
						$(this).find("input:hidden:eq(1)").attr("id","expenditureId" + i);
						$(this).find("input:hidden:eq(2)").attr("id","prExpId" + i);
						$(this).find("input:hidden:eq(3)").attr("id","newBalance" + i);
						$(this).find("input:hidden:eq(4)").attr("id","pacHeadIdHidden" + i);
						$(this).find("input:hidden:eq(5)").attr("id","disallowedAmt" + i);
						$(this).find("input:hidden:eq(6)").attr("id","budgetFlag" + i);
						$(this).find("input:hidden:eq(7)").attr("id","notEnoughBudgetFlag" + i);

						$(this).find("input:hidden:eq(0)").attr("name",	"expenditureDetailList[" + i + "].id");
						$(this).find("input:hidden:eq(2)").attr("name",	"expenditureDetailList[" + i + "].projectedExpenditureId");
						$(this).find("input:hidden:eq(3)").attr("name",	"expenditureDetailList[" + i + "].newBalanceAmount");
						$(this).find("input:hidden:eq(1)").attr("name",	"expenditureDetailList[" + i + "].expenditureId");

						// $(this).find("select:eq(2)").attr("name","expDetList["+i+"].fundId");
						// $(this).find("select:eq(3)").attr("name","expDetList["+i+"].functionId");
						// $(this).find("select:eq(4)").attr("name","expDetList["+i+"].fieldId");
						$(this).find("select:eq(1)").attr("name","expenditureDetailList[" + i + "].pacHeadId");
						$(this).find("input:hidden:eq(4)").attr("name",	"expenditureDetailList[" + i+ "].pacSacHeadId");
						$(this).find("select:eq(0)").attr("name","expenditureDetailList["+ i+ "].budgetCodeId");
						// $(this).find("select:eq(1)").attr("name","expDetList["+i+"].sacHeadId");
						$(this).find("input:text:eq(1)").attr(	"name","expenditureDetailList[" + i+ "].actualAmount");
						$(this).find("input:text:eq(2)").attr(	"name",	"expenditureDetailList[" + i+ "].billChargesAmount");
						$(this).find("input:hidden:eq(5)").attr("name","expenditureDetailList[" + i+ "].disallowedAmt");
						//$(this).find("input:text:eq(3)").attr(	"name",	"expenditureDetailList[" + i+ "].disallowedAmount");
						//$(this).find("input:text:eq(4)").attr("name",	"expenditureDetailList[" + i+ "].disallowedRemark");
						$(this).closest("tr").attr("id", "exptr" + (i));

						/*
						 * $(this).parents('tr').find('.delButton').attr("id",
						 * "delButton"+i);
						 * $(this).parents('tr').find('.addbtn').attr("id",
						 * "addButton"+i);
						 */
					});
}

// to generate dynamic table
$("#deductionDetTable")
		.on(
				"click",
				'.addDedButton',
				function(e) {

					var errorList = [];
					$('.dedDetailTableClass').each(function(i) {
										var pacHeadId = $.trim($("#dedPacHeadId" + i).val());
										if (pacHeadId == 0 || pacHeadId == "" || pacHeadId == null){
											errorList.push(getLocalMessage('account.acchead.deduction'));
										}
										
										var bmBilltype=$("#bmBilltypeCpdId option:selected").attr("code");
										if (bmBilltype == "ESB") {
										var bchId = $.trim($("#bchId" + i).val());
										if (bchId == 0 || bchId == "" || bchId == null){
											errorList.push(getLocalMessage('account.exphead.deduction'));
											}
										}
										var deductionAmt = $.trim($("#deductionAmt" + i).val());
										if (deductionAmt == 0 || deductionAmt == "" || deductionAmt == null){
											errorList.push(getLocalMessage('account.enter.deductionamt'));
										}
									});
					if (errorList.length > 0) {

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

					var content = $(this).closest('#dedDetailTable tr').clone();
					$(this).closest("#dedDetailTable").append(content);

					content.find("select").val('').attr("selected", "selected"); //Defect #164529
					content.find("input:text").val("");
					content.find('div.chosen-container').remove();
					content.find("select:eq(0)").chosen().trigger(
							"chosen:updated");
					reOrderDedTableIdSequence();
					e.preventDefault();

				});

// to delete row
$("#deductionDetTable").on("click", '.delDedButton', function(e) {
	var rowCount = $('#deductionDetTable tr').length;
	var mode = $("#editMode").val();
	if(mode =="edit"){	
		if (rowCount <= 1) {
			return false;
		}
	}else{
		if (rowCount <= 2) {
			return false;
		}
	}

	$(this).closest('#deductionDetTable tr').remove();

	var deletedDedId=$(this).parent().parent().find('input[type=hidden]:first').attr('value');
	 if(deletedDedId != ''){
		 removeDedIdArray.push(deletedDedId);
		 $("#deletedDedId").val(removeDedIdArray);
	 }
	 
	var dedAmountTotal = 0;
	var rowCount = $('#dedDetailTable tr').length;
	for (var i = 0; i < rowCount - 1; i++) {
		dedAmountTotal += parseFloat($("#deductionAmt" + i).val());
		if (isNaN(dedAmountTotal)) {
			dedAmountTotal = "";
		}
	}
	$("#deductionsTot").val(dedAmountTotal);
	getAmountFormatInStatic('deductionsTot');
	getNetPayable();
	reOrderDedTableIdSequence();
	e.preventDefault();
});

function reOrderDedTableIdSequence() {

	$('.deductionClass')
			.each(
					function(i) {
						/*
						 * $(this).find("select:eq(2)").attr("id","dedFundId"+i);
						 * $(this).find("select:eq(3)").attr("id","dedFunctionId"+i);
						 * $(this).find("select:eq(4)").attr("id","dedFieldId"+i);
						 */
						$(this).find("select:eq(0)").attr("id",
								"dedPacHeadId" + i);
						$(this).find("select:eq(1)").attr("id","bchId"+i);
						/*$(this).find("input:text:eq(1)").attr("id",
								"deductionRate" + i);*/
						$(this).find("input:text:eq(1)").attr("id",
								"deductionAmt" + i);
						$(this).find("select:eq(0)").attr("onchange","validateDedAccountHead("+i+")");
						$(this).find("select:eq(1)").attr("onchange","validateDedExpenditureAccountHead("+i+")");
						/*$(this).find("input:text:eq(1)").attr("onblur",
								"calculatePercentage(" + i + ")");*/
						$(this).find("input:text:eq(1)").attr("onblur",
								"calculateDeductionAmt(" + i + ")");
						/*
						 * $(this).find("select:eq(2)").attr("name","deductionDetailList["+i+"].dedFundId");
						 * $(this).find("select:eq(3)").attr("name","deductionDetailList["+i+"].dedFunctionId");
						 * $(this).find("select:eq(4)").attr("name","deductionDetailList["+i+"].dedFieldId");
						 */
						$(this).find("select:eq(0)").attr("name",
								"deductionDetailList[" + i + "].budgetCodeId");
						$(this).find("select:eq(1)").attr("name","deductionDetailList["+i+"].bchId");
						/*$(this).find("input:text:eq(1)").attr("name",
								"deductionDetailList[" + i + "].deductionRate");*/
						$(this).find("input:text:eq(1)").attr(
								"name",
								"deductionDetailList[" + i
										+ "].deductionAmount");

						$(this).find(".delDedButton").attr("id",
								"delDedButton" + i);
						$(this).find(".addDedButton").attr("id",
								"addDedButton" + i);

						$(this).closest("tr").attr("id", "deduction" + (i));
					});
}

/*
 * $('.expenditureClass').each(function(i) { var
 * primaryCode=$('#pacHeadId'+i).val();
 * $('#sacHeadId'+i).find('option:gt(0)').remove(); if (primaryCode > 0) { var
 * postdata = 'pacHeadId=' + primaryCode; var json =
 * __doAjaxRequest('AccountBillEntry.html?sacHeadItemsList','POST', postdata,
 * false, 'json'); var optionsAsString='';
 * 
 * $.each( json, function( key, value ) { if(key==
 * $('#selectedSecondaryValue'+i).val()) { optionsAsString += "<option value='"
 * +key+"' selected>" + value + "</option>"; } else { optionsAsString += "<option
 * value='" +key+"'>" + value + "</option>"; } });
 * $('#sacHeadId'+i).append(optionsAsString ); } });
 */
// });

function saveBillEntry(element) {
	 //debugger;
	$('#billEntryDateHiddenId').val($('#bmEntrydate').val());
	var errorList = [];
	errorList = validateForm(errorList,element);
	var depDate = $("#depositDate").val();
	var tranDate = $("#bmEntrydate").val();
	var defectLiablityDate=$("#defectLaiblityDate").val();
	if(tranDate != null && tranDate != undefined && tranDate !='' && depDate != null && depDate != undefined && depDate !=''){
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var depositDate = new Date($("#depositDate").val().replace(pattern,'$3-$2-$1'));
		var transactDate = new Date($("#bmEntrydate").val().replace(pattern,'$3-$2-$1'));
		if(depositDate > transactDate){
			errorList.push(getLocalMessage('account.transactiondate'));
		}
	}
	
	if(tranDate != null && tranDate != undefined && tranDate !='' && defectLiablityDate != null && defectLiablityDate != undefined && defectLiablityDate !=''){
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var defectLiablityDate = new Date($("#defectLaiblityDate").val().replace(pattern,'$3-$2-$1'));
		var transactDate = new Date($("#bmEntrydate").val().replace(pattern,'$3-$2-$1'));
		if(transactDate<=defectLiablityDate){
			errorList.push(getLocalMessage('account.defectliblitydate'));
		}
	}
	
	/*if(grantPayableAmount< $("#bmInvoicevalue").val())
	{
		errorList.push(getLocalMessage('Bill amount exceeded. Amount should be less than equal to '+grantPayableAmount));
	}
	*/
	if (errorList.length == 0) {
		showConfirmBoxSave();
		
	} else {
		displayErrorsPage(errorList);
	}
	// return saveOrUpdateForm(element, 'Saved Successfully',
	// 'AccountBillEntry.html', 'create');

}

function showConfirmBoxSave(){
	
	  var auth=$("#modeCheckId").val();
	  var saveorAproveMsg=getLocalMessage("account.btn.save.msg");
	    //if(auth=='Auth')
		 //saveorAproveMsg="Approve";
	 
	var	errMsgDiv		=	'.msg-dialog-box';
	var message='';
	var cls =getLocalMessage("account.btn.save.yes");
	var no=getLocalMessage("account.btn.save.no");
	
	 message	+='<h4 class=\"text-center text-blue-2\">'+ ""+saveorAproveMsg+""+ '</h4>';
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
	
	var url = "AccountBillEntry.html?create";
	//var formName = findClosestElementId(element, 'form');
	//var theForm = '#' + formName;
	//$('#expenditureBudgetCode0').prop('disabled', false).trigger("chosen:updated");
	//var requestData = __serializeForm(theForm);
	var mode = $("#editMode").val();
	var requestData = $('#frmAccountBillEntryId').serialize();
	var status = __doAjaxRequestForSave(url, 'post', requestData, false,'', '');
	var obj = $(status).find('#successfulFlag');
	var msg = $(status).find('#displayBillNo');
	var billNo=msg.val();
	if ($('.content').html(status)) {
		if (obj.val() == 'Y') {
			showConfirmBox(billNo,mode);
		}
	} else {
		$(".widget-content").html(status);
		$(".widget-content").show();
	}
}

function validateForm(errorList,element) {
	
	var discrepencyAmount = 0;
	
	/*var bmEntrydate = $("#bmEntrydate").val();
	if (bmEntrydate == "" || bmEntrydate == null) {
		errorList.push(getLocalMessage('Please enter Bill Entry Date'));
	}
	var bmEntrydate = $("#bmBilldate").val();
	if (bmEntrydate == "" || bmEntrydate == null) {
		errorList.push(getLocalMessage('Please enter Bill Date'));
	}*/
	var entryDate;
	var mode = $("#editMode").val();
	if(mode =="edit"){	
		entryDate = $('#bmEntrydate').val();
	}else{
		entryDate = $('#transactionDateId').val();
	}
	// Defect #157803
	if(entryDate== undefined && $('#bmEntrydate').val()!= undefined ){
		entryDate = $('#bmEntrydate').val();
	}
	var depositFlag = $("#depositFlag").val();
	var advanceFlag = $("#advanceFlag").val();
	var modeCheck = $("#modeCheckId").val();
	var fieldId = $("#fieldId").val();
	var departmentId = $("#departmentId").val();
	var invoiceAmount = $("#bmInvoicevalue").val();
	var grantPayableAmount = $("#grantPayableAmount").val();
	var grantFlag = $("#grantFlag").val();
/*	var fundId = $("#fundId").val();*/
	var bmTaxableValue = $("#bmTaxableValue").val();
	var bmWPOrderNumber = $("#bmWPOrderNumber").val();
	var bmInvoicenumber = $("#bmInvoicenumber").val();
	if (bmTaxableValue != "" || bmTaxableValue != null || bmTaxableValue != 0) {
		if(Number(bmTaxableValue) > invoiceAmount)
	    errorList.push(getLocalMessage('account.taxableamt'));
  }
	
	if(grantFlag != null && grantFlag !="" && grantFlag =="Y"){
		if(invoiceAmount > grantPayableAmount){
			errorList.push(getLocalMessage('account.billinvoiceamt')+' '+'Payable amount is : '+grantPayableAmount);
		}
	}
	
	if (fieldId == "" || fieldId == undefined || fieldId == null
		    || fieldId == '0') {
		errorList.push(getLocalMessage('account.field.id'));
	    }
	if (departmentId == "" || departmentId == undefined || departmentId == null
		    || departmentId == '0') {
		errorList.push(getLocalMessage('account.dept'));
	    }
	if(depositFlag != 'Y' && advanceFlag != 'Y'){
	if (modeCheck != 'Auth') {
	var transactionDateId = $("#transactionDateId").val();
	
	/*if (fundId == "" || fundId == undefined || fundId == null
		    || fundId == '0') {
		errorList.push(getLocalMessage('Please select Fund'));
	    }
	
	
	}*/
	
	/*if (bmTaxableValue != "" || bmTaxableValue != null || bmTaxableValue != 0) {
			if(bmTaxableValue > invoiceAmount)
		
		errorList.push(getLocalMessage('Taxable Amount cannot be greater than Invoice Amnount'));
	}*/
	
	if(transactionDateId!=null)
		{
		errorList = validatedate(errorList,'transactionDateId');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#transactionDateId").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
					//errorList.push("Transaction date can not be less than SLI date");
				}
			 }
		  }
		}
	if (transactionDateId == "" || transactionDateId == null) {
		errorList.push(getLocalMessage('account.select.transactiondate'));
		 }
		}else{
		var bmEntrydate = $("#bmEntrydate").val();
		if(bmEntrydate!=null)
		{
		errorList = validatedate(errorList,'bmEntrydate');
		if (errorList.length == 0) {
			var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
			if(response == "Y"){
				errorList.push("SLI Prefix is not configured");
			}else{
			var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
			var date = new Date($("#bmEntrydate").val().replace(pattern,'$3-$2-$1'));
			var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
			if (date < sliDate) {
					//errorList.push("Transaction date can not be less than SLI date");
				}
			 }
		  }
		}
		if (bmEntrydate == "" || bmEntrydate == null) {
			errorList.push(getLocalMessage('account.select.transactiondate'));
			}
		}
	}else{
	var bmEntrydate = $("#bmEntrydate").val();
	if(bmEntrydate!=null)
	{
	errorList = validatedate(errorList,'bmEntrydate');
	if (errorList.length == 0) {
		var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
		if(response == "Y"){
			errorList.push("SLI Prefix is not configured");
		}else{
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var date = new Date($("#bmEntrydate").val().replace(pattern,'$3-$2-$1'));
		var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
		if (date < sliDate) {
			//errorList.push("Transaction date can not be less than SLI date");
			}
		 }
	  }
	}
	if (bmEntrydate == "" || bmEntrydate == null) {
		errorList.push(getLocalMessage('account.select.billEntrydate'));
		}
	}
	
	var bmBilltypeCpdId = $("#bmBilltypeCpdId").val();
	if (bmBilltypeCpdId == "" || bmBilltypeCpdId == null) {
		errorList.push(getLocalMessage('account.billtype'));
	}
	
	var vmVendorname = $("#vmVendorname").val();
	if (vmVendorname == "" || vmVendorname == null) {
		errorList.push(getLocalMessage('account.vendorname'));
	}
	
	var bmInvoicevalue = $("#bmInvoicevalue").val();
	if (bmInvoicevalue == "" || bmInvoicevalue == null || bmInvoicevalue == 0) {
		errorList.push(getLocalMessage('account.enter.billinvoiceamt'));
	}

	var actualAmount = $('#actAmt0').val();
	var sanctionedAmount = $('#bchChargesAmt0').val();

	if (parseFloat(sanctionedAmount) < parseFloat(actualAmount)) {
		
		var disallowedRemark = $('#disallowedRemark').val();
		
		if(disallowedRemark == "" || disallowedRemark == null){
			//errorList.push(getLocalMessage('Please enter Disallowed Remark'));
		}
	}
	
	var rowCountExp = $('.expDetailTableClass tr').length;
	for (var i = 0; i < rowCountExp - 1; i++) {

		var budgetCode = $('#expenditureBudgetCode' + i).val();
	
		if (budgetCode == "" || budgetCode == null) {
			errorList.push(getLocalMessage('account.select.exphead'));
		}
		/*
		 * var pacHeadId $('#pacHeadId' + i).val(); if(pacHeadId=="" ||
		 * pacHeadId==null){ errorList.push(getLocalMessage('Please select
		 * Account Heads')); }
		 */
		var actAmt = $("#actAmt" + i).val();
		if (actAmt == "" || actAmt == null || actAmt == 0) {
			errorList
					.push(getLocalMessage('account.select.amount.expDetails'));
		}

		var bchChargesAmt = $("#bchChargesAmt" + i).val();
		if (bchChargesAmt == "" || bchChargesAmt == null || bchChargesAmt == 0) {
			errorList.push(getLocalMessage('account.sanctionedAmount'));
		}

		/*var disallowedAmt = $("#disallowedAmt" + i).val();
		var disallowedRemark = $("#disallowedRemark" + i).val();

		if (disallowedAmt != "") {

			if (disallowedRemark == "")
				errorList.push(getLocalMessage('Please enter the reason for disallowed amount'));
		}*/

		var billAmount = $('#actAmt' + i).val();
		var invoiceValue = $('#bmInvoicevalue').val();
		var amountTot = $("#amountTot").val();
		
		if(i == (rowCountExp-2)){
		
		if (parseFloat(billAmount) > parseFloat(invoiceValue)) {
			errorList
					.push(getLocalMessage('account.billamount.greater.invoiceamt'));
		}
		if (parseFloat(amountTot) > parseFloat(invoiceValue)) {
			errorList
					.push(getLocalMessage('account.greater.not.totalamt'));
		}
		}
		var bmBilltype=$("#bmBilltypeCpdId option:selected").attr("code");
		if ((date== undefined || sliDate==undefined || date > sliDate) && (bmBilltype!="AD" && bmBilltype!="ESB")) {
		checkBudgetHeadAmountExistOrNot(i,budgetCode,bchChargesAmt,entryDate,errorList);
		}
	}

	$('.expenditureClass')
			.each(
					function(i) {
						for (var m = 0; m < i; m++) {
							if (($('#expenditureBudgetCode' + m).val() == $(
									'#expenditureBudgetCode' + i).val())) {
								errorList
										.push("Budget Heads cannot be same,please select another Budget Head");
								// var prRevBudgetCode =
								// $("#expenditureBudgetCode"+i).val("");
								// $("#prCollected"+i).prop("disabled",true);
							}
						}
					});

		$('.deductionClass').each(function(i) {
		
						for (var m = 0; m < i; m++) {
							if (($('#dedPacHeadId' + m).val() == $(
									'#dedPacHeadId' + i).val()) && ($('#bchId' + m).val() == $(
											'#bchId' + i).val())) {
								errorList
										.push("Deduction and Expenditure head cannot be same,please select another Deduction Expenditure Head");
								//$("#bchId"+i).val("");
							}
						}
						/*if ($('#deductionRate'+i).val()!= ""){
							
							if ($('#dedPacHeadId'+i).val()==""){
								errorList.push("Please select Deduction Budget Heads");
							}
						}*/
						
						if ($('#dedPacHeadId'+i).val()!=""){
						var deductionRate = $.trim($("#deductionRate" + i).val());
							if (deductionRate == 0 || deductionRate == ""){
								//errorList.push(getLocalMessage('Please enter the deduction rate'));
							}
						}
		});
	
	var countE = 0;
	var countA = 0;
	var countD = 0;
	var formType = $("#formType").val();
	$('.deductionClass').each(function(i) {
		for (var m = 0; m <= i; m++) {
			if (($('#dedPacHeadId' + m).val() != null) && ($('#dedPacHeadId' + m).val() != "") && ($('#dedPacHeadId' + m).val() != 0)) {
				
				var bmBilltype=$("#bmBilltypeCpdId option:selected").attr("code");
				if (bmBilltype != "ESB") {
					if(formType == 'update'){
						if ($('#bchId' + m).val() == null || $('#bchId' + m).val() == "" || $('#bchId' + m).val() == 0) {
							countE++;
							//errorList.push("Please select deduction expenditure head");
							}
					}
					}
				if ($('#deductionAmt' + m).val() == null || $('#deductionAmt' + m).val() == "" || $('#deductionAmt' + m).val() == 0) {
					countA++;
					//errorList.push("Please enter deduction amount.");
				}
			}
		}
		
	});
	
	var bmBilltype=$("#bmBilltypeCpdId option:selected").attr("code");
	if (bmBilltype != "ESB") {
	$('.deductionClass').each(function(i) {
	for (var m = 0; m <= i; m++) {
		if (($('#bchId' + m).val() != null) && ($('#bchId' + m).val() != "") && ($('#bchId' + m).val() != 0)) {
			
			if ($('#dedPacHeadId' + m).val() == null || $('#dedPacHeadId' + m).val() == "" || $('#dedPacHeadId' + m).val() == 0) {
				countD++;
				//errorList.push("Please select deduction expenditure head");
			}else{
				if ($('#deductionAmt' + m).val() == null || $('#deductionAmt' + m).val() == "" || $('#deductionAmt' + m).val() == 0) {
					countA++;
					//errorList.push("Please enter deduction amount.");
				}
			}
		  }
	    }
	  });
	}
	if (bmBilltype == "MI" || bmBilltype == "W" || bmBilltype == "S"  ) {
		if (bmWPOrderNumber == "" || bmWPOrderNumber == undefined || bmWPOrderNumber == null
			    || bmWPOrderNumber == '0') {
			errorList.push(getLocalMessage("account.validation.enter.wo.po.proposal.no"));
		    }
	}
	if (bmInvoicenumber == "" || bmInvoicenumber == undefined || bmInvoicenumber == null
		    || bmInvoicenumber == '0') {
		errorList.push(getLocalMessage("account.validation.enter.invoice.bill.no"));
	    }
	$('.deductionClass').each(function(i) {
		for (var m = 0; m <= i; m++) {
			if (($('#deductionAmt' + m).val() != null) && ($('#deductionAmt' + m).val() != "") && ($('#deductionAmt' + m).val() != 0)) {
			
				var bmBilltype=$("#bmBilltypeCpdId option:selected").attr("code");
				if (bmBilltype != "ESB") {
					if(formType == 'update'){
				if ($('#bchId' + m).val() == null || $('#bchId' + m).val() == "" || $('#bchId' + m).val() == 0) {
				countE++;
				//errorList.push("Please select deduction expenditure head");
					}
					}
				}
				if ($('#dedPacHeadId' + m).val() == null || $('#dedPacHeadId' + m).val() == "" || $('#dedPacHeadId' + m).val() == 0) {
				countD++;	
				//errorList.push("Please enter deduction amount.");
				}
			}
		}
	
	});

	if(countE != 0){
		errorList.push("Please select deduction expenditure head.");
	}
	if(countA != 0){
		errorList.push("Please enter deduction amount.");
	}
	/*if(countD != 0){
		errorList.push("Please select deduction head.");
	}*/
	
	var rowCountDed = $('.dedDetailTableClass tr').length;
	for (var i = 0; i < rowCountDed - 1; i++) {
		// Validation for deductions
	}
	
	var notEnoughBudgetFlag = $("#notEnoughBudgetFlag").val();
	var flag="";
	$('.expenditureClass').each(function(i) {
		if($("#notEnoughBudgetFlag"+i).val() == "true"){
			flag = "true";
		}
	});
	
	if(flag == "true"){
		errorList.push(getLocalMessage("account.validamount"));
	}
	
	//var budgetFlag = $("#budgetFlag").val();
	/*var flag="";
	$('.expenditureClass').each(function(i) {
		if($("#budgetFlag"+i).val() != "true"){
			flag = "true";
		}
	});
	if(flag == "true"){
		errorList.push(getLocalMessage("One or more budgets are not viewed,please view budgets to reflect the expenditures"));
	}*/
	
	var disallowedAmtTot = $("#disallowedAmtTot").val();
	var disallowedRemark = $("#disallowedRemark").val();
	var disallowedRemarkUpd = $("#disallowedRemarkUpd").val();
	var modeCheck = $("#modeCheckId").val();
	
	if (modeCheck == 'Auth') {
		var authorizationDateId = $("#authorizationDateId").val();
		if (authorizationDateId == undefined || authorizationDateId == ""){
			errorList.push(getLocalMessage('account.authdate'));
		}
	}
	
	if (modeCheck != 'Auth') {
		
		if (disallowedAmtTot != undefined 
				&& disallowedAmtTot !='0.00'
				&& disallowedAmtTot != "0" && disallowedAmtTot != "") {
			
			var mode = $("#editMode").val();
			if(mode =="edit"){		
				if (disallowedRemarkUpd == undefined || disallowedRemarkUpd == ""){
					errorList.push(getLocalMessage('account.remark.disallowedAmount'));
				}
			}else{
				if (disallowedRemark == undefined || disallowedRemark == ""){
					errorList.push(getLocalMessage('account.remark.disallowedAmount'));
				}
			}		
		}
	}
	
	/*var mode = $("#editMode").val();
	if(mode=="edit"){
		var disallowedRemarkUpd = $("#disallowedRemarkUpd").val();
		if (disallowedAmtTot != "" && disallowedAmtTot != "0") {
			if (disallowedRemarkUpd == "")
				errorList.push(getLocalMessage('Please enter the reason for disallowed amount'));
		}
	}*/
	
	var bmNarration = $("#bmNarration").val();
	if (bmNarration == "" || bmNarration == null || bmNarration == 0) {
		errorList.push(getLocalMessage('account.narration'));
	}
	
	
	var netPayable = $('#bmTotAmt').val();
	if(parseFloat(netPayable) < 0 || netPayable==""){
		//errorList.push("Net payable amount cannot be negative or blank");
		//errorList.push(getLocalMessage('Discrepency found in bill entry details.'));
		discrepencyAmount++;
	}
	var authorizationMode = $("#authorizationMode").val();
	if(authorizationMode == "Auth"){
		
		var  checkerRemarks = $('#checkerRemarks').val();
		
		if(!$("input[name='checkerAuthorization']:checked").val()){
			errorList.push("Please select final decision");
		}
		
		if(checkerRemarks == ""){
			errorList.push("Please enter remarks");
		}
	}
	
	var templateExistFlag = $("#templateExistFlag").val();
	if(templateExistFlag=="N"){
		errorList.push(getLocalMessage('account.valid.voucher'));
	}
	
	var amountTot = $("#amountTot").val();
	var disAllowedTot = $("#disallowedAmtTot").val();
	var deductionTot = $("#deductionsTot").val();
	var billPaybleAmt = parseFloat(amountTot) - (parseFloat(disAllowedTot) + parseFloat(deductionTot));
	var netPayableAmt = $("#bmTotAmt").val();
	if(parseFloat(netPayableAmt) != parseFloat(billPaybleAmt)){
		//errorList.push(getLocalMessage('Discrepency found in bill summary.'));
		discrepencyAmount++;
	}
	
	var modeE = $("#editMode").val();
	if(modeE !="edit"){
		
		var url = "AccountBillEntry.html?checkExpHeadDedExpheadExists";
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(url, 'post', requestData, false);
		if(response){
			errorList.push(getLocalMessage('account.select.proper.deduction.exphead'));
		}
	}
	verifyBillEntryAmountDetails(errorList, discrepencyAmount);
	
	return errorList;
}

function verifyBillEntryAmountDetails(errorList, discrepencyAmount){
	var amountTot = $("#amountTot").val();
	var sanctionedTot = $("#sanctionedTot").val();
	var disAllowedTot = $("#disallowedAmtTot").val();
	var deductionTot = $("#deductionsTot").val();
	var netPayableAmt = $("#bmTotAmt").val();
	
	var sumOfDedctionAmt = 0;
	/*$('.deductionClass').each(function(i) {
		
		for (var m = 0; m <= i; m++) {
			if ($('#deductionAmt' + m).val() != null && $('#deductionAmt' + m).val() != 0 && $('#deductionAmt' + m).val() != "") {
				alert($('#deductionAmt' + m).val());
				sumOfDedctionAmt += parseFloat($('#deductionAmt' + m).val());
			}
		}
	});*/
	
	var rowCount = $('#dedDetailTable tr').length;
	for (var i = 0; i < rowCount - 1; i++) {
		sumOfDedctionAmt += parseFloat($('#deductionAmt' + i).val());
	}
	
	if((sumOfDedctionAmt != null) && (sumOfDedctionAmt != "") && (!isNaN(sumOfDedctionAmt)) && (sumOfDedctionAmt != undefined) ){
		if(parseFloat(sumOfDedctionAmt.toFixed(2)) != parseFloat(deductionTot)){
			//errorList.push(getLocalMessage('Discrepency found in bill summary.'));
			//discrepencyAmount++;
		}
	}
	
	var billPaybleAmt = (parseFloat(sanctionedTot) + parseFloat(disAllowedTot));
	if((parseFloat(amountTot) != parseFloat(billPaybleAmt))){
		//errorList.push(getLocalMessage('Discrepency found in bill summary.'));
		//discrepencyAmount++;
	}
	
	var billNetPaybleAmt = (parseFloat(sanctionedTot) - parseFloat(deductionTot)); 
	if((parseFloat(netPayableAmt) != parseFloat(billNetPaybleAmt))){
		//errorList.push(getLocalMessage('Discrepency found in bill summary.'));	
		//discrepencyAmount++;
	}
	if(discrepencyAmount != 0){
		errorList.push(getLocalMessage('account.discrepancy.billentry'));
	}
}

function displayErrorsPage(errorList) {

	if (errorList.length > 0) {

		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}

}

function showConfirmBox(msg,mode) {
	//debugger;
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = 'Proceed';
	var authorizationMode = $("#authorizationMode").val();
	var decisionFlag=$("input:radio[name='checkerAuthorization']").filter(":checked").val();
	var flagType = $('#advanceFlag').val();
	var depflagType = $('#depositFlag').val();
	if(flagType=="Y"){
		message += '<h5 class=\'text-center text-blue-2 padding-5\'>'+"Bill No: "+ msg +" has been Saved Successfully" + '</h5>';
	    message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+ cls + '\'  id=\'btnNo\' onclick="proceedAdv()"/></div>';
    }else if(depflagType=="Y"){
		message += '<h5 class=\'text-center text-blue-2 padding-5\'>'+"Bill No: "+msg+" has been Saved Successfully" + '</h5>';
	    message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+ cls + '\'  id=\'btnNo\' onclick="proceedDep()"/></div>';
    }else {
	if(authorizationMode != "Auth"){
		if(mode =="edit"){	
			message += '<h5 class=\'text-center text-blue-2 padding-5\'>'+"Bill No: "+msg+" has been Updated Successfully" + '</h5>';
			message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';
		}else{
		message += '<h5 class=\'text-center text-blue-2 padding-5\'>'+"Bill No: "+msg+" has been Saved Successfully" + '</h5>';
		message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';
		}
	}
	else{
	    if(decisionFlag=='Y'){	
		message += '<h5 class=\'text-center text-blue-2 padding-5\'>'+"Bill No: "+msg+" Authorized Successfully" + '</h5>';
		message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+ cls + '\'  id=\'btnNo\' onclick="proceedAuth()"/></div>';
	     }
	    if(decisionFlag=='R'){	
		message += '<h5 class=\'text-center text-blue-2 padding-5\'>'+"Bill No: "+msg+" Send Back Successfully" + '</h5>';
		message += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''+ cls + '\'  id=\'btnNo\' onclick="proceedAuth()"/></div>';
		     }
	    }
	}
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	//showModalBox(errMsgDiv);
	showModalBoxWithoutClose(errMsgDiv);
}

function proceed() {
	var theForm = '#frmAccountBillEntryId';
	var requestData = __serializeForm(theForm);
	var url = "AccountBillEntry.html?accountBillEntryFormPrint"
	var returnData = __doAjaxRequest(url, 'post', requestData, false);
	$(errMsgDiv).show('false');
	   $.fancybox.close();
		$('.widget').html(returnData);
}	
	
	


function proceedAuth(){
	
	window.location.href = 'AdminHome.html';
	$.fancybox.close();
	
	/*var theForm = '#frmAccountBillEntryId';
	var requestData = __serializeForm(theForm);
	var url = "AccountBillEntry.html?accountBillEntryFormPrint"
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
	
	$(errMsgDiv).show('false');
	   $.fancybox.close();
		$('.widget').html(returnData);*/
	//window.location.href = 'AccountBillAuthorization.html';
}

function proceedAdv() {
	//	alert("Hi")
	
	var theForm = '#frmAccountBillEntryId';
	var requestData = __serializeForm(theForm);
	var url = "AccountBillEntry.html?accountBillEntryFormPrint"
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
	
	$(errMsgDiv).show('false');
	   $.fancybox.close();
		$('.widget').html(returnData);
	
		//window.location.href = "AdvanceEntry.html";
}

function proceedDep() {
	//	alert("Hi")
	var theForm = '#frmAccountBillEntryId';
	var requestData = __serializeForm(theForm);
	var url = "AccountBillEntry.html?accountBillEntryFormPrint"
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
	
	$(errMsgDiv).show('false');
	   $.fancybox.close();
		$('.widget').html(returnData);
	
	
		//window.location.href = "AccountDeposit.html";
}

function enableViewBudget(count) {
 //debugger;
	var billAmount = parseFloat($('#actAmt' + count).val()).toFixed(2);
	var invoiceValue = parseFloat($('#bmInvoicevalue').val()).toFixed(2);
	var amountTot = parseFloat($("#amountTot").val()).toFixed(2);
	var billAmtTotal = 0;
	var response =__doAjaxRequest('AccountReceiptEntry.html?ActualSLIDate', 'GET', {}, false,'json');
	var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
	var date = new Date($("#transactionDateId").val().replace(pattern,'$3-$2-$1'));
	var sliDate = new Date(response.replace(pattern,'$3-$2-$1'));
	var errorList = [];

	if (billAmount > 0) {
		$('#bchChargesAmt' + count).val(billAmount);
		$('#viewExpDet' + count).prop("disabled", false);
	} else {
		$('#viewExpDet' + count).prop("disabled", true);
	}
	if(date < sliDate){
		$('#viewExpDet' + count).prop("disabled", true);
	}

	var rowCount = $('#expDetailTable tr').length;
	for (var i = 0; i < rowCount - 1; i++) {

		billAmtTotal += parseFloat($("#actAmt" + i).val());
		if (isNaN(billAmtTotal)) {
			billAmtTotal = "";
			//errorList.push(getLocalMessage('Please enter Amount'));
		}
	}
	var finalBillAmtTotal = parseFloat(billAmtTotal).toFixed(2);
	$("#amountTotHidden").val(finalBillAmtTotal);
	$("#amountTot").val(finalBillAmtTotal);
	$("#sanctionedTot").val(finalBillAmtTotal);//saction total  amount
	getAmountFormatInStatic('amountTot');
	getNetPayable();

	if (parseFloat(billAmount) > parseFloat(invoiceValue)) {
		errorList
				.push(getLocalMessage('account.billamount.greater.invoiceamt'));
	}
	if (parseFloat(amountTot) > parseFloat(invoiceValue)) {
		errorList
				.push(getLocalMessage('account.greater.not.totalamt'));
	}
	if (errorList.length > 0) {

		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});

		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	} else {
		$('#errorDivId').hide();
	}
}

function getExpenditureDetails2(obj) {

	var billEntryDate = $("#bmEntrydate").val();
	var deptId = $("#dpDeptid").val();
	var modeType = $("#modeType").val();
	var errorList = [];

	if (deptId == "") {
		errorList.push(getLocalMessage('account.dept'));
	}
	if (errorList.length > 0) {

		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});

		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}

	if (billEntryDate != "" && modeType !='update') {

		// var postData = 'billEntryDate='+ billEntryDate;
		// var url = "AccountBillEntry.html?getExpenditureDetails";
		// var response=__doAjaxRequest(url,'post',postData,false);
		var divName = ".content";
		// var formName = findClosestElementId(obj, 'form');
		// var theForm = '#' + formName;
		var requestData = $('#frmAccountBillEntryId').serialize();

		var url = "AccountBillEntry.html?getExpenditureDetails";

		var response = __doAjaxRequest(url, 'post', requestData, false);

		$(divName).html(response);
	}
}

	function expenditureDetailsViewForViewMode(count) {

		
		// var departmentId = $('#dpDeptid').val();
		var sacHeadId = $('#expenditureBudgetCode' + count).val();
		var entryDate = $('#transactionDateId').val();
		var sanctionedAmount = $('#bchChargesAmt' + count).val();
		var errorList = [];
		// $('.expenditureClass').each(function(i) {
		if (sacHeadId == "")
			errorList.push(getLocalMessage('account.select.exphead'));

		if(entryDate === undefined){
			entryDate = $('#bmEntrydate').val();
		}
		
		if (sanctionedAmount == 0 || sanctionedAmount == "")
			errorList.push(getLocalMessage('account.sanctionedAmount'));

		$('.expenditureClass').each(function(i) {

							for (var m = 0; m < i; m++) {
								if (($('#expenditureBudgetCode' + m).val() == $(
										'#expenditureBudgetCode' + i).val())) {
									errorList.push("Budget Heads cannot be same,please select another Budget Head");
								}
							}

						});

		if (errorList.length > 0) {

			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index] + '</li>';
			});

			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
			return false;
		}

		// });
		else {

			var postData = 'sacHeadId=' + sacHeadId + '&entryDate='
					+ entryDate + '&bchChargesAmt=' + parseFloat(sanctionedAmount) + '&count='
					+ count;
			var url = "AccountBillEntry.html?viewExpDetailsInViewMode";

			var response = __doAjaxRequest(url, 'post', postData, false);
			
			var errorMsg = $(response).find('#errorMsg').val();
			if (errorMsg != undefined && errorMsg != '') {
				
				var errorList = [];
				errorList.push(errorMsg);
				var errMsg = '<ul>';
				$.each(errorList, function(index) {
					errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
							+ errorList[index] + '</li>';
				});

				errMsg += '</ul>';

				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				$('html,body').animate({
					scrollTop : 0
				}, 'slow');
				
			} else {
				//var divName = '.child-popup-dialog';
				var divName = '.popUp';
				$(divName).removeClass('ajaxloader');
				$(divName).html(response);
				$('#expActAmt0').val(sanctionedAmount);
				// prepareTags();
				//$(".popUp").append('#expDetailTable tbody tr td:nth-child(10)');
				//showModalBox(divName);
				$(".popUp").show();
				$(".popUp").draggable();
			}
		}
	}

function viewExpenditureDetails(count) {

	
	// var departmentId = $('#dpDeptid').val();
	var sacHeadId = $('#expenditureBudgetCode' + count).val();
	var entryDate = $('#transactionDateId').val();
	var sanctionedAmount = $('#bchChargesAmt' + count).val();
	var deptId=$("#departmentId").val();
	var fieldId=$("#fieldId").val();
	var errorList = [];
	// $('.expenditureClass').each(function(i) {
	if (sacHeadId == "")
		errorList.push(getLocalMessage('account.select.exphead'));
	
	if (fieldId == "")
		errorList.push(getLocalMessage('Select Field Id'));

	if(entryDate === undefined){
		entryDate = $('#bmEntrydate').val();
	}
	
	if (sanctionedAmount == 0 || sanctionedAmount == "")
		errorList.push(getLocalMessage('account.valid.sanctionedAmount'));

	$('.expenditureClass').each(function(i) {

						for (var m = 0; m < i; m++) {
							if (($('#expenditureBudgetCode' + m).val() == $(
									'#expenditureBudgetCode' + i).val())) {
								errorList.push("Budget Heads cannot be same,please select another Budget Head");
							}
						}

					});

	if (errorList.length > 0) {

		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});

		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}

	// });
	else {

		var postData = 'sacHeadId=' + sacHeadId + '&entryDate='
				+ entryDate + '&bchChargesAmt=' + parseFloat(sanctionedAmount) + '&count='
				+ count +'&deptId='+deptId+'&fieldId='+fieldId;
		var url = "AccountBillEntry.html?viewExpDetails";

		var response = __doAjaxRequest(url, 'post', postData, false);
		
		var errorMsg = $(response).find('#errorMsg').val();
		if (errorMsg != undefined && errorMsg != '') {
			
			var errorList = [];
			errorList.push(errorMsg);
			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index] + '</li>';
			});

			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
			
		} else {
			//var divName = '.child-popup-dialog';
			var divName = '.popUp';
			$(divName).removeClass('ajaxloader');
			$(divName).html(response);
			$('#expActAmt0').val(sanctionedAmount);
			// prepareTags();
			//$(".popUp").append('#expDetailTable tbody tr td:nth-child(10)');
			//showModalBox(divName);
			$(".popUp").show();
			$(".popUp").draggable();
		}
	}
}

function setPrevBalanceAmount(prExpId, amount, count) {

	$("#prExpId" + count).val(prExpId);
	$("#newBalance" + count).val(amount);
	$( ".popUp" ).hide();
	//$("#notEnoughBudgetFlag").val("false");
	$("#budgetFlag"+count).val("true");
}


function closePopup(){
	$( ".popUp" ).hide();
	//$("#notEnoughBudgetFlag").val("true");
	
}

function calculateDisallowedAmt(count) {
	
	//debugger
	var actualAmount = $('#actAmt' + count).val();
	var sanctionedAmount = $('#bchChargesAmt' + count).val();
	
	var budgetCodeId = $('#expenditureBudgetCode' + count).val();
	
	var entryDate;
	var mode = $("#editMode").val();
	if(mode =="edit"){	
		entryDate = $('#bmEntrydate').val();
	}else{
		entryDate = $('#transactionDateId').val();
	}
	//var entryDate = $('#transactionDateId').val();
	//var sanctionedAmount = $('#bchChargesAmt' + count).val();
	
	var disallowedAmount;
	var errorList = [];

	//var amount = $('#actAmt'+count).val();
	//var sanctionedAmt = $('#bchChargesAmt'+count).val();
	
	 var amunt=0; 
	    var sactionAmt = 0;
	    
	    var rowCount = $('#expDetailTable tr').length;
		for (var m = 0; m < rowCount - 1; m++) {
			
			var actAmt = $("#actAmt"+m).val();
			var collectedAmt = $("#bchChargesAmt"+m).val();
			
			if ((actAmt != "" && actAmt != undefined && !isNaN(actAmt)) && (collectedAmt != "" && collectedAmt != undefined && !isNaN(collectedAmt))) {
				sactionAmt += parseFloat($("#bchChargesAmt"+m).val());
				amunt += parseFloat($("#actAmt"+m).val());
			}
		}
		var amount = amunt.toFixed(2); 
		var sanctionedAmt = sactionAmt.toFixed(2); 
		
	if (!isNaN(sanctionedAmt) && sanctionedAmt == 0) {
		$('#bchChargesAmt'+count).val("");
		$("#disallowedAmtTot").val("");
		$("#sanctionedTot").val("");
		$("#bmTotAmt").val("");
		var errorList = [];
		errorList.push('Sanctioned Amount cannot be zero(0).');
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});

		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	} else {
		if (!isNaN(amount)) {
			
			var disallowedAmt = amount - sanctionedAmt;
			$("#disallowedAmtTot").val(disallowedAmt.toFixed(2));
			var deductToatlAmount = parseFloat(0.00);
			var result = deductToatlAmount.toFixed(2);
			$("#deductionsTot").val(result);
			getAmountFormatInStatic('disallowedAmtTot');
		}
	}
	
	// var billAmtTotal=0;
	var sancTotal = 0;
	var disallowedAmt = 0;
	var rowCount = $('#expDetailTable tr').length;

	for (var i = 0; i < rowCount - 1; i++) {

		// billAmtTotal += parseFloat($("#actAmt" + i).val());
		sancTotal += parseFloat($("#bchChargesAmt" + i).val());
		disallowedAmt += parseFloat($("#disallowedAmt" + i).val());
		if (isNaN(disallowedAmt)) {
			disallowedAmt = 0;
		}

		if (isNaN(sancTotal)) {
			sancTotal = "";
		}
	}
	
	// $("#amountTot").val(billAmtTotal);
	var finalSacnToatalAmt = parseFloat(sancTotal).toFixed(2);
	$("#sanctionedTot").val(finalSacnToatalAmt);
	getAmountFormatInStatic('sanctionedTot');
	$("#sanctionedTotHidden").val(finalSacnToatalAmt);
	//$("#disallowedAmtTot").val(disallowedAmt);
	
	var totalDeductions = 0;
	var rowCount = $('#dedDetailTable tr').length;

	for (var i = 0; i < rowCount - 1; i++) {

		totalDeductions += parseFloat($("#deductionAmt" + i).val());
		if (isNaN(totalDeductions)) {
			totalDeductions = 0;
		}
	}
	$("#deductionsTot").val(totalDeductions.toFixed(2));
	getAmountFormatInStatic('deductionsTot');
	
	getNetPayable();

	checkBudgetHeadAmountExists(count,budgetCodeId,sanctionedAmount,entryDate);
	
	/*var postData = 'budgetCodeId=' + budgetCodeId + '&entryDate='
	+ entryDate + '&bchChargesAmt=' + sanctionedAmount + '&count='+ count;
	var url = "AccountBillEntry.html?getExpDetails";

	var response = __doAjaxRequest(url, 'post', postData, false);
	
	
	$("#prExpId" + count).val(response[0].projectedExpenditureId);
	$("#newBalance" + count).val(response[0].newBalanceAmount);
	*/
	
	/*if(response[0].hasError == "true"){
		
		errorList.push(getLocalMessage('Sanctioned amount exceeds budget amount, please enter valid amount'));
		$("#notEnoughBudgetFlag"+count).val("true");
		
		if (errorList.length > 0) {

			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index] + '</li>';
			});

			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
			return false;
		}
	}
	else{
		$("#notEnoughBudgetFlag"+count).val("false");
		$('#errorDivId').hide();
	}*/
	var mode = $("#editMode").val();

	var totalActualAmt = 0;
	var totalSanctionAmt = 0;
	var rowCount = $('#expDetailTable tr').length;
	for (var i = 0; i < rowCount - 1; i++) {
		// billAmtTotal += parseFloat($("#actAmt" + i).val());
		totalActualAmt += parseFloat($("#actAmt" + i).val());
		totalSanctionAmt += parseFloat($("#bchChargesAmt" + i).val());
	}
	
	if (parseFloat(totalSanctionAmt) < parseFloat(totalActualAmt)) {
		
		disallowedAmount = actualAmount - sanctionedAmount;
		$('#disallowedAmt' + count).val(disallowedAmount);
		$('#disallowedRemark').prop("disabled", false);
		$('#disallowedRemarkDiv').show();
		if(mode=="edit"){
			$('#disallowedRemarkUpd').prop("disabled", false);
			$('#disallowedRemarkDivUpd').show();
		}
		
	} else if (parseFloat(totalSanctionAmt) == parseFloat(totalActualAmt)) {

		$('#disallowedAmt' + count).val("");
		$('#disallowedRemark').val("");
		$('#disallowedAmt' + count).prop("readonly", true);
		$('#disallowedRemark').prop("disabled", true);
		
		if(mode=="edit"){
			$('#disallowedRemarkUpd').prop("disabled", true);
			$('#disallowedRemarkDivUpd').hide();
			$('#disallowedRemarkUpd').val("");
			$('#disallowedRemarkHiddenUpd').val("");
		}
		$('#disallowedRemarkDiv').hide();
		$('#disallowedRemark').val("");
		$('#disallowedRemarkHidden').val("");
	}

		if (parseFloat(sanctionedAmount) > parseFloat(actualAmount)) {
			errorList.push(getLocalMessage('account.sanctionedAmount.greater.billamt'));
		}
		if (errorList.length > 0) {

			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index] + '</li>';
			});

			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
			return false;
		}
	
}

function checkBudgetHeadAmountExists(count,budgetCodeId,sanctionedAmount,entryDate){
    	//debugger;
	$('.error-div').hide();
	var errorList = [];
	
	
	var postData = 'budgetCodeId=' + budgetCodeId + '&entryDate='
	+ entryDate + '&bchChargesAmt=' + sanctionedAmount + '&count='+ count;
	var url = "AccountBillEntry.html?getExpDetails";

	var response = __doAjaxRequest(url, 'post', postData, false);
	
	//$("#prExpId" + count).val(response[0].projectedExpenditureId);
	//$("#newBalance" + count).val(response[0].newBalanceAmount);
	
	var budgetCheckFlag=response[0].budgetCheckFlag;
	var originalEstimate = response[0].originalEstimate;
	var balanceAmount = response[0].billBudIdfyFlag;
	
	var bugDefParamStatusFlag = $('#budgetDefParamStatusFlag').val();
	
	if(budgetCheckFlag=="Y"){
	   if(balanceAmount == 'Y'){
		 errorList.push(getLocalMessage("account.bill.entry.controller.budget.data"));
		if(bugDefParamStatusFlag == 'Y'){
			$('#expenditureBudgetCode' + count).val("").trigger('chosen:updated');
			$('#actAmt' + count).val("");
			$('#bchChargesAmt' + count).val("");
		}
	} else if(parseFloat(sanctionedAmount) > parseFloat(originalEstimate - balanceAmount)) {
		errorList.push(getLocalMessage("account.validation.insuffient.budget.balance.amount"));
	}
 }
	
	if (errorList.length > 0) {

		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});

		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}

function checkBudgetHeadAmountExistOrNot(count,budgetCodeId,sanctionedAmount,entryDate,errorList){
	debugger;
$('.error-div').hide();
var postData = 'budgetCodeId=' + budgetCodeId + '&entryDate='
+ entryDate + '&bchChargesAmt=' + sanctionedAmount + '&count='+ count;
var url = "AccountBillEntry.html?getExpDetails";

var response = __doAjaxRequest(url, 'post', postData, false);

//$("#prExpId" + count).val(response[0].projectedExpenditureId);
//$("#newBalance" + count).val(response[0].newBalanceAmount);

var budgetCheckFlag=response[0].budgetCheckFlag;
var originalEstimate = response[0].originalEstimate;
var balanceAmount = response[0].billBudIdfyFlag;

var bugDefParamStatusFlag = $('#budgetDefParamStatusFlag').val();

if(budgetCheckFlag=="Y"){
   if(balanceAmount == 'Y'){
	 errorList.push(getLocalMessage("account.bill.entry.controller.budget.data"));
	if(bugDefParamStatusFlag == 'Y'){
		$('#expenditureBudgetCode' + count).val("").trigger('chosen:updated');
		$('#actAmt' + count).val("");
		$('#bchChargesAmt' + count).val("");
	}
} else if(parseFloat(sanctionedAmount) > parseFloat(originalEstimate - balanceAmount)) {
	errorList.push(getLocalMessage("account.validation.insuffient.budget.balance.amount"));
}
}

}






function calculatePercentage(count) {
	
	var deductionRate = $("#deductionRate" + count).val();
	//var invoiceValue = $("#bmInvoicevalue").val();
	var sanctionedTot = $("#sanctionedTot").val();
	
	if (sanctionedTot != "") {
		var result = (parseFloat(deductionRate) * parseFloat(sanctionedTot)) / 100;
		if (isNaN(result)) {
			result = "";
		}
		
		if (!isNaN(result) && result != "" && result != null && result != undefined) {
			$("#deductionAmt" + count).val(parseFloat(result).toFixed(2));
			getAmountFormatInStatic('deductionAmt'+count);
		}
		var totalDeductions = 0;
		var rowCount = $('#dedDetailTable tr').length;

		for (var i = 0; i < rowCount - 1; i++) {

			totalDeductions += parseFloat($("#deductionAmt" + i).val());
			if (isNaN(totalDeductions)) {
				totalDeductions = 0;
			}

		}
		$("#deductionsTot").val(totalDeductions.toFixed(2));
		getAmountFormatInStatic('deductionsTot');
		getNetPayable();

	} else {
		var errorList = [];
		errorList.push(getLocalMessage('account.sanctionedAmount.present'));
		if (errorList.length > 0) {

			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
						+ errorList[index] + '</li>';
			});

			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
			return false;
		}
	}
}

function getNetPayable() {

	
	var amountTot = parseFloat($("#amountTot").val()).toFixed(2);
	var disallowedAmtTot = parseFloat($("#disallowedAmtTot").val()).toFixed(2);
	var deductionsTot = parseFloat($("#deductionsTot").val()).toFixed(2);
	
	if(isNaN(deductionsTot)){
		deductionsTot = 0;
	}
	if(isNaN(disallowedAmtTot)){
		disallowedAmtTot = 0;
	}
	$("#disallowedAmtTot").val(parseFloat(disallowedAmtTot).toFixed(2));
	$("#deductionsTot").val(parseFloat(deductionsTot).toFixed(2));
	
	
	var netPayable = parseFloat(amountTot) - (parseFloat(disallowedAmtTot) + parseFloat(deductionsTot));
	if (isNaN(netPayable)) {
		$("#bmTotAmt").val("");
	} else {
		var finalNetPayableAmt = parseFloat(netPayable).toFixed(2);
		$("#netPayableHidden").val(finalNetPayableAmt);
		$("#bmTotAmt").val(finalNetPayableAmt);
		getAmountFormatInStatic('bmTotAmt');
		getAmountFormatInStatic('netPayableHidden');
	}
}

function clearBillEntryForm() {

	//$('#bmBilltypeCpdId').val("");
	$('#bmBilltypeCpdId').val('').trigger('chosen:updated');
	$('#bmEntrydate').val("");
	$('#bmInvoicevalue').val("");
	$('#field').val("");
	$('#bmBilldate').val("");
	$('#dpDeptid').val("");
	//$('#vmVendorname').val("");
	$('#vmVendorname').val('').trigger('chosen:updated');
	$('#vmVendorCodeDesc').val("");

	$('#bmInvoicenumber').val("");
	$('#bmInvoicedate').val("");
	$('#bmWPOrderNumber').val("");
	$('#bmWPOrderDate').val("");
	$('#bmResolutionNumber').val("");
	$('#bmResolutionDate').val("");
	$('#bmNarration').val("");

	var rowCountExp = $('.expDetailTableClass tr').length;
	for (var i = 0; i < rowCountExp - 1; i++) {

		$('#expenditureBudgetCode'+i).val('').trigger('chosen:updated');
		$('#pacHeadId' + i).val("");
		$("#actAmt" + i).val("");
		$("#bchChargesAmt" + i).val("");
		$("#disallowedAmt" + i).val("");
		$("#disallowedRemark" + i).val("");

	}
	$('#amountTot').val("");
	$("#sanctionedTot").val("");
	$("#disallowedAmtTot").val("");
	$("#deductionsTot").val("");
	$("#bmTotAmt").val("");

	var rowCountDed = $('.dedDetailTableClass tr').length;
	for (var i = 0; i < rowCountDed - 1; i++) {
		//$('#dedPacHeadId' + i).val("");
		$('#dedPacHeadId'+i).val('').trigger('chosen:updated');
		$("#deductionRate" + i).val("");
		$("#deductionAmt" + i).val("");
	}

}

function validateBudgetHead(count) {
	
	var budgetCode = $('#expenditureBudgetCode' + count).val();
	var entryDate = $('#bmEntrydate').val();
	var errorList = [];

	$('.expenditureClass').each(function(i) {
						for (var m = 0; m < i; m++) {
							if (($('#expenditureBudgetCode' + m).val() == $(
									'#expenditureBudgetCode' + i).val())) {
								errorList
										.push("Budget Heads cannot be same,please select another Budget Head");
								// var prRevBudgetCode =
								// $("#expenditureBudgetCode"+i).val("");
								// $("#prCollected"+i).prop("disabled",true);
							}
						}

					});

	if (errorList.length > 0) {

		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}

	var postdata = 'budgetCode=' + budgetCode + "&entryDate=" + entryDate;

	var response = __doAjaxRequest('AccountBillEntry.html?getDepartmentIdByBudgetCode', 'POST',	postdata, false, 'json');
	// $('#pacHeadId' + count).val(response[0]);
	// $('#pacHeadIdHidden' + count).val(response[0]);
	$('#departmentId').val(response);
	
	/*var departmentId = $("#departmentId").val();
	var templData = "&departmentId="+departmentId;
	var response2 = __doAjaxRequest('AccountBillEntry.html?checkTemplate','POST',templData,false,'json');
	alert(response2);
	$('#templateExistFlag').val(response2);*/
}

function checkTemplate(){
	
	var bmBilltype=$("#bmBilltypeCpdId option:selected").attr("code");

	var response = __doAjaxRequest('AccountBillEntry.html?checkTemplate','POST',null,false,'json');
	
	$('#templateExistFlag').val(response);

	
		$("#addButton0").prop('disabled', false);
		$("#delButton0").prop('disabled', false);
		$("#bchId0").prop('disabled', false);
	
	
	
	if (bmBilltype == "ESB") {
		//$("#bchId0").prop( "disabled", true);
		$('#bchId0').val('');
		$("#dedExpenHeadField").show();
		$("#dedExpenHeadFielddisplay").show();
	}else{
		$( "#bchId0").prop( "disabled", false);
		$('#bchId0').val('');
		$("#dedExpenHeadField").hide();
		$("#dedExpenHeadFielddisplay").hide();
	}
	if (bmBilltype == 'MI' || bmBilltype == 'W' || bmBilltype == 'S') {
		$('#bmWPOrderNumberL').addClass('required-control');
	} else {
		$('#bmWPOrderNumberL').removeClass('required-control');
	}
}




function validateDedAccountHead(cnt) {
	//debugger;
	$('.error-div').hide();
	var errorList = [];
	
	var bmBilltype=$("#bmBilltypeCpdId option:selected").attr("code");
	if (bmBilltype == "ESB") {
	$('.deductionClass')
			.each(
					function(i) {
						for (var m = 0; m < i; m++) {
							if (($('#dedPacHeadId' + m).val() == $(
									'#dedPacHeadId' + i).val())) {
								errorList
										.push("Deduction heads cannot be same,please select another Account Head");
								// var prRevBudgetCode =
								// $("#expenditureBudgetCode"+i).val("");
								// $("#prCollected"+i).prop("disabled",true);
								$('#dedPacHeadId'+cnt).val("");
								$('#dedPacHeadId'+cnt).val('').trigger('chosen:updated');
							}
						}
					});
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
		}
	}

//var theForm = '#accountBillEntryBean';

var requestData = $('#frmAccountBillEntryId').serialize();
		
if (errorList.length == 0) {
	
	var divName = ".content";

	var url = "AccountBillEntry.html?getdeductionExpHeadMapDetails";

	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	if(bmBilltype != "MI"){
	$('#bchId'+cnt).text('');
	$('#bchId'+cnt).append($('<option>', { 
        value: '0',
        text : 'Select'
	   }));
	}
	$.each(returnData, function(key, value) {
		$('#bchId'+cnt).append($('<option>', { 
	        value: key,
	        text : value 
	    }));
		$('#bchId'+cnt).trigger("chosen:updated");
	});
	var bmBilltypeCpdId=$('#bmBilltypeCpdId').val();
	var requestData = $('#frmAccountBillEntryId').serialize() +'&bmBilltypeCpdId='+bmBilltypeCpdId+'&cnt='+cnt;
	var url = "AccountBillEntry.html?taxDeduction";
	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	if(returnData == "")
		{
		var deductionAmt=$('#deductionAmt' + cnt).val("")
		}
	else{
		var deductionAmt=$('#deductionAmt' + cnt).val(returnData)
		}
	
	var amount=0;
	  var rowCount = $('#dedDetailTable tr').length;
		for (var m = 0; m < rowCount - 1; m++) {
			
				var collectedAmt = $("#deductionAmt"+m).val();
				if (collectedAmt != "" && collectedAmt != undefined && !isNaN(collectedAmt)) {
					amount += parseFloat($("#deductionAmt"+m).val());
				}
		}
		$("#deductionsTot").val(amount.toFixed(2));
		getAmountFormatInStatic('deductionsTot');
		getNetPayable();	
}
}

function validateDedExpenditureAccountHead(cont){
	
	$('.error-div').hide();
	var errorList = [];
	
	$('#deductionAmt' + cont).val("");
	
	$('.deductionClass')
	.each(
			function(i) {
				for (var m = 0; m < i; m++) {
					if (($('#dedPacHeadId' + m).val() == $(
							'#dedPacHeadId' + i).val()) && ($('#bchId' + m).val() == $(
									'#bchId' + i).val())) {
						errorList
								.push("Deduction and Expenditure head cannot be same,please select another Deduction Expenditure Head");
						//$("#bchId"+i).val("");
						// var prRevBudgetCode =
						// $("#expenditureBudgetCode"+i).val("");
						// $("#prCollected"+i).prop("disabled",true);
					}
				}
			});
if (errorList.length > 0) {
var errMsg = '<ul>';
$.each(errorList, function(index) {
	errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
			+ errorList[index] + '</li>';
});
errMsg += '</ul>';

$('#errorId').html(errMsg);
$('#errorDivId').show();
$('html,body').animate({
	scrollTop : 0
}, 'slow');
return false;
}

}
function validateExpAccountHead() {
	//debugger;
	var errorList = [];
	//$("#dedPacHeadId0").val('').trigger('chosen:updated');
	//$('#bchId0').val("");
	//$('#deductionRate0').val("");
	//$('#deductionAmt0').val("");
 
	$('.expenditureClass')
			.each(
					function(i) {
						for (var m = 0; m < i; m++) {
							if (($('#expenditureBudgetCode' + m).val() == $(
									'#expenditureBudgetCode' + i).val())) {
								errorList
										.push("Expenditure Heads cannot be same,please select another Account Head");
								// var prRevBudgetCode =
								// $("#expenditureBudgetCode"+i).val("");
								// $("#prCollected"+i).prop("disabled",true);
							}
						}

					});
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}

function validateTransactionDate( selectedDate) {
	
	
	var requestData ='transactionDate='+selectedDate;
	
	var response = __doAjaxRequest('AccountBillEntry.html?onTransactionDate', 'GET', requestData, false,'json');
	if (response == 'OK') {
		$('#errorDivId').hide();
	} else {
		$('#transactionDateId').val('');
		var errorList = [];
		errorList.push(response);
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}
/**
 * 
 */
function validateAuthorizationDate( selectedDate) {
	
	
	var requestData = {
			'authorizationDate':selectedDate,
			'entryDate': $('#bmEntrydate').val()
	}
	
	var response = __doAjaxRequest('AccountBillEntry.html?onAuthorizationDate', 'GET', requestData, false,'json');
	if (response == 'OK') {
		$('#errorDivId').hide();
	} else {
		$('#authorizationDateId').val('');
		var errorList = [];
		errorList.push(response);
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}

$('#createBtn').click(function(){
	
	 var ajaxResponse = __doAjaxRequest('PaymentEntry.html?createPage', 'POST', {}, false,'html');
	 $('#content').html(ajaxResponse);
});	



function calculateDeductionAmt(cont){
	//debugger;
	var errorList = [];
	var form = $("#modeType").val();
	var bmBilltype=$("#bmBilltypeCpdId option:selected").attr("code");
	
	if(form == 'update'){
	if (bmBilltype != "ESB") {
	var bchId = $('#bchId' + cont).val();
	

	/*if (bmBilltype != "MI") {
		if (bchId == "" || bchId == null || bchId == 0){
			errorList.push(getLocalMessage('account.exphead.deduction'));
			$('#deductionAmt' + cont).val("");
			}
		}*/
	

	
	//$("#deductionRate"+cont).val("");
	
	var expSacHeadId = $("#bchId"+cont).val();
	var expSacHeadIds = $("#expenditureBudgetCode"+cont).val();
	
    var amount=0; 
    var sactionAmt = 0;
    
    var rowCount = $('#expDetailTable tr').length;
	for (var m = 0; m < rowCount - 1; m++) {
		
			var collectedAmt = $("#bchChargesAmt"+m).val();
			if (collectedAmt != "" && collectedAmt != undefined && !isNaN(collectedAmt)) {
			sactionAmt += parseFloat($("#bchChargesAmt"+m).val());
			
		}
	}
	
	var rowCount = $('#dedDetailTable tr').length;
		for (var m = 0; m < rowCount - 1; m++) {
			
			if(expSacHeadId == $("#bchId"+m).val()){
				var collectedAmt = $("#deductionAmt"+m).val();
				if (collectedAmt != "" && collectedAmt != undefined && !isNaN(collectedAmt)) {
					amount += parseFloat($("#deductionAmt"+m).val());
				}
			}
		}
		
		var resultExpAmt = sactionAmt.toFixed(2); 
		var resultDecAmt = amount.toFixed(2); 
	if(parseFloat(resultExpAmt) < parseFloat(resultDecAmt)){
		errorList.push("Deduction amount cannot be grater than sanctioned amount.");
		$("#deductionAmt"+cont).val("");
		}
	}
	}else{
		
		var amount=0; 
	    var sactionAmt = 0;
	    
	    var rowCount = $('#expDetailTable tr').length;
		for (var m = 0; m < rowCount - 1; m++) {
				var collectedAmt = $("#bchChargesAmt"+m).val();
				if (collectedAmt != "" && collectedAmt != undefined && !isNaN(collectedAmt)) {
				sactionAmt += parseFloat($("#bchChargesAmt"+m).val());
				}
		}
		
		var rowCount = $('#dedDetailTable tr').length;
			for (var m = 0; m < rowCount - 1; m++) {
				
					var collectedAmt = $("#deductionAmt"+m).val();
					if (collectedAmt != "" && collectedAmt != undefined && !isNaN(collectedAmt)) {
						amount += parseFloat($("#deductionAmt"+m).val());
					}
			}
			
			var resultExpAmt = sactionAmt.toFixed(2); 
			var resultDecAmt = amount.toFixed(2); 
		if(parseFloat(resultExpAmt) < parseFloat(resultDecAmt)){
			errorList.push("Deduction amount cannot be grater than sanctioned amount.");
			$("#deductionAmt"+cont).val("");
			}
	}
	
	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
					+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}else{
		var totalDeductions = 0;
		var rowCount = $('#dedDetailTable tr').length;

		for (var i = 0; i < rowCount - 1; i++) {

			totalDeductions += parseFloat($("#deductionAmt" + i).val());
			if (isNaN(totalDeductions)) {
				totalDeductions = 0;
			}
		}
		$("#deductionsTot").val(totalDeductions.toFixed(2));
		getAmountFormatInStatic('deductionsTot');
		getNetPayable();
	}
}


function validateAuthorizationDates() {
	
	
	var requestData = {
			'authorizationDate': $('#authorizationDateId').val(),
			'entryDate': $('#bmEntrydate').val()
	}
	
	var response = __doAjaxRequest('AccountBillEntry.html?onAuthorizationDate', 'GET', requestData, false,'json');
	if (response == 'OK') {
		$('#errorDivId').hide();
	} else {
		$('#authorizationDateId').val('');
		var errorList = [];
		errorList.push(response);
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
		});
		errMsg += '</ul>';
		
		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		$('html,body').animate({
			scrollTop : 0
		}, 'slow');
		return false;
	}
}

function viewRTGSBillPaymentDetails(){
	
	
		var divName	=	formDivName;
		var paymentId = $('#paymentId').val();
		var url = "RTGSPaymentEntry.html?viewRTGSPaymentEntryDetail";
	    var requestData = 'gridId='+paymentId;
	    var ajaxResponse = __doAjaxRequest(url, 'GET', requestData, false,'html');
		$('.content').html(ajaxResponse);
}

function showRTGSPaymentDetails(){
	
	
	 var ajaxResponse = __doAjaxRequest('RTGSPaymentEntry.html?showData', 'POST', {}, false,'html');
	 $('#content').html(ajaxResponse);
}

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

function backGrantMasterForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'grantMaster.html');
	$("#postMethodForm").submit();
}

function backLoanMaster() {
	var loanId = $("#loanId").val();
	var formUrl = 'loanmaster.html';
	var actionParam = 'editForm';
	
	var requestdata = {
				"loanId" : loanId
			};

			var divName = '.content-page';
			var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, requestdata,
					'html', divName);
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
		
		
}
//added by rahul.chaubey for reset Defect #35910
function ResetForm()
{ 
	var loanId = $("#loanId").val()
	var loanRepaymentId=parseFloat($("#loanRepaymentId").val())
	var loanPayAmount =parseFloat( $("#loanPayableAmount").val())
	if(loanId != ""  && (loanRepaymentId!="" && loanRepaymentId!= null) ){
		var divName = '.content-page';
		var data = {
				"loanId":loanId,
				"loanRepaymentId":loanRepaymentId,
				"loanPayAmount":loanPayAmount
		};
		var ajaxResponse = __doAjaxRequest('AccountBillEntry.html?formForCreate', 'POST', data, false);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse)
		$('.error-div').hide();
		prepareTags();
		}
	else
	{
		var divName = '.content-page';
		var ajaxResponse = __doAjaxRequest('AccountBillEntry.html?formForCreate', 'POST', {}, false);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse)
		$('.error-div').hide();
		prepareTags();
	}

	
}
//added by rahul.chaubey
function setTaxableAmount()
{	 
	//debugger;
	var bmInvoicevalue = $("#bmInvoicevalue").val();
	if(bmInvoicevalue != "" && bmInvoicevalue){
	var invoiceAmount =parseFloat(bmInvoicevalue.replace(/\,/g,''));
	 if((invoiceAmount != undefined && !isNaN(invoiceAmount))){ 
		 $("#bmTaxableValue").val(parseFloat(invoiceAmount).toFixed(2));
	 }
	}
	 else { $("#bmTaxableValue").val("")}
	
}



function getDeductionAmtAutomatically(){
	debugger;
	//changes by Vishwanath auto calculate deduction amount when taxable amount change
	var bmBilltype=$("#bmBilltypeCpdId option:selected").attr("code");
	if (bmBilltype == "MI") {
	$('.deductionClass')
			.each(
					function(i) {
							var bmBilltypeCpdId=$('#bmBilltypeCpdId').val();
							var requestData = $('#frmAccountBillEntryId').serialize() +'&bmBilltypeCpdId='+bmBilltypeCpdId+'&cnt='+i;
							var url = "AccountBillEntry.html?taxDeduction";
							var returnData = __doAjaxRequest(url, 'post', requestData, false);

							if(returnData == "")
								{
								var deductionAmt=$('#deductionAmt' + i).val("")
								}
							else{
								var deductionAmt=$('#deductionAmt' + i).val(returnData)
								}
					});
	       }
	
  getAmountFormatInStatic('bmTaxableValue');
  
  var amount=0;
  var rowCount = $('#dedDetailTable tr').length;
	for (var m = 0; m < rowCount - 1; m++) {
		
			var collectedAmt = $("#deductionAmt"+m).val();
			if (collectedAmt != "" && collectedAmt != undefined && !isNaN(collectedAmt)) {
				amount += parseFloat($("#deductionAmt"+m).val());
			}
	}
	$("#deductionsTot").val(amount.toFixed(2));
	getAmountFormatInStatic('deductionsTot');
	getNetPayable();
	
}

function loadBudgetExpenditureFieldData(obj) {

	var errorList = [];
	var deptId = $("#departmentId").val();
	var fieldId = $("#fieldId").val();

	if (deptId == "")
		errorList.push(getLocalMessage('Select Department'));

	if (fieldId == "")
		errorList.push(getLocalMessage('Select Field Id'));
	if (errorList.length > 0) {
		displayErrorsPage(errorList);
		$("#fieldId").val('0');
		$('#fieldId').trigger('chosen:updated');
	} else {
		var divName = ".content";
		var formName = findClosestElementId(obj, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var url = "AccountBillEntry.html?getjqGridFiledload";
		var response = __doAjaxRequest(url, 'post', requestData, false, "json");
		if (jQuery.isEmptyObject(response)) {
			errorList
					.push('No Budget are available against selected Department and Field Id');
			displayErrorsPage(errorList);
		} else {
			var selectOption = getLocalMessage('account.common.select');
			$('#expenditureBudgetCode0').empty().append(
					'<option selected="selected" value="0">' + selectOption
							+ '</option>');
			$.each(response, function(key, value) {
				$('#expenditureBudgetCode0').append(
						$("<option></option>").attr("value", key).text(value));
			});
			$('#expenditureBudgetCode0').trigger('chosen:updated');
		}
	}
}

