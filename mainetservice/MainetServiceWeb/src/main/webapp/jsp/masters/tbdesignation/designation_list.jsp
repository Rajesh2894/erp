<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% response.setContentType("text/html; charset=utf-8"); %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/mainet/ui/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="js/mainet/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="js/masters/designation/designationMaster.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="master.desg.det" text="Designation Master"/></h2>
			<apptags:helpDoc url="Designation.html" helpDocRefURL="Designation.html"></apptags:helpDoc>
 		</div>
		<div class="widget-content padding">
			<form:form action="" modelAttribute="designation" class="form">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<ul>
						<li>
							<!-- <span class="error-div"></span> -->
						</li>
					</ul>
				</div>
		
				<input type="hidden" value="${userSession.organisation.defaultStatus}" id="orgHidden"/>
				<div class="text-center padding-bottom-10">
				<c:if test="${userSession.organisation.defaultStatus eq 'Y'}">
					<a class="btn btn-success pullright addDesignationClass" href='${url_create}'><i class="fa fa-plus-circle"></i> <spring:message code="bt.add"/></a>
				</c:if>
					
				</div>
				<div class="clear padding-top-10">
					<table id="grid"></table>
					<div id="pagered"></div>
				</div>
 			</form:form>
		</div>
	</div>
</div>