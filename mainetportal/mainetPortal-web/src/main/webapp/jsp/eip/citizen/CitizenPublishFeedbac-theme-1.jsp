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
   	}});
</script>

<jsp:include page="/jsp/tiles/validationerror.jsp" />
<div class="message alert" role="alert" style="display: none"></div>
<div class="error-div alert alert-danger alert-dismissable" role="alert" style="display: none"></div>
<form:form method="post" action="CitizenFeedBack.html" name="frmCitizenFeedBack" id="frmCitizenFeedBack">
   <div class="feedback">
      <div class="container-fluid dashboard-page" id="CitizenService">
         <div class="container-fluid">
            <div class="container">
               <div class="row">
                  <div class="col-md-12">
                     <div class="carousel slide" data-ride="carousel" id="quote-carousel">
                        <!-- Carousel Slides / Quotes -->
                        <div class="carousel-inner text-center">
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
                              <div class="item ${active}">
                                 <blockquote>
                                    <div class="row">
                                       <div class="col-sm-8 col-sm-offset-2">
                                          <p>${feedBack.feedBackDetails}</p>
                                          <small>${feedBack.fdUserName}</small>
                                          <c:set value="${feedBack.feedBackAnswar}" var="adminAnswars" />
                                          <c:set var="answars" value="${fn:split(adminAnswars, ':')}" />
                                          <c:forEach items="${answars}" var="answar" varStatus="loop">
                                             <c:if test="${loop.last}">
                                                <c:if test="${not empty answar }">
                                                   <c:set var="ans" value="${fn:split(answar, '|')}" />
                                                   <div class="col-sm-4 col-sm-offset-8 reply">
                                                      <span><i class="fa fa-hand-o-right" aria-hidden="true"></i></span>
                                                      <strong> Reply from ${ans[0]} : ${ans[1]}</strong>
                                                      <p>${ans[2]}</p>
                                                   </div>
                                                </c:if>
                                             </c:if>
                                          </c:forEach>
                                       </div>
                                    </div>
                                 </blockquote>
                              </div>
                           </c:forEach>
                        </div>
                        <!-- Carousel Buttons Next/Prev -->
                        <a aria-label="Go to previous Slide" data-slide="prev" href="#quote-carousel" class="left carousel-control"><i class="fa fa-chevron-left"></i></a>
                        <a aria-label="Go to next Slide" data-slide="next" href="#quote-carousel" class="right carousel-control"><i class="fa fa-chevron-right"></i></a>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      </div>
   </div>
   <div class="margin-top-10 margin-bottom-10 text-center">
      <a onclick="getfeedbackForm()" class="btn  btn-success"
         href='javascript:void(0);'>
         <span>
            <spring:message
               code="Feedback.add" text="Add Feedback" />
         </span>
      </a>
      <apptags:backButton url="CitizenHome.html" buttonLabel="feedback.Back"></apptags:backButton>
   </div>
</form:form><hr>