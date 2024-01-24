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
					
				});


function  viewRtiForm(applicationId)
{
	
	    var requestData = "applicationId="+applicationId;
		var url = "SecondApealRtiApplication.html?view";
		var ajaxResponse = __doAjaxRequest(url, 'post',requestData , false);

		var divName = formDivName;
		$('.content').removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		//prepareTags();

}