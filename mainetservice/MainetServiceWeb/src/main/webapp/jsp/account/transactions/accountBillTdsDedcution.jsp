<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script>

 	$(function() {
		$('.deductionClass').each(function(i) {
			var budgetCodeId = $('option:selected', $("#dedPacHeadId"+i)).attr('value');
		    $('#dedPacHeadId'+i).val(budgetCodeId).chosen().trigger('chosen:updated'); 
		    reOrderDedTableIdSequence();
		});
	});
 	
 // to generate dynamic table
 	$("#deductionDetTable").on("click",'.addDedButton',function(e) {

 						var errorList = [];
 						$('.dedDetailTableClass').each(function(i) {
 							var pacHeadId = $.trim($("#dedPacHeadId" + i).val());
 							if (pacHeadId == 0 || pacHeadId == "")
 								errorList.push(getLocalMessage('Please select Account Heads for Deductions'));

 						 	var deductionRate = $.trim($("#deductionRate" + i).val());
 								if (deductionRate == "")
 									errorList.push(getLocalMessage('Please enter the deduction rate'));
 						});
 						if (errorList.length > 0) {

 							var errMsg = '<ul>';
 							$.each(errorList,function(index) {
 												errMsg += '<li> <i class="fa fa-exclamation-circle"></i>&nbsp;'
 														+ errorList[index]
 														+ '</li>';
 											});

 							errMsg += '</ul>';

 							$('#errorId').html(errMsg);
 							$('#errorDivId').show();
 							$('html,body').animate({
 								scrollTop : 0
 							}, 'slow');
 							return false;
 						}

 						var content = $(this).closest('#dedDetailTable tr').clone();
 						$(this).closest("#dedDetailTable").append(content);
 						content.find("select").attr("value", "");
 						content.find("input:text").val("");
 						content.find('div.chosen-container').remove();
 						content.find("select:eq(0)").chosen().trigger("chosen:updated");
 						reOrderDedTableIdSequence();
 						e.preventDefault();

 					});
 	
 	function reOrderDedTableIdSequence() {

 		$('.deductionClass').each(function(i) {
 						
 							$(this).find("select:eq(0)").attr("id",	"dedPacHeadId" + i);
 							$(this).find("input:text:eq(1)").attr("id",	"deductionRate" + i);
 							$(this).find("input:text:eq(2)").attr("id",	"deductionAmt" + i);
 							$(this).find("input:text:eq(1)").attr("onblur",	"calculatePercentage(" + i + ")");
 						
 							$(this).find("select:eq(0)").attr("name",	"deductionDetailList[" + i + "].budgetCodeId");
 							$(this).find("input:text:eq(1)").attr("name","deductionDetailList[" + i + "].deductionRate");
 							$(this).find("input:text:eq(2)").attr("name","deductionDetailList[" + i + "].deductionAmount");

 							$(this).find(".delDedButton").attr("id","delDedButton" + i);
 							$(this).find(".addDedButton").attr("id","addDedButton" + i);

 							$(this).closest("tr").attr("id", "deduction0" + (i));
 						});
 	}
 	
 // to delete row
 	$("#deductionDetTable").on("click", '.delDedButton', function(e) {
 		var rowCount = $('#deductionDetTable tr').length;
 		if (rowCount <= 2) {
 			return false;
 		}
 		$(this).closest('#deductionDetTable tr').remove();

 		var dedAmountTotal = 0;
 		var rowCount = $('#dedDetailTable tr').length;
 		for (var i = 0; i < rowCount - 1; i++) {
 			dedAmountTotal += parseInt($("#deductionAmt" + i).val());
 			if (isNaN(dedAmountTotal)) {
 				dedAmountTotal = "";
 			}
 		}
 		$("#deductionsTot").val(dedAmountTotal);
 		getAmountFormat('#deductionsTot');
 		getNetPayable();
 		reOrderTableIdSequence();
 		e.preventDefault();
 	});
 	

</script>
<form:form modelAttribute="accountBillEntryBean" id="tdsDeductionForm">

	<form:hidden value="${tdsFlag}" path="" id="tdsFlag" />
	<form:hidden value="${totalDeductions}" path=""
		id="totalDeductionsHidden" />
	<form:hidden value="${netPayableAmount}" path=""
		id="netPayableAmountHidden" />
	<div class="table-responsive" id="deductionDetTable"
		style="overflow: visible;">
		<table id="dedDetailTable" class="table table-bordered table-striped dedDetailTableClass">
			<tbody>
				<tr>
					<th scope="col" width="55%" id="thHead"><spring:message
							code="account.budget.code.master.accountcode"
							text="Account Heads" /></th>
					<th scope="col" width="10%" id="thRate"><spring:message
							code="account.bill.rate" text="Rate(%)" /></th>
					<th scope="col" width="25%" id="thDedAmount"><spring:message
							code="bill.amount" text="Amount" /></th>
					<c:if test="${!viewMode}">
						<th scope="col" width="10%"><spring:message
								code="bill.action" text="Action" /></th>
					</c:if>
				</tr>
				<c:choose>
					<c:when test="${empty accountBillEntryBean.taxDescriptionList}">
						<c:if test="${mode eq 'create'}">
							<tr class="deductionClass" id="deduction">
								<td><form:select id="dedPacHeadId" aria-labelledby="thHead"
										path="" class="form-control chosen-select-no-results"
										disabled="${viewMode}" onchange="">
										<form:option value="">
											<spring:message code="account.common.select" />
										</form:option>
										<c:forEach items="${accountBillEntryBean.taxDescriptionList}"
											varStatus="status" var="objArr">
											<form:option value="${objArr[1]}" code="${objArr[1]}">${objArr[0]}</form:option>
										</c:forEach>
									</form:select></td>
								<td><form:input path="" aria-labelledby="thRate"
										class="form-control"
										onkeypress="return hasAmount(event, this, 13, 2)" /></td>
								<td><form:input path="" aria-labelledby="thDedAmount"
										class="form-control"
										onkeypress="return hasAmount(event, this, 13, 2)" /></td>
								<td><a data-placement="top" title="Add"
									class="btn btn-success btn-sm addDedButton1" id="addDedButton"><i
										class="fa fa-plus-circle"></i></a> <a data-placement="top"
									title="Delete" class="btn btn-danger btn-sm delDedButton"
									id="delDedButton"><i class="fa fa-trash-o"></i></a></td>
							</tr>
						</c:if>
						<c:if test="${mode eq 'update'}">
							<c:forEach items="${deductionDetailList}" var="dedDetList"
								varStatus="status">
								<c:set value="${status.index}" var="count"></c:set>
								<c:set value="${dedDetList.budgetCodeId}" var="budgetCodeIdDed" />
								<form:hidden path="deductionDetailList[${count}].id"
									value='${accountBillEntryBean.deductionDetailList[count].id}' />
								<tr class="deductionClass" id="deduction0">
									<td></td>
									<td><form:input id="deductionRate${count}"
											path="deductionDetailList[${count}].deductionRate"
											onblur="calculatePercentage(${count})" disabled="${viewMode}"
											class="form-control"
											onkeypress="return hasAmount(event, this, 13, 2)" /></td>
									<td><form:input id="deductionAmt${count}"
											path="deductionDetailList[${count}].deductionAmountStr"
											readonly="true" class="form-control text-right"
											onkeypress="return hasAmount(event, this, 13, 2)" /></td>
									<c:if test="${!viewMode}">
										<td><a data-placement="top" title="Add"
											class="btn btn-success btn-sm addDedButton"
											id="addDedButton${count}"><i class="fa fa-plus-circle"></i></a>
											<a data-placement="top" title="Delete"
											class="btn btn-danger btn-sm delDedButton"
											id="delDedButton${count}"><i class="fa fa-trash-o"></i></a></td>
									</c:if>
								</tr>
							</c:forEach>
						</c:if>
					</c:when>

					<c:otherwise>
						<c:forEach items="${accountBillEntryBean.taxDescriptionList}"
							var="tax" varStatus="status">
							<c:set value="${status.index}" var="count"></c:set>
							<tr class="deductionClass" id="deduction0">
								<td><form:select id="dedPacHeadId${count}"
										aria-labelledby="thHead"
										path="deductionDetailList[${count}].budgetCodeId"
										class="form-control chosen-select-no-results"
										disabled="${viewMode}" onchange="validateDedAccountHead()">
										<form:option value="">
											<spring:message code="account.common.select" />
										</form:option>
										<c:forEach items="${accountBillEntryBean.taxDescriptionList}"
											varStatus="status" var="objArr">
											<c:choose>
												<c:when test="${tax[1] eq objArr[1]}">
													<form:option value="${objArr[1]}" code="${objArr[1]}"
														selected="selected">${objArr[0]}</form:option>
												</c:when>
												<c:otherwise>
													<form:option value="${objArr[1]}" code="${objArr[1]}">${objArr[0]}</form:option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</form:select></td>
								<td><c:forEach items="${rateList}" varStatus="statusRate"
										var="rate">
										<c:choose>
											<c:when test="${tax[1] eq rate[0]}">
												<form:input id="deductionRate${count}"
													path="deductionDetailList[${count}].deductionRate"
													value="${rate[1]}" class="form-control"
													onblur="calculatePercentage(${count})"
													onkeypress="return hasAmount(event, this, 13, 2)" />
											</c:when>
										</c:choose>
									</c:forEach></td>
								<td><c:forEach items="${amountList}" varStatus="amountRate"
										var="amount">
										<c:choose>
											<c:when test="${tax[1] eq amount[0]}">
												<form:input id="deductionAmt${count}"
													path="deductionDetailList[${count}].deductionAmount"
													value="${amount[1]}" class="form-control"
													onkeypress="return hasAmount(event, this, 13, 2)" />
											</c:when>
										</c:choose>
									</c:forEach></td>
								<td><a data-placement="top" title="Add"
									class="btn btn-success btn-sm addDedButton"
									id="addDedButton${count}"><i class="fa fa-plus-circle"></i></a>
									<a data-placement="top" title="Delete"
									class="btn btn-danger btn-sm delDedButton"
									id="delDedButton${count}"><i class="fa fa-trash-o"></i></a></td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</form:form>