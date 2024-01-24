$(document).ready(function()
{	
	$(function() {
		$('#ChqDdDate').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			minDate: 0
		});
	});
	
	$("#offlinePay").change(function(e)
	{
		if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N')		
		{
			$("#Collection_mode").hide();			
			$("#payModeIn").val('0');
			$("#bankID").val('');
			$("#instrumentNo").val('');
			$("#ChqDdDate").val('');
			$("#offlinePayement").show();
		}
	});
	
	$("#payUlb").change(function(e)
	{
		if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'P')	
		{
			$("#Collection_mode").show();
			$("#offlinePayement").hide();
			$(".cash").show();
		}
	});
	
	$("#payModeIn").change(function(e)
	{
		if($('#payModeIn option:selected').attr('code') == "C")
		{
			$(".cash").hide();
		}
		else
		{
			$(".cash").show();
		}
	});
	
});