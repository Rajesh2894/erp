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
<%@ taglib uri="www.ABMFrameworkTag.org" prefix="frameworktag"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/accountGeneralLedgerReport.js"></script>
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<script>
	$(function() {
		var response =__doAjaxRequest('AccountReceiptEntry.html?SLIDate', 'GET', {}, false,'json');
		var disableBeforeDate = new Date(response[0], response[1], response[2]);
		var date = new Date();
		var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
		
		$("#transactionDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			minDate : disableBeforeDate,
			maxDate : today
		});
		
		$("#fromDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
		$("#fromDateId").datepicker('setDate', new Date()); 
		$('#fromDateId, #toDateId, #transactionDateId').change(function(){
 			//alert();
  	  		var check = $(this).val();
  	  		if(check == ''){
  	  		$(this).parent().switchClass("has-success","has-error");
  	  		}
  	 		else{
  	 		$(this).parent().switchClass("has-error","has-success");}
  	 	    });

		$("#toDateId").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
		$("#toDateId").datepicker('setDate', new Date()); 
		$("#accountHeadId").chosen({
			width : "95%"
		});
		
		 $("#fromDateId").keyup(function(e){
			    if (e.keyCode != 8){    
			        if ($(this).val().length == 2){
			            $(this).val($(this).val() + "/");
			        }else if ($(this).val().length == 5){
			            $(this).val($(this).val() + "/");
			        }
			     }
			    });
		 $("#toDateId").keyup(function(e){
			    if (e.keyCode != 8){    
			        if ($(this).val().length == 2){
			            $(this).val($(this).val() + "/");
			        }else if ($(this).val().length == 5){
			            $(this).val($(this).val() + "/");
			        }
			     }
			    });
		 $("#transactionDateId").keyup(function(e){
			    if (e.keyCode != 8){    
			        if ($(this).val().length == 2){
			            $(this).val($(this).val() + "/");
			        }else if ($(this).val().length == 5){
			            $(this).val($(this).val() + "/");
			        }
			     }
			    });
		
		});
		
 </script>
<div id="content">
	<apptags:breadcrumb></apptags:breadcrumb>

	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="account.financial.daily.report"
						text="Daily Report" />
				</h2>
			</div>
			<div class="widget-content padding">
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>

				<form:form action="AccountFinancialReport.html"
					modelAttribute="reportDTO" class="form-horizontal"
					id="AccountFinancialReportfrm">
					<input type="hidden" value="${reportType}" id="reportTypeHidden">
					
					<form:hidden path="" id="indexdata" />
					<c:set var="count" value="0" scope="page" />
					
					<div class="form-group">
						
							<c:choose>
							<c:when test="${reportType eq 'GLR'}">
							<div class="col-sm-4">
							<label class="margin-left-20" for="flag"><input
													type="checkbox" name="flag" id="flag">
							<spring:message code="account.budgetopenmaster.selectall"
													text="" /></label>
							
							</div>
							</c:when>
							</c:choose>
							
							</div>
							
							<c:choose>
							<c:when test="${reportType eq 'GLR'}">
							<div class="form-group">
							<label for="fromDateId" class="col-sm-2 control-label required-control"><spring:message
                            code="from.date.label" text="From Date" /></label>
							<div class="col-sm-4">
							<div class="input-group">
							<form:input path="fromDate" id="fromDateId"	cssClass="mandColorClass form-control" data-rule-required="true"  maxlength="10"/>
							<label class="input-group-addon mandColorClass" for="fromDateId"><i class="fa fa-calendar"></i> </label>
							</div>
							</div>
							<label for="toDateId" class="col-sm-2 control-label required-control"><spring:message code="budget.reappropriation.authorization.todate" text="To Date" /></label>
							<div class="col-sm-4">
								<div class="input-group"><form:input path="toDate" id="toDateId" cssClass="mandColorClass form-control datepicker" data-rule-required="true"  maxlength="10"/>
									<label class="input-group-addon mandColorClass" for="toDateId"><i class="fa fa-calendar"></i> </label>
								</div>
							</div>
							</div>
							</c:when>
							</c:choose>
						
						<c:choose>
							<c:when test="${reportType eq 'GLR'}">
							
								
								<div id="opnId" class="">
							<div class="table-overflow-sm" id="opnBalTableDivID">
								<table class="table table-bordered table-condensed"
									id="opnBalTable">
									
										<tr>
											<th scope="col" width="85%" style="text-align: center"><spring:message
													code="account.deposit.accountHead" text="" /><span
												class="mand">*</span></th>
										
											<th class="text-center" scope="col" width="15%"><span
												class="small"><spring:message
														code="account.budgetopenmaster.addremove" text="" /></span></th>
										</tr>						

										<tr id="opnBalId" class="appendableClass">
											<td><form:select
													path="generalLedgerList[${count}].accountHeadId"
													cssClass="form-control mandColorClass chosen-select-no-results applyChoosen"
													id="pacHeadId${count}"
													data-rule-required="true">
													<form:option value="">
														<spring:message code="tax.heads.master.pacHeadId" text="Select Account Head" />
													</form:option>
													<c:forEach items="${acHeadLookUps}" var="lookUp">
													<form:option value="${lookUp.lookUpId}">${lookUp.descLangFirst}</form:option>
													</c:forEach>
												</form:select></td>
											
											<td class="text-center">
												<button class="btn btn-blue-2 btn-sm addButton" id="addButton${count}" data-toggle="tooltip" data-original-title="Add">
													<i class="fa fa-plus-circle"></i>
												</button>
												<button class="btn btn-danger btn-sm delButton" id="delButton${count}" data-toggle="tooltip" data-original-title="Delete">
													<i class="fa fa-trash-o"></i>
												</button>
											</td>
										</tr>

								</table>
							</div>
						</div>
						
								</c:when>
							</c:choose>
 					<INPUT type="hidden" id="count" value="0" />
					<div class="text-center margin-top-10">
					<button type="button" class="btn btn-blue-2" onclick="viewReport(this)"  title="Submit"><spring:message code="account.financial.view.report" text="View Report" /></button>
					<button type="button" class="btn btn-warning resetSearch" onclick="window.location.href = '${resetPage}'" title="Reset"><spring:message code="account.bankmaster.reset" text="Reset" /></button>
					<apptags:backButton url="AdminHome.html"></apptags:backButton>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</div>

