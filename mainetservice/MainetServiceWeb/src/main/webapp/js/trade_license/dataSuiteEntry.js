var fileArray = [];
$(document).ready(function() {
	
	var errorList = [];
	
	$(document).on('change', '.tradeCat', function() {
		var p=1;
		var q=0;
		var type=($('option:selected', $("#triCod"+1)).attr('code'));
		$("#itemDetails tbody tr").each(
				function(i) {
		if(($('option:selected', $("#triCod"+p)).attr('code')) == 'TL'){
		
			$("#trdQuantity"+q).prop("disabled", true); 
			$("#triCod3").prop("disabled", false); 
			$("#triCod4").prop("disabled", false); 
			$(".addItemCF").hide();
			$(".delButton").hide();
			//$("#trdUnit"+q).prop("disabled", true); 			
		} else{
			$("#trdQuantity"+q).prop("disabled", false); 
			$("#triCod3").prop("disabled", false); 
			$("#triCod4").prop("disabled", false); 
			$(".addItemCF").show();
			$(".delButton").show();
			//$("#trdUnit"+q).prop("disabled", false); 
		}
		if(type!=($('option:selected', $("#triCod"+p)).attr('code'))){
			
			errorList.push(getLocalMessage("tradelicense.validation.categoryType"));
			$("#triCod"+p).val('0');
			
			displayErrorsOnPage(errorList);
		}	
		p=p+6;
		q=q+1;
		
		
	});
	});

	//Defect #105322
	$('#trdLicfromDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate: 0,
		onSelect: function(selected) {
			$("#trdLictoDate").datepicker("option","minDate", selected);
	      }
	});
	$('#trdLictoDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		onSelect: function(selected) {
			var todate= $('#trdLictoDate').val();
			$("#businessStartDate").datepicker("option", "maxDate",todate);
	      } 
	});
	
	$('#businessStartDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
	});
	
	

	$("#datatables").dataTable({
		"oLanguage" : {
			"sSearch" : ""
		},
		"aLengthMenu" : [ [ 5, 10, 15, -1 ], [ 5, 10, 15, "All" ] ],
		"iDisplayLength" : 5,
		"bInfo" : true,
		"lengthChange" : true

	});

	$(".reset").click(function() {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'dataEntrySuites.html');
		$("#postMethodForm").submit();

	});
	if ($("#saveMode").val() == 'E') {
		getOwnerTypeDetails('N');
	}

	if ($("#saveMode").val() == 'V') {
		$("#dataEntrySuite :input").prop("disabled", true);
		$('.backButton').prop("disabled", false);
		$('.Upload').prop("disabled", false);
	}
	
	var flag=$("#trdFtype").val();
	 if(flag != null && flag!="" && flag!=undefined ){
	getOwnerTypeDetails();
	
	 }
	 
	 
	 $("#trdLictype").change(function(e) {

			if ($('#trdLictype option:selected').attr('code') == "T") {
				$("#licensePeriod").show();

			} else if ($('#trdLictype option:selected').attr('code') == "P") {
				$("#licensePeriod").hide();
			}

		});
	 
	 
	 $(function() {
			$('#businessStartDate').datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				
			});
		});
	 

               var ownType = $("#trdFtype").val();

					if (ownType == "0" || ownType == undefined || ownType == "") {

						$("#agreementDate").hide();

					}

					if (ownType == "R") {

						$("#agreementDate").show();

					}

					$("#agreementFromDate").datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,

					});

					$('#agreementToDate').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,

					});
					//Defect #154010
					var tableSelector = $('table#itemDetails thead tr th');
					tableSelector.each(function(i){
						if(i <= 1) {
							$(this).append('<span class="mand">*</span>');
						} else {
							return false;
						}
					});
	 
	 
	
});

function openDataEntrySuiteForm(mode) {
	
	var errorList = [];
	/*
	 * var url = "Milestone.html?checkForProjId"; var isDefaulValue =
	 * __doAjaxRequest(url, 'POST', {}, false, 'json');
	 * 
	 * if (isDefaulValue == "N") {
	 * errorList.push(getLocalMessage("project.not.defined"));
	 * displayErrorsOnPage(errorList); } else {
	 */
	var divName = formDivName;
	var requestData = {
		"mode" : mode
	}
	var url = "dataEntrySuites.html?createDataEntrySuite";
	var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
	// }

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
				
				//$(this).find("input:text:eq(0)").attr("id", "trdQuantity" + i);
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
				
				//$(this).find("input:text:eq(0)").attr("name", "tradeMasterDetailDTO.tradeLicenseItemDetailDTO["+i+"].trdQuantity");
				$(this).find("input:text:eq(0)").attr("name", "tradeMasterDetailDTO.tradeLicenseItemDetailDTO["+i+"].trdUnit");


			});
}

function validateitemDetailsTable(errorList) {
	var subCategory = [];
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
								
								//var tradeQuantity = $("#trdQuantity" + i).val();
								var tradeUnit = $("#trdUnit" + i).val();
								var level = 1;

							} else {
								var utp = i;
								utp = i * 6;
								var itemCode1 = $("#triCod" + (utp + 1)).val();
								var itemCode2 = $("#triCod" + (utp + 2)).val();
								var itemCode3 = $("#triCod" + (utp + 3)).val();
								var itemCode4 = $("#triCod" + (utp + 4)).val();
								var itemCode5 = $("#triCod" + (utp + 5)).val();
								
								//var tradeQuantity = $("#trdQuantity" + i).val();
								var tradeUnit = $("#trdUnit" + i).val();
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
							if (tradeUnit == "" || tradeUnit == undefined
									|| tradeUnit == "0") {
								errorList
										.push(getLocalMessage("trade.enter.item.value")
												+ " " + (i + 1));
							}else {
								if (subCategory.includes(itemCode2)) {
									errorList
											.push(getLocalMessage("tradelicense.validation.dup.subcategory")
													+ (i + 1));
								}
								if (errorList.length == 0) {
									subCategory.push(itemCode2);
								}
							}
							
							if (rowCount <= 2) {
								var utp = i+1;
								
								if(($('option:selected', $("#triCod"+utp)).attr('code')) == 'STR'){
									
									
									/*if (tradeQuantity == '0' || tradeQuantity == undefined
											|| tradeQuantity == "") {
										errorList
												.push(getLocalMessage("tradelicense.validation.quantity")
														+" " +level);
									}*/
									if (tradeUnit == '0' || tradeUnit == undefined
											|| tradeUnit == "") {
										errorList
												.push(getLocalMessage("tradelicense.validation.unit")
														+" " +level);
									}
									}
								}
								 else {
										var utp = i;
										utp = i * 6;
										if(($('option:selected', $("#triCod"+(utp+1))).attr('code')) == 'STR'){
											
											/*if (tradeQuantity == '0' || tradeQuantity == undefined
													|| tradeQuantity == "") {
												errorList
														.push(getLocalMessage("tradelicense.validation.quantity")
																+" " +level);
											}*/
											if (tradeUnit == '0' || tradeUnit == undefined
													|| tradeUnit == "") {
												errorList
														.push(getLocalMessage("tradelicense.validation.unit")
																+" " +level);
											}
											}
								 }
							
						});
	return errorList;
}

function getOwnerTypeDetails(type) {
	
	
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
			"ownershipType" : ownerType,
			"type" : type,
		};
		var URL = 'dataEntrySuites.html?getOwnershipTypeDiv';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$("#owner").html(returnData);
		$("#owner").show();
	} else {
		$("#owner").html("");
	}
}

function saveDataEntrySuiteForm(obj) {
	var errorList = [];
	errorList = validateMarketLicenseForm(errorList);
	errorList = errorList.concat(validateOwnerDetailsTable());
	errorList = errorList.concat(validateitemDetailsTable());
	
	if (errorList.length == 0 && $("#saveMode").val() == 'C') {
		var oldLiscenseNo = $("#trdOldlicno").val().trim();
		var checkUrl = "dataEntrySuites.html?validateOldLiscenseNo";
		var postdata = "oldLiscenseNo=" + oldLiscenseNo;
		var returnData = __doAjaxRequest(checkUrl, 'POST', postdata, '', '');
		if (returnData != null && returnData != "") {
			errorList
					.push(getLocalMessage('trade.old.licNo.present'));

		}
	}
	if (errorList.length == 0) {
		return saveOrUpdateForm(obj, "", "dataEntrySuites.html", 'saveform');
	} else {
		displayErrorsOnPage(errorList);
	}

}

function validateMarketLicenseForm(errorList) {
	
	var licenseType = $("#trdLictype").val();
	var businessName = $("#trdBusnm").val();
	var trdBusadd = $("#trdBusadd").val();
	var fromDateValue = $("#trdLicfromDate").val();
	var toDateValue = $("#trdLictoDate").val();
	var ownershipType = $("#trdFtype").val();
	var propertyNo = $("#propertyNo").val();
	var oldLicenseNo = $("#trdOldlicno").val();
	var trdOwnerNm= $("#trdOwnerNm").val();
	var pmPropNo = $("#pmPropNo").val();
	var gstNo=$("#app_gstNumber").val();
	var trdBusnm=$("#trdBusnm").val();
	var businessStartDate = $("#businessStartDate").val();

	
	var propertyActive= $("#propertyActive").val();

		
/*	if (propertyActive == 'Y') {

		if (propertyNo == "0" || propertyNo == undefined || propertyNo == "") {

			errorList
					.push(getLocalMessage("tradelicense.validation.propertyNo"));
		}

		if (trdOwnerNm == "0" || trdOwnerNm == undefined || trdOwnerNm == "") {

			errorList
					.push(getLocalMessage("license.details.propertyOwnerName"));
		}

	}

	else {

		if (pmPropNo == "0" || pmPropNo == undefined || pmPropNo == "") {

			errorList
					.push(getLocalMessage("tradelicense.validation.propertyNo"));
		}

		if (trdOwnerNm == "0" || trdOwnerNm == undefined || trdOwnerNm == "") {

			errorList
					.push(getLocalMessage("license.details.propertyOwnerName"));
		}

	}*/
	
	if(gstNo != 0 && gstNo != '' && gstNo != undefined)
	{
		var regExGst = /^([0-9]){2}([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}([0-9]){1}([Z]){1}([0-9|a-zA-Z]){1}?$/;
		if(!regExGst.test(gstNo) && gstNo !=''){
			errorList.push("Please Enter valid GST No.");
		}
	}
	if (licenseType == "0" || licenseType == undefined || licenseType == "") {

		errorList.push(getLocalMessage("tradelicense.validation.licensetype"));
	}
	if (ownershipType == "0" || ownershipType == undefined
			|| ownershipType == "") {
		errorList
				.push(getLocalMessage("tradelicense.validation.ownershipType"));
	}
     //Defect #108762
	if (oldLicenseNo == "0" || oldLicenseNo == undefined || oldLicenseNo == "") {

		errorList.push(getLocalMessage("tradelicense.validation.oldLicenseNo"));
	}
	if (trdBusadd == "0" || trdBusadd == undefined || trdBusadd == "") {

		errorList.push(getLocalMessage("tradelicense.validation.businessaddress"));
	}
	
	if (trdBusnm == "0" || trdBusnm == undefined || trdBusnm == "") {

		errorList.push(getLocalMessage("tradelicense.validation.businessname"));
	}
     //#138145
	 if (businessStartDate == "" || businessStartDate == null || businessStartDate == undefined) {
		errorList.push(getLocalMessage('tradelicense.validation.businessStartDate'));
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

	return errorList;
}

function searchDataEntry() {
	
	var errorList = [];
	var msg='';
	var licenseType = $("#trdLictype").val();
	var oldLicenseNo = $("#trdOldlicno").val();
	var newLicenseNo = $("#trdLicno").val();
	var trdWard1 = $("#trdWard" + 1).val();
	var trdWard2 = $("#trdWard" + 2).val();
	var trdWard3 = $("#trdWard" + 3).val();
	var trdWard4 = $("#trdWard" + 4).val();
	var trdWard5 = $("#trdWard" + 5).val();	
	var printable= $("#printable").val();
	var pimaryOwner= $("#primaryOwnerName").val();
	var businessName= $("#trdBusnm").val();
	
	if(trdWard1  == undefined){
		trdWard1 = "";
	}
	if(trdWard2  == undefined){
		trdWard2 = "";
	}
	if(trdWard3  == undefined){
		trdWard3 = "";
	}
	if(trdWard4  == undefined){
		trdWard4 = "";
	}
	if(trdWard5  == undefined){
		trdWard5 = "";
	}
	if(pimaryOwner  == undefined){
		pimaryOwner = "";
	}
	if(businessName  == undefined){
		businessName = "";
	}
	
	if (oldLicenseNo != '' || newLicenseNo != '' || trdWard1 != '') {
		
		var requestData = '&licenseType=' + licenseType + '&oldLicenseNo='
		+ oldLicenseNo + '&newLicenseNo=' + newLicenseNo + '&trdWard1='
		+ trdWard1 + '&trdWard2='+ trdWard2 + '&trdWard3='+ trdWard3 + '&trdWard4='+ trdWard4 + '&trdWard5='+ trdWard5+'&pimaryOwner='+pimaryOwner+'&businessName='+businessName;
		
		var table = $('#datatables').DataTable();
		table.rows().remove().draw();
		$(".warning-div").hide();
		var ajaxResponse = doAjaxLoading(
				'dataEntrySuites.html?getDataEntryList', requestData, 'html');
		var prePopulate = JSON.parse(ajaxResponse);
		var result = [];
		$
				.each(
						prePopulate,
						function(index) {
							var obj = prePopulate[index];
                            //Defect #138334
							if(printable=='Y'){
								msg='<button type="button" class="btn btn-warning btn-sm btn-sm margin-right-10" onclick="printApplicantData(\''+ obj.trdId+ '\')"  title="Print"><i class="fa fa-pencil-square-o"></i></button>';
							}
							console.log(obj.licenseType);
							result
									.push([
											obj.licenseType,
											obj.trdOldlicno,
											obj.trdLicno,
											obj.licenseFromDate,
											obj.licenseToDate,
											obj.trdBusnm,
											obj.trdBusadd,
											'<div class="text-center">'
													+ '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10"  onclick="getActionForDefination(\''
													+ obj.trdId
													+ '\',\'V\')" title="View"><i class="fa fa-eye"></i></button>'
													+msg
													+ '<button type="button" class="btn btn-warning btn-sm btn-sm" onclick="getActionForDefination(\''
													+ obj.trdId
													+ '\',\'E\')"  title="Edit"><i class="fa fa-pencil"></i></button>',
											'</div>' ]);
						});
		table.rows.add(result);
		table.draw();
		if (prePopulate.length == 0) {
			errorList
					.push(getLocalMessage("scheme.master.validation.nodatafound"));
			displayErrorsOnPage(errorList);
			$("#errorDiv").show();
		} else {
			$("#errorDiv").hide();
		}
	} else {
		errorList.push(getLocalMessage('tender.search.validation'));
		displayErrorsOnPage(errorList);
	}
}

function getActionForDefination(trdId, formMode) {
	
	var divName = formDivName;
	var url = "dataEntrySuites.html?editDataEntry";
	data = {
		"trdId" : trdId,
		"formMode" : formMode
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
	reOrderItemDetailsSequence();
	prepareTags();
}

function printApplicantData(trdId) {
	debugger;
	var divName = formDivName;
	var url = "DuplicateLicensePrinting.html?printApplicantData";
	data = {
		"appNo" : trdId,
		"actualTaskId" : null,
		"workflowId" :null
	};
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
	reOrderItemDetailsSequence();
	prepareTags();
	
}


function checkOldLiscenseNo(errorList) {
	var oldLiscenseNo = $("#trdOldlicno").val().trim();
	var checkUrl = "dataEntrySuites.html?validateOldLiscenseNo";
	var postdata = "oldLiscenseNo=" + oldLiscenseNo;
	var returnData = __doAjaxRequest(checkUrl, 'POST', postdata, '', '');
	if (status != null && status != "") {
		errorList.push(getLocalMessage('trade.old.licNo.present'));
	}
}

function getPropertyDetails(element) {
	
	var errorList = [];
	var theForm = '#tradeLicenseForm';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'dataEntrySuites.html?getPropertyDetails';
	var response = __doAjaxRequest(URL, 'POST', requestData, false, 'json');

	if (response.pmPropNo != null) {
		$('#primaryOwnerName').val(response.primaryOwnerName);
		$('#usage').val(response.usage);
		$('#totalOutsatandingAmt').val(response.totalOutsatandingAmt);
		$('#trdBusadd').val(response.trdBusadd);
	} else if (response.length == null) {
		errorList.push("No data found");
		displayErrorsOnPage(errorList);
		$('#primaryOwnerName').val("");
		$('#usage').val("");
		$('#totalOutsatandingAmt').val("");
		$('#trdBusadd').val("");
	}
}

function validateOwnerDetailsTable() {
	
	var errorList = [];
	var rowCount = $('#ownerDetail tr').length;	
	var mobNo = [];
	var aadharno=[];
	var email=[];

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
				if(ownerMobileNo!=undefined)
					mobNo.push($("#troMobileno" + i ).val());
				var ownerAdharNo = $("#troAdhno" + i).val();
				if(ownerAdharNo!=undefined)
					aadharno.push($("#troAdhno" + i ).val());
				var emailId = $("#troEmailid" + i).val();
				if(emailId!=undefined)
					email.push($("#troEmailid" + i ).val());
				
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
							
							
							/*if(ownerAdharNo.length !=0 && ownerAdharNo.length<12){
								errorList.push(getLocalMessage('trade.valid.adharno')+" " +constant);
							}*/
							
							/*if (ownerAdharNo != "")
							{
								var adharregex = new RegExp(/^\d{4}\s\d{4}\s\d{4}$/i);
								//var adharRegex = new RegExp(/^[0-9]\d{12}$/i);
								 var valid = adharRegex.test(ownerAdharNo);
								  if (!valid) {
									  errorList.push(getLocalMessage('trade.valid.adharno')+" " +constant);
								  }
							}*/
							
							
						});
	
	
	
	
	
	var j = 1;
	var k = 1;
	var count = 0;
	for (var i = 0; i <= mobNo.length; i++) {
		for (j = i+1; j <= mobNo.length; j++) {
			if (mobNo[i] == mobNo[j]) {
				if (count == 0) {
					errorList.push(getLocalMessage("trade.validation.duplicateMob")+ (j + 1));
				}
				count++;
			}
		}
		k++;
	}
	var m = 1;
	var n = 1;

	var count = 0;
	for (var i = 0; i <= aadharno.length; i++) {
		for (m = i+1; m <= aadharno.length; m++) {
			if (aadharno[i] == aadharno[m]) {
				if(aadharno[i]==""||aadharno[m]=="")
					continue;
				if (count == 0) {
					errorList
							.push(getLocalMessage("trade.validation.duplicateAdhar")
									+ (m + 1));
				}
				count++;
			}
		}
		n++;
	}
	var m = 1;
	var n = 1;

	var count = 0;
	for (var i = 0; i <= email.length; i++) {
		for (m = i+1; m <= email.length; m++) {
			
			if (email[i] == email[m]) {
				if(email[i]==""||email[m]=="")
					continue;
				if (count == 0) {
					errorList
							.push(getLocalMessage("trade.validation.duplicateEmail")
									+ (m + 1));
				}
				count++;
			}
		}
		n++;
	}
	return errorList;
}
