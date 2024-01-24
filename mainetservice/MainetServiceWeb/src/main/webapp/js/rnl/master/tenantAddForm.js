/**
 * ritesh.patil
 * 
 */
var removeIdArray=[];
$(document).ready(function(){
	
	$("#type").change(function(e){
    	codeVal=$(this).find("option:selected").attr("code");
        $(this).find("option:selected").each(function(){
            if(codeVal=="I"){
                $(".hidebox").not(".individual").hide();
                $(".individual").show();
            }
            else if(codeVal =="G" ){
                $(".hidebox").not(".group").hide();
                $(".group").show();
            }
            else if(codeVal == "O" || codeVal == "T"){
                $(".hidebox").not(".organization").hide();
                $(".organization").show();
            }
		});
    }).change();
    
    
$("#customFields").on('click','.addCF',function(i){
		
		var row=0;
		var errorList = [];
		errorList = validateAdditionalOwners(errorList);
		
		if (errorList.length == 0) {
			if (errorList.length == 0 ) {
				var romm=0;
				var content = $(this).closest('tr').clone();
				$(this).closest("tr").after(content);
				var clickedIndex = $(this).parent().parent().index() - 1;	
				content.find("input:text").val('');
				content.find("input:hidden").val('');
				content.find("select").val('0');
				$('.error-div').hide();
				reOrderTableIdSequence();
			}else {
				displayErrorsOnPageView(errorList);
			}
		}else {
			displayErrorsOnPageView(errorList);
		}
	
});

$("#customFields").on('click','.remCF',function(){
	
	if($("#customFields tr").length != 2)
		{
			 $(this).parent().parent().remove();
			 reOrderTableIdSequence();
			 id=$(this).parent().parent().find('input[type=hidden]:first').attr('value');
			 if(id != ''){
				 removeIdArray.push(id);
			 }
			 $('#removeChildIds').val(removeIdArray);
		}
   else
		{
	   var errorList = [];
	   errorList.push(getLocalMessage("water.additnlowner.deletrw.validtn"));   
	   displayErrorsOnPageView(errorList);
			//alert("You cannot delete first row");
		}
 });

$('#submitTenant').click(function(){
	 
	  var	formName =	findClosestElementId($(this), 'form');
		var theForm	=	'#'+formName;
	    var errorList = [];
		 errorList = validateTenantMasterForm(errorList);
		 if (errorList.length == 0) {
			 if(isTenantTypeGroup()){
			         errorList=validateAdditionalOwners(errorList);
			     }
			      if(errorList.length == 0){
						 var	formName =	findClosestElementId($(this), 'form');
						 var theForm	=	'#'+formName;
						 return saveOrUpdateForm($(this),"", tenantUrl,'saveform');
					}else{
						showRLValidation(errorList);
					 }	 
		 }else{
			showRLValidation(errorList);
		 }
   });
});


function reOrderTableIdSequence() {

	$('.appendableClass').each(function(i) {

		$(this).find("select:eq(0)").attr("id", "title_"+i);
		$(this).find("input:hidden:eq(0)").attr("id", "tntOwnerId_"+i);
		$(this).find("input:text:eq(0)").attr("id", "fName_"+i);
		$(this).find("input:text:eq(1)").attr("id", "mName_"+i);
		$(this).find("input:text:eq(2)").attr("id", "lName_"+i);
		$(this).find("input:text:eq(3)").attr("id", "mobileNumber_"+i);
		$(this).find("input:text:eq(4)").attr("id", "emailId_"+i);
		$(this).find("input:text:eq(5)").attr("id", "aadharNumber_"+i);
		$(this).find("input:text:eq(6)").attr("id", "panNumber_"+i);
		$(this).find("select:eq(0)").attr("name","tenantMaster.tenantOwnerMasters["+i+"].title");
		$(this).find("input:hidden:eq(0)").attr("name", "tenantMaster.tenantOwnerMasters["+i+"].tntOwnerId");
		$(this).find("input:text:eq(0)").attr("name", "tenantMaster.tenantOwnerMasters["+i+"].fName");
		$(this).find("input:text:eq(1)").attr("name", "tenantMaster.tenantOwnerMasters["+i+"].mName");
		$(this).find("input:text:eq(2)").attr("name", "tenantMaster.tenantOwnerMasters["+i+"].lName");
		$(this).find("input:text:eq(3)").attr("name", "tenantMaster.tenantOwnerMasters["+i+"].mobileNumber");
		$(this).find("input:text:eq(4)").attr("name", "tenantMaster.tenantOwnerMasters["+i+"].emailId");
		$(this).find("input:text:eq(5)").attr("name", "tenantMaster.tenantOwnerMasters["+i+"].aadharNumber");
		$(this).find("input:text:eq(6)").attr("name", "tenantMaster.tenantOwnerMasters["+i+"].panNumber");
		
	});
	
     
      
      
      
	
}

function validateAdditionalOwners(errorList){

$('.appendableClass').each(function(i) {
	row=i+1;
		errorList = validateAdditionalOwnerTableData(errorList,i);
  });
  
return errorList;

}


function isTenantTypeGroup(){
  type=$("#type").find("option:selected").attr("code");
	if(type == 'G') return true;
	else return false;
}


/**
 * validate each mandatory column of additional owner details 
 * @param errorList
 * @param i
 * @returns
 */
function validateAdditionalOwnerTableData(errorList, i) {

	 var applicantTitle = $.trim($("#title_"+i).val());
	 var applicantFirstName = $.trim($("#fName_"+i).val());		
	 var applicantLastName = $.trim($("#lName_"+i).val());		
	 var applicantMobileNo = $.trim($("#mobileNumber_"+i).val());		
	 var applicantEmailId = $.trim($("#emailId_"+i).val());	
	
	 if(applicantTitle =="" || applicantTitle =='0'  || applicantTitle == undefined ){
		 errorList.push(getLocalMessage('title owner'));
	 }
	 if(applicantFirstName == "" || applicantFirstName == undefined){
		 errorList.push(getLocalMessage('fname owner'));
	 }
	 if(applicantLastName == "" || applicantLastName == undefined){
		 errorList.push(getLocalMessage('lname owner'));
	 }
	 if(applicantMobileNo == ""  || applicantMobileNo == undefined){
		 errorList.push(getLocalMessage('mobile owner'));
	 }
	 if(applicantEmailId == ""  || applicantEmailId == undefined){
		 errorList.push(getLocalMessage('applicantEmailId owner'));
	 }
	
	 return errorList;
}

function validateTenantMasterForm(errorList) {
		tenantType= $('#type :selected').attr('code');
		title= $.trim($('#title').val());
		fName= $.trim($('#fName').val());
		lName= $.trim($('#lNmae').val());
		address1= $.trim($('#address1').val());
		address2= $.trim($('#address2').val());
		pinCode= $.trim($('#pinCode').val());
		emailId= $.trim($('#emailId').val());
		mobileNumber=  $.trim($('#mobileNumber').val());
		tntOrgName= $.trim($('#tntOrgName').val());
		
		if(tenantType == '0' || tenantType == undefined ){
			 errorList.push(getLocalMessage('estate.master.location.validate.msg'));
		 }
		
		if(tenantType == 'G' || tenantType == 'I'){
			 if(title == '0' || title == undefined ){
				 errorList.push(getLocalMessage('Title'));
			 }
			 if(fName == '' || fName == undefined){
				 errorList.push(getLocalMessage('first name'));
			 }
			 if(lName == '' || lName == undefined ){
				 errorList.push(getLocalMessage('last name'));
			 }
		}
	
		if(tenantType == 'O' || tenantType == 'T'){ 
			
			 if(tntOrgName == '' || tntOrgName == undefined ){
				 errorList.push(getLocalMessage('tntOrgName'));
			 }
			
		}
	
	if(address1 == '' || address1 == undefined ){
		 errorList.push(getLocalMessage('address1'));
	 }
	 if(address2 == '' || address2 == undefined){
		 errorList.push(getLocalMessage('address2'));
	 }
	 if(pinCode =='' || pinCode == undefined ){
		 errorList.push(getLocalMessage('pinCode'));
	 }
	 if(emailId =='' || emailId == undefined ){
		 errorList.push(getLocalMessage('emailId'));
	 }
	 if(mobileNumber =='' || mobileNumber == undefined ){
		 errorList.push(getLocalMessage('mobileNumber'));
	 }
	
	return errorList;	
}

function displayErrorsOnPageView(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	
	return false;
}

function closeOutErrBox(){
	$('.error-div').hide();
}


function showRLValidation(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
    errMsg += '<ul>';
    $.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
    $('html,body').animate({ scrollTop: 0 }, 'slow');
	$('.error-div').html(errMsg);
	$(".error-div").show();
	$('html,body').animate({ scrollTop: 0 }, 'slow');
	return false;
}
