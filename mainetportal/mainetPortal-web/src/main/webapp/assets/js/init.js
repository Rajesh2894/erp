$(document).ready(function() {
	
	//$(".responsiveChosen").chosen({width: "50%"});
	/*$('#navbar').hide();*/
	if ($('.content-page').is('.breadcrumb'))
	{
 	     $('.header-body').removeClass('hidden-lg hidden-md');   // Hide button
	}
 	
//	$('#navigation').find('li.dropdown-submenu').removeClass();
//	$('#navigation').find('ul.dropdown-menu').removeClass();
//	$('#navigation').find('i.fa.fa-caret-down').removeClass();
	
	$(document).on("click", "#menu-toggle", function(){
		$('.navigation').slideToggle();
	});

	$('a.close_icon').click(function(){
		$('.navigation').slideUp();
	});

$(document).on("click", "#mobile-button", function(){
	$('#navbar').slideToggle();
});


$('#mobile .parent ul li a,#mobile .parent ul li input, #myCarousel, .bg-green-texture, .news-bg, .photo-box, .facts, .dashboard-page,  .page-heading, #footer, .footer-logos, .visitors-counter, footer,.about-bg,.key-contact,.highlights-bg,.padding-40,.content,.complaint,.service-div,.drag1,.help-no').click(function(){
	$('#navbar').slideUp();
});


	
	
  resizefunc.push("initscrolls");
  resizefunc.push("changeptype");
  $('.animate-number').each(function() {
      $(this).animateNumbers($(this).attr("data-value"), true, parseInt($(this).attr("data-duration")));
    })
    //WIDGET ACTIONS
//  $(".widget-header .widget-close").on("click", function(event) {
//    event.preventDefault();
//    $item = $(this).parents(".widget:first");
//    bootbox.confirm("Are you sure to remove this widget?", function(result) {
//      if (result === true) {
//        $item.addClass("animated bounceOutUp");
//        window.setTimeout(function() {
//          if ($item.data("is-app")) {
//
//            $item.removeClass("animated bounceOutUp");
//            if ($item.hasClass("ui-draggable")) {
//              $item.find(".widget-popout").click();
//            }
//            $item.hide();
//            $("a[data-app='" + $item.attr("id") + "']").addClass("clickable");
//          } else {
//            $item.remove();
//          }
//        }, 300);
//      }
//    });
//  });

  $(document).on("click", ".widget-header .widget-toggle", function(event) {
    event.preventDefault();
    $(this).toggleClass("closed").parents(".widget:first").find(".widget-content").slideToggle();
  });

  $(document).on("click", ".widget-header .widget-popout", function(event) {
    event.preventDefault();
    var widget = $(this).parents(".widget:first");
    if (widget.hasClass("modal-widget")) {
      $("i", this).removeClass("fa fa-object-group").addClass("fa fa-object-ungroup");
      widget.removeAttr("style").removeClass("modal-widget");
      widget.find(".widget-maximize,.widget-toggle").removeClass("nevershow");
      widget.draggable("destroy").resizable("destroy");
    } else {
      widget.removeClass("maximized");
      widget.find(".widget-maximize,.widget-toggle").addClass("nevershow");
      $("i", this).removeClass("fa fa-object-ungroup").addClass("fa fa-object-group");
      var w = widget.width();
      var h = widget.height();
      widget.addClass("modal-widget").removeAttr("style").width(w).height(h);
      $(widget).draggable({
        handle: ".widget-header",
        containment: ".content-page"
      }).css({
        "left": widget.position().left - 2,
        "top": widget.position().top - 120
      }).resizable({
        minHeight: 150,
        minWidth: 200
      });
    }
    window.setTimeout(function() {
      $("body").trigger("resize");
    }, 300);
  });

  $("a[data-app]").each(function(e) {
    var app = $(this).data("app");
    var status = $(this).data("status");
    $("#" + app).data("is-app", true);
    if (status == "inactive") {
      $("#" + app).hide();
      $(this).addClass("clickable");
    }
  });

  $(document).on("click", "a[data-app].clickable", function(event) {
    event.preventDefault();
    $(this).removeClass("clickable");
    var app = $(this).data("app");
    $("#" + app).show();
    $("#" + app + " .widget-popout").click();
    topd = $("#" + app).offset().top - $(window).scrollTop();
    $("#" + app).css({
      "left": "10",
      "top": -(topd - 90) + "px"
    }).addClass("fadeInDown animated");
    window.setTimeout(function() {
      $("#" + app).removeClass("fadeInDown animated");
    }, 300);
  });

  $(document).on("click", ".widget", function() {
    if ($(this).hasClass("modal-widget")) {
      $(".modal-widget").css("z-index", 5);
      $(this).css("z-index", 6);
    }
  });

  $(document).on("click", '.widget .reload', function(event) {
    event.preventDefault();
    var el = $(this).parents(".widget:first");
    blockUI(el);
    window.setTimeout(function() {
      unblockUI(el);
    }, 1000);
  });
});


/*(function($) {
 
  $.fn.buttonLoader = function(action) {
    var self = $(this);
    if (action == 'start') {
      if ($(self).attr("disabled") == "disabled") {
        return false;
      }
      $('.btn').attr("disabled", true);
      $(self).attr('data-btn-text', $(self).text());
      var text = 'Please Wait';
      console.log($(self).attr('data-load-text'));
      if ($(self).attr('data-load-text') != undefined && $(self).attr('data-load-text') != "") {
        var text = $(self).attr('data-load-text');
      }
      alert($(self).html());
      $(self).html('<span class="spinner"><i class="fa fa-spinner fa-spin" title="button-loader"></i></span> ' + text);
      $(self).addClass('active');
      alert($(self).html());
    }
    if (action == 'stop') {
      $(self).html($(self).attr('data-btn-text'));
      $(self).removeClass('active');
      $('.btn').attr("disabled", false);
    }
  }
})(jQuery);*/



function nifty_modal_alert(effect, header, text) {

  var randLetter = String.fromCharCode(65 + Math.floor(Math.random() * 26));
  var uniqid = randLetter + Date.now();

  $modal = '<div class="md-modal md-effect-' + effect + '" id="' + uniqid + '">';
  $modal += '<div class="md-content">';
  $modal += '<h3>' + header + '</h3>';
  $modal += '<div class="md-modal-body">' + text;
  $modal += '</div>';
  $modal += '</div>';
  $modal += '</div>';

  $("body").prepend($modal);

  window.setTimeout(function() {
    $("#" + uniqid).addClass("md-show");
    $(".md-overlay,.md-close").click(function() {
      $("#" + uniqid).removeClass("md-show");
      window.setTimeout(function() {
        $("#" + uniqid).remove();
      }, 500);
    });
  }, 100);

  return false;
}


//SCROLL TO TOP
$(window).scroll(function() {
  if ($(this).scrollTop() > 300) {
    $('.tothetop').addClass("showup");
  } else {
    $('.tothetop').removeClass("showup");
  }
});
//Click event to scroll to top
$('.tothetop').click(function() {
  $('html, body').animate({
    scrollTop: 0
  }, 600);
  return false;
});

$('a[href="#MainContent"]').click(function() {
  $('html, body').animate({
	  scrollTop: 400
	  }, 600);
	  return false;
	});


 


(function($) {
  $.fn.animateNumbers = function(stop, commas, duration, ease) {
    return this.each(function() {
      var $this = $(this);
      var start = parseInt($this.text().replace(/,/g, ""));
      commas = (commas === undefined) ? true : commas;
      $({
        value: start
      }).animate({
        value: stop
      }, {
        duration: duration == undefined ? 1000 : duration,
        easing: ease == undefined ? "swing" : ease,
        step: function() {
          $this.text(Math.floor(this.value));
          if (commas) {
            $this.text($this.text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
          }
        },
        complete: function() {
          if (parseInt($this.text()) !== stop) {
            $this.text(stop);
            if (commas) {
              $this.text($this.text().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"));
            }
          }
        }
      });
    });
  };
})(jQuery);

//DatePicker
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
$("#datepicker4").datepicker({
  changeMonth: true,
  changeYear: true
});
//Required Control
$('.required-control').next().children().addClass('mandColorClass');

//Livechat Add Alt and Chosen
$(window).on('load', function(){
  $(".mylivechat_collapsed_img").attr("alt", "Maximize");  
  chosen();
});
$(document).ajaxComplete(function() {
  chosen();
});

function chosen() {
  var config = {
    '.chosen-select-no-results': {
      no_results_text: 'Oops, nothing found!'
    },
  }
  for (var selector in config) {
    $(selector).chosen(config[selector]);
  }
  $('.required-chosen').next().addClass('mandColorClass');
  $('.required-control').next().children().addClass('mandColorClass');
}




//Play Pause Button
$('.control').on('mousedown', function() {
  $(this).toggleClass('pause play');
});
$(document).on('keyup', function(e) {
  if (e.which == 32) {
    $('.control').toggleClass('pause play');
  }
});

 
//Client Side Validation Global
$(document).ready(function() {
	
	/*var nav = $('.list-group');
	if (nav.length) {
	  var contentNav = nav.offset().top;
 	}
    var stickyNav = function () {
        var scrollTop = $(window).scrollTop();
        if (scrollTop > contentNav) {
            $('.dashboard-page .list-group').addClass('sticky');
        } else {
            $('.dashboard-page .list-group').removeClass('sticky');
        }
    };
    stickyNav();
    $(window).scroll(function () {
        stickyNav();
    });
*/
	
//	var navWrap = $('.col-sm-3 .list-group'),
//    nav = $('.list-group'),
//    startPosition = navWrap.offset().top,
//    stopPosition = $('#footer, .footer-logos').offset().top - nav.outerHeight();
//
//	$(document).scroll(function () {
//		
//	
//		
//		
//     var y = $(this).scrollTop();
//
//    if (y > startPosition) {
//        nav.addClass('sticky');
//        if (y > stopPosition) {
//            nav.css('top', stopPosition - y);
//        } else {
//            nav.css('top', 40);
//        }
//    } else {
//        nav.removeClass('sticky');
//    } 
//});
// 

	
	
	
   (function($) {
	   $('input.form-control.mandColorClass').blur(function()
				{
			 	 	var check = $(this).val();
					if(check == '')
					{$(this).parent().switchClass("has-success","has-error");
					$(this).addClass("shake animated");}
					else
					{$(this).parent().switchClass("has-error","has-success");
					$(this).removeClass("shake animated");}
		    });
			
			  $('input.form-control.hasDatepicker').blur(function()
				    {
			 	     var check = $(this).val();
					    if(check == '')
						{$(this).parent().switchClass("has-success","has-error");
						$(this).addClass("shake animated");}
				      	else
						{$(this).parent().switchClass("has-error","has-success");
						$(this).removeClass("shake animated");}
				    });
			  
				$('select.form-control.mandColorClass').blur(function()
					  {
					     var check = $(this).val();
					     if(check == '' || check == '0')
					     {$(this).parent().switchClass("has-success","has-error");
					     $(this).addClass("shake animated");}
					     else
					     {$(this).parent().switchClass("has-error","has-success");
					     $(this).removeClass("shake animated");}
					   });
				
				$('textarea.form-control.mandColorClass').blur(function()
					{
						var check = $(this).val();
					    if(check == '' || check == '0')
					    {$(this).parent().switchClass("has-success","has-error");
					    $(this).addClass("shake animated");}
					    else
					    {$(this).parent().switchClass("has-error","has-success");
					    $(this).removeClass("shake animated");}
					});
  })(jQuery);
  
  $(document).ajaxComplete(function() {
    $('input[type=reset]').on('click', function() {
      $(".form-horizontal div").removeClass("has-error");
      $(".form-horizontal div").removeClass("has-success");
    });
    $('button[type=reset]').on('click', function() {
      $(".form-horizontal div").removeClass("has-error");
      $(".form-horizontal div").removeClass("has-success");
    });

    /*var tagCheckbox = (".form-group .col-sm-4 .form-control");
    $(tagCheckbox).each(function() {
      var attrid = this.id;
      $(this).parent().prev('label').attr('for', attrid);
      return attrid;
    });*/

    $('input.form-control.mandColorClass').blur(function() {
      var check = $(this).val();
      if (check == '') {
        $(this).parent().switchClass("has-success", "has-error");
      } else {
        $(this).parent().switchClass("has-error", "has-success");
      }
    });

    $('input.form-control.hasDatepicker').change(function() {
      var check = $(this).val();
      if (check == '') {
        $(this).parent().switchClass("has-success", "has-error");
      } else {
        $(this).parent().switchClass("has-error", "has-success");
      }
    });


    $('select.form-control.mandColorClass').blur(function() {
      var check = $(this).val();
      if (check == '' || check == '0') {
        $(this).parent().switchClass("has-success", "has-error");
      } else {
        $(this).parent().switchClass("has-error", "has-success");
      }
    });

    $('textarea.form-control.mandColorClass').blur(function() {
      var check = $(this).val();
      if (check == '') {
        $(this).parent().switchClass("has-success", "has-error");
      } else {
        $(this).parent().switchClass("has-error", "has-success");
      }
    });
    $("input[type=reset]").click(function() {
      if ($('div').hasClass('has-error')) {
        $(this).removeClass('has-error');
      }

    });
  });
});




//Revolution Slider
(function(theme, $) {

	theme = theme || {};

	var instanceName = '__revolution';

	var PluginRevolutionSlider = function($el, opts) {
		return this.initialize($el, opts);
	};

	PluginRevolutionSlider.defaults = {
		sliderType: 'standard',
		sliderLayout: 'fullwidth',
		delay: 9000,
		gridwidth: 1170,
		gridheight: 500,
		spinner: 'spinner3',
		navigation: {
			keyboardNavigation: 'off',
			keyboard_direction: 'horizontal',
			mouseScrollNavigation: 'off',
			onHoverStop: 'on',
			touch: {
				touchenabled: 'on',
				swipe_threshold: 75,
				swipe_min_touches: 1,
				swipe_direction: 'horizontal',
				drag_block_vertical: false
			},
			arrows: {
				enable: true,
				hide_onmobile: false,
				hide_under: 0,
				hide_onleave: true,
				hide_delay: 200,
				hide_delay_mobile: 1200,
				left: {
					h_align: 'left',
					v_align: 'center',
					h_offset: 30,
					v_offset: 0
				},
				right: {
					h_align: 'right',
					v_align: 'center',
					h_offset: 30,
					v_offset: 0
				}
			}
		}
	};

	PluginRevolutionSlider.prototype = {
		initialize: function($el, opts) {
			if ($el.data(instanceName)) {
				return this;
			}

			this.$el = $el;

			this
				.setData()
				.setOptions(opts)
				.build()
				.events();

			return this;
		},

		setData: function() {
			this.$el.data(instanceName, this);

			return this;
		},

		setOptions: function(opts) {
			this.options = $.extend(true, {}, PluginRevolutionSlider.defaults, opts, {
				wrapper: this.$el
			});

			return this;
		},

		build: function() {
			if (!($.isFunction($.fn.revolution))) {
				return this;
			}

			// Single Slider Class
			if(this.options.wrapper.find('> ul > li').length == 1) {
				this.options.wrapper.addClass('slider-single-slide');
			}

			this.options.wrapper.revolution(this.options);

			return this;
		},

		events: function() {

			return this;
		}
	};

	// expose to scope
	$.extend(theme, {
		PluginRevolutionSlider: PluginRevolutionSlider
	});

	// jquery plugin
	$.fn.themePluginRevolutionSlider = function(opts) {
		return this.map(function() {
			var $this = $(this);

			if ($this.data(instanceName)) {
				return $this.data(instanceName);
			} else {
				return new PluginRevolutionSlider($this, opts);
			}

		});
	}

}).apply(this, [window.theme, jQuery]);


//Revolution Slider
(function($) {

	'use strict';

	if ($.isFunction($.fn['themePluginRevolutionSlider'])) {

		$(function() {
			$('[data-plugin-revolution-slider]:not(.manual), .slider-container .slider:not(.manual)').each(function() {
				var $this = $(this),
					opts;

				var pluginOptions = $this.data('plugin-options');
				if (pluginOptions)
					opts = pluginOptions;

				$this.themePluginRevolutionSlider(opts);
			});
		});

	}

}).apply(this, [jQuery]);



$(function(){
	if (navigator.userAgent.indexOf('Safari') != -1 && navigator.userAgent.indexOf('Chrome') == -1 )  { 
		   $('.complaint .tab-pane .content-tab ul.banner-new li a').css('display','-webkit-box');
		   if($(window).width() >= 750){
				 $('.minister-details ul li').css({'float' : 'left','width' : '25%'});
			   }
		}
	else if ((navigator.userAgent.indexOf('MSIE') != -1) || (navigator.userAgent.match(/Trident\/7\./)) || (navigator.userAgent.indexOf('Edge') != -1))  { 
		  
		$('.header-search').css('display','inline-block');
		   $('.complaint .tab-pane .content-tab ul.banner-new li a').css('display','block');
		   if($(window).width() >= 750){
			 $('.minister-details ul li').css({'float' : 'left','width' : '25%'});
		   }
	}
});

$(document).ready(function () {	
	var elem = $('#data thead th');
	elem.each(function () {
		 if( $(this).text()=='Date') {
			 thIndex=elem.index(this);
			 $("#date_filter").css('display','block');
			 $.fn.dataTable.ext.search.push(
				        function (settings, data, dataIndex) {
				            var min = $('#min').datepicker("getDate");
				            var max = $('#max').datepicker("getDate");
				            var d = data[thIndex].split("/");
				            var startDate = new Date(d[1]+"-"+d[0]+"-"+d[2]);
				            if(startDate=="Invalid Date"){startDate= new Date(data[thIndex]);}
				            if (min == null && max == null) { return true; }
				            if (min == null && startDate <= max) { return true;}
				            if(max == null && startDate >= min) {return true;}
				            if (startDate <= max && startDate >= min) { return true; }
				            return false;
				        }
				    );			 
		    }
	});
	
   

    $("#min").datepicker({ onSelect: function () { table.draw(); }, changeMonth: true, changeYear: true , dateFormat:"yy-mm-dd"});
    $("#max").datepicker({ onSelect: function () { table.draw(); }, changeMonth: true, changeYear: true, dateFormat:"yy-mm-dd" });
    var table = $('#data').DataTable({
		//your normal options
		"language": { "search": "" }, 
		"pagingType": "full_numbers",
		"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
		"aaSorting": [],
		"scrollX": true,
		});

    // Event listener to the two range filtering inputs to redraw on input
    $('#min, #max').change(function () {
        table.draw();
    });
    
    $('#min, #max').keyup(function(e) {
    if(e.keyCode == 8 || e.keyCode == 46) {
        $.datepicker._clearDate(this);
    }
});
});

(function($) {
    $.fn.hasScrollBar = function() {
        return this.get(0).scrollWidth > this.width();
    }
})(jQuery);


$(document).ready(function() {
	/* ----- To open the uploaded document on a new browser tab starts ----- */
	var addTarget = $('.helpDocViewLabel .additional-btn > a');
	addTarget.on('click', function() {
		$(this).attr('target', '_blank');
	});
	/* ----- To open the uploaded document on a new browser tab ends ----- */
	
	/* ----- To find fancybox and add navigation arrows starts ----- */
	var photoGalleryContainer = $('.content-page .photogallery');
	photoGalleryContainer.find('a.fancybox').attr('data-fancybox-group','images');
	/* ----- To find fancybox and add navigation arrows ends ----- */
	
	/* ----- JS for accordion side-bar after citizen login starts ----- */
	var citDashContentHeight = $('.citDashContent').innerHeight();
	var sidebarNavigationLink = $('.cit-dash #sidebar-menu ul li > a');
	var sidebarContentDiv = $('.cit-dash #sidebar-menu ul li > ul');
	$('.cit-dash #sidebar-menu').css({'height':citDashContentHeight});
	$('.cit-dash .menuBtn, .cit-dash .menuBtnClose').on('click', function() {
		$('.menuBtn').toggleClass('hide');
		$('.menuBtnClose').toggleClass('show');
		$('.cit-dash .sidebar-main').toggleClass('sidebar-open');
		$('.dataTables_paginate .pagination>.active>a').toggleClass('z-index-0');
		if(sidebarNavigationLink.hasClass('open')){
			sidebarNavigationLink.removeClass('open');
			sidebarContentDiv.slideUp(300);
		}
	});
	sidebarNavigationLink.on("click", function(e){
		if($(this).parent().has("ul")) {
		 e.preventDefault();
		 if($(this).hasClass("open")) {
			 $(this).removeClass("open");
		     $(this).next("ul").slideUp(300);
		 }else{				 
		     if($(this).parent().parent().attr("id")=="nav"){
		    	 sidebarContentDiv.slideUp(300);
				 sidebarNavigationLink.removeClass("open");
		       	 $(this).next("ul").slideDown(300);
			     $(this).addClass("open");			     
		     }else{
		    	 $(this).parent().parent().find("li ul").slideUp(300);
		    	 $(this).parent().prev().find("a").removeClass("open");
		    	 $(this).parent().parent().prev().addClass("open");
		    	 $(this).next("ul").slideDown(300);
			     $(this).addClass("open");
			     $(this).parent().parent().slideDown(300);
		     }				 			 
		 }     
	   }
	});
	/* ----- JS for accordion side-bar after citizen login ends ----- */
	
});

