/**
 * @author vishwajeet.kumar
 * @since 26 June 2018
 */

var termsCondById=[];

$(document).ready(function() {
	
});

function validateTermsAndConditionForApproval(errorList){
	
	$(".termsApprovalClass").each(function(i) {		
		var approvalTermsDesc = $("#approvalTermsDesc" +i).val();
		var constant = i+1;
		
		if(approvalTermsDesc == '' || approvalTermsDesc ==undefined){
			/*errorList.push(getLocalMessage("work.order.please.enter.terms.and.condition")+" "+constant);*/
		}
	});
	
	return errorList;
}

$("#termsAndConditionForApproval").on("click",'.addTermsAndConditionForApproval',function(e) {
	
	  var count = $('#termsAndConditionForApproval tr').length - 1;
	  var errorList = [];
	  errorList = validateTermsAndConditionForApproval(errorList);	
      if (errorList.length > 0) { 
	       displayErrorsOnPage(errorList);
	 } 
    else {
			   $("#errorDiv").addClass('hide');
			    e.preventDefault();
				var clickedRow = $(this).parent().parent().index();
				var content = $('#termsAndConditionForApproval tr').last().clone();
				$('#termsAndConditionForApproval tr').last().after(content);
				content.find("input:hidden").attr("value", "");
				content.find("input:text").val('');
				content.find("textarea").val('');			
				reOrderTermsAndCondition();
			}
});


function reOrderTermsAndCondition(){
	$('.termsApprovalClass').each(function(i){		 
		$(this).find("hidden:eq(0)").attr("id","teId" + (i));	
		$(this).find("input:text:eq(0)").attr("id" ,"sNo" + (i));
		$(this).find("textarea:eq(0)").attr("id" ,"approvalTermsDesc" + (i));
		
		$(this).find("input:hidden:eq(0)").attr("name","termsConditionDtosList[" + (i) + "].teId");
		$(this).find("textarea:eq(0)").attr("name","termsConditionDtosList[" + (i)+ "].termDesc");
		$("#sNo" + i).val(i+1);
	});
}

$('#termsAndConditionForApproval').on("click",'.deleteApprovalTermsDetails' , function(e){
	 
	var errorList = [];
	var count = 0;
	$('.termsApprovalClass').each(function(i){
		count += 1;
	});
	var rowCount = $('#termsAndConditionForApproval tbody tr').length;
	 if(rowCount <= 1){
		 errorList.push(getLocalMessage("first.row.cannot.be.deleted"));
	 }
	   if(errorList.length > 0){
		    displayErrorsOnPage(errorList);
			return false;
	 }else{
		 $(this).parent().parent().remove();
		 var termsAndConditionId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			if(termsAndConditionId != ''){
				termsCondById.push(termsAndConditionId);
				}
			$('#removeTermsById').val(termsCondById);
			reOrderTermsAndCondition();
	 }	
});