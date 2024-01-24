<%@ page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility" />

<!-- <link href="https://fonts.googleapis.com/css?family=Alegreya+Sans" rel="stylesheet"> -->
<link rel="stylesheet" href="assets/libs/bootstrap/css/bootstrap.min.css">
<link href="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.min.css" rel="stylesheet" type="text/css" />
<script	src="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
	
<script src="assets/js/jquery.marquee.min.js"></script>
<link href="assets/css/style-skdcl.css" rel="stylesheet" type="text/css"  />

<script>

$(document).ready(function() {
	$('.popular-box #news-carousel').owlCarousel({
		loop : true,
		margin : 10,
		animateOut : 'slideOutDown',
		animateIn : 'flipInX',
		 autoPlay:true,
		    autoPlayTimeout:4000,
		    autoplayHoverPause:true,

		items : 1,
		nav : true,
		responsive : {
			0 : {
				items : 1
			},
			600 : {
				items : 1
			},
			1000 : {
				items : 1
			}
		}
	})
});

$(document).ready(function(){
	//Owl Caraousel-----------------------------------------------

	$( "#photo-carosel .owl-next").html('<img alt="Image Next" src="https://d1ycj7j4cqq4r8.cloudfront.net/bbb447994b253bea1bb81b002e3413b2.svg" />');
		$( "#photo-carosel .owl-prev").html('<img alt="Image Pre" src="https://d1ycj7j4cqq4r8.cloudfront.net/20bd4ea61b53e89f4d8c6531d59f19ab.svg" />');

	$('#photo-carosel .owl-carousel').owlCarousel({
		autoPlay: 3000, //Set AutoPlay to 3 seconds
		items : 4,
		margin: 20,
		stagePadding: 50,
		nav: true,
		navText:["<div class='nav-btn prev-slide'></div>","<div class='nav-btn next-slide'></div>"]
		/* responsive: {
			0: {
				items: 1
			},
			600: {
				items: 3
			},
			1000: {
				items: 4
			}
		} */
	});
	
   $(".press-release").click(function(){
	   
	    var imageId = $(this).data('id');
	    var imageList=imageId.split(",");
	    $('div#press-modal-body-div > div').remove();	
	    $('div#press-modal-body-div > img').remove();	    
	    for(i=0;i<imageList.length;i++){
			if(i==0){
				$("<div>", {
				    'class': "item active press-release-item"
				}).appendTo('.press-modal-body');
				var img = $('<img alt="Image" class="img-responsive" />').attr({
		            'id': 'myImage'+i,
		            'src': imageList[i]
		        }).appendTo(".press-release-item");
			}
			else{
				$("<div>", {
				    'class': "item press-release-item"
				}).appendTo('.press-modal-body');
				var img = $('<img alt="Image" class="img-responsive" />').attr({
		            'id': 'myImage'+i,
		            'src': imageList[i]
		        }).appendTo(".press-release-item:eq("+i+")");
			}
	    }
	    
     $('#pressreleasediv').modal('show');
   });  
});


	$(function() {
		var curHeight = $('.about1').height();
		var curHeight2 = $('.about2').height();
		if (curHeight >= 117 || curHeight2 >= 117) {
			$('.read-more').css('display', 'block');
		} else
			$('.read-more').css('display', 'none');
	});
	function showPropertyTaxCalculator(obj) {
		var data = {};
		var response = __doAjaxRequest(
				'PropertyTaxCalculator.html?viewApplication', 'POST', data,
				false);
		$(formDivName).html(response);
	}

	$(function() {
		$('.drag1 .col-lg-12').find('.col-lg-6').removeClass('col-lg-6')
				.addClass('col-lg-4');
		$('.drag1 .col-lg-12 .dark-blue').removeClass('height-390').addClass(
				'height-200');
		$('.drag1 .col-lg-12 .list-number').removeClass('col-sm-12');
		$('.drag1 .col-lg-12 .dark-blue .helpline').removeClass('help-line')
				.addClass('help-line1');
	});
</script>
<c:if
	test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}">
	<script>
		$(function() {
			$('#myCarousel').css('margin-top', '0px');
		});
	</script>
</c:if>

${command.setThemeMap()} ${command.getAboutUs()}

<div class="invisibeHead"></div>
<!-- Carousel Slider starts -->
<div class="container">
	<div class="row">
		<c:if test="${command.themeMap['SLIDER_IMG'] ne 'I'}">
		<div id="myCarousel" class="slider-main carousel slide" data-ride="carousel">
			<c:set var="maxfilecount" value="${userSession.getSlidingImgLookUps().size()}" />
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<c:if test="${maxfilecount gt 0 }">
					<c:forEach begin="0" end="${maxfilecount-1}" varStatus="index">
						<c:choose>
							<c:when test="${index.index eq 0}">
								<li data-target="#myCarousel" data-slide-to="${index.index}"
									class="active"></li>
							</c:when>
							<c:otherwise>
								<li data-target="#myCarousel" data-slide-to="${index.index}"></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:if>
			</ol>

			<!-- Wrapper for slides -->
			<div class="carousel-inner">
				<c:if test="${userSession.getSlidingImgLookUps().size() gt 0}">
					<c:forEach items="${userSession.slidingImgLookUps}" var="slide"
						varStatus="status">

						<c:set var="data" value="${fn:split(slide,'*')}" />
						<c:set var="image" value="${data[0]}" />
						<c:choose>
							<c:when
								test="${userSession.getCurrent().getLanguageId() eq 1}">
								<c:set var="caption" value="${data[1]}" />
							</c:when>
							<c:otherwise>
								<c:set var="caption" value="${data[2]}" />
							</c:otherwise>
						</c:choose>
						<c:if test="${status.index eq 0 }">
							<div class="item active">
								<img src="./${image}" alt="${caption}" />
								<c:if test="${not empty caption}">
									<div class="container hidden-xs">
										<div class="carousel-caption animate fadeInLeft">
											<h1>${caption}</h1>
										</div>
									</div>
								</c:if>
							</div>
						</c:if>
						<c:if test="${status.index ne 0 }">
							<div class="item active">
								<img src="./${image}" alt="${caption}" />
								<c:if test="${not empty caption}">
									<div class="container hidden-xs">
										<div class="carousel-caption animate fadeInLeft">
											<h1>${caption}</h1>
										</div>
									</div>
								</c:if>
							</div>
						</c:if>
					</c:forEach>
				</c:if>

			</div>

			<!-- Left and right controls -->
			<a class="left carousel-control hidden-xs" href="#myCarousel"
				data-slide="prev"><span
				class="glyphicon glyphicon-chevron-left"></span><span
				class="sr-only">Previous</span></a> <a
				class="right carousel-control hidden-xs" href="#myCarousel"
				data-slide="next"><span
				class="glyphicon glyphicon-chevron-right"></span><span
				class="sr-only">Next</span></a>
		</div>
		</c:if>
	</div>
</div>
<!-- Carousel Slider ends -->
	
<!-- Project Dashboard starts -->
<div class="container">
	<div class="row">
		<div class="project-dashboard-section">
			<ul class="nav nav-pills">
				<li class="nav-item">
					<a href="#pan-city" data-toggle="tab">
						<span class="pds-icon">
							<i class="fa fa-building-o" aria-hidden="true"></i>
						</span>
						<h5><spring:message code="theme6.portal.pan.city" text="Pan City"/></h5>
					</a>
				</li>
				<li class="nav-item">
					<a href="#abd" data-toggle="tab">
						<span class="pds-icon">
							<i class="fa fa-tasks" aria-hidden="true"></i>
						</span>
						<h5><spring:message code="theme6.portal.abd" text="ABD"/></h5>
					</a>
				</li>
				<li class="nav-item">
					<a href="#convergence" data-toggle="tab">
						<span class="pds-icon">
							<i class="fa fa-arrows-alt" aria-hidden="true"></i>
						</span>
						<h5><spring:message code="theme6.portal.convergence" text="Convergence"/></h5>
					</a>
				</li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane" id="pan-city">
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th width="8%">Sr. No.</th>
									<th width="50%">Project Name</th>
									<th width="22%">Implementing Agency</th>
									<th width="20%" class="text-r">Cost Of Project</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="4" class="text-center">No records found</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="tab-pane" id="abd">
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th width="8%">Sr. No.</th>
									<th width="50%">Project Name</th>
									<th width="22%">Implementing Agency</th>
									<th width="20%" class="text-r">Cost Of Project</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="4" class="text-center">No records found</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="tab-pane" id="convergence">
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th width="8%">Sr. No.</th>
									<th width="50%">Project Name</th>
									<th width="22%">Implementing Agency</th>
									<th width="20%" class="text-r">Cost Of Project</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="4" class="text-center">No records found</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Project Dashboard ends -->
	
<!-- Share Your Ideas starts -->
<div class="share-ideas-bg">
	<div class="container">
		<div class="row">
			<div class="share-ideas-main">
				<button type="button" class="get-started-btn"><spring:message code="theme6.portal.get.started" text="Get Started"/></button>
				<jsp:include page="/jsp/eip/citizen/CitizenFeedBackLogin.jsp" />
			</div>
		</div>
	</div>
</div>
<!-- Share Your Ideas ends -->
	
<!-- Photo and Video Gallery starts -->
<div class="container">
	<div class="row photo-video-section">
		<div class="photo-gallery-section col-xs-12 col-sm-6 col-md-6 col-lg-6">
			<div class="section-h2">
				<h2><spring:message code="theme6.portal.photo.gallery" text="Photo Gallery"/></h2>
			</div>
			<ul id="rcbrand-p-slider">
				<c:if test="${not empty command.galleryMap['photo']}"> 
				    <c:set var="links" value="${fn:split(command.galleryMap['photo'],',')}" /> 
				    <c:forEach items="${links}" var="downloadL" varStatus="count">
						<%-- <c:set var="lnk1" value="./cache/${downloadL}" />  --%>
						<c:set var="lnk1" value="./${downloadL}" /> 
						<%-- <p>${lnk1}</p> --%>
						<%-- <p>The value is ${lnk1}</p> --%>
						<li>
							<a href="${lnk1}">
								<img alt="Photo Gallery Image" class="img-responsive" src="${lnk1}"/>
							</a>
						</li>
					</c:forEach>
				</c:if>
			</ul>
		</div>
		<div class="video-gallery-section col-xs-12 col-sm-6 col-md-6 col-lg-6">
			<div class="section-h2">
				<h2><spring:message code="theme6.portal.video.gallery" text="Video Gallery"/></h2>
			</div>
			<ul id="rcbrand-v-slider">
				<c:if test="${not empty command.galleryMap['vedio']}"> 
				    <c:set var="links" value="${fn:split(command.galleryMap['vedio'],',')}" /> 
				    <c:forEach items="${links}" var="downloadL" varStatus="count">
						<%-- <c:set var="lnk1" value="./cache/${downloadL}" />  --%>
						<c:set var="lnk1" value="./${downloadL}" /> 
						<li>
							<a href="#">
								<video alt="Videos" controls>
									<source src="${lnk1}"type="video/mp4">
									<source src="movie.ogg" type="video/ogg">
									Your browser does not support the video tag.
								</video>
							</a>
						</li>
					</c:forEach>
				</c:if>
			</ul>
		</div>
	</div>
</div>
<!-- Photo and Video Gallery ends -->

<!-- Useful Links starts -->
<div class="container">
	<div class="row">
		<div class="useful-links-section col-xs-12 col-sm-12 col-md-12 col-lg-12">
			<div class="section-h2">
				<h2><spring:message code="theme6.portal.useful.links" text="Useful Links"/></h2>
			</div>
			<span class="uls-view-all">
				<a href=""><spring:message code="theme6.portal.view.all" text="View All..."/></a>
			</span>
			<ul id="usefull-links">
				<li>
					<a href="#"><img src="./images/KDMC-images/ulnk-1.png" class="img-responsive" alt="ulnk-1"></a>
				</li>
				<li>
					<a href="#"><img src="./images/KDMC-images/ulnk-2.png" class="img-responsive" alt="ulnk-2"></a>
				</li>
				<li>
					<a href="#"><img src="./images/KDMC-images/ulnk-3.png" class="img-responsive" alt="ulnk-3"></a>
				</li>
				<li>
					<a href="#"><img src="./images/KDMC-images/ulnk-4.png" class="img-responsive" alt="ulnk-4"></a>
				</li>
				<li>
					<a href="#"><img src="./images/KDMC-images/ulnk-5.png" class="img-responsive" alt="ulnk-5"></a>
				</li>
			</ul>
		</div>
	</div>
</div>
<!-- Useful Links ends -->

<script src="js/eip/citizen/guest-home.js"></script>

<script>
	$(document)
			.ready(
					function() {

						var show_per_page = 5;
						/* var number_of_items = $('#list').children('li').size(); */
						var number_of_items = $('#list').children('li').length;
						var number_of_pages = Math.ceil(number_of_items
								/ show_per_page);

						$('#list-main')
								.append(
										'<ul class="pagination"></ul><input id=current_page type=hidden><input id=show_per_page type=hidden>');
						$('#current_page').val(0);
						$('#show_per_page').val(show_per_page);

						var navigation_html = '';
						var current_link = 0;
						while (number_of_pages > current_link) {
							navigation_html += '<li><a class="page" onclick="go_to_page('
									+ current_link
									+ ')" longdesc="'
									+ current_link
									+ '">'
									+ (current_link + 1)
									+ '</a></li>';
							current_link++;
						}
						navigation_html += '';

						$('.pagination').html(navigation_html);
						$('.pagination li a.page:first').addClass('active');

						$('#list').children().css('display', 'none');
						$('#list').children().slice(0, show_per_page).css(
								'display', 'block');

					});

	function go_to_page(page_num) {

		var show_per_page = parseInt($('#show_per_page').val(), 0);
		start_from = page_num * show_per_page;
		end_on = start_from + show_per_page;
		$('#list').children().css('display', 'none').slice(start_from, end_on)
				.css('display', 'block');

		$('ul li a.active').removeClass('active');
		$('.pagination li a.page[longdesc=' + page_num + ']')
				.addClass('active');
		$('#current_page').val(page_num);
	}

	$(function() {
		$('ul.scrolllistbox-new').marquee();
	});
</script>
<script>
	$(document).ready(
			function() {
				$("div.bhoechie-tab-menu>div.list-group>a").click(
						function(e) {
							e.preventDefault();
							$(this).siblings('a.active').removeClass("active");
							$(this).addClass("active");
							var index = $(this).index();
							$("div.bhoechie-tab>div.bhoechie-tab-content")
									.removeClass("active");
							$("div.bhoechie-tab>div.bhoechie-tab-content").eq(
									index).addClass("active");
						});

				$(".header-inner").addClass('hide');

			});
</script>
<c:if
	test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">
	<script>
		$(document).ready(
				function() {
					var space = $('.minister-details').offset().top;
					if (space >= 200) {
						$('.org-select').addClass('org-select-slider').css(
								'float', 'right');
					} else {
						$('.org-select').addClass('org-select-minister');
					}
				});
	</script>
</c:if>

<c:if
	test="${(not empty command.themeMap['SLIDER_IMG']) && (command.themeMap['SLIDER_IMG'] ne 'A')}">
	<script>
		$(function() {
			$('.about-bg').css('margin-top', '65px');
		});
	</script>
</c:if>
