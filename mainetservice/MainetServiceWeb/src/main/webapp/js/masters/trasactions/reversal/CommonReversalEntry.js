$(document)
		.ready(
				function() {
					
						
						$('#datatables').DataTable({
				    		"oLanguage": { "sSearch": "" } ,
				    		"aLengthMenu": [ [5, 10, 15, -1], [5, 10, 15, "All"] ],
				    	    "iDisplayLength" : 5, 
				    	    "bInfo" : true,
				    	    "lengthChange": true,
				    	    "bPaginate": true,
				    	    "bFilter": true
				    	    });
						
						$('#selectall').click(function(event) {
							if (this.checked) {
								$('.checkall').each(function() {
									this.checked = true;
								});
							} else {
								$('.checkall').each(function() {
									this.checked = false;

								});
							}
						});
						
						
					 	yearLength();	
						
						$('#reasonForDeletion').hide();
						
						
				});



function showField()
{
	$('#reasonForDeletion').show();

}




function Reversal(element)
{
	var errorList = [];
	var remarks = $("#receiptDelRemark").val();
	
	if(remarks == '' || remarks == undefined)
	{
			errorList.push(getLocalMessage("water.receipt.reversal.remark.validation")); //Defect #158035
	}
	if (errorList.length > 0) 
	{
			showError(errorList);
	}else{
		return saveOrUpdateForm(element,
		"Your application for contract Agreement saved successfully!",'CommonReversalEntry.html','reverse');

	}
	
	
}

function CommonReversalEntrySerach() {
	
	    var formAction=$('#CommonReversalEntry').attr('action');
		var url=formAction+'?search';
	
		$('#CommonReversalEntry').attr('action', url);

		$('#CommonReversalEntry').submit();
		
}

function PropertyReceiptDelete(element)
{
		
	$('#datatables').DataTable().destroy();
	return saveOrUpdateForm(element,
			"Property Receipt Deleted Successfully",
			'CommonReversalEntry.html','SearchReceiptByPropNo');
	
}

function CommonReversalEntryReset(element){
	
	$("#CommonReversalEntry").prop('action', '');
	$("#CommonReversalEntry").prop('action','CommonReversalEntry.html');
	
	$("#CommonReversalEntry").submit();
	
}
function CommonReversalEntryBack(element)
{
	    var formAction=$('#CommonReversalEntry').attr('action');
		var url=formAction+'?back';
	
		$('#CommonReversalEntry').attr('action', url);

		$('#CommonReversalEntry').submit();
	
}

function deletePropertyReceipt(element)
{
	
	return saveOrUpdateForm(element,
			"Your application for contract Agreement saved successfully!",
			'PropertyReceiptDeletion.html','deleteReceipt');

}
function yearLength(){
	
	var dateFields = $('.dateClass');
    dateFields.each(function () {
            var fieldValue = $(this).val();
            if (fieldValue.length > 10) {
                    $(this).val(fieldValue.substr(0, 10));
            }
    })
}