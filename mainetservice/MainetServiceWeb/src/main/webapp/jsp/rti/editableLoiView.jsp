<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/rti/rtiPioResponse.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>


<style>
textarea.form-control {
	resize: vertical !important;
	height: 2.3em;
}
</style>

<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<b><spring:message code="rti.edit.loi" text="Ediatble LOI"></spring:message></b>
			</h2>

			<apptags:helpDoc url="RtiApplicationDetailForm.html"></apptags:helpDoc>
			<!-- <a href="RtiApplicationDetailForm.html?ShowHelpDoc" target="_new" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"><span class="hide">Help</span></i></a> -->
		</div>

		<div class="widget-content padding">

			<form:form method="POST" action="PioResponse.html"
				commandName="command" class="form-horizontal" id="editable"
				name="editable">
				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
				</div>



				<div class="form-group">
					<tr>
						<label class="col-sm-2 control-label"><spring:message
								code="rti.applicationNO" /></label>


						<div class="col-sm-4">
							<input type="text" class="form-control text-right"
								value="${command.reqDTO.apmApplicationId}" readonly="readonly" />
						</div>
					</tr>


					<div>

						<label class="col-sm-2 control-label"><spring:message
								code="rti.total.amt" /></label>

						<div class="col-sm-4">
							<td><input type="text" class="form-control text-right"
								value="${command.totalBeforeSave}" readonly="readonly" /></td>
						</div>

					</div>
				</div>



				<div class="col-sm-10 col-sm-offset-1">

					<c:set var="d" value="0" scope="page"></c:set>
					<table
						class="table table-bordered  table-condensed margin-bottom-20 margin-top-20"
						id="itemDetails">
						<thead>
							<tr>
								<th><spring:message code="rti.chargemediaType"></spring:message></th>
								<th><spring:message code="rti.quantity" text="Quantity"></spring:message></th>
								<th><spring:message code="rti.amount" text="Amount"></spring:message></th>
								<th><spring:message code="rti.edit" text="Edit"></spring:message></th>
								<th><spring:message code="rti.remarks" text="Remarks"></spring:message></th>


							</tr>
						</thead>

						<tbody>



							<c:forEach items="${command.chargeAmountList}"
								var="rtiMediaListDTO" varStatus="status">
								<tr>


									<%-- <td>${status.count}</td> --%>
									<c:choose>
										<c:when
											test="${userSession.getCurrent().getLanguageId() eq 1}">
											<td>${rtiMediaListDTO.mediaType}</td>
										</c:when>
										<c:otherwise>
										<td>${rtiMediaListDTO.mediatypeMar}</td>
										</c:otherwise>
									</c:choose>
									<td class="text-right">${rtiMediaListDTO.quantity}</td>
									<td class="text-right">${rtiMediaListDTO.chargeAmount}</td>


									<td><form:input type="text"
											class="form-control text-right hasNumber"
											path="chargeAmountList[${status.count-1}].editedAmount"
											value="${rtiMediaListDTO.chargeAmount}" maxlength="10"
											readonly="${rtiMediaListDTO.editableLoiFlag eq 'N' ? false : true }" /></td>



									<td><form:input type="text" class="form-control"
											path="chargeAmountList[${status.count-1}].remarks"
											maxlength="100"
											readonly="${rtiMediaListDTO.editableLoiFlag eq 'N' ? false : true }" /></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
				<div class="clear"></div>
				<div class="text-center hidden-print padding-top-20">


					<input type="button" class="btn  btn-success" id="submit"
						name="submit" value="<spring:message code="rti.proceed"/>"
						onclick="editableToLetter(this);" />


				</div>

			</form:form>
		</div>
	</div>
</div>





