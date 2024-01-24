function getTenderDetails(){
	
	var tenderId = $('#trTenderId').val(); 
	alert(tenderId);
	var postData = 'tenderId=' + tenderId;
	__doAjaxRequest('AccountLiabilityBooking.html?getTenderDetails','POST',postData,false,'json');
	
}

$(function() {
	$("#search").click(function(){ 
		var errorList1 = [];
		var tenderId = $('#trTenderId').val(); 
		 var trTenderId = $.trim($("#trTenderId").val());
		 if(trTenderId==0 || trTenderId=="")
		    errorList1.push(getLocalMessage('account.select.tender.no'));
		 if(errorList1.length > 0){ 
				var errMsg = '<ul>';
				$.each(errorList1, function(index) {
					errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList1[index] + '</li>';
				});
				errMsg += '</ul>';
				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				$('html,body').animate({ scrollTop: 0 }, 'slow');
				return false;
		} 
		
		
		if( tenderId != '' ) {				
			var url = "AccountLiabilityBooking.html?getTenderDetails";
			var returnData = "tenderId="+tenderId;
			
			$.ajax({
				url : url,
				data : returnData,
				success : function(response) {
					var divName = '.widget';
					$("#lbBookingDiv").html(response);
					$("#lbBookingDiv").show();
				},
				error : function(xhr, ajaxOptions, thrownError) {
					var errorList = [];
					errorList.push(getLocalMessage("admin.login.internal.server.error"));
					showError(errorList);
				}
			});				
		}
	
	});
});


function saveLiability(element){
	
	 var errorList = [];
	 var lbEntryDate = $.trim($("#lbEntryDate").val());
	 if(lbEntryDate==0 || lbEntryDate==""){
	    errorList.push(getLocalMessage('account.liability.entry.date'));
	 }
	 
	 $('.liabilityClass').each(function(i) {
		 
		 var pacHeadId = $.trim($("#pacHeadId"+i).val());
		 if(pacHeadId==0 || pacHeadId=="")
		    errorList.push(getLocalMessage('account.select.primary.acchead'));
		 
		 var sacHeadId = $.trim($("#sacHeadId"+i).val());
		 if(sacHeadId==0 || sacHeadId=="")
		    errorList.push(getLocalMessage('account.select.secondary.acchead'));
		 
		 var liability=$('#liability').val();
		 if(liability=="true"){
		 var liabilityAmount = $.trim($("#liabilityAmount"+i).val());
		 if(liabilityAmount==0 || liabilityAmount=="")
		    errorList.push(getLocalMessage('account.liability.amt'));
		 
		 var faYearid = $.trim($("#faYearid"+i).val());
		 if(faYearid==0 || faYearid=="")
		    errorList.push(getLocalMessage('account.select.financialYear'));
		 }
		 
		 
		 if(liability!="true"){
			 
			  $('#pacHeadId'+i).prop("disabled", false);
			  $('#sacHeadId'+i).prop("disabled", false);
			  $('#fundId'+i).prop("disabled", false);
			  $('#functionId'+i).prop("disabled", false);
			  $('#fieldId'+i).prop("disabled", false);
		 }
		 
		 
	 });
	 
	 
	 if(errorList.length > 0){ 
			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			});

			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');
			return true;
	} 
	 else{
		 return saveOrUpdateForm(element, 'Saved Successfully', 'AccountLiabilityBooking.html', 'create');
	 }
		
}

//to generate dynamic table
$("#liabilityDetTable").on("click", '.addButton',function(e) {
	 var errorList = [];
	 $('.appendableClass tr').each(function(i) {
		 row = i-1;
	});
	 
	 var pacHeadId = $.trim($("#pacHeadId"+row).val());
	 if(pacHeadId==0 || pacHeadId=="")
	    errorList.push(getLocalMessage('account.select.primary.acchead'));
	 
	 var sacHeadId = $.trim($("#sacHeadId"+row).val());
	 if(sacHeadId==0 || sacHeadId=="")
	    errorList.push(getLocalMessage('account.select.secondary.acchead'));

	 
	 var liabilityAmount = $.trim($("#liabilityAmount"+row).val());
	 if(liabilityAmount==0 || liabilityAmount=="")
	    errorList.push(getLocalMessage('account.liability.amt'));
	 
	 var faYearid = $.trim($("#faYearid"+row).val());
	 if(faYearid==0 || faYearid=="")
	    errorList.push(getLocalMessage('account.select.financialYear'));
	 
	 if(errorList.length > 0){ 
		 
			var errMsg = '<ul>';
			$.each(errorList, function(index) {
				errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;' + errorList[index] + '</li>';
			});

			errMsg += '</ul>';

			$('#errorId').html(errMsg);
			$('#errorDivId').show();
			$('html,body').animate({ scrollTop: 0 }, 'slow');
			return false;
	} 
	 
	 
	
	 e.preventDefault();
	 
	 var content = $(this).closest('#liabDetailTable tr').clone();
	 $(this).closest("#liabDetailTable").append(content);
	 
	 // reset values
	 content.find("select").attr("value", "");
	 content.find("input:text").val("");
	 
	// for generating dynamic Id
	 content.find("select:eq(2)").attr("id","fundId" + (row));
	 content.find("select:eq(3)").attr("id","functionId" + (row)); 
	 content.find("select:eq(4)").attr("id","fieldId" + (row)); 
	 content.find("select:eq(0)").attr("id","pacHeadId" + (row)); 
	 content.find("select:eq(1)").attr("id","sacHeadId" + (row));
	 content.find("select:eq(5)").attr("id","faYearid" + (row));
	 content.find("input:text:eq(0)").attr("id","liabilityAmount" + (row));
	 content.find("select:eq(0)").attr("onchange","setSecondaryCodeFinance("+row+")");
	 
	 
	// for generating dynamic name
	 content.find("select:eq(2)").attr("name","detList["+row+"].fundId");
	 content.find("select:eq(3)").attr("name","detList["+row+"].functionId"); 
	 content.find("select:eq(4)").attr("name","detList["+row+"].fieldId"); 
	 content.find("select:eq(0)").attr("name","detList["+row+"].pacHeadId"); 
	 content.find("select:eq(1)").attr("name","detList["+row+"].sacHeadId"); 
	 content.find("select:eq(5)").attr("name","faYearid" + (row));
	 content.find("input:text:eq(0)").attr("name","liabilityAmount" + (row));
	 
	 content.find('.delButton').attr("id", "delButton"+ (row));
	 content.find('.addButton').attr("id", "addButton"+ (row));
	 
	 content.find("select").val("");
	 reOrderTableIdSequence();
	 
	
});

// to delete row
$("#liabilityDetTable").on("click", '.delButton', function(e) {
	
	var rowCount = $('#liabilityDetTable tr').length;
	if(rowCount<=2){
		return false;
	}
	
	 $(this).closest('#liabilityDetTable tr').remove();

	 	reOrderTableIdSequence();
	 	e.preventDefault();
});	



function reOrderTableIdSequence(){
	
	 $('.appendableClass tr').each(function(i) {
		 i=i-1;
		 $(".datepicker").datepicker("destroy");
		 
		 $(this).find("select:eq(2)").attr("id","fundId"+i);
		 $(this).find("select:eq(3)").attr("id","functionId"+i); 
		 $(this).find("select:eq(4)").attr("id","fieldId"+i); 
		 $(this).find("select:eq(0)").attr("id","pacHeadId"+i); 
		 $(this).find("select:eq(1)").attr("id","sacHeadId"+i); 
		 $(this).find("select:eq(5)").attr("id","faYearid"+i);
		 $(this).find("input:text:eq(0)").attr("id","liabilityAmount"+i);
		 $(this).find("select:eq(0)").attr("onchange","setSecondaryCodeFinance("+i+")");
		 
		 
		 $(this).find("select:eq(2)").attr("name","detList["+i+"].fundId");
		 $(this).find("select:eq(3)").attr("name","detList["+i+"].functionId"); 
		 $(this).find("select:eq(4)").attr("name","detList["+i+"].fieldId"); 
		 $(this).find("select:eq(0)").attr("name","detList["+i+"].pacHeadId"); 
		 $(this).find("select:eq(1)").attr("name","detList["+i+"].sacHeadId"); 
		 $(this).find("select:eq(5)").attr("name","detList["+i+"].faYearid");
		 $(this).find("input:text:eq(0)").attr("name","detList["+i+"].liabilityAmount");
		 
		 
		 $(this).parents('tr').find('.delButton').attr("id", "delButton"+i);
		 $(this).parents('tr').find('.addButton').attr("id", "addButton"+i);
		 
		 
	 });
}


var thisCount;
function setSecondaryCodeFinance(count)
 {
	thisCount = count;
	 var primaryCode=$('#pacHeadId'+count).val();
	 
		$('#sacHeadId'+count).find('option:gt(0)').remove();
		
		if (primaryCode > 0) {
			var postdata = 'pacHeadId=' + primaryCode;
			
			var json = __doAjaxRequest('AccountLiabilityBooking.html?sacHeadItemsList',
					'POST', postdata, false, 'json');
			var  optionsAsString='';
			
			$.each( json, function( key, value ) {
				optionsAsString += "<option value='" +key+"'>" + value + "</option>";
				});
			$('#sacHeadId'+count).append(optionsAsString );
			
		}
 }	

$(document).ready(function(){
	var liability=$('#liability').val();
	  $('.liabilityClass').each(function(i) {
	    	 var primaryCode=$('#pacHeadId'+i).val();
	    	 $('#sacHeadId'+i).find('option:gt(0)').remove();
	    	 if (primaryCode > 0) {
	    	 var postdata = 'pacHeadId=' + primaryCode;
	    	 var json = __doAjaxRequest('AccountLiabilityBooking.html?sacHeadItemsList',
	    				'POST', postdata, false, 'json');
	    		var  optionsAsString='';
	    		
	    		$.each( json, function( key, value ) {
	    			if(key== $('#selectedSecondaryValue'+i).val())
	       			{	
	       				optionsAsString += "<option value='" +key+"' selected>" + value + "</option>";
	       			}
	       			else
	       	   			{
	       				optionsAsString += "<option value='" +key+"'>" + value + "</option>";
	       	   			}
	    			});
	    		$('#sacHeadId'+i).append(optionsAsString );
	    		
	    	}
	    });
});

function enableDetFields(){
	
	  $('.liabilityClass').each(function(i) {
		  
		  $('#pacHeadId'+i).prop("disabled", false);
		  $('#sacHeadId'+i).prop("disabled", false);
		  $('#fundId'+i).prop("disabled", false);
		  $('#functionId'+i).prop("disabled", false);
		  $('#fieldId'+i).prop("disabled", false);
		  $('#liabilityAmount'+i).prop("readonly", false);
		  $('#faYearid'+i).prop("disabled", false);
		  $('#addButton'+i).prop("disabled", false);
		  $('#saveButton').prop("disabled", false);
		  $('#editButton').prop("disabled", true);
	  });
}

$(function() {
	
	 $('.liabilityClass').each(function(i) {
		 
		 $('#addButton'+i).prop("disabled", true);
		 $('#saveButton').prop("disabled", true);
	 });
	
});





