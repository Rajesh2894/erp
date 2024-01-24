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
<script src="js/masters/institutionwiseconsumption/institutionwiseconsumption.js"></script>
 <div class="widget" id="widget">
       <c:if test="${mode eq 'update'}">
        <div class="widget-header">
			<h2>
				<spring:message code="master.instcsmp.detail"/>
			</h2>
		</div>
       </c:if>
       <c:if test="${mode eq 'create'}">
        <div class="widget-header"><h2><spring:message code="master.instcsmp.detail.add"/> </h2></div>
       </c:if>
       <c:if test="${mode eq 'view'}">
        <div class="widget-header"><h2><spring:message code="master.instcsmp.detail"/> </h2></div>
       </c:if>
        <div class="widget-content padding">


		
		<form:form class="form-horizontal" modelAttribute="tbWtInstCsmp" name="frmMaster" method="POST" action="WaterInstiWiseConsuption.html" onsubmit="validation(this);" id="form2">
			<div class="warning-div alert alert-danger alert-dismissible hide" id="errorDivScrutiny">
				<button type="button" class="close" aria-label="Close" onclick="closeErrBox()"><span aria-hidden="true">&times;</span></button>
				
				<ul><li><i class='fa fa-exclamation-circle'></i>&nbsp;<form:errors path="*"/></li></ul>
				<script>
					
		  		</script>
			</div>
			
		<c:if test="${mode == 'view'}">
				<SCRIPT type="text/javascript">
					$(document).ready(
							function() {
								$('.error-div').hide();
								$('#editOriew').find('*').attr('disabled','disabled');
								$('#editOriew').find('*').addClass("disablefield");
								$('#instWiseConsmpFrmDt').attr('disabled','disabled');
								$('#instWiseConsmpFrmDt').addClass("disablefield");
								$('#instWiseConsmpToDt').attr('disabled','disabled');
								$('#instWiseConsmpToDt').addClass("disablefield");
								$('#instType').attr('disabled','disabled');
								$('#instType').addClass("disablefield");
								$('#litersPerHead').attr('disabled','disabled');
								$('#litersPerHead').addClass("disablefield");
							});
				</SCRIPT>

			</c:if>
 
	
 
			<!-- Store data in hidden fields in order to be POST even if the field is disabled -->
			 
		<div class="form-group">
				<label class="label-control col-sm-2"><spring:message code="master.frmDt"/> </label>
				<div class="col-sm-4">
				<form:input path="instFrmDtStringFormat" cssClass="datepicker cal form-control"  id="instWiseConsmpFrmDt" disabled="${editMode}" />
                <form:hidden path="instId" />
					 <form:errors id="instWiseConsmpFrmDt" path="instFrmDtStringFormat" cssClass="label label-danger" />
				</div>
				<label class="label-control col-sm-2"><spring:message code="master.toDt"/></label>
				<div class="col-sm-4">
				
					<form:input id="instWiseConsmpToDt" path="instToDtStringFormat" class="datepicker cal form-control" disabled="${editMode}" />
					<form:errors id="instWiseConsmpToDt" path="instToDtStringFormat" cssClass="label label-danger" />
				</div>
			</div>
			
			<div class="form-group">
				<label class="label-control col-sm-2"><spring:message code="master.instcsmp.instType"/> </label>
				<c:if test="${mode eq 'edit'}">
				<div class="col-sm-4">
					<form:input id="instType" path="instType" class="form-control" maxLength="500" disabled="${editMode}"/>
					<form:errors id="instType" path="instType" cssClass="label label-danger" />
				</div>
				</c:if>
				<c:if test="${mode eq 'view'}">
				<div class="col-sm-4">
					<form:input id="instWiseConsmpToDt" path="instType" class="form-control" maxLength="500" disabled="${editMode}"/>
					<form:errors id="instWiseConsmpToDt" path="instType" cssClass="label label-danger" />
				</div>
				</c:if>
				<c:if test="${mode eq 'create'}">
				<div class="col-sm-4"><form:select id="instType" path="instType" cssClass="form-control">
				<form:option value="">-Select-</form:option>
						<c:forEach items="${listOfInstType}" var="instType">
							<form:option value="${instType.lookUpId}">${instType.descLangFirst}</form:option>
						</c:forEach>
				</form:select>
				<form:errors id="instWiseConsmpToDt" path="instType" cssClass="label label-danger" />
				</div>
				</c:if>
				<label class="label-control col-sm-2"><spring:message code="master.instcsmp.ltrperhead"/></label>
				<div class="col-sm-4">
					<form:input id="litersPerHead" path="instLitPerDay" class="form-control" maxLength="100" disabled="${editMode}"/>
					<form:errors id="litersPerHead" path="instLitPerDay" cssClass="label label-danger" />
				</div>
			</div>
			
			<div class="form-group">
				<label class="checkbox-inline">
					<form:checkbox path="instFlag" value="true" id="isdeleted" />
					<spring:message code="master.active"/>
				</label>
			</div>
			
			
		
			<div class="text-center padding-top-10">
				<c:if test="${mode eq 'create'}"> 
				<input type="button" class="btn btn-success btn-submit" onclick="SaveInstituteWiseConsumptionDetails(this)" value=<spring:message code="master.save"/> /> 
				</c:if>
				<c:if test="${mode eq 'edit'}">
				<input type="button" class="btn btn-success btn-submit" onclick="updateInstituteWiseConsumptionDetails(this)" value=<spring:message code="master.save"/>/> 
				</c:if>
				<spring:url var="cancelButtonURL" value="WaterInstiWiseConsuption.html" />
				<a role="button" class="btn btn-danger" href="${cancelButtonURL}">Back</a>
			</div>

		</form:form>
	</div>
	</div>