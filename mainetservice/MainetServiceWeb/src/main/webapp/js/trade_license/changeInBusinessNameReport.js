$(document).ready(function() {
	
	var value= $("#imgMode").val();
	var hyperLink='';  
	 hyperLink+="<img src='./"+value+"'/>";
	 
	 $('#propImages').html(hyperLink);
	 
});