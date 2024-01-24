//Added new ABM3223 of Defect #126626
$(function() {
	//$("#owner").hide();
	   //$("#ownerType").hide();
	/* To add new Row into table */
		$("#ownerDetail").on('click','.addCF',function(){
              
		var errorList = [];
		errorList = validateOwnerDetailsTable(errorList);
		if (errorList.length == 0) {
			var content = $("#ownerDetail").find('tr:eq(1)').clone();
			$("#ownerDetail").append(content);

			content.find("input:text").val('');
			content.find("select").val('');
			content.find("textarea").val('');

			content.find("input:hidden").val('');
			$('.error-div').hide();
			reOrderOwnerDetailsSequence('.appendableClass'); // reorder id and Path
			return false;
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});

function validateOwnerDetailsTable() {
	var errorList = [];
	var rowCount = $('#ownerDetail tr').length;	
	var mobNo = [];
	var email=[];
	var aadharno=[];
	var panno=[];
	

	if ($.fn.DataTable.isDataTable('#ownerDetail')) {
		$('#ownerDetail').DataTable().destroy();
	}
	if (errorList == 0)
		$("#ownerDetail tbody tr").each(function(i) {
			
			if(rowCount<=2){

							var ownerName = $("#agencyOwner" + i).val();
							var ownerMobileNo = $("#agencyContactNo" + i).val();
							var emailId = $("#agencyEmail" + i).val();
							var ownerAdharNo = $("#uidNo" + i).val();
							var pancardno = $("#panNumber" + i).val();
							
							
							var constant = 1;
			}
			else{
				var ownerName = $("#agencyOwner" + i).val();
				var ownerMobileNo = $("#agencyContactNo" + i).val();
				if(ownerMobileNo!=undefined)
					mobNo.push($("#agencyContactNo" + i ).val());
				var emailId = $("#agencyEmail" + i).val();
				if(emailId!=undefined)
					email.push($("#agencyEmail" + i ).val());
				var ownerAdharNo = $("#uidNo" + i).val();
				if(ownerAdharNo!=undefined)
					aadharno.push($("#uidNo" + i ).val());
				var pancardno = $("#panNumber" + i).val();
				if(pancardno!=undefined)
					panno.push($("#panNumber" + i ).val());
				
				
				var constant = i+1;
			}
							if (ownerName == '0' || ownerName == undefined
									|| ownerName == "") {
								errorList
										.push(getLocalMessage("tradelicense.validation.ownername")
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
							 if (emailId == "" || emailId == undefined
									    || emailId == null) {
									errorList
										.push(getLocalMessage('agency.registration.validate.agency.emailId'));
								    }else{
								    	if (emailId !="")		
										{
										  var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
										  var valid = emailRegex.test(emailId);
										   if (!valid) {
											   errorList.push(getLocalMessage('trade.vldnn.emailid')+" " +constant);
										   } 
										}
								    }
							
							if(ownerAdharNo.length !=0 && ownerAdharNo.length<12){
								errorList.push(getLocalMessage('trade.valid.adharno')+" " +constant);
							}
							
							if(pancardno.length !=0 && pancardno.length<10){
								errorList.push(getLocalMessage('adh.validate.panNumber')+" " +constant);
							}
							if (pancardno != "")
							{
								var regpan = new RegExp(/^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/);
								 var valid = regpan.test(pancardno);
								  if (!valid) {
									  errorList.push(getLocalMessage('adh.validate.panNumber')+" " +constant);
								  }
							}
			
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
	
	var q = 1;
	var r = 1;

	var count = 0;
	for (var i = 0; i <= panno.length; i++) {
		for (q = i+1; q <= panno.length; q++) {
			if (panno[i] == panno[q]) {
				if(panno[i]==""||panno[q]=="")
					continue;
				if (count == 0) {
					errorList
							.push(getLocalMessage("adh.duplicate.panNo.not.allow")
									+ (q + 1));
				}
				count++;
			}
		}
		r++;
	}
	return errorList;
}

function validateMobile(mobile) {
	var regexPattern = /^[0-9]\d{9}$/;
	return regexPattern.test(mobile);
}


function reOrderOwnerDetailsSequence(appendableClass) {
	
	$(appendableClass).each(
		function(i) 
		{
			
			// id binding
			$(this).find("input:text:eq(0)").attr("id", "agencyOwner" + i);
			$(this).find("input:text:eq(1)").attr("id", "agencyContactNo" + i);
			$(this).find("input:text:eq(2)").attr("id", "agencyEmail" + i);
			$(this).find("input:text:eq(3)").attr("id", "uidNo" + i);
			$(this).find("input:text:eq(4)").attr("id", "panNumber"+i);
			$(this).find("a:eq(1)").attr("id", "#deleteOwnerRow_"+i);

			// path binding
			
			
			$(this).find("input:text:eq(0)").attr("name", "agencyRequestDto.masterDtolist[" + i + "].agencyOwner");
			$(this).find("input:text:eq(1)").attr("name", "agencyRequestDto.masterDtolist[" + i + "].agencyContactNo");
			$(this).find("input:text:eq(2)").attr("name", "agencyRequestDto.masterDtolist[" + i + "].agencyEmail");
			$(this).find("input:text:eq(3)").attr("name", "agencyRequestDto.masterDtolist[" + i + "].uidNo");
			$(this).find("input:text:eq(4)").attr("name", "agencyRequestDto.masterDtolist[" + i + "].panNumber");
		

		});
}

/*To delete Row From the table*/ 
$("#ownerDetail").on('click', '.remCF', function() {
	
	/*var rowId=this.id;	
	var leftText = rowId.lastIndexOf("_");
	var deletedOwnerRowId = rowId.substring(leftText+1);
	var data = {"deletedOwnerRowId" : deletedOwnerRowId};
	var URL = 'AgencyRegistration.html.html?deleteOwnerTable';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);*/
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


$('body').on('focus',".hasAadharNo", function(){
	$('.hasAadharNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	});
	});

/*function fileCountUpload() {
	
	var errorList = [];
	  errorList = validateOwnerDetailsTable(errorList);
	  if (errorList.length == 0) {
		  
		  var content = $("#ownerDetail").find('tr:eq(1)').clone();
			$("#ownerDetail").append(content);

			content.find("input:text").val('');
			content.find("select").val('');
			content.find("textarea").val('');

			//content.find("select").val('0');
			content.find("input:hidden").val('');
			$('.error-div').hide();
			reOrderOwnerDetailsSequence('.appendableClass'); // reorder id and Path
		  
		  
	//var row = $("#ownerDetail tbody .appendableClass").length;
	//$("#length").val(row);
	//var formName = findClosestElementId(obj, 'form');
	var theForm = '#AgencyRegistration';
	var requestData = __serializeForm(theForm);

	var response = __doAjaxRequest('AgencyRegistration.html?fileCountUpload',
			'POST', requestData, false, 'html');
	$("#owner").html(response);
	
	var content = $("#ownerDetail").find('tr:eq(1)').clone();
	content.find("input:text").val('');
	content.find("select").val('');
	content.find("textarea").val('');
	content.find("input:hidden").val('');
	prepareTags();
	  }
	  else {
			//$('#ownerDetail').DataTable();
			displayErrorsOnPage(errorList);
		}
}*/
