$(function(){
	$('.firstUnitRow').each(function(i){
		$("#year"+i).prop("disabled", true); 
	});
	
	 $(".datepicker2").datepicker({
	        dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true
	});
	 
	/*To add new Row into table*/ 
	$("#unitDetailTable").on('click','.addCF',function(){
		
			var errorList = [];
			errorList = validateUnitDetailTable(errorList);					
			if (errorList.length == 0) {	
				
			$(".datepicker2").removeClass("hasDatepicker");	
				var content =	$("#unitDetailTable").find('tr:eq(1),tr:eq(2)').clone();
				$('.secondUnitRow').removeClass('in');
				$("#unitDetailTable").append(content);
				$('.secondUnitRow:last').addClass('in');
												
				content.find("input:text").val('');
				content.find("select").val('0');
				content.find("input:hidden:eq(0)").val('0');
			
				$('.error-div').hide();
				datePickerLogic();
				reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');  // reorder id and Path after Addition of new Row
				if($("#checkDetail").prop('checked')==true){   // after tick unable year 
						$("#unitDetailTable tr:last").prev().find("select:eq(0)").prop('disabled',false).removeClass("disabled").addClass("mandColorClass");
				}
				return false;
			}							
			else {
				showErrorOnPage(errorList);
		}			
	}); 

	/*To delete Row From the table*/ 
	$("#unitDetailTable").on('click','.remCF',function(){
	
		
		//To remove entry from database
		var inputId=this.id;		
		var left_text = inputId.lastIndexOf("_");
		var deletedRowCount = inputId.substring(left_text+1);		
		var data = {"deletedRowCount" : deletedRowCount};
		var URL = 'SelfAssessmentForm.html?deleteUnitTableRow';
		
		
		//To delete Unit No from unit specific additional information 
		var unitCount= $("#unitNo"+deletedRowCount).val();		
		var a=0; 
		var tableLength = $('#unitDetailTable tr').length-1;
	/*	for(var j=1;j<tableLength;j++) // table tr length 
		{
			var unitNoFact=$("#unitNo"+j).val();   			
			if(unitCount==unitNoFact){
				a++;
			}
		}*/
		var tableRow=tableLength/2;
		if(unitCount<=tableRow){
			for(i=1;i<=tableRow;i++){
				if(i>=unitCount){
					$(".selectUnit option[value="+i+"]").remove();
				}
			}
		}
		/*if(a==1 && unitCount!=""){
			$(".selectUnit option[value="+unitCount+"]").remove();
		}*/

		
		//remove row From UI
		var index = $(this).closest("tr").index();
		if(($("#unitDetailTable tr").length) >3 && (index!=1)) {
			var tr = $(this).closest("tr");
		    tr.add(tr.next()).remove();
		    $('.secondUnitRow:last').addClass('in');
			var returnData = __doAjaxRequest(URL, 'POST', data, false);
			reOrderUnitTabIdSequence('.firstUnitRow','.secondUnitRow');
			
		}
		else {	
			var errorList = [];
			errorList.push(getLocalMessage("unit.Firstrowcannotberemove"));
			showErrorOnPage(errorList);
		}	
	});
		
	$('body').on('click', 'tr.secondUnitRow', function() {
	      $('tr.in').removeClass('active');
	});
	
	$('body').on('click', 'a.click_advance', function() {	
		$("i", this).toggleClass("fa-caret-up fa-caret-down");
	}); 
	
});


function datePickerLogic(){
	var yearOfAcq =$("#proAssAcqDate").val();
	 $(".datepicker2").datepicker({
	        dateFormat: 'dd/mm/yy',		
			changeMonth: true,
			changeYear: true,
			maxDate:yearOfAcq
		});
}



//validation of unit details table
function validateUnitDetailTable(errorList){	
	var rowCount = $('#unitDetailTable tr').length;	
	if(rowCount==3){
		reorderFirstRow();
	}
	$('.firstUnitRow').each(function(i) {	
		if(rowCount<=3)
			{			
			var year =$("#hiddenYear0").val();
//			var unitType=  $("#assdUnitTypeId").val();
			var floorNo=  $("#assdFloorNo").val();
			var yearOfConstruc=  $("#yearOfConstruc0").val();
			var usageType1=  $("#assdUsagetype1").val();
			var usageType2=  $("#assdUsagetype2").val();
			var usageType3=  $("#assdUsagetype3").val();
			var usageType4=  $("#assdUsagetype4").val();
			var usageType5=  $("#assdUsagetype5").val();
			var ConstruType=  $("#assdConstruType").val();
			var taxableArea=  $("#taxableArea0").val();			
			var level = 1;
			}
		else{
		var year =$("#hiddenYear"+i).val();	
//		var unitType=  $("#assdUnitTypeId"+i).val();
		var floorNo=  $("#assdFloorNo"+i).val();
		var yearOfConstruc=  $("#yearOfConstruc"+i).val();
		
		var utp=i;
		if(i>0){
		utp=i*6;
		}
		var usageType1=  $("#assdUsagetype"+utp).val();
		var usageType2=  $("#assdUsagetype"+(utp+1)).val();
		var usageType3=  $("#assdUsagetype"+(utp+2)).val();
		var usageType4=  $("#assdUsagetype"+(utp+3)).val();
		var usageType5=  $("#assdUsagetype"+(utp+4)).val();
		var ConstruType=  $("#assdConstruType"+i).val();
		var taxableArea=  $("#taxableArea"+i).val();
		var level = i+1;
		}
		 if(year=='' || year == undefined || year == '0'){			 
			 errorList.push(getLocalMessage('unitdetails.selectYearlevel')+" " +level); 
		 }
		 /*if(unitType=='0' || unitType == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectUnitType')+" " +level); 
		 }*/ 		 
		 if(floorNo=='0' || floorNo == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectFloorNo')+ " " +level); 
		 } 			 
		 if(yearOfConstruc=='' || yearOfConstruc == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectConstructionCompletionDate')+ " " +level); 
		 } 		 	
		 if(usageType1=='0' || usageType1 == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectUsageType')+" " +level); 
		 } 
		 if((usageType2 != undefined) && usageType2=='0' ){
			  errorList.push(getLocalMessage('unitdetails.selectSubUsageType')+" " +level); 
		 } 
		 if((usageType3 != undefined) && usageType3=='0' ){
			  errorList.push(getLocalMessage('unitdetails.selectSubUsageType')+" " +level); 
		 } 
		 if((usageType4 != undefined) && usageType4=='0' ){
			  errorList.push(getLocalMessage('unitdetails.selectSubUsageType')+" " +level); 
		 }
		 if((usageType5 != undefined) && usageType5=='0' ){
			  errorList.push(getLocalMessage('unitdetails.selectSubUsageType')+" " +level); 
		 }
		 if(ConstruType=='0' || ConstruType == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectConstructionType')+" "+level); 
		 } 
		 if(taxableArea=='' || taxableArea == undefined || (taxableArea<=0)){
			  errorList.push(getLocalMessage('unitdetails.selectTaxableArea')+" " +level); 
		 }		 	 
	});
	
	$('.secondUnitRow').each(function(j) {	
		if(rowCount<=3){
//			var roadFactor =$("#assdRoadFactor").val();
			var occupancyType =$("#assdOccupancyType").val();
			var natureOfProperty1=  $("#natureOfProperty1").val();
			var natureOfProperty2=  $("#natureOfProperty2").val();
			var natureOfProperty3=  $("#natureOfProperty3").val();
			var natureOfProperty4=  $("#natureOfProperty4").val();
			var natureOfProperty5=  $("#natureOfProperty5").val();
			var level=1;
		}
		else{
//			var roadFactor =$("#assdRoadFactor"+j).val();
			var occupancyType =$("#assdOccupancyType"+j).val();
			var utp=j;
			if(j>0){
			utp=j*6;
			}
			var natureOfProperty1=  $("#natureOfProperty"+utp).val();
			var natureOfProperty2=  $("#natureOfProperty"+(utp+1)).val();
			var natureOfProperty3=  $("#natureOfProperty"+(utp+2)).val();
			var natureOfProperty4=  $("#natureOfProperty"+(utp+3)).val();
			var natureOfProperty5=  $("#natureOfProperty"+(utp+4)).val();
			var level=j+1;
		}
		
		/*if(roadFactor=='0' || roadFactor == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectRoadFactor')+" "+level); 
		 }*/
		if(occupancyType=='0' || occupancyType == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectOccupacyType')+" " +level); 
		 }
		
		if(natureOfProperty1=='0' || natureOfProperty1 == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectNatureOfProperty')+" " +level); 
		 } 
		 if((natureOfProperty2 != undefined) && natureOfProperty2=='0' ){
			  errorList.push(getLocalMessage('unitdetails.selectSubNatureOfProperty')+" " +level); 
		 } 
		 if((natureOfProperty3 != undefined) && natureOfProperty3=='0' ){
			  errorList.push(getLocalMessage('unitdetails.selectSubNatureOfProperty')+" " +level); 
		 } 
		 if((natureOfProperty4 != undefined) && natureOfProperty4=='0' ){
			  errorList.push(getLocalMessage('unitdetails.selectSubNatureOfProperty')+" " +level); 
		 }
		 if((natureOfProperty5 != undefined) && natureOfProperty5=='0' ){
			  errorList.push(getLocalMessage('unitdetails.selectSubNatureOfProperty')+" " +level); 
		 }
	});
	return errorList;
}


function showAddInfo(factorCode,factRadioVal){
	
	if($('input[name="provisionalAssesmentMstDto.proAssfactor['+factRadioVal+']"]:checked').val()=="Y"){
		
		var data = {"factorCode" : factorCode};		
		var URL = 'SelfAssessmentForm.html?getFactorValueDiv';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$("#"+factorCode).html(returnData);
		$("#"+factorCode).show();
		reorderfactor('.specFact','.eachFact');
		var a=0;
		var tableLength = $('#unitDetailTable tr').length;
		for(var j=1;j<tableLength;j++) // table tr length 
		{
			var unitNoFact=$("#unitNo"+a).val();
			var unitSelectId=".factQuestion"+factRadioVal;
			if($(""+unitSelectId+" option[value="+unitNoFact+"]").length == 0 && unitNoFact!=undefined){
				$(""+unitSelectId+"").append("<option value="+unitNoFact+">" +unitNoFact+ "</option>");	
			}
			a++;
		}
	}else{	 
		var data = {"factorCode" : factorCode};
		var URL = 'SelfAssessmentForm.html?deleteFactorStatus';
		var returnData = __doAjaxRequest(URL, 'POST', data, false);
		$("#"+factorCode).html('');
		reorderfactor('.specFact','.eachFact');
	}
}
// reorder id and path for unit specific addition information table
function reorderfactor(specFact,eachFact)
{
	$(eachFact).each(function(j) {
		$(specFact).each(function(i) {
			$(this).find("select:eq(0)").attr("id", "unitNoFact"+i).attr('onchange','resetFactorValue(this,'+i+')');
			//$(this).find("select:eq(0)").attr("class","applicableUnitNo"+i);
			$(this).find("select:eq(1)").attr("id", "assfFactorValueId"+i).attr('onchange','enabledisable(this,'+i+')');
			$(this).find("input:hidden:eq(0)").attr("name","provAsseFactDtlDto["+i+"].assfFactorId");
			$(this).find("input:hidden:eq(1)").attr("id","factPref"+i);
			$(this).find("input:hidden:eq(1)").attr("name","provAsseFactDtlDto["+i+"].factorValueCode");
			$(this).find("input:hidden:eq(2)").attr("id","proAssfId"+i);
			$(this).find("input:hidden:eq(2)").attr("name","provAsseFactDtlDto["+i+"].proAssfId");
			$(this).find("select:eq(0)").attr("name","provAsseFactDtlDto["+i+"].unitNoFact");
			$(this).find("select:eq(1)").attr("name","provAsseFactDtlDto["+i+"].assfFactorValueId");
			$(this).find("a:eq(0)").attr("onclick", "addUnitRow("+(i)+")");
			$(this).find("a:eq(1)").attr("id", "deleteFactorTableRow_"+i);			
		 });
		$(this).find("select:eq(0)").attr("class","form-control mandColorClass selectUnit factQuestion"+j);

		});
}


function reorderFirstRow(){
//	$(".firstUnitRow").find("select:eq(1)").attr("id", "assdUnitTypeId");
	$(".firstUnitRow").find("select:eq(1)").attr("id", "assdFloorNo");
	$(".firstUnitRow").find("select:eq(2)").attr("id", "assdConstruType");
	$(".firstUnitRow").find("select:eq(3)").attr("id", "assdUsagetype1");
	$(".firstUnitRow").find("select:eq(4)").attr("id", "assdUsagetype2");
	$(".firstUnitRow").find("select:eq(5)").attr("id", "assdUsagetype3");
	$(".firstUnitRow").find("select:eq(6)").attr("id", "assdUsagetype4");
	$(".firstUnitRow").find("select:eq(7)").attr("id", "assdUsagetype5");
//	$(".secondUnitRow ").find("select:eq(0)").attr("id", "assdRoadFactor");
	$(".secondUnitRow ").find("select:eq(0)").attr("id", "assdOccupancyType");
	$(".secondUnitRow").find("select:eq(1)").attr("id", "natureOfProperty1");
	$(".secondUnitRow").find("select:eq(2)").attr("id", "natureOfProperty2");
	$(".secondUnitRow").find("select:eq(3)").attr("id", "natureOfProperty3");
	$(".secondUnitRow").find("select:eq(4)").attr("id", "natureOfProperty4");
	$(".secondUnitRow").find("select:eq(5)").attr("id", "natureOfProperty5");
}

//To reorder ID and Path
function reOrderUnitTabIdSequence(classNameFirst,classNamesecond) {

			$(classNameFirst).each(function(i) {
	
			// re-ordering id 	
			$(".datepicker2").removeClass("hasDatepicker");
			var incr=i+1;

			$(this).find("select:eq(0)").attr("id", "year"+i);
			$(this).find("input:hidden:eq(0)").attr("id", "hiddenYear"+i);
			$(this).find("input:hidden:eq(0)").attr("onchange", "compareDate("+(i)+")");
			
			$(this).find("select:eq(0)").attr("onchange", "unitCount("+(i)+")");
			$(this).find("input:text:eq(0)").attr("id", "unitNo"+i);
//			$(this).find("select:eq(1)").attr("id", "assdUnitTypeId"+i);
			$(this).find("select:eq(1)").attr("id", "assdFloorNo"+i);
			$(this).find("input:text:eq(1)").attr("id", "yearOfConstruc"+i);
			$(this).find("select:eq(2)").attr("id", "assdConstruType"+i);
			$(this).find("input:text:eq(2)").attr("id", "taxableArea"+i);
			$(this).find("a:eq(0)").attr("data-target", "#group-of-rows-"+i);
			$(this).find("a:eq(0)").attr("aria-controls", "#group-of-rows-"+i);
			$(this).find("a:eq(1)").attr("id", "deleteRow_"+i);
			var utp=i;
			if(i>0){
			utp=i*6;
			}
			$(this).find("select:eq(3)").attr("id", "assdUsagetype"+utp);
			$(this).find("select:eq(4)").attr("id", "assdUsagetype"+(utp+1));
			$(this).find("select:eq(5)").attr("id", "assdUsagetype"+(utp+2));
			$(this).find("select:eq(6)").attr("id", "assdUsagetype"+(utp+3));
			$(this).find("select:eq(7)").attr("id", "assdUsagetype"+(utp+4));
			
			// re-ordering path binding
			//if($("#checkDetail").prop('checked')==false){
				$("#unitNo"+i).val(incr);
			//}
		
			$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUnitNo");
			$(this).find("input:hidden:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].faYearId");
//			$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUnitTypeId");
			$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdFloorNo");
			$(this).find("input:text:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdYearConstruction");
			$(this).find("select:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdConstruType");
			$(this).find("select:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype1");
			$(this).find("select:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype2");
			$(this).find("select:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype3");
			$(this).find("select:eq(6)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype4");
			$(this).find("select:eq(7)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdUsagetype5");
			$(this).find("input:text:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+i+"].assdBuildupArea");
			if(i>0)
			{
				//if($("#checkDetail").prop('checked')==false){
				
					var a=i-1;
					$("#hiddenYear"+i).val($("#hiddenYear"+a).val());
					$("#year"+i).val($("#year"+a).val());
				//}
			}
			
			var unitNoFact=$("#unitNo"+i).val();
			if($(".selectUnit option[value="+(unitNoFact)+"]").length == 0 && unitNoFact!=undefined){				
				$(".selectUnit").append("<option value="+unitNoFact+">" +unitNoFact+ "</option>");	
			}
			datePickerLogic();
			
		});
		
		$(classNamesecond).each(function(j) {
			var utp=j;
			if(j>0){
			utp=j*6;
			}
//			$(this).find("select:eq(0)").attr("id", "assdRoadFactor"+j);
			$(this).find("select:eq(0)").attr("id", "assdOccupancyType"+j);
//			$(this).find("input:text:eq(0)").attr("id", "proAssdAnnualRent"+j);
			$(this).find("input:text:eq(0)").attr("id", "occupierName"+j);		
//			$(this).find("select:eq(0)").attr("onchange", "occupancyTypeChange("+(j)+")");
			$(this).attr("id", "group-of-rows-"+j);
			
			$(this).find("select:eq(1)").attr("id", "natureOfProperty"+utp);
			$(this).find("select:eq(2)").attr("id", "natureOfProperty"+(utp+1));
			$(this).find("select:eq(3)").attr("id", "natureOfProperty"+(utp+2));
			$(this).find("select:eq(4)").attr("id", "natureOfProperty"+(utp+3));
			$(this).find("select:eq(5)").attr("id", "natureOfProperty"+(utp+4));

			// re-ordering path binding
//			$(this).find("select:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdRoadFactor");
			$(this).find("select:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdOccupancyType");
//			$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdAnnualRent");
			$(this).find("input:text:eq(0)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].occupierName");
			
			$(this).find("select:eq(1)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty1");
			$(this).find("select:eq(2)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty2");
			$(this).find("select:eq(3)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty3");
			$(this).find("select:eq(4)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty4");
			$(this).find("select:eq(5)").attr("name","provisionalAssesmentMstDto.provisionalAssesmentDetailDtoList["+j+"].assdNatureOfproperty5");

		});
}


function unitCount(id){		
	//var unitCount= $("#unitNo"+id).val();
	var year =$("#year"+id).val();
	var data = {"finId":year};
	var URL = 'SelfAssessmentForm.html?compareDate';
	var returnData = __doAjaxRequest(URL, 'POST', data, false);
	
	$('#yearOfConstruc'+id).datepicker('option', 'maxDate', new Date(returnData));  //restrict construction completion date based on year of Acquisition

	var a=0;
	var count=0;
	var selYear=$("#year"+id).val();
	$("#hiddenYear"+id).val(selYear);
	var tableLength = $('#unitDetailTable tr').length;
	for(var j=1;j<tableLength;j++) // unit detail table tr length 
	{
		var dropDownvalue=$("#year"+a).val();
		if(dropDownvalue == selYear){
			count++;
		}
		a++;
	}
	$("#unitNo"+id).val(count);
	if($(".selectUnit option[value="+count+"]").length == 0){
		$(".selectUnit").append("<option value="+count+">" +count+ "</option>");	
	}
}