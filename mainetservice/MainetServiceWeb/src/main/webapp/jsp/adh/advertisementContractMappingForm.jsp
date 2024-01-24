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
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/adh/advertisementContractMapping.js"></script>

<style>
.overflow-y-hidden {
	overflow-y: hidden;
}
table#hoardingDetailTable tbody tr td .chosen-container.chosen-with-drop.chosen-container-active > .chosen-drop {
	position: relative;
}
table#hoardingDetailTable tbody tr td .chosen-container ul.chosen-results li {
	 white-space: normal;
}
table#hoardingDetailTable tbody tr td select + .chosen-container {
	width: 200px !important;
}
.width-6 {
	width: 6% !important;
}
.width-8 {
	width: 8% !important;
}
.width-10 {
	width: 10% !important;
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="" text="Advertisement Contract Mapping" />
			</h2>
		</div>

		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="adh.mand" text="Field with"></spring:message><i
					class="text-red-1">*</i> <spring:message code="adh.mand.field"
						text="is mandatory"></spring:message></span>
			</div>
			<form:form action="AdvertisementContractMapping.html"
				name="AdvertisementContractMapping"
				id="AdvertisementContractMapping" class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>


				<c:if test="${command.saveMode eq 'A'}">
					<div class="form-group">
						<label class="col-sm-2 control-label"><spring:message
								code="contract.details.label.number" text="Contract Number" /></label>
						<div class="col-sm-4">
							<form:select path="contractMappingDto.contractId" id="contId"
								class="form-control chosen-select-no-results" onchange="checkDuplicateContract()"
								data-rule-required="true">
								<form:option value="0">
									<spring:message code="adh.select" text="Select"></spring:message>
								</form:option>
								<c:forEach items="${command.contractNoList}"
									var="contractNoList">
									<form:option value="${contractNoList[0]}">${contractNoList[1]}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</c:if>
				<c:if test="${command.saveMode eq 'V'}">
					<div class="table-responsive clear">
					<div class="table-responsive margin-top-10">
						<table class="table table-striped table-bordered" id="datatables">
							<thead>
								<tr>
									<th><spring:message code="contract.details.label.number"
											text="Contract Number"></spring:message></th>
									<th><spring:message code="contract.details.label.date"
											text="Contract Date"></spring:message></th>
									<th><spring:message code="contract.label.department"
											text="Department"></spring:message></th>
									<th><spring:message code="contract.label.represented.by"
											text="Represented By"></spring:message></th>
									<th><spring:message code="contract.label.vendor.name"
											text="Vendor Name"></spring:message></th>
									<th><spring:message
											code="contract.details.label.from.date"
											text="Contract From Date"></spring:message></th>
									<th><spring:message code="contract.details.label.to.date"
											text="Contract To Date"></spring:message></th>
									<th width="120"><spring:message
											code="contract.label.view.contract" text="View Contract"></spring:message></th>

								</tr>
							</thead>

							<tbody id="propertyListId">
								<c:forEach items="${command.contractMappingList}"
									var="contractMapping" varStatus="count">
									<tr>
										<td>${contractMapping.contractNo}</td>
										<td>${contractMapping.contDate}</td>
										<td>${contractMapping.deptName}</td>
										<td>${contractMapping.representedBy}</td>
										<td>${contractMapping.vendorName}</td>
										<td>${contractMapping.fromDate}</td>
										<td>${contractMapping.toDate}</td>
										<td class="text-center"><a href="javascript:void(0);" data-placement="top"
											class="btn btn-blue-2 btn-sm"
											data-original-title="View Mapping"
											onClick="showContract(${contractMapping.contId},'V')"><strong
												class="fa fa-eye"></strong><span class="hide"><spring:message
														code="rnl.master.view" text="View"></spring:message></span></a></td>

									</tr>
								</c:forEach>

							</tbody>
						</table>
					</div>
				</c:if>
				<div class="table-responsive">
					<div class="table-responsive margin-top-10 overflow-y-hidden">
						<table class="table table-striped table-condensed table-bordered"
							id="hoardingDetailTable">

							<thead>

								<tr>
									<th class="width-6"><spring:message
											code="adh.sr.no" text="Sr.No" /></th>
									<th class="width-10"><spring:message code="hoarding.label.number"
											text="Hoarding Number" /></th>
									<th><spring:message
											code="hoarding.label.hoarding.description" text="Description" /><i
										class="text-red-1">*</i></th>
									<th><spring:message
											code="hoarding.entry.label.hoarding.height" text="Height" /><i
										class="text-red-1">*</i></th>
									<th><spring:message
											code="hoarding.entry.label.hoarding.length" text="Length" /><i
										class="text-red-1">*</i></th>
									<th><spring:message code="adh.label.area" text="Area" /><i
										class="text-red-1">*</i></th>
									<th><spring:message code="adh.label.facing" text="Facing" /></th>
									<c:if test="${command.saveMode ne 'V'}">
										<th class="width-8"><spring:message
											code="adh.action" text="Action" /></th>
									</c:if>
								</tr>
							</thead>


							<c:if test="${command.saveMode eq 'A'}">
								<tbody>
									<c:set var="e" value="0" scope="page" />
									<tr>
										<td align="center"><form:input path=""
												cssClass="form-control mandColorClass" id="sequence${e}"
												value="${e+1}" disabled="true" /></td>
										<form:hidden
											path="contractMappingDto.hoardingMasterList[${e}].hoardingNumber" id="hoardingNumber"/>
										<td class="width-10"><form:select
												path="contractMappingDto.hoardingMasterList[${e}].hoardingId"
												id="hoardingNo${e}" class="form-control chosen-select-no-results"
												data-rule-required="true"
												onchange="getHoardingDetailByHoardingNo(this)"
												disabled="${command.saveMode eq 'V' ? true : false }">
												<form:option value="0">
													<spring:message code="adh.select" text="Select"></spring:message>
												</form:option>
												<c:forEach items="${command.hoardingNoList}"
													var="hoardingNo">
													<form:option value="${hoardingNo[0]}">${hoardingNo[1]}</form:option>
												</c:forEach>
											</form:select></td>


										<td><form:input
												path="contractMappingDto.hoardingMasterList[${e}].hoardingDescription"
												id="hoardingDescription${e}"
												cssClass="required-control form-control" disabled="true" /></td>

										<td><form:input
												path="contractMappingDto.hoardingMasterList[${e}].hoardingHeight"
												id="hoardingHeight${e}"
												cssClass="required-control form-control" disabled="true" /></td>
										<td><form:input
												path="contractMappingDto.hoardingMasterList[${e}].hoardingLength"
												id="hoardingLength${e}"
												cssClass="required-control form-control" disabled="true" /></td>
										<td><form:input
												path="contractMappingDto.hoardingMasterList[${e}].hoardingArea"
												id="hoardingArea${e}"
												cssClass="required-control form-control" disabled="true" /></td>
										<td><form:input
												path="contractMappingDto.hoardingMasterList[${e}].displayIdDesc"
												id="displayIdDesc${e}"
												cssClass="required-control form-control" disabled="true" /></td>

										<c:if test="${command.saveMode ne 'V'}">
											<td class="text-center">
												<a href="#" data-toggle="tooltip"
												data-placement="top" class="btn btn-blue-2  btn-sm"
												data-original-title="Add" onclick="addHoardingDetails();"><strong
													class="fa fa-plus"></strong><span class="hide"></span></a>
												<a href="#" data-toggle="tooltip"
												data-placement="top" class="btn btn-danger btn-sm"
												data-original-title="Delete"
												onclick="deleteHoardingRow($(this),'removeHoardingId');">
													<strong class="fa fa-trash"></strong> <span class="hide"><spring:message
															code="adh.delete" text="Delete" /></span>
											</a></td>
										</c:if>
									</tr>
									<c:set var="e" value="${e + 1}" scope="page" />
								</tbody>
							</c:if>


							<c:if test="${command.saveMode eq 'V'}">
								<tbody>
									<c:set var="e" value="0" scope="page" />
									<c:forEach
										items="${command.contractMappingDto.hoardingMasterList}"
										var="hoardingMasterList" varStatus="count">

										<tr>
											<td align="center"><form:input path=""
													cssClass="form-control mandColorClass" id="sequence${e}"
													value="${e+1}" disabled="true" /></td>

											<td><form:input
													path="contractMappingDto.hoardingMasterList[${e}].hoardingNumber"
													id="hoardingNo${e}"
													cssClass="required-control form-control" disabled="true" />
											</td>


											<td><form:input
													path="contractMappingDto.hoardingMasterList[${e}].hoardingDescription"
													id="hoardingDescription${e}"
													cssClass="required-control form-control" disabled="true" /></td>

											<td><form:input
													path="contractMappingDto.hoardingMasterList[${e}].hoardingHeight"
													id="hoardingHeight${e}"
													cssClass="required-control form-control" disabled="true" /></td>
											<td><form:input
													path="contractMappingDto.hoardingMasterList[${e}].hoardingLength"
													id="hoardingLength${e}"
													cssClass="required-control form-control" disabled="true" /></td>
											<td><form:input
													path="contractMappingDto.hoardingMasterList[${e}].hoardingArea"
													id="hoardingArea${e}"
													cssClass="required-control form-control" disabled="true" /></td>
											<td><form:input
													path="contractMappingDto.hoardingMasterList[${e}].displayIdDesc"
													id="displayIdDesc${e}"
													cssClass="required-control form-control" disabled="true" /></td>

											<c:if test="${command.saveMode ne 'V'}">
												<td class="text-center">
													<a href="#" data-toggle="tooltip"
													data-placement="top" class="btn btn-blue-2  btn-sm"
													data-original-title="Add" onclick="addHoardingDetails();"><strong
														class="fa fa-plus"></strong><span class="hide"></span></a>
													<a href="#" data-toggle="tooltip"
													data-placement="top" class="btn btn-danger btn-sm"
													data-original-title="Delete"
													onclick="deleteHoardingRow($(this),'removeHoardingId');">
														<strong class="fa fa-trash"></strong> <span class="hide"><spring:message
																code="adh.delete" text="Delete" /></span>
												</a></td>
											</c:if>
										</tr>
										<c:set var="e" value="${e + 1}" scope="page" />
									</c:forEach>

								</tbody>
							</c:if>
						</table>
					</div>
				</div>

				<div class="padding-top-10 text-center">
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="saveADHContractMapping(this)" id="mappingsubmit">
							<spring:message code="bt.save" />
						</button>

						<button type="Reset" class="btn btn-warning" id="resetEstate"
							onclick="addContractMapping('AdvertisementContractMapping.html','addAdvertisementContractMapping')">
							<spring:message code="bt.clear" />
						</button>
						
					</c:if>
					<input type="button" id="backBtn" class="btn btn-danger"
						onclick="window.location.href='AdvertisementContractMapping.html'"
						value="<spring:message code="bt.backBtn"/>" />
				</div>



			</form:form>
		</div>
	</div>
</div>

