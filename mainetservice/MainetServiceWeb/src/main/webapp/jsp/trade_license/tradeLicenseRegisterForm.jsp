<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/trade_license/tradeLicenseRegister.js"></script>    
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="trade.license.register"
					text="Trade License Register" />
			</h2>
			</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="trade.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="TradeLicenseRegister.html"
				cssClass="form-horizontal" id="TradeLicenseRegisterFormReport">
			       <jsp:include page="/jsp/tiles/validationerror.jsp" /> 
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
            
             
          <div class="form-group">
					<c:set var="baseLookupCode" value="MWZ" />
					<apptags:lookupFieldSet cssClass="form-control required-control"
						baseLookupCode="MWZ" hasId="true"
						pathPrefix="tradeMaster.trdWard" hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true" showAll="true"
						isMandatory="true" />
				</div>
       
			<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveForm(this)">
						<spring:message code="trade.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetform">
						<spring:message code="trade.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="trade.btn.back"></spring:message>
					</a>
			</div>
       </form:form>
			 </div> 
			</div>
	  </div>