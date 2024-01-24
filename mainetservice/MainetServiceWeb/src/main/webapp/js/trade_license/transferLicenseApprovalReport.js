$(document).ready(function() {
	
	var value= $("#imgMode").val();
	var hyperLink='';  
	 hyperLink+="<img src='./"+value+"'/>";
	 
	 $('#propImages').html(hyperLink);

	$("#resetform").on("click", function(){ 
		  window.location.reload("#tradeLicensePrint")
		});
	
	});

function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
}

function viewLicense(element) 
{
	var errorList = [];
	var applicationId = $("#applicationId").val();
	
	if (applicationId == "") {
		errorList.push("selelct appln no");
	}
	if (errorList.length > 0) {
		$("#errorDiv").show();
		displayErrorsOnPage(errorList);	
	} 
	else {
	
	var divName = '.content-page';
	var actionParam = {
			'applicationId' : applicationId
		}
	var url = "TradeLicenseReportFormat.html?viewLicense";
	var ajaxResponse = __doAjaxRequest(url, 'POST', actionParam, false, 'html');
	$(divName).removeClass('ajaxloader');
	$(divName).html(ajaxResponse);
	prepareTags();
	}

	 
}

function printdiv(printpage) {
	
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}

function resetLicenseForm()
{
	
	$('input[type=text]').val('');  
	$(".alert-danger").hide();
	$("#tradeLicensePrint").validate().resetForm();
}

function displayImage(images){
	hyperLink='';  
	if(images != null){
		  $.each( images, function( key, value ) {
			  hyperLink+="<a href='./"+value+"' class='fancybox'><img src='./"+value+"' class='img-thumbnail margin-top-10'/></a>";
			});
	  }
	  $('#propImages').html(hyperLink);
}