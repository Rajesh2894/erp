<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<style>
.additional-btn{display:none;}
#status .content{padding: 0px !important;}
</style>
<%-- <apptags:breadcrumb></apptags:breadcrumb> --%>
 <ol class="breadcrumb">
      <li  class="breadcrumb-item"><a href="CitizenHome.html"><strong class="fa fa-home"></strong><spring:message code="home" text="Home"/>  </a></li>
      <li class="breadcrumb-item active"><spring:message code="citizen.application.application.status" text="Application Status"/></li>
</ol>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="eip.app.search" text="Application Search" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<ul>
					<li><label id="errorId"></label></li>
				</ul>
			</div>
			<form:form action="CitizenHome.html" method="get" class="form-horizontal">
				<h4>
					<spring:message code="app.status.head" text="Enter your Application Number to know the Application Status" />
				</h4>
				<div class="form-group margin-top-10" >

					<label class="col-sm-2 control-label required-control" for="searchString">
						<spring:message code="citizen.dashboard.applNo"	text="Application No." />
					</label>
					<div class="col-sm-4">					
					<spring:message code="app.no.lbl" text="Please enter Application No." var="placeholderSerachString"/>
						<input type="text" class="form-control hasNumber" name="searchString" id="searchString"  maxlength="19"
						value="" required="required" placeholder="${placeholderSerachString}">
					</div>
					<div class="hidden-lg hidden-md hidden-sm"><br/></div>					
					<button type="button" id="submitButton" name="submitPreLogin"	class="btn btn-success">
						<spring:message code="care.submit" />
					</button>
					<button type="Reset" id="resetButton" name="reset"	class="btn btn-warning">
						<spring:message code="care.reset" />
					</button>
				</div>
					
			</form:form>
		</div>
	</div>
	<div id="status"></div>
</div>
<!-- End Content here -->