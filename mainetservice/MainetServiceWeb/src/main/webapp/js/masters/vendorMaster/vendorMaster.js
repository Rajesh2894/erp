var fileArray=[];
$("#tbAcVendormaster").validate({

	onkeyup : function(element) {
		this.element(element);
		console.log('onkeyup fired');
	},
	onfocusout : function(element) {
		this.element(element);
		console.log('onfocusout fired');
	}
});

$(function() {
	$(document)
			.on(
					'click',
					'.createData',
					function() {
						var url = "Vendormaster.html?form";
						$
								.ajax({
									url : url,
									success : function(response) {
										$(".widget-content").html(response);
										$(".widget-content").show();
									},
									error : function(xhr, ajaxOptions,
											thrownError) {
										var errorList = [];
										errorList
												.push(getLocalMessage("admin.login.internal.server.error"));
										showError(errorList);
									}
								});
					});
});

function searchVendorData() {
	$('.error-div').hide();
	var errorList = [];

	var cpdVendortype = $("#cpdVendortype").val();
	var cpdVendorSubType = $("#cpdVendorSubType").val();
	var vendor_vmvendorcode = $("#vendor_vmvendorcode").val();
	var vmCpdStatus = $("#vmCpdStatus").val();

	if ((cpdVendortype == "" || cpdVendortype == "0")
			&& (cpdVendorSubType == "" || cpdVendorSubType == "0")
			&& (vendor_vmvendorcode == "" || vendor_vmvendorcode == "0")
			&& (vmCpdStatus == "" || vmCpdStatus == "0")) {
		errorList.push(getLocalMessage("vendor.error.valid.serchCriteria"));
	}

	if (errorList.length > 0) {
		var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});
		errMsg += '</ul>';

		$('#errorId').html(errMsg);
		$('#errorDivId').show();
		return false;
	}

	if (errorList.length == 0) {

		var url = "Vendormaster.html?getjqGridsearch";
		var requestData = {
			"cpdVendortype" : cpdVendortype,
			"cpdVendorSubType" : cpdVendorSubType,
			"vendor_vmvendorcode" : vendor_vmvendorcode,
			"vmCpdStatus" : vmCpdStatus
		};
		var result = __doAjaxRequest(url, 'POST', requestData, false, 'json');
		if (result != null && result != "") {
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		} else {
			var errorList = [];

			errorList
					.push(getLocalMessage("vendor.error.valis.noRecord"));

			if (errorList.length > 0) {
				var errMsg = '<ul>';
				$.each(errorList, function(index) {
					errMsg += '<li>' + errorList[index] + '</li>';
				});
				errMsg += '</ul>';

				$('#errorId').html(errMsg);
				$('#errorDivId').show();
			}
			$("#grid").jqGrid('setGridParam', {
				datatype : 'json'
			}).trigger('reloadGrid');
		}
	}

	/*
	 * if (errorList.length == 0) { //vmvendorname = $("#vmvendorname").val();
	 * var url = "Vendormaster.html?getjqGridsearch"; var requestData = {
	 * "cpdVendortype" : cpdVendortype };
	 *  $ .ajax({ url : url, data : requestData, datatype : "json", type :
	 * 'GET', success : function(response) {
	 * 
	 * $("#grid").jqGrid('setGridParam', { datatype : 'json'
	 * }).trigger('reloadGrid');
	 *  }, error : function(xhr, ajaxOptions, thrownError) { var errorList = [];
	 * errorList .push(getLocalMessage("admin.login.internal.server.error"));
	 * showError(errorList); } }); } else { var errMsg = '<button type="button"
	 * class="close" aria-label="Close" src="css/images/close.png"
	 * onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	 * 
	 * $.each(errorList, function(index) { errMsg += '<li><i class="fa
	 * fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>'; });
	 * 
	 * errMsg += '</ul>'; $('.error-div').html(errMsg);
	 * $('.error-div').show(); return false; }
	 */
};

var vmVendorid = '';
$(function() {
	$("#grid")
			.jqGrid(
					{
						url : "Vendormaster.html?getGridData",
						datatype : "json",
						mtype : "GET",
						colNames : [
								getLocalMessage('accounts.vendor.type'),
								getLocalMessage('bill.vendor.code'),
								getLocalMessage('account.tenderentrydetails.VendorEntry'),
								getLocalMessage('accounts.vendor.address'),
								getLocalMessage('accounts.master.status'), "",
								getLocalMessage('bill.action') ],
						colModel : [ {
							name : "vmVendorid",
							width : 0,
							sortable : true,
							hidden : true,
							searchoptions : {
								"sopt" : [ "bw", "eq" ]
							}
						}, {
							name : "vmVendorcode",
							width : 20,
							sortable : true,
							searchoptions : {
								"sopt" : [ "bw", "eq" ]
							}
						}, {
							name : "vmVendorname",
							width : 45,
							sortable : true,
							searchoptions : {
								"sopt" : [ "bw", "eq" ]
							}
						}, {
							name : "vmVendoradd",
							width : 75,
							sortable : false,
							searchoptions : {
								"sopt" : [ "eq" ]
							}
						}, {
							name : "vmCpdStatusDesc",
							width : 25,
							align : 'center',
							sortable : false,
							formatter : statusFormatter,
							searchoptions : {
								"sopt" : [ "eq" ]
							}
						}, {
							name : "vmCpdStatusDesc",
							width : 25,
							sortable : true,
							hidden : true,
							searchoptions : {
								"sopt" : [ "eq" ]
							}
						}, {
							name : 'vmVendorid',
							index : 'vmVendorid',
							width : 30,
							align : 'center !important',
							formatter : addLink,
							search : false
						} ],
						pager : "#pagered",
						emptyrecords: getLocalMessage("jggrid.empty.records.text"),
						rowNum : 30,
						rowList : [ 5, 10, 20, 30 ],
						sortname : "vmVendorcode",
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
						caption : getLocalMessage('master.lbl.vendorMasList')
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
	return "<a class='btn btn-blue-3 btn-sm viewVendorClass' title='View'value='"
			+ rowdata.vmVendorid
			+ "' vmVendorid='"
			+ rowdata.vmVendorid
			+ "' ><i class='fa fa-eye'></i></a> "
			+ "<a class='btn btn-warning btn-sm addVendorClass' title='Edit'value='"
			+ rowdata.vmVendorid
			+ "' vmVendorid='"
			+ rowdata.vmVendorid
			+ "' ><i class='fa fa-pencil'></i></a> ";
}

function statusFormatter(cellValue, options, rowdata, action) {

	if (rowdata.vmCpdStatusDesc == 'Active') {
		return "<a href='#'  class='fa fa-check-circle fa-2x green'   value='"
				+ rowdata.vmCpdStatusDesc
				+ "'  alt='Vendor is Active' title='Vendor is Active'></a>";
	} else if (rowdata.vmCpdStatusDesc == 'Inactive') {
		return "<a href='#'  class='fa fa-times-circle fa-2x red' value='"
				+ rowdata.vmCpdStatusDesc
				+ "' alt='Vendor is  InActive' title='Vendor is InActive'></a>";
	} else {
		return "<a href='#'  class='fa fa-ban fa-2x' value='"
				+ rowdata.vmCpdStatusDesc
				+ "' alt='Vendor is  Blacklisted' title='Vendor is Blacklisted'></a>";
	}

}

$(function() {
	$(document)
			.on(
					'click',
					'.addVendorClass',
					function() {
						var errorList = [];
						var $link = $(this);
						var vmVendorid = $link.closest('tr').find('td:eq(0)')
								.text();
						var authStatus = $link.closest('tr').find('td:eq(5)')
								.text();
						var url = "Vendormaster.html?formForUpdate";
						var requestData = "vmVendorid=" + vmVendorid
								+ "&MODE1=" + "Edit";
						var returnData = __doAjaxRequest(url, 'post',
								requestData, false);
						if (authStatus != "Inactive"
								&& authStatus != "Blacklist") {
							$('.content').html(returnData);
						} else {
							errorList
									.push("It is already Inactive/Blacklist so EDIT is not allowed!");
							if (errorList.length > 0) {
								var errMsg = '<ul>';
								$.each(errorList, function(index) {
									errMsg += '<li>' + errorList[index]
											+ '</li>';
								});
								errMsg += '</ul>';
								$('#errorId').html(errMsg);
								$('#errorDivId').show();
								return false;
							}
						}
					});

	$(document).on('click', '.viewVendorClass', function() {
		var $link = $(this);
		var vmVendorid = $link.closest('tr').find('td:eq(0)').text();
		var url = "Vendormaster.html?viewMode";
		var requestData = "vmVendorid=" + vmVendorid + "&MODE1=" + "View";
		var returnData = __doAjaxRequest(url, 'post', requestData, false);
		var divName = '.content';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		$('select').attr("disabled", true);
		$('input[type=text]').attr("disabled", true);
		$('input[type=checkbox]').attr('disabled', true);
		$('select').prop('disabled', true).trigger("chosen:updated");
		return false;
	});

});

function saveDataVendor(element) {
	
	$('.error-div').hide();
	var errorList = [];

	// var sliMode = $('#sliMode').val();
	// $('#sliPrefixMode').val(sliMode);

	var panNo = $('#vendor_vmpannumber').val();
	var mobileNo = $('#vendor_mobileNo').val();
	var uidNo = $('#vendor_vmuidno').val();
	var vat = $("#vendor_tinnumber").val();
	var gst = $('#gstNumber').val();
	if (gst != 0 || gst != '') {
		/*var regExGst = /^([0-9]){2}([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}([0-9]){1}([a-zA-Z]){1}([0-9a-zA-Z]){1}?$/;*/
		var regExGst = /^([0-9]){2}([A-Z]){5}([0-9]){4}([A-Z]){1}([1-9A-Z]){1}Z([0-9A-Z]){1}?$/;
		if (!regExGst.test(gst) && gst != '') {
			errorList.push(getLocalMessage("tbOrganisation.error.gstNo"));
		}
	}
	
	var bankAccNo=$("#vendor_bankaccountnumber").val();
	
	if(bankAccNo!="" && bankAccNo!=undefined && bankAccNo.length <8 || bankAccNo.length>16 ){
		errorList.push("Please Enter Account Number Minimum 8 or Maximum 16 Digit");
	}
	
	if (mobileNo.length == 10) {
		var mobileRegex = /^[789]\d{9}$/;
		if (!mobileRegex.test(mobileNo)) {
			errorList.push(getLocalMessage('account.valid.mobileNo'));
		}
	}
		
	/*if(panNo =="" && uidNo=="" && gst==""){
		errorList.push(getLocalMessage("tbOrganisation.error.atLeastOne"));
	}*/

	 /*var chk = $('#vendor_rtgsvendorflag').is(':checked');
	 if(chk){
		 $("#rtgsvendorflag").val('Y');
	 }else{
		 $("#rtgsvendorflag").val('N');
	 }*/
		 
	if (errorList.length > 0) {
		showVendorError(errorList);
	} else {
		if(vat==undefined)
			vat=null;
		var validateReq = {
			"panNo" : panNo,
			"mobileNo" : mobileNo,
			"uidNo" : uidNo,
			"vat" : vat,
			"gst" : gst,
			"mode" : $('#form_AddEditMode').val(),
			"vendorId" : $('#vendorId').val()
		}

		var validateUrl = "Vendormaster.html?validatePANandMobile";
		var rtrnData = __doAjaxRequestForSave(validateUrl, 'post', validateReq,
				false, '', '');
		for ( var i in rtrnData) {
			errorList.push(rtrnData[i]);
		}

		var vendorCode = $("#cpdVendortype option:selected").attr("code");
		if (vendorCode == 'E') {
			var empName = $('#emp_Id').val();
			if(empName == null || empName == ''){
				errorList.push('Please select employee name');	
			}
		}
		
		if (errorList.length <= 0) {
			var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var formAction = $("#formAction").val();
			$(theForm).attr('action', formAction);
			var requestData = __serializeForm(theForm);
			var succsessMsg= getLocalMessage('account.record.saved');
			if($('#form_AddEditMode').val()=="update"){
				succsessMsg= getLocalMessage('account.edit.succ');
			}else{
				succsessMsg= getLocalMessage('account.record.saved');
			}
			return doActionWithParam(element, succsessMsg,
					requestData, 'Vendormaster.html');
			
		} else {
			showVendorError(errorList);
		}
	}
}

$(document).ready(
		function() {
			// $("#bankId").chosen();
			var mode = $("#form_AddEditMode").val();
			if (mode == 'View') {
			} else {
				$("#bankId").chosen();
			}
			$("#cpdVendortype").chosen();
			$("#cpdVendorSubType").chosen();
			$("#listOfTbAcFunctionMasterItems").chosen();
			$("#functionId").chosen();
			$('.required-control').next().children().addClass('mandColorClass')
					.attr("required", true);

		});

function getVendorTypeWisePrimaryAcHead(obj) {

	$('.error-div').hide();
	var errorList = [];

	if (errorList.length == 0) {

		if ($('#sliMode').val() == "L") {

			var divName = ".content";

			var formName = findClosestElementId(obj, 'form');

			var theForm = '#' + formName;

			var requestData = __serializeForm(theForm);

			var url = "Vendormaster.html?getVendorTypeWisePrimaryAcHead";

			var response = __doAjaxRequest(url, 'post', requestData, false);

			$(divName).removeClass('ajaxloader');
			$(divName).html(response);
			// $('#priHeadDiv').show();
			// $('#priHeadLbl').addClass('required-control');
			$('.required-control').next().children().addClass('mandColorClass');
			$("#hiddenDiv").show();

		}

		var vendorCode = $("#cpdVendortype option:selected").attr("code");
		if (vendorCode == 'E') {
			$('#emp_IdDiv').show();
			$('#vendorvmvendorname').hide();
			$('#emp_Idl').show();
			$('#vendorvmvendornamel').hide();
		} else {
			$('#emp_IdDiv').hide();
			$('#vendorvmvendorname').show();
			$('#emp_Idl').hide();
			$('#vendorvmvendornamel').show();
		}

	}
};

function clearVendorSubType(obj) {

	var cpdVendorSubType = $('#cpdVendorSubType').val("");
	$("#cpdVendorSubType").val('').trigger('chosen:updated');
	checkErrorForChosen(obj);
}
function populateMSME(obj) {

$("#MSMENo").hide();
$("#vendor_msmeNo").val('');
	var cpdVendorSubType = $("#cpdVendorSubType option:selected").attr("code");
	if(cpdVendorSubType=="MSME"||cpdVendorSubType=='MSME'){
		$("#MSMENo").show();
	}
}
function fnValidatePAN(Obj) {

	$('.error-div').hide();
	var errorList = [];
	Obj = $('#panNo').val();
	if (Obj != "") {
		ObjVal = Obj;
		var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
		var code = /([C,P,H,F,A,T,B,L,J,G])/;
		var code_chk = ObjVal.substring(3, 4);
		if (ObjVal.search(panPat) == -1) {
			errorList.push('Invaild PAN Number');
			
		} else if (code.test(code_chk) == false) {
			errorList.push('Invaild PAN Number');
			
		}
	
	if (errorList.length > 0) {
		displayErrorsOnPage(errorList);
	}
	return errorList;
}

	if (errorList.length > 0) {
		showVendorError(errorList);
	}
};

function fnValidatePHONE(Obj) {

	$('.error-div').hide();
	var errorList = [];

	var vendor_MobileNo = $.trim($("#vendor_mobileNo").val());
	var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
	if (vendor_MobileNo.match(phoneno)) {

	} else {
		errorList.push(getLocalMessage('master.valid.mobNo'));

	}

	if (errorList.length > 0) {
		showVendorError(errorList);
	}
};

function clearVendorForm() {

	$('#bankId').val('').trigger('chosen:updated');
	$('#cpdVendortype').val('').trigger('chosen:updated');
	$('#cpdVendorSubType').val('').trigger('chosen:updated');
	$('#listOfTbAcFunctionMasterItems').val('').trigger('chosen:updated');
	$('#functionId').val('').trigger('chosen:updated');
	$(':input').not(':button, :submit, :reset, :hidden').val('');
	$(':checkbox, :radio').prop('checked', false);
	$('#vendorErrorDiv').hide();
}

function clearForm() {

	$(':input').not(':button, :submit, :reset, :hidden').val('');
	$(':checkbox, :radio').prop('checked', false);
}

function showVendorError(errorList) {
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#vendorErrorDiv").html(errMsg);
	$("#vendorErrorDiv").show();
	$("html, body").animate({
		scrollTop : 0
	}, "slow");
	return false;
}

function exportTemplate() {
	var $link = $(this);
	/* var spId = $link.closest('tr').find('td:eq(0)').text(); */
	// var functionId = 1;
	var url = "Vendormaster.html?exportTemplateData";
	var requestData = "";
	var returnData = __doAjaxRequest(url, 'post', requestData, false);

	$('.content').html(returnData);

	prepareDateTag();
	return false;
}
function closeOutErrorBox() {
	$('#errorDivSec').hide();
}



function printReport(obj) {
	
	     $('.error-div').hide();
		    var formUrl = "Vendormaster.html?getReportData";
		    var divName = '.content-page';
			var ajaxResponse = doAjaxLoading(formUrl,'POST','html');
			$(divName).removeClass('ajaxloader');
			$(divName).html(ajaxResponse);
			prepareTags();
};

$("#attachDocuments").on("click",'#deleteFile',function(e) {
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

