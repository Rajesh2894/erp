<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script src="js/account/accountGrantMaster.js"></script>
<script>$("select").chosen();</script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2> <spring:message code="Grant.Master" text="Grant Master" />
			</h2>
		</div>
		<div class="widget-content padding">
			<form:form action="grantMaster.html" method="POST"
				commandName="command" class="form-horizontal form"
				name="grantMaster" id="grantMasterId">
				<!-- Start Validation include tag -->
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
		
			
				 <div
					class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>  
					</button>
						<span id="errorId"></span>
				</div>
				
				
				<div class="mand-label clearfix">
					<span><spring:message code="account.common.mandmsg" /> <i
					class="text-red-1">*</i> <spring:message
						code="account.common.mandmsg1" /></span>
				</div>
				
	 			
	 		<h4><spring:message
								code="Grant.Number" text="Grant Details " /></h4>
	 		<c:if test= "${command.saveMode eq 'V' || command.saveMode eq 'E'}">
				<div class="form-group">
					<label for="text-1"
						class="control-label col-sm-2 "><spring:message
								code="Grant.Number" text="Grant Number " /></label> 
						<div class="col-sm-4">
					<form:input 
								id = "lnNo"
								path="accountGrantMasterDto.grtNo"
								cssClass= "form-control"	
								readonly="true"		
							/>
							</div>
							
				</div>
				</c:if>
	 		
				<div class="form-group">
					<label for="text-1" class="control-label col-sm-2 required-control"><spring:message code="Grant.Type" text="Grant Type" /></label>
					<div class="col-sm-4">
						<label class="radio-inline "> <form:radiobutton
								id="grtType" path="accountGrantMasterDto.grtType" value="R"
								checked="true"
								disabled ="${command.saveMode eq 'V' ? true : false }"
								 /><spring:message code="acc.Revenue" text="Revenue" />
						</label> <label class="radio-inline "> <form:radiobutton
								id="Capital" path="accountGrantMasterDto.grtType" value="C" 
								disabled ="${command.saveMode eq 'V' ? true : false }"
								/>
							<spring:message
								code="acc.Capital" text="Capital" />
						</label>
					</div>
					<spring:message code="acc.Grant.Name" var="EnterGrantName" />
					<apptags:input labelCode="Grant.Name"
						path="accountGrantMasterDto.grtName" isMandatory="true"
						placeholder="${EnterGrantName}"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
				</div>
				
				<spring:message code="Enter.Grant.Date" var="EnterGrantDate" />
			 <div class="form-group">
					
						<form:hidden path="accountGrantMasterDto.sliDate" id = "sliDate"/>							
						<label class ="control-label col-sm-2 required-control" for = "text-1"><spring:message code="Grant.Date" text="Grant Date" /></label>
						<div class="col-sm-4">
							<div class = "input-group">
							<fmt:formatDate pattern="dd/MM/yyyy" value="${command.accountGrantMasterDto.grtDate}" var="grtDate" />
							<form:input 
							id = "grtDate" value="${grtDate}"
							cssClass ="datepicker fa fa-calendar form-control dateValidation"
							path ="accountGrantMasterDto.grtDate"	 						
							placeholder="${EnterGrantDate}"
							disabled ="${command.saveMode eq 'V' ? true : false }"
							maxlength="10"
							onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"
							onChange= "validateDateFormat()"
							/>
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>
							</div>
							
						</div>
					<spring:message code="Enter.Nature.of.the.Grant" var="EnterNatureoftheGrant" />	 
					<label for="text-1" class="control-label col-sm-2 required-control">
						<spring:message code="Nature.of.the.Grant" text="Nature of the Grant" /></label>
					<div class="col-sm-4">
						<form:textarea rows="1" cols="50"
							path="accountGrantMasterDto.grtNature" id = "grantNature"
							placeholder="${EnterNatureoftheGrant}"
							class="form-control padding-left-10"
							disabled="${command.saveMode eq 'V' ? true : false }"></form:textarea>
					</div>
			</div>
				
				
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="Grant.From.Period" text="Grant From Period"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select id="fromYearId" path="accountGrantMasterDto.fromPerd"
							class="form-control mandColorClass   chosen-select-no-results "
							placeholder=""
							disabled="${command.saveMode eq 'V' ? true : false }">
							<form:option value="" selected="false">
								<spring:message code="account.common.select" text="Select" />
							</form:option>
							<c:forEach items="${command.listOfinalcialyear}" var="financeMap">
								<form:option value="${financeMap.key}" code="${financeMap.key}">${financeMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
					
					<label class="col-sm-2 control-label required-control"> <spring:message
							code="Grant.To.Period" text="Grant To Period"></spring:message>
					</label>
					<div class="col-sm-4">
						<form:select id="toYearId" path="accountGrantMasterDto.toPerd"
							class="form-control mandColorClass   chosen-select-no-results"
							disabled="${command.saveMode eq 'V' ? true : false }"
							onChange = "dateValueCheck()"
							>
							<form:option value="" selected="true">
								<spring:message code="account.common.select" text="Select" />
							</form:option>
							<c:forEach items="${command.listOfinalcialyear}" var="financeMap">
								<form:option value="${financeMap.key}" code="${financeMap.key}">${financeMap.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				
				
				
		<div class="form-group">
					<%-- <apptags:input labelCode="Sanction No."
						path="accountGrantMasterDto.sactNo" isMandatory="true"
						placeholder="Enter Sanctioning No."  
						id = "sactNo"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input> --%>
						
						<label for = "text-1" class = "col-sm-2 control-label required-control"><spring:message
								code="Sanction.Number" text="Sanction Number" /></label>
						<div class = "col-sm-4">
						<form:input 
							id ="sactNo"
							path="accountGrantMasterDto.sactNo"
							cssClass = "form-control text-right"
							disabled="${command.saveMode eq 'V' ? true : false }"/>
						</div>

						
						<label for = "text-1" class = "col-sm-2 control-label required-control"><spring:message
								code="Sanction.Amount" text="Sanction Amount" /></label>
						<div class = "col-sm-4">
						<form:input 
							id ="santionAmt"
							path="accountGrantMasterDto.santionAmt"
							cssClass = "form-control text-right"
							disabled="${command.saveMode eq 'V' ? true : false }"
							onkeypress="return hasAmount(event, this, 10, 2)" />
						</div>
		</div>  <spring:message
								code="Enter.Sanction.Date" var="EnterSanctionDate" />

					<div class="form-group">
					
					<label for="text-1" class="control-label col-sm-2 required-control"><spring:message
								code="Sanction.Date" text="Sanction Date " /></label>
					<div class="col-sm-4">
						<div class="input-group">
							
							<fmt:formatDate pattern="dd/MM/yyyy" value="${command.accountGrantMasterDto.sanctionDate}" var="sanctionDate" />
							<form:input class="form-control datepicker dateValidation" id="sanctionDate"
								path="accountGrantMasterDto.sanctionDate"  value="${sanctionDate}"
								placeholder="${EnterSanctionDate}" maxlength="10"
								disabled="${command.saveMode eq 'V' ? true : false }"  
								onkeydown="this.value=this.value.replace(/^(\d\d)(\d)$/g,'$1/$2').replace(/^(\d\d\/\d\d)(\d+)$/g,'$1/$2').replace(/[^\d\/]/g,'')"/>
							<label class="input-group-addon" for="trasaction-date-icon30"><i
								class="fa fa-calendar"></i></label>

						</div>
					</div>
					<spring:message
									code="Enter.Sanctioning.Authority" var="EnterSanctioningAuthority" />
					<apptags:input labelCode="Sanctioning.Authority"
						path="accountGrantMasterDto.sanctionAuth" isMandatory="true"
						placeholder="${EnterSanctioningAuthority}"
						isDisabled="${command.saveMode eq 'V' ? true : false }" 
					
						></apptags:input>
					
					
				</div>
				
			<div class="form-group">
				<%-- <apptags:input labelCode="Received Amount"
					cssClass = "hasDecimal text-right"
						path="accountGrantMasterDto.receivedAmt" isMandatory="true"
						isDisabled="${command.saveMode eq 'V' ? true : false }"
						onBlur="return hasAmount(event, this, 12, 2)"
						></apptags:input> --%>
							
						<label for = "text-1" class = "control-label col-sm-2 required-control"><spring:message
									code="accounts.receipt.received.amt" text="Received Amount" /></label>	
					<div class = "col-sm-4">
						<form:input 
						cssClass = "form-control text-right"
						path="accountGrantMasterDto.receivedAmt"
						 disabled ="${command.saveMode eq 'V'? true: false}"
						onkeypress="return hasAmount(event, this, 10, 2)" 
						id ="receivedAmt"
						/>
					</div>	
				 		
					<label for="text-1" class="control-label col-sm-2 required-control"><spring:message
								code="account.common.fund" text="Fund " /></label>
					<div class="col-sm-4">  
						<form:select
							class="form-control mandColorClass  chosen-select-no-results"
							path="accountGrantMasterDto.fundId" id="fundId"
							placeholder="Enter fund"
							disabled="${command.saveMode eq 'V' ? true : false }">
							<form:option value="" selected="true">
								<spring:message code="account.common.select" text="Select" />
							</form:option>
							<c:forEach items="${command.fundList}" var="fundData">
								<form:option code="${fundData.fundCode}"
									value="${fundData.fundId}">${fundData.fundCompositecode} ${fundData.fundDesc}</form:option>
							</c:forEach>
						</form:select>
					</div> 
				</div>
				
				<div class="form-group" id="Opening">
				<label for = "text-1" class = "control-label col-sm-2 required-control">Opening Balance Amount</label>	
					<div class = "col-sm-4">
						<form:input 
						cssClass = "form-control text-right"
						path="accountGrantMasterDto.openingBalance"
						 disabled ="${command.saveMode eq 'V'? true: false}"
						onkeypress="return hasAmount(event, this, 10, 2)" 
						id ="openingBalance"
						/>
					</div>	
				</div>
				
				
			
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
								<table class="table table-bordered table-striped grantMaster">
									<thead>
										<tr>
											<th class="text-center"><spring:message code="receipt.register.receiptnumber" text="Receipt Number" /></th>
											<th class="text-center"><spring:message code="accounts.receipt.received_from" text="Received From" /></th>
											<th class="text-center"><spring:message code="receipt.register.receiptdate" text="Receipt Date" /></th>
											<th class="text-center"><spring:message code="accounts.receipt.receiptAmount" text="Receipt Amount" /></th>
											<th class="text-center"><spring:message code="accounts.receipt.narration" text="Narration" /></th>
											<c:choose>
												<c:when test="${command.saveMode eq 'C'}">
													<th class="text-center"><spring:message code="Action.s" text="Action(s)" /></th>
												</c:when>
											</c:choose>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${command.saveMode eq 'V'}">
												<c:forEach items="${command.receiptMasBeanList}" var="data"
													varStatus="index">
													<tr>
														<td class="text-center">${data.rmRcptid}</td>
														<td class="text-center">${data.rmReceivedfrom}</td>
														<td class="text-center"><fmt:formatDate
																pattern="dd/MM/yyyy" value="${data.rmDate}" /></td>
														<td class="text-right">${data.rmAmount}</td>
														<td>${data.rmNarration}</td>
														<c:choose>
															<c:when test="${command.saveMode eq 'C'}">
																<td class="text-center">
																	<button type="button" class="btn btn-blue-2"
																		data-toggle="tooltip" data-original-title="Add">
																		<i class="fa fa-plus" aria-hidden="true"></i>
																	</button>
																	<button type="button" class="btn btn-danger"
																		data-toggle="tooltip" data-original-title="Remove">
																		<i class="fa fa-trash-o" aria-hidden="true"></i>
																	</button>
																</td>
															</c:when>
														</c:choose>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td class="text-center">&nbsp;</td>
													<td class="text-center">&nbsp;</td>
													<td class="text-center">&nbsp;</td>
													<td class="text-center">&nbsp;</td>
													<td></td>
													<c:choose>
														<c:when test="${command.saveMode eq 'C'}">
															<td class="text-center">
																<button type="button" class="btn btn-blue-2"
																	data-toggle="tooltip" data-original-title="Add">
																	<i class="fa fa-plus" aria-hidden="true"></i>
																</button>
																<button type="button" class="btn btn-danger"
																	data-toggle="tooltip" data-original-title="Remove">
																	<i class="fa fa-trash-o" aria-hidden="true"></i>
																</button>
															</td>
														</c:when>
													</c:choose>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-group accordion-toggle" id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="account.contra.voucher.payment.detail" text="Payment Details" />
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse">
							<div class="panel-body">
								<table class="table table-bordered table-striped">
									<thead>
										<tr>
											<th class="text-center"><spring:message code="advance.management.master.paymentnumber" text="Payment Number" /></th>
											<th class="text-center"><spring:message code="advance.management.master.paymentdate" text="Payment Date" /></th>
											<th class="text-center"><spring:message code="accounts.deduction.register.vendorname" text="Vendor Name" /></th>
											<th class="text-center"><spring:message code="accounts.stop.payment.payment.amount" text="Payment Amount" /></th>
											<th class="text-center"><spring:message code="accounts.receipt.narration" text="Narration" /></th>
										</tr>
									</thead>
									<tbody>
									 <c:forEach items="${command.paymentList}" var="paymentDto"
													varStatus="index">
										<tr>
											<td class="text-center">${paymentDto.paymentNo}</td>
											<td class="text-center">${paymentDto.paymentEntryDate}</td>
											<td class="text-center">${paymentDto.vendorName}</td>
											<td class="text-right">${paymentDto.paymentAmount}</td>
											<td>${paymentDto.narration}</td>
										</tr>
										</c:forEach>
									
									<!-- 
										<tr>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr> -->
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a3" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="Refund.Details" text="Refund Details" />
								</a>
							</h4>
						</div>
						<div id="a3" class="panel-collapse collapse">
							<div class="panel-body">
								<table class="table table-bordered table-striped">
									<thead>
										<tr>
											<th class="text-center"><spring:message code="account.paymentEntry.billNumber" text="Bill Number" /></th>
											<th class="text-center"><spring:message code="Bill.Date" text="Bill Date" /></th>
											<th class="text-center"><spring:message code="accounts.vendormaster.vendorName" text="Vendor Name" /></th>
											<th class="text-center"><spring:message code="Bill.Amount" text="Bill Amount" /></th>
											<th class="text-center"><spring:message code="accounts.receipt.narration" text="Narration" /></th>
										</tr>
									</thead>
									<tbody>
									 <c:forEach items="${command.billDtoList}" var="billDto"
													varStatus="index">
										<tr>
											<td>${billDto.billNo}</td>
											<td>${billDto.billDate}</td>
											<td>${billDto.vendorName}</td>
											<td class= "text-right">${billDto.invoiceValue}</td>
											<td>${billDto.narration}</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center margin-top-10">
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success" title="Save"
							onclick="saveGrantMaster(this)">
							<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i><spring:message code="account.configuration.save" text="Save" />
						</button>
						<button type="button" class="btn btn-warning" title="Reset"
							onclick="ResetForm(this)">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i><spring:message code="account.bankmaster.reset" text="Reset"/>
						</button>
					</c:if>
					<button type="button" class="btn btn-danger" data-toggle="tooltip"
						data-original-title="Back" onclick="backGrantMasterForm();">
						<i class="fa fa-chevron-circle-left padding-right-5"
							aria-hidden="true"></i><spring:message code="account.bankmaster.back" text="Back" />
					</button>
				</div>     
			</form:form>
		</div> 
	</div>
</div>