<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/adh/hoardingRegister.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>

<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" scope="request" />
<script src="assets/libs/excel-export/jquery.tablesorter.js"></script>
<script src="assets/libs/excel-export/jquery.tablesorter.pager.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>




<div class ="content">
	<div class = "widget">
		<div class="widget-header">
			<h2>
				<spring:message code="hoarding.register.table.title"
					text="Hoarding Register"></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="HoardingRegister.html"
				cssClass="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp"></jsp:include>
				<div class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
					
					<div class="form-group">
					<!-- <This is for Hierarchical> -->
						<apptags:lookupFieldSet baseLookupCode="ADZ" hasId="true"
							pathPrefix="hoardingMasterDto.hoardingZone"
							hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"  
							cssClass="form-control margin-bottom-10" showAll="true"
							columnWidth="20%" /><!-- pathPrefix="hoardingMasterDto.hoardingZone" -->
					</div>
					
					<div class="form-group">
					<!-- <This is for Hierarchical> -->
						<apptags:lookupFieldSet baseLookupCode="ADH" hasId="true"
							pathPrefix="hoardingMasterDto.hoardingTypeId"
							hasLookupAlphaNumericSort="true"
							hasSubLookupAlphaNumericSort="true"  
							cssClass="form-control margin-bottom-10" showAll="true"
							columnWidth="20%" /><!-- pathPrefix="hoardingMasterDto.hoardingTypeId" -->
					</div>
					
					<div class="text-center">
						<button type="button" class="btn btn-blue-2" 
							data-original-title="View Report" id="viewreport" 
							onClick="viewHoardingReport(this)">
						<spring:message code="adh.view.report" text="View Report"></spring:message>
						</button>

						<button type="button" class="btn btn-warning resetSearch"
							 	data-original-title="Reset"
								onclick="window.location.href='HoardingRegister.html'">
								<spring:message code="adh.reset" text="Reset"></spring:message>
						</button>
					</div>
					
					
			</form:form>
		</div>
	</div>
</div>



<%-- <div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="hoarding.register.table.title"
					text="Hoarding Register"></spring:message>
			</h2>
		</div>
		
		<div class="widget-content padding">
			<form:form action="HoardingRegister.html"
				cssClass="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp"></jsp:include>
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
					
				<div class="form-group">
					<!-- <This is for Hierarchical> -->
					<apptags:lookupFieldSet baseLookupCode="ADZ" hasId="true"
						pathPrefix="hoardingMasterDto.hoardingZone"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"  
						cssClass="form-control margin-bottom-10" showAll="false"
						columnWidth="20%" /><!-- pathPrefix="hoardingMasterDto.hoardingZone" -->
				</div>
				
				<div class="form-group">
					<!-- <This is for Hierarchical> -->
					<apptags:lookupFieldSet baseLookupCode="ADH" hasId="true"
						pathPrefix="hoardingMasterDto.hoardingTypeId"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"  
						cssClass="form-control margin-bottom-10" showAll="false"
						columnWidth="20%" /><!-- pathPrefix="hoardingMasterDto.hoardingTypeId" -->
				</div>

				<div class="text-center">
					<button type="button" class="btn btn-blue-2" 
						data-original-title="View Report" id="viewreport" onClick="report('HoardingRegister.html','hoarding')">
						<spring:message code="adh.view.report" text="View Report"></spring:message>
					</button>

					<button type="button" class="btn btn-warning resetSearch"
						 data-original-title="Reset"
						onclick="window.location.href='HoardingRegister.html'">
						<spring:message code="adh.reset" text="Reset"></spring:message>
					</button>
				</div>
				</form:form>
		</div>
		
	</div>
</div> --%>
