<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/sfac/fpoProfileManagement.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />



<!-- Start Content here -->

<div class="content animated top">
	<div class="widget">
		

		<div class="widget-content padding">
			<form:form id="fpoPMStorageInfo"
				action="FPOProfileManagementForm.html" method="post"
				class="form-horizontal">
				<jsp:include page="/jsp/tiles/validationerror.jsp" />
					<form:hidden path="fpmId" id="fpmId" />
				<div
					class="warning-div error-div alert alert-danger alert-dismissible"
					id="errorDiv"></div>




				<div class="panel-group accordion-toggle"
					id="accordion_single_collapse">



					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" class=""
									data-parent="#accordion_single_collapse" href="#storageInfoDiv">
									<spring:message code="sfac.fpo.pm.storageCenters"
										text="Storage Centers" />
								</a>
							</h4>
						</div>
						<div id="storageInfoDiv" class="panel-collapse collapse in">
							<div class="panel-body">
								<c:set var="d" value="0" scope="page"></c:set>
								<table
									class="table table-bordered table-striped contact-details-table"
									id="storageInfoTable">
									<thead>
										<tr>
											<th width="8%"><spring:message code="sfac.srno"
													text="Sr. No." /></th>
											<th><spring:message code="sfac.cob.commodity.name"
													text="Commodity Name" /></th>

											<th><spring:message code="sfac.fpo.pm.storageName"
													text="Storage Name" /></th>
											<th><spring:message code="sfac.fpo.pm.storageAddress"
													text="Storage Address" /></th>
											<th><spring:message code="sfac.fpo.pm.storageAdminName"
													text="Storage Admin Name" /></th>
											<th><spring:message code="sfac.contact.no"
													text="Contact No." /></th>
											<th><spring:message code="sfac.emailId"
													text="Email Id" /></th>		
											<th width="10%"><spring:message code="sfac.action"
													text="Action" /></th>
										</tr>
									</thead>
									<tbody>
										<c:choose>
											<c:when
												test="${fn:length(command.dto.strorageInfomartionDTOs)>0 }">
												<c:forEach var="dto"
													items="${command.dto.strorageInfomartionDTOs}"
													varStatus="status">
													<tr class="appendableStorageDetails">

														<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.strorageInfomartionDTOs[${d}].commodityName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="commodityName${d}" class="form-control hasCharacter" maxlength="100"/></td>
														<td><form:input
																path="dto.strorageInfomartionDTOs[${d}].storageName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageName${d}" class="form-control alphaNumeric" maxlength="100" /></td> 
																
																<td><form:input
																path="dto.strorageInfomartionDTOs[${d}].storageAddress" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageAddress${d}" class="form-control alphaNumeric" maxlength="200" /></td>
																
																<td><form:input
																path="dto.strorageInfomartionDTOs[${d}].storageAdminName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageAdminName${d}" class="form-control hasCharacter" maxlength="100" /></td>	
																
																<td><form:input cssClass="mandColorClass hasNumber form-control" disabled="${command.viewMode eq 'V' ? true : false }"
																path="dto.strorageInfomartionDTOs[${d}].contactNumber" maxlength="10"
																id="contactNumber${d}" /></td>	
																
																<td><form:input cssClass="mandColorClass hasEmail form-control" disabled="${command.viewMode eq 'V' ? true : false }"
																path="dto.strorageInfomartionDTOs[${d}].email" 
																id="email${d}"  /></td>			



														<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
															class="btn btn-blue-2 btn-sm addStorageInfoButton" 
															title='<spring:message code="sfac.fpo.add" text="Add" />'
															onclick="addStorageInfoButton(this);"> <i
																class="fa fa-plus-circle"></i></a> <a
															class='btn btn-danger btn-sm deleteStorageDetails'
															title='<spring:message code="sfac.fpo.delete" text="Delete" />'
															onclick="deleteStorageDetails(this);"> <i
																class="fa fa-trash"></i>
														</a></c:if></td>

													</tr>
													<c:set var="d" value="${d + 1}" scope="page" />
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr class="appendableStorageDetails">
																											<td align="center"><form:input path=""
																cssClass="form-control mandColorClass" id="sNo${d}"
																value="${d+1}" disabled="true" />
														<td><form:input
																path="dto.strorageInfomartionDTOs[${d}].commodityName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="commodityName${d}" class="form-control hasCharacter" maxlength="100"/></td>
														<td><form:input
																path="dto.strorageInfomartionDTOs[${d}].storageName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageName${d}" class="form-control alphaNumeric" maxlength="100" /></td>
																
																<td><form:input
																path="dto.strorageInfomartionDTOs[${d}].storageAddress" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageAddress${d}" class="form-control alphaNumeric" maxlength="200" /></td>
																
																<td><form:input
																path="dto.strorageInfomartionDTOs[${d}].storageAdminName" disabled="${command.viewMode eq 'V' ? true : false }"
																id="storageAdminName${d}" class="form-control hasCharacter" maxlength="100"/></td>	
																
																<td><form:input cssClass="mandColorClass hasNumber form-control" disabled="${command.viewMode eq 'V' ? true : false }"
																path="dto.strorageInfomartionDTOs[${d}].contactNumber" maxlength="10"
																id="contactNumber${d}" /></td>	
																
																<td><form:input cssClass="mandColorClass hasEmail form-control" disabled="${command.viewMode eq 'V' ? true : false }"
																path="dto.strorageInfomartionDTOs[${d}].email"
																id="email${d}"  /></td>	



													<td class="text-center"><c:if test="${command.viewMode ne 'V'}"><a
														class="btn btn-blue-2 btn-sm addStorageInfoButton"
														title='<spring:message code="sfac.fpo.add" text="Add" />'
														onclick="addStorageInfoButton(this);"> <i
															class="fa fa-plus-circle"></i></a> <a
														class='btn btn-danger btn-sm deleteStorageDetails'
														title='<spring:message code="sfac.fpo.delete" text="Delete" />'
														onclick="deleteStorageDetails(this);"> <i
															class="fa fa-trash"></i>
													</a></c:if></td>

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

				<div class="text-center padding-top-10">
				<c:if test="${command.viewMode ne 'V'}">
					<button type="button" class="btn btn-success"
						title='<spring:message code="sfac.savencontinue" text="Save & Continue" />'
						onclick="saveStorageInfoForm(this);">
					<spring:message code="sfac.savencontinue" text="Save & Continue" />
					</button>
					</c:if>
					<button type="button" class="btn btn-danger"
					onclick="navigateTab('creditGrant-tab','creditGrantInfo','')"
						title='<spring:message code="sfac.button.back" text="Back" />'>
					<spring:message code="sfac.button.back" text="Back" />
					</button>
				</div>

			</form:form>
		</div>
	</div>
</div>