<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/trade_license/tradeLicenseApplicationForm.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script>
$("html, body").animate({ scrollTop: 0 }, "slow");

</script>

<div id="validationDiv">
	<!-- Start Content here -->
	<%-- <apptags:breadcrumb ></apptags:breadcrumb> --%>
		<div class="content">
			<div class="widget">
				<div class="widget-header text-large">
			<h2>
				<b><spring:message code="trade.license.termscondition"></spring:message></b>
				
			</h2>
				</div>
			
			
				<div class="widget-content padding">
					
					<%-- <form:form method="POST" action="TradeApplicationForm.html" class="form-horizontal" id="termsCondition" name="termsCondition"> 
						<div class="compalint-error-div">
								<jsp:include page="/jsp/tiles/validationerror.jsp" />
								<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>
						</div> --%>
						
						
								                 
									    
							 <h3 class="text-center h5-head text-bold"><spring:message code="trade.vachanpatra" /></h3>
						 		<ol>
								<li><spring:message code="content1" text="" /> <spring:message code="content1.1" text="" /> <spring:message code="content1.2" text="" /> <spring:message code="content1.3" text="" /></li>
								<li><spring:message code="content2" text="" /> <spring:message code="content2.1" text="" /> <spring:message code="content2.2" text="" /></li>
								<li><spring:message code="content3" text="" /></li>
								<li><spring:message code="content4" text="" /></li>
								<li><spring:message code="content5" text="" /></li>
								<li><spring:message code="content6" text="" /></li>
								<li><spring:message code="content7" text="" /></li>
								<li><spring:message code="content8" text="" /></li>
								<li><spring:message code="content9" text="" /></li>
								<li><spring:message code="content10" text="" /></li>
								<li><spring:message code="content11" text="" /></li>
								<li><spring:message code="content12" text="" /></li>
								<li><spring:message code="content13" text="" /></li>
								<li><spring:message code="content14" text="" /></li>
								<li><spring:message code="content15" text="" /></li>
								<li><spring:message code="content16" text="" /></li>
								<li><spring:message code="content17" text="" /></li>
								<li><spring:message code="content18" text="" /></li>
								<li><spring:message code="content19" text="" /></li>
								<li><spring:message code="content20" text="" /></li>
								<li><spring:message code="content21" text="" /></li>
								<br>
								<%-- <div class="col-sm-6">
								<p><spring:message code="date" text="" /></p>
								</div>
								
								<div class="col-sm-6">
								<div class="col-sm-4"><p class="margin-bottom-10"><spring:message code="signature"></spring:message></p></div>
								<div class="col-sm-8"><input type="Text" value="" name="ZHASTAKSHAR" id="ZHASTAKSHAR" maxlength="40" size="40"></div>
								</div>
									    
								<div class="col-sm-6">
								<p><spring:message code="place" text="" /></p>
								</div>	    
									  
								<div class="col-sm-6">
								<div class="col-sm-4"><p class="margin-bottom-10"><spring:message code="vachanmpatra.name"></spring:message> </p></div>
								<div class="col-sm-8"><input type="Text" value="" name="ZHASTAKSHAR" id="ZHASTAKSHAR" maxlength="40" size="40"></div>
								</div>	    
									
								<div class="col-sm-6 col-sm-offset-6"><div class="col-sm-4"><p class="margin-bottom-10"><spring:message code="vachanmpatra.husband/father.name"></spring:message></p></div>
								<div class="col-sm-8"> <input type="Text" value="" name="ZHASTAKSHAR" id="ZHASTAKSHAR" maxlength="40" size="40"></div>
								</div>	
									 	   --%>
									    
								</ol> 
								
								<%-- <div class="padding-top-10 text-center">
							
								<button type="button" class="btn btn-danger" id="back"
									onclick="backTermsConditionPage(this);">
									<spring:message code="trade.back"></spring:message>
								</button>
								</div> --%>
								
								
								   
						<%-- </form:form> --%>
						</div>
						</div>
						</div>
						</div>
						
				    				        
						