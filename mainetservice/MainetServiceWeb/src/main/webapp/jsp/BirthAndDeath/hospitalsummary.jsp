
<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/birthAndDeath/hospitalMaster.js"></script>


<apptags:breadcrumb></apptags:breadcrumb>
		
<div class="content">

	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="HospitalMasterDTO.form.name" text="Hospital Master" /></strong>
			</h2>
		</div>
		
		<div class="widget-content padding">
			<!-- Search Criteria -->
			<form:form action="HospitalMaster.html" method="POST"
						commandName="command" class="form-horizontal form"
						name="frmHospitalMasterSummary" id="frmHospitalMasterSummary">
			<jsp:include page="/jsp/tiles/validationerror.jsp"/>

				<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;">
						<i class="fa fa-plus-circle"></i>
				</div>
				

				   <div class="form-group">
						<apptags:input labelCode="HospitalMasterDTO.hiName"	path="hospitalMasterDTO.hiName" isMandatory="" cssClass="hasCharacter form-control" maxlegnth="20"/>
						<label class="control-label col-sm-2"
						for="Census"> <spring:message code="HospitalMasterDTO.hicpdTypeId" text="Hospital Type" />
						</label>
							     <c:set var="baseLookupCode" value="HTY" />
								 <apptags:lookupField
								  items="${command.getLevelData(baseLookupCode)}"
								  path="hospitalMasterDTO.cpdTypeId" cssClass="form-control"
								  isMandatory="" hasId="true"
								  selectOptionLabelCode="selectdropdown" />
				</div>
			
				<!-- Buttons start -->
				<div class="text-center">
				<div class="text-center clear padding-10">
					<button type="button" id="search" class="btn btn-blue-2" onclick = "searchHospitalData()"
						title="Search">
						
						<spring:message code="HospitalMasterDTO.form.search" text="Search" />
					</button>

					<button type="button" id="reset"
						onclick="window.location.href='HospitalMaster.html'"
						class="btn btn-warning" title="Reset">
						
						<spring:message code="HospitalMasterDTO.form.reset" text="Reset" />
					</button>
					
							<!-- <input type="button"
						onclick="window.location.href='AdminHome.html'"
						class="btn btn-danger  hidden-print" value="Back"> -->
					<input type="button" value="<spring:message code="back.msg" text="Back"/>" class="btn btn-danger"
						onclick="window.location.href='AdminHome.html'" id="backBtn">	
						 
					<button type="button" id="add" class="btn btn-blue-2"
						onclick="openForm('HospitalMaster.html','hospitalMaster')"
						title="Add">
						
						<spring:message code="HospitalMasterDTO.form.add" text="Add" />
					</button>

				</div>
				</div>

				<!-- Table Grid Start  -->
	         
	          <div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="hospitalDataTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="BirthRegDto.srNo" text="Sr.No" /></th>
									 <th width="20%" align="center"><spring:message
											code="HospitalMasterDTO.hiNameMar" text="Hospital Name(Reg)" /></th>
									<th width="10%" align="center"><spring:message
											code="HospitalMasterDTO.hiName" text="Hospital Name" /></th>
									<th width="15%" align="center"><spring:message
											code="HospitalMasterDTO.hiAddrMar" text="Address(Reg)" /></th>
									<th width="10%" align="center"><spring:message
											code="HospitalMasterDTO.hiAddr" text="Address" /></th>
									<th width="20%" align="center">
										<spring:message code="HospitalMasterDTO.hospDesc" text="Description" />
									</th>
									<th width="10%" align="center"><spring:message
											code="HospitalMasterDTO.hiCode" text="Code" /></th>
									<th width="10%" align="center"><spring:message
											code="HospitalMasterDTO.form.action" text="Action" /></th> 
										
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${hospitals}" var="hospital" varStatus="item">
									
								<tr>
								   <td class="text-center">${item.count}</td>
								    <td>${hospital.hiNameMar}</td>
									<td>${hospital.hiName}</td>
									<td>${hospital.hiAddrMar}</td>
									<td>${hospital.hiAddr}</td>
									<td>${hospital.cpdDesc}</td>			
									<td>${hospital.hiCode}</td>
									
									<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View Hospital Master"
												onclick="modifyHospital('${hospital.hiId}','HospitalMaster.html','viewBND','V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit Hospital Master"
												onclick="modifyHospital('${hospital.hiId}','HospitalMaster.html','editBND','E')">
												<i class="fa fa-pencil"></i>
											</button> 		
									</td>						
								</tr>
							</c:forEach>
							</tbody>
							
							</table>
							</div>		
			</form:form>
					
</div>
</div>
</div>

