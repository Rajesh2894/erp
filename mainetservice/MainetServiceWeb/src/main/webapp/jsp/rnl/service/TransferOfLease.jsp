<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag" %>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<% response.setContentType("text/html; charset=utf-8"); %>

<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="js/rnl/service/transferOfLease.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
<div class="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="rnl.transferOfLease" text="Transfer of Lease" />
		</h2>
		<div class="additional-btn">
			<a href="#" data-toggle="tooltip" data-original-title="Help"><i
				class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message
						code="rnl.book.help" text="Help"></spring:message></span></a>
		</div>
	</div>
	<div class="widget-content padding">
		<div class="mand-label clearfix">
			<span><spring:message code="rnl.book.field" text="Field with"></spring:message><i
				class="text-red-1">*</i> <spring:message
					code="master.estate.field.mandatory.message" text="is mandatory"></spring:message>
			</span>
		</div>
		<div class="error-div alert alert-danger alert-dismissible"
			id="errorDivId" style="display: none;">
			<ul>
				<li><label id="errorId"></label></li>
			</ul>
		</div>
		<form:form action="TransferOfLease.html" method="POST" commandName="command"
			class="form-horizontal" name="transferOfLeaseForm"
			id="transferOfLeaseForm">
			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<div class="panel-group accordion-toggle"
				id="accordion_single_collapse">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse"
								data-parent="#accordion_single_collapse" href="#estate"> <spring:message
									code="rnl.transferOfLeaseToAnotherTenant" text="Transfer the Lease of the property to another tenant"></spring:message></a>
						</h4>
					</div>
					<div id="estate" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="contractNo"><spring:message
										code="rnl.notice.contractNo" text="Contract No." /></label>
								<div class="col-sm-4">
									<form:input name="contractNo" type="text"
										class="form-control hasNumberWithFSlash" id="contractNo"
										path="contractMasterDto.contNo" 
										onchange="searchContractData('TransferOfLease.html','getContractDetail');"></form:input>
								</div>
								
								<label class="col-sm-2 control-label required-control"
									for="currentTenantName"><spring:message
										code="rnl.currTenantName" text="Current Tenant Name" /></label>
								<div class="col-sm-4">
									<form:input name="CurrentTenantName" type="text"
										class="form-control" id="currentTenantName"
										path="contractMasterDto.contractPart2List[0].contp2Name" disabled="true"></form:input>
								</div>
						</div>
						
						<div class="form-group">
								<label class="control-label col-sm-2 required-control"
									for="contractStartDate"><spring:message code="rnl.contractStartDate"
										text="Contract Start Date"></spring:message></label>
								<div class="col-sm-4">
									<div class="input-group">
										<fmt:formatDate value="${command.contractMasterDto.contractDetailList[0].contFromDate}" 
											pattern="dd/MM/yyyy" var="fromDate" />
										<form:input path="" value="${fromDate}"
											type="text" id="contractStartDate"
											class="form-control"
											data-rule-required="true" disabled="true"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>
								<label class="control-label col-sm-2 required-control"
									for="contractEndDate"><spring:message code="rnl.contractEndDate"
										text=" Contract End Date"></spring:message></label>
								<div class="col-sm-4">
									<div class="input-group">
									<fmt:formatDate value="${command.contractMasterDto.contractDetailList[0].contToDate}" 
											pattern="dd/MM/yyyy" var="toDate" />
										<form:input path="" value="${toDate}"
											type="text" id="contractEndDate"
											class="form-control"
											data-rule-required="true" disabled="true"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>
							</div>
						
						<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="agreementamount "><spring:message
										code="rnl.agreementAmount" text="Agreement Amount " /></label>
								<div class="col-sm-4">
									<form:input name="agreementamount" type="text"
										class="form-control hasNumber" id="agreementamount"
										path="contractMasterDto.contractDetailList[0].agreementAmount" disabled="true" ></form:input>
								</div>
								
								<label class="col-sm-2 control-label required-control"
									for="balanceAmount"><spring:message
										code="rnl.balanceAmount" text="Balance Amount" /></label>
								<div class="col-sm-4">
									<form:input name="balanceAmount" type="text"
										class="form-control" id="balanceAmount"
										path="contractMasterDto.totalBalanceAmount" disabled="true"></form:input>
								</div>
						</div>
					</div>
				</div>
				
		</div>
		
		
		<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse"
								data-parent="#accordion_single_collapse" href="#estate"> <spring:message
									code="rnl.transferDetails" text="Transfer Details"></spring:message></a>
						</h4>
					</div>
					<div id="estate" class="panel-collapse collapse in">
						<div class="panel-body">
							<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="newTenantName"><spring:message
										code="rnl.newTenantName" text="New Tenant Name" /></label>
								<div class="col-sm-4">
									<form:select path="vendorId"
										class="shift form-control chosen-select-no-results" id="newTenantName"
										data-rule-required="true">
									<form:option value=""><spring:message code='rnl.master.select' text="Select" /></form:option>
											<c:forEach items="${command.vendorList}" var="vendor">
												<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
											</c:forEach>
										</form:select>
								</div>
								<div class="col-sm-4">
									<label><a href="#" onclick="createData(this);">
										<spring:message code="rnl.addTenant" text="Add Tenant" /></a>
										<span class="warning-text margin-left-5">
											<spring:message code="rnl.tenantNotInList" text="(If Tenant Name not in the List)" />
										</span>
									</label>
								</div>
								
						</div>
						
						
						<div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="appreciationRate "><spring:message
										code="rnl.apprRate" text="Appreciation Rate in percentage" /></label>
								<div class="col-sm-4">
									<form:input name="appreciationRate" type="text"
										class="form-control hasNumber" id="appreciationRate"
										path="appreciationRate"></form:input>
								</div>
								
								
						</div>
					</div>
				</div>
				
		</div>
	</div>

			<div class="padding-top-10 text-center" id="chekListChargeId">
					<button type="button" id="confirmToProceedBT" class="btn btn-success"
						title='<spring:message code="rnl.proceed" text="Proceed To Confirm" />'
						onclick="saveOrUpdateForm(this,'SavedSuccessfully','TransferOfLease.html', 'saveform')">
						<spring:message code="rnl.proceed" text="Proceed To Confirm" />
					</button>
					<input type="button" id="resetBtn" class="btn btn-warning"
						title='<spring:message code="rnl.reset" text="Reset" />'
						onclick="window.location.href='TransferOfLease.html'" value="<spring:message code="rnl.reset" text="Reset" />" />
					<input type="button" id="backBtn" class="btn btn-danger"
						title='<spring:message code="rnl.master.back" text="Back" />'
						onclick="window.location.href='AdminHome.html'" value="<spring:message code="rnl.master.back" text="Back" />"  />
				</div>	
				
		

			
		</form:form>
	</div>
</div>
</div>
<!-- End of info box -->