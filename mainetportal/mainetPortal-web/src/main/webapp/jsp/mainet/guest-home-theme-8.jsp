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
<script src="assets/libs/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js" ></script>
<script src="assets/js/jquery.marquee.min.js"></script>
<!-- <link href="assets/css/style-blue-theme-7.css" rel="stylesheet" type="text/css"/> -->
<style>
.owl-carousel .owl-item{
	max-height:600px !important;
}
</style>
<script>
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
	
	/* ----- JS for Photo Gallery Carousel starts ----- */
	/* $(document).ready(function(){
		$('#photo-carousel .owl-carousel').owlCarousel({
			autoPlay: 3000, //Set AutoPlay to 3 seconds
	        pagination: false,
	        items : 1,
	        margin:5,
	        navigation : true,
	        navigationText: [
        	   "<i class='fa fa-chevron-left'></i>",
        	   "<i class='fa fa-chevron-right'></i>"
        	],
	        itemsDesktop : [1199,1],
	        itemsDesktopSmall : [980,1],
	        itemsTablet: [768,2],
	        itemsTabletSmall: false,
	        itemsMobile : [479,1]
		});
	}); */
	/* ----- JS for Photo Gallery Carousel ends ----- */
	
</script>
<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() ne 'Y'}">
	<script>
		$(function() {
			$('#myCarousel').css('margin-top', '0px');
		});
	</script>
</c:if>

${command.setThemeMap()}
${command.getAboutUs()}



<div id="main">
	<section id="newsScrollBar">
        <div id="newsBar">
          <div class="newsTitle col-xs-2 col-sm-2 col-md-2 col-lg-2">
            <p>
              NEWS UPDATES >>
            </p>
          </div>
          <div class="newsMarquee col-xs-10 col-sm-10 col-md-10 col-lg-10">
            <marquee behavior="" direction="">
              Lorem ipsum dolor sit amet, consectetur adipisicing elit.
              Quaerat modi, ipsa blanditiis quisquam, molestiae corporis voluptate eligendi.
            </marquee>
          </div>
        </div>
      </section>
  
<!-- Slider Start -->
	<c:if test="${command.themeMap['SLIDER_IMG'] ne 'I'}">
		<div class="container-fluid slider-main" id="container-fluid1" style="padding-left:0px;padding-right:0px;margin-bottom:0px;">			
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="sld">		
			<div class="row">
				<div id="myCarousel" class="carousel slide carousel-fade"
					data-ride="carousel" style="background: #000">
					<c:set var="maxfilecount"
						value="${userSession.getSlidingImgLookUps().size()}" />						
					<%--  Indicators --%>
					<ol class="carousel-indicators hidden-xs">
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

					<%--  Wrapper for slides --%>
					<div class="carousel-inner">

						<c:if test="${userSession.getSlidingImgLookUps().size() gt 0}">
							
							<c:forEach items="${userSession.slidingImgLookUps}" var="slide"
								varStatus="status">

								<c:set var="data" value="${fn:split(slide,'*')}" />
								<c:set var="image" value="${data[0]}" />
								<c:choose>
									<c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
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
			</div>
			</div>			
			<div class="clearfix"></div>
		</div>
	</c:if>
<%--  Slider End --%>
	<div class="clear"></div>
	
	  <section id="imageDiscription">
          <div class="container">
          <div class="imageDisandLinks">
            <p class="col-xs-2 col-sm-2 col-md-2 col-lg-2 text-center"><strong>Discription :-</strong> </p>
            <p class="col-xs-8 col-sm-8 col-md-8 col-lg-8"><marquee behavior="" direction="">Lorem ipsum dolor sit amet, consectetur adipisicing elit.
              Quaerat modi, ipsa blanditiis quisquam, molestiae corporis voluptate eligendi.</marquee></p>
            <p class="ml-4 col-xs-2 col-sm-2 col-md-2 col-lg-2 text-center requirement">Requirement</p>
          </div>

        </div>
        </section>
	
		
		<section id="testimonials" class="testimonials section-bg">
          <div class="container">
            <div class="row">
              <div class="d-flex flex-column justify-content-center align-items-stretch">
                  <div class="content testimonials-msg" data-aos="fade-up">
                      <div class="section-title">
                        <p> <spring:message code="theme8.lovely.city" text="OUR LOVELY CITY"/></p>
                        <h2></h2>
                      </div>
                    <div class="ourLovelyCity row">
                      <div class="mt-3 col-xs-12 col-sm-12 col-md-8 col-lg-8">
                        <img style="width: 100%;" src="./assets/img/our-lovely-city.png" alt="">
                      </div>
                      <div class="pl-5 mt-3 col-xs-12 col-sm-12 col-md-4 col-lg-4">
                        <h4><strong><spring:message code="theme8.citizens.communication" text="Citizens Communication"/></strong></h4>
                        <p style="font-size: 15px;">Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's
                          standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.
                          It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.
                          It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages.
                        </p>
                          
                      </div>
                    </div>

                      

                  </div>
              </div>
             
            </div>
          </div>
           
          
        </section>
        
        
        <section id="generalInformation">
          <div class="container pb-5">
          <div class="section-title padding-top-20">
            <p><spring:message code="theme8.general.information" text="General Information"/></p>
            <h2></h2>
          </div>
            <div class="row">
              <div class="discoverInfo col-xs-12 col-sm-7 col-md-7 col-lg-7 margin-top-10" style="height:420px;">
                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                <h4><spring:message code="theme8.dept.information" text="DEPARTMENT INFO"/></h4>
                <p style="font-size: 12px;">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Deleniti corrupti quos fugiat maiores et fugit ipsa odio, asperiores veritatis sit dolorem eligendi qui quod, beatae error ducimus. Vel, sed magni!</p>
                <img src="./assets/img/arrow.png" alt="">
                <span class="margin-top-45 secondheading">
                <h4 class="margin-top-45"><spring:message code="theme8.work.awarded" text="WORK ORDERS AWARDED"/></h4>
                <p style="font-size: 12px;">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Deleniti corrupti quos fugiat maiores et fugit ipsa odio, asperiores veritatis sit dolorem eligendi qui quod, beatae error ducimus. Vel, sed magni!</p>
                <img class="margin-top-20" src="./assets/img/arrow.png" alt="">
              </span>
                </div>
                <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
                <img width="100%" src="./assets/img/train.png" alt="">
                <img class="margin-top-45" width="100%" src="./assets/img/WORK-ORDERS-AWARDED.jpeg" alt="">
                </div>
                
              </div>
              <div class="activityInfo pt-3 col-xs-12 col-sm-4 col-md-4 col-lg-4 margin-top-10 text-center" style="height:420px;">
                <h4><spring:message code="theme8.activities" text="Activities & Initiatives"/></h4>
                <div class="card1">
                  <img src="./assets/img/amrith-mahotsav.png" alt="">
                  <p style="width:90%; margin-left: 18px;">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Deleniti corrupti quos fugiat maiores et fugit ipsa.</p>
                </div>
                <div class="card1 mt-5 pt-5">
                  <img src="./assets/img/g20.png" alt="">
                  <p style="width:90%; margin-left: 18px;">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Deleniti corrupti quos fugiat maiores et fugit ipsa.</p>
                </div>
              </div>
              </div>       
          </div>
        </section>

        <section id="projects" class="projects">
          <div class="container" data-aos="fade-up">
            <div class="section-title">
              <p><spring:message code="theme8.smartcity.challenges" text="Smart City Challenges"/></p>
              <h2></h2>
            </div>
            <div class="projects-carousel" id="project-slider">
              <div class="project-wrap">
                <div class="projects-item">
                    <div class="row">
                        <div class="col-lg-7">
                          <p class="project-text">
                            <span class="large-text">L</span>orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
                            <br /><br />
                            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
                        </p>
                        </div>
                        <div class="col-lg-5" style="background:#F08726; padding:2rem;">
                        <img src="assets/img/bridge-11.jpeg" style="width:100%" alt=""></div>
                    </div>
                </div>
              </div>

              <div class="project-wrap">
                <div class="projects-item">
                    <div class="row">
                    	<div class="col-lg-5" style="background:#366FB4; height: 520px; padding:2rem;">
                          <img width="100%"  src="assets/img/bridge-11.jpeg" alt="">
                        </div>
                        <div class="col-lg-7">
                          <p class="project-text">
                            <span class="large-text">L</span>orem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
                            <br /><br />
                            Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
                        </p>
                        </div>
                        
                      </div>
                    </div>
                </div>
              </div>

            </div>
          </div>      
        </section>
        
        <section id="news" class="news">
          <div class="container" data-aos="fade-up">

            <div class="section-title">
              <p><spring:message code="theme8.news.updates" text="News & Updates"/></p>
              <h2></h2>
            </div>

            <div class="news-carousel news-flex">
              <div class="news-wrap">
                <div class="news-item" data-aos="zoom-in" data-aos-delay="100">
                  <div class="news-box rounded"><img src="assets/img/newsandevens-4.jpg" width="100%">
                    <div class="news-content">
                      <h3>Smart City Cycle Station</h3>
                      <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry.</p>
                    </div>
                  </div>
                </div>
              </div>
              

              <div class="news-wrap">
                <div class="news-item" data-aos="zoom-in" data-aos-delay="100">
                  <div class="news-box rounded"><img src="assets/img/kalyan temple.jpeg" width="100%" height="330px">
                    <div class="news-content">
                      <h3>Kalyan Birla Temple</h3>
                      <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry.</p>
                    </div>
                  </div>
                </div>
              </div>

              <div class="news-wrap">
                <div class="news-item" data-aos="zoom-in" data-aos-delay="100">
                  <div class="news-box rounded"><img src="assets/img/kalyan bridge.png" width="100%" height="330px">
                    <div class="news-content">
                      <h3>Kalyan Bridge</h3>
                      <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry.</p>
                    </div>
                  </div>
                </div>
              </div>

               <div class="news-wrap">
                <div class="news-item" data-aos="zoom-in" data-aos-delay="100">
                  <div class="news-box rounded"><img src="assets/img/kalyan railway.png" width="100%" height="330px">
                    <div class="news-content">
                      <h3>Kalyan Railway Station</h3>
                      <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry.</p>
                    </div>
                  </div>
                </div>
              </div>

            </div>
            <div class="text-center mt-5"><a href="#" class="get-started-btn">Go To News & Updates</a></div>
          </div>
       </section>
       
       
       <section id="connect">
        <div class="connectWithUs mt-5 mb-5 container">
          <div class="section-title">
            <p>Stay Connected</p>
            <h2></h2>
          </div>
               <div class="row">
                 
                 <div class="col-sm-4">
                  <div class="faceBook">
                    <h2>FaceBook</h2>
                    <div class="faceBox mt-4">
                      <!-- <iframe src="http://www.facebook.com" style="width:100%;height:100%"></iframe> -->
                      <iframe src="https://www.facebook.com/plugins/page.php?href={your_facebook_page}&tabs=timeline&width=340&height=300&small_header=false&adapt_container_width=true&hide_cover=false&show_facepile=true&appId" width="350" height="450" style="border:none;overflow:hidden" scrolling="no" frameborder="0" allowfullscreen="true" allow="autoplay; clipboard-write; encrypted-media; picture-in-picture; web-share"></iframe>

                    </div>
                  </div>
                 </div>

                 <div class="col-sm-4">
                  <div class="instagram">
                    <h2>Instagram</h2>
                    <div class="instaBox mt-4">
                      <iframe src="https://www.instagram.com/p/CGnw1SIhPs1/embed" width="350" height="450" frameborder="0" scrolling="no" allowtransparency="true"></iframe>
                    </div>
                  </div>
                 </div>

                 <div class="col-sm-4">
                  <div class="twitter">
                    <h2>Twitter</h2>
                    <div class="twitterBox mt-4">
                       <iframe src="https://www.instagram.com/p/CGnw1SIhPs1/embed" width="350" height="450" frameborder="0" scrolling="no" allowtransparency="true"></iframe>
                    </div>
                  </div>
                 </div>
              </div>
        </div>
       </section>
        

<script src="js/eip/citizen/guest-home.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
      $('#p-image-container').desoSlide({
        thumbs: $('#p-slider li > a'),
        auto: {
            start: true
        },
        controls: {
            show:  true,
            keys:  true
        },
        first: 0,
        interval: 6000
    });
    })
  </script>
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
$(document).ready(function() {
    $("div.bhoechie-tab-menu>div.list-group>a").click(function(e) {
        e.preventDefault();
        $(this).siblings('a.active').removeClass("active");
        $(this).addClass("active");
        var index = $(this).index();
        $("div.bhoechie-tab>div.bhoechie-tab-content").removeClass("active");
        $("div.bhoechie-tab>div.bhoechie-tab-content").eq(index).addClass("active");
    });
    
	  $(".header-inner").addClass('hide');
	
});

	
</script>
<c:if
	test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">
	<script>
/* $(document).ready(function() {
		var space=$('.minister-details').offset().top;
		if(space>=200){
		 $('.org-select').addClass('org-select-slider').css('float','right');
			/* $('.minister-div').removeClass('col-lg-9').addClass('col-lg-12');
			$('.minister-div').removeClass('col-md-8').addClass('col-md-12'); */
			
		/* }else{
			$('.org-select').addClass('org-select-minister');
			/* $('.minister-div').removeClass('col-lg-12').addClass('col-lg-9');
			$('.minister-div').removeClass('col-md-12').addClass('col-md-8'); */
	//	} 
//}); 

</script>
</c:if>

<c:if test="${(not empty command.themeMap['SLIDER_IMG']) && (command.themeMap['SLIDER_IMG'] ne 'A')}">  
 <script>
$(function(){
	$('.about-bg').css('margin-top','65px');	
});


</script>
</c:if>
	