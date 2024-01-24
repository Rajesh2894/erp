<!-- Start JSP Necessary Tags -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/birthAndDeath/cemeterymaster.js"></script>

<apptags:breadcrumb></apptags:breadcrumb>
		
<div class="content">
	
	<div class="widget">
		<div class="widget-header">
			<h2>
				<strong><spring:message code="CemeteryMasterDTO.ceName" text="Cemetery Master" /></strong>
			</h2>
		</div>
		
		
		<div class="widget-content padding">
			<!-- Search Criteria -->
			<form:form action="CemeteryMaster.html" method="POST"
						commandName="command" class="form-horizontal form"
						name="frmCemeteryMasterSummary" id="frmCemeteryMasterSummary">
			<jsp:include page="/jsp/tiles/validationerror.jsp"/>

				<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv" style="display: none;">
						<i class="fa fa-plus-circle"></i>
				</div>
				

				<div class="form-group">
						<apptags:input labelCode="CemeteryMasterDTO.ceName"	path="cemeteryMasterDTO.ceName"  cssClass="hasCharacter form-control" isMandatory=""/>
						<label class="control-label col-sm-2"
						for="Census"> <spring:message code="CemeteryMasterDTO.cecpdTypeId" text="Cemetery master" />
						</label>
							     <c:set var="baseLookupCode" value="CTY" />
								 <apptags:lookupField
								  items="${command.getLevelData(baseLookupCode)}"
								  path="cemeteryMasterDTO.cpdTypeId" cssClass="form-control"
								  isMandatory="" hasId="true"
								  selectOptionLabelCode="selectdropdown" />
				</div>
			
				<!-- Buttons start -->
				<div class="text-center">
				<div class="text-center clear padding-10">
					<button type="button" id="search" class="btn btn-blue-2"
					onclick="searchCemeteryData()"
						title="Search">
						<i class="fa fa-search padding-right-5" aria-hidden="true"></i>
						<spring:message code="CemeteryMasterDTO.form.search" text="Search" />
						
						
					</button>

					<button type="button" id="reset"
						onclick="window.location.href='CemeteryMaster.html'"
						class="btn btn-warning" title="Reset">
						<i class="fa fa-undo padding-right-5" aria-hidden="true"></i>
						<spring:message code="CemeteryMasterDTO.form.reset" text="Reset" />
					</button>
                              
                              <!-- <input type="button"
						onclick="window.location.href='AdminHome.html'"
						class="btn btn-danger  hidden-print" value="Back"> -->
                   <button type="button" class="btn btn-danger " id="backId"
						data-toggle="tooltip" data-original-title="" 
						onclick="window.location.href='AdminHome.html'">
						<spring:message code="BirthRegDto.back"/>
					</button>           
                              
					<button type="button" id="add" class="btn btn-blue-2"
						onclick="openForm('CemeteryMaster.html','cemeteryMaster')"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="CemeteryMasterDTO.form.add" text="Add" />
					</button>

				</div>
				</div>

				<!-- Table Grid Start  -->
	         
	         <div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="cemeteryDataTable">
							<thead>
								<tr>
									<th width="5%"align="center"><spring:message
											code="BirthRegDto.srNo" text="Sr.No" /></th>
									<th width="25%" align="center"><spring:message
											code="CemeteryMasterDTO.ceNameMar" text="" /></th>
									<th width="15%" align="center"><spring:message
											code="CemeteryMasterDTO.ceName" text="" /></th>
									<th width="25%" align="center"><spring:message
											code="CemeteryMasterDTO.ceAddrMar" text="" /></th>
									<th width="10%" align="center"><spring:message
											code="CemeteryMasterDTO.ceAddr" text="" /></th>
									<th width="10%" align="center"><spring:message
											code="CemeteryMasterDTO.cecpdTypeId" text="Description" /></th>
									<th width="10%" align="center"><spring:message
											code="CemeteryMasterDTO.form.action" text="Action" /></th>							
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${cemeterys}" var="cemetery" varStatus="item">
									
								<tr>
								   <td class="text-center">${item.count}</td>
								   <td>${cemetery.ceNameMar}</td>
									<td>${cemetery.ceName}</td>
									<td>${cemetery.ceAddrMar}</td>
									<td>${cemetery.ceAddr}</td>
									<td>${cemetery.cpdDesc}</td>
								
									
									<td class="text-center">
											<button type="button" class="btn btn-blue-2 btn-sm"
												title="View Cemetery Master"
												onclick="modifyCemetery('${cemetery.ceId}','CemeteryMaster.html','viewBND','V')">
												<i class="fa fa-eye"></i>
											</button>
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit Cemetery Master"
												onclick="modifyCemetery('${cemetery.ceId}','CemeteryMaster.html','editBND','E')">
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



