$(document).ready(function() {
	
	var value= $("#imgMode").val();
	var hyperLink='';  
	 hyperLink+="<img src='./"+value+"'/>";
	 
	 $('#propImages').html(hyperLink);

	
	
	});

function backPage() {

	window.location.href = getLocalMessage("AdminHome.html");
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



function displayImage(images){
	hyperLink='';  
	if(images != null){
		  $.each( images, function( key, value ) {
			  hyperLink+="<a href='./"+value+"' class='fancybox'><img src='./"+value+"' class='img-thumbnail margin-top-10'/></a>";
			});
	  }
	  $('#propImages').html(hyperLink);
}