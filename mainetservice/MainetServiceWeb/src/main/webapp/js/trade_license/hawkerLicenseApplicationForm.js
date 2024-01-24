var formDivName = '.content-page';
var divName = ".widget-content";
var maxDays;

$(document).ready(function() {
	var errorList = [];
	$('#businessStartDate').attr('readonly', true);
	$("#addBtn").show();
	$(".delButton").show();
	
	$(document).on('change', '.tradeCat', function() {
		var p=1;
		var q=0;
		var type=($('option:selected', $("#triCod"+1)).attr('code'));
		$("#itemDetails tbody tr").each(
				function(i) {
					
								
	
		if(type!=($('option:selected', $("#triCod"+p)).attr('code'))){
			
			  var errorList = [];
		      errorList.push(getLocalMessage("tradelicense.validation.categoryType"));
			  $("#triCod" + p).val('0');

			displayErrorsOnPage(errorList);
		}	
		p=p+6;
		q=q+1;
		
		
	});
	
		
});
	$("#trdLicfromDate").datepicker('setDate', null);

	$("#trdLictoDate").datepicker('setDate', null);
	    
	 $("#trdLictoDate").datepicker("destroy");
	 $("#trdLicfromDate").datepicker("destroy");
	
	
	
		
		var dateFields = $('.date');
		dateFields.each(function () {
			
			var fieldValue = $(this).val();
			if (fieldValue.length > 10) {
				$(this).val(fieldValue.substr(0, 10));
			}
		});
	 
	 
	
	$(function() {
		$('#businessStartDate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			
		});
	});
	

	
	 var flag=$("#trdFtype").val();
	 if(flag != null && flag!="" && flag!=undefined ){
	getOwnerTypeDetails();
	 }
	
	
	      
	  if($("#viewMode").val() == 'V'){
	     $("#checkId").attr("checked",true);
	   }
	  else {
	   $("#checkId").attr("checked",false);
	         }
	  
	  
	  
	  $("#resetform").on("click", function(){ 
		  window.location.reload("#hawkerLicenseApplicationForm")
		});
	  
	
		
		$('#trdFtype').hide();
	 
		var liceseTypeFlag=$("#hideTemporaryDate").val();
		
		
		
		
		if((liceseTypeFlag=='N') || ($('#trdLictype option:selected').attr('code') == "P")){	
			$("#licensePeriod").hide();
		}
		else{
			$("#licensePeriod").show();
		
			
		}
	
	
		
		
});

function savehawkerLicenseApplicationForm(obj) {

	var errorList = [];
	errorList = validateMarketLicenseForm(errorList);
	errorList = errorList.concat(validateOwnerDetailsTable());
	errorList = errorList.concat(validateitemDetailsTable());
	errorList = errorList.concat(validateAadharNumber());
	
	
	if (errorList.length ==0) {
	errorList = errorList.concat(validateDuplicate());
	}
	
	if (errorList.length == 0) {
		
	
		
		return saveOrUpdateForm(obj,
				"Hawker License Form Submitted Successfully", "HawkerLicenseApplicationForm.html?getCheckListAndCharges",
				'savehawkerLicenseApplicationForm');
		
	}else {
		displayErrorsOnPage(errorList);
	}
	}

function getChecklistAndCharge(obj){
	
	var theForm = '#hawkerLicenseApplicationForm';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'HawkerLicenseApplicationForm.html?getCheckListAndCharges';
	var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
			'html');
	if(returnData != null){
		
		$(formDivName).html(returnData);
		prepareTazgs();
	}
}

function showTermsConditionForm(element) {
	
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest(
			'HawkerLicenseApplicationForm.html?viewTermsCondition', 'POST',
			requestData, false, 'html');
	// $(formDivName).html(response);
	var divContents = response;
	var printWindow = window.open('', '_blank');
	printWindow.document.write('<html><head><title></title>');
	printWindow.document
			.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
			.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
			.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
			.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
			.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document
			.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
	printWindow.document.write(divContents);
	printWindow.document.write('</body></html>');
	printWindow.document.close();

}

function validateMarketLicenseForm(errorList) {

	
	
	var errorList = [];
	var licenseType = $("#trdLictype").val();
	var businessName = $("#trdBusnm").val();
	var businessAddress=$("#trdBusadd").val();
	var fromDateValue = $("#trdLicfromDate").val();
	var toDateValue = $("#trdLictoDate").val();
	var ownershipType = $("#trdFtype").val();
	var propertyNo = $("#propertyNo").val();
	var checkbox = document.getElementById("checkId");
	var area = $("#trdFarea").val();
	var propertyOwnerName = $("#propertyOwnerName").val();
	//var propertyAddress = $("#propertyAddress").val();
	var businessDate =$("#businessStartDate").val();
	var gstNo=$("#app_gstNumber").val();

	if (licenseType == "0" || licenseType == undefined || licenseType == "") {

		errorList.push(getLocalMessage("tradelicense.validation.licensetype"));
	}
	if ($('#trdLictype option:selected').attr('code') == "T") {
		
		if(fromDateValue == "" || fromDateValue == null || fromDateValue == undefined)
		{
			errorList.push(getLocalMessage('tradelicense.validation.licensefromperiod'));
		}
		 if(toDateValue == "" || toDateValue == null || toDateValue == undefined)
		{
			errorList.push(getLocalMessage('tradelicense.validation.licensetoperiod'));
		}
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var eDate = new Date(fromDateValue.replace(pattern,'$3-$2-$1'));
		var sDate = new Date(toDateValue.replace(pattern,'$3-$2-$1'));
		if (eDate > sDate) {
			errorList.push(getLocalMessage('trade.validation.toDate'));
		}
		if(fromDateValue != "")
		{
			if(pattern.test(fromDateValue)==false)
			{
				errorList.push(getLocalMessage('trade.wrongfromDate'));
			}
		}
		if(toDateValue!="")
		{
			if(pattern.test(toDateValue)==false)
			{
				errorList.push(getLocalMessage('trade.wrongtoDate'));
			}
		}
	}
	if (businessName == "" || businessName == undefined || businessName == "") {
		errorList.push(getLocalMessage("tradelicense.validation.businessname"));
	}
	
	if (businessAddress == "" || businessAddress == undefined || businessAddress == "") {
	  errorList.push(getLocalMessage("tradelicense.validation.businessaddress")); 
	  }
	/*if (area == "" || area == undefined || area == "") {
		  errorList.push(getLocalMessage("trade.validation.area")); 
		  }*/
	if (ownershipType == "0" || ownershipType == undefined
			|| ownershipType == "") {
		errorList
				.push(getLocalMessage("tradelicense.validation.ownershipType"));
	}
	/*if (propertyNo == "" || propertyNo == undefined || propertyNo == "") {
		errorList.push(getLocalMessage("tradelicense.validation.propertyNo"));
	}*/
	
	/*if($("#checkId").prop('checked')==false){
		  errorList.push(getLocalMessage('Please Accept Terms And Condition')); 
	}*/
	if(checkbox.checked==false){
		  errorList.push(getLocalMessage('tradelicense.validation.term.condition')); 
	}
	
	/*if (email !="")		
	{
	  var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	  var valid = emailRegex.test(email);
	   if (!valid) {
		   errorList.push(getLocalMessage('roadcutting.vldnn.emailid'));
	   } 
	}
*/
	/*if (propertyOwnerName == "" || propertyOwnerName == undefined || propertyOwnerName == "") {
		errorList.push(getLocalMessage("tradelicense.validation.propertyOwnerName"));
	}*/
	/*if (propertyAddress == "" || propertyAddress == undefined || propertyAddress == "") {
		errorList.push(getLocalMessage("tradelicense.validation.propertyAddress"));
	}*/
	if (businessDate == "" || businessDate == undefined || businessDate == "") {
		errorList.push(getLocalMessage("tradelicense.validation.businessDate"));
	}
	
	if(gstNo != 0 && gstNo != '' && gstNo != undefined)
	{
		var regExGst = /^([0-9]){2}([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}([0-9]){1}([Z]){1}([0-9|a-zA-Z]){1}?$/;
		if(!regExGst.test(gstNo) && gstNo !=''){
			errorList.push("Please Enter valid GST No.");
		}
	}
	

	/*var trdLicfromDate = moment(trdLicfromDate, "DD.MM.YYYY HH.mm").toDate();

	var businessDate = moment(businessDate, "DD.MM.YYYY HH.mm").toDate();

	if (trdLicfromDate != null && businessDate != null) {

		if (businessDate.getTime() < trdLicfromDate.getTime()) {

			errorList
					.push("Please select greater business date than license from date ");

		}

	}*/

	return errorList;
}

/*
 * function backTermsConditionPage(element) {
 * 
 * 
 * var formName = findClosestElementId(element, 'form'); var theForm = '#' +
 * formName; var requestData = __serializeForm(theForm); var response =
 * __doAjaxRequest('HawkerLicenseApplicationForm?backPage', 'POST', requestData,
 * false, 'html'); $(formDivName).html(response); }
 */

function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}

function validateMobile(mobile) {
	var regexPattern = /^[0-9]\d{9}$/;
	return regexPattern.test(mobile);
}

function deleteEntry(obj, ids) {
	
	deleteTableRow('ownerDetail', obj, ids);
	$('#ownerDetail').DataTable().destroy();
	$("#ownerDetail tbody tr").each(function(i) {

	});

}

function deleteItemDetailEntry(obj, ids) {
	
	deleteTableRow('itemDetails', obj, ids);
	$('#itemDetails').DataTable().destroy();
	$("#itemDetails tbody tr").each(function(i) {

	});
}

function getOwnerTypeDetails() {
	
	var ownerType = $("#trdFtype" + " option:selected").attr("code");
	if(ownerType =='R')
	{
	$("#agreementDate").show();
	}
	else
	{
		$("#agreementDate").hide();
	}
	
	
	if (ownerType != undefined) {
		var data = {
			"ownershipType" : ownerType
		};
		var URL = 'HawkerLicenseApplicationForm.html?getOwnershipTypeDiv';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$("#owner").html(returnData);
		$("#owner").show();
	} else {
		$("#owner").html("");
	}
	

	
}


$(function() {
	
	/* To add new Row into table */
	$("#itemDetails").on('click', '.addItemCF', function() {
		
		var errorList = [];
		errorList = validateitemDetailsTable(errorList);
	
		
		if (errorList.length == 0) {

			var content = $("#itemDetails").find('tr:eq(1)').clone();
			$("#itemDetails").append(content);

			content.find("select").val('0');
			content.find("input:hidden").val('');
			content.find("input:text").val("");
			
		
			$('.error-div').hide();
			reOrderItemDetailsSequence(); // reorder id and Path
			
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});

$(function() {
	
	/* To add new Row into table */
	$("#itemDetails").on('click', '.delButton', function() {
		

		if ($("#itemDetails tr").length != 2) {
			$(this).parent().parent().remove();
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
				
				
				$(this).find("input:hidden:eq(0)").attr("id", "triId" + (utp + 1))
						.attr(
								"name",
								"tradeMasterDetailDTO.tradeLicenseItemDetailDTO["
										+ (i) + "].triId");
				
				$(this).find("input:hidden:eq(1)").attr("id", "triId" + (utp + 2))
				.attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO["
								+ (i) + "].triId");
				$(this).find("input:hidden:eq(2)").attr("id", "triId" + (utp + 3))
				.attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO["
								+ (i) + "].triId");
				$(this).find("input:hidden:eq(3)").attr("id", "triId" + (utp + 4))
				.attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO["
								+ (i) + "].triId");
				$(this).find("input:hidden:eq(4)").attr("id", "triId" + (utp + 5))
				.attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO["
								+ (i) + "].triId");
				
				$(this).find("select:eq(0)").attr("id", "triCod" + (utp + 1));
				$(this).find("select:eq(1)").attr("id", "triCod" + (utp + 2));
				$(this).find("select:eq(2)").attr("id", "triCod" + (utp + 3));
				$(this).find("select:eq(3)").attr("id", "triCod" + (utp + 4));
				$(this).find("select:eq(4)").attr("id", "triCod" + (utp + 5));
				
				$(this).find("input:text:eq(0)").attr("id", "trdUnit" + i);
				
				$(this).find("select:eq(0)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCod1");
				$(this).find("select:eq(1)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCod2");
				$(this).find("select:eq(2)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCod3");
				$(this).find("select:eq(3)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCod4");
				$(this).find("select:eq(4)").attr(
						"name",
						"tradeMasterDetailDTO.tradeLicenseItemDetailDTO[" + i
								+ "].triCod5");
				
				$(this).find("input:text:eq(0)").attr("name", "tradeMasterDetailDTO.tradeLicenseItemDetailDTO["+i+"].trdUnit");
				
				
			});
}

function validateitemDetailsTable(errorList) {
	
	var subCategory = [];
	var category = [];
	var errorList =[];
	
	var rowCount = $('#itemDetails tr').length;
	if ($.fn.DataTable.isDataTable('#itemDetails')) {
		$('#itemDetails').DataTable().destroy();
	}

	if (errorList == 0)
		$("#itemDetails tbody tr")
				.each(
						function(i) {
							

							if (rowCount <= 2) {
								
								var itemCode1 = $("#triCod" + 1).val();
								var itemCode2 = $("#triCod" + 2).val();
								var itemCode3 = $("#triCod" + 3).val();
						 		var itemCode4 = $("#triCod" + 4).val();
								var itemCode5 = $("#triCod" + 5).val();
								
								var level = 1;
								var tradeQuantity = $("#trdUnit" + i).val();
							
							} else {
								var utp = i;
								utp = i * 6;
								var itemCode1 = $("#triCod" + (utp + 1)).val();
								var itemCode2 = $("#triCod" + (utp + 2)).val();
								var itemCode3 = $("#triCod" + (utp + 3)).val();
								var itemCode4 = $("#triCod" + (utp + 4)).val();
								var itemCode5 = $("#triCod" + (utp + 5)).val();
								
								var tradeQuantity = $("#trdUnit" + i).val();
							
								var level = i+1;

							}
							
							
							
						
							if (itemCode1 == "" || itemCode1 == undefined
									|| itemCode1 == "0") {
								errorList
										.push(getLocalMessage("trade.select.category")
												+ " " + (i + 1));
							}

							if (itemCode2 == "" || itemCode2 == undefined
									|| itemCode2 == "0") {
								errorList
										.push(getLocalMessage("trade.select.subcategory")
												+ " " + (i + 1));
							}
							
							if($('option:selected', $("#triCod"+1)).attr('code') != 'MBL'){
								
							if (tradeQuantity == "" || tradeQuantity == undefined
									|| tradeQuantity == "0") {
							
								
								errorList
										.push(getLocalMessage("trade.enter.item.value")
												+ " " + (i + 1));
								
							}
							}
							
							
							
							
								if (subCategory.includes(itemCode2)) {
									errorList
											.push(getLocalMessage("tradelicense.validation.dup.subcategory")
													+ (i + 1));
								}
								if (errorList.length == 0) {
									subCategory.push(itemCode2);
								}
								
								
								
						
						
						});
	return errorList;
}
function doItemDeletion(obj, id) {

	requestData = {
		"id" : id
	};
	url = 'HawkerLicenseApplicationForm.html?doItemDeletion';
	var row = $("#itemDetails tbody .appendableClass").length;
	if (row != 1) {
		//$("#ownerDetail tbody .appendableClass").parent().parent().remove();
		var response = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	}

}

function generateChallanAndPayment(element) {

	var errorList = [];
	var status=false ;
	//errorList = validateTradeForm(errorList);
	if (errorList.length == 0) {
	
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'N'
			|| $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
					":checked").val() == 'P') {
		status =  saveOrUpdateForm(element,
				"Your application Data  saved successfully!",
				'HawkerLicenseApplicationForm.html?PrintReport', 'generateChallanAndPayement');
	} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'Y') {
		status =  saveOrUpdateForm(element,
				"Your application Data  saved successfully!!",
				'HawkerLicenseApplicationForm.html?redirectToPay', 'generateChallanAndPayement');
	}
	else{
		
		status =  saveOrUpdateForm(element,
		"Your application Data  saved successfully!!",
		'AdminHome.html', 'generateChallanAndPayement');
	}
	} else {
		
		displayErrorsOnPage(errorList);
	}
	agencyRegAcknow(status);
	

}

function validateTradeForm(errorList){
	
	var errorList = [];	
	var amountToPay = $("#amountToPay").val();
	
	if(amountToPay != undefined || amountToPay == ""){
		if(amountToPay > 0 && $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == "" || $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == undefined){
			errorList.push(getLocalMessage("tradelicense.validation.collectionMode"));
		}
	}
	return errorList;
}


function editForm(element) {
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = {};
	requestData = __serializeForm(theForm);
	var returnData = __doAjaxRequest(
			'HawkerLicenseApplicationForm.html?savehawkerLicenseApplicationForm', 'POST',
			requestData, false);

	showConfirmBoxForTradeLicense(returnData);

}
function showConfirmBoxForTradeLicense(sucessMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("bt.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'+sucessMsg.command.message+'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="showScrutinyLabel()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}
function showScrutinyLabel(){
	$.fancybox.close();
	loadScrutinyLabel('ScrutinyLabelView.html?setViewDataFromModule',$('#_appId').val(),$('#_labelId').val(),$('#_serviceId').val());
}

function editApplication(element){
	

	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('HawkerLicenseApplicationForm.html?editApplication',
			'POST', requestData, false, 'html');

	$(formDivName).html(response);
	reOrderItemDetailsSequence(); // reorder id and Path
	getOwnerTypeDetails();
	var fromDateValue = $("#trdLicfromDate").val();
	var toDateValue = $("#trdLictoDate").val();
	//getLicenseValidityDateRange();
	
	$("#trdLicfromDate").val(fromDateValue);
	$("#trdLictoDate").val(toDateValue);
	
	if($('#trdLictype option:selected').attr('code') == "P"){	
		$("#licensePeriod").hide();
	}
	else{
		$("#licensePeriod").show();
	}
	
	
}

function resetTradeForm()
{
	
	$('input[type=text]').val('');  
	$(".alert-danger").hide();
	$("#hawkerLicenseApplicationForm").validate().resetForm();
}

function validateOwnerDetailsTable() {
	
	var errorList = [];
	var rowCount = $('#ownerDetail tr').length;	

	if ($.fn.DataTable.isDataTable('#ownerDetail')) {
		$('#ownerDetail').DataTable().destroy();
	}
	if (errorList == 0)
		$("#ownerDetail tbody tr").each(function(i) {
			
			if(rowCount<=2){

							var ownerName = $("#troName" + i).val();
							var fatherhusbandName = $("#troMname" + i).val();
							var ownerGender = $("#troGen" + i).val();
							var ownerAddress = $("#troAddress" + i).val();
							var ownerMobileNo = $("#troMobileno" + i).val();
							var ownerAdharNo = $("#troAdhno" + i).val();
							var emailId = $("#troEmailid" + i).val();
							var constant = 1;
			}
			else{
				var ownerName = $("#troName" + i).val();
				var fatherhusbandName = $("#troMname" + i).val();
				var ownerGender = $("#troGen" + i).val();
				var ownerAddress = $("#troAddress" + i).val();
				var ownerMobileNo = $("#troMobileno" + i).val();
				var ownerAdharNo = $("#troAdhno" + i).val();
				var emailId = $("#troEmailid" + i).val();
				
				var constant = i+1;
			}
							if (ownerName == '0' || ownerName == undefined
									|| ownerName == "") {
								errorList
										.push(getLocalMessage("tradelicense.validation.ownername")
												+" " +constant);
							}
							if (fatherhusbandName == ""
									|| fatherhusbandName == undefined
									|| fatherhusbandName == "0") {
								errorList
										.push(getLocalMessage("tradelicense.validation.fatherhusbandname")
												+" " +constant);
							}
							if (ownerGender == "" || ownerGender == undefined
									|| ownerGender == "0") {
								errorList
										.push(getLocalMessage("tradelicense.validation.ownergender")
												+" " +constant);
							}
							if (ownerAddress == "" || ownerAddress == undefined
									|| ownerAddress == "0") {
								errorList
										.push(getLocalMessage("tradelicense.validation.owneraddress")
												+" " +constant);
							}
							
							if (ownerMobileNo == "" || ownerMobileNo == undefined || ownerMobileNo == "0") {
								errorList.push(getLocalMessage("tradelicense.validation.ownerMobileNo")
										+" " +constant);
							}
							else {	
								if (!validateMobile(ownerMobileNo)) 
								{
									errorList.push(getLocalMessage("tradelicense.validation.validMobileNo")+" " +constant);
								}
							}
							
							
							if (emailId !="")		
							{
							  var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
							  var valid = emailRegex.test(emailId);
							   if (!valid) {
								   errorList.push(getLocalMessage('trade.vldnn.emailid')+" " +constant);
							   } 
							}
							
							
							if(ownerAdharNo.length !=0 && ownerAdharNo.length<12){
								errorList.push(getLocalMessage('trade.valid.adharno')+" " +constant);
							}
							
							if (ownerAdharNo != "")
							{
								var adharRegex = new RegExp(/^[0-9]\d{12}$/i);
								 var valid = adharRegex.test(ownerAdharNo);
								  if (!valid) {
									  errorList.push(getLocalMessage('trade.valid.adharno')+" " +constant);
								  }
							}
							
							
						});
	
	
	return errorList;
}

$('body').on('focus',".hasAadharNo", function(){
	$('.hasAadharNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	});
	});


function agencyRegAcknow(status) {
	
    if (!status) {
	var URL = 'HawkerLicenseApplicationForm.html?printAgencyRegAckw';
	var returnData = __doAjaxRequest(URL, 'POST', {}, false);

	var title = 'Agency Registration Acknowlegement';
	var printWindow = window.open('', '_blank');

	printWindow.document.write('<html><head><title>' + title + '</title>');
	printWindow.document
		.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
	printWindow.document
		.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
	printWindow.document
		.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
	printWindow.document
		.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
	printWindow.document
		.write('<script src="js/mainet/ui/jquery.min.js"></script>')
	printWindow.document
		.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
	printWindow.document
		.write('<script>$(window).on("load",function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
	printWindow.document.write('</head><body style="background:#fff;">');
	printWindow.document.write(returnData);
	printWindow.document.write('</body></html>');
	printWindow.document.close();
    }
}
/*function getLicenseValidityDateRange()
{
	
	
	var errorList = [];  
    $("#trdLicfromDate").datepicker('setDate', null);

	$("#trdLictoDate").datepicker('setDate', null);
	    
	 $("#trdLictoDate").datepicker("destroy");
	 $("#trdLicfromDate").datepicker("destroy");
	 
	var licenseType = $("#trdLictype").val();

	requestData = {
		"licenseType" : licenseType
	};
	
	if(licenseType=="")
		{
		
		}
	else
	{

	var response = __doAjaxRequest('HawkerLicenseApplicationForm.html?LicenseType',
			'POST', requestData, false, 'html');
	maxDays=response;
	
	if(response !="")
	{

	var licMaxTenureDays = response;
	$("#trdLictoDate").datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : '0',
		maxDate : +licMaxTenureDays,
	});

	$('#trdLicfromDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		minDate : '0',
		maxDate : '0',
	});

	}
	
	else
	{
	
	errorList
	.push("Date Range Is not Defined for License Type");

	displayErrorsOnPage(errorList);
    }
	}
	
	
	if($('#trdLictype option:selected').attr('code') == "P"){	
		$("#licensePeriod").hide();
	}
	else{
		$("#licensePeriod").show();
	}
	

}
*/

function setValues(obj) {

	$('#trdBankIfscCode').val($('#bankId option:selected').attr('bankIfscCode'));
	
}

function validateAadharNumber()
{
	

	var errorList = [];
var adharNumber=Number($("#troAdhno0").val());
requestData = 
{
		"adharNumber" : adharNumber
};
	
	if(adharNumber=="")
		{
		
		}
	else
	{

	var response = __doAjaxRequest('HawkerLicenseApplicationForm.html?AdharNumber',
			'POST', requestData, false, 'html');

			
	       if (response != 0) 
	       {
	    	   errorList
	    		.push("Duplicate Vendor  Aadhar Number. Already Registered");

	    		displayErrorsOnPage(errorList);
		   }
	}
	return errorList;

}

function validateDuplicate()
{
	
	
var p=1;
var q=0;
var errorList = [];
var type=($('option:selected', $("#triCod"+1)).attr('code'));
$("#itemDetails tbody tr").each(
		function(i) {
			
						

if(type!=($('option:selected', $("#triCod"+p)).attr('code'))){
	
	errorList.push(getLocalMessage("tradelicense.validation.categoryType"));
	$("#triCod"+p).val('0');
	
	//displayErrorsOnPage(errorList);
}	
p=p+6;
q=q+1;


});

return errorList;
}
