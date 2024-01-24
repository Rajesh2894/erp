/**
 * @author Jeetendra.Pal
 */
var removeIdArray=[];
$(document).ready(function() {
	
	/*$('#WorkEstimateMeasureDetailsTab').DataTable();*/
	
	getDateBySorName();
	calculateTotal();

	calculateRateAnalysis();
	
$('.appendableClass').each(function(i) {
	getRateUnitBySorId(i);
});
	
$('.decimal').on('input', function() {
    this.value = this.value
      .replace(/[^\d.]/g, '')             // numbers and decimals only
      .replace(/(^[\d]{5})[\d]/g, '$1')   // not more than 5 digits at the beginning
      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
      .replace(/(\.[\d]{4})./g, '$1');    // not more than 2 digits after decimal
  });
	

		   
$("#WorkEstimateMeasureDetailsTab").on("click", '.addButton',function(e) {
	if ( $.fn.DataTable.isDataTable('#WorkEstimateMeasureDetailsTab') ) {
		  $('#WorkEstimateMeasureDetailsTab').DataTable().destroy();
		}
	var errorList = [];	
	errorList = validateMeasurementDetails(errorList);
	
	if (errorList.length > 0) { 
	       $("#errorDiv").show();
	       displayErrorsOnPage(errorList);
	 } else{
		  $("#errorDiv").hide();
		  e.preventDefault();
		  var content = $('#WorkEstimateMeasureDetailsTab tr').last().clone();
		  $('#WorkEstimateMeasureDetailsTab tr').last().after(content);
		  content.find("input:text").val('');
		  content.find("select").val('');
		  content.find("input:hidden").val('');
		  content.find("input:text:eq(2)").attr("id","meMentNumber" + i).val("1");
		  content.find("select:eq(0)").attr("name","measureDetailsList[" + i+ "].meMentValType").val('C');
		  content.find("select:eq(1)").attr("name","measureDetailsList[" + i+ "].meMentType").val('A');
		  
		  $('.decimal').on('input', function() {
			    this.value = this.value
			      .replace(/[^\d.]/g, '')             // numbers and decimals only
			      .replace(/(^[\d]{5})[\d]/g, '$1')   // not more than 5 digits at the beginning
			      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
			      .replace(/(\.[\d]{4})./g, '$1');    // not more than 4 digits after decimal
			  });
		  reOrderTableIdSequence();
	 }
});
		   
		   
$("#WorkEstimateMeasureDetailsTab").on("click", '.delButton', function(e) {
	
  if ( $.fn.DataTable.isDataTable('#WorkEstimateMeasureDetailsTab') ) {
		  $('#WorkEstimateMeasureDetailsTab').DataTable().destroy();
		}
	var countRows = -1;
	$('.appendableClass').each(function(i) {
		if ( $(this).closest('tr').is(':visible') ) {
				countRows = countRows + 1;			
		}
	});
	row = countRows;
	if (row != 0) {
		$(this).parent().parent().remove();
		row--;
		var deletedSodId=$(this).parent().parent().find('input[type=hidden]:first').attr('value');
		 if(deletedSodId != ''){
			 removeIdArray.push(deletedSodId);
		 }
	 $('#removeChildIds').val(removeIdArray);
		reOrderTableIdSequence();
	}
	e.preventDefault();
});	

function reOrderTableIdSequence() {
	
	$('.appendableClass').each(function(i) {
		
		$(this).find("input:text:eq(0)").attr("id", "sequence" + (i));
		$("#sequence"+i).val(i+1);
		/*meValue*/
		$(this).find("select:eq(0)").attr("id", "meMentValType"+ i);
		$(this).find("select:eq(1)").attr("id", "meMentType"+ i);
		$(this).find("input:hidden:eq(0)").attr("id", "meMentId"+ i);
		
	 	$(this).find("input:text:eq(1)").attr("id","meMentParticulare" + i);
		$(this).find("input:text:eq(2)").attr("id","meMentNumber" + i);
		$(this).find("input:text:eq(3)").attr("id","meMentFormula" + i);
		$(this).find("input:text:eq(4)").attr("id","meMentLength" + i);
		$(this).find("input:text:eq(5)").attr("id","meMentBreadth" +i);
		$(this).find("input:text:eq(6)").attr("id","meMentHeight" + i);
		$(this).find("input:text:eq(7)").attr("id","meValue" + i);
		$(this).find("input:text:eq(8)").attr("id","meMentToltal" + i);

		$(this).find("input:button:eq(4)").attr("id", "delButton"+ i);
		$(this).parents('tr').find('.delButton').attr("id", "delButton"+ i);
		
		$(this).find("select:eq(0)").attr("name","measureDetailsList[" + i+ "].meMentValType");
		$(this).find("select:eq(1)").attr("name","measureDetailsList[" + i+ "].meMentType");
	 	
		$(this).find("input:text:eq(1)").attr("name","measureDetailsList[" + i+ "].meMentParticulare");
		$(this).find("input:text:eq(2)").attr("name","measureDetailsList[" + i+ "].meMentNumber");
		$(this).find("input:text:eq(3)").attr("name","measureDetailsList[" + i+ "].meMentFormula");
		$(this).find("input:text:eq(4)").attr("name","measureDetailsList[" + i+ "].meMentLength");
		$(this).find("input:text:eq(5)").attr("name","measureDetailsList[" + i+ "].meMentBreadth");
		$(this).find("input:text:eq(6)").attr("name","measureDetailsList[" + i+ "].meMentHeight");
		$(this).find("input:text:eq(7)").attr("name","measureDetailsList[" + i+ "].meValue");
		$(this).find("input:text:eq(8)").attr("name","measureDetailsList[" + i+ "].meMentToltal");
		$(this).find("input:hidden:eq(0)").attr("name","measureDetailsList[" + i+ "].meMentId");
	 	
	});
	triggerDatatable();
}

function triggerDatatable(){
	$("#WorkEstimateMeasureDetailsTab").dataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [10,20,30,-1], [10,20,30,"All"] ],
	    "iDisplayLength" : 10, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "scrollCollapse": true,
	    "bSort" : false
	   }).fnPageChange( 'last' );	 	 
}
			
			
			// for Rate Analysis
$("#rateAnalysisTab").on("click", '.addButton',function(e) {
	
	if ( $.fn.DataTable.isDataTable('#rateAnalysisTab') ) {
		  $('#rateAnalysisTab').DataTable().destroy();
		}
	var errorList = [];	
	errorList = validateRateAnalysis(errorList);							
	if(errorList.length > 0){								
		$("#errorDiv").show();
	    displayErrorsOnPage(errorList);							
	}else{								
		$("#errorDiv").hide();								
		e.preventDefault();
		var content = $('#rateAnalysisTab tbody').last().clone();
		$('#rateAnalysisTab tbody').last().after(content);																		
		content.find("input:text").val('');
		content.find("select:eq(0)").val('');
		content.find("input:hidden").val('');								 	
		 $('.decimal').on('input', function() {
			   this.value = this.value
			     .replace(/[^\d.]/g, '')             // numbers and decimals only
			     .replace(/(^[\d]{4})[\d]/g, '$1')   // not more than 4 digits at the beginning
			     .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
			     .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
		 });								
	}															
	reOrderRateAnlysisTableIdSequence();
});
			 			 
$("#rateAnalysisTab").on("click", '.rateAnalysisDelButton', function(e) {
					
	var countRows = -1;
	$('.appendableClass').each(function(i) {
		if ( $(this).closest('tbody').is(':visible') ) {
			countRows = countRows + 1;			
		}
	});
	row = countRows;
	if (row != 0) {
		$(this).parent().parent().parent().remove();
		var deletedSodId=$(this).parent().parent().find('input[type=hidden]:first').attr('value');
		 if(deletedSodId != ''){
			 removeIdArray.push(deletedSodId);
		 }
	 $('#removeMaterialById').val(removeIdArray);
		reOrderRateAnlysisTableIdSequence();
	}
	e.preventDefault();
});	
});

function calculateRateAnalysis(){
	
		$('.appendableClass').each(function (i) {
			var sum = 0;
			var basicRate = parseFloat($("#sorBasicRate" + i).val());
			var quantity = parseFloat($("#workEstimQuantity" + i).val());
			if (!isNaN(quantity)) {
				sum = (basicRate * quantity);
			}
			if(!isNaN(sum)){
				$("#workEstimAmount" + i).val(sum.toFixed(2));
				var grandTotal = 0;
				$(".allamount").each(function () {
					var total = parseFloat($(this).val());
					grandTotal += isNaN(total) ? 0 : total;
				});
				var tot = $("#rateTotal").val(grandTotal.toFixed(2));
			}
		});
}	

function reOrderRateAnlysisTableIdSequence() {
		
$('.appendableClass').each(function(i) {
	
	$(this).find("input:text:eq(0)").attr("id", "sequence" + (i));
	
	$(this).find("input:hidden:eq(0)").attr("id", "workEstemateId" + (i));
	$("#sequence"+i).val(i+1);
	
	$(this).find("select:eq(0)").attr("id", "gRateMastId"+ i);
	$(this).find("select:eq(0)").attr("name","rateAnalysisMaterialList[" + i+ "].gRateMastId");
	$(this).find("input:hidden:eq(0)").attr("name","rateAnalysisMaterialList[" + i+ "].workEstemateId");
	$(this).find("select:eq(0)").attr("onchange","getRateUnitBySorId("+i+");");
	
	$(this).find("input:text:eq(1)").attr("id", "sorBasicRate"+ i);
	$(this).find("input:text:eq(1)").attr("name","rateAnalysisMaterialList[" + i+ "].sorBasicRate");
	
	$(this).find("input:text:eq(2)").attr("id", "sorIteamUnit"+ i);
	$(this).find("input:text:eq(2)").attr("name","rateAnalysisMaterialList[" + i+ "].sorIteamUnit");
	
	$(this).find("input:text:eq(3)").attr("id", "workEstimQuantity"+ i);
	$(this).find("input:text:eq(3)").attr("name","rateAnalysisMaterialList[" + i+ "].workEstimQuantity");
	
	$(this).find("input:text:eq(4)").attr("id", "workEstimAmount"+ i);
	$(this).find("input:text:eq(4)").attr("name","rateAnalysisMaterialList[" + i+ "].workEstimAmount");
	
	//achor type
	$(this).find("a:eq(0)").attr("onclick", "showOtherRate("+i+",this);");
  });
}

function calculateTotal() {
	
	/*$('#WorkEstimateMeasureDetailsTab').DataTable().destroy();*/
	var errorList =[];
	var subTotal=0;
	$('.appendableClass').each(function(i) {
				
		var toatlAmount=0.00;
	 	$("#meMentFormula"+i).prop("readonly", true);
 		$("#meMentLength"+i).prop("readonly", true);
 		$("#meMentBreadth"+i).prop("readonly", true);
 		$("#meMentHeight"+i).prop("readonly", true);
 		$("#meValue"+i).prop("readonly", true);
	 	
	 	if($("#meMentValType"+i).val()=="C"){
	 		$("#meMentLength"+i).prop("readonly", false);
	 		$("#meMentBreadth"+i).prop("readonly", false);
	 		$("#meMentHeight"+i).prop("readonly", false);
	 		$("#meMentFormula"+i).val("");
	 		
	 		if($("#meMentLength"+i).val() !="" && $("#meMentLength"+i).val() !=0 && $("#meMentBreadth"+i).val()!="" && $("#meMentBreadth"+i).val()!=0 && $("#meMentHeight"+i).val() !="" && $("#meMentHeight"+i).val() !=0)
	 			toatlAmount=$("#meMentLength"+i).val() * $("#meMentBreadth"+i).val() * $("#meMentHeight"+i).val();
	 		else if($("#meMentLength"+i).val() !="" && $("#meMentLength"+i).val() !=0 && $("#meMentBreadth"+i).val()!="" && $("#meMentBreadth"+i).val()!=0 )
	 			toatlAmount=$("#meMentLength"+i).val() * $("#meMentBreadth"+i).val();
	 		else if($("#meMentHeight"+i).val() !="" && $("#meMentHeight"+i).val() !=0 && $("#meMentBreadth"+i).val()!="" && $("#meMentBreadth"+i).val()!=0)
	 			toatlAmount= $("#meMentBreadth"+i).val() * $("#meMentHeight"+i).val();
	 		else if($("#meMentLength"+i).val() !="" && $("#meMentLength"+i).val() !=0 && $("#meMentHeight"+i).val()!="" && $("#meMentHeight"+i).val()!=0)
	 			toatlAmount= $("#meMentLength"+i).val() * $("#meMentHeight"+i).val();
	 		else if($("#meMentLength"+i).val() !="" && $("#meMentLength"+i).val() !=0)
	 			toatlAmount=$("#meMentLength"+i).val();
	 		else if($("#meMentBreadth"+i).val() !="" && $("#meMentBreadth"+i).val() !=0)
	 			toatlAmount=$("#meMentBreadth"+i).val();
	 		else if($("#meMentHeight"+i).val() !="" && $("#meMentHeight"+i).val() !=0)
	 			toatlAmount=$("#meMentHeight"+i).val();
	 		
	 	}else if($("#meMentValType"+i).val()=="D"){
	 		$("#meValue"+i).prop("readonly", false);
	 		$("#meMentLength"+i).val("");
	 		$("#meMentBreadth"+i).val("");
	 		$("#meMentHeight"+i).val("");
	 		toatlAmount=$("#meValue"+i).val();
	 	}else if($("#meMentValType"+i).val()=="F"){
	 		$("#meMentFormula"+i).prop("readonly", false);
	 		$("#meMentLength"+i).val("");
	 		$("#meMentBreadth"+i).val("");
	 		$("#meMentHeight"+i).val("");
	 		try {
	 			toatlAmount= eval($("#meMentFormula"+i).val()); 
	 		} catch (e) {
	 		    if (e instanceof SyntaxError) {
	 		    	toatlAmount=0.00;
	 		        //alert(e.message);
	 		    	errorList.push(getLocalMessage("work.measurement.sheet.details.formula.validation ")+"  "+e.message);
	 		    	displayErrorsOnPage(errorList);
	 		 }
	 	}
	 }
			 	
		$("#meMentToltal"+i).val("");
		if(toatlAmount!="")
		$("#meValue"+i).val((Number(toatlAmount)).toFixed(4));
		if($("#meMentType"+i).val()=="A" && toatlAmount!=undefined ){
			
			   var amountAdd=toatlAmount*$("#meMentNumber"+i).val();
			   if(amountAdd!="")
			   $("#meMentToltal"+i).val(amountAdd.toFixed(4));
			}else if($("#meMentType"+i).val()=="D" && toatlAmount!=undefined ){
			 	var amountSub=toatlAmount*$("#meMentNumber"+i).val();
			 	 if(amountSub!="")
			 	$("#meMentToltal"+i).val(-1*(amountSub.toFixed(4)));
			 }
		subTotal+=+$("#meMentToltal"+i).val();
			 	
	});
	 if(subTotal!="")
	$("#subTotal").val(subTotal.toFixed(4));
	/*$("#WorkEstimateMeasureDetailsTab").dataTable({
		"oLanguage": { "sSearch": "" } ,
		"aLengthMenu": [ [10,20,30,-1], [10,20,30,"All"] ],
	    "iDisplayLength" : 10, 
	    "bInfo" : true,
	    "lengthChange": true,
	    "scrollCollapse": true,
	    "bSort" : false
	   }).fnPageChange( 'last' ); */
}

function validateMeasurementDetails(errorList){
	if ( $.fn.DataTable.isDataTable('#WorkEstimateMeasureDetailsTab') ) {
	  $('#WorkEstimateMeasureDetailsTab').DataTable().destroy();
	}
	   $('.appendableClass').each(function(i) {
		     var particulars = $("#meMentParticulare" + i).val();
			 var subTotal = $("#meMentToltal" + i).val();
			 var measurementList = i+1;	
			 
			 if(particulars == null || particulars == ""){
				 errorList.push(getLocalMessage("work.measurement.sheet.details.enter.particulars")+" "+measurementList); 
			 }
			 if(subTotal =="" || subTotal =="0"){
				 errorList.push(getLocalMessage("work.measurement.sheet.details.sub.total.does.not.blank")+" "+measurementList);
			 }				   
	   });
	  
	   return errorList;
}

function saveWorkEstimateMeasureDetails(element){
	
	var errorList = [];
	errorList = validateMeasurementDetails(errorList);
	if(errorList.length > 0){
		 $('#WorkEstimateMeasureDetailsTab').DataTable();
		$("#errorDiv").show();
	    displayErrorsOnPage(errorList);
	}else {
		 $('#WorkEstimateMeasureDetailsTab').DataTable().destroy();
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(
				'WorkEstimate.html?SaveLbhForm', 'POST',
				requestData, false);
		showConfirmBox(getLocalMessage("works.estimate.measure.sheet.save"));
	}	
}

function showConfirmBox(sucessMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'+sucessMsg+'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceed()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);

	return false;
}

function proceed() {
	measurementSheet();
	$.fancybox.close();

}


function getDateBySorName(){
	if($("#sorName").val()==0){
		return false;
	}
	var requestData = {
			"sorName" : $("#sorName").val()
		};
		var result = __doAjaxRequest("WorkEstimate.html?getDateBySorName", 'post',
				requestData, false, 'json');
		var dates=result.split(",");
		$('#fromDate').val(dates[0]);
		if(dates[1]!='null'){
			var dateAr =dates[1].split('-');
			var newDate = dateAr[2] + '/' + dateAr[1] + '/' + dateAr[0];		
		   $('#toDate').val(newDate);
		}
		else{
			$('#toDate').val("dd/MM/yyyy");
		}
}

function getRateUnitBySorId(index){
	
	if($("#gRateMastId"+index).find("option:selected").attr('code')!=undefined){
	var rateAndunit = $("#gRateMastId"+index).find("option:selected").attr('code').split(",");
	$("#sorBasicRate"+index).val(rateAndunit[0]);
	$("#sorIteamUnit"+index).val(rateAndunit[1]);
	
	}
}

function showOtherRate(index,elements){
	
	var errorList = [];
	errorList = validateRateAnalysis(errorList);
	if(errorList.length > 0){
		$("#errorDiv").show();
	    displayErrorsOnPage(errorList);		
	}
	else {
		$("#materialMapKey").val($("#gRateMastId"+index).val());
		var divName = '.content-page';
		var url = "WorkEstimate.html?AddWorkLabour";
		var requestData = $.param($('#rateAnalysis').serializeArray())
		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
	}
	
}

function validateRateAnalysis(errorList){
	
	 $('.appendableClass').each(function(i) {
	     var materials = $("#gRateMastId" + i).val();
		 var quantity = $("#workEstimQuantity" + i).val();
		 var rateList = i+1;	
		 
		 if(materials == null || materials == ""){
			 errorList.push(getLocalMessage("work.measurement.sheet.details.enter.materials")+" "+rateList); 
		 }
		 if(quantity =="" || quantity =="0"){
			 errorList.push(getLocalMessage("work.estimate.enter.quantity")+" "+rateList);
		 }				   
   });
   return errorList;
 }

function saveRateAnalysis(element){
	
	var errorList = [];	
	errorList = validateRateAnalysis(errorList);							
	if(errorList.length > 0){								
		$("#errorDiv").show();
	    displayErrorsOnPage(errorList);	
	}else{
		var formName = findClosestElementId(element, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(
				'WorkEstimate.html?saveRateAnalysis', 'POST',
				requestData, false);
		showConfirmBox(getLocalMessage("works.estimate.rate.analysis.save"));
	}
	
}

/*function calculateToatlMaterial() {
	
	$('.appendableClass').each(
			function(i) {
				var toatlAmount = $("#sorBasicRate" + i).val()
						* $("#workEstimQuantity" + i).val();
				$("#workEstimAmount" + i).val(toatlAmount);
			});
}*/

