function resetForm1(resetBtn) {
	
//	alert("ooo");
	cleareFile(resetBtn);
	if (resetBtn && resetBtn.form) {
		$('div[id*=file_list]').html('');
		$('.alert').hide();
		$('div').removeClass("has-success has-error");
		$('#frmCitizenContactUs').find('input:text,textarea').val('');
 		//resetBtn.form.reset();
	};
}

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
