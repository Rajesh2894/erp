<!-- Start JSP Necessary Tags -->
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript"
	src="js/works_management/reports/completionCertificate.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<%
    response.setContentType("text/html; charset=utf-8");
%>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">

			<h2>
				<spring:message code="completion.certificate"
					text="Completion Certificate" />
			</h2>
			<div class="additional-btn">
			 <apptags:helpDoc url="CompletionCertificate.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="CompletionCertificate.html"
				class="form-horizontal" id="completionCertificate"
				name="completionCertificate">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>
				<div class="form-group">
					<label class="col-sm-2 control-label "><spring:message
							code="project.master.projname" /></label>
					<div class="col-sm-4">
						<form:select path="contractCompletionDto.projId" id="projId"
							class="form-control chosen-select-no-results"
							onchange="getWorkName(this,'N');">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
							<c:forEach items="${command.projectMasterList}"
								var="activeProjName">
								<form:option value="${activeProjName.projId }"
									code="${activeProjName.projId }">${activeProjName.projNameEng}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-sm-2 control-label"><spring:message
							code="work.def.workname" /></label>
					<div class="col-sm-4">
						<form:select path="contractCompletionDto.workId" id="workId"
							class="form-control chosen-select-no-results"
							onchange="getWorkDetails(this);">
							<form:option value="">
								<spring:message code='work.management.select' />
							</form:option>
						</form:select>
					</div>
				</div>

				<!-- REMOVE AS PER SUDA UAT -->


				<div class="form-group">
					<label class="col-sm-2 "><spring:message
							code="work.order.contract.no" text="" /></label>
					<div class="col-sm-4">
						<form:input path="contractCompletionDto.contractNo"
							Class="form-control" id="contractNo" readonly="true" />
					</div>
					<label class="col-sm-2 "><spring:message
							code="mb.ContractorName" text="" /></label>
					<div class="col-sm-4">
						<form:input path="contractCompletionDto.contractorName"
							Class="form-control" id="contractorName" readonly="true" />
					</div>

				</div>
				<div class="form-group">
					<label class="col-sm-2 "><spring:message
							code="mb.contract.StartDate" text="" /></label>
					<div class="col-sm-4">
						<form:input path="contractCompletionDto.contractStartDate"
							Class="form-control" id="contractStartDate" readonly="true" />
					</div>
					<label class="col-sm-2 "><spring:message
							code="mb.contract.EndDate" text="" /></label>
					<div class="col-sm-4">
						<form:input path="contractCompletionDto.contractEndDate"
							Class="form-control" id="contractEndDate" readonly="true" />
					</div>

				</div>
				<div class="form-group">
					<label for="" class="col-sm-2 "><spring:message
							code="work.def.workType" text="Type of Work" /> </label>
					<div class="col-sm-4">
						<form:input path="contractCompletionDto.workTypeDesc"
							Class="form-control " id="workType" readonly="true" />
					</div>
				</div>


				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="work.def.asset.info" text="" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="form-group">

									<label for="" class="col-sm-2 control-label required-control"><spring:message
											code="wms.AssetClass" text="Asset Class" /> </label>
									<div class="col-sm-4">
										<form:input path="contractCompletionDto.assetCategory"
											cssClass="form-control" readonly="true" id="assetCategory" />
									</div>
									<label for="" class="col-sm-2 control-label required-control"><spring:message
											code="work.def.asset.category" text="Asset Category" /> </label>
									<div class="col-sm-4">
										<%-- <c:set var="baseLookupCode" value="ASC" />
										<form:select path="astDet.assetInformationDTO.assetClass2"
											class="form-control chosen-select-no-results" id="assetCat">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select> --%>
										<form:input path="" cssClass="form-control" value="Immovable"
											readonly="true" />
									</div>
								</div>


								<div class="form-group">
									<label for="" class="col-sm-2 required-control"><spring:message
											code="work.def.asset.name" text="Asset Name" /> </label>
									<div class="col-sm-4">
										<form:input path="astDet.assetInformationDTO.assetName"
											Class="form-control " id="assetName" />
									</div>

									<label for="" class="col-sm-2 required-control"><spring:message
											code="wms.AssetDescription" text="Asset Description" /> </label>
									<div class="col-sm-4">
										<form:input path="astDet.assetInformationDTO.details"
											Class="form-control " id="assetDescription" maxlength="" />
									</div>
								</div>
								<div class="form-group">
									<apptags:textArea labelCode="wms.Purpose"
										path="astDet.assetInformationDTO.purpose" cssClass=""
										maxlegnth="250" isMandatory="true"></apptags:textArea>
									<%-- <label class="col-sm-2 control-label required-control"><spring:message
											code="mb.completionDate" text="" /></label>
									<div class="col-sm-4">
										<form:input path="contractCompletionDto.completionDate"
											Class="form-control  mandColorClass" id=""
											data-rule-required="" />
									</div> --%>

								</div>
								<div class="form-group">
									<label class="col-sm-2 "><spring:message
											code="work.estimate.report.length" text="Length" /></label>
									<div class="col-sm-4">
										<form:input
											path="astDet.assetInformationDTO.assetSpecificationDTO.length"
											Class="form-control decimal text-right"
											onkeypress="return hasAmount(event, this, 5, 2)"
											onchange="getAmountFormatInDynamic((this),'length')"
											id="length" />
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="work.estimate.report.breadth" text="Breadth" /></label>
									<div class="col-sm-4">
										<form:input
											path="astDet.assetInformationDTO.assetSpecificationDTO.breadth"
											Class="form-control decimal text-right"
											onkeypress="return hasAmount(event, this, 5, 2)"
											onchange="getAmountFormatInDynamic((this),'breadth')"
											id="breadth" />
									</div>

								</div>

								<div class="form-group">

									<label class="col-sm-2 control-label"><spring:message
											code="wms.Width" text="Width" /></label>
									<div class="col-sm-4">
										<form:input
											path="astDet.assetInformationDTO.assetSpecificationDTO.width"
											Class="form-control decimal text-right"
											onkeypress="return hasAmount(event, this, 5, 2)"
											onchange="getAmountFormatInDynamic((this),'width')"
											id="width" />
									</div>
									<label class="col-sm-2 "><spring:message
											code="wms.HeightDiameter" text="Height/Diameter" /></label>
									<div class="col-sm-4">
										<form:input
											path="astDet.assetInformationDTO.assetSpecificationDTO.height"
											Class="form-control decimal text-right"
											onkeypress="return hasAmount(event, this, 5, 2)"
											onchange="getAmountFormatInDynamic((this),'height')"
											id="height" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 "><spring:message
											code="wms.Nooffloor" text="No. of floor" /></label>
									<div class="col-sm-4">
										<form:input
											path="astDet.assetInformationDTO.assetSpecificationDTO.noOfFloor"
											Class="form-control text-right hasNumber" id="noOfFloor" />
									</div>
									<label class="col-sm-2 control-label"><spring:message
											code="wms.CarpetArea" text="Carpet Area" /></label>
									<div class="col-sm-4">
										<form:input
											path="astDet.assetInformationDTO.assetSpecificationDTO.carpet"
											Class="form-control text-right decimal"
											onkeypress="return hasAmount(event, this, 5, 2)"
											onchange="getAmountFormatInDynamic((this),'carpetArea')"
											id="carpetArea" />
									</div>

								</div>

								<div class="form-group">
									<label class="col-sm-2 required-control"><spring:message
											code="wms.AcquisitionDate" text="Acquisition Date" /></label>
									<div class="col-sm-4">
										<form:input
											path="astDet.assetPurchaseInformationDTO.dateOfAcquisition"
											Class="form-control  mandColorClass datepicker "
											id="acquisitionDate" data-rule-required="true"
											readonly="true" />
									</div>
									<label class="col-sm-2 control-label required-control"><spring:message
											code="wms.completionDate" text="Completion Date" /></label>
									<div class="col-sm-4">
										<form:input path="astDet.assetInformationDTO.creationDate"
											Class="form-control  mandColorClass datepicker "
											id="completionDate" data-rule-required="true" readonly="true" />
									</div>

								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="text-center margin-top-10">
					<button class="btn btn-success  search" title='<spring:message code="works.management.save" text="Save" />'
						onclick="saveCompletionForm(this);" type="button">
						<spring:message code="works.management.save" text="Save" />
					</button>
					<button class="btn btn-warning  reset" title='<spring:message code="works.management.reset" text="Reset" />'
						onclick="getCompletionForm();" type="reset">
						<spring:message code="works.management.reset" text="Reset" />
					</button>
					<button class="btn btn-danger" title='<spring:message code="works.management.back" text="Back" />'
						onclick="window.location.href='CompletionCertificate.html'"
						type="button">
						<spring:message code="works.management.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>
