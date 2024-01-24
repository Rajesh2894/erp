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
				});




function serachPropertyDeletion() {
	
	
	
	var formAction	=	$('#propertyDemandNoticeDeletion').attr('action');
		var url=formAction+'?SearchNotice';
	
		$('#propertyDemandNoticeDeletion').attr('action', url);

		$('#propertyDemandNoticeDeletion').submit();
		
	
}

function deleteNotice(element)
{
		
	$('#datatables').DataTable().destroy();
	return saveOrUpdateForm(element,
			"Demand Notice generation  done successfully",
			'PropertyNoticeDeletion.html','DeleteNotice');

	
}


function resetNoticeDeleteForm(element){
	
	$("#propertyDemandNoticeDeletion").prop('action', '');
	$("#propertyDemandNoticeDeletion").prop('action','PropertyNoticeDeletion.html');
	
	$("#propertyDemandNoticeDeletion").submit();
	
	
}


function showErrorOnPage(errorList){
	var errMsg = '<ul>';
	var errMsg = '<button type="button" class="close" aria-label="Close" src="css/images/close.png" onclick="closeOutErrBox()"><span aria-hidden="true">&times;</span></button><ul>';
	$.each(errorList, function(index) {
		errMsg += '<li><i class="fa fa-exclamation-circle"></i> &nbsp;' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';
	$("#errorDiv").html(errMsg);
	$("#errorDiv").show();
	$("html, body").animate({ scrollTop: 0 }, "slow");
	return false;
}