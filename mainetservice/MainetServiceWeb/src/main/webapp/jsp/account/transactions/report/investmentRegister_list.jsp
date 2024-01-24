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
<script src="js/account/accountInvesmantMaster.js"
	type="text/javascript"></script>
<!-- End JSP Necessary Tags -->

<apptags:breadcrumb></apptags:breadcrumb>
<div class = "content">
	<div class="widget">
		<div class = "widget-header">
			<h2>
				<spring:message code="account.investment.register.title"
				text="Investment Register" />
			</h2>
			<apptags:helpDoc url="investmentRegister.html"></apptags:helpDoc>
		</div>
		<div class= "widget-content padding">
			<form:form action ="" method = "get" class = "form-horizontal">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
				
						<%-- <apptags:input labelCode="Investment Number"
						
						path="accountInvestmentMasterDto.invstNo" isMandatory="true"
						placeholder="Enter Investment Number"></apptags:input> --%>
						
						<label for="text-1" class="control-label col-sm-2 "><spring:message code="acc.fund" text="Fund" /></label>
					<div class="col-sm-4">
						<form:select
							class="form-control mandColorClass chosen-select-no-results"
							path="accountInvestmentMasterDto.fundName" id="fundId" onchange="loadInvestNumber(this)"
							placeholder="Select fund">
							<form:option value="" selected="true">
								<spring:message code="acc.select" text="Select" />
							</form:option>
							<c:forEach items="${command.fundList}" var="fundData">
								<form:option code="${fundData.fundCode}"
									value="${fundData.fundId}">${fundData.fundCompositecode} ${fundData.fundDesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
						
						<label for="text-1" class="control-label col-sm-2 "><spring:message code="investment.Number" text="Investment Number" /></label>
					<div class="col-sm-4">
						<form:select
							class="form-control mandColorClass chosen-select-no-results"
							path="" id="invstNo"
							placeholder="Select Investment Number">
							<form:option value="" selected="true">
								<spring:message code="acc.select" text="Select" />
							</form:option>
							<c:forEach items="${command.invstId}" var="data">
								<form:option 
									value="${data}">${data}</form:option>
							</c:forEach>
						</form:select>
					</div>
						
				</div>
				
				
				<div class= "form-group">
					<label for="text-1" class="control-label col-sm-2 ">
						<spring:message code="budget.reappropriation.authorization.fromdate" text="From Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
						<spring:message code="acc.Enter.From.Date" text="Enter From Date"
							var="enterToDate"></spring:message>
							<form:input class="form-control datepicker" id="regFromDate"
								path=""
									onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"
								placeholder="${enterToDate}" maxlength="10" />
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>
						</div>
					</div>
						<label for="text-1" class="control-label col-sm-2 "><spring:message code="budget.reappropriation.authorization.todate" text="To Date" />
					</label>
					<div class="col-sm-4">
						<div class="input-group">
						<spring:message code="acc.Enter.To.Date" text="Enter To Date"
							var="enterToDate"></spring:message>
							<form:input class="form-control datepicker" id="regToDate"
								path=""
									onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"
								placeholder="${enterToDate}" maxlength="10" 
								/>
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>
						</div>
					</div>
				</div>
				
			<!--onClick="report('investmentRegister.html','report')" -->	
					<div class="text-center padding-top-10">
					<button type="button" class="btn btn-blue-2"  onClick="report('investmentRegister.html','report')" title="View Report">
					<spring:message code="account.investment.view"  text="View Report"></spring:message>
					</button>
					
					<button type="button" class="btn btn-warning" onclick="window.location.href='investmentRegister.html'"  title="Reset"> <spring:message code="account.bankmaster.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
		</form:form>
	</div>
   </div>
</div>
			<!-- code="account.investment.view" -->	