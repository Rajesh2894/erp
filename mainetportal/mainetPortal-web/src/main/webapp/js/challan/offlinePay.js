

$(document)
		.ready(
				function() {
					
					if($('#offlineModeFlagId').val() == 'N'){
						$("#onlineModeId").prop("checked", true);
					}	
					
					
					
					
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


						if ($('#payModeIn').find(":selected").attr('code') == 'C') {

							$('.PPO').show();
							$('.CPAUC').show();
							$('#bankID').prop("disabled", true);
							$('#bankID').addClass('disablefield');
							$('#bankID').val('');
							$('#acNo').attr('disabled', true);
							$('#acNo').addClass('disablefield');
							$('#chqNo').attr('disabled', true);
							$('#chqNo').addClass('disablefield');
							$('.chqDate').attr('disabled', true);
							$('.chqDate').addClass('disablefield');
							$('#offlineDTO.bmChqDDDate').attr('disabled', true);
							$('#offlineDTO.bmChqDDDate').addClass('disablefield');
							$('#amount').attr('disabled', false);
							$('#amount').removeClass('disablefield');

						} else if ($('#payModeIn').find(":selected").attr(
								'code') == null) {
							$('.PPO').show();
							$('.CPAUC').hide();

						} else {

							$('.CPAUC').show();
							$('.PPO').show();
							$('#bankID').attr("disabled", false);
							$('#bankID').removeClass('disablefield');
							$('#acNo').attr('disabled', false);
							$('#acNo').removeClass('disablefield');
							$('#chqNo').attr('disabled', false);
							$('#chqNo').removeClass('disablefield');
							$('.chqDate').attr('disabled', false);
							$('.chqDate').removeClass('disablefield');
							$('#amount').removeClass('disablefield');

						}

						if ($('#payModeIn').find(":selected").val() == '0') {

							$('#bankID').val('');
						}

					}

					else
						{
						$('.offlinepayment').hide();
						$('.PCB').hide();
						$('.PCU').hide();
						$('.PPO').hide();
						$('.CPAUC').hide();
						}
				
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
			
		}
		if (ulb == true) {
			$('.PCB').hide();
			$('.PCU').show();
			$('.PPO').hide();
			$('.CPAUC').hide();
			
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
		$('#bankID').val(blank);
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
		$('#bankID').val(blank);
		$('#drawnOn').val(blank);
		$('#acNo').val(blank);
		$('#chqNo').val(blank);
		$('.chqDate').val(blank);

	} else if (value == 'P') {
		$('.PPO').show();
		$('#payModeIn').val(blank);
		$('.offlinepayment').hide();
		$('#bankAccId').val('0');
		$('.PCB').hide();
		$('.PCU').hide();
		
		$('.CPAUC').hide();

		if ($('#payModeIn').find(":selected").val() == '0') {

			$('#bankID').attr("disabled", true);
			$('#bankID').addClass('disablefield');
			$('#acNo').attr('disabled', true);
			$('#acNo').addClass('disablefield');
			$('#chqNo').attr('disabled', true);
			$('#chqNo').addClass('disablefield');
			$('.chqDate').attr('disabled', true);
			$('.chqDate').addClass('disablefield');

		} else {
		}

	}
}


function showForm(obj) {

	var offlineMode = ($('option:selected', $(obj)).attr('code'));
	if (offlineMode == 'PCB') {
		$('#PCB').show();
		$('#bankAccId').val('0');
		$('#PCU').hide();
		
		$('#PPO').hide();

	} else if (offlineMode == 'PCU') {
		$('#PCU').show();
		$('#PCB').hide();
		$('#PPO').hide();
		$('#bankAccId').val('0');
	
	} else if (offlineMode == 'PPO') {
		$('#PPO').show();
		$('#PCU').hide();
		$('#PCB').hide();
		$('#bankAccId').val('0');
	
	}else {
		$('#PCB').hide();
		$('#PCU').hide();
		$('#PPO').hide();
		$('#bankAccId').val('0');
		
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

	if (selectedValue == 'C') {
		$('.CPAUC').show();
		$('#bankID').attr("disabled", true);
		$('#bankID').addClass('disablefield');
		$('#bankID').val(blank);
		$('#drawnOn').val(blank);
		$('#acNo').attr('disabled', true);
		$('#acNo').addClass('disablefield');
		$('#acNo').val(blank);
		$('#chqNo').attr('disabled', true);
		$('#chqNo').addClass('disablefield');
		$('#chqNo').val(blank);
		$('.chqDate').attr('disabled', true);
		$('.chqDate').addClass('disablefield');
		$('.chqDate').val(blank);

	} else if (selectedValue == null) {

		$('.CPAUC').hide();
		$('#bankID').attr("disabled", true);
		$('#bankID').addClass('disablefield');
		$('#bankID').val(blank);
		$('#drawnOn').val(blank);
		$('#acNo').attr('disabled', true);
		$('#acNo').addClass('disablefield');
		$('#acNo').val(blank);
		$('#chqNo').attr('disabled', true);
		$('#chqNo').addClass('disablefield');
		$('#chqNo').val(blank);
		$('.chqDate').attr('disabled', true);
		$('.chqDate').addClass('disablefield');
		$('.chqDate').val(blank);

	}

	else {

		$('.CPAUC').show();
		$('.PPO').show();
		$('#bankID').attr("disabled", false);
		$('#bankID').removeClass('disablefield');
		$('#acNo').attr('disabled', false);
		$('#acNo').removeClass('disablefield');
		$('#chqNo').attr('disabled', false);
		$('#chqNo').removeClass('disablefield');
		$('.chqDate').attr('disabled', false);
		$('.chqDate').removeClass('disablefield');
		$('#amount').removeClass('disablefield');
		$('#bankID').val(blank);
		$('#drawnOn').val(blank);
		$('#acNo').val(blank);
		$('#chqNo').val(blank);
		$('.chqDate').val(blank);
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
