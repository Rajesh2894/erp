

$( document ).ready(function() {


	$("#addMoreReasonDet").click(function () 
			{	
	
			if(checkValidnReasonDetails($("#reasonCountID").val()))
				{
				
					$("#reasonCountID").val(parseInt($("#reasonCountID").val())	+	1);	
					$('#reasonTableID tr:last').after(generateReasonTable($("#reasonCountID").val()));

				}		
			
			});
	
	$("#removeMoreRejDet").click(function () 
			{	
	
				var counter	=	$("#reasonCountID").val();
		
				if(counter > 0)
		 		{
					$("#reasonCurrentId"+counter).remove();
					
			  		counter	=	parseInt(counter)	-	1;
		
			  		$("#reasonCountID").val(counter);
				}
				else
					showErrormsgboxTitle("Atleast One" + $("#hoticeHearderID").html() + " needed"); 

			
			});
	
	/*$("#applicationId").change(function () 
			{		
				var theForm	=	'#frmMasterForm';
				
				var url	=	$(theForm).attr('action') + '?getAppDetails';					
				
				var calcValue = new Array();
				
				calcValue[0] = $(this).val();
				
				calcValue[1] = $("#noticeType").val();
					
				var data	=	'calValue='+calcValue;
				
				var returnData	=	__doAjaxRequest(url, 'post', data , false,'json');		

				alert(returnData);
			});*/
});


function saveForm(element) 
{
	var url	=	$("#frmMasterForm").attr('action');	
	
	
	return  saveOrUpdateForm(element, "save sucessfully",url, 'saveform');
	
 }


function checkValidnReasonDetails(count)
{
	
	return true;
}

function clearForm()
{
	var url	=	$("#frmMasterForm").attr('action');	
	
	window.open(url,'_self',false);

}


function generateReasonTable(count)
{
	
	var str	=	'<tr id="reasonCurrentId'+ count +'">'+			
					'<td>'+
						'<input type="text" />'+	
					'</td>	'+
			
					'<td>'+
						'<input id="taxAmt'+ count +'" name="entity.noticeReasons['+count+'].noticeReason"   type="text" maxlength="395" class="mandClassColor input2">'+	
					'</td> '+			 		
		 			
					'</tr>';

	return str;

}