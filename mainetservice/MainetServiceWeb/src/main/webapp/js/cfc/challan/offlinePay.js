

$(document)
		.ready(
				function() {
									   
					if($('#offlineModeFlagId').val() == 'N'){
						$("#payAtCounter").prop("checked", true);
					}	

					var actionUrl = 'GeneralBankMaster.html?getBankList';
					$('#bankID').empty().append(
							$("<option></option>").attr("value", "0").text(
									getLocalMessage('selectdropdown')));
					var ajaxResponse = __doAjaxRequest(actionUrl, 'POST',{}, false, 'html');
					var prePopulate = JSON.parse(ajaxResponse);

					$.each(prePopulate, function(index, value) {
						$('#bankID').append(
								$("<option></option>").attr("value",value.bankId).text(
										(value.bank + " - " + value.micr + " - " + value.branch)));
	
					});
					var bankIdHidden = $("#hiddenBankId").val();
					if(bankIdHidden != null || bankIdHidden != 0 || bankIdHidden != undefined){
						$("#bankID").val(bankIdHidden);
					}
					$(".chosen-select-no-results").trigger("chosen:updated");
					
					$('.lessthancurrdate').datepicker({
						dateFormat : 'dd/mm/yy',
						changeMonth : true,
						changeYear : true,
						maxDate : '-0d',
						yearRange : "-100:-0"
					});
					
					
					 if ($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'P') {

						$('.offlinepayment').hide();
						$('.PCB').hide();
						$('.PCU').hide();
					
						$('#acNo').val($('#acNo').val());
						$('#chqNo').val($('#chqNo').val());
						$('.chqDate').val($('.chqDate').val());

						var lastchar = $("input[name='offlineDTO.bmChqDDDate']")
								.val().substring(0, 10);
						$("input[name='offlineDTO.bmChqDDDate']").val(lastchar);


						if ($('#payModeIn').find(":selected").attr('code') == 'C' || $('#payModeIn').find(":selected").attr('code') == 'POS') {
							$('.PPO').show();
							$('.CPAUC').hide();
							$('#PCP').hide();
							$('#bankID').removeClass('mandColorClass');
							$('#bankID').prop("disabled", true);
							$('#bankID').addClass('disablefield');
							$('#bankID').val('0');
							$('#drawnOn').val('');
							$('#acNo').attr('disabled', true);
							$('#acNo').removeClass('mandColorClass');
							$('#acNo').addClass('disablefield');
							$('#chqNo').attr('disabled', true);
							$('#chqNo').removeClass('mandColorClass');
							$('#chqNo').addClass('disablefield');
							$('.chqDate').attr('disabled', true);
							$('.chqDate').addClass('disablefield');
							$('.chqDate').removeClass('mandColorClass');
							$('#offlineDTO.bmChqDDDate').attr('disabled', true);
							$('#offlineDTO.bmChqDDDate').addClass('disablefield');
							$('#offlineDTO.bmChqDDDate').removeClass('mandColorClass');
							

						}else if($('#payModeIn').find(":selected").attr('code')=='PCP' || $('#payModeIn').find(":selected").attr('code')=='CHL' || $('#payModeIn').find(":selected").attr('code')=='NJS'){
							$('#PCP').show();
							$('#accNo').hide();
							$('#bankDet').hide();
							if($('#payModeIn').find(":selected").attr('code')=='PCP'){
								$("#selectType").text($("#PPN").val());
								$("#selectDate").text($("#PPD").val());
							}
							else if( $('#payModeIn').find(":selected").attr('code')=='CHL'){
								$("#selectType").text($("#CN").val());
								$("#selectDate").text($("#CD").val());
							}else{
								$("#selectType").text($("#NJSN").val());
								$("#selectDate").text($("#NJSD").val());
							}
						} 
						
						else if ($('#payModeIn').find(":selected").attr(
								'code') == null) {
							$('.PPO').show();
							$('.CPAUC').hide();
							$('#PCP').hide();

						} else {
							$('.CPAUC').show();
							$('#bankDet').show();
							$('.PPO').show();
							$('#PCP').hide();
							$('#bankID').removeClass('disablefield');
							$('#bankID').addClass('mandColorClass');
							$('#bankID').attr("disabled", false);
							$('#acNo').attr('disabled', false);
							$('#acNo').removeClass('disablefield');
							$('#acNo').addClass('mandColorClass');
							$('#chqNo').attr('disabled', false);
							$('#chqNo').removeClass('disablefield');
							$('#chqNo').addClass('mandColorClass');
							$('.chqDate').attr('disabled', false);
							$('.chqDate').removeClass('disablefield');
							$('.chqDate').addClass('mandColorClass');
							if ($('#payModeIn').find(":selected").attr('code') == 'PCP') {
								$('#PCP').show();
								$('#accNo').hide();
								$('#bankDet').hide();
								$("#selectType").text($("#PPN").val());
								$("#selectDate").text($("#PPD").val());
							}
						}

						if ($('#payModeIn').find(":selected").val() == '0' || $('#payModeIn').find(":selected").val() ==null) {

							$('#bankID').val('0');
						}

					}
					else
						{
						$('.offlinepayment').hide();
						$('.PCB').hide();
						$('.PCU').hide();
						$('.PPO').hide();
						$('.CPAUC').hide();
						$('#PCP').hide();
						}
				
					 $('#drawnOn').attr('disabled', false);
				});

function fieldsVisible(type4, type5, type6, type7, type8) {

	
	onlineOrOffline = type4;
	bank = type5;
	ulb = type6;
	dd = type7;
	postal = type8;
	
	if (onlineOrOffline == true) {
		$('.offlinepayment').show();
		if (bank == true) {

			$('.PCB').show();
			$('.PCU').hide();
			$('.PPO').hide();
			$('.CPAUC').hide();
			$('#PCP').hide();
			
		}
		if (ulb == true) {
			$('.PCB').hide();
			$('.PCU').show();
			$('.PPO').hide();
			$('.CPAUC').hide();
			$('#PCP').hide();
			
		}
		if (dd == true) {
			$('.PCB').hide();
			$('.PCU').hide();
			$('.PPO').hide();
			$('.CPAUC').hide();
			
		}
		if (postal == true) {
			$('.PCB').hide();
			$('.PCU').hide();
			$('.PPO').show();
			$('.CPAUC').hide();
		}
		
	}
	
}

function showDiv(obj) {
	value = obj.value;
	var blank = '';
	if (value == 'N') {
		$('.offlinepayment').show();
		$('.PPO').hide();
		$('.PCB').hide();
		$('.CPAUC').hide();
		$('#PCP').hide();
		$('#bankID').val('0');
		$('#drawnOn').val(blank);
		$('#acNo').val(blank);
		$('#chqNo').val(blank);
		$('.chqDate').val(blank);
		$('#bankAccId').val('0');

	} else if (value == 'Y') {
		$('.offlinepayment').hide();
		$('#offlineDTO.bankaAccId').val(blank);
		$('#selectBank').val(blank);
		$('.PCB').hide();
		$('.PCU').hide();
		$('.PPO').hide();
		$('#bankAccId').val('0');
		$('.CPAUC').hide();
		$('#bankID').val('0');
		$('#drawnOn').val(blank);
		$('#acNo').val(blank);
		$('#chqNo').val(blank);
		$('.chqDate').val(blank);
		$('#PCP').hide();

	} else if (value == 'P') {
		$('.PPO').show();
		$('#payModeIn').val('0');
		$('.offlinepayment').hide();
		$('#bankAccId').val('0');
		$('.PCB').hide();
		$('.PCU').hide();
		
		$('.CPAUC').hide();
		$('#PCP').hide();
		if ($('#payModeIn').find(":selected").val() == '0' || $('#payModeIn').find(":selected").val() ==null) {

			$('#bankID').attr("disabled", true);
			$('#bankID').addClass('disablefield');
			$('#bankID').removeClass('mandColorClass');
			$('#acNo').attr('disabled', true);
			$('#acNo').addClass('disablefield');
			$('#acNo').removeClass('mandColorClass');
			$('#chqNo').attr('disabled', true);
			$('#chqNo').addClass('disablefield');
			$('#chqNo').removeClass('mandColorClass');
			$('.chqDate').attr('disabled', true);
			$('.chqDate').addClass('disablefield');
			$('.chqDate').removeClass('mandColorClass');

		} 
	}
}


function showForm(obj) {

	var offlineMode = ($('option:selected', $(obj)).attr('code'));
	if (offlineMode == 'PCB') {
		$('#PCB').show();
		$('#bankAccId').val('0');
		$('#PCU').hide();
		$('#PCP').hide();
		$('#PPO').hide();

	} else if (offlineMode == 'PCU') {
		$('#PCU').show();
		$('#PCB').hide();
		$('#PPO').hide();
		$('#bankAccId').val('0');
		$('#PCP').hide();
	
	} else if (offlineMode == 'PPO') {
		$('#PPO').show();
		$('#PCU').hide();
		$('#PCB').hide();
		$('#bankAccId').val('0');
		$('#PCP').hide();
	
	}else {
		$('#PCB').hide();
		$('#PCU').hide();
		$('#PPO').hide();
		$('#bankAccId').val('0');
		$('#PCP').hide();
		
	}

}

/**
 * 
 * @param objParent
 *            this function used for to fetch Bank IFSC code
 */
function enableDisableCollectionModes(objParent) {
	var blank = '';
	var selectedValue = $('#payModeIn').find(":selected").attr('code');

	if (selectedValue == 'C'|| selectedValue=='POS' || selectedValue=='MCARD' || selectedValue=='MCASH' ) {
		$('.CPAUC').hide();
		$('#PCP').hide();
		$('#bankID').removeClass('mandColorClass');
		$('#bankID').attr("disabled", true);
		$('#bankID').addClass('disablefield');
		$('#bankID').val('0');
		$('#drawnOn').val(blank);
		$('#drawnOn').removeClass('mandColorClass');
		$('#acNo').attr('disabled', true);
		$('#acNo').addClass('disablefield');
		$('#acNo').val(blank);
		$('#acNo').removeClass('mandColorClass');
		$('#chqNo').attr('disabled', true);
		$('#chqNo').addClass('disablefield');
		$('#chqNo').val(blank);
		$('#chqNo').removeClass('mandColorClass');
		$('.chqDate').attr('disabled', true);
		$('.chqDate').addClass('disablefield');
		$('.chqDate').val(blank);
		$('.chqDate').removeClass('mandColorClass');
		

	} else if (selectedValue == null) {

		$('.CPAUC').hide();
		$('#PCP').hide();
		$('#bankID').attr("disabled", true);
		$('#bankID').addClass('disablefield').removeClass('mandColorClass');
		$('#bankID').val('0');
		$('#drawnOn').val(blank);
		$('#acNo').attr('disabled', true);
		$('#acNo').addClass('disablefield').removeClass('mandColorClass');
		$('#acNo').val(blank);
		$('#chqNo').attr('disabled', true);
		$('#chqNo').addClass('disablefield').removeClass('mandColorClass');
		$('#chqNo').val(blank);
		$('.chqDate').attr('disabled', true);
		$('.chqDate').addClass('disablefield').removeClass('mandColorClass');
		$('.chqDate').val(blank);

	}

	else {

		$('.CPAUC').show();
		$('.PPO').show();
		$('#bankDet').show();
		$('#PCP').hide();
		$('#bankID').attr("disabled", false);
		$('#bankID').removeClass('disablefield').addClass('mandColorClass');
		$('#acNo').attr('disabled', false);
		$('#acNo').removeClass('disablefield').addClass('mandColorClass');
		$('#chqNo').attr('disabled', false);
		$('#chqNo').removeClass('disablefield').addClass('mandColorClass');
		$('.chqDate').attr('disabled', false);
		$('.chqDate').removeClass('disablefield').addClass('mandColorClass');
		$('#bankID').val('0');
		$('#drawnOn').val(blank);
		$('#acNo').val(blank);
		$('#chqNo').val(blank);
		$('.chqDate').val(blank);
		if(selectedValue=='PCP' || selectedValue=='CHL' || selectedValue=='NJS'){
			$('#PCP').show();
			$('#accNo').hide();
			$('#bankDet').hide();
		}
		switch ($('#payModeIn').find(":selected").attr('code')) {
		case "P":
			$("#selectType").text($("#PO").val());
			$("#selectDate").text($("#POD").val());
			break;
		case "Q":
			$("#selectType").text($("#CQ").val());
			$("#selectDate").text($("#CQD").val());
			break;
		case "D":
			$("#selectType").text($("#DD").val());
			$("#selectDate").text($("#DDD").val());
			break;
		case "PCP":
			$("#selectType").text($("#PPN").val());
			$("#selectDate").text($("#PPD").val());
			break;
		case "CHL":
			$("#selectType").text($("#CN").val());
			$("#selectDate").text($("#CD").val());
			break;
		case "NJS":
			$("#selectType").text($("#NJSN").val());
			$("#selectDate").text($("#NJSD").val());
			break;
		}
	}

}

/**
 * 
 * @param obj :
 *            form Object
 * @param id :
 *            pass drawn On field id
 */
function getBankCode(obj) {

	var actionUrl = 'ChallanAtULBCounter.html?bankCode';
	var selectedValue = $('#bankID').find(":selected").val();
	var data = 'cbBankCode=' + selectedValue;
	var result = __doAjaxRequest(actionUrl, 'POST', data, false, 'json');
	$('#drawnOn').val(result);

}

function trimTime() {
	var lastchar = $("input[name='offlineDTO.bmChqDDDate']").val()
			.substring(0, 10);
	$("input[name='offlineDTO.bmChqDDDate']").val(lastchar);
}

function openBankForm(){
	var bankId = 1;
    var url = "GeneralBankMaster.html?form";
    var requestData = "bankId=" + bankId  + "&MODE_DATA=" + "ADD";
	var ajaxResponse = __doAjaxRequest(url, 'POST', requestData, false, 'html');
	
	var divName = ".content";
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	return false;
}
	
