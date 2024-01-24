

/*If Corresponding Address Different Property Address*/
$(function() {
	/*Corresponding Address checkbox is by default tick*/
	$('#isCorrespondenceAddressSame').prop('checked', true);
	$("#checkValue").val("Y");
})

function checkForRequired(){		
		$("#hideBillingDetails").toggle();
}
$("#isCorrespondenceAddressSame").change(function() {
    if(this.checked) {
    	$("#checkValue").val("Y"); // set Y and copy property address to correspondence address
    }
    else{
    	$("#checkValue").val("N"); 
    }
});


/*function getWardZone(deptid,orgid){
	var result=	getOperationalWardZone(orgid,deptid,$("#locId").val());
	$("#wardZone").html(result);
}*/

	

