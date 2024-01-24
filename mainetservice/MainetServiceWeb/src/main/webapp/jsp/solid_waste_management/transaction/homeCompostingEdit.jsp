<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript"
	src="js/solid_waste_management/homeCompostingForm.js"></script>
<%
    response.setContentType("text/html; charset=utf-8");
%>
<style>
.input-group-field {
	display: table-cell;
	width: 114px;
}
</style>
<script type="text/javascript">
    
</script>
<!-- Start Content here -->
<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="swm.home.composting" />
			</h2>
		</div>
		<div class="widget-content padding ">

			<div class="mand-label clearfix">
				<span><spring:message code="population.master.mand"
						text="Field with" /><i class="text-red-1">*</i> <spring:message
						code="population.master.mand.field" text="is mandatory" /> </span>
			</div>
			<form:form action="HomeCompostingForm.html"
				name="homeCompostingTransaction" id="homeCompostingTransactionId"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />

				<form:hidden path="removeWetWaste" id="removeWetWaste" />
				<div class="error-div alert alert-danger alert-dismissible"
					id="errorDivId" style="display: none;">
					<button type="button" class="close" onclick="closeOutErrBox()"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<span id="errorId"></span>
				</div>
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="" text="Home Composting" />
								</a>
							</h4>
						</div>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<label class="control-label col-sm-2 required-control">
										<spring:message code="swm.home.name.citizen"></spring:message>
									</label>
									<div class="col-sm-4">
										<form:input path="masterDto.swName" type="text"
											class="form-control mandColorClass has" id="nameOfCitizen" />
									</div>

									<label class="control-label col-sm-2 required-control">
										<spring:message code="swm.home.composting.address"></spring:message>
									</label>
									<div class="col-sm-4">
										<form:input path="masterDto.swAddress" type="text"
											class="form-control mandColorClass" id="address" />
									</div>

								</div>
								<div class="form-group">
									<label class="control-label col-sm-2 required-control">
										<spring:message code="swm.home.composting.mobile"></spring:message>
									</label>
									<div class="col-sm-4">
										<form:input path="masterDto.swMobile" type="text"
											class="form-control mandColorClass hasMobileNo" id="mobileNo" />
									</div>

									<label class="control-label col-sm-2 required-control">
										<spring:message code="swm.home.composting.select.date"></spring:message>
									</label>
									<div class="col-sm-4">
										<form:input path="composeDate"
											cssClass="form-control mandColorClass datepicker "
											id="composeDate" />
									</div>
								</div>
								<div class="form-group">

									<apptags:lookupFieldSet baseLookupCode="SWZ" hasId="true"
										pathPrefix="masterDto.swCod" hasLookupAlphaNumericSort="true"
										hasSubLookupAlphaNumericSort="true"
										cssClass="form-control margin-bottom-10" showAll="false"
										columnWidth="20%" />

								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a2" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#a2"> <spring:message
										code="" text="Wet Waste Composting" />
								</a>
							</h4>
						</div>
						<div id="a2" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="table-responsive">
									<c:set var="d" value="0" scope="page" />
									<table id="wetWasteCompostingTable"
										class="table table-striped table-bordered appendableClass wasteDetails">
										<thead>
											<tr>
												<th width="100"><spring:message code="area.details.id"></spring:message></th>
												<th><spring:message
														code="swm.home.composting.item.name"></spring:message><i
													class="text-red-1">*</i></th>
												<th><spring:message code=""
														text="Collected Wet Waste in Kg"></spring:message></th>
												<th><spring:message
														code="swm.home.composting.compost.prepare.Kg"></spring:message></th>
												<th scope="col" width="8%"><spring:message code="solid.waste.action" text="Action" /></th>			
											</tr>
										</thead>
										<tfoot>

										</tfoot>
										<tbody>
											<c:forEach items="${command.homeComposeDetailList}"
												var="data" varStatus="index">
												<tr class="firstWetWasteRow">
													<td align="center" width="10%"><form:input path=""
															cssClass="form-control mandColorClass " id="sequence${d}"
															value="${d+1}" disabled="true" /></td>
													<form:hidden
														path="masterDto.homeComposeDetailList[${d}].swHomeCompId" />
													<td align="center"><form:select
															path="masterDto.homeComposeDetailList[${d}].swHomeComItem"
															cssClass="form-control mandColorClass" id="itemName${d}"
															onblur="checkDuplicateItemName()">
															<form:option value="0">Select</form:option>
															<c:forEach items="${command.lookupList}" var="lookUp">
																<form:option value="${lookUp.lookUpId}"
																	code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
															</c:forEach>
														</form:select></td>
													</td>

													<td><form:input type="text"
															path="masterDto.homeComposeDetailList[${d}].swHomeCompCollection"
															class="form-control text-right hasNumber"
															id="collectedWetWaste${d}"></form:input></td>
													<td><form:input type="text"
															path="masterDto.homeComposeDetailList[${d}].swHomeCompPepared"
															class="form-control text-right hasNumber"
															id="compostPrepare${d}" onchange=""></form:input></td>

													<td class="text-center">
													<a href="javascript:void(0);" data-toggle="tooltip"
														title="Add" data-placement="top"
														onclick="addEntryData('wetWasteCompostingTable');"
														class=" btn btn-success btn-sm"><i
															class="fa fa-plus-circle"></i></a>
													<a href="#" data-toggle="tooltip"
														data-placement="top" class="btn btn-danger btn-sm"
														onclick="deleteEntry($(this),'removeWetWaste');"> <strong
															class="fa fa-trash"></strong> <span class="hide"><spring:message
																	code="solid.waste.delete" text="Delete" /></span>
													</a></td>
												</tr>
												<c:set var="d" value="${d + 1}" scope="page" />
											</c:forEach>
										</tbody>
									</table>
								</div>

							</div>
						</div>
					</div>
				</div>


				<div class="text-center padding-top-20">
					<button type="button" class="btn btn-success btn-submit"
						onclick="saveHomeComposting(this)" id="submit">
						<spring:message code="solid.waste.submit" />
					</button>
					<button type="button" class="button-input btn btn-danger"
						name="button-Cancel" value="Cancel" style=""
						onclick="backHomeCompostingForm();" id="button-Cancel">
						<spring:message code="solid.waste.back" text="Back" />
					</button>

				</div>


			</form:form>
		</div>
	</div>
</div>
