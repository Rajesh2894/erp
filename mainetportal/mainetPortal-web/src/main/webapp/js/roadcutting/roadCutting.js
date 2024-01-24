$(function() {
	
	$("#roadRoute").on('click', '.addRoad', function() {
		
		var errorList = [];
		errorList = validateUnitDetailTable(errorList);
		if (errorList.length == 0){
		var content = $("#roadRoute").find('tr:eq(1)').clone();
		$("#roadRoute").append(content);
		reOrderTableIdSequences();
		content.find("input:text:eq(0)").val('');
		content.find("input:text:eq(1)").val('');
		content.find("input:text:eq(2)").val('1');
		content.find("input:text:eq(3)").val('');
		content.find("input:text:eq(4)").val('');
		content.find("input:text:eq(5)").val('');
		content.find("input:text:eq(6)").val('');
		content.find("input:text:eq(7)").val('1');
		content.find("select:eq(1)").val('');
		content.find("input:hidden").val('');
		}
		else
			{
			displayErrorsOnPage(errorList);
			}

	});

	$("#roadRoute").on('click', '.remRoad', function() {
		
		if ($("#roadRoute tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderTableIdSequences();
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("mimimum row required"));
			displayErrorsOnPageView(errorList);
			//alert("You cannot delete first row");
		}

	});

});

function reOrderTableIdSequences() {
	
	$('.appendableClass').each(
			function(i) {
				$(this).find("select:eq(0)").attr("id",
						"roadCuttingDto.roadList" + i + ".typeOfTechnology");
				$(this).find("input:text:eq(0)").attr("id",
						"roadCuttingDto.roadList" + i + ".roadRouteDesc");
				$(this).find("input:text:eq(1)").attr("id",
						"roadCuttingDto.roadList" + i + ".rcdEndpoint");
				$(this).find("select:eq(1)").attr("id",
						"roadCuttingDto.roadList" + i + ".roadType");
				$(this).find("input:text:eq(2)").attr("id",
						"roadCuttingDto.roadList" + i + ".numbers");
				$(this).find("input:text:eq(3)").attr("id",
						"roadCuttingDto.roadList" + i + ".length");
				$(this).find("input:text:eq(4)").attr("id",
						"roadCuttingDto.roadList" + i + ".breadth");
				$(this).find("input:text:eq(5)").attr("id",
						"roadCuttingDto.roadList" + i + ".height");
				$(this).find("input:text:eq(6)").attr("id",
						"roadCuttingDto.roadList" + i + ".diameter");
				$(this).find("input:text:eq(7)").attr("id",
						"roadCuttingDto.roadList" + i + ".quantity");

				/*$(this).find("input:button:eq(4)").attr("id", "delButton"+ i);
				$(this).parents('tr').find('.delButton').attr("id", "delButton"+ i);*/

				$(this).find("select:eq(0)").attr("name",
						"roadCuttingDto.roadList[" + i + "].typeOfTechnology");
				$(this).find("input:text:eq(0)").attr("name",
						"roadCuttingDto.roadList[" + i + "].roadRouteDesc");
				$(this).find("input:text:eq(1)").attr("name",
						"roadCuttingDto.roadList[" + i + "].rcdEndpoint");
				$(this).find("select:eq(1)").attr("name",
						"roadCuttingDto.roadList[" + i + "].roadType");
				$(this).find("input:text:eq(2)").attr("name",
						"roadCuttingDto.roadList[" + i + "].numbers");
				$(this).find("input:text:eq(3)").attr("name",
						"roadCuttingDto.roadList[" + i + "].length");
				$(this).find("input:text:eq(4)").attr("name",
						"roadCuttingDto.roadList[" + i + "].breadth");
				$(this).find("input:text:eq(5)").attr("name",
						"roadCuttingDto.roadList[" + i + "].height");
				$(this).find("input:text:eq(6)").attr("name",
						"roadCuttingDto.roadList[" + i + "].diameter");
				$(this).find("input:text:eq(7)").attr("name",
						"roadCuttingDto.roadList[" + i + "].quantity");
				$(this).find("a:eq(0)").attr("onclick","openUploadForm("+i+",'A'"+")");
				
				

			});
}

function verifyDecimalNo(obj) {
	obj.value = obj.value.replace(/[^\d.]/g, '') // numbers and decimals only
	.replace(/(\..*)\./g, '$1') // decimal can't exist more than once
	.replace(/(\.[\d]{6})./g, '$1'); // max 2 digits after decimal
}

function displayErrorsOnPageView(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();

	return false;
}

function localDetails() {
	
	if ($('input:checkbox[id=localDetail]').is(':checked')) {
		var applicantCompName1 = $("#applicantCompName1").val();
		var companyAddress1 = $("#companyAddress1").val();
		var personName1 = $("#personName1").val();
		var personAddress1 = $("#personAddress1").val();
		var faxNumber1 = $("#faxNumber1").val();
		var telephoneNo1 = $("#telephoneNo1").val();
		var personMobileNo1 = $("#personMobileNo1").val();
		var personEmailId1 = $("#personEmailId1").val();
		$("#companyName2").val(applicantCompName1);
		$("#companyAddress2").val(companyAddress1);
		$("#personName2").val(personName1);
		$("#personAddress2").val(personAddress1);
		$("#faxNumber2").val(faxNumber1);
		$("#telephoneNo2").val(telephoneNo1);
		$("#personMobileNo2").val(personMobileNo1);
		$("#personEmailId2").val(personEmailId1);
	} else {
		$("#companyName2").val(null);
		$("#companyAddress2").val(null);
		$("#personName2").val(null);
		$("#personAddress2").val(null);
		$("#faxNumber2").val(null);
		$("#telephoneNo2").val(null);
		$("#personMobileNo2").val(null);
		$("#personEmailId2").val(null);
	}

}
function saveRoadCuttingInfo(element) {
	
	var errorList = [];
	errorList = validateRoadCuttingForm(errorList);
	if (errorList.length == 0) {
		
		if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(
				":checked").val() == 'N'
				|| $("input:radio[name='offlineDTO.onlineOfflineCheck']")
						.filter(":checked").val() == 'P') {
			return saveOrUpdateForm(element,
					"Your application for Road Cutting saved successfully!",
					'RoadCutting.html?PrintReport', 'saveRoadCutting');
		} else if ($("input:radio[name='offlineDTO.onlineOfflineCheck']")
				.filter(":checked").val() == 'Y') {
			return saveOrUpdateForm(element,
					"Your application for Road Cutting saved successfully!",
					'RoadCutting.html?redirectToPay', 'saveRoadCutting');
		}
		else{
			errorList.push(getLocalMessage('Please select any mode of payment'));
			displayErrorsOnPage(errorList);
		}
	} else {
		displayErrorsOnPage(errorList);
	}
}

function validateRoadCuttingForm(errorList) {

	var applicantCompName1 = $("#applicantCompName1").val();
	var companyAddress1 = $("#companyAddress1").val();
	var personName1 = $("#personName1").val();
	var personAddress1 = $("#personAddress1").val();
	var faxNumber1 = $("#faxNumber1").val();
	var telephoneNo1 = $("#telephoneNo1").val();
	var personMobileNo1 = $("#personMobileNo1").val();
	var personEmailId1 = $.trim($("#personEmailId1").val());

	var companyName2 = $("#companyName2").val();
	var companyAddress2 = $("#companyAddress2").val();
	var personName2 = $("#personName2").val();
	var personAddress2 = $("#personAddress2").val();
	var personMobileNo2 = $("#personMobileNo2").val();
	var faxNumber2 = $("#faxNumber2").val();
	var telephoneNo2 = $("#telephoneNo2").val();
	var personEmailId2 = $.trim($("#personEmailId2").val());

	var contractorName = $("#contractorName").val();
	var contractorAddress = $("#contractorAddress").val();
	var contractorContactPerName = $("#contractorContactPerName").val();
	var contracterContactPerMobileNo = $("#contracterContactPerMobileNo").val();
	var contractorEmailId = $.trim($("#contractorEmailId").val());
	var codWard1 = $("#codWard1").val();
	var codWard2 = $("#codWard2").val();
	var totalCostOfproject = $("#totalCostOfproject").val();
	var estimteForRoadDamgCharge = $("#estimteForRoadDamgCharge").val();

	if (applicantCompName1 == "0" || applicantCompName1 == undefined
			|| applicantCompName1 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.applicantcompname1'));
	}
	if (companyAddress1 == "0" || companyAddress1 == undefined
			|| companyAddress1 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.companyaddress1'));
	}
	if (personName1 == "0" || personName1 == undefined || personName1 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.personname1'));
	}
	if (personAddress1 == "0" || personAddress1 == undefined
			|| personAddress1 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.personaddress1'));
	}
	if (faxNumber1 == "0" || faxNumber1 == undefined || faxNumber1 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.faxnumber1'));
	}
	if (telephoneNo1 == "0" || telephoneNo1 == undefined || telephoneNo1 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.telephoneno1'));
	}
	if (personMobileNo1 == "0" || personMobileNo1 == undefined
			|| personMobileNo1 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.personmobileno1'));
	}
	if (personEmailId1 !="")		
	{
	  var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	  var valid = emailRegex.test(personEmailId1);
	   if (!valid) {
		   errorList.push(getLocalMessage('roadcutting.vldnn.emailid'));
	   } 
	}
	if (companyName2 == "0" || companyName2 == undefined || companyName2 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.companyname2'));
	}
	if (companyAddress2 == "0" || companyAddress2 == undefined
			|| companyAddress2 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.companyaddress2'));
	}
	if (personName2 == "0" || personName2 == undefined || personName2 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.personname2'));
	}
	if (personAddress2 == "0" || personAddress2 == undefined
			|| personAddress2 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.personaddress2'));
	}
	if (personMobileNo2 == "0" || personMobileNo2 == undefined
			|| personMobileNo2 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.personmobileno2'));
	}
	if (faxNumber2 == "0" || faxNumber2 == undefined || faxNumber2 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.faxnumber2'));
	}
	if (telephoneNo2 == "0" || telephoneNo2 == undefined || telephoneNo2 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.telephoneno2'));
	}
	if (contractorEmailId !="")		
	{
	  var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	  var valid = emailRegex.test(contractorEmailId);
	   if (!valid) {
		   errorList.push(getLocalMessage('roadcutting.vldnn.emailid'));
	   } 
	}
	if (contractorName == "0" || contractorName == undefined
			|| contractorName == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.contractorname'));
	}
	if (contractorAddress == "0" || contractorAddress == undefined
			|| contractorAddress == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.contractoraddress'));
	}
	if (contractorContactPerName == "0"
			|| contractorContactPerName == undefined
			|| contractorContactPerName == '') {
		errorList
				.push(getLocalMessage('roadcutting.vldnn.contractorcontactpername'));
	}
	if (contracterContactPerMobileNo == "0"
			|| contracterContactPerMobileNo == undefined
			|| contracterContactPerMobileNo == '') {
		errorList
				.push(getLocalMessage('roadcutting.vldnn.contractercontactpermobileno'));
	}
	if (personEmailId2 !="")		
	{
	  var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
	  var valid = emailRegex.test(personEmailId2);
	   if (!valid) {
		   errorList.push(getLocalMessage('roadcutting.vldnn.emailid'));
	   } 
	}
	if (codWard1 == "0" || codWard1 == undefined || codWard1 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.codward1'));
	}
	if (codWard2 == "0" || codWard2 == undefined || codWard2 == '') {
		errorList.push(getLocalMessage('roadcutting.vldnn.codward2'));
	}
	if (totalCostOfproject == "0" || totalCostOfproject == undefined
			|| totalCostOfproject == '' || totalCostOfproject <= 0) {
		errorList.push(getLocalMessage('roadcutting.vldnn.totalcostofproject'));
	}
	if (estimteForRoadDamgCharge == "0"
			|| estimteForRoadDamgCharge == undefined
			|| estimteForRoadDamgCharge == '' || estimteForRoadDamgCharge <= 0) {
		errorList
				.push(getLocalMessage('roadcutting.vldnn.estimteforroaddamgcharge'));
	}
	var rowCount = $('#roadRoute tr').length;
	for (var i = 0; i < rowCount - 1; i++) {

		var typeOfTechnology = "#roadCuttingDto\\.roadList" + i
				+ "\\.typeOfTechnology";
		var roadRouteDesc = "#roadCuttingDto\\.roadList" + i + "\\.roadRouteDesc";
		var rcdEndpoint = "#roadCuttingDto\\.roadList" + i + "\\.rcdEndpoint";
		var roadType = "#roadCuttingDto\\.roadList" + i + "\\.roadType";
		var numbers = "#roadCuttingDto\\.roadList" + i + "\\.numbers";
		var length = "#roadCuttingDto\\.roadList" + i + "\\.length";
		var breadth = "#roadCuttingDto\\.roadList" + i + "\\.breadth";
		var height = "#roadCuttingDto\\.roadList" + i + "\\.height";
		var daimeter = "#roadCuttingDto\\.roadList" + i + "\\.diameter";
		var quantity = "#roadCuttingDto\\.roadList" + i + "\\.quantity";

		var typeOfTechnology1 = $(typeOfTechnology).val();
		var roadRouteDesc1 = $(roadRouteDesc).val();
		var rcdEndpoint1 = $(rcdEndpoint).val();
		var roadType1 = $(roadType).val();
		var numbers1 = $(numbers).val();
		var length1 = $(length).val();
		var breadth1 = $(breadth).val();
		var height1 = $(height).val();
		var daimeter1 = $(daimeter).val();
		var quantity1 = $(quantity).val();

		if (typeOfTechnology1 == undefined || typeOfTechnology1 == '') {
			errorList
					.push(getLocalMessage("roadcutting.vldnn.typeOfTechnology")
							+ " Row " + (i + 1));
		}
		if (roadRouteDesc1 == undefined || roadRouteDesc1 == '') {
			errorList.push(getLocalMessage("roadcutting.vldnn.roadRouteDesc")
					+ " Row " + (i + 1));
		}
		if (rcdEndpoint1 == undefined || rcdEndpoint1 == '') {
			errorList.push(getLocalMessage("roadcutting.vldnn.rcdEndpoint")
					+ " Row " + (i + 1));
		}
		if (roadType1 == undefined || roadType1 == '') {
			errorList.push(getLocalMessage("roadcutting.vldnn.roadType")
					+ " Row " + (i + 1));
		}
		if (numbers1 == undefined || numbers1 == ''||parseFloat(numbers1).toFixed(0)=="0") {
			errorList.push(getLocalMessage("roadcutting.vldnn.numbers")
					+ " Row " + (i + 1));
		}
		if (length1 == undefined || length1 == ''|| parseFloat(length1).toFixed(0)=="0") {
			errorList.push(getLocalMessage("roadcutting.vldnn.length")
					+ " Row " + (i + 1));
		}
		if (/*breadth1 == undefined || breadth1 == ''||*/parseFloat(breadth1).toFixed(0)=="0") {
			errorList.push(getLocalMessage("roadcutting.vldnn.breadth")
					+ " Row " + (i + 1));
		}
		if (/*height1 == undefined || height1 == ''||*/parseFloat(height1).toFixed(0)=="0") {
			errorList.push(getLocalMessage("roadcutting.vldnn.height")
					+ " Row " + (i + 1));
		}
		if (/*daimeter1 == undefined || daimeter1 == ''||*/parseFloat(daimeter1).toFixed(0)=="0") {
			errorList.push(getLocalMessage("roadcutting.vldnn.daimeter")
					+ " Row " + (i + 1));
		}
		if (/*quantity1 == undefined || quantity1 == ''||*/parseFloat(quantity1).toFixed(0)=="0") {
			errorList.push(getLocalMessage("roadcutting.vldnn.quantity")
					+ " Row " + (i + 1));
		}
	}
	return errorList;
}

function openViewModeForm(obj){
	
	var rowId = obj.split("E")[1];
	var row = jQuery('#gridWaterDisconnection').jqGrid ('getRowData', rowId);
	var colData = row['csCcn'];
	$("#csIdn").val(colData);
	$.fancybox.close();
	
	
}
function saveRoadCuttingForm(element)
{
	var errorList = [];
errorList = validateRoadCuttingForm(errorList);
if(errorList.length == 0)
	{
	saveOrUpdateForm(element, '','','saveform');
	$(".panel-heading").hide();
	$('#submitButtonId').prop('disabled', true);
	$('input[type="text"],textarea,select,input[type="radio"]').attr('disabled',true);
	$("#editButtonId").show();
	}
else
	{
	displayErrorsOnPage(errorList);
	}
		
	
}
function editForm(obj){
	$("#editButtonId").hide();
	$('#submitButtonId').prop('disabled', false);
	$('input[type="text"],textarea,select,input[type="radio"]').attr('disabled',false);
}
//this is for validation of list not null while click on the add button
function validateUnitDetailTable(errorList) {
	var rowCount = $('#roadRoute tr').length;
	$('.appendableClass')
	.each(function(i) {
				
				if (rowCount <= 1) {
					var typeOfTechnology = "#roadCuttingDto\\.roadList" + i
					+ "\\.typeOfTechnology";
			var roadRouteDesc = "#roadCuttingDto\\.roadList" + i + "\\.roadRouteDesc";
			var rcdEndpoint = "#roadCuttingDto\\.roadList" + i + "\\.rcdEndpoint";
			var roadType = "#roadCuttingDto\\.roadList" + i + "\\.roadType";
			var numbers = "#roadCuttingDto\\.roadList" + i + "\\.numbers";
			var length = "#roadCuttingDto\\.roadList" + i + "\\.length";
			var breadth = "#roadCuttingDto\\.roadList" + i + "\\.breadth";
			var height = "#roadCuttingDto\\.roadList" + i + "\\.height";
			var diameter = "#roadCuttingDto\\.roadList" + i + "\\.diameter";
			var quantity = "#roadCuttingDto\\.roadList" + i + "\\.quantity";

			var typeOfTechnology1 = $(typeOfTechnology).val();
			var roadRouteDesc1 = $(roadRouteDesc).val();
			var rcdEndpoint1 = $(rcdEndpoint).val();
			var roadType1 = $(roadType).val();
			var numbers1 = $(numbers).val();
			var length1 = $(length).val();
			var breadth1 = $(breadth).val();
			var height1 = $(height).val();
			var daimeter1 = $(daimeter).val();
			var quantity1 = $(quantity).val();
					var level = 1;
				} else {
					var utp = i;
					utp = i * 6;	
					var typeOfTechnology = "#roadCuttingDto\\.roadList" + i
					+ "\\.typeOfTechnology";
			var roadRouteDesc = "#roadCuttingDto\\.roadList" + i + "\\.roadRouteDesc";
			var rcdEndpoint = "#roadCuttingDto\\.roadList" + i + "\\.rcdEndpoint";
			var roadType = "#roadCuttingDto\\.roadList" + i + "\\.roadType";
			var numbers = "#roadCuttingDto\\.roadList" + i + "\\.numbers";
			var length = "#roadCuttingDto\\.roadList" + i + "\\.length";
			var breadth = "#roadCuttingDto\\.roadList" + i + "\\.breadth";
			var height = "#roadCuttingDto\\.roadList" + i + "\\.height";
			var diameter = "#roadCuttingDto\\.roadList" + i + "\\.diameter";
			var quantity = "#roadCuttingDto\\.roadList" + i + "\\.quantity";

			var typeOfTechnology1 = $(typeOfTechnology).val();
			var roadRouteDesc1 = $(roadRouteDesc).val();
			var rcdEndpoint1 = $(rcdEndpoint).val();
			var roadType1 = $(roadType).val();
			var numbers1 = $(numbers).val();
			var length1 = $(length).val();
			var breadth1 = $(breadth).val();
			var height1 = $(height).val();
			var diameter1 = $(diameter).val();
			var quantity1 = $(quantity).val();
					var level = i + 1;
				}
					if (typeOfTechnology1 == undefined || typeOfTechnology1 == '') {
						errorList
								.push(getLocalMessage("roadcutting.vldnn.typeOfTechnology")
										+ "  " + level);
					}
					if (roadRouteDesc1 == undefined || roadRouteDesc1 == '') {
						errorList.push(getLocalMessage("roadcutting.vldnn.roadRouteDesc")
								+ "  " + level);
					}
					if (rcdEndpoint1 == undefined || rcdEndpoint1 == '') {
						errorList.push(getLocalMessage("roadcutting.vldnn.rcdEndpoint")
								+ "  " + level);
					}
					if (roadType1 == undefined || roadType1 == '') {
						errorList.push(getLocalMessage("roadcutting.vldnn.roadType")
								+ "  " + level);
					}
					if (numbers1 == undefined || numbers1 == ''||parseFloat(numbers1).toFixed(0)=="0") {
						errorList.push(getLocalMessage("roadcutting.vldnn.numbers")
								+ "  " + level);
					}
					if (length1 == undefined || length1 == ''|| parseFloat(length1).toFixed(0)=="0") {
						errorList.push(getLocalMessage("roadcutting.vldnn.length")
								+ "  " + level);
					}
					if (/*breadth1 == undefined || breadth1 == ''||*/parseFloat(breadth1).toFixed(0)=="0") {
						errorList.push(getLocalMessage("roadcutting.vldnn.breadth")
								+ "  " + level);
					}
					if (/*height1 == undefined || height1 == ''||*/parseFloat(height1).toFixed(0)=="0") {
						errorList.push(getLocalMessage("roadcutting.vldnn.height")
								+ "  " +level);
					}
					if (/*diameter1 == undefined || diameter1 == ''||*/parseFloat(diameter1).toFixed(0)=="0") {
						errorList.push(getLocalMessage("roadcutting.vldnn.daimeter")
								+ " " + level);
					}
					if (/*quantity1 == undefined || quantity1 == ''||*/parseFloat(quantity1).toFixed(0)=="0") {
						errorList.push(getLocalMessage("roadcutting.vldnn.quantity")
								+ " " + level);
					}
			});
	return errorList
	
}
function calculateQuantity(element) {
	
	var rowCount = $('#roadRoute tr').length;
	for (var i = 0; i < rowCount - 1; i++) {

		var typeOfTechnology = "#roadCuttingDto\\.roadList" + i
				+ "\\.typeOfTechnology";
		var roadRouteDesc = "#roadCuttingDto\\.roadList" + i + "\\.roadRouteDesc";
		var roadType = "#roadCuttingDto\\.roadList" + i + "\\.roadType";
		var numbers = "#roadCuttingDto\\.roadList" + i + "\\.numbers";
		var length = "#roadCuttingDto\\.roadList" + i + "\\.length";
		var breadth = "#roadCuttingDto\\.roadList" + i + "\\.breadth";
		var height = "#roadCuttingDto\\.roadList" + i + "\\.height";
		var daimeter = "#roadCuttingDto\\.roadList" + i + "\\.diameter";
		var quantity = "#roadCuttingDto\\.roadList" + i + "\\.quantity";

		var typeOfTechnology1 = $(typeOfTechnology).val();
		var roadRouteDesc1 = $(roadRouteDesc).val();
		var roadType1 = $(roadType).val();
		var numbers1 = $(numbers).val();
		var length1 = $(length).val();
		var breadth1 = $(breadth).val();
		var height1 = $(height).val();
		var daimeter1 = $(daimeter).val();
		var quantity1 = $(quantity).val();
		if(length1==undefined ||length1 == '')
			{
			length1=1;
			}
			if(	breadth1==undefined|| breadth1=='')
				{
				breadth1=1;
				}
				if(	height1==undefined||height1=='')
					{
					height1=1;
					}
					if(	daimeter1==undefined||daimeter1=='')
						{
						daimeter1=1;
						}
			{
		var quantityUpdate=numbers1*length1*breadth1*height1*daimeter1
			}
		$(quantity).val(parseFloat(quantityUpdate).toFixed(2));
	}
	
}

function openUploadForm (index,mode) {
	
	var divName = formDivName;
	saveData();
	data = {"index":parseInt(index),"mode":mode};
	var url = "RoadCutting.html?uploadEndPoints";
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}

function deleteEndPoints (index) {
	
	var divName = formDivName;
	saveData();
	data = {"index":parseInt(index)};
	var url = "RoadCutting.html?deleteEndPoints";
	var response = __doAjaxRequest(url, 'post', data, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}

function saveData(){
	var divName = formDivName;
	var requestData = __serializeForm("#RoadCuttingId");
	var url = "RoadCutting.html?bindData";
	var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}
function back (mode) {
	
	var divName = formDivName;
	var requestData = __serializeForm("#RoadCuttingId");
	var url;
	if(mode ==="A"){
		url = "RoadCutting.html?back";
	}else{
		url = "RoadCutting.html?scrBack";
	}
	
	var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
	
	$('.content').removeClass('ajaxloader');
	$(divName).html(response);
}


function startPoints() {
	
	var URL = 'RoadCutting.html?getCoordinates';
	var index = 100+parseInt($("#index").val());
	var data = {"fileIndex":index};
	var returnData = __doAjaxRequest(URL, 'post', data, false, 'json');
	if(returnData[0]!=0){
		$("#sLatitude").val(returnData[0]!=0?returnData[0]:null);
		$("#sLongitude").val(returnData[1]!=0?returnData[1]:null);
	}

}
function endPoints() {
	
	var URL = 'RoadCutting.html?getCoordinates';
	var index = 100+parseInt($("#index").val());
	var data = {"fileIndex":index};
	var returnData = __doAjaxRequest(URL, 'post', data, false, 'json');
	if(returnData[0]!=0){
		$("#eLatitude").val(returnData[0]!=0?returnData[0]:null);
		$("#eLongitude").val(returnData[1]!=0?returnData[1]:null);
	}
}


function otherDeletionTask(obj){
	
	$("#sLatitude").val(null);
	$("#sLongitude").val(null);
}

function showMap() {

	
	var slat = $('#sLatitude').val();
	var slong = $('#sLongitude').val();
	var elat = $('#eLatitude').val();
	var elong = $('#eLongitude').val();
	mapList = [{loc:"Start",lat:slat,long:slong},{loc:"End",lat:elat,long:elong}]
	initMap2(mapList);
}
function initMap2(mapList){
	
	 var locations = [mapList];
	                var map = new google.maps.Map(document.getElementById('map-canvas'), {
	                	 zoom: parseInt(getLocalMessage("RC.map.zoom")),
		                  center:  new google.maps.LatLng((getLocalMessage("RC.lat")), parseFloat(getLocalMessage("RC.long"))),
	                  mapTypeId: google.maps.MapTypeId.ROADMAP
	                });
	                var infowindow = new google.maps.InfoWindow();
	                var marker, i;
	                for (i = 0; i < locations[0].length; i++) {  
		                  marker = new google.maps.Marker({
		                    position: new google.maps.LatLng(locations[0][i].lat, locations[0][i].long),		                   
		                    map: map
		                  });
		                  google.maps.event.addListener(marker, 'mouseover', (function(marker, i) {
		                    return function() {
		                      infowindow.setContent(locations[0][i].loc);
		                      infowindow.open(map, marker);
		                    }
		                  })(marker, i));
	                }
    }