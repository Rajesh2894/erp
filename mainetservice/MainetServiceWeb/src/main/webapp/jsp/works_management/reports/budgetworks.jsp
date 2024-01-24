<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/works_management/reports/worksBudgetReport.js"></script>
<script type="text/javascript">
	
</script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="work.budget.watch.report"
					text="Budget Watch Register Report" />
			</h2>
			<div class="additional-btn">
				       <apptags:helpDoc url="BudgetWorksReport.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="BudgetWorksReport.html" class="form-horizontal"
				id="budgetReport" name="budgetReport">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">

					<label class="col-sm-2 control-label  required-control"><spring:message
							code="sor.fromdate" text="" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" cssClass="form-control datepicker"
								id="fromDate" readonly="true" />
							<label class="input-group-addon" for="fromDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=fromDate></label>
						</div>
					</div>
					<label class="col-sm-2 control-label  required-control"><spring:message
							code="sor.todate" text="" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input path="" cssClass="form-control datepicker"
								id="toDate" readonly="true" />
							<label class="input-group-addon" for="toDate"><i
								class="fa fa-calendar"></i><span class="hide"> <spring:message
										code="" text="icon" /></span><input type="hidden" id=toDate></label>
						</div>
					</div>
				</div>

				<div class="text-center padding-bottom-10">
					<button class="btn btn-success " type="button"
						onclick="viewBudgetReport();">
						<i class="fa fa-sign-out padding-right-5"></i>
						<spring:message code="mileStone.submit" text="Submit" />
					</button>
					<button class="btn btn-warning  reset" type="reset"
						onclick="resetBudgetForm();">
						<i class="fa fa-undo padding-right-5"></i>
						<spring:message code="works.management.reset" text="" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>
