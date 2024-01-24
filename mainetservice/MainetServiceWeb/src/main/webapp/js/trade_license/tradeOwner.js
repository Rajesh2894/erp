
$(document).ready(function() {


	 reOrderOwnerDetailsSequence('.appendableClass');
	

});


$(function() {
	/* To add new Row into table */
		$("#ownerDetail").on('click','.addCF',function(){

		
		var errorList = [];
		errorList = validateOwnerDetailsTable(errorList);
		if (errorList.length == 0) {

			//fileCountUpload();
			var content = $("#ownerDetail").find('tr:eq(1)').clone();
			$("#ownerDetail").append(content);

			content.find("input:text").val('');
			content.find("select").val('');
			content.find("textarea").val('');

			//content.find("select").val('0');
			content.find("input:hidden").val('');
			$('.error-div').hide();
			reOrderOwnerDetailsSequence('.appendableClass'); // reorder id and Path
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});

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
						/*	if (ownerAdharNo == "" || ownerAdharNo == undefined || ownerAdharNo == "0") {
								errorList.push(getLocalMessage("tradelicense.validation.ownerAdharNo")
										+" " +constant);
							}*/
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


function reOrderOwnerDetailsSequence(classNameFirst) {
	
	$(classNameFirst).each(
		function(i) 
		{
			
			// id binding
			$(this).find("select:eq(0)").attr("id", "troTitle" + i);
			$(this).find("input:text:eq(0)").attr("id", "troName" + i);
			$(this).find("input:text:eq(1)").attr("id", "troMname" + i);
			$(this).find("select:eq(1)").attr("id", "troGen" + i);
			$(this).find("textarea:eq(0)").attr("id", "troAddress" + i);
			$(this).find("input:text:eq(2)").attr("id", "troMobileno" + i);
			$(this).find("input:text:eq(3)").attr("id", "troEmailid" + i);
			$(this).find("input:text:eq(4)").attr("id", "troAdhno" + i);

			// path binding
			$(this).find("select:eq(0)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troTitle");
			$(this).find("input:text:eq(0)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troName");
			$(this).find("input:text:eq(1)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troMname");
			$(this).find("select:eq(1)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troGen");
			$(this).find("textarea:eq(0)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troAddress");
			$(this).find("input:text:eq(2)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troMobileno");
			$(this).find("input:text:eq(3)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troEmailid");
			$(this).find("input:text:eq(4)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troAdhno");

		

		});
}

/*To delete Row From the table*/ 
$("#ownerDetail").on('click', '.delButton', function() {
	
	
	if ($("#ownerDetail tr").length != 2) {
		$(this).parent().parent().remove();
		/*$(deleteRow).closest('tr').remove();*/
		reOrderOwnerDetailsSequence('.appendableClass');
	} else {
		var errorList = [];
		errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
		displayErrorsOnPage(errorList);
	}
});


function fileCountUpload() {
	
	var errorList = [];
	  errorList = validateOwnerDetailsTable(errorList);
	  if (errorList.length == 0) {
		  
		  /*var content = $("#ownerDetail").find('tr:eq(1)').clone();
			$("#ownerDetail").append(content);

			content.find("input:text").val('');
			content.find("select").val('');
			content.find("textarea").val('');

			//content.find("select").val('0');
			content.find("input:hidden").val('');
			$('.error-div').hide();
			reOrderOwnerDetailsSequence('.appendableClass'); // reorder id and Path
		  */
		  
	var row = $("#ownerDetail tbody .appendableClass").length;
	$("#length").val(row);
	//var formName = findClosestElementId(obj, 'form');
	var theForm = '#tradeLicenseForm';
	var requestData = __serializeForm(theForm);

	var response = __doAjaxRequest('TradeApplicationForm.html?fileCountUpload',
			'POST', requestData, false, 'html');
	$("#owner").html(response);
	
	/*var content = $("#ownerDetail").find('tr:eq(1)').clone();
	content.find("input:text").val('');
	content.find("select").val('');
	content.find("textarea").val('');
	content.find("input:hidden").val('');*/
	prepareTags();
	  }
	  else {
			//$('#ownerDetail').DataTable();
			displayErrorsOnPage(errorList);
		}
}



$('body').on('focus',".hasAadharNo", function(){
	$('.hasAadharNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	});
	});

/*function doFileDeletion(obj, id) {
	
		requestData = {
			"id" : id
		};
		url = 'TradeApplicationForm.html?doEntryDeletion';
		var row = $("#ownerDetail tbody .appendableClass").length;
		if (row != 1) {
			$("#ownerDetail tbody .appendableClass").parent().parent().remove();
			var response = __doAjaxRequest(url, 'POST', requestData, false, 'html');
		}

	}*/
