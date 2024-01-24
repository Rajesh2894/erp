<%@ page import="org.w3c.dom.Document"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<jsp:useBean id="stringUtility" class="com.abm.mainet.common.util.StringUtility" />

<c:if test="${userSession.getCurrent().getOrganisation().getDefaultStatus()=='Y'}"></c:if>
<c:if test="${empty userSession.getEmployee().getEmploginname() || userSession.getEmployee().getEmploginname() eq 'NOUSER'}">
   <div class="footer-logos">
      <div class="container clear">
         <div class="row">
            <div class="col-md-12 col-lg-12">
               <div id="carosel">
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
 
 <div id="footer">
 <div class="footer-top">
      <div class="container">
        <div class="row">
        	<div class="col-sm-6 col-md-4 col-lg-4 col-xs-12 footer-abt-pry">
        		
        		<div class="card-footer">
        		 <c:if test="${userSession.languageId eq 1}">
                 <div class="card-footer-heading">${userSession.organisation.ONlsOrgname}</div>
               </c:if>
               <c:if test="${userSession.languageId eq 2}">
                  <div class="card-footer-heading">${userSession.organisation.ONlsOrgnameMar}</div>
               </c:if> 
        			<p class="pb-3"></p>
              <p>
                1, Sarojini Naidu Marg,
                  Civil Lines,<br>
                  Prayagraj (Allahabad) -211001.
               </p>
               <p><strong><spring:message code="eip.phone" text="Phone" />:</strong>  0532-2427221</p>
               <p><strong><spring:message code="eip.email" text="E-Mail" />:</strong>  osnagarnigam@rediffmail.com</p>
              
               <c:if test="${command.userSession.socialMediaMap.size() gt 0}">
	              <div class="social-links mt-3">
	              <c:forEach var="media" items="${command.userSession.socialMediaMap}" varStatus="count">
	              			<a href="${media.value}" target="new_${ count.count}" class="${media.key}"><i class="fa fa-${media.key}"></i></a>
	              </c:forEach>
	                
	              </div>
              </c:if> 
        		</div>
        	</div>
        	<div class="col-sm-6 col-md-4 col-lg-4 col-xs-12 emergency-contact-list footer-links">
        			
        		<div class="card-footer">
        		<div class="card-footer-heading"><spring:message code="theme7.portal.emergency.no" text="Emergency Contact No." /></div>
        			 <ul>
	                  <li><i class="fa fa-angle-right"></i><a href="#">Toll Free No. : 1800 108 1818</a></li>
	                  <li><i class="fa fa-angle-right"></i><a href="#">Covid Helpline No. : +91-9567645474</a></li>
	                  <li><i class="fa fa-angle-right"></i><a href="#">PMC Call Center : 1800 108 1818</a></li>
	                  <li><i class="fa fa-angle-right"></i><a href="#">Grivance Call Center : 1800 108 1818</a></li>
	                </ul>
        		</div>
        	</div>
        	<c:if test="${ not empty totalRegisUser}">
        	<div class="col-sm-6 col-md-4 col-lg-4 col-xs-12 counters-section footer-links">
        		
        		<div class="card-footer">
        		<div class="card-footer-heading"><spring:message code="theme7.portal.total.site.counter" text="Site Counter" /></div>
        			<ul>
                  <li><i class="fa fa-angle-right"></i><a href="#"><spring:message code="theme7.portal.total.visitors" text="Total Visitors" /> : <span class="animate-number counter-numeral" data-value="<%=request.getSession().getAttribute("countuser")%>" id="countuser2"  data-duration="3000">0</span></a></li>
                  <li>
                  	<img
	                           src="<spring:message code="hitcounter.URL" arguments="${orgId}"/>"                        
	                        Alt="
	                        <spring:message code="left.TotalVisitors"/>
	                        " height="20"
	                        width="129" class="hide" />
                  <i class="fa fa-angle-right"></i><a href="#"><spring:message code="theme7.portal.today.visitors" text="Today Visitors" /> : <span class="animate-number counter-numeral" data-value="<%=request.getSession().getAttribute("loggedInUser")%>" data-duration="3000" id="loggedInUser">0</span></a></li>
                  <li><i class="fa fa-angle-right"></i><a href="#"><spring:message code="theme7.portal.active.sessions" text="Active Sessions" /> : <span class="animate-number counter-numeral" data-value="<%=request.getSession().getAttribute("activeuser")%>" data-duration="3000" id="activeuser">0</span></a></li>
                  <li><i class="fa fa-angle-right"></i><a href="#"><spring:message code="theme7.portal.register.user" text="Register Users" /> : <span class="animate-number counter-numeral" data-value="<%=request.getSession().getAttribute("totalRegisUser")%>" data-duration="3000" id="totalRegisUser">0</span></a></li>
                </ul>
        		</div>
        	</div>
        	</c:if>
        </div>
      </div>
    </div>  
  </div>
 </c:if>
<div class="fade-black">
	<footer class="clearfix">
		<div class="container">
			<div class="row">
				<!--Scroll To Top--> 
				<a class="tothetop" href="javascript:void(0);">
				   <!-- <strong class="fa fa-angle-up"></strong>  -->
				<i class="fa fa-angle-up" aria-hidden="true"></i>
				 <span>
				   <spring:message code="Top" text="Top"/>
				</span>
				</a>
				<div class="col-sm-6 col-md-6 col-lg-6">
				   <div class="web-certs">
				      <ul>
				         <li><img src="assets/img/css.jpg" class="holder_img" alt="W3C CSS standards" ></li>
				         <li><img src="assets/img/html.jpg" class="holder_img" alt="W3C XHTML standards"></li>
				         <li><img src="assets/img/uc.jpg" class="holder_img" alt="GIGW standards" ></li>
				         <li><img src="assets/img/w3c.png" class="holder_img" alt="W3C WAI-AA WCAG standards"></li>
				      </ul>
				   </div>
				</div>
				<div class="footer-links col-sm-6 col-md-6 col-lg-6">
				   <ul>
				      <li>
				         <i class="fa fa-info-circle" aria-hidden="true"></i>
				         <a href="#" title="Content managed by Suda" >
				            <spring:message code="suda.content.managed" text="Content managed by Suda"/>
				   </a>
				</li>
				<li>
				   <i class="fa fa-info-circle" aria-hidden="true"></i>
				   <a href="http://abmindia.com/" title="Website designed, developed by ABM" >
				      <spring:message code="website.design" text="Website designed, developed by ABM"/>
				            </a>
				         </li>
				      </ul>
				</div>
				
				<div class="footer-link1 col-sm-12 col-lg-12">
					<ul>
						<li>
						   <a href="ScreenReader.html" title="Screen-Reader" >
						      <spring:message code="ScreenReader" text="Screen-Reader"/>
						   </a>
						</li>
						<li>
						   <a href="Websitepolicy.html" title="Website Policies" >
						      <spring:message code="website.policies" text="Website Policies"/>
						   </a>
						</li>
						<li>
						   <a href="help.html" title="Help" >
						      <spring:message code="help.footer" text="Help"/>
						   </a>
						<li>
						   <a href="webInfo.html" title="Web Information Manager" >
						      <spring:message code="web.information" text="Web Information Manager"/>
						   </a>
						</li>
						<li>
						   <a href="http://abmindia.com/" title="Copyright &copy; ABM 2018" target="_blank">
						      <spring:message code="Copyright" text="Copyright &copy; ABM 2018"/>
						   </a>
						</li>
						<c:set var="lastUpdated" value="${userSession.lastUpdated}"></c:set>
						<%-- <fmt:formatDate type = "date" value="${lastUpdated}"  dateStyle="SHORT" pattern="dd/MM/yyyy" var="formatedDt"/> --%>
						<c:choose>
							<c:when test="${fn:contains(lastUpdated, ' 12:') || fn:contains(lastUpdated, ' 00:')}">	
								<fmt:formatDate type = "both" value="${lastUpdated}"  dateStyle="SHORT"  timeStyle="SHORT"  pattern="dd/MM/yyyy hh:mm a"  var="formatedDt"/>
							</c:when>	
							<c:otherwise>
								<fmt:formatDate type = "both" value="${lastUpdated}"  dateStyle="SHORT"  timeStyle="SHORT"  pattern="dd/MM/yyyy K:mm a"  var="formatedDt"/>
							</c:otherwise>
						</c:choose>
						<li>
							<a href="javascript:void(0)" title="
							<spring:message code="last.update" text="Last Updated on ${formatedDt}"/>
							" >
							<spring:message code="last.update" text="Last Updated on ${formatedDt}"/>
							&nbsp; ${formatedDt}</a> 
						</li>
						<li>
			            	<a href="javascript:void(0)" title="<spring:message code="portal.version" text="Mainet - Version 2.0"/>">
								<spring:message code="portal.version" text="Mainet - Version 2.0"/>
							</a>
						</li>
						<li>
						   <a href='CitizenContactUs.html' title="Contact Us">
							<spring:message code="eip.citizen.footer.contactUs" text="Contact Us" />
						</a>
						</li>
						<li><a href='DataArchival.html?archivedData' title="Faqs"> <spring:message code="lbl.archiveData" text="Web Archive" />
						</a>
						</li>
						<li><a href='CitizenFAQ.html?getFAQ' title="Faqs"> <spring:message code="top.faq" text="Faqs" />
						</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</footer>
</div>
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