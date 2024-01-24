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
	src="js/solid_waste_management/populationMaster.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<!-- Start Main Page Heading -->
		<div class="widget-header">
			<h2>
				<spring:message code="population.master.heading"
					text="Population Master" />
			</h2>
			<apptags:helpDoc url="PopulationMaster.html" />
		</div>
		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="population.master.mand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="population.master.mand.field" text="is mandatory"></spring:message>
				</span>
			</div>
			<form:form action="PopulationMaster.html" name="PopulationMaster"
				id="PopulationMasterForm" class="form-horizontal">
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
					<label class="control-label col-sm-2 required-control" for="Year"><spring:message
							code="population.master.year" text="Select Year"></spring:message></label>
					<c:set var="baseLookupCode" value="CYR" />
					<c:choose>
						<c:when
							test="${command.saveMode eq 'A' || command.saveMode eq 'E' || command.saveMode eq 'R'}">
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="populationMasterDTO.popYear"
								cssClass="form-control required-control" isMandatory="true"
								selectOptionLabelCode="selectdropdown" hasId="true" />
						</c:when>
						<c:when test="${command.saveMode eq 'V'}">
							<apptags:lookupField
								items="${command.getLevelData(baseLookupCode)}"
								path="populationMasterDTO.popYear"
								cssClass="form-control required-control" isMandatory="true"
								selectOptionLabelCode="selectdropdown" hasId="true"
								disabled="true" />
						</c:when>
					</c:choose>
					<form:hidden path="populationMasterDTO.popActive" id="popActive" />
					<form:hidden path="saveMode" id="saveMode"/>
				</div>
				<div class="panel-default">
					<div class="panel-collapse collapse in" id="UnitDetail">
						<div class=" clear padding-10">
							<c:set var="d" value="0" scope="page" />
							<table id="unitDetailTable"
								class="table table-striped table-bordered appendableClass unitDetails">
								<thead>
									<tr>
										<th scope="col" width="5%"><spring:message
												code="population.master.srno" text="Sr.No." /></th>
										<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"											
											pathPrefix="populationslist[${d}].codDwzid" 
											isMandatory="true" hasLookupAlphaNumericSort="true"
											hasSubLookupAlphaNumericSort="true"
											cssClass="form-control required-control" showAll="false"
											hasTableForm="true" showData="false"/>
										<th width="8%"><spring:message
												code="population.master.population" text="Poplation Count" /></th>
										<c:choose>
											<c:when
												test="${command.saveMode eq 'A' ||  command.saveMode eq 'E' || command.saveMode eq 'R'}">		
													<th width="8%" class="text-center"><spring:message code="solid.waste.action" text="Action" /></th>
											</c:when>
										</c:choose>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${command.saveMode eq 'A'}">
											<tr class="firstUnitRow">
												<td align="center" width="5%"><form:input path=""
														cssClass="form-control mandColorClass " id="sequence"
														value="${d+1}" disabled="true" /></td>
												<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
													pathPrefix="populationslist[${d}].codDwzid"
													isMandatory="true" hasLookupAlphaNumericSort="true"
													hasSubLookupAlphaNumericSort="true"
													cssClass="form-control required-control " showAll="false"
													hasTableForm="true" showData="true" disabled="false"/>
												<td width="8%"><form:input path="populationslist[${d}].popEst"
														class="form-control mandColorClass hasNumber" id="popEstId"
														disabled="false" /></td>
												<td class="text-center" width="8%">
												<a href="javascript:void(0);" data-toggle="tooltip"
														data-original-title="Add"
														class="addCF btn btn-success btn-sm unit" id="addUnitRow"><i
															class="fa fa-plus-circle"></i>
												</a>
												<a href="#"
													class="btn btn-danger btn-sm" title="Delete"
													onclick="deleteEntry($(this),'removedIds');"> <strong
														class="fa fa-trash"></strong>
												</a></td>
											</tr>
										</c:when>
										<c:when test="${command.saveMode eq 'E' || command.saveMode eq 'R'}">
											<c:forEach items="${command.populationslist}" var="data"
												varStatus="index">
												<tr class="firstUnitRow">
													<td align="center" width="5%"><form:input path=""
															cssClass="form-control mandColorClass " id="sequence"
															value="${index.count}" disabled="true" /></td>
													<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
														pathPrefix="populationslist[${index.index}].codDwzid"
														isMandatory="true" hasLookupAlphaNumericSort="true"
														hasSubLookupAlphaNumericSort="true"
														cssClass="form-control required-control " showAll="false"
														hasTableForm="true" showData="true" disabled="false" />
													<td width="8%"><form:input
															path="populationslist[${index.index}].popEst"
															class="form-control mandColorClass hasNumber" id="popEstId"
															disabled="false" /></td>
															<td  hidden="true"><form:input
															path="populationslist[${index.index}].delete"
															class="form-control mandColorClass hasNumber" id="delete"
															disabled="false" /></td>
													<td class="text-center" width="8%">
													<a href="javascript:void(0);" data-toggle="tooltip"
															data-original-title="Add"
															class="addCF btn btn-success btn-sm unit" id="addUnitRow"><i
																class="fa fa-plus-circle"></i>
													</a>
													<a href="#"
														class="btn btn-danger btn-sm" title="Delete"
														onclick="deleteEntry($(this),'removedIds');"> <strong
															class="fa fa-trash"></strong>
													</a></td>
												</tr>
											</c:forEach>
										</c:when>
										<c:when test="${command.saveMode eq 'V'}">
											<c:forEach items="${command.populationslist}" var="data"
												varStatus="index">
												<tr class="firstUnitRow">
													<td align="center"><form:input path=""
															cssClass="form-control mandColorClass " id="sequence"
															value="${index.count}" disabled="true" /></td>
													<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
														pathPrefix="populationslist[${index.index}].codDwzid"
														isMandatory="true" hasLookupAlphaNumericSort="true"
														hasSubLookupAlphaNumericSort="true"
														cssClass="form-control required-control hasNumber" showAll="false"
														hasTableForm="true" showData="true" disabled="true"
														columnWidth="10%" />
													<td><form:input
															path="populationslist[${index.index}].popEst"
															class="form-control mandColorClass" id="popEstId"
															disabled="true" /></td>
												</tr>
											</c:forEach>
										</c:when>
									</c:choose>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="text-center padding-top-10">
					<c:choose>
						<c:when test="${command.saveMode eq 'A' || command.saveMode eq 'R'}">
							<button type="button" class="btn btn-success btn-submit" title='<spring:message code="solid.waste.submit" text="Submit" />'
								onclick="savePopulationMasterForm(this);">
								<spring:message code="solid.waste.submit" text="Submit"></spring:message>
							</button>
							<button type="Reset" class="btn btn-warning" title='<spring:message code="solid.waste.reset" text="Reset" />'
								onclick="ResetForm(this)">
								<spring:message code="solid.waste.reset" text="Reset"></spring:message>
							</button>
							<button type="button" class="button-input btn btn-danger" title='<spring:message code="solid.waste.back" text="Back" />'
								name="button-Cancel" value="Cancel" style=""
								onclick="backPopulationMasterForm();" id="button-Cancel">
								<spring:message code="solid.waste.back" text="Back" />
							</button>
						</c:when>
						<c:when test="${command.saveMode eq 'E'}">
							<button type="button" class="btn btn-success btn-submit" title='<spring:message code="solid.waste.submit" text="Submit" />'
								onclick="savePopulationMasterForm(this);">
								<spring:message code="solid.waste.submit" text="Submit"></spring:message>
							</button>
							<button type="button" class="button-input btn btn-danger" title='<spring:message code="solid.waste.back" text="Back" />'
								name="button-Cancel" value="Cancel" style=""
								onclick="backPopulationMasterForm();" id="button-Cancel">
								<spring:message code="solid.waste.back" text="Back" />
							</button>
						</c:when>
						<c:when test="${command.saveMode eq 'V'}">
							<button type="button" class="button-input btn btn-danger" title='<spring:message code="solid.waste.back" text="Back" />'
								name="button-Cancel" value="Cancel" style=""
								onclick="backPopulationMasterForm();" id="button-Cancel">
								<spring:message code="solid.waste.back" text="Back" />
							</button>
						</c:when>
					</c:choose>
				</div>
			</form:form>
		</div>
	</div>
</div>