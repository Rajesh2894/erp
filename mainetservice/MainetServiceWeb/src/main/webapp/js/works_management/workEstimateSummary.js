/**
 * @author Vishwajeet.kumar
 * @author Jeetendra.pal
 * @since 5 feb 2018
 */

var fileArray=[];
var removeOverHeadById=[];
var removeNonSorById=[];
var removeLabourById=[];
var removeDirectAbstract=[];
var removeLabourFormIdArray=[];
var removeMachineryById=[];

$(document).ready(function() { $(function()
	 { $('.custDate').datepicker({
		dateFormat : 'dd/mm/yy',
		changeMonth : true,
		changeYear : true
	 });
	 
	 $("#gisid").prop("readonly",false);
	 addAsterixInAttachDocTable(); //Defect #162291
	 
   });

/**
 * This method is used to calculate NonSor Sub total
 * workNonSorItems.jsp
 *  START METHOD
 */
$('.appendableClass').each(function (i) {
	 var sum = 0;
	 var basicRate = parseFloat($("#sorRate" + i).val());
	 var quantity = parseFloat($("#workQuantity" + i).val());
	 if (!isNaN(quantity)) {
   	 sum =+ (basicRate * quantity);
    }
	
	 if(!isNaN(sum)){
		 $("#workAmount" + i).val(sum.toFixed(2));
        var grandTotal = 0;
 	     $(".amount").each(function () {
 	    	 var nonSorTotal = parseFloat($(this).val());
 	          grandTotal += isNaN(nonSorTotal) ? 0 : nonSorTotal;
 	     });
 	 var tot =   $("#totalnonSor").val(grandTotal.toFixed(2));
 	  
 	 $('#totalSor').html(grandTotal.toFixed(2));
     }
});


/**
 * This method is used to calculate OverHead Data Sub total
 * workOverHeadsForm.jsp
 *  START METHOD
 */
$('.appendableClass').each(function (i) {
      var grandTotal = 0;
      $(".overheadcalculation").each(function (i) {
	     var overHeadValue = parseFloat(parseFloat($(this).val()));
         grandTotal += isNaN(overHeadValue) ? 0 : overHeadValue;
    });
      $("#totalOverhead").val(grandTotal.toFixed(2));
 });  	 



/**
 * This method is used to calculate measurementList Sub total
 * workEstimateMeasurementList.jsp
 *  START METHOD
 */
    $('.appendableClass').each(function(i) {
    	var grandTotal = 0;
        $(".measureMentCal").each(function (i) {
  	     var total = parseFloat(parseFloat($(this).val()));
           grandTotal += isNaN(total) ? 0 : total;
      });
        $("#subTotalAmount").val(grandTotal.toFixed(2));
    });
    var selectedType = $("#projId").find("option:selected").attr('code');
	if(!(selectedType==undefined)){
		$("#projCode").val(selectedType);
	}
	  getDefinationNumber();
	  reStructureRateTable();
	  getDateBySorName();
	  
	$('.appendableClass').each(function(i) {
		if($("#gRateMastId"+i).val()!=null && $("#gRateMastId"+i).val()!=""){
			$("#selectType"+i).val("M");
		}else{
		     $("#selectType"+i).val("D");
			  $("#gRateMastId"+i).html('');
		}
	});

	
	/**
	 * This method is used to calculate all measurement amount
	 *  START METHOD
	 */	
calculateTotal();


 
$('.decimal').on('input', function() {
    this.value = this.value
      .replace(/[^\d.]/g, '')             // numbers and decimals only
      .replace(/(^[\d]{5})[\d]/g, '$1')   // not more than 5 digits at the beginning
      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
      .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
  });
	  
$('.decimalDirectAbstract').on('input', function() {
    this.value = this.value
      .replace(/[^\d.]/g, '')             // numbers and decimals only
      .replace(/(^[\d]{5})[\d]/g, '$1')   // not more than 5 digits at the beginning
      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
      .replace(/(\.[\d]{4})./g, '$1');    // not more than 4 digits after decimal
  });

   $("#datatables").dataTable({ "oLanguage": { "sSearch": "" } ,
	 "aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	 "iDisplayLength" : 5, 
	  "bInfo" : true,
	  "lengthChange": true 
	});	
			
$("#toBeHide").hide();	   
 
$("#directAbstractTab").dataTable({ "oLanguage": { "sSearch": "" } ,
	 "aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	 "iDisplayLength" : 5, 
	  "bInfo" : true,
	  "lengthChange": true 
	});

// Defect #80050
$("#estimateFromSor").dataTable({ "oLanguage": { "sSearch": "" } ,
	 "aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
	 "iDisplayLength" : 5, 
	  "bInfo" : true,
	  "lengthChange": true 
	});
		   
$("#directAbstractTab").on("click", '.addButton',function(e) {

	if ( $.fn.DataTable.isDataTable('#directAbstractTab') ) {
			$('#directAbstractTab').DataTable().destroy();
		 }
		var errorList = [];	
		errorList = validateAbstractTab(errorList);
		if(errorList.length > 0){
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
			return false;
		}else{
			$("#errorDiv").hide();
            e.preventDefault();		
			var content = $('#directAbstractTab tr').last().clone();
			$('#directAbstractTab tr').last().after(content);
			content.find("input:text").val('');
			//content.find("select:eq(0)").val('0');
			content.find("select:eq(1)").val('0');			
            content.find("input:hidden").val('');
            content.find('div.chosen-container').parent('td').children('select').css({'display':'block'});
            content.find('div.chosen-container').remove();
			content.find("select:eq(0)").val('0').chosen().trigger("chosen:updated"); 
			
		 	$('.decimalDirectAbstract').on('input', function() {
			    this.value = this.value
			      .replace(/[^\d.]/g, '')             // numbers and decimals only
			      .replace(/(^[\d]{5})[\d]/g, '$1')   // not more than 5 digits at the beginning
			      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
			      .replace(/(\.[\d]{4})./g, '$1');    // not more than 4 digits after decimal
			  });							
			reOrderTableIdSequence();
		}												
		/*if(!validatedirectAbstractList()){
			$('#directAbstractTab').DataTable();
			return false;
		}*/						
	});

		  		   
$("#directAbstractTab").on("click", '.delButton', function(e) {
	if ( $.fn.DataTable.isDataTable('#directAbstractTab') ) {
		 $('#directAbstractTab').DataTable().destroy();
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
			removeDirectAbstract.push(deletedSodId);
		}
		$('#removeDirectAbstract').val(removeDirectAbstract);
		  reOrderTableIdSequence();
	 }
	e.preventDefault();
});	


function reOrderTableIdSequence() {
	var cpMode = $("#modeCpd").val();
	$('.appendableClass').each(function(i) {
		if(cpMode =='N'){
			$(this).find("select:eq(0)").attr("id", "sordCategory"+ i)
			$(this).find("input:text:eq(0)").attr("id","sordSubCategory" + i);
			$(this).find("input:text:eq(1)").attr("id","sorDIteamNo" + i);
			$(this).find("input:text:eq(2)").attr("id","sorDDescription" + i);
			
			$(this).find("input:text:eq(3)").attr("id","meMentLengthD" + i);
			$(this).find("input:text:eq(4)").attr("id","meMentBreadthD" + i);
			$(this).find("input:text:eq(5)").attr("id","meMentHeightD" + i);
			$(this).find("input:text:eq(6)").attr("id","meNosDirect" + i);
			$(this).find("input:text:eq(7)").attr("id","sorBasicRateD" +i);
			$(this).find("input:text:eq(8)").attr("id","workQuantityD" + i);
			
			$(this).find("input:text:eq(9)").attr("id","workEstimAmountD" + i);
			$(this).find("select:eq(1)").attr("id", "sorIteamUnit"+ i)
			$(this).find("input:hidden:eq(0)").attr("id", "workEstemateId" + (i));

			$(this).find("input:button:eq(4)").attr("id", "delButton"+ i);
			$(this).parents('tr').find('.delButton').attr("id", "delButton"+ i);
			$(this).parents('tr').find('.addButton').attr("id","addButton" + i);
			
			$(this).find("select:eq(0)").attr("name","workEstimateList[" + i+ "].sordCategory");
			$(this).find("select:eq(1)").attr("name","workEstimateList[" + i+ "].sorIteamUnit");
					 	
			$(this).find("input:hidden:eq(0)").attr("name","workEstimateList[" + i+ "].workEstemateId");
			
			$(this).find("input:text:eq(0)").attr("name","workEstimateList[" + i+ "].sordSubCategory");
		
			$(this).find("input:text:eq(1)").attr("name","workEstimateList[" + i+ "].sorDIteamNo");
			$(this).find("input:text:eq(2)").attr("name","workEstimateList[" + i+ "].sorDDescription");
			
			$(this).find("input:text:eq(3)").attr("name","workEstimateList[" + i+ "].meMentLength");
			$(this).find("input:text:eq(4)").attr("name","workEstimateList[" + i+ "].meMentBreadth");
			$(this).find("input:text:eq(5)").attr("name","workEstimateList[" + i+ "].meMentHeight");
			$(this).find("input:text:eq(6)").attr("name","workEstimateList[" + i+ "].meNos");

			$(this).find("input:text:eq(7)").attr("name","workEstimateList[" + i+ "].sorBasicRate");			
			$(this).find("input:text:eq(8)").attr("name","workEstimateList[" + i+ "].workEstimQuantity");	
			$(this).find("input:text:eq(9)").attr("name","workEstimateList[" + i+ "].workEstimAmount");
			
			/*var toatlAmount=$("#sorBasicRate"+i).val() * $("#workEstimQuantity"+i).val();
			 $("#workEstimAmount"+i).val(toatl);	*/	
			calculateTotalRate();
			
		}else{
			$(this).find("select:eq(0)").attr("id", "sordCategory"+ i)
			$(this).find("input:text:eq(1)").attr("id","sorDIteamNo" + i);
			$(this).find("input:text:eq(2)").attr("id","sorDDescription" + i);
			
			$(this).find("input:text:eq(3)").attr("id","meMentLengthD" + i);
			$(this).find("input:text:eq(4)").attr("id","meMentBreadthD" + i);
			$(this).find("input:text:eq(5)").attr("id","meMentHeightD" + i);
			$(this).find("input:text:eq(6)").attr("id","meNosDirect" + i);
									
			$(this).find("input:text:eq(7)").attr("id","workQuantityD" + i);
			$(this).find("input:text:eq(8)").attr("id","sorBasicRateD" +i);
			$(this).find("input:text:eq(9)").attr("id","workEstimAmountD" + i);
			$(this).find("select:eq(1)").attr("id", "sorIteamUnit"+ i)
			$(this).find("input:hidden:eq(0)").attr("id", "workEstemateId" + (i));

			$(this).find("input:button:eq(4)").attr("id", "delButton"+ i);
			$(this).parents('tr').find('.delButton').attr("id", "delButton"+ i);
		
			$(this).parents('tr').find('.addButton').attr("id","addButton" + i);
			
			$(this).find("select:eq(0)").attr("name","workEstimateList[" + i+ "].sordCategory");
			$(this).find("select:eq(1)").attr("name","workEstimateList[" + i+ "].sorIteamUnit");
					 	
			$(this).find("input:hidden:eq(0)").attr("name","workEstimateList[" + i+ "].workEstemateId");
			$(this).find("input:text:eq(1)").attr("name","workEstimateList[" + i+ "].sorDIteamNo");
			$(this).find("input:text:eq(2)").attr("name","workEstimateList[" + i+ "].sorDDescription");
			
			$(this).find("input:text:eq(3)").attr("name","workEstimateList[" + i+ "].meMentLength");
			$(this).find("input:text:eq(4)").attr("name","workEstimateList[" + i+ "].meMentBreadth");
			$(this).find("input:text:eq(5)").attr("name","workEstimateList[" + i+ "].meMentHeight");
			$(this).find("input:text:eq(6)").attr("name","workEstimateList[" + i+ "].meNos");
			
			$(this).find("input:text:eq(7)").attr("name","workEstimateList[" + i+ "].workEstimQuantity");
			$(this).find("input:text:eq(8)").attr("name","workEstimateList[" + i+ "].sorBasicRate");
			$(this).find("input:text:eq(9)").attr("name","workEstimateList[" + i+ "].workEstimAmount");
			
			/*var toatlAmount=$("#sorBasicRate"+i).val() * $("#workEstimQuantity"+i).val();
			 $("#workEstimAmount"+i).val(toatl);	*/	
			calculateTotalRate();			
		}								
	  });
	//$('#directAbstractTab').DataTable();
}
			//end dynamic table
			
			
$("#attachDoc").on("click", '.delButton', function(e) {
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
	   //	reOrderTableIdSequence();
	}
	e.preventDefault();
});	
			
			
 $("#deleteDoc").on("click", '#deleteFile', function(e) {
	var errorList = [];
	if (errorList.length > 0) {
	  $("#errorDiv").show();
	  showErr(errorList);
	 return false;
	}else{
           $(this).parent().parent().remove();
			var fileId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			if(fileId != ''){
				fileArray.push(fileId);
			}
		    $('#removeEnclosureFileById').val(fileArray);
		    addAsterixInAttachDocTable(); //Defect #162291
		}
	});	
 
 
 
 
 /**
  * This method is used to Search All Works Estimations
  * workEstimateSummary.jsp
  *  START METHOD
  */
   $(".searchWorkDefination").click(function() {
	  
 	var errorList=[];
 		var estimateNo = $('.workEstimationNo').val();
 		var projectId = $('#projectId').val();
 		var workName = $('#workName').val();
 		var status = $('#status').val();
 		var fromDate   = $('.fromDate').val();
 		var todate = $('.todate').val();
 		var langId=$("#langId").val();
 		if(projectId != '0' && projectId != '' ){
 			if(workName == '' || workName == '0'){
 				errorList.push(getLocalMessage('work.estimate.select.work.name'));
 				displayErrorsOnPage(errorList);
 				return false;
 			}
 		}
 		//Defect #80050
 		if(estimateNo === undefined){
 			estimateNo = '';
 		}
 		if( estimateNo!= '' || projectId != '0' || workName != '' || status !='0' || fromDate !='' || todate !=''){
 			var requestData = 'estimateNo='+estimateNo +'&projectId='+projectId+'&workName='+workName+'&status='+status+'&fromDate='+fromDate+'&toDate='+todate;
 			var table = $('#datatables').DataTable();
 			table.rows().remove().draw();
 			$(".warning-div").hide();
 			var ajaxResponse = __doAjaxRequest('WorkEstimate.html?filterEstimateListData', 'POST',requestData, false,'json');
 			var viewProgress=getLocalMessage("work.management.workView");
 			var editProgress=getLocalMessage("work.management.editView");
 			var sendApproval=getLocalMessage("work.management.sendTool");
 			var actionHistory=getLocalMessage("work.management.actionTool");
 			var result = [];
 			if(ajaxResponse.length == 0){
 				errorList.push(getLocalMessage('work.management.vldn.grid.nodatafound'));
 				displayErrorsOnPage(errorList);
 				return false;
 			}
 			$.each(ajaxResponse, function(index){
 				var obj = ajaxResponse[index];
 				var workStatus=getLocalMessage("status.draft");
 				if(langId == 1)
				{
 					obj.projName=obj.projName;
 					
				}
			else{
				obj.projName=obj.projNameReg;
			}
 				 if(obj.workStatus !="Draft"){
 					result.push([obj.projName,obj.workcode,obj.workName,'<div style="display: flex; justify-content: flex-end"> <div>'+obj.workEstAmt+'</div></div>' ,workStatus,'<td >'+
 						  '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="getActionForDefination(\''+obj.workId+'\',\'V\')" title="'+viewProgress+'"><i class="fa fa-eye"></i></button>'+
 						  '<button type="button"  class="btn btn-primary btn-sm margin-right-10 margin-left-30"  onclick="getActionForWorkFlow(\''+obj.workId+'\',\'V\')" title="'+actionHistory+'"><i class="fa fa-history"></i></button>'+
 						  '</td>']);
 				 } else if(obj.workEstAmt == '0'){
 					 result.push([obj.projName,obj.workcode,obj.workName,'<div style="display: flex; justify-content: flex-end"> <div>'+obj.workEstAmt+'</div></div>' ,workStatus,'<td >'+
 						  '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="getActionForDefination(\''+obj.workId+'\',\'V\')" title="'+viewProgress+'"><i class="fa fa-eye"></i></button>'+
 						  '<button type="button" class="btn btn-warning btn-sm btn-sm margin-right-10" onclick="getActionForDefination(\''+obj.workId+'\',\'E\')"  title="'+editProgress+'"><i class="fa fa-pencil-square-o"></i></button>'+
 						  '</td>']);
 				 }				 
 				 else {
 						result.push([obj.projName,obj.workcode,obj.workName,'<div style="display: flex; justify-content: flex-end"> <div>'+obj.workEstAmt+'</div></div>' ,workStatus,'<td >'+
 							  '<button type="button"  class="btn btn-blue-2 btn-sm margin-right-10 margin-left-30"  onclick="getActionForDefination(\''+obj.workId+'\',\'V\')" title="'+viewProgress+'"><i class="fa fa-eye"></i></button>'+
 							  '<button type="button" class="btn btn-warning btn-sm btn-sm margin-right-10" onclick="getActionForDefination(\''+obj.workId+'\',\'E\')"  title="'+editProgress+'"><i class="fa fa-pencil-square-o"></i></button>'+
 							  '<button type="button" class="btn btn-green-3 btn-sm btn-sm margin-right-10" onclick="sendForApproval(\''+obj.workId+'\',\'S\')"  title="'+sendApproval+'"><i class="fa fa-share-square-o"></i></button>'+
 							  '</td>']);
 					}
 				
 	         });
 			table.rows.add(result);
 			table.draw();
 		}else{
 			errorList.push(getLocalMessage('work.management.valid.select.any.field'));
 			displayErrorsOnPage(errorList);
 		}
    });
});					    
		   
 $('#addlabourForm').validate({
	onkeyup: function(element) {
	this.element(element);
	console.log('onkeyup fired');
	},
	onfocusout: function(element) {
	this.element(element);
	console.log('onfocusout fired');
	}});
		   
			 $('[id^="sordCategory"]').data('rule-required',true);
			 $('[id^="sorDIteamNo"]').data('rule-required',true);
			 $('[id^="sorDDescription"]').data('rule-required',true);
			 $('[id^="sorIteamUnit"]').data('rule-required',true);
			 $('[id^="sorBasicRate"]').data('rule-required',true);

function calculateTotalRate() {
	$('.appendableClass').each(function(i) {
		var total = 0;			
		var lbh = 0;			
		var basicRate =parseFloat($("#sorBasicRateD" + i).val());
		var quantity = parseFloat($("#workQuantityD" + i).val());
		
		var length = parseFloat($("#meMentLengthD" + i).val());
		var breadth = parseFloat($("#meMentBreadthD" + i).val());
		var height = parseFloat($("#meMentHeightD" + i).val());
		var nos = $("#meNosDirect" + i).val();
		

		if (!isNaN(length) && !isNaN(breadth) && !isNaN(height)) {
			lbh = length * breadth * height;
		} else if (!isNaN(length) && !isNaN(breadth)) {
			lbh = length * breadth;
		} else if (!isNaN(breadth) && !isNaN(height)) {
			lbh = breadth * height;
		} else if (!isNaN(length) && !isNaN(height)) {
			lbh = length * height;
		}
		
		if(!isNaN(lbh) && lbh!=0 && !isNaN(nos) && nos!=0) {
			lbh = lbh * nos;
			quantity=lbh;
			$("#workQuantityD" + i).val(lbh.toFixed(4));
			$("#workQuantityD"+ i).prop("readonly",true);
		}
		else{
			$("#workQuantityD"+ i).prop("readonly",false);
		}
				
		if(!isNaN(quantity) && !isNaN(basicRate)){
			total =+(basicRate * quantity);
		}	
		if(!isNaN(total)){
			$("#workEstimAmountD" + i).val(total.toFixed(2));			
		}
										 					
	});
}


function openAddWorkEstimate(formUrl, actionParam, flag) {
	if (!actionParam) {
		actionParam = "add";
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
	if(actionParam=='AddWorkEstimate' && $("#sorList").val()!="")
		getSorByValue();
	if(flag=='true'){
		$('.fancybox-overlay').hide();
	}
}

function getSorByValue() {
		var actionParam = {
		'actionParam' : $("#sorList").val(),
		'sorId' : $("#sorId").val(),
		'workFormPrevious' : $("#workDefination").val()
	}
	var url = "WorkEstimate.html?selectAllSorData";
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$("#estimationTagDiv").html(ajaxResponse);
	prepareTags();
}

function backForm() {
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'WorkEstimate.html');
	$("#postMethodForm").submit();
}

function validateAbstractTab(errorList){
	
	   $('.appendableClass').each(function(i) {
		   var directAbstract = i+1;
		   var category = $("#sordCategory" +i).find("option:selected").attr('value');
		   var subCategory = $("#sordSubCategory" +i).val();
		   var itemCode = $("#sorDIteamNo"+i).val();
		   var description = $("#sorDDescription" +i).val();
		   var itemUnit = $("#sorIteamUnit"+i).find("option:selected").attr('value');
		   var rate = $("#sorBasicRateD" +i).val();
		   var quantity = $("#workQuantityD" +i).val();
		   var total = $("#workEstimAmountD" + i).val();
		   var cpMode = $("#modeCpd").val();
		   var meNos = $("#meNosDirect" + i).val();
		   
		   if(category =="" || category =="0"){
			   errorList.push(getLocalMessage("sor.select.category") +" "+directAbstract);
		   }
		   if(cpMode == 'N'){
			   if(subCategory==null || subCategory == ""){
				   errorList.push(getLocalMessage("work.estimate.select.sub.category") +" "+directAbstract);
			   }  
		   }		 		   
		   if(itemCode =="" || itemCode ==null){
			   errorList.push(getLocalMessage("work.estimate.enter.item.code") +" "+directAbstract);
		   }
		   if(description =="" || description ==null){
			   errorList.push(getLocalMessage("work.estimate.enter.description") +" "+directAbstract);
		   }
		   if(meNos =="" || meNos =="0"){
			   errorList.push(getLocalMessage("work.estimate.enter.nos") +" "+directAbstract);
		   }
		   if(itemUnit =="" || itemUnit =="0"){
			   errorList.push(getLocalMessage("work.estimate.select.unit") +" "+directAbstract);
		   }
		   if(rate == ""||rate == ""){
			   errorList.push(getLocalMessage("work.estimate.enter.rate") +" "+directAbstract);
		   }
		   if(quantity == ""||quantity=="0"){
			   errorList.push(getLocalMessage("work.estimate.enter.quantity") +" "+directAbstract);
		   }
		   if(total=="0" || total ==""){
			   errorList.push(getLocalMessage("work.estimate.enter.total") +" "+directAbstract);
		   }
	   });
	   return errorList;
}


function validateEstimateFromSor(errorList){
	
	var itemCode=[];
	$('.appendableFormSorClass').each(function(i) {	
		 var index = i+1;
		 var sorIteamNumber = $("#sorIteamNumber"+i).find("option:selected").attr('value');
		 var sorChapter = $("#sorChapter"+i).find("option:selected").attr('value');
		 var description = $("#sorDDescription" +i).val();
		 var itemUnit = $("#sorIteamUnit"+i).find("option:selected").attr('value');
		 var rate = $("#sorBasicRate" +i).val();
		   
		if (itemCode.includes($("#sorIteamNumber"+i).val())) {
			errorList.push(getLocalMessage("wms.estimate.vldn.duplicate.itemno")+" "+$("#sorIteamNumber"+i).val());
		}
		itemCode.push($("#sorIteamNumber"+i).val());
		
		if(sorChapter =="" || sorChapter =="0"){
			   errorList.push(getLocalMessage("work.estimate.sor.select.chapter") +" "+index);
		   }
		
		if(sorIteamNumber =="" || sorIteamNumber =="0" || sorIteamNumber == null){
			   errorList.push(getLocalMessage("work.estimate.sor.select.item") +" "+index);
		   }	
		
		if(description =="" || description ==null){
			   errorList.push(getLocalMessage("work.estimate.enter.item.description") +" "+index);
		  }
		if(itemUnit =="" || itemUnit =="0"){
			   errorList.push(getLocalMessage("work.estimate.select.unit") +" "+index);
		   }
		if(rate == ""||rate == ""){
			   errorList.push(getLocalMessage("work.estimate.enter.rate") +" "+index);
		  }
  });
	 return errorList;
}

function saveData(element){
	if ($.fn.DataTable.isDataTable('#datatables')) {
		$('#datatables').DataTable().destroy();
	}
	if ($.fn.DataTable.isDataTable('#directAbstractTab')) {
		$('#directAbstractTab').DataTable().destroy();
	}	
	if ($.fn.DataTable.isDataTable('#estimateFromSor')) {
		$('#estimateFromSor').DataTable().destroy();
	}	
	var errorList = [];
	
	var projName = $("#projId").find("option:selected").attr('value');
	var workName = $("#workDefination").val();
	var sorList = $("#sorList").val();
	var sorId = $("#sorId").find("option:selected").attr('value');

	if(projName==""||projName=="0"){
		errorList.push(getLocalMessage("work.Def.valid.select.projName"));
	}
	if(workName =="" || workName ==null){
		errorList.push(getLocalMessage("work.estimate.select.work.name"));
	}
	if(sorList == "" || sorList == null){
		errorList.push(getLocalMessage("work.estimate.select.estimate.type"));
	}
	if(sorList=="S" && (sorId == null || sorId == "" || sorId == "0")){
		errorList.push(getLocalMessage("sor.select.sorname"));
	}
	if(sorList=="P" && (sorId == null || sorId == "" || sorId == "0")){
		errorList.push(getLocalMessage("work.estimate.sor.workname.vldn"));
	}

	if(sorList=='U'){
		errorList = validateAbstractTab(errorList);
	}
	
	else if(sorList=="S" || sorList=="P"){		
		errorList = validateEstimateFromSor(errorList);		
	}
	if(errorList.length > 0){
		$("#errorDiv").show();
		$('#datatables').DataTable();
		$('#directAbstractTab').DataTable();
		displayErrorsOnPage(errorList);
	}else{
		
		/*$('#estimateFromSor tbody .appendableFormSorClass').each(function(i) {
			var ChkBx =$("#sorCheckBox" + i).is(':checked');
			var sorCheckedEstimationId=$("#sorCheckedEstimationId" + i).val();
			if (sorCheckedEstimationId =="" && !ChkBx) {
				$("#sorCheckBox" + i).prop("disabled","disabled");
				$("#deletedFlagForSor" + i).prop("disabled","disabled");
			}
			else if(sorCheckedEstimationId !="" && !ChkBx){
				$("#deletedFlagForSor" + i).val("Y");
			}
		});*/
		
		var requestData=$("#workEstimate :input[value!='on']").serialize();
		
		var response = doAjaxLoading('WorkEstimate.html?saveEstimateData',
				 requestData, 'json');
		if (response.length!=0) {
			jQuery.each(response, function() {
				errorList.push(this);
			});
			$("#errorDiv").show();
			/*$('#datatables tbody .appendableClass').each(function(i) {
				$("#deletedFlagForSor" + i).val("");
				$("#sorCheckBox" + i).prop("disabled",false);
				$("#deletedFlagForSor" + i).prop("disabled",false);
			});
			$('#datatables').DataTable();
			$('#directAbstractTab').DataTable();*/
			showErr(errorList);
		} else {
			showConfirmBox(getLocalMessage('work.estimate.sor.item.saved.success'));
		}
	}	
}



function getProjCode() {
	var selectedType = $("#projId").find("option:selected").attr('code');
	if(selectedType==undefined){
		return false;
	}else{
		$("#projCode").val(selectedType);	
	}
	
	if(!($("#saveMode").val()=="E" || $("#saveMode").val()=="V")){
	$('#workDefination').find('option').remove();
	$("#workDefination").trigger("chosen:updated");
	var requestData={"projId":$("#projId").val()};
	var response = __doAjaxRequest('WorkEstimate.html?getWorksList', 'POST', requestData, false,'json');
	 $.each(response, function(key, value) {
		 $('#workDefination').append($("<option></option>").attr("value",value.workId).attr("code",value.workcode).text(value.workName));
	});
	 
	 $("#workDefination").val(response).trigger('chosen:updated');
	 $("#definationNumber").val("");
	}
}
function getDefinationNumber() {
	var selectedType = $("#workDefination").find("option:selected").attr('code');
	$("#definationNumber").val(selectedType);
}

function measurementSheetAction(param,sorDId,workEId,sorId){
	
	var actionParam = {
			'sorName' : sorDId,
			'workEId' : workEId,
			'sorId' : sorId
			}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('WorkEstimate.html' + '?' + param, actionParam, 'html',
			divName);

	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function openAddWorkLabour(formUrl,actionParam){
	if (!actionParam) {
		actionParam = "add";
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function measurement(){	
	var divName = '.content-page';
	var url = "WorkEstimate.html?AddMeasurementForm";
	var actionParam = {
			'actionParam' : 'actionParam'
		}
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function measurementSheet(){
	var divName = '.content-page';
	var url = "WorkEstimate.html?AddMeasurementSheet";
	var actionParam = {
			'actionParam' : 'actionParam'
		}
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	
}
function toBeHide(){
	$("#toBeHide").show();
}

function getLabourUnitBySorId(index){
	var rateAndunit = $("#gRateMastId"+index).find("option:selected").attr('code').split(",");
	$("#sorLabourRate"+index).val(rateAndunit[0]);
	$("#sorIteamUnit"+index).val(rateAndunit[1]);
}
function getMachineryUnitBySorId(index){
	var rateAndunit = $("#gRateMastId"+index).find("option:selected").attr('code').split(",");
	$("#sorLabourRate"+index).val(rateAndunit[0]);
	$("#sorIteamUnit"+index).val(rateAndunit[1]);
}

function getActionForDefination(workId,mode){
	var divName = '.content-page';
	var url = "WorkEstimate.html?editEstimation";
	var actionParam = {
			'workId' : workId,
			'mode' :mode
		}
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	getSorByValue();
	
}

function getActionForWorkFlow(workId,mode){
	var url = "WorkEstimate.html?getWorkFlowHistory";
	var actionParam = {
			'workId' : workId,
			'mode' :mode
	}
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	var errMsgDiv = '.msg-dialog-box';
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(ajaxResponse);
	$(errMsgDiv).show();
	showModalBox(errMsgDiv);
	return false;
}

function rateTypeData(index) {
	reStructureRateTable();
	var rateType = $("#rateTypeId" + index ).find("option:selected").attr('value');
	var selectType=$("#selectType" + index).find("option:selected").attr('value');	
	if(rateType =='R' || rateType ==undefined){
		$('#gRateMastId'+index).html('');
		$("#workEstimQuantity"+index).prop("readonly", true); 
		$("#workEstimAmount"+index).prop("readonly", false);
		return false;
	}
	if(selectType=='D' || selectType==undefined){
		$('#gRateMastId'+index).html('');
		$("#workEstimAmount"+index).prop("readonly", false); 
 		 $("#workEstimQuantity"+index).prop("readonly", true); 
		return false;
	}
	 $("#workEstimAmount"+index).prop("readonly", true); 
	 $("#workEstimQuantity"+index).prop("readonly", false); 
	
	 var requestData = {
		"rateType" :$("#rateTypeId"+index).find("option:selected").attr('code')
	};
	var result = __doAjaxRequest("WorkEstimate.html?getRateListByType", 'post',
			requestData, false, 'json');
	
	var sorList = result;
	$('#gRateMastId'+index).html('');
	$('#gRateMastId'+index).append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	$.each(sorList, function(indexData, value) {
		var code=value.maItemUnit+","+value.maRate;
		$('#gRateMastId'+index)
				.append(
						$('<option></option>').attr("value", value.maId).attr("code",code).text(
								(value.maDescription)));
		$('#gRateMastId'+index).trigger("chosen:updated");
	});
}
function rateMachinaryData(index) {
	reStructureRateTable();
	var selectType=$("#selectType"+index).find("option:selected").attr('value');

	if(selectType=='D' || selectType==undefined){
		$('#gRateMastId'+index).html('');
		 $("#workEstimAmount"+index).prop("readonly", false); 
  		 $("#workEstimQuantity"+index).prop("readonly", true); 
		return false;
	}
	
	 $("#workEstimAmount"+index).prop("readonly", true); 
	 $("#workEstimQuantity"+index).prop("readonly", false); 
	var requestData = {
		"rateType" :$("#rateTypeId"+index).val()
	};
	var result = __doAjaxRequest("WorkEstimate.html?getRateListByType", 'post',
			requestData, false, 'json');
	
	var sorList = result;
	$('#gRateMastId'+index).html('');
	$('#gRateMastId'+index).append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));
	$.each(sorList, function(indexData, value) {
		var code=value.maItemUnit+","+value.maRate;
		$('#gRateMastId'+index)
				.append(
						$('<option></option>').attr("value", value.maId).attr("code",code).text(
								(value.maDescription)));
		$('#gRateMastId'+index).trigger("chosen:updated");
	});
}

function reStructureRateTable() {	
	var subTotal=0;
	$('.appendableClass').each(function(i) {
			var toatlAmount=0.00;
			$("#gRateMastId"+i).prop("readonly", true);
			$("#rate"+i).prop("readonly", true);
			$("#maItemUnit"+i).prop("readonly", true);				
		
			if($("#rateTypeId"+i).val()=="R")
				$("#selectType"+i).prop("readonly", true);
			 
			if($("#selectType"+i).val()=="D"  || $("#rateTypeId"+i).val()=="R"){
			 		$("#gRateMastId"+i).val("");
					$("#rate"+i).val("");
					$("#maItemUnit"+i).val("");
			 	}
			 	getRateUnitBySorId(i);
	 });
}

function getRateUnitBySorId(index){	
	var unitRateString=$("#gRateMastId"+index).find("option:selected").attr('code');
	if(unitRateString!="" && unitRateString!=undefined){
	var rateAndunit = $("#gRateMastId"+index).find("option:selected").attr('code').split(",");
	$("#maItemUnit"+index).val(rateAndunit[0]);
	$("#rate"+index).val(rateAndunit[1]);
	}
}

function getDateBySorName(){
	if($("#sorName").val()==0 || $("#sorName").val()==undefined ){
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

/**
 * This All Method  is used to create save Machinery Details
 * @return save Machinery
 *  START METHOD
 */

function openAddWorkMachinery(formUrl,actionParam){
	if (!actionParam) {
		actionParam = "add";
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
}

function saveMachinery(machineryData){
	var errorList = [];	
	if ($("#sorNameId").val() ==""){
		errorList.push(getLocalMessage("work.estimate.sor.name.required"));
	}
	errorList = validateMachineryList(errorList);	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
	} else {
		$("#errorDiv").hide();
		
		var formName = findClosestElementId(machineryData, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(
				'WorkEstimate.html?saveMachineryData', 'POST',
				requestData, false);
		showConfirmBox(getLocalMessage("work.estimate.machinery.creation.success"));
	}	
}


$("#MachineryDetails").on("click",'.addMachineryDetails',function(e) {
	  var count = $('#MachineryDetails tr').length - 1;
	  var errorList = [];
	  errorList = validateMachineryList(errorList);	
  if (errorList.length > 0) { 
  	       $("#errorDiv").show();
	              showErr(errorList);
			 } 
        else {
        	$("#errorDiv").addClass('hide');
			      e.preventDefault();
					var clickedRow = $(this).parent().parent().index();
					var content = $('#MachineryDetails tr').last().clone();
					$('#MachineryDetails tr').last().after(content);
					content.find("input:text").attr("value", "");
					content.find("select").attr("selected", "selected").val('');
					content.find("input:text").val('');
					content.find("input:hidden").val('');
					reOrderMachineryDetails();
				}
});

function validateMachineryList(errorList){
		$('.appendableClass').each(function(i){		
		var machineList = i+1;
		var selectType = $("#selectType"+i).find("option:selected").attr('value');
		if(selectType =="" || selectType ==null){
			errorList.push(getLocalMessage("work.estimate.select.type")+" "+machineList);
			return false;
		}
		if(selectType == "M"){
			var fromMaster = $("#gRateMastId" +i).val();
			var labourQuantity = $("#workEstimQuantity" +i).val();
			
			if(fromMaster == "" || fromMaster == "0"){
				errorList.push(getLocalMessage("work.estimate.select.form.master")+" "+machineList);
			}
			if(labourQuantity == "" || labourQuantity =="0"){
				errorList.push(getLocalMessage("work.estimate.enter.quantity")+" "+machineList);
			}			
		}else {
			var totalAmount = $("#workEstimAmount" + i).val();
			if(totalAmount == "" || totalAmount =="0"){
				errorList.push(getLocalMessage("work.estimate.enter.total.amount")+" "+machineList);
			}
		}				
	});	
	return errorList;
}


function calculateTotal(){
	 $('.appendableClass').each(function (i) {
		 var sum = 0;
	  	 if($("#selectType"+i).val()=='M'){
	  	     $("#workEstimAmount"+i).prop("readonly", true);
	  	     $("#workEstimQuantity"+i).prop("readonly", false); 
	  	     var machineRate = parseFloat($("#rate" + i).val());
	  	     var machineQuantity = parseFloat($("#workEstimQuantity" + i).val());
	  	     if ((!isNaN(machineQuantity)) && (!isNaN(machineRate ))) {
	  	    	 sum = (machineRate * machineQuantity);
	  	     	}
		  	 if(!isNaN(sum)){
		    	   $("#workEstimAmount" + i).val(sum.toFixed(2));
		      			 var grandTotal = 0;
		        	     $(".allamount").each(function () {
		        	    	 var total = parseFloat($(this).val());
		        	          grandTotal += isNaN(total) ? 0 : total;
		        	     });
		        	 var tot =   $("#allTotal").val(grandTotal.toFixed(2));
		             }
	  	 }else{
	  	    	 $("#workEstimAmount"+i).prop("readonly", false); 
	  	    	 $("#workEstimQuantity"+i).prop("readonly", true); 
	  	 }	  	 
	  }); 
}

function reOrderMachineryDetails(){	
	$('.appendableClass').each(function(i){		
		$(this).find("select:eq(0)").attr("id","selectType" + (i)).attr("onchange","rateMachinaryData("+i+");");	
		$(this).find("select:eq(1)").attr("id","gRateMastId" + (i)).attr("name","addMachinaryList[" + (i)+ "].gRateMastId").attr("onchange","getRateUnitBySorId("+i+");");	;	
		$(this).find("select:eq(2)").attr("id","maItemUnit" + (i));	
		
		 $(this).find("input:hidden:eq(0)").attr("id", "workEstemateId" + (i));
		 $(this).find("input:hidden:eq(0)").attr("name","addMachinaryList[" + (i)+ "].workEstemateId");
		 
		 $(this).find("input:hidden:eq(1)").attr("id", "rateTypeId" + (i));
		 $(this).find("input:hidden:eq(1)").attr("name","addMachinaryList[" + (i)+ "].workEstimFlag");
		 
		 $("#rateTypeId"+i).val("C");
		
		$(this).find("input:text:eq(0)").attr("id" ,"rate" + (i));
		$(this).find("input:text:eq(1)").attr("id" ,"workEstimQuantity" + (i)).attr("name","addMachinaryList[" + (i)+ "].workEstimQuantity");
		$(this).find("input:text:eq(2)").attr("id" ,"workEstimAmount" + (i)).attr("name","addMachinaryList[" + (i)+ "].workEstimAmount");
	});
}

$('#MachineryDetails').on("click",'.deleteMachineryDetails' , function(e){	
	var errorList = [];
	var count = 0;
	$('.appendableClass').each(function(i){
		count += 1;
	});
	var rowCount = $('#MachineryDetails tr').length;
	 if(rowCount <= 2){
		 errorList.push(getLocalMessage("first.row.cannot.be.deleted"));
	 }
	   if(errorList.length > 0){
		    $("#errorDiv").show();
		    showErr(errorList);
			return false;
	 }else{
		 $(this).parent().parent().remove();
		 var machineryId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			if(machineryId != ''){
				removeMachineryById.push(machineryId);
				}
			$('#removeMachineryById').val(removeMachineryById);
		 reOrderMachineryDetails();
	 }		
});

/**
 * This All Method  is used to create Labour Details
 * @return Add Labour Details
 *  START METHOD
 */

$("#LabourFormDetails").on('input', '.txtCal', function () {
		$('.appendableClass').each(function (i) {
		var sum = 0;
		var machineRate = parseFloat($("#sorLabourRate" + i).val());
		var machineQuantity = parseFloat($("#workEstimQuantity" + i).val());
		if ((!isNaN(machineQuantity)) && (!isNaN(machineRate ))) {
			sum = (machineRate * machineQuantity);
		}
		$("#workEstimAmount" + i).val(sum.toFixed(2));
	});     
});

function reOrderLabourFormDetails(){
		$('.appendableClass').each(function(i){
		
		$(this).find("select:eq(0)").attr("id","selectType" + (i)).attr("onchange","rateMachinaryData("+i+");");	
		$(this).find("select:eq(1)").attr("id","gRateMastId" + (i)).attr("name","addLabourList[" + (i)+ "].gRateMastId").attr("onchange","getRateUnitBySorId("+i+");");	;	
		$(this).find("select:eq(2)").attr("id","maItemUnit" + (i));	
		
		$(this).find("input:hidden:eq(0)").attr("id", "workEstemateId" + (i));
		$(this).find("input:hidden:eq(0)").attr("name","addLabourList[" + (i)+ "].workEstemateId");
		
		$(this).find("input:hidden:eq(1)").attr("id", "rateTypeId" + (i));
		$(this).find("input:hidden:eq(1)").attr("name","addLabourList[" + (i)+ "].workEstimFlag");
		
		$("#rateTypeId"+i).val("A");
		
		$(this).find("input:text:eq(0)").attr("id" ,"rate" + (i));
		$(this).find("input:text:eq(1)").attr("id" ,"workEstimQuantity" + (i)).attr("name","addLabourList[" + (i)+ "].workEstimQuantity");
		$(this).find("input:text:eq(2)").attr("id" ,"workEstimAmount" + (i)).attr("name","addLabourList[" + (i)+ "].workEstimAmount");
	});
}

$('#LabourFormDetails').on("click",'.deleteLabourFormDetails' , function(e){
	
	var errorList = [];
	var count = 0;
	$('.appendableClass').each(function(i){
		count += 1;
	});
	var rowCount = $('#LabourFormDetails tr').length;
	if(rowCount <= 2){
		errorList.push(getLocalMessage("first.row.cannot.be.deleted"));
	}
	if(errorList.length > 0){
		$("#errorDiv").show();
		showErr(errorList);
		return false;
	}else{
		$(this).parent().parent().remove();
		var labourId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
		if(labourId != ''){
			removeLabourFormIdArray.push(labourId);
		}
		$('#removeLabourFormById').val(removeLabourFormIdArray);
		reOrderLabourFormDetails();
	}		
});

$("#LabourFormDetails").on("click",'.addLabourFormDetails',function(e) {
	  var count = $('#LabourFormDetails tr').length - 1;
	  var errorList = [];
	  errorList = validatLabourFormList(errorList);	
      if (errorList.length > 0) { 
	       $("#errorDiv").show();
	           showErr(errorList);
	   }else {
  			      $("#errorDiv").addClass('hide');
			      e.preventDefault();
					var clickedRow = $(this).parent().parent().index();
					var content = $('#LabourFormDetails tr').last().clone();
					$('#LabourFormDetails tr').last().after(content);
					content.find("input:text").attr("value", "");
					content.find("select").attr("selected", "selected").val('');
					content.find("input:text").val('');
					content.find("input:hidden").val('');
					reOrderLabourFormDetails();
				}
});

function validatLabourFormList(errorList){
		$('.appendableClass').each(function(i){		
		var labourList = i+1;
		var selectType = $("#selectType"+i).find("option:selected").attr('value');
		if(selectType =="" || selectType ==null){
			errorList.push(getLocalMessage("work.estimate.select.type")+" "+labourList);
			return false;
		}
		if(selectType == "M"){
			var fromMaster = $("#gRateMastId" +i).val();
			var labourQuantity = $("#workEstimQuantity" +i).val();
			
			if(fromMaster == "" || fromMaster == "0"){
				errorList.push(getLocalMessage("work.estimate.select.form.master")+" "+labourList);
			}
			if(labourQuantity == "" || labourQuantity =="0"){
				errorList.push(getLocalMessage("work.estimate.enter.quantity")+" "+labourList);
			}			
		}else {
			var totalAmount = $("#workEstimAmount" + i).val();
			if(totalAmount == "" || totalAmount =="0"){
				errorList.push(getLocalMessage("work.estimate.enter.total.amount")+" "+labourList);
			}
		}		
	});	
	return errorList;
}

function saveLabourForm(labourData){
	var errorList = [];	
	if ($("#sorNameId").val() ==""){
		errorList.push(getLocalMessage("work.estimate.sor.name.required"));
	}
	 errorList = validatLabourFormList(errorList);	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
	} else {
		$("#errorDiv").hide();
		
		var formName = findClosestElementId(labourData, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(
				'WorkEstimate.html?saveLabourFormData', 'POST',
				requestData, false);
		showConfirmBox(getLocalMessage("work.estimate.labour.creation.success"));
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
	if($("#sorList").val()=='U'){
        $.fancybox.close();
         openAddWorkNonSorForm('WorkEstimate.html','AddWorkNonSorForm');
		$.fancybox.close();
	}
	else{
		measurementSheet();
		$.fancybox.close();
	}
}

function showConfirmBoxForDirectAbsEstim(sucessMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'+sucessMsg+'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForDirectAbsEstim()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);

	return false;
}

function proceedForDirectAbsEstim() {
	if($("#sorList").val()=='U'){
		 var flag;
		openAddWorkEstimate("WorkEstimate.html", "AddWorkEstimate", flag);
		$.fancybox.close();
		
		/*
		 * $.fancybox.close();
		 * openAddWorkEstimate('WorkEstimate.html','AddWorkEstimate');
		 * $.fancybox.close();
		 */
	}
	else{
		measurementSheet();
		$.fancybox.close();
	}
}
/**
 *  Labour Master Method END
 */

/**
 * This All Method  is used to create Rate Master
 * @return Add Rate Master
 *  START METHOD
 */

function saveRateTypeForm(RateTypeData){
	
	var errorList = [];
		if ($("#sorNameId").val() ==""){
		errorList.push(getLocalMessage("work.estimate.sor.name.required"));
	}
	errorList = validateRateAnalysiList(errorList);
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
	} else {
		$("#errorDiv").hide();
		var formName = findClosestElementId(RateTypeData, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(
					'WorkEstimate.html?saveLabourData', 'POST',
					requestData, false);
		showConfirmBoxFoLabour(getLocalMessage("work.estimate.labour.creation.success"));			
	}
}


$("#rateAnalysisMasterTab").on("click",'.addButton',function(e) {	
	var count = $('#rateAnalysisMasterTab tr').length - 1;		 
	 var errorList = [];
	 errorList = validateRateAnalysiList(errorList);
	 if(errorList.length > 0){
		  $("#errorDiv").show();
          showErr(errorList);
	  }else {
		  $("#errorDiv").addClass('hide');
		     e.preventDefault();
			var clickedRow = $(this).parent().parent().index();
			var content = $('#rateAnalysisMasterTab tr').last().clone();
			$('#rateAnalysisMasterTab tr').last().after(content);
			content.find("input:hidden").attr("value", "");
			content.find("input:text").val('');
			content.find("select").attr("selected", "selected").val('');
			content.find("input:text").val('');
			reOrderRateTypeList();			
	}
});

function reOrderRateTypeList(){
	$('.appendableClass').each(function(i){		
		//Id
		$(this).find("select:eq(0)").attr("id","rateTypeId" + (i)).attr("onchange","rateTypeData("+i+");").attr("name","addAllRatetypeEntity[" + (i)+ "].workEstimFlag");	
		$(this).find("select:eq(1)").attr("id","selectType" + (i)).attr("onchange","rateTypeData("+i+");");	
		$(this).find("select:eq(2)").attr("id","gRateMastId" + (i)).attr("name","addAllRatetypeEntity[" + (i)+ "].gRateMastId").attr("onchange","getRateUnitBySorId("+i+");");	;	
		$(this).find("select:eq(3)").attr("id","maItemUnit" + (i));	
		
		 $(this).find("input:hidden:eq(0)").attr("id", "workEstemateId" + (i));
		 $(this).find("input:hidden:eq(0)").attr("name","addAllRatetypeEntity[" + (i)+ "].workEstemateId");
		
		$(this).find("input:text:eq(0)").attr("id" ,"rate" + (i));
		$(this).find("input:text:eq(1)").attr("id" ,"workEstimQuantity" + (i)).attr("name","addAllRatetypeEntity[" + (i)+ "].workEstimQuantity");
		$(this).find("input:text:eq(2)").attr("id" ,"workEstimAmount" + (i)).attr("name","addAllRatetypeEntity[" + (i)+ "].workEstimAmount");
	});
}

$('#rateAnalysisMasterTab').on("click",'.delButton' , function(e){
	
	var errorList = [];
	var count = 0;
	$('.appendableClass').each(function(i){
		count += 1;
	});
	var rowCount = $('#rateAnalysisMasterTab tr').length;
	 if(rowCount <= 2){
		 errorList.push(getLocalMessage("first.row.cannot.be.deleted"));
	 }
	   if(errorList.length > 0){
		    $("#errorDiv").show();
		    showErr(errorList);
			return false;
	 }else{
		 $(this).parent().parent().remove();
		 var rateId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			if(rateId != ''){
				removeLabourById.push(rateId);
				}
			$('#removeLabourById').val(removeLabourById);
		 reOrderRateTypeList();
	 }		 		
});

function validateRateAnalysiList(errorList){	
	$(".appendableClass").each(function(i) {		
		var rateType = i+1;
		var rateTypeData = $("#rateTypeId" + i).find("option:selected").attr('value');
		var workEstimAmount = $("#workEstimAmount" +i).val();
		var selectType = $("#selectType"+i).find("option:selected").attr('value');
		
	    if(rateTypeData =="" || rateTypeData =="0"){
	    	errorList.push(getLocalMessage("work.estimate.select.rate.type") + " " + rateType)
	    }
		if(rateTypeData == 'R'){
				if(workEstimAmount == "" || workEstimAmount == "0")
				errorList.push(getLocalMessage("work.estimate.enter.total.amount") + " " + rateType);
		   }
		if(rateTypeData != 'R'){
			if(selectType =="" || selectType ==null){
				errorList.push(getLocalMessage("work.estimate.select.type") +" "+ rateType);
			}					
			if(selectType == "M"){
				var fromMaster = $("#gRateMastId" +i).val();
				var labourQuantity = $("#workEstimQuantity" +i).val();
				
				if(fromMaster == "" || fromMaster == "0"){
					errorList.push(getLocalMessage("work.estimate.select.form.master")+" "+rateType);
				}
				if(labourQuantity == "" || labourQuantity =="0"){
					errorList.push(getLocalMessage("work.estimate.enter.quantity")+" "+rateType);
				}																	
			}else{
				var totalAmount = $("#workEstimAmount" + i).val();
				if(totalAmount == "" || totalAmount =="0"){
					errorList.push(getLocalMessage("work.estimate.enter.total.amount")+" "+rateType);
				}
			}
		}
	});
	return errorList;
}

function showConfirmBoxFoLabour(sucessMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\">'+sucessMsg+'</h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForMaterial()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function proceedForMaterial() {
	var divName = '.content-page';
	var ajaxResponse = __doAjaxRequest("WorkEstimate.html?openSaveRateFromOthertype", 'POST', {}, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	$.fancybox.close();
}

/**
 * This All Method  is used to create Update Non SOR details
 * @return workOverHead
 *  START METHOD
 */
function openAddWorkNonSorForm(formUrl,actionParam){
	if (!actionParam) {
		actionParam = "add";
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveNonSor(nonSorData){
	var errorList = [];
	if ($(".defination").val() ==""){
	errorList.push(getLocalMessage("work.estimate.workDefination.required"));
	}
	if ($(".workName").val() ==""){
	errorList.push(getLocalMessage("work.estimate.work.name.required"));
	}	
	
	 errorList = validateNonSORItemsDetails(errorList);	
	
   if (errorList.length > 0) {
	  $("#errorDiv").show();
	   showErr(errorList);
    } else {
	   $("#errorDiv").hide();	
	   var formName = findClosestElementId(nonSorData, 'form');
	   var theForm = '#' + formName;
	   var requestData = __serializeForm(theForm);
	   var response = __doAjaxRequest(
			   'WorkEstimate.html?saveNonSorData', 'POST',
			   requestData, false);
	   showConfirmBoxForOverHeads(getLocalMessage('work.estimate.nonSor.creation.success'));
  }
}
$("#nonSorDetails").on("click",'.addNonSORDetails',function(e) {	
	  var count = $('#nonSorDetails tr').length - 1;
	  var errorList = [];
	  errorList = validateNonSORItemsDetails(errorList);	
  if (errorList.length > 0) { 
  	       $("#errorDiv").show();
	              showErr(errorList);
			 } 
        else {			      
        	$("#errorDiv").addClass('hide');
        	e.preventDefault();
			var clickedRow = $(this).parent().parent().index();
			var content = $('#nonSorDetails tr').last().clone();
			$('#nonSorDetails tr').last().after(content);
			content.find("input:text").attr("value", "");
			content.find("select").attr("selected", "selected").val('');
			content.find("input:text").val('');
			content.find("input:hidden").val('');
			reOrderNonSORItemsDetails();
			}
});

function validateNonSORItemsDetails(errorList){
	var rowCount = $('#nonSorDetails tr').length;
	if(rowCount==3 && $("#removeNonSorById").val()!=""){
		return errorList;
	}
	
$(".appendableClass").each(function(i) {	
		var itemCode = $("#sorDIteamNo" + i).val();
		var sorDesc = $("#sorDDescription" + i).val();
		var sorItemUnit = $("#sorIteamUnit" + i).val();
		var sorBasicRate = $("#sorRate" + i).val();
		var quantity = $("#workQuantity" + i).val();
		var amount = $("#workAmount" + i).val();
		
		var sorConstant = i+1;
		if(itemCode == ""){
			errorList.push(getLocalMessage("work.estimate.enter.item.code")+" "+sorConstant);
		}
		if(sorDesc == ""){
			errorList.push(getLocalMessage("work.estimate.enter.description")+" "+sorConstant);
		}
		if(sorItemUnit == ""){
			errorList.push(getLocalMessage("work.estimate.select.unit")+" "+sorConstant);
		}
		if(sorBasicRate == ""){
			errorList.push(getLocalMessage("work.estimate.enter.rate")+" "+sorConstant);
		}
		if(quantity == ""){
			errorList.push(getLocalMessage("work.estimate.enter.quantity")+" "+sorConstant);
		}					
	});
	return errorList;	
}

function reOrderNonSORItemsDetails(){	
  $('.appendableClass').each(function(i){
	
	  $(this).find("select:eq(0)").attr("id","sorIteamUnit" + (i));
	  $(this).find("input:hidden:eq(0)").attr("id", "workEstemateId" + (i));
	  $(this).find("input:text:eq(0)").attr("id" ,"sNo" + (i));
	  $(this).find("input:text:eq(1)").attr("id" ,"sorDIteamNo" + (i));
	  $(this).find("input:text:eq(2)").attr("id","sorDDescription" + (i));
	  $(this).find("input:text:eq(3)").attr("id","workQuantity" + (i));
	  $(this).find("input:text:eq(4)").attr("id" ,"sorRate" + (i));
	  $(this).find("input:text:eq(5)").attr("id" ,"workAmount" + (i));
	  $(this).find("input:text:eq(6)").attr("id" ,"meRemark" + (i));
	
	//Names
	  $(this).find("input:hidden:eq(0)").attr("name","workEstimateNonSorList[" + (i)+ "].workEstemateId");
	$(this).find("select:eq(0)").attr("name","workEstimateNonSorList[" + (i)+ "].sorIteamUnit");
	$(this).find("input:text:eq(1)").attr("name" ,"workEstimateNonSorList[" + (i)+ "].sorDIteamNo");
	$(this).find("input:text:eq(2)").attr("name","workEstimateNonSorList[" + (i)+ "].sorDDescription");
	$(this).find("input:text:eq(3)").attr("name","workEstimateNonSorList[" + (i)+ "].workEstimQuantity");
	$(this).find("input:text:eq(4)").attr("name","workEstimateNonSorList[" + (i)+ "].sorBasicRate");
	$(this).find("input:text:eq(5)").attr("name","workEstimateNonSorList[" + (i)+ "].workEstimAmount");
	$(this).find("input:text:eq(6)").attr("name","workEstimateNonSorList[" + (i)+ "].meRemark");
	$(this).parents('tr').find('.delButton').attr("id","delButton" + i);
	$(this).parents('tr').find('.addButton').attr("id","addButton" + i);
	
	$("#sNo" + i).val(i+1);
});
}

$("#nonSorDetails").on("click",'.deleteNonSORDetails',function(e) {

			var errorList = [];
			var counter = 0;
			$('.appendableClass').each(function(i) {
				counter += 1;
			});
			var rowCount = $('#nonSorDetails tr').length;

			if (rowCount == 3) {
				$("#sorDIteamNo0").val("");
				$("#sorDDescription0").val("");
				$("#workQuantity0").val("");
				$("#sorIteamUnit0").val("");
				$("#sorRate0").val("");
				$("#workAmount0").val("");
				$("#meRemark0").val("");
				var nonSORId = $(this).parent().parent().find(
						'input[type=hidden]:first').attr('value');

				if (nonSORId != '' && nonSORId !=undefined) {
					removeNonSorById.push(nonSORId);
					$('#removeNonSorById').val(removeNonSorById);
					$('#totalnonSor').val("0.00")
				}
			}
			if (rowCount != 3) {
				$(this).parent().parent().remove();
				var nonSORId = $(this).parent().parent().find(
						'input[type=hidden]:first').attr('value');
				if (nonSORId != '' && nonSORId !=undefined) {
					removeNonSorById.push(nonSORId);
				}
			}

			$('#removeNonSorById').val(removeNonSorById);
			reOrderNonSORItemsDetails();
	
});
   
$("#nonSorDetails").on('input','.calculation', function () {
      $('.appendableClass').each(function (i) {
    	 var sum = 0;
    	 var basicRate = parseFloat($("#sorRate" + i).val());
    	 var quantity = parseFloat($("#workQuantity" + i).val());
    	 if (!isNaN(quantity)) {
        	 sum = (basicRate * quantity);
         }    	
    	 if(!isNaN(sum)){
    		 $("#workAmount" + i).val(sum.toFixed(2));
             var grandTotal = 0;
      	     $(".amount").each(function () {
      	    	 var nonSorTotal = parseFloat($(this).val());
      	          grandTotal += isNaN(nonSorTotal) ? 0 : nonSorTotal;
      	     });
      	 var tot =   $("#totalnonSor").val(grandTotal.toFixed(2));
      	 $('#totalSor').html(grandTotal.toFixed(2));
          }
     });             
});

function showConfirmBoxForOverHeads(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");

	message += '<h4 class=\"text-center text-blue-2 padding-12\"> '+ getLocalMessage(successMsg) +' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
			+ '<input type=\'button\' value=\'' + cls
			+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
			+ ' onclick="proceedForOverHeads()"/>' + '</div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);

	return false;
}

function proceedForOverHeads() {
	openAddWorkNonSorForm('WorkEstimate.html','AddWorkOverheadsForm');
	$.fancybox.close();	
}

/**
 * This All Method  is used to create Update overHead details
 * @return workOverHead
 *  START METHOD
 */
function openAddWorkOverheadsForm(formUrl,actionParam){
	if (!actionParam) {
		actionParam = "add";
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}

function saveWorkOverHeads(overHeadData){
	var errorList = [];
	
	if ($(".defination").val() ==""){
		errorList.push(getLocalMessage("work.estimate.workDefination.required"));
	}
	if ($(".workName").val() ==""){
		errorList.push(getLocalMessage("work.estimate.work.name.required"));
	}
	errorList = validateOverheadsDetailsList(errorList);
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
	} else {
		$("#errorDiv").hide();
		
		var formName = findClosestElementId(overHeadData, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(
				'WorkEstimate.html?saveOverHeadData', 'POST',
				requestData, false);
		showConfirmBoxForEnclosures(getLocalMessage("work.estimate.overhead.creation.success"));
 }
}

$("#overheadsDetails").on("click",'.addOverHeadsDetails',function(e) {	
	  var count = $('#overheadsDetails tr').length - 1;
	  var errorList = [];
	  errorList = validateOverheadsDetailsList(errorList);	
  if (errorList.length > 0) { 
  	       $("#errorDiv").show();
	              showErr(errorList);
	 } 
      else {
			   $("#errorDiv").addClass('hide');
			    e.preventDefault();
				var clickedRow = $(this).parent().parent().index();
				var content = $('#overheadsDetails tr').last().clone();
				$('#overheadsDetails tr').last().after(content);
				content.find("input:hidden").attr("value", "");
				content.find("input:text").val('');
				content.find("select").val('');
				content.find("input:text").val('');
				content.find('div.chosen-container').remove();
				content.find("select:eq(0)").chosen().trigger("chosen:updated"); 
				reOrderOverHeadsDetailsList();
				
			}
});

function validateOverheadsDetailsList(errorList){
	var rowCount = $('#overheadsDetails tr').length;
	if(rowCount==3 && $("#removeOverHeadById").val()!=""){
		return errorList;
	}
	$(".appendableClass").each(function(i) {
		var itemCode = $("#overHeadCode" + i).val();
		var overHeadDesc = $("#overheadDesc" + i).val();
		var valueTypes = $("#overHeadvalType" + i).val();
		var overHeadRate = $("#overHeadRate" + i).val();
		var overHeadValue = $("#overHeadValue" + i).val();
		
		var overHeadConstant = i+1;
		if(itemCode == ""){
			errorList.push(getLocalMessage("work.estimate.enter.item.code")+" "+overHeadConstant);
		}
		if(overHeadDesc == ""){
			errorList.push(getLocalMessage("work.estimate.enter.overhead.description")+" "+overHeadConstant);
		}
		if(overHeadRate == ""){
			errorList.push(getLocalMessage("work.estimate.enter.rate")+" "+overHeadConstant);
		}
		if(overHeadValue == ""){
			errorList.push(getLocalMessage("work.estimate.enter.overheads.value")+" "+overHeadConstant);
		}
					
	});
	return errorList;
}

function reOrderOverHeadsDetailsList(){
	$('.appendableClass').each(function(i){
		//Id
	    $(this).find("select:eq(0)").attr("id" ,"overHeadCode" + (i)).attr("onchange","getAllOverheadDetails("+(i)+")");
		$(this).find("select:eq(1)").attr("id","overHeadvalType" + (i)).attr("onchange","calculateTotalAmount("+(i)+")");		
		$(this).find("hidden:eq(0)").attr("id","overHeadId" + (i));	
		$(this).find("input:text:eq(0)").attr("id" ,"sNo" + (i));
		
		$(this).find("input:text:eq(2)").attr("id","overheadDesc" + (i));
		$(this).find("input:text:eq(3)").attr("id","overHeadRate" + (i)).attr("onblur","calculateTotalAmount("+(i)+")");;
		$(this).find("input:text:eq(4)").attr("id" ,"overHeadValue" + (i));
		
		//Names
		$(this).find("select:eq(0)").attr("name","estimOverHeadDetDto[" + (i)+ "].overHeadCode");
		$(this).find("select:eq(1)").attr("name","estimOverHeadDetDto[" + (i)+ "].overHeadvalType");		
		$(this).find("hidden:eq(0)").attr("name","estimOverHeadDetDto[" + (i)+ "].overHeadId");
		
		$(this).find("input:text:eq(2)").attr("name","estimOverHeadDetDto[" + (i)+ "].overheadDesc");		
		$(this).find("input:text:eq(3)").attr("name","estimOverHeadDetDto[" + (i)+ "].overHeadRate");
		$(this).find("input:text:eq(4)").attr("name","estimOverHeadDetDto[" + (i)+ "].overHeadValue");
		$(this).parents('tr').find('.delButton').attr("id","delButton" + i);
		$(this).parents('tr').find('.addButton').attr("id","addButton" + i);
		$("#sNo" + i).val(i+1);
	});
}

$('#overheadsDetails').on("click",'.deleteOverHeadsDetails' , function(e){
	
	var errorList = [];
	var count = 0;
	$('.appendableClass').each(function(i){
		count += 1;
	});
	var rowCount = $('#overheadsDetails tr').length;
	
	if (rowCount == 3) {
		$("#overHeadCode0").val("");
		$("#overheadDesc0").val("");
		$("#overHeadvalType0").val("");
		$("#overHeadRate0").val("");
		$("#overHeadValue0").val("");				
		var overHeadId = $(this).parent().parent().find(
				'input[type=hidden]:first').attr('value');

		if (overHeadId != '') {
			removeOverHeadById.push(overHeadId);
			$('#removeOverHeadById').val(removeOverHeadById);
			$('#totalOverhead').val("0.00")
		}				
	}
	if (rowCount != 3) {
	 
		 $(this).parent().parent().remove();
		 var overHeadId = $(this).parent().parent().find('input[type=hidden]:first').attr('value');
			if(overHeadId != ''){
				removeOverHeadById.push(overHeadId);
				}
	}
	 $('#removeOverHeadById').val(removeOverHeadById);
	reOrderOverHeadsDetailsList();
	 	
});

$("#overheadsDetails").on('input', function () {
	    var grandTotal = 0;
	     $(".overheadcalculation").each(function (i) {
   		 var overHeadValue = parseFloat(parseFloat($(this).val()));
    	 grandTotal += isNaN(overHeadValue) ? 0 : overHeadValue;
	     });    	 
	     $("#totalOverhead").val(grandTotal.toFixed(2));
 });

function showConfirmBoxForEnclosures(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");
	
	message += '<h4 class=\"text-center text-blue-2 padding-12\"> '+ getLocalMessage(successMsg) +' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
		+ '<input type=\'button\' value=\'' + cls
		+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
		+ ' onclick="proceedForEnclosures()"/>' + '</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	
	return false;
}

/**
 * This All Method  is used to create Update work enclosure file upload
 * @return workEnclosures.jsp
 *  START METHOD
 */
function proceedForEnclosures() {
	openAddWorkOverheadsForm('WorkEstimate.html','AddWorkEnclosuersForm');
	$.fancybox.close();
	
}

function openAddEnclosuresForm(formUrl,actionParam){	
	if (!actionParam) {
		actionParam = "add";
	}
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading(formUrl + '?' + actionParam, {}, 'html',
			divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();	
}

//Defect #162291
function addAsterixInAttachDocTable() {
	 var deleteDocTable = $('form#workEnclosuresForm #deleteDoc tbody');
	 var deleteDocTableCheckLength = deleteDocTable.children().length >= 2;
	 if (deleteDocTableCheckLength) {
		 $('form#workEnclosuresForm #attachDoc tbody tr th span.mand').remove();
	 } else {
		 $('form#workEnclosuresForm #attachDoc tbody tr th:nth-child(2)').append('<span class="mand">*</span>');
	 }
}

function saveEnclosuresData(enclosuresData){
	var errorList = [];
	
	if ($(".defination").val() ==""){
		errorList.push(getLocalMessage("work.estimate.workDefination.required"));
	}

	if ($(".workName").val() ==""){
		errorList.push(getLocalMessage("work.estimate.work.name.required"));
	}
	
	//Defect #162291
	var attachDocTable = $('#attachDoc tbody');
	var deleteDocTable = $('#deleteDoc tbody');
	var fileUploadList = $('.fileUpload').next();
	var cond1 = attachDocTable.children().length <= 2;
	var cond2 = fileUploadList.children().length < 1;
	var cond3 = deleteDocTable.children().length >= 2;
	if (!cond3) {
		if (cond1 && cond2) {
			errorList.push(getLocalMessage('work.document.valid'));
		}
	}
	
	if (errorList.length > 0) {
		$("#errorDiv").show();
		showErr(errorList);
	} else {
		$("#errorDiv").hide();
		
		var formName = findClosestElementId(enclosuresData, 'form');
		var theForm = '#' + formName;
		var requestData = __serializeForm(theForm);
		var response = __doAjaxRequest(
				'WorkEstimate.html?saveWorkEnclosuresData', 'POST',
				requestData, false);
		showConfirmBoxForFinalSubmit(getLocalMessage('work.estimate.fileUpload.creation.success'));
	}
	
}

function fileCountUpload(element) {
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var response = __doAjaxRequest('WorkEstimate.html?fileCountUpload','POST',requestData,false,'html');
	$("#uploadTagDiv").html(response);
	prepareTags();
}

function showConfirmBoxForFinalSubmit(successMsg) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = getLocalMessage("works.management.proceed");
	
	message += '<h4 class=\"text-center text-blue-2 padding-12\"> '+ getLocalMessage(successMsg) +' </h4>';
	message += '<div class=\'text-center padding-bottom-10\'>'
		+ '<input type=\'button\' value=\'' + cls
		+ '\'  id=\'btnNo\' class=\'btn btn-blue-2 \'    '
		+ ' onclick="proceedForFinalSubmit()"/>' + '</div>';
	
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	
	return false;
}
/**
 * Work Enclosure File Upload All method are End
 */

/**
 *This Method  is used to final submit of work Estimation data
 * @return workEstimation.jsp
 */
function proceedForFinalSubmit() {
	if ($("#requestFormFlag").val() == 'AP') {
		var divName = '.content-page';
		var url = "WorkEstimateApproval.html?showEstimateCurrentForm";
		var requestData = {};
		var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false,
				'html');
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		prepareTags();
		$.fancybox.close();

	} else if ($("#saveMode").val() == 'M') {
		backAddMBMasterForm();
	} else {
		$("#postMethodForm").prop('action', '');
		$("#postMethodForm").prop('action', 'WorkEstimate.html');
		$("#postMethodForm").submit();
	}
}

function sendForApproval(workId,mode){
	var divName = '.content-page';
	var url = "WorkEstimate.html?sendForApproval";
	var requestData = {
			'workId' : workId,
			'mode' :mode
		}
	
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	showEstimateForApproval(ajaxResponse, workId, mode);
	$( ".searchWorkDefination" ).trigger("click");
	
}

function showEstimateForApproval(ajaxResponse, workId, mode) {
	var errMsgDiv = '.msg-dialog-box';
	var message = '';
	var cls = '';
	var sMsg = '';
	var Proceed =  getLocalMessage("works.management.proceed");
	var Cancel =  getLocalMessage("mb.cancel");
	
	if (ajaxResponse.checkStausApproval == "Y") {
		sMsg = getLocalMessage("work.estimate.approval.submitted");
		message += '<div class="text-center padding-top-25">'+'<p class="text-center text-blue-2 padding-12">' + sMsg
				+ '</p>'+'</div>';
		
		message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
				+ ' onclick="closeSend();"/>' + '</div>';		
	} else if(ajaxResponse.checkStausApproval == "N") {
		sMsg = getLocalMessage("work.estimate.approval.process.not.defined");
		message += '<div class="text-center padding-top-25">'+ '<p class="text-center text-blue-2 padding-12">' + sMsg
				+ '</p>'+'</div>';
		
		message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
				+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
				+ ' onclick="closeSend();"/>' + '</div>';
		}else if(ajaxResponse.checkStausApproval == "L"){
			
			//var workId = ajaxResponse.WorkIdApproval;
			sMsg = getLocalMessage("work.estimate.legacy.data.approved");
			message += '<div class="text-center padding-top-25">'+ '<p class="text-center text-blue-2 padding-12">' + sMsg
						+ '</p>'+'</div>';
			
			message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>' + '<p >'
					+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
					+ ' onclick="sendForLegacyApproval(\'' + workId + '\');"/>' + "  " 
					+ '<input class="btn btn-danger" style="margin-right:10px" type=\'button\' value=\''+Cancel+'\'  id=\'Proceed\' '
					+ ' onclick="closeSend();"/>'
					+ '</p>' + '</div>';
			
		}else if(ajaxResponse.checkStausApproval == "F"){
			sMsg = getLocalMessage("work.estimate.approval.bpm.process");
			message += '<div class="text-center padding-top-25">'+ '<p class="text-center text-blue-2 padding-12">' + sMsg
					+ '</p>'+'</div>';
			
			message += '<div class=\'text-center padding-top-15 padding-bottom-15\'>'
					+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
					+ ' onclick="closeSend();"/>' + '</div>';
		}else{
			sMsg = getLocalMessage("work.estimate.approval.initiated.fail");
			sMsg1 = getLocalMessage("work.estimate.approval.contact.administration");
			message += '<div class="text-center padding-top-25">' +'<p class="text-center red padding-12">' + sMsg + '</p>'
		        +'<p class="text-center red padding-12">' + sMsg1 + '</p>'
		        +'</div>';
		
			message += '<div class=\'text-center padding-top-10 padding-bottom-10\'>'
			    	+ '<input class="btn btn-info" style="margin-right:10px" type=\'button\' value=\''+Proceed+'\'  id=\'Proceed\' '
			    	+ ' onclick="closeSend();"/>' + '</div>';
		}
	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(message);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showModalBoxWithoutClose(errMsgDiv);
	return false;
}

function closeSend(){
	$.fancybox.close();
}

function sendForLegacyApproval(workId){
	
	$.fancybox.close();
	var url = "WorkEstimate.html?approvedLegacyData";
	var requestData = {
			'workId' : workId			
		}
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'json');
	if(ajaxResponse == "S"){
		
	}
	 $( ".searchWorkDefination" ).trigger("click");
}

function backSendForApprovalForm() {
	var divName = '.content-page';
	var url = "WorkEstimateApproval.html?showEstimateCurrentForm";
	var requestData = {
			};
	//var requestData = {'appNo' : workcode};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
}



function closePrefixErrBox() {
	$('.warning-div').addClass('hide');
}

function showErr(errorList) {
	$(".warning-div").removeClass('hide');
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closePrefixErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);

	$("html, body").animate({
		scrollTop : 0
	}, "slow");
}

function backToTender(){
	//$.fancybox.close();
	var divName = '.content-page';
	var url = "TenderInitiation.html?showCurrentForm";
	var requestData = {};
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	$(divName).removeClass('ajaxloader');	
	$(divName).html(ajaxResponse);
	prepareTags();
	getProjectList();
	if($("#mode").val()=='V'){
		$("#TenderInitiation :input").prop("disabled", true); 
		$("select").prop("disabled", true).trigger("chosen:updated");
		$(".workClass").prop("disabled",false);
		$('.viewEstimate').prop("disabled",false);
		$('.viewNIT').prop("disabled",false);
		$(".viewFormA").prop("disabled", false);
		$(".viewPQR").prop("disabled", false);
		$(".viewFormF").prop("disabled", false);
		$("#button-Cancel").prop("disabled", false);
	}

	
}

function getProjectList(){
	var deptId = $("#deptId").val();
	var project = $("#project").val();
	if(deptId!="" &&  deptId!=0){
		var requestData ={"deptId":deptId}
		var projectList = __doAjaxRequest('TenderInitiation.html?getProjectList', 'POST', requestData, false,'json');
		 $.each(projectList, function(index, value) {
			 $('#projId').append($("<option></option>").attr("value",value.projId).attr("code",value.projCode).text(value.projNameEng));
		});
		 $("#projId").val(project).trigger('chosen:updated');
	   }
	}



// for Excel Upload

function uploadExcelFile(){

	var errorLis=[];
	var fileName=$("#excelFilePathDirects").val().replace(/C:\\fakepath\\/i, '');
	if(fileName==null || fileName==""){
		errorLis.push(getLocalMessage("excel.upload.vldn.error"));
		showErr(errorLis);
		return false;
	}
	var count=0;
	$("#filePath").val(fileName);
	var requestData = $.param($('#workEstimate').serializeArray())
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('WorkEstimate.html?' + "loadExcelData",
			requestData, 'json');
	jQuery.each(ajaxResponse, function() {
			errorLis.push(this);
			count++;
			});
	if(count==0){
		showConfirmBoxForDirectAbsEstim(getLocalMessage("excel.sor.items.saved.success"));
	}else{
		showErr(errorLis);	
	}
	
	
}

function exportExcelData(){
	window.location.href='WorkEstimate.html?exportRateExcelData';
}

function getWorkName(obj) {	
	var requestData = {
		"projId" : $(obj).val()
	};
	$('#workName').html('');
	$('#workName').append(
			$("<option></option>").attr("value", "0").text(
					getLocalMessage('selectdropdown')));

	var ajaxResponse = doAjaxLoading('WorkEstimate.html?worksName',
			requestData, 'html');
	var prePopulate = JSON.parse(ajaxResponse);

	$.each(prePopulate, function(index, value) {
		$('#workName').append(
				$("<option></option>").attr("value", value.workId).text(
						(value.workName)));
	});
	$('#workName').trigger("chosen:updated");
}

function resetWorkEstimate(){
	$("#postMethodForm").prop('action', '');
	$("#postMethodForm").prop('action', 'WorkEstimate.html');
	$("#postMethodForm").submit();
}

function calculateTotalAmount(index) {
	var overHeadvalType = $("#overHeadvalType" + index).find("option:selected").attr('code');
	var overHeadRate = $("#overHeadRate" + index).val();
	var workEstimateAmt = $("#overheadEstimateAmount").val();
	var totalOverHeadAmount="";

	if (overHeadvalType != "" && overHeadRate != "" && workEstimateAmt != "") {
		if (overHeadvalType == "PER") {
			totalOverHeadAmount = (Number(workEstimateAmt * Number(overHeadRate) / 100)).toFixed(2);
			$("#overHeadValue" + index).val(totalOverHeadAmount);
		} else if (overHeadvalType == "AMT") {
			$("#overHeadValue" + index).val(overHeadRate);
		} else if (overHeadvalType == "MUL") {
			$("#overHeadValue" + index).val((workEstimateAmt*overHeadRate).toFixed(2));
		}
	}
	
	 var grandTotal = 0;
     $(".overheadcalculation").each(function (i) {
		 var overHeadValue = parseFloat(parseFloat($(this).val()));
	 grandTotal += isNaN(overHeadValue) ? 0 : overHeadValue;
     });    	 
     $("#totalOverhead").val(grandTotal.toFixed(2));
	
}

function backAddMBMasterForm() {
	$.fancybox.close();
	var divName = '.content-page';
	var ajaxResponse = doAjaxLoading('MeasurementBook.html' + '?'
			+ 'openMbMasForm', {}, 'html', divName);
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);

	prepareTags();

}

function showErrorMsgForUsedSor(status) {
	var errorLis = [];
	if (!status) {
		errorLis.push("Sor is Used in Measurement Sheet");
		showErr(errorLis);
	}
	return status;
}



function getAllItemsList(index){
	$('#sorIteamNumber' + index).html('');
	$('#sorIteamNumber' + index).append(
			$("<option></option>").attr("value", "").text(
					getLocalMessage('selectdropdown'))).trigger(
			'chosen:updated');

	var requestData = {
		"sorChapterVal" : $('#sorChapter' + index).val()
	}
	var sorItemsList = __doAjaxRequest("WorkEstimate.html" + '?getAllItemsList',
			'POST', requestData, false, 'json');
	$.each(sorItemsList, function(i, value) {
		var code=value.sorDDescription+"$#"+value.sorIteamUnit+"$#"+value.sorBasicRate+"$#"+value.sordId;
		$('#sorIteamNumber' + index).append(
				$("<option></option>").attr("value", value.sorDIteamNo).attr("code",
						code).text(
						value.sorDIteamNo));
	});
	$('#sorIteamNumber' + index).trigger('chosen:updated');
	$('#sorDDescription' + index).val("");
	$('#sorIteamUnit' + index).val("");
	$('#sorBasicRate' + index).val("");
	
}

function getSorItmsDescription(index){	
	var unitRateString=$("#sorIteamNumber"+index).find("option:selected").attr('code');
	var sorDdId=$("#sorChapter"+index).val();
	if(unitRateString!="" && unitRateString!=undefined){
	var rateAndunit = $("#sorIteamNumber"+index).find("option:selected").attr('code').split("$#");
	$("#sorDDescription"+index).val(rateAndunit[0]);
	$("#sorBasicRate"+index).val(rateAndunit[2]);
	$("#sorIteamUnit"+index).val(rateAndunit[1]);
	$("#sorIteamUnitHidden"+index).val(rateAndunit[1]);
	$("#sorDetailsId"+index).val(rateAndunit[3]);
	$("#sorCheckBox"+index).val(true);
	
	}
}



//from sor type (demo observations-04/12/2018)

$("#estimateFromSor").on("click", '.addButtonForSor',function(e) {

	if ( $.fn.DataTable.isDataTable('#estimateFromSor') ) {
			$('#estimateFromSor').DataTable().destroy();
		 }
		var errorList = [];	
		errorList = validateEstimateFromSor(errorList);
		if(errorList.length > 0){
			$("#errorDiv").show();
			displayErrorsOnPage(errorList);
			return false;
		}else{
			$("#errorDiv").hide();
           e.preventDefault();	
           var clickedRow = $('#estimateFromSor tr').length-2;	
           var category= $('#sorChapter'+clickedRow).val();
 		  	var itemNo= $('#sorIteamNumber'+clickedRow).val();
			var content = $('#estimateFromSor tbody tr').last().clone();
			$('#estimateFromSor tbody tr').last().after(content);
			content.find("input:text").val('');
			content.find("textarea").val('');
			content.find("input:hidden").val('');
			content.find("select").val('');
			content.find('div.chosen-container').remove();
			content.find("select:eq(0)").chosen().trigger("chosen:updated"); 
			content.find("select:eq(1)").chosen().trigger("chosen:updated"); 
			reOrderFromSorTableIdSequence();
			//Defect #78287
		/*	$('#sorChapter'+(clickedRow+1)).val(category).trigger("chosen:updated");
			$('#sorIteamNumber'+(clickedRow+1)).val(itemNo).trigger("chosen:updated");*/
		}												
		/*if(!validatedirectAbstractList()){
			$('#directAbstractTab').DataTable();
			return false;
		}*/
	
	});

		  		   
$("#estimateFromSor").on("click", '.delButton', function(e) {
	if ( $.fn.DataTable.isDataTable('#estimateFromSor') ) {
		 $('#estimateFromSor').DataTable().destroy();
		}
		var countRows = -1;
		$('.appendableFormSorClass').each(function(i) {
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
			removeDirectAbstract.push(deletedSodId);
		}
		$('#removeDirectAbstract').val(removeDirectAbstract);
		  reOrderFromSorTableIdSequence();
	 }
	e.preventDefault();
});	


function reOrderFromSorTableIdSequence() {
	var cpMode = $("#modeCpd").val();
	$('.appendableFormSorClass').each(function(i) {
			$(this).find($('[id^="sorChapter"]')).attr('id',"sorChapter"+i+"_chosen");
			$(this).find($('[id^="sorIteamNumber"]')).attr('id',"sorIteamNumber"+i+"_chosen");
			$(this).find("select:eq(0)").attr("id", "sorChapter"+ i).attr("onchange","getAllItemsList("+i+");");
			$(this).find("select:eq(1)").attr("id", "sorIteamNumber"+ i).attr("onchange","getSorItmsDescription("+i+");");
			$(this).find("select:eq(2)").attr("id", "sorIteamUnit"+ i);
			
			$(this).find("textarea:eq(0)").attr("id","sorDDescription" + i);
			$(this).find($('[id^="sorBasicRate"]')).attr("id","sorBasicRate" + i);
			
			$(this).find("input:hidden:eq(0)").attr("id", "sorCheckedEstimationId" + (i));
			$(this).find("input:hidden:eq(1)").attr("id", "sorDetailsId" + (i));
			$(this).find("input:hidden:eq(2)").attr("id", "sorCheckBox" + (i));
			$(this).find("input:hidden:eq(3)").attr("id", "sorIteamUnitHidden" + (i));

			
			$(this).find("select:eq(0)").attr("name","sorDetailsList[" + i+ "].sordCategory");
			$(this).find("select:eq(1)").attr("name","sorDetailsList[" + i+ "].sorDIteamNo");
			$(this).find("select:eq(2)").attr("name","sorDetailsList[" + i+ "].sorIteamUnit");
					 	
			$(this).find("input:hidden:eq(0)").attr("name","sorDetailsList[" + i+ "].workEstimateId");
			$(this).find("input:hidden:eq(1)").attr("name","sorDetailsList[" + i+ "].sordId");
			$(this).find("input:hidden:eq(2)").attr("name","sorDetailsList[" + i+ "].checkBox");
			$(this).find("input:hidden:eq(3)").attr("name","sorDetailsList[" + i+ "].sorIteamUnit");
			
			$(this).find("textarea:eq(0)").attr("name","sorDetailsList[" + i+ "].sorDDescription");
			$(this).find($('[id^="sorBasicRate"]')).attr("name","sorDetailsList[" + i+ "].sorBasicRate");
			$(this).parents('tr').find('.delButton').attr("id","delButton" + i);
			$(this).parents('tr').find('.addButton').attr("id","addButton" + i);
	  });
	//$('#estimateFromSor').DataTable();
}


function getAllOverheadDetails(index){
	var overheadDesc=$("#overHeadCode"+index).find("option:selected").attr('code');	
	if(overheadDesc != "" && overheadDesc!=undefined){
		var overheadDesc=$("#overHeadCode"+index).find("option:selected").attr('code').split(",");		
		$("#overheadDesc"+index).val(overheadDesc[1]);
		
		
	}	
}