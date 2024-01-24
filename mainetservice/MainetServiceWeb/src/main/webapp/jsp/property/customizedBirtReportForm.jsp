<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript"
	src="js/property/customizedBirtReport.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code=""
					text="Customized property Reports" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="property.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="customizedPropertyBirtReports.html" cssClass="form-horizontal"
				id="customizedBirtReports">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="property.outstanding.report.type" text="Report Type" /></label>
                        <div class="col-sm-4">
						<form:select path="propertyDto.reportType"
							id="reportType"
							class="form-control chosen-select-no-results"
							data-rule-required="true">

<form:option value="0"><spring:message code="bill.Select" text="Select" /></form:option>
<form:option value="A"><spring:message code="prop.cust.birt.CFC.Employee.Collection" text="CFC Employee Collection Report" /></form:option>	
<form:option value="B"><spring:message code="property.customized.birt.final.assessment.register" text="Final Assessment register" /></form:option>	
<form:option value="C1"><spring:message code="property.customized.birt.day.book.collection.report" text="Day Book Collection Report" /></form:option>
<form:option value="C2"><spring:message code="prop.cust.birt.Daily.Collection.Report" text="Daily Collection Report" /></form:option>
<form:option value="C3"><spring:message code="prop.cust.birt.Demand.Detail.Collection.Register" text="Demand And Collection Register" /></form:option>	
<form:option value="C4"><spring:message code="prop.cust.birt.Demand.Detail.Collection.Register.EmpWise" text="Demand And Collection Register Employee Wise" /></form:option>	
<form:option value="C5"><spring:message code="prop.cust.birt.Demand.Collectionp.PropertyCount.Summary.WardWise" text="Demand And Collection Property Count Summary Ward Wise" /></form:option>
<form:option value="C6"><spring:message code="prop.cust.birt._Demand.Collection.PropertyCount.Summary.ZoneWise" text="Demand And Collection Property Count Summary Zone Wise" /></form:option>
<form:option value="D"><spring:message code="property.customized.birt.property.tax.history.audit" text="Property Tax History for Audit" /></form:option>
<form:option value="D1"><spring:message code="prop.cust.birt.Summary.Collection.WardWise" text="Summary Collection Register Ward Wise" /></form:option>
<form:option value="D2"><spring:message code="prop.cust.birt.Summary.Collection.ZoneWise" text="Summary Collection Register Zone Wise" /></form:option>
<form:option value="E1"><spring:message code="prop.cust.birt.Collection.Summary.Demand.Collection.Property.Count.WardWise" text="Ward Wise Demand and Collection Summary" /></form:option>	
<form:option value="E2"><spring:message code="prop.cust.birt.Collection.Summary.Demand.Collection.Property.Count.ZoneWise" text="Zone Wise Demand and Collection Summary" /></form:option>
		</form:select>
					</div>
</div>
			
			
				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveCustomBirtForm(this)">
						<spring:message code="property.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetform"
					onclick="window.location.href='customizedPropertyBirtReports.html'">
						<spring:message code="property.btn.reset" />
					</button>
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="property.btn.back"></spring:message>
					</a>
				</div>
			</form:form>
		</div>
	</div>
</div>






