
//to return owner div onselection of drop down menu
function getOwnerTypeDetails() {
	debugger;
var ownerType = $("#ownerTypeId" + " option:selected").attr("code");
if(ownerType!=undefined){
var data = {"ownershipType" : ownerType};
var URL = 'NewPropertyRegistration.html?getOwnershipTypeDiv';
var returnData = __doAjaxRequest(URL, 'POST', data, false);
clearOccupancyDetails();
$("#owner").html(returnData);
$("#owner").show();
$("#proceed").hide();
$("#checkList").show();
$("#checkListDiv").hide();
}
else{
	$("#owner").html("");
}
}
// validation for joint owner second row
$('body').on('focus',".hasPanno", function(){
	jQuery('.hasPanno').keyup(function () { 
	    this.value = this.value.replace(/[^a-z A-Z 0-9]/g,'');
	    $(this).attr('maxlength','10');
	});
	});
	$('body').on('focus',".hasCharacter", function(){
		$('.hasCharacter').keyup(function () { 
		    this.value = this.value.replace(/[^a-z A-Z]/g,'');	   
		});
	});
	$('body').on('focus',".hasMobileNo", function(){
		$('.hasMobileNo').keyup(function () {
		    this.value = this.value.replace(/[^0-9]/g,'');
		    $(this).attr('maxlength','10');
		});
	});
	$('body').on('focus',".hasAadharNo", function(){
	$('.hasAadharNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	});
	});	
	// property share field
	$('body').on('focus',".hasNumber", function(){
		$('.hasNumber').keyup(function () { 
			 this.value = this.value.replace(/[^0-9]/g,'');		   
		});
	});			
	$('body').on('focus',".hasNoSpace", function(){	
	jQuery('.hasNoSpace').keyup(function () { 
	    this.value = this.value.replace(/\s/g,'');
	   
	});
	});


$(function(){
	//$("#owner").hide();
	/*In case of Joint Owner: add new row into Joint Owner table and Reorder ID and Path*/
	$("#jointOwnerTable").on('click','.addCF',function(){

		var errorList = [];	
		if (errorList.length == 0) {
				var content = $(this).closest('tr').clone();
				$(this).closest("tr").after(content);
				content.find("input:text").val('');
				content.find("select").val('0');	
				content.find("input:hidden").remove();
				$('.error-div').hide();				
				reOrderJointOwnerTableSequence('.jointOwner');				
				return false;
			} else {
			displayErrorsOnPage(errorList);
		}
		
	}); 
	
	/*To remove row from Joint Owner table and Reorder ID and Path*/
	$("#jointOwnerTable").on('click','.remCF',function(){
	
		var rowId=this.id;	
		var leftText = rowId.lastIndexOf("_");
		var deletedOwnerRowId = rowId.substring(leftText+1);
		var data = {"deletedOwnerRowId" : deletedOwnerRowId};
		var URL = 'NewPropertyRegistration.html?deleteOwnerTable';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		
		if($("#jointOwnerTable tr").length	!=2 ) {			
			$(this).parent().parent().remove();					
			reOrderJointOwnerTableSequence('.jointOwner');
		}
			 else {
				var errorList = [];
				errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
				displayErrorsOnPage(errorList);
			}
	});
});


/* Function to reorder ID and Path after deletion/addition of Row*/
function reOrderJointOwnerTableSequence(className) {
	$(className).each(function(i) {
		
		$(this).find("input:text:eq(0)").attr("id", "assoOwnerName_"+i);
		$(this).find("select:eq(0)").attr("id", "ownerGender_"+i);
		$(this).find("select:eq(1)").attr("id", "ownerRelation_"+i);

		$(this).find("input:text:eq(1)").attr("id", "assoGuardianName_"+i);
		$(this).find("input:text:eq(2)").attr("id", "assoPropertyShare_"+i);
		$(this).find("input:text:eq(3)").attr("id", "assoMobileno_"+i);
		$(this).find("input:text:eq(4)").attr("id", "emailId_"+i);
		$(this).find("input:text:eq(5)").attr("id", "assoAddharno_"+i);
		$(this).find("input:text:eq(6)").attr("id", "pannumber"+i);
		$(this).find("a:eq(1)").attr("id", "#deleteOwnerRow_"+i);
		//$(this).find("input:hidden:eq(0)").attr("id", "assoPrimaryOwn"+i);

		
		// re-ordering path binding
		$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList["+i+"].assoOwnerName");
		$(this).find("select:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList["+i+"].genderId");
		$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList["+i+"].relationId");
		$(this).find("input:text:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList["+i+"].assoGuardianName");
		$(this).find("input:text:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList["+i+"].propertyShare");		
		$(this).find("input:text:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList["+i+"].assoMobileno");
		$(this).find("input:text:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList["+i+"].eMail");
		$(this).find("input:text:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList["+i+"].assoAddharno");
		$(this).find("input:text:eq(6)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList["+i+"].assoPanno");
		
		//$(this).find("input:hidden:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList["+i+"].assoOType");

	});
	
}

function fnValidatePAN(Obj) {
	
	$('.error-div').hide();
	var errorList = [];	
    if (Obj == null) Obj = $('#pannumber').val();
    if (Obj.value != "") {
        ObjVal = Obj.value;
        var panPat = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/;
        var code = /([C,P,H,F,A,T,B,L,J,G])/;
        var code_chk = ObjVal.substring(3,4);
        if (ObjVal.search(panPat) == -1) {
        	errorList.push('Invaild PAN Number');
        	$('#pannumber').val("");
        }else if(code.test(code_chk) == false) {
        	errorList.push('Invaild PAN Number');
        	$('#pannumber').val("");
        }
    }
    
    if (errorList.length > 0) {   	
    	displayErrorsOnPage(errorList);
	} 
};

function clearOccupancyDetails(){
	
	var rowCount = $('#unitDetailTable tr').length;
	if(rowCount > 3){
		for(var i=0; i<rowCount; i++){
			$("#occupierName"+i).val('');
			$('#assdOccupancyType' + i).val('0').trigger('chosen:updated');
		}
	}else{
		$("#occupierName").val('');
		$('#assdOccupancyType').val('0').trigger('chosen:updated');
	}
	
	
}