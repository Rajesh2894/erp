$(document ).ready(function() {
	$('.fix_navigation a').on('click', function(e) {
	  e.preventDefault();
	  $link = $(this).attr('href');
	  
	  $('html, body').animate({
	    scrollTop: $($link).offset().top - 10
	  }, 500 );
	});
});	