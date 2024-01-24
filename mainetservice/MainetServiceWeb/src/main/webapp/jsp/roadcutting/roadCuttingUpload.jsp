<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/roadcutting/roadCutting.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?libraries=places&key=AIzaSyAvvwgwayDHlTq9Ng83ouZA_HWSxbni25c"></script>

 <style>
.width-50 .form-control{width: 50%;}
</style>

<div class="pagediv">
	<apptags:breadcrumb></apptags:breadcrumb>
	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message text="Project Location" code="" />
				</h2>
				<apptags:helpDoc url="RoadCutting.html"></apptags:helpDoc>
			</div>
			<div class="widget-content padding">
				<form:form id="RoadCuttingId" action="RoadCutting.html"
					method="POST" class="form-horizontal" name="RoadCuttingId">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
						<form:hidden path="" value="${index}" id="index"/>
					<div class="panel-group accordion-toggle"
						id="accordion_single_collapse1">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-toggle="collapse" class="" id=""
										data-parent="#accordion_single_collapse1" href="#c1"><spring:message
											text="Project Location" /></a>
								</h4>
							</div>
							<div id="c1" class="panel-collapse collapse in">
								<div class="panel-body">
								
				<c:if test="${mode eq 'A' }">
					<div class="form-group">
						<label class="col-sm-2 control-label"> <spring:message
								code="road.start.point" text="Upload Start Point Image" />
						</label>
						<div class="col-sm-4">
							<apptags:formField fieldType="7" labelCode="" hasId="true"
								fieldPath="" isMandatory="false" showFileNameHTMLId="true"
								fileSize="BND_COMMOM_MAX_SIZE"
								maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
								checkListMandatoryDoc="" callbackOtherTask="startPoints()" checkListDesc=""
								currentCount="${100 + index}" />
						</div>
						<label class="col-sm-2 control-label"><spring:message
								code="road.end.point" text="Upload End Point Image" /></label>
						<div class="col-sm-4">
							<apptags:formField fieldType="7" labelCode="" hasId="true"
								fieldPath="" isMandatory="false" showFileNameHTMLId="true"
								fileSize="BND_COMMOM_MAX_SIZE"
								maxFileCount="CHECK_LIST_MAX_COUNT"
								validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
								checkListMandatoryDoc="" callbackOtherTask="endPoints()" checkListDesc=""
								currentCount="${200 + index}" />
						</div>
					</div>
			</c:if>
			
			
			
			<c:if test="${mode eq 'V' }">
					<div class="form-group">
						<label class="col-sm-2 control-label"> <spring:message
								code="road.start.point" text="Upload Start Point Image" />
						</label>
							<div class="col-sm-4">
								<c:set var="keyString" value="${100 + index}"/>
								<c:forEach items="${command.projectLocationDoc}" var="map">
									<c:if test="${map.key == keyString}">
										<c:set var="lookUp" value="${map.value}"/>
									</c:if>
								</c:forEach>

						
								<apptags:filedownload filename="${lookUp.attFname}"
									filePath="${lookUp.attPath}"
									actionUrl="AdminHome.html?Download"></apptags:filedownload>
							</div>
						<label class="col-sm-2 control-label"><spring:message
								code="" text="Upload End Point Image" /></label>
						<div class="col-sm-4">
							<c:set var="keyString" value="${200 + index}"/>
								<c:forEach items="${command.projectLocationDoc}" var="map">
									<c:if test="${map.key == keyString}">
										<c:set var="lookUp" value="${map.value}"/>
									</c:if>
								</c:forEach>
							<apptags:filedownload filename="${lookUp.attFname}"
								filePath="${lookUp.attPath}"
								actionUrl="AdminHome.html?Download"></apptags:filedownload>
						</div>
					</div>
			</c:if>
					<div class="form-group">
						<label class="col-sm-2 control-label"> <spring:message
								code="RoadCuttingDto.latlong" text="Lat/Long" />
						</label>
						<div class="col-sm-2">
							<form:input
								path="roadCuttingDto.roadList[${index}].rcdStartlatitude"
								class="form-control" maxlength="500" placeholder="Latitude" id="sLatitude" readonly="${mode eq 'V' }"/>
								</div>
						<div class="col-sm-2">
							<form:input
								path="roadCuttingDto.roadList[${index}].rcdStartlogitude"
								class="form-control" maxlength="500" placeholder="Longitude" id="sLongitude" readonly="${mode eq 'V' }"/>
						</div>
						<label class="col-sm-2 control-label"><spring:message
								code="RoadCuttingDto.latlong" text="Lat/LONG" /></label>
						<div class="col-sm-2">
							<form:input
								path="roadCuttingDto.roadList[${index}].rcdEndlatitude"
								class="form-control" maxlength="500" placeholder="Latitude"  id="eLatitude" readonly="${mode eq 'V' }"/>
							</div>
							<div class="col-sm-2">	
							<form:input
								path="roadCuttingDto.roadList[${index}].rcdEndlogitude"
								class="form-control" maxlength="500" placeholder="Longitude" id="eLongitude" readonly="${mode eq 'V' }"/>
						</div>
					</div>
					
					</div>
					</div>
					</div>
					
					
					<!-- Affected Area -->
						<%-- <div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title table" id="">
									<a data-toggle="collapse" class=""
										data-parent="#accordion_single_collapse1" href="#b3"><spring:message
											text="Affected Area Details" /></a>
								</h4>
							</div>
							<div id="b3" class="panel-collapse collapse in">
								<div class="panel-body">
								
									<c:set var="count" value="0" scope="page" />
									<div class="table-responsive">
										<table class="table text-left table-striped table-bordered"
											id="roadRoute">
											<tbody>
												<tr>
													<th><spring:message	code=""  text="Sr. No."/></th>
													<th><spring:message	code=""  text="Upload"/></th>
													<th><spring:message	code="" text="Latitude"/></th>	
													<th><spring:message code="" text="Longitude"/></th>
													<th><a href="javascript:void(0);" data-toggle=""
														data-placement="top"
														class="addRoad btn btn-success btn-sm"
														data-original-title="Add" id="addAffectedArea" title="add"><i
															class="fa fa-plus-circle"></i></a></th>
												</tr>
													<tr class="appendableClass">
														<td width="5%">
															<form:input path="" value="${count+1 }" readonly="true" class="form-control" />
														</td>
														<td><div class=" col-sm-9">
														<form:input	path="" class="form-control" maxlength="500"  /></div>
															<apptags:formField fieldType="7" labelCode=""
																hasId="true" fieldPath="" isMandatory="false"
																showFileNameHTMLId="true" fileSize="BND_COMMOM_MAX_SIZE"
																maxFileCount="CHECK_LIST_MAX_COUNT"
																validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																checkListMandatoryDoc="" callbackOtherTask="mytask()"
																checkListDesc="" currentCount="${300 + index}" />
														</td>
														<td><form:input	path="" class="form-control" maxlength="500"  /></td>
														<td><form:input	path="" class="form-control" maxlength="500"  /></td>

														<td class="text-center"><a href="javascript:void(0);" data-toggle=""
															data-placement="top"
															class="remRoad btn btn-danger btn-sm"
															data-original-title="Delete" title="Delete"><i
																class="fa fa-trash"></i></a></td>
													</tr>
													
											</tbody>
										</table>
									</div>

								</div>

							</div>
						</div> --%>
						</div>	
					<!-- Affected Area -->
					<div class="form-group">
						<div class="text-center">
							<a data-toggle="collapse" href="#collapseExample"
								class="btn btn-blue-2" id="viewloc" onclick="showMap()"><i
								class="fa fa-map-marker"></i> <spring:message
									code="disposal.site.master.view.location" text="View  Location" /></a>
						</div>
						<div class="collapse margin-top-10" id="collapseExample">
							<div class="border-1 padding-5"
								style="height: 400px; width: 100%;" id="map-canvas"></div>
						</div>
					</div>


					<div class="text-center">
						<c:if test="${mode eq 'A' }">
						<button type="button" class="button-input btn btn-success"
							name="button" value="procced" onclick="saveData()"
							id="procced">
							<spring:message text="Procced" />
						</button>
						
						<button type="button" class="button-input btn btn-danger"
							name="button" value="procced" onclick="back('A')"
							id="procced">
							<spring:message text="Back" />
						</button>
						</c:if>
						
						<c:if test="${mode eq 'V' }">
						
						<button type="button" class="button-input btn btn-danger"
							name="button" value="procced" onclick="back('V')"
							id="procced">
							<spring:message text="Back" />
						</button>
						</c:if>
					</div>

				</form:form>
			</div>
		</div>
	</div>
</div>
