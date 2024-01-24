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
<script type="text/javascript" src="js/adh/listOfDefaulters.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="defaulter.register.table.title"
					text="Defaulter Register"></spring:message>
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="ListOfDefaulters.html" cssClass="form-horizontal">
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
						cssClass="form-control margin-bottom-10" showAll="true"
						columnWidth="20%" />
				</div>

				<div class="form-group">
					<!-- <This is for Hierarchical> -->
					<apptags:lookupFieldSet baseLookupCode="ADH" hasId="true"
						pathPrefix="hoardingMasterDto.hoardingTypeId"
						hasLookupAlphaNumericSort="true"
						hasSubLookupAlphaNumericSort="true"
						cssClass="form-control margin-bottom-10" showAll="true"
						columnWidth="20%" />
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2 required-control"> <spring:message
							code="adh.due.date" text="Till Due Date"></spring:message>
					</label>
					<div class="col-sm-4">
						<div class="input-group">
							<input class="form-control datepicker" id="tillDueDate"
								maxlength="10"> <label class="input-group-addon"><i
								class="fa fa-calendar"></i></label>
						</div>
					</div>

				</div>
				


				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success btn-submit" id="save"
						onclick="viewReportform(this)">
						<spring:message code="adh.btn.submit" />
					</button>
					<button type="Reset" class="btn btn-warning" id="resetForm" 
					onclick="window.location.href='ListOfDefaulters.html'"  >
						<spring:message code="adh.btn.reset" />
					</button>
					
				</div>
		</div>
		</form:form>
	</div>
</div>
</div>