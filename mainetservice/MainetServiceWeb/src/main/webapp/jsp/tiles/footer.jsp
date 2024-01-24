<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="date" class="java.util.Date" />
<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />

<link href="assets/libs/owl-carousel/owl.carousel.css" rel="stylesheet" />
<script src="assets/libs/owl-carousel/owl.carousel.min.js"></script>
<!-- Footer Start -->
  <%--   <footer class="clearfix">
      <div class="social-icons pull-left">
        <ul>
          <li class="twitter"><a href="<spring:message text="https://twitter.com/" code="eip.Twitter"/>" title="Twitter" target="_blank">Twitter</a></li>
          <li class="facebook"><a href="<spring:message text="https://www.facebook.com/" code="eip.Facebook"/>" title="Facebook"  target="_blank">Facebook</a></li>
          <li class="youtube"><a href="<spring:message text="https://www.youtube.com/" code="eip.youtube"/>" title="Youtube"  target="_blank">YouTube</a></li>
        </ul>
      </div>
      <div class="footer-links pull-right"> 
		  <a title="Privacy Policy" class="fancybox fancybox.ajax" target="_self" href="./jsp/tiles/PrivacyPolicy.jsp"><spring:message text="Privacy Policy" code="eip.Privacy" /></a> 
		  <a title="Terms & Conditions" class="fancybox fancybox.ajax" target="_self" href="./jsp/tiles/TermsCondition.jsp"><spring:message text="Terms & Conditions" code="eip.Terms" /></a> 
		  <a title="Refund / Cancellation" class="fancybox fancybox.ajax" target="_self" href="./jsp/tiles/RefundCancellation.jsp"><spring:message text="Refund / Cancellation" code="eip.Refund" /></a> 
		  <a title="Copyright &copy; ABM" href="http://abmindia.com/" target="_new"><spring:message text="Copyright &copy; ABM" code="eip.copyright" /> ${year}</a>
	  </div>
      </footer> --%>
<!-- Footer End -->
<div>
 <footer class="clearfix margin-top-30">
<div class="footer-logos hidden">
<div class="container clear">
		<div class="row">
		<div class="col-md-12 col-lg-12">
		<div id="carosel">
		<div class="owl-carousel owl-theme">
	
 					<div class="item"><a href="http://digitalindia.gov.in/" target="_blank"><img src="assets/img/digital-india.png" class="img-responsive" alt="Digital India - Power to Empower" title="Digital India - Power to Empower" ></a></div>
            		<div class="item"><a href="http://www.pmindia.gov.in/en/" target="_blank"><img src="assets/img/pmindia.png" class="img-responsive" alt="PM India" title="PM India" ></a></div>
            		<div class="item"><a href="https://www.india.gov.in/" target="_blank"><img src="assets/img/india-gov.png" class="img-responsive" alt="India.gov.in" title="India.gov.in" ></a></div>
             		<div class="item"><a href="http://goidirectory.nic.in" target="_blank"><img src="assets/img/goe-directory.png" class="img-responsive" alt="Goe Directory" title="Goe Directory" ></a></div>
             		<div class="item"><a href="https://incredibleindia.org/" target="_blank"><img src="assets/img/incredible-india.png" class="img-responsive" alt="Incredible india" title="Incredible india" ></a></div>
             		<div class="item"><a href="http://www.makeinindia.com/home" target="_blank"><img src="assets/img/makeinindia.png" class="img-responsive" alt="Make in India" title="Make in India"></a></div>
             		<div class="item"><a href="https://www.mygov.in/" target="_blank"><img src="assets/img/mygov.png" class="img-responsive" alt="My Gov" title="My Gov" ></a></div>
             		<div class="item"><a href="https://pmnrf.gov.in/" target="_blank"><img src="assets/img/pmnrf.png" class="img-responsive" alt="Prime Minister's National Relief Fund" title="Prime Minister's National Relief Fund" ></a></div>
					<div class="item"><a href="https://data.gov.in/" target="_blank"><img src="assets/img/data-gov.png" class="img-responsive" alt="open government data" title="open government data" ></a></div>
 					 <div class="item"><a href="https://services.india.gov.in/" target="_blank"><img src="assets/img/gevernment-portal.jpg" class="img-responsive" alt="service portal" title="service portal"></a></div>
 					
 		</div>
		</div>
		</div>
		</div>
</div>
</div>

<!-- VISITOR COUNT-->






 <div class="container-fluid">
	<!--Scroll To Top--> 
    <a class="tothetop" href="javascript:void(0);"><strong class="fa fa-angle-up"></strong> <span><spring:message code="Top" text="Top"/></span></a> 
	<div class="col-sm-5 col-md-5 col-xs-12">
	
	 <div class="pull-left ">
	    <ul>
	      <li><img src="assets/img/css.jpg" class="holder_img" alt="Loading please wait" title="W3C CSS standards"></li>
	      <li><img src="assets/img/html.jpg" class="holder_img" alt="Loading please wait" title="W3C XHTML standards"></li>
	      <li><img src="assets/img/uc.jpg" class="holder_img" alt="Loading please wait" title="GIGW standards"></li>
	      <li><img src="assets/img/w3c.png" class="holder_img" alt="Loading please wait" title="W3C WAI-AA WCAG standards"></li>
	    </ul>
	  </div>
	</div>
	
	<div class="footer-links col-sm-5 col-sm-offset-2  col-md-5 col-md-offset-2  col-xs-12 text-right">

	<ul>
	 <li> <i class="fa fa-info-circle" aria-hidden="true"></i><a href="#" title="Content managed by Suda" ><spring:message code="suda.content.managed" text="Content managed by Suda"/></a> </li>
 	<li><i class="fa fa-info-circle" aria-hidden="true"></i><a href="http://abmindia.com/" title="Designed and developed by ABM" ><spring:message code="website.design" text="Designed and developed by ABM"/></a> </li>
	</ul>

	</div>
  </div>
</footer>
<div class="footer-link1">
<div class="">

	<div class="footer-link1 col-sm-12 text-center">
	<ul>
<%-- 			  <a title="Privacy Policy" class="fancybox fancybox.ajax" target="_self" href="./jsp/tiles/PrivacyPolicy.jsp"><spring:message text="Privacy Policy" code="eip.Privacy" /></a> 
 --%>	
 		<li> <a href="./jsp/tiles/ScreenReader.jsp" title="<spring:message code="screen.reader" text="Screen-Reader"/>" class="fancybox fancybox.ajax" ><spring:message code="screen.reader" text="Screen-Reader"/></a>    </li>
 		    
 			<li> <a href="./jsp/tiles/Websitepolicy.jsp" title="<spring:message code="website.policies" text="Website Policies"/>" class="fancybox fancybox.ajax"><spring:message code="website.policies" text="Website Policies"/></a></li> 
 			<li> <a href="./jsp/tiles/help.jsp" title="<spring:message code="help.footer" text="Help"/>" class="fancybox fancybox.ajax"><spring:message code="help.footer" text="Help"/></a> 
 			<li><a href="./jsp/tiles/webInfo.jsp" title="<spring:message code="web.information" text="Web Information Manager"/>" class="fancybox fancybox.ajax"><spring:message code="web.information" text="Web Information Manager"/></a> </li>
 
			<li> <a href="http://abmindia.com/" title="<spring:message code="copyright" text="Copyright &copy; ABM ${currentYear}"/>" target="_blank" id="cpy"><spring:message code="copyright" text="Copyright &copy; ABM ${currentYear}"/>&nbsp;</a> </li>
 	        <%-- <c:set var="lastUpdated" value="${userSession.lastUpdated}"></c:set>
 	        <fmt:formatDate type = "date" value="${lastUpdated}"  dateStyle="SHORT" pattern="dd/MM/yyyy" var="formatedDt"/> --%>
<%--  	      <li> <a href="javascript:void(0)" title="<spring:message code="last.update" text="Last Updated on ${formatedDt}"/>" ><spring:message code="last.update" text="Last Updated on ${formatedDt}"/>&nbsp; ${formatedDt}</a> </li>
 --%>	  </ul>
	  </div>


</div>
</div>
</div>
<script>
$(function(){
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
});
</script>
<script>
/***********************  Character Converter *********************************
Compatible chars for displaying Hindi/Marathi characters
Author:ABM2144
*********************************************************************************/	
	var rawnum = ""+${currentYear};
	var langnum = "${userSession.languageId}";	
    var digits_mr = ['\u0966','\u0967','\u0968','\u0969','\u096A','\u096B','\u096C','\u096D','\u096E','\u096F'];
    var digits_en = ['0','1','2','3','4','5','6','7','8','9'];
    var finalnum="";
    function langconvnum(rawnum){for(i =0;i<rawnum.length;i++){if(langnum!="1"){finalnum+=digits_mr[rawnum.substring(i,(i+1))];}else{finalnum+=digits_en[rawnum.substring(i,(i+1))]}};document.getElementById('cpy').innerHTML += finalnum;};
    $(function(){langconvnum(rawnum)});
</script>