<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/trade_license/inspectionDetailEntry.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>

<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="inscpection.details "
					text="Inspection Details" />
			</h2>
			<div class="additional-btn">
				<a href="#" data-toggle="tooltip" data-original-title="Help"><i
					class="fa fa-question-circle fa-lg"></i></a>
			</div>
		</div>


		<div class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="" text="" /> <i
					class="text-red-1"></i> <spring:message
						code="works.fiels.mandatory.message" text="" /></span>
			</div>
			<form:form action="InspectionDetailForm.html" class="form-horizontal"
				id="inspectionDetails" name="inspectionDetails">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;"></div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">


					<div class="panel panel-default">
						<h4 class="panel-title table" id="">
							<a data-toggle="collapse" class=""
								data-parent="#accordion_single_collapse" href="#a1"> <spring:message
									code="" text="Inspection Details" /></a>
						</h4>
						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
				
                                <div class="form-group">
								<label class="col-sm-2 control-label required-control"
									for="Date"><spring:message code="inscpection.Date" text="Date" /></label>
								<div class="col-sm-4">
									<div class="input-group ">
										<form:input class="form-control mandColorClass datepicker"
											id="inspDate" readonly="true" path="inspectionDetailDto.inspDate"></form:input>
										<span class="input-group-addon"><i
											class="fa fa-calendar"></i></span>
									</div>
								</div>
								</div>


								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="inscpection.license.no" text="License Number" /></label>
									<div class="col-sm-4">
										<form:select path="inspectionDetailDto.licNo" id="licNo"
											class="form-control mandColorClass chosen-select-no-results"
											onchange="">
											<form:option value="">
												<spring:message code='master.selectDropDwn' />
											</form:option>
											<c:forEach items="${command.tradeMasterDetailDTOList}"
												var="activeProjName">
												<form:option value="${activeProjName.trdLicno}"
													code="${activeProjName.trdLicno}">${activeProjName.trdLicno}</form:option>
											</c:forEach>
										</form:select>
									</div>

								</div>

								
														
								<%-- <div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="inscpection.no" text="Insepction No" /></label>
									<div class="col-sm-4">
										<form:input path="inspectionDetailDto.inspNo" id="inspNo"
											class="form-control mandColorClass hasNumber" value="" />
									</div>
								</div>
 --%>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="inscpector.name" text="Inspector Name" /></label>
									<div class="col-sm-4">
										<form:input path="inspectionDetailDto.inspectorName"
											id="inspectorName"
											class="form-control mandColorClass hasCharacter" value="" />
									</div>
								</div>


								<div class="form-group">
									<label class="col-sm-2 control-label required-control"><spring:message
											code="inscpection.desc" text="Description" /></label>
									<div class="col-sm-4">
										<form:textarea path="inspectionDetailDto.description" id="desc"
											class="form-control mandColorClass" value=""  />
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-sm-2 control-label"><spring:message
											code="inscpection.remark" text="Remark" /></label>
									<div class="col-sm-4">
										<form:textarea path="inspectionDetailDto.remark" id="Remark"
											class="form-control mandColorClass" value="" />
									</div>
								</div>
	
								<div class="padding-top-10 text-center">
									<button type="button" class="btn btn-success btn-submit"
										id="save" onclick="submitEntryDetails(this)">
										<spring:message code="obj.submit" />
									</button>
									
									<input type="button" class="btn btn-warning"
										value="<spring:message
										code="reset.msg" text="Reset" />"
										onclick="resetForm(this)" id="Reset" />
								</div>

							</div>
						</div>
					</div>


				</div>
			</form:form>
		</div>

	</div>
</div>
