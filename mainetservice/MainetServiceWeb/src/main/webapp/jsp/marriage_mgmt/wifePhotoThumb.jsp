<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/marriage_mgmt/wife.js"></script>
<script type="text/javascript" src="js/marriage_mgmt/mrm-file-upload.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript"
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<style>
.fileUpload.fileinput.fileinput-new, .removePhotoW, .removeThumbW {
	display: inline-block;
	width: auto;
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="checklistVerification.breadcrumb" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span>Field with <i class="text-red-1">*</i> is mandatory
				</span>
			</div>

			<form:form method="post" action="MarriageRegistration.html"
				name="MarriageRegistration" id="frmMaster" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />



				<h4 class="margin-top-0">
					<spring:message text="Applicant Details"
						code="cfc.applicant.detail" />
				</h4>

				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							code="checklistVerification.serviceName" text="Service Name" />
					</label>
					<div class="col-sm-4">
						<span class="form-control height-auto">${command.applicationDetails.applicationService }</span>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							text="Name Of Applicant" code="mrm.marriage.appName" /> </label>
					<div class="col-sm-4">
						<span class="form-control text-hidden">${command.applicationDetails.applicantsName }</span>
					</div>
				</div>


				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							text="Application ID " code="cfc.application" /> </label>
					<div class="col-sm-4">
						<span class="form-control">${command.applicationDetails.apmApplicationId }</span>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							text="Application Date" code="cfc.applIcation.date" /> </label>
					<div class="col-sm-4">
						<c:set value="${command.applicationDetails.apmApplicationDate }"
							var="appDate" />
						<span class="form-control"><fmt:formatDate type="date"
								value="${appDate}" pattern="dd/MM/yyyy" /></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label"><spring:message
							text="Applicant Mobile No " code="master.loi.applicant.mob" /> </label>
					<div class="col-sm-4">
						<span class="form-control">${command.mobNo }</span>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							text="Applicant Email Id" code="master.loi.applicant.email" /> </label>
					<div class="col-sm-4">
						<span class="form-control">${command.email}</span>
					</div>
				</div>
				<form:hidden path="photoThumbDisp" id="photoThumbDispW" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse1">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse1" href="#wifeDoc"><spring:message
										code="mrm.tab.wife.photoThumbDetails" text="wife photo"/></a>
							</h4>
						</div>
						<form:hidden path="photoId" id="photoIdW" />
						<form:hidden path="thumbId" id="thumbIdW" />
						<div id="wifeDoc" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<div class="col-sm-6">
										<div class="thumbnail text-center">

											<div>

												<div id="phoW" class="text-center">
												<h5 class="text-center">
													<spring:message code="mrm.master.photos" text="Photos"></spring:message>
												</h5>
												<!-- showPhoto id is used to display image -->
												<div id="showPhotoW"></div>
													<c:if test="${command.modeType ne 'V'}">
														<!--start camera button  -->
														<div id="startCamBTW" class="text-center caption">
															<button  type="button" class="button-input btn btn-success"
																		name="button" onclick="return startWebCAMW()" >
																		<spring:message code="mrm.photoCap" text="Capture" />
															</button>
														</div>
														<div id="webcamW" class="caption">
															<div class="addPhotoW">
																<video id="playerW" width=150 height=150 autoplay style="margin: 0rem auto;"></video>
																<canvas id="canvasW"></canvas>
																
															    <button type="button" class="button-input btn btn-success"
																	name="button" value="Capture"  id="captureW">
																	<spring:message code="mrm.photoCap" text="Capture" />
																</button>
															    
																<div class="removePhotoW"></div>
															</div>
														</div>
													</c:if>
													
												
												<div id="manualW">
													<div class="caption text-center">
														<c:if test="${command.modeType ne 'V'}">
	
															<div class="addPhotoW">
	
																<apptags:formField fieldType="7" labelCode=""
																	hasId="true"
																	fieldPath="marriageDTO.wifeDTO.capturePhotoPath"
																	isMandatory="false" showFileNameHTMLId="true"
																	fileSize="MAX_FILE_SIZE" 
																	maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																	currentCount="${command.getPhotoId()}"
																	callbackOtherTask="wifeDocumentTask()" />
																<div class="removePhotoW"></div>
															</div>
														</c:if>
													</div>
													<div>
														<small class="text-red"> <spring:message
																code="mrm.image.upload.size"
																text="(Maximum Size should be 50KB - 100KB)" />
														</small>
													</div>
												</div>
											</div>
											</div>
										</div>
									</div>


									<div class="col-sm-6">
										<div class="thumbnail text-center">
											<h5 class="text-center">
												<spring:message code="mrm.thumb.impression"
													text="Thumb Impression"></spring:message>
											</h5>
											<div id="showThumbW"></div>

											<div id="thumW" class="text-center">
												<c:if test="${command.modeType ne 'V'}">
												<div id="fingerPrintW" class="caption">
													<div id="addThumbW">
														<!-- <img id="imgFingerW" width=150 height=150  style="margin: 0rem auto;"></img> -->
															<button type="button" class="button-input btn btn-success"
																name="button" onclick="return captureFingerPrint()" >
																<spring:message code="mrm.thumbCap" text="Thumb" />
															</button>
														<div class="removeThumbW"></div>
													</div>
													</div>
												</c:if>

											</div>
											<div>
												<%-- <small class="text-red"> <spring:message
														code="mrm.image.upload.size"
														text="(Maximum Size should be 50KB - 100KB)" />
												</small> --%>
											</div>
											
											<div id="manualThumbW">
													<div  class="caption text-center">
														<c:if test="${command.modeType ne 'V'}">
															<div class="addThumbW">
																<apptags:formField fieldType="7" labelCode="" hasId="true"
																	fieldPath="marriageDTO.wifeDTO.captureFingerprintPath"
																	isMandatory="false" showFileNameHTMLId="true"
																	fileSize="MAX_FILE_SIZE" 
																	maxFileCount="CHECK_LIST_MAX_COUNT"
																	validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																	currentCount="${command.getThumbId()}"
																	callbackOtherTask="wifeDocumentTask()" />
																<div class="removeThumbW"></div>
															</div>
														</c:if>
	
													</div>
													<div>
														<small class="text-red "> <spring:message
																code="mrm.image.upload.size"
																text="(Maximum Size should be 50KB - 100KB)" />
														</small>
													</div>
												</div>

										</div>
									</div>
									
									

								</div>


							</div>
						</div>
					</div>
				</div>

				<%-- <c:choose>
						<c:when test="${docAndCharge}">
							<div class="padding-top-10 text-center">
								<button type="button" class="btn btn-success proceed" 
									disabled="true" onclick="displayMarCharge(this);">
									<spring:message code="mrm.chargeGenerateBT"
										text="Generate Charge"></spring:message>
								</button>
								<apptags:backButton url="AdminHome.html" />
							</div>
						</c:when>
						<c:otherwise>
							<div class="padding-top-10 text-center">
								<button type="button" class="btn btn-success proceed" 
									disabled="true" onclick="marChecklistSubmit(this);">
									<spring:message code="mrm.button.submit" text="Submit"></spring:message>
								</button>
								<apptags:backButton url="AdminHome.html" />
							</div>
						</c:otherwise>
					</c:choose> --%>

				
				
				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success proceed"
						 onclick="saveWifePhotoThumb(this);">
						<spring:message code="mrm.button.submit" text="Submit"></spring:message>
					</button>
					<%-- <apptags:backButton url="AdminHome.html" /> --%>
					<button type="button" class="btn btn-danger" 
						  onclick="backToHusbPhotoThumb(this);"> <spring:message code="mrm.button.back" text="Back"></spring:message>
					</button>
				</div>



			</form:form>
		</div>
	</div>
</div>