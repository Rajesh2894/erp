<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- End JSP Necessary Tags -->
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/property/previousYearManualReceiptWithBillChange.js"></script>

<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code=""
						text="Previous Year Manual Receipt With Bill Change" /></strong>
			</h2>

			<apptags:helpDoc url="PreviousYearManualReceiptWithBillChange.html"></apptags:helpDoc>

		</div>


		<div class="widget-content padding">


			<div class="mand-label clearfix">
				<span><spring:message code="property.Fieldwith" /><i
					class="text-red-1">* </i> <spring:message
						code="property.ismandatory" /> </span>
			</div>

			<form:form action="SelfAssessmentForm.html"
				class="form-horizontal form" name="frmSelfAssessmentForm"
				id="frmSelfAssessmentForm">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				
				<%-- <c:if test="${command.approvalFlag eq 'Y'}"> --%>

				<div id="" class="panel-collapse collapse in">
					<div class="panel-body">

						<div class="form-group">
							<apptags:input labelCode="propertydetails.PropertyNo."
								path="billPayDto.propNo" cssClass="mandColorClass"
								isReadonly="true"></apptags:input>
							<apptags:input labelCode="propertydetails.oldpropertyno"
								path="billPayDto.oldpropno" cssClass="mandColorClass"
								isReadonly="true"></apptags:input>
						</div>


						<div class="form-group">
							<apptags:input labelCode="ownerdetail.Ownername"
								path="billPayDto.ownerFullName" isDisabled="true"></apptags:input>
							<%-- 				         	<apptags:input labelCode="propertydetails.PropertyNo." path="billPayDto.propNo" isDisabled="true" ></apptags:input> --%>
							<apptags:input labelCode="ownersdetail.mobileno"
								path="billPayDto.mobileNo" cssClass="mandColorClass hasNumber"
								maxlegnth="10" isReadonly="true"></apptags:input>
						</div>
						<div class="form-group">
						
						<apptags:date labelCode="Manual Receipt Date"
									datePath="manualReeiptDate" fieldclass="datepicker"
									isMandatory="true"></apptags:date>
							<apptags:input labelCode="Receipt Amount" isMandatory="true"
								path="manualRecptArrearPaidAmnt" cssClass="mandColorClass"></apptags:input>
						</div>
						
						<div class="form-group">
						
							<apptags:input labelCode="Manual Receipt No" isMandatory="true"
								path="assesmentManualNo" cssClass="mandColorClass"></apptags:input>
								
								<apptags:input labelCode="Narration"
								path="offlineDTO.narration" cssClass="mandColorClass"></apptags:input>
						</div>
					</div>
				</div>
				
				<div class="overflow margin-top-10" id="document">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped">
													<tbody>
														<tr>
															<th> <spring:message
																		code="water.serialNo" text="Sr No" />
															</th>
															<th> <spring:message
																		code="water.docName" text="Document Name" />
															</th>
															<th> <spring:message
																		code="water.status" text="Status" />
															</th>
															<th> <spring:message
																		code="water.uploadText" text="Upload" />
															</th>
														</tr>
															<tr>
																<td>1</td>
																<td>Manual Receipt</td>
																	<td> <spring:message
																				code="water.doc.mand" />
																	</td>
																<td><div id="docs_0" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath=""
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																			currentCount="0" />
																	</div>
																	</td>
															</tr>
													</tbody>
												</table>
											</div>
										</div>
<div class="panel panel-default">
							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />
						</div>
				<div class="text-center padding-bottom-10" id="updateButtons">
					<button type="button" class="btn btn-success btn-submit"
						id="proceed12" onclick="updaeReviseBill(this)">
						<spring:message code="" text="Update" />
					</button>

				</div>
				<%-- </c:if> --%>

			</form:form>
		</div>
	</div>
</div>


