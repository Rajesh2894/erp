<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/mainet/file-upload.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script src="js/care/grievance-status.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="care.complaint.status" text="Complaint Status" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
			<form:form action="" method="get" class="form-horizontal">
				<h4>
					<spring:message code="care.token.number.status" text="Enter your Token Number to know the Complaint Status" />
				</h4>
				<div class="margin-top-10 form-group">
					<label class="col-sm-2 control-label required-control"
						for="token-no"><spring:message code="care.token.number"
							text="Token No." /></label>
					<div class="col-sm-4">
						<input type="text" class="form-control" name="searchString" maxlength="50"
							id="tokenNumber" value="" required="required">
					</div>
					<div class="col-sm-6">
						<button type="button" id="submit" class="btn btn-success" onclick="submitAfterLoginForm(this)"> <spring:message code="care.submit" />	</button>
						<button type="Reset" id="resetCustom" class="btn btn-warning"> <spring:message code="care.reset" />
						</button>
						<button type="button" class="btn btn-danger" onclick="window.location.href='CitizenHome.html'">
						<spring:message code="care.back" text="Back" />
						</button>
					</div>
				</div>

			</form:form>
		</div>
	</div>
	<div id="status"></div>
</div>
<!-- End Content here -->