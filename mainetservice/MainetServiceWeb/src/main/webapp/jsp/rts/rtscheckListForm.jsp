<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/rts/rtsCheckListForm.js"></script>
<style>
	#drainageChecklistId #a5 table tr th label{
		font-size: 14px;
	}
</style>
<div class="pagedivCheckList">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="DrainageConnectionDTO.apppDrainageConn" text="Application for Drainage Connection" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"><i
						class="fa fa-question-circle fa-lg"></i><span class="hide">Help</span></a>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form id="drainageChecklistId" commandName="command"
					action="drainageConnection.html" method="POST"
					class="form-horizontal" name="drainageChecklistId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="ajaxResponse"></div>
					<form:hidden path="errorMessage" id="errorMessage" />
					<form:hidden path="applicationchargeApplFlag"
						id="applicationchargeApplFlag" />

					<!---------------------------------------------------------------document upload start------------------------ -->

					<c:if test="${command.checkListApplFlag == 'Y'}">
						<div class="panel-group accordion-toggle"
							id="accordion_single_collapse">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title table" id="">
										<a data-toggle="collapse" class=""
											data-parent="#accordion_single_collapse1" href="#a5"><spring:message code="BirthCertificateDTO.document"
												text="Document Upload Details" /></a>
									</h4>
								</div>
								<div id="a5" class="panel-collapse collapse in">
									<div class="panel-body">
										<div class="table-responsive">
											<table class="table table-hover table-bordered table-striped">
												<tbody>
													<tr>
													<th  width="20%"><label class="tbold"><spring:message
																code="label.checklist.srno" text="Sr No" /></label></th>
													<th  width="25%"><label class="tbold"><spring:message
																code="scheme.document.name" text="Document Required" /></label></th>
													<th  width="20%"><label class="tbold"><spring:message
																code="label.checklist.status" text="Status" /></label></th>
													<th  width="35%"><label class="tbold"><spring:message
																code="label.checklist.upload" text="Upload" /></label></th>
													</tr>
													<c:forEach items="${command.checkList}" var="lookUp"
														varStatus="lk">
														<tr>
															<td>${lookUp.documentSerialNo}</td>
															<c:choose>
																<c:when
																	test="${userSession.getCurrent().getLanguageId() eq 1}">
																	<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
																	<td><label>${lookUp.doc_DESC_ENGL}</label></td>
																</c:when>
																<c:otherwise>
																	<c:set var="docName" value="${lookUp.doc_DESC_ENGL }" />
																	<td><label>${lookUp.doc_DESC_Mar}</label></td>
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
																		isMandatory="true" showFileNameHTMLId="true"
																		fileSize="BND_COMMOM_MAX_SIZE"
																		checkListMandatoryDoc="${lookUp.checkkMANDATORY}"
																		maxFileCount="CHECK_LIST_MAX_COUNT"
																		validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
																		currentCount="${lk.index}" checkListDesc="${docName}" />
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
					<c:if test="${command.checkListApplFlag != 'Y'}">
						<!------------------------------------------------------------  -->
						<!--  AttachDocuments Form starts here -->
						<!------------------------------------------------------------  -->
						<div class="panel panel-default" id="accordion_single_collapse">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#a19"> <spring:message
										code="BirthCertificateDTO.document" text="Attached Documents" /></a>
							</h4>
							<div id="a19" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="form-group">
										<div class="col-sm-12 text-left">
											<div class="table-responsive">
												<table class="table table-bordered table-striped"
													id="attachDocs">
													<tr>
														<th><spring:message code="scheme.document.name"
																text="" /></th>
														<th><spring:message code="scheme.view.document"
																text="" /></th>
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
																	actionUrl="drainageConnection.html?Download">
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
						<!------------------------------------------------------------  -->
						<!--   AttachDocuments Form  ends here -->
						<!------------------------------------------------------------  -->
					</c:if>

					<div id="paymentDetails">
						<c:if test="${command.applicationchargeApplFlag == 'Y'}">

							<jsp:include page="/jsp/cfc/Challan/offlinePay.jsp" />

							<div class="form-group margin-top-10">
								<label class="col-sm-2 control-label"> <spring:message
										code="rti.amtpay" /></label>
								<div class="col-sm-4">
									<input type="text" class="form-control"
										value="${command.offlineDTO.amountToShow}" maxlength="12"
										readonly="readonly" id="AmountToShow" /> <a
										class="fancybox fancybox.ajax text-small text-info"
										href="drainageConnection.html?showChargeDetails"> <spring:message
											code="rti.amtpay" /> <i class="fa fa-question-circle "></i></a>
								</div>
							</div>
						</c:if>
					</div>

					<div class="text-center">
					<c:if test="${command.applicationchargeApplFlag == 'Y' || command.checkListApplFlag == 'Y' }">
							<button type="button" class="btn btn-submit" title="Submit"
								onclick="saveRtsForm(drainageChecklistId)"><spring:message code="rts.save" /></button>

						</c:if>
						<button type="button" class="btn btn-danger"
							data-original-title="Back" onclick="previousPage()">
							<i class="fa fa-chevron-circle-left padding-right-5"
								aria-hidden="true"></i><spring:message code="TbDeathregDTO.form.backbutton" />
						</button>
					</div>
					<!---------------------------------------------------------------document upload end------------------------ -->
				</form:form>
			</div>
		</div>
	</div>
</div>
<!-- <script>
debugger;
var errorMessage = $("#errorMessage").val();
var errorList = []
if(errorMessage != null || errorMessage !="")
	{
		errorList.push(errorMessage)
		displayErrorsOnPage(errorList);
	}

</script> -->