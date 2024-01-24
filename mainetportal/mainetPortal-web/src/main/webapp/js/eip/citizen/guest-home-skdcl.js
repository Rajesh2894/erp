$(window).on('load', function(){
	var selectedContainer = $('#text-resize');
	selectedContainer.find('.content-page').removeClass('container');
});

$(document).ready(function() {
	/* ----- JS for photo and video gallery starts ----- */
	$("#rcbrand-p-slider").rcbrand({
		itemsToScroll: 1,
		visibleItems: 3,
		autoPlay: {
			enable:true,
			interval: 3000,
			pauseOnHover:true
		},
		infinite:true,
		responsiveBreakpoints: { 
			portrait: { 
				changePoint:480,
				visibleItems: 1,
				itemsToScroll: 1
			}, 
			landscape: { 
				changePoint:640,
				visibleItems: 2,
				itemsToScroll: 2
			},
			tablet: { 
				changePoint:768,
				visibleItems: 3,
				itemsToScroll: 3
			}
		}
	});
	
	$("#rcbrand-v-slider").rcbrand({
		visibleItems: 3,
		autoPlay: {
			enable:false,
			interval: 3000,
			pauseOnHover:true
		},
		itemsToScroll: 1,
		responsiveBreakpoints: { 
			portrait: { 
				changePoint:480,
				visibleItems: 1,
				itemsToScroll: 1
			}, 
			landscape: { 
				changePoint:640,
				visibleItems: 2,
				itemsToScroll: 2
			},
			tablet: { 
				changePoint:768,
				visibleItems: 3,
				itemsToScroll: 3
			}
		}
	});
	/* ----- JS for photo and video gallery ends ----- */
	
	/* ----- JS for the slider in the Useful Links section starts ----- */
	$("#usefull-links").rcbrand({
		itemsToScroll: 1,
		autoPlay: {
			enable:true,
			interval: 3000,
			pauseOnHover:true
		},
		infinite:true,
		responsiveBreakpoints: { 
			portrait: { 
				changePoint:480,
				visibleItems: 1,
				itemsToScroll: 1
			}, 
			landscape: { 
				changePoint:640,
				visibleItems: 2,
				itemsToScroll: 2
			},
			tablet: { 
				changePoint:768,
				visibleItems: 3,
				itemsToScroll: 3
			}
		}
	});
	/* ----- JS for the slider in the Useful Links section ends ----- */
	
	/* ----- JS for share your ideas section starts ----- */
	$('.get-started-btn').on('click', function(){
		$('.get-started-btn').toggleClass('si-open');
		$('.share-ideas-main').toggleClass('expand');
	});
	
	var shareIdeasMain = $('.share-ideas-main');
	shareIdeasMain.find('.padding-40').removeAttr('class');
	
	/* ----- JS for share your ideas section ends ----- */
	
	/* ----- JS to hide/show website setting starts ----- */
	$('.ws-btn').on('click', function(){
		$('.ws-icon, .website-settings').toggleClass('action-close');
	});
	/* ----- JS to hide/show website setting ends ----- */

});
