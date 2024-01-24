/*Target redirect function Start*/
function redirectWater() {
    if ($("#BillPayment").val() == 2) {
        window.open('WaterBillPayment.html', '_blank');
    }
}




var childDivName = '.child-popup-dialog';

function closelandingPageImage() {
    $(childDivName).hide();
    return false;
}

function getStatus1(event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode == '13') {
        getServiceStatus();
    }
}

function getStatus2(event) {
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if (keycode == '13') {
        getStatus();
    }
}
/* ***********************Get Status Js Start******************* */
function getStatus() {
    var rtiId = $('#rtiId').val();
    var response = getLocalMessage('eip.citizen.noNotEnter');

    if (rtiId) {
        var postdata = 'rtiId= ' + rtiId;

        response = __doAjaxRequest(
            'RtiApplicationStatus.html?getRtiStatus', 'POST',
            postdata, false, 'html');
    }
    citizenChildPoppup(response);

}

function getServiceStatus() {
    var serviceId = $('#serviceId').val();
    var response = '<html><span>' +
        getLocalMessage('service.noNotEnter') + '</span></html>';

    if (serviceId) {
        var postdata = 'serviceId= ' + serviceId;
        response = __doAjaxRequest(
            'ServiceApplicationStatus.html?getServiceStatus',
            'POST', postdata, false, 'html');
    }
    citizenChildPoppup(response);

}



function getServiceStatus2() {
    var serviceId = $('#serviceId').val();
    var response = '<html><span>' +
        getLocalMessage('service.noNotEnter') + '</span></html>';

    if (serviceId) {
        var postdata = 'serviceId= ' + serviceId;
        response = __doAjaxRequest(
            'ServiceApplicationStatus.html?getServiceStatus',
            'POST', postdata, false, 'html');
        $("#demo1-home").html(response)
        return false;
    } else {
        /*$("#demo2-home").html(response)*/
        return false;
    };
    //citizenChildPoppup1(response);

    return false;
}
/*Target redirect function End*/

/*Box shift start*/
$(document).ready(function() {
	
	$(".bhoechie-tab-menu .list-group>a").click(function(e) {
	    e.preventDefault();
	    $(this).siblings('a.active').removeClass("active");
	    $(this).addClass("active");
	    var index = $(this).index();
	    $(".bhoechie-tab .bhoechie-tab-content").removeClass("active");
	    $(".bhoechie-tab .bhoechie-tab-content").eq(index).addClass("active");
	});
	
	$('.modal-body').slimScroll({
	    color: '#313131',
	    size: '8px',
	    height: '420px',
	    alwaysVisible: true,
	    touchScrollStep: 50
	});
	$('.text-contents').slimScroll({
	    color: '#313131',
	    size: '4px',
	    height: '170px',
	    alwaysVisible: true,
	    touchScrollStep: 50
	});
	$('.columsns-multilevel').slimScroll({
	    color: '#313131',
	    size: '4px',
	    height: '285px',
	    alwaysVisible: true,
	    touchScrollStep: 50
	});
	
	$('#custom, ul.banner').slimScroll({
	    color: '#313131',
	    size: '10px',
	    height: '300px',
	    alwaysVisible: true,
	    touchScrollStep: 50
	});
	
	 
	
	
    //$(".top-summary").shapeshift();
    $("#countuser2").attr("data-value", getCookie("countuser") == "" ? 0 : getCookie("countuser"));
});
/*Box shift End*/

/*Service Carousel Start */
$(document).ready(function($) {
	
	 
	 $( "#share" ).click(function() {
		  $( "ul.sticky-new" ).slideToggle( "slow", function() {
		    // Animation complete.
		  });
		});
	 
 
	
    $("#service-carousel2").owlCarousel({
        items: 2
    });
    $("#video-gallery").owlCarousel({
        items: 2
    });
    $("#carosel .owl-carousel").owlCarousel({
        autoPlay: 3000, //Set AutoPlay to 3 seconds
        pagination: false,
        items : 6,
        navigation : true,
        margin:5,
        itemsDesktop : [1199,6],
        itemsDesktopSmall : [980,3],
        itemsTablet: [768,2],
        itemsTabletSmall: false,
        itemsMobile : [479,1]
    });
	
    $("#exservices").owlCarousel({
        autoPlay: 3000, //Set AutoPlay to 3 seconds
        pagination: false,
        items : 6,
        navigation : true,
        margin:0,
        itemsDesktop : [1199,6],
        itemsDesktopSmall : [980,4],
        itemsTablet: [768,2],
        itemsTabletSmall: false,
        itemsMobile : [479,1]
    });
    
  /*  $("#carosel-services .owl-carousel").owlCarousel({
        autoPlay: 3000, //Set AutoPlay to 3 seconds
        pagination: false,
        items : 4,
       // center: true,
        navigation : true,
        margin:15,
        itemsDesktop : [1199,4],
        itemsDesktopSmall : [980,3],
        itemsTablet: [768,2],
        itemsTabletSmall: false,
        itemsMobile : [479,1]
    });*/
	
    
    
    
    
    
    
	
	
 $("#schemes-new .owl-carousel").owlCarousel({
        autoPlay: 3000, //Set AutoPlay to 3 seconds
        pagination: true,
        items : 4,
        navigation : true,
        stopOnHover:true,
        margin:5,
        itemsDesktop : [1199,6],
        itemsDesktopSmall : [980,3],
        itemsTablet: [768,2],
        itemsTabletSmall: false,
        itemsMobile : [479,1]
    });	
        
    
    $("#visitors").owlCarousel({
        autoPlay: 3000, //Set AutoPlay to 3 seconds
        pagination: false,
        items : 2,
        navigation : true,
        margin:5,
        autoHeight : true,
        itemsDesktop : [1199,4],
        itemsDesktopSmall : [980,3],
        itemsTablet: [768,2],
        itemsTabletSmall: false,
        itemsMobile : [479,1]
    });
    
    $("#service-carousel3").owlCarousel({
        items: 4
    });
    
    $('.citizenservices').scrollbox({
        linear: true,
        step: 1,
        delay: 0,
        speed: 50
    });
});
/*Service Carousel End */
/*Citizen Services script Start*/
var list = document.getElementsByClassName("tab-link");

for (var i = 0; i < list.length; i++) {
    list[i].setAttribute("data-tab", "tab-" + i);
}
var list = document.getElementsByClassName("tab_content_ser");
for (var i = 0; i < list.length; i++) {
  list[i].setAttribute("id", "tab-" + i);
}

$(document).ready(function() {
    $('ul.tabs li:first').addClass('current');
    $('.member .col-sm-8 div:first').addClass('current');
    $('ul.tabs li').click(function() {
        var tab_id = $(this).attr('data-tab');
        $('ul.tabs li').removeClass('current');
        $('.tab-content').removeClass('current');
        $(this).addClass('current');
        $("#" + tab_id).addClass('current');
    })
    
    
        $('.single-news > .row .news-image').each(function () {
            var lnk = $(this).find('a').attr('onclick');
            $('a.download', this).attr('onclick', lnk);
        });
    
    	$(".mkcmp").click(function(e){
    		$(".nidaan-contents").show();
    		$("#ckstat").hide();
    		$("#mkpay").hide();
 	        $("#mkcmp").show();
 	        $('html, body').animate({scrollTop: $("#mkcmp").offset().top - 200}, 2000);  
    	});
    	$(".ckstat").click(function(){
    		$(".nidaan-contents").show();
    		$("#mkcmp").hide();
    		$("#mkpay").hide();
    		$("#ckstat").show();
    		$('html, body').animate({scrollTop: $("#ckstat").offset().top - 200}, 2000);
    	});
    	$(".mkpay").click(function(){
    		$(".nidaan-contents").show();
    		$("#mkcmp").hide();
    		$("#ckstat").hide();
    		$("#mkpay").show();
    		$('html, body').animate({scrollTop: $("#mkpay").offset().top - 200}, 2000);
    	});
});
/*Citizen Services script End*/




$(function() {
    $('.material-card > .mc-btn-action').click(function () {
        var card = $(this).parent('.material-card');
        var icon = $(this).children('i');
        icon.addClass('fa-spin-fast');

        if (card.hasClass('mc-active')) {
            card.removeClass('mc-active');

            window.setTimeout(function() {
                icon
                    .removeClass('fa-arrow-left')
                    .removeClass('fa-spin-fast')
                    .addClass('fa-comment-o');

            }, 800);
        } else {
            card.addClass('mc-active');

            window.setTimeout(function() {
                icon
                    .removeClass('fa-comment-o')
                    .removeClass('fa-spin-fast')
                    .addClass('fa-arrow-left');

            }, 800);
        }
    });
});

function subscribe() {
	openPopup('NewsLetterSubscription.html');
}

$(function () {
    $('.scrolllistbox1').totemticker({
        row_height: '80px',
        speed: 2000,
        interval: 2000,
        mousestop: true,
    });
});

(function($) {
			

	      var content = $(".scrolllistbox"),
	          autoScrollTimer = 10000,
	          height=0,
	          autoScrollTimerAdjust, autoScroll;
	      content.mCustomScrollbar({
	          scrollButtons: {
	              enable: false
	          },
	          callbacks: {
	              whileScrolling: function() {
	                  /*autoScrollTimerAdjust = autoScrollTimer * this.mcs.topPct / 12000;*/
	                  autoScrollTimerAdjust = 500;	                 
	              },
	          }
	      });
	      content.addClass("auto-scrolling-on auto-scrolling-to-bottom");
	      AutoScrollOn("bottom");

	      $(".auto-scrolling-toggle").click(function(e) {
	          e.preventDefault();
	          if (content.hasClass("auto-scrolling-on")) {
	              AutoScrollOff();
	          } else {
	              if (content.hasClass("auto-scrolling-to-top")) {
	                  AutoScrollOn("top", autoScrollTimerAdjust);
	              } else {
	                  AutoScrollOn("bottom", autoScrollTimer - autoScrollTimerAdjust);
	              }
	          }
	      });

	      function AutoScrollOn(to, timer) {
	          if (!timer) {
	              timer = autoScrollTimer;
	          }
	          content.addClass("auto-scrolling-on").mCustomScrollbar("scrollTo", to, {
	              scrollInertia: timer,
	              scrollEasing: "easeInOutSmooth"
	          });
	          autoScroll = setTimeout(function() {
	              if (content.hasClass("auto-scrolling-to-top")) {
	                  AutoScrollOn("bottom", autoScrollTimer - autoScrollTimerAdjust);
	                  content.removeClass("auto-scrolling-to-top").addClass("auto-scrolling-to-bottom");
	              } else {
	                  AutoScrollOn("top", autoScrollTimerAdjust);
	                  content.removeClass("auto-scrolling-to-bottom").addClass("auto-scrolling-to-top");
	              }
	          }, timer);
	      }

	      function AutoScrollOff() {
	          clearTimeout(autoScroll);
	          content.removeClass("auto-scrolling-on").mCustomScrollbar("stop");
	      }
	})(jQuery);
(function($) {
	  $(window).on('load', function(){
	      var content = $(".service-content"),
	          autoScrollTimer = 8000,
	          autoScrollTimerAdjust, autoScroll;
	      content.mCustomScrollbar({
	          scrollButtons: {
	              enable: false
	          },
	          callbacks: {
	              whileScrolling: function() {
	                  autoScrollTimerAdjust = autoScrollTimer * this.mcs.topPct / 12000;
	              },
	          }
	      });
	      content.addClass("auto-scrolling-on auto-scrolling-to-bottom");
	      AutoScrollOn("bottom");

	      $(".auto-scrolling-toggle").click(function(e) {
	          e.preventDefault();
	          if (content.hasClass("auto-scrolling-on")) {
	              AutoScrollOff();
	          } else {
	              if (content.hasClass("auto-scrolling-to-top")) {
	                  AutoScrollOn("top", autoScrollTimerAdjust);
	              } else {
	                  AutoScrollOn("bottom", autoScrollTimer - autoScrollTimerAdjust);
	              }
	          }
	      });

	      function AutoScrollOn(to, timer) {
	          if (!timer) {
	              timer = autoScrollTimer;
	          }
	          content.addClass("auto-scrolling-on").mCustomScrollbar("scrollTo", to, {
	              scrollInertia: timer,
	              scrollEasing: "easeInOutSmooth"
	          });
	          autoScroll = setTimeout(function() {
	              if (content.hasClass("auto-scrolling-to-top")) {
	                  AutoScrollOn("bottom", autoScrollTimer - autoScrollTimerAdjust);
	                  content.removeClass("auto-scrolling-to-top").addClass("auto-scrolling-to-bottom");
	              } else {
	                  AutoScrollOn("top", autoScrollTimerAdjust);
	                  content.removeClass("auto-scrolling-to-bottom").addClass("auto-scrolling-to-top");
	              }
	          }, timer);
	      }

	      function AutoScrollOff() {
	          clearTimeout(autoScroll);
	          content.removeClass("auto-scrolling-on").mCustomScrollbar("stop");
	      }
	      
	     

	  });
	})(jQuery);

$(document).ready(function(){
		    $.ajax({
		        url: "HitCounterCookies.html?userActivity",
		        success: 
		          function(result){
		        	$('#activeuser').text(result['activeuser']);
		        	$('#totalRegisUser').text(result['totalRegisUser']);
		        	$('#countuser2').text(result['countuser']);
		        	$('#loggedInUser').text(result['loggedInUser']);
		           
		           setTimeout(function(){
		        	 userActivity2(); //this will send request again and again;
		           }, 10000);
		        }});
		});

function userActivity2(){
    $.ajax({
        url: "HitCounterCookies.html?userActivity",
        success: 
          function(result){
        	$('#activeuser').text(result['activeuser']);
        	$('#totalRegisUser').text(result['totalRegisUser']);
        	$('#countuser2').text(result['countuser']);
        	$('#loggedInUser').text(result['loggedInUser']);
           
        }});
}


function myFunction() {
    var x = document.getElementById("telephone-view");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
    
}
function getCheckList(serviceCode,serviceUrl,serviceName) {
	
		var url = "CitizenHome.html?getChecklist";
		var requestData = {'serviceCode':serviceCode,'serviceUrl':serviceUrl,'serviceName':serviceName};
		var response = __doAjaxRequest(url,'post',requestData,false,'html');
		if(response == ''){
			showFancyBoxWithoutClose("No Documents Required");
		}else{
			//$('.modal').html(response);
			showFancyBoxWithoutClose(response);
		}	
}

function getLogin(serviceURL){
	
		var data = "serviceURL=" + serviceURL;
		__doAjaxRequest("CitizenHome.html?storeServiceURL", 'post', data, false,'json');
		 $.fancybox.close();
		getCitizenLoginForm("");
	}


/*User Story #91836*/
function checkBookingAvailability() {
var errorList = [];
  var theForm = '#estateBookingForm';
   var requestData = {};
	requestData = __serializeForm(theForm);
    var response = __doAjaxRequest('EstateBooking.html?getBookingDetails', 'POST', requestData, false);
    $.fancybox.close();
    getCitizenLoginForm("");
    $(formDivName).html(response);
}

function showFancyBoxWithoutClose(childDialog)
{
	
	$.fancybox({
		'width':800,
        'height':535,
        'autoSize' : false,
        type: 'inline',
        href: childDialog,
        closeBtn   : false,
        openEffect  : 'elastic', // 'elastic', 'fade' or 'none'
        helpers: {
			overlay : {
				closeClick : false
			}
		},
		 keys : {
			    close  : null
			  }
    });
	
	
	return false;
}

function subscribeNewsLetter(obj) {
	
	var errorList = [];
	errorList = validateSubscribeEmail(errorList);
	if (errorList.length == 0) {
		var subscribeEmail=$("#subscribeemail").val();
		var formData ="email="+subscribeEmail;
		var response = __doAjaxRequest('CitizenHome.html?subscribe',
				'POST', formData, false);
		
		if(response=="success"){
			errorList.push(getLocalMessage("portal.newssub.save"));
			showError1(errorList);
		}else{
			errorList.push(getLocalMessage("portal.newssub.validate"));
			showError1(errorList);
		}
		
	} else {
		$('.message').hide();
		showError1(errorList);
		$(obj).prop('disabled', false);
	}
}
function showError1(errorList) {
	var errMsg = '<button type="button" class="close" onclick="closeErrBox1()"><span aria-hidden="true">&times;</span></button><ul>'
	$.each(errorList, function(index) {
		errMsg += '<li>' + errorList[index] + '</li>';
	});
	errMsg += '</ul>';
	$('.message').html(errMsg);
	$('.error-div2').hide();
	$('.message').show();
}
function closeErrBox1() {
	$('.message').hide();
}
$(document).ready(function() {
	$('#resettButon').on('click', function() {
		$('#email').val('');
		$('.error-div2').hide();
		$('.message').hide();
	});
	$('.message').hide();
});
function validateSubscribeEmail(errorList) {
	var empEmail = $('#subscribeemail').val();
	if (empEmail=='') {
		errorList.push(getLocalMessage("Feedback.clientemailId"));
	} else {
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

		if (reg.test(empEmail) == false) {
			errorList.push(getLocalMessage("citizen.valid.email.error"));

		}
	}
	return errorList;
}
function searchBillPay()
{
var formurl=$("#billtype").val();
var consumerNo = $("#billNumber").val();

var url = formurl+"?searchBillPay";
var postdata = "consumerNo="+consumerNo;
var response = __doAjaxRequest(url,'POST', postdata, false, 'html');
$('.content-page').html(response);
}


document.onscroll = function() {
    if (window.innerHeight + window.scrollY+300 > document.body.clientHeight) {
       $('.mbr-arrow').css('display','none');
    }
    else{
    	$('.mbr-arrow').css('display','block');
    }
}
	$(document).ready(function() {
	    $(".mbr-arrow a").click(function(event){
	        $('html, body').animate({scrollTop: '+=630px'}, 800);
	    });
	});
	
	$(document).ready(function() {
		/* headerMenu();
	    $(window).resize(function() {
	        headerMenu();
	    });
	
	    function headerMenu() {
	        if ($(window).width() > 1023) {
	            var bannerH = $(window).height();
	            $('.sidemenu .list-group').css('height',bannerH/2.9);
	            $('#myCarousel').css('max-height',bannerH/2.3);
	            $('#myCarousel img').css('height',bannerH/2.3);
	        }
	    }*/
	    $(document).on('click', '.smooth-scroll a[href^="#"]', function(e) {	    	
	    	 var id = $(this).attr('href');
	        var $id = $(id);
	        if ($id.length === 0) {
	            return;
	        }
	        e.preventDefault();
	        var pos = $id.offset().top;
	        $('body, html').animate({scrollTop: pos-180}, 800);
	    });
	});
	
	$(function(){		
		$(".section-nav li a").each(function(){
			if($(this).html()=="Dashboard")	{
				$(this).css('display','none');
			}
		});		
	});
	
	$(window).scroll(function (event) {
		var a = $(".table-responsives").height();			
	    if(a - $(window).scrollTop() < 75 || $('.dataTables_scrollBody').hasScrollBar() == false){
		   $("#btn-dt-navi").hide();
	   }else{
		   $("#btn-dt-navi").show();
	   }
	 });

	