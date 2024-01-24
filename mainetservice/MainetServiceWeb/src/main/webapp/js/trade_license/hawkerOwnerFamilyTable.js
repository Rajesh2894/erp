
$(document).ready(function() {


	 reOrderownerFamilyDetailsSequence('.appendableFamilyClass');
	

});


$(function() {
	/* To add new Row into table */
		$("#ownerFamilyDetail").on('click','.addCF',function(){

		
		var errorList = [];
		errorList = validateownerFamilyDetailsTable(errorList);
		if (errorList.length == 0) {

			//fileCountUpload();
			var content = $("#ownerFamilyDetail").find('tr:eq(1)').clone();
			$("#ownerFamilyDetail").append(content);

			content.find("input:text").val('');
			content.find("select").val('');
			content.find("textarea").val('');

			//content.find("select").val('0');
			content.find("input:hidden").val('');
			$('.error-div').hide();
			reOrderownerFamilyDetailsSequence('.appendableFamilyClass'); // reorder id and Path
		} else {
			displayErrorsOnPage(errorList);
		}
	});
});

function validateownerFamilyDetailsTable() {
	var errorList = [];
	var rowCount = $('#ownerFamilyDetail tr').length-1;	
	var mobNo = [];
	var famMemUidNo=[];
	var email=[];

	if ($.fn.DataTable.isDataTable('#ownerFamilyDetail')) {
		$('#ownerFamilyDetail').DataTable().destroy();
	}
	if (errorList == 0)
		$("#ownerFamilyDetail tbody tr").each(function(i) {
			
			if(rowCount<2){

							var famMemName = $("#famMemName" + i).val();
							var famMemAge = $("#famMemAge" + i).val();
							var famMemUidNo = $("#famMemUidNo" + i).val();
							var famMemRelation = $("#famMemRelation" + i).val();
							
							var constant = 1;
			}
			else{
				var famMemName = $("#famMemName" + i).val();
				var famMemAge = $("#famMemAge" + i).val();
				var famMemUidNo = $("#famMemUidNo" + i).val();
				var famMemRelation = $("#famMemRelation" + i).val();
				
				var constant = i+1;
			}
							if (famMemName == '0' || famMemName == undefined
									|| famMemName == "") {
								errorList
										.push(getLocalMessage("hawkerlicense.validation.famMemName")
												+" " +constant);
							}
							if (famMemAge == ""
									|| famMemAge == undefined
									|| famMemAge == "0") {
								errorList
										.push(getLocalMessage("hawkerlicense.validation.famMemAge")
												+" " +constant);
							}
							
							
							if (famMemRelation == "" || famMemRelation == undefined
									|| famMemRelation == "0") {
								errorList
										.push(getLocalMessage("hawkerlicense.validation.famMemRelation")
												+" " +constant);
							}
							
							
						});
	


	
	return errorList;
}


function reOrderownerFamilyDetailsSequence(classNameFirst) {
	
	$(classNameFirst).each(
		function(i) 
		{
			
			// id binding
			$(this).find("input:text:eq(0)").attr("id", "famMemName" + i);
			$(this).find("input:text:eq(1)").attr("id", "famMemAge" + i);
			$(this).find("input:text:eq(2)").attr("id", "famMemUidNo" + i);
			$(this).find("input:text:eq(3)").attr("id", "famMemRelation" + i);
			
			// path binding
			$(this).find("input:text:eq(0)").attr("name", "tradeMasterDetailDTO.ownerFamilydetailDTO["+i+"].famMemName");
			$(this).find("input:text:eq(1)").attr("name", "tradeMasterDetailDTO.ownerFamilydetailDTO["+i+"].famMemAge");
			$(this).find("input:text:eq(2)").attr("name", "tradeMasterDetailDTO.ownerFamilydetailDTO["+i+"].famMemUidNo");
			$(this).find("input:text:eq(3)").attr("name", "tradeMasterDetailDTO.ownerFamilydetailDTO["+i+"].famMemRelation");

		

		});
}

/*To delete Row From the table*/ 
/*$("#ownerFamilyDetail").on('click', '.delButton', function() {
	

	if ($("#ownerFamilyDetail tr").length != 2) {
		$(this).parent().parent().remove();
		$(deleteRow).closest('tr').remove();
		reOrderownerFamilyDetailsSequence('.appendableFamilyClass');
	} else {
		var errorList = [];
		errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
		displayErrorsOnPage(errorList);
	}
});*/


$('body').on('focus',".hasAadharNo", function(){
	$('.hasAadharNo').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	});
	});

function DeleteRow(obj)

{
	

	if ($("#ownerFamilyDetail tr").length != 2) {
		$(obj).parent().parent().remove();
		/* $(deleteRow).closest('tr').remove(); */
		reOrderownerFamilyDetailsSequence('.appendableFamilyClass');
	} else {
		var errorList = [];
		errorList.push(getLocalMessage("trade.firstrowcannotbeRemove"));
		displayErrorsOnPage(errorList);
	}
}
