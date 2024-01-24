<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="js/water/legacyWaterBirtReportForm.js"></script>
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
					text="Water Reports" />
			</h2>
		</div>
		
		
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="water.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="legacyWaterBirtReports.html"
				cssClass="form-horizontal" id="ReportForm">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

			
			<div class="form-group">
					<label class="col-sm-2 control-label required-control"><spring:message
							code="water.report.type" text="Report Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportType" class="form-control chosen-select-no-results"
							data-rule-required="true"  onChange="showReportType()">

		<form:option value="0"><spring:message code="water.report.select" text="Select" /></form:option>
		<form:option value="E"><spring:message code="water.report.bill.gen.status" text="Bill Generation Status Report " /></form:option>
		<form:option value="F"><spring:message code="water.report.bill.receipt.report" text="Bill And Receipt Report" /></form:option>
		<form:option value="B"><spring:message code="water.report.connection.adj.detail" text="Connection Adjustment Detail" /></form:option>
		<form:option value="A"><spring:message code="water.report.demand.recovery.arrear.current" text="Demand and Recovery Of Arrears and Current Ward Wise" /></form:option>
	    <form:option value="D"><spring:message code="water.report.defaulter" text="Defaulter Register" /></form:option>
	    <form:option value="DRAC"><spring:message code="water.report.demand.recovery.conn.wise" text="Demand Recovery Of Arrear Current Connection Wise" /></form:option>
		<form:option value="C"><spring:message code="water.report.closed.connection" text="List Of Closed Connection" /></form:option>
		<form:option value="LCR"><spring:message code="water.report.list.ccn.no.reading" text="List Of CCN Which Has No Reading Before Bill Generation" /></form:option>
		<form:option value="LCW"><spring:message code="water.report.list.consumer.ward.grp" text="List Of Consumer Ward & Group Wise" /></form:option>
		<form:option value="RNF"><spring:message code="water.report.register.notice.warrant.other.penalty.charge" text="Register Of Notice Warrant Other Fees Penalty Charge " /></form:option>
		<form:option value="SMR"><spring:message code="water.report.summary.meter.Reading.data.entry" text="Summary Of Meter Reading Data Entry " /></form:option>
		<form:option value="SSY"><spring:message code="water.report.summary.statement.year.head.wise.Collection" text="Summary Statement Year Head Wise Collection" /></form:option>
		<form:option value="W1"><spring:message code="water.report.wz.list.pending.bill" text="Ward Zone Wise List Of Pending Bills" /></form:option>
		<form:option value="W2"><spring:message code="water.report.ward.connection.no.last.reading.detail" text="Ward Connection No. Wise Last Reading Detail " /></form:option>
		<form:option value="W3"><spring:message code="water.report.ward.category.conn.size.total.conn" text="Ward Category Connection Size Wise Total Connection" /></form:option>
		<form:option value="W4"><spring:message code="water.wz.consumer.arrear.current.bil.amount" text="Ward Zone Consumer Wise Arrear Current Bill Amount" /></form:option>
		<form:option value="W5"><spring:message code="water.report.wz.wise.colln" text="Ward Wise Zone Wise Collection" /></form:option>
		<form:option value="WMD"><spring:message code="water.report.meter.detail" text="Water Meter Detail" /></form:option>
		</form:select>
		</div>
					
					</div>
					
					
				
				
				<div class="form-group" id="SubType1" >
					<label class="col-sm-2 control-label required-control"><spring:message
							code="water.report.subtype" text="Report Sub Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportSubType1" class="form-control chosen-select-no-results"
							data-rule-required="true">
	    <form:option value="0"><spring:message code="bill.Select" text="Select" /></form:option>
		<form:option value="A1"><spring:message code="water.report.demand.recovery.arrear.current.period.wise" text="Period Wise" /></form:option>
		<form:option value="A2"><spring:message code="water.report.demand.recovery.arrear.current.asOnDate" text="As On Date" /></form:option>
		</form:select>
					</div>
				</div>
					
				
               <div class="form-group" id="SubType2" >
					<label class="col-sm-2 control-label required-control"><spring:message
							code="water.report.subtype" text="Report Sub Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportSubType2" class="form-control chosen-select-no-results"
							data-rule-required="true">

		<form:option value="0"><spring:message code="bill.Select" text="Select" /></form:option>
		<form:option value="B1"><spring:message code="water.report.conn.adj.detail.normalize" text="Normalize Report" /></form:option>
		<form:option value="B2"><spring:message code="water.report.conn.adj.detail.conn.no.wise" text="Connection No. Wise" /></form:option>
		</form:select>
					</div>
				</div>
					
				
                  <div class="form-group" id="SubType3" >
					<label class="col-sm-2 control-label required-control"><spring:message
							code="water.report.subtype" text="Report Sub Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportSubType3" class="form-control chosen-select-no-results"
							data-rule-required="true">

		<form:option value="0"><spring:message code="bill.Select" text="Select" /></form:option>
		<form:option value="C1"><spring:message code="water.report.closed.conn" text="Closed Connection Report" /></form:option>
		<form:option value="C2"><spring:message code="water.report.closed.conn.arr.wise" text="Arrear Wise" /></form:option>
        </form:select>
					</div>
				</div>


            <div class="form-group" id="SubType4" >
					<label class="col-sm-2 control-label required-control"><spring:message
							code="water.report.subtype" text="Report Sub Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportSubType4" class="form-control chosen-select-no-results"
							data-rule-required="true">

		<form:option value="0"><spring:message code="bill.Select" text="Select" /></form:option>
		<form:option value="D1"><spring:message code="water.report.default.detail" text="Detail" /></form:option>
		<form:option value="D2"><spring:message code="water.report.default.summary" text="Summary" /></form:option>
      </form:select>
					</div>
				</div>
			
			<div class="form-group" id="SubType5" >
					<label class="col-sm-2 control-label required-control"><spring:message
							code="water.report.subtype" text="Report Sub Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportSubType5" class="form-control chosen-select-no-results"
							data-rule-required="true">

		<form:option value="0"><spring:message code="bill.Select" text="Select" /></form:option>
		<form:option value="E1"><spring:message code="water.report.consumption.demand" text="Consumption & Demand Of Generated Bills" /></form:option>
		<form:option value="E2"><spring:message code="water.report.list.bills.not.gen" text="List Of Bills Not Generated Connections" /></form:option>
      </form:select>
					</div>
				</div>
				
				<div class="form-group" id="SubType6" >
					<label class="col-sm-2 control-label required-control"><spring:message
							code="water.report.subtype" text="Report Sub Type" /></label>
					<div class="col-sm-4">
						<form:select path="reportType" id="reportSubType6" class="form-control chosen-select-no-results"
							data-rule-required="true">

		<form:option value="0"><spring:message code="bill.Select" text="Select" /></form:option>
		<form:option value="F1"><spring:message code="water.report.bill.receipt.detail" text="Water Bill Receipt Detail Report" /></form:option>
		<form:option value="F2"><spring:message code="water.report.ward.wise.bill.receipt.details.consumer" text="Ward Wise and Bill Receipt Details Of Consumer" /></form:option>
      </form:select>
					</div>
				</div>
			
				
				
				
				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="saveReportForms(this)">
						<spring:message code="water.btn.submit" />
					</button>
					
					
					<button type="Reset" class="btn btn-warning" id="resetReportform">
						<spring:message code="water.btn.reset" text="Reset"></spring:message>
					</button>


					<a class="btn btn-danger" id="back" href="AdminHome.html"> <spring:message
							code="water.btn.back"></spring:message>
					</a>
				</div>
			</form:form>
		</div>
	</div>
</div>




