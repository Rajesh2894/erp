<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript"
	src="js/cfc/checklistVerificationSearch.js"></script>

<ol class="breadcrumb">
	<li><a href="AdminHome.html"><spring:message
				code="menu.home" /></a></li>
	<li><a href="javascript:void(0);"><spring:message text="CFC"
				code="cfc.module.breadcrumb" /></a></li>
	<li><a href="javascript:void(0);"><spring:message
				text="Transaction" code="audit.transactions" /></a></li>
	<li class="active"><spring:message
			text="Document Verification Search"
			code="cfc.service.checklist.search" /></li>
</ol>

<div class="content">
	<div class="widget">
		<div class="widget-header">		
			<h2>
				<spring:message text="Document Verification Search"
					code="cfc.service.checklist.search" />
			</h2>
			<apptags:helpDoc url="DocumentResubmission.html"></apptags:helpDoc>
		</div>
		<div class="widget-content padding">
			<form:form action="ChecklistVerificationSearch.html"
				name="frmMasterForm" id="frmMasterForm" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div class="form-group">
					<%-- <label class="col-sm-2 control-label"><spring:message code="cfc.service" text="Services" /></label>
                <div class="col-sm-4">
                  <apptags:selectField items="${command.serviceList}" selectOptionLabelCode="tp.selService" hasId="true" fieldPath="serviceId" cssClass="form-control"/>
                </div> --%>
					<label class="col-sm-2 control-label"><spring:message
							code="cfc.service.status" text="Status" /></label>
					<div class="col-sm-4">
						<form:select path="appStatus" cssClass="form-control">
							<form:option value="">
								<spring:message code="cfc.status" text="Choose Status" />
							</form:option>
							<form:option value="Y">
								<spring:message code="cfc.status.approve" text="APPROVE" />
							</form:option>
							<form:option value="N">
								<spring:message code="cfc.status.reject" text="REJECT" />
							</form:option>
							<form:option value="H">
								<spring:message code="cfc.status.hold" text="HOLD" />
							</form:option>
							<form:option value="P">
								<spring:message code="cfc.status.pending" text="PENDING" />
							</form:option>
							<form:option value="R">
								<spring:message code="cfc.status.resubmit" text="RESUBMITED" />
							</form:option>
						</form:select>
					</div>
				</div>

              <div class="form-group">
                <label class="col-sm-2 control-label"><spring:message code="cfc.application" text="Application Id" /></label>
                <div class="col-sm-4">
                 <form:input path="applicationId" maxlength="16" cssClass="hasNumber form-control" />
                </div>
                <label class="col-sm-2 control-label"><spring:message code="cfc.date.from" text="From Date" /></label>
                <div class="col-sm-4">
                <div class="input-group">
                     <form:input path="fromDate" id="fromDate" cssClass="form-control" />
                      <label for="datepicker" class="input-group-addon"><i class="fa fa-calendar"></i></label>
                    </div>
                 
                </div>
              </div>
				
				
				<div class="form-group">
                  <label class="col-sm-2 control-label"><spring:message code="cfc.date.to" text="To Date" /></label>
                  <div class="col-sm-4">
                    <div class="input-group">
                    <form:input path="toDate" id="toDate" cssClass="form-control"/>
                      <label for="datepicker" class="input-group-addon"><i class="fa fa-calendar"></i></label>
                    </div>
                  </div>
                  <label class="col-sm-2 control-label"><spring:message code="cfc.applicant.name" text="Applicant Name" /></label>
                  <div class="col-sm-4">
                  <form:input path="applicantName" cssClass="hasCharacter form-control" maxlength="300" />
                  </div>                  
                </div>

				
				
				
				<div class="text-center padding-10">
					<input type="button" value="<spring:message code="bt.search" />" onclick="findAll(this)" class="btn btn-success"> 
					<input type="button" value="<spring:message code="bt.clear" />" onclick="clearForm('ChecklistVerificationSearch.html')"	class="btn btn-danger">
				</div>

				<c:if test="${command.sucessResult}">
					<div class="table-responsive">
						<apptags:jQgrid id="checklistVerification"
							url="ChecklistVerificationSearch.html?SEARCH_RESULTS"
							mtype="post" gridid="gridChecklistVerification"
							colHeader="cfc.application,cfc.applIcation.date,cfc.applicant.name,cfc.service,cfc.service.status"
							colModel="[                            
                             {name : 'apmApplicationId',index : 'apmApplicationId',editable : false,sortable : true,search : true,align : 'center'},
                             {name : 'apmApplicationDate',index : 'apmApplicationDate',editable : false,sortable : true,search : true,align : 'center',formatter : dateTemplate},                                         
                             {name : 'applicantsName',index : 'applicantsName',editable : false,sortable : true,search : true,align : 'center'},                                         
                             {name : 'applicationService',index : 'applicationService',editable : false,sortable : true,search : true,align : 'center'},
                             {name : 'approvalStatus',index : 'approvalStatus',editable : false,sortable : true,search : true,align : 'center'}
                             
                         ]"
							sortCol="rowId" isChildGrid="false" hasActive="false"
							viewAjaxRequest="true" hasViewDet="true" hasDelete="false"
							height="350" showrow="true" caption="cfc.service.checklist"
							loadonce="true" />
					</div>
				</c:if>
				<c:if test="${command.error}">
					<hr>
					<h3 class="text-center">
						<spring:message code="challan.noRecord" text="No Record Found" />
					</h3>
				</c:if>
			</form:form>
		</div>
	</div>
</div>

