	$(function() {
	/* To add new Row into table */
		$("#ownerDetail").on('click','.addCF',function(){

		
		var errorList = [];
		errorList = validateOwnerDetailsTable(errorList);
		if (errorList.length == 0) {

			fileCountUpload();//155986
			//var content = $("#ownerDetail").find('tr:eq(1)').clone();
			//$("#ownerDetail").append(content);

			content.find("input:text").val('');
			content.find("select").val('');
			content.find("textarea").val('');

			//content.find("select").val('0');
			content.find("input:hidden").val('');
			$('.error-div').hide();
			reOrderOwnerDetailsSequence('.appendableClass'); // reorder id and Path
			hasCharacter();
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});
	
function hasCharacter() {
	$('.hasCharacter').keyup(function () { 
		this.value = this.value.replace(/[^a-z A-Z]/g,'');
	});
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

							//var ownerName = $("#troName" + i).val();
							var ownerFristname=$("#troFirstName"+i).val();
							var ownerMiddlename=$("#troMiddleName"+i).val();
							var ownerLastname=$("#troLastName"+i).val();
							//var fatherhusbandName = $("#troMname" + i).val();
							var fatherhusbandFristname=$("#troMfristname"+i).val();
							var fatherhusbandMiddlename=$("#troMmiddlename"+i).val();
							var fatherhusbandLastname=$("#troMlastname"+i).val();
							var ownerGender = $("#troGen" + i).val();
							var ownerAddress = $("#troAddress" + i).val();
							var ownerMobileNo = $("#troMobileno" + i).val();
							var ownerAdharNo = $("#troAdhno" + i).val();
							var emailId = $("#troEmailid" + i).val();
							
							var constant = 1;
			}
			else{
				//var ownerName = $("#troName" + i).val();
				var ownerFristname=$("#troFirstName"+i).val();
				var ownerMiddlename=$("#troMiddleName"+i).val();
				var ownerLastname=$("#troLastName"+i).val();
				//var fatherhusbandName = $("#troMname" + i).val();
				var fatherhusbandFristname=$("#troMfristname"+i).val();
				var fatherhusbandMiddlename=$("#troMmiddlename"+i).val();
				var fatherhusbandLastname=$("#troMlastname"+i).val();
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
							/*if (ownerName == '0' || ownerName == undefined
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
							}*/
							if(ownerFristname == '0' || ownerFristname == undefined || ownerFristname == ""){
								errorList
								.push(getLocalMessage("tradelicense.validation.ownerfname")
										+" " +constant);
							}
							
							if(ownerLastname == '0' || ownerLastname == undefined || ownerLastname == ""){
								errorList
								.push(getLocalMessage("tradelicense.validation.ownerlname")
										+" " +constant);
							}
							/*if(fatherhusbandFristname == '0' || fatherhusbandFristname == undefined || fatherhusbandFristname == ""){
								errorList
								.push(getLocalMessage("tradelicense.validation.fatherhusbandfname")
										+" " +constant);
							}
							
							if(fatherhusbandLastname == '0' || fatherhusbandLastname == undefined || fatherhusbandLastname == ""){
								errorList
								.push(getLocalMessage("tradelicense.validation.fatherhusbandlname")
										+" " +constant);
							}*/
							
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
							
							/*if (ownerAdharNo == "" || ownerAdharNo == undefined || ownerAdharNo == "0") {
								errorList.push(getLocalMessage("tradelicense.validation.ownerAdharNo")
										+" " +constant);
							}*/
							
							if (ownerMobileNo == "" || ownerMobileNo == undefined || ownerMobileNo == "0") {
								errorList.push(getLocalMessage("tradelicense.validation.ownerMobileNo")
										+" " +constant);
							}
							else {	
								if (!validateMobile(ownerMobileNo)) 
								{thaneOwnerDataEntry}
							}
							
							
							
							
							if (emailId !="")		
							{
							  var emailRegex = new RegExp(/^([\w\.\-]+)@([\w\-]+)((\.(\w){2,3})+)$/i);
							  var valid = emailRegex.test(emailId);
							   if (!valid) {
								   errorList.push(getLocalMessage('trade.vldnn.emailid')+" " +constant);
							   } 
							}
							
							
							if((ownerAdharNo.length !=0 && ownerAdharNo.length<12) ||(ownerAdharNo.length !=0 && ownerAdharNo.length>12)){
								errorList.push(getLocalMessage('trade.valid.adharno')+" " +constant);
							}
							
							/*if (ownerAdharNo != "")
							{
								var adharregex = new RegExp(/^\d{4}\s\d{4}\s\d{4}$/i);
								//var adharRegex = new RegExp(/^[0-9]\d{
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
	debugger;
	$(classNameFirst).each(
		function(i) 
		{
			
			// id binding
			$(this).find("select:eq(0)").attr("id", "troTitle" + i);
			/*$(this).find("input:text:eq(0)").attr("id", "troName" + i);
			$(this).find("input:text:eq(1)").attr("id", "troMname" + i);*/
			$(this).find("input:text:eq(0)").attr("id","troFirstName"+i);
			$(this).find("input:text:eq(1)").attr("id","troMiddleName"+i);
			$(this).find("input:text:eq(2)").attr("id","troLastName"+i);
			
			$(this).find("input:text:eq(3)").attr("id","troMfristname"+i);
			$(this).find("input:text:eq(4)").attr("id","troMmiddlename"+i);
			$(this).find("input:text:eq(5)").attr("id","troMlastname"+i);
			
			$(this).find("select:eq(1)").attr("id", "troGen" + i);
			$(this).find("textarea:eq(0)").attr("id", "troAddress" + i);
			$(this).find("input:text:eq(6)").attr("id", "troMobileno" + i);
			$(this).find("input:text:eq(7)").attr("id", "troEmailid" + i);
			$(this).find("input:text:eq(8)").attr("id", "troAdhno" + i);
			$(this).find("input[type='file']:eq(0)").attr("id", "uploadedDocumentPath" + i);

			// path binding
			$(this).find("select:eq(0)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troTitle");
			$(this).find("input:text:eq(0)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].ownFname");
			$(this).find("input:text:eq(1)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].ownMname");
			$(this).find("input:text:eq(2)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].ownLname");
			$(this).find("input:text:eq(3)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].gurdFname");
			$(this).find("input:text:eq(4)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].gurdMname");
			$(this).find("input:text:eq(5)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].gurdLname");
			$(this).find("select:eq(1)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troGen");
			$(this).find("textarea:eq(0)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troAddress");
			$(this).find("input:text:eq(6)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troMobileno");
			$(this).find("input:text:eq(7)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troEmailid");
			$(this).find("input:text:eq(8)").attr("name", "tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO["+i+"].troAdhno");
			$(this).find("input[type='file']:eq(0)").attr("name", "tradeMasterDetailDTO.attachments["+i+"].uploadedDocumentPath");
		

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

function validateMobile(mobile) {
	var regexPattern = /^[0-9]\d{9}$/;
	return regexPattern.test(mobile);
}
$('body').on('focus',".hasAadharNo", function(){
	$('.hasAadharNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	});
	});