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
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/property/propertyStatusUpdate.js"></script>

<style type="text/css">
.boldClass {
	font-weight: bold !important;
}
</style>
<apptags:breadcrumb></apptags:breadcrumb>

<!-- Start Content here -->
<div class="content">
	<!-- Start Main Page Heading -->
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="propertyTax.statusUpdate"
						text="Property Status Update" /></strong>
			</h2>		
		</div>

		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="property.changeInAss"
						text="Property Status Update" /> </span>
			</div>

			<!-- Start Form -->
			<form:form action="PropertyStatusUpdate.html"
				class="form-horizontal form" name="PropertyStatusUpdate"
				id="PropertyStatusUpdate">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>

				<div class="form-group">
					<label class="col-sm-2 control-label" for="assStatus"><spring:message
							code="property.propertyStatus" text="Current Status"/></label>
					<div class="col-sm-4">
						<form:input path="provisionalAssesmentMstDto.assStatus"
							id="assStatus" class="form-control boldClass" disabled="true" />
					</div>
				</div>
				<div class="form-group">
					<apptags:input labelCode="propertydetails.PropertyNo."
						path="provisionalAssesmentMstDto.assNo" isDisabled="true"></apptags:input>

					<apptags:input labelCode="property.ChangeInAss.oldpid"
						path="provisionalAssesmentMstDto.assOldpropno" isDisabled="true"></apptags:input>
				</div>

				<div class="panel-collapse collapse in" id="OwnshipDetail">

					<div class="form-group">
						<label class="col-sm-2 control-label" for="ownershiptype"><spring:message
								code="property.ownershiptype" /></label>
						<div class="col-sm-4">
							<form:input path="provisionalAssesmentMstDto.proAssOwnerTypeName"
								id="ownershiptype" class="form-control" disabled="true" />
							<form:hidden path="ownershipPrefix" id="ownershipId"
								class="form-control" />

						</div>
					</div>


					<c:choose>
						<c:when test="${command.getOwnershipPrefix() eq 'SO'}">
							<table id="singleOwnerTable"
								class="table table-striped table-bordered ">
								<tbody>
									<tr>

										<th width="20%"><spring:message
												code="ownersdetail.ownersname" /></th>
										<th width="9%"><spring:message code="ownersdetail.gender" /></th>
										<th width="9%"><spring:message
												code="ownerdetails.relation" /></th>
										<th width="20%"><spring:message
												code="ownersdetails.GuardianName" /></th>
										<th width="10%"><spring:message
												code="ownersdetail.mobileno" /></th>
										<th width="10%"><spring:message code="property.email" /></th>
										<th width="12%"><spring:message
												code="ownersdetail.adharno" /></th>
										<th width="10%"><spring:message
												code="ownersdetail.pancard" /></th>
									</tr>

									<tr>
										<td><form:input id="assoOwnerName"
												path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName"
												class="form-control" disabled="true" /></td>

										<td class="ownerDetails"><form:input id="ownerGender"
												path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].proAssGenderId"
												class="form-control" disabled="true" /></td>

										<td class="ownerDetails"><form:input id="ownerRelation"
												path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].proAssRelationId"
												class="form-control" disabled="true" /></td>

										<td class="mand"><form:input id="assoGuardianName"
												path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName"
												class="form-control" disabled="true" /></td>

										<td><form:input id="assoMobileno"
												path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno"
												class="hasNumber form-control" disabled="true" /></td>

										<td><form:input id="emailId"
												path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail"
												class="hasemailclass form-control preventSpace"
												disabled="true" /></td>

										<td class="ownerDetails"><form:input id="assoAddharno"
												path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoAddharno"
												class="form-control " disabled="true" /></td>
										<td class="companyDetails"><form:input id="assoPanno"
												path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno"
												class="form-control" disabled="true" /></td>
									</tr>
								</tbody>
							</table>
						</c:when>

						<c:when test="${command.getOwnershipPrefix() eq 'JO'}">

							<table id="jointOwnerTable"
								class="table text-left table-striped table-bordered">
								<tbody>
									<tr>
										<th width="17%"><spring:message
												code="ownersdetail.ownersname" /></th>
										<th width="10%"><spring:message
												code="ownersdetail.gender" /></th>
										<th width="8%"><spring:message
												code="ownerdetails.relation" /></th>
										<th width="17%"><spring:message
												code="ownersdetails.GuardianName" /></th>
										<th width="5%"><spring:message
												code="ownerdetails.PropertyShare" /></th>
										<th width="11%"><spring:message
												code="ownersdetail.mobileno" /></th>
										<th width="10%"><spring:message code="property.email" /></th>
										<th width="12%"><spring:message
												code="ownersdetail.adharno" /></th>
										<th width="10%"><spring:message
												code="ownersdetail.pancard" /></th>
									</tr>

									<c:forEach var="ownershipTypeList"
										items="${command.getProvisionalAssesmentMstDto().getProvisionalAssesmentOwnerDtlDtoList()}">
										<tr>
											<td>${ownershipTypeList.assoOwnerName}</td>
											<td>${ownershipTypeList.proAssGenderId}</td>
											<td>${ownershipTypeList.proAssRelationId}</td>
											<td>${ownershipTypeList.assoGuardianName}</td>
											<td>${ownershipTypeList.propertyShare}</td>
											<td>${ownershipTypeList.assoMobileno}</td>
											<td>${ownershipTypeList.eMail}</td>
											<td>${ownershipTypeList.assoAddharno}</td>
											<td>${ownershipTypeList.assoPanno}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</c:when>
						<c:otherwise>
							<c:set var="d" value='0' />
							<table id="companyDetailTable"
								class="table text-left table-striped table-bordered">
								<tbody>
									<tr>
										<th width="25%"><spring:message
												code="ownersdetail.ownersname" /></th>
										<th width="25%"><spring:message
												code="ownersdetail.contactpersonName" /></th>
										<th width="10%"><spring:message
												code="ownersdetail.mobileno" /></th>
										<th width="10%"><spring:message code="property.email" /></th>
										<th width="10%"><spring:message
												code="ownersdetail.pancard" /></th>
									</tr>

									<tr>
										<td><form:input id="assoOwnerName_${d}"
												path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoOwnerName"
												class="form-control" disabled="true" /></td>

										<td><form:input id="assoGuardianName_${d}"
												path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoGuardianName"
												class="form-control " disabled="true" /></td>

										<td><form:input id="assoMobileno_${d}"
												path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoMobileno"
												class="hasNumber form-control" disabled="true" /></td>

										<td><form:input id="emailId_${d}"
												path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].eMail"
												class="hasemailclass form-control preventSpace"
												disabled="true" /></td>

										<td class="companyDetails"><form:input
												id="assoPanno_${d}"
												path="provisionalAssesmentMstDto.provisionalAssesmentOwnerDtlDtoList[0].assoPanno"
												class="form-control" disabled="true" /></td>
									</tr>
								</tbody>
							</table>
						</c:otherwise>
					</c:choose>

				</div>

				<!----------------------------------------------------Property Address Details------------------------------------------->
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#propertyAddress"><spring:message
							code="property.Propertyaddress" /></a>
				</h4>
				<div class="panel-collapse collapse in" id="propertyAddress">
					<div class="form-group">
						<apptags:textArea labelCode="property.propertyaddress"
							path="provisionalAssesmentMstDto.assAddress" isDisabled="true"></apptags:textArea>
						<apptags:input labelCode="property.location"
							path="provisionalAssesmentMstDto.locationName" isDisabled="true"></apptags:input>

					</div>
					<div class="form-group">
						<apptags:input labelCode="property.pincode"
							path="provisionalAssesmentMstDto.assPincode" isDisabled="true"></apptags:input>
						<apptags:input labelCode="property.khata.no"
							path="provisionalAssesmentMstDto.tppKhataNo" isDisabled="true"></apptags:input>
					</div>
					<div class="form-group">
						<apptags:input labelCode="property.house.no"
							path="provisionalAssesmentMstDto.tppPlotNo" isDisabled="true"></apptags:input>
						<apptags:input labelCode="property.khasra.no"
							path="provisionalAssesmentMstDto.tppSurveyNumber"
							isDisabled="true"></apptags:input>
					</div>
				</div>
				<!-------------------------------------------------Property Address Details Ends------------------------------------------->

				<!----------------------------------------------------------Tax-Zone details-------------------------------->
				<h4 class="margin-top-0 margin-bottom-10 panel-title">
					<a data-toggle="collapse" href="#taxZone"><spring:message
							code="property.taxZoneDetails" /></a>
				</h4>
				<div class="panel-collapse collapse in" id="taxZone">
					<div class="panel-body">
						<div class="form-group">

							<apptags:input labelCode="property.propertyZone"
								path="provisionalAssesmentMstDto.assWardDesc1" isDisabled="true"></apptags:input>

							<c:if
								test="${command.provisionalAssesmentMstDto.assWard2 ne null}">
								<apptags:input labelCode="property.propertyWard"
									path="provisionalAssesmentMstDto.assWardDesc2"
									isDisabled="true"></apptags:input>
							</c:if>
						</div>
						<div class="form-group">
							<c:if
								test="${command.provisionalAssesmentMstDto.assWard3 ne null}">

								<apptags:input labelCode="property.propertyWard"
									path="provisionalAssesmentMstDto.assWardDesc3"
									isDisabled="true"></apptags:input>
							</c:if>
							<c:if
								test="${command.provisionalAssesmentMstDto.assWard4 ne null}">
								<apptags:input labelCode="property.propertyWard"
									path="provisionalAssesmentMstDto.assWardDesc4"
									isDisabled="true"></apptags:input>
							</c:if>
						</div>
						<div class="form-group">
							<c:if
								test="${command.provisionalAssesmentMstDto.assWard5 ne null}">
								<apptags:input labelCode="property.propertyWard"
									path="provisionalAssesmentMstDto.assWardDesc5"
									isDisabled="true"></apptags:input>
							</c:if>
						</div>
						<div class="form-group">
							<apptags:input labelCode="unitdetails.RoadType"
								path="provisionalAssesmentMstDto.proAssdRoadfactorDesc"
								isDisabled="true"></apptags:input>
						</div>

					</div>
				</div>
				<!---------------------------------------------Tax-Zone details End----------------------------------------->
				<div class="form-group">
					<label
						class="col-sm-2 control-label mandColorClass required-control"><spring:message
							code="audit.upload.document" text="Attach Documents" /> </label>
					<div class="col-sm-4">
						<apptags:formField fieldType="7" fieldPath=""
							showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
							isMandatory="true" maxFileCount="CHECKLIST_MAX_UPLOAD_COUNT"
							validnFunction="CHECK_LIST_VALIDATION_EXTENSION_PDF_DOC_XLS"
							currentCount="0">
						</apptags:formField>
					</div>
				</div>

				<div class="form-group">
					<div class="text-center padding-15 clear">
						<c:choose>
							<c:when test="${command.statusFlag eq 'I' }">
								<button type="button" class="btn btn-warning" id="active"
									onclick="confirmToProceed(this)">
									<spring:message code="propertyTax.statusUpdateActive"
										text="Active" />
								</button>
							</c:when>
							<c:otherwise>
								<button type="button" class="btn btn-warning" id="inactive"
									onclick="confirmToProceed(this)">
									<spring:message code="propertyTax.statusUpdateInactive"
										text="Inactive" />
								</button>
							</c:otherwise>
						</c:choose>
						<button type="button" class="btn btn-danger" id="back"
							onclick="backToSearch()">
							<spring:message code="bt.backBtn" text="Back"></spring:message>
						</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
