var w;
var h;
var dw;
var dh;

var changeptype = function(){
    w = $(window).width();
    h = $(window).height();
    dw = $(document).width();
    dh = $(document).height();

    if(jQuery.browser.mobile === true){
      	$("body").addClass("mobile").removeClass("fixed-left");
    }
    if(!$("#wrapper").hasClass("forced")){
    if(w > 990){
   	$("body").removeClass("smallscreen").addClass("widescreen");
   	$("#wrapper").removeClass("enlarged");
	}else{
		$("body").removeClass("widescreen").addClass("smallscreen");
	    	$("#wrapper").addClass("enlarged");
	    	$(".left ul").removeAttr("style");
	    }
	    if($("#wrapper").hasClass("enlarged") && $("body").hasClass("fixed-left")){
	    	$("body").removeClass("fixed-left").addClass("fixed-left-void");
	    }else if(!$("#wrapper").hasClass("enlarged") && $("body").hasClass("fixed-left-void")){
	    	$("body").removeClass("fixed-left-void").addClass("fixed-left");
	    }

	}
	toggle_slimscroll(".slimscrollleft");
}	

$(document).ready(function(){	
	
	resizeChosen();
 	jQuery(window).on('resize', resizeChosen);
 	function resizeChosen() {
	$(".chosen-container").each(function() {$(this).attr('style', 'width: 100%');});          
}
	
	$('input[type=reset]').on('click', function () {
			$(".form-horizontal div").removeClass("has-error");
			$(".form-horizontal div").removeClass("has-success");
  			$(".table-responsive .table tbody td").removeClass("has-error");
			$(".table-responsive .table tbody td").removeClass("has-success");
  			$(".panel-body .table tbody tr td").removeClass("has-error");
			$(".panel-body .table tbody tr td").removeClass("has-success");
 	});

	$('button[type=reset]').on('click', function () {
 		$(".form-horizontal div").removeClass("has-error");
	   	$(".form-horizontal div").removeClass("has-success");
 	   	$(".table-responsive .table tbody td").removeClass("has-error");
		$(".table-responsive .table tbody td").removeClass("has-success");
 		$(".panel-body .table tbody tr td").removeClass("has-error");
		$(".panel-body .table tbody tr td").removeClass("has-success");
 	});

	$(document).on("click", "input[type=reset]", function () { 
		$(".form-horizontal div").removeClass("has-error");
	   	$(".form-horizontal div").removeClass("has-success");
  	   	$(".table-responsive .table tbody td").removeClass("has-error");
		$(".table-responsive .table tbody td").removeClass("has-success");
 	});
	   		
	$(document).on("click", "button[type=reset]", function () { 
 		$(".form-horizontal div").removeClass("has-error");
		$(".form-horizontal div").removeClass("has-success");
  		$(".table-responsive .table tbody td").removeClass("has-error");
		$(".table-responsive .table tbody td").removeClass("has-success");
  	});

	 
	
	
 	(function($){
 	   $('.required-control').next().children().addClass('mandColorClass');
  		var tagCheckbox = (".form-group .col-sm-4 .form-control");
 	 	$(tagCheckbox).each(function() { 
 		  var attrid= this.id;
 		  $(this).parent().prev('label').attr('for',attrid);
 		  return attrid;     
 		});		
 	
  	  $('input.form-control.mandColorClass, textarea.form-control.mandColorClass').blur(function(){
  		var check = $(this).val();
  		if(check == ''){
  		$(this).parent().switchClass("has-success","has-error");
  		}
 		else{
 		$(this).parent().switchClass("has-error","has-success");}
 	    });
 	  
 	  
 	 $('input.form-control.mandColorClass, textarea.form-control.mandColorClass').change(function(){
	  		var check = $(this).val();
	  		if(check == ''){
	  		$(this).parent().switchClass("has-success","has-error");
	  		}
	 		else{
	 		$(this).parent().switchClass("has-error","has-success");}
	 	    }); 
  	  
 	  	$(document).on("blur", "input.form-control.mandColorClass, textarea.form-control.mandColorClass", function () { 
   		var check = $(this).val();
   		if(check == ''){
   		$(this).parent().switchClass("has-success","has-error");
   		}
  		else{
  		$(this).parent().switchClass("has-error","has-success");}
  	    });
  	  
  	$('select.form-control.mandColorClass').blur(function(){
	    var check = $(this).val();
	   if(check == '' || check == '0'){
		$(this).parent().switchClass("has-success","has-error");
		}
		else{
		$(this).parent().switchClass("has-error","has-success");
		}
		});
 
  	$(document).on("blur", "select.form-control.mandColorClass", function () { 
  		var check = $(this).val();
 		if(check == '' || check == '0')
 		{$(this).parent().switchClass("has-success","has-error");
 		}
 		else{
 		$(this).parent().switchClass("has-error","has-success");
 		}
 		});	

 
 		})(jQuery);
 		
 		resizefunc.push("initscrolls");
 		resizefunc.push("changeptype");
 		$('.animate-number').each(function(){
		$(this).animateNumbers($(this).attr("data-value"), true, parseInt($(this).attr("data-duration"))); 
 		})
 		
 		
 		$(".open-left").click(function(e){
 			e.stopPropagation();
 			
 		    $("#wrapper").toggleClass("enlarged");
 		    $("#wrapper").addClass("forced");

 		    if($("#wrapper").hasClass("enlarged") && $("body").hasClass("fixed-left")){
	    	$("body").removeClass("fixed-left").addClass("fixed-left-void");
	    	$('.side-menu').addClass('hidden');
			$('.content-page').css('margin-left','0px');
			$('.topbar .topbar-left').css('height','50px');
 		    }else if(!$("#wrapper").hasClass("enlarged") || $("body").hasClass("fixed-left-void")){
	    	$("body").removeClass("fixed-left-void").addClass("fixed-left");
	    	$('.side-menu').removeClass('hidden');
			$('.content-page').css('margin-left','280px');
			$('.topbar .topbar-left').css('height','80px');
 		    }
 		    if($("#wrapper").hasClass("enlarged")){
 		    $(".left ul").removeAttr("style");
 		    }else{
 		    $(".subdrop").siblings("ul:first").show();
 		    }
 		    toggle_slimscroll(".slimscrollleft");
 		    $("body").trigger("resize");
 		   $('#sidebar-menu>ul>li>a')[0].focus();
 		});

 		/*$("#sidebar-menu a").on('click',function(e){
 			
 		  if(!$("#wrapper").hasClass("enlarged")){

 		    if($(this).parent().hasClass("has_sub")) {
 		      e.preventDefault();
 		    }   

 		    if(!$(this).hasClass("subdrop")) {
 		      $("ul",$(this).parents("ul:first")).slideUp(350);
 		      $("a",$(this).parents("ul:first")).removeClass("subdrop");
 		      $("#sidebar-menu .pull-right i").removeClass("fa-angle-up").addClass("fa-angle-down");
 		      
 		      $(this).next("ul").slideDown(350);
 		      $(this).addClass("subdrop");
 		    }else if($(this).hasClass("subdrop")) {
 		      $(this).removeClass("subdrop");
 		      $(this).next("ul").slideUp(350);
 		    }
 		  } 
 		});*/
 		

 		/*New Menu Script*/
 		$("#sidebar-menu ul ul a").on('click',function(e){ 			
 		    if($(this).parent().hasClass("has_sub")) {
 		      e.preventDefault();
 		    }   

 		    if(!$(this).hasClass("subdrop")) {
 		      $("ul",$(this).parents("ul:first")).slideUp(350);
 		      $("a",$(this).parents("ul:first")).removeClass("subdrop");
 		      $("#sidebar-menu .pull-right i").removeClass("fa-angle-up").addClass("fa-angle-down");
 		      
 		      $(this).next("ul").slideDown(350);
 		      $(this).addClass("subdrop");
 		    }else if($(this).hasClass("subdrop")) {
 		      $(this).removeClass("subdrop");
 		      $(this).next("ul").slideUp(350);
 		    }
 		  
 		});
 		
 $("#sidebar-menu ul li.has_sub a.active").parents("li:last").children("a:first").addClass("active").trigger("click");


 $(".widget-header .widget-close").on("click",function(event){
   event.preventDefault();
   $item = $(this).parents(".widget:first");
   bootbox.confirm("Are you sure to remove this widget?", function(result) {
     if(result === true){
       $item.addClass("animated bounceOutUp");
         window.setTimeout(function () {
           if($item.data("is-app")){
             
             $item.removeClass("animated bounceOutUp");
             if($item.hasClass("ui-draggable")){
               $item.find(".widget-popout").click();
             }
             $item.hide();
             $("a[data-app='"+$item.attr("id")+"']").addClass("clickable");
           }else{
             $item.remove();
           }
         }, 300);
     }
   }); 
 });


$(document).on("click", ".widget-header .widget-toggle", function(event){
  event.preventDefault();
  $(this).toggleClass("closed").parents(".widget:first").find(".widget-content").slideToggle();
});

$(document).on("click", ".widget-header .widget-popout", function(event){
  event.preventDefault();
  var widget = $(this).parents(".widget:first");
  if(widget.hasClass("modal-widget")){
    $("i",this).removeClass("icon-window").addClass("icon-publish");
    widget.removeAttr("style").removeClass("modal-widget");
    widget.find(".widget-maximize,.widget-toggle").removeClass("nevershow");
    widget.draggable("destroy").resizable("destroy");
  }else{
    widget.removeClass("maximized");
    widget.find(".widget-maximize,.widget-toggle").addClass("nevershow");
    $("i",this).removeClass("icon-publish").addClass("icon-window");
    var w = widget.width();
    var h = widget.height();
    widget.addClass("modal-widget").removeAttr("style").width(w).height(h);
    $(widget).draggable({ handle: ".widget-header",containment: ".content-page" }).css({"left":widget.position().left-2,"top":widget.position().top-2}).resizable({minHeight: 150,minWidth: 200});
  }
  window.setTimeout(function () {
    $("body").trigger("resize");
  },300);
});

$("a[data-app]").each(function(e){
    var app = $(this).data("app");
    var status = $(this).data("status");
    $("#"+app).data("is-app",true);
    if(status == "inactive"){
    	$("#"+app).hide();
    	$(this).addClass("clickable");
    }
});

$(document).on("click", "a[data-app].clickable", function(event){
    event.preventDefault();
    $(this).removeClass("clickable");
    var app = $(this).data("app");
    $("#"+app).show();
    $("#"+app+" .widget-popout").click();
    topd = $("#"+app).offset().top - $(window).scrollTop();
    $("#"+app).css({"left":"10","top":-(topd-90)+"px"}).addClass("fadeInDown animated");
    window.setTimeout(function () {
      $("#"+app).removeClass("fadeInDown animated");
    }, 300);
});

$(document).on("click", ".widget", function(){
    if($(this).hasClass("modal-widget")){
      $(".modal-widget").css("z-index",5);
      $(this).css("z-index",6);
    }
});

$(document).on("click", '.widget .reload', function (event) { 
  event.preventDefault();
  var el = $(this).parents(".widget:first");
  blockUI(el);
    window.setTimeout(function () {
       unblockUI(el);
    }, 1000);
});

$(document).on("click", ".widget-header .widget-maximize", function(event){
    event.preventDefault();
    $(this).parents(".widget:first").removeAttr("style").toggleClass("maximized");
    $("i",this).toggleClass("icon-resize-full-1").toggleClass("icon-resize-small-1");
    $(this).parents(".widget:first").find(".widget-toggle").toggleClass("nevershow");
    $("body").trigger("resize");
    return false;
});

$( ".portlets" ).sortable({
    connectWith: ".portlets",
    handle: ".widget-header",
    cancel: ".modal-widget",
    opacity: 0.5,
    dropOnEmpty: true,
    forcePlaceholderSize: true,
    receive: function(event, ui) {$("body").trigger("resize")}
});





$(window).resize(debounce(resizeitems,100));
$("body").trigger("resize");
});

var debounce = function(func, wait, immediate) {
  var timeout, result;
  return function() {
    var context = this, args = arguments;
    var later = function() {
      timeout = null;
      if (!immediate) result = func.apply(context, args);
    };
    var callNow = immediate && !timeout;
    clearTimeout(timeout);
    timeout = setTimeout(later, wait);
    if (callNow) result = func.apply(context, args);
    return result;
  };
}

function resizeitems(){
 if($.isArray(resizefunc)){  
    for (i = 0; i < resizefunc.length; i++) {
       window[resizefunc[i]]();
   }
  }
}

function initscrolls(){
    if(jQuery.browser.mobile !== true){
	    $('.slimscroller').slimscroll({
	      height: 'auto',
	      size: "5px"
	    });

	    $('.slimscrollleft').slimScroll({
	        height: '600px',
	        position: 'right',
	        size: "10px",
	        alwaysVisible: true,
	        railOpacity: 1,
	        color: '#fff'
	    });
	}
}
function toggle_slimscroll(item){
    if($("#wrapper").hasClass("enlarged")){
      $(item).css("overflow","inherit").parent().css("overflow","inherit");
      $(item). siblings(".slimScrollBar").css("visibility","hidden");
    }else{
      $(item).css("overflow","hidden").parent().css("overflow","hidden");
      $(item). siblings(".slimScrollBar").css("visibility","visible");
    }
}


function blockUI(item) {    
    $(item).block({
      message: '<div class="loading"></div>',
      css: {
          border: 'none',
          width: '14px',
          backgroundColor: 'none'
      },
      overlayCSS: {
          backgroundColor: '#fff',
          opacity: 0.4,
          cursor: 'wait'
      }
    });
}

function unblockUI(item) {
    $(item).unblock();
}

function toggle_fullscreen(){
    var fullscreenEnabled = document.fullscreenEnabled || document.mozFullScreenEnabled || document.webkitFullscreenEnabled;
    if(fullscreenEnabled){
      if(!document.fullscreenElement && !document.mozFullScreenElement && !document.webkitFullscreenElement && !document.msFullscreenElement) {
          launchIntoFullscreen(document.documentElement);
      }else{
          exitFullscreen();
      }
    }
}


function launchIntoFullscreen(element) {
  if(element.requestFullscreen) {
    element.requestFullscreen();
  } else if(element.mozRequestFullScreen) {
    element.mozRequestFullScreen();
  } else if(element.webkitRequestFullscreen) {
    element.webkitRequestFullscreen();
  } else if(element.msRequestFullscreen) {
    element.msRequestFullscreen();
  }
}

function exitFullscreen() {
  if(document.exitFullscreen) {
    document.exitFullscreen();
  } else if(document.mozCancelFullScreen) {
    document.mozCancelFullScreen();
  } else if(document.webkitExitFullscreen) {
    document.webkitExitFullscreen();
  }
}

  $(window).scroll(function(){
    if ($(this).scrollTop() > 300) {$('.tothetop').addClass("showup");} 
    else {$('.tothetop').removeClass("showup");}
  });
  
  $('.tothetop').click(function(){
    $('html, body').animate({scrollTop : 0},600);
    return false;
  });
  
  $('body').tooltip({
    selector: "[data-toggle=tooltip]",
    container: "body"
  });

  (function($) {
    $.fn.animateNumbers = function(stop, commas, duration, ease) {
        return this.each(function() {
            var $this = $(this);
            var start = parseInt($this.text().replace(/,/g, ""));
			commas = (commas === undefined) ? true : commas;
            $({value: start}).animate({value: stop}, {
            	duration: duration == undefined ? 1000 : duration,
            	easing: ease == undefined ? "swing" : ease,
            	step: function() {
            		$this.text(Math.floor(this.value));
					if (commas) { $this.text($this.text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")); }
            	},
            	complete: function() {
            	   if (parseInt($this.text()) !== stop) {
            	       $this.text(stop);
					   if (commas) { $this.text($this.text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,")); }
            	   }
            	}
            });
        });
    };
})(jQuery);
  
  $("#datepicker").datepicker({
  	changeMonth: true,
  	changeYear: true
  });
  $("#datepicker2").datepicker({
  	changeMonth: true,
  	changeYear: true
  });
  $("#datepicker3").datepicker({
  	changeMonth: true,
  	changeYear: true
  });
  
  $(window).on('load', function() {chosen();});
  
  $( document ).ajaxComplete(function() {
   	 $('.required-control').next().children().addClass('mandColorClass');
   		chosen();
 
   	$('input[type=Reset]').on('click', function () {
   		$(".form-horizontal div").removeClass("has-error");
		$(".form-horizontal div").removeClass("has-success");
 		$(".table-responsive .table tbody td").removeClass("has-error");
		$(".table-responsive .table tbody td").removeClass("has-success");
 		$(".panel-body .table tbody tr td").removeClass("has-error");
		$(".panel-body .table tbody tr td").removeClass("has-success");
 	});
   		
   	$('button[type=Reset]').on('click', function () {
   		$(".form-horizontal div").removeClass("has-error");
		$(".form-horizontal div").removeClass("has-success");
 		$(".table-responsive .table tbody td").removeClass("has-error");
		$(".table-responsive .table tbody td").removeClass("has-success");
 		$(".panel-body .table tbody tr td").removeClass("has-error");
		$(".panel-body .table tbody tr td").removeClass("has-success");
    	});
   		$(document).on("click", "input[type=Reset]", function () { 
   			$(".form-horizontal div").removeClass("has-error");
			$(".form-horizontal div").removeClass("has-success");
  			$(".table-responsive .table tbody td").removeClass("has-error");
			$(".table-responsive .table tbody td").removeClass("has-success");
  			$(".panel-body .table tbody tr td").removeClass("has-error");
			$(".panel-body .table tbody tr td").removeClass("has-success");
   		});
   		   		
   		$(document).on("click", "button[type=Reset]", function () { 
   			$(".form-horizontal div").removeClass("has-error");
			$(".form-horizontal div").removeClass("has-success");
  			$(".table-responsive .table tbody td").removeClass("has-error");
			$(".table-responsive .table tbody td").removeClass("has-success");
  			$(".panel-body .table tbody tr td").removeClass("has-error");
			$(".panel-body .table tbody tr td").removeClass("has-success");
   	 	});

   		var tagCheckbox = (".form-group .col-sm-4 .form-control");
 	 	$(tagCheckbox).each(function() { 
 		 var attrid= this.id;
 		 $(this).parent().prev('label').attr('for',attrid);
 		 return attrid;     
 		});	 

 	 	
 		 $('input.form-control.mandColorClass, textarea.form-control.mandColorClass').blur(function(){
 	  		var check = $(this).val();
 	  		if(check == ''){
 	  		$(this).parent().switchClass("has-success","has-error");
 	  		}
 	 		else{
 	 		$(this).parent().switchClass("has-error","has-success");}
 	 	    });
 		 
 		 
 		$('.form-group input.form-control.mandColorClass.datepicker').blur(function(){
  	  		var check = $(this).val();
  	  		var d=check.slice(0, 2);var m=check.slice(3, 5);var y=check.slice(6, 8);
  	  		if((check == '')||(check.slice(0,1)=="_")||(d>"31")||(d=="00")||(m>"12")||(m=="00")||(y=="00")){
  	  		$(this).parent().switchClass("has-success","has-error");$(this).val('');
  	  		}
  	 		else{
  	 		$(this).parent().switchClass("has-error","has-success");}
  	 	    }); 
 		 
 		 $('input.form-control.mandColorClass, textarea.form-control.mandColorClass').change(function(){
  	  		var check = $(this).val();
  	  		if(check == ''){
  	  		$(this).parent().switchClass("has-success","has-error");
  	  		}
  	 		else{
  	 		$(this).parent().switchClass("has-error","has-success");}
  	 	    }); 
  		 
 		$('.form-group input.form-control.mandColorClass.datepicker').change(function(){
  	  		var check = $(this).val();
  	  		var o=check.slice(0,1);var d=check.slice(0, 2);var m=check.slice(3, 5);var y=check.slice(6, 8);
  	  		if((check == '')||(check.slice(0,1)=="_")||(d>"31")||(d=="00")||(m>"12")||(m=="00")||(y=="00")){
  	  		$(this).parent().switchClass("has-success","has-error");$(this).val('');
  	  		}
  	 		else{
  	 		$(this).parent().switchClass("has-error","has-success");}
  	 	    });
 		 
 		$('select.form-control.mandColorClass').blur(function(){
		     var check = $(this).val();
		     if(check == '' || check == '0'){
		    	 $(this).parent().switchClass("has-success","has-error");
 				 }
 			else{
 				$(this).parent().switchClass("has-error","has-success");
 				}
 			});
 		 
  	 	var checkboxes = $("label.mandColorClass > input[type='checkbox']"),
 	 	submitcheckbox = $('.has-checkbox');
 	 	checkboxes.click(function() {
 	 	submitcheckbox.toggleClass("has-error", !checkboxes.is(":checked"));
 		submitcheckbox.toggleClass("has-success", checkboxes.is(":checked"));
		});
 	 	
 	 	
// 	 	$('.panel-collapse').on('shown.bs.collapse', function(e) {
// 	 	  	var $panel = $(this).closest('.panel');
// 	 	  	
//  	 		$('html,body').animate({scrollTop: $panel.offset().top}, 20);
//    	 		return false;
//  	 		});
//
// 	 	    var arr = jQuery.map(jQuery('.panel-collapse'),function(n,i){
// 	 	       return jQuery(n).attr('id');
// 	 	    });
// 	 		var message = jQuery.map(jQuery('.panel-title > a'),function(n,i){
// 	 		return jQuery(n).text();
// 	 	  });
//
//
// 	 		var list = $('<ol class="breadcrumb-new breadcrumb-arrow" />'); 
// 	 		extractResult(arr);   
//
// 	 		$('body .widget-content .form-horizontal').prepend(list); 
// 	 		var $breadcrumb = $('ol.breadcrumb-new').length; 
// 	 	    if ($breadcrumb > 1) {$('ol.breadcrumb-new').remove();}
//
// 	 	    function extractResult(result){
// 	 		jQuery.each(result, function(index, value) {
// 	 	   	$('<li><a data-target="#'+ value +'"  href="#'+ value +'" data-toggle="collapse" data-parent="#accordion_single_collapse">'+ message[index] +'</li>',{href:value}).appendTo(list);
// 	 	   	});
// 	 		}
//
//
//  	 	    
//  	 		$('ol.breadcrumb-new li:first-child').addClass('active');
// 	 	 	$('ol.breadcrumb-new li').click(function(e) {
// 	 	 	$('ol.breadcrumb-new li').removeClass('active');
// 	 		   var $this = $(this);
// 	 		   if (!$this.hasClass('active')) {
// 	 			   $this.addClass('active');
// 	 	    }
// 	 	    //e.preventDefault();
//  	 		});
//
// 	 		$("ol.breadcrumb-new li a, .accordion-toggle .panel.panel-default .panel-title a").on("click", function(e){
// 	 		var textToCompare	=	$(this).text();
// 	 		var dynamicTextToCompare	= "";
// 	 		$("ol.breadcrumb-new").find('li').each(function(j, element)
// 	 			{
// 	 		$(element).removeClass('active')
// 	 		$(element).find('a').each(function(obj1)
// 	 		{
// 	 		dynamicTextToCompare =	$(this).text();
// 	 		});
// 	 			if(dynamicTextToCompare == textToCompare)
// 	 		{
// 	 		$(element).attr("class","active");
// 	 		}
// 	 		});
// 	 		});
//
// 	 		var index = 1; 
// 	 		var nthDiv = $('.panel-collapse').eq(index);
// 	 		if (nthDiv.hasClass('in')) {
// 	 			$('ol.breadcrumb-new li').siblings().removeClass('active');
// 	 		$('ol.breadcrumb-new li').eq(index).addClass('active');
// 	 		}		
//
// 	 		if (!$("ol.breadcrumb-new li").length ){$('ol.breadcrumb-new').hide();}

 	 		

 	 		 

  });

  function chosen(){
	  
	var config = {'.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},}
	for (var selector in config) {$(selector).chosen({search_contains:true});}
	$('.required-chosen').next().addClass('mandColorClass');
    $('.required-control').next().children().addClass('mandColorClass');
  }
  
  
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
	  var d = new Date();
	  d.setTime(d.getTime() + (1 * 24 * 60 * 60 * 1000));
	  var expires = "expires=" + d.toGMTString();
  	if (arg == "O") {
  		var user = getCookie("accessibility");
  	}
  	document.cookie = "accessibilityCol" + "=" + arg + "; " + expires;  	
  	location.reload(window.location.href);
  }
 
      $('#incfont').click(function() {
          curSize = parseInt($('#text-resize').css('font-size')) + 1;
          if (curSize <= 18)
              $('#text-resize').css('font-size', curSize);
      });
      $('#decfont').click(function() {
          curSize = parseInt($('#text-resize').css('font-size')) - 1;
          if (curSize >= 14)
              $('#text-resize').css('font-size', curSize);
      });
      $('#norfont').click(function() {
          curSize1 = parseInt($('#text-resize').css('font-size', ''));
          $('#text-resize').css('font-size', curSize1);
      });
  /*Calculator*/
      $(function () {
    		$.calculator.setDefaults({showOn: 'opbutton', buttonImageOnly: true, clearText:'CA',buttonImage: '../assets/Calculator/calculator.png',showFormula:true,buttonStatus:'Open the calculator',closeText:'Close',isOperator: function(ch, event, value, base, decimalChar) { 
    	        return '+-*/'.indexOf(ch) > -1 && !(ch === '-' && value === '')}});
    		
    		$('#basicCalculator').calculator();
    		
    	});
    	jQuery(document).ready(function(){
    	$(".calculator-keyentry").attr("tabindex","0");
    	});
    	$( function() {
    	    $( "#drag" ).draggable({
    		containment: "window",revertDuration: 1
    	});
    	});
    	 $(document).on("click", "#hideshow", function(){
    		$('#basicCalculator').fadeToggle();
    		 $(".calculator-keyentry")[0].focus();
    		  $('.calculator-result span').text('');
    		 
    	});
    	$(document).on("click", ".content,.widget,.widget-header,.widget-content,.side-menu", function(){
    		$('#basicCalculator').slideUp();
    	});
    
    	$(document).on("focusout",".input-group",function(){
    	    if($('.input-group').hasClass('has-error')){
    			$(this).css('margin-bottom','20px');
    			}else{
    				$(this).css('margin-bottom','0px');	
    			}
    	});
    	
/* $(function(){ 	
    	   $('.scroll-menu').slimScroll({
   	        position: 'right',
   	        size: "5px",
   	        alwaysVisible: true,
   	        railOpacity: 1,
   	        opacity:1,
   	        color: '#0e5c31'
   	    });
	   $('.scroll-menu1').slimScroll({
		    height:'240px',
  	        position: 'right',
  	        size: "5px",
  	        alwaysVisible: true,
  	        railOpacity: 1,
  	        opacity:1,
  	        color: '#8caf9b'
  	    });   	   
    	  
 });  */
 

$("#searchButton").click(function(){
 var src_str =$("#sidebar-menu").html();
 var term = $("#search_input").val();
 term = term.replace(/(\s+)/,"(<[^>]+>)*$1(<[^>]+>)*");
 var pattern = new RegExp("("+term+")", "gi");

 src_str = src_str.replace(pattern, "<mark>$1</mark>");
 src_str = src_str.replace(/(<mark>[^<>]*)((<[^>]+>)+)([^<>]*<\/mark>)/,"$1</mark>$2<mark>$4");
 $("#sidebar-menu").replaceWith(src_str);
 
 $("#nav").wrap('<div id="sidebar-menu" class="has_sub active">')

 });
$(window).resize(function(e) {
var display_height= $(window).height();
var header_height=$(".topbar").height();
var footer_height=$(".footer-link1 ul").height();
var height=display_height-(header_height+footer_height);

$('.leftmenu .nav-tabs').slimScroll({
    height: height,
    position: 'right',
    size: "8px",
    alwaysVisible: true,
    railOpacity: 1,
    color: '#fff'
});

$('.desktop .tab-content').slimScroll({
    height:height,
    position: 'right',
    size: "8px",
    alwaysVisible: true,
    railOpacity: 1,
    color: '#343535'
});
});

function deactivatekeyboard(){
	$('.virtual-keyboard > a').removeClass('vk-on').addClass('virtualKeyboard vk-off');
	location.reload();
}
function nodeactivatekeyboard(){
	$.fancybox.close();
}

$(document).ready(function() {
	/* ----- Virtual Keyboard starts ----- */
	$('.virtualKeyboard').on('click', function(){
		if($(this).hasClass('virtualKeyboard')){
			$(document).keyboard({
				language: 'hindi, us:English',
				keyboardPosition: 'bottom',
				blackoutColor:'transparent',
				showSelectedLanguage: true,
				allowEscapeCancel: true,  
				allowEnterAccept: true
			});
			alert(getLocalMessage('service.virtual.keyboard.activation.text'));
			$('.virtualKeyboard').removeClass('virtualKeyboard vk-off').addClass('vk-on');
		} else {
			var message='';
			
			var msg=getLocalMessage('service.virtual.keyboard.warn.text');
			var cls1 = getLocalMessage('eip.commons.yes');
			var cls2 = getLocalMessage('eip.commons.no');
			
			message='<p class="text-center padding-10 margin-top-10 text-info">'+msg+'</p>';
			message	+='<div class="text-center padding-bottom-10"><input type=\'button\' value=\''+cls1+'\' class=\'btn btn-blue-2 margin-right-5\'';
			message	+=' onclick="deactivatekeyboard()" />';
			message	+='<input type=\'button\' id=\'btnNo\'  value=\''+cls2+'\' class=\'btn btn-danger\'';
			message	+=' onclick="nodeactivatekeyboard()" /></div>';	
			
			$(errMsgDiv).removeClass('ok-msg').addClass('warn-msg');
			$(errMsgDiv).html(message);
			$(errMsgDiv).show();
			
			$('#btnNo').focus();
			showModalBoxWithoutClose(errMsgDiv);
		}
	});
	/* ----- Virtual Keyboard ends ----- */
});
