 
$(function(){
	
	$("html, body").animate({ scrollTop: 0 }, "slow");
     
$(".lessthancurrdate").datepicker({
	        dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true,
			maxDate: '-0d',
			 
	});

$(".billToDate").datepicker({
    dateFormat: 'dd/mm/yy',		
	changeMonth: true,
	changeYear: true,
	maxDate: '-0d',

	onSelect:function(){
		var errorList = [];
		var dateFrom = $(".lessthancurrdate").datepicker('getDate');
		var dateTo =$(".billToDate").datepicker('getDate');
		if(dateFrom>=dateTo){
			//alert("Opp! taxToDate must be later than Bill Date");
			errorList.push(getLocalMessage("prop.taxtoDate.later.billDate"));
			showBillError(errorList);
			 $(".billToDate").val("");
		}
		
	}
});
	

var schId=$("#financialYear").val();
if(schId==null || schId == 0){
	$("#billWiseDetail").hide();
	$("#nextView").hide();
}else{
	$("#billList").hide();
}
	 
/*reOrderTaxTabIdSequence('.firstUnitRow');

$(".firstUnitRow").each(function(i) {
	 
	calculate(i);
});
  */
});


/*   function calculate(i){
		 
				 var arrear0value=$("#areear"+i).val();
					var current0value=$("#current"+i).val();
					if((arrear0value != null && arrear0value != undefined && arrear0value !='' && !isNaN(arrear0value))
							&& (current0value != null && current0value != undefined && current0value !='' &&  !isNaN(current0value))){
						var total= parseInt(arrear0value)+parseInt(current0value);
						$("#total"+i).val(total);
					}
	  
					var arrear0value=$("#areear"+i).val();
					var current0value=$("#current"+i).val();
					if((arrear0value != null && arrear0value != undefined && arrear0value !='' && !isNaN(arrear0value))
							&& (current0value != null && current0value != undefined && current0value !='' &&  !isNaN(current0value))){
						var total= parseInt(arrear0value)+parseInt(current0value);
						$("#total"+i).val(total);
					}
	}*/

 

function getYear(element){
	
/*	$("#yearOfConstruc0").val('');
	$("#proAssAcqDate").val('');*/
	var errorList = [];
	var finyear=$("#financialYear").val();
	if(finyear==0){
		errorList.push(getLocalMessage("property.report.finYear"));
    }
	if (errorList.length == 0) {
	var theForm	=	'#DataEntryOutStanding';
	var requestData = {};
	requestData = __serializeForm(theForm);
	var URL = 'DataEntrySuite.html?financialYear'; 
	
	var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
	$(formDivName).html(returnData);
	$("#nextView").show();
	$("#billList").hide();
	$("#billWiseDetail").show();
	}
	else {
		showVendorError(errorList);
	}
/*	clearTableData();
	yearLimit(returnData[1]);
	$("#year0").val(returnData[1]);
	$("#hiddenYear0").val(returnData[1]);
	$('#proAssAcqDate').datepicker('option', 'maxDate', new Date(returnData[0]));*/
}

function getSchedule(element){
	$("#nextView").hide();
	$("#billList").show();
	$("#billWiseDetail").hide();

}


/*
$("#taxdetailTable").on('click','.addCF',function(){

		
		
		var content =	$("#taxdetailTable").find('tr:eq(1)').clone();
		$("#taxdetailTable").append(content);
		reOrderTaxTabIdSequence('.firstUnitRow');						
		content.find("input:text").val('');
		content.find("select").val('0');

	  // reorder id and Path after Addition of new Row
 		return false;
						
		
}); 

$("#taxdetailTable").on('click','.remCF',function(){

	var index = $(this).closest("tr").index();
	if($("#taxdetailTable tr").length	!=2 ) {			
		$(this).parent().parent().remove();					
		reOrderTaxTabIdSequence('.firstUnitRow');
	}
		 else {
			var errorList = [];
			errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
			showBillError(errorList);
		}
  
});*/


/*function reOrderTaxTabIdSequence(classNameFirst) {
	$(classNameFirst).each(function(i) {
	var incr=i+1;

	$(this).find("select:eq(0)").attr("id", "taxDesc"+i);

	$(this).find("input:text:eq(0)").attr("id", "areear"+i);
	
	$(this).find("input:text:eq(1)").attr("id", "current"+i);
	$(this).find("input:text:eq(2)").attr("id", "total"+i);
	$(this).find("input:text:eq(0)").attr("onchange", "calculate("+(i)+")");
	$(this).find("input:text:eq(1)").attr("onchange", "calculate("+(i)+")");
	
	$(this).find("select:eq(0)").attr("name","tbBillMas.tbWtBillDet["+i+"].taxId");
	$(this).find("input:text:eq(0)").attr("name","tbBillMas.tbWtBillDet["+i+"].bdPrvArramt");
	$(this).find("input:text:eq(1)").attr("name","tbBillMas.tbWtBillDet["+i+"].bdCurTaxamt");

	});
}*/
	
function showErrorOnPage(errorList){
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
	return false;
}
  

function confirmToNext(element){ 
		var theForm	=	'#DataEntryOutStanding';
		var requestData = {};
		requestData = __serializeForm(theForm);

		var URL = 'DataEntrySuite.html?nextToViewAfterArrear';
		
		var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		if(returnData)
		{
			$(formDivName).html(returnData);
		/*	var divName = '.content';
			$(divName).html(returnData);*/
		}
	 
}

function showBillError(errorList){
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
	return false;
}

 