$(document).ready(
		function () {
			
	
});
function saveContactForm(element)
{
var cls5 = getLocalMessage('eip.admin.contactUs.save');
	/*if($('#phoneno').val()==0 || $('#Fname').val()==" " ||$('#Lname').val()=="" ||$('#DescQuery').val()=="")
	{

	showErrormsgboxTitle("Please Enter All fields:");
	return false;
	}*/
	
return saveOrUpdateForm(element,cls5,'CitizenContactUs.html','saveform');
		
}
