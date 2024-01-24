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
<script src="js/account/accountChequeDishonour.js"></script>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>


<script>
	$(document).ready(function() {
		var val = $('#keyTest').val();
		if (val != '' && val != undefined) {
			displayMessageOnSubmit(val);

		}

		$(".datepicker").datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			maxDate : '0'
		});
		$(".datepicker").datepicker('setDate', new Date());

		$(".datepicker").keyup(function(e) {
			if (e.keyCode != 8) {
				if ($(this).val().length == 2) {
					$(this).val($(this).val() + "/");
				} else if ($(this).val().length == 5) {
					$(this).val($(this).val() + "/");
				}
			}
		});
	});
</script>

<c:if test="${tbAccountChequeDishonour.hasError =='true'}">
	<apptags:breadcrumb></apptags:breadcrumb>
</c:if>

<script>
	$('#sacHeadId${count}').val($('#secondaryId').val());
</script>

<div class="widget" id="widget">
	<div class="widget-header">
		<h2>
			<spring:message code="account.chequeDish.cheDishonour" text="Cheque Dishonour" />
		</h2>
		<apptags:helpDoc url="AccountChequeDishonour.html"
			helpDocRefURL="AccountChequeDishonour.html"></apptags:helpDoc>
	</div>

	<div class="widget-content padding">
		<form:form class="form-horizontal"
			modelAttribute="tbAccountChequeDishonour" method="POST"
			action="AccountChequeDishonour.html">

			<form:hidden path="index" id="index" />
			<form:hidden path="" value="${keyTest}" id="keyTest" />

			<div class="mand-label clearfix">
				<span><spring:message code="account.common.mandmsg" /> <i
					class="text-red-1">*</i> <spring:message
						code="account.common.mandmsg1" /></span>
			</div>
			<form:hidden path="hasError" />
			<form:hidden path="alreadyExists" id="alreadyExists"></form:hidden>
			<form:hidden path="" id="hiddenFinYear"></form:hidden>

			<form:hidden path="chequeDishonourId" id="chequeDishonourId" />
			<form:hidden path="bankAccount" id="bankAccount" />
			<form:hidden path="dishonourIds" id="dishonourIds" />
			<form:hidden path="chequedddate" id="chequedddate" />
			<form:hidden path="chequeddno" />

			<div class="warning-div alert alert-danger alert-dismissible hide"
				id="clientSideErrorDivScrutiny"></div>
			<div class="error-div alert alert-danger alert-dismissible"
				id="errorDivId" style="display: none;">
				<button type="button" class="close" onclick="closeOutErrBox()"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<span id="errorId"></span>
			</div>

			<jsp:include page="/jsp/tiles/validationerror.jsp" />
			<c:set var="count" value="0" scope="page" />
			<ul id="ulId">
				<li>

					<fieldset id="divId" class="clear">


						<form:hidden
							path="chequeDishonourDtoList[${count}].chequeDishonourId"
							id="chequeDishonourId"
							value="${chequeDishonourDtoList[count].chequeDishonourId}" />
						<form:hidden path="chequeDishonourDtoList[${count}].receiptModeId"
							id="receiptModeId"
							value="${chequeDishonourDtoList[count].receiptModeId}" />

					</fieldset> <input type="hidden" value="${viewMode}" id="test" />
                    




					<div id="viewDishonourDetails">

						<div class="panel-group accordion-toggle">

							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse" href="#collapse_two"><spring:message
												code="account.chequeDish.dishonourDetails" text="Dishonour Details" /></a>
									</h4>
								</div>
								<div id="collapse_two" class="panel-collapse collapse in">
									<div class="panel-body">

										<div class="form-group">

											<label class="control-label  col-sm-2 required-control">
												<spring:message code="account.dishonour.dishonourDate" text="Dishonour Date" />
											</label>
											<div class="col-sm-4">
												<div class="input-group">
													<form:input path="dishonourDate"
														cssClass="datepicker mandColorClass form-control"
														name="dishonourDate" id="dishonourDate"
														data-rule-required="true" maxlength="10" />
													<label class="input-group-addon" for="dishonourDate"><i
														class="fa fa-calendar"></i> <input type="hidden"
														id="trasaction-date-icon30"></label>
												</div>
											</div>

											<label class="control-label  col-sm-2 required-control">
												<spring:message code="account.chequeDishonour.dishonourCharges" text="Dishonour Charges" />
											</label>
											<div class="col-sm-4">
												<form:input
													cssClass="form-control mandColorClass text-right amount"
													onkeypress="return hasAmount(event, this, 13, 2)"
													id="dishonourAmount" path="dishonourAmount"
													onchange="getAmountFormatInStatic('dishonourAmount')"
													data-rule-required="true"></form:input>
											</div>

										</div>

										<div class="form-group">

											<label class="control-label  col-sm-2 required-control">
												<spring:message code="account.chequeDish.dishonourRemark" text="Dishonour Remarks" />
											</label>
											<div class="col-sm-4">
												<form:textarea id="remarks" path="remarks"
													class="form-control mandColorClass"
													data-rule-maxLength="200" data-rule-required="true" />
											</div>

										</div>

										<div class="text-center padding-top-10">
											<input type="button" id="saveBtn"
												class="btn btn-success btn-submit"
												onclick="saveLeveledData(this)" value="<spring:message code="accounts.stop.payment.save" text="Save" />">
											<spring:url var="cancelButtonURL"
												value="AccountChequeDishonour.html" />
											<a role="button" class="btn btn-danger"
												href="${cancelButtonURL}"><spring:message
													code="account.bankmaster.back" text="Back" /></a>
										</div>

									</div>
								</div>
							</div>
						</div>
					</div>
				</li>
			</ul>
		</form:form>
	</div>

</div>


