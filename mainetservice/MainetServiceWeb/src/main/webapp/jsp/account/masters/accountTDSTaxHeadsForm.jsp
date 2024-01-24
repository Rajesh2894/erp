<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<script src="js/account/accountTDSTaxHeads.js"></script>
<script src="js/mainet/validation.js"></script>


<script>
$(document).ready(function() {	
	
	$("#budgetCode0").chosen();
		
	var updateModeId = $("#updateModeId").val();
	if(updateModeId == 'update'){
		 $('.tdsClass').each(function(i) {
			$('#status'+i).prop( "disabled", false );

			var statusValue = $("status"+i+" option:selected").text();
			$('#addButtonId'+i).prop( "disabled", true );
			$('#delButtonId'+i).prop( "disabled", true );
			
			if(statusValue == 'Inactive' ){
				$('#addButtonId'+i).prop( "disabled", false );
				$('#delButtonId'+i).prop( "disabled", false );
				}
		});
	}

});
</script>


<c:if test="${tdsTaxHeadsMasterBean.hasError eq 'false'}">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div id="content">
</c:if>


<c:url value="${saveAction}" var="url_form_submit" />
<c:url value="${mode}" var="form_mode" />


<div class="form-div">

	<div class="widget" id="widget">

		<div class="widget-header">
			<h2>
				<spring:message code="tax.heads.master.widget.header" text="" />
			</h2>
		<apptags:helpDoc url="AccountTDSTaxHeadsMaster.html" helpDocRefURL="AccountTDSTaxHeadsMaster.html"></apptags:helpDoc>		
		</div>

		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="account.common.mandmsg" /> <i
					class="text-red-1">*</i> <spring:message
						code="account.common.mandmsg1" /></span>
			</div>

			<form:form method="POST" action="AccountTDSTaxHeadsMaster.html"
				name="taxHeadsMaster" id="taxHeadsMaster" class="form-horizontal"
				modelAttribute="tdsTaxHeadsMasterBean">

				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
				</div>
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<form:hidden path="successfulFlag" id="successfulFlag" />
				<div class="form-group">
					<label for="cpdIdDeductionType"
						class="col-sm-2 control-label required-control"><spring:message
							code="" text="TDS Type" /></label>
					<div class="col-sm-4">
						<form:select id="cpdIdDeductionType" path="cpdIdDeductionType"
							cssClass="form-control" disabled="${viewMode}"
							data-rule-required="true">
							<form:option value="">
								<spring:message code="account.common.select" />
							</form:option>
							<c:forEach items="${listOfTbComparamDetItems}" varStatus="status"
								var="taxItem">
								<form:option value="${taxItem.lookUpId}" code="">${taxItem.descLangFirst}</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>

				<c:if test="${viewMode =='false'}">
					<form:hidden value="update" id="updateModeId" path="" />
				</c:if>
				<div class="table-overflow-sm" id="taxHeadsTable">
					<table id="tdsTaxHeadsTable"
						class="table table-bordered table-striped appendableClass ">
						<tbody>
							<tr>

								<th scope="col" width="300"><spring:message code=""
										text="Budget Head" /><span class="mand float-right">*</span></th>

								<th scope="col" width="200"><spring:message code=""
										text="Status" /><span class="mand float-right">*</span></th>


								<c:if test="${viewMode==false}">
									<th scope="col" width="100"><spring:message
											code="account.common.add.remove" /></th>
								</c:if>
							</tr>
							<c:forEach items="${list}" var="taxHeadsLevel"
								varStatus="statusCount">
								<c:set value="${statusCount.index}" var="count"></c:set>
								<form:hidden path="taxHeadsDtoList[${count}].tdsId" />
								<tr class="tdsClass">
									<td><form:select id="budgetCode${count}"
											path="taxHeadsDtoList[${count}].budgetCodeId"
											class="form-control mandColorClass chosen-select-no-results"
											disabled="${viewMode}" data-rule-required="true">
											<form:option value="">
												<spring:message code="account.common.select" />
											</form:option>
											<c:forEach items="${headCodeMap}" varStatus="status"
												var="budgetCode">
												<form:option value="${budgetCode.key}"
													code="${budgetCode.key}">${budgetCode.value}</form:option>
											</c:forEach>
										</form:select></td>


									<td><form:hidden id="statusHidden${count}"
											path="taxHeadsDtoList[${count}].status" /> <form:select
											path="taxHeadsDtoList[${count}].status" class="form-control"
											onchange="setHiddenStatusField(${count})" id="status${count}"
											disabled="true" data-rule-required="true">
											<option value="">Select</option>
											<c:forEach items="${status}" var="lookUp">
												<c:choose>
													<c:when test="${activeID eq lookUp.lookUpId}">
														<form:option value="${lookUp.lookUpId}"
															selected="selected">${lookUp.descLangFirst}</form:option>
													</c:when>
													<c:otherwise>
														<form:option value="${lookUp.lookUpId}"
															label="${lookUp.descLangFirst}"></form:option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</form:select></td>


									<c:if test="${viewMode == false}">
										<td>
											<button data-toggle="" data-placement="top" title="Add"
												class="btn btn-success btn-sm addButton"
												id="addButtonId${count}">
												<i class="fa fa-plus-circle"></i>
											</button>
											<button data-toggle="" data-placement="top" title="Delete"
												class="btn btn-danger btn-sm delButton"
												id="delButtonId${count}">
												<i class="fa fa-trash-o"></i>
											</button>
										</td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>

				<div class="text-center padding-10">
					<c:if test="${!viewMode}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveTaxHeadForm(this)" id="btnSave">
							<spring:message code="account.bankmaster.save" text="Save" />
						</button>
						<button type="Reset" class="btn btn-warning" id="btnReset">
							<spring:message code="account.bankmaster.reset" text="Reset" />
						</button>
					</c:if>
					<input type="button" class="btn btn-danger"
							onclick="window.location.href='AccountTDSTaxHeadsMaster.html'"
							value="<spring:message code="" text="Back"/>" id="cancelEdit" />
					
				</div>
			</form:form>
		</div>
	</div>
</div>

<c:if test="${tdsTaxHeadsMasterBean.hasError eq 'false'}">
	</div>
</c:if>




