<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/common/pgBankParameterDetail.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code=""
					text="PG Bank Param Details Form" />
			</h2>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>

			<form:form action="PgBankParameterDetail.html" name="PgBankParameterDetail"
				id="PgBankParameterDetail" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>
					
					<div class="form-group">
								<apptags:input labelCode="PG Name"
									path="pgBankMasterDto.pgName"
									cssClass="" isMandatory="true"></apptags:input>
								<apptags:input labelCode="MerchantId"
									path="pgBankMasterDto.merchantId" maxlegnth="50"
									isMandatory="true" cssClass=""></apptags:input>
							</div>
							
							<div class="form-group">
								<apptags:input labelCode="PG URL"
									path="pgBankMasterDto.pgUrl"
									cssClass="" isMandatory="true"></apptags:input>
									
									<apptags:input labelCode="Bank Id"
									path="pgBankMasterDto.bankid"
									cssClass="hasNumber" isMandatory="true"></apptags:input>
							</div>
							
							
							<div class="table-responsive">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-condensed table-bordered"
							id="pgParamDetailTable">

							<thead>

								<tr>
									<th>S.No</th>
									<th><spring:message code="" text="Param Name" /></th>
									<th><spring:message code="" text="Value" /><i
										class="text-red-1">*</i></th>
										<th width="50"><a href="#" data-toggle="tooltip"
											data-placement="top" class="btn btn-blue-2  btn-sm"
											data-original-title="Add" onclick="addPgDetails();"><strong
												class="fa fa-plus"></strong><span class="hide"></span></a></th>
								</tr>
							</thead>


							<tbody>
								<c:set var="e" value="0" scope="page" />
								<c:forEach items="${command.pgBankDetailDtoList}"
									var="pgBankDetailDtoList" varStatus="count">

									<tr>
										<td align="center"><form:input path=""
												cssClass="form-control mandColorClass" id="sequence${e}"
												value="${e+1}" disabled="true" /></td>

										<td><form:input path="pgBankDetailDtoList[${e}].parName"
												id="parName${e}" cssClass="required-control form-control"
												 /></td>
										<td><form:input path="pgBankDetailDtoList[${e}].parValue"
												id="parValue${e}" cssClass="required-control form-control" /></td>
												
												<td><a href="#" data-toggle="tooltip"
												data-placement="top" class="btn btn-danger btn-sm"
												data-original-title="Delete"
												onclick="deletePgDetailRow($(this),'removePgDetailId');">
													<strong class="fa fa-trash"></strong> <span class="hide"><spring:message
															code="adh.delete" text="Delete" /></span>
											</a></td>

									</tr>
									<c:set var="e" value="${e + 1}" scope="page" />
								</c:forEach>

							</tbody>
						</table>
					</div>
				</div>
				<div class="text-center">
				<button type="button" class="btn btn-success"
							data-toggle="tooltip" data-original-title="Save"
							onclick="save(this)">
							<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
							<spring:message code="" text="Save"></spring:message>
						</button>
						<button type="button" class="btn btn-danger"
								onclick="window.location.href='AdminHome.html'">
								<spring:message code=""  text="Back"/>
							</button>
						</div>
			</form:form>
		</div>
	</div>
</div>