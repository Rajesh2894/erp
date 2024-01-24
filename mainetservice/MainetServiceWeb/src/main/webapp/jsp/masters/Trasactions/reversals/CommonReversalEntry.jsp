<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<script type="text/javascript"
	src="js/masters/trasactions/reversal/CommonReversalEntry.js"></script>

<style>
.sectionSeperator {
	border-bottom: 1px solid #123456;
	border-top: 1px solid #123456;

}
</style>
 <script>
 $( document ).ready(function() {	
 $('.datepicker').datepicker({
		dateFormat: 'dd/mm/yy',	
		changeMonth: true,
		changeYear: true
	});
 });
 </script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message
						code="Reversal.Entry" />
			</h2>	
			
		</div>

		<div class="widget-content padding">

		
			<div class="mand-label clearfix">
				<span><spring:message code="property.Fieldwith" /><i
					class="text-red-1">* </i> <spring:message
						code="property.ismandatory" /></span>
			</div>
		
		
			    <form:form action="CommonReversalEntry.html"
				class="form-horizontal form" name="CommonReversalEntry"
				id="CommonReversalEntry">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				
				<div class="warning-div error-div alert alert-danger alert-dismissible" id="errorDiv"></div>

				<div class="accordion-toggle ">
				
               <div class="form-group">
			 		
					<label class="col-sm-2 control-label required-control"><spring:message
									code="wt.Department" text="Department" /></label>
					<%-- <div class="col-sm-4">
					
					<form:input path= "dept.lookUpDesc" value="${dept.getLookUpDesc()}" class="form-control" id=" "  disabled="true"></form:input>
					
					</div> --%>
					
					<div class="col-sm-4">

						<form:select path="depId" id="depId"
							class="chosen-select-no-results" data-rule-required="true"
							onchange=""
							>
							<form:option value="0">
								<spring:message code="adh.select" text="Select"></spring:message>
							</form:option>
							<c:forEach items="${command.deparatmentList}" var="department">
								<form:option value="${department.dpDeptid}">${department.dpDeptdesc}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="col-sm-2 control-label"><spring:message
							code="common.reversal.type" text="Reversal Type" /><span
						class="mand">*</span></label>
					<div class="col-sm-4">
						<%-- <form:hidden path="" id="transactionTypeIdHidden" /> --%>
						
						
					<form:select path="trasactionReversalDTO.transactionType"  id="Id"  cssClass="form-control chosen-select-no-results">
			         <c:forEach items="${command.transactionType}" var="lookUp">
					 <form:option value="${lookUp.lookUpId}">${lookUp.lookUpDesc}</form:option>
					 </c:forEach> 
				     </form:select> 
						
					</div>
					
				</div>
				<div class="form-group">
				
					<label class="col-sm-2 control-label"><spring:message
							code="common.trasactionNo" text="Receipt/Bill No." /><span
						class="mand">*</span></label>
					<div class="col-sm-4">
						<form:input path="trasactionReversalDTO.transactionNo" id="transactionNo" class="form-control hasNumber mandColorClass" />
					</div>
					
					<label class="col-sm-2 control-label required-control"><spring:message code="common.date" text="Receipt/Bill Date"/></label>
		            <div class="col-sm-4">
		             <div class="input-group"> 
		             <form:input path="trasactionReversalDTO.transactionDate"  class="datepicker form-control mandColorClass dateClass" id="transDate"  data-rule-required="true" placeholder="DD/MM/YYYY" autocomplete="off"/>
		             <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		    
		           </div>
		           </div>
					
			</div>

					<div class="form-group">
						<spring:message code="water.message" />
					</div>

					<div class="form-group searchBtn">
						<div class="text-center padding-bottom-10">
							<button type="button" class="btn btn-blue-2 " id="serchBtn"
								onclick="CommonReversalEntrySerach()">
								<i class="fa fa-search"></i>
								<spring:message code="property.changeInAss.Search" />
							</button>
							 <button type="button" class="btn btn-warning " id="resetBtn"
								onclick="CommonReversalEntryReset(this)">		
							<spring:message code="water.btn.reset"  text="Reset"/>
							</button>
							
							
						<button type="button" class="btn btn-danger" id="btn3" onclick="window.location.href='AdminHome.html'">
					   <spring:message code="water.nodues.cancel"  text="Cancel"/></button>
							
						</div>
						
						 <input type="hidden" id="reverseId" name="reverseFlag" value=""/>
						
					</div>
							<div class="table-responsive clear" id="PropDetails">
							<table id="datatables" class="table table-striped table-bordered">
								<thead>
									<tr>

										<th width="2%"><spring:message
												code="water.serialNo" text="Sr. No."/></th>
										<th width="15%"><spring:message
												code="common.reversal.appNo" text="Application No"/></th>
										<th width="15%"><spring:message
												code="common.reversal.receiptNo" text="Receipt No" /></th>
										<th width="15%"><spring:message code="common.ownerName" text="Owner Name"/></th>
										<th width="10%"><spring:message code="common.receipt.amount" text="Receipt Amount" /></th>
										<th width="5%"><spring:message code="common.reversal.action" text="Action" /></th>
									</tr>
								</thead>
                                <tbody>
			   	                  <c:forEach var="receiptList" items="${command.getReceiptMasList()}" varStatus="status" >
								
									<tr>

										<td align="center">
										<label class="margin-left-20">${status.index+1}</label>
										</td>
										<td class="text-center">${receiptList.additionalRefNo}</td>
										<td class="text-center">${receiptList.rmRcptno}</td>
										<td class="text-center">${receiptList.rmReceivedfrom}</td>
										<td class="text-center">${receiptList.rmAmount}</td>
										<td class="text-center">
										<button class="btn btn-primary btn-sm" title='Reverse' type="button" onclick="showField()"><i class='fa fa-file-text-o' aria-hidden='true'></i></button>
										<!-- <button class="btn btn-primary btn-sm" title='Receipt Reversal' type="button" onclick="Reversal(this)"><i class='fa fa-file-text-o' aria-hidden='true'></i></button> -->
										
										</td>

									</tr>
					                  </c:forEach>
								</tbody>
							</table>
							
						</div>
						
						<div id="reasonForDeletion">
						<div class="form-group"  id="">
						<apptags:textArea isMandatory="true" labelCode="water.meter.cutOffReason" path="tbServiceReceiptMasBean.receiptDelRemark" maxlegnth="500" cssClass="preventSpace"> </apptags:textArea>
						</div>
						<div class="form-group"  id="">
						
						<div class="text-center padding-bottom-10">
							<button type="button" class="btn btn-blue-1 " id="deleteBtn"
								onclick="Reversal(this)">
								<spring:message code="wt.delete"  text="Delete"/>
							</button>
							
							
							<button type="button" class="btn btn-danger" id="btn3" onclick="window.location.href='AdminHome.html'">
					       <spring:message code="water.nodues.cancel"  text="Cancel"/></button>
						</div>
					    </div>		
						
						</div>
						
						
						

				</div>
 
		</form:form>
	
	  </div>

      </div>

      </div>
