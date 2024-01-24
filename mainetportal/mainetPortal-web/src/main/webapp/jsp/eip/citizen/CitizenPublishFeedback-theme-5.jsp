<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%  response.setContentType("text/html; charset=utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<script> 
   var $ = jQuery.noConflict();
   $(document).ready(function() {
   	$(".header-inner").removeClass("hide");
	$('#quote-carousel').carousel({ interval: 4000, cycle: true });
   	if($("#top-header").hasClass('hide')){
   		$("#top-header").removeClass('hide');
   	}
   	/* ----- JS for Feedback Carousel starts ----- */
   	var coverflow = $("#feedbackCarousel").flipster({
   		style: 'carousel',
   		loop: true,
   		autoplay: 3000,
        spacing: -0.5,
        scrollwheel: false,
        buttons: true
   	});
   	$(window).on("resize", function (e) {
        checkScreenSize();
    });
    checkScreenSize();
    function checkScreenSize(){
        var w = $(window).width();
        if (w < 481) {
        	$('.feedback-carousel ul li blockquote').css('width', w);
        } else {
        	$('.feedback-carousel ul li blockquote').css('width', '30rem');
        }
    }
    /* ----- JS for Feedback Carousel ends ----- */
   });
</script>

<jsp:include page="/jsp/tiles/validationerror.jsp" />
<div class="message alert" role="alert" style="display: none"></div>
<div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
<div class="container">
<form:form method="post" action="CitizenFeedBack.html" name="frmCitizenFeedBack" id="frmCitizenFeedBack">
	<div class="feedback">
      <div class="dashboard-page" id="CitizenService">
    	<h2><spring:message code="theme5.portal.smart.citizen.corner" text="Smart Citizen Corner" /></h2>
		<!-- Carousel Slides / Quotes -->
		<div id="feedbackCarousel" class="feedback-carousel">
			<ul>
				<!-- Quote 1 -->
				<c:forEach items="${publishFeedBackList}" var="feedBack" varStatus="stat">
					<c:choose>
						<c:when test="${stat.index eq 0 }">
							<c:set var="active" value="active"/>
						</c:when>
						<c:otherwise>
							<c:set var="active" value=""/>
						</c:otherwise>
					</c:choose>
					<li>
						<blockquote>
							<p>${feedBack.feedBackDetails}</p>
							<small>${feedBack.fdUserName}</small>
							<c:set value="${feedBack.feedBackAnswar}" var="adminAnswars" />
							<c:set var="answars" value="${fn:split(adminAnswars, ':')}" />
							<c:forEach items="${answars}" var="answar" varStatus="loop">
								<c:if test="${loop.last}">
									<c:if test="${not empty answar }">
									<c:set var="ans" value="${fn:split(answar, '|')}" />
										<div class="reply">
											<span><i class="fa fa-hand-o-right" aria-hidden="true"></i></span>
											<strong> Reply from ${ans[0]} : ${ans[1]}</strong>
											<p>${ans[2]}</p>
										</div>
									</c:if>
								</c:if>
							</c:forEach>
						</blockquote>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="traverse-buttons">
			<a onclick="getfeedbackForm()" class="btn btn-success"
			href='javascript:void(0);'>
				<span><spring:message code="theme5.portal.add.suggestion" text="Add Suggestion" /></span>
			</a>
			<apptags:backButton url="CitizenHome.html" buttonLabel="feedback.Back"></apptags:backButton>
		</div>
   	  </div>
	</div>
</form:form>
</div><hr>