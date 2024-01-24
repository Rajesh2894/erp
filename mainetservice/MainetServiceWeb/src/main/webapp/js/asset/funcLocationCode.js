var cdmURL = "AssetFunctionalLocation.html";
$(document).ready(function() {
	$('#functionLocationMaster').validate({
		onkeyup : function(element) {
			this.element(element);
			console.log('onkeyup fired');
		},
		onfocusout : function(element) {
			this.element(element);
			console.log('onfocusout fired');
		}
	});

});


function verifyDecimalNo(obj) {
	obj.value = obj.value.replace(/[^\d.]/g, '') // numbers and decimals only
	.replace(/(\..*)\./g, '$1') // decimal can't exist more than once
	.replace(/(\.[\d]{6})./g, '$1'); // max 2 digits after decimal
}

function saveFuncLocCode(obj){

	var errorList = [];
	
	/*var rowCount = $('#funcLocCodeTable tr').length;
	
	
	for(var i=0;i<rowCount-1;i++)
	{
		
		var funcLocCode_id				=	"#funcLocDTOList" + i + "\\.funcLocationCode";
		var description_id				=	"#funcLocDTOList" + i + "\\.description";
		var parentId_id					=	"#funcLocDTOList" + i + "\\.parentId";
		var startPoint_id				=	"#funcLocDTOList" + i + "\\.startPoint";
		var endPoint_id					=	"#funcLocDTOList" + i + "\\.endPoint";
		var unit_id						=	"#funcLocDTOList" + i + "\\.unit";
		
		var funcLocCodeVal				=	$(funcLocCode_id).val();
		var descriptionVal				=	$(description_id).val();
		var parentIdVal					=	$(parentId_id).val();
		var startPointVal				=	$(startPoint_id).val();
		var endPointVal					=	$(endPoint_id).val();
		var unitVal		=	$(unit_id).val();
	
		if (funcLocCodeVal == undefined || funcLocCodeVal == '') {
			errorList.push(getLocalMessage("asset.functional.location.vldnn.code1") + " Row "+(i+1) );
		}
		if (descriptionVal == undefined || descriptionVal == '') {
			errorList.push(getLocalMessage("asset.functional.location.vldnn.description") + " Row "+(i+1) );
		}
		if (parentIdVal == undefined || parentIdVal == '') {
			errorList.push(getLocalMessage("asset.functional.location.vldnn.parentid") + " Row "+(i+1) );
		}
		if (startPointVal == undefined || startPointVal == '') {
			errorList.push(getLocalMessage("asset.functional.location.vldnn.startpoint") + " Row "+(i+1) );
		}
		if (endPointVal == undefined || endPointVal == '') {
			errorList.push(getLocalMessage("asset.functional.location.vldnn.endpoint") + " Row "+(i+1) );
		}
		if (unitVal == undefined || unitVal == '') {
			errorList.push(getLocalMessage("asset.functional.location.vldnn.unitofmeasurement") + " Row "+(i+1) );
		}
		
		
	}*/
	errorList = validateEntryDetails(errorList);
	
	if (errorList.length > 0) 
	{
		$("#errorDiv").show();
		showErr(errorList);
	}
	
	else 
	{
		$("#errorDiv").hide();
		var response	=	saveOrUpdateForm(obj,getLocalMessage('functional.location.master.vldn.savesuccessmsg'),
												'AssetFunctionalLocation.html', 'saveform');
		prepareDateTag();
	}
	
}

function showErr(errorList) {

	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);
	$(".warning-div").removeClass('hide')
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	errorList = [];
}

function closeErrBox() {
	$('.warning-div').addClass('hide');
}


$(function () {   
	$("#funcLocCodeTable").on('click','.addCF',function(i){
		//validate check 
		var errorList = [];
		//D#80482
		errorList = validateEntryDetails(errorList);
		if (errorList.length == 0) {
			
			var $tableBody = $("#funcLocCodeTable").find("tbody"),
			$trLast = $tableBody.find("tr:last"),
			$trNew = $trLast.clone();

			var newValue = parseInt($trLast.find("#OISrNo").val())+1;
			$trNew.find("#OISrNo").val(newValue);
			$trNew.find("#b").val('');
			$trLast.after($trNew);
			reOrderTableIdSequence();
			
			$trNew.find("input:text").val('');
			$trNew.find("select:eq(1)").val('');
			$trNew.find("input:hidden").val('');
			$("#errorDiv").hide();
		} else {
			displayErrorsOnPage(errorList);
		}

	});
	
	
	
$("#funcLocCodeTable").on('click','.remCF',function(){
		
		if($("#funcLocCodeTable tr").length != 2)
		{
				 $(this).parent().parent().remove();					
				 reOrderTableIdSequence();
		}
	    else
		{
		   var errorList = [];
		   errorList.push(getLocalMessage("asset.delete.first.row"));   
		   displayErrorsOnPageView(errorList);
				//alert("You cannot delete first row");
		}
		
 });
	
});

function validateEntryDetails(errorList) {
	$("#funcLocCodeTable tbody tr")
			.each(
					function(i) {
						let position = " at Row " + (i + 1);	
						var funcLocCode_id				=	"#funcLocDTOList" + i + "\\.funcLocationCode";
						var description_id				=	"#funcLocDTOList" + i + "\\.description";
						var parentId_id					=	"#funcLocDTOList" + i + "\\.parentId";
						var startPoint_id				=	"#funcLocDTOList" + i + "\\.startPoint";
						var endPoint_id					=	"#funcLocDTOList" + i + "\\.endPoint";
						var unit_id						=	"#funcLocDTOList" + i + "\\.unit";
						let funcLocationCode = $(funcLocCode_id).val();
						let description = $(description_id).val();
						let parentId = $(parentId_id).val();
						let startPoint = $(startPoint_id).val();
						let endPoint = $(endPoint_id).val();
						let unit = $(unit_id).val();
						
						if (funcLocationCode == '' || funcLocationCode == undefined) {
							errorList.push(getLocalMessage('asset.functional.location.vldnn.code1') +position);
						}
						
						if (description == '' || description == undefined) {
							errorList.push(getLocalMessage('asset.functional.location.vldnn.description') +position);
						}
						
						if (parentId == "0" || parentId == undefined || parentId == "") {
							errorList.push(getLocalMessage("asset.functional.location.vldnn.parentid")+ position);
						}
						
						if (startPoint == '' || startPoint == undefined) {
							errorList.push(getLocalMessage('asset.functional.location.vldnn.startpoint') +position);
						}
						
						if (endPoint == '' || endPoint == undefined) {
							errorList.push(getLocalMessage('asset.functional.location.vldnn.endpoint') +position);
						}
						
						if (unit == '' || unit == undefined) {
							errorList.push(getLocalMessage('asset.functional.location.vldnn.unitofmeasurement') +position);
						}
						
					});

	return errorList;

}


function reOrderTableIdSequence() {
	
	$('.appendableClass').each(function(i) {
		$(this).find("input:text:eq(0)").attr("id", "funcLocDTOList"+ i+".funcLocationCode");
//		$(this).find("input:hidden:eq(0)").attr("id", "maId"+ i);
		$(this).find("input:text:eq(1)").attr("id", "funcLocDTOList"+ i+".description");
		$(this).find("select:eq(0)").attr("id", "funcLocDTOList"+ i+".parentId");
		$(this).find("input:text:eq(2)").attr("id", "funcLocDTOList"+ i+".startPoint");
		$(this).find("input:text:eq(3)").attr("id", "funcLocDTOList"+ i+".endPoint");
		$(this).find("select:eq(1)").attr("id", "funcLocDTOList"+ i+".unit");
		
		

		$(this).find("input:button:eq(4)").attr("id", "delButton"+ i);
		$(this).parents('tr').find('.delButton').attr("id", "delButton"+ i);
		
		$(this).find("input:text:eq(0)").attr("name", "funcLocDTOList[" + i + "].funcLocationCode").attr("onchange","checkForDuplicateIteamNo(this,"+i+")");
//		$(this).find("input:hidden:eq(0)").attr("name", "materialMasterListDto[" + i + "].maId");
		$(this).find("input:text:eq(1)").attr("name", "funcLocDTOList[" + i + "].description");
		$(this).find("select:eq(0)").attr("name", "funcLocDTOList[" + i + "].parentId").attr("onchange","resetIteamNo(this,"+i+")");
		$(this).find("input:text:eq(2)").attr("name", "funcLocDTOList[" + i + "].startPoint").attr("onkeyup","verifyDecimalNo(this)");
		$(this).find("input:text:eq(3)").attr("name", "funcLocDTOList[" + i + "].endPoint").attr("onkeyup","verifyDecimalNo(this)");
		$(this).find("select:eq(1)").attr("name", "funcLocDTOList[" + i + "].unit").attr("onchange","resetIteamNo(this,"+i+")");
		
	});
	$('#materialMasterTab').DataTable();
}

function validateFuncLocCode(obj)
{
	
	
	var elmId	=	$(obj).attr("id");
	
	id	=	elmId.replace(".","\\.");
	var funcLocCode	= $('#'+id).val();

	 
	var requestData = {};
			
	requestData= {funcLocCode:funcLocCode};
	var actionParam	=	"validateFuncLocCode";
	
	var url	=	'AssetFunctionalLocation.html'+'?' + actionParam;
		
		var obj	=	"";
	var response	=__doAjaxRequestForSave(url, 'post',requestData, false,'',obj);
	
	console.log("-"+response["errMsg"]);
	
	var duplicate	=	response["errMsg"];
	
	if(duplicate	!=	'')
	{
		var errorList = [];
	   	errorList.push(duplicate);   
		showErr(errorList);	
	}
	 /* else
		 $(".warning-div").addClass('hide') */
}


function showErr(errorList) {

	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i>&nbsp;'
				+ errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$(".warning-div").html(errMsg);
	$(".warning-div").removeClass('hide')
	$('html,body').animate({
		scrollTop : 0
	}, 'slow');
	errorList = [];
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
