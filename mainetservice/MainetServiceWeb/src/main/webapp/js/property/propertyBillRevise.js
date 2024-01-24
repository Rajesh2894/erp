function SearchButton(element) {
	var theForm	=	'#changeAssessmentSearch';
	var requestData = {};
	requestData = __serializeForm(theForm);

	var ajaxResponse = __doAjaxRequest(
			'PropertyBillRevise.html?displayChangeInAssessmentForm', 'POST',
			requestData, false, 'html');
	
	// for Assessment Type  
	if(($('option:selected', $("#selectedAssType")).attr('code')) == 'C'){
	$(".dataDiv").html(ajaxResponse);
	}
	$("#dataDiv").html(ajaxResponse);
	$(formDivName).html(returnData);
	reOrderChangeAssessUnitTabIdSequence('.firstUnitRow','.secondUnitRow'); // reordering Id and Path for unit details table

	reorderfactor('.specFact','.eachFact');
	
	return false;
	
}


function confirmToProceedToGetCharges(element){
	var errorList = [];
	if($("#checkDetail").prop('checked')==false){
		  errorList.push(getLocalMessage('In order to proceed You need to Checked the checkbox inside Unit Details')); 
	}
	if (errorList.length == 0) {
		var theForm	=	'#frmSelfAssessmentForm';
		var requestData = {};
		requestData = __serializeForm(theForm);

		var URL = 'PropertyBillRevise.html?calculateReviseCharges';
		
		var returnData = __doAjaxRequestValidationAccor(element,URL,'POST',requestData, false,'html');
		if(returnData)
		{
			$(formDivName).html(returnData);
			$("#fetchTaxDet").hide();		
			reorderFirstRow();
			reOrderChangeUnitTabIdSequence1('.firstUnitRow','.secondUnitRow');	 // reordering Id and Path for unit details table	
			reOrderJointOwnerTableSequence('.jointOwner');   // reordering Id and Path for joint owner table
			
		//	occupancyTypeChange1();   
			yearLength();
			
			reorderfactor('.specFact','.eachFact');  // reordering Id and Path for unit specific Additional Info Table
			
		
		}
		yearLength();

	}else{
		showVendorError(errorList);
	}
	
	
}


function getFinancialYearForRevise(){
	
	$("#yearOfConstruc0").val('');
	var lastPaymentDetails= $("#lastpaymentMadeUpto").val();
	var yearOfAcq =$("#proAssAcqDate").val();
	if(lastPaymentDetails=='0' || lastPaymentDetails==null || lastPaymentDetails==undefined)		
	{
	var data = {"yearOfAcq" : yearOfAcq};
	var URL = 'PropertyBillRevise.html?getFinancialYear'; 
	var returnData = __doAjaxRequest(URL, 'POST', data, false);	
	//clearTableData();
	yearLimitForRevise(returnData);
	$("#year0").val(returnData);
	$("#year1").val(returnData);
	$("#year2").val(returnData);
	$("#year3").val(returnData);
	$("#year4").val(returnData);
	$("#year5").val(returnData);
	$("#year6").val(returnData);
	$("#year7").val(returnData);
	$("#year8").val(returnData);
	$("#year9").val(returnData);
	$("#year10").val(returnData);
	$("#year11").val(returnData);
	$("#year12").val(returnData);
	$("#year13").val(returnData);
	$("#year14").val(returnData);
	$("#year15").val(returnData);
	$("#year16").val(returnData);
	$("#year17").val(returnData);
	$("#year18").val(returnData);
	$("#year19").val(returnData);
	$("#year20").val(returnData);
	$("#year21").val(returnData);
	$("#year22").val(returnData);
	$("#year23").val(returnData);
	$("#year24").val(returnData);
	$("#year25").val(returnData);
	$("#hiddenYear0").val(returnData);
	$("#hiddenYear1").val(returnData);
	$("#hiddenYear2").val(returnData);
	$("#hiddenYear3").val(returnData);
	$("#hiddenYear4").val(returnData);
	$("#hiddenYear5").val(returnData);
	$("#hiddenYear6").val(returnData);
	$("#hiddenYear7").val(returnData);
	$("#hiddenYear8").val(returnData);
	$("#hiddenYear9").val(returnData);
	$("#hiddenYear10").val(returnData);
	$("#hiddenYear11").val(returnData);
	$("#hiddenYear12").val(returnData);
	$("#hiddenYear13").val(returnData);
	$("#hiddenYear14").val(returnData);
	$("#hiddenYear15").val(returnData);
	$("#hiddenYear16").val(returnData);
	$("#hiddenYear17").val(returnData);
	$("#hiddenYear18").val(returnData);
	$("#hiddenYear19").val(returnData);
	$("#hiddenYear20").val(returnData);
	$("#hiddenYear21").val(returnData);
	$("#hiddenYear22").val(returnData);
	$("#hiddenYear23").val(returnData);
	$("#hiddenYear24").val(returnData);
	$("#hiddenYear25").val(returnData);
	}
	$(".datepicker2").datepicker("option","maxDate", yearOfAcq);
}

function yearLimitForRevise(finYearId){
	$('.displayYearList').html('');
	var data = {"finYearId" : finYearId};
	var url = "PropertyBillRevise.html?displayYearListBasedOnDate";
	var returnData=	__doAjaxRequest(url, 'post', data , false);
	$('.displayYearList').append('<option value="0">select Year</option>');
	$.each( returnData, function( key, value ) {		
		 $('.displayYearList').append('<option value="' + key + '">' + value  + '</option>');	 
		});
}


function displayDetailsTillCurrentYearForBillRevise(){
	if($('#checkDetail').is(":checked")){
		var errorList = [];
		var rowCount = $('#unitDetailTable tr').length;	
		if(rowCount<=3){
			reorderFirstRow();
		}
		
		var yearOfConstruc=  $("#yearOfConstruc0").val();
		 if(yearOfConstruc=='' || yearOfConstruc == undefined){
			  errorList.push(getLocalMessage('unitdetails.selectConstructionCompletionDate')+ " " +"1"); 
		 } 
		//errorList = validateUnitDetailTable(errorList);	
		if (errorList.length == 0) {
			var finYearId= $("#hiddenYear0").val();
			var data = {"finYearId" : finYearId};
			var URL = 'PropertyBillRevise.html?getFinanceYearListFromGivenDate';
			var returnData = __doAjaxRequest(URL, 'POST', data, false);
			yearList=returnData;
			var returnDataLength =returnData.length;
			var tableLength=$("#unitDetailTable tr").length;
			for(var i=1; i<=returnDataLength-1;i++)    // yearList
			{ 
				var a=0;
				var c=0;
				var year= returnData[i];
				for(var j=1;j<tableLength;j++) // table tr length 
				{
					var content =	$("#unitDetailTable").find('tr:eq(\"'+j+'\")').clone();
					$('.secondUnitRow').removeClass('in');
					$("#unitDetailTable").append(content);
					$('.secondUnitRow:last').addClass('in');
					reOrderChangeUnitTabIdSequence1('.firstUnitRow','.secondUnitRow');		
					if(j%2!=0)  
					{						
						var utp=a;
						if(a>0){
						utp=a*6;
						}						
						
//						var unitSelectedValue=	$("#assdUnitTypeId"+a+" option:selected").val();
						var floorSelectedValue=	$("#assdFloorNo"+a+" option:selected").val();
						var usageSelectedValue1=$("#assdUsagetype"+utp+" option:selected").val();
						var usageSelectedValue2=$("#assdUsagetype"+(utp+1)+" option:selected").val();
						var usageSelectedValue3=$("#assdUsagetype"+(utp+2)+" option:selected").val();
						var usageSelectedValue4=$("#assdUsagetype"+(utp+3)+" option:selected").val();
						var usageSelectedValue5=$("#assdUsagetype"+(utp+4)+" option:selected").val();
						var constSelectedValue=	$("#assdConstruType"+a+" option:selected").val();
								
//						$('#unitDetailTable tr:last').find("select:eq(1)").find("option[value = '" + unitSelectedValue + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(1)").find("option[value = '" + floorSelectedValue + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(2)").find("option[value = '" + constSelectedValue + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(3)").find("option[value = '" + usageSelectedValue1 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(4)").find("option[value = '" + usageSelectedValue2 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(5)").find("option[value = '" + usageSelectedValue3 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(6)").find("option[value = '" + usageSelectedValue4 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(7)").find("option[value = '" + usageSelectedValue5 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("input:hidden:eq(0)").attr("value",year);
						$('#unitDetailTable tr:last').find("select:eq(0)").find("option[value = '" + year + "']").attr("selected", "selected");
						a++;
					}
					
					if(j%2==0)
					{	
						var ntp=c;
						if(c>0){
						ntp=c*6;
						}
//						var roadTypeSelectedValue=	$("#assdRoadFactor"+c+" option:selected").val();
						var occupancyTypeSelectedValue=	$("#assdOccupancyType"+c+" option:selected").val();
						var natureOfProperty1=$("#natureOfProperty"+ntp+" option:selected").val();
						var natureOfProperty2=$("#natureOfProperty"+(ntp+1)+" option:selected").val();
						var natureOfProperty3=$("#natureOfProperty"+(ntp+2)+" option:selected").val();
						var natureOfProperty4=$("#natureOfProperty"+(ntp+3)+" option:selected").val();
						var natureOfProperty5=$("#natureOfProperty"+(ntp+4)+" option:selected").val();
						
//						$('#unitDetailTable tr:last').find("select:eq(0)").find("option[value = '" + roadTypeSelectedValue + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(0)").find("option[value = '" + occupancyTypeSelectedValue + "']").attr("selected", "selected");
												
						$('#unitDetailTable tr:last').find("select:eq(1)").find("option[value = '" + natureOfProperty1 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(2)").find("option[value = '" + natureOfProperty2 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(3)").find("option[value = '" + natureOfProperty3 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(4)").find("option[value = '" + natureOfProperty4 + "']").attr("selected", "selected");
						$('#unitDetailTable tr:last').find("select:eq(5)").find("option[value = '" + natureOfProperty5 + "']").attr("selected", "selected");
						
						//To make Monthly field required in case of Tenanted.
					/*	$(this).find("select:eq(0)").attr("onchange", "occupancyTypeChange("+(c)+")");
						if(($('option:selected', $("#assdOccupancyType"+c)).attr('code')) == 'TAN'){							
							$("#proAssdAnnualRent"+c).prop('required',true);
						}else{
							$("#proAssdAnnualRent"+c).prop('required',false);
						}*/
						c++;
					
					}		
				}
			}
	
		}else {
			showVendorError(errorList);
			$('#checkDetail').prop('checked', false);			
		}
		
	}else{		
		$(".selectUnit option:gt(2)").remove();		
		clearTableData();  
	}
}



function updaeReviseBill(element) {
	var errorList = [];
	var divName = '.content-page';
	var formName = findClosestElementId(element, 'form');
	var theForm = '#' + formName;
	var requestData = __serializeForm(theForm);
	var object = __doAjaxRequest(
		"PropertyBillRevise.html?updateRevisedBill", 'POST',
		requestData, false, 'json');
	
	if (object.error != null && object.error != 0) {
		errorList.push(getLocalMessage('Please upload document'));
	    displayErrorsOnPage(errorList);
	} else {
	    if (object.successFlag != null) {
		showBoxForApproval(getLocalMessage("Bill updated succesfully"));
	    }
	}
	    
}

function showBoxForApproval(succesMessage) {

    var childDivName = '.msg-dialog-box';
    var message = '';
    var Proceed = getLocalMessage("proceed");
    var no = 'No';
    message += '<p class="text-blue-2 text-center padding-15">' + succesMessage
	    + '</p>';

	message += '<div class=\'text-center padding-bottom-10\'>'
		+ '<input class="btn btn-success" style="margin-right:10px" type=\'button\' value=\''
		+ Proceed + '\'  id=\'Proceed\' '
		+ ' onclick="closeApproval();"/>' + '</div>';

    $(childDivName).addClass('ok-msg').removeClass('warn-msg');
    $(childDivName).html(message);
    $(childDivName).show();
    $('#Proceed').focus();
    showModalBoxWithoutClose(childDivName);
}

function closeApproval() {
    window.location.href = 'AdminHome.html';
    $.fancybox.close();
}


function editButton(element) {
	
	$("#PropDetails").hide();
	$("#TaxCalculation").hide();
	$("#document").hide();
	$("#updateButtons").hide();
	$("#taxDetails").hide();
	$("#datatables").hide();
	$("#fetchTaxDet").show();
	
	
	
}