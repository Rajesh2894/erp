<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>

<%
	response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript"
	src="js/masters/tblocationmas/locationList.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content animated slideInDown">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="master.locMaster" text="Location Master" />
			</h2>
			<apptags:helpDoc url="LocationMas.html"></apptags:helpDoc>


		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="contract.breadcrumb.fieldwith"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="common.master.mandatory" text="is mandatory" /> </span>
			</div>
			<form action="LocationMas.html" method="POST" name="locationMaster"
				class="form-horizontal" id="locationMaster">
				<div class="text-center padding-bottom-10">
					<button type="button" class="btn btn-success btn-submit"
						id="addLocMasLink">
						<i class="fa fa-plus-circle"></i>
						<spring:message code='master.addButton' />
					</button>
					<button type="button" class="btn btn-primary"
						onclick="exportTemplate();">
						<spring:message code="master.expImp" text="Export/Import" />
					</button>
				</div>
				<div id="" align="center">
					<table id="locationGrid"></table>
					<div id="locationPager"></div>
				</div>
			</form>
		</div>
	</div>
</div>
