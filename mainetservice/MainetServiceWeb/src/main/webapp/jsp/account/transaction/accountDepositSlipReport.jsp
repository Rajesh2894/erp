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
<script src="js/mainet/validation.js"></script>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/chequeOrCashDeposite.js"></script>

<script>
	function printdiv(printpage)
	{
		var headstr = "<html><head><title></title></head><body>";
		var footstr = "</body>";
		var newstr = document.all.item(printpage).innerHTML;
		var oldstr = document.body.innerHTML;
		document.body.innerHTML = headstr+newstr+footstr;
		window.print();
		document.body.innerHTML = oldstr;
		return false;
	}
</script>


 <div class="widget">
            <div class="widget-header">
                <h2><spring:message code="account.deposit.slip"
								text="Bank Deposit Slip" />
                </h2>
            </div>
            <div class="widget-content padding">
                <form:form  modelAttribute = "accountChequeOrCashDepositDTO" action="AccountDepositSlip.html" method="POST" class="form-horizontal">
                    <div id="receipt">
                    	<div class="receipt-content">
                        <div class="row">
                        <div class="col-xs-12 text-center">
                            <h3 class="text-extra-large  margin-bottom-0 margin-top-0">
                            <c:if test="${userSession.languageId eq 1}">
                                     ${ userSession.getCurrent().organisation.ONlsOrgname}<br>
							</c:if>
							<c:if test="${userSession.languageId eq 2}">
									 ${ userSession.getCurrent().organisation.ONlsOrgnameMar}<br>
							</c:if> 
							</h3>
                            </div>
                            <div class="col-sm-12">
                                <p class="text-center "><spring:message code="account.deposit.slip"
								text="Bank Deposit Slip" /></p>
                            </div>
                          
                            
                            <div class="clearfix"></div>
                            <div class="col-sm-8">
                                <h5 class="padding-top-9"><strong><spring:message code="account.deposit.holder.name"
								text="Name of Account Holder " /> </strong>${accountChequeOrCashDepositDTO.organisationName}--${accountChequeOrCashDepositDTO.bankAccountName}</h5>
                            </div>
                              <div class="col-sm-4 text-right">
                               <h5><strong><spring:message code="account.deposit.dates"
								text="Date:" /></strong>${accountChequeOrCashDepositDTO.remittanceDate}</h5>
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-sm-3">
                                <h5 ><strong><spring:message code="account.deposit.type"
								text="Bank Account Type  " /></strong>${accountChequeOrCashDepositDTO.bankAccountType}</h5>
                            </div>
                            
                            
                            <div class="col-sm-4 ">
                              
                                    <h5><strong><spring:message code="account.deposit.account"
								text="Account No." /></strong> ${accountChequeOrCashDepositDTO.bankaccountNo}</h5>
                                
                                
                            </div>
                            <div class="clearfix"></div>
                            <div class="col-sm-12">
                                <h5 ><strong><spring:message code="account.deposit.account.holder"
								text="Account Holder Branch" /></strong> ${accountChequeOrCashDepositDTO.bank}-${accountChequeOrCashDepositDTO.branch}</h5>
                            </div>
                            
                            <div class="clearfix"></div>
                           
                            
                            
                        </div>

                        <table class="table table-bordered table-condensed">
                          
                                <tr>
                                    <th width="180"><spring:message code="account.deposit.bank"
								text="BANK" /></th>
                                    <th width="154"><spring:message code="account.deposit.branch"
								text="BRANCH" /></th>
                                     <th width="10%"><spring:message code="account.deposit.chequeno"
								text="CHEQUE NO." /></th>
                                    <th width="10%"><spring:message code="account.deposit.deno"
								text="DENO" /> </th>
                                    <th width="8%"><spring:message code="account.deposit.count"
								text="COUNT" /> </th>
                                    <th width="20"><spring:message code="account.deposit.rs"
								text="Rs." /></th>
                                     <th width="20"><spring:message code="account.deposit.pices"
								text="PAISA" /> </th> 
                                     </tr>  
                                     <c:forEach items="${accountChequeOrCashDepositDTO.denomination}"
											varStatus="status" var="denLookupVar">                           
									<tr>
									<td align="left">${denLookupVar.depositBankName}</td>
									<td align="left">${denLookupVar.branch}</td>
									<td align="right">${denLookupVar.payOrderNo}</td>
									<td align="right">${denLookupVar.rupesTpye}</td>
                                    <td align="right">${denLookupVar.numberOfRupes}</td>
                                    <td align="right">${denLookupVar.indainCurrencyAmount}</td>
                                    <td align="right">${denLookupVar.indianCurrencyDecimalAmount}</td>
                                    </tr>
                                   </c:forEach>
                                <tr>
                                    <td colspan="3"><strong><spring:message code="account.deposit.rupeesinwords"
								text="Rupees In Words" /> </strong>${accountChequeOrCashDepositDTO.totalAmountInWords}</td>
                                    
                                   
                                   <th><strong>TOTAL</strong></th>
                                    <th style="text-align: right;">${accountChequeOrCashDepositDTO.picesCount}</th>
                                    <th style="text-align: right;">${accountChequeOrCashDepositDTO.indainCurrencyAmount}</th>
                                    <th style="text-align: right;">${accountChequeOrCashDepositDTO.indianCurrencyDecimalAmount}</th>

                                </tr>
                               
                                <tr>
                                    <th class="text-center" colspan="7"><strong><spring:message code="account.deposit.office"
								text="FOR OFFICE USE" /> </strong></th>
                                    
                                </tr>
                                <tr>
                                    <th height="40"><spring:message code="account.deposit.slipNumber"
								text="SLIP NUMBER" /></th>
                                    <td>${accountChequeOrCashDepositDTO.slipNumber}</td>
                                    <th><spring:message code="account.deposit.account.ddeposit.verifyingofficer"
								text="VERIFYING OFFICER" /> </th>
                                    <td></td>
                                    <th><strong><spring:message code="account.deposit.signature"
								text="SINNATURE OF DEPOSITER" /> </strong></th>
                                    <td colspan="2"></td>
                                </tr>
                           
                        </table>
                        <div class="text-center hidden-print padding-10">
                            <button onclick="printdiv('receipt');" class="btn btn-primary hidden-print"><i class="fa fa-print"></i><spring:message code="account.budgetestimationpreparation.print" text="Print" /> </button>
                            <input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountDepositSlip.html'"
							value="<spring:message code="account.bankmaster.back" text="Back" />" id="cancelEdit" />
                        </div>
                        </div>
                        <style>
							@media print {
								.receipt-content {
									padding: 15px;
								}
							}
						</style>
                    </div>
                </form:form>
            </div>
        </div>
  