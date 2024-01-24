function chkLinkType() {
	// alert(document.getElementById("linkRadioButton1").checked)
	/* document.getElementById("exLink").value = ''; */
	if (document.getElementById("linkRadioButton2").checked) {
		document.getElementById("exLink").value = 'http://';
	} else {
		document.getElementById("exLink").value = '';
	}
}
$( document ).ready(function() {

	jQuery('.hasNumber').keyup(function () { 
	    this.value = this.value.replace(/[^0-9]/g,'');
	  
	});
});