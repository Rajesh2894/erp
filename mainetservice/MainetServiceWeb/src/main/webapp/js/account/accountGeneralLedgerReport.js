
$(function(){

$('#reportTypeId').change(function(){
	
	var selectedId = "GLR"
	var requestData = {
			'reportTypeCode': selectedId,
	}
	var ajaxResponse = __doAjaxRequest('AccountGeneralLedgerReports.html?onReportType', 'POST', requestData, false,'html');
	var reportTypeHidden = $(ajaxResponse).find('#reportTypeHidden').val();
	$('#content').html(ajaxResponse);
	 $('.required-control').next().children().addClass('mandColorClass');
	$('#reportTypeId').val(selectedId);
	
	
	$( "#reportTypeId" ).prop( "disabled", true );
	
	
});
	
function reOrderTableIdSequence() {
	//debugger;
$('.appendableClass').each(function(i) {
		$(this).find('div.chosen-container').attr('id',"pacHeadId"+i+"_chosen");
		$(this).find("select:eq(0)").attr("id", "pacHeadId" + i);
		$(this).find("input:text:eq(0)").attr("id", "pacHeadId" + i);
		$(this).find("select:eq(0)").attr("name","generalLedgerList[" + i + "].accountHeadId");
		
		$(this).find("input:text:eq(0)").attr("name","generalLedgerList[" + i + "].accountHeadId");
		$(this).find('.delButton').attr("id",	"delButton" + i);
		$(this).find('.addButton').attr("id",	"addButton" + i);
		$(this).closest("tr").attr("id", "opnBalId" + (i));
		$("#indexdata").val(i);
		
	});

}	




//to generate dynamic table
$("#opnBalTableDivID").on("click", '.addButton', function(e) {
	debugger;
var errorList = [];

   $('.appendableClass').each(function(i) {
		 var sacHeadId = $.trim($("#pacHeadId"+i).val());
		 if(sacHeadId==0 || sacHeadId=="") 
		 errorList.push("Please select Account Head");
		
		 var openbalAmt = $.trim($("#openbalAmt"+i).val());
		 if(openbalAmt==0 || openbalAmt=="")
		 for(m=0; m<i;m++){
				if(($('#pacHeadId'+m).val() == $('#pacHeadId'+i).val())){
					
					errorList.push("The Combination AccountHead already exists!");
				}
			}

		$("#indexdata").val(i);
   });
			if (errorList.length > 0) {
				$('#index').val(i);
				var errMsg = '<ul>';
				$.each(
								errorList,
								function(index) {
									errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
											+ errorList[index]
											+ '</li>';
								});

				errMsg += '</ul>';

				$('#errorId').html(errMsg);
				$('#errorDivId').show();
				$('html,body').animate({
					scrollTop : 0
				}, 'slow');
				return false;
			}
			e.preventDefault();
			var content = $(this).closest('#opnBalTable tr').clone();
			$(this).closest("#opnBalTable").append(content);
			// reset values
			content.find("select").attr("value", "");
			content.find("input:text").val("");
			content.find("select").val("");
			content.find("input:checkbox").attr('checked',false);
			content.find('div.chosen-container').remove();
			$(".applyChoosen").chosen().trigger("chosen:updated");
			content.find('label').closest('.error').remove(); //for removal duplicate
			$('#flag').prop('disabled', true);
			reOrderTableIdSequence();
		});

	
	//to delete row
	$("#opnBalTableDivID").on("click", '.delButton', function(e) {
	
	var rowCount = $('#opnBalTable tr').length;
	if (rowCount <= 2) {
	var msg = "can not remove";
	showErrormsgboxTitle(msg);
	return false;
	}
	
	$(this).closest('#opnBalTable tr').remove();
	reOrderTableIdSequence();
	e.preventDefault();
	});
});




function viewReport(element) {
	//debugger;
	showloader(true);
	setTimeout(function(){
		 if(checkValidation()){
			
			$('#reportTypeId').prop('disabled', false);
			var url = "AccountGeneralLedgerReports.html?report";
			var formName = findClosestElementId(element, 'form');
			var theForm = '#' + formName;
			var requestData = __serializeForm(theForm);
			var ajaxResponse = __doAjaxRequestForSave(url, 'post', requestData, false,
					'', element);
			 if(ajaxResponse != false){
			    	var hiddenVal = $(ajaxResponse).find('#errorId').val();
			    	if (hiddenVal == 'Y') {
			    		var errorList =[];
			    		errorList.push('No record found!');
			    		displayErrorsOnPage(errorList);
			    	} else {
			    		$('#content').html(ajaxResponse);
			    	}
			    }
		}
	showloader(false);
	},2);
}

function checkValidation()
{   
	var errorList=[];
	var dateFrom='01/04/';
	var dateTo='31/03/';
	var finciStartDate;    
	var finciToDate;     
	var finciToDate1;
	var type="GLR";
		 if($("#fromDateId").val()=="" && $("#toDateId").val()=="")
			 {
			 errorList.push("Please Select From Date and To Date");
			 $( "#reportTypeId" ).prop( "disabled", true );
			 }
		 else if($("#fromDateId").val()!="" && $("#toDateId").val()=="")
			 {
			 errorList.push("Please Select To Date"); 
			 $( "#reportTypeId" ).prop( "disabled", true );
			 }
		 else if($("#fromDateId").val()=="" && $("#toDateId").val()!="")
			 {
			 errorList.push("Please Select From Date"); 
			 $( "#reportTypeId" ).prop( "disabled", true );
			 }
		  var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		  var sDate = new Date($("#fromDateId").val().replace(pattern,'$3-$2-$1'));
		  var eDate = new Date($("#toDateId").val().replace(pattern,'$3-$2-$1'));
		  /*  Check FromDate Cannot Be less than To Date */
		  if (sDate > eDate) {
			  errorList.push("To Date can not be less than From Date");
			  $( "#reportTypeId" ).prop( "disabled", true );
		    }  
		  var currentYear=sDate.getFullYear();
		  var endYear=eDate.getFullYear();
		  var gapYear=endYear-currentYear;
		  if(gapYear=="0"){
			     dateFrom=new Date((dateFrom+currentYear).replace(pattern,'$3-$2-$1'));//01/04/2019
			     dateTo=new Date((dateTo+currentYear).replace(pattern,'$3-$2-$1')); //31/03/2019
			     if(((sDate>dateTo)&&(eDate>dateTo))){
			    	var dateEndoftheYear='31/12/';
			    	var lastFromDate=new Date((dateEndoftheYear+currentYear).replace(pattern,'$3-$2-$1')); //31/12/2019
			    	if(sDate>lastFromDate || eDate>lastFromDate){
			    	    	 errorList.push("From Date and To Date should be within the financial year");
			    	  }
			    }else if(((sDate<=dateTo)&&(eDate<=dateTo))){
			    	
			    } else{
			    	errorList.push("From Date and To Date should be within the financial year");
			    }
		  }else if(gapYear=="1"){
			     dateFrom=new Date((dateFrom+currentYear).replace(pattern,'$3-$2-$1'));//01/04/2019
			     dateTo=new Date((dateTo+endYear).replace(pattern,'$3-$2-$1')); //31/03/201
			 
			     if((sDate<dateFrom) || (sDate>dateTo)){
			    	 errorList.push("From Date and To Date should be within the financial year");
			     }else if((eDate<dateFrom)||(eDate>dateTo)){
			    	 errorList.push("From Date and To Date should be within the financial year");
			     }
		  }else{
			      errorList.push("From Date and To Date should be within the financial year");
		  }
		  if($("#fromDateId").val()!=null)
			  {
			  errorList = validatedate(errorList,'fromDateId');
			  }
	      if($("#toDateId").val()!=null)
	    	  {
	    	  errorList = validatedate(errorList,'toDateId');
	    	  }
	if(errorList.length>0)
		{
	 var errMsg = '<ul>';
		$.each(errorList, function(index) {
			errMsg += '<li>' + errorList[index] + '</li>';
		});
     errMsg += '</ul>';
      $('#errorId').html(errMsg);
		$('#errorDivId').show();
		return false;
		}
	if(type == 'GLR'){
		
		$('#flag').prop('disabled', false);
		var chk = $('#flag').is(':checked');
		if(chk)
		{
			 
		}else{
			var j =  $("#indexdata").val();		
			var pacHeadId= $('#pacHeadId'+j).val();
			if(pacHeadId != ""){
			for(n=0; n<j;n++){	
				
			if(($('#pacHeadId'+n).val() == $('#pacHeadId'+j).val())){
				errorList.push("The Combination Account Head already exists!");
			}
			}
			}
			
			var k =  $("#indexdata").val();
			for(n=0; n<=k;n++){	
			var sacheadIdRev = $('#pacHeadId'+n).val();
			var openbalAmt = $("#openbalAmt"+n).val();
			if (sacheadIdRev == '') {
				
				errorList.push(getLocalMessage('please.select.Account.heads'));
			}		 	 
			}
		}
	}
	
	if(errorList.length>0)
	{
	var errMsg = '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
    errMsg += '</ul>';
    $('#errorId').html(errMsg);
	$('#errorDivId').show();
	return false;
	}
	return true;
}

$(document).ready(function()
		{
		$("#flag").click(function()
			{
		 var chk = $('#flag').is(':checked');
		 if(chk)
			 {
			 $("#pacHeadId0").val("");
			 $('#pacHeadId0').prop('disabled', true).trigger("chosen:updated");
			 $('#addButton0').prop('disabled', true);
			 $('#delButton0').prop('disabled', true);
			 }
		 else
			 {
			 $('#pacHeadId0').prop('disabled', false).trigger("chosen:updated");
			 $('#addButton0').prop('disabled', false);
			 $('#delButton0').prop('disabled', false);
			 }
		});
	});
