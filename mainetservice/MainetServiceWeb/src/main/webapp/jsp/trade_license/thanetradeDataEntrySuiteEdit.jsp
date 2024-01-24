<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	response.setContentType("text/html; charset=utf-8");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>

<script type="text/javascript" src="js/trade_license/thaneTradeDataEntrySuite.js"></script>


<c:choose>

	<c:when test="${command.ownershipPrefix eq 'O'}">

		<c:set var="d" value="0" scope="page"></c:set>
		<table class="table table-striped table-bordered" id="ownerDetail">
			<thead>
				<tr>
					<th width="8%"><spring:message code="owner.details.title"></spring:message></th>
					<th width="15%"><spring:message code="owner.details.name"></spring:message><span
						class="mand">*</span></th>
					<th width="10%"><spring:message
							code="owner.details.father/husband.name"></spring:message></th>
					<th width="8%"><spring:message code="owner.details.gender"></spring:message><span
						class="mand">*</span></th>
					<th width="15%"><spring:message code="owner.details.address"></spring:message><span
						class="mand">*</span></th>
					<th width="10%"><spring:message code="owner.details.mobileNo"></spring:message></th>
					<th width="10%"><spring:message code="owner.details.emailid"></spring:message></th>
					<th width="10%"><spring:message code="owner.details.adharno"></spring:message></th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when
						test="${fn:length(command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO)>0 }">
						<c:forEach var="taxData"
							items="${command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO}"
							varStatus="status">
							<tr class="appendableClass">
								<td><c:set var="baseLookupCode" value="TTL" /> <form:select
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troTitle"
										onchange="" class="form-control mandColorClass"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troTitle${d}" data-rule-required="true">
										<form:option value="">
											<spring:message code="trade.sel.optn.gender" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troName"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasCharacter"
										id="troName${d}" /></td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMname"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasCharacter"
										id="troMname${d}" /></td>
								<td>

									<div>

										<c:set var="baseLookupCode" value="GEN" />
										<form:select
											path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troGen"
											onchange="" class="form-control mandColorClass"
											disabled="${command.viewMode eq 'V' ? true : false }"
											id="troGen${d}" data-rule-required="true">
											<form:option value="">
												<spring:message code="trade.sel.optn.gender" />
											</form:option>
											<c:forEach items="${command.getLevelData(baseLookupCode)}"
												var="lookUp">
												<form:option value="${lookUp.lookUpCode}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select>
									</div>


								</td>
								<td>
									<%-- <form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
									type="text" disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control"
									id="troAddress${d}" data-rule-required="true" />  --%> <form:textarea
										class="form-control mandColorClass"
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troAddress${d}" data-rule-required="true"></form:textarea>


								</td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMobileno"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasMobileNo"
										id="troMobileno${d}" data-rule-required="true" /></td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troEmailid"
										type="text"
										class="form-control unit required-control hasemailclass"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troEmailid${d}" data-rule-email="true" maxLength="50" /></td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAdhno"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasAadharNo"
										data-rule-maxlength="12" data-rule-minlength="12"
										id="troAdhno${d}" maxlegnth="12" /></td>
								
					
							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="appendableClass">
							<td><c:set var="baseLookupCode" value="TTL" /> <form:select
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troTitle"
									onchange="" class="form-control mandColorClass"
									disabled="${command.viewMode eq 'V' ? true : false }"
									id="troTitle${d}" data-rule-required="true">
									<form:option value="">
										<spring:message code="trade.sel.optn.gender" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select></td>

							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troName"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasCharacter"
									id="troName${d}" /></td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMname"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasCharacter"
									id="troMname${d}" /></td>
							<td>

								<div>

									<c:set var="baseLookupCode" value="GEN" />
									<form:select
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troGen"
										onchange="" class="form-control mandColorClass"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troGen${d}" data-rule-required="true">
										<form:option value="">
											<spring:message code="trade.sel.optn.gender" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpCode}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select>
								</div>


							</td>
							<td>
								<%-- <form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control" id="troAddress${d}"
										data-rule-required="true" /> --%> <form:textarea
									class="form-control mandColorClass"
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									id="troAddress${d}" data-rule-required="true"></form:textarea>

							</td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMobileno"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasMobileNo"
									id="troMobileno${d}" data-rule-required="true" /></td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troEmailid"
									type="text"
									class="form-control unit required-control hasemailclass"
									disabled="${command.viewMode eq 'V' ? true : false }"
									id="troEmailid${d}" data-rule-email="true" maxLength="50" /></td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAdhno"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasAadharNo"
									data-rule-maxlength="12" data-rule-minlength="12"
									id="troAdhno${d}" maxlegnth="12" /></td>
							
							
							</tr>
						<c:set var="d" value="${d + 1}" scope="page" />
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>

		<c:set var="d" value="0" scope="page"></c:set>
		<table class="table table-striped table-bordered" id="ownerDetail">
			<thead>
				<tr>
					<th width="8%"><spring:message code="owner.details.title" /></th>
					<c:choose>
					<c:when test="${command.ownershipPrefix eq 'R'}">
					<th width="15%"><spring:message code="" text="Tenant Name"></spring:message><span
						class="mand">*</span></th>
					</c:when>
					<c:otherwise>
					
					<th width="15%"><spring:message code="owner.details.name"></spring:message><span
						class="mand">*</span></th>
					</c:otherwise>
					</c:choose>
					<th width="10%"><spring:message
							code="owner.details.father/husband.name"></spring:message></th>
					<th width="8%"><spring:message code="owner.details.gender"></spring:message><span
						class="mand">*</span></th>
					<th width="10%"><spring:message code="owner.details.address"></spring:message><span
						class="mand">*</span></th>
					<th width="10%"><spring:message code="owner.details.mobileNo"></spring:message></th>
					<th width="15%"><spring:message code="owner.details.emailid"></spring:message></th>
					<th width="15%"><spring:message code="owner.details.adharno"></spring:message></th>

					<c:if test="${command.viewMode ne 'V'}">
					<th colspan="2"><a href="javascript:void(0);" title="Add"
						class=" addCF btn btn-success btn-sm" id="addUnitRow"> <i
							class="fa fa-plus-circle"></i></a></th></c:if>
							
	
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when
						test="${fn:length(command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO)>0 }">
						<c:forEach var="taxData"
							items="${command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO}"
							varStatus="status">
							<tr class="appendableClass">

								<td><c:set var="baseLookupCode" value="TTL" /> <form:select
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troTitle"
										onchange="" class="form-control mandColorClass"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troTitle${d}" data-rule-required="">
										<form:option value="">
											<spring:message code="trade.sel.optn.gender" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>


								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troName"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasCharacter"
										id="troName${d}" data-rule-required="true" /></td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMname"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasCharacter"
										id="troMname${d}" /></td>
								<td><c:set var="baseLookupCode" value="GEN" /> <form:select
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troGen"
										onchange="" class="form-control mandColorClass"
										id="troGen${d}"
										disabled="${command.viewMode eq 'V' ? true : false }"
										data-rule-required="true">
										<form:option value="">
											<spring:message code="trade.sel.optn.gender" />
										</form:option>
										<c:forEach items="${command.getLevelData(baseLookupCode)}"
											var="lookUp">
											<form:option value="${lookUp.lookUpCode}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>
								<td>
									<%-- <form:input
											path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
											type="text"
											disabled="${command.viewMode eq 'V' ? true : false }"
											class="form-control unit required-control"
											id="troAddress${d}" data-rule-required="true" /> --%> <form:textarea
										class="form-control mandColorClass"
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troAddress${d}" data-rule-required="true"></form:textarea>

								</td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMobileno"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasMobileNo"
										id="troMobileno${d}" data-rule-required="true" /></td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troEmailid"
										type="text"
										class="form-control unit required-control hasemailclass"
										disabled="${command.viewMode eq 'V' ? true : false }"
										id="troEmailid${d}" data-rule-email="true" maxLength="50" /></td>
								<td><form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAdhno"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control hasAadharNo"
										data-rule-maxlength="12" data-rule-minlength="12"
										id="troAdhno${d}" maxlegnth="12" /></td>
								
								
								<c:if test="${command.viewMode ne 'V'}">
								<td class="text-center"><a href="javascript:void(0);"
									class="btn btn-danger btn-sm delButton" ><i
										class="fa fa-minus" id=""></i></a></td></c:if>


							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
						</c:forEach>
					</c:when>
					<c:otherwise>

						<tr class="appendableClass">

							<td><c:set var="baseLookupCode" value="TTL" /> <form:select
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troTitle"
									onchange="" class="form-control mandColorClass"
									id="troTitle${d}" data-rule-required="">
									<form:option value="">
										<spring:message code="trade.sel.optn.gender" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select></td>


							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troName"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasCharacter"
									id="troName${d}" data-rule-required="true" /></td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMname"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasCharacter"
									id="troMname${d}" /></td>
							<td><c:set var="baseLookupCode" value="GEN" /> <form:select
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troGen"
									disabled="${command.viewMode eq 'V' ? true : false }"
									onchange="" class="form-control mandColorClass" id="troGen${d}"
									data-rule-required="true">
									<form:option value="">
										<spring:message code="trade.sel.optn.gender" />
									</form:option>
									<c:forEach items="${command.getLevelData(baseLookupCode)}"
										var="lookUp">
										<form:option value="${lookUp.lookUpCode}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select></td>
							<td>
								<%-- <form:input
										path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
										type="text"
										disabled="${command.viewMode eq 'V' ? true : false }"
										class="form-control unit required-control" id="troAddress${d}"
										data-rule-required="true" /> --%> <form:textarea
									class="form-control mandColorClass"
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAddress"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									id="troAddress${d}" data-rule-required="true"></form:textarea>


							</td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troMobileno"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasMobileNo"
									id="troMobileno${d}" data-rule-required="true" /></td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troEmailid"
									type="text"
									class="form-control unit required-control hasemailclass"
									disabled="${command.viewMode eq 'V' ? true : false }"
									id="troEmailid${d}" data-rule-email="true" maxLength="50" /></td>
							<td><form:input
									path="command.tradeMasterDetailDTO.tradeLicenseOwnerdetailDTO[${d}].troAdhno"
									type="text"
									disabled="${command.viewMode eq 'V' ? true : false }"
									class="form-control unit required-control hasAadharNo"
									data-rule-maxlength="12" data-rule-minlength="12"
									id="troAdhno${d}" maxlegnth="12" /></td>
							
							
							<c:if test="${command.viewMode ne 'V'}">
							<td class="text-center"><a href="javascript:void(0);"
								class="btn btn-danger btn-sm delButton"><i
									class="fa fa-minus" id=""></i></a></td></c:if>
									

						</tr>
						<c:set var="d" value="${d + 1}" scope="page" />
					</c:otherwise>
				</c:choose>

			</tbody>
		</table>
	</c:otherwise>
</c:choose>



