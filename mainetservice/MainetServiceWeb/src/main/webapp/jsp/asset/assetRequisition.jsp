<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css">
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/asset/assetRequisition.js"></script>
<script>
	$(".datepicker").datepicker({
	    dateFormat: 'dd/mm/yy',		
		changeMonth: true,
		changeYear: true,	
	});
	</script>
	
<style>
.center {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>

<apptags:breadcrumb></apptags:breadcrumb>
<div class="content">
	<div class="widget">
		<div class="widget-header">
			<h2>
				<spring:message code="${userSession.moduleDeptCode == 'AST' ? 'asset.requisition.header':'asset.ITrequisition.header'}"/>
			</h2>
			<apptags:helpDoc url="AssetRequisition.html"></apptags:helpDoc>
		</div>

		<div class="widget-content padding">
			<form:form id="astReq" name="astReq" class="form-horizontal"
				action="AssetRequisition.html" method="post">
				<div class="compalint-error-div">
					<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>
				</div>
				<input type="hidden" id="moduleDeptUrl"  value="${userSession.moduleDeptCode == 'AST' ? 'AssetRequisition.html':'ITAssetRequisition.html'}">
				<form:input type="hidden" path="saveMode"
					value="${command.saveMode}" />
					<form:input type="hidden" path="approvalLastFlag"
					value="${command.approvalLastFlag}" />
				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse1">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse1" href="#Applicant"><spring:message
										code="asset.information.assettype" /></a>
							</h4>

						</div>

						<div id="Applicant" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="form-group">
									<c:set var="baseLookupCodeACL"
										value="${userSession.moduleDeptCode == 'AST' ? 'ACL':'ICL'}" />
									<label class="col-sm-2 control-label required-control"
										for="assetgroup"> <spring:message
											code="asset.requisition.category" />
									</label>
									<apptags:lookupField path="astRequisitionDTO.astCategory"
										disabled="${command.approvalViewFlag eq 'V'}"
										items="${command.getLevelData(baseLookupCodeACL)}"
										cssClass="form-control chosen-select-no-results"
										hasChildLookup="false" hasId="true" showAll="false"
										selectOptionLabelCode="Select" isMandatory="true" />

									<apptags:input labelCode="asset.requisition.name"
										path="astRequisitionDTO.astName" isMandatory="true"
										maxlegnth="180"
										isDisabled="${command.approvalViewFlag eq 'V'}"></apptags:input>
								</div>

								<div class="form-group">
									<%-- <apptags:textArea labelCode="asset.requisition.description"
										path="astRequisitionDTO.astDesc" isMandatory="true"
										isDisabled="${command.approvalViewFlag eq 'V' }"></apptags:textArea> --%>

									<label class="col-sm-2 control-label required-control"
										for="AssetDescription"><spring:message
											code="asset.requisition.description" text="Asset Description" /></label>
									<div class="col-sm-4">
										<form:textarea name="" cols="" rows="" class="form-control"
											id="AssetDescription" path="astRequisitionDTO.astDesc"
											maxlength="100" onkeyup="countChar(this,100,'AssetDes')"
											onfocus="countChar(this,100,'AssetDes')"
											disabled="${command.approvalViewFlag eq 'V' }"></form:textarea>
										<div class="pull-right">
										<c:if test="${command.saveMode eq 'C' }">
											<spring:message code="charcter.remain"
												text="characters remaining " />
											<span id="AssetDes">100</span>
										</c:if>
										</div>
									</div>




									<c:if test="${command.approvalViewFlag eq 'V' || command.approvalLastFlag eq 'N '}">
										<label class="col-sm-2 control-label required-control"
											for="quantity"><spring:message
												code="asset.requesting.quantity" text= "Requested Quantity"/> <span class="mand"></span></label>
									</c:if>

									<c:if
										test="${command.approvalViewFlag ne 'V' }">
										<label class="col-sm-2 control-label required-control"
											for="quantity"><spring:message
												code="asset.requisition.quantity" text="Quantity" /> <!-- <span class="mand">*</span> --></label>

									</c:if>

									<div class="col-sm-4">
										<form:input path="astRequisitionDTO.astQty" type="text"
											disabled="${command.approvalViewFlag eq 'V'}"
											class="form-control  text-left" id="astQty"
											onkeypress="return hasAmount(event, this, 6, 2)" 
											/>
										<!-- <span class="input-group-addon"><i class="fa fa-inr"></i></span> -->
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="assetgroup"> <spring:message
											code="asset.requisition.location" /></label>
									<div class="col-sm-4">
										<form:select path="astRequisitionDTO.astLoc" id="astLoc"
											disabled="${command.approvalViewFlag eq 'V'}"
											cssClass="form-control chosen-select-no-results">
											<form:option value="">
												<spring:message code="asset.info.select" />
											</form:option>
											<c:forEach items="${command.locList}" var="location">
												<form:option value="${location.locId}">${location.locNameEng}</form:option>
											</c:forEach>
										</form:select>
									</div>

									<label class="col-sm-2 control-label required-control"
										for="assetgroup"> <spring:message
											code="asset.requisition.department" /></label>

									<div class="col-sm-4">
										<form:select path="astRequisitionDTO.astDept" id="astDept"
											disabled="${command.approvalViewFlag eq 'V'}"
											cssClass="form-control chosen-select-no-results">
											<form:option value="">
												<spring:message code="asset.info.select" />
											</form:option>
											<c:forEach items="${command.departmentsList}" var="obj">
											
											<form:option value="${obj.dpDeptid}"
													code="${obj.dpDeptcode}">${obj.dpDeptdesc}</form:option>
																	
											</c:forEach>
											
										</form:select>
										
									</div>
							
								</div>
								<c:if test="${command.approvalViewFlag eq 'V'}">
								<c:if test="${command.approvalLastFlag eq 'Y' || command.lastChecker}">
								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
										for="quantity"><spring:message code="asset.totalQuantity"
											text="Total Asset Quantity" /></label>
									<div class="col-sm-4">
										<form:input path="astRequisitionDTO.astTotalQty" type="text"
											disabled="${command.approvalViewFlag eq 'V'}"
											class="form-control  text-left" id="astTotalQty"
											onkeypress="return hasAmount(event, this, 6, 2)" />
									</div>
									
									<label class="col-sm-2 control-label required-control"
										for="quantity"><spring:message code="asset.remainingQuantity"
											text="Remaining Quantity" /></label>
									<div class="col-sm-4">
										<form:input path="astRequisitionDTO.astRemainingQty" type="text"
											disabled="${command.approvalViewFlag eq 'V'}"
											class="form-control  text-left" id="astRemainingQty"
											onkeypress="return hasAmount(event, this, 6, 2)" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label required-control"
									><spring:message code="asset.employeeName" text="Employee Name" />
									</label>
									<div class="col-sm-4">
										<form:select path="astRequisitionDTO.empId" id="empIds"
											cssClass="form-control chosen-select-no-results" disabled="${command.completedFlag eq 'Y'}">
											<form:option value="">
												<spring:message code="asset.info.select" />
											</form:option>
											<c:forEach items="${command.empList}" var="obj">
												<form:option value="${obj.empId}"
													code="${obj.emploginname}">${obj.empname} ${obj.emplname}-${obj.designation.dsgname}</form:option>
											</c:forEach>
										</form:select>
									</div>

									<label class="col-sm-2 control-label required-control"
										for="quantity"><spring:message code="asset.dispactchedDate"
											text="Dispactched Date" /></label>
									<div class="col-sm-4">
										<div class="input-group">
											<form:input class="form-control datepicker"
												 maxlength="10" id="dispatchedDate" disabled="${command.completedFlag eq 'Y'}"
												path="astRequisitionDTO.dispatchedDate" isMandatory="false"></form:input>
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span>
										</div>
									</div>
								</div>
									
								
                              </c:if>
									<c:if
										test="${command.levelCheck eq '1'|| command.levelCheck eq '2'|| command.approvalLastFlag eq 'Y' }">
										<div class="form-group">
											<c:if test="${command.approvalLastFlag eq 'Y'}">
												<label class="col-sm-2 control-label required-control"
													for="quantity"><spring:message
														code="asset.dispatch.quantity" text=" Dispatch Quantity" /></label>
											</c:if>
											<c:if test="${command.approvalLastFlag ne 'Y'}">
												<label class="col-sm-2 control-label required-control"
													for="quantity"><spring:message
														code="asset.Sanctioned.Quantity"
														text=" Sanctioned Quantity" /></label>
											</c:if>

											<div class="col-sm-4">
												<form:input path="astRequisitionDTO.dispatchQuantity"
													type="text"
													disabled="${command.approvalLastFlag eq 'Y' || command.completedFlag eq 'Y'}"
													class="form-control  text-left" id="dispatchQuantity"
													onkeypress="return hasAmount(event, this, 6, 2)"
													onchange="quantityCheck()" />
											</div>


											<label class="col-sm-2 control-label required-control "
												for="quantity"><spring:message code=""
													text=" Rejected Quantity" /></label>
											<div class="col-sm-4">
												<form:input path="astRequisitionDTO.rejectedQuantity"
													type="text"
													disabled="${command.approvalLastFlag eq 'Y' || command.completedFlag eq 'Y'}"
													class="form-control  text-left" id="rejectedQuantity"
													onkeypress="return hasAmount(event, this, 6, 2)" />
											</div>



										</div>

									</c:if>



								</c:if>
                              
                              
							</div>
						</div>
					</div>
					
					<c:if test="${command.approvalViewFlag eq 'V'}">
					<c:if test="${command.approvalLastFlag eq 'Y' || command.lastChecker }">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse2" href="#Applicants"><spring:message
										text="Dispatch Details" /></a>
							</h4>

						</div>

						<div id="Applicants" class="panel-collapse collapse in">
							<div class="panel-body">
									<c:set var="d" value="0" scope="page"></c:set>
									<table class="table table-striped table-bordered"
										id="assetCodeDetail">
										<thead>
											<tr>
												<th width="40%"><spring:message code="asset.information.assetcode" text="Asset Code" /></th>
												<th width="40%"><spring:message code="asset.information.assetsrno" text="Serial Number" /></th>
												<c:if test="${command.completedFlag eq 'N' }">
												<th width="20"></th></c:if>
											</tr>
										</thead>
										<tbody>
							
										<!-- <tr class="assetCodeDetail">
 -->										
											<c:choose>

													<c:when test="${command.completedFlag eq 'Y' }">


														<c:forEach items="${command.astlist}">
															<tr class="assetCodeDetail">
																<td><form:select path="astlist[${d}].astCode"
																		cssClass="form-control chosen-select-no-results"
																		disabled="true" id="astCode${d}">
																		<form:option value="">
																			<spring:message code='asset.info.select' />
																		</form:option>
																		<c:forEach items="${command.astlist}"
																			var="astCodeinfo">
																			<form:option value="${astCodeinfo.astCode}">${astCodeinfo.astCode}</form:option>
																		</c:forEach>
																	</form:select></td>
																<td><form:select path="astlist[${d}].serialNo"
																		id="serialNo${d}" disabled="true"
																		cssClass="form-control chosen-select-no-results">
																		<form:option value="">
																			<spring:message code='asset.info.select' />
																		</form:option>
																		<c:forEach items="${command.astlist}" var="serialNo">

																			<form:option value="${serialNo.serialNo}">${serialNo.serialNo}</form:option>
																		</c:forEach>
																	</form:select></td>
																<c:set var="d" value="${d+1}" scope="page" />
															</tr>
														</c:forEach>
													</c:when>

													<c:otherwise>
													 <tr class="assetCodeDetail">
														<td><form:select
																path="astRequisitionDTO.dto[${d}].astCode"
																cssClass="form-control chosen-select-no-results"
																disabled="${command.completedFlag eq 'Y'}"
																id="astCode${d}" onchange="getSerialNo(this,${d})">
																<form:option value="">
																	<spring:message code='asset.info.select' />
																</form:option>
																<c:forEach items="${command.astCodeList}" var="lookUp">
																	<form:option value="${lookUp}">${lookUp}</form:option>
																</c:forEach>
															</form:select></td>
														<td><form:select
																path="astRequisitionDTO.dto[${d}].serialNo"
																id="serialNo${d}"
																disabled="${command.completedFlag eq 'Y'}"
																cssClass="form-control chosen-select-no-results">
																<form:option value="">
																	<spring:message code='asset.info.select' />
																</form:option>
																<c:forEach items="${command.serialNoList}"
																	var="serialNo">
																	<form:option value="${serialNo}">${serialNo}</form:option>
																</c:forEach>
															</form:select></td>
													</c:otherwise>

												</c:choose>
										
										
											
										<c:if test="${command.completedFlag eq 'N' }">
											<td align="center"><a href='#' data-toggle="tooltip" data-placement="top"
														onclick='addAssteCode();'
														class="btn btn-blue-2 btn-sm addButton"> <i
														class="fa fa-plus-circle"></i></a> <a
														href="javascript:void(0);" 
														data-toggle="tooltip" data-placement="top"
														class="btn btn-danger btn-sm delButton"><i
															class="fa fa-minus"></i></a></td>
											</c:if>
													</tr>
										</tbody>
									</table>
								
							</div>
						</div>
					</div>
				  </c:if>
				  </c:if>
				</div>

				<c:if test="${command.saveMode eq 'C' }">
				
					<div class="text-center clear padding-10">
						<button type="button" class="btn btn-success" title='<spring:message code="asset.requisition.submit" text="Submit" />'
							onclick="saveRequisition(this)">
							<spring:message code="asset.requisition.submit" text="Submit" />
						</button>
						<button type="button" id="resetForm" class="btn btn-warning" title='<spring:message code="bt.clear" text="Reset" />'>
							<spring:message code="bt.clear" text="Reset" />
						</button>

						<%-- <button type="reset" class="btn btn-warning"
							onclick="window.location.href='AssetRequisition.html'">
							<spring:message code="asset.requisition.reset" />
						</button> --%>
						<button type="button" class="button-input btn btn-danger" title='<spring:message code="asset.requisition.back" text="Back" />'
							onclick="window.location.href='${userSession.moduleDeptCode == 'AST' ? 'AssetRequisition.html':'ITAssetRequisition.html'}'">
							<spring:message code="asset.requisition.back" text="Back" />
						</button>
					</div>
				</c:if>

								
				
				
				<c:if test="${(command.levelCheck eq '1' || command.levelCheck eq '2' || command.levelCheck eq '3') && command.completedFlag eq 'N' &&  command.saveMode ne 'C'}">
					<div class="widget-content padding panel-group accordion-toggle"
						id="accordion_single_collapse1">
						<apptags:CheckerAction hideSendback="true" hideForward="true"></apptags:CheckerAction>
					</div>
				</c:if>


					<div class="text-center widget-content padding">
					
					<c:if test="${(command.levelCheck eq '1' || command.levelCheck eq '2' || command.levelCheck eq '3') && (command.completedFlag eq 'N' && command.saveMode ne 'C')}">
						<button type="button" id="save" class="btn btn-success btn-submit" title='<spring:message code="master.save" text="Save" />'
							onclick="saveDecisionAction(this);">
							<spring:message code="master.save" text="Save" />
						</button>
						</c:if>
						
					
				<c:if test="${(command.levelCheck eq '1' || command.levelCheck eq '2' || command.levelCheck eq '3') && command.completedFlag eq 'N' &&  command.saveMode ne 'C'}">	
						<button type="button" class="button-input btn btn-danger" title='<spring:message code="bt.backBtn" text="Back" />'
							name="button-Cancel" value="Cancel"
							onclick="window.location.href='AdminHome.html'"
							id="button-Cancel">
							<spring:message code="bt.backBtn" text="Back" />
						</button>
						</c:if>
						
							
				<c:if test="${command.completedFlag eq 'Y' }">	
						<button type="button" class="button-input btn btn-danger" title='<spring:message code="bt.backBtn" text="Back" />'
							name="button-Cancel" value="Cancel"
							onclick="window.location.href='AdminHome.html'"
							id="button-Cancel">
							<spring:message code="bt.backBtn" text="Back" />
						</button>
						</c:if>
					</div>
			
		
					
			
			</form:form>


		</div>

	</div>
</div>
