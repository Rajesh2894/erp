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
<script src="js/mainet/jquery.jqGrid.min.js"></script>
<script src="js/account/accountYearEndProcess.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message
					code="Account.Year.End.Process" text="Account Year End Process"></spring:message>
			</h2>
		<apptags:helpDoc url="AccountYearEndProcess.html" helpDocRefURL="AccountYearEndProcess.html"></apptags:helpDoc>		
		</div>
		<div class="widget-content padding">

			<form:form action="" modelAttribute="tbAcYearEndProcess"
				class="form-horizontal">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<div class="form-group">

					<label class="col-sm-2 control-label required-control"><spring:message
							code="account.budget.code.master.financialyear"
							text="Financial Year" /></label>
					<div class="col-sm-4">						
									<form:select id="faYearid" path="faYearid"
										cssClass="form-control mandColorClass" disabled="${viewMode}"
										data-rule-required="true">
										<c:forEach items="${financeMap}" varStatus="status"
											var="financeMap">
											<form:option value="${financeMap.key}"
												code="${financeMap.key}">${financeMap.value}</form:option>
										</c:forEach>
									</form:select>						
					</div>
					
					</div>			

				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-blue-2 searchData"
						onclick="searchYearEndProcessFormData(this)">
						<spring:message code="accounts.process" text="Process" />
					</button>
					<spring:url var="cancelButtonURL"
						value="AccountYearEndProcess.html" />
					<a role="button" class="btn btn-warning" href="${cancelButtonURL}"><spring:message
							code="account.bankmaster.reset" text="Reset" /></a>
				</div>
			</form:form>
		</div>
	</div>
</div>

