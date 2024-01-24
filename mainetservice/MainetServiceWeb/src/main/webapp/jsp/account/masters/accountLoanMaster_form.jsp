<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="java.util.Date"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<!-- <script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script> -->
<!-- <script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script> -->
<script src="js/mainet/validation.js"></script>
<script src="js/mainet/script-library.js"></script>
<script src="js/account/accountLoanMaster.js" type="text/javascript"></script>
<script>$("select").chosen();</script>
<!--  <script>
function dynamicSum(e) {
 
 var num1 =  Number($('#prnpalAmount'+i).val());
}           												                     }
</script> -->

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2><spring:message code="Loan.Master" text="Loan Master" /></h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
			</div>
		</div>
		<div class="widget-content padding">
			<form:form action="loanmaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="loanMasterform" id="id_loanMasterform" onsubmit="return validateForm()">
				<form:hidden path="accountLoanMasterDto.sliDate" id = "sliDate"/>
				<form:hidden path ="accountLoanMasterDto.lnNo" id = "loanCode" />
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<spring:message code="account.enter.loanPeriod" text="Enter Loan Period" var="enterLoanPeriod" />
				<spring:message code="Enter.Loan.Name" text="Enter Loan Name" var="enterLoanName" />
				<spring:message code="account.enter.loanPurpose" text="Enter Loan Purpose" var="enterLoanPurpose" />
				<spring:message code="account.enter.sanctionNo" text="Enter Sanction Number" var="enterSanctionNumber" /> 
				<spring:message code="Loan.Sanction.Date" text="Loan Sanction Date" var="loanSanctionDate" />  
				<spring:message code="Number.Of.Installments" text="Enter Number Of Installments" var="enterNumberOfInstallments" />
				<spring:message code="Resolution.Number" text="Resolution Number" var="resolutionNumber" /> 
				<spring:message code="bill.resolution.date" text="Resolution Date" var="resolutionDate" /> 
				<spring:message code="account.enter.loanRemark" text="Enter Loan Remarks" var="enterLoanRemarks" />
				<spring:message code="account.enter.installmentAmt" text="Enter Installment Amount" var="enterInstallmentAmount" />
				<spring:message code="account.enter.repaymentDate.row" text="Enter Repayment Date" var="enterRepaymentDate" />
				<spring:message code="account.enter.principalAmt.row" text="Enter Principal Amount" var="enterPrincipalAmount" />
				<spring:message code="account.enter.interestAmt" text="Enter Interest Amount" var="enterInterestAmount" />
				
				<!-- ----------------- this div below is used to display the error message on the top of the page--------------------------->
				<div
					class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
						<span id="errorId"></span>
				</div>

				<!-- ---------------------------------------------------------------------------------------------------------------------- -->

				<div class="mand-label clearfix">
					<span>Field with <i class="text-red-1">*</i> is mandatory
					</span>
				</div>
				<h4><spring:message code="Loan.Details" text="Loan Details" /></h4>
		<!-- ---------------------------------------------------------------------------------------------------------------------- -->
				
				<c:if test= "${command.saveMode eq 'V' || command.saveMode eq 'E'}">
				<div class="form-group">
					<label for="text-1"
						class="control-label col-sm-2">Loan Code</label> 
						<div class="col-sm-4">
					<form:input 
								id = "lnNo"
								path="accountLoanMasterDto.lnNo"
								cssClass= "form-control"	
								disabled="true"		
							/>
							</div>
							
				</div>
				</c:if>
				
				<div class="form-group">
					<apptags:input labelCode="Loan.Name"
						path="accountLoanMasterDto.loanName" isMandatory="true"
						placeholder='${enterLoanName}'
						isDisabled="${command.saveMode eq 'V' ? true : false }">
					</apptags:input>

					<label for="text-1" class="col-sm-2 control-label "><spring:message code="receivable.demand.entry.deptName" text="Department Name" />
						<span class="mand">*</span>
					</label>
					<div class="col-sm-4">
						<!-- This form tag binds the incoming data from the user to the dto using the path attribute -->
						<form:select path="accountLoanMasterDto.lnDeptname"
							class="form-control mandColorClass chosen-select-no-results"
							label="account.common.select"
							disabled="${command.saveMode eq 'V' ? true : false }" id="deId">
							<!-- Here the option is being loaded in the drop down list using forEach loop  -->
							<form:option value="" selected="true"> <spring:message code="Select.Department.Name" text="Select Department Name" /></form:option>
							<c:forEach items="${command.depList}" var="dept">
								<form:option value="${dept.key}" code="${dept.key}">${dept.value}</form:option>
							</c:forEach>

						</form:select>
					</div>
				</div>
		<!-- ---------------------------------------------------------------------------------------------------------------------- -->

				<div class="form-group">
				<%-- 	<apptags:input labelCode="Loan Amount" 
					cssClass = "hasDecimal"
						path="accountLoanMasterDto.santionAmount" isMandatory="true"
						placeholder="Enter Loan Amount"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
					 --%>

			 <label for= "text-1" class = "col-sm-2 control-label required-control"><spring:message code="Loan.Amount" text="Loan Amount" /></label>
			<div class = "col-sm-4">
				<form:input
					id ="santionAmount"
					path="accountLoanMasterDto.santionAmount"
					cssClass ="text-right form-control"
					disabled="${command.saveMode eq 'V' ? true : false }"
				 	onchange="rangeValidation()"
				 	onkeypress="return hasAmount(event,this,12,2)"
				 />
			</div>
					<apptags:input labelCode="Loan.Purpose"
						path="accountLoanMasterDto.lnPurpose" isMandatory="true"
						placeholder='${enterLoanPurpose}'
						isDisabled="${command.saveMode eq 'V' ? true : false }">
					</apptags:input>
				</div>
				
	<!-- ********************************************************************************************************************************************************** -->			
				
				
				
				
				
	<!-- ********************************************************************************************************************************************************** -->					
				  <div class="form-group ">
					<label for="text-1" class="col-sm-2 control-label "><spring:message code="accounts.receipt.received_from" text="Received From" />
						<span class="mand">*</span>
					</label>
					<div class="col-sm-4">
						<form:select path="accountLoanMasterDto.vendorId"
							class="form-control mandColorClass chosen-select-no-results"
							label="acc.report.select"
							disabled="${command.saveMode eq 'V' ? true : false }" id="vendeId">
							<form:option value="" selected="true"><spring:message code="acc.report.select" text="Select" /></form:option>
							<c:forEach items="${command.vendorList}" varStatus="status"
								var="vendor">
								<form:option value="${vendor.vmVendorid}">${vendor.vmVendorcode} - ${vendor.vmVendorname}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label for="text-1" class="col-sm-2 control-label "><spring:message code="Loan.Period" text="Loan Period" />
						<span class="mand">*</span>
					</label>
					<div class="col-sm-4">
						<div class="input-group col-sm-12 ">	
												
							<form:input type='text'
										path="accountLoanMasterDto.loanPeriod"
										class='form-control hasNumber' placeholder='${enterLoanPeriod}' id="loanPeriod" 
											disabled="${command.saveMode eq 'V' ? true : false}" />							
							<c:set var="baseLookupCode" value="UTS" />
							<div class="input-group-field">
								<div class="col-sm-4">
							<form:select path="accountLoanMasterDto.loanPeriodUnit"
								         cssClass="dropdown-divider form-control mandColorClass chosen-select-no-results"
										 label="rChallan.unit"
										 id="unit"
										disabled="${command.saveMode eq 'V' ? true : false }" >
							<form:option value="" selected="true"><spring:message code="rChallan.unit" text="Unit" />  </form:option>
								<c:forEach items="${command.getLevelData(baseLookupCode)}" var="lookUp">
									<form:option value="${lookUp.lookUpDesc}"
										code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
								</c:forEach>
							</form:select>
									</div></div>	
						</div>
					</div>
				 
			 		<%-- <apptags:input labelCode="Loan Period" cssClass ="hasNumber"
						path="accountLoanMasterDto.loanPeriod" isMandatory="true"
						placeholder="Enter Loan Period"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>  --%>
				 
				</div>  

				<%-- <div class="form-group">
			  	<apptags:input labelCode="Loan Sanction Number"
						path="accountLoanMasterDto.SanctionNo" isMandatory="true"
						placeholder="Enter Sanction Number"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>  

					<apptags:input labelCode="Loan Sanction Date" isReadonly="true"
						cssClass="datepicker fa fa-calendar"
						path="accountLoanMasterDto.sanctionDate" isMandatory="true"
						isDisabled="${command.saveMode eq 'V' ? true : false }" />
				</div> --%>
				
				<div class="form-group"> 
					<apptags:input labelCode="Loan.Sanction.Number"
						path="accountLoanMasterDto.SanctionNo" isMandatory="true"
						placeholder='${enterSanctionNumber}' 
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
					
					<label for="text-1" class="control-label col-sm-2 required-control"><spring:message code="Loan.Sanction.Date" text="Loan Sanction Date" /></label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input class="form-control datepicker" id="sanctionDate"
								path="accountLoanMasterDto.sanctionDate"
								 placeholder='${loanSanctionDate}' maxlength="10"
								disabled="${command.saveMode eq 'V' ? true : false }"  
								onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"/>
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>

						</div>
					</div>
				</div>
				

				<div class="form-group">
					<apptags:input labelCode="Number.Of.Installments"
						path="accountLoanMasterDto.noOfInstallments" isMandatory="true"
						placeholder='${enterNumberOfInstallments}'
						 cssClass = "hasNumber"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

					<%-- <apptags:input labelCode="Interest Rate"
					cssClass="  mandColorClass hasDecimal "
						path="accountLoanMasterDto.lnInrate" isMandatory="true"
						placeholder="Enter Interest Rate"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						
					></apptags:input> --%>
					<label for = "text-1" class= "col-sm-2 control-label required-control"><spring:message code="Interest.Rate" text="Interest Rate" /></label>
					<div class= "col-sm-4">
						<form:input 
						cssClass ="form-control"
						id ="lnInrate"
						path="accountLoanMasterDto.lnInrate"
						onkeypress ="return hasAmount(event,this,12,2)"
						onchange="rangeValidation()"
						disabled = "${command.saveMode eq 'V' ? true : false }"
						/>
					</div>
				
				</div>
				
				<%-- <div class="form-group">
					<apptags:input labelCode="Resolution No."
						path="accountLoanMasterDto.resNo" isMandatory="true"
						placeholder="Enter Resolution No."
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

					<apptags:input labelCode="Resolution Date"
						cssClass="datepicker fa fa-calendar"
						path="accountLoanMasterDto.resDate" isMandatory="true"
						placeholder="Resolution Date"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						
						></apptags:input>
				</div>
				 --%>
				
				<div class="form-group">
					<label for="text-1" class="control-label col-sm-2 required-control"><spring:message code="Resolution.Number" text="Resolution Number" />
						</label>
					<div class="col-sm-4">
							<form:input class="form-control hasNumber" id="resNo"
								path="accountLoanMasterDto.resNo"
								placeholder='${resolutionNumber}' maxlength="10"
								disabled="${command.saveMode eq 'V' ? true : false }"  
								/>
					
					</div>
					
					<label for="text-1" class="control-label col-sm-2 required-control"><spring:message code="bill.resolution.date" text="Resolution Date" />
						</label>
					<div class="col-sm-4">
						<div class="input-group">
							<form:input class="form-control datepicker" id="rsnDate"
								path="accountLoanMasterDto.resDate"
							    placeholder='${resolutionDate}' maxlength="10"
								disabled="${command.saveMode eq 'V' ? true : false }"  
								onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"/>
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>

						</div>
					</div>
				</div>
				
				
				<%-- <div class="form-group">
					<apptags:input labelCode="Remarks"
						path="accountLoanMasterDto.lmRemark" isMandatory="true"
						placeholder="Enter Loan Remarks"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						
						></apptags:input>
						
						<apptags:input labelCode="Installment Amount"
						path="accountLoanMasterDto.instAmt" isMandatory="true"
						placeholder="Enter Installment Amount"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						onBlur="return hasAmount(event,this,12,2)"
						></apptags:input>
				</div> --%>
				
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="receipt.register.remarks" text="Remarks"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input id="lmRemark" path="accountLoanMasterDto.lmRemark" 
							class="form-control mandColorClass  "
							placeholder='${enterLoanRemarks}'
							disabled="${command.saveMode eq 'V' ? true : false }"/>

					</div>
					
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="Installment.Amount" text="Installment Amount"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:input id="instAmt" path="accountLoanMasterDto.instAmt"
							class="form-control mandColorClass "
							placeholder='${enterInstallmentAmount}'
							disabled="${command.saveMode eq 'V' ? true : false }"
							onkeypress="return hasAmount(event,this,10,2)" />
				
					</div>
				</div>
				
				
				
									
									<c:set var="d" value="0" scope="page" />



				<!------------------------------------------------------------  -->
				<!-- Re-payment Schedule form  -->
				<!------------------------------------------------------------  -->


				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="Repayment.Schedule" text="Repayment Schedule" />
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse">
							<div class="panel-body">
								<table class="table table-bordered margin-bottom-10 appendableClass" id = "repayTable">
									<thead>
										<tr>
										<!-- <th width="6%">S.No</th> -->
											<th width="8%" class="text-center"><spring:message code="Installment.Sequence.Number" text="Installment Sequence Number" /></th>
											<th class="text-center"><spring:message code="Repayment.Date" text="Repayment Date" /></th>
											<th class="text-center"><spring:message code="Principal.Amount" text="Principal Amount" /></th>
											<th class="text-center"><spring:message code="Interest.Amount" text="Interest Amount" /></th>
											<th class="text-center"><spring:message code="Repayment.Total.Amount" text="Repayment Total Amount" /></th>
											<c:if test="${command.saveMode ne 'V'}"><th class="text-center"><spring:message code="Action.s" text="Action(s)" /></th></c:if>
										</tr>
									</thead>
									
			<!-- ************************************************************************************************************************ -->
									<!-- Original#2 -->
			<!-- ************************************************************************************************************************ -->	
									<tbody>
									<c:choose>
										<c:when test="${fn:length(command.accountLoanMasterDto.accountLoanDetList) > 0}">
										<c:set var="e" value="0" scope="page"/>
											<c:forEach var="det" items="${command.accountLoanMasterDto.accountLoanDetList}" varStatus="status">
											<tr class="appendableClass">
											<td align="center"><form:input path=""
															cssClass="form-control hasNumber" id="sequence${e}" 
															readonly="true"
															value = "${e+1}"
															 />
										<%-- 	<td><form:input
															path="accountLoanMasterDto.accountLoanDetList[${e}].instSeqno"
															cssClass="form-control hasDecimal"
															disabled="${command.saveMode eq 'V' ? true : false }"
															id="instSeqno${e}" placeholder="Enter Installment Sequence Number"
															 />
											</td> --%>
											<td><div><form:input cssClass="form-control datepicker"
												    id="instDueDate${e}"
													path="accountLoanMasterDto.accountLoanDetList[${e}].instDueDate"
													placeholder="Enter Repayment Date" maxlength="10"
													disabled="${command.saveMode eq 'V' ? true : false }"  
													onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"/>
											</td>
											<td><form:input type="text" cssClass="form-control hasDecimal text-right" name=""
													class ="num"
													id="prnpalAmount${e}"
													path="accountLoanMasterDto.accountLoanDetList[${e}].prnpalAmount"
													placeholder="Enter Principal Amount"
													disabled="${command.saveMode eq 'V' ? true : false }"
													onkeypress ="return hasAmount(event,this,10,2)"
													onchange = "dynamicSum('${e}')"
													/>
											</td>
											<td><form:input type="text" cssClass="form-control hasDecimal text-right" name=""
													id="intAmount${e}"
													path="accountLoanMasterDto.accountLoanDetList[${e}].intAmount"
													placeholder="Enter Interest Amount"
													disabled="${command.saveMode eq 'V' ? true : false }" 
													 onchange ="dynamicSum(this)"
													onkeypress ="return hasAmount(event,this,10,2)"
													/></td>
											<td><form:input type="text" cssClass="form-control hasDecimal text-right" name=""
													id="balIntAmt${e}"
													path="accountLoanMasterDto.accountLoanDetList[${e}].balIntAmt"
													readonly="true" 
													/></td>
												 
											<c:if test="${command.saveMode ne 'V'}">
												<td class="text-center">
														<button type="button" class="btn btn-blue-2 addButton"
																data-original-title="Add"
													            id ="add"
													             onclick="addRow();"
													            >
														       <i class="fa fa-plus" aria-hidden="true"></i>
													    </button>
														<button type="button" class="btn btn-danger delButton"
															    data-original-title="Remove"
															    onclick="deleteRow($(this),'removeIds');"
															    >
													            <i class="fa fa-trash-o" aria-hidden="true"></i>
												       </button>
												       
												       <button type="button" class="btn btn-success btn-sm"
																title="Bill Refund"
																onclick="doOpenBillRefundPageForLoan(${det.lnmas.loanId},${det.lnDetId},${det.balIntAmt});">
																<i class="fa fa-file-text-o" aria-hidden="true"></i>
														</button>
											   </td>
											</c:if>
											</tr>
											<c:set var="e" value="${e + 1}"  />
											</c:forEach>
									</c:when>
									<c:otherwise>
									<c:set var="e" value="0" scope="page" />
										<tr>
											<td align="center"><form:input path=""
												cssClass="form-control mandColorClass" id="sequence${e}"
												value="${e+1}" disabled="true" />
											</td>
										<%-- 	<td><form:input path="accountLoanMasterDto.accountLoanDetList[${e}].instSeqno"
															cssClass="form-control hasDecimal"
															disabled="${command.saveMode eq 'V' ? true : false }"
															id="instSeqno${e}" placeholder="Enter Installment Sequence Number"/>
											</td> --%>
											<td><form:input
												path="accountLoanMasterDto.accountLoanDetList[${e}].instDueDate"
												id="instDueDate${e}"
												cssClass="required-control form-control datepicker" 
												placeholder='${enterRepaymentDate}' maxlength="10"
												disabled="${command.saveMode eq 'V' ? true : false }"  
												onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"/>
											</td>
											<td><form:input type="text" cssClass="form-control hasDecimal text-right"
													id="prnpalAmount${e}"
													path="accountLoanMasterDto.accountLoanDetList[${e}].prnpalAmount"
													placeholder='${enterPrincipalAmount}'
													disabled="${command.saveMode eq 'V' ? true : false }"
												 	 onkeyup ="dynamicSum(this)"
													onkeypress ="return hasAmount(event,this,10,2)"/>
											</td>
											<td><form:input type="text" cssClass="form-control hasDecimal text-right" name=""
													id="intAmount${e}"
													path="accountLoanMasterDto.accountLoanDetList[${e}].intAmount"
													placeholder='${enterInterestAmount}'
													disabled="${command.saveMode eq 'V' ? true : false }" 
													 onkeyup ="dynamicSum(this)"
													 class ="amt"
													onkeypress ="return hasAmount(event,this,10,2)"/>
											</td>
											<td>
											<form:input type="text" cssClass="form-control hasDecimal text-right" name=""
													id="balIntAmt${e}" 	 class ="amt"
													path="accountLoanMasterDto.accountLoanDetList[${e}].balIntAmt"
													readonly="true"
													/>
											</td>
											
											</td> 
												<td align="center">
												<a href="#"  
												data-placement="top" class="btn btn-blue-2  btn-sm"
												data-original-title="Add" onclick="addRow();"><strong
													class="fa fa-plus"></strong><span class="hide"></span></a>
													
												<a href="#"  
													data-placement="top" class="btn btn-danger btn-sm"
													data-original-title="Delete"
													onclick="deleteRow($(this),'removeIds');">
														<strong class="fa fa-trash"></strong> <span class="hide"><spring:message
																 text="Delete" /></span>
												</a>
												
												</td>
 											
										</tr>
										<c:set var="e" value="${e + 1}" scope="page" />
									
									</c:otherwise>
									
									
									</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>

				<!------------------------------------------------------------  -->
				<!--  Re-payment form ends here -->
				<!------------------------------------------------------------  -->




<!------------------------------------------------------------  -->
				<!--  Receipt Details form starts here -->
				<!------------------------------------------------------------  -->



				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="accounts.receipt.receipt_details" text="Receipt Details" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse">
							<div class="panel-body">
								
								<table class="table table-bordered margin-bottom-10">
									<thead>
										<tr>
											<th class="text-center"> <spring:message code="accounts.receipt.receipt_no" text="Receipt Number" /></th>
											<th class="text-center"> <spring:message code="accounts.receipt.received_from" text="Received From" /></th>
											<th class="text-center"> <spring:message code="accounts.receipt.receipt_date" text="Receipt Date" /></th>
											<th class="text-center"> <spring:message code="accounts.receipt.receipt_amount" text="Receipt Amount" /></th>
											<th class="text-center"> <spring:message code="accounts.receipt.narration" text="Narration" /></th>
										</tr>
									</thead>
									<tbody >
										<c:forEach items="${command.receiptMasBeanList}" var="data"
											varStatus="index">
											<tr>
												<td class="text-center">${data.rmRcptid}</td>
												<td class="text-center">${data.rmReceivedfrom}</td>
												<td class="text-center"><fmt:formatDate
														pattern="dd/MM/yyyy" value="${data.rmDate}" /></td>
												<td class="text-right">${data.rmAmount}</td>
												<td class="text-center">${data.rmNarration}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>


				<!------------------------------------------------------------  -->
				<!--   Receipt Details ends here -->
				<!------------------------------------------------------------  -->



				<!------------------------------------------------------------  -->
				<!--   payment details form starts from here -->
				<!------------------------------------------------------------  -->


				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a3" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="account.contra.voucher.payment.detail" text="Payment Details" />
								</a>
							</h4>
						</div>
						<div id="a3" class="panel-collapse collapse">
							<div class="panel-body">
								<table class="table table-bordered margin-bottom-10 myTable">
									<thead>
										<tr>
											<th class="text-center"> <spring:message code="advance.management.master.paymentnumber" text="Payment Number" /></th>
											<th class="text-center"> <spring:message code="advance.management.master.paymentdate" text="Payment Date" /></th>
											<th class="text-center"> <spring:message code="advance.management.master.paymentamount" text="Payment Amount" /></th>
										</tr>
									</thead>
									
									
									<tbody>
									 <c:forEach items="${command.paymentList}" var="paymentDto"
													varStatus="index">
										<tr>
											<td class="text-center">${paymentDto.paymentNo}</td>
											<td class="text-center">${paymentDto.paymentEntryDate}</td>
											<td class="text-right">${paymentDto.paymentAmount}</td>
										</tr>
										</c:forEach>
									</tbody>
									<%-- <tbody>
										<tr>
										
											<td>
												<form:input
												cssClass = "form-control"
												id = "paymentNumber"
												path = ""
												disabled="${command.saveMode eq 'V' ? true : true }" 
												/>
												</td>
											<td>
											<form:input
												cssClass = "form-control"  
												id = "paymentDate"
												path = ""
												disabled="${command.saveMode eq 'V' ? true : true }" 
												/>
												</td>
											<td><form:input
												cssClass = "form-control"
												id = "paymentAmount"
												path = ""
												disabled="${command.saveMode eq 'V' ? true : true }" 
												/>
												
												</td>
										</tr>
									</tbody>
 --%>								</table>
							</div>
						</div>
					</div>
				</div>

				<!-- Below is the div for three buttons 
					If the condition is true, only then those buttons(save and reset) will be displayed.
					Back button needs no condition.
				 -->
				<div class="text-center margin-top-15">
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success"
							 data-original-title="Save"
							onclick="saveLoanForm(this)">
							<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i><spring:message code="account.bankmaster.save" text="Save" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
						<button type="button" class="btn btn-warning"
							 data-original-title="Reset"
							onclick="ResetForm();">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i><spring:message code="account.bankmaster.reset" text="Reset" />
						</button>
					</c:if>
					<button type="button" class="btn btn-danger" 
						 onclick="backLoanMasterForm();">
						<i class="fa fa-chevron-circle-left padding-right-5"
							aria-hidden="true"></i><spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>