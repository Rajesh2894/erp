<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/VendorContractAgreement.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<!-- Start info box -->
	<div class="widget">
		<div class="widget-header">
			<h2>Contract Agreement</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i><span class="hide"><spring:message
							code="solid.waste.help" text="Help" /></span></a>
			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="solid.waste.mand"
						text="Field with" /><i class="text-red-1">*</i> <spring:message
						code="solid.waste.mand.field" text="is mandatory " /></span>
			</div>
			<form:form action="VendorContractAgreement.html"
				name="VendorContractAgreement" id="VendorContractAgreementId"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label required-control"
						for="ContractDate">Contract Date</label>
					<div class="col-sm-4">
						<div class="input-group">
							<c:set var="now" value="<%=new java.util.Date()%>" />
							<fmt:formatDate pattern="dd/MM/yyyy" value="${now}" var="date" />
							<form:input path="" class="form-control mandColorClass"
								id="ContractDate" value="${date}" maxlength="10" />
							<label class="input-group-addon mandColorClass"
								for="ContractDate"><i class="fa fa-calendar"></i> </label>
						</div>
					</div>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#Party1">Party
									I (ULB)</a>
							</h4>
						</div>
						<div id="Party1" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="table-overflow">
									<table class="table table-bordered table-striped">
										<tr>
											<th width="400">Department</th>
											<th width="400">Designation</th>
											<th>Represented By</th>
											<th width="100">Photo/Thumb</th>
										</tr>
										<tr>
											<td><c:set var="baseLookupCode" value="VCH" /> <apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path=""
													cssClass="form-control required-control"
													isMandatory="false" selectOptionLabelCode="selectdropdown" /></td>
											<td><c:set var="baseLookupCode" value="VCH" /> <apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path=""
													cssClass="form-control required-control"
													isMandatory="false" selectOptionLabelCode="selectdropdown" /></td>
											<td><c:set var="baseLookupCode" value="VCH" /> <apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path=""
													cssClass="form-control required-control"
													isMandatory="false" selectOptionLabelCode="selectdropdown" /></td>
											<td><a class="btn btn-blue-2" data-toggle="collapse"
												href="#Party1PhotoThumb"><i class="fa fa-camera"></i>
													Upload & View</a></td>
										</tr>
									</table>
								</div>
								<div class="collapse margin-top-5" id="Party1PhotoThumb">
									<div class="well">
										<div class="form-group">
											<div class="col-sm-6">
												<div class="thumbnail">
													<h5 class="text-center">Photos</h5>
													<img src="../images/placeholder.png" class="img-thumbnail">
													<div class="caption">
														<div class="text-center">
															<a href="#" class="btn btn-blue-1" data-toggle="tooltip"
																data-placement="top" title="Capture image"><i
																class="fa fa-camera"></i></a>
															<button type="reset" class="btn btn-danger"
																data-toggle="tooltip" data-placement="top" title="Reset">
																<i class="fa fa-refresh"></i>
															</button>
														</div>
														<div class="text-center">
															<small class="text-blue-2" style="padding-left: 10px;">(UploadFile
																upto 5MB and only jpeg,jpg,png,gif,bmp)</small>
															<apptags:formField fieldType="7" fieldPath=""
																showFileNameHTMLId="true"
																fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
																maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
																validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																currentCount="0">
															</apptags:formField>
														</div>
													</div>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="thumbnail">
													<h5 class="text-center">Thumb Impression</h5>
													<img src="../images/thumb-placeholder.png"
														class="img-thumbnail">
													<div class="caption">
														<div class="text-center">
															<a href="#" class="btn btn-blue-1" data-toggle="tooltip"
																data-placement="top" title="Capture image"><i
																class="fa fa-camera"></i></a>
															<button type="reset" class="btn btn-danger"
																data-toggle="tooltip" data-placement="top" title="Reset">
																<i class="fa fa-refresh"></i>
															</button>
														</div>
														<div class="text-center">
															<small class="text-blue-2" style="padding-left: 10px;">(UploadFile
																upto 5MB and only jpeg,jpg,png,gif,bmp)</small>
															<apptags:formField fieldType="7" fieldPath=""
																showFileNameHTMLId="true"
																fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
																maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
																validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																currentCount="0">
															</apptags:formField>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="text-center">
											<button type="submit" class="btn btn-success">Submit</button>
											<button type="button" class="btn btn-danger">Cancel</button>
										</div>
									</div>
								</div>
								<h4>Witness</h4>
								<div class="table-overflow">
									<table class="table table-bordered table-striped"
										id="customFields3">
										<tr>
											<th width="400">Name</th>
											<th width="400">Address</th>
											<th>Aadhaar No.</th>
											<th width="100">Photo/Thumb</th>
											<th width="50"><a title="Add" href="javascript:void(0);"
												class="addCF3 btn btn-success"><i
													class="fa fa-plus-circle"></i><span class="hide">Add</span></a></th>
										</tr>
										<tr>
											<td><form:input name="" path="" id=""
													class="form-control" /></td>
											<td><form:input name="" path="" id=""
													class="form-control" /></td>
											<td><form:input name="" path="" id=""
													class="form-control" /></td>
											<td><a class="btn btn-blue-2" data-toggle="collapse"
												href="#WitnessPhotoThumb"><i class="fa fa-camera"></i>
													Upload & View</a></td>
											<td><a title="Delete" class="btn btn-danger remCF3"
												href="javascript:void(0);"><i class="fa fa-trash-o"></i><span
													class="hide">Delete</span></a></td>
										</tr>
									</table>
								</div>
								<div class="collapse margin-top-5" id="WitnessPhotoThumb">
									<div class="well">
										<div class="form-group">
											<div class="col-sm-6">
												<div class="thumbnail">
													<h5 class="text-center">Photos</h5>
													<img src="../images/placeholder.png" class="img-thumbnail">
													<div class="caption">
														<div class="text-center">
															<a href="#" class="btn btn-blue-1" data-toggle="tooltip"
																data-placement="top" title="Capture image"><i
																class="fa fa-camera"></i></a>
															<button type="reset" class="btn btn-danger"
																data-toggle="tooltip" data-placement="top" title="Reset">
																<i class="fa fa-refresh"></i>
															</button>
														</div>
														<div class="text-center">
															<small class="text-blue-2" style="padding-left: 10px;">(UploadFile
																upto 5MB and only jpeg,jpg,png,gif,bmp)</small>
															<apptags:formField fieldType="7" fieldPath=""
																showFileNameHTMLId="true"
																fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
																maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
																validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																currentCount="0">
															</apptags:formField>
														</div>
													</div>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="thumbnail">
													<h5 class="text-center">Thumb Impression</h5>
													<img src="../images/thumb-placeholder.png"
														class="img-thumbnail">
													<div class="caption">
														<div class="text-center">
															<a href="#" class="btn btn-blue-1" data-toggle="tooltip"
																data-placement="top" title="Capture image"><i
																class="fa fa-camera"></i></a>
															<button type="reset" class="btn btn-danger"
																data-toggle="tooltip" data-placement="top" title="Reset">
																<i class="fa fa-refresh"></i>
															</button>
														</div>
														<div class="text-center">
															<small class="text-blue-2" style="padding-left: 10px;">(UploadFile
																upto 5MB and only jpeg,jpg,png,gif,bmp)</small>
															<apptags:formField fieldType="7" fieldPath=""
																showFileNameHTMLId="true"
																fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
																maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
																validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																currentCount="0">
															</apptags:formField>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="text-center">
											<button type="submit" class="btn btn-success">Submit</button>
											<button type="button" class="btn btn-danger">Cancel</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#Party2">
									Party II (Vendor)</a>
							</h4>
						</div>
						<div id="Party2" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="table-overflow-sm">
									<table class="table table-bordered table-striped">
										<tr>
											<th width="400">Vendor Type</th>
											<th width="400">Vendor Name</th>
											<th>Represented By</th>
											<th width="100">Photo/Thumb</th>
										</tr>
										<tr>
											<td><c:set var="baseLookupCode" value="VCH" /> <apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path=""
													cssClass="form-control required-control"
													isMandatory="false" selectOptionLabelCode="selectdropdown" /></td>


											<td><c:set var="baseLookupCode" value="VCH" /> <apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path=""
													cssClass="form-control required-control"
													isMandatory="false" selectOptionLabelCode="selectdropdown" /></td>


											<td><form:input name="" path="" id=""
													class="form-control" /></td>

											<td><a class="btn btn-blue-2" data-toggle="collapse"
												href="#Party2PhotoThumb"><i class="fa fa-camera"></i>
													Upload & View</a></td>
										</tr>
									</table>
								</div>
								<div class="collapse margin-top-5" id="Party2PhotoThumb">
									<div class="well">
										<div class="form-group">
											<div class="col-sm-6">
												<div class="thumbnail">
													<h5 class="text-center">Photos</h5>
													<img src="../images/placeholder.png" class="img-thumbnail">
													<div class="caption">
														<div class="text-center">
															<a href="#" class="btn btn-blue-1" data-toggle="tooltip"
																data-placement="top" title="Capture image"><i
																class="fa fa-camera"></i></a>
															<button type="reset" class="btn btn-danger"
																data-toggle="tooltip" data-placement="top" title="Reset">
																<i class="fa fa-refresh"></i>
															</button>
														</div>
														<div class="text-center">
															<small class="text-blue-2" style="padding-left: 10px;">(UploadFile
																upto 5MB and only jpeg,jpg,png,gif,bmp)</small>
															<apptags:formField fieldType="7" fieldPath=""
																showFileNameHTMLId="true"
																fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
																maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
																validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																currentCount="0">
															</apptags:formField>
														</div>
													</div>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="thumbnail">
													<h5 class="text-center">Thumb Impression</h5>
													<img src="../images/thumb-placeholder.png"
														class="img-thumbnail">
													<div class="caption">
														<div class="text-center">
															<a href="#" class="btn btn-blue-1" data-toggle="tooltip"
																data-placement="top" title="Capture image"><i
																class="fa fa-camera"></i></a>
															<button type="reset" class="btn btn-danger"
																data-toggle="tooltip" data-placement="top" title="Reset">
																<i class="fa fa-refresh"></i>
															</button>
														</div>
														<div class="text-center">
															<small class="text-blue-2" style="padding-left: 10px;">(UploadFile
																upto 5MB and only jpeg,jpg,png,gif,bmp)</small>
															<apptags:formField fieldType="7" fieldPath=""
																showFileNameHTMLId="true"
																fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
																maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
																validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																currentCount="0">
															</apptags:formField>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="text-center">
											<button type="submit" class="btn btn-success">Submit</button>
											<button type="button" class="btn btn-danger">Cancel</button>
										</div>
									</div>
								</div>
								<h4>Witness</h4>
								<div class="table-overflow-sm">
									<table class="table table-bordered table-striped"
										id="customFields4">
										<tr>
											<th width="400">Name</th>
											<th width="400">Address</th>
											<th>Aadhaar No.</th>
											<th width="100">Photo/Thumb</th>
											<th width="50"><a title="Add" href="javascript:void(0);"
												class="addCF4 btn btn-success"><i
													class="fa fa-plus-circle"></i><span class="hide">Add</span></a></th>
										</tr>
										<tr>

											<td><form:input name="" path="" id=""
													class="form-control" /></td>
											<td><form:input name="" path="" id=""
													class="form-control" /></td>
											<td><form:input name="" path="" id=""
													class="form-control" /></td>
											<td><a class="btn btn-blue-2" data-toggle="collapse"
												href="#Party2WitnessPhotoThumb"><i class="fa fa-camera"></i>
													Upload & View</a></td>
											<td><a title="Delete" class="btn btn-danger remCF4"
												href="javascript:void(0);"><i class="fa fa-trash-o"></i><span
													class="hide">Delete</span></a></td>
										</tr>
									</table>
								</div>
								<div class="collapse margin-top-5" id="Party2WitnessPhotoThumb">
									<div class="well">
										<div class="form-group">
											<div class="col-sm-6">
												<div class="thumbnail">
													<h5 class="text-center">Photos</h5>
													<img src="../images/placeholder.png" class="img-thumbnail">
													<div class="caption">
														<div class="text-center">
															<a href="#" class="btn btn-blue-1" data-toggle="tooltip"
																data-placement="top" title="Capture image"><i
																class="fa fa-camera"></i></a>
															<button type="reset" class="btn btn-danger"
																data-toggle="tooltip" data-placement="top" title="Reset">
																<i class="fa fa-refresh"></i>
															</button>
														</div>
														<div class="text-center">
															<small class="text-blue-2" style="padding-left: 10px;">(UploadFile
																upto 5MB and only jpeg,jpg,png,gif,bmp)</small>
															<apptags:formField fieldType="7" fieldPath=""
																showFileNameHTMLId="true"
																fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
																maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
																validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																currentCount="0">
															</apptags:formField>
														</div>
													</div>
												</div>
											</div>
											<div class="col-sm-6">
												<div class="thumbnail">
													<h5 class="text-center">Thumb Impression</h5>
													<img src="../images/thumb-placeholder.png"
														class="img-thumbnail">
													<div class="caption">
														<div class="text-center">
															<a href="#" class="btn btn-blue-1" data-toggle="tooltip"
																data-placement="top" title="Capture image"><i
																class="fa fa-camera"></i></a>
															<button type="reset" class="btn btn-danger"
																data-toggle="tooltip" data-placement="top" title="Reset">
																<i class="fa fa-refresh"></i>
															</button>
														</div>
														<div class="text-center">
															<small class="text-blue-2" style="padding-left: 10px;">(UploadFile
																upto 5MB and only jpeg,jpg,png,gif,bmp)</small>
															<apptags:formField fieldType="7" fieldPath=""
																showFileNameHTMLId="true"
																fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
																maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
																validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
																currentCount="0">
															</apptags:formField>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="text-center">
											<button type="submit" class="btn btn-success">Submit</button>
											<button type="button" class="btn btn-danger">Cancel</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#Contract">Contract</a>
							</h4>
						</div>
						<div id="Contract" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="TenderName">Tender No.</label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input name="" path="" id="TenderNo"
												class="form-control mandColorClass" />
											<a href="#" class="input-group-addon"><i
												class="fa fa-search"></i></a>
										</div>
									</div>
									<label class="col-sm-2 control-label required-control"
										for="TenderDate">Tender Date</label>
									<div class="col-sm-4">
										<div class="input-group">

											<c:set var="now" value="<%=new java.util.Date()%>" />
											<fmt:formatDate pattern="dd/MM/yyyy" value="${now}"
												var="date" />
											<form:input path="" class="form-control mandColorClass"
												id="TenderDate" value="${date}" maxlength="10" />
											<label class="input-group-addon mandColorClass"
												for="TenderDate"><i class="fa fa-calendar"></i> </label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<apptags:input labelCode="Resolution No." path=""
										isMandatory="true" maxlegnth="10"
										cssClass="form control mandcolour hasNumber">
									</apptags:input>
									<label class="col-sm-2 control-label required-control"
										for="ResulationDate">Resolution Date</label>
									<div class="col-sm-4">
										<div class="input-group">
											<c:set var="now" value="<%=new java.util.Date()%>" />
											<fmt:formatDate pattern="dd/MM/yyyy" value="${now}"
												var="date" />
											<form:input path="" class="form-control mandColorClass"
												id="ResulationDate" value="${date}" maxlength="10" />
											<label class="input-group-addon mandColorClass"
												for="ResulationDate"><i class="fa fa-calendar"></i>
											</label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="ContractType">Contract Type</label>
									<div class="col-sm-4">
										<c:set var="baseLookupCode" value="VCH" />
										<apptags:lookupField
											items="${command.getLevelData(baseLookupCode)}" path=""
											cssClass="form-control required-control" isMandatory="false"
											selectOptionLabelCode="selectdropdown" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="ContractFromDate">Contract From Date</label>
									<div class="col-sm-4">
										<div class="input-group">
											<c:set var="now" value="<%=new java.util.Date()%>" />
											<fmt:formatDate pattern="dd/MM/yyyy" value="${now}"
												var="date" />
											<form:input path="" class="form-control mandColorClass"
												id="ContractFromDate" value="${date}" maxlength="10" />
											<label class="input-group-addon mandColorClass"
												for="ContractFromDate"><i class="fa fa-calendar"></i>
											</label>
										</div>
									</div>
									<label class="col-sm-2 control-label required-control"
										for="ContractToDate">Contract To Date</label>
									<div class="col-sm-4">
										<div class="input-group">
											<c:set var="now" value="<%=new java.util.Date()%>" />
											<fmt:formatDate pattern="dd/MM/yyyy" value="${now}"
												var="date" />
											<form:input path="" class="form-control mandColorClass"
												id="ContractToDate" value="${date}" maxlength="10" />
											<label class="input-group-addon mandColorClass"
												for="ContractToDate"><i class="fa fa-calendar"></i>
											</label>

										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Contract Mode <span
										class="mand">*</span></label>
									<div class="col-sm-4">
										<label class="radio-inline"> <form:radiobutton
												name="ContractType" class="ContractMode" path="" id=""
												value="Commercial" /> Commercial
										</label> <label class="radio-inline"> <form:radiobutton
												name="ContractType" class="ContractMode" path="" id=""
												value="Non-Commercial" /> Non-Commercial
										</label>
									</div>

									<div class="display-hide Commercial">
										<label class="col-sm-2 control-label" for="ContractPayment">Contract
											Payment <span class="mand">*</span>
										</label>
										<div class="col-sm-4">
											<label class="radio-inline"> <form:radiobutton
													name="ContractPayment" path="" id="" value="" /> Payable
											</label> <label class="radio-inline"> <form:radiobutton
													name="ContractPayment" path="" id="" value="" />
												Receivable
											</label>
										</div>
									</div>
								</div>
								<div class="display-hide Commercial">
									<div class="form-group">

										<apptags:input labelCode="Contract No." path=""
											isMandatory="true" maxlegnth="10"
											cssClass="form control mandcolour hasNumber">
										</apptags:input>

										<apptags:input labelCode="ContractAmount" path=""
											isMandatory="true" maxlegnth="10"
											cssClass="form control mandcolour hasNumber">
										</apptags:input>


										<apptags:input labelCode="Security Deposit Amount" path=""
											isMandatory="true" maxlegnth="10"
											cssClass="form control mandcolour hasNumber">
										</apptags:input>

									</div>


									<div class="form-group">

										<apptags:input labelCode="Security Deposit Receipt" path=""
											isMandatory="true" maxlegnth="10"
											cssClass="form control mandcolour hasNumber">
										</apptags:input>



										<apptags:input labelCode="Security Deposit Receipt" path=""
											isMandatory="true" maxlegnth="10"
											cssClass="form control mandcolour hasNumber">
										</apptags:input>


										<label class="col-sm-2 control-label"
											for="SecurityDepositDate">Security Deposit Date</label>
										<div class="col-sm-4">
											<div class="input-group">
												<c:set var="now" value="<%=new java.util.Date()%>" />
												<fmt:formatDate pattern="dd/MM/yyyy" value="${now}"
													var="date" />
												<form:input path="" class="form-control mandColorClass"
													id="SecurityDepositDate" value="${date}" maxlength="10" />
												<label class="input-group-addon mandColorClass"
													for="SecurityDepositDate"><i class="fa fa-calendar"></i>
												</label>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="PaymentTerms">Payment Terms</label>
										<div class="col-sm-4">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="false"
												selectOptionLabelCode="selectdropdown" />
										</div>
										<div class="col-sm-4">
											<label class="checkbox-inline" for="AppreciationApplicable">
												<form:checkbox name="AppreciationApplicable"
													id="AppreciationApplicable" path="" value="" /> <strong>
													Appreciation Applicable</strong>
											</label>
										</div>
									</div>
								</div>
								<div class="showhide">
									<div class="form-group">
										<label class="col-sm-2 control-label required-control"
											for="TypeAmount">Appreciation Type</label>
										<div class="col-sm-4">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="false"
												selectOptionLabelCode="selectdropdown" />
											<form:input name="" path="" id=""
												class="form-control col-xs-8"
												placeholder="Appreciation value" />
										</div>

										<label class="col-sm-2 control-label required-control" for="">Appreciation
											Period</label>
										<div class="col-sm-4">
											<c:set var="baseLookupCode" value="VCH" />
											<apptags:lookupField
												items="${command.getLevelData(baseLookupCode)}" path=""
												cssClass="form-control required-control" isMandatory="false"
												selectOptionLabelCode="selectdropdown" />
											<form:input name="" path="" id=""
												class="form-control col-xs-8"
												placeholder="Appreciation Cycle" />
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-6">
										<label class="checkbox-inline" for="Allow Renewal"> <form:checkbox
												name="Allow Renewal" id="Allow Renewal" path="" value="" />
											<strong> Allow Renewal</strong>
										</label>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#UploadDocument">
									Upload Agreement and Other Document</a>
							</h4>
						</div>
						<div id="UploadDocument" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="table-overflow-sm">
									<table class="table table-bordered table-striped"
										id="customFields">
										<tr>
											<th width="100">Sr. No.</th>
											<th>Document Name <span class="mand">*</span></th>
											<th>Type of Content <span class="mand">*</span></th>
											<th>Upload <span class="mand">*</span></th>
											<th width="50"><a title="Add" href="javascript:void(0);"
												class="addCF btn btn-success"><i
													class="fa fa-plus-circle"></i><span class="hide">Add</span></a></th>
										</tr>
										<tr>
											<td>1</td>
											<td><form:input name="" path="" id=""
													class="form-control mandColorClass" /></td>
											<td><c:set var="baseLookupCode" value="VCH" /> <apptags:lookupField
													items="${command.getLevelData(baseLookupCode)}" path=""
													cssClass="form-control required-control"
													isMandatory="false" selectOptionLabelCode="selectdropdown" /></td>
											<td><small class="text-blue-2"
												style="padding-left: 10px;">(UploadFile upto 5MB and
													only jpeg,jpg,png,gif,bmp)</small> <apptags:formField fieldType="7"
													fieldPath="" showFileNameHTMLId="true"
													fileSize="WORK_COMMON_MAX_SIZE" isMandatory="false"
													maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
													validnFunction="IMAGE_UPLOAD_VALIDATION_EXTENSION"
													currentCount="0">
												</apptags:formField></td>
											<td><a title="Delete" class="btn btn-danger remCF"
												href="javascript:void(0);"><i class="fa fa-trash-o"></i><span
													class="hide">Delete</span></a></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#Terms">Terms
									& Conditions</a>
							</h4>
						</div>
						<div id="Terms" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="table-responsive">
									<table class="table table-bordered table-striped"
										id="customFields2">
										<tr>
											<th>Sr.No.</th>
											<th>Terms & Conditions<span class="mand">*</span></th>
											<th width="50"><a title="Add" href="javascript:void(0);"
												class="addCF2 btn btn-success"><i
													class="fa fa-plus-circle"></i><span class="hide">Add</span></a></th>
										</tr>
										<tr>
											<td width="50">1</td>
											<td><form:textarea name="" path="" id="" cols="" rows=""
													class="form-control mandColorClass" /></td>
											<td><a title="Delete" class="btn btn-danger remCF2"
												href="javascript:void(0);"><i class="fa fa-trash-o"></i><span
													class="hide">Delete</span></a></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center">
					<button type="button" id="save" class="btn btn-success"
						onclick="saveVendorVerificationForm(this);">
						<spring:message code="solid.waste.submit" text="Submit" />
					</button>
					<button type="Reset" class="btn btn-warning">
						<spring:message code="solid.waste.reset" text="Reset" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backButton();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>
				</div>
			</form:form>
		</div>
	</div>

</div>