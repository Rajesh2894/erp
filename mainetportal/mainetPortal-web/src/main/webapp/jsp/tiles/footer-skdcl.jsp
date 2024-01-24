<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}"></c:if>
<c:if test="${empty userSession.getEmployee().getEmploginname() || userSession.getEmployee().getEmploginname() eq 'NOUSER'}">

<!-- Scroll To Top starts -->
<a class="tothetop scroll-top" href="javascript:void(0);">
	<%-- <strong class="fa fa-angle-up"></strong>  --%>
	<i class="fa fa-angle-up" aria-hidden="true"></i>
	<%-- <span><spring:message code="Top" text="Top"/></span> --%>
</a>
<!-- Scroll To Top ends -->

<!-- Footer starts -->
<footer>
	<!-- Social Icons starts -->
	<div class="social-icons-bg">
		<div class="container">
			<div class="row">
				<div class="skdcl-social-icons-section col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<h2>Follow Us On</h2>
					<ul class="social-icons-content">
						<c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
							<li>
								<a href="${media.value}" target="new_${ count.count}" class="${media.key}">
									<span class="sm-icon"><i class="fa fa-${media.key}"></i></span>
									<span class="sm-text">${media.key}</span>
								</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- Social Icons ends -->
	<div class="container">
		<div class="row footer-section">
			<!-- Footer Section 1 starts -->
			<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 fs-1">
				<a class="navbar-brand" href="CitizenHome.html">
					<c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
						<span class="website-logo">
							<c:set var="parts" value="${fn:split(logo, '*')}" />
							<c:if test="${parts[1] eq '1'}">
								<img src="${parts[0]}" class="img-responsive"
								alt="Organisation Logo"
								<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
								<c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if> Logo
								">
							</c:if>
						</span>
					</c:forEach>
					<%-- <span class="website-name">
						<c:if test="${userSession.languageId eq 1}">
							${userSession.organisation.ONlsOrgname}
						</c:if>
						<c:if test="${userSession.languageId eq 2}">
							${userSession.organisation.ONlsOrgnameMar}
						</c:if>
					</span> --%>
				</a>
				<!-- Government Standards starts -->
				<ul class="gov-standards">
					<li><img src="assets/img/css.jpg" class="holder_img" alt="W3C CSS standards" ></li>
					<li><img src="assets/img/html.jpg" class="holder_img" alt="W3C XHTML standards"></li>
					<li><img src="assets/img/uc.jpg" class="holder_img" alt="GIGW standards" ></li>
					<li><img src="assets/img/w3c.png" class="holder_img" alt="W3C WAI-AA WCAG standards"></li>
				</ul>
				<!-- Government Standards ends -->
			</div>
			<!-- Footer Section 1 ends -->
			
			<!-- Footer Section 2 starts -->
			<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 fs-2">
				<!-- Contact Us Section starts -->
				<div class="contact-us-section">
					<h4><spring:message code="theme6.portal.contact.us" text="Contact Us"/></h4>
					<p><spring:message code="theme6.portal.contact.us.text" text="Contact us for more information"/></p>
					<p>
						<span class="cus-label">
							<span class="cus-icon"><i class="fa fa-envelope" aria-hidden="true"></i></span>
							<span class="cus-text"><spring:message code="theme6.portal.email" text="Email"/></span>
						</span>
						<a href="mailto:<spring:message code="theme6.portal.email.id" text="smartkalyandevcorp@skdcl.in"/>" class="cus-info">
							<spring:message code="theme6.portal.email.id" text="smartkalyandevcorp@skdcl.in"/>
						</a>
					</p>
					<p>
						<span class="cus-label">
							<span class="cus-icon"><i class="fa fa-phone" aria-hidden="true"></i></span>
							<span class="cus-text"><spring:message code="theme6.portal.phone.number.label" text="Phone Number"/></span>
						</span>
						<a href="tel:<spring:message code="theme6.portal.phone.number" text="+91-9664362761"/>" class="cus-info">
							<spring:message code="theme6.portal.phone.number" text="+91-9664362761"/>
						</a>
					</p>
					<p>
						<span class="cus-label">
							<span class="cus-icon"><i class="fa fa-map-marker" aria-hidden="true"></i></span>
							<span class="cus-text"><spring:message code="theme6.portal.address.label" text="Address"/></span>
						</span>
						<a class="cus-info"><spring:message code="theme6.portal.address" text="Sarvode Mall, Bhanunagar Kalyan(West), Bhoiwada, Kalyan, Maharashtra 421301"/></a>
					</p>
				</div>
				<!-- Contact Us Section ends -->
			</div>
			<!-- Footer Section 2 ends -->
			
			<!-- Footer Section 3 starts -->
			<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4 fs-3">
				<!-- Resources Section starts -->
				<h4><spring:message code="theme6.portal.resources" text="Resources"/></h4>
				<ul class="resources-section">
					<li>
						<a href="CitizenHome.html" title="<spring:message code="top.home" text="Home"/>">
							<span class="rs-icon"><i class="fa fa-home" aria-hidden="true"></i></span>
							<span class="rs-text"><spring:message code="top.home" text="Home"/></span>
						</a>
					</li>
					<li>
						<a href="">
							<span class="rs-icon"><i class="fa fa-bookmark" aria-hidden="true"></i></span>
							<span class="rs-text"><spring:message code="theme6.portal.about" text="About"/></span>
						</a>
					</li>
					<li>
						<a href="">
							<span class="rs-icon"><i class="fa fa-file-text" aria-hidden="true"></i></span>
							<span class="rs-text"><spring:message code="theme6.portal.tenders" text="Tenders"/></span>
						</a>
					</li>
					<li>
						<a href="">
							<span class="rs-icon"><i class="fa fa-newspaper-o" aria-hidden="true"></i></span>
							<span class="rs-text"><spring:message code="theme6.portal.press.release" text="Press Release"/></span>
						</a>
					</li>
				</ul>
				<!-- Resources Section ends -->
				<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}">
 					<c:if test="${ not empty totalRegisUser}">
						<!-- Visitor Counter section starts -->
						<ul class="visitor-count-section">
							<li>
								<span class="vc-icon"><i class="fa fa-pie-chart" aria-hidden="true"></i> </span>
								<span class="vc-text"><spring:message code="TotalVisitors" text="Total Visitors" /></span>
								<span class="vc-count">
									<span class="animate-number" data-value="<%=request.getSession().getAttribute("countuser")%>" id="countuser2" data-duration="3000">0</span>
								</span>
							</li>
						</ul>
						<!-- Visitor Counter section ends -->
					</c:if>
				</c:if>
			</div>
			<!-- Footer Section 3 ends -->
			
			<!-- Footer Section Bottom starts -->
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<!-- Website Info Bottom starts -->
				<ul class="website-info-bottom">
					<li>
						<span class="website-info">	
							<a href="#" title="Content managed by Suda" >
								<spring:message code="theme6.portal.kdmc.content.managed" text="Content Managed By KDMC"/>
							</a>
						</span>
					</li>
					<li>
						<span class="website-info">	
							<a href="http://abmindia.com/" title="Website designed, developed by ABM" >
								<spring:message code="website.design" text="Website designed, developed by ABM"/>
							</a>
						</span>
					</li>
					<li>
						<span class="website-info">	
							<a href="http://abmindia.com/" title="Copyright &copy; ABM 2018" target="_blank">
								<spring:message code="Copyright" text="Copyright &copy; ABM 2018"/>
							</a>
						</span>
					</li>
					<c:set var="lastUpdated" value="${userSession.lastUpdated}"></c:set>
					<%-- <fmt:formatDate type = "date" value="${lastUpdated}"  dateStyle="SHORT"  pattern="dd/MM/yyyy" var="formatedDt"/> --%>
					<fmt:formatDate type = "both" value="${lastUpdated}"  dateStyle="SHORT"  timeStyle="SHORT"  pattern="dd/MM/yyyy  HH:mm a"  var="formatedDt"/>
					<li>
						<span class="website-info">	
							<a href="javascript:void(0)" title="
								<spring:message code="last.update" text="Last Updated on ${formatedDt}"/>" >
								<spring:message code="last.update" text="Last Updated on ${formatedDt}"/>&nbsp; ${formatedDt}
							</a>
						</span>
					</li>
				</ul>
				<!-- Website Info Bottom ends -->
			</div>
			<!-- Footer Section Bottom ends -->
		
		</div>
	</div>
</footer>
<!-- Footer ends -->
</c:if>

<script>
   $(function(){
	$(".navigation a[href='javascript:void(0);']").removeAttr('href');	
   	$(".section-nav li a").each(function(){
   		if($(this).html()=="Dashboard")	{
   			$(this).css('display','none');
   		}
   	});
   	$('.columsns-multilevel').slimScroll({
   	    color: '#313131',
   	    size: '4px',
   	    height: '285px',
   	    alwaysVisible: true
   	});
   });
</script>