<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="apptags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript" src="js/mainet/validation.js"></script>
<script type="text/javascript" src="js/mainet/file-upload.js"></script>
<script type="text/javascript" src="js/birthAndDeath/deathRegistration.js"></script>
<apptags:breadcrumb></apptags:breadcrumb>
<script>

	$(document).ready(function() {
		$('.datepicker').datepicker({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			maxDate : '-0d',
			changeYear : true,

		});
	});

</script>



<div class="pagediv">

	<div class="content animated top">
		<div class="widget">
			<div class="widget-header">
				<h2>
					<spring:message code="TbDeathregDTO.deathRegSum" text="Death Registration Summary" />
				</h2>
				<div class="additional-btn">
					<a href="#" data-toggle="tooltip" data-original-title="Help"> <i
						class="fa fa-question-circle fa-lg"></i> <span class="hide">Help</span>
					</a>
				</div>
			</div>
			<div class="widget-content padding">
				<form:form id="frmDeathRegistrationForm"
					action="DeathRegistration.html" method="POST"
					class="form-horizontal" name="deathRegFormId">

					<jsp:include page="/jsp/tiles/validationerror.jsp" />

					<div
						class="warning-div error-div alert alert-danger alert-dismissible"
						id="errorDiv"></div>

					<h4>
						<spring:message code="TbDeathregDTO.form.deathRegistration" text="Death Registration" />
					</h4>
					
					<div class="form-group"> 
						<apptags:input labelCode="TbDeathregDTO.applicationNo" 
							path="tbDeathregDTO.applnId" isMandatory="false"
							cssClass="hasNumber form-control" maxlegnth="20">
						</apptags:input>
						
						<label class="col-sm-2 control-label">
							<spring:message code="TbDeathregDTO.drDod" text="Date of Death" />
						</label>
						<div class="col-sm-4">
						    <div class="input-group">
						        <form:input path="tbDeathregDTO.drDod"
						            cssClass="form-control mandColorClass" id="drDod"
						            readonly="false" />
						        <div class="input-group-addon">
						            <i class="fa fa-calendar"></i>
						        </div>
						    </div>
						</div>
						<%-- <apptags:date fieldclass="datepicker" readonly="true"
							labelCode="TbDeathregDTO.drDod" datePath="tbDeathregDTO.drDod">
						</apptags:date> --%>	
						
					</div>

					<div class="text-center">
						<button type="button" class="btn btn-blue-3" title="Search"
							onclick="SearchDeathDraftData(this)">
							<spring:message code="TbDeathregDTO.form.search" text="Search" />
						</button>
					
				     	<button type="button" class="btn btn-warning btn-yellow-2"
							title="Submit"
							onclick="window.location.href ='DeathRegistration.html'">
						  <spring:message code="TbDeathregDTO.form.reset" text="Reset" />
						</button>
						<apptags:backButton url="AdminHome.html"></apptags:backButton>
						<button type="button" id="add" class="btn btn-green-3"
						onclick="openForm('DeathRegistration.html','deathRegDraft')"
						title="Add">
						<i class="fa fa-plus-circle padding-right-5" aria-hidden="true"></i>
						<spring:message code="TbDeathregDTO.form.add" text="Add" />
				     	</button>
					</div>
					
						
						<!-- grid search -->
					
					 <div class="table-responsive clear">
						<table class="table table-striped table-bordered"
							id="deathRegDraftTable">
							<thead>
								<tr>
									<th width="5%" align="center"><spring:message
											code="TbDeathregDTO.form.srno" text="Sr.No." /></th>
									<th width="20%" align="center"><spring:message
											code="TbDeathregDTO.drDod" text="Deceased Date" /></th>
									<th width="25%" align="center"><spring:message
											code="TbDeathregDTO.applicationNo" text="Application No." /></th>
									<th width="10%" align="center"><spring:message
											code="TbDeathregDTO.drDeceasedname" text="Deceased Name" /></th>
									<th width="20%" align="center"><spring:message
											code="TbDeathregDTO.drSex" text="Sex" /></th>
									<th width="10%" align="center">
										<spring:message code="TbDeathregDTO.cpdRegUnit" text="Reg. Unit" />
									</th>
									<th width="20%" align="center"><spring:message
											code="TbDeathregDTO.form.action" text="Action" /></th>
								</tr>
							</thead>
							
							<tbody>
							<c:forEach items="${data}" var="deathRegDTO" varStatus="item">
									
								<tr>
								   <td class="text-center">${item.count}</td>
								   <td align="center"><fmt:formatDate pattern="dd/MM/yyyy"
												value="${deathRegDTO.drDod}"/></td>
								   
								   <c:choose>
											<c:when test="${empty deathRegDTO.applnId}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${deathRegDTO.applnId}</td>
											</c:otherwise>
										</c:choose>
								   
								   	<c:choose>
											<c:when test="${empty deathRegDTO.drDeceasedname}">
												<td align="center"><p>-</p></td>
											</c:when>
											<c:otherwise>
												<td align="center">${deathRegDTO.drDeceasedname}</td>
											</c:otherwise>
										</c:choose>
								   
								   <c:choose>
											<c:when test="${deathRegDTO.drSex==0}">
												<td align="center"><p>-</p></td>
											</c:when>
											<%-- <c:when test="${deathRegDTO.drSex==79}">
												<td align="center"><p>MALE</p></td>
											</c:when>
											<c:when test="${deathRegDTO.drSex==80}">
												<td align="center"><p>FEMALE</p></td>
											</c:when> --%>
											<c:otherwise>
												<td align="center">${deathRegDTO.drSex}</td>
											</c:otherwise>
										</c:choose>
									
									<c:choose>
											<c:when test="${deathRegDTO.cpdDesc==null}">
												<td align="center"><p>-</p></td>
											</c:when>
								
											<c:otherwise>
												<td align="center">${deathRegDTO.cpdDesc}</td>
											</c:otherwise>
										</c:choose>
									
									
									
									<td class="text-center">
											<button type="button" class="btn btn-warning btn-sm"
												title="Edit Death registration Form"
											    onclick="modifyDeath('${deathRegDTO.drDraftId}', '${deathRegDTO.applnId}','DeathRegistration.html','editBND','E')">
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
</div>









 
 
 
