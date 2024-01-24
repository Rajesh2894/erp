<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/works_management/schemeMaster.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="scheme.master.title" text="" />
			</h2>
			<div class="additional-btn">
				    <apptags:helpDoc url="WmsSchemeMaster.html"></apptags:helpDoc>

			</div>
		</div>
		<div class="widget-content padding">

			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1">*</i> <spring:message code="" text="" /></span>
			</div>
			<form:form action="WmsSchemeMaster.html" class="form-horizontal"
				name="wmsSchemeMaster" id="wmsSchemeMaster">
				<!-- Start Validation include tag -->
				<form:hidden path="saveMode" id="saveMode" />
				<form:hidden path="cpdMode" id="cpdMode" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a1"><spring:message
										code="wms.SourceofFund" text="Source of Fund" /></a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">

									<label class="col-sm-2 control-label "><spring:message
											code="wms.SourceofFund" text="Source of Fund" /></label>
									<div class="col-sm-4">
										<form:select path="schemeMasterDTO.wmSchCodeId1"
											class="form-control chosen-select-no-results" id="sourceCode"
											onchange="getSchemeDetails(this);" disabled="true">
											<form:option value="">
												<spring:message code='work.management.select' />
											</form:option>
											<c:forEach items="${command.sourceLookUps}" var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>

									<label class="col-sm-2 control-label "><spring:message
											code="scheme.master.select.scheme.name"
											text="Select Scheme Name" /></label>
									<div class="col-sm-4">
										<form:select path="schemeMasterDTO.wmSchCodeId2"
											id="sourceName" class="form-control chosen-select-no-results"
											onchange="getSchemeDetailNames(this);" disabled="true">
											<c:if test="${command.saveMode eq 'C'}">
												<form:option value="">
													<spring:message code='work.management.select' />
												</form:option>
											</c:if>
											<c:if test="${command.saveMode ne 'C'}">
												<c:forEach items="${command.schemeLookUps}" var="lookUp">
													<form:option value="${lookUp.lookUpId}"
														code="${lookUp.lookUpCode}">${lookUp.lookUpCode} -- ${lookUp.lookUpDesc}</form:option>
												</c:forEach>
											</c:if>
										</form:select>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 type="h4" class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse2"><spring:message
										code="" text="Scheme Master" /></a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<apptags:textArea labelCode="scheme.master.schemename.eng"
										path="schemeMasterDTO.wmSchNameEng" isReadonly="true"></apptags:textArea>
									<apptags:textArea labelCode="scheme.master.schemename.reg"
										path="schemeMasterDTO.wmSchNameReg" isReadonly="true"></apptags:textArea>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="scheme.master.fund"></spring:message></label>
									<c:choose>
										<c:when test="${command.cpdMode eq 'L'}">
											<div class="col-sm-4">
												<form:select path="schemeMasterDTO.wmSchFund"
													cssClass="form-control" id="wmSchFund" disabled="true">
													<form:option value="">
														<spring:message code='work.management.select' />
													</form:option>
													<c:forEach items="${command.fundList}" var="fundMaster">
														<form:option value="${fundMaster.key}"
															code="${fundMaster.key}">${fundMaster.value}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-sm-4">
												<form:input path="schemeMasterDTO.schFundName"
													cssClass="form-control" readonly="true" />
											</div>
										</c:otherwise>
									</c:choose>
									<apptags:textArea labelCode="work.management.description"
										path="schemeMasterDTO.wmSchDesc" cssClass="" isReadonly="true"></apptags:textArea>
								</div>
							</div>
						</div>
					</div>
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;"></div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 type=h4 class="panel-title">
								<a data-toggle="" class=""
									data-parent="#accordion_single_collapse" href="#a3"><spring:message
										code="scheme.master.funding.pattern" text="" /></a>
							</h4>
						</div>
						<div id="a3" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="table-responsive">
									<c:set var="d" value="0" scope="page"></c:set>
									<table class="table table-bordered table-striped"
										id="schemeMstList">
										<tr>
											<th width=""><spring:message code="scheme.master.srno"
													text="" /><input type="hidden" id="srNo"></th>
											<th width="60%"><spring:message
													code="scheme.master.sponsoredby" text="" /></th>
											<th><spring:message code="scheme.master.sharepercent"
													text="" /></th>
											<th class="text-center" width="80"><button type="button"
													onclick="return false;"
													class="btn btn-blue-2 btn-sm  addSchemeMstList">
													<i class="fa fa-plus-circle"></i>
												</button></th>
										</tr>
										<c:choose>
											<c:when
												test="${fn:length(command.schemeMasterDTO.mastDetailsDTO) > 0}">

												<c:forEach var="schemeListData"
													items="${command.schemeMasterDTO.mastDetailsDTO}"
													varStatus="status">
													<tr class="appendableClass">
														<form:hidden
															path="schemeMasterDTO.mastDetailsDTO[${d}].schDetId"
															id="schDetId${d}" />
														<form:hidden
															path="schemeMasterDTO.mastDetailsDTO[${d}].schActiveFlag"
															id="schActiveFlag${d}" value="A" />

														<td></td>
														<td><form:select
																path="schemeMasterDTO.mastDetailsDTO[${d}].schDSpon"
																class=" form-control " id="schDSpon${d}">
																<form:option value="">
																	<spring:message code='work.management.select' />
																</form:option>
																<c:forEach items="${command.getLevelData('SBY')}"
																	var="lookup">
																	<form:option value="${lookup.lookUpId}"
																		code="${lookup.lookUpCode}">${lookup.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:input
																path="schemeMasterDTO.mastDetailsDTO[${d}].schSharPer"
																cssClass="form-control  text-right"
																onkeypress="return hasAmount(event, this, 3, 2)"
																onchange="getAmountFormatInDynamic((this),'schSharPer')"
																placeholder="000.00" id="schSharPer${d}" /></td>
														<td class="text-center"><a href='#'
															onclick='return false;'
															class='btn btn-danger btn-sm deleteSchemeLink'><i
																class="fa fa-trash"></i></a></td>
													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>

											<c:otherwise>

												<tr class="appendableClass">
													<form:hidden
														path="schemeMasterDTO.mastDetailsDTO[${d}].schDetId"
														id="schDetId${d}" />
													<form:hidden
														path="schemeMasterDTO.mastDetailsDTO[${d}].schActiveFlag"
														id="schActiveFlag${d}" value="A" />
													<td></td>
													<td><form:select
															path="schemeMasterDTO.mastDetailsDTO[${d}].schDSpon"
															class=" form-control " id="schDSpon${d}">
															<form:option value="">
																<spring:message code='work.management.select' />
															</form:option>
															<c:forEach items="${command.getLevelData('SBY')}"
																var="lookup">
																<form:option value="${lookup.lookUpId}"
																	code="${lookup.lookUpCode}">${lookup.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>
													<td><form:input
															path="schemeMasterDTO.mastDetailsDTO[${d}].schSharPer"
															cssClass="form-control  text-right"
															onkeypress="return hasAmount(event, this, 3, 2)"
															onchange="getAmountFormatInDynamic((this),'schSharPer')"
															placeholder="000.00" id="schSharPer${d}" /></td>
													<td class="text-center"><a href='#'
														onclick='return false;'
														class='btn btn-danger btn-sm deleteSchemeLink'><i
															class="fa fa-trash"></i></a></td>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:otherwise>
										</c:choose>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="schemeProjectView">
							<thead>
								<tr>
									<th width="10%" align="center"><spring:message
											code="ser.no" text="Sr.No" /></th>
									<th width="20%" align="center"><spring:message
											code="project.master.projname" text="" /></th>
									<th width="20%" align="center"><spring:message
											code="project.master.projdesc" text="" /></th>
									<th width="20%" align="center"><spring:message
											code="project.master.startdate" text="" /></th>
									<th width="20%" align="center"><spring:message
											code="project.master.enddate" text="" /></th>
									<th width="10%" align="center"><spring:message
											code="works.management.action" text="Action" /></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${command.projectMasterDtos}"
									var="projectMasterDto" varStatus="status">
									<tr>
										<td>${status.count}</td>
										<td>${projectMasterDto.projNameEng}</td>
										<td>${projectMasterDto.projDescription}</td>
										<td align="center">${projectMasterDto.startDateDesc}</td>
										<td align="center">${projectMasterDto.endDateDesc}</td>

										<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												onClick="viewProject(${projectMasterDto.projId})"
												title="View">
												<i class="fa fa-eye"></i>
											</button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="text-center clear padding-10">
						<button type="button" class="button-input btn btn-danger"
							name="button-Cancel" value="Cancel" style=""
							onclick="backProjectMasterForm();" id="button-Cancel">
							<i class="fa fa-chevron-circle-left padding-right-5"></i>
							<spring:message code="works.management.back" text="" />
						</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>







