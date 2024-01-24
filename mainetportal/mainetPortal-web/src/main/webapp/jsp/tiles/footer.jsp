<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>




<c:if test="${empty userSession.getEmployee().getEmploginname() || userSession.getEmployee().getEmploginname() eq 'NOUSER'}">
<div class="white container-fluid clear">
    <div class="row">
    <div class="col-md-12 col-lg-12 no-padding">
          
                 <div id="carosel">
                <div class="owl-carousel owl-theme">
            		<div class="item"><a href="http://digitalindia.gov.in/"><img src="assets/img/digital-india.png" class="img-responsive" alt="Digital India - Power to Empower" title="Digital India - Power to Empower"></a></div>
            		<div class="item"><a href="https://www.india.gov.in/"><img src="assets/img/india-gov.png" class="img-responsive" alt="India.gov.in" title="India.gov.in"></a></div>
            		<div class="item"><a href="http://eci.nic.in/"><img src="assets/img/eci.png" class="img-responsive" alt="eci"></a></div>
             		<div class="item"><a href="http://goidirectory.nic.in"><img src="assets/img/goe-directory.png" class="img-responsive" alt="Goe Directory" title="Goe Directory"></a></div>
             		<div class="item"><a href="https://incredibleindia.org/"><img src="assets/img/incredible-india.png" class="img-responsive" alt="Incredible india" title="Incredible india"></a></div>
             		<div class="item"><a href="http://www.makeinindia.com/home"><img src="assets/img/makeinindia.png" class="img-responsive" alt="Make in India" title="Make in India"></a></div>
             		<div class="item"><a href="https://www.mygov.in/"><img src="assets/img/mygov.png" class="img-responsive" alt="My Gov" title="My Gov"></a></div>
             		<div class="item"><a href="https://pmnrf.gov.in/"><img src="assets/img/pmnrf.png" class="img-responsive" alt="Prime Minister's National Relief Fund" title="Prime Minister's National Relief Fund"></a></div>
                 </div>
              </div>
            </div>
          
        </div>
    </div>

<!-- VISITOR COUNT-->
<c:if test="${ not empty totalRegisUser}">
	<div class="white container-fluid padding-10 visitors-counter">
		<div class="container">
			<div class="col-sm-3 col-md-6 col-lg-3 col-xs-12"
				style="border-right: 1px #fff dotted;">
				<ul>
					<li><i class="fa fa-pie-chart text-primary"></i> <spring:message
							code="TotalVisitors" text="Total Visitors" /> <span
						class="animate-number badge pull-right"
						data-value="<%=request.getSession().getAttribute("countuser")%>"
						id="countuser2" data-duration="3000">0</span></li>
				</ul>
			</div>

			<div class="col-sm-3 col-md-6 col-lg-3 col-xs-12"
				style="border-right: 1px #fff dotted;">
				<ul>
					<li><c:set var="orgId"
							value="${userSession.organisation.orgid}" /> <img alt="Organisation Logo"
						src="<spring:message code="hitcounter.URL" arguments="${orgId}"/>"
						title="<spring:message code="left.TotalVisitors"/>"
						Alt="<spring:message code="left.TotalVisitors"/>" height="20"
						width="129" border="0" class="hide" /> <i
						class="fa fa-calendar-check-o text-danger"></i> Today Visitors <span
						class="animate-number badge pull-right"
						data-value="<%=request.getSession().getAttribute("loggedInUser")%>"
						data-duration="3000">0</span></li>
				</ul>
			</div>


			<div class="col-sm-3 col-md-6 col-lg-3 col-xs-12"
				style="border-right: 1px #fff dotted;">
				<ul>
					<li><i class="fa fa-user text-success"></i> <spring:message
							code="ActiveUsers" text="Active Users" /> <span
						class="animate-number badge pull-right"
						data-value="<%=request.getSession().getAttribute("activeuser")%>"
						data-duration="3000">0</span></li>
				</ul>
			</div>


			<div class="col-sm-3 col-md-6 col-lg-3 col-xs-12">
				<ul>
					<li class="facebook-color"><i class="fa fa-users text-info"></i>
						<spring:message code="RegisterUsers" text="Register Users" /> <span
						class="animate-number badge pull-right"
						data-value="<%=request.getSession().getAttribute("totalRegisUser")%>"
						data-duration="3000">0</span></li>
				</ul>
			</div>
		</div>
	</div>
</c:if>
</c:if>



<c:if test="${empty userSession.getEmployee().getEmploginname() || userSession.getEmployee().getEmploginname() eq 'NOUSER'}">


<!-- VISITOR COUNT-->

<c:if test="${ not empty totalRegisUser}">
	<div class="white container-fluid padding-10 visitors-counter">
		<div class="container">
			<div class="col-sm-3 col-md-6 col-lg-3 col-xs-12"
				style="border-right: 1px #fff dotted;">
				<ul>
					<li><i class="fa fa-pie-chart text-primary"></i> <spring:message
							code="TotalVisitors" text="Total Visitors" /> <span
						class="animate-number badge pull-right"
						data-value="<%=request.getSession().getAttribute("countuser")%>"
						id="countuser2" data-duration="3000">0</span></li>
				</ul>
			</div>

			<div class="col-sm-3 col-md-6 col-lg-3 col-xs-12"
				style="border-right: 1px #fff dotted;">
				<ul>
					<li><c:set var="orgId"
							value="${userSession.organisation.orgid}" /> <img alt="Organisation Logo"
						src="<spring:message code="hitcounter.URL" arguments="${orgId}"/>"
						title="<spring:message code="left.TotalVisitors"/>"
						Alt="<spring:message code="left.TotalVisitors"/>" height="20"
						width="129" border="0" class="hide" /> <i
						class="fa fa-calendar-check-o text-danger"></i><spring:message code="TodayVisitors" text="Today Visitors" /> <span
						class="animate-number badge pull-right"
						data-value="<%=request.getSession().getAttribute("loggedInUser")%>"
						data-duration="3000" id="loggedInUser">0</span></li>
				</ul>
			</div>


			<div class="col-sm-3 col-md-6 col-lg-3 col-xs-12"
				style="border-right: 1px #fff dotted;">
				<ul>
					<li><i class="fa fa-user text-success"></i> <spring:message
							code="ActiveUsers" text="Active Users" /> <span
						class="animate-number badge pull-right"
						data-value="<%=request.getSession().getAttribute("activeuser")%>"
						data-duration="3000" id="activeuser">0</span></li>
				</ul>
			</div>


			<div class="col-sm-3 col-md-6 col-lg-3 col-xs-12">
				<ul>
					<li class="facebook-color"><i class="fa fa-users text-info"></i>
						<spring:message code="RegisterUsers" text="Register Users" /> <span
						class="animate-number badge pull-right"
						data-value="<%=request.getSession().getAttribute("totalRegisUser")%>"
						data-duration="3000" id="totalRegisUser">0</span></li>
				</ul>
			</div>
		</div>
	</div>
</c:if>

</c:if>




 <footer class="clearfix">
 <div class="container">
	<!--Scroll To Top--> 
    <a class="tothetop" href="javascript:void(0);"><strong class="fa fa-angle-up"></strong><span><spring:message code="Top" text="Top"/></span></a> 
	
	 <div class="pull-left">
	    <ul>
	      <li><img src="assets/img/css.jpg" class="holder_img" alt="Loading please wait"></li>
	      <li><img src="assets/img/html.jpg" class="holder_img" alt="Loading please wait"></li>
	      <li><img src="assets/img/uc.jpg" class="holder_img" alt="Loading please wait"></li>
	      <li><img src="assets/img/w3c.png" class="holder_img" alt="Loading please wait"></li>
	    </ul>
	  </div>
	  <div class="footer-links pull-right">
	  	<a class="fancybox fancybox.iframe" href="./jsp/tiles/EIPguidelines.jsp"><spring:message code="eip.citizen.online.guidelines" text=""/></a> 
	      <a onclick="openPopup('CitizenFeedBack.html?publishFeedBack')" href='javascript:void(0);'><span> <spring:message code="Feedback" text="Feedback"/></span></a>
	      <a href="ScreenReader.html" title="Screen-Reader"><spring:message code="ScreenReader" text="Screen-Reader"/></a>  
	      <a href="Privacypolicy.html" title="Privacy Policy"><spring:message code="PrivacyPolicy" text="Privacy Policy"/></a> 
	      <a href="Termsconditions.html" title="Terms & Conditions"><spring:message code="TermsConditions" text="Terms & Conditions"/></a> 
	      <a href="Refundcancellation.html" title="Refund / Cancellation"><spring:message code="RefundCancellation" text="Refund / Cancellation"/></a> 
	      <a href="http://abmindia.com/" title="Copyright &copy; ABM 2015"><spring:message code="Copyright" text="Copyright &copy; ABM 2017"/></a> 
	  </div>
  </div>
</footer>