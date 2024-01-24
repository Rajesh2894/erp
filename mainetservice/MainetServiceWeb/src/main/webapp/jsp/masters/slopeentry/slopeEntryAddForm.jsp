<?xml version="1.0" encoding="UTF-8" standalone="no"?>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		pageEncoding="ISO-8859-1"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%
		response.setContentType("text/html; charset=utf-8");
	%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script src="js/masters/slopeentry/slopeentry.js"></script>
  <!-- Start info box -->
    <div class="widget" id="widget">
       <c:if test="${mode eq 'update'}">
        <h2><spring:message code="water.slope.modify"/></h2>
       </c:if>
       <c:if test="${mode eq 'create'}">
        <h2><spring:message code="water.slope.add"/></h2>
       </c:if>
        <div class="widget-content padding">
	<form:form class="form-horizontal" modelAttribute="tbSlopePrm" name="frmMaster" method="POST" action="TBSlopeParam.html" onsubmit="validation(this);" id="form2">
			<div class="warning-div alert alert-danger alert-dismissible hide" id="errorDivScrutiny">
				<button type="button" class="close" aria-label="Close" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button>
				
				<ul><li><i class='fa fa-exclamation-circle'></i>&nbsp;<form:errors path="*"/></li></ul>
			</div>
			
		<c:if test="${mode == 'view'}">
				<SCRIPT type="text/javascript">
					$(document).ready(
							function() {
								$('.error-div').hide();
								$('#editOriew').find('*').attr('disabled','disabled');
								$('#editOriew').find('*').addClass("disablefield");
								$('#slope_frmDt').attr('disabled','disabled');
								$('#slope_frmDt').addClass("disablefield");
								$('#slope_toDt').attr('disabled','disabled');
								$('#slope_toDt').addClass("disablefield");
								$('#slope_frm').attr('disabled','disabled');
								$('#slope_frm').addClass("disablefield");
								$('#slope_to').attr('disabled','disabled');
								$('#slope_to').addClass("disablefield");
								$('#slope_val').attr('disabled', 'disabled');
								$('#slope_val').addClass("disablefield");
							});
				</SCRIPT>

			</c:if>
 
	
 
			<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
			 
		<div class="form-group">
				<label class="label-control col-sm-2"><spring:message code="water.slope.frmDt"/> </label>
				<div class="col-sm-4">
				
               
                <form:input path="spFrmdtStringFormat" 	cssClass="datepicker cal form-control"  id="slope_frmDt" disabled="${editMode}" />
                <form:hidden path="spId" />
					 <form:errors id="designation_dsgname_errors" path="spFrmdtStringFormat" cssClass="label label-danger" />
				</div>
				<label class="label-control col-sm-2"><spring:message code="water.slope.toDt"/></label>
				<div class="col-sm-4">
				
					<form:input id="slope_toDt" path="spTodtStringFormat" class="datepicker cal form-control" disabled="${editMode}" />
					<form:errors id="slope_toDt" path="spTodtStringFormat" cssClass="label label-danger" />
				</div>
			</div>
			
			<div class="form-group">
				<label class="label-control col-sm-2"><spring:message code="water.slope.from"/></label>
				<div class="col-sm-4">
					<form:input id="slope_frm" path="spFrom" class="form-control" maxLength="500" disabled="${editMode}"/>
					<form:errors id="slope_frm" path="spFrom" cssClass="label label-danger" />
				</div>
				<label class="label-control col-sm-2"><spring:message code="water.slope.to"/></label>
				<div class="col-sm-4">
					<form:input id="slope_to" path="spTo" class="form-control" maxLength="100" disabled="${editMode}"/>
					<form:errors id="slope_to" path="spTo" cssClass="label label-danger" />
				</div>
			</div>
			
			<div class="form-group">
				<label class="label-control col-sm-2"><spring:message code="water.slope.val"/></label>
				<div class="col-sm-4">
					<form:input id="slope_val" path="spValue" class="form-control" maxLength="500" disabled="${editMode}" />
					<form:errors id="slope_val" path="spValue" cssClass="label label-danger" />
				</div>
			</div>
			
			
		
			<div class="text-center padding-top-10">
				<c:if test="${mode eq 'create'}"> 
				<input type="button" class="btn btn-success btn-submit" onclick="SaveSlopeEntryDetails(this)" value="Save"> </input>
				</c:if>
				<c:if test="${mode eq 'update'}">
				<input type="button" class="btn btn-success btn-submit" onclick="updateSlopeEntryDetails(this)" value="Save"> </input>
				</c:if>
				<spring:url var="cancelButtonURL" value="TBSlopeParam.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}">Back</a>
			</div>

		</form:form>
	</div>
	</div>
