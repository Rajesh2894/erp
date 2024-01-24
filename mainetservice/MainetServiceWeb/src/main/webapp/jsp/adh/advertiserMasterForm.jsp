
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
<script type="text/javascript" src="js/adh/advertiserMaster.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script src="assets/libs/fullcalendar/moment.min.js"></script>
<!-- End JSP Necessary Tags -->
<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="advertiser.master.entry.title"
					text="Advertiser Master Entry" />
			</h2>
		</div>
		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>

			<form:form action="AdvertiserMaster.html"
				name="advertiserMasterEntry" id="advertiserMasterEntry"
				class="form-horizontal" commandName="command">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<apptags:input labelCode="advertiser.master.advertiser.number"
						path="advertiserMasterDto.agencyLicNo" 
						isDisabled="true"></apptags:input>
					<apptags:input labelCode="advertiser.master.advertiser.old.number"
						path="advertiserMasterDto.agencyOldLicNo" maxlegnth="40"
						cssClass="preventSpace"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
				</div>

				<div class="form-group">
					<apptags:date labelCode="agency.licence.from.date"
						datePath="advertiserMasterDto.agencyLicFromDate"
						fieldclass="datepicker" isMandatory="true"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>
					<apptags:date labelCode="advertiser.master.renewal.due.date"
						datePath="advertiserMasterDto.agencyLicToDate"
						fieldclass="datepicker" isMandatory="true"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>
				</div>

				<div class="form-group">
					<apptags:date labelCode="advertiser.master.registration.date"
						datePath="advertiserMasterDto.agencyLicIssueDate"
						fieldclass="datepicker" isMandatory="true"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>
					<apptags:input labelCode="advertiser.master.advertiser.name"
						path="advertiserMasterDto.agencyName" maxlegnth="200"
						cssClass="preventSpace hasCharacter" isMandatory="true"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label required-control" for=""><spring:message
							code="advertiser.master.category" text="Agency Category" /></label>
					<c:set var="baseLookupCode" value="ADC" />
					<apptags:lookupField
						items="${command.getLevelData(baseLookupCode)}"
						path="advertiserMasterDto.agencyCategory" cssClass="form-control"
						hasChildLookup="false" hasId="true" showAll="false"
						selectOptionLabelCode="adh.select" isMandatory="true"
						showOnlyLabel="Agency Category"
						disabled="${command.saveMode eq 'V' ? true : false }" />
					<apptags:input labelCode="advertiser.master.owner"
						path="advertiserMasterDto.agencyOwner" maxlegnth="200"
						cssClass="preventSpace hasCharacter" isMandatory="true"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

				</div>

				<div class="form-group">
					<apptags:input labelCode="advertiser.master.address"
						path="advertiserMasterDto.agencyAdd" maxlegnth="400"
						isMandatory="true" cssClass="preventSpace"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
					<apptags:input labelCode="advertiser.master.mobile.number"
						path="advertiserMasterDto.agencyContactNo" maxlegnth="40"
						isMandatory="true" cssClass="hasMobileNo"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

				</div>

				<div class="form-group">
					<apptags:input labelCode="advertiser.master.emailid"
						path="advertiserMasterDto.agencyEmail" maxlegnth="50"
						isMandatory="true" cssClass="hasemailclass preventSpace"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
					<apptags:input labelCode="advertiser.master.gst.number"
						path="advertiserMasterDto.gstNo" maxlegnth="15"
						cssClass=""
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

				</div>

				<div class="form-group">
					<apptags:input labelCode="advertiser.master.pan"
						path="advertiserMasterDto.panNumber" maxlegnth="20"
						cssClass="preventSpace"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
					<apptags:input labelCode="advertiser.master.uid.number"
						path="advertiserMasterDto.uidNo" maxlegnth="12"
						cssClass="hasNumber"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
				</div>
				<div class="form-group">
					<apptags:input labelCode="advertiser.master.remarks"
						path="advertiserMasterDto.agencyRemark" maxlegnth="400"
						cssClass="preventSpace"
						isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

				</div>


				<%-- <div class="accordion-toggle" id="">

					<h4 class="margin-top-0 margin-bottom-10 panel-title">
						<a data-toggle="collapse" class="collapsed" href="#a1"> <spring:message
								code="hoarding.details.table.title" text="Hoarding Details" />
						</a>
					</h4>
					<div id="a1" class="panel-collapse collapse">
						<div class="panel-body">
							<div class="table-responsive">
								<table id="hoardingDetails" summary="Hoarding Details"
									class="table table-bordered table-striped">
									<thead>
										<tr>
											<th><spring:message code="hoarding.label.number"
													text="Hoarding Number" /></th>

											<th><spring:message code="hoarding.label.hoarding.type"
													text="Hoarding Type" /></th>

											<th><spring:message
													code="hoarding.label.hoarding.subtype"
													text="Hoarding Subtype" /></th>

											<th><spring:message
													code="hoarding.label.hoarding.description"
													text="Description" /></th>

											<th><spring:message
													code="hoarding.label.hoarding.location" text="Location" /></th>

											<th><spring:message
													code="hoarding.label.hoarding.status" text="Status" /></th>

											<c:if test="${command.saveMode ne 'V'}">
												<th width="50"><a href="#" title="Add"
													class="btn btn-blue-2  btn-sm" onclick="addHoardingRow()"><strong
														class="fa fa-plus"></strong><span class="hide"></span></a></th>
											</c:if>
										</tr>
									</thead>

									<tbody>

										<tr>
											<td><form:input path=""
													cssClass="required-control form-control"
													disabled="${command.saveMode eq 'V' ? true : false }" /></td>
											<td><form:input path=""
													cssClass="required-control form-control" disabled="true" /></td>
											<td><form:input path=""
													cssClass="required-control form-control" disabled="true" /></td>
											<td><form:input path=""
													cssClass="required-control form-control" disabled="true" /></td>
											<td><form:input path=""
													cssClass="required-control form-control" disabled="true" /></td>
											<td><form:input path=""
													cssClass="required-control form-control" disabled="true" /></td>

											<c:if test="${command.saveMode ne 'V'}">
												<td><a href="#" class="btn btn-danger btn-sm"
													title="Delete"
													onclick="deleteHoardingRow($(this),'removeHoardingId');">
														<strong class="fa fa-trash"></strong> <span class="hide"><spring:message
																code="adh.delete" text="Delete" /></span>
												</a></td>
											</c:if>
										</tr>
									</tbody>


								</table>
							</div>
						</div>
					</div>
				</div> --%>


				<%-- <div class="panel-group accordion-toggle" id="contractDetails">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="contract.details.title" text="Contract Details" />
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">
									<apptags:input labelCode="contract.details.label.number"
										path=""
										isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
									<apptags:date labelCode="contract.details.label.date"
										datePath="" fieldclass="datepicker"
										isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>
								</div>

								<div class="form-group">
									<apptags:date labelCode="contract.details.label.from.date"
										datePath="" fieldclass="datepicker"
										isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>
									<apptags:date labelCode="contract.details.label.to.date"
										datePath="" fieldclass="datepicker"
										isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:date>
								</div>

								<div class="form-group">
									<apptags:input
										labelCode="contract.details.label.contract.amount" path=""
										isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
									<apptags:input
										labelCode="contract.details.label.contract.payment.schedule"
										path=""
										isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
								</div>
							</div>
						</div>
					</div>
				</div> --%>

				<div class="text-center">
					<c:if test="${command.saveMode eq 'A'}">
						<button type="button" class="btn btn-success"
							data-toggle="tooltip" data-original-title="Save"
							onclick="save(this,'A')">
							<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
							<spring:message code="adh.save" text="Save"></spring:message>
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success"
							data-toggle="tooltip" data-original-title="Save"
							onclick="save(this,'E')">
							<i class="fa fa-floppy-o padding-right-5" aria-hidden="true"></i>
							<spring:message code="adh.save" text="Save"></spring:message>
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'A'}">
						<button type="button" class="btn btn-warning"
							data-toggle="tooltip" data-original-title="Reset"
							onclick="resetForm(this);">
							<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
							<spring:message code="adh.reset" text="Reset"></spring:message>
						</button>
					</c:if>
					<button type="button" class="btn btn-danger" data-toggle="tooltip"
						data-original-title="Back"
						onclick="window.location.href='AdvertiserMaster.html'">
						<i class="fa fa-chevron-circle-left padding-right-5"
							aria-hidden="true"></i>
						<spring:message code="adh.back" text="Back"></spring:message>
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>