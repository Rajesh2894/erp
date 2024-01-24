$( document ).ready(function(){
	
	$('.lessthancurrdate').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true,
		maxDate: '-0d',
		yearRange: "-100:-0"
	});
	
	$("#payModeIn").change(function(e)
			 {
				$("#amountToPay").val($("#amount").text());
			 });
	
	var selectedValue = $('#payModeIn').find(":selected").attr('code');
	var blank='';
	if(selectedValue=='C' || selectedValue==null ){
		$('#hideShow').hide();
		//$('#bankID').attr("disabled", true); 
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
});

function enableDisableCollectionModes()
{ 
	var blank='';
	var selectedValue = $('#payModeIn').find(":selected").attr('code');
	
	if(selectedValue=='C' || selectedValue==null){
		$('#hideShow').hide();
		//$('#bankID').attr("disabled", true); 
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
	else{
		$('#hideShow').show();
		$('.PPO').show();
		//$('#bankID').attr("disabled", false); 
		$('#bankID').removeClass('disablefield').addClass('mandColorClass');
		$('#acNo').attr('disabled', false);
		$('#acNo').removeClass('disablefield').addClass('mandColorClass');
		$('#chqNo').attr('disabled', false);
		$('#chqNo').removeClass('disablefield').addClass('mandColorClass');
		$('.chqDate').attr('disabled', false);
		$('.chqDate').removeClass('disablefield').addClass('mandColorClass');
		$('#amount').removeClass('disablefield').addClass('mandColorClass');
		$('#bankID').val('0');
		$('#drawnOn').val(blank);
		$('#acNo').val(blank);
		$('#chqNo').val(blank);
		$('.chqDate').val(blank);
		switch($('#payModeIn').find(":selected").attr('code')){
		case "P":$("#selectType").text($("#PO").val());$("#selectDate").text($("#POD").val());break;
		case "Q":$("#selectType").text($("#CQ").val());$("#selectDate").text($("#CQD").val());break;
		case "D":$("#selectType").text($("#DD").val());$("#selectDate").text($("#DDD").val());break;
	}
	}

	
}

function getBankCode(obj){

	var actionUrl='ChallanAtULBCounter.html?bankCode';	
	var selectedValue = $('#bankID').find(":selected").val();

	var data='cbBankCode='+selectedValue;
	var result=__doAjaxRequest(actionUrl,'POST',data,false,'json');
	$('#drawnOn').val(result);
	
}
function clearForm(url)
{
	window.open(url,'_self',false);

}


function regenerateChallan(element) {
	
	var paramValue = 'PrintULBChallan';
	
	return saveOrUpdateForm(element, $('#regeneraterechallan').val(),
			'ChallanAtULBCounter.html?' + paramValue, 'RegenerateChallan');
	
}

