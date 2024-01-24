
$( document ).ready(function() {
	if ($("#checkListFlag").val() == 'Y' || $("#checkListFlag").val() == '' || $("#checkListFlag").val() == undefined) {
		$("#submitOtp").show();
		$("#otpdetails").show();
		$("#divSubmit").hide();
	}if ($("#checkListFlag").val() == 'N'){
		$("#submitOtp").hide();
		$("#otpdetails").hide();
		$("#divSubmit").show();
	}
	
	$("#consumerDetails").hide();
	$("#back").hide();
	$('#noDuesCertificate').validate({
		   onkeyup: function(element) {
	       this.element(element);
	       console.log('onkeyup fired');
	 },
	       onfocusout: function(element) {
	       this.element(element);
	       console.log('onfocusout fired');
	}});
	
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
$("#finList").hide();
	    $("#chargesDiv").hide();
	    //$('#chekListChargeId').hide();
	   // $("#divSubmit").hide();
	    $("#due").hide();
		$("#finList").hide();
		
	    $("#checkListDetails").hide();	
		if($('#free').val()=='F')
			{
			 $("#payment").hide();
			}
		else
			{
			 $("#payment").show();
			}
		 if($('#povertyLineId').val()=='N')
			 {
		        $("#bpldiv").hide();
			 }
		  if($('#povertyLineId').val()=='Y')
		   {
			   $("#bpldiv").show();
		   }
		  else
		   {
			 $("#bpldiv").hide();
		   }

	 $("#povertyLineId").change(function(e) {
	
			if ($("#povertyLineId").val() == 'Y') {
			
				$("#bpldiv").show();
				
			}
			else
				{
				$("#bpldiv").hide();
				}
			
		    });
	 
});
function saveNoDuesCertificateForm(element) {
	
	var errorList = [];
	//errorList = validateFormData(errorList);
	if (errorList.length == 0) {
	  if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'N' || $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'P')
	  	  {
		  
		      var status = saveOrUpdateForm(element,"Your application for No Dues Certificate saved successfully!", 'NoDuesCertificateController.html?PrintReport', 'save');
		      //noDuePrintCerticate(status);

	  	  }
                else if($("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val() == 'Y')
	      {
	
            	 return saveOrUpdateForm(element,"Your application for No Dues Certificate saved successfully!", 'NoDuesCertificateController.html?redirectToPay', 'save');
	     } 
          else
           {
            	  return saveOrUpdateForm(element,"Your application for No Dues Certificate saved successfully!", 'CitizenHome.html', 'save');
           }
	} else {
		$("#divSubmit").show();
		displayErrors(errorList);
	}
}
 
function validateFormData(errorList) {
	 
	var applicantTitle= $.trim($('#applicantTitle').val());
	var firstName= $.trim($('#firstName').val());
	var lastName= $.trim($('#lastName').val());
	var applicantMobileNo= $.trim($('#mobileNo').val());
	var villTownCity= $.trim($('#villTownCity').val());
	var blockName= $.trim($('#blockName').val());	
	var applicantPinCode= $.trim($('#pinCode').val());

	var conNum= $.trim($('#consumerNo').val());
	var finYear= $.trim($('#onYearSelect').val());
	var cooNoname= $.trim($('#consumerName').val()); 
	var isBPL= $.trim($('#povertyLineId').val()); 
	var isBPLNO= $.trim($('#bplNo').val());
	var free =	$.trim($('#free').val()); 
	var consumerAddress= $.trim($('#consumerAddress').val());
	var payMode= $("input:radio[name='offlineDTO.onlineOfflineCheck']").filter(":checked").val();

	 if(applicantTitle =="" || applicantTitle =='0' || applicantTitle == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	 }
	 if(firstName =="" || firstName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	 }
	 if(lastName == "" || lastName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	 }
	 if(applicantMobileNo == "" || applicantMobileNo == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	 }
	 if(villTownCity == "" || villTownCity == undefined){
		 errorList.push(getLocalMessage('water.validation.ApplicantcityVill'));
	 }
	
	 if(applicantPinCode == "" || applicantPinCode == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	 }
	
	 if(conNum == "" || conNum == undefined){
		 errorList.push(getLocalMessage('water.validation.connectionno'));
	 }
	 /*if(finYear == "" || finYear =='0' || finYear == undefined){
		 errorList.push(getLocalMessage('water.validation.finYear'));
	 }*/
	 if(cooNoname == "" || cooNoname == undefined){
		 errorList.push(getLocalMessage('water.validation.ownerfirstname'));
	 }
	 	
	  if(free !='F' )
		  {
		    if(payMode == "" || payMode == undefined){
				 errorList.push(getLocalMessage('water.validation.paymode'));
			 }
		  }
	 
	 if(isBPL == "0" || isBPL == undefined){
		  
		 errorList.push(getLocalMessage('water.validation.isabovepovertyline'));
	 }
	 if(isBPL == "Y" && isBPLNO =='' ){			  
			
		errorList.push(getLocalMessage('water.validation.bplnocantempty'));
	  }
		 	 
	return errorList;	

}
function getNODuesChecklistAndCharges(element)
{
	
	    var errorList = [];
	    $("#applicantTitle").prop("disabled",false);
		$("#firstName").prop("disabled",false);
		$("#middleName").prop("disabled",false);
		$("#lastName").prop("disabled",false);
		$("#mobileNo").prop("disabled",false);
		$("#emailId").prop("disabled",false);
		$("#gender").prop("disabled",false);
		var isBPL= $.trim($('#povertyLineId').val()); 
		var isBPLNO= $.trim($('#bplNo').val());
		var  fin =$('select#onYearSelect').val();   
		var noOfCopies =  $("#noOfCopies").val();
		var userOtp =  $("#userOtp").val();
		/*if(fin!=null)
			{
	    if(fin[0] =="-1" || fin == null)	    	
		    {
		    	 errorList.push(getLocalMessage('water.validation.finYear'));		    	
		    }
			}
		else
			{
			 errorList.push(getLocalMessage('water.validation.finYear'));	
			}*/
		if(noOfCopies =='' || noOfCopies =='undefined')	    	
		    {
		    	 errorList.push(getLocalMessage('water.validation.noOfCopies'));		    	
		    }
		
		if(userOtp =='' || userOtp =='undefined')	    	
	    {
	    	 errorList.push(getLocalMessage('water.validate.otp'));		    	
	    }
			
		 if(isBPL == "0" || isBPL == undefined){
			  
			 errorList.push(getLocalMessage('water.validation.isabovepovertyline'));
		 }
		 if(isBPL == "Y" && isBPLNO =='' ){			  				
			errorList.push(getLocalMessage('water.validation.bplnocantempty'));
		  }
		
	if (errorList.length == 0) { 
		var	formName =	findClosestElementId(element, 'form');
		var theForm	=	'#'+formName;
		var requestData = {};
		requestData = __serializeForm(theForm);
	    var URL = 'NoDuesCertificateController.html?getCharges';
        var returnData =__doAjaxRequest(URL,'POST',requestData, false);
        var divName = '#validationDiv';
		$(divName).removeClass('ajaxloader');
		$(divName).html(returnData);
		$(divName).show();
		var free=$('#free').val();
		
		if(free=='F'){
			$('#waterformappdetails').show();
			//$('#chekListChargeId').hide();
			$("#chargesDiv").show();			
			$("#checkListDetails").hide();
			$('.required-control').next().children().addClass('mandColorClass');
			}
		else
			{
			$("#chargesDiv").show();
			$("#payment").show();
			//$("#checkListDetails").show();
			$('#chekListChargeId').hide();										
			}
		
		$("#due").show();
		$("#checkListDetails").show();
		$("#finList").show();
		//$("#divSubmit").show();
		$('#consumerDetails').show();
		$("#noOfCopies").prop("readonly", true);
		$("#consumerNo").prop("readonly", true);
		$("#viewdues").prop('disabled', true);
		$("#reset").prop('disabled', true);
		
		
		
       } 
	else {
		
		displayErrors(errorList);
	}
}
 
  function resetDueWaterForm()
  {
	  $('input[type=text]').val('');  
  }
  function searchConnection(element)
  {
	  $('.msg-error').hide();
  	    var errorList=[];	
  	  var connectionNo =	$("#consumerNo").val();
  	 //var errorList = validateApplicationForm(errorList);
  	var applicantTitle= $.trim($('#applicantTitle').val());
	var firstName= $.trim($('#firstName').val());
	var lastName= $.trim($('#lastName').val());
	var mobileNo= $.trim($('#mobileNo').val());
	var areaName= $.trim($('#areaName').val());
	var pinCode= $.trim($('#pinCode').val());
	var povertyLineId= $.trim($('#povertyLineId').val());
	
	
	if(applicantTitle =="" || applicantTitle =='0' || applicantTitle == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	 }
	 if(firstName =="" || firstName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	 }
	 if(lastName == "" || lastName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	 }
	 if(mobileNo == "" || mobileNo == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	 }
	 if(areaName == "" || areaName == undefined){
		 errorList.push(getLocalMessage('water.validation.ApplicantcityVill'));
	 }
	
	 if(pinCode == "" || pinCode == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	 }
	 
	  if(povertyLineId == "" || applicantTitle =='0' || povertyLineId == undefined){
	 errorList.push(getLocalMessage('Please Select Bpl'));
 }
  		
  		if(connectionNo =='' || connectionNo ==undefined)	    	
  		    {
  		    	errorList.push('Please Enter Connection No'); 		    	
  		    }	  	
  	 if (errorList.length==0) 
  	 {
  		var theForm	=	'#noDuesCertificate';
  		var requestData = __serializeForm(theForm);	   
  		var URL = 'NoDuesCertificateController.html?' + 'getConnectionDetail';

  		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
  		
  		if(returnData == "" || returnData == null ||returnData.errorMsg=='exception')
  		{									
  			var URL = $("form").attr('action');	            
  	        _openPopUpForm(URL,"showErrorPage");						
  		}
  		else if(returnData.error==true)
  		{			
  			errorList
  			.push(getLocalMessage('water.bill.search'));
  		    displayErrors(errorList);
  		  //$("#due").hide(); 
			 //$("#finList").hide();
			 //$('#chekListChargeId').hide();
  		}
  		else if (returnData.billGenerated) {
  			
  			errorList.push(getLocalMessage('water.nodues.bill.generate ')
  					+ " " + connectionNo + " "
  					+ getLocalMessage('water.nodues.eligible'));
  			displayErrors(errorList);
  		    } 
  		else
  		 {					
  			$("#consumerName").val(returnData.consumerName);
  			$("#consumerAddress").val(returnData.consumerAddress);
  			$("#waterDues").val(returnData.waterDues);
  			$("#duesAmount").val(returnData.duesAmount);
  			
  			
  			//if($("#duesAmount").val()>0){
  			if(returnData.dues){			
  				errorList.push(getLocalMessage('water.nodues.exist') + " "
  						+ connectionNo+ " "
  						+ getLocalMessage('water.nodues.eligible'));
    		  displayErrors(errorList);
    		  $("#consumerDetails").hide();
    		  $("#back").show();
  			}
  			
  			else{
  				 $("#consumerDetails").show();
  				$("#back").hide();
  				$("#submitOtp").show();
  				$("#consumerNo").prop("readonly", true);
  			}
  			
  			  $("#due").show(); 
  			  $("#finList").show();
  			
  		  }		
  		}
  		 else 
  		 {				
  			 displayErrors(errorList);			
  		 }
  		 
  }
function displayErrors(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>';

	errMsg += '<ul>';

	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});

	errMsg += '</ul>';

	$('.msg-error').html(errMsg);
	$(".msg-error").show();
	return false;
}

function closeOutErrBox(){
	$('.msg-error').hide();
	
}

function generateOtp(element){
	
	var theForm	=	'#noDuesCertificate';
		var requestData = __serializeForm(theForm);	   
		var URL = 'NoDuesCertificateController.html?' + 'generateOtp';

		var returnData = __doAjaxRequest(URL, 'POST', requestData, false);
}


function validateApplicationForm(errorList) {
	 
	var applicantTitle= $.trim($('#applicantTitle').val());
	var firstName= $.trim($('#firstName').val());
	var lastName= $.trim($('#lastName').val());
	var mobileNo= $.trim($('#mobileNo').val());
	var areaName= $.trim($('#areaName').val());
	var pinCode= $.trim($('#pinCode').val());
	var povertyLineId= $.trim($('#povertyLineId').val());
	
	
	if(applicantTitle =="" || applicantTitle =='0' || applicantTitle == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantNameTitle'));
	 }
	 if(firstName =="" || firstName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantFirstName'));
	 }
	 if(lastName == "" || lastName == undefined ){
		 errorList.push(getLocalMessage('water.validation.ApplicantLastName'));
	 }
	 if(mobileNo == "" || mobileNo == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantMobileNo'));
	 }
	 if(areaName == "" || areaName == undefined){
		 errorList.push(getLocalMessage('water.validation.ApplicantcityVill'));
	 }
	
	 if(pinCode == "" || pinCode == undefined){
		 errorList.push(getLocalMessage('water.validation.applicantPinCode'));
	 }
	 
	 return errorList;
}


function resetConnection(element){
	$("#consumerNo").prop("readonly", false);
	$("#consumerNo").val('');
	$("#noOfCopies").prop("readonly", false);
	$("#noOfCopies").val('');
	$("#consumerDetails").hide();
}


/*function printCertificate(element){
	
	var theForm	=	'#noDuesCertificate';
	requestData = __serializeForm(theForm);
    var URL = 'NoDuesCertificateController.html?noDueCertificatePrint';
    var returnData =__doAjaxRequest(URL,'POST',requestData, false);
}*/

/*function noDuePrintCerticate(status) {
	
    if (!status) {
		var URL = 'NoDuesCertificateController.html?noDueCertificatePrint';
		var returnData = __doAjaxRequest(URL, 'POST', {},
				false);
		
		
		var title='No Dues Certificate';
		var printWindow = window.open('', '_blank');
		printWindow.document.write('<html><head><title>' + title + '</title>');
		printWindow.document
				.write('<link href="assets/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />')
		printWindow.document
				.write('<link href="assets/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet" />')
		printWindow.document
				.write('<link href="assets/css/style.css" rel="stylesheet" type="text/css" />')
		printWindow.document
				.write('<link href="assets/css/style-responsive.css" rel="stylesheet" type="text/css" />')
		printWindow.document
				.write('<script src="assets/libs/jquery/jquery.min.js"></script>')
		printWindow.document
				.write('<script type="text/javascript" src="assets/libs/excel-export/excel-export.js"></script>')
		printWindow.document
				.write('<script>$(window).load(function() {$(".table-pagination, .remove-btn, .paging-nav, tfoot").remove(); $(".table thead tr th").removeClass("tablesorter-headerDesc tablesorter-headerAsc tablesorter-header"); $(".table tr").removeAttr("style");});</script>')
		printWindow.document.write('</head><body style="background:#fff;">');
		printWindow.document
				.write('<div style="position:fixed; width:100%; bottom:0px; z-index:1111;"><div class="text-center"><button onclick="window.print();" class="btn btn-success hidden-print" type="button"><i class="fa fa-print" aria-hidden="true"></i> Print</button>  <button onClick="window.close();" type="button" class="btn btn-blue-2 hidden-print">Close</button></div></div>')
		printWindow.document.write(returnData);
		printWindow.document.write('</body></html>');
		printWindow.document.close();
		
	}
}*/