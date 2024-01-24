(function($) {
	$(document).ready(function() {
		var date = new Date();
		date.setTime(date.getTime() + (10 * 1000));
		var expires = "expires=" + date.toGMTString();
		document.cookie = "ScriptOnOff" + "=" + 'ON' + "; " + expires;

	});
})(jQuery);
function setCookie(cname, cvalue, exdays) {
	var d = new Date();
	d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
	var expires = "expires=" + d.toGMTString();
	document.cookie = cname + "=" + cvalue + "; " + expires;
}
function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
}
function setcontrast(arg) {
	/*
	 * var rates = document.getElementsByName('stqc_contrastscheme'); var
	 * ratevalue; for(var i = 0; i < rates.length; i++){ if(rates[i].checked){
	 * ratevalue = rates[i].value;
	 */
	/* alert(ratevalue); */
	/* alert(arg+"arg"+window.location.href) */
	if (arg == "O") {
		/* alert(arg+"O") */
		/* localStorage.setItem('accessibility','Y'); */
		var d = new Date();
		d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
		var expires = "expires=" + d.toGMTString();
		document.cookie = "accessibilityCol" + "=" + 'O' + "; " + expires;
		var user = getCookie("accessibility");
		/* alert("user"+user); */
	}
	if (arg == "B") {
		/* alert(arg+"B") */
		/* localStorage.setItem('accessibility','N') */
		var d = new Date();
		d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
		var expires = "expires=" + d.toGMTString();
		document.cookie = "accessibilityCol" + "=" + 'B' + "; " + expires;
	}
	if (arg == "G") {
		/* alert(arg+"G") */
		/* localStorage.setItem('accessibility','N') */
		var d = new Date();
		d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
		var expires = "expires=" + d.toGMTString();
		document.cookie = "accessibilityCol" + "=" + 'G' + "; " + expires;
	}
	location.reload(window.location.href);
	/* location.reload("CitizenHome.html"); */
	/* window.open("CitizenHome.html") */
	/*
	 * } }
	 */
}

function changeLanguage(url) {
	var array = document.URL.split('/');
	array = array[array.length - 1];
	url = url + '&url=' + array;
	window.location.href = url;
}
$(document).ready(function($) {
	$(".pull-left ul li a,.pull-right ul li a").focus(function() {
		$(this).parent().addClass('active');
	});
	$(".pull-left ul li a,.pull-right ul li a").focusout(function() {
		$(this).parent().removeClass('active');
	});

	$('#incfont').click(function() {
		curSize = parseInt($('#text-resize').css('font-size')) + 1;
		if (curSize <= 19)
			$('#text-resize').css('font-size', curSize);
	});
	$('#decfont').click(function() {
		curSize = parseInt($('#text-resize').css('font-size')) - 1;
		if (curSize >= 10)
			$('#text-resize').css('font-size', curSize);
	});
	$('#norfont').click(function() {
		curSize1 = parseInt($('#text-resize').css('font-size', ''));
		$('#text-resize').css('font-size', curSize1);
	});
});
