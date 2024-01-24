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
<script src="js/mainet/validation.js"></script>
<script src="assets/libs/bootstrap-inputmask/inputmask.js"></script>
<script src="js/mainet/file-upload.js"></script>
<script src="js/challan/offlinePay.js"></script>
<script src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/rts/rtsChecklist.js"></script>



<div id="validationDiv">
	<!-- Start Content here -->
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<b><spring:message code="DrainageConnectionDTO.apppDrainageConn"></spring:message></b>
				</h2>
				<div class="additional-btn">
					<apptags:helpDoc url="rtsService.html"></apptags:helpDoc>
				</div>
			</div>

			<div class="widget-content padding">
				<form:form method="POST" action="drainageConnection.html"
					class="form-horizontal" id="rtiForm" name="rtiForm">

					<div class="compalint-error-div">
						<jsp:include page="/jsp/tiles/validationerror.jsp" />
						<div
							class="warning-div error-div alert alert-danger alert-dismissible"
							id="errorDiv" style="display: none;"></div>
					</div>
					<form:hidden path="applicationchargeApplFlag"
						id="applicationchargeApplFlag" />
					<div id="payandCheckIdDiv">

						<c:if test="${command.checkListApplFlag == 'Y'}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse"
											href="#Upload_Attachment" tabindex="-1"> <spring:message
												code="rti.uploadAttach" text="Upload Attachment"></spring:message></a>
									</h4>
								</div>
								<div id="Upload_Attachment" class="panel-collapse collapse in">
									<div class="panel-body">

										<div class="overflow margin-top-10">
											<div class="table-responsive">
												<table
													class="table table-hover table-bordered table-striped"
													id="DrainageTable">
													<tbody>
														<tr>
															<th><spring:message code="rti.serialNo" /></th>
															<th><spring:message code="rti.documentName" /></th>
															<th><spring:message code="rti.rtiStatus1" /></th>
															<th><spring:message code="rti.uploadDoc" /></th>
														</tr>
														<c:forEach items="${command.checkList}" var="lookUp"
															varStatus="lk">
															<tr>
																<td>${lookUp.documentSerialNo}</td>
																<c:choose>
																	<c:when
																		test="${userSession.getCurrent().getLanguageId() eq 1}">
																		<td>${lookUp.doc_DESC_ENGL}</td>
																	</c:when>
																	<c:otherwise>
																		<td>${lookUp.doc_DESC_Mar}</td>
																	</c:otherwise>
																</c:choose>
																<c:if test="${lookUp.checkkMANDATORY eq 'Y'}">
																	<td><spring:message code="water.doc.mand" /></td>
																</c:if>
																<c:if test="${lookUp.checkkMANDATORY eq 'N'}">
																	<td><spring:message code="water.doc.opt" /></td>
																</c:if>
																<td>
																	<div id="docs_${lk}" class="">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true" fieldPath="checkList[${lk.index}]"
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="BND_COMMOM_MAX_SIZE"
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="PDF_UPLOAD_EXTENSION"
																			currentCount="${lk.index}" />
																	</div>
																	<small class="text-blue-2"> <spring:message code="rts.checklist.validation"
																		text="(Upload Image File upto 5 MB)" />
															    </small>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:if>


					</div>
					<c:if test="${command.checkListApplFlag != 'Y'}">
						<!------------------------------------------------------------  -->
						<!--  AttachDocuments Form starts here -->
						<!------------------------------------------------------------  -->
						<div class="panel panel-default" id="accordion_single_collapse">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a19" tabindex="-1"> <spring:message
										code="" text="Attached Documents" /></a>
							</h4>
							<div id="a19" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-sm-12 text-left">
											<div class="table-responsive">
												<table class="table table-bordered table-striped"
													id="attachDocs">
													<tr>
														<th><spring:message code="rts.document.name" text="" /></th>
														<th><spring:message code="rts.view.document" text="" /></th>
													</tr>
													<c:forEach items="${command.checkList}" var="lookUp">
														<tr>
															<%-- <td align="center">${lookUp.documentName}</td> --%>
															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<td align="center"><label>${lookUp.doc_DESC_ENGL}</label></td>
																</c:when>
																<c:otherwise>
																	<td align="center"><label>${lookUp.doc_DESC_Mar}</label></td>
																</c:otherwise>
															</c:choose>
															<td align="center"><apptags:filedownload
																	filename="${lookUp.documentName}"
																	filePath="${lookUp.uploadedDocumentPath}"
																	actionUrl="rtsService.html?Download">
																</apptags:filedownload></td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</div>
									</div>
									<div class="form-group"></div>
								</div>
							</div>
						</div>

					</c:if>
					<!------------------------------------------------------------  -->
					<!--   AttachDocuments Form  ends here -->
					<!------------------------------------------------------------  -->


					<div id="paymentDetails">
						<c:if test="${command.applicationchargeApplFlag == 'Y'}">
							<jsp:include page="/jsp/payment/onlineOfflinePay.jsp" />
							<div class="form-group margin-top-10">
								<label class="col-sm-2 control-label"> <spring:message
										code="rti.amtpay" /></label>
								<div class="col-sm-4">
									<input type="text" class="form-control"
										value="${command.offlineDTO.amountToShow}" maxlength="12" id="AmountToShow"
										readonly="readonly" /> <a
										class="fancybox fancybox.ajax text-small text-info"
										href="rtsService.html?showChargeDetails"
										> <spring:message code="rti.amtpay" /> <i
										class="fa fa-question-circle "></i></a>
								</div>
							</div>
						</c:if>
					</div>

					<div class="padding-top-10 text-center">
						<c:if test="${command.saveMode eq 'V' ? false : true }">
							<button type="button" class="btn btn-success" id="save"
								onclick="saveRtsForm(rtiForm);">
								<spring:message code="rts.submit" />
							</button>
						</c:if>



						<button type="button" class="btn btn-danger" id="bck"
							onclick="previousPage()">
							<spring:message code="trade.back"></spring:message>
						</button>
					</div>
				</form:form>
			</div>

		</div>
	</div>
</div>


<script>
	$(document).ready(function() {
		$('.fancybox').fancybox();
	});
</script>





