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
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script src="js/legal/judgemaster.js"></script>
<script src="js/mainet/validation.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
<!-- Start Content here -->
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="lgl.judgemasterform"
						text="judgemasterform" /></strong>
			</h2>
		</div>

		<div id="content" class="widget-content padding">
			<div class="mand-label clearfix">
				<span><spring:message code="leadlift.master.fieldmand"
						text="Field with" /> <i class="text-red-1">*</i> <spring:message
						code="leadlift.master.ismand" text="is mandatory" /></span>
			</div>
			<!-- Start Form -->
			<form:form action="JudgeMaster.html" method="POST"
				name="judgeMasterForm" class="form-horizontal" id="judgeMasterForm"
				commandName="command">

				<jsp:include page="/jsp/tiles/validationerror.jsp" />
				<div class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv" style="display: none;">
					<i class="fa fa-plus-circle"></i>
				</div>

				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a1" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="lgl.judgeinfo" text="Judge Information" />
								</a>
							</h4>
						</div>

						<div id="a1" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:choose>
									<c:when
										test="${command.saveMode eq 'E' || command.saveMode eq 'V'}">

										<div class="form-group">
											<apptags:input labelCode="lgl.firstName"
												path="judgeMasterDto.judgeFName" isMandatory="true"
												cssClass="" 
												isDisabled="${command.saveMode eq 'V' ? true : false }" maxlegnth="100"></apptags:input>
											<apptags:input labelCode="lgl.middleName"
												path="judgeMasterDto.judgeMName" isMandatory="false" cssClass="" 
												isDisabled="${command.saveMode eq 'V' ? true : false }" maxlegnth="15"></apptags:input>
										</div>


                                       <div class="form-group">
         
                                        <apptags:input labelCode="lgl.lastName"
												path="judgeMasterDto.judgeLName" isMandatory="true" cssClass="" 
												isDisabled="${command.saveMode eq 'V' ? true : false }" maxlegnth="100"></apptags:input>
         
                                         <apptags:input labelCode="lgl.benchName"
												path="judgeMasterDto.judgeBenchName" isMandatory="true"
												isDisabled="${command.saveMode eq 'V' ? true : false }" maxlegnth="100"></apptags:input>
            
         
                                      </div>


										<div class="form-group">
											<label class="col-sm-2 control-label  "
												for="gender"><spring:message code="lgl.gender" /></label>
											<apptags:lookupField items="${command.getLevelData('GEN')}"
												path="judgeMasterDto.judgeGender" cssClass="form-control"
												selectOptionLabelCode="Select" hasId="true"
												isMandatory="false"
												disabled="${command.saveMode eq 'V' ? true : false }" />

											<%-- <apptags:date labelCode="lgl.dateOfBirth"
												fieldclass="lessthancurrdate" datePath="judgeMasterDto.judgeDob"
												isMandatory="false" cssClass="custDate mandColorClass date"
												readonly="${command.saveMode eq 'V' ? true : false }"></apptags:date> --%>
										</div>

										<%-- <div class="form-group">
											<apptags:input labelCode="lgl.phoneNumber"
												path="judgeMasterDto.judgeContactNo" cssClass="hasNumber"
												maxlegnth="10" isMandatory="false"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
											<apptags:input labelCode="lgl.email"
												path="judgeMasterDto.judgeEmail" dataRuleEmail="true"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>

										<div class="form-group">
											<apptags:input labelCode="lgl.panNumber"
												path="judgeMasterDto.judgePanNo" cssClass="hasNoSpace"
												maxlegnth="10"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
											<apptags:input labelCode="lgl.aadharNumber"
												path="judgeMasterDto.judgeAdharNo" cssClass="hasAadharNo"
												maxlegnth="10"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>

										<div class="form-group">
											<apptags:textArea labelCode="lgl.address"
												path="judgeMasterDto.judgeAddress" isMandatory="false"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:textArea>

										</div> --%>
									</c:when>
									<c:otherwise>
										<div class="form-group">
											<apptags:input labelCode="lgl.firstName"
												path="judgeMasterDto.judgeFName" isMandatory="true"
												cssClass="" maxlegnth="100"></apptags:input>
											<apptags:input labelCode="lgl.middleName"
												path="judgeMasterDto.judgeMName" isMandatory="false" cssClass=""  maxlegnth="15"></apptags:input>
										</div>

                                        <div class="form-group">
                                        
                                        <apptags:input labelCode="lgl.lastName"
												path="judgeMasterDto.judgeLName" isMandatory="true"  cssClass="" maxlegnth="100"></apptags:input>
                                        
                                        <apptags:input labelCode="lgl.benchName"
												path="judgeMasterDto.judgeBenchName" isMandatory="true" maxlegnth="100"></apptags:input>
                                        
                                        </div>
										<div class="form-group">
											<label class="col-sm-2 control-label  "
												for="gender"><spring:message code="lgl.gender" /></label>
											<apptags:lookupField items="${command.getLevelData('GEN')}"
												path="judgeMasterDto.judgeGender" cssClass="form-control"
												selectOptionLabelCode="Select" hasId="true"
												isMandatory="false" />

											<%-- <apptags:date labelCode="lgl.dateOfBirth"
												fieldclass="lessthancurrdate" datePath="judgeMasterDto.judgeDob"
												isMandatory="false"></apptags:date> --%>
										</div>

										<%-- <div class="form-group">
											<apptags:input labelCode="lgl.phoneNumber"
												path="judgeMasterDto.judgeContactNo" cssClass="hasNumber"
												maxlegnth="10" isMandatory="false"></apptags:input>
											<apptags:input labelCode="lgl.email"
												path="judgeMasterDto.judgeEmail" dataRuleEmail="true"></apptags:input>

										</div>
										<div class="form-group">
											<apptags:input labelCode="lgl.panNumber"
												path="judgeMasterDto.judgePanNo" cssClass="hasNoSpace"
												maxlegnth="10"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

											<label class="col-sm-2 control-label" for="pannumber"><spring:message
													code="lgl.panNumber" /></label>
											<div class="col-sm-4">
												<form:input type="text" name="PAN Number" id="pannumber"
													path="judgeMasterDto.judgePanNo"
													class="form-control text-uppercase hasNoSpace"
													maxLength="10" onchange="fnValidatePAN(this)" />
											</div>

											<apptags:input labelCode="lgl.aadharNumber"
												path="judgeMasterDto.judgeAdharNo" cssClass="hasAadharNo"
												maxlegnth="10"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>
										</div>

										<div class="form-group">
											<apptags:textArea labelCode="lgl.address"
												path="judgeMasterDto.judgeAddress" isMandatory="false"></apptags:textArea>

										</div> --%>
									</c:otherwise>
								</c:choose>
							</div>
						</div>


<!-- Details of PA start-->


         <div class="panel-group accordion-toggle"
					id="accordion_single_collapse">

					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-target="#a3" data-toggle="collapse" class="collapsed"
									data-parent="#accordion_single_collapse" href="#collapse1">
									<spring:message code="lgl.details.contactPerson" text="Details of PA/Registrar /Contact person" />
								</a>
							</h4>
						</div>

						<div id="a3" class="panel-collapse collapse in">
						  <div class="panel-body">

                                     <div class="form-group">

                                        <apptags:input labelCode="lgl.cpName"
												path="judgeMasterDto.contactPersonName" isMandatory="false"
												cssClass=""
												isDisabled="${command.saveMode eq 'V' ? true : false }" maxlegnth="15"></apptags:input>
										<apptags:input labelCode="lgl.phoneNumber"
									  			path="judgeMasterDto.contactPersonPhoneNo" cssClass="hasNumber"
												maxlegnth="10" isMandatory="false"
												isDisabled="${command.saveMode eq 'V' ? true : false }"></apptags:input>

                                     </div>

                                       <div class="form-group">
											
											<apptags:input labelCode="lgl.email"
												path="judgeMasterDto.contactPersonEmail" dataRuleEmail="true"
												isDisabled="${command.saveMode eq 'V' ? true : false }" maxlegnth="100"></apptags:input>
										</div>

                         </div>
                       </div>
                      </div>
           </div>              


<!-- Details of PA  end-->



						<%-- grid start--%>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 class="panel-title">
									<a data-target="#a2" data-toggle="collapse" class="collapsed"
										data-parent="#accordion_single_collapse"> <spring:message
											code="lgl.judgeinfor" text="Judge Information" />
									</a>
								</h4>
							</div>

							<div id="a2" class="panel-collapse collapse in">
								<div class="panel-body">

									<c:set var="d" value="0" scope="page"></c:set>
									<table class="table table-bordered table-striped"
										id="judgemasterTbl">
										<thead>
											<tr>
												<th scope="col" width="3%"><spring:message
														code="lgl.judgeId" text="Sr.No." /></th>
												<%-- <th scope="col" width="10%"><spring:message
														code="lgl.crtType" text="Court Type" /><span class="mand">*</span></th> --%>
												<th scope="col" width="10%"><spring:message
														code="lgl.crtName" text="Court Name" /><span class="mand" >*</span></th>
												<th scope="col" width="15%"><spring:message
														code="lgl.crtAddress" text="Court Address" /><span
													></span></th>
												<%-- <th scope="col" width="10%"><spring:message
														code="lgl.appointmentFrom" text="Appointment Form" /><span
													class="mand">*</span></th>
												<th scope="col" width="10%"><spring:message
														code="lgl.appointmentTo" text="Appointment To" /><span
													class="mand">*</span></th> --%>
												<th scope="col" width="5%"><spring:message
														code="lgl.appointmentStatus" text=" Status" /><span
													 ></span></th>
												<c:if test="${command.saveMode ne 'V'}">	 
												<th scope="col" width="8%"><spring:message
														code="lgl.action" text="Action" /></th>
												</c:if>		
											</tr>
										</thead>

										<tbody>

											<c:choose>
												<c:when
													test="${command.saveMode eq 'V' || command.saveMode eq 'E' }">

													<c:forEach var="judgeInfo"
														items="${command.judgeMasterDto.judgeDetails}"
														varStatus="status">
														<tr>
															<c:set var="disabledRow"
																value="${not empty command.judgeMasterDto.judgeDetails[d].id}"
																scope="page"></c:set>
															<c:set var="disabledToDate"
																value="${command.judgeMasterDto.judgeDetails[d].judgeStatus eq 'N'|| command.saveMode eq 'V'}"
																scope="page"></c:set>

															<td align="center"><form:input path=""
																	cssClass="form-control mandColorClass "
																	id="sequence${d}" value="${d+1}" disabled="true" /></td>
															<%-- <td align="center"><form:select
																	path="judgeMasterDto.judgeDetails[${d}].crtType"
																	cssClass="form-control" id="crtType${d}" onchange=""
																	disabled="${disabledRow}" data-rule-required="true">
																	<form:option value="">Select</form:option>
																	<c:forEach items="${command.crtTypes}" var="category">
																		<form:option value="${category.lookUpId}"
																			code="${category.lookUpCode}">${category.lookUpDesc}</form:option>
																	</c:forEach>
																</form:select></td> --%>
															<td align="center"><form:select
																	path="judgeMasterDto.judgeDetails[${d}].crtId"
																	cssClass="form-control" id="crtName${d}"
																	disabled="${disabledRow}" data-rule-required="true"
																	onchange="showCourtAddress(this)">
																	<form:option value="">Select</form:option>
																	<c:forEach items="${command.courts}" var="category">
																		<form:option value="${category.lookUpId}"
																			code="${category.lookUpCode}">${category.lookUpDesc}</form:option>
																	</c:forEach>

																</form:select></td>
															<td align="center"><form:textarea
																	path="judgeMasterDto.judgeDetails[${d}].crtAddress"
																	style="margin: 0px; height: 33px;"
																	cssClass="form-control" maxlength="4000"
																	id="crtAddress${d}" disabled="true"
																	data-rule-required="true" /></td>
															<%-- <td align="center"><form:input
																	path="judgeMasterDto.judgeDetails[${d}].fromPeriod"
																	cssClass="form-control  mandColorClass fromDateClass text-center"
																	id="fromPeriod${d}" disabled="${disabledRow}" /></td>
															<td align="center"><form:input
																	path="judgeMasterDto.judgeDetails[${d}].toPeriod"
																	cssClass="form-control  mandColorClass toDateClass text-center"
																	id="toPeriod${d}"
																	disabled="${disabledRow  && disabledToDate}" /></td> --%>
															<td align="center"><form:select
																	path="judgeMasterDto.judgeDetails[${d}].judgeStatus"
																	cssClass="form-control" id="judgeStatus${d}"
																	onchange=""
																	disabled="${disabledRow  && disabledToDate}"
																	data-rule-required="true">
																	<form:option value="">Select</form:option>
																	<form:option value="Y">Yes</form:option>
																	<form:option value="N">No</form:option>
																</form:select></td>
															
													<c:if test="${command.saveMode ne 'V'}">
														<td align="center">
														<c:if
															test="${empty command.judgeMasterDto.judgeDetails[d].id || command.saveMode eq 'E'}">
															<a href="javascript:void(0);"
																data-toggle="tooltip" data-placement="top"
																onclick="addEntryData('judgemasterTbl');"
																class="btn btn-success btn-sm"><i
																class="fa fa-plus-circle"></i></a> 
															<a href="javascript:void(0);" data-placement="top"
															class="btn btn-danger btn-sm delButton" onclick="deleteEntry('judgemasterTbl',$(this),'removedIds');"><i
															class="fa fa-minus"></i></a>
														</c:if>
														</td>
													</c:if>
															
														</tr>
														<c:set var="d" value="${d + 1}" scope="page" />
													</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass "
																id="sequence${d}" value="${d+1}" disabled="true" /></td>
														<%-- <td align="center"><form:select
																path="judgeMasterDto.judgeDetails[${d}].crtType"
																cssClass="form-control" id="crtType${d}" onchange=""
																disabled="${command.saveMode eq 'V' ? true : false }"
																data-rule-required="true">
																<form:option value="">Select</form:option>
																<c:forEach items="${command.crtTypes}" var="category">
																	<form:option value="${category.lookUpId}"
																		code="${category.lookUpCode}">${category.lookUpDesc}</form:option>
																</c:forEach>
															</form:select></td> --%>
														<td align="center"><form:select
																path="judgeMasterDto.judgeDetails[${d}].crtId"
																cssClass="form-control" id="crtName${d}"
																data-rule-required="true"
																
																disabled="${command.saveMode eq 'V' ? true : false }"
																onchange="showCourtAddress(this)">
																<form:option value="">Select</form:option>
																<c:forEach items="${command.courts}" var="category">
																	<form:option value="${category.lookUpId}"
																		code="${category.lookUpCode}">${category.lookUpDesc}</form:option>
																</c:forEach>
<!-- data-rule-required="true" for validation -->
															</form:select></td>
														<td align="center"><form:textarea
																path="judgeMasterDto.judgeDetails[${d}].crtAddress"
																style="margin: 0px; height: 33px;"
																cssClass="form-control" maxlength="4000"
																id="crtAddress${d}" disabled="true"
																data-rule-required="true" /></td>
														<%-- <td align="center"><form:input
																path="judgeMasterDto.judgeDetails[${d}].fromPeriod"
																cssClass="form-control  mandColorClass fromDateClass text-center"
																id="fromPeriod${d}"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td>
														<td align="center"><form:input
																path="judgeMasterDto.judgeDetails[${d}].toPeriod"
																cssClass="form-control  mandColorClass toDateClass text-center"
																id="toPeriod${d}"
																disabled="${command.saveMode eq 'V' ? true : false }" /></td> --%>
														<td align="center"><form:select
																path="judgeMasterDto.judgeDetails[${d}].judgeStatus"
																cssClass="form-control" id="judgeStatus${d}" onchange=""
																disabled="${command.saveMode eq 'V' ? true : false }"
																>
																<form:option value="">Select</form:option>
																<form:option value="Y">Yes</form:option>
																<form:option value="N">No</form:option>
															</form:select></td>
														<c:if test="${command.saveMode ne 'V'}">
															<td align="center">
																<a href="javascript:void(0);"
																data-toggle="tooltip" data-placement="top"
																onclick="addEntryData('judgemasterTbl');"
																class="btn btn-success btn-sm"><i
																class="fa fa-plus-circle"></i></a> 
																<a href="javascript:void(0);" data-placement="top"
																class="btn btn-danger btn-sm delButton" 
																onclick="deleteEntry('judgemasterTbl',$(this),'removedIds');"><i
																class="fa fa-minus"></i></a>
															</td>
														</c:if>
														
													</tr>
												</c:otherwise>
											</c:choose>


										</tbody>
									</table>
								</div>
							</div>
						</div>
						<%-- grid end--%>
					</div>
				</div>

				<div class="text-center clear padding-10">
					<c:if test="${command.saveMode eq 'C' || command.saveMode eq 'E'}">
						<button type="button" class="btn btn-success btn-submit"
							onclick="Proceed(this)" id="btnSave">
							<spring:message code="lgl.save" text="Save" />
						</button>
					</c:if>
					<c:if test="${command.saveMode eq 'C'}">
						<apptags:resetButton></apptags:resetButton>
					</c:if>

					<apptags:backButton url="JudgeMaster.html"></apptags:backButton>
				</div>
			</form:form>
			<!-- End Form -->
		</div>
	</div>
	<!-- End Widget Content here -->
</div>




