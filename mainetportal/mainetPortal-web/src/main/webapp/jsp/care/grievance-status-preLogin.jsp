<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
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
			<form:form action="grievance.html" method="get" class="form-horizontal">
				<h4>
					<spring:message code="care.tokenOrMobile.number.status" text="Enter your Token Number or Mobile Number to know the Complaint Status" />
				</h4>
				<div class="form-group margin-top-10" >

					<label class="col-sm-2 control-label required-control" for="searchString">
						<spring:message code="care.tokenNoMobileNo"	text="Token No./Mobile No." />
					</label>
					<div class="col-sm-4">
					
					<spring:message code="care.placeholder.serachString" text="lease enter Token No. Or Mobile No." var="placeholderSerachString"/>
						<input type="text" class="form-control " name="searchString" id="searchString"  maxlength=""
						value="" required="required" placeholder="${placeholderSerachString}">
					</div>
					<button type="button" id="submitPreLogin" name="submitPreLogin"	class="btn btn-success" onclick="submitPreLoginForm(this)">
						<spring:message code="care.submit" />
					</button>
					<button type="Reset" id="resetCustom" name="reset"	class="btn btn-warning">
						<spring:message code="care.reset" />
					</button>

				</div>
					
			</form:form>
		</div>
	</div>
	<div id="status"></div>
</div>
<!-- End Content here -->