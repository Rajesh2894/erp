<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js"></script>
<script src="js/account/transaction/accountLoan_list.js" type="text/javascript"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="register.of.Loans" text="Register of Loans" /></h2>
		</div>
		<apptags:helpDoc url="loanreport.html"
			helpDocRefURL="loanreport.html"></apptags:helpDoc>
		<div class="widget-content padding">
			
			<form:form action="" class="form-horizontal">
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

					<label for="loanCode" class="col-sm-2 control-label"><spring:message
							code="Loan.Code" text="Loan Code" /></label>
					<div class="col-sm-4">
						<%-- <form:input  class="form-control padding-left-10"
							placeholder="Enter Loan Code" id="loanCode" path=""/> --%>
							
						<form:select id="loanCode" path=""
							class="form-control mandColorClass chosen-select-no-results">
							
							<form:option value="" selected="true">
								<spring:message code="acc.select" text="Select" />
							</form:option>
							
					 <c:forEach items="${command.id}" var="data">
								<form:option value="${data}">${data}</form:option>
					</c:forEach>  
						</form:select>
						
							
					</div>
				
				</div>
				
				<div class="text-center padding-top-10">
					<button type="button" class="btn btn-blue-2" onClick="loanreport('loanreport.html','report')" title="View Report">
					<spring:message code="view.Report" text="View Report"></spring:message>
					</button>
					
					<button type="button" class="btn btn-warning" onClick="loanReportReset(this)" title="Reset"> <spring:message code="account.btn.reset" text="Reset" />
					</button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
				</div>
			</form:form>
		</div>
	</div>
</div>