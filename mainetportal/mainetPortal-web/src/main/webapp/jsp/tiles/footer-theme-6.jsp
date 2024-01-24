<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:useBean id="marathiConvert" class="com.abm.mainet.common.util.Utility"></jsp:useBean>

<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}"></c:if>


<!-- Footer starts -->
<footer>
	<div class="container">
		<div class="row footer-section">
			<!-- Scroll To Top starts -->
			<a class="tothetop scroll-top" href="javascript:void(0);">
				<%-- <strong class="fa fa-angle-up"></strong>  --%>
				<i class="fa fa-angle-up" aria-hidden="true"></i>
				<%-- <span><spring:message code="Top" text="Top"/></span> --%>
			</a>
			<!-- Scroll To Top ends -->
			<!-- Footer Section 1 starts -->
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 fs-1">
				<a class="navbar-brand" href="CitizenHome.html">
					<%-- <c:forEach items="${userSession.logoImagesList}" var="logo" varStatus="status">
						<span class="website-logo">
							<c:set var="parts" value="${fn:split(logo, '*')}" />
							<c:if test="${parts[1] eq '1'}">
								<img src="${parts[0]}" class="img-responsive"
								alt="
								<c:if test="${userSession.languageId eq 1}">${userSession.organisation.ONlsOrgname}</c:if>
								<c:if test="${userSession.languageId eq 2}">${userSession.organisation.ONlsOrgnameMar}</c:if> Logo
								">
							</c:if>
						</span>
					</c:forEach> --%>
					<span class="website-logo bg-black">
						<img src="./images/KDMC-images/kdmc_footer_logo.png" class="img-responsive" alt="Kalyan Dombivli Municipal Corporation Logo">
					</span>
					<span class="website-name">
						<c:if test="${userSession.languageId eq 1}">
							${userSession.organisation.ONlsOrgname}
						</c:if>
						<c:if test="${userSession.languageId eq 2}">
							${userSession.organisation.ONlsOrgnameMar}
						</c:if>
					</span>
				</a>
				<!-- Social Icons starts -->
				<%-- <div class="social-icons-section">
					<ul>
						<c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
							<c:if test="${media.key ne 'youtube'}">
								<li>
									<a href="${media.value}" target="new_${ count.count}" class="${media.key}">
										<i class="fa fa-${media.key}"></i>
									</a>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div> --%>
				<!-- Social Icons ends -->
			</div>
			<!-- Footer Section 1 ends -->
			
			<!-- Footer Section 2 starts -->
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 fs-2">
				<a href="ScreenReader.html" title="Screen-Reader" >
					<spring:message code="ScreenReader" text="Screen-Reader"/>
				</a>
				<a href="SectionInformation.html?editForm&rowId=&page=Website Policies" title="Website Policies" >
					<spring:message code="website.policies" text="Website Policies"/>
				</a>
				<a href="help.html" title="Help" >
					<spring:message code="help.footer" text="Help"/>
				</a>
				<a href="webInfo.html" title="Help" >
					<spring:message code="web.information" text="Web Information Manager"/>
				</a>
				<%-- <a href="webInfo.html" title="Web Information Manager" >
					<spring:message code="web.information" text="Web Information Manager"/>
				</a> --%>
				<a href="" onclick="changeULBbylink(2)">SKDCL Smart City</a>
			</div>
			<!-- Footer Section 2 ends -->
			
			<!-- Footer Section 3 starts -->
			
			<%-- <c:if test="${empty userSession.getEmployee().getEmploginname() || userSession.getEmployee().getEmploginname() eq 'NOUSER'}">
				<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus() eq 'Y'}"> --%>
	      			<c:if test="${ not empty totalRegisUser}">
						<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 fs-3">
							<ul class="visitor-count-section">
								<li>
									<span class="vc-icon"><i class="fa fa-pie-chart" aria-hidden="true"></i> </span>
									<span class="vc-text"><spring:message code="TotalVisitors" text="Total Visitors" /></span>
									<span class="vc-count">
										<c:choose>
											<c:when test="${userSession.languageId eq 1}">
												<span class="animate-number" data-value="<%=request.getSession().getAttribute("countuser")%>" id="countuser2" data-duration="3000">0</span>
											</c:when>
											<c:otherwise>
											<c:set var="countUserNo" value="${countuser}" />
												<span class="animate-number" data-value="${marathiConvert.convertToRegional('marathi',countUserNo)}" id="countuserReg" data-duration="3000">0</span>
											</c:otherwise>
										</c:choose>
									</span>
								</li>
								<li>
									<span class="vc-icon"><i class="fa fa-sign-in" aria-hidden="true"></i></span>
									<span class="vc-text">
										<c:set var="orgId" value="${userSession.organisation.orgid}" />
										<img src="<spring:message code="hitcounter.URL" arguments="${orgId}"/>" Alt="<spring:message code="left.TotalVisitors"/>"
										height="20" width="129" class="hide" />
	                    				<spring:message code="TodayVisitors" text="Today Visitors" />
									</span>
									<span class="vc-count">
										<c:choose>
											<c:when test="${userSession.languageId eq 1}">
												<span class="animate-number" data-value="<%=request.getSession().getAttribute("loggedInUser")%>" data-duration="3000" id="loggedInUser">0</span>
											</c:when>
											<c:otherwise>
											<c:set var="loggedInUserNo" value="${loggedInUser}" />
												<span class="animate-number" data-value="${marathiConvert.convertToRegional('marathi',loggedInUserNo)}" id="loggedInUserReg" data-duration="3000">0</span>
											</c:otherwise>
										</c:choose>
									</span>
								</li>
								<li>
									<span class="vc-icon"><i class="fa fa-user" aria-hidden="true"></i></span>
									<span class="vc-text"><spring:message code="ActiveUsers" text="Active Users" /></span>
									<span class="vc-count">
										 <c:choose>
											<c:when test="${userSession.languageId eq 1}">
												<span class="animate-number" data-value="<%=request.getSession().getAttribute("activeuser")%>" data-duration="3000" id="activeuser">0</span>
											</c:when>
											<c:otherwise>
											<c:set var="activeuserNo" value="${activeuser}" />
												<span class="animate-number" data-value="${marathiConvert.convertToRegional('marathi',activeuserNo)}" id="activeuserReg" data-duration="3000">0</span>
											</c:otherwise>
										</c:choose>
									</span>
								</li>
								<li>
									<span class="vc-icon"><i class="fa fa-users" aria-hidden="true"></i></span>
									<span class="vc-text"><spring:message code="RegisterUsers" text="Register Users" /></span>
									<span class="vc-count">
										<c:choose>
											<c:when test="${userSession.languageId eq 1}">
												<span class="animate-number" data-value="<%=request.getSession().getAttribute("totalRegisUser")%>" data-duration="3000" id="totalRegisUser">0</span>
											</c:when>
											<c:otherwise>
											<c:set var="totalRegisUserNo" value="${totalRegisUser}" />
												<span class="animate-number" data-value="${marathiConvert.convertToRegional('marathi',totalRegisUserNo)}" id="totalRegisUserReg" data-duration="3000">0</span>
											</c:otherwise>
										</c:choose>
									</span>
								</li>
							</ul>
						</div>
					</c:if>
				<%-- </c:if>
			</c:if> --%>
			<!-- Footer Section 3 ends -->
		</div>
	</div>
<c:if test="${empty userSession.getEmployee().getEmploginname() || userSession.getEmployee().getEmploginname() eq 'NOUSER'}">
	 <div class="footer-logos">
      <div class="container clear">
         <div class="row">
            <div class="col-md-12 col-lg-12">
               <div id="carosel-footer">
                  <div class="owl-carousel owl-theme">
                     <div class="item"><a href="http://digitalindia.gov.in/" target="_blank"><img src="assets/img/digital-india.png" class="img-responsive" alt="Digital India - Power to Empower"></a></div>
                     <div class="item"><a href="http://www.pmindia.gov.in/en/" target="_blank"><img src="assets/img/pmindia.png" class="img-responsive" alt="PM India"></a></div>
                      <div class="item"><a href="https://www.india.gov.in/" target="_blank"><img src="assets/img/india-gov.png" class="img-responsive" alt="India.gov.in"></a></div>
                     <div class="item"><a href="http://goidirectory.nic.in" target="_blank"><img src="assets/img/goe-directory.png" class="img-responsive" alt="Goe Directory"></a></div>
                     <div class="item"><a href="https://incredibleindia.org/" target="_blank"><img src="assets/img/incredible-india.png" class="img-responsive" alt="Incredible india"></a></div>
                     <div class="item"><a href="http://www.makeinindia.com/home" target="_blank"><img src="assets/img/makeinindia.png" class="img-responsive" alt="Make in India"></a></div>
                     <div class="item"><a href="https://www.mygov.in/" target="_blank"><img src="assets/img/mygov.png" class="img-responsive" alt="My Gov"></a></div>
                     <div class="item"><a href="https://pmnrf.gov.in/" target="_blank"><img src="assets/img/pmnrf.png" class="img-responsive" alt="Prime Minister's National Relief Fund"></a></div>
                     <div class="item"><a href="https://data.gov.in/" target="_blank"><img src="assets/img/data-gov.png" class="img-responsive" alt="open government data"></a></div>
                     <div class="item"><a href="https://services.india.gov.in/" target="_blank"><img src="assets/img/gevernment-portal.jpg" class="img-responsive" alt="service portal"></a></div>  
                  </div>
                  
               </div>
               
            </div>
         </div>
      </div>
   </div> 
</c:if>
	<div class="dark-shade">
		<div class="container">
			<div class="row footer-section-2">
				<!-- Footer Section Bottom starts -->
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<!-- Website Info Bottom starts -->
					<ul class="website-info-bottom">
						<li>
							<spring:message code="theme6.portal.kdmc.content.managed" text="Content Managed By KDMC"/>
						</li>
						<li>
							<a href="https://www.abmindia.com" title="Website designed, developed by ABM" target="_blank" class="external">
								<spring:message code="website.design" text="Website designed, developed by ABM"/>
							</a>
						</li>
						<%-- <li>
							<a href="http://abmindia.com/" title="Copyright &copy; ABM 2018" target="_blank">
								<spring:message code="Copyright" text="Copyright &copy; ABM 2018"/>
							</a>
						</li> --%>
						<c:set var="lastUpdated" value="${userSession.lastUpdated}"></c:set>
						<%-- <fmt:formatDate type = "date" value="${lastUpdated}"  dateStyle="SHORT"  pattern="dd/MM/yyyy" var="formatedDt"/> --%>
						<fmt:formatDate type = "both" value="${lastUpdated}"  dateStyle="SHORT"  timeStyle="SHORT"  pattern="dd/MM/yyyy  HH:mm"  var="formatedDt"/>
						<li>
							<c:choose>
									<c:when test="${userSession.languageId eq 1}">
										<spring:message code="last.update" text="Last Updated on ${formatedDt}"/>&nbsp; ${formatedDt}
									</c:when>
									<c:otherwise>
										<spring:message code="last.update" text="Last Updated on ${marathiConvert.convertToRegional('marathi',formatedDt)}"/>&nbsp;
										${marathiConvert.convertToRegional('marathi',formatedDt)}
									</c:otherwise>
								</c:choose>
						<%-- 	<a href="javascript:void(0)" title="
								<spring:message code="last.update" text="Last Updated on ${formatedDt}"/>" >
								<c:choose>
									<c:when test="${userSession.languageId eq 1}">
										<spring:message code="last.update" text="Last Updated on ${formatedDt}"/>&nbsp; ${formatedDt}
									</c:when>
									<c:otherwise>
										<spring:message code="last.update" text="Last Updated on ${marathiConvert.convertToRegional('marathi',formatedDt)}"/>&nbsp;
										${marathiConvert.convertToRegional('marathi',formatedDt)}
									</c:otherwise>
								</c:choose>
							</a> --%>
						</li>
						<li>
							<spring:message code="portal.version" text="Mainet - Version 2.0"/>
						</li>
						<c:if test="${empty userSession.getEmployee().getEmploginname() || userSession.getEmployee().getEmploginname() eq 'NOUSER'}">
							<li>
								<a href="DataArchival.html?archivedData" >
									<spring:message code="lbl.archiveData" text="Archive Data"/>
								</a>
							</li>
							<%-- <c:if test="${command.themeMap['OPINION_POLL'] ne 'I'}"> --%>
								<li class="visible-xs visible-sm">
									<a href="AdminOpinionPollOptionResponseForm.html">
										<spring:message code="theme6.portal.opinion.poll" text="Opinion Poll"/>
									</a>
								</li>
							<%-- </c:if> --%>
							<li>
								<a href="SectionInformation.html?editForm&rowId=&page=Online Citizen Guidelines">
									<spring:message code="theme6.portal.online.citizen.guidelines" text="Online Citizen Guidelines"/>
								</a>
							</li>
							<%-- <c:if test="${command.themeMap['FEEDBACK'] ne 'I'}"> --%>
								<li class="visible-xs visible-sm">
									<a href="CitizenContactUs.html" title="<spring:message code="eip.citizen.footer.feedback" text="Feedback"/>">
										<spring:message code="eip.citizen.footer.contactUs" text="Feedback"/>
									</a>
								</li>
							<%-- </c:if> --%>
						</c:if>
						<li>
			            	<%-- <a id="desktop-version">
			            		<i class="fa fa-desktop" aria-hidden="true"></i>
			            		<spring:message code="theme6.portal.desktop.version" text="Desktop Version"/>
			            	</a> --%>
							<a id="mobile-version">
								<span class="mv-1">
									<i class="fa fa-mobile" aria-hidden="true"></i>
									<spring:message code="theme6.portal.mobile.version" text="Mobile"/>
								</span>
								<span class="mv-2">
									<i class="fa fa-tablet" aria-hidden="true"></i>
									<spring:message code="theme6.portal.tab.version" text="Tab Version"/>
								</span>
							</a>
			            </li>
					</ul>
					<!-- Website Info Bottom ends -->
				</div>
				<!-- Footer Section Bottom ends -->
			</div>
		</div>
	</div>
	
	<div class="container">
		<div class="row footer-section-3">
			<!-- Browser Compatibility starts -->
			<div class="browser-compatibility col-xs-12 col-sm-6 col-md-4 col-lg-4">
				<p><spring:message code="theme6.portal.browser.compatibility" text="Best viewed with Chrome 88+, Edge 88+, Firefox 85+"/></p>
			</div>
			<!-- Browser Compatibility ends -->
			
			<!-- Subscribe To Newsletter starts -->
			<div class="subscribe-newsletter col-xs-12 col-sm-4 col-md-4 col-lg-4">
				<c:set var = "themeMapVar" value ="<%=com.abm.mainet.common.ui.model.AbstractModel.themeMap%>"/>
				<c:if test="${not empty themeMapVar }">
					<c:if test="${themeMapVar['SUBSCRIBE_NEWSLETTER'] ne 'I'}">
						<c:if test="${empty userSession.getEmployee().getEmploginname() || userSession.getEmployee().getEmploginname() eq 'NOUSER'}">
							<a href="NewsLetterSubscription.html" title="<spring:message code="theme6.portal.subscribe.newsletter" text="Subscribe To Newsletter"/>">
							<spring:message code="theme6.portal.subscribe.newsletter" text="Subscribe To Newsletter"/>
							</a>
						</c:if>
					</c:if>
				</c:if>
			</div>
			<!-- Subscribe To Newsletter ends -->
			
			<!-- Government Standards starts -->
			<div class="gov-standards col-xs-12 col-sm-6 col-md-4 col-lg-4">
				<ul>
					<li><img src="assets/img/css.jpg" class="holder_img" alt="W3C CSS standards" ></li>
					<li><img src="assets/img/html.jpg" class="holder_img" alt="W3C XHTML standards"></li>
					<li><img src="assets/img/uc.jpg" class="holder_img" alt="GIGW standards" ></li>
					<li><img src="assets/img/w3c.png" class="holder_img" alt="W3C WAI-AA WCAG standards"></li>
				</ul>
			</div>
			<!-- Government Standards ends -->
		</div>
	</div>
	
</footer>
<!-- Footer ends -->

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
