<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/audit/auditParaEntry.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('.main-report-content').find('table').addClass('table table-bordered');
	});
	function printdiv(printpage) {
		var headstr = "<html><head><title></title><link href='assets/css/style-responsive.css' rel='stylesheet' type='text/css' /></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr + newstr + footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
</script>
<style>
	table {
		width: 100% !important;
	}
	.appendixDesc {
		padding-bottom: 2rem;
		margin: 1rem 0 0;
	}
	
	
</style>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget" id="receipt">
		<%-- <div class="widget-header">
			<h2>
				<spring:message code="audit.report.heading" text="Audit Report Department Wise" />
			</h2>
			<apptags:helpDoc url="" />
		</div> --%>
		
		<div class="widget-content padding" >
			<style>
			   
				@media print {
					@page { margin: 15px; }
					table {
						width: 100% !important;
					}
					.main-report-content .table tr th,
					.main-report-content .table tr td {
						padding: 5px 10px !important;
					}
					.appendixDesc {
						padding-bottom: 2rem;
						margin: 1rem 0 0;
					}
					
					
				}
				.widget-content{
				       padding: 2rem;
					    border:2px solid #000;
					    margin: auto;
					}
				.dateTime {
					text-align: right;				
				}
			</style>
			<form:form action="" name="" id="" cssClass="form-horizontal">
				
				<div class="row" style="border-bottom: 2px solid #000; margin-bottom:10px;">
					<div class="col-xs-3 col-sm-3">
						<img src="${userSession.orgLogoPath}" width="80">
					</div>

					<div class="col-xs-6 col-sm-6 text-center">
						<h2 class="text-bold margin-bottom-10 padding-bottom-10">${userSession.organisation.ONlsOrgnameMar}</h2>
						<h3><spring:message code="audit.report1" text="Audit Report Department Wise" /></h3>
						<h5><spring:message code="audit.report2" text="Financial Year" /><b>: ${finyear}</b></h5>
					</div>
					
					<div class="col-xs-3 col-sm-3 dateTime">
						<div>
						<span><b><spring:message code="audit.mgmt.date" text="Date" />: ${date}</span></b>
						<span></span>
						</div>
						<div>
						<span><b><spring:message code="audit.report3" text="Time" />: ${time}</span></b>
						<span></span>
						</div>
					</div>
				</div>
				<c:forEach items="${auditParaEntryDtoList}"	var="masterData" varStatus="status">
					<div class="main-report-content">
						<div class="row">
							<div class="col-xs-4 col-sm-4">
								<span><b><spring:message code="audit.mgmt.auditParaNo" text="Audit Para No" /></b>: ${masterData.auditParaCode}</span>
							</div>
							<div class="col-xs-8 col-sm-8">
								<span><b>${masterData.auditParaSub}</b></span>
							</div>
						</div>
						
						<div class="appendixDesc">
							${masterData.auditAppendix}
						</div>
					</div>
				</c:forEach> 
				
				<div class="text-center hidden-print padding-20">
					<button onclick="printdiv('receipt')"
						class="btn btn-primary hidden-print">
						<i class="fa fa-print"></i>
						<spring:message code="trade.print" text="Print" />
					</button>
				</div>
			</form:form>
			</div>
		
	</div>
</div>