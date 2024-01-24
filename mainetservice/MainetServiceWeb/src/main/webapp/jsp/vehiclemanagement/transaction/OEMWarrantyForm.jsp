<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- End JSP Necessary Tags -->


<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript"
	src="js/vehicle_management/OEMWarranty.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/script-library.js"></script>
<link
	href="assets/libs/bootstrap-multiselect/css/bootstrap-multiselect.css"
	rel="stylesheet" type="text/css">
<!-- <style>
.multiselect-container {
	overflow-y: scroll;
	height: 100px;
	position: relative !important;
}

.fileUpload.fileinput.fileinput-new .fileUploadClass {
	left: 30px !important;
}
</style>
<script
	src="assets/libs/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>
<script>
	$(document).ajaxComplete(function() {
		multiselect();
	});
	function multiselect() {
		$('.multiselect-ui').multiselect({
			buttonText : function(options, select) {
				//  console.log(select[0].length);
				if (options.length === 0) {
					return 'None selected';
				}
				if (options.length === select[0].length) {
					return 'All selected (' + select[0].length + ')';
				} else if (options.length >= 1) {
					return options.length + ' selected';
				} else {
					var labels = [];
					console.log(options);
					options.each(function() {
						labels.push($(this).val());
					});
					return labels.join(', ') + '';
				}
			}

		});
	}
</script> -->

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="oem.warranty"
						text="OEM Warranty" /></strong>
				<apptags:helpDoc url="OEMWarranty.html" />
			</h2>
		</div>
		<div id="content" class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<form:form action="OEMWarranty.html" method="POST"
				name="OEMWarrantyForm" class="form-horizontal" id="OEMWarrantyForm"
				commandName="command">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed" href="#collapse1">
									<spring:message code="oem.warranty.details"
										text="OEM Warranty Details" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="form-group">
									<%-- <label class="control-label col-sm-2 required-control"
										for="department"><spring:message
											code="oem.warranty.department" text="Department" /></label>
									<div class="col-sm-4">
										<form:select path="oemWarrantyDto.department"
											cssClass="form-control" id="department"
											disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' ? true : false }"
											onchange="getVehicleTypeByDept()">
											<form:option value="">
												<spring:message code="oem.select" text="Select" />
											</form:option>
											<c:forEach items="${departments}" var="dept">
												<form:option value="${dept.dpDeptid}">${dept.dpDeptdesc}</form:option>
											</c:forEach>
										</form:select>
									</div> --%>


									<label class="control-label col-sm-2 required-control"
										for="VehicleType"><spring:message
											code="vehicle.master.vehicle.type" text="Vehicle Type" /></label>

									<apptags:lookupField items="${command.getLevelData('VCH')}"
										hasId="true" path="oemWarrantyDto.vehicleType"
										cssClass="form-control required-control" isMandatory="true"
										selectOptionLabelCode="selectdropdown"
										changeHandler="showVehicleRegNo(this,'A')"
										disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' ? true : false }" />
										
										
										<label class="col-sm-2 control-label required-control"
										for="vehicle"><spring:message
											code="oem.warranty.vehicleNumber" text="Vehicle No" /></label>
									<div class="col-sm-4">
										<form:select path="oemWarrantyDto.veId" id="veNo"
											onchange="searchVeNo()"
											class="chosen-select-no-results form-control mandColorClass "
											label="Select"
											disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' ? true : false }">
											<%-- <form:option value="0">
												<spring:message code="petrolRequisitionDTO.veNo"
													text="select" />
											</form:option> --%>
											<form:option value="0">
												<spring:message code="oem.select" text="select" />
											</form:option>
											<c:forEach items="${command.vehicleMasterList}" var="lookup">
												<form:option value="${lookup.veId}" code="${lookup.veId}">${lookup.veNo}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								<div class="form-group">

									<label class="control-label col-sm-2 required-control"
										for="Remarks"><spring:message
											code="oem.warranty.remarks" text="Remarks" /></label>
									<div class="col-sm-4">
										<form:textarea path="oemWarrantyDto.remarks" maxLength="250"
											disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' ? true : false }"
											class="form-control" id="Remarks"></form:textarea>
									</div>
								</div>

							</div>
						</div>
					</div>

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed">
									<spring:message
										code="vehicle.part.details" text="Part Details" />
								</a>
							</h4>

						</div>

						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page" />
								<table class="table table-bordered table-striped"
									id="vehicleschedulingTbl">
									<thead>
										<tr>
											<th scope="col" width="3%"><spring:message code="area.details.id"
													text="Sr.No." /></th>
											<th scope="col" width="10%"><spring:message
													code="oem.warranty.partType" text="Part Type" /><span
												class="mand">*</span></th>
											<%-- Defect #154104 --%>
											<th scope="col" width="10%"><spring:message
													code="oem.warranty.partPosition" text="Part Position" /><span class="mand">*</span>
											</th>
											<th scope="col" width="12%"><spring:message
													code="oem.warranty.partName"
													text="Part Name(In Case Part Type Other)" /></th>
											<th scope="col" width="12%"><spring:message
													code="oem.warranty.warrantyPeriod" text="Warranty Period" /><span
												class="mand">*</span></th>
											<th scope="col" width="8%"><spring:message
													code="refueling.pump.master.unit" text="Unit" /><span class="mand">*</span></th>
											<th scope="col" width="10%"><spring:message
													code="oem.warranty.purchaseDate" text="Part Purchase Date" /><span
												class="mand">*</span></th>
											<th scope="col" width="10%"><spring:message
													code="oem.warranty.lastDateOfWarranty"
													text="Last Date Of Warranty" /><span class="mand">*</span></th>

											<c:if test="${command.saveMode ne 'V'}">
												<c:if test="${command.saveMode ne 'E'}">
													<%-- Defect #154104 --%>
													<th scope="col" width="8%">
														<spring:message code="vehicle.master.vehicle.action" text="Action" />
													</th>
												</c:if>
											</c:if>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.oemWarrantyDto.tbvmoemwarrantydetails) > 0 && command.saveMode ne 'C'}">

												<c:forEach var="vehicleInfo"
													items="${command.oemWarrantyDto.tbvmoemwarrantydetails}"
													varStatus="status">

													<tr class="firstUnitRow">
														<td><form:input path="" id="sNo${d}"
																value="${d + 1}" readonly="true"
																cssClass="form-control " /> <form:hidden
																path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].oemDetId"
																id="termsId${d}" /></td>


														<td align="center"><form:select
																path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].partType"
																class="form-control mandColorClass " label="Select"
																disabled="${command.saveMode eq 'V' ? true : false }"
																id="partType${d}">
																<c:set var="baseLookupCode" value="VPT" />
																<form:option value="0">
																	<spring:message code="" text="select" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>

														<td align="center"><form:select
																path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].partPosition"
																class="form-control mandColorClass " label="Select"
																disabled="${command.saveMode eq 'V' ? true : false }"
																id="partPosition${d}">
																<c:set var="baseLookupCode" value="VPP" />
																<form:option value="0">
																	<spring:message code="" text="select" />
																</form:option>
																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>

														<td align="center"><form:input
																path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].partName"
																class="form-control hasNameClass valid" maxlength="10"
																id="partName${d}"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>

														<td align="center"><form:input
																path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].warrantyPeriod"
																cssClass="hasNumber form-control mandColorClass warPerCustCheck"
																maxlength="3" onKeyUp ="unitNumFun(this.id)" id="warrantyPeriod${d}"
																disabled="${command.saveMode eq 'V' ? true : false }" />
														</td>
														<td align="center"><form:select path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].unit" id="unit${d}"
																cssClass="form-control unitCustClass" hasId="true"
																data-rule-required="true" disabled="${command.saveMode eq 'V' ? true : false }">
																<form:option value="">
																	<spring:message code="oem.select" text="Select" />
																</form:option>
																<c:set var="baseLookupCode" value="APG" />

																<c:forEach
																	items="${command.getLevelData(baseLookupCode)}"
																	var="lookUp">
																	<form:option value="${lookUp.lookUpId}"
																		code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>

														<td align="center"><form:input
																path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].purchaseDate"
																id="purchaseDate${d}"
																class="form-control mandColorClass datepicker purdatepicker dateValidation"
																value="" readonly="false" maxLength="10"
																disabled="${command.saveMode eq 'V' ? true : false }"
																autocomplete="off" />
																</td>

														<td align="center"><form:input
																path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].lastDateOfWarranty"
																id="lastDateOfWarranty${d}"
																class="form-control mandColorClass datepicker wardatepicker dateValidation"
																placeholder="Enter Warranty Period & Part Purchase Date"
																readonly="false" maxLength="10" disabled="true"
																autocomplete="off" /></td>

														<c:if test="${command.saveMode ne 'V'}">
															<c:if test="${command.saveMode ne 'E'}">
																<td align="center">
																	<%-- Defect #154104 --%>
																	<a href="javascript:void(0);" data-toggle="tooltip"
																		data-placement="top"
																		onclick="addEntryData('vehicleschedulingTbl');"
																		class=" btn btn-success btn-sm"><i
																			class="fa fa-plus-circle"> </i></a>
																	<a class="btn btn-danger btn-sm delButton"
																	onclick="deleteEntry('vehicleschedulingTbl',$(this),'removedIds');">
																		<i class="fa fa-minus"></i>
																</a></td>
															</c:if>
														</c:if>


													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="firstUnitRow">
													<td><form:input path="" id="sNo${d}" value="1"
															readonly="true" cssClass="form-control" /></td>


													<%-- <tr class="firstUnitRow">
											<td><form:input path="" id="sNo${d+1}" value="1"
													readonly="true" cssClass="form-control" /></td> --%>
													<td align="center"><form:select
															path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].partType"
															class="form-control mandColorClass " label="Select"
															id="partType${d}">
															<c:set var="baseLookupCode" value="VPT" />
															<form:option value="0">
																<spring:message code="oem.select" text="select" />
															</form:option>
															<c:forEach
																items="${command.getLevelData(baseLookupCode)}"
																var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>

													<td align="center"><form:select
															path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].partPosition"
															class="form-control mandColorClass " label="Select"
															id="partPosition${d}">
															<c:set var="baseLookupCode" value="VPP" />
															<form:option value="0">
																<spring:message code="oem.select" text="select" />
															</form:option>
															<c:forEach
																items="${command.getLevelData(baseLookupCode)}"
																var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>

													<td align="center"><form:input
															path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].partName"
															class="form-control hasNameClass valid" maxlength="50"
															id="partName${d}" /></td>

													<td align="center"><form:input
															path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].warrantyPeriod"
															cssClass="form-control hasNumber text-right warPerCustCheck"
															maxlength="3" onKeyUp ="unitNumFun(this.id)" id="warrantyPeriod${d}" /></td>
													<td align="center"><form:select path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].unit" id="unit"
															cssClass="form-control unitCustClass" hasId="true"
															data-rule-required="true">
															<form:option value="">
																<spring:message code="oem.select" text="Select" />
															</form:option>
															<c:set var="baseLookupCode" value="APG" />

															<c:forEach
																items="${command.getLevelData(baseLookupCode)}"
																var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>

													<td align="center"><form:input
															path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].purchaseDate"
															id="purchaseDate${d}" class="form-control purdatepicker"
															value="" readonly="false" maxLength="10"
															disabled="${command.saveMode eq 'V' ? true : false }"
															autocomplete="off" /></td>

													<td align="center"><form:input
															path="oemWarrantyDto.tbvmoemwarrantydetails[${d}].lastDateOfWarranty"
															id="lastDateOfWarranty${d}"
															class="form-control mandColorClass wardatepicker"
															disabled="true" readonly="false" maxLength="10"
															autocomplete="off" /></td>



													<td align="center">
														<%-- Defect #154104 --%>
														<a href="javascript:void(0);" data-toggle="tooltip"
															data-placement="top"
															onclick="addEntryData('vehicleschedulingTbl');"
															class=" btn btn-success btn-sm"><i
																class="fa fa-plus-circle"> </i></a>
														<a class="btn btn-danger btn-sm delButton"
														onclick="deleteEntry('vehicleschedulingTbl',$(this),'removedIds');">
															<i class="fa fa-minus"></i>
													</a></td>

												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						</div>

					</div>

					
				</div>

				<div class="text-center clear padding-10">
					<%-- <c:if test="${command.saveMode eq 'V'}">
						<button type="button" class="btn btn-success btn-submit"
							title='<spring:message code="vehicle.submit" text="Submit" />'
							onclick="Proceed(this,'C')" id="btnSave">
							<spring:message code="vehicle.submit" text="Submit" />
						</button>
					</c:if> --%>
					<%-- <c:if test="${command.saveMode ne 'C'}"> --%>
					<c:if test="${command.saveMode ne 'V'}">
						<button type="button" class="btn btn-success btn-submit"
							title='<spring:message code="vehicle.submit" text="Submit" />'
							onclick="Proceed(this,'E')" id="btnSave">
							<spring:message code="vehicle.submit" text="Submit" />
						</button>
					</c:if>
					<%-- </c:if> --%>

					<c:if test="${command.saveMode ne 'V'}">
						<c:if test="${command.saveMode ne 'E'}">
							<button type="button" class="btn btn-warning"
								title='<spring:message code="lgl.reset" text="Reset" />'
								onclick="openAddOEMWarranty('OEMWarranty.html','AddOEMWarrenty');">
								<spring:message code="lgl.reset" text="Reset" />
							</button>
						</c:if>
					</c:if>
					<apptags:backButton url="OEMWarranty.html"></apptags:backButton>
				</div>
			</form:form>


		</div>
	</div>

</div>






