<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/trade_license/legacyLicenseReport.js"></script>    
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="lic.Legacy.License.Report"
					text=" Legacy License Reports" />
			</h2>
			</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="trade.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="legacyLicenseBirtReport.html"
				cssClass="form-horizontal" id="LicenseLegacyReport">
			       <jsp:include page="/jsp/tiles/validationerror.jsp" /> 
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
            
              
          <div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="" text="Report Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportType" class="form-control chosen-select-no-results"
							data-rule-required="true">

		<form:option value="0"><spring:message code="license.select" text="Select" /></form:option>
		<form:option value="A"><spring:message code="lic.License.Register.Legacy.Report" text="License Register Legacy Report" /></form:option>
		<form:option value="B"><spring:message code="lic.List.Of.Defaulter.Legacy.Report" text="List Of Defaulter Legacy Report" /></form:option>
		<form:option value="C"><spring:message code="lic.License.Renewal.Notice.Legacy.Report" text="License Renewal Notice Legacy Report" /></form:option>
		<form:option value="D"><spring:message code="lic.License.MIS.Legacy.Report" text="License MIS Legacy Report" /></form:option>
		<form:option value="E"><spring:message code="lic.License.Cancellation.Legacy.Report" text="License Cancellation Legacy Report" /></form:option>
		<form:option value="F"><spring:message code="lic.Market.License.Renewal.Legacy.Report" text="Market License Renewal Legacy Report" /></form:option>
		<form:option value="H"><spring:message code="lic.Summary.License.Register.Legacy.Report" text="Summary License Register Legacy Report" /></form:option>
		
 

						</form:select>
					</div>
				</div>


          



       
			<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveLicenseReportForm(this)">
						<spring:message code="trade.btn.submit" />
					</button>
					<button type="button" class="btn btn-warning resetSearch"
						  data-original-title="Reset"
						onclick="window.location.href='legacyLicenseBirtReport.html'">
						<spring:message code="trade.btn.reset" text="Reset"></spring:message>
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="trade.btn.back"></spring:message>
					</a>
			</div>
       </form:form>
			 </div> 
			</div>
	  </div>