
$( document ).ready(function() {
	
	$('input.mandClassColor').blur(function() {
		var input=$(this);
		
		if(input.val() == 0 || input.val() == null || input.val() == " " )
			{input.removeClass("valid").addClass("invalid");
			   
			}  
		else
		{input.removeClass("invalid").addClass("valid");
		}
	});
	
	$('select.mandClassColor').blur('select', function() {
		var select=$(this);
		if(select.val() == 0 || select.val() == null)
		{ select.removeClass("valid").addClass("invalid");
		}
		else
		{select.removeClass("invalid").addClass("valid");
		}

	});
	
	$('textarea.mandClassColor').blur('input', function() {
		var textarea=$(this);
		if(textarea.val() == 0 || textarea.val() == null)
		{ textarea.removeClass("valid").addClass("invalid");
		}
		else
		{textarea.removeClass("invalid").addClass("valid");
		}

	});
	
	
	$('input.cal').change(function() {
		var input=$(this);
		if(input.val() == 0 || input.val() == null || input.val() == "")
		{input.removeClass("valid").addClass("invalid");
		}
		else
		{input.removeClass("invalid").addClass("valid");
		}
	}); 
	
	$("input[value=Reset]").click(function(){
		   if ( $('.registration input.mandClassColor').is('.valid') ) {
			   $('.registration input.mandClassColor').removeClass("valid");
		   }
		    if ( $('.registration textarea.mandClassColor').is('.valid') ) {
			   $('.registration textarea.mandClassColor').removeClass("valid");
		   }
		    if ($('.registration select.mandClassColor').is('.valid')) {
			   $('.registration select.mandClassColor').removeClass("valid");
		   }
		   	if ($('.registration input.mandClassColor').is('.invalid') ) {
			   $('.registration input.mandClassColor').removeClass("invalid");
		   }
		   	if ( $('.registration textarea.mandClassColor').is('.invalid')) {
			   $('.registration textarea.mandClassColor').removeClass("invalid");
		  }
			if ( $('.registration select.mandClassColor').is('.invalid') ) {
			   $('.registration select.mandClassColor').removeClass("invalid");
		   }
	}); 

	$("input[value=Reset]").click(function(){
		   if ( $('.form input.mandClassColor').is('.valid') ) {
			   $('.form input.mandClassColor').removeClass("valid");
		   }
		    if ( $('.form textarea.mandClassColor').is('.valid') ) {
			   $('.form textarea.mandClassColor').removeClass("valid");
		   }
		    if ($('.form select.mandClassColor').is('.valid')) {
			   $('.form select.mandClassColor').removeClass("valid");
		   }
		   	if ($('.form input.mandClassColor').is('.invalid') ) {
			   $('.form input.mandClassColor').removeClass("invalid");
		   }
		   	if ( $('.form textarea.mandClassColor').is('.invalid')) {
			   $('.form textarea.mandClassColor').removeClass("invalid");
		  }
			if ( $('.form select.mandClassColor').is('.invalid') ) {
			   $('.form select.mandClassColor').removeClass("invalid");
		   }
	}); 

$("input[value=Clear]").click(function(){
		   if ( $('.form input.mandClassColor').is('.valid') ) {
			   $('.form input.mandClassColor').removeClass("valid");
		   }
		    if ( $('.form textarea.mandClassColor').is('.valid') ) {
			   $('.form textarea.mandClassColor').removeClass("valid");
		   }
		    if ($('.form select.mandClassColor').is('.valid')) {
			   $('.form select.mandClassColor').removeClass("valid");
		   }
		   	if ($('.form input.mandClassColor').is('.invalid') ) {
			   $('.form input.mandClassColor').removeClass("invalid");
		   }
		   	if ( $('.form textarea.mandClassColor').is('.invalid')) {
			   $('.form textarea.mandClassColor').removeClass("invalid");
		  }
			if ( $('.form select.mandClassColor').is('.invalid') ) {
			   $('.form select.mandClassColor').removeClass("invalid");
		   }
	}); 

	
$(".hasNumber1").keypress(function(event) { return isNumber(event)});

jQuery('.hasNumber').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
  
});
jQuery('.hasNumber1to999').keyup(function () { 
    
	var pattern = new RegExp('^([1-9]|[1-9][0-9]|[1-9][0-9][0-9])$');
	
	if(!pattern.test(this.value))
	{
		this.value='';
	}
});

jQuery('.hasNameClass').keyup(function () { 
    this.value = this.value.replace(/[!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~0-9]/g, '');
 
});
	
		
jQuery('.hasCharacter').keyup(function () { 
    this.value = this.value.replace(/[^a-z A-Z]/g,'');
   
});

jQuery('.hasCharacterwithdot').keyup(function () { 
	
    this.value = this.value.replace(/[^a-z A-Z ./]/g,'');
   
});

jQuery('.maxLength20').keyup(function () { 
	 $(this).attr('maxlength','20');
  
});

jQuery('.maxLength1950').keyup(function () { 
	
	 $(this).attr('maxlength','2000');
 
});

jQuery('.maxLength30').keyup(function () { 
	 $(this).attr('maxlength','60');
  
});

jQuery('.maxLength3').keyup(function () { 
	 $(this).attr('maxlength','3');
 
});

jQuery('.maxLength4').keyup(function () { 
	 $(this).attr('maxlength','4');

});

jQuery('.maxLength1980').keyup(function () { 
	 $(this).attr('maxlength','2000');
 
});

jQuery('.maxLength100').keyup(function () { 
	 $(this).attr('maxlength','100');
 
});
jQuery('.maxLength200').keyup(function () { 
	$(this).attr('maxlength','200');
	
});

jQuery('.maxLength10').keyup(function () { 
	$(this).attr('maxlength','10');
	
});

jQuery('.maxLength300').keyup(function () { 
	 $(this).attr('maxlength','300');
  
});


jQuery('.hasDecimal').keyup(function () { 
    this.value = this.value.replace(/[^0-9\.]/g,'');
});

jQuery('.hasCharacterWithPeriod').keyup(function () { 
    this.value = this.value.replace(/[^a-z A-Z\.]/g,'');
   
});
jQuery
('.hasPincode').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','6');
});

jQuery('.hasMobileNo').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','10');
});

jQuery('.hasAadharNo').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','12');
});


jQuery('.hasFaxNo').keyup(function () { 
    this.value = this.value.replace(/[^0-9]/g,'');
    $(this).attr('maxlength','12');
});

jQuery('.hasSpecialChara').keyup(function () { 
		
	if (this.value.match(/[^a-zA-Z ]/g )|| this.value.match(/[^\u0900-\u0954 ]/g) ){
		this.value = this.value.replace(/[^a-zA-Z\u0900-\u0954 ]/g, '');
	}   
});

$(".preventSpace").on("keypress", function(e) {
    if ((e.which === 32 && !this.value.length )|| e.which == 13 || e.which == 34 || e.which == 39)
        e.preventDefault();
});

	$('.hasAmount').keyup(function () {
	
		
		var regx	=	/^\d+(?:\.\d{0,2})?$/;
		//var regx	= /^[-+]?[0-9]*.[0-9]{2}$/;
	
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			$(this).val('');	
		}   
	});
	
	$('.has3Decimal').keyup(function () {
			
			var regx	=   /^\d+(\.\d{0,3})?$/;
		
			var amount	=	$(this).val();
		
			if(!regx.test(amount))
			{
				amount = amount.substring(0, amount.length-1);
				
				$(this).val(amount);	
			}   
		});
	
	$('.has2Decimal').keyup(function () {
		
		var regx	=   /^\d+(\.\d{0,2})?$/;
	
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		}   
	});
	
$('.has1Decimal').keyup(function () {
		
		var regx	=   /^\d+(\.\d{0,1})?$/;
	
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		}   
	});
	
	
	$('.alphaNumeric').keyup(function() {
		
		var regx	=   /^[ A-Za-z0-9_@./#&+-\s]*$/;
		
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		} 
	});
	
	$('.decimal42').keyup(function() {
		
		var regx	=   /^\d{0,4}(\.\d{0,2})?$/;
		
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		} 
	});
	
$('.decimal13').keyup(function() {
		
		var regx	=   /^\d{0,1}(\.\d{0,3})?$/;
		
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		} 
	});

$('.decimal33').keyup(function() {
	
	var regx	=   /^\d{0,3}(\.\d{0,3})?$/;
	
	var amount	=	$(this).val();

	if(!regx.test(amount))
	{
		amount = amount.substring(0, amount.length-1);
		
		$(this).val(amount);	
	} 
});

$('.decimal0_3').keyup(function() {
	
	var regx	=   /^[0](\.\d{0,3})?$/;
	
	var amount	=	$(this).val();
	
	if(!regx.test(amount))
	{
		amount = amount.substring(0, amount.length-1);
		
		$(this).val(amount);	
	} 
});

	
	$('.decimal22').keyup(function() {
		
		var regx	=   /^\d{0,2}(\.\d{0,2})?$/;
		
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		} 
	});
	
	$('.decimal82').keyup(function() {
		
		var regx	=   /^\d{0,8}(\.\d{0,2})?$/;
		
		var amount	=	$(this).val();
	
		if(!regx.test(amount))
		{
			amount = amount.substring(0, amount.length-1);
			
			$(this).val(amount);	
		} 
	});
	
	$('.charsRemaining').hide(); 
	$('textarea').focus(function(){
	      $(this).parent().find('.charsRemaining').show();       
	})
	$('textarea').blur(function(){

      $(this).parent().find('.charsRemaining').hide();       
});
	
/* Used for Indian currency Formatter client side */
$('.currencyFormat')
		.keyup(
				function() {
					var value = $(this).val().replace(/,/g, "");
					value = $(this).val().replace(/[^0-9\.]+/g, '')
					value = value.toString();
					var afterPoint = '';
					if (value.indexOf('.') > 0) {
						afterPoint = value.substring(value
								.indexOf('.'), value.length);
						afterPoint = afterPoint.substring(0, 3);
					}
					value = Math.floor(value);
					value = value.toString();
					var lastThree = value
							.substring(value.length - 3);
					var otherNumbers = value.substring(0,
							value.length - 3);
					if (otherNumbers != '')
						lastThree = ',' + lastThree;
					var indianRupee = otherNumbers.replace(
							/\B(?=(\d{2})+(?!\d))/g, ",")
							+ lastThree + afterPoint;
					$(this).val(indianRupee);
				});	
});


function isNumber(evt) 
{	
	var iKeyCode = (evt.which) ? evt.which : evt.keyCode;
    
    if (iKeyCode != 46 && iKeyCode > 31 && (iKeyCode < 48 || iKeyCode > 57) && iKeyCode != 45)
    	
       return false;

    return true;
}  

function validateAadhaar(aadhaar_number)
{
	debugger;
	/*var aadhaar_number = $('#'+aadhaarNumberid).val();*/
    // Regex to check valid
    // aadhaar_number  
    let regex = new RegExp(/^[2-9]{1}[0-9]{3}\-[0-9]{4}\-[0-9]{4}$/);
 
    // if aadhaar_number 
    // is empty return false
    if (aadhaar_number == null) {
        return false;
    }
 
    // Return true if the aadhaar_number
    // matched the ReGex
    if (regex.test(aadhaar_number) == true) {
        return true;
    }
    else {
        return false;
    }
}
 

/*function validateAadhaar(aadhaarNumberid){
		debugger;
		var aadhaarNumber = $('#'+aadhaarNumberid).val();
	  if (aadhaarNumber.length !== 12) {
	    return false;
	  }
	
	  if (aadhaarNumber[0] < '2' || aadhaarNumber[0] > '9') {
	    return false;
	  }
	  
	  if (aadhaarNumber[4] !== ' ' || aadhaarNumber[8] !== ' ') {
	    return false;
	  }
	  
	  return true;
}
*/