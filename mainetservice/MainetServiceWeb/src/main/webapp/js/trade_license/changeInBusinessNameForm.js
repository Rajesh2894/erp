var formDivName = '.content-page';
var divName = ".widget-content";


$(document).ready(function() {
	/*$("#resetform").on("click", function() {
		window.location.reload("#changeBusinessNameForm");
	*/
/*	var flag = $("#immediateService").val();
	if (flag == 'N') {
		$("#offlinebutton").hide();
		$("#offlineLabel").hide();
	} else {
		$("#offlinebutton").show();
		$("#offlineLabel").show();
	}
	
	*/
	
	
	reOrderItemDetailsSequence(); 
	
	$(document).on('change', '.tradeCat', function() {
		var p=1;
		var q=0;		
		var errorList = [];
		var type=($('option:selected', $("#triCod"+1)).attr('code'));
		$("#itemDetails tbody tr").each(
				function(i) {
		if(($('option:selected', $("#triCod"+p)).attr('code')) == 'TL'){
		
			$("#trdQuantity"+q).prop("disabled", true); 
			$("#addBtn").hide();
			$(".delButton").hide();
			//$("#trdUnit"+q).prop("disabled", true); 			
		} else{
			$("#trdQuantity"+q).prop("disabled", false); 
			$("#addBtn").show();
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
	

	
});

function getLicenseDetails(e) {
	debugger;
	var errorList = [];
	var theForm = '#changeBusinessNameForm';
	var trdLicno = $("#licenseNo").val();

	errorList = validateChangeBusinessForm(errorList);
	
	
	if (errorList.length == 0) {

	var requestData = {
		'trdLicno' : trdLicno
	}
	var response = __doAjaxRequest(
			'ChangeInBusinessNameForm.html?getLicenseDetails', 'POST',
			requestData, false, 'html');
	
	$('.content').removeClass('ajaxloader');
	$(formDivName).html(response);
	}
	else
		{
		 
				displayErrorsOnPage(errorList);
		
		
		}
}

function validateChangeBusinessForm(errorList) {
	
	var licenseNo = $("#licenseNo").val();

	if (licenseNo == "" || licenseNo == undefined || licenseNo == "") {
		errorList.push(getLocalMessage("trade.validation.licenseNo"));
	}

	return errorList;
}

function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}

function getChecklistAndCharges(obj) {
	
	var errorList = [];
	/*var businessName = $("#newBusinessName").val();
	if (businessName == "" || businessName == undefined || businessName == null) {
		errorList.push("New Trade Name Cannot be Empty");
	}*/

	
	
	errorList = errorList.concat(validateitemDetailsTable());
	
	if (errorList.length == 0) {
		var theForm = '#changeBusinessNameForm';
		var requestData = {};
		requestData = __serializeForm(theForm);
		var URL = 'ChangeInBusinessNameForm.html?getCheckListAndCharges';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false,
				'html');
		if (returnData != null) {
			$('.content').removeClass('ajaxloader');

			$(formDivName).html(returnData);
			prepareTags();
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function generateChallanAndPayment(element) {
	//#129512
	var status;
	saveObj = element;
	var yes = getLocalMessage('license.yes');
	var no = getLocalMessage('license.No');
	var warnMsg = getLocalMessage('payment.popup');

	message = '<p class="text-blue-2 text-center padding-15">' + warnMsg
			+ '</p>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input class="btn btn-success" type=\'button\' value=\'' + yes
			+ '\'  id=\'yes\' ' + ' onclick="onPaymentYes()"/>&nbsp;'
			+ '<input class="btn btn-success " type=\'button\' value=\'' + no
			+ '\'  id=\'no\' ' + ' onclick="closeConfirmBoxForm()"/>'
			+ '</div>';

	$(childDivName).addClass('ok-msg').removeClass('warn-msg');
	$(childDivName).html(message);
	$(childDivName).show();
	$('#yes').focus();
	showModalBox(childDivName);
	return false;

}
//#129512
function onPaymentYes(){
$.fancybox.close();
var errorList = [];
errorList = errorList.concat(validateitemDetailsTable());
var status;

if (errorList.length == 0) {
	if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
			":checked").val() == 'N'
			|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
					.filter(":checked").val() == 'P') {

		status = saveOrUpdateForm(
				saveObj,
				"Your application for change in Business Name saved successfully!",
				'ChangeInBusinessNameForm.html?PrintReport',
				'generateChallanAndPayement');
		/*openChangeInBusinessNameTab(status);*/

	} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")

	.filter(":checked").val() == 'Y') {

		status = saveOrUpdateForm(
				saveObj,
				"Your application for change in Business Name saved successfully!",
				'ChangeInBusinessNameForm.html?redirectToPay',
				'generateChallanAndPayement');	
	} else

	{
		status = saveOrUpdateForm(
				saveObj,
				"Your application for change in Business Name saved successfully!",
				'AdminHome.html', 'generateChallanAndPayement');
	}
} else {
	displayErrors(errorList);

}
//openChangeInBusinessNameTab(status);
}

function openChangeInBusinessNameTab(status) {
	debugger;
	
	if (!status) {
		var URL = 'ChangeInBusinessNameForm.html?getChangeInBusNameLicensePrint';
		var returnData = __doAjaxRequest(URL, 'POST', {}, false);

		var title = 'Change in Business Name License Certificate';
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
		printWindow.document
				.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
		printWindow.document.write(returnData);
		printWindow.document.write('</body></html>');
		printWindow.document.close();

	}
}

function editForm(element)
{
	
	return saveOrUpdateForm(element,
			"Your application Data  saved successfully!!",
			'AdminHome.html', 'saveChangeInBusinessForm');	
}

function saveChangeInBusinessForm(obj) 
{

	var errorList = [];
	errorList = errorList.concat(validateitemDetailsTable());
	
	if (errorList.length == 0)
	{
		
		return saveOrUpdateForm(obj,
				" ", "ChangeInBusinessNameForm.html",
				'getCheckListAndCharges');
		
	}
	else 
	{
		displayErrorsOnPage(errorList);
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
		debugger;
		
	var	tableLength=$("#itemDetails tr").length;
	
	 var currentRow=$(this).closest("tr"); 
   
	 var active=$("#"+currentRow.find("input:hidden:eq(0)").attr("id")).val();
	
		
		if (active==null || active==''|| active=='0' || active==undefined) {
			$(this).parent().parent().remove();
			reOrderItemDetailsSequence(); // reorder id and Path
		} else {
			var errorList = [];
			errorList.push("trade Item can not be deleted");
			displayErrorsOnPage(errorList);
		}
	});
});



function reOrderItemDetailsSequence() {
	
	$("#itemDetails tbody tr").each(
			function(i) {
				
				
				
				debugger;
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
				//$(this).find("select:eq(1)").attr("id", "triCod" + (utp + 2));
				
				$(this).find("input:text:eq(0)").attr("id", "trdUnit" + i);
				//$(this).find("input:text:eq(1)").attr("id", "trdUnit" + i);
				
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
				//$(this).find("input:text:eq(1)").attr("name", "tradeMasterDetailDTO.tradeLicenseItemDetailDTO["+i+"].trdUnit");
				
			});
}

function validateitemDetailsTable(errorList) {
	debugger;
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
								
								var level = 1;
								var tradeQuantity = $("#trdUnit" + i).val();
								//var tradeUnit = $("#trdUnit" + i).val();

							} else {
								var utp = i;
								utp = i * 6;
								var itemCode1 = $("#triCod" + (utp + 1)).val();
								var itemCode2 = $("#triCod" + (utp + 2)).val();
								var itemCode3 = $("#triCod" + (utp + 3)).val();
								var itemCode4 = $("#triCod" + (utp + 4)).val();
								var itemCode5 = $("#triCod" + (utp + 5)).val();
								
								var tradeQuantity = $("#trdUnit" + i).val();
								//var tradeUnit = $("#trdUnit" + i).val();
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
							if (tradeQuantity == "" || tradeQuantity == undefined
									|| tradeQuantity == "0") {
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
							
							
							
							/*if (rowCount <= 2) {
							var utp = i+1;
							
							if(($('option:selected', $("#triCod"+utp)).attr('code')) == 'STR'){
								
								
								if (tradeQuantity == '0' || tradeQuantity == undefined
										|| tradeQuantity == "") {
									errorList
											.push(getLocalMessage("tradelicense.validation.quantity")
													+" " +level);
								}
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
										
										if (tradeQuantity == '0' || tradeQuantity == undefined
												|| tradeQuantity == "") {
											errorList
													.push(getLocalMessage("tradelicense.validation.quantity")
															+" " +level);
										}
										if (tradeUnit == '0' || tradeUnit == undefined
												|| tradeUnit == "") {
											errorList
													.push(getLocalMessage("tradelicense.validation.unit")
															+" " +level);
										}
										}
							 }*/
							
						});
	return errorList;
}
function deleteItemDetailEntry(obj, ids,status) {
	
	deleteTableRow('itemDetails', obj, ids);
	$('#itemDetails').DataTable().destroy();
	$("#itemDetails tbody tr").each(function(i) {

	});
}