<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/marriage_mgmt/witnessPhotoThumb.js"></script>
<script type="text/javascript" src="js/marriage_mgmt/mrm-file-upload.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<script type="text/javascript"
	src="js/mainet/jQueryMaskedInputPlugin.js"></script>
<style>
.fileUpload.fileinput.fileinput-new, .removePhoto, .removeThumb {
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

			<form:form action="MarriageRegistration.html" id="husbandFormId"
						method="post" class="form-horizontal">
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
				
				<form:hidden path="photoThumbDisp" id="photoThumbDisp" />
				<form:hidden path="photoId" id="wit1PhotoId" />
				<form:hidden path="thumbId" id="wit1ThumbId" />
				<!-- List of Witness -->
				<c:forEach items="${command.marriageDTO.witnessDetailsDTO}" var="witnessDTO" varStatus="count">
				
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse1">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title table" id="">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse1" href="#document"><spring:message code="mrm.witness" text="Witness " /> ${count.count} 
									<spring:message code="mrm.tab.photoThumbDetails" text="Photo And Thumb Impression" /></a>
							</h4>
						</div>
						
						<div id="document" class="panel-collapse collapse in">
							
							<div class="row margin-bottom-10 margin-left-0 margin-right-0">
								<div class="col-xs-3 col-sm-2 padding-top-10">
									<span class="text-bold"><spring:message
											code="mrm.witness" text="Witness Name" /></span>
								</div>
								<div class="col-xs-9 col-sm-10 padding-top-10">
									<c:choose>
									  <c:when test="${userSession.getCurrent().getLanguageId() eq 1}">
									   	:<span class="text-bold margin-left-20">${witnessDTO.firstNameEng} ${witnessDTO.lastNameEng}</span>
									  </c:when>
									  <c:otherwise>
									  	:<span class="text-bold margin-left-20">${witnessDTO.firstNameReg} ${witnessDTO.lastNameReg}</span>
									  </c:otherwise>
									</c:choose>
									
								</div>
							</div>
							
							
							<div class="panel-body">
								<div class="form-group">
									<div class="col-sm-6">
										<div class="thumbnail text-center">


											<div class="pho" class="text-center">
													<h5 class="text-center">
														<spring:message code="mrm.master.photos" text="Photos"></spring:message>
													</h5>
													<!-- showPhoto id is used to display image -->
													<div id="showPhoto_${witnessDTO.witnessId}0"></div>
													
														<c:if test="${command.modeType ne 'V'}">
														<!--start camera button  -->
														<div class="startCamBTWT" class="text-center">
															<div class="caption">
																<button  type="button" class="button-input btn btn-success" id="startCamId_${witnessDTO.witnessId}0"
																			name="button" onclick="return startWebCAMWT(${witnessDTO.witnessId}0)" >
																			<spring:message code="mrm.photoCap" text="Capture" />
																</button>
															</div>
														</div>
														<div class="webcam caption" id="webcam_${witnessDTO.witnessId}0">
															<div class="addPhoto">
																<video class="player" id="ply_${witnessDTO.witnessId}0" width=150 height=150 autoplay style="margin: 0rem auto;"></video>
																<canvas class="canvas" id="canv_${witnessDTO.witnessId}0"></canvas>
																
															    <button type="button" class="button-input btn btn-success" id="captureWebCAMWT_${witnessDTO.witnessId}0"
																	name="button" value="Capture"   onclick="return captureWebCAMWT(${witnessDTO.witnessId}0,${witnessDTO.witnessId}1)" >
																	<spring:message code="mrm.photoCap" text="Capture" />
																</button>
															    
															   
																<div class="removePhoto remPhoto_${witnessDTO.witnessId}0"></div>
															</div>
														</div>
														</c:if>
													
													
														<div class="manual">
															<div class="caption">
																<c:if test="${command.modeType ne 'V'}">
																	<div class="addPhoto">
																		<apptags:formField fieldType="7" labelCode=""
																			hasId="true"
																			fieldPath=""
																			isMandatory="false" showFileNameHTMLId="true"
																			fileSize="MAX_FILE_SIZE" 
																			maxFileCount="CHECK_LIST_MAX_COUNT"
																			validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																			currentCount="${witnessDTO.witnessId}0"
																			callbackOtherTask="otherTask(${witnessDTO.witnessId}0,${witnessDTO.witnessId}1)" />
																			
																		<div class="removePhoto remPhoto_${witnessDTO.witnessId}0"></div>
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
									<div class="col-sm-6">
										<div class="thumbnail text-center">
											<h5 class="text-center">
												<spring:message code="mrm.thumb.impression"
													text="Thumb Impression"></spring:message>
											</h5>
											<div id="showThumb_${witnessDTO.witnessId}1"></div>
											<div id="thum" class="text-center">
												<div class="fingerPrintWT caption">
														<div class="addThumb">
	
															<!-- <img id="imgFinger" width=150px height=150px  style="margin: 0rem auto;"></img> -->
															<button type="button" class="button-input btn btn-success"
																name="button" onclick="return captureFingerPrintWT(${witnessDTO.witnessId}0,${witnessDTO.witnessId}1)" >
																<spring:message code="mrm.thumbCap" text="Thumb" />
															</button>
	
															<div class="removeThumb remThumb_${witnessDTO.witnessId}1"></div>
														</div>
												</div>
											
											<div class="manualThumbWT">
												<div class="caption">
													<c:if test="${command.modeType ne 'V'}">
														<div class="addThumb">
															<apptags:formField fieldType="7" labelCode="" hasId="true"
																fieldPath=""
																isMandatory="false" showFileNameHTMLId="true"
																fileSize="MAX_FILE_SIZE" 
																maxFileCount="CHECK_LIST_MAX_COUNT"
																validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																currentCount="${witnessDTO.witnessId}1"
																callbackOtherTask="otherTask(${witnessDTO.witnessId}0,${witnessDTO.witnessId}1)" />
																
															<div class="removeThumb remThumb_${witnessDTO.witnessId}1"></div>
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
				</div>
								<script>
						   
							   otherTask(${witnessDTO.witnessId}0,${witnessDTO.witnessId}1);
						   
				               </script>
				</c:forEach>

				<div class="padding-top-10 text-center">
					<button type="button" class="btn btn-success proceed"
						 onclick="displayMarCharge(this);">
						<spring:message code="mrm.chargeGenerateBT" text="Generate Charge"></spring:message>
					</button>
					<%-- <apptags:backButton url="AdminHome.html" /> --%>
					<button type="button" class="btn btn-danger" 
						  onclick="saveHusbPhotoThumb(this);"> <spring:message code="mrm.button.back" text="Back"></spring:message>
					</button>
					
				</div>



			</form:form>
		</div>
	</div>
</div>