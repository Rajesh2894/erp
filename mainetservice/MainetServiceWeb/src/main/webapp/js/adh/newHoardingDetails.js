$(document).ready(function() {
	$('.decimal').on('input', function() {
	    this.value = this.value
	      .replace(/[^\d.]/g, '')             // numbers and decimals only
	      .replace(/(^[\d]{5})[\d]/g, '$1')   // not more than 5 digits at the beginning
	      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
	      .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
	  });
});

$(function() {
$("#newAdvertisingTableId").on('click', '.addHorButton', function(e) {
	var errorList = [];
	debugger;
	 errorList = validateHoardindDetails(errorList);
	if (errorList.length == 0) {
		$('.error-div').hide();
		e.preventDefault();	
		var clickedRow = $('#newAdvertisingTableId tr').length-2;	
		//var facingValue = $('#dispTypeId'+clickedRow).val();
		var content = $("#newAdvertisingTableId").find('tr:eq(1)').clone();
		$("#newAdvertisingTableId").append(content);
		content.find("input:text").val('');
		content.find("select").val('0');
		content.find("input:hidden:eq(0)").val("0");
		content.find("textarea").val("");
		//content.find("select:eq(5)").chosen().trigger("chosen:updated"); 
		
		reOrderAdvertisingIdSequence('.advertisingDetailsClass');
		
		
	}else{
		displayErrorsOnPage(errorList);
	}	
	});
});

function addData() {
	var errorList = [];
	errorList = validateHoardindDetails(errorList);
	if (errorList.length == 0) {
		
		
		if (errorList.length == 0){
			$("#errorDiv").hide();
			addTableRow('newAdvertisingTableId');
			
		}
	} 
	
	if (errorList.length > 0) {
		$('#newAdvertisingTableId').DataTable();
		displayErrorsOnPage(errorList);
		
	}
	return false;
}
var idcount;
function reOrderAdvertisingIdSequence(advertisingDetailsClass){

	$(advertisingDetailsClass).each(function(i) {
		
		var utp = i;
		if (i > 0) {
			//utp = i * 6;
			//utp = i * 5;
		}
		//ID
		//$(this).find($('[id^="adhFee"]')).attr('id',"adhFee"+i+"_chosen");
		$(this).find("input:text:eq(0)").attr("id", "sequence" + i).val(i + 1);
		$(this).find("select:eq(0)").attr("id", "hoardingNo" + i);
		
		
		$(this).find("textarea:eq(0)").attr("id", "advDetailsDesc" + i);
		$(this).find("input:text:eq(1)").attr("id", "advDetailsHeight" + i);
		$(this).find("input:text:eq(2)").attr("id", "advDetailsLength" + i);
		$(this).find("input:text:eq(3)").attr("id", "advDetailsArea" + i);
		$(this).find("input:text:eq(4)").attr("id", "displayIdDesc" + i);
		//content.find("select:eq(1)").chosen().trigger("chosen:updated"); 
		
		
		//NAME
		$(this).find("input:text:eq(0)").attr("name", i);
		$(this).find("select:eq(0)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].hoardingId");
		$(this).find("textarea:eq(0)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].advDetailsDesc");
		$(this).find("input:text:eq(1)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].advDetailsHeight");
		$(this).find("input:text:eq(2)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].advDetailsLength");
		$(this).find("input:text:eq(3)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].advDetailsArea");
		//$(this).find("input:text:eq(4)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].dispTypeId");
		$(this).find("input:text:eq(4)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].displayIdDesc");
		
		idcount = i;
	});
}

function validateHoardindDetails(errorList) {
  
    {

	var i = 0;
	if ($.fn.DataTable.isDataTable('#hoardingNo')) {
	    $('#newAdvertisingTableId').DataTable().destroy();
	}

	$("#newAdvertisingTableId tbody tr")
		.each(
			function(i) {

			    var hoardingNo = $("#hoardingNo" + i).val();
			    var hoardingDescription = $(
				    "#advDetailsDesc" + i).val();
			    var hoardingHeight = $("#advDetailsDesc" + i).val();
			    var hoardingLength = $("#advDetailsLength" + i).val();
			    var hoardingArea = $("#advDetailsArea" + i).val();
			    var displayTypeId = $("#displayIdDesc" + i).val();

			    var rowCount = i + 1;

			    if (hoardingNo == "0" || hoardingNo == undefined
				    || hoardingNo == null) {
				errorList
					.push(getLocalMessage("contract.validate.hoardingNo")
						+ rowCount);
			    }
			    if (hoardingDescription == "0"
				    || hoardingDescription == undefined
				    || hoardingDescription == null
				    || hoardingDescription == "") {
				errorList
					.push(getLocalMessage("hoarding.master.validate.description")
						+ rowCount);
			    }
			    if (hoardingHeight == "0"
				    || hoardingHeight == undefined
				    || hoardingHeight == null
				    || hoardingHeight == "") {
				errorList
					.push(getLocalMessage("contract.validate.hoarding.height")
						+ rowCount);
			    }
			    if (hoardingLength == "0"
				    || hoardingLength == undefined
				    || hoardingLength == null
				    || hoardingLength == "") {
				errorList
					.push(getLocalMessage("contract.validate.hoarding.length")
						+ rowCount);
			    }
			    if (hoardingArea == "0"
				    || hoardingArea == undefined
				    || hoardingArea == null
				    || hoardingArea == "") {
				errorList
					.push(getLocalMessage("hoarding.master.validate.hoardingArea")
						+ rowCount);
			    }
			    if (displayTypeId == "0"
				    || displayTypeId == undefined
				    || displayTypeId == null
				    || displayTypeId == "") {
				errorList
					.push(getLocalMessage("adh.validate.displaytypeid")
						+ rowCount);
			    }

			});
	return errorList;

    }
}


$(function() {
	
	/* To delete row into table */
	$("#newAdvertisingTableId").on('click', '.delButton', function() {
		
		if ($("#newAdvertisingTableId tr").length != 2) {
			$(this).parent().parent().remove();
			reOrderAdvertisingIdSequence('.advertisingDetailsClass'); // reorder id and Path
		} else {
			var errorList = [];
			errorList.push(getLocalMessage("adh.first.row.not.remove"));
			displayErrorsOnPage(errorList);
		}
	});
});
function getHoardingDetailsByHoardingNumber(element) {
    var hoardingId = $(element).attr('id');
    var index = hoardingId.charAt(hoardingId.length - 1);

	var requestData = {
	    "hoardingId" : $(element).val()
	};
	$(".warning-div").hide();

	var ajaxResponse = doAjaxLoading(
		'NewHoardingApplication.html?searchHoardingDetailsByHoardingNumber',
		requestData, 'json');
	if(ajaxResponse.status == "Y"){
		var errorList = [];
		errorList.push(getLocalMessage("adh.validate.hoardingNo"));
		displayErrorsOnPage(errorList);
		$("#hoardingNo" + index).val('0');
		return false;
	}
	$("#hoardingNumber" + index).val( ajaxResponse.hoardingDto.hoardingNumber);
	$("#hoardingId" + index).val( ajaxResponse.hoardingDto.hoardingId);
	$("#advDetailsDesc"+index).val(ajaxResponse.hoardingDto.hoardingDescription);
	$("#advDetailsHeight"+index).val(ajaxResponse.hoardingDto.hoardingHeight);
	$("#advDetailsLength"+index).val(ajaxResponse.hoardingDto.hoardingLength);
	$("#advDetailsArea"+index).val(ajaxResponse.hoardingDto.hoardingArea);
	$("#displayIdDesc"+index).val(ajaxResponse.hoardingDto.displayIdDesc);
	 
}
