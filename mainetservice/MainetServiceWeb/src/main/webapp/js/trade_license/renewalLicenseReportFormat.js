
$(document).ready(function() {
	
	var value= $("#imgMode").val();
	var hyperLink='';  
	 hyperLink+="<img src='./"+value+"'/>";
	 
	 $('#propImages').html(hyperLink);

	$("#resetform").on("click", function(){ 
		  window.location.reload("#tradeLicensePrint")
		});
	
	});

function printReport(printpage) {
	
	var headstr = "<html><head><title></title></head><body>";
	var footstr = "</body>";
	var newstr = document.all.item(printpage).innerHTML;
	var oldstr = document.body.innerHTML;
	document.body.innerHTML = headstr + newstr + footstr;
	window.print();
	document.body.innerHTML = oldstr;
	return false;
}
