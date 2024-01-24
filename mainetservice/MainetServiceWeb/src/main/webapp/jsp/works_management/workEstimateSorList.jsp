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
	src="js/works_management/workEstimateSummary.js"></script>
<link href="assets/libs/jquery-datatables/css/dataTables.bootstrap.css"
	rel="stylesheet" type="text/css" />
<script src="assets/libs/jquery-datatables/js/jquery.dataTables.min.js"></script>
<script src="assets/libs/jquery-datatables/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/mainet/validation.js"></script>

<!-- End JSP Necessary Tags -->

<c:choose>
	<c:when test="${command.esimateType eq 'S'}">
		<div class="form-group">
			<label class="label-control col-sm-2"><spring:message
					code="material.master.ssorname" text="Select SOR Name" /></label>
			<div class="col-sm-4">
				<form:select path="command.sorCommonId" id="sorId"
					onchange="getSorByValue();"
					class="form-control chosen-select-no-results"
					disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' || command.saveMode eq 'M'}">
					<form:option value="">
						<spring:message code='work.management.select' />
					</form:option>
					<c:forEach items="${command.scheduleOfrateMstList}" var="activeSor">
						<form:option value="${activeSor.sorId }">${activeSor.sorName }</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>

		<c:set var="d" value="0" scope="page" />
		<form:hidden path="command.removeDirectAbstract"
			id="removeDirectAbstract" />
		<div class="fromSorAdd" id="fromSorAdd">
			<table class="table table-bordered table-striped"
				id="estimateFromSor">
				<c:choose>
					<c:when test="${command.modeCpd eq 'N'}">
						<thead>
							<tr>
								<%-- 	<th scope="col" width="10%" align="center"><spring:message
										code="work.management.select" text="Select" /></th> --%>
								<th scope="col" width="12%" align="center"><spring:message
										code="work.estimate.sor.chapter" text="Chapter" /></th>
								<th scope="col" width="13%" align="center"><spring:message
										code="sor.subCategory" text="SubCategory" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="material.master.itemcode" text="Item Code" /></th>
								<th scope="col" width="25%" align="center"><spring:message
										code="work.management.description" text="Description" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.management.unit" text="Unit" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.estimate.rate" text="Rate" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="sor.labour" text="Labour" /></th>
								<c:if test="${command.saveMode ne 'V'}">
								<!-- 	<th scope="col" width="5%"><a onclick='return false;'
										class="btn btn-blue-2 btn-sm addButtonForSor"> <i
											class="fa fa-plus-circle"></i></a></th> -->
							<th width="8%" class="text-center" scope="col"><spring:message code="" text="Action" /></th>
								</c:if>
							</tr>
						</thead>
					</c:when>
					<c:otherwise>
						<thead>
							<tr>
								<%-- <th scope="col" width="10%" align="center"><spring:message
										code="sor.select" text="Select" /></th> --%>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.estimate.sor.chapter" text="Chapter" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.measurement.sheet.details.itemcode"
										text="Item Code" /></th>
								<th scope="col" width="35%" align="center"><spring:message
										code="work.management.description" text="Description" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.management.unit" text="Unit" /></th>
								<th scope="col" width="15%" align="center"><spring:message
										code="sor.baserate" text="Rate" /></th>
								<c:if test="${command.saveMode ne 'V'}">
								<!-- 	<th scope="col" width="5%"><a onclick='return false;'
										class="btn btn-blue-2 btn-sm addButtonForSor"> <i
											class="fa fa-plus-circle"></i></a></th> -->
								<th width="8%" class="text-center" scope="col"><spring:message
												code="" text="Action" /></th>
								</c:if>
							</tr>
						</thead>
					</c:otherwise>
				</c:choose>
				<tbody>
				
					<c:choose>
						<c:when test="${fn:length(command.sorDetailsList) > 0}">
							<c:forEach var="sorDetailsList" items="${command.sorDetailsList}"
								varStatus="status">
								<tr class="appendableFormSorClass">
									<td><form:select id="sorChapter${d}"
											path="command.sorDetailsList[${d}].sordCategory"
											class="form-control chosen-select-no-results"
											onchange="getAllItemsList(${d});">
											<form:option value="">
												<spring:message code="work.management.select" />
											</form:option>
											<c:choose>
												<c:when test="${command.saveMode ne 'V' && command.saveMode ne 'E'}">
													<c:forEach items="${command.chaperList}"
														var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>													
												</c:when>	
												<c:otherwise>											
													<c:forEach items="${command.getLevelData('WKC')}"
														var="lookUp">
														<form:option value="${lookUp.lookUpId}"
															code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
													</c:forEach>
												</c:otherwise>
											</c:choose>
										</form:select> <form:hidden
											path="command.sorDetailsList[${d}].workEstimateId"
											id="sorCheckedEstimationId${d}" /> <form:hidden
											path="command.sorDetailsList[${d}].sordId"
											id="sorDetailsId${d}" /> <form:hidden
											path="command.sorDetailsList[${d}].checkBox"
											id="sorCheckBox${d}" /> <form:hidden
											path="command.sorDetailsList[${d}].sorIteamUnit"
											id="sorIteamUnitHidden${d}" /></td>

									<c:if test="${command.modeCpd eq 'N'}">
										<td>${mstDto.sordSubCategory}</td>
									</c:if>

									<td><form:select id="sorIteamNumber${d}"
											path="command.sorDetailsList[${d}].sorDIteamNo"
											class="form-control chosen-select-no-results"
											onchange="getSorItmsDescription(${d});">
											<form:option selected="selected"
												value="${sorDetailsList.sorDIteamNo}">${sorDetailsList.sorDIteamNo}
							</form:option>
										</form:select></td>

									<td><form:textarea id="sorDDescription${d}"
											readonly="true"
											path="command.sorDetailsList[${d}].sorDDescription"
											class="form-control" /></td>

									<td><form:select id="sorIteamUnit${d}"
											path="command.sorDetailsList[${d}].sorIteamUnit"
											class="form-control" disabled="true">
											<form:option value="">
												<spring:message code="work.management.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('WUT')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:input
											path="command.sorDetailsList[${d}].sorBasicRate"
											readonly="true" class="form-control" id="sorBasicRate${d}" /></td>

									<c:if test="${command.modeCpd eq 'N'}">
										<td align="right">${mstDto.sorLabourRate}</td>
									</c:if>
									<c:if test="${command.saveMode ne 'V'}">
								<!-- 		<td class="text-center"><a href='#'
											onclick='return false;'
											class='btn btn-danger btn-sm delButton'><i
												class="fa fa-trash"></i></a></td> -->
                                          <!--  Defect #80050 -->
										<td class="text-center" ><button title="Add" onclick='return false;'
												class="btn btn-blue-2 btn-sm addButtonForSor" id="addButton${d}">
												<i class="fa fa-plus-circle"></i>
											</button>
											<button title="Delete"
												class='btn btn-danger btn-sm delButton' id="delButton${d}"
												onclick='return false;'>
												<i class="fa fa-trash-o"></i>
											</button></td>

									</c:if>
									<c:set var="d" value="${d + 1}" scope="page" />
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="appendableFormSorClass">
								<td><form:select id="sorChapter${d}"
										path="command.sorDetailsList[${d}].sordCategory"
										class="form-control chosen-select-no-results"
										onchange="getAllItemsList(${d});">
										<form:option value="">
											<spring:message code="work.management.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WKC')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select> <form:hidden
										path="command.sorDetailsList[${d}].workEstimateId"
										id="sorCheckedEstimationId${d}" /> <form:hidden
										path="command.sorDetailsList[${d}].sordId"
										id="sorDetailsId${d}" /> <form:hidden
										path="command.sorDetailsList[${d}].checkBox"
										id="sorCheckBox${d}" /> <form:hidden
										path="command.sorDetailsList[${d}].sorIteamUnit"
										id="sorIteamUnitHidden${d}" /></td>

								<c:if test="${command.modeCpd eq 'N'}">
									<td>${mstDto.sordSubCategory}</td>
								</c:if>

								<td><form:select id="sorIteamNumber${d}"
										path="command.sorDetailsList[${d}].sorDIteamNo"
										class="form-control chosen-select-no-results"
										onchange="getSorItmsDescription(${d});">
										<form:option value="">
											<spring:message code="work.management.select" />
										</form:option>
									</form:select></td>

								<td><form:textarea id="sorDDescription${d}" readonly="true"
										path="command.sorDetailsList[${d}].sorDDescription"
										class="form-control" /></td>

								<td><form:select id="sorIteamUnit${d}"
										path="command.sorDetailsList[${d}].sorIteamUnit"
										class="form-control" disabled="true">
										<form:option value="">
											<spring:message code="work.management.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WUT')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

								<td><form:input
										path="command.sorDetailsList[${d}].sorBasicRate"
										readonly="true" class="form-control" id="sorBasicRate${d}" /></td>

								<c:if test="${command.modeCpd eq 'N'}">
									<td align="right">${mstDto.sorLabourRate}</td>
								</c:if>
								<c:if test="${command.saveMode ne 'V'}">
									<!-- <td class="text-center"><a href='#'
										onclick='return false;'
										class='btn btn-danger btn-sm delButton'><i
											class="fa fa-trash"></i></a></td> -->
                                    <!--  Defect #80050 -->
									<td class="text-center" ><button title="Add" onclick='return false;'
											class="btn btn-blue-2 btn-sm addButtonForSor"
											id="addButton${d}">
											<i class="fa fa-plus-circle"></i>
										</button>
										<button title="Delete" class='btn btn-danger btn-sm delButton'
											id="delButton${d}" onclick='return false;'>
											<i class="fa fa-trash-o"></i>
										</button></td>
								</c:if>
							</tr>
						</c:otherwise>
					</c:choose>

				</tbody>
			</table>
		</div>
	</c:when>
	<c:when test="${command.esimateType eq 'P'}">
		<div class="form-group">
			<label class="label-control col-sm-2"><spring:message
					code="work.estimation.select.work.name" text="Select Work Name">
				</spring:message> </label>
			<div class="col-sm-4">
				<form:select path="command.workId" id="sorId"
					onchange="getSorByValue();"
					class="form-control chosen-select-no-results"
					disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' || command.saveMode eq 'M'}">
					<form:option value="">
						<spring:message code='work.management.select' />
					</form:option>
					<c:forEach items="${command.workDefinitionDto}" var="workDef">
						<form:option value="${workDef.workId }">${workDef.workName }</form:option>
					</c:forEach>
				</form:select>
			</div>

			<label class="label-control col-sm-2"><spring:message
					code="leadlift.master.ReportSorName" text="SOR Name" /></label>
			<div class="col-sm-4">
				<form:select path="command.sorCommonId" id="sorId"
					class="form-control chosen-select-no-results"
					disabled="${command.saveMode eq 'V' || command.saveMode eq 'E' || command.saveMode eq 'M' || command.esimateType eq 'P' }">
					<form:option value="">
						<spring:message code='work.management.select' />
					</form:option>
					<c:forEach items="${command.scheduleOfrateMstList}" var="activeSor">
						<form:option value="${activeSor.sorId }">${activeSor.sorName }</form:option>
					</c:forEach>
				</form:select>
			</div>

		</div>

		<%-- 		<c:set var="d" value="0" scope="page" />
		<div class="table-responsive clear">
			<table class="table table-bordered table-striped" id="datatables">

				<c:choose>
					<c:when test="${command.modeCpd eq 'N'}">
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="sor.select" text="Select" /></th>
								<th scope="col" width="15%" align="center"><spring:message
										code="work.estimate.sor.chapter" text="Chapter" /></th>
								<th scope="col" width="15%" align="center"><spring:message
										code="sor.subCategory" text="SubCategory" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.measurement.sheet.details.itemcode"
										text="Item Code" /></th>
								<th scope="col" width="30%" align="center"><spring:message
										code="work.management.description" text="Description" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.management.unit" text="Unit" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.estimate.rate" text="Rate" /></th>
								<th scope="col" width="15%" align="center"><spring:message
										code="sor.labour" text="Labour" /></th>
							</tr>
						</thead>
					</c:when>
					<c:otherwise>
						<thead>
							<tr>
								<th scope="col" width="10%" align="center"><spring:message
										code="sor.select" text="Select" /></th>
								<th scope="col" width="20%" align="center"><spring:message
										code="work.estimate.sor.chapter" text="Chapter" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.measurement.sheet.details.itemcode"
										text="Item Code" /></th>
								<th scope="col" width="35%" align="center"><spring:message
										code="work.management.description" text="Description" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.management.unit" text="Unit" /></th>
								<th scope="col" width="15%" align="center"><spring:message
										code="work.estimate.rate" text="Rate" /></th>
							</tr>
						</thead>
					</c:otherwise>
				</c:choose>

				<tbody>
					<c:forEach items="${command.sorDetailsList}" var="mstDto">
						<c:if test="${mstDto.flag eq 'SOR'}">
							<tr>
								<td align="center"><form:checkbox
										path="command.sorDetailsList[${d}].checkBox"
										id="sorCheckBox${d}" /> <form:hidden
										path="command.sorDetailsList[${d}].workEstimateId"
										id="sorCheckedEstimationId${d}" disabled="true" /> <form:hidden
										path="command.sorDetailsList[${d}].deletedFlagForSor"
										id="deletedFlagForSor${d}" /></td>
								<td>${mstDto.sordCategoryDesc}</td>
								<c:if test="${command.modeCpd eq 'N'}">
									<td>${mstDto.sordSubCategory}</td>
								</c:if>
								<td align="right">${mstDto.sorDIteamNo}</td>
								<td>${mstDto.sorDDescription}</td>
								<td>${mstDto.sorIteamUnitDesc}</td>
								<td align="right">${mstDto.sorBasicRate}</td>
								<c:if test="${command.modeCpd eq 'N'}">
									<td align="right">${mstDto.sorLabourRate}</td>
								</c:if>
							</tr>
						</c:if>
						<c:set var="d" value="${d + 1}" scope="page" />
					</c:forEach>
				</tbody>
			</table>
		</div> --%>


		<c:set var="d" value="0" scope="page" />
		<form:hidden path="command.removeDirectAbstract"
			id="removeDirectAbstract" />
		<div class="fromSorAdd" id="fromSorAdd">
			<table class="table table-bordered table-striped"
				id="estimateFromSor">
				<c:choose>
					<c:when test="${command.modeCpd eq 'N'}">
						<thead>
							<tr>
								<%-- 	<th scope="col" width="10%" align="center"><spring:message
										code="work.management.select" text="Select" /></th> --%>
								<th scope="col" width="12%" align="center"><spring:message
										code="work.estimate.sor.chapter" text="Chapter" /></th>
								<th scope="col" width="13%" align="center"><spring:message
										code="sor.subCategory" text="SubCategory" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="material.master.itemcode" text="Item Code" /></th>
								<th scope="col" width="25%" align="center"><spring:message
										code="work.management.description" text="Description" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.management.unit" text="Unit" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.estimate.rate" text="Rate" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="sor.labour" text="Labour" /></th>
								<c:if test="${command.saveMode ne 'V'}">
								<!-- 	<th scope="col" width="5%"><a onclick='return false;'
										class="btn btn-blue-2 btn-sm addButtonForSor"> <i
											class="fa fa-plus-circle"></i></a></th> -->
							<th width="8%" class="text-center" scope="col"><spring:message code="" text="Action" /></th>
								</c:if>
							</tr>
						</thead>
					</c:when>
					<c:otherwise>
						<thead>
							<tr>
								<%-- <th scope="col" width="10%" align="center"><spring:message
										code="sor.select" text="Select" /></th> --%>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.estimate.sor.chapter" text="Chapter" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.measurement.sheet.details.itemcode"
										text="Item Code" /></th>
								<th scope="col" width="35%" align="center"><spring:message
										code="work.management.description" text="Description" /></th>
								<th scope="col" width="10%" align="center"><spring:message
										code="work.management.unit" text="Unit" /></th>
								<th scope="col" width="15%" align="center"><spring:message
										code="sor.baserate" text="Rate" /></th>
								<c:if test="${command.saveMode ne 'V'}">
									<!-- <th scope="col" width="5%"><a onclick='return false;'
										class="btn btn-blue-2 btn-sm addButtonForSor"> <i
											class="fa fa-plus-circle"></i></a></th> -->

								<th width="8%" class="text-center" scope="col"><spring:message code="" text="Action" /></th>
								</c:if>
							</tr>
						</thead>
					</c:otherwise>
				</c:choose>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(command.sorDetailsList) > 0}">
							<c:forEach var="sorDetailsList" items="${command.sorDetailsList}"
								varStatus="status">
								<tr class="appendableFormSorClass">
									<td><form:select id="sorChapter${d}"
											path="command.sorDetailsList[${d}].sordCategory"
											class="form-control chosen-select-no-results"
											onchange="getAllItemsList(${d});">
											<form:option value="">
												<spring:message code="work.management.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('WKC')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select> <form:hidden
											path="command.sorDetailsList[${d}].workEstimateId"
											id="sorCheckedEstimationId${d}" /> <form:hidden
											path="command.sorDetailsList[${d}].sordId"
											id="sorDetailsId${d}" /> <form:hidden
											path="command.sorDetailsList[${d}].checkBox"
											id="sorCheckBox${d}" /> <form:hidden
											path="command.sorDetailsList[${d}].sorIteamUnit"
											id="sorIteamUnitHidden${d}" /></td>

									<c:if test="${command.modeCpd eq 'N'}">
										<td>${mstDto.sordSubCategory}</td>
									</c:if>

									<td><form:select id="sorIteamNumber${d}"
											path="command.sorDetailsList[${d}].sorDIteamNo"
											class="form-control chosen-select-no-results"
											onchange="getSorItmsDescription(${d});">
											<form:option selected="selected"
												value="${sorDetailsList.sorDIteamNo}">${sorDetailsList.sorDIteamNo}
							            </form:option>
										</form:select></td>

									<td><form:textarea id="sorDDescription${d}"
											readonly="true"
											path="command.sorDetailsList[${d}].sorDDescription"
											class="form-control" /></td>

									<td><form:select id="sorIteamUnit${d}"
											path="command.sorDetailsList[${d}].sorIteamUnit"
											class="form-control " disabled="true">
											<form:option value="">
												<spring:message code="work.management.select" />
											</form:option>
											<c:forEach items="${command.getLevelData('WUT')}"
												var="lookUp">
												<form:option value="${lookUp.lookUpId}"
													code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
											</c:forEach>
										</form:select></td>

									<td><form:input
											path="command.sorDetailsList[${d}].sorBasicRate"
											readonly="true" class="form-control" id="sorBasicRate${d}" /></td>

									<c:if test="${command.modeCpd eq 'N'}">
										<td align="right">${mstDto.sorLabourRate}</td>
									</c:if>
									<c:if test="${command.saveMode ne 'V'}">
										<!-- <td class="text-center"><a href='#'
											onclick='return false;'
											class='btn btn-danger btn-sm delButton'><i
												class="fa fa-trash"></i></a></td> -->
                                           <!--  Defect #80050 -->
										<td class="text-center"><button title="Add"
												class="btn btn-blue-2 btn-sm addButtonForSor"
												id="addButton${d}">
												<i class="fa fa-plus-circle"></i>
											</button>
											<button title="Delete"
												class='btn btn-danger btn-sm delButton' id="delButton${d}">
												<i class="fa fa-trash-o"></i>
											</button></td>
										<c:set var="d" value="${d + 1}" scope="page" />
									</c:if>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="appendableFormSorClass">
								<td><form:select id="sorChapter${d}"
										path="command.sorDetailsList[${d}].sordCategory"
										class="form-control chosen-select-no-results"
										onchange="getAllItemsList(${d});">
										<form:option value="">
											<spring:message code="work.management.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WKC')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select> <form:hidden
										path="command.sorDetailsList[${d}].workEstimateId"
										id="sorCheckedEstimationId${d}" /> <form:hidden
										path="command.sorDetailsList[${d}].sordId"
										id="sorDetailsId${d}" /> <form:hidden
										path="command.sorDetailsList[${d}].checkBox"
										id="sorCheckBox${d}" /> <form:hidden
										path="command.sorDetailsList[${d}].sorIteamUnit"
										id="sorIteamUnitHidden${d}" /></td>

								<c:if test="${command.modeCpd eq 'N'}">
									<td>${mstDto.sordSubCategory}</td>
								</c:if>

								<td><form:select id="sorIteamNumber${d}"
										path="command.sorDetailsList[${d}].sorDIteamNo"
										class="form-control chosen-select-no-results"
										onchange="getSorItmsDescription(${d});">
										<form:option value="">
											<spring:message code="work.management.select" />
										</form:option>
									</form:select></td>

								<td><form:textarea id="sorDDescription${d}" readonly="true"
										path="command.sorDetailsList[${d}].sorDDescription"
										class="form-control" /></td>

								<td><form:select id="sorIteamUnit${d}"
										path="command.sorDetailsList[${d}].sorIteamUnit"
										class="form-control" disabled="true">
										<form:option value="">
											<spring:message code="work.management.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WUT')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

								<td><form:input
										path="command.sorDetailsList[${d}].sorBasicRate"
										readonly="true" class="form-control" id="sorBasicRate${d}" /></td>

								<c:if test="${command.modeCpd eq 'N'}">
									<td align="right">${mstDto.sorLabourRate}</td>
								</c:if>
								<c:if test="${command.saveMode ne 'V'}">
									<!-- <td class="text-center"><a href='#'
										onclick='return false;'
										class='btn btn-danger btn-sm delButton'><i
											class="fa fa-trash"></i></a></td> -->

									<td class="text-center"><button title="Add"
											class="btn btn-blue-2 btn-sm addButtonForSor"
											id="addButton${d}">
											<i class="fa fa-plus-circle"></i>
										</button>
										<button title="Delete" class='btn btn-danger btn-sm delButton'
											id="delButton${d}">
											<i class="fa fa-trash-o"></i>
										</button></td>

									<c:set var="d" value="${d + 1}" scope="page" />
								</c:if>
							</tr>
						</c:otherwise>
					</c:choose>

				</tbody>
			</table>
		</div>

	</c:when>

	<c:otherwise>

		<h4 class="margin-bottom-10">
			<spring:message code="work.estimate.fromdirectAbstractEstimate"
				text="Upload SOR" />
		</h4>

		<c:set var="d" value="0" scope="page" />
		<form:hidden path="command.removeDirectAbstract"
			id="removeDirectAbstract" />
		<div class="directAbstractAdd" id="directAbstractAdd">
			<table class="table table-bordered table-striped"
				id="directAbstractTab">
				<thead>
					<tr>
						<th scope="col" width="12%" align="center"><spring:message
								code="work.estimate.sor.chapter" text="Chapter" /><span
							class="mand">*</span></th>
						<c:if test="${command.modeCpd eq 'N'}">
							<th scope="col" width="12%" align="center"><spring:message
									code="sor.subCategory" text="SubCategory" /><span class="mand">*</span></th>
						</c:if>
						<th scope="col" width="6%" align="center"><spring:message
								code="sor.item.code" text="Item Code" /><span class="mand">*</span></th>
						<th scope="col" width="12%" align="center"><spring:message
								code="work.management.description" text="Description" /><span
							class="mand">*</span></th>
						<th width="6%"><spring:message
								code="work.measurement.sheet.details.Length" text="Length" /><span
							class="mand">*</span></th>
						<th width="6%"><spring:message
								code="work.measurement.sheet.details.width" text="Breadth/Width" /><span
							class="mand">*</span></th>
						<th width="7%"><spring:message
								code="work.measurement.sheet.details.height" text="Depth/Height" /><span
							class="mand">*</span></th>
						<th width="5%"><spring:message code="" text="Nos." /><span
							class="mand">*</span></th>
						<th scope="col" width="5%" align="center"><spring:message
								code="work.estimate.quantity" text="Quantity" /><span
							class="mand">*</span></th>
						<th scope="col" width="7%" align="center"><spring:message
								code="work.management.unit" text="Unit" /><span class="mand">*</span></th>
						<th scope="col" width="5%" align="center"><spring:message
								code="work.estimate.rate" text="Rate" /><span class="mand">*</span></th>
						<th scope="col" width="7%" align="center"><spring:message
								code="work.estimate.Total" text="Amount" /><span class="mand">*</span></th>
					<!-- 	<th scope="col" width="4%"><a onclick='return false;'
							class="btn btn-blue-2 btn-sm addButton"> <i
								class="fa fa-plus-circle"></i></a></th> -->
						<c:if test="${command.saveMode ne 'V'}">
						<th width="8%" class="text-center" scope="col"><span
							class="small"><spring:message
									code="" text="Action" /></span></th>
						</c:if>
					</tr>
				</thead>
				<c:choose>
					<c:when test="${fn:length(command.workEstimateList) > 0}">
						<c:forEach var="schemeListData"
							items="${command.workEstimateList}" varStatus="status">
							<tr class="appendableClass">
								<td><form:select
										path="command.workEstimateList[${d}].sordCategory"
										class="form-control chosen-select-no-results" id="sordCategory${d}"
										disabled="${command.saveMode eq 'V'}">
										<form:option value="0">
											<spring:message code="holidaymaster.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WKC')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select> <form:hidden
										path="command.workEstimateList[${d}].workEstemateId"
										id="workEstemateId${d}" /></td>
								<c:if test="${command.modeCpd eq 'N'}">
									<td><form:input id="sordSubCategory${d}"
											path="command.workEstimateList[${d}].sordSubCategory"
											class=" form-control" maxlength="50"
											readonly="${command.saveMode eq 'V'}" /></td>
								</c:if>
								<td><form:input id="sorDIteamNo${d}"
										path="command.workEstimateList[${d}].sorDIteamNo"
										class=" form-control" maxlength="50"
										readonly="${command.saveMode eq 'V'}" /></td>

								<td><form:input id="sorDDescription${d}"
										path="command.workEstimateList[${d}].sorDDescription"
										class=" form-control" maxlength="50"
										readonly="${command.saveMode eq 'V'}" /></td>

								<td><form:input id="meMentLengthD${d}"
										path="command.workEstimateList[${d}].meMentLength"
										class=" decimalDirectAbstract form-control text-right"
										readonly="${command.saveMode eq 'V'}"
										onblur="calculateTotalRate();" placeholder="00.0000"
										onkeypress="return hasAmount(event, this, 6, 4)"
										onchange="getAmountFormatInDynamic((this),'meMentLength')" /></td>
								<td><form:input id="meMentBreadthD${d}"
										path="command.workEstimateList[${d}].meMentBreadth"
										class="decimalDirectAbstract form-control text-right"
										readonly="${command.saveMode eq 'V'}"
										onblur="calculateTotalRate();" placeholder="00 .0000"
										onkeypress="return hasAmount(event, this, 6, 4)"
										onchange="getAmountFormatInDynamic((this),'meMentBreadth')" /></td>
								<td><form:input id="meMentHeightD${d}"
										path="command.workEstimateList[${d}].meMentHeight"
										class=" form-control text-right"
										readonly="${command.saveMode eq 'V'}"
										onblur="calculateTotalRate();" placeholder="000.0000"
										onkeypress="return hasAmount(event, this, 8, 4)"
										onchange="getAmountFormatInDynamic((this),'meMentHeight')" /></td>

								<td><form:input id="meNosDirect${d}"
										path="command.workEstimateList[${d}].meNos"
										class=" form-control text-right hasNumber" maxlength="5"
										readonly="${command.saveMode eq 'V'}"
										onblur="calculateTotalRate();" /></td>

								<td><form:input id="workQuantityD${d}"
										path="command.workEstimateList[${d}].workEstimQuantity"
										onkeyup="calculateTotalRate();" placeholder="000.0000"
										class=" form-control text-right decimalDirectAbstract"
										onkeypress="return hasAmount(event, this, 8, 4)"
										onchange="getAmountFormatInDynamic((this),'workQuantity')"
										readonly="${command.saveMode eq 'V'}" /></td>

								<td><form:select id="sorIteamUnit${d}"
										path="command.workEstimateList[${d}].sorIteamUnit"
										class="form-control" disabled="${command.saveMode eq 'V'}">
										<form:option value="0">
											<spring:message code="work.management.select" />
										</form:option>
										<c:forEach items="${command.getLevelData('WUT')}" var="lookUp">
											<form:option value="${lookUp.lookUpId}"
												code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
										</c:forEach>
									</form:select></td>

								<td><form:input id="sorBasicRateD${d}"
										path="command.workEstimateList[${d}].sorBasicRate"
										onkeyup="calculateTotalRate();" placeholder="00.00"
										class=" form-control text-right  decimal"
										onkeypress="return hasAmount(event, this, 13, 2)"
										onchange="getAmountFormatInDynamic((this),'sorBasicRate')"
										readonly="${command.saveMode eq 'V'}" /></td>


								<td><form:input id="workEstimAmountD${d}"
										path="command.workEstimateList[${d}].workEstimAmount"
										class=" form-control text-right" readonly="true"
										placeholder="0000.00" /></td>
                                <!--  Defect #80050 -->
								<c:if test="${command.saveMode ne 'V'}">
									<td class="text-center">
										<button title="Add" class="btn btn-blue-2 btn-sm addButton"
											id="addButton${d}">
											<i class="fa fa-plus-circle"></i>
										</button>
										<button title="Delete" class="btn btn-danger btn-sm delButton"
											id="delButton${d}">
											<i class="fa fa-trash-o"></i>
										</button>
									</td>
								</c:if>
								<!-- 	<td class="text-center"><a href='#' onclick='return false;'
									class='btn btn-danger btn-sm delButton'><i
										class="fa fa-trash"></i></a></td> -->

							</tr>
							<c:set var="d" value="${d + 1}" scope="page" />
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="appendableClass">
							<td><form:select
									path="command.workEstimateList[${d}].sordCategory"
									class="form-control chosen-select-no-results" id="sordCategory${d}">
									<form:option value="0">
										<spring:message code="holidaymaster.select" />
									</form:option>
									<c:forEach items="${command.getLevelData('WKC')}" var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select></td>
							<c:if test="${command.modeCpd eq 'N'}">
								<td><form:input id="sordSubCategory${d}"
										path="command.workEstimateList[${d}].sordSubCategory"
										class=" form-control" maxlength="50" /></td>
							</c:if>
							<td><form:input id="sorDIteamNo${d}"
									path="command.workEstimateList[${d}].sorDIteamNo"
									class=" form-control" maxlength="50" /></td>

							<td><form:input id="sorDDescription${d}"
									path="command.workEstimateList[${d}].sorDDescription"
									class=" form-control" maxlength="50" /></td>

							<td><form:input id="meMentLengthD${d}"
									path="command.workEstimateList[${d}].meMentLength"
									class=" decimalDirectAbstract form-control text-right"
									onblur="calculateTotalRate();" placeholder="00.0000"
									onkeypress="return hasAmount(event, this, 6, 4)"
									onchange="getAmountFormatInDynamic((this),'meMentLength')" /></td>
							<td><form:input id="meMentBreadthD${d}"
									path="command.workEstimateList[${d}].meMentBreadth"
									class="decimalDirectAbstract form-control text-right"
									onblur="calculateTotalRate();" placeholder="00 .0000"
									onkeypress="return hasAmount(event, this, 6, 4)"
									onchange="getAmountFormatInDynamic((this),'meMentBreadth')" /></td>
							<td><form:input id="meMentHeightD${d}"
									path="command.workEstimateList[${d}].meMentHeight"
									class=" form-control text-right" onblur="calculateTotalRate();"
									placeholder="000.000"
									onkeypress="return hasAmount(event, this, 8, 4)"
									onchange="getAmountFormatInDynamic((this),'meMentHeight')" /></td>

							<td><form:input id="meNosDirect${d}"
									path="command.workEstimateList[${d}].meNos"
									class=" form-control text-right hasNumber" maxlength="5"
									readonly="${command.saveMode eq 'V'}"
									onblur="calculateTotalRate();" /></td>

							<td><form:input id="workQuantityD${d}"
									path="command.workEstimateList[${d}].workEstimQuantity"
									onkeyup="calculateTotalRate();" placeholder="000.0000"
									class=" form-control decimalDirectAbstract text-right"
									onkeypress="return hasAmount(event, this, 8, 4)"
									onchange="getAmountFormatInDynamic((this),'workQuantity')" /></td>

							<td><form:select id="sorIteamUnit${d}"
									path="command.workEstimateList[${d}].sorIteamUnit"
									class="form-control">
									<form:option value="0">
										<spring:message code="work.management.select" />
									</form:option>
									<c:forEach items="${command.getLevelData('WUT')}" var="lookUp">
										<form:option value="${lookUp.lookUpId}"
											code="${lookUp.lookUpCode}">${lookUp.lookUpDesc}</form:option>
									</c:forEach>
								</form:select></td>

							<td><form:input id="sorBasicRateD${d}"
									path="command.workEstimateList[${d}].sorBasicRate"
									onkeyup="calculateTotalRate();" placeholder="00.00"
									class=" form-control decimal text-right"
									onkeypress="return hasAmount(event, this, 13, 2)"
									onchange="getAmountFormatInDynamic((this),'sorBasicRate')" /></td>


							<td><form:input id="workEstimAmountD${d}"
									path="command.workEstimateList[${d}].workEstimAmount"
									class=" form-control text-right" readonly="true"
									placeholder="0000.00" /></td>

                              <!--  Defect #80050 -->
							<c:if test="${command.saveMode ne 'V'}">
								<td class="text-center">
									<button title="Add" class="btn btn-blue-2 btn-sm addButton"
										id="addButton${d}">
										<i class="fa fa-plus-circle"></i>
									</button>
									<button title="Delete" class="btn btn-danger btn-sm delButton"
										id="delButton${d}">
										<i class="fa fa-trash-o"></i>
									</button>
								</td>
							</c:if>
							<!-- <td class="text-center"><a href='#' onclick='return false;'
								class='btn btn-danger btn-sm delButton'><i
									class="fa fa-trash"></i></a></td> -->

						</tr>
						<c:set var="d" value="${d + 1}" scope="page" />


						<!-- Excel Upload Start-->
						<div class="form-group">
							<label class="col-sm-2 control-label" for="UploadDocument"><spring:message
									code="excel.upload" text="Excel Upload" /></label>
							<div class="col-sm-2">
								<apptags:formField fieldPath="command.excelFilePathDirects"
									showFileNameHTMLId="true" fileSize="WORK_COMMON_MAX_SIZE"
									maxFileCount="CHECK_LIST_MAX_COUNT"
									validnFunction="EXCEL_IMPORT_VALIDATION_EXTENSION"
									currentCount="0" fieldType="7">
								</apptags:formField>
								<small class="text-blue-2"><spring:message
									code="work.upload.size" text="(Upload Excel upto 5MB )" /></small>
							</div>
							<div class="col-sm-2">
								<form:hidden path="command.excelFilePathDirects" id="filePath" />
								<button type="button" class="btn btn-success save"
									name="button-save" value="saveExcel" style=""
									onclick="uploadExcelFile();" id="button-save">
									<spring:message code="Upload" text="Save Excel" />
								</button>
							</div>

							<label class="col-sm-2 control-label" for="ExportDocument"><spring:message
									code="work.estimate.download" text="DownLoad Template" /></label>
							<div class="col-sm-4">
								<button type="button" class="btn btn-success save"
									name="button-Cancel" value="import" style=""
									onclick="exportExcelData();" id="import">
									<spring:message code="" text="Download" />
								</button>
							</div>
						</div>
						<!-- Excel Upload End-->


					</c:otherwise>
				</c:choose>
			</table>
		</div>
	</c:otherwise>
</c:choose>

<div class="text-center clear padding-10">
	<c:if test="${command.requestFormFlag eq 'AP'}">
		<button type="button" class="button-input btn btn-danger"
			name="button-Cancel" onclick="backSendForApprovalForm();"
			id="button-Cancel">
			<i class="fa fa-chevron-circle-left padding-right-5"></i>
			<spring:message code="works.management.back" text="" />
		</button>
	</c:if>
	<c:if test="${command.requestFormFlag eq 'TNDR'}">
		<button type="button" class="button-input btn btn-danger"
			name="button-Cancel" onclick="backToTender();" id="button-Cancel">
			<i class="fa fa-chevron-circle-left padding-right-5"></i>
			<spring:message code="works.management.back" text="" />
		</button>
	</c:if>
	<c:if
		test="${command.requestFormFlag ne 'AP' && command.requestFormFlag ne 'TNDR' && command.saveMode ne 'M'}">
		<button type="button" class="button-input btn btn-danger"
			name="button-Cancel" value="Cancel" style="" onclick="backForm();"
			id="button-Cancel">
			<i class="fa fa-chevron-circle-left padding-right-5"></i>
			<spring:message code="works.management.back" text="" />
		</button>
	</c:if>
	<c:if test="${command.saveMode eq 'M'}">
		<button type="button" class="button-input btn btn-danger"
			name="button-Cancel" value="Cancel" style=""
			onclick="backAddMBMasterForm();" id="button-Cancel">
			<i class="fa fa-chevron-circle-left padding-right-5"></i>
			<spring:message code="" text="Back To MB" />
		</button>
	 </c:if>
	 <c:if test="${command.saveMode ne 'V'}">
		<button type="button" id="save" class="btn btn-success btn-submit"
			onclick="saveData(this);">
			<i class="fa fa-sign-out padding-right-5"></i>
			<spring:message code="wms.AddSelected.Items"
				text="Add Selected Items" />
		</button>
	</c:if>
 
</div>
