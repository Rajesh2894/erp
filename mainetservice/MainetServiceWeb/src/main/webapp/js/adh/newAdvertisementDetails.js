$(document).ready(function() {
	$('.decimal').on('input', function() {
	    this.value = this.value
	      .replace(/[^\d.]/g, '')             // numbers and decimals only
	      .replace(/(^[\d]{5})[\d]/g, '$1')   // not more than 5 digits at the beginning
	      .replace(/(\..*)\./g, '$1')         // decimal can't exist more than once
	      .replace(/(\.[\d]{2})./g, '$1');    // not more than 2 digits after decimal
	  });
	reOrderAdvertisingIdSequence('.advertisingDetailsClass');
});

function validateAdvertisingDetails(errorList){
	
	var rowCount = $('#newAdvertisingTableId tr').length;
	var waste = [];
	var info = '<li><i class="fa fa-exclamation-circle"></i> &nbsp;';
	$('.advertisingDetailsClass').each(function(i) {
			if (rowCount <= 2) {
					var adhTypeId1 = $("#adhTypeId" + 1).val();
				    var adhTypeId2 = $("#adhTypeId" + 2).val();
				    /*var adhTypeId3 = $("#adhTypeId" + 3).val();
					var adhTypeId4 = $("#adhTypeId" + 4).val();
					var adhTypeId5 = $("#adhTypeId" + 5).val();*/
					var advDetailsDesc = $("#advDetailsDesc" + i).val();
					var advDetailsHeight = $("#advDetailsHeight" + i).val();
					var advDetailsLength = $("#advDetailsLength" + i).val();
					var advDetailsArea = $("#advDetailsArea" + i).val();
					var dispTypeId = $("#dispTypeId" + i).val();
					var unit = $("#unit" + i).val();
				var level = 1;
		} else {
				var utp = i;
				utp = i * 6;
				var adhTypeId1 = $("#adhTypeId" + (utp + 1)).val();
			    var adhTypeId2 = $("#adhTypeId" + (utp + 2)).val();
			   /* var adhTypeId3 = $("#adhTypeId" + (utp + 3)).val();
				var adhTypeId4 = $("#adhTypeId" + (utp + 4)).val();
				var adhTypeId5 = $("#adhTypeId" + (utp + 5)).val();*/
				var advDetailsDesc = $("#advDetailsDesc" + i).val();
				var advDetailsHeight = $("#advDetailsHeight" + i).val();
				var advDetailsLength = $("#advDetailsLength" + i).val();
				var advDetailsArea = $("#advDetailsArea" + i).val();
				var dispTypeId = $("#dispTypeId" + i).val();
				var unit = $("#unit" + i).val();
				/*if (adhTypeId5= undefined) {
					waste.push($("#codWast" + (utp + 5)).val())
				} else {
					if (codWast4 != undefined) {
						waste.push($("#codWast" + (utp + 4)).val())
					} else {
						waste.push($("#codWast" + (utp + 3)).val())
					}
				}*/
	     	var level = i + 1;
		}
		if (adhTypeId1 == '' || adhTypeId1 == undefined || adhTypeId1 == '0') {
				errorList.push(getLocalMessage("adh.advertisement.validation.select.advertiser.type")+" " +level);
			}
		if (adhTypeId2 == '' || adhTypeId2 == undefined || adhTypeId2 == '0') {
				errorList.push(getLocalMessage("adh.advertisement.validation.select.advertiser.subtype" + " ")+" " +level);
			}
		if (advDetailsDesc == '' || advDetailsDesc == undefined || advDetailsDesc == '0') {
			errorList.push(getLocalMessage("adh.advertisement.validation.enter.description "+ " ")+" " +level);
		}
		if (advDetailsHeight == '' || advDetailsHeight == undefined || advDetailsHeight == '0.00') {
			errorList.push(getLocalMessage("adh.advertisement.validation.enter.height"+ " ")+" " +level);
		}
		if (advDetailsHeight == '' || advDetailsHeight == undefined || advDetailsHeight == '0.00') {
			errorList.push(getLocalMessage("adh.advertisement.validation.enter.length "+ " ")+" " +level);
		}
		if (advDetailsArea == '' || advDetailsArea == undefined || advDetailsArea == '0.00') {
			errorList.push(getLocalMessage("adh.advertisement.validation.enter.area "+ " ")+" " +level);
		}
		if (dispTypeId == '' || dispTypeId == undefined || dispTypeId == '0') {			
			errorList.push(getLocalMessage("adh.advertisement.validation.select.display.face"+ " ")+" " +level);
		}
		if (unit == '' || unit == undefined || unit == '0') {
			if(unit == '0'){
				errorList.push(getLocalMessage("adh.advertisement.validation.valid.unit.number"+ " ")+" " +level);
			}else{
				errorList.push(getLocalMessage("adh.advertisement.validation.unit "+ " ")+" " +level);
			}			
		}
	});				
	return errorList;
  }



$(function() {
$("#newAdvertisingTableId").on('click', '.addAdvertising', function(e) {
	
	var errorList = [];
	
	 errorList = validateAdvertisingDetails(errorList);
	if (errorList.length == 0) {
		$('.error-div').hide();
		e.preventDefault();	
		var clickedRow = $('#newAdvertisingTableId tr').length-2;	
		var facingValue = $('#dispTypeId'+clickedRow).val();
		var content = $("#newAdvertisingTableId").find('tr:eq(1)').clone();
		$("#newAdvertisingTableId").append(content);
		content.find("input:text").val('');
		content.find("select").val('0');
		content.find("input:hidden:eq(0)").val("0");
		content.find("textarea").val("");
		content.find("select:eq(5)").chosen().trigger("chosen:updated"); 
		
		reOrderAdvertisingIdSequence('.advertisingDetailsClass');
		$("#adhTypeId" + (idcount * 6 + 2) + " option:not(:first)").remove();
		$("#adhTypeId" + (idcount * 6 + 3) + " option:not(:first)").remove();
		/*$("#adhTypeId" + (idcount * 6 + 4) + " option:not(:first)").remove();
		$("#adhTypeId" + (idcount * 6 + 5) + " option:not(:first)").remove();*/
		$('#dispTypeId'+(clickedRow+1)).val(facingValue).trigger("chosen:updated");
		
	}else{
		displayErrorsOnPage(errorList);
	}	
	});
});
var idcount;
function reOrderAdvertisingIdSequence(advertisingDetailsClass){
	
	$(advertisingDetailsClass).each(function(i) {		
		var utp = i;
		if (i > 0) {
			utp = i * 6;
		}
		//ID
		//$(this).find($('[id^="adhFee"]')).attr('id',"adhFee"+i+"_chosen");
		$(this).find("input:text:eq(0)").attr("id", "sequence" + i).val(i + 1);
		$(this).find("select:eq(0)").attr("id", "adhTypeId" + (utp + 1));
		$(this).find("select:eq(1)").attr("id", "adhTypeId" + (utp + 2));
		/*$(this).find("select:eq(2)").attr("id", "adhTypeId" + (utp + 3));
		$(this).find("select:eq(3)").attr("id", "adhTypeId" + (utp + 4));
		$(this).find("select:eq(4)").attr("id", "adhTypeId" + (utp + 5));*/
		
		$(this).find("textarea:eq(0)").attr("id", "advDetailsDesc" + i);
		$(this).find("input:text:eq(1)").attr("id", "advDetailsHeight" + i);
		$(this).find("input:text:eq(2)").attr("id", "advDetailsLength" + i);
		$(this).find("input:text:eq(3)").attr("id", "advDetailsArea" + i);
		$(this).find("input:text:eq(4)").attr("id", "unit" + i);
		$(this).find("select:eq(2)").attr("id", "dispTypeId" + i);
		
		
		//NAME
		$(this).find("input:text:eq(0)").attr("name", i);
		$(this).find("select:eq(0)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].adhTypeId1");
		$(this).find("select:eq(1)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].adhTypeId2");
/*		$(this).find("select:eq(2)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].adhTypeId3");
		$(this).find("select:eq(3)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].adhTypeId4");
		$(this).find("select:eq(4)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].adhTypeId5");*/
	
		$(this).find("textarea:eq(0)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].advDetailsDesc");
		$(this).find("input:text:eq(1)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].advDetailsHeight");
		$(this).find("input:text:eq(2)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].advDetailsLength");
		$(this).find("input:text:eq(3)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].advDetailsArea");
		$(this).find("input:text:eq(4)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].unit");
		$(this).find("select:eq(2)").attr("name", "advertisementReqDto.advertisementDto.newAdvertDetDtos[" + i + "].dispTypeId");
		idcount = i;
	});
}


$(function() {
	
	/* To add new Row into table */
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

function calculateArea(){
	$('.advertisingDetailsClass').each(function(i) {
		
    	var area = 0;
    	var height = parseFloat($("#advDetailsHeight" + i).val());
   	    var length = parseFloat($("#advDetailsLength" + i).val());
   	 if (!isNaN(height) && !isNaN(length)) {
   		area =+ (height * length);
   	    }
        $("#advDetailsArea" + i).val(area.toFixed(2));
    });
	
}


function getPropertyDetails(element) {
	debugger;
	var propertyNo = $("#propNumber").val();
	var theForm = '#newAdvertisementApplication';
	if (propertyNo != '') {
		var requestData = {};
		requestData = __serializeForm(theForm);

		var URL = 'NewAdvertisementApplication.html?getPropertyDetails';
		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
		var errMsg = returnData["errMsg"];
		if (errMsg != '' && errMsg != undefined) {
			var errorList = [];
			errorList.push(errMsg);
			showMessageOnSubmit(errMsg, 'NewAdvertisementApplication.html');
		} else {
			$(formDivName).html(returnData);
		}
	}
}

function showMessageOnSubmit(message, redirectURL) {
	
	var errMsgDiv = '.msg-dialog-box';
	var cls = "Ok";

	var d = '<h5 class=\'text-center text-blue-2 padding-5\'>' + message
			+ '</h5>';
	d += '<div class=\'text-center\'><input type=\'button\' class= "btn btn-success" value=\''
			+ cls + '\'  id=\'btnNo\' onclick="proceed()"/></div>';

	$(errMsgDiv).addClass('ok-msg').removeClass('warn-msg');
	$(errMsgDiv).html(d);
	$(errMsgDiv).show();
	$('#btnNo').focus();
	showPopUpMsg(errMsgDiv);
}

function showPopUpMsg(childDialog) {
	$.fancybox({
		type : 'inline',
		href : childDialog,
		openEffect : 'elastic',
		closeBtn : false,
		helpers : {
			overlay : {
				closeClick : false
			}
		},
		keys : {
			close : null
		}
	});
	return false;
}
function proceed() {
	window.location.href = "NewAdvertisementApplication.html";
}