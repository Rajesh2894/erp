/**
 * 
 */


$( document ).ready(function() {
	
 if($('#povertyLineId').val()=='N')
			 {
		 $("#bpldiv").hide();
			 }
		 if($('#povertyLineId').val()=='Y')
		 {
			 $("#bpldiv").show();
			 $("#bplNo").data('rule-required',true);
		 }
		 else
			 {
			 $("#bpldiv").hide();
			 $("#bplNo").data('rule-required',false);
			 }
	 
	 $("#povertyLineId").change(function(e) {
		
			if ( $("#povertyLineId").val() == 'Y') {
			$("#bpldiv").show();
			 $("#bplNo").data('rule-required',true);
			}
			else
			{
			$("#bpldiv").hide();
			 $("#bplNo").data('rule-required',false);
			}
			});
     	
});