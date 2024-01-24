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
	src="js/property/legacyPropertyReportForm.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="prop.birt.legacy.Property.report"
					text="Property Report" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="property.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="legacyPropertyBirtReport.html" cssClass="form-horizontal"
				id="legacyBirtReport">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="property.demand.report.type" text="Report Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportType" class="form-control chosen-select-no-results"
							data-rule-required="true"  onChange="showReportType()">

		<form:option value="0"><spring:message code="bill.Select" text="Select" /></form:option>
		<form:option value="A"><spring:message code="prop.birt.Advance.Payment.Register" text="Advance Payment Register" /></form:option>
		<form:option value="ACR"><spring:message code="prop.birt.Advance.Collection.Register" text="Advance Collection Register" /></form:option>
		<form:option value="ADR"><spring:message code="prop.birt.Adjustment.Transaction.Register" text="Advance Adjustment Register" /></form:option>
		<form:option value="B"><spring:message code="prop.birt.Ward.Zone.Assesment.Register" text="Assesment Register Report" /></form:option>
		<form:option value="IPA"><spring:message code="prop.birt.Individual.Assesment.Register" text="Individual Assesment Register Report" /></form:option>
	    <form:option value="C"><spring:message code="prop.birt.Ward.Zone.Collection.Register.Report" text="Collection Register Report" /></form:option>
	    <form:option value="IPC"><spring:message code="prop.birt.Individual.Property.Collection.Register" text="Individual Property Wise Collection Register" /></form:option>
		<form:option value="D"><spring:message code="prop.birt.Ward.Zone.Demand.Register" text="Demand Register Report" /></form:option>
		<form:option value="IPD"><spring:message code="prop.birt.Individual.Property.Demand.Register" text="Individual Property Demand Register" /></form:option>
		<form:option value="DEF"><spring:message code="prop.birt.defaulter.list.Register" text="Defaulter List" /></form:option>
		<form:option value="CHE"><spring:message code="prop.birt.cheque.Register" text="Cheque Register" /></form:option>
		<form:option value="DCH"><spring:message code="prop.birt.dis.cheque.Register" text="Dishonour Cheque Register" /></form:option>
		<form:option value="CON"><spring:message code="prop.birt.consession.report" text="Concession Report" /></form:option>
		<form:option value="E"><spring:message code="prop.birt.Demand.Collection.Of.Land.Building" text="Demand And Collection Of Land And Building" /></form:option>
		<form:option value="F"><spring:message code="prop.birt.New.Arrears.Year.BreakUp.Report" text="New Arrears Year Wise Break Up Report" /></form:option>
		<form:option value="H"><spring:message code="prop.birt.New.Extended.Properties.For.Period.Report" text="Status Of Properties Report" /></form:option>
		<form:option value="I"><spring:message code="prop.birt.Tax.Wise.Demand.For.Current.Financial.Year" text="Tax Wise Demand For Current Financial Year" /></form:option>
		<form:option value="M"><spring:message code="prop.birt.Transfer.Case.Detail.Report" text="Transfer Case Detail Report" /></form:option>
		<form:option value="VDR"><spring:message code="prop.birt.Vouchers.Details.Against.Advance.Payment" text="Vouchers Details Against Advance Payment" /></form:option>
        <form:option value="N"><spring:message code="prop.birt.Ward.Wise.Year.Wise.Demand.Recovery" text="Ward Wise Year Wise Demand Recovery" /></form:option>
		<form:option value="P"><spring:message code="prop.birt.Property.Name.Address.Summary.Report" text="Property Name And Address Summary Report" /></form:option>
		<form:option value="PLR"><spring:message code="prop.birt.property.ledger.report" text="Property Ledger Report" /></form:option>	
		<%-- <form:option value="RNW"><spring:message code="prop.birt.register.Notice.Warrant.Other.Penalty.report" text="Register Of Notice Warrant Other Fees & Penalty Charge Report" /></form:option> --%>
		<form:option value="TCW"><spring:message code="prop.birt.TaxCollector.WiseCollection.Report" text="Tax Collector Wise Collection Report" /></form:option>	
		<form:option value="YTC"><spring:message code="prop.birt.YearWise.TaxWise.Collection.Summary.Report" text="Year Wise Tax Wise Collection Summary Report" /></form:option>
		<form:option value="O"><spring:message code="prop.birt.objection.hearing.report" text="Objection Register" /></form:option>		
		<form:option value="NMAM"><spring:message code="prop.birt.nmam.demand.register.report" text="NMAM Demand Register Report" /></form:option>	
		<form:option value="Note"><spring:message code="prop.birt.register.Notice.report" text="Register Of Notice Report" /></form:option>	
			
		


						</form:select>
					</div>
					
				
					
					
				</div>
				
					<div class="form-group" id="SubType" >
					<label class="col-sm-2 control-label required-control"><spring:message
							code="prop.birt.Report.Sub.Type" text="Report Sub Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportSubType" class="form-control chosen-select-no-results"
							data-rule-required="true">

		<form:option value="0"><spring:message code="bill.Select" text="Select" /></form:option>
		<form:option value="NPR"><spring:message code="prop.birt.New.Properties.Report" text="New Properties" /></form:option>
		<form:option value="EPR"><spring:message code="prop.birt.New.Extended.Properties.Report" text="Extended Properties" /></form:option>
        <form:option value="RAP"><spring:message code="prop.birt.Revised.Assessment.Properties.Report" text="Revised Assessment Properties" /></form:option>
        <form:option value="NPPD"><spring:message code="prop.birt.New.Properties.PropertyWise.Details" text="New Properties PropertyWise Details" /></form:option>
        <form:option value="EPPD"><spring:message code="prop.birt.Extended.Properties.PropertyWise.Details" text="Extended Properties PropertyWise Details" /></form:option>

		


						</form:select>
					</div>
				</div>


              <div class="form-group" id="SubType2" >
					<label class="col-sm-2 control-label required-control"><spring:message
							code="prop.birt.Report.Sub.Type" text="Report Sub Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportSubType2" class="form-control chosen-select-no-results"
							data-rule-required="true">

		<form:option value="0"><spring:message code="bill.Select" text="Select" /></form:option>
		<form:option value="obj1"><spring:message code="prop.birt.objection.hearing.register1" text="Objection & Hearing Register" /></form:option>
		<form:option value="obj2"><spring:message code="prop.birt.objection.hearing.register2" text="Objection Register 2" /></form:option>

		


						</form:select>
					</div>
				</div>


                  <div class="form-group" id="SubType3" >
					<label class="col-sm-2 control-label required-control"><spring:message
							code="prop.birt.Report.Sub.Type" text="Report Sub Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportSubType3" class="form-control chosen-select-no-results"
							data-rule-required="true">

		<form:option value="0"><spring:message code="bill.Select" text="Select" /></form:option>
		<form:option value="NMMD"><spring:message code="prop.birt.nmam.demand.report" text="Demand" /></form:option>
		<form:option value="NMMC"><spring:message code="prop.birt.nmam.collection.report" text="Collection" /></form:option>
		<form:option value="NMMB"><spring:message code="prop.birt.nmam.balance.report" text="sBalance " /></form:option>

		


						</form:select>
					</div>
				</div>

     <div class="form-group" id="SubType4" >
					<label class="col-sm-2 control-label required-control"><spring:message
							code="prop.birt.Report.Sub.Type" text="Report Sub Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportSubType4" class="form-control chosen-select-no-results"
							data-rule-required="true">

		<form:option value="0"><spring:message code="bill.Select" text="Select" /></form:option>
		<form:option value="Note1"><spring:message code="prop.birt.register.Detail.Notice.Warrant.Other.Penalty.report" text="Detail" /></form:option>
		<form:option value="Note2"><spring:message code="prop.birt.register.Summary.Notice.report" text="Summary" /></form:option>

		


						</form:select>
					</div>
				</div>



				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="savelegacyBirtForm(this)">
						<spring:message code="property.btn.submit" />
					</button>
					
					
					<button type="button" class="btn btn-warning resetSearch"
						  data-original-title="Reset"
						onclick="window.location.href='legacyPropertyBirtReport.html'">
						<spring:message code="property.btn.reset" text="Reset"></spring:message>
					</button>
					
					
					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="property.btn.back"></spring:message>
					</a>
				</div>
			</form:form>
		</div>
	</div>
</div>






