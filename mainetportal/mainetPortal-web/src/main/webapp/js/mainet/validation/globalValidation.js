/**
 * ritesh.patil
 * 
 * Global client side validation and custom validation
 * 
 */
/* By default, hidden input are ignored(eg. accordin , chosen etc.) from validate plugin. 
 * Make sure it is run before document is loaded. */
/*$.validator.setDefaults({ errorElement: 'div'});*/

$.validator.setDefaults({ ignore: ":hidden:not(.chosen-select-no-results)" });
$.validator.setDefaults({ ignore: [] });
$.validator.setDefaults({
                   errorPlacement: function (error, element) {
					  if (element.hasClass("chosen-select-no-results")) {
				          var id = element.attr('id');
				          error.insertAfter("#" + id + "_chosen");
				      }
					 else if (element.is(":checkbox")) {
						 alert(element.parent().find('span').attr('class'));
						    error.insertAfter(element.parent().find('span'));
						}
					 else {
				          error.insertAfter(element);
				      }
			}
});
$.validator.setDefaults({
	   onkeyup: function(element) {
       this.element(element);
       console.log('onkeyup fired');
 },
onfocusout: function(element) {
       this.element(element);
       console.log('onfocusout fired');
}});

function checkErrorForChosen(obj){
	 var ID = $(obj).attr("id");
	    if (!$(obj).valid()) {
	        $("#" + ID + "_chosen").addClass("input-validation-error");
	    }
	    else {
	        $("#" + ID + "_chosen").removeClass("input-validation-error");
	    }
}