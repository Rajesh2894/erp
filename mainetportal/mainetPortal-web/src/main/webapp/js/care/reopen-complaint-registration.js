$(document).ready(function(){
	
	$(".error-div").hide();
	
$('.showRequestDetailsLink').click(function(event) {
	 //$("#actionHistory tr").remove();
	 var documentId = $(this).attr('id');
	 $("#applicationId").val(documentId);
     var getActionHistoryByDocumentId="grievance.html?getActionHistoryByApplicationId";
	 var requestData = 'applicationId='+documentId;	 
	 var response = __doAjaxRequest(getActionHistoryByDocumentId,'post',requestData,false,'html');
	 var obj = jQuery.parseJSON(response);
	 if(!$.isEmptyObject(obj)){
		 //var trHTML = '<tr><th>Sr. No.</th><th>Date & Time</th><th>Action</th><th>Employee Name</th><th>Email</th><th>Designation</th><th>Remarks</th><th>Attachements</th></tr>';
		 $('#actionHistory tbody').remove();

			$('#actionHistory').append("<tbody>...</tbody>");
		  $.each(obj, function (i, item) {
			  	i=i+1;
				var actor = (item.empName == null)?"":item.empName;
				var actorEmail = (item.empEmail == null)?"":item.empEmail;
				var actorGrp = (item.empGroupDescEng == null)?"Citizen":item.empGroupDescEng;
				var attachements = "<ul>";
				if(item.attachementUrls){
					
					for(j=0 ;j<item.attachementUrls.length;j++){
						var link = "<li><a href=\"javascript:void(0);\" onclick=\"downloadFile('" +item.attachementUrls[j].replace(/\\/g, "\\\\")+ "','grievance.html?Download')\"><i class=\"fa fa-download\"></i> "+item.attachementUrls[j].split("\\").pop()+"</a></li>";
						attachements += link;
					}
					attachements += "</ul>"
				}
				
				/*trHTML += '<tr><td>' + i + 
				  '</td><td>' + formatDate(new Date(item.dateOfAction)) +
				  '</td><td>' + item.decision +
				  '</td><td>' + actor +
				  '</td><td>' + actorEmail +
				  '</td><td>' + actorGrp +
				  '</td><td>' +item.comments  +
				  '</td><td>' +attachements  +
				  '</td></tr>';*/
			
				$('#actionHistory tbody').append('<tr><td>' + i + 
						  '</td><td>' + formatDate(new Date(item.dateOfAction)) +
						  '</td><td>' + item.decision +
						  '</td><td>' + actor +
						  '</td><td>' + actorEmail +
						  '</td><td>' + actorGrp +
						  '</td><td>' +item.comments  +
						  '</td><td>' +attachements  +
						  '</td></tr>');
				
			});
		  //$('#actionHistory').append(trHTML);
		  
		 let complaintNo = $(this).attr('data-complaint');
		 $('#complaintNo').html(complaintNo);
		 $('#reopenListHistId').hide();
		 $('#collapseExample').show();
		
		 
 }
	 event.preventDefault();
});



$('#searchGrievance').click(function(event) {
	
	
	/*if($('#TokenNumber').val()){
	 var url = 'grievance.html?searchGrievanceRaisedByRegisteredCitizen&searchString='+$('#TokenNumber').val();
		var errorList = [];
	 
		var url = 'grievance.html?searchGrievanceRaisedByRegisteredCitizen';
		var requestData = 'searchString=' + $('#TokenNumber').val();
		var response = __doAjaxRequest(url, 'post', requestData, false, 'html');
		if (response == '') {
			errorList.push(getLocalMessage('care.cant.reopen.pending.invalid'));
		} else {
			$('.content').html('');
			$('.content').html(response);
		}
	
		
	 if (errorList.length > 0) {
			displayErrorsOnPage(errorList);
			$('html,body').animate({
				scrollTop : 0
			}, 'slow');
		}
	 //openRelatedForm(url,event);
	}*/
	//D#112758
	$(".alert").hide();
	var searchString = $('#TokenNumber').val();
	searchString = (searchString== null)?$('#TokenNumber').val():searchString;
	var errorList=[];
	if(searchString == undefined || searchString == null || searchString == ""){
		errorList.push(getLocalMessage('care.token.empty'));	
	}
	if(errorList.length>0){
		displayErrorsOnPage(errorList);
	}else{
		var divName = '.content-page';
		var requestData = 'searchString=' + $('#TokenNumber').val();
		var ajaxResponse = doAjaxLoading('grievance.html?searchGrievanceRaisedByRegisteredCitizen', requestData, 'html',
				divName);
		$(divName).removeClass('ajaxloader');
		$(divName).html(ajaxResponse);
		//prepareTags();
	}
});



$('.showFeedbackLink').click(function(event) {
     var feedback="feedback.html";	
	 var response = __doAjaxRequest(feedback,'post',null,false,'html');
	 console.log(response);
	 
	 event.preventDefault();
});

$('#btnCancel').click(function(event) {
   $('#collapseExample').hide();
   $('#YourReply').val('');
   $(".error-div").hide();
   $('#reopenListHistId').show();
});



});

function resetReopenForm(resetBtn) {
    cleareFile(resetBtn);
    $('#reopenCommentCount').html(1000);
    if (resetBtn && resetBtn.form) {
    	$('[id*=file_list]').html('');
    	$('.error-div').hide();
		resetBtn.form.reset();
    };
}


function reopenComplaint(obj){
	var errorList=[];
	var remark = $('#YourReply').val();
	var reopeningReason = $('#reopeningReason').val(); 
	if(remark != undefined && remark != null)
		remark = remark.trim();
	if(remark == undefined || remark == null || remark == ""){
		errorList.push(getLocalMessage('care.validation.error.remark'));	
	}
	if(reopeningReason != undefined && reopeningReason != null)
		reopeningReason = reopeningReason.trim();
	if(reopeningReason == undefined || reopeningReason == null || reopeningReason == "0"){
		errorList.push(getLocalMessage('care.validation.error.reopeningReason'));	
	}
	if(errorList.length>0){
		displayErrorsOnPage(errorList);
	}
	if(errorList.length == 0){
		return saveOrUpdateForm(obj,"", "grievance.html", 'saveReopenDetails');
	}else{
		displayErrorsOnPage(errorList);
	}

}

function closeOutErrBox (){
	$(".error-div").hide();
}

function displayErrorsOnPage(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	errMsg += '<ul>';
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('.error-div').html(errMsg);
	$(".error-div").show();
	return false;
}

$(document).ready(function() {
	  $(".showRequestDetailsLink").on("click", function( e ) {
	    e.preventDefault();
	    $("body, html").animate({ 
	      scrollTop: $( $(this).attr('href') ).offset().top 
	    }, 600);
	  });
});

$(document).ready(function() {
	  $("#btnCancel").on("click", function( e ) {
	    e.preventDefault();
	    $("body, html").animate({ 
	      scrollTop: $( $(this).attr('href') ).offset().top 
	    }, 600);

	  });
	});


function formatDate(date){
	 var day = date.getDate() + "";
	 var month = (date.getMonth() + 1) + "";
	 var year = date.getFullYear() + "";
	 var hour = date.getHours() + "";
	 var minutes = date.getMinutes() + "";
	 var ampm = hour >= 12 ? 'PM' : 'AM';

	 day = checkZero(day);
	 month = checkZero(month);
	 year = checkZero(year);
	 hour = checkZero(hour);
	 mintues = checkZero(minutes);
	 ampm = checkZero(ampm);

	 var a = day + "/" + month + "/" + year + " " + hour + ":" + minutes + " " + ampm;
	 return a;
}

function checkZero(data){
  if(data.length == 1){
    data = "0" + data;
  }
  return data;
}