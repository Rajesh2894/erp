<?xml version="1.0" encoding="UTF-8" standalone="no"?> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% response.setContentType("text/html; charset=utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script  src="js/masters/designation/designationMaster.js"/>
<script type="text/javascript">
	jQuery('.hasCharacter').keyup(function (evt) { 
		var theEvent = evt || window.event;
		var key = theEvent.keyCode || theEvent.which;
		if(key == 37 || key == 39 || key == 8 || key == 46) {
		return;
		}	else{
			this.value = this.value.replace(/[^a-z A-Z @ # $ & \- / `]/g,'');
		}
	}); 
</script>
<c:if test="${ShowBreadCumb == false}">

<apptags:breadcrumb></apptags:breadcrumb>
</c:if>
      <!-- Start info box -->
      <div class="widget" id="widget">
      <div class="widget-header">
			<h2><spring:message code="master.desg.det"/></h2>
			<apptags:helpDoc url="Designation.html" helpDocRefURL="Designation.html"></apptags:helpDoc>
 		</div>
        <div class="widget-content padding">
        <div class="mand-label clearfix">
        <span><spring:message code="contract.breadcrumb.fieldwith" text="Field with"/> <i class="text-red-1">*</i><spring:message code="common.master.mandatory" text="is mandatory"/>
        </span>
        </div>
         <c:url value="${saveAction}" var="url_form_submit" />
		<form:form class="form-horizontal" modelAttribute="designationBean" name="frmMaster" method="POST" action="${url_form_submit}" id="frmMaster">
		 <jsp:include page="/jsp/tiles/validationerror.jsp" />
		<div class="alert alert-danger alert-dismissible error-div" id="errorDivScrutiny"></div>
 		<c:if test="${mode == 'View'}">
				<script type="text/javascript">
					$(document).ready(
							function() {
								$('.error-div').hide();
								$('#editOriew').find('*').attr('disabled','disabled');
								$('#editOriew').find('*').addClass("disablefield");
								$('#designation_dsgname').attr('disabled','disabled');
								$('#designation_dsgname').addClass("disablefield");
								$('#designation_dsgnamereg').attr('disabled','disabled');
								$('#designation_dsgnamereg').addClass("disablefield");
								$('#designation_dsgdesc').attr('disabled','disabled');
								$('#designation_dsgdesc').addClass("disablefield");
								$('#designation_dsgshortname').attr('disabled','disabled');
								$('#designation_dsgshortname').addClass("disablefield");
								$('#designation_dsgshortname1').attr('disabled', 'disabled');
								$('#designation_dsgshortname1').addClass("disablefield");
								$('#isdeleted').attr('disabled', 'disabled');
								$('#isdeleted').addClass("disablefield");
							});
				</script>
 			</c:if>
			<c:if test="${mode == 'create'}">
				<script type="text/javascript">
				$(document).ready(function() {
					$('#isDeleted-lbl').addClass('required-control');
				});
				</script>
 			</c:if>
			<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
			<form:hidden path="dsgid" />
			<form:hidden path="mode" id="mode" />
			<c:if test="${mode eq 'update'}">
				<input type="hidden" value="${status}" id="status"/>
			</c:if>
			
 			<div class="form-group">
			<label class="control-label col-sm-2 required-control"><spring:message code="master.desg.name"/> </label>
			<div class="col-sm-4"><form:input id="designation_dsgname" path="dsgname" class="form-control hasCharacter" maxLength="100" /></div>
			<label class="control-label col-sm-2 required-control"><spring:message code="master.desg.regName"/> </label>
			<div class="col-sm-4"><form:input id="designation_dsgnamereg" path="dsgnameReg" class="form-control" maxLength="100" /></div>
			</div>
			<div class="form-group">
			<label class="control-label col-sm-2 required-control"><spring:message code="master.desg.descShrt"/></label>
			<div class="col-sm-4">
			<c:choose>
				<c:when test="${mode eq 'create'}"> 
				<form:input id="designation_dsgshortname" path="dsgshortname" class="form-control hasCharacter" maxLength="4"  style="text-transform: uppercase;"/>					
				</c:when>
				<c:otherwise>
				<form:input id="designation_dsgshortname" path="dsgshortname" class="form-control hasCharacter" disabled="true"/>
				<form:hidden path="dsgshortname"/>
				</c:otherwise>
				</c:choose>
				</div>
 				<label class="control-label col-sm-2" for="designation_dsgdesc"><spring:message code="master.desg.desc"/></label>
				<div class="col-sm-4"><form:input id="designation_dsgdesc" path="dsgdescription" class="form-control" maxLength="500" /></div>
 			</div>
 			<div class="form-group">
			<label class="control-label col-sm-2" for="isDeleted-lbl"><spring:message code="SchemeMaster.status"/></label>
			<div class="col-sm-4">
 				<label class="checkbox-inline" id="isDeleted-lbl">
				<c:if test="${mode eq 'create'}"><form:checkbox path="isdeleted" value="0" id="isdeleted" disabled="true" checked="checked"/></c:if>
				<c:if test="${mode ne 'create'}"><form:checkbox path="isdeleted" value="0" id="isdeleted" onchange="checkForDesignation()"/></c:if>				
					<spring:message code="master.active"/>
				</label>
			</div>
			</div>
			
			<div class="text-center padding-top-10">
				<c:if test="${mode != 'View'}">
					<input type="button" class="btn btn-success btn-submit" onclick="return saveDataDesignation(this);" value="<spring:message code="bt.save"/>">
					<input type="button" class="btn btn-warning" value="<spring:message code="reset.msg"/>" onclick="this.form.reset();resetDesignation()"/>
				</c:if>
				<spring:url var="cancelButtonURL" value="Designation.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}"><spring:message code="back.msg" text="Back"/></a>
			</div>

		</form:form>
	</div>
</div>

