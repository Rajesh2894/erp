<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>

<!-- Start Of Terms & Condition Section -->


<div class="panel panel-default">
	<div class="panel-heading">
		<h4 class="panel-title">
			<a data-target="#c" data-toggle="collapse" class="collapsed"
				data-parent="#accordion_single_collapse" href="#c"> <spring:message
					code="work.order.terms.and.conditions" text="Terms & Conditions" /></a>
		</h4>
	</div>
	<form:hidden path="removeTermsById" id="removeTermsById" />
	<div id="c" class="panel-collapse collapse in">
		<div class="panel-body">
			<div class="">
				<c:set var="t" value="0" scope="page"></c:set>
				<table class="table table-bordered table-striped"
					id="termsAndConditionForApproval">
					<thead>
						<tr>
							<th width="5%"><spring:message code="ser.no" text="" /><input
								type="hidden" id="srNo"></th>

							<th scope="col" width="85%" align="center"><spring:message
									code="work.order.terms.and.conditions"
									text="Terms & Conditions" /><span class="mand"></span></th>
							<th class="text-center" width="10%"><spring:message
									code="works.management.action"
									text="Action" /></th>
							
						</tr>
					</thead>
					<c:choose>
						<c:when test="${fn:length(command.termsConditionDtosList) > 0}">
							<c:forEach var="workApprovalList"
								items="${command.termsConditionDtosList}" varStatus="status">
								<tr class="termsApprovalClass">

									<td><form:input path="" id="sNo${t}" value="${t + 1}"
											readonly="true" cssClass="form-control " /> <form:hidden
											path="termsConditionDtosList[${t}].teId" id="termsId${t}" /></td>

									<td><form:textarea
											path="termsConditionDtosList[${t}].termDesc"
											cssClass="form-control control-label required-control"
											id="approvalTermsDesc${t}" /></td>
									<!-- disabled="${command.flagForSendBack eq 'SEND_BACK' } -->

									<td class="text-center"><a href='#'
										onclick='return false;'
										class='btn btn-danger btn-sm deleteApprovalTermsDetails'><i
											class="fa fa-trash"></i></a></td>
								</tr>
								<c:set var="t" value="${t + 1}" scope="page" />
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tbody>
								<tr class="termsApprovalClass">

									<td><form:input path="" id="sNo${t}" value="1"
											readonly="true" cssClass="form-control" /></td>

									<td><form:textarea
											path="termsConditionDtosList[${t}].termDesc"
											cssClass="form-control control-label required-control"
											id="approvalTermsDesc${t}" /></td>

									<td class="text-center">
										<c:if test="${command.saveMode ne 'V' }">
											<button type="button"
												onclick="return false;"
												title="<spring:message code="work.estimate.add" text="Add" />"
												class="btn btn-blue-2 btn-sm  addTermsAndConditionForApproval">
												<i class="fa fa-plus-circle"></i>
											</button>
										</c:if>
										<a href='#'
										onclick='return false;'
										title="<spring:message code="work.estimate.delete" text="Delete" />"
										class='btn btn-danger btn-sm deleteApprovalTermsDetails'><i
											class="fa fa-minus"></i></a></td>
								</tr>
								<c:set var="t" value="${t + 1}" scope="page" />
							</tbody>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
		</div>
	</div>

</div>

<!-- End Of Terms & Condition Section -->